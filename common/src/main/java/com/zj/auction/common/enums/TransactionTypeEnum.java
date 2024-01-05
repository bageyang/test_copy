package com.zj.auction.common.enums;

/**
 * 交易类型
 */
public enum TransactionTypeEnum {
    /**
     * 返利
     */
    REBATE((byte)1),
    /**
     * 充值
     */
    RECHARGE((byte)2),
    /**
     * 外部转账
     */
    EXTERNAL_TRANSFER((byte)3),
    /**
     * 内部划转
     */
    INNER_TRANSFER((byte)4),
    /**
     * 保证金操作
     */
    BAIL((byte)5),
    /**
     * 提现
     */
    WITHDRAW((byte)6),
    ;
    private final byte code;

    TransactionTypeEnum(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }
}
