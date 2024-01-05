package com.zj.auction.general.pc.service.impl;

import com.github.pagehelper.PageHelper;
import com.zj.auction.common.mapper.OrderMapper;
import com.zj.auction.common.model.Order;
import com.zj.auction.common.query.OrderQuery;
import com.zj.auction.general.pc.service.OrderManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class OrderManagerServiceImpl implements OrderManagerService {
    private final OrderMapper orderMapper;

    @Autowired
    public OrderManagerServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public List<Order> listOrder(OrderQuery query) {
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        return orderMapper.listOrderByCondition(query);
    }

    @Override
    public Order getOrderInfo(Long orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }
}
