package com.zj.auction.gateway.utils;

import com.alibaba.fastjson2.JSON;
import com.zj.auction.gateway.vo.RejectRet;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

/**
 * bageyang
 */
public class GateWayResponseUtil {

    /**
     * 统一返回
     * @param response
     * @param msg
     * @return
     */
    public static Mono<Void> doReject(ServerHttpResponse response, String msg) {
        RejectRet rejectRet = new RejectRet(-1, msg);
        byte[] bits = JSON.toJSONBytes(rejectRet);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }


}
