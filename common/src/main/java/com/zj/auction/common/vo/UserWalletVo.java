package com.zj.auction.common.vo;

import java.math.BigDecimal;

public class UserWalletVo {
    private BigDecimal cashBalance;
    private BigDecimal integralBalance;
    private BigDecimal rebateBalance;

    public BigDecimal getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(BigDecimal cashBalance) {
        this.cashBalance = cashBalance;
    }

    public BigDecimal getIntegralBalance() {
        return integralBalance;
    }

    public void setIntegralBalance(BigDecimal integralBalance) {
        this.integralBalance = integralBalance;
    }

    public BigDecimal getRebateBalance() {
        return rebateBalance;
    }

    public void setRebateBalance(BigDecimal rebateBalance) {
        this.rebateBalance = rebateBalance;
    }
}
