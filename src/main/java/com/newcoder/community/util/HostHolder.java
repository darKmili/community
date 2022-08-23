package com.newcoder.community.util;

import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： leon
 * @description：
 * @date： 2022/8/23
 * @version: 1.0
 */
@Component
public class HostHolder {
    private ThreadLocal<Object> threadLocal = new ThreadLocal<>();

    public Object getValue(){
        return threadLocal.get();
    }

    public void setValue(Object key){
        threadLocal.set(key);
    }

    public void removeValue(){
        threadLocal.remove();
    }


}
