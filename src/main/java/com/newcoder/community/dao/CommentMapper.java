package com.newcoder.community.dao;

import com.newcoder.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论查询类
 *
 * @author： leon
 * @description：
 * @date： 2022/8/24
 * @version: 1.0
 */
@Mapper
public interface CommentMapper {
    int countCommentRowsByType(@Param("entityType") int entityType,@Param("entityId") int entityId);

    List<Comment> selectCommentByEntityTypeAndEntityId(@Param("entityType")int entityType,@Param("entityId")int entityId,@Param("offset") int offset,@Param("limit") int limit);
}
