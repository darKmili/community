package com.newcoder.community.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: User
 * @Description: TODO
 * @author: li
 * @Date: 2022/8/20 6:06 下午
 */
@Data
@Alias("user")
public class User implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private String salt;
    private String email;
    private Integer type;
    private Integer status;
    private String activationCode;
    private String headerUrl;
    private Date createTime;
}
