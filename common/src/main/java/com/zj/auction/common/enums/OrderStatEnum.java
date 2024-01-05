package com.zj.auction.common.enums;

import java.util.Objects;

/**
 * 订单状态枚举
 *  -2 流拍 => 4 商品流拍 Commodity Auction
 *  -1 抢拍成功未支付 => -2,1
 *  1 抢拍成功买家已支付,卖家未放货
 *  2 商品所有者已转移(买家已支付,卖家已放货)
 *  3 商品转怕中未支付保证金
 *  4 商品转拍中
 *  5 等待买家付款(针对商品拥有者而言) => -2,6
 *  6 买家已付款--等待放货(针对商品拥有者而言)
 *  7 finish
 */
public enum OrderStatEnum {
    AUCTION_ABORTED( -2),
    UN_PAYMENT( -1),
    WAIT_CONFIRM( 1),
    AUCTION_SUCCESS( 2),
    WAIT_MARGIN( 3),
    ON_AUCTION( 4),
    WAIT_PAYMENT( 5),
    UN_CONFIRM(6),
    FINISH( 7),
    ;
    private int code;

    OrderStatEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isEqual(Integer code){
        return Objects.nonNull(code) && Objects.equals(this.code,code);
    }
}
