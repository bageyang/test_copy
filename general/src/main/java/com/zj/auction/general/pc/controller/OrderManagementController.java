package com.zj.auction.general.pc.controller;

import com.zj.auction.common.dto.PageVo;
import com.zj.auction.common.dto.Ret;
import com.zj.auction.common.model.Order;
import com.zj.auction.common.query.OrderQuery;
import com.zj.auction.common.vo.OrderTransactionVo;
import com.zj.auction.general.pc.service.OrderManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/order")
public class OrderManagementController {
    private OrderManagerService orderManagerService;

    @Autowired
    public OrderManagementController(OrderManagerService orderManagerService) {
        this.orderManagerService = orderManagerService;
    }

    @PostMapping("/list")
    public Ret<PageVo<Order>> listOrder(OrderQuery query){
        return Ret.ok(orderManagerService.listOrder(query));
    }

    @PostMapping("/info")
    public Ret<Order> getOrderInfo(Long orderId){
        return Ret.ok(orderManagerService.getOrderInfo(orderId));
    }

    @PostMapping("/line")
    public Ret<List<Order>> listOrderTimeLine(Long stockNumber){
        return Ret.ok(orderManagerService.listOrderTimeLine(stockNumber));
    }

    @PostMapping("/forceConfirm")
    public Ret<Boolean> forceConfirm(Long orderId){
        return Ret.ok(orderManagerService.forceConfirm(orderId));
    }

    @PostMapping("/forcePayed")
    public Ret<Boolean> forcePayed(Long orderId){
        return Ret.ok(orderManagerService.forcePayed(orderId));
    }
}
