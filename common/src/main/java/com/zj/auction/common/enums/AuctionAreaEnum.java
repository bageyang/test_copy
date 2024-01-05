package com.zj.auction.common.enums;

public enum AuctionAreaEnum {
    //竞拍区
    MORNING_AREA((byte)0,"上午区"),
    AFTERNOON_AREA((byte)1,"下午区"),
    AREA_TYPE((byte)1,"活动专区"),

    //是否开发
    OPEN((byte)0,"正常开放"),
    CLOSE((byte)1,"关闭状态" );

    private final byte code;
    private final String text;



    AuctionAreaEnum(byte code, String text) {
        this.code = code;
        this.text = text;
    }

    public byte getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}
