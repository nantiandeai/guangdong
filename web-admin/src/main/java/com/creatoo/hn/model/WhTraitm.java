package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "wh_traitm")
public class WhTraitm {
    /**
     * 批次标识
     */
    @Id
    private String traitmid;

    /**
     * 培训标识
     */
    private String traid;

    /**
     * 培训标题
     */
    private String tratitle;

    /**
     * 开始日期
     */
    private Date sdate;

    /**
     * 结束日期
     */
    private Date edate;

    /**
     * 是否需要报名
     */
    private String isenrol;

    /**
     * 报名开始时间
     */
    private Date enrolstime;

    /**
     * 报名结束时间
     */
    private Date enroletime;

    /**
     * 报名是否需要审核
     */
    private String isenrolqr;

    /**
     * 状态:0：初始 1：审批 2：以审3：发布
     */
    private Integer state;

    /**
     * 报名人数限制
     */
    private String enrollimit;

    /**
     * 是否需要面试通知
     */
    private String isnotic;

    /**
     * 报名介绍
     */
    private String enroldesc;
    
    /**
     * 是否上首页
     */
    private Integer traitmghp;

    /**
     *上首页排序 
     */
    private Integer traitmidx;
    /**
     * 课程简介
     */
    private String trasummary;
    /**
     * 获取批次标识
     *
     * @return traitmid - 批次标识
     */
    public String getTraitmid() {
        return traitmid;
    }

    /**
     * 设置批次标识
     *
     * @param traitmid 批次标识
     */
    public void setTraitmid(String traitmid) {
        this.traitmid = traitmid;
    }

    /**
     * 获取培训标识
     *
     * @return traid - 培训标识
     */
    public String getTraid() {
        return traid;
    }

    /**
     * 设置培训标识
     *
     * @param traid 培训标识
     */
    public void setTraid(String traid) {
        this.traid = traid;
    }

    /**
     * 获取培训标题
     *
     * @return tratitle - 培训标题
     */
    public String getTratitle() {
        return tratitle;
    }

    /**
     * 设置培训标题
     *
     * @param tratitle 培训标题
     */
    public void setTratitle(String tratitle) {
        this.tratitle = tratitle;
    }

    /**
     * 获取开始日期
     *
     * @return sdate - 开始日期
     */
    public Date getSdate() {
        return sdate;
    }

    /**
     * 设置开始日期
     *
     * @param sdate 开始日期
     */
    public void setSdate(Date sdate) {
        this.sdate = sdate;
    }

    /**
     * 获取结束日期
     *
     * @return edate - 结束日期
     */
    public Date getEdate() {
        return edate;
    }

    /**
     * 设置结束日期
     *
     * @param edate 结束日期
     */
    public void setEdate(Date edate) {
        this.edate = edate;
    }

    /**
     * 获取是否需要报名
     *
     * @return isenrol - 是否需要报名
     */
    public String getIsenrol() {
        return isenrol;
    }

    /**
     * 设置是否需要报名
     *
     * @param isenrol 是否需要报名
     */
    public void setIsenrol(String isenrol) {
        this.isenrol = isenrol;
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
     * 获取报名是否需要审核
     *
     * @return isenrolqr - 报名是否需要审核
     */
    public String getIsenrolqr() {
        return isenrolqr;
    }

    /**
     * 设置报名是否需要审核
     *
     * @param isenrolqr 报名是否需要审核
     */
    public void setIsenrolqr(String isenrolqr) {
        this.isenrolqr = isenrolqr;
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
     * 获取报名人数限制
     *
     * @return enrollimit - 报名人数限制
     */
    public String getEnrollimit() {
        return enrollimit;
    }

    /**
     * 设置报名人数限制
     *
     * @param enrollimit 报名人数限制
     */
    public void setEnrollimit(String enrollimit) {
        this.enrollimit = enrollimit;
    }

    /**
     * 获取是否需要面试通知
     *
     * @return isnotic - 是否需要面试通知
     */
    public String getIsnotic() {
        return isnotic;
    }

    /**
     * 设置是否需要面试通知
     *
     * @param isnotic 是否需要面试通知
     */
    public void setIsnotic(String isnotic) {
        this.isnotic = isnotic;
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
    /**
     * 获取是否上首页
     * @return
     */
	public Integer getTraitmghp() {
		return traitmghp;
	}
	/**
	 * 设置是否上首页
	 * @param traitmghp
	 */
	public void setTraitmghp(Integer traitmghp) {
		this.traitmghp = traitmghp;
	}
	/**
	 * 获取上首页排序
	 * @return
	 */
	public Integer getTraitmidx() {
		return traitmidx;
	}
	/**
	 * 设置是否上首页
	 * @param traitmidx
	 */
	public void setTraitmidx(Integer traitmidx) {
		this.traitmidx = traitmidx;
	}
	/**
	 * 获取课程简介
	 * @return
	 */
	public String getTrasummary() {
		return trasummary;
	}
	/**
	 * 得到课程简介
	 * @param trasummary
	 */
	public void setTrasummary(String trasummary) {
		this.trasummary = trasummary;
	}
	
    
}