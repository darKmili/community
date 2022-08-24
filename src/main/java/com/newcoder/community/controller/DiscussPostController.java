package com.newcoder.community.controller;

import com.newcoder.community.dao.CommentMapper;
import com.newcoder.community.dao.DiscussPostMapper;
import com.newcoder.community.entity.DiscussPost;
import com.newcoder.community.entity.Page;
import com.newcoder.community.entity.User;
import com.newcoder.community.service.DiscussPostService;
import com.newcoder.community.service.UserService;
import com.newcoder.community.util.Constants;
import com.newcoder.community.util.HostHolder;
import com.newcoder.community.util.ResultJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： leon
 * @description：
 * @date： 2022/8/23
 * @version: 1.0
 */
@Controller
@RequestMapping("/discuss")
public class DiscussPostController {
    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentMapper commentMapper;

    @PostMapping("/upload")
    @ResponseBody
    public ResultJSON uploadDiscuss(String title,String content){
        final User user = (User)hostHolder.getValue();
        if (user == null){
            return new ResultJSON(403,"没有权限访问");
        }
        final DiscussPost discussPost = new DiscussPost();
        discussPost.setUserId(user.getId());
        discussPost.setTitle(title);
        discussPost.setContent(content);
        discussPost.setCreateTime(new Date());
        discussPost.setStatus(0);
        discussPost.setType(0);
        discussPost.setCommentCount(0);
        discussPostService.addDiscussPost(discussPost);
        return new ResultJSON(200,"上传帖子成功");
    }

    @GetMapping("/details/{id}")
    public String getDiscussPost(@PathVariable("id") int id, Model model, Page page){
        final DiscussPost discussPost = discussPostService.findDiscussPostById(id);
        model.addAttribute("discussPost",discussPost);
        final User user = userService.findUserById(discussPost.getUserId());
        model.addAttribute("user",user);

        /**
         * 找到当前帖子下面的所有评论，并且给他分页
         * 找到每条评论里面的回复
         */
        commentMapper.selectCommentByEntityTypeAndEntityId(Constants.COMMENT_DISCUSS_TYPE,discussPost.getId(), page.getOffset(), page.getLimit());

        return "/site/discuss-detail";
    }
}
