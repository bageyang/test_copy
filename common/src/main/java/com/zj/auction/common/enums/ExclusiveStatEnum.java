package com.zj.auction.common.enums;

public enum ExclusiveStatEnum {
    UN_EXCLUSIVE((byte)1),
    EXCLUSIVE((byte)2);
    private final byte code;

    ExclusiveStatEnum(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }
}
