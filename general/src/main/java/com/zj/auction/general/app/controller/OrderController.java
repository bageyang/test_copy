package com.zj.auction.general.app.controller;

import com.zj.auction.common.model.Order;
import com.zj.auction.common.query.OrderQuery;
import com.zj.auction.general.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/list")
    public List<Order> listUserOrder(OrderQuery query){
        return orderService.listUserOrder(query);
    }






}
