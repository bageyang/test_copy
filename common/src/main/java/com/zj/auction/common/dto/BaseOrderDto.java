package com.zj.auction.common.dto;

import java.time.LocalDateTime;

/**
 * 基础订单信息
 */
public class BaseOrderDto {
    private LocalDateTime createTime;
    private Long orderSn;
    private Long auctionId;
    private Long userId;
    /**
     * stockNumber
     */
    private Long sn;

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(Long orderSn) {
        this.orderSn = orderSn;
    }

    public Long getSn() {
        return sn;
    }

    public void setSn(Long sn) {
        this.sn = sn;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    @Override
    public String toString() {
        return "BaseOrderDto{" +
                "createTime=" + createTime +
                ", orderSn=" + orderSn +
                ", auctionId=" + auctionId +
                ", userId=" + userId +
                ", sn=" + sn +
                '}';
    }
}
