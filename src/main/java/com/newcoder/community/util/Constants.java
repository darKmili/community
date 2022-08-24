package com.newcoder.community.util;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： leon
 * @description：
 * @date： 2022/8/22
 * @version: 1.0
 */
public class Constants {
    /**
     * 激活成功
     */
    public static final int ACTIVE_SUCCESS = 0;
    /**
     * 重复激活
     */
    public static final int ACTIVE_REPLICA = 1;
    /**
     * 激活失败
     */
    public static final int ACTIVE_FAIL = 2;
    public static final String KAPTCHA_SESSION_KEY = "KAPTCHA_code";
    public static final long REMEMBER_DURATION = 60*60*24*10;
    public static final long NO_REMEMBER_DURATION = 60*60*24;
    /**
     * 评论类型-帖子
     */
    public static final int COMMENT_DISCUSS_TYPE=1;
    /**
     * 评论类型-回复
     */
    public static final int COMMENT_REPLY_TYPE=2;
}
