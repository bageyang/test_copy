package com.zj.auction.common.enums;

public enum FundTypeEnum {
    /**
     * 现金
     */
    CASH((byte)1),
    /**
     * 积分(金高粱)
     */
    INTEGRAL((byte)2),
    /**
     * 绩效钱包 performance
     */
    REBATE((byte)3),
    ;
    private final byte code;

    FundTypeEnum(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }
}
