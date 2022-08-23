package com.newcoder.community.service;

import com.newcoder.community.entity.User;

import java.util.Map;

/**
 * @ClassName: UserService
 * @Description: TODO
 * @author: li
 * @Date: 2022/8/21 1:25 上午
 */
public interface UserService {
    User findUserById(int userId);

    Map<String, Object> register(User user);

    int activation(int userId, String activeCode);

    Map<String, Object> login(String username, String password, long duration);

    void logout(String ticket);

    void uploadHead(int userId, String headerUrl);

    Map<String, Object> updatePassword(String origin, String pass1, String pass2);
}
