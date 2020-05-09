package com.gageshan.safechat.interceptor;

import com.gageshan.safechat.enums.ChatType;
import com.gageshan.safechat.enums.ConstantEnum;
import com.gageshan.safechat.netty.UserRef;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Create by gageshan on 2020/5/10 0:37
 */
@Component
public class UserAuthInteceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String userToken = (String)session.getAttribute(ConstantEnum.USER_TOKEN.STATUS);
        if(userToken == null) {
            response.sendRedirect("login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
