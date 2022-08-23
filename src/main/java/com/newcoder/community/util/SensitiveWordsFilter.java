package com.newcoder.community.util;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 敏感词过滤器
 *
 * @author： leon
 * @description：
 * @date： 2022/8/23
 * @version: 1.0
 */
@Component
@Slf4j
public class SensitiveWordsFilter {

    private class TrieNode{
        private boolean isSensitive = false;
        private Map<Character,TrieNode> children = new HashMap<>();

        public boolean isSensitive() {
            return isSensitive;
        }

        public void setSensitive(boolean sensitive) {
            isSensitive = sensitive;
        }

        public void addSubNode(Character c,TrieNode subNode){
            children.put(c,subNode);
        }

        public TrieNode getSubNode(Character c){
           return children.get(c);
        }
    }

    private static final String REPLACE_WORD="***";
    private TrieNode root = new TrieNode();

    @PostConstruct
    public void init(){
        try(InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        ){
            String keyword;
            while ((keyword =bufferedReader.readLine())!=null){
                this.addWord(keyword);
            }
            System.out.println("init");
        }catch (IOException e){
            log.error("敏感词过滤工具初始化失败");
            e.printStackTrace();
        }
    }

    private void addWord(String word){
        if (StrUtil.isBlank(word)){
            return;
        }
        TrieNode move = this.root;
        for (int i = 0; i < word.toCharArray().length; i++) {
             char c = word.charAt(i);
            TrieNode tmp = move.getSubNode(c);
            if (tmp == null){
                tmp = new TrieNode();
                move.addSubNode(c,tmp);
            }
            move = tmp;
        }
        move.isSensitive = true;
    }

    /**
     * 只有英文字母，数字，中文为有效数字，其他为无效。在判断时无效字母忽略
     * @param c
     * @return
     */
    private boolean isChineseOrChar(Character c){
        return Character.isLetterOrDigit(c)||isChineseCharacter(c);
    }

    /**
     * 判断一个字符是否是中文,根据字节码判断
     * */
    private  boolean isChineseCharacter(char c) {
        return c<0x2e80||c>0x9fff;
    }

    /**
     * 过滤敏感词
     * @param text
     * @return
     */
    public String filterSensitiveWord(String text){
        if (StrUtil.isBlank(text)){
            return "";
        }
        // 第一个指针，用于指向字典树
        TrieNode move = this.root;
        // 具体判断的就是begin和position区间内的单词是否是敏感词
        // 第二个指针，用于指向开始结点
        int begin = 0;
        // 第三个指针，用于指向移动结点
        int position = 0;
        StringBuilder sb = new StringBuilder();
        while (begin<text.length()){
            if (position<text.length()){
                if (!isChineseOrChar(text.charAt(position))){
                    position++;
                    continue;
                }
                // 获取当前值，查看当前单词是否在字典树上
                char c = text.charAt(position);
                TrieNode subNode = move.getSubNode(c);
                if (subNode == null){
                    // 当前不存在这个值
                    do {
                        sb.append(text.charAt(begin));
                        begin++;
                    } while (begin<text.length()&&!isChineseOrChar(text.charAt(begin)));

                    position = begin;
                    move = root;
                }else if (subNode.isSensitive()){
                    // 当前值是敏感词
                    sb.append(REPLACE_WORD);
                    begin = ++position;
                    while (begin<text.length()&&!isChineseOrChar(text.charAt(begin))){
                        sb.append(text.charAt(begin));
                        begin++;
                    }
                    position = begin;
                    move = root;
                }else {
                    // 当前值不是敏感词
                    move = subNode;
                    position++;
                }

                //
            }else {
                begin++;
                position=begin;
                move = root;
            }
        }

        return sb.toString();
    }


}
