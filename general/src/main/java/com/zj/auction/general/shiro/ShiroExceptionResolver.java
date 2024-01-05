package com.zj.auction.general.shiro;

import org.apache.shiro.ShiroException;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mao Qi
 * @title ShiroExceptionResolver
 * @package com.duoqio.boot.framework.shiro
 * @describe shiro异常处理
 * @date 2019/8/28 9:23
 * @copyright 重庆多企源科技有限公司
 * @website {http://www.duoqio.com/index.asp?source=code}
 */
public class ShiroExceptionResolver extends SimpleMappingExceptionResolver {

    @Override
    public ModelAndView resolveException(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, Object handler, @NonNull Exception ex) {
        if (ex instanceof ShiroException) {
            return new ModelAndView("redirect:/login");
        }
        return super.resolveException(request, response, handler, ex);
    }
}
