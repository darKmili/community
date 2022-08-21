package com.newcoder.community.service.impl;

import com.newcoder.community.dao.DiscussPostMapper;
import com.newcoder.community.entity.DiscussPost;
import com.newcoder.community.service.DiscussPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<DiscussPost> findDiscussPost(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPost(userId,offset,limit);
    }

    @Override
    public int findCountDiscussPost(int userId) {
        return discussPostMapper.selectCountRows(userId);
    }
}
