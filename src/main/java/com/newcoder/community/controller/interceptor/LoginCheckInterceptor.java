package com.newcoder.community.controller.interceptor;

import com.newcoder.community.annotation.LoginChecked;
import com.newcoder.community.entity.User;
import com.newcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 登录检查拦截器
 *
 * @author： leon
 * @description：
 * @date： 2022/8/23
 * @version: 1.0
 */
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Autowired
    private HostHolder hostHolder;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod ){
            HandlerMethod method = (HandlerMethod) handler;
            LoginChecked methodAnnotation = method.getMethodAnnotation(LoginChecked.class);
            User user = (User) hostHolder.getValue();
            if (methodAnnotation!=null&&user==null){
                response.sendRedirect(contextPath+"/login");
                return false;
            }
        }
        return true;
    }
}
