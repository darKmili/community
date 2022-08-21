package com.newcoder.community.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: DiscussPost
 * @Description: TODO
 * @author: li
 * @Date: 2022/8/21 12:23 上午
 */
@Data
@Alias("discussPost")
public class DiscussPost implements Serializable {
    private Integer id;
    private Integer userId;
    private String title;
    private String content;
    private Integer type;
    private Integer status;
    private Date createTime;
    private Double score;

}
