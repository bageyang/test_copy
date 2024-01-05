package com.zj.auction.common.enums;

/**
 * 状态枚举
 *
 * @author yangbing
 */
public enum StatusEnum {
    /**
     * 请求状态
     */
    SUCCESS(200, "成功"),
    FAILED(499, "失败"), // 通用失败
    ERROR(500, "错误"), //  通用错误


    /**
     * 系统相关异常 (1000 - 1999）
     */
    AUCTION_FINISH_ERROR(1001,"拍品已结束"),
    PARAM_ERROR(1002,"参数缺失"),
    SECKILL_FAIL_ERROR(1003,"抢单失败,请稍后再试！"),
    OWNER_ORDER_MISS_ERROR(1004,"无法找到原始订单信息"),
    AUCTION_MISS_ERROR(1005,"无法找到拍品信息"),
    STOCK_NOT_MATCH_ERROR(1006,"库存信息不像符"),
    AUCTION_NOT_MATCH_ERROR(1007,"拍品信息不匹配"),
    ORDER_STATUS_ERROR(1008,"订单状态异常"),
    TRANSFER_LIMIT_ERROR(1009,"转拍限制,无法转拍"),
    PAYMENT_VOUCHER_BLANK_ERROR(1010,"支付凭证不得为空"),
    GOODS_INFO_BLANK_ERROR(1011,"商品信息不得为空"),
    STOCK_BASE_GOODS_ERROR(1012,"只能添加同一商品下的库存"),
    CREATE_AUCTION_ERROR(1013,"只能添加同一商品下的库存"),
    ;

    private Integer code;

    private String message;

    StatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
