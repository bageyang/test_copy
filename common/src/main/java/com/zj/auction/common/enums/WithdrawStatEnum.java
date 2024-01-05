package com.zj.auction.common.enums;

public enum WithdrawStatEnum {
    WAIT_AUDIT((byte)1),
    SUCCESS((byte)2),
    REJECT((byte)3);
    private final byte code;

    WithdrawStatEnum(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }
}
