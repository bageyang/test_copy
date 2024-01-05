package com.zj.auction.common.model;

import java.io.Serializable;

public class Permis implements Serializable {
    private Long permisId;

    private String permisName;

    private String permis;

    private Integer levelNum;

    private Integer status;

    private Long pid;

    private String resUrl;

    private Integer typeId;

    private Integer deleteFlag;

    private Integer orderIndex;

    private String imgPath;

    private String memo;

    private static final long serialVersionUID = 1L;

    public Long getPermisId() {
        return permisId;
    }

    public void setPermisId(Long permisId) {
        this.permisId = permisId;
    }

    public String getPermisName() {
        return permisName;
    }

    public void setPermisName(String permisName) {
        this.permisName = permisName;
    }

    public String getPermis() {
        return permis;
    }

    public void setPermis(String permis) {
        this.permis = permis;
    }

    public Integer getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(Integer levelNum) {
        this.levelNum = levelNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", permisId=").append(permisId);
        sb.append(", permisName=").append(permisName);
        sb.append(", permis=").append(permis);
        sb.append(", levelNum=").append(levelNum);
        sb.append(", status=").append(status);
        sb.append(", pid=").append(pid);
        sb.append(", resUrl=").append(resUrl);
        sb.append(", typeId=").append(typeId);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", orderIndex=").append(orderIndex);
        sb.append(", imgPath=").append(imgPath);
        sb.append(", memo=").append(memo);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}