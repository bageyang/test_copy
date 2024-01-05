package com.zj.auction.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;

public class AuctionListVo {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long auctionId;
    private Long goodsId;
    private BigDecimal cashPrice;
    private String imageUrl;
    private Byte auctionStatus;

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Byte getAuctionStatus() {
        return auctionStatus;
    }

    public void setAuctionStatus(Byte auctionStatus) {
        this.auctionStatus = auctionStatus;
    }
}
