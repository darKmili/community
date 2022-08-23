package com.newcoder.community.controller;

import org.springframework.http.HttpCookie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： leon
 * @description：
 * @date： 2022/8/22
 * @version: 1.0
 */
@RestController
public class AlphaController {
    @GetMapping("/cookie/set")
    public String setCookie(HttpServletResponse response){
        Cookie cookie = new Cookie("myid","xxxx123");
        cookie.setMaxAge(60*10);
        cookie.setPath("/cookie");
        response.addCookie(cookie);

        return "setCookie";
    }
    @GetMapping("/cookie/get")
    public String getCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getValue());
        }
        return "getCookie:";
    }
    @GetMapping("/session/set")
    public String setSession(HttpSession httpSession){
        httpSession.setAttribute("myid","haha session");
        return "setSession:";
    }
    @GetMapping("/session/get")
    public String getSession(HttpSession httpSession){
        Object myid = httpSession.getAttribute("myid");
        System.out.println(myid);
        return "getSession"+myid;
    }
}
