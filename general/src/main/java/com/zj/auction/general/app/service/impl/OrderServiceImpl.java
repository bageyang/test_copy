package com.zj.auction.general.app.service.impl;

import com.zj.auction.common.enums.OrderStatEnum;
import com.zj.auction.common.enums.StatusEnum;
import com.zj.auction.common.exception.CustomException;
import com.zj.auction.common.mapper.GoodsMapper;
import com.zj.auction.common.mapper.OrderMapper;
import com.zj.auction.common.mapper.StockMapper;
import com.zj.auction.common.model.Goods;
import com.zj.auction.common.model.Order;
import com.zj.auction.common.model.Stock;
import com.zj.auction.general.app.service.AuctionService;
import com.zj.auction.general.app.service.OrderService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final StockMapper stockMapper;
    private final GoodsMapper goodsMapper;
    private final AuctionService auctionService;
    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper, StockMapper stockMapper, GoodsMapper goodsMapper, AuctionService auctionService) {
        this.orderMapper = orderMapper;
        this.stockMapper = stockMapper;
        this.goodsMapper = goodsMapper;
        this.auctionService = auctionService;
    }

    @Override
    public void finishPayTransferFee(String orderSn) {

    }

    @Override
    public void uploadPaymentVoucher(String orderSn) {

    }

    @Override
    public Object transfer2Auction(Long stockSn) {
        //
        Long userId = 0L;
        Order order = orderCheck(userId,stockSn);
        Stock stock = stockMapper.selectOneBySn(stockSn);
        if(Objects.isNull(stock)){
            throw new CustomException(StatusEnum.STOCK_NOT_MATCH_ERROR);
        }
        Goods goods = goodsMapper.selectByPrimaryKey(stock.getGoodsId());
        if(!checkTransferLimit(goods,stock)){
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
        return doPayTransferFee(order);
    }

    /**
     * 转拍限制检查
     * @param goods 商品
     * @param stock 库存
     * @return true/false
     */
    private boolean checkTransferLimit(Goods goods, Stock stock) {
        // todo 转拍金额限制取消 ?
        Integer transferNum = stock.getTransferNum();
        Integer handNum = goods.getHandNum();
        return transferNum < handNum;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transferPaymentCallBack(Long stockSn) {
        Order order = orderMapper.selectOwnerOrderBySnAndStatus(stockSn,OrderStatEnum.WAIT_MARGIN.getCode());
        Stock stock = stockMapper.selectOneBySn(stockSn);

        Order updateOrder = new Order();
        updateOrder.setId(order.getId());
        byte statCode = OrderStatEnum.ON_AUCTION.getCode();
        updateOrder.setOrderStatus(statCode);
        stock.setStockStatus(statCode);
        int transferNum = stock.getTransferNum()+ 1;
        stock.setTransferNum(transferNum);
        // todo 设置价格
        stock.setPrice(stock.getPrice());

        orderMapper.updateByPrimaryKeySelective(updateOrder);
        stockMapper.updateByPrimaryKeySelective(stock);
        auctionService.addAuction(stock);
    }

    /**
     * 调取支付转拍手续费接口
     * @param order 订单信息
     * @return unknown
     */
    public Object doPayTransferFee(Order order) {
        return null;
    }

    private Order orderCheck(Long userId, Long stockSn) {
        if(Objects.isNull(stockSn)){
            throw new CustomException(StatusEnum.PARAM_ERROR);
        }
        int orderStatCode = OrderStatEnum.AUCTION_SUCCESS.getCode();
        Order order = orderMapper.selectOwnerOrderBySnAndStatus(stockSn,orderStatCode);
        if(Objects.isNull(order)){
            throw new CustomException(StatusEnum.OWNER_ORDER_MISS_ERROR);
        }
        Long ownerId = order.getUserId();
        if(!Objects.equals(userId,ownerId)){
            throw new CustomException(StatusEnum.AUCTION_STATUS_ERROR);
        }
        return order;
    }

    @Override
    public void finishOrder(String orderSn) {

    }

    @Override
    public List<Order> listUserOrder(Long userId) {
        return null;
    }
}
