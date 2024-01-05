package com.zj.auction.common.enums;

public enum ExpressStatEnum {
    // 待发货,
    WAIT_DELIVER((byte)1),
    // 待收货
    WAIT_RECEIVED((byte)2),
    // 已收货
    RECEIVED((byte)3)
    ;
    private final byte code;

    ExpressStatEnum(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }
}
