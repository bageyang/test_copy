package com.zj.auction.general.app.service.impl;

import com.zj.auction.common.enums.OrderStatEnum;
import com.zj.auction.common.enums.StatusEnum;
import com.zj.auction.common.exception.CustomException;
import com.zj.auction.common.mapper.OrderMapper;
import com.zj.auction.common.mapper.StockMapper;
import com.zj.auction.common.model.Order;
import com.zj.auction.common.model.Stock;
import com.zj.auction.general.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final StockMapper stockMapper;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper, StockMapper stockMapper) {
        this.orderMapper = orderMapper;
        this.stockMapper = stockMapper;
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
        order.setOrderStatus();
        orderMapper.updateByPrimaryKey()
        return doPayTransferFee(order);
    }

    @Override
    public void transferPaymentCallBack(Long stockSn) {
        Order order = orderMapper.selectOwnerOrderBySnAndStatus(stockSn,orderStatCode);
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
