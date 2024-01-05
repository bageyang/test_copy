package com.zj.auction.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Stock implements Serializable {
    private Long id;

    private Long goodsId;

    private BigDecimal cashPrice;

    private BigDecimal integralPrice;

    private Long stockNumber;

    private Byte stockStatus;

    private Long ownerId;

    private Integer transferNum;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Byte isDeleted;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getCashPrice() {
        return cashPrice;
    }

    public void setCashPrice(BigDecimal cashPrice) {
        this.cashPrice = cashPrice;
    }

    public BigDecimal getIntegralPrice() {
        return integralPrice;
    }

    public void setIntegralPrice(BigDecimal integralPrice) {
        this.integralPrice = integralPrice;
    }

    public Long getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(Long stockNumber) {
        this.stockNumber = stockNumber;
    }

    public Byte getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(Byte stockStatus) {
        this.stockStatus = stockStatus;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getTransferNum() {
        return transferNum;
    }

    public void setTransferNum(Integer transferNum) {
        this.transferNum = transferNum;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Stock{");
        sb.append("id=").append(id);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", cashPrice=").append(cashPrice);
        sb.append(", integralPrice=").append(integralPrice);
        sb.append(", stockNumber=").append(stockNumber);
        sb.append(", stockStatus=").append(stockStatus);
        sb.append(", ownerId=").append(ownerId);
        sb.append(", transferNum=").append(transferNum);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append('}');
        return sb.toString();
    }
}