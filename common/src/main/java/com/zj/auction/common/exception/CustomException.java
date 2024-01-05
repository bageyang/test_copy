package com.zj.auction.common.exception;

import com.zj.auction.common.enums.StatusEnum;

public class CustomException extends RuntimeException{
    StatusEnum errType;
    public CustomException() {
    }

    public CustomException(StatusEnum errType) {
        this.errType = errType;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
