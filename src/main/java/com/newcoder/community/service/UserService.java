package com.newcoder.community.service;

import com.newcoder.community.entity.User;

/**
 * @ClassName: UserService
 * @Description: TODO
 * @author: li
 * @Date: 2022/8/21 1:25 上午
 */
public interface UserService {
    User findUserById(int userId);
}
