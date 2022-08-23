package com.newcoder.community.util;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.context.Context;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： leon
 * @description：
 * @date： 2022/8/21
 * @version: 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class MailClientTest {
    @Autowired
    private MailClient mailClient;

    @Test
    void sendMail() {
        Context context = new Context(Locale.CHINA);
        context.setVariable("username","张三");
        context.setVariable("text","hehehe");
        mailClient.sendMail("1227642494@qq.com","测试邮件",context);
    }
}