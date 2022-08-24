package com.newcoder.community.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.newcoder.community.dao.LoginTicketMapper;
import com.newcoder.community.dao.UserMapper;
import com.newcoder.community.entity.LoginTicket;
import com.newcoder.community.entity.User;
import com.newcoder.community.service.UserService;
import com.newcoder.community.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: UserServiceImpl
 * @Description: TODO
 * @author: li
 * @Date: 2022/8/21 1:26 上午
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${app.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String appContext;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public User findUserById(int userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public Map<String, Object> register(User user) {
        Map<String, Object> map = new HashMap<>(16);
        if (user == null) {
            throw new IllegalArgumentException("非法请求");
        }
        if (StrUtil.isBlank(user.getUsername())) {
            map.put("usernameMsg", "用户名为空");
            return map;
        }
        if (StrUtil.isBlank(user.getEmail())) {
            map.put("emailMsg", "邮箱为空");
            return map;
        }
        if (StrUtil.isBlank(user.getPassword())) {
            map.put("passwordMsg", "密码为空");
            return map;
        }

        User user1 = userMapper.selectByUsername(user.getUsername());
        if (user1 != null) {
            map.put("usernameMsg", "用户名已经注册");
            return map;
        }
        User user2 = userMapper.selectByEmail(user.getEmail());
        if (user2 != null) {
            map.put("emailMsg", "邮箱已经注册");
            return map;
        }
        /**
         * 初始化用户
         */
        {
            user.setSalt(CommonUtil.randomString());
            user.setType(0);
            user.setStatus(0);
            user.setCreateTime(new Date());
            user.setPassword(CommonUtil.MD5(user.getPassword() + user.getSalt()));
            user.setActivationCode(CommonUtil.randomString(10));
            user.setHeaderUrl(String.format("https://images.nowcoder.com/head/%dt.png", RandomUtil.randomInt(500)));
        }
        userMapper.insertUser(user);

        Context context = new Context();
        context.setVariable("username", user.getUsername());
        context.setVariable("url", domain + appContext + "/active/" + user.getId() + "/" + user.getActivationCode());
        // TODO　考虑异步消息优化时间
        mailClient.sendMail(user.getEmail(), "注册成功", context);
        // 发送激活邮箱给用户

        return map;
    }

    @Override
    public int activation(int userId, String activeCode) {
        User user = findUserById(userId);
        if (user == null) {
            return Constants.ACTIVE_FAIL;
        }
        if (user.getStatus() == 0 && user.getActivationCode().equals(activeCode)) {

            userMapper.updateStatus(user.getId(), 1);
            return Constants.ACTIVE_SUCCESS;
        }
        if (user.getStatus() == 1) {
            return Constants.ACTIVE_REPLICA;
        }
        return Constants.ACTIVE_FAIL;

    }

    @Override
    public Map<String, Object> login(String username, String password, long duration) {
        Map<String, Object> map = new HashMap<>();
        if (StrUtil.isBlank(username)) {
            map.put("usernameMsg", "用户名不能为空");
            return map;
        }
        if (StrUtil.isBlank(password)) {
            map.put("passwordMsg", "用户密码不能为空");
            return map;
        }
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            map.put("usernameMsg", "用户未注册");
            return map;
        }
        if (user.getStatus() == 0) {
            map.put("usernameMsg", "用户未激活，请尽快激活");
            return map;
        }
        if (!user.getPassword().equalsIgnoreCase(CommonUtil.MD5(password + user.getSalt()))) {
            map.put("passwordMsg", "无效的用户密码");
            return map;
        }

        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setStatus(0);
        loginTicket.setTicket(CommonUtil.randomString(10));
        loginTicket.setExpired(new Date(System.currentTimeMillis() + duration * 1000));
        loginTicketMapper.insertTicket(loginTicket);

        map.put("ticket", loginTicket.getTicket());

        return map;
    }

    @Override
    public void logout(String ticket) {
        loginTicketMapper.updateTicketStatus(ticket, 1);
    }

    @Override
    public void uploadHead(int userId, String headerUrl) {
        userMapper.updateHeader(userId, headerUrl);
    }

    @Override
    public Map<String, Object> updatePassword(String origin, String pass1, String pass2) {
        Map<String, Object> map =new HashMap<>();
        if (StrUtil.isBlank(origin)){
            map.put("originMsg","原密码不能为空");
        }
        User user = (User) hostHolder.getValue();
        if (!CommonUtil.MD5(origin+user.getSalt()).equals(user.getPassword())){
            map.put("originMsg","原密码不正确");
        }
        if (StrUtil.isBlank(pass1)){
            map.put("pass1Msg","新密码不能为空");
        }

        if (StrUtil.isBlank(pass2)||!pass1.equals(pass2)){
            map.put("pass2Msg","前后两次密码不一致");
        }
        userMapper.updatePassword(user.getId(),CommonUtil.MD5(pass1+user.getSalt()));
        return map;
    }
}
