package com.zj.auction.common.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Builder
@Data
@TableName("zj_wallet")
public class Wallet implements Serializable {
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 钱包类型
     */
    private Byte fundType;

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

    private static final long serialVersionUID = 1L;


}