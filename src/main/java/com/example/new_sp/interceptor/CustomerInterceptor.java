package com.example.new_sp.interceptor;

import com.example.new_sp.Masks.AuthorityMask;
import com.example.new_sp.Masks.TypeMask;
import com.example.new_sp.domain.account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
public class CustomerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("用户拦截");
        HttpSession session = request.getSession(false);
        if(Optional.ofNullable(session).isPresent()){
            account ac = (account) session.getAttribute("account");
            if ((ac.getAuthority()& TypeMask.Customer)==TypeMask.Customer){
                System.out.println("用户判断成功");
                return true;
            }
            else {
                System.out.println("用户判断失败");
                return false;
            }
        }
        return false;
    }
}
