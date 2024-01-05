package com.zj.auction.common.enums;

import java.util.Objects;

public enum AuctionStatEnum {
    UN_START((byte)0,"未开始"),
    OPEN((byte) 1,"进行中"),
    CLOSE((byte)2,"已结束");

    private final byte code;
    private final String text;

    AuctionStatEnum(byte code, String text) {
        this.code = code;
        this.text =text;
    }

    public static boolean isFinish(Byte code){
        return Objects.equals(CLOSE.code,code);
    }
}
