package com.zj.auction.common.dto;

import java.time.LocalDateTime;

public class OrderNotifyDto {
    private Long orderSn;
    private Byte notifyType;
    private LocalDateTime createTime;
    private Long delayTime;

    public OrderNotifyDto(Long orderSn, Byte notifyType) {
        this.orderSn = orderSn;
        this.notifyType = notifyType;
    }

    public OrderNotifyDto() {
    }

    public Long getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(Long orderSn) {
        this.orderSn = orderSn;
    }

    public Byte getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(Byte notifyType) {
        this.notifyType = notifyType;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(Long delayTime) {
        this.delayTime = delayTime;
    }
}
