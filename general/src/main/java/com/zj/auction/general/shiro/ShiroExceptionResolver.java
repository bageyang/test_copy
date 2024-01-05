package com.zj.auction.general.shiro;

import org.apache.shiro.ShiroException;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ShiroExceptionResolver extends SimpleMappingExceptionResolver {

    @Override
    public ModelAndView resolveException(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, Object handler, @NonNull Exception ex) {
        if (ex instanceof ShiroException) {
            return new ModelAndView("redirect:/login");
        }
        return super.resolveException(request, response, handler, ex);
    }
}
