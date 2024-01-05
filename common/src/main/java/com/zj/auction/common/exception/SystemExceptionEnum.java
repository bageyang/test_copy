package com.zj.auction.common.exception;

/**
 * 系统异常枚举
 *
 * @author 胖胖不胖
 * @date 2022/05/28
 */
public enum SystemExceptionEnum implements GlobalException {
    SUCCESS(200, "成功"),
    SYSTEM_ERROR(500, "系统异常"),
    PARAMETER_ERROR(403, "请求方式错误"),
    NOT_FOUND(404, "您访问的页面不存在或查询无结果"),
    UNAUTHORIZED(401, "未授权"),
    ERROR_Permission_Denied(402,"错误权限，被拒绝"),
    TOKEN_HAS_DUE(503,"服务器繁忙，请稍后重试"),
    PARAM_ERROR(606,"参数缺失或错误，请稍后重试"),
    DATA_ILLEGALITY_CODE(607,"数据非法，请稍后重试"),
    UNEXPECTED_ERROR(502,"无法预期的错误，我们正在收集信息"),
    CODE_ERROR(512,"验证码失效"),

    ;
    SystemExceptionEnum(Integer  code, String message){
        this.code = code;
        this.message = message;
    }
    private final Integer  code;
    private final String message;

    @Override
    public Integer  getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
