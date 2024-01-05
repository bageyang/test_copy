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
    SECKILL_FAIL_ERROR(1003,"抢单失败,请稍后再试！")
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
