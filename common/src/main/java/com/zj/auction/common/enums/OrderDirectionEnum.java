package com.zj.auction.common.enums;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public enum OrderDirectionEnum {
    BUY((byte) 1),SELL((byte)2);
    private final byte code;

    OrderDirectionEnum(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }

    public static boolean check(Byte code) {
        return Objects.nonNull(code)
                && (Objects.equals(BUY.getCode(), code)
                    || Objects.equals(SELL.getCode(), code));
    }

    public boolean isEqual(Byte code) {
        return Objects.equals(getCode(),code);
    }
}
