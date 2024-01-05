package com.zj.auction.common.util;

import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ip info
 *
 * @author Mao Qi
 * @date 2019年6月6日
 * @describe
 */
@Log4j2
public class IPUtils {
    private static final String UNKNOWN = "unknown";
    private static final String X_FORWARDED_FOR = "X-Forwarded-For";
    private static final String X_REAL_IP = "X-Real-IP";
    private static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
    private static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
    private static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
    private static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";
    private static final String LOCALHOST_IP = "127.0.0.1";
    private static final String LOCALHOST_IP_16 = "0:0:0:0:0:0:0:1";
    private static final int MAX_IP_LENGTH = 15;


    /**
     * 获取ip地址
     *
     * @param request request
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String ipAddress = request.getHeader(X_REAL_IP);
        if (StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(X_FORWARDED_FOR);
        }
        if (StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(PROXY_CLIENT_IP);
        }
        if (StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(WL_PROXY_CLIENT_IP);
        }
        if (StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(HTTP_CLIENT_IP);
        }
        if (StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(HTTP_X_FORWARDED_FOR);
        }
        if (StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        if (StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (LOCALHOST_IP.equals(ipAddress) || LOCALHOST_IP_16.equals(ipAddress)) {
                //根据网卡取本机配置的IP
                try {
                    InetAddress inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    log.error("获取IP地址, 出现异常={}", e.getMessage(), e);
                }
            }
            log.info("获取IP地址 ipAddress={}", ipAddress);
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > MAX_IP_LENGTH) {
            if (ipAddress.indexOf(StringUtils.COMMA) > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(StringUtils.COMMA));
            }
        }
        return ipAddress;
    }
}
