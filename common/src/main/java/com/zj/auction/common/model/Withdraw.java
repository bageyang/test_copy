package com.zj.auction.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Withdraw {
    private Long id;

    private Long userId;

    private BigDecimal withdrawNum;

    private Byte auditStatus;

    private LocalDateTime drawTime;

    private LocalDateTime auditTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}