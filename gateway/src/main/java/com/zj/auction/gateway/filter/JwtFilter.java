package com.zj.auction.gateway.filter;


import com.zj.auction.gateway.shiro.JwtUtil;
import com.zj.auction.gateway.utils.GateWayResponseUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Set;

/**
 * bageyang
 */
@Component
public class JwtFilter implements GlobalFilter, Ordered {

    @Value("${config.whiteUrl:}")
    Set<String> whiteList;
    private static final String AUTHORIZATION = "accessToken";


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getURI().getPath();
        //白名单直接放行
        Optional<String> pass = whiteList.stream().filter(path::startsWith).findAny();
        if (pass.isPresent()) {
            return chain.filter(exchange);
        }
        HttpHeaders httpHeaders = request.getHeaders();
        String accessToken = httpHeaders.getFirst(AUTHORIZATION);
        if (null == accessToken) {
            return GateWayResponseUtil.doReject(response, "认证信息不能为空");
        }
        boolean expire = JwtUtil.isExpire(accessToken);
        if(expire){
            return GateWayResponseUtil.doReject(response, "登录失效");
        }
        String userId = JwtUtil.getUserId(accessToken);
        exchange.getRequest().mutate().header("userId", userId).build();
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return 0;
    }

}
