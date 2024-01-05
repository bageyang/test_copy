package com.zj.auction.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * @author Mao Qi
 * @title JwtUtils
 * @package com.duoqio.common.util
 * @describe Jwt Util
 * @date 2019/10/28 15:19
 * @copyright 重庆多企源科技有限公司
 * @website {http://www.duoqio.com/index.asp?source=code}
 */
public class JwtUtils {
    private static final String K = "N0JFRkUwQkY4MDBBQzgwRTlEMTAxOUU5NjVBOUY2Njc0ODk1ODg1QkYyMDMzMEE3QTdEOUNCQzBEODJERjE4RQ==";

    /**
     * 由字符串生成加密key
     * md5加密
     */
    private static SecretKey generalKey() {
        final String stringKey = K + "*tang^hu@2019*";
        byte[] encodedKey = stringKey.getBytes();
        return Keys.hmacShaKeyFor(encodedKey);
    }

    /**
     * @param id        id
     * @param subject   subject
     * @param ttlMillis ttlMillis
     * @return java.lang.String
     * @describe 创建jwt
     * @title createJWT
     * @author Mao Qi
     * @date 2019/10/28 15:23
     */
    public static String createJWT(String id, String subject, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey key = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id)//设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(now)//iat: jwt的签发时间
                .setSubject(subject)// 主题
                .signWith(key, signatureAlgorithm);//设置签名使用的签名算法和签名使用的秘钥
        /*if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            //设置过期时间
            builder.setExpiration(exp);
        }*/
        return builder.compact();
    }

    /**
     * @param jwt jwt
     * @return io.jsonwebtoken.Claims
     * @describe 解密jwt 刷新token时间
     * @title parseJWT
     * @author Mao Qi
     * @date 2019/10/28 15:23
     */
    public static Claims parseJWT(String jwt) {
        SecretKey key = generalKey();
        return Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
    }

    /**
     * @param jwt       jwt
     * @param ttlMillis ttlMillis
     * @return io.jsonwebtoken.Claims
     * @describe 刷新token时间
     * @title refresh
     * @author Mao Qi
     * @date 2019/10/28 15:22
     */
    public static Claims refresh(String jwt, long ttlMillis) {
        SecretKey key = generalKey();
        long nowMillis = System.currentTimeMillis();
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            claims.setExpiration(exp);
        }
        return claims;
    }
}
