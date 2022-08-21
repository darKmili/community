package com.newcoder.community.service;

import com.newcoder.community.entity.DiscussPost;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: DIscussPostService
 * @Description: TODO
 * @author: li
 * @Date: 2022/8/21 1:25 上午
 */
public interface DiscussPostService {
    List<DiscussPost> findDiscussPost(int userId,int offset,int limit);

    int findCountDiscussPost(int userId);
}
