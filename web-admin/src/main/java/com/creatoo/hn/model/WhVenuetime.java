package com.creatoo.hn.model;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "wh_venuetime")
public class WhVenuetime {
    /**
     * 场馆时段标识
     */
    @Id
    private String vtid;

    /**
     * 场馆标识
     */
    private String venid;

    /**
     * 时段开始时间HH:mm
     */
   
    private String vtstime;

    /**
     * 时段结束时间HH:mm
     */
   
    private String vtetime;

    /**
     * 状态0-停用，1-启用
     */
    private Integer vtstate;

    /**
     * 获取场馆时段标识
     *
     * @return vtid - 场馆时段标识
     */
    public String getVtid() {
        return vtid;
    }

    /**
     * 设置场馆时段标识
     *
     * @param vtid 场馆时段标识
     */
    public void setVtid(String vtid) {
        this.vtid = vtid;
    }

    /**
     * 获取场馆标识
     *
     * @return venid - 场馆标识
     */
    public String getVenid() {
        return venid;
    }

    /**
     * 设置场馆标识
     *
     * @param venid 场馆标识
     */
    public void setVenid(String venid) {
        this.venid = venid;
    }

    /**
     * 获取时段开始时间HH:mm
     *
     * @return vtstime - 时段开始时间HH:mm
     */
    public String getVtstime() {
        return vtstime;
    }

    /**
     * 设置时段开始时间HH:mm
     *
     * @param vtstime 时段开始时间HH:mm
     */
    public void setVtstime(String vtstime) {
        this.vtstime = vtstime;
    }

    /**
     * 获取时段结束时间HH:mm
     *
     * @return vtetime - 时段结束时间HH:mm
     */
    public String getVtetime() {
        return vtetime;
    }

    /**
     * 设置时段结束时间HH:mm
     *
     * @param vtetime 时段结束时间HH:mm
     */
    public void setVtetime(String vtetime) {
        this.vtetime = vtetime;
    }

    /**
     * 获取状态0-停用，1-启用
     *
     * @return vtstate - 状态0-停用，1-启用
     */
    public Integer getVtstate() {
        return vtstate;
    }

    /**
     * 设置状态0-停用，1-启用
     *
     * @param vtstate 状态0-停用，1-启用
     */
    public void setVtstate(Integer vtstate) {
        this.vtstate = vtstate;
    }
}