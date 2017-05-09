package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "wh_venuedate")
public class WhVenuedate {
    /**
     * 主键
     */
    @Id
    private String vendid;

    /**
     * 场馆主键
     */
    private String vendpid;

    /**
     * 开始日期
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date vendsdate;

    /**
     * 结束日期
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date vendedate;

    /**
     * 操作时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date vendopttime;

    /**
     * 状态:0-停用， 1- 启用
     */
    private Integer vendstate;

    /**
     * 获取主键
     *
     * @return vendid - 主键
     */
    public String getVendid() {
        return vendid;
    }

    /**
     * 设置主键
     *
     * @param vendid 主键
     */
    public void setVendid(String vendid) {
        this.vendid = vendid;
    }

    /**
     * 获取场馆主键
     *
     * @return vendpid - 场馆主键
     */
    public String getVendpid() {
        return vendpid;
    }

    /**
     * 设置场馆主键
     *
     * @param vendpid 场馆主键
     */
    public void setVendpid(String vendpid) {
        this.vendpid = vendpid;
    }

    /**
     * 获取开始日期
     *
     * @return vendsdate - 开始日期
     */
    public Date getVendsdate() {
        return vendsdate;
    }

    /**
     * 设置开始日期
     *
     * @param vendsdate 开始日期
     */
    public void setVendsdate(Date vendsdate) {
        this.vendsdate = vendsdate;
    }

    /**
     * 获取结束日期
     *
     * @return vendedate - 结束日期
     */
    public Date getVendedate() {
        return vendedate;
    }

    /**
     * 设置结束日期
     *
     * @param vendedate 结束日期
     */
    public void setVendedate(Date vendedate) {
        this.vendedate = vendedate;
    }

    /**
     * 获取操作时间
     *
     * @return vendopttime - 操作时间
     */
    public Date getVendopttime() {
        return vendopttime;
    }

    /**
     * 设置操作时间
     *
     * @param vendopttime 操作时间
     */
    public void setVendopttime(Date vendopttime) {
        this.vendopttime = vendopttime;
    }

    /**
     * 获取状态:0-停用， 1- 启用
     *
     * @return vendstate - 状态:0-停用， 1- 启用
     */
    public Integer getVendstate() {
        return vendstate;
    }

    /**
     * 设置状态:0-停用， 1- 启用
     *
     * @param vendstate 状态:0-停用， 1- 启用
     */
    public void setVendstate(Integer vendstate) {
        this.vendstate = vendstate;
    }
}