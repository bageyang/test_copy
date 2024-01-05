package com.zj.auction.general.shiro;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.ExpiredCredentialsException;

import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: lhy
 *  jwt过滤器，作为shiro的过滤器，对请求进行拦截并处理
跨域配置不在这里配了，我在另外的配置类进行配置了，这里把重心放在验证上
 */
@Slf4j
@Component
public class JWTFilter extends BasicHttpAuthenticationFilter {

    /**
     * 进行token的验证
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        //在请求头中获取token
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("Authorization"); //前端命名Authorization
        //token不存在
        if(token == null || "".equals(token)){
            out(response,"无token，无权访问，请先登录");
            return false;
        }

        //token存在，进行验证
        JwtToken jwtToken = new JwtToken(token);
        try {
            SecurityUtils.getSubject().login(jwtToken);  //通过subject，提交给myRealm进行登录验证
            return true;
        } catch (ExpiredCredentialsException e){
            out(response,"token过期，请重新登录");
            e.printStackTrace();
            return false;
        } catch (ShiroException e){
            // 其他情况抛出的异常统一处理，由于先前是登录进去的了，所以都可以看成是token被伪造造成的
            out(response,"token被伪造，无效token");
            e.printStackTrace();
            return false;
        }
    }
    /**
     * json形式返回结果token验证失败信息，无需转发
     */
    private void out(ServletResponse response, String res) throws IOException {
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        ObjectMapper mapper = new ObjectMapper();
        String jsonRes = mapper.writeValueAsString(res);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.getOutputStream().write(jsonRes.getBytes());
    }

    /**
     * 过滤器拦截请求的入口方法
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            return executeLogin(request, response);  //token验证
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * isAccessAllowed()方法返回false，即认证不通过时进入onAccessDenied方法
     */
//    @Override
//    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
//        return super.onAccessDenied(request, response);
//    }

    /**
     * token认证executeLogin成功后，进入此方法，可以进行token更新过期时间
     */
//    @Override
//    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {

//    }
}
