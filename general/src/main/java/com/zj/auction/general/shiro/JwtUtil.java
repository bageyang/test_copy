package com.zj.auction.general.shiro;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

//jwt工具，用来生成、校验token以及提取token中的信息
@Slf4j
public class JwtUtil {

    //指定一个token过期时间（毫秒）
    private static long EXPIRE_TIME =  24 * 60 * 60 * 1000; //1天  24 * 60 * 60 * 1000

    /**ji

     生成token
     */
//注意这里的sercet不是密码，而是进行三件套（salt+MD5+1024Hash）处理密码后得到的凭证
//这里为什么要这么做，在controller中进行说明
    public static String getJwtToken(String username, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret); //使用密钥进行哈希
// 附带username信息的token
        return JWT.create()
                .withClaim("username", username)
                .withExpiresAt(date) //过期时间
                .sign(algorithm); //签名算法
    }

    public static String getTmpJwtToken(String value, String secret,long expressTime) {
        Date date = new Date(expressTime);
        Algorithm algorithm = Algorithm.HMAC256(secret); //使用密钥进行哈希
// 附带username信息的token
        return JWT.create()
                .withClaim("userId", value)
                .withExpiresAt(date) //过期时间
                .sign(algorithm); //签名算法
    }
    /**

     校验token是否正确
     */
    public static boolean verifyToken(String token, String username, String secret) {
        try {
//根据密钥生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
//效验TOKEN（其实也就是比较两个token是否相同）
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
    /**

     在token中获取到username信息
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    /**

     判断是否过期
     */
    public static boolean isExpire(String token){
        if (null!=token) {
            DecodedJWT jwt = JWT.decode(token);
//        if (jwt.getExpiresAt().getTime() > System.currentTimeMillis()){
//            EXPIRE_TIME=EXPIRE_TIME+1 * 1 * 60 * 1000;
//        }
//            Date date = new Date(jwt.getExpiresAt().getTime());
//            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            System.out.println(sd.format(da
//            te));
            return jwt.getExpiresAt().getTime() < System.currentTimeMillis();
        }
        return false;
    }

}
