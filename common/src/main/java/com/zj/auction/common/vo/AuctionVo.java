package com.zj.auction.common.vo;

import com.zj.auction.common.model.Goods;

import java.math.BigDecimal;

public class AuctionVo {
    private String auctionName;

    private String goodsName;

    private Long goodsId;

    private BigDecimal prices;

    private Integer stockQuantity;

    private Byte auctionStatus;

    private Integer auctionAreaId;

    private Goods goodsInfo;

    public String getAuctionName() {
        return auctionName;
    }

    public void setAuctionName(String auctionName) {
        this.auctionName = auctionName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getPrices() {
        return prices;
    }

    public void setPrices(BigDecimal prices) {
        this.prices = prices;
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

    public Goods getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(Goods goodsInfo) {
        this.goodsInfo = goodsInfo;
    }
}
