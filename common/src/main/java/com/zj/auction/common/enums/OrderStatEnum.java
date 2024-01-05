package com.zj.auction.common.enums;

import java.util.Objects;

/**
 * 订单状态枚举
 *  -3 转拍次数达到上限
 *  -2 流拍 => 4
 *  -1 抢拍成功未支付 => -2,1
 *  1 抢拍成功买家已支付,卖家未放货
 *  2 商品所有者已转移(买家已支付,卖家已放货) => 3,-3
 *  3 商品转怕中未支付保证金
 *  4 商品转拍中
 *  5 等待买家付款(针对商品拥有者而言) => -2,6
 *  6 买家已付款--等待放货(针对商品拥有者而言)
 *  7 finish 上限
 */
public enum OrderStatEnum {
    AUCTION_UPPER_LIMIT( (byte)-3),
    AUCTION_ABORTED( (byte)-2),
    UN_PAYMENT( (byte)-1),
    WAIT_CONFIRM( (byte)1),
    AUCTION_SUCCESS( (byte)2),
    WAIT_MARGIN( (byte)3),
    ON_AUCTION( (byte)4),
    WAIT_PAYMENT( (byte)5),
    UN_CONFIRM((byte)6),
    FINISH( (byte) 7),
    ;
    private byte code;

    OrderStatEnum(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public boolean isEqual(Byte code){
        return Objects.nonNull(code) && Objects.equals(this.code,code);
    }
}
