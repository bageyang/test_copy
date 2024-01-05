package com.zj.auction.general.app.service;

import com.zj.auction.common.model.Order;

import java.util.List;

public interface OrderService {
    // todo 支付订单回调
    // todo 查看订单详情

    /**
     * 支付转拍收费费回调
     * @param orderSn
     */
    void finishPayTransferFee(String orderSn);

    /**
     * 上传支付凭证
     * @param orderSn 订单号
     */
    void uploadPaymentVoucher(String orderSn);

    /**
     * 转拍上架拍品
     * @param orderSn 订单号
     */
    void transfer2Auction(String orderSn);

    /**
     * 放货
     */
    void finishOrder(String orderSn);

    /**
     * 查询我的订单
     * @param userId
     * @return
     */
    List<Order> listUserOrder(Long userId);


}
