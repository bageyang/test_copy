package com.zj.auction.common.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserConfig implements Serializable {
    private Integer userConfigId;

    private String addr;

    private Integer cardNumber;

    private String domainName;

    private Integer subUserNum;

    private String subUserNumAll;

    private String province;

    private String city;

    private String county;

    private Long provinceId;

    private Long cityId;

    private Long countyId;

    private String frontImage;

    private String reverseImage;

    private LocalDateTime applyTime;

    private String faceImg;

    private String enteringStatus;

    private static final long serialVersionUID = 1L;

    public Integer getUserConfigId() {
        return userConfigId;
    }

    public void setUserConfigId(Integer userConfigId) {
        this.userConfigId = userConfigId;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Integer getSubUserNum() {
        return subUserNum;
    }

    public void setSubUserNum(Integer subUserNum) {
        this.subUserNum = subUserNum;
    }

    public String getSubUserNumAll() {
        return subUserNumAll;
    }

    public void setSubUserNumAll(String subUserNumAll) {
        this.subUserNumAll = subUserNumAll;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getCountyId() {
        return countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }

    public String getFrontImage() {
        return frontImage;
    }

    public void setFrontImage(String frontImage) {
        this.frontImage = frontImage;
    }

    public String getReverseImage() {
        return reverseImage;
    }

    public void setReverseImage(String reverseImage) {
        this.reverseImage = reverseImage;
    }

    public LocalDateTime getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(LocalDateTime applyTime) {
        this.applyTime = applyTime;
    }

    public String getFaceImg() {
        return faceImg;
    }

    public void setFaceImg(String faceImg) {
        this.faceImg = faceImg;
    }

    public String getEnteringStatus() {
        return enteringStatus;
    }

    public void setEnteringStatus(String enteringStatus) {
        this.enteringStatus = enteringStatus;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userConfigId=").append(userConfigId);
        sb.append(", addr=").append(addr);
        sb.append(", cardNumber=").append(cardNumber);
        sb.append(", domainName=").append(domainName);
        sb.append(", subUserNum=").append(subUserNum);
        sb.append(", subUserNumAll=").append(subUserNumAll);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", county=").append(county);
        sb.append(", provinceId=").append(provinceId);
        sb.append(", cityId=").append(cityId);
        sb.append(", countyId=").append(countyId);
        sb.append(", frontImage=").append(frontImage);
        sb.append(", reverseImage=").append(reverseImage);
        sb.append(", applyTime=").append(applyTime);
        sb.append(", faceImg=").append(faceImg);
        sb.append(", enteringStatus=").append(enteringStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}