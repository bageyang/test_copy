package com.zj.auction.general.app.service.impl;

import com.github.pagehelper.PageHelper;
import com.zj.auction.common.constant.RedisConstant;
import com.zj.auction.common.dto.BaseOrderDto;
import com.zj.auction.common.dto.OrderNotifyDto;
import com.zj.auction.common.dto.PaymentVoucher;
import com.zj.auction.common.dto.PickUpDto;
import com.zj.auction.common.enums.OrderDirectionEnum;
import com.zj.auction.common.enums.OrderNotifyEnum;
import com.zj.auction.common.enums.OrderStatEnum;
import com.zj.auction.common.enums.StatusEnum;
import com.zj.auction.common.exception.CustomException;
import com.zj.auction.common.mapper.GoodsMapper;
import com.zj.auction.common.mapper.OrderMapper;
import com.zj.auction.common.mapper.StockMapper;
import com.zj.auction.common.model.Auction;
import com.zj.auction.common.model.Goods;
import com.zj.auction.common.model.Order;
import com.zj.auction.common.model.Stock;
import com.zj.auction.common.query.OrderQuery;
import com.zj.auction.common.util.StringUtils;
import com.zj.auction.general.app.service.AuctionService;
import com.zj.auction.general.app.service.OrderMqService;
import com.zj.auction.general.app.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final StockMapper stockMapper;
    private final GoodsMapper goodsMapper;
    private final AuctionService auctionService;
    private final OrderMqService orderMqService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper, StockMapper stockMapper, GoodsMapper goodsMapper, AuctionService auctionService,  OrderMqService orderMqService) {
        this.orderMapper = orderMapper;
        this.stockMapper = stockMapper;
        this.goodsMapper = goodsMapper;
        this.auctionService = auctionService;
        this.orderMqService = orderMqService;
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
        // todo 发送延迟队列 卖家完成支付,卖家
    }

    @Override
    public boolean uploadPaymentVoucher(PaymentVoucher paymentVoucher) {
        String orderVoucher = paymentVoucher.getOrderVoucher();
        Long orderSn = paymentVoucher.getOrderSn();
        Order buyerOrder = orderMapper.selectOrderByOrderNumber(orderSn);
        if (!buyerOrderUnPaid(buyerOrder)) {
            throw new CustomException(StatusEnum.ORDER_STATUS_ERROR);
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
        // todo 获取用户id
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
    public void transferPaymentCallBack(Long stockSn) {
        Order order = orderMapper.selectOwnerOrderByStockNumberAndStatus(stockSn, OrderStatEnum.WAIT_MARGIN.getCode());
        Stock stock = stockMapper.selectOneBySn(stockSn);

        Order updateOrder = new Order();
        updateOrder.setId(order.getId());
        byte statCode = OrderStatEnum.ON_AUCTION.getCode();
        updateOrder.setOrderStatus(statCode);
        stock.setStockStatus(statCode);
        int transferNum = stock.getTransferNum() + 1;
        stock.setTransferNum(transferNum);
        // todo 设置价格
        stock.setPrice(stock.getPrice());

        orderMapper.updateByPrimaryKeySelective(updateOrder);
        stockMapper.updateByPrimaryKeySelective(stock);
        auctionService.addAuction(stock);
        // todo 计算业绩

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
        if (Objects.isNull(orderSn)) {
            throw new CustomException(StatusEnum.PARAM_ERROR);
        }
        Order sellerOrder = orderMapper.selectOrderByOrderNumber(orderSn);
        Byte orderStatus = sellerOrder.getOrderStatus();
        if (!OrderStatEnum.UN_CONFIRM.isEqual(orderStatus)) {
            throw new CustomException(StatusEnum.ORDER_STATUS_ERROR);
        }
        // todo 订单用户检查
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
        order.setStockNumber(stock.getStockNumber());
        order.setUserId(orderInfo.getUserId());
        order.setTotalAmount(auction.getPrice());
        order.setPayAmount(auction.getPrice());
        order.setItemId(ownerOrder.getId());
        order.setCreateTime(LocalDateTime.now());
        order.setOrderStatus(OrderStatEnum.UN_PAYMENT.getCode());
        return order;
    }

    @Override
    public Boolean pickUpOrder(PickUpDto pickUpDto) {
        // todo 提货限制

        return null;
    }
}
