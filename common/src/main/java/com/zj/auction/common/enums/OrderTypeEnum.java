package com.zj.auction.common.enums;

import java.util.Objects;

public enum OrderTypeEnum {
    /**
     * 现金订单
     */
    CASH((byte)1),
    /**
     * 积分订单
     */
    INTEGRAL((byte)2);
    private final byte code;

    OrderTypeEnum(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }

    public static boolean isValid(Byte code){
        return Objects.nonNull(code)
                &&(Objects.equals(CASH.getCode(),code)
                ||Objects.equals(INTEGRAL.getCode(),code)
            );
    }

    public static OrderTypeEnum codeOf(Byte code){
        if(Objects.isNull(code)){
            return null;
        }
        if(Objects.equals(CASH.getCode(),code)){
            return CASH;
        }
        if(Objects.equals(INTEGRAL.getCode(),code)){
            return INTEGRAL;
        }
        return null;
    }

}
