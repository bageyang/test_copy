package com.zj.auction.common.dto;

public class PaymentVoucher {
    private Long  orderSn;
    private String orderVoucher;

    public Long getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(Long orderSn) {
        this.orderSn = orderSn;
    }

    public String getOrderVoucher() {
        return orderVoucher;
    }

    public void setOrderVoucher(String orderVoucher) {
        this.orderVoucher = orderVoucher;
    }
}
