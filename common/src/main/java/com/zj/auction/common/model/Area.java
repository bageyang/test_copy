package com.zj.auction.common.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Area implements Serializable {
    private Long areaId;

    private String areaName;

    private Long pid;

    private Integer levelNum;

    private String letter1;

    private String letter2;

    private Integer chooseNum;

    private Integer status;

    private String remakes;

    private LocalDateTime addTime;

    private LocalDateTime updateTime;

    private Integer deleteFlag;

    private Integer typeId;

    private String memo1;

    private Long addUserId;

    private String transactionId;

    private Long updateUserId;

    private String tel;

    private static final long serialVersionUID = 1L;

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(Integer levelNum) {
        this.levelNum = levelNum;
    }

    public String getLetter1() {
        return letter1;
    }

    public void setLetter1(String letter1) {
        this.letter1 = letter1;
    }

    public String getLetter2() {
        return letter2;
    }

    public void setLetter2(String letter2) {
        this.letter2 = letter2;
    }

    public Integer getChooseNum() {
        return chooseNum;
    }

    public void setChooseNum(Integer chooseNum) {
        this.chooseNum = chooseNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemakes() {
        return remakes;
    }

    public void setRemakes(String remakes) {
        this.remakes = remakes;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getMemo1() {
        return memo1;
    }

    public void setMemo1(String memo1) {
        this.memo1 = memo1;
    }

    public Long getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Long addUserId) {
        this.addUserId = addUserId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", areaId=").append(areaId);
        sb.append(", areaName=").append(areaName);
        sb.append(", pid=").append(pid);
        sb.append(", levelNum=").append(levelNum);
        sb.append(", letter1=").append(letter1);
        sb.append(", letter2=").append(letter2);
        sb.append(", chooseNum=").append(chooseNum);
        sb.append(", status=").append(status);
        sb.append(", remakes=").append(remakes);
        sb.append(", addTime=").append(addTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", typeId=").append(typeId);
        sb.append(", memo1=").append(memo1);
        sb.append(", addUserId=").append(addUserId);
        sb.append(", transactionId=").append(transactionId);
        sb.append(", updateUserId=").append(updateUserId);
        sb.append(", tel=").append(tel);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}