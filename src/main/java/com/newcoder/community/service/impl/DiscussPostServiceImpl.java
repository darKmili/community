package com.newcoder.community.service.impl;

import com.newcoder.community.dao.DiscussPostMapper;
import com.newcoder.community.entity.DiscussPost;
import com.newcoder.community.service.DiscussPostService;
import com.newcoder.community.util.SensitiveWordsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import org.unbescape.html.HtmlEscape;

import java.util.List;

/**
 * @ClassName: DiscussPostServiceImpl
 * @Description: TODO
 * @author: li
 * @Date: 2022/8/21 1:26 上午
 */
@Service
public class DiscussPostServiceImpl implements DiscussPostService {
    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private SensitiveWordsFilter sensitiveWordsFilter;

    @Override
    public List<DiscussPost> findDiscussPost(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPost(userId,offset,limit);
    }

    @Override
    public int findCountDiscussPost(int userId) {
        return discussPostMapper.selectCountRows(userId);
    }

    @Override
    public int addDiscussPost(DiscussPost discussPost) {
        if (discussPost==null){
            return 0;
        }
        // 转义字符串中的HTML标签
        discussPost.setTitle(HtmlUtils.htmlEscape(discussPost.getTitle()));
        discussPost.setContent(HtmlUtils.htmlEscape(discussPost.getContent()));

        // 过滤敏感词
        discussPost.setTitle(sensitiveWordsFilter.filterSensitiveWord(discussPost.getTitle()));
        discussPost.setContent(sensitiveWordsFilter.filterSensitiveWord(discussPost.getContent()));


        return discussPostMapper.insertDiscussPost(discussPost);
    }
}
