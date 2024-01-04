package com.zj.auction.common.exception;

public interface GlobalException {
    Integer  getCode();
    String getMessage();

    Object getData();
}
