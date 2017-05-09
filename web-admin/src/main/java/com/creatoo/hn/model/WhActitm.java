package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "wh_actitm")
public class WhActitm {
    /**
     * 活动批次id
     */
    @Id
    private String actitmid;
    /**
     * 活动批次标题
     */
    private String actitmtitle;

	/**
     * 关连活动标识
     */
    private String refactid;

    /**
     * 活动开始时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date sdate;

    /**
     * 活动结束时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date edate;

    /**
     * 报名人数
     */
    private Integer peoplecount;

    /**
     * 报名开始时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date enrolstime;

    /**
     * 报名结束时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date enroletime;

    /**
     * 是否需要实名： 0否 1是
     */
    private Integer isrealname;

    /**
     * 是否需要报名：0否，1是
     */
    private Integer isenrol;

    /**
     * 是否需要订票：0否，1是
     */
    private Integer isyp;

    /**
     * 状态:0：初始 1：审批 2：以审3：发布
     */
    private Integer state;

    /**
     * 报名介绍
     */
    private String enroldesc;
    /**
     * 首页排序
     */
    private String mainsort;
    /**
     * 是否上首页
     */
    private Integer issmain;
    /**
     * 获取活动批次id
     *
     * @return actitmid - 活动批次id
     */
    public String getActitmid() {
        return actitmid;
    }

    /**
     * 设置活动批次id
     *
     * @param actitmid 活动批次id
     */
    public void setActitmid(String actitmid) {
        this.actitmid = actitmid;
    }

    /**
     *
     * @return actitmtitle - 活动批次Title
     */
    public String getActitmtitle() {
		return actitmtitle;
	}
    /**
     *
     * @param actitmtitle 活动批次title
     */
	public void setActitmtitle(String actitmtitle) {
		this.actitmtitle = actitmtitle;
	}

    /**
     * 获取关连活动标识
     *
     * @return refactid - 关连活动标识
     */
    public String getRefactid() {
        return refactid;
    }

    /**
     * 设置关连活动标识
     *
     * @param refactid 关连活动标识
     */
    public void setRefactid(String refactid) {
        this.refactid = refactid;
    }

    /**
     * 获取活动开始时间
     *
     * @return sdate - 活动开始时间
     */
    public Date getSdate() {
        return sdate;
    }

    /**
     * 设置活动开始时间
     *
     * @param sdate 活动开始时间
     */
    public void setSdate(Date sdate) {
        this.sdate = sdate;
    }

    /**
     * 获取活动结束时间
     *
     * @return edate - 活动结束时间
     */
    public Date getEdate() {
        return edate;
    }

    /**
     * 设置活动结束时间
     *
     * @param edate 活动结束时间
     */
    public void setEdate(Date edate) {
        this.edate = edate;
    }

    /**
     * 获取报名人数
     *
     * @return peoplecount - 报名人数
     */
    public Integer getPeoplecount() {
        return peoplecount;
    }

    /**
     * 设置报名人数
     *
     * @param peoplecount 报名人数
     */
    public void setPeoplecount(Integer peoplecount) {
        this.peoplecount = peoplecount;
    }

    /**
     * 获取报名开始时间
     *
     * @return enrolstime - 报名开始时间
     */
    public Date getEnrolstime() {
        return enrolstime;
    }

    /**
     * 设置报名开始时间
     *
     * @param enrolstime 报名开始时间
     */
    public void setEnrolstime(Date enrolstime) {
        this.enrolstime = enrolstime;
    }

    /**
     * 获取报名结束时间
     *
     * @return enroletime - 报名结束时间
     */
    public Date getEnroletime() {
        return enroletime;
    }

    /**
     * 设置报名结束时间
     *
     * @param enroletime 报名结束时间
     */
    public void setEnroletime(Date enroletime) {
        this.enroletime = enroletime;
    }

    /**
     * 获取是否需要实名： 0否 1是
     *
     * @return isrealname - 是否需要实名： 0否 1是
     */
    public Integer getIsrealname() {
        return isrealname;
    }

    /**
     * 设置是否需要实名： 0否 1是
     *
     * @param isrealname 是否需要实名： 0否 1是
     */
    public void setIsrealname(Integer isrealname) {
        this.isrealname = isrealname;
    }

    /**
     * 获取是否需要报名：0否，1是
     *
     * @return isenrol - 是否需要报名：0否，1是
     */
    public Integer getIsenrol() {
        return isenrol;
    }

    /**
     * 设置是否需要报名：0否，1是
     *
     * @param isenrol 是否需要报名：0否，1是
     */
    public void setIsenrol(Integer isenrol) {
        this.isenrol = isenrol;
    }

    /**
     * 获取是否需要订票：0否，1是
     *
     * @return isyp - 是否需要订票：0否，1是
     */
    public Integer getIsyp() {
        return isyp;
    }

    /**
     * 设置是否需要订票：0否，1是
     *
     * @param isyp 是否需要订票：0否，1是
     */
    public void setIsyp(Integer isyp) {
        this.isyp = isyp;
    }

    /**
     * 获取状态:0：初始 1：审批 2：以审3：发布
     *
     * @return state - 状态:0：初始 1：审批 2：以审3：发布
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置状态:0：初始 1：审批 2：以审3：发布
     *
     * @param state 状态:0：初始 1：审批 2：以审3：发布
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取报名介绍
     *
     * @return enroldesc - 报名介绍
     */
    public String getEnroldesc() {
        return enroldesc;
    }

    /**
     * 设置报名介绍
     *
     * @param enroldesc 报名介绍
     */
    public void setEnroldesc(String enroldesc) {
        this.enroldesc = enroldesc;
    }

	public String getMainsort() {
		return mainsort;
	}

	public void setMainsort(String mainsort) {
		this.mainsort = mainsort;
	}

	public Integer getIssmain() {
		return issmain;
	}

	public void setIssmain(Integer issmain) {
		this.issmain = issmain;
	}
    
}