package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "wh_fetch_state")
public class WhFetchState {
    /**
     * 采集来源标识
     */
    @Id
    private String fsfromid;

    /**
     * 开始采集时间.
     */
    private Date fsstime;

    /**
     * 采集结束时间.
     */
    private Date fsetime;

    /**
     * 采集人标识.
     */
    private String fsoptuid;

    /**
     * 采集状态:0-运行中;1-已结束.
     */
    private Integer fsstate;

    /**
     * 获取采集来源标识
     *
     * @return fsfromid - 采集来源标识
     */
    public String getFsfromid() {
        return fsfromid;
    }

    /**
     * 设置采集来源标识
     *
     * @param fsfromid 采集来源标识
     */
    public void setFsfromid(String fsfromid) {
        this.fsfromid = fsfromid;
    }

    /**
     * 获取开始采集时间.
     *
     * @return fsstime - 开始采集时间.
     */
    public Date getFsstime() {
        return fsstime;
    }

    /**
     * 设置开始采集时间.
     *
     * @param fsstime 开始采集时间.
     */
    public void setFsstime(Date fsstime) {
        this.fsstime = fsstime;
    }

    /**
     * 获取采集结束时间.
     *
     * @return fsetime - 采集结束时间.
     */
    public Date getFsetime() {
        return fsetime;
    }

    /**
     * 设置采集结束时间.
     *
     * @param fsetime 采集结束时间.
     */
    public void setFsetime(Date fsetime) {
        this.fsetime = fsetime;
    }

    /**
     * 获取采集人标识.
     *
     * @return fsoptuid - 采集人标识.
     */
    public String getFsoptuid() {
        return fsoptuid;
    }

    /**
     * 设置采集人标识.
     *
     * @param fsoptuid 采集人标识.
     */
    public void setFsoptuid(String fsoptuid) {
        this.fsoptuid = fsoptuid;
    }

    /**
     * 获取采集状态:0-运行中;1-已结束.
     *
     * @return fsstate - 采集状态:0-运行中;1-已结束.
     */
    public Integer getFsstate() {
        return fsstate;
    }

    /**
     * 设置采集状态:0-运行中;1-已结束.
     *
     * @param fsstate 采集状态:0-运行中;1-已结束.
     */
    public void setFsstate(Integer fsstate) {
        this.fsstate = fsstate;
    }
}