package com.newcoder.community.util;

import cn.hutool.core.util.StrUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： leon
 * @description：
 * @date： 2022/8/23
 * @version: 1.0
 */
public class CookieUtil {

    public static String getCookieValue(HttpServletRequest httpServletRequest,String key){
        if (StrUtil.isBlank(key)){
            throw new IllegalArgumentException("无效的参数");
        }
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies!=null){
            for (Cookie cookie : cookies) {
                if (key.equals(cookie.getName())){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
