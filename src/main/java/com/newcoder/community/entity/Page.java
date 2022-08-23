package com.newcoder.community.entity;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： leon
 * @description：
 * @date： 2022/8/21
 * @version: 1.0
 */
public class Page {

    /**
     * 当前页码
     */
    private int current = 1;
    /**
     * 分页行数
     */
    private int limit = 10;
    /**
     * 总行数
     */
    private int rows;
    /**
     * 查询路径(复用分页链接)
     */
    private String path;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if (current > 0) {
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit > 0) {
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取总页数
     * @return
     */
    public int getTotal() {
        if (rows % limit == 0) {
            return rows / limit;
        }
        return rows/limit + 1;
    }

    /**
     * 获取当时起始行
     * @return
     */
    public int getOffset(){
        return (current-1)*limit;
    }

    /**
     * 获取起始页
     * @return
     */
    public int getFrom(){
        return current-2>0?current-2:1;
    }

    /**
     * 获取终止页
     * @return
     */
    public int getTo(){
        return Math.min(current + 2, getTotal());
    }

}
