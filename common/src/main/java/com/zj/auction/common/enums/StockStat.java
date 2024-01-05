package com.zj.auction.common.enums;

public enum StockStat {
    /**
     * 流转中
     */
    flowing((byte)1),
    /**
     * 被取货 回收
     */
    PICKED_UP((byte)2),
    /**
     * 回收
     */
    RECYCLE((byte)3)
    ;
    private final byte code;

    StockStat(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }
}
