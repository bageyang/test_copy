package com.zj.auction.common.enums;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 订单状态枚举 todo 提货状态
 *  -3 转拍次数达到上限
 *  -2 流拍 => 4
 *  -1 抢拍成功未支付 => -2,1
 *  1 抢拍成功买家已支付,卖家未放货
 *  2 商品所有者已转移(买家已支付,卖家已放货) => 3,-3
 *  3 商品转拍中未支付保证金
 *  4 商品转拍中
 *  5 等待买家付款(针对商品拥有者而言) => -2,6
 *  6 买家已付款,等待卖家确认收款放货--等待放货(针对商品拥有者而言)
 *  7 finish
 */
public enum OrderStatEnum {
    /**
     * 转拍次数达到上限
     */
    AUCTION_UPPER_LIMIT( (byte)-3),
    /**
     * 流拍
     */
    AUCTION_ABORTED( (byte)-2),
    /**
     * 抢拍成功未支付
     */
    UN_PAYMENT( (byte)-1),
    /**
     * 买家已确认支付,卖家未放货
     */
    WAIT_SELLER_CONFIRM( (byte)1),
    /**
     * 卖家拒绝
     */
    SELLER_REJECT( (byte)2),
    /**
     * 买家已支付,卖家已放货
     */
    AUCTION_SUCCESS( (byte)3),
    /**
     * 商品转拍中未支付保证金
     */
    WAIT_MARGIN( (byte)4),
    /**
     * 商品转拍中(已上架拍品)
     */
    ON_AUCTION( (byte)5),
    /**
     * 等待买家付款
     */
    WAIT_BUYER_PAYMENT( (byte)6),
    /**
     * 买家已付款,等待卖家确认收款放货
     */
    UN_CONFIRM((byte)7),
    /**
     * finish
     */
    FINISH( (byte) 8),

    ;
    private byte code;

    OrderStatEnum(byte code) {
        this.code = code;
    }

    public static List<Byte> getDirectStatList(Byte orderDirection) {
        if(OrderDirectionEnum.BUY.isEqual(orderDirection)){
            return Lists.newArrayList(AUCTION_UPPER_LIMIT.getCode(),
                    UN_PAYMENT.getCode(),
                    WAIT_SELLER_CONFIRM.getCode(),
                    SELLER_REJECT.getCode(),
                    AUCTION_SUCCESS.getCode());
        }
        if(OrderDirectionEnum.SELL.isEqual(orderDirection)){
            return Lists.newArrayList(WAIT_MARGIN.getCode(),
                    ON_AUCTION.getCode(),
                    WAIT_BUYER_PAYMENT.getCode(),
                    UN_CONFIRM.getCode(),
                    FINISH.getCode());
        }
        return Collections.emptyList();
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
