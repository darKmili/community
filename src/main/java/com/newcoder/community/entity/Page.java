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
     * 当前页码，默认当前页码为首页,应该由前端传入，没有传入默认为1
     */
    private int current = 1;
    /**
     * 分页行数,默认一页10行，由前端传入，没有传入默认为10
     */
    private int limit = 10;
    /**
     * 总行数，需要从数据库传入，当传入时需要重新跳转该对象
     */
    private int rows;
    /**
     * 默认偏移量
     */
    private int offset = 0;
    /**
     * 总页数
     */
    private int total;
    /**
     * 前两页
     */
    private int from;
    /**
     * 后两页
     */
    private int to;
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
        if (rows > 0) {
            this.rows = rows;
        }
        // 总页数
        if (rows % limit == 0) {
            setTotal(rows / limit);
        } else {
            setTotal(rows / limit + 1);
        }
        // 当前页数是否合法
        if (getCurrent()<1){
            setCurrent(1);
        }else if (getCurrent()>getTotal()){
            setCurrent(getTotal());
        }
        // 设置偏移量
        setOffset((getCurrent()-1)*limit);
        // 设置前两页后两页
        setFrom(Math.max(getCurrent()-2,1));
        setTo(Math.min(getCurrent()+2,getTotal()));
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取总页数
     *
     * @return
     */
    public int getTotal() {
        return total;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }


    /**
     * 获取当时起始行
     *
     * @return
     */
    public int getOffset() {

        return offset;
    }

    /**
     * 获取起始页
     *
     * @return
     */
    public int getFrom() {
        return from;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    /**
     * 获取终止页
     *
     * @return
     */
    public int getTo() {
        return to;
    }

}
