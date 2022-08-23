package com.newcoder.community.dao;

import com.newcoder.community.entity.LoginTicket;
import com.newcoder.community.util.CommonUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： leon
 * @description：
 * @date： 2022/8/22
 * @version: 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class LoginTicketMapperTest {
    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Test
    void insertTicket() {
        for (int i = 0; i < 100; i++) {
            LoginTicket loginTicket = new LoginTicket();
            loginTicket.setTicket(CommonUtil.randomString(6));
            loginTicket.setStatus(0);
            loginTicket.setUserId(new Random().nextInt(100));
            loginTicket.setExpired(new Date());
            loginTicketMapper.insertTicket(loginTicket);
        }
    }

    @Test
    void selectByTicket() {
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("23ybb4");
        System.out.println(loginTicket);
    }

    @Test
    void updateTicket() {
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("ghd462");
        if (loginTicket.getExpired().before(new Date())){
            loginTicketMapper.updateTicketStatus(loginTicket.getTicket(), 1);
        }
    }
}