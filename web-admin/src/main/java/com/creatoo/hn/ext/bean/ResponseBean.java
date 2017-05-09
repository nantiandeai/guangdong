package com.creatoo.hn.ext.bean;

import java.util.List;

/**
 *
 * Created by Administrator on 2017/3/16.
 */
public class ResponseBean {

    /**
     * 标识失败的常量
     */
    public static final String FAIL = "0";
    

    /**
     * 返回的成功信息 1、成功 其它表示失败
     */
    private String success = "1";

    /**
     * 返回的错误信息
     */
    private String errormsg;

    /**
     * 返回的数据list
     */
    private List rows;

    /**
     * 返回的数据总条数
     */
    private long total;

    /**
     * 当前页
     */
    private int page;

    /**
     * 每页最多记录数
     */
    private int pageSize;

    /**]
     * ajax返回的数据
     * @return
     */
    private Object data;


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
