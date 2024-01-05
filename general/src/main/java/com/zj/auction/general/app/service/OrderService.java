package com.zj.auction.general.app.service;

import com.zj.auction.common.dto.BaseOrderDto;
import com.zj.auction.common.dto.PaymentVoucher;
import com.zj.auction.common.dto.PickUpDto;
import com.zj.auction.common.dto.Ret;
import com.zj.auction.common.model.Order;
import com.zj.auction.common.query.OrderQuery;

import java.util.List;

public interface OrderService {

    /**
     * 用户支付拍品完成回调
     * @param orderSn
     */
    void finishPayCallBack(Long orderSn);

    /**
     * 上传支付凭证
     * @param paymentVoucher 订单号
     */
    boolean uploadPaymentVoucher(PaymentVoucher paymentVoucher);

    /**
     * 转拍上架拍品
     * @param orderSn 订单号
     */
    Boolean transfer2Auction(Long orderSn);

    /**
     * 转拍支付回调
     * @param stockSn
     */
    void transferPaymentCallBack(Long stockSn);

    /**
     * 放货
     */
    boolean finishOrder(Long orderSn);

    /**
     * 查询我的订单
     * @param query
     * @return
     */
    List<Order> listUserOrder(OrderQuery query);


    Boolean existSkillOrder(Long orderSn);

    Boolean rejectConfirm(Long orderSn);

    Boolean pickUpOrder(PickUpDto pickUpDto);


    void generatorOrder(BaseOrderDto orderInfo);
}
