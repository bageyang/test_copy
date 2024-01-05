package com.zj.auction.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private String transactionSn;

    private BigDecimal balanceBefore;

    private BigDecimal changeBalance;

    private BigDecimal balanceAfter;

    private String remark;
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createTime;
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updateTime;

}