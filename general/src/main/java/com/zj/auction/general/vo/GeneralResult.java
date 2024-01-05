package com.zj.auction.general.vo;

import com.zj.auction.common.enums.StatusEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * describe:SB式的返回结果
 */
@Data
public class GeneralResult<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 返回码
     **/
    private int code = 0;

    /**
     * 返回异常消息
     **/
    private String msg;

    /**
     * 返回的结果
     **/
    private transient T result;

    /**
     * 分页实体
     **/
    private transient PageAction pageAction;

    /**
     * 防止重复提交的Token
     **/
    private String token;

    public GeneralResult() {
    }

    public GeneralResult(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public GeneralResult(int code, T result) {
        super();
        this.code = code;
        this.result = result;
    }

    public GeneralResult(T result , PageAction pageAction) {
        super();
        this.pageAction = pageAction;
        this.result = result;
    }

    public GeneralResult(int code, T result , PageAction pageAction) {
        super();
        this.pageAction = pageAction;
        this.result = result;
    }

    public GeneralResult(int code, String msg, T result) {
        super();
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public GeneralResult(int code, String msg, T result, PageAction pageAction, String token) {
        this.code = code;
        this.msg = msg;
        this.result = result;
        this.pageAction = pageAction;
        this.token = token;
    }


    /**
     * @param <T> T
     */
    public static <T> GeneralResult<T> success() {
        return success(null);
    }

    /**
     * @param <T>  T
     * @param data data
     */
    public static <T> GeneralResult<T> success(T data) {
        return new GeneralResult<>( StatusEnum.SUCCESS.getCode(), data);
    }

    /**
     * @param <T>  T
     * @param data data
     */
    public static <T> GeneralResult<T> success(T data,PageAction pageAction) {
        return new GeneralResult<T>( StatusEnum.SUCCESS.getCode(), data,pageAction);
    }

    public static <T> GeneralResult<T> failure(int code, String msg) {
        return new GeneralResult(code, msg);
    }

    /**
     * @param msg  错误信息
     * @describe 失败
     * @title fail
     * @author Mao Qi
     * @date 2019/9/6 16:58
     * @return com.duoqio.boot.framework.entity.vo.GeneralResult
     */
    public static <T> GeneralResult<T> failure(String msg) {
        return new GeneralResult(StatusEnum.ERROR.getCode(), msg);
    }

}
