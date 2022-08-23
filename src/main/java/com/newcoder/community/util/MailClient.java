package com.newcoder.community.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

/**
 * 邮件发送工具类
 *
 * @author： leon
 * @description：
 * @date： 2022/8/21
 * @version: 1.0
 */
@Component
@Slf4j
public class MailClient {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    public void sendMail(String to, String subject,  Context context,String path) {



        if (path==null){
            path = "/mail/activation";
        }
        String content = templateEngine.process(path, context);


        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.addTo(to);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setText(content,true);
            mimeMessageHelper.setSubject(subject);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e){
            log.info(e.getMessage());
            log.error("邮件发送失败:"+e.getMessage());
        }
    }

    public void sendMail(String to, String subject,  Context context){
        sendMail(to,subject,context,null);
    }

}
