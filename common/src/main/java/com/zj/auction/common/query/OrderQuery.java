package com.zj.auction.common.query;

import java.util.List;

public class OrderQuery extends PageQuery{
    private Long userId;
    private Long orderId;
    private Long orderType;
    /**
     * 订单方向(1:买,2卖)
     */
    private Byte orderDirection;
    /**
     * 订单状态 OrderStatEnum
     */
    private Byte orderStat;
    private List<Byte> orderStatList;
    private Long stockNumber;
    private Long orderSn;
    private Long goodsId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Long getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(Long stockNumber) {
        this.stockNumber = stockNumber;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getOrderType() {
        return orderType;
    }

    public void setOrderType(Long orderType) {
        this.orderType = orderType;
    }

    public Byte getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(Byte orderDirection) {
        this.orderDirection = orderDirection;
    }

    public Byte getOrderStat() {
        return orderStat;
    }

    public void setOrderStat(Byte orderStat) {
        this.orderStat = orderStat;
    }

    public List<Byte> getOrderStatList() {
        return orderStatList;
    }

    public void setOrderStatList(List<Byte> orderStatList) {
        this.orderStatList = orderStatList;
    }
}
