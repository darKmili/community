package com.newcoder.community.util;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;

/**
 * 通用工具类
 *
 * @author： leon
 * @description：
 * @date： 2022/8/21
 * @version: 1.0
 */
public class CommonUtil {

    public static String randomString(int length){
        return RandomUtil.randomString(length);
    }
    public static String randomString(){
        return RandomUtil.randomString(5);
    }

    public static String MD5(String s){
        MD5 md5 = MD5.create();
        return md5.digestHex(s);
    }
}
