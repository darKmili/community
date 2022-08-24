package com.newcoder.community.entity;

import lombok.Data;

import java.util.Date;

/**
 * 评论实体
 *
 * @author： leon
 * @description：
 * @date： 2022/8/24
 * @version: 1.0
 */
@Data
public class Comment {
    private Integer id;
    private Integer userId;
    private Integer entityType;
    private Integer entityId;
    private Integer target;
    private String content;
    private Integer status;
    private Date  createTime;
}
