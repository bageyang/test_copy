package com.zj.auction.general.app.controller;

import com.zj.auction.common.dto.PaymentVoucher;
import com.zj.auction.common.dto.PickUpDto;
import com.zj.auction.common.dto.Ret;
import com.zj.auction.common.model.Order;
import com.zj.auction.common.query.OrderQuery;
import com.zj.auction.general.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/list")
    public Ret<List<Order>> listUserOrder(@RequestBody OrderQuery query){
        return Ret.ok(orderService.listUserOrder(query));
    }

    @GetMapping("/exist/{orderSn}")
    public Ret<Boolean> existOrder(@PathVariable("orderSn")Long orderSn){
        return Ret.ok(orderService.existSkillOrder(orderSn));
    }

    /**
     * 转拍
     * @param orderSn 订单号
     * @return true/false
     */
    @PostMapping("/transferAuction/{orderSn}")
    public Ret<Boolean> transferAuction(@PathVariable("orderSn")Long orderSn){
        return Ret.ok(orderService.transfer2Auction(orderSn));
    }

    /**
     * 上传支付凭证
     * @param paymentVoucher 支付凭证内容
     * @return true/false
     */
    @PostMapping("/uploadPaymentVoucher")
    public Ret<Object> uploadPaymentVoucher(@RequestBody PaymentVoucher paymentVoucher){
        return Ret.ok(orderService.uploadPaymentVoucher(paymentVoucher));
    }

    /**
     * 确认收款,放货
     * @param orderSn 订单号
     * @return true/false
     */
    @PostMapping("/confirmPayment/{orderSn}")
    public Ret<Boolean> confirmPayment(@PathVariable("orderSn") Long orderSn){
        return Ret.ok(orderService.finishOrder(orderSn));
    }

    /**
     * 拒绝放货
     * @param orderSn 订单号
     * @return true/false
     */
    @PostMapping("/rejectConfirmPayment/{orderSn}")
    public Ret<Boolean> rejectConfirmPayment(@PathVariable("orderSn") Long orderSn){
        return Ret.ok(orderService.rejectConfirm(orderSn));
    }

    /**
     * 提货
     * @return true/false
     */
    @PostMapping("/pickUp")
    public Ret<Boolean> pickUpOrder(@RequestBody PickUpDto pickUpDto){
        return Ret.ok(orderService.pickUpOrder(pickUpDto));
    }






}
