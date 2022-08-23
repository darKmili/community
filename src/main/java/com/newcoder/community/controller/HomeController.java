package com.newcoder.community.controller;

import com.newcoder.community.entity.DiscussPost;
import com.newcoder.community.entity.Page;
import com.newcoder.community.entity.User;
import com.newcoder.community.service.DiscussPostService;
import com.newcoder.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author： leon
 * @description：
 * @date： 2022/8/21
 * @version: 1.0
 */
@Controller
public class HomeController {
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String home(Model model, Page page){
        //方法调用前。Spring MVC会自动实例化Model和Page，并将Page注入Model
        //所以在thymeleaf中可以直接访问Page对象中的数据
        page.setRows(discussPostService.findCountDiscussPost(0));
        page.setPath("/index");

        List<Map<String,Object>> discussPosts = new ArrayList<>();
        List<DiscussPost> discussPost = discussPostService.findDiscussPost(0, page.getOffset(), page.getLimit());
        if (discussPost!=null){
            for (DiscussPost post : discussPost) {
                User user = userService.findUserById(post.getUserId());
                Map<String,Object> map = new HashMap<>();
                map.putIfAbsent("post",post);
                map.putIfAbsent("user",user);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts",discussPosts);
        return "/index";
    }

}
