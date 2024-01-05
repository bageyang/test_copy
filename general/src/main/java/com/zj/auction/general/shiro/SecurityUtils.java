package com.zj.auction.general.shiro;

import com.zj.auction.common.model.User;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.subject.Subject;

/**
 * 安全跑龙套
 *
 * @author 胖胖不胖
 * @date 2022/06/16
 */
public class SecurityUtils extends org.apache.shiro.SecurityUtils {

    /**
     * @return {@link User}
     * @describe 获取用户信息
     * @title getPrincipal
     */
    public static User getPrincipal() {
        Subject subject = getSubject();
        if (subject == null || subject.getPrincipal() == null) {
            throw new UnauthenticatedException();
        }
        return (User) subject.getPrincipal();
    }

    /**
     * @describe 退出登录
     */
    public static void logout() {
        Subject subject = getSubject();
        if (subject == null) {
            return;
        }
        subject.logout();
    }
}
