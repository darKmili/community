package com.newcoder.community.controller.interceptor;

import com.newcoder.community.dao.LoginTicketMapper;
import com.newcoder.community.dao.UserMapper;
import com.newcoder.community.entity.LoginTicket;
import com.newcoder.community.entity.User;
import com.newcoder.community.util.CookieUtil;
import com.newcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： leon
 * @description：
 * @date： 2022/8/23
 * @version: 1.0
 */
@Component
public class HomeInterceptor implements HandlerInterceptor {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginTicketMapper ticketMapper;

    @Autowired
    private HostHolder threadLocalUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = CookieUtil.getCookieValue(request, "ticket");
        LoginTicket loginTicket = ticketMapper.selectByTicket(ticket);
        if (loginTicket!=null&&loginTicket.getStatus()==0&&loginTicket.getExpired().after(new Date())){
            User user = userMapper.selectById(loginTicket.getUserId());
            if (user.getStatus()==1){
                threadLocalUtil.setValue(user);
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView!=null){
            User user = (User) threadLocalUtil.getValue();
            if (user!=null){
                modelAndView.addObject("loginUser",user);
            }
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        threadLocalUtil.removeValue();
    }
}
