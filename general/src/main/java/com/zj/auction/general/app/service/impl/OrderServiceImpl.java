package com.zj.auction.general.app.service.impl;

import com.github.pagehelper.PageHelper;
import com.zj.auction.common.constant.Constant;
import com.zj.auction.common.constant.RedisConstant;
import com.zj.auction.common.dto.BaseOrderDto;
import com.zj.auction.common.dto.OrderNotifyDto;
import com.zj.auction.common.dto.PaymentVoucher;
import com.zj.auction.common.dto.PickUpDto;
import com.zj.auction.common.enums.*;
import com.zj.auction.common.exception.CustomException;
import com.zj.auction.common.mapper.*;
import com.zj.auction.common.model.*;
import com.zj.auction.common.query.OrderQuery;
import com.zj.auction.common.util.StringUtils;
import com.zj.auction.general.app.service.AuctionService;
import com.zj.auction.general.app.service.OrderMqService;
import com.zj.auction.general.app.service.OrderService;
import com.zj.auction.general.app.service.RebateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final StockMapper stockMapper;
    private final GoodsMapper goodsMapper;
    private final AuctionService auctionService;
    private final OrderMqService orderMqService;
    private final AddressMapper addressMapper;
    private final ExpressOrderMapper expressOrderMapper;
    private final SystemCnfMapper systemCnfMapper;
    private final RebateService rebateService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper, StockMapper stockMapper, GoodsMapper goodsMapper, AuctionService auctionService, OrderMqService orderMqService, AddressMapper addressMapper, ExpressOrderMapper expressOrderMapper, SystemCnfMapper systemCnfMapper, RebateService rebateService) {
        this.orderMapper = orderMapper;
        this.stockMapper = stockMapper;
        this.goodsMapper = goodsMapper;
        this.auctionService = auctionService;
        this.orderMqService = orderMqService;
        this.addressMapper = addressMapper;
        this.expressOrderMapper = expressOrderMapper;
        this.systemCnfMapper = systemCnfMapper;
        this.rebateService = rebateService;
    }

    @Override
    public void finishPayCallBack(Long orderSn) {
        Order buyerOrder = orderMapper.selectOrderByOrderNumber(orderSn);
        Byte orderStatus = buyerOrder.getOrderStatus();
        if (!OrderStatEnum.UN_PAYMENT.isEqual(orderStatus)) {
            log.error("用户支付拍品状态异常,订单号{}", orderSn);
            return;
        }
        Long stockNumber = buyerOrder.getStockNumber();
        Order sellerOrder = orderMapper.selectOwnerOrderByStockNumberAndStatus(stockNumber, OrderStatEnum.WAIT_BUYER_PAYMENT.getCode());
        if (Objects.isNull(sellerOrder)) {
            log.error("卖家订单状态异常,库存号{}", stockNumber);
        }
        buyerOrder.setOrderStatus(OrderStatEnum.WAIT_SELLER_CONFIRM.getCode());
        sellerOrder.setOrderStatus(OrderStatEnum.UN_CONFIRM.getCode());
        orderMapper.updateByPrimaryKeySelective(buyerOrder);
        orderMapper.updateByPrimaryKeySelective(sellerOrder);
    }

    @Override
    public boolean uploadPaymentVoucher(PaymentVoucher paymentVoucher) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String orderVoucher = paymentVoucher.getOrderVoucher();
        Long orderSn = paymentVoucher.getOrderSn();
        Order buyerOrder = orderMapper.selectOrderByOrderNumber(orderSn);
        if (!buyerOrderUnPaid(buyerOrder)) {
            throw new CustomException(StatusEnum.ORDER_STATUS_ERROR);
        }
        if(Objects.equals(buyerOrder.getUserId(),user.getUserId())){
            throw new CustomException(StatusEnum.OWNER_ORDER_MISS_ERROR);
        }
        if (StringUtils.isBlank(orderVoucher)) {
            throw new CustomException(StatusEnum.PAYMENT_VOUCHER_BLANK_ERROR);
        }
        Order sellerOrder = orderMapper.selectOwnerOrderByStockNumberAndStatus(buyerOrder.getStockNumber(), OrderStatEnum.WAIT_BUYER_PAYMENT.getCode());
        if (Objects.isNull(sellerOrder)) {
            throw new CustomException(StatusEnum.ORDER_STATUS_ERROR);
        }
        if(!OrderStatEnum.WAIT_BUYER_PAYMENT.isEqual(sellerOrder.getOrderStatus())){
            throw new CustomException(StatusEnum.ORDER_STATUS_ERROR);
        }
        buyerOrder.setOrderStatus(OrderStatEnum.WAIT_SELLER_CONFIRM.getCode());
        sellerOrder.setOrderStatus(OrderStatEnum.UN_CONFIRM.getCode());
        orderMapper.updateByPrimaryKeySelective(buyerOrder);
        orderMapper.updateByPrimaryKeySelective(sellerOrder);
        OrderNotifyDto orderNotifyDto = new OrderNotifyDto(sellerOrder.getOrderSn(), OrderNotifyEnum.CONFIRM_TIME_OUT.getCode());
        orderMqService.sendOrderDelayMsg(orderNotifyDto, 20, TimeUnit.MINUTES);
        return true;
    }

    private boolean buyerOrderUnPaid(Order order) {
        return Objects.nonNull(order)
                && (OrderStatEnum.UN_PAYMENT.isEqual(order.getOrderStatus())
                || OrderStatEnum.SELLER_REJECT.isEqual(order.getOrderStatus())
        );
    }

    @Override
    public Boolean transfer2Auction(Long orderSn) {
//        User user = SecurityUtils.getPrincipal();
//        if(Objects.isNull(user)){
//            throw new CustomException(StatusEnum.USER_TOKEN_ERROR);
//        }
        Long userId = 2L;
        Order order = orderCheck(userId, orderSn);
        if(OrderStatEnum.WAIT_MARGIN.isEqual(order.getOrderStatus())){
            return true;
        }
        Stock stock = stockMapper.selectOneBySn(orderSn);
        if (Objects.isNull(stock)) {
            throw new CustomException(StatusEnum.STOCK_NOT_MATCH_ERROR);
        }
        Goods goods = goodsMapper.selectByPrimaryKey(stock.getGoodsId());
        if (!checkTransferLimit(goods, stock)) {
            throw new CustomException(StatusEnum.TRANSFER_LIMIT_ERROR);
        }
        // 更改订单状态
        Order updateOrder = new Order();
        updateOrder.setId(order.getId());
        updateOrder.setOrderStatus(OrderStatEnum.WAIT_MARGIN.getCode());
        orderMapper.updateByPrimaryKeySelective(updateOrder);
        Stock updateStock = new Stock();
        updateStock.setId(stock.getId());
        updateStock.setStockStatus(OrderStatEnum.WAIT_MARGIN.getCode());
        stockMapper.updateByPrimaryKeySelective(updateStock);
        return true;
    }

    /**
     * 转拍限制检查
     *
     * @param goods 商品
     * @param stock 库存
     * @return true/false
     */
    private boolean checkTransferLimit(Goods goods, Stock stock) {
        Integer transferNum = stock.getTransferNum();
        Integer handNum = goods.getHandNum();
        return transferNum < handNum;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transferPaymentCallBack(Long orderSn) {
        Order order = orderMapper.selectOrderByOrderNumber(orderSn);
        if(Objects.isNull(order)){
            log.error("转拍支付回调,订单不存在:{}",orderSn);
            return;
        }
        if (!OrderStatEnum.WAIT_MARGIN.isEqual(order.getOrderStatus())) {
            log.error("转拍支付回调,订单状态异常:{}",order);
            return;
        }
        Long stockNumber = order.getStockNumber();
        Stock stock = stockMapper.selectOneBySn(stockNumber);
        byte statCode = OrderStatEnum.ON_AUCTION.getCode();
        int transferNum = stock.getTransferNum() + 1;
        Byte orderTypeCode = order.getOrderType();
        OrderTypeEnum orderType = OrderTypeEnum.codeOf(orderTypeCode);
        String cashPremium = systemCnfMapper.selectValueByKeyName(Constant.CASH_PREMIUM_KEY);
        String integralPremium = systemCnfMapper.selectValueByKeyName(Constant.INTEGRAL_PREMIUM_KEY);
        // 现金
        BigDecimal cashPrice = order.getTotalAmount();
        // 积分
        BigDecimal integralPrice = order.getIntegralFee();

        BigDecimal cashPremiumPrice = cashPrice.multiply(new BigDecimal(cashPremium).add(BigDecimal.ONE));
        BigDecimal integralPremiumPrice = integralPrice.multiply(new BigDecimal(integralPremium).add(BigDecimal.ONE));
        stock.setStockStatus(statCode);
        stock.setTransferNum(transferNum);
        stock.setCashPrice(cashPremiumPrice);
        stock.setIntegralPrice(integralPremiumPrice);

        Order updateOrder = new Order();
        updateOrder.setId(order.getId());
        updateOrder.setOrderStatus(statCode);

        orderMapper.updateByPrimaryKeySelective(updateOrder);
        stockMapper.updateByPrimaryKeySelective(stock);
        auctionService.addAuction(stock,orderType);
        rebateService.rebateOrder(order);
    }

    /**
     * 调取支付转拍手续费接口
     *
     * @param order 订单信息
     * @return unknown
     */
    public Object doPayTransferFee(Order order) {
        return null;
    }

    private Order orderCheck(Long userId, Long orderSn) {
        if (Objects.isNull(orderSn)) {
            throw new CustomException(StatusEnum.PARAM_ERROR);
        }
        Order order = orderMapper.selectOrderByOrderNumber(orderSn);
        if (Objects.isNull(order)) {
            throw new CustomException(StatusEnum.OWNER_ORDER_MISS_ERROR);
        }
        Byte orderStatus = order.getOrderStatus();
        if(!(OrderStatEnum.AUCTION_SUCCESS.isEqual(orderStatus)
                ||OrderStatEnum.WAIT_MARGIN.isEqual(orderStatus))){
            throw new CustomException(StatusEnum.ORDER_STATUS_ERROR);
        }
        Long ownerId = order.getUserId();
        if (!Objects.equals(userId, ownerId)) {
            throw new CustomException(StatusEnum.ORDER_STATUS_ERROR);
        }
        return order;
    }


    @Override
    public boolean finishOrder(Long orderSn) {
//        User user = SecurityUtils.getPrincipal();
//        if(Objects.isNull(user)){
//            throw new CustomException(StatusEnum.USER_TOKEN_ERROR);
//        }
        if (Objects.isNull(orderSn)) {
            throw new CustomException(StatusEnum.PARAM_ERROR);
        }
        Order sellerOrder = orderMapper.selectOrderByOrderNumber(orderSn);
        Byte orderStatus = sellerOrder.getOrderStatus();
        if (!OrderStatEnum.UN_CONFIRM.isEqual(orderStatus)) {
            throw new CustomException(StatusEnum.ORDER_STATUS_ERROR);
        }
        if(!Objects.equals(sellerOrder.getUserId(), 2L)){
            throw new CustomException(StatusEnum.ORDER_STATUS_ERROR);
        }
        Long stockNumber = sellerOrder.getStockNumber();
        Order buyerOrder = orderMapper.selectOwnerOrderByStockNumberAndStatus(stockNumber, OrderStatEnum.WAIT_SELLER_CONFIRM.getCode());
        if (Objects.isNull(buyerOrder)) {
            throw new CustomException(StatusEnum.ORDER_STATUS_ERROR);
        }
        sellerOrder.setOrderStatus(OrderStatEnum.FINISH.getCode());
        buyerOrder.setOrderStatus(OrderStatEnum.AUCTION_SUCCESS.getCode());
        orderMapper.updateByPrimaryKeySelective(sellerOrder);
        orderMapper.updateByPrimaryKeySelective(buyerOrder);
        return true;
    }

    @Override
    public Boolean rejectConfirm(Long orderSn) {
        if (Objects.isNull(orderSn)) {
            throw new CustomException(StatusEnum.PARAM_ERROR);
        }
        Order sellerOrder = orderMapper.selectOrderByOrderNumber(orderSn);
        Byte orderStatus = sellerOrder.getOrderStatus();
        if (!OrderStatEnum.UN_CONFIRM.isEqual(orderStatus)) {
            throw new CustomException(StatusEnum.ORDER_STATUS_ERROR);
        }
        Long stockNumber = sellerOrder.getStockNumber();
        Order buyerOrder = orderMapper.selectOwnerOrderByStockNumberAndStatus(stockNumber, OrderStatEnum.WAIT_SELLER_CONFIRM.getCode());
        if (Objects.isNull(buyerOrder)) {
            throw new CustomException(StatusEnum.ORDER_STATUS_ERROR);
        }
        if (OrderStatEnum.WAIT_SELLER_CONFIRM.isEqual(buyerOrder.getOrderStatus())) {
            throw new CustomException(StatusEnum.ORDER_STATUS_ERROR);
        }
        sellerOrder.setOrderStatus(OrderStatEnum.WAIT_BUYER_PAYMENT.getCode());
        buyerOrder.setOrderStatus(OrderStatEnum.SELLER_REJECT.getCode());
        orderMapper.updateByPrimaryKeySelective(sellerOrder);
        orderMapper.updateByPrimaryKeySelective(buyerOrder);
        return null;
    }


    @Override
    public List<Order> listUserOrder(OrderQuery query) {
        if (Objects.isNull(query)) {
            throw new CustomException(StatusEnum.PARAM_ERROR);
        }
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        if(Objects.isNull(user)){
            throw new CustomException(StatusEnum.USER_TOKEN_ERROR);
        }
        query.setUserId(user.getUserId());
        Byte orderDirection = query.getOrderDirection();
        if (Objects.nonNull(orderDirection) && Objects.isNull(query.getOrderStat())) {
            if (OrderDirectionEnum.check(orderDirection)) {
                List<Byte> directStatList = OrderStatEnum.getDirectStatList(orderDirection);
                query.setOrderStatList(directStatList);
            }
        }
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        return orderMapper.listOrderByCondition(query);
    }

    @Override
    public Boolean existSkillOrder(Long orderSn) {
        return redisTemplate.opsForSet().isMember(RedisConstant.AUCTION_ORDER_CACHE_KEY, orderSn);
    }


    @Override
    public void generatorOrder(BaseOrderDto orderInfo) {
        log.info("开始生成用户订单:{}", orderInfo);
        Long sn = orderInfo.getSn();
        Long auctionId = orderInfo.getAuctionId();
        Auction auction = auctionService.getAuctionById(auctionId);
        // 独占
        int i = orderMapper.countExclusiveAuctionUserNum(sn, OrderStatEnum.UN_PAYMENT.getCode());
        if (i > 0) {
            log.error("生成用户订单失败,userId:{},stockNumber:{}", orderInfo.getUserId(), orderInfo.getSn());
        }
        Order ownerOrder = orderMapper.selectOwnerOrderByStockNumberAndStatus(sn, OrderStatEnum.ON_AUCTION.getCode());
        Stock stock = stockMapper.selectOneBySn(sn);
        checkOrderStatus(ownerOrder, orderInfo, auction);
        Order order = buildOrder(orderInfo, auction, ownerOrder, stock);
        ownerOrder.setOrderStatus(OrderStatEnum.WAIT_BUYER_PAYMENT.getCode());
        orderMapper.insertSelective(order);
        orderMapper.updateByPrimaryKeySelective(ownerOrder);
        log.info("成功生成用户订单:库存号:{},订单号{}", sn, order.getOrderSn());
        OrderNotifyDto orderNotifyDto = new OrderNotifyDto(order.getOrderSn(), OrderNotifyEnum.PAYMENT_TIME_OUT.getCode());
        orderMqService.sendOrderDelayMsg(orderNotifyDto, 20, TimeUnit.SECONDS);
    }


    /**
     * 检查秒杀订单状态
     *
     * @param ownerOrder 原拥有这订单信息
     * @param orderInfo  秒杀订单信息
     * @param auction    拍品信息
     */
    private void checkOrderStatus(Order ownerOrder, BaseOrderDto orderInfo, Auction auction) {
        if (Objects.isNull(ownerOrder)) {
            throw new CustomException(StatusEnum.OWNER_ORDER_MISS_ERROR);
        }
        if (Objects.isNull(auction)) {
            throw new CustomException(StatusEnum.AUCTION_MISS_ERROR);
        }
        Long ownerSn = ownerOrder.getStockNumber();
        Long sn = orderInfo.getSn();
        Long ownerAuctionId = ownerOrder.getAuctionId();
        Long orderAuctionId = orderInfo.getAuctionId();
        if (!Objects.equals(ownerSn, sn)) {
            throw new CustomException(StatusEnum.STOCK_NOT_MATCH_ERROR);
        }
        if (!Objects.equals(ownerAuctionId, orderAuctionId)) {
            throw new CustomException(StatusEnum.AUCTION_NOT_MATCH_ERROR);
        }
    }

    private Order buildOrder(BaseOrderDto orderInfo, Auction auction, Order ownerOrder, Stock stock) {
        Order order = new Order();
        order.setGoodsId(auction.getGoodsId());
        order.setAuctionId(orderInfo.getAuctionId());
        order.setOrderSn(orderInfo.getOrderSn());
        order.setStockId(stock.getId());
        order.setOrderType(ownerOrder.getOrderType());
        order.setStockNumber(stock.getStockNumber());
        order.setUserId(orderInfo.getUserId());
        order.setTotalAmount(auction.getCashPrice());
        order.setPayAmount(auction.getCashPrice());
        order.setItemId(ownerOrder.getId());
        order.setCreateTime(LocalDateTime.now());
        order.setOrderStatus(OrderStatEnum.UN_PAYMENT.getCode());
        return order;
    }

    @Override
    public Boolean pickUpOrder(PickUpDto pickUpDto) {
        if (Objects.isNull(pickUpDto) || Objects.isNull(pickUpDto.getOrderId()) || Objects.isNull(pickUpDto.getAddressId())) {
            throw new CustomException(StatusEnum.PARAM_ERROR);
        }
//        User user = SecurityUtils.getPrincipal();
//        if(Objects.isNull(user)){
//            throw new CustomException(StatusEnum.USER_TOKEN_ERROR);
//        }
        Long userId =  2L;
        Long orderId = pickUpDto.getOrderId();
        Long addressId = pickUpDto.getAddressId();
        Order order = orderMapper.selectByPrimaryKey(orderId);
        Byte orderStatus = order.getOrderStatus();
        if(!canPickUp(orderStatus)){
            throw new CustomException(StatusEnum.ORDER_STATUS_ERROR);
        }

        if(!Objects.equals(order.getUserId(), userId)){
            throw new CustomException(StatusEnum.ORDER_STATUS_ERROR);
        }
        Address address = addressMapper.selectByPrimaryKey(addressId);
        if(Objects.isNull(address)){
            throw new CustomException(StatusEnum.ADDRESS_MISS_ERROR);
        }
        order.setOrderStatus(OrderStatEnum.PICKUP.getCode());
        orderMapper.updateByPrimaryKeySelective(order);
        ExpressOrder expressOrder =  buildExpressOrder(order,address);
        expressOrderMapper.insertSelective(expressOrder);
        return true;
    }

    private ExpressOrder buildExpressOrder(Order order,Address address) {
        ExpressOrder expressOrder = new ExpressOrder();
        expressOrder.setOrderSn(order.getOrderSn());
        expressOrder.setOrderId(order.getId());
        expressOrder.setUserId(order.getUserId());
        expressOrder.setAddrId(address.getAddrId());
        expressOrder.setExpressStatus(ExpressStatEnum.WAIT_DELIVER.getCode());
        String addressStr = new StringJoiner("\n")
                .add(address.getAddr1())
                .add(address.getAddr2())
                .add(address.getAddr3())
                .toString();
        expressOrder.setAddr(addressStr);
        // todo
//        expressOrder.setReceiveUserName("");
//        expressOrder.setUserId();
        return expressOrder;
    }

    private boolean canPickUp(Byte statCode){
       return OrderStatEnum.AUCTION_SUCCESS.isEqual(statCode)||OrderStatEnum.WAIT_MARGIN.isEqual(statCode);
    }
}
