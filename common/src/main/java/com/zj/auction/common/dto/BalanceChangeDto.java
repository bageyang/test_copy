package com.zj.auction.common.dto;

import com.zj.auction.common.enums.FundTypeEnum;

import java.math.BigDecimal;

public class BalanceChangeDto {
    /**
     * 更改金额
     */
    private BigDecimal changeNum;
    /**
     * 钱包类型
     */
    private FundTypeEnum fundType;
    /**
     * 用户id
     */
    private Long userId;

    private String transactionSn;

    /**
     * 备注
     */
    private String remark;

    public BigDecimal getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(BigDecimal changeNum) {
        this.changeNum = changeNum;
    }

    public FundTypeEnum getFundType() {
        return fundType;
    }

    public void setFundType(FundTypeEnum fundType) {
        this.fundType = fundType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTransactionSn() {
        return transactionSn;
    }

    public void setTransactionSn(String transactionSn) {
        this.transactionSn = transactionSn;
    }
}
