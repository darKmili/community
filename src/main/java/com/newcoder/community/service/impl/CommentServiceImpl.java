package com.newcoder.community.service.impl;

import com.newcoder.community.dao.CommentMapper;
import com.newcoder.community.entity.Comment;
import com.newcoder.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： leon
 * @description：
 * @date： 2022/8/24
 * @version: 1.0
 */
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> findCommentByEntityTypeAndEntityId(int entityType, int entityId, int offset, int limit) {
        return commentMapper.selectCommentByEntityTypeAndEntityId(entityType,entityId,offset,limit);
    }

    @Override
    public int findCountByEntityTypeAndEntityId(int entityType, int entityId) {
        return commentMapper.countCommentRowsByType(entityType,entityId);
    }
}
