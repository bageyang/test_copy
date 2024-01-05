package com.zj.auction.common.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PayLog implements Serializable {
    private Long id;

    private Long userId;

    private Long orderId;

    private String orderSn;

    private Byte payType;

    private String reqParam;

    private Byte reqStatus;

    private Byte resStatus;

    private String callBackParam;

    private String callBackStatus;

    private String errInfo;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    public String getReqParam() {
        return reqParam;
    }

    public void setReqParam(String reqParam) {
        this.reqParam = reqParam;
    }

    public Byte getReqStatus() {
        return reqStatus;
    }

    public void setReqStatus(Byte reqStatus) {
        this.reqStatus = reqStatus;
    }

    public Byte getResStatus() {
        return resStatus;
    }

    public void setResStatus(Byte resStatus) {
        this.resStatus = resStatus;
    }

    public String getCallBackParam() {
        return callBackParam;
    }

    public void setCallBackParam(String callBackParam) {
        this.callBackParam = callBackParam;
    }

    public String getCallBackStatus() {
        return callBackStatus;
    }

    public void setCallBackStatus(String callBackStatus) {
        this.callBackStatus = callBackStatus;
    }

    public String getErrInfo() {
        return errInfo;
    }

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", orderId=").append(orderId);
        sb.append(", orderSn=").append(orderSn);
        sb.append(", payType=").append(payType);
        sb.append(", reqParam=").append(reqParam);
        sb.append(", reqStatus=").append(reqStatus);
        sb.append(", resStatus=").append(resStatus);
        sb.append(", callBackParam=").append(callBackParam);
        sb.append(", callBackStatus=").append(callBackStatus);
        sb.append(", errInfo=").append(errInfo);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}