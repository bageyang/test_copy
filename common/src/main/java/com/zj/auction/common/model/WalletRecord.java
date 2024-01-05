package com.zj.auction.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletRecord {
    private Long id;

    private Long userId;

    private Byte walletType;

    private Byte transactionType;

    private Byte transactionSn;

    private BigDecimal balanceBefore;

    private BigDecimal changeBalance;

    private BigDecimal balanceAfter;

    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}