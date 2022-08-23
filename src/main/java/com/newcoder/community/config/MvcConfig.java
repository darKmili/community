package com.newcoder.community.config;

import com.newcoder.community.controller.interceptor.HomeInterceptor;
import com.newcoder.community.controller.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： leon
 * @description：
 * @date： 2022/8/23
 * @version: 1.0
 */
@Component
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private HomeInterceptor homeInterceptor;

    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(homeInterceptor)
               .excludePathPatterns("/**/*.css","/**/*.js","/**/*.png","/**/*.jpg","/login","/register","/captcha-image");

        registry.addInterceptor(loginCheckInterceptor)
                .excludePathPatterns("/**/*.css","/**/*.js","/**/*.png","/**/*.jpg","/login","/register","/captcha-image");
    }
}
