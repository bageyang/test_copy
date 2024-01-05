package com.zj.auction.common.enums;

public enum AuctionAppointmentEnum {
    //支付方式
    ALI_PAYMENT((byte)0,"支付宝支付"),
    WX_PAYMENT((byte)1, "微信支付"),

    //预约状态
    APPOINTMENT_NO_PAY((byte)0, "预约待支付" ),
    APPOINTMENT_SUCCESS((byte)1, "预约成功" ),
    APPOINTMENT_EXPIRE((byte)2, "预约过期" );
    private final byte code;
    private final String text;

    AuctionAppointmentEnum(byte code, String text) {
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
