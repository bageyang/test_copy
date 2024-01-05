package com.zj.auction.common.vo;
import com.zj.auction.common.constant.ResponseCode;
import com.zj.auction.common.exception.ServiceException;

/**
 * 统一数据返回 可根据实际需要修改和扩编
 */
public class addResponseData<T> {

    private static final long serialVersionUID = 1L;

    public boolean success;
    /**
     * 状态码
     */
    public int code;

    /**
     * msg
     */
    public String msg;
    /**
     * 返回的数据
     */
    public Object data;

    /**
     * 返回成功默认状态码200
     */
    public static <T> ResponseData<T> ok() {
        return returnResult(true, "操作成功", ResponseCode.OK, null);
    }

    /**
     * 返回成功默认状态码200
     *
     * @Param data 返回的数据
     */
    public static <T> ResponseData<T> ok(T data) {
        return returnResult(true, "操作成功", ResponseCode.OK, data);
    }

    /**
     * 返回成功 自定义状态码消息
     *
     * @Param data 返回的数据
     * @Param code 返回的状态码
     * @Param msg  返回的消息
     */
    public static <T> ResponseData<T> ok(String msg, T data) {
        return returnResult(true, msg, ResponseCode.OK, data);
    }

    /**
     * 返回成功 自定义状态码消息
     *
     * @Param data 返回的数据
     * @Param code 返回的状态码
     * @Param msg  返回的消息
     */
    public static <T> ResponseData<T> ok(String msg, int code, T data) {
        return returnResult(true, msg, code, data);
    }

    /**
     * 操作失败
     */
    public static <T> ResponseData<T> fail() {
        return returnResult(false, "操作失败", ResponseCode.ERROR, null);
    }

    /**
     * 返回失败
     *
     * @Param msg 返回失败的消息 必须
     * @Param code 返回失败的错误码 必须
     */
    public static <T> ResponseData<T> fail(String msg, int code) {
        return returnResult(false, msg, code, null);
    }

    /**
     * 返回失败
     *
     * @Param data 返回的数据
     * @Param code 返回的状态码
     * @Param msg  返回的消息
     */
    public static <T> ResponseData<T> fail(String msg, int code, T data) {
        return returnResult(false, msg, code, data);
    }

    private static <T> ResponseData<T> returnResult(boolean success, String msg, int code, T data) {
        ResponseData<T> rspData = new ResponseData<>();
        rspData.setSuccess(success);
        rspData.setCode(code);
        rspData.setMsg(msg);
        rspData.setData(data);
        return rspData;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
