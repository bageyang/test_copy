package com.zj.auction.gateway.exception;


import com.zj.auction.gateway.vo.RejectRet;
import io.netty.channel.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public RejectRet handleException(Exception e) {
        if (e instanceof ConnectTimeoutException) {
            ConnectTimeoutException ex = (ConnectTimeoutException) e;
            LOGGER.error("连接超时,{}",ex.getMessage());
            return RejectRet.of(500, "连接超时");
        } else {
            LOGGER.error("系统异常:", e);
            return RejectRet.of(500, "系统异常");
        }
    }

}
