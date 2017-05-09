package com.creatoo.hn.model;

import javax.persistence.*;

@Table(name = "wh_traitmtime")
public class WhTraitmtime {
    /**
     * 课时标识
     */
    @Id
    private String timeid;

    /**
     * 培训标识
     */
    private String traitmid;

    /**
     * 课时日期
     */
    private String tradate;

    /**
     * 开始时间
     */
    private String stime;

    /**
     * 结束时间
     */
    private String etime;

    /**
     * 标题
     */
    private String dtitle;

    /**
     * 获取课时标识
     *
     * @return timeid - 课时标识
     */
    public String getTimeid() {
        return timeid;
    }

    /**
     * 设置课时标识
     *
     * @param timeid 课时标识
     */
    public void setTimeid(String timeid) {
        this.timeid = timeid;
    }

    /**
     * 获取培训标识
     *
     * @return traitmid - 培训标识
     */
    public String getTraitmid() {
        return traitmid;
    }

    /**
     * 设置培训标识
     *
     * @param traitmid 培训标识
     */
    public void setTraitmid(String traitmid) {
        this.traitmid = traitmid;
    }

    /**
     * 获取课时日期
     *
     * @return tradate - 课时日期
     */
    public String getTradate() {
        return tradate;
    }

    /**
     * 设置课时日期
     *
     * @param tradate 课时日期
     */
    public void setTradate(String tradate) {
        this.tradate = tradate;
    }

    /**
     * 获取开始时间
     *
     * @return stime - 开始时间
     */
    public String getStime() {
        return stime;
    }

    /**
     * 设置开始时间
     *
     * @param stime 开始时间
     */
    public void setStime(String stime) {
        this.stime = stime;
    }

    /**
     * 获取结束时间
     *
     * @return etime - 结束时间
     */
    public String getEtime() {
        return etime;
    }

    /**
     * 设置结束时间
     *
     * @param etime 结束时间
     */
    public void setEtime(String etime) {
        this.etime = etime;
    }

    /**
     * 获取标题
     *
     * @return dtitle - 标题
     */
    public String getDtitle() {
        return dtitle;
    }

    /**
     * 设置标题
     *
     * @param dtitle 标题
     */
    public void setDtitle(String dtitle) {
        this.dtitle = dtitle;
    }
}