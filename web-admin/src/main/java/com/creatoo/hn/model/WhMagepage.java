package com.creatoo.hn.model;

import javax.persistence.*;

@Table(name = "wh_magepage")
public class WhMagepage {
    /**
     * 页码ID
     */
    @Id
    private String pageid;

    /**
     * 电子杂志ID
     */
    private String pagemageid;

    /**
     * 电子杂志页码图片
     */
    private String pagepic;

    /**
     * 页码标题
     */
    private String pagetitle;

    /**
     * 页码简介
     */
    private String pagedesc;

    /**
     * 页码排序
     */
    private String pageidx;

    /**
     * 页码状态(0:停用 1：启用)
     */
    private Integer pagestate;

    /**
     * 获取页码ID
     *
     * @return pageid - 页码ID
     */
    public String getPageid() {
        return pageid;
    }

    /**
     * 设置页码ID
     *
     * @param pageid 页码ID
     */
    public void setPageid(String pageid) {
        this.pageid = pageid;
    }

    /**
     * 获取电子杂志ID
     *
     * @return pagemageid - 电子杂志ID
     */
    public String getPagemageid() {
        return pagemageid;
    }

    /**
     * 设置电子杂志ID
     *
     * @param pagemageid 电子杂志ID
     */
    public void setPagemageid(String pagemageid) {
        this.pagemageid = pagemageid;
    }

    /**
     * 获取电子杂志页码图片
     *
     * @return pagepic - 电子杂志页码图片
     */
    public String getPagepic() {
        return pagepic;
    }

    /**
     * 设置电子杂志页码图片
     *
     * @param pagepic 电子杂志页码图片
     */
    public void setPagepic(String pagepic) {
        this.pagepic = pagepic;
    }

    /**
     * 获取页码标题
     *
     * @return pagetitle - 页码标题
     */
    public String getPagetitle() {
        return pagetitle;
    }

    /**
     * 设置页码标题
     *
     * @param pagetitle 页码标题
     */
    public void setPagetitle(String pagetitle) {
        this.pagetitle = pagetitle;
    }

    /**
     * 获取页码简介
     *
     * @return pagedesc - 页码简介
     */
    public String getPagedesc() {
        return pagedesc;
    }

    /**
     * 设置页码简介
     *
     * @param pagedesc 页码简介
     */
    public void setPagedesc(String pagedesc) {
        this.pagedesc = pagedesc;
    }

    /**
     * 获取页码排序
     *
     * @return pageidx - 页码排序
     */
    public String getPageidx() {
        return pageidx;
    }

    /**
     * 设置页码排序
     *
     * @param pageidx 页码排序
     */
    public void setPageidx(String pageidx) {
        this.pageidx = pageidx;
    }

    /**
     * 获取页码状态(0:停用 1：启用)
     *
     * @return pagestate - 页码状态(0:停用 1：启用)
     */
    public Integer getPagestate() {
        return pagestate;
    }

    /**
     * 设置页码状态(0:停用 1：启用)
     *
     * @param pagestate 页码状态(0:停用 1：启用)
     */
    public void setPagestate(Integer pagestate) {
        this.pagestate = pagestate;
    }
}