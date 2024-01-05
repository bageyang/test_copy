package com.zj.auction.general.shiro;
import com.zj.auction.common.model.User;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.subject.Subject;

/**
 * 1）：身份令牌异常，不支持的身份令牌 -->
 * <p>
 * 　　org.apache.shiro.authc.pam.UnsupportedTokenException
 * <p>
 * 2）：未知账户/没找到帐号,登录失败 -->
 * <p>
 * 　　org.apache.shiro.authc.UnknownAccountException
 * <p>
 * 3）：帐号锁定 -->
 * <p>
 * 　　org.apache.shiro.authc.LockedAccountException
 * <p>
 * 4）：用户禁用 -->
 * <p>
 * 　　org.apache.shiro.authc.DisabledAccountException
 * <p>
 * 5）：登录重试次数，超限。只允许在一段时间内允许有一定数量的认证尝试 -->
 * <p>
 * 　　org.apache.shiro.authc.ExcessiveAttemptsException
 * <p>
 * 6）：一个用户多次登录异常：不允许多次登录，只能登录一次 。即不允许多处登录-->
 * <p>
 * 　　org.apache.shiro.authc.ConcurrentAccessException
 * <p>
 * 7）：账户异常 -->
 * <p>
 * 　　org.apache.shiro.authc.AccountException
 * <p>
 * 8）：过期的凭据异常 -->
 * <p>
 * 　　org.apache.shiro.authc.ExpiredCredentialsException
 * <p>
 * 9）：错误的凭据异常 -->
 * <p>
 * 　　org.apache.shiro.authc.IncorrectCredentialsException
 * <p>
 * 10）：凭据异常 -->
 * <p>
 * 　　org.apache.shiro.authc.CredentialsException
 * <p>
 * 　　org.apache.shiro.authc.AuthenticationException
 * <p>
 * <!-- 权限异常 -->
 * <p>
 * 11）：没有访问权限，访问异常 -->
 * <p>
 * 　　org.apache.shiro.authz.HostUnauthorizedException
 * <p>
 * 　　org.apache.shiro.authz.UnauthorizedException
 * <p>
 * 12）： 授权异常 -->
 * <p>
 * 　　org.apache.shiro.authz.UnauthenticatedException
 * <p>
 * 　　org.apache.shiro.authz.AuthorizationException
 * <p>
 * 13）：shiro全局异常 -->
 * <p>
 * 　　org.apache.shiro.ShiroException
 *
 * @author Mao Qi
 * @title SecurityUtils
 * @describe SecurityUtils
 */
public class SecurityUtils extends org.apache.shiro.SecurityUtils {

    /**
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
     * @title logout
     */
    public static void logout() {
        Subject subject = getSubject();
        if (subject == null) {
            return;
        }
        subject.logout();
    }
}
