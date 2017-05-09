package com.creatoo.hn.model;

import javax.persistence.*;

@Table(name = "wh_fetch_from")
public class WhFetchFrom {
    /**
     * 采集来源标识
     */
    @Id
    private String fromid;

    /**
     * 采集来源名称
     */
    private String fromname;

    /**
     * 采集数据类型:对应数据库表名
     */
    private String fromfetchtype;

    /**
     * 采集数据列表页URL,分页值可以用${page}来代替.
     */
    private String fromlisturl;

    /**
     * 采集数据列表页URL中分页变量的初始值.
     */
    private String fromlistvalinitval;

    /**
     * 采集数据列表页URL中分页变量的递增值
     */
    private String fromlistvaladdval;

    /**
     * 如何查找列表页中的每个元素
     */
    private String fromlistitemmatch;

    /**
     * 如何查找列表页每个元素的详细页地址
     */
    private String frominfoaddrmatch;

    /**
     * 在列表页如何查找元素唯一主键
     */
    private String fromitemidmatch;

    /**
     * 采集来源状态:0-无效;1-有效.
     */
    private Integer fromstate;

    /**
     * 获取采集来源标识
     *
     * @return fromid - 采集来源标识
     */
    public String getFromid() {
        return fromid;
    }

    /**
     * 设置采集来源标识
     *
     * @param fromid 采集来源标识
     */
    public void setFromid(String fromid) {
        this.fromid = fromid;
    }

    /**
     * 获取采集来源名称
     *
     * @return fromname - 采集来源名称
     */
    public String getFromname() {
        return fromname;
    }

    /**
     * 设置采集来源名称
     *
     * @param fromname 采集来源名称
     */
    public void setFromname(String fromname) {
        this.fromname = fromname;
    }

    /**
     * 获取采集数据类型:对应数据库表名
     *
     * @return fromfetchtype - 采集数据类型:对应数据库表名
     */
    public String getFromfetchtype() {
        return fromfetchtype;
    }

    /**
     * 设置采集数据类型:对应数据库表名
     *
     * @param fromfetchtype 采集数据类型:对应数据库表名
     */
    public void setFromfetchtype(String fromfetchtype) {
        this.fromfetchtype = fromfetchtype;
    }

    /**
     * 获取采集数据列表页URL,分页值可以用${page}来代替.
     *
     * @return fromlisturl - 采集数据列表页URL,分页值可以用${page}来代替.
     */
    public String getFromlisturl() {
        return fromlisturl;
    }

    /**
     * 设置采集数据列表页URL,分页值可以用${page}来代替.
     *
     * @param fromlisturl 采集数据列表页URL,分页值可以用${page}来代替.
     */
    public void setFromlisturl(String fromlisturl) {
        this.fromlisturl = fromlisturl;
    }

    /**
     * 获取采集数据列表页URL中分页变量的初始值.
     *
     * @return fromlistvalinitval - 采集数据列表页URL中分页变量的初始值.
     */
    public String getFromlistvalinitval() {
        return fromlistvalinitval;
    }

    /**
     * 设置采集数据列表页URL中分页变量的初始值.
     *
     * @param fromlistvalinitval 采集数据列表页URL中分页变量的初始值.
     */
    public void setFromlistvalinitval(String fromlistvalinitval) {
        this.fromlistvalinitval = fromlistvalinitval;
    }

    /**
     * 获取采集数据列表页URL中分页变量的递增值
     *
     * @return fromlistvaladdval - 采集数据列表页URL中分页变量的递增值
     */
    public String getFromlistvaladdval() {
        return fromlistvaladdval;
    }

    /**
     * 设置采集数据列表页URL中分页变量的递增值
     *
     * @param fromlistvaladdval 采集数据列表页URL中分页变量的递增值
     */
    public void setFromlistvaladdval(String fromlistvaladdval) {
        this.fromlistvaladdval = fromlistvaladdval;
    }

    /**
     * 获取如何查找列表页中的每个元素
     *
     * @return fromlistitemmatch - 如何查找列表页中的每个元素
     */
    public String getFromlistitemmatch() {
        return fromlistitemmatch;
    }

    /**
     * 设置如何查找列表页中的每个元素
     *
     * @param fromlistitemmatch 如何查找列表页中的每个元素
     */
    public void setFromlistitemmatch(String fromlistitemmatch) {
        this.fromlistitemmatch = fromlistitemmatch;
    }

    /**
     * 获取如何查找列表页每个元素的详细页地址
     *
     * @return frominfoaddrmatch - 如何查找列表页每个元素的详细页地址
     */
    public String getFrominfoaddrmatch() {
        return frominfoaddrmatch;
    }

    /**
     * 设置如何查找列表页每个元素的详细页地址
     *
     * @param frominfoaddrmatch 如何查找列表页每个元素的详细页地址
     */
    public void setFrominfoaddrmatch(String frominfoaddrmatch) {
        this.frominfoaddrmatch = frominfoaddrmatch;
    }

    /**
     * 获取在列表页如何查找元素唯一主键
     *
     * @return fromitemidmatch - 在列表页如何查找元素唯一主键
     */
    public String getFromitemidmatch() {
        return fromitemidmatch;
    }

    /**
     * 设置在列表页如何查找元素唯一主键
     *
     * @param fromitemidmatch 在列表页如何查找元素唯一主键
     */
    public void setFromitemidmatch(String fromitemidmatch) {
        this.fromitemidmatch = fromitemidmatch;
    }

    /**
     * 获取采集来源状态:0-无效;1-有效.
     *
     * @return fromstate - 采集来源状态:0-无效;1-有效.
     */
    public Integer getFromstate() {
        return fromstate;
    }

    /**
     * 设置采集来源状态:0-无效;1-有效.
     *
     * @param fromstate 采集来源状态:0-无效;1-有效.
     */
    public void setFromstate(Integer fromstate) {
        this.fromstate = fromstate;
    }
}