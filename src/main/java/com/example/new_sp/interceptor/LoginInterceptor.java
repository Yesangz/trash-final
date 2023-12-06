package com.example.new_sp.interceptor;

import com.example.new_sp.domain.account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Optional;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        try{
            System.out.println("登录拦截");
            HttpSession session = request.getSession(false);
            if (Optional.ofNullable(session).isPresent()){
                account ac = (account) session.getAttribute("account");
                if (Optional.ofNullable(ac).isPresent()){
                    System.out.println("登录拦截通过");
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("拦截器重定向");
        response.sendRedirect("/login.html");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
