package com.zj.auction.common.model;

import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Builder
public class Wallet implements Serializable {
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 基金类型
     */
    private Integer fundType;

    /**
     * 资金
     */
    private BigDecimal balance;

    /**
     * 冻结
     */
    private BigDecimal freeze;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 交易类型
     */
    private Integer transactionType;

    /**
     * 更改之前资金
     */
    private BigDecimal balanceBefore;

    /**
     * 更新资金
     */
    private BigDecimal updateBalance;


    /**
     * 备注
     */
    private String remark;

    /**
     * 钱包类型
     */
    private Integer walletType;

    /**
     * 贸易号
     */
    private String tradeNo;

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

    public Integer getFundType() {
        return fundType;
    }

    public void setFundType(Integer fundType) {
        this.fundType = fundType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getFreeze() {
        return freeze;
    }

    public void setFreeze(BigDecimal freeze) {
        this.freeze = freeze;
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

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(BigDecimal balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public BigDecimal getUpdateBalance() {
        return updateBalance;
    }

    public void setUpdateBalance(BigDecimal updateBalance) {
        this.updateBalance = updateBalance;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getWalletType() {
        return walletType;
    }

    public void setWalletType(Integer walletType) {
        this.walletType = walletType;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", fundType=").append(fundType);
        sb.append(", balance=").append(balance);
        sb.append(", freeze=").append(freeze);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", transactionType=").append(transactionType);
        sb.append(", balanceBefore=").append(balanceBefore);
        sb.append(", updateBalance=").append(updateBalance);
        sb.append(", remark=").append(remark);
        sb.append(", walletType=").append(walletType);
        sb.append(", tradeNo=").append(tradeNo);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}