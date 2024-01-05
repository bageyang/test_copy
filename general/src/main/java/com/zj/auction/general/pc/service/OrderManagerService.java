package com.zj.auction.general.pc.service;

import com.zj.auction.common.model.Order;
import com.zj.auction.common.query.OrderQuery;

import java.util.List;

public interface OrderManagerService {

    List<Order> listOrder(OrderQuery order);

    Order getOrderInfo(Long orderId);

    List<Order> listOrderTimeLine(Long stockNumber);

    Boolean forceConfirm(Long orderId);

    Boolean forcePayed(Long orderId);
}
