package com.newcoder.community.dao;

import com.newcoder.community.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName: UserMapper
 * @Description: TODO
 * @author: li
 * @Date: 2022/8/20 6:11 下午
 */
@Mapper
public interface UserMapper {
    User selectById(Integer id);

    User selectByUsername(String username);

    User selectByEmail(String email);

    int insertUser(User user);

    int updateStatus(int id,int status);

    int updateHeader(int id,String headerUrl);

    int updatePassword(int id,String password);
}
