package com.newcoder.community.util;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： leon
 * @description：
 * @date： 2022/8/23
 * @version: 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class SensitiveWordsFilterTest {
    @Autowired
    private SensitiveWordsFilter sensitiveWordsFilter;

    @Test
    void filterSensitiveWord() {
        final String s = sensitiveWordsFilter.filterSensitiveWord("小日dd本人");
        System.out.println(s);
    }
}