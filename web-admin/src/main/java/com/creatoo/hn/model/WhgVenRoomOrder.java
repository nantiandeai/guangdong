package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "whg_ven_room_order")
public class WhgVenRoomOrder {
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
     * 订单号
     */
    private String orderid;

    /**
     * 会员ID
     */
    private String userid;

    /**
     * 预定人姓名
     */
    private String ordercontact;

    /**
     * 预定人联系手机
     */
    private String ordercontactphone;

    /**
     * 订单摘要信息
     */
    private String ordersummary;

    /**
     * 订单生成时间
     */
    private Date crtdate;

    /**
     * 订单状态. 0- 预定申请, 1-取消申请, 2-审核失败， 3-预定成功
     */
    private Integer state;

    /**
     * 日期,指定到哪一天
     */
    private Date timeday;

    /**
     * 开始时间(时分)
     */
    private Date timestart;

    /**
     * 结束时间(时分)
     */
    private Date timeend;

    /**
     * 取票状态。1：未取票，2：已取票
     */
    private Integer ticketstatus;

    /**
     * 取票时间
     */
    private Date printtime;

    /**
     * 取票次数
     */
    private Integer printtickettimes;

    /**
     * 验票状态。1：未验票，2：已验票
     */
    private Integer ticketcheckstate;

    /**
     * 验票时间
     */
    private Date ticketcheckdate;

    /**
     * 预订用途
     */
    private String purpose;

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
     * 获取订单号
     *
     * @return orderid - 订单号
     */
    public String getOrderid() {
        return orderid;
    }

    /**
     * 设置订单号
     *
     * @param orderid 订单号
     */
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    /**
     * 获取会员ID
     *
     * @return userid - 会员ID
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置会员ID
     *
     * @param userid 会员ID
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * 获取预定人姓名
     *
     * @return ordercontact - 预定人姓名
     */
    public String getOrdercontact() {
        return ordercontact;
    }

    /**
     * 设置预定人姓名
     *
     * @param ordercontact 预定人姓名
     */
    public void setOrdercontact(String ordercontact) {
        this.ordercontact = ordercontact;
    }

    /**
     * 获取预定人联系手机
     *
     * @return ordercontactphone - 预定人联系手机
     */
    public String getOrdercontactphone() {
        return ordercontactphone;
    }

    /**
     * 设置预定人联系手机
     *
     * @param ordercontactphone 预定人联系手机
     */
    public void setOrdercontactphone(String ordercontactphone) {
        this.ordercontactphone = ordercontactphone;
    }

    /**
     * 获取订单摘要信息
     *
     * @return ordersummary - 订单摘要信息
     */
    public String getOrdersummary() {
        return ordersummary;
    }

    /**
     * 设置订单摘要信息
     *
     * @param ordersummary 订单摘要信息
     */
    public void setOrdersummary(String ordersummary) {
        this.ordersummary = ordersummary;
    }

    /**
     * 获取订单生成时间
     *
     * @return crtdate - 订单生成时间
     */
    public Date getCrtdate() {
        return crtdate;
    }

    /**
     * 设置订单生成时间
     *
     * @param crtdate 订单生成时间
     */
    public void setCrtdate(Date crtdate) {
        this.crtdate = crtdate;
    }

    /**
     * 获取订单状态. 0- 预定申请, 1-取消申请, 2-审核失败， 3-预定成功
     *
     * @return state - 订单状态. 0- 预定申请, 1-取消申请, 2-审核失败， 3-预定成功
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置订单状态. 0- 预定申请, 1-取消申请, 2-审核失败， 3-预定成功
     *
     * @param state 订单状态. 0- 预定申请, 1-取消申请, 2-审核失败， 3-预定成功
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取日期,指定到哪一天
     *
     * @return timeday - 日期,指定到哪一天
     */
    public Date getTimeday() {
        return timeday;
    }

    /**
     * 设置日期,指定到哪一天
     *
     * @param timeday 日期,指定到哪一天
     */
    public void setTimeday(Date timeday) {
        this.timeday = timeday;
    }

    /**
     * 获取开始时间(时分)
     *
     * @return timestart - 开始时间(时分)
     */
    public Date getTimestart() {
        return timestart;
    }

    /**
     * 设置开始时间(时分)
     *
     * @param timestart 开始时间(时分)
     */
    public void setTimestart(Date timestart) {
        this.timestart = timestart;
    }

    /**
     * 获取结束时间(时分)
     *
     * @return timeend - 结束时间(时分)
     */
    public Date getTimeend() {
        return timeend;
    }

    /**
     * 设置结束时间(时分)
     *
     * @param timeend 结束时间(时分)
     */
    public void setTimeend(Date timeend) {
        this.timeend = timeend;
    }

    /**
     * 获取取票状态。1：未取票，2：已取票
     *
     * @return ticketstatus - 取票状态。1：未取票，2：已取票
     */
    public Integer getTicketstatus() {
        return ticketstatus;
    }

    /**
     * 设置取票状态。1：未取票，2：已取票
     *
     * @param ticketstatus 取票状态。1：未取票，2：已取票
     */
    public void setTicketstatus(Integer ticketstatus) {
        this.ticketstatus = ticketstatus;
    }

    /**
     * 获取取票时间
     *
     * @return printtime - 取票时间
     */
    public Date getPrinttime() {
        return printtime;
    }

    /**
     * 设置取票时间
     *
     * @param printtime 取票时间
     */
    public void setPrinttime(Date printtime) {
        this.printtime = printtime;
    }

    /**
     * 获取取票次数
     *
     * @return printtickettimes - 取票次数
     */
    public Integer getPrinttickettimes() {
        return printtickettimes;
    }

    /**
     * 设置取票次数
     *
     * @param printtickettimes 取票次数
     */
    public void setPrinttickettimes(Integer printtickettimes) {
        this.printtickettimes = printtickettimes;
    }

    /**
     * 获取验票状态。1：未验票，2：已验票
     *
     * @return ticketcheckstate - 验票状态。1：未验票，2：已验票
     */
    public Integer getTicketcheckstate() {
        return ticketcheckstate;
    }

    /**
     * 设置验票状态。1：未验票，2：已验票
     *
     * @param ticketcheckstate 验票状态。1：未验票，2：已验票
     */
    public void setTicketcheckstate(Integer ticketcheckstate) {
        this.ticketcheckstate = ticketcheckstate;
    }

    /**
     * 获取验票时间
     *
     * @return ticketcheckdate - 验票时间
     */
    public Date getTicketcheckdate() {
        return ticketcheckdate;
    }

    /**
     * 设置验票时间
     *
     * @param ticketcheckdate 验票时间
     */
    public void setTicketcheckdate(Date ticketcheckdate) {
        this.ticketcheckdate = ticketcheckdate;
    }

    /**
     * 获取预订用途
     *
     * @return purpose - 预订用途
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * 设置预订用途
     *
     * @param purpose 预订用途
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}