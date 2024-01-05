//package com.zj.auction.gateway.filter;
//
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.Optional;
//import java.util.Set;
//
///**
// * bageyang
// */
//@Component
//public class JwtFilter implements GlobalFilter, Ordered {
//
//    @Value("${config.whiteUrl:}")
//    Set<String> whiteList;
//    private static final String AUTHORIZATION = "Authorization";
//
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        ServerHttpResponse response = exchange.getResponse();
//        String path = request.getURI().getPath();
//        //白名单直接放行
//        Optional<String> pass = whiteList.stream().filter(path::startsWith).findAny();
//        if (pass.isPresent()) {
//            return chain.filter(exchange);
//        }
//
//        HttpHeaders httpHeaders = request.getHeaders();
//
//        String token = httpHeaders.getFirst(AUTHORIZATION);
//        if (null == token) {
//            return GateWayResponseUtil.doReject(response, "认证信息不能为空");
//        }
//        boolean verify = true; // todo 校验token
//        if (!verify) {
//            return GateWayResponseUtil.doReject(response, "认证信息失败");
//        }
//
//        // todo 获取用户id
//        String userId = "202";
//        exchange.getRequest().mutate().header("userId", userId).build();
//        return chain.filter(exchange);
//    }
//
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//
//}
