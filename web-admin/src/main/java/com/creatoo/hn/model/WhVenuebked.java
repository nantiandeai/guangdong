package com.creatoo.hn.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "wh_venuebked")
public class WhVenuebked {
    /**
     * 预定标识
     */
    @Id
    private String vebid;

    /**
     * 预定时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date vebday;

    /**
     * 预定时段开始时间HH:mm
     */
    private String vebstime;

    /**
     * 预定结束时间HH:mm
     */
    private String vebetime;

    /**
     * 场馆标识
     */
    private String vebvenid;

    /**
     * 预定的用户标识
     */
    private String vebuid;

    /**
     * 培训标识
     */
    private String vebtrainid;

    /**
     * 活动标识
     */
    private String vebactivid;

    /**
     * 状态.0-提交申请;1-审核通过;2-审核失败;
     */
    private Integer vebstate;

    /**
     * 审核不通过原因
     */
    private String vebcheckmsg;
    /**
     * 时段标识
     */
    private String vebtid;
    /**
     * 场馆预定的时间
     */
    private Date vebordertime;

    /**
     * 获取预定标识
     *
     * @return vebid - 预定标识
     */
    public String getVebid() {
        return vebid;
    }

    /**
     * 设置预定标识
     *
     * @param vebid 预定标识
     */
    public void setVebid(String vebid) {
        this.vebid = vebid;
    }

    /**
     * 获取预定时间
     *
     * @return vebday - 预定时间
     */
    public Date getVebday() {
        return vebday;
    }

    /**
     * 设置预定时间
     *
     * @param vebday 预定时间
     */
    public void setVebday(Date vebday) {
        this.vebday = vebday;
    }

    /**
     * 获取预定时段开始时间HH:mm
     *
     * @return vebstime - 预定时段开始时间HH:mm
     */
    public String getVebstime() {
        return vebstime;
    }

    /**
     * 设置预定时段开始时间HH:mm
     *
     * @param vebstime 预定时段开始时间HH:mm
     */
    public void setVebstime(String vebstime) {
        this.vebstime = vebstime;
    }

    /**
     * 获取预定结束时间HH:mm
     *
     * @return vebetime - 预定结束时间HH:mm
     */
    public String getVebetime() {
        return vebetime;
    }

    /**
     * 设置预定结束时间HH:mm
     *
     * @param vebetime 预定结束时间HH:mm
     */
    public void setVebetime(String vebetime) {
        this.vebetime = vebetime;
    }

    /**
     * 获取场馆标识
     *
     * @return vebvenid - 场馆标识
     */
    public String getVebvenid() {
        return vebvenid;
    }

    /**
     * 设置场馆标识
     *
     * @param vebvenid 场馆标识
     */
    public void setVebvenid(String vebvenid) {
        this.vebvenid = vebvenid;
    }

    /**
     * 获取预定的用户标识
     *
     * @return vebuid - 预定的用户标识
     */
    public String getVebuid() {
        return vebuid;
    }

    /**
     * 设置预定的用户标识
     *
     * @param vebuid 预定的用户标识
     */
    public void setVebuid(String vebuid) {
        this.vebuid = vebuid;
    }

    /**
     * 获取培训标识
     *
     * @return vebtrainid - 培训标识
     */
    public String getVebtrainid() {
        return vebtrainid;
    }

    /**
     * 设置培训标识
     *
     * @param vebtrainid 培训标识
     */
    public void setVebtrainid(String vebtrainid) {
        this.vebtrainid = vebtrainid;
    }

    /**
     * 获取活动标识
     *
     * @return vebactivid - 活动标识
     */
    public String getVebactivid() {
        return vebactivid;
    }

    /**
     * 设置活动标识
     *
     * @param vebactivid 活动标识
     */
    public void setVebactivid(String vebactivid) {
        this.vebactivid = vebactivid;
    }

    /**
     * 获取状态.0-提交申请;1-审核通过;2-审核失败;
     *
     * @return vebstate - 状态.0-提交申请;1-审核通过;2-审核失败;
     */
    public Integer getVebstate() {
        return vebstate;
    }

    /**
     * 设置状态.0-提交申请;1-审核通过;2-审核失败;
     *
     * @param vebstate 状态.0-提交申请;1-审核通过;2-审核失败;
     */
    public void setVebstate(Integer vebstate) {
        this.vebstate = vebstate;
    }

    /**
     * @return vebcheckmsg
     */
    public String getVebcheckmsg() {
        return vebcheckmsg;
    }

    /**
     * @param vebcheckmsg
     */
    public void setVebcheckmsg(String vebcheckmsg) {
        this.vebcheckmsg = vebcheckmsg;
    }

	public String getVebtid() {
		return vebtid;
	}

	public void setVebtid(String vebtid) {
		this.vebtid = vebtid;
	}
	
	/**
	 * 获取场馆预定的时间
	 * @return
	 */
	public Date getVebordertime() {
		return vebordertime;
	}

	/**
	 * 设置场馆预定的时间
	 * @param vebordertime
	 */
	public void setVebordertime(Date vebordertime) {
		this.vebordertime = vebordertime;
	}
    
}