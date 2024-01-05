package com.zj.auction.common.vo;

public class OrderVo {
    private Long sn;
    private Long orderId;
    private Long orderSn;
    private Long userId;
    /**
     * 对方信息
     */
    private UserAccountInfo counterPartInfo;

    public Long getSn() {
        return sn;
    }

    public void setSn(Long sn) {
        this.sn = sn;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(Long orderSn) {
        this.orderSn = orderSn;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserAccountInfo getCounterPartInfo() {
        return counterPartInfo;
    }

    public void setCounterPartInfo(UserAccountInfo counterPartInfo) {
        this.counterPartInfo = counterPartInfo;
    }
}
