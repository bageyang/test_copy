//package com.zj.auction.common.util;
//
//import com.alibaba.fastjson.JSONObject;
//import com.rocket.common.core.constant.ResponseCode;
//import com.rocket.common.core.domain.ResponseData;
//import com.zj.auction.common.dto.Ret;
//import com.zj.auction.general.vo.GeneralResult;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import reactor.core.publisher.Mono;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * ServerLet 工具类
// */
//
//public class ServletUtils {
//    /**
//     * 设置webflux模型响应
//     *
//     * @param response ServerHttpResponse
//     * @param code     响应状态码
//     * @param value    响应内容
//     * @return Mono<Void>
//     */
//    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, Object value, int code, String msg) {
//        return webFluxResponseWriter(response, HttpStatus.OK, value, code, msg);
//    }
//
//    /**
//     * 设置webflux模型响应
//     *
//     * @param response ServerHttpResponse
//     * @param value    响应内容
//     * @return Mono<Void>
//     */
//    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, Object value, String msg) {
//        return webFluxResponseWriter(response, HttpStatus.OK, value, ResponseCode.ERROR, msg);
//    }
//
//    /**
//     * 设置webflux模型响应
//     *
//     * @param response   ServerHttpResponse
//     * @param httpStatus http状态码
//     * @param code       响应状态码
//     * @param value      响应内容
//     * @return Mono<Void>
//     */
//    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, HttpStatus httpStatus, Object value, int code, String msg) {
//        return webFluxResponseWriter(response, MediaType.APPLICATION_JSON_VALUE, httpStatus, value, code, msg);
//    }
//
//    /**
//     * 设置webflux模型响应
//     *
//     * @param response    ServerHttpResponse
//     * @param contentType content-type
//     * @param httpStatus  http状态码
//     * @param code        响应状态码
//     * @param value       响应内容
//     * @return Mono<Void>
//     */
//    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, String contentType, HttpStatus httpStatus, Object value, int code, String msg) {
//        response.setStatusCode(httpStatus);
//        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, contentType);
//        Ret<?> result = Ret.ok(msg, code, value.toString());
//        DataBuffer dataBuffer = response.bufferFactory().wrap(JSONObject.toJSONString(result).getBytes());
//        return response.writeWith(Mono.just(dataBuffer));
//    }
//
//    /**
//     * 获取 HttpServletRequest
//     */
//    public static HttpServletRequest GetRequest() {
//        try {
//            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//            return servletRequestAttributes.getRequest();
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    /**
//     * 获取 Request中的 String 参数
//     */
//    public static String GetParameter(String name) {
//        String param = GetRequest().getParameter(name);
//        if (StringUtils.isNotBlank(param)) {
//            return param;
//        }
//        return "";
//    }
//    /**
//     * 获取 Request中的 String 参数
//     */
//    public static String GetRequestToken(String name) {
//        String param = GetRequest().getHeader(name);
//        if (StringUtils.isNotBlank(param)) {
//            return param;
//        }
//        return "";
//    }
//}
