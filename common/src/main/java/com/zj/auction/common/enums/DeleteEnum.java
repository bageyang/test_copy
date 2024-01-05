package com.zj.auction.common.enums;

public enum DeleteEnum {
    UN_DELETE((byte)0),
    DELETED((byte)1);
    private final byte code;

    DeleteEnum(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }
}
