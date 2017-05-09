package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "whg_ven_room_time")
public class WhgVenRoomTime {
    /**
     * PK
     */
    @Id
    private String id;

    /**
     * 活动室ID
     */
    private String roomid;

    /**
     * 时段开始
     */
    private Date timestart;

    /**
     * 时段结束
     */
    private Date timeend;

    /**
     * 日期
     */
    private Date timeday;

    /**
     * 状态：0禁用预定订，1启用预定
     */
    private Integer state;

    /**
     * 获取PK
     *
     * @return id - PK
     */
    public String getId() {
        return id;
    }

    /**
     * 设置PK
     *
     * @param id PK
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取活动室ID
     *
     * @return roomid - 活动室ID
     */
    public String getRoomid() {
        return roomid;
    }

    /**
     * 设置活动室ID
     *
     * @param roomid 活动室ID
     */
    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    /**
     * 获取时段开始
     *
     * @return timestart - 时段开始
     */
    public Date getTimestart() {
        return timestart;
    }

    /**
     * 设置时段开始
     *
     * @param timestart 时段开始
     */
    public void setTimestart(Date timestart) {
        this.timestart = timestart;
    }

    /**
     * 获取时段结束
     *
     * @return timeend - 时段结束
     */
    public Date getTimeend() {
        return timeend;
    }

    /**
     * 设置时段结束
     *
     * @param timeend 时段结束
     */
    public void setTimeend(Date timeend) {
        this.timeend = timeend;
    }

    /**
     * 获取日期
     *
     * @return timeday - 日期
     */
    public Date getTimeday() {
        return timeday;
    }

    /**
     * 设置日期
     *
     * @param timeday 日期
     */
    public void setTimeday(Date timeday) {
        this.timeday = timeday;
    }

    /**
     * 获取状态：0禁用预定订，1启用预定
     *
     * @return state - 状态：0禁用预定订，1启用预定
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置状态：0禁用预定订，1启用预定
     *
     * @param state 状态：0禁用预定订，1启用预定
     */
    public void setState(Integer state) {
        this.state = state;
    }
}