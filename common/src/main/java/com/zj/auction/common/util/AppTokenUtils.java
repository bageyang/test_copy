package com.zj.auction.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zj.auction.common.exception.ServiceException;
import com.zj.auction.common.exception.SystemExceptionEnum;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Title WxTokenUtils
 * @Package com.duoqio.boot.business.util.token
 * @Description APP或者微信登录token
 * @Author Mao Qi
 * @Date 2020/3/20 17:20
 * @Copyright 重庆多企源科技有限公司
 * @Website {http://www.duoqio.com/index.asp?source=code}
 */
@Log4j2
@Component
public class AppTokenUtils {
    private static final ThreadLocal<AuthToken> CURRENT_CONSUMER_TOKEN = new ThreadLocal<>();
    private static final String AUTH_HEADER_NAME = "Token";
    private static final String USER_ID = "userId";
    private static final String OPEN_ID = "openId";
    public static final String CODE_FILE = "telCode:";
    //验证码过期时间
    private static Long code = 5*60L;

    /**
     * @param appToken 登录信息
     * @describe 设置当前用户登录信息
     * @title setWxToken
     * @author Mao Qi
     * @date 2019/8/2 19:23
     */
    public static void setAppToken(AuthToken appToken) {
        CURRENT_CONSUMER_TOKEN.set(appToken);
    }


    /**
     * @describe 获取token
     * @title remove
     * @author Mao Qi
     * @date 2019/8/2 19:43
     */
    public static AuthToken getAuthToken() {
        AuthToken wxToken = CURRENT_CONSUMER_TOKEN.get();
        if (wxToken == null) {
            throw new ServiceException(SystemExceptionEnum.PARAM_ERROR);
        }
        return wxToken;
    }

    //获取用户 (不抛异常)
    public static AuthToken getAuthTokenNoThrow() {
        AuthToken wxToken = CURRENT_CONSUMER_TOKEN.get();
        return wxToken;
    }
    /**
     * @describe 移除token
     * @title remove
     * @author Mao Qi
     * @date 2019/8/2 19:43
     */
    public static void remove() {
        CURRENT_CONSUMER_TOKEN.remove();
    }


    /**
     * @param token token
     * @return java.lang.String
     * @describe 获取openId
     * @title getUserIdFromToken
     * @author Mao Qi
     * @date 2019/8/2 19:28
     */
    public static String getUserIdFromToken(String token) {
        try {
            Claims jwt = JwtUtils.parseJWT(token);
            String subject = jwt.getSubject();
            JSONObject json = JSON.parseObject(subject);
            return json.getString(USER_ID);
        } catch (Exception e) {
            throw new ServiceException(-1,"业务异常");
        }
    }

    /**
     * @param token token
     * @return java.lang.String
     * @describe 获取openId
     * @title getOpenIdFromToken
     * @author Mao Qi
     * @date 2019/8/2 19:28
     */
    public static String getOpenIdFromToken(String token) {
        try {
            Claims jwt = JwtUtils.parseJWT(token);
            String subject = jwt.getSubject();
            JSONObject json = JSON.parseObject(subject);
            return json.getString(OPEN_ID);
        } catch (Exception e) {
            throw new ServiceException(-1,"业务异常");
        }
    }

    /**
     * 获取请求头中的token
     *
     * @param request 用户请求
     * @return 用户认证后的token
     */
    public static String getAuthTokenFromRequest(HttpServletRequest request) {
        String token = null;
        if (request.getHeader(AUTH_HEADER_NAME) != null) {
            token = request.getHeader(AUTH_HEADER_NAME);
        }
        return token;
    }

    /**
     * @describe 生成token
     * @author Mao Qi
     * @date 2019/8/2 9:16
     */
    public static String createToken(String id, String subject, long ttlMillis) {
        return JwtUtils.createJWT(id, subject, ttlMillis);
    }

    /**
     * @Description 将手机验证码存入redis
     * @Title setMessage
     * @Author Mao Qi
     * @Date 2020/4/13 19:06
     * @param key
     * @param value
     * @return	boolean
     */
    public static boolean setMessage(String key,String value){
        return RedisUtil.set(CODE_FILE+key, value,code);
    }


    /**
     * @Description 获取redis里面的信息
     * @Title getMassage
     * @Author Mao Qi
     * @Date 2020/4/13 19:59
     * @param tel
     * @return	java.lang.String
     */
    public static String getMassage(String tel){
        try {
            return String.valueOf(RedisUtil.get(CODE_FILE+tel));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
