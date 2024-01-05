package com.zj.auction.common.exception;

/**
 * 服务异常
 *
 * @author Administrator
 * @date 2021/05/19
 */
public class ServiceException extends RuntimeException{
    private final Integer  code;

    public ServiceException(GlobalException exception) {
        // 使用父类的 message 字段
        super(exception.getMessage());
        // 设置错误码
        this.code = exception.getCode();
    }

    public ServiceException(Integer  code, String message) {
        // 使用父类的 message 字段
        super(message);
        // 设置错误码
        this.code = code;
    }
    public Integer  getCode() {
        return code;
    }
}
