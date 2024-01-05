package com.zj.auction.general.auth;
import com.zj.auction.common.model.User;
import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

import java.io.Serializable;


/**
 * 身份验证令牌
 *
 * @author 胖胖不胖
 * @date 2022/05/30
 */
@Data
public class AuthToken implements Serializable, AuthenticationToken {
   private Long userId;
   private String token;
   private User user;

   /**
    * 用户唯一标识
    */
   private String openId;

   /**
    * 会话密钥
    */
   private String sessionKey;

   /**
    * 用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回
    */
   private String unionId;


   /**
    * @param userId     用户编号
    * @param openId     用户唯一标识
    * @param sessionKey 会话密钥
    * @param unionId    用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回
    */
   public AuthToken(Long userId, String openId, String sessionKey, String unionId, String token) {
      this.userId = userId;
      this.openId = openId;
      this.sessionKey = sessionKey;
      this.unionId = unionId;
      this.token = token;
   }

   /**
    * @param openId     用户唯一标识
    * @param sessionKey 会话密钥
    * @param unionId    用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回
    */
   public AuthToken(String openId, String sessionKey, String unionId, String token) {
      this.openId = openId;
      this.sessionKey = sessionKey;
      this.unionId = unionId;
      this.token = token;
   }

   /**
    * @param userId     用户编号
    * @param token     加密token
    */
   public AuthToken(Long userId, String token) {
      this.userId = userId;
      this.token = token;
   }

   public AuthToken() {
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
