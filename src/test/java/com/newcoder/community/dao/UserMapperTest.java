package com.newcoder.community.dao;

import cn.hutool.core.util.RandomUtil;
import com.newcoder.community.MainApplication;
import com.newcoder.community.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @ClassName: UserMapperTest
 * @Description: TODO
 * @author: li
 * @Date: 2022/8/20 7:11 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class UserMapperTest  {

    @Autowired
    private UserMapper userMapper;

    @Test
    void selectById() {

        long start = System.currentTimeMillis();
        User user = userMapper.selectById(1543);
        System.out.println(System.currentTimeMillis()-start);
        assertNotNull(user);
        System.out.println(user);

    }

    @Test
    void selectByUsername() {
        long start = System.currentTimeMillis();
        User user = userMapper.selectByUsername("yd2trozgh0");
        System.out.println(System.currentTimeMillis()-start);
        assertNotNull(user);
        System.out.println(user);
    }

    @Test
    void selectByEmail() {
        long start = System.currentTimeMillis();
        User user = userMapper.selectByEmail("9433557624@qq.com");
        System.out.println(System.currentTimeMillis()-start);
        assertNotNull(user);
        System.out.println(user);
    }

    @Test
    void insertUser() {
        for (int i = 0; i < 1000; i++) {
            User user = new User();
            user.setUsername(RandomUtil.randomString(10));
            user.setPassword(RandomUtil.randomString(12));
            user.setSalt(RandomUtil.randomString(5));
            user.setEmail(RandomUtil.randomNumbers(10)+"@qq.com");
            user.setActivationCode(RandomUtil.randomString(8));
            user.setType(0);
            user.setStatus(0);
            user.setCreateTime(new Date());
            user.setHeaderUrl(RandomUtil.randomString(10));
            userMapper.insertUser(user);

        }
    }

    @Test
    void updateStatus() {
        int randomInt = RandomUtil.randomInt(2000);
        int i = userMapper.updateStatus(randomInt, 1);
        assertEquals(1, i);
        User user = userMapper.selectById(randomInt);
        assertEquals(1, (int) user.getStatus());
    }

    @Test
    void updateHeader() {
        int randomInt = RandomUtil.randomInt(2000);
        int i = userMapper.updateHeader(randomInt, "http://header.com/pig.png");
        assertEquals(1, i);
        User user = userMapper.selectById(randomInt);
        assertEquals("http://header.com/pig.png", user.getHeaderUrl());
    }

    @Test
    void updatePassword() {
        int randomInt = RandomUtil.randomInt(2000);
        int i = userMapper.updatePassword(randomInt, "123456");

        assertEquals(1, i);
        User user = userMapper.selectById(randomInt);
        assertEquals("123456", user.getPassword());
    }
}