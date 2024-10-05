package com.qifei.intercepter;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Intercepters implements HandlerInterceptor {

    @Override
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session=request.getSession();
        String uri=request.getRequestURI();
        System.out.println("uri=="+uri);
        System.out.println("url");
        if(session.getAttribute("User")==null&&(!uri.equals(request.getContextPath())))
        {
            session.setAttribute("errorMsg","请登录");
            response.sendRedirect(request.getContextPath()+"/");
            return false;
        }
            session.removeAttribute("errorMsg");
            return true;
    }
}
