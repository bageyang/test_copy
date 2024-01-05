package com.zj.auction.common.enums;

import static com.zj.auction.common.enums.OrderDirectionEnum.BUY;
import static com.zj.auction.common.enums.OrderDirectionEnum.SELL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 订单状态枚举
 *  -3 转拍次数达到上限
 *  -2 流拍
 *  -1 抢拍成功未支付
 *  1 抢拍成功买家已支付,卖家未放货
 *  2 卖家拒绝
 *  3 商品所有者已转移(买家已支付,卖家已放货)
 *  4 商品转拍中未支付保证金
 *  5 商品转拍中
 *  6 等待买家付款(针对商品拥有者而言)
 *  7 买家已付款,等待卖家确认收款放货--等待放货(针对商品拥有者而言)
 *  8 finish
 *  9 提货中
 *  10 已收货
 */
public enum OrderStatEnum {

    /**
     * 转拍次数达到上限
     */
    AUCTION_UPPER_LIMIT( (byte)-3, BUY.getCode()),
    /**
     * 流拍
     */
    AUCTION_ABORTED( (byte)-2,BUY.getCode()),
    /**
     * 抢拍成功未支付
     */
    UN_PAYMENT( (byte)-1,  BUY.getCode()),
    /**
     * 买家已确认支付,卖家未放货
     */
    WAIT_SELLER_CONFIRM( (byte)1, BUY.getCode()),
    /**
     * 卖家拒绝
     */
    SELLER_REJECT( (byte)2,  BUY.getCode()),

    /**
     * 买家已支付,卖家已放货
     */
    AUCTION_SUCCESS( (byte)3,  BUY.getCode()),
    /**
     * 商品转拍中未支付保证金
     */
    WAIT_MARGIN( (byte)4, SELL.getCode()),
    /**
     * 商品转拍中(已上架拍品)
     */
    ON_AUCTION( (byte)5, SELL.getCode()),
    /**
     * 等待买家付款
     */
    WAIT_BUYER_PAYMENT( (byte)6, SELL.getCode()),
    /**
     * 买家已付款,等待卖家确认收款放货
     */
    UN_CONFIRM((byte)7, SELL.getCode()),
    /**
     * finish
     */
    FINISH( (byte) 8,  SELL.getCode()),
    PICKUP( (byte) 9,  BUY.getCode()),
    RECEIVED( (byte) 10,  BUY.getCode()),

    ;
    /**
     * 订单状态码
     */
    private final byte code;
    /**
     * 状态码对应订单方向(1:买,2卖)
     */
    private final byte orderWay;



    private static final List<Byte> buyStatCode = new ArrayList<>();

    private static final List<Byte> sellStatCode = new ArrayList<>();

    OrderStatEnum(byte code, byte orderWay) {
        this.code = code;
        this.orderWay = orderWay;
    }

    static {
        OrderStatEnum[] values = OrderStatEnum.values();
        for (OrderStatEnum value : values) {
            if (BUY.isEqual(value.getOrderWay())) {
                buyStatCode.add(value.getCode());
            }
            if(SELL.isEqual(value.getOrderWay())){
                sellStatCode.add(value.getCode());
            }
        }
    }

    public static List<Byte> getDirectStatList(Byte orderDirection) {
        if(BUY.isEqual(orderDirection)){
            return buyStatCode;
        }
        if(SELL.isEqual(orderDirection)){
            return sellStatCode;
        }
        return Collections.emptyList();
    }

    public byte getCode() {
        return code;
    }

    public byte getOrderWay() {
        return orderWay;
    }

    public boolean isEqual(Byte code){
        return Objects.nonNull(code) && Objects.equals(this.code,code);
    }
}
