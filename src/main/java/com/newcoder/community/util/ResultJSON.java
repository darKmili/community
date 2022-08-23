package com.newcoder.community.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： leon
 * @description：
 * @date： 2022/8/23
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultJSON {

    private int code;
    private String message;
    private Object data;


    public ResultJSON(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }
}
