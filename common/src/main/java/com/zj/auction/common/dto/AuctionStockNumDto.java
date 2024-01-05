package com.zj.auction.common.dto;

public class AuctionStockNumDto {
    private Long goodsId;
    private Integer num;
    private Long ownerId;
    /**
     * 拍品生成的是现金区还是积分区的
     */
    private Byte auctionType;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Byte getAuctionType() {
        return auctionType;
    }

    public void setAuctionType(Byte auctionType) {
        this.auctionType = auctionType;
    }
}
