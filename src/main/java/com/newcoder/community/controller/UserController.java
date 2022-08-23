package com.newcoder.community.controller;

import cn.hutool.core.util.StrUtil;
import com.newcoder.community.annotation.LoginChecked;
import com.newcoder.community.dao.LoginTicketMapper;
import com.newcoder.community.entity.User;
import com.newcoder.community.service.UserService;
import com.newcoder.community.util.CommonUtil;
import com.newcoder.community.util.CookieUtil;
import com.newcoder.community.util.HostHolder;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： leon
 * @description：
 * @date： 2022/8/23
 * @version: 1.0
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Value("${app.upload-path}")
    private String uploadPath;
    @Value("${app.domain}")
    private String domain;
    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LoginTicketMapper loginTicketMapper;


    @GetMapping("/setting")
    @LoginChecked
    public String getSettingPage(){
        return "/site/setting";
    }

    @PostMapping("/uploadImage")
    @LoginChecked
    public String uploadImage(MultipartFile headImage, Model model){
        if (headImage == null){
            model.addAttribute("error","还未上传文件");
            return "/site/setting";
        }
        String originalFilename = headImage.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf('.'));
        if (StrUtil.isBlank(suffix)||(!".png".equals(suffix)&&!".jpg".equals(suffix))){
            model.addAttribute("uploadMsg","上传文件格式错误");
            return "/site/setting";
        }

        String newFileName = CommonUtil.randomString(10) + suffix;

        File dir = new File(uploadPath);

        if (!dir.exists()){
            dir.mkdirs();
        }

        File dest = new File(dir.getAbsoluteFile()+"/"+newFileName);

        try {
            headImage.transferTo(dest);
        } catch (IOException e) {
            log.error("上传文件失败");
            e.printStackTrace();
        }

        String headerUrl = domain+contextPath+"/user/header/"+newFileName;
        User user = (User)hostHolder.getValue();
        userService.uploadHead(user.getId(),headerUrl);
        model.addAttribute("uploadMsg","头像修改成功");

        return "/site/setting";
    }

    @GetMapping("/header/{filepath}")
    public void getHeaderImage(HttpServletResponse response,@PathVariable("filepath") String filepath){

        if (StrUtil.isBlank(filepath)){
            log.error("头像文件名为空");
            throw new IllegalArgumentException("头像文件名为空");
        }
        response.setContentType("image/"+filepath.substring(filepath.lastIndexOf('.')+1));
        File file = new File(uploadPath+"/"+filepath);

        try (FileInputStream inputStream = new FileInputStream(file);
             ServletOutputStream outputStream = response.getOutputStream();
             )
        {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b=inputStream.read(buffer))!=-1){
                outputStream.write(buffer,0,b);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/change/pass")
    @LoginChecked
    public String changePassword(String origin, String pass1, String pass2, Model model, HttpServletRequest httpServletRequest){

        Map<String, Object> map = userService.updatePassword(origin, pass1, pass2);
        if (map!=null&& !map.isEmpty()){
            model.addAllAttributes(map);
            return "/site/setting";
        }

        String ticket = CookieUtil.getCookieValue(httpServletRequest, "ticket");
        userService.logout(ticket);

        model.addAttribute("message","密码已经修改成功，请重新登录");
        model.addAttribute("target","/login");
        return "/site/operate-result";

    }
}
