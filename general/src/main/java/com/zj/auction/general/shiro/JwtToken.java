package com.zj.auction.general.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author: lhy
 * 自定义的shiro接口token，可以通过这个类将string的token转型成AuthenticationToken，可供shiro使用
 * 注意：需要重写getPrincipal和getCredentials方法，因为是进行三件套处理的，没有特殊配置shiro无法通过这两个         方法获取到用户名和密码，需要直接返回token，之后交给JwtUtil去解析获取。（当然了，可以对realm进行配
置HashedCredentialsMatcher，这里就不这么处理了）
 */
public class JwtToken implements AuthenticationToken {
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }
    @Override
    public Object getCredentials() {
        return token;
    }
}
