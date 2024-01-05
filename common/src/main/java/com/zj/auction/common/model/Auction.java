package com.zj.auction.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Auction implements Serializable {
    private Long id;

    private String auctionName;

    private Long goodsId;

    private BigDecimal cashPrice;

    private BigDecimal integralPrice;

    private Integer stockQuantity;

    private Byte auctionStatus;

    private Integer auctionAreaId;

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

    public String getAuctionName() {
        return auctionName;
    }

    public void setAuctionName(String auctionName) {
        this.auctionName = auctionName;
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

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Byte getAuctionStatus() {
        return auctionStatus;
    }

    public void setAuctionStatus(Byte auctionStatus) {
        this.auctionStatus = auctionStatus;
    }

    public Integer getAuctionAreaId() {
        return auctionAreaId;
    }

    public void setAuctionAreaId(Integer auctionAreaId) {
        this.auctionAreaId = auctionAreaId;
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
        final StringBuilder sb = new StringBuilder("Auction{");
        sb.append("id=").append(id);
        sb.append(", auctionName='").append(auctionName).append('\'');
        sb.append(", goodsId=").append(goodsId);
        sb.append(", cashPrice=").append(cashPrice);
        sb.append(", integralPrice=").append(integralPrice);
        sb.append(", stockQuantity=").append(stockQuantity);
        sb.append(", auctionStatus=").append(auctionStatus);
        sb.append(", auctionAreaId=").append(auctionAreaId);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append('}');
        return sb.toString();
    }
}