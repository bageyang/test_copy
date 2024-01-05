package com.zj.auction.common.enums;

import java.util.Objects;

/**
 * 订单通知枚举
 */
public enum OrderNotifyEnum {
    /**
     * 支付超时
     */
    PAYMENT_TIME_OUT((byte)1),
    /**
     * 确认收货超时
     */
    CONFIRM_TIME_OUT((byte)2),
    ;
    private byte code;

    OrderNotifyEnum(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }

    public boolean isEqual(Byte code){
        return Objects.nonNull(code)&&Objects.equals(getCode(),code);
    }
}
