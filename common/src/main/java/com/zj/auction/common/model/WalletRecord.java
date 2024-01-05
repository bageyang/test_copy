package com.zj.auction.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WalletRecord implements Serializable {
    private Long id;

    private Long userId;

    private Byte walletType;

    private Byte transactionType;

    private BigDecimal balanceBefore;

    private BigDecimal changeBalance;

    private BigDecimal balanceAfter;

    private String remark;

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

    public Byte getWalletType() {
        return walletType;
    }

    public void setWalletType(Byte walletType) {
        this.walletType = walletType;
    }

    public Byte getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Byte transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(BigDecimal balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public BigDecimal getChangeBalance() {
        return changeBalance;
    }

    public void setChangeBalance(BigDecimal changeBalance) {
        this.changeBalance = changeBalance;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        sb.append(", walletType=").append(walletType);
        sb.append(", transactionType=").append(transactionType);
        sb.append(", balanceBefore=").append(balanceBefore);
        sb.append(", changeBalance=").append(changeBalance);
        sb.append(", balanceAfter=").append(balanceAfter);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}