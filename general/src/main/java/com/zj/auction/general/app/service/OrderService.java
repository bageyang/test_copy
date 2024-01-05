package com.zj.auction.general.app.service;

import com.zj.auction.common.dto.Ret;
import com.zj.auction.common.model.Order;
import com.zj.auction.common.query.OrderQuery;

import java.util.List;

public interface OrderService {
    // todo 支付订单回调
    // todo 查看订单详情

    /**
     * 用户支付拍品完成回调
     * @param orderSn
     */
    void finishPayCallBack(Long orderSn);

    /**
     * 上传支付凭证
     * @param orderSn 订单号
     */
    void uploadPaymentVoucher(Long orderSn,String orderVoucher);

    /**
     * 转拍上架拍品
     * @param stockSn 库存编号
     */
    Object transfer2Auction(Long stockSn);

    /**
     * 转拍支付回调
     * @param stockSn
     */
    void transferPaymentCallBack(Long stockSn);

    /**
     * 放货
     */
    void finishOrder(Long orderSn);

    /**
     * 查询我的订单
     * @param query
     * @return
     */
    List<Order> listUserOrder(OrderQuery query);


}
