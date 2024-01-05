package com.zj.auction.general.shiro;

public enum ResultCode {

    SUCCESS(200, "success"),
    FAILURE(1, "failure"),

    // 用户登录认证错误码
    UNKNOWN_ACCOUNT(20020,"账户不存在"),
    INCORRECT_CREDENTIALS(20021,"用户名或密码不正确"),
    LOCKED_ACCOUNT(20022,"账户被锁定"),
    EXCESSIVE_ATTEMPTS(20023,"密码错误次数过多"),
    LOGIN_FAIL(20024,"内部错误"),
    UNAUTHORIZED(20025,"未登录"),
    FORBIDDEN(20026,"无访问权限");

    private Integer code;
    private String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer code() {
        return code;
    }

    public String msg() {
        return msg;
    }

}