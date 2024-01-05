package com.zj.auction.common.dto;

import java.math.BigDecimal;

public class RebateTransferDto {
    private BigDecimal num;
    private Long toUserId;

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }
}
