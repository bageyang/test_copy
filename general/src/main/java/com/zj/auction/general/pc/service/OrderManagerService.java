package com.zj.auction.general.pc.service;

import com.zj.auction.common.model.Order;

import java.util.List;

public interface OrderManagerService {
    // todo 订单查询
    // todo 订单列表
    // todo 订单详情
    // todo 查询用户历史订单
    // todo 查询库存订单流转列表
    List<Order> listOrder(Order order);

    Order getOrderInfo();

}
