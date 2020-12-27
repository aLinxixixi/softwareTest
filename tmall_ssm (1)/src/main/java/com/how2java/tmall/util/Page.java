package com.how2java.tmall.util;

public class Page {

    private int start; //开始页数
    private int count; //每页显示条数
    private int total; //总条数
    private String param; //参数

    private static final int defaultCount = 5;

    public Page(){
        count = defaultCount;
    }
    public Page(int start, int count) {
        this();
        this.start = start;
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public static int getDefaultCount() {
        return defaultCount;
    }

    //根据 每页显示的数量count以及总共有多少条数据total，计算出总共有多少页
    public int getTotalPage(){
        int totalPage;
        if (total % count == 0)
            totalPage = total / count;
        else
            totalPage = total / count + 1;

        if (totalPage == 0)
            totalPage = 1;
        return totalPage;
    }

//    getLast 计算出最后一页开始的数值的id是多少
// 计算出最后一页的第一个条目在数据库中所有数据中的索引。这个是用于和start进行比较，来判断当前页面是否是最后一页。

    public int getLast(){
        int last;

        if (total % count == 0)
            last = total - count;
        else
            last = total - total % count;

        last = last < 0 ? 0 : last;
        return last;

    }
//    isHasPreviouse 判断是否有前一页
    public boolean isHasPreviouse(){
        if (start == 0)
            return false;
        return true;
    }
//    isHasNext 判断是否有后一页
    public boolean isHasNext(){
        if (start == getLast())
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Page{" +
                "start=" + start +
                ", count=" + count +
                ", total=" + total +
                ", param='" + param + '\'' +
                '}';
    }
}
