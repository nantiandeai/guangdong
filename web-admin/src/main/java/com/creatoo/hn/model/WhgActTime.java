package com.creatoo.hn.model;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "whg_act_time")
public class WhgActTime {
    /**
     * 主键ID
     */
    @Id
    private String id;

    /**
     * 活动id
     */
    private String actid;

    /**
     * 活动场次日期
     */
    private Date playdate;

    /**
     * 场次开始时间
     */
    private String playstime;

    /**
     * 场次结束时间
     */
    private String playetime;

    /**
     * 场次总座位数
     */
    private Integer seats;

    /**
     * 状态.0-非正常; 1-正常
     */
    private Integer state;

    private Date playstarttime;

    private Date playendtime;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取活动id
     *
     * @return actid - 活动id
     */
    public String getActid() {
        return actid;
    }

    /**
     * 设置活动id
     *
     * @param actid 活动id
     */
    public void setActid(String actid) {
        this.actid = actid;
    }

    /**
     * 获取活动场次日期
     *
     * @return playdate - 活动场次日期
     */
    public Date getPlaydate() {
        return playdate;
    }

    /**
     * 设置活动场次日期
     *
     * @param playdate 活动场次日期
     */
    public void setPlaydate(Date playdate) {
        this.playdate = playdate;
    }

    /**
     * 获取场次开始时间
     *
     * @return playstime - 场次开始时间
     */
    public String getPlaystime() {
        return playstime;
    }

    /**
     * 设置场次开始时间
     *
     * @param playstime 场次开始时间
     */
    public void setPlaystime(String playstime) {
        this.playstime = playstime;
    }

    /**
     * 获取场次结束时间
     *
     * @return playetime - 场次结束时间
     */
    public String getPlayetime() {
        return playetime;
    }

    /**
     * 设置场次结束时间
     *
     * @param playetime 场次结束时间
     */
    public void setPlayetime(String playetime) {
        this.playetime = playetime;
    }

    /**
     * 获取场次总座位数
     *
     * @return seats - 场次总座位数
     */
    public Integer getSeats() {
        return seats;
    }

    /**
     * 设置场次总座位数
     *
     * @param seats 场次总座位数
     */
    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    /**
     * 获取状态.0-非正常; 1-正常
     *
     * @return state - 状态.0-非正常; 1-正常
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置状态.0-非正常; 1-正常
     *
     * @param state 状态.0-非正常; 1-正常
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * @return playstarttime
     */
    public Date getPlaystarttime() {
        return playstarttime;
    }

    /**
     * @param playstarttime
     */
    public void setPlaystarttime(Date playstarttime) {
        this.playstarttime = playstarttime;
    }

    /**
     * @return playendtime
     */
    public Date getPlayendtime() {
        return playendtime;
    }

    /**
     * @param playendtime
     */
    public void setPlayendtime(Date playendtime) {
        this.playendtime = playendtime;
    }
}