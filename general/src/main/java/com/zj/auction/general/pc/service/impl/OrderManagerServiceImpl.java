package com.zj.auction.general.pc.service.impl;

import com.github.pagehelper.PageHelper;
import com.zj.auction.common.dto.OrderNotifyDto;
import com.zj.auction.common.enums.OrderNotifyEnum;
import com.zj.auction.common.enums.OrderStatEnum;
import com.zj.auction.common.enums.StatusEnum;
import com.zj.auction.common.exception.CustomException;
import com.zj.auction.common.mapper.OrderMapper;
import com.zj.auction.common.model.Order;
import com.zj.auction.common.query.OrderQuery;
import com.zj.auction.general.app.service.OrderMqService;
import com.zj.auction.general.pc.service.OrderManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OrderManagerServiceImpl implements OrderManagerService {
    private final OrderMapper orderMapper;
    private final OrderMqService orderMqService;

    @Autowired
    public OrderManagerServiceImpl(OrderMapper orderMapper,  OrderMqService orderMqService) {
        this.orderMapper = orderMapper;
        this.orderMqService = orderMqService;
    }

    @Override
    public List<Order> listOrder(OrderQuery query) {
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        return orderMapper.listOrderByCondition(query);
    }

    @Override
    public Order getOrderInfo(Long orderId) {
        if(Objects.isNull(orderId)){
            throw new CustomException(StatusEnum.PARAM_ERROR);
        }
        return orderMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public List<Order> listOrderTimeLine(Long stockNumber) {
        if(Objects.isNull(stockNumber)){
            throw new CustomException(StatusEnum.PARAM_ERROR);
        }
        return orderMapper.listOrderByStockNumberOrderByCreatTime(stockNumber);
    }

    @Override
    public Boolean forceConfirm(Long orderId) {
        Order sellerOrder = orderMapper.selectByPrimaryKey(orderId);
        Byte orderStatus = sellerOrder.getOrderStatus();
        if (!OrderStatEnum.UN_CONFIRM.isEqual(orderStatus)) {
            throw new CustomException(StatusEnum.ORDER_STATUS_ERROR);
        }
        Long stockNumber = sellerOrder.getStockNumber();
        Order buyerOrder = orderMapper.selectOwnerOrderByStockNumberAndStatus(stockNumber, OrderStatEnum.WAIT_SELLER_CONFIRM.getCode());
        if(Objects.isNull(buyerOrder)){
            throw new CustomException(StatusEnum.ORDER_STATUS_ERROR);
        }
        sellerOrder.setOrderStatus(OrderStatEnum.FINISH.getCode());
        buyerOrder.setOrderStatus(OrderStatEnum.AUCTION_SUCCESS.getCode());
        orderMapper.updateByPrimaryKeySelective(sellerOrder);
        orderMapper.updateByPrimaryKeySelective(buyerOrder);
        return true;
    }

    @Override
    public Boolean forcePayed(Long orderId) {
        if(Objects.isNull(orderId)){
            throw new CustomException(StatusEnum.PARAM_ERROR);
        }
        Order buyerOrder = orderMapper.selectOrderByOrderNumber(orderId);
        if(!buyerOrderUnPaid(buyerOrder)){
            throw new CustomException(StatusEnum.ORDER_STATUS_ERROR);
        }
        Order sellerOrder = orderMapper.selectOwnerOrderByStockNumberAndStatus(buyerOrder.getStockNumber(), OrderStatEnum.WAIT_BUYER_PAYMENT.getCode());
        if(Objects.isNull(sellerOrder)){
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
        orderMqService.sendOrderDelayMsg(orderNotifyDto,20, TimeUnit.MINUTES);
        return null;
    }

    private boolean buyerOrderUnPaid(Order order) {
        return Objects.nonNull(order)
                && (OrderStatEnum.UN_PAYMENT.isEqual(order.getOrderStatus())
                || OrderStatEnum.SELLER_REJECT.isEqual(order.getOrderStatus())
        );
    }

}
