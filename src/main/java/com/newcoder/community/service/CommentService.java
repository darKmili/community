package com.newcoder.community.service;

import com.newcoder.community.entity.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： leon
 * @description：
 * @date： 2022/8/24
 * @version: 1.0
 */
public interface CommentService {
    List<Comment> findCommentByEntityTypeAndEntityId(int entityType, int entityId,  int offset,  int limit);

    int findCountByEntityTypeAndEntityId(int entityType, int entityId);

}
