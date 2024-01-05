package com.zj.auction.common.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 基础订单信息
 */
@Data
public class PayDto {
    private LocalDateTime createTime;
    private String billType;
    private String payType;
    private String transtionSn;
    private String billStatus;
    private Long originSn;
    private BigDecimal amount;
    private Long userId;

    @Override
    public String toString() {
        return "PayDto{" +
                "createTime=" + createTime +
                ", billType='" + billType + '\'' +
                ", payType='" + payType + '\'' +
                ", transtionSn='" + transtionSn + '\'' +
                ", billStatus='" + billStatus + '\'' +
                ", originSn=" + originSn +
                ", amount=" + amount +
                ", userId=" + userId +
                '}';
    }
}
