package com.newcoder.community.dao;

import com.newcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: DiscussPostMapper
 * @Description: TODO
 * @author: li
 * @Date: 2022/8/21 12:31 上午
 */
@Mapper
public interface DiscussPostMapper {

    List<DiscussPost> selectDiscussPost(int userId,int offset,int limit);

    /**
     * Param 注解用于给参数加别名，
     * 如果只有一个参数，并且在if中使用，那么必须加别名
     * @param userId
     * @return
     */
    int selectCountRows(@Param("userId") int userId);

    int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectById(int id);
}
