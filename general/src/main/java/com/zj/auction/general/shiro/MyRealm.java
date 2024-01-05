package com.zj.auction.general.shiro;

import com.zj.auction.common.model.User;
import com.zj.auction.general.app.service.AppUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: lhy
 * 自定义Realm
 */
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private AppUserService userService;

    /**
     * 限定这个realm只能处理JwtToken（不加的话会报错）
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 授权(授权部分这里就省略了，先把重心放在认证上)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取到用户名，查询用户权限
        return null;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) {
        String token = (String) auth.getCredentials();  //JwtToken中重写了这个方法了
        String username = JwtUtil.getUsername(token);   // 获得username

        //用户不存在（这个在登录时不会进入，只有在token校验时才有可能进入）
        if(username == null)
            throw new UnknownAccountException();

        //根据用户名，查询数据库获取到正确的用户信息
        User user = userService.getUserByName(username);

        //用户不存在（这个在登录时不会进入，只有在token校验时才有可能进入）
        if(user == null)
            throw new UnknownAccountException();

        //密码错误(这里获取到password，就是3件套处理后的保存到数据库中的凭证，作为密钥)
        if (! JwtUtil.verifyToken(token, username, user.getPassWord())) {
            throw new IncorrectCredentialsException();
        }
        //toke过期
        if(JwtUtil.isExpire(token)){
            throw new ExpiredCredentialsException();
        }

        return new SimpleAuthenticationInfo(user, token, getName());
    }
}

