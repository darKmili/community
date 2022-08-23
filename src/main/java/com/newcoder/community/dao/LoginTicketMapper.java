package com.newcoder.community.dao;

import com.newcoder.community.entity.LoginTicket;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： leon
 * @description：
 * @date： 2022/8/22
 * @version: 1.0
 */
@Mapper
public interface LoginTicketMapper {
    int insertTicket(LoginTicket loginTicket);

    LoginTicket selectByTicket(String ticket);

    int updateTicketStatus(String ticket, int status);
}
