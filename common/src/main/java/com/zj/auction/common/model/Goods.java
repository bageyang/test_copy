package com.zj.auction.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Goods implements Serializable {
    private Long id;

    private Integer postId;

    private Long userId;

    private BigDecimal oldPrice;

    private BigDecimal price;

    private BigDecimal discountPrices;

    private Integer acquireIntegral;

    private String rebateTransactionId;

    private Integer hotFlag;

    private Integer discountFlag;

    private Integer newFlag;

    private String goodsName;

    private String imgUrl;

    private String videoUrl;

    private Integer typeId;

    private Integer status;

    private String transactionId;

    private String remarks;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer addUserId;

    private Double weight;

    private Double volume;

    private String rebateRatio;

    private String serviceTel;

    private Integer sort;

    private Long updateUserId;

    private Integer sellTotal;

    private Byte recommend;

    private String frozenExplain;

    private String shipAddress;

    private BigDecimal bucklePointRatio;

    private String specification;

    private Long tagId;

    private Long auctionId;

    private Integer auctionStatus;

    private Integer handNum;

    private BigDecimal deliveryMoney;

    private Byte subType;

    private BigDecimal cashPrice;

    private Byte isDeleted;

    private String content;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(BigDecimal oldPrice) {
        this.oldPrice = oldPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscountPrices() {
        return discountPrices;
    }

    public void setDiscountPrices(BigDecimal discountPrices) {
        this.discountPrices = discountPrices;
    }

    public Integer getAcquireIntegral() {
        return acquireIntegral;
    }

    public void setAcquireIntegral(Integer acquireIntegral) {
        this.acquireIntegral = acquireIntegral;
    }

    public String getRebateTransactionId() {
        return rebateTransactionId;
    }

    public void setRebateTransactionId(String rebateTransactionId) {
        this.rebateTransactionId = rebateTransactionId;
    }

    public Integer getHotFlag() {
        return hotFlag;
    }

    public void setHotFlag(Integer hotFlag) {
        this.hotFlag = hotFlag;
    }

    public Integer getDiscountFlag() {
        return discountFlag;
    }

    public void setDiscountFlag(Integer discountFlag) {
        this.discountFlag = discountFlag;
    }

    public Integer getNewFlag() {
        return newFlag;
    }

    public void setNewFlag(Integer newFlag) {
        this.newFlag = newFlag;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public Integer getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Integer addUserId) {
        this.addUserId = addUserId;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getRebateRatio() {
        return rebateRatio;
    }

    public void setRebateRatio(String rebateRatio) {
        this.rebateRatio = rebateRatio;
    }

    public String getServiceTel() {
        return serviceTel;
    }

    public void setServiceTel(String serviceTel) {
        this.serviceTel = serviceTel;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Integer getSellTotal() {
        return sellTotal;
    }

    public void setSellTotal(Integer sellTotal) {
        this.sellTotal = sellTotal;
    }

    public Byte getRecommend() {
        return recommend;
    }

    public void setRecommend(Byte recommend) {
        this.recommend = recommend;
    }

    public String getFrozenExplain() {
        return frozenExplain;
    }

    public void setFrozenExplain(String frozenExplain) {
        this.frozenExplain = frozenExplain;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public BigDecimal getBucklePointRatio() {
        return bucklePointRatio;
    }

    public void setBucklePointRatio(BigDecimal bucklePointRatio) {
        this.bucklePointRatio = bucklePointRatio;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    public Integer getAuctionStatus() {
        return auctionStatus;
    }

    public void setAuctionStatus(Integer auctionStatus) {
        this.auctionStatus = auctionStatus;
    }

    public Integer getHandNum() {
        return handNum;
    }

    public void setHandNum(Integer handNum) {
        this.handNum = handNum;
    }

    public BigDecimal getDeliveryMoney() {
        return deliveryMoney;
    }

    public void setDeliveryMoney(BigDecimal deliveryMoney) {
        this.deliveryMoney = deliveryMoney;
    }

    public Byte getSubType() {
        return subType;
    }

    public void setSubType(Byte subType) {
        this.subType = subType;
    }

    public BigDecimal getCashPrice() {
        return cashPrice;
    }

    public void setCashPrice(BigDecimal cashPrice) {
        this.cashPrice = cashPrice;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", postId=").append(postId);
        sb.append(", userId=").append(userId);
        sb.append(", oldPrice=").append(oldPrice);
        sb.append(", price=").append(price);
        sb.append(", discountPrices=").append(discountPrices);
        sb.append(", acquireIntegral=").append(acquireIntegral);
        sb.append(", rebateTransactionId=").append(rebateTransactionId);
        sb.append(", hotFlag=").append(hotFlag);
        sb.append(", discountFlag=").append(discountFlag);
        sb.append(", newFlag=").append(newFlag);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", imgUrl=").append(imgUrl);
        sb.append(", videoUrl=").append(videoUrl);
        sb.append(", typeId=").append(typeId);
        sb.append(", status=").append(status);
        sb.append(", transactionId=").append(transactionId);
        sb.append(", remarks=").append(remarks);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", addUserId=").append(addUserId);
        sb.append(", weight=").append(weight);
        sb.append(", volume=").append(volume);
        sb.append(", rebateRatio=").append(rebateRatio);
        sb.append(", serviceTel=").append(serviceTel);
        sb.append(", sort=").append(sort);
        sb.append(", updateUserId=").append(updateUserId);
        sb.append(", sellTotal=").append(sellTotal);
        sb.append(", recommend=").append(recommend);
        sb.append(", frozenExplain=").append(frozenExplain);
        sb.append(", shipAddress=").append(shipAddress);
        sb.append(", bucklePointRatio=").append(bucklePointRatio);
        sb.append(", specification=").append(specification);
        sb.append(", tagId=").append(tagId);
        sb.append(", auctionId=").append(auctionId);
        sb.append(", auctionStatus=").append(auctionStatus);
        sb.append(", handNum=").append(handNum);
        sb.append(", deliveryMoney=").append(deliveryMoney);
        sb.append(", subType=").append(subType);
        sb.append(", cashPrice=").append(cashPrice);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", content=").append(content);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}