package com.zj.auction.common.dto;

import com.zj.auction.common.enums.StatusEnum;


/**
 * 返回结果
 * @author yangbing
 * @param <T>
 */
public class Ret<T> {


    private T data;
    private int code;
    private String msg;

    //成功，无返回值
    public Ret() {
        this.code = StatusEnum.SUCCESS.getCode();
        this.msg = StatusEnum.SUCCESS.getMessage();
    }

    //成功，有返回值
    public Ret(T data) {
        this.data = data;
        this.code = StatusEnum.SUCCESS.getCode();
        this.msg = StatusEnum.SUCCESS.getMessage();
    }


    //失败，及提示信息
    public Ret(StatusEnum resultStatus) {
        this.code = resultStatus.getCode();
        this.msg = resultStatus.getMessage();
    }

    //成功，无返回值
    public static <T> Ret<T> ok() {
        return new Ret<>();
    }

    //成功，有返回值
    public static <T> Ret<T> ok(T data) {
        return new Ret<>(data);
    }

    // 成功或失败
    public static <T> Ret<T> isOk(boolean isOk) {
        return isOk ? ok() : fail();
    }

    // 失败
    public static <T> Ret<T> fail() {
        return error(StatusEnum.FAILED);
    }

    public static <T> Ret<T> error() {
        return error(StatusEnum.ERROR);
    }

    //失败，及提示信息
    public static <T> Ret<T> error(StatusEnum resultStatus) {
        return new Ret<>(resultStatus);
    }

    //判断成功还是失败
    public boolean isOk() {
        return this.code == StatusEnum.SUCCESS.getCode();
    }


    public T getData() {
        return data;
    }

    public Ret<T> setData(T data) {
        this.data = data;
        return this;
    }

    public int getCode() {
        return code;
    }

    public Ret<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Ret<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

}
