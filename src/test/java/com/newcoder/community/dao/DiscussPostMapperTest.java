package com.newcoder.community.dao;

import cn.hutool.core.util.RandomUtil;
import com.newcoder.community.entity.DiscussPost;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @ClassName: DiscussPostMapperTest
 * @Description: TODO
 * @author: li
 * @Date: 2022/8/21 1:00 上午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class DiscussPostMapperTest {
    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Test
    void selectDiscussPost() {
        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPost(77, 0, 100);
        discussPosts.forEach(System.out::println);
    }

    @Test
    void selectCountRows() {
        int i = discussPostMapper.selectCountRows(77);
        System.out.println(i);
    }

    @Test
    void insertDiscussPost() {

        for (int i = 0; i < 200; i++) {
            int user_id = RandomUtil.randomInt(200);
            DiscussPost discussPost = new DiscussPost();
            discussPost.setUserId(user_id);
            discussPost.setTitle("世界上只有一个中国");
            discussPost.setContent("人之初性本善，清明上河图，处处闻啼鸟");
            discussPost.setCreateTime(new Date());
            discussPost.setScore(RandomUtil.randomDouble(100));
            discussPost.setType(0);
            discussPost.setStatus(0);
            discussPostMapper.insertDiscussPost(discussPost);
        }
    }
}