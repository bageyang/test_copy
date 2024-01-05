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
    FAILED(201, "失败"), // 通用失败
    ERROR(202, "错误"); //  通用错误


    /**
     * 系统相关异常 (1000 - 1999）
     */


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
