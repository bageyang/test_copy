package com.zj.auction.common.exception;

import com.zj.auction.common.enums.StatusEnum;

public class CustomException extends RuntimeException{
    StatusEnum errType;
    private String message;
    public CustomException() {
    }

    public CustomException(StatusEnum errType) {
        this.errType = errType;
        this.message = errType.getMessage();
    }

    public CustomException(String message) {
        this.message = message;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }


    @Override
    public String getMessage() {
        return this.message;
    }
}
