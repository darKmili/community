package com.newcoder.community.controller;

import cn.hutool.core.util.StrUtil;
import com.google.code.kaptcha.Producer;
import com.newcoder.community.entity.User;
import com.newcoder.community.service.UserService;
import com.newcoder.community.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： leon
 * @description：
 * @date： 2022/8/21
 * @version: 1.0
 */
@Controller
public class LoginController  {
    @Autowired
    private UserService userService;

    @Autowired
    private Producer producer;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @GetMapping("/register")
    public String getRegisterPage() {
        return "/site/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String getRegisterPage(User user, Model model) {

        Map<String, Object> objectMap = userService.register(user);
        // 注册成功了,跳转到过度页面，然后在跳转主页
        if (objectMap == null || objectMap.isEmpty()) {
            model.addAttribute("message", "您的账号已经注册成功,请尽快激活!");
            model.addAttribute("target", "/login");
            return "/site/operate-result";
        }
        // 注册失败了，跳转到注册页面，并且展示错误信息
        model.addAllAttributes(objectMap);

        return "/site/register";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "/site/login";
    }

    @GetMapping("/active/{userId}/{activationCode}")
    public String activation(Model model, @PathVariable("userId") Integer userId, @PathVariable("activationCode") String activationCode) {
        int activation = userService.activation(userId, activationCode);
        if (activation == Constants.ACTIVE_SUCCESS) {
            model.addAttribute("message", "您的账号已经激活成功,可以正常使用了!");
            model.addAttribute("target", "/login");
        } else if (activation == Constants.ACTIVE_REPLICA) {
            model.addAttribute("message", "您的账号已经激活,无需重复激活");
            model.addAttribute("target", "/login");

        } else {
            model.addAttribute("message", "您的账号已经激活成功,可以正常使用了!");
            model.addAttribute("target", "/index");
        }

        return "/site/operate-result";
    }

    @GetMapping("/captcha-image")
    public void getVerifyCode(HttpServletResponse response, HttpServletRequest request){
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        String text = producer.createText();
        HttpSession session = request.getSession();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY,text);
        BufferedImage image = producer.createImage(text);

        try (OutputStream outputStream = response.getOutputStream()){
            ImageIO.write(image,"png",outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @PostMapping("/login")
    public String login(String username,String password,String code,boolean remember,HttpServletResponse response ,HttpSession httpSession,Model model){
        String saveCode = (String) httpSession.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (StrUtil.isBlank(code)||StrUtil.isBlank(saveCode)||!saveCode.equals(code)){
            model.addAttribute("codeMsg","验证码错误");
            return "/site/login";
        }
        long duration = remember?Constants.REMEMBER_DURATION:Constants.NO_REMEMBER_DURATION;
        Map<String, Object> map = userService.login(username, password, duration);
        // 登录成功
        if (map.containsKey("ticket")){
            Cookie cookie = new Cookie("ticket", (String) map.get("ticket"));
            cookie.setMaxAge((int) duration);
            cookie.setPath(contextPath);
            response.addCookie(cookie);
            return "redirect:/index";
        }else {
            // 登录失败
            model.addAllAttributes(map);
            return "/site/login";
        }

    }
    @GetMapping("/logout")
    public String logout(@CookieValue("ticket") String ticket){

        userService.logout(ticket);

        return "redirect:/login";
    }

}
