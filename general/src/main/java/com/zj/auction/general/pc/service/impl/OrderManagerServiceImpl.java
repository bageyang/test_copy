package com.zj.auction.general.pc.service.impl;

import com.zj.auction.common.model.Order;
import com.zj.auction.general.pc.service.OrderManagerService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderManagerServiceImpl implements OrderManagerService {
    @Override
    public List<Order> listOrder(Order order) {
        return null;
    }

    @Override
    public Order getOrderInfo() {
        return null;
    }
}
