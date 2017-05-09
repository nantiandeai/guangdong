package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "whg_act_seat")
public class WhgActSeat {
    /**
     * 活动座位表主键ID
     */
    @Id
    private String id;

    /**
     * 活动座位价格
     */
    private Integer seatprice;

    /**
     * 座位出票状态(1-未出票 2-已出票 3-出票失败)
     */
    private Integer seatticketstatus;

    /**
     * 活动ID
     */
    private String activityid;

    /**
     * 座位状态(1-正常2-待修 3-不存在)
     */
    private Integer seatstatus;

    /**
     * 座位坐标(行)
     */
    private Integer seatrow;

    /**
     * 座位坐标(列)
     */
    private Integer seatcolumn;

    /**
     * 座位区域
     */
    private String seatarea;

    /**
     * 座位编号
     */
    private String seatcode;

    /**
     * 座位创建时间
     */
    private Date seatcreatetime;

    /**
     * 座位最后修改时间
     */
    private Date seatupdatetime;

    /**
     * 座位创建人
     */
    private String seatcreateuser;

    /**
     * 座位最后修改人
     */
    private String seatupdateuser;

    /**
     * 座位编号，座位别名
     */
    private String seatnum;

    /**
     * 获取活动座位表主键ID
     *
     * @return id - 活动座位表主键ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置活动座位表主键ID
     *
     * @param id 活动座位表主键ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取活动座位价格
     *
     * @return seatprice - 活动座位价格
     */
    public Integer getSeatprice() {
        return seatprice;
    }

    /**
     * 设置活动座位价格
     *
     * @param seatprice 活动座位价格
     */
    public void setSeatprice(Integer seatprice) {
        this.seatprice = seatprice;
    }

    /**
     * 获取座位出票状态(1-未出票 2-已出票 3-出票失败)
     *
     * @return seatticketstatus - 座位出票状态(1-未出票 2-已出票 3-出票失败)
     */
    public Integer getSeatticketstatus() {
        return seatticketstatus;
    }

    /**
     * 设置座位出票状态(1-未出票 2-已出票 3-出票失败)
     *
     * @param seatticketstatus 座位出票状态(1-未出票 2-已出票 3-出票失败)
     */
    public void setSeatticketstatus(Integer seatticketstatus) {
        this.seatticketstatus = seatticketstatus;
    }

    /**
     * 获取活动ID
     *
     * @return activityid - 活动ID
     */
    public String getActivityid() {
        return activityid;
    }

    /**
     * 设置活动ID
     *
     * @param activityid 活动ID
     */
    public void setActivityid(String activityid) {
        this.activityid = activityid;
    }

    /**
     * 获取座位状态(1-正常2-待修 3-不存在)
     *
     * @return seatstatus - 座位状态(1-正常2-待修 3-不存在)
     */
    public Integer getSeatstatus() {
        return seatstatus;
    }

    /**
     * 设置座位状态(1-正常2-待修 3-不存在)
     *
     * @param seatstatus 座位状态(1-正常2-待修 3-不存在)
     */
    public void setSeatstatus(Integer seatstatus) {
        this.seatstatus = seatstatus;
    }

    /**
     * 获取座位坐标(行)
     *
     * @return seatrow - 座位坐标(行)
     */
    public Integer getSeatrow() {
        return seatrow;
    }

    /**
     * 设置座位坐标(行)
     *
     * @param seatrow 座位坐标(行)
     */
    public void setSeatrow(Integer seatrow) {
        this.seatrow = seatrow;
    }

    /**
     * 获取座位坐标(列)
     *
     * @return seatcolumn - 座位坐标(列)
     */
    public Integer getSeatcolumn() {
        return seatcolumn;
    }

    /**
     * 设置座位坐标(列)
     *
     * @param seatcolumn 座位坐标(列)
     */
    public void setSeatcolumn(Integer seatcolumn) {
        this.seatcolumn = seatcolumn;
    }

    /**
     * 获取座位区域
     *
     * @return seatarea - 座位区域
     */
    public String getSeatarea() {
        return seatarea;
    }

    /**
     * 设置座位区域
     *
     * @param seatarea 座位区域
     */
    public void setSeatarea(String seatarea) {
        this.seatarea = seatarea;
    }

    /**
     * 获取座位编号
     *
     * @return seatcode - 座位编号
     */
    public String getSeatcode() {
        return seatcode;
    }

    /**
     * 设置座位编号
     *
     * @param seatcode 座位编号
     */
    public void setSeatcode(String seatcode) {
        this.seatcode = seatcode;
    }

    /**
     * 获取座位创建时间
     *
     * @return seatcreatetime - 座位创建时间
     */
    public Date getSeatcreatetime() {
        return seatcreatetime;
    }

    /**
     * 设置座位创建时间
     *
     * @param seatcreatetime 座位创建时间
     */
    public void setSeatcreatetime(Date seatcreatetime) {
        this.seatcreatetime = seatcreatetime;
    }

    /**
     * 获取座位最后修改时间
     *
     * @return seatupdatetime - 座位最后修改时间
     */
    public Date getSeatupdatetime() {
        return seatupdatetime;
    }

    /**
     * 设置座位最后修改时间
     *
     * @param seatupdatetime 座位最后修改时间
     */
    public void setSeatupdatetime(Date seatupdatetime) {
        this.seatupdatetime = seatupdatetime;
    }

    /**
     * 获取座位创建人
     *
     * @return seatcreateuser - 座位创建人
     */
    public String getSeatcreateuser() {
        return seatcreateuser;
    }

    /**
     * 设置座位创建人
     *
     * @param seatcreateuser 座位创建人
     */
    public void setSeatcreateuser(String seatcreateuser) {
        this.seatcreateuser = seatcreateuser;
    }

    /**
     * 获取座位最后修改人
     *
     * @return seatupdateuser - 座位最后修改人
     */
    public String getSeatupdateuser() {
        return seatupdateuser;
    }

    /**
     * 设置座位最后修改人
     *
     * @param seatupdateuser 座位最后修改人
     */
    public void setSeatupdateuser(String seatupdateuser) {
        this.seatupdateuser = seatupdateuser;
    }

    /**
     * 获取座位编号，座位别名
     *
     * @return seatnum - 座位编号，座位别名
     */
    public String getSeatnum() {
        return seatnum;
    }

    /**
     * 设置座位编号，座位别名
     *
     * @param seatnum 座位编号，座位别名
     */
    public void setSeatnum(String seatnum) {
        this.seatnum = seatnum;
    }
}