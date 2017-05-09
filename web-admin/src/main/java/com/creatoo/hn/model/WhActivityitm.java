package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "wh_activityitm")
public class WhActivityitm {
    /**
     * 活动场次标识
     */
    @Id
    private String actvitmid;

    /**
     * 活动关联id
     */
    private String actvrefid;

    /**
     * 活动场次开始时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date actvitmsdate;

    /**
     * 活动场次结束时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date actvitmedate;

    /**
     * 活动场次报名开始时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date actvbmsdate;

    /**
     * 活动场次报名结束时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date actvbmedate;

    /**
     * 活动订票/报名数
     */
    private Integer actvitmdpcount;

    /**
     * 活动余数
     */
    private Integer actvitmbmcount;
    
    /**
     * 活动状态
     */
    private Integer actvitmstate;
    
    /**
     * 个人 活动报名最大人数
     */
    private Integer actvitmonemax;
    
    /**
     * 是否需要上传参赛作品
     */
    private Integer actvitmupworks;
    
    private Integer ismoney;
    
    /**
     * 获取活动场次标识
     *
     * @return actvitmid - 活动场次标识
     */
    public String getActvitmid() {
        return actvitmid;
    }

    /**
     * 设置活动场次标识
     *
     * @param actvitmid 活动场次标识
     */
    public void setActvitmid(String actvitmid) {
        this.actvitmid = actvitmid;
    }

    /**
     * 获取活动关联id
     *
     * @return actvrefid - 活动关联id
     */
    public String getActrefvid() {
        return actvrefid;
    }

    /**
     * 设置活动关联id
     *
     * @param actvrefid 活动关联id
     */
    public void setActrefvid(String actrefvid) {
        this.actvrefid = actrefvid;
    }

    /**
     * 获取活动场次开始时间
     *
     * @return actvitmsdate - 活动场次开始时间
     */
    public Date getActvitmsdate() {
        return actvitmsdate;
    }

    /**
     * 设置活动场次开始时间
     *
     * @param actvitmsdate 活动场次开始时间
     */
    public void setActvitmsdate(Date actvitmsdate) {
        this.actvitmsdate = actvitmsdate;
    }

    /**
     * 获取活动场次结束时间
     *
     * @return actvitmedate - 活动场次结束时间
     */
    public Date getActvitmedate() {
        return actvitmedate;
    }

    /**
     * 设置活动场次结束时间
     *
     * @param actvitmedate 活动场次结束时间
     */
    public void setActvitmedate(Date actvitmedate) {
        this.actvitmedate = actvitmedate;
    }

    /**
     * 获取活动场次报名开始时间
     *
     * @return actvbmsdate - 活动场次报名开始时间
     */
    public Date getActvbmsdate() {
        return actvbmsdate;
    }

    /**
     * 设置活动场次报名开始时间
     *
     * @param actvbmsdate 活动场次报名开始时间
     */
    public void setActvbmsdate(Date actvbmsdate) {
        this.actvbmsdate = actvbmsdate;
    }

    /**
     * 获取活动场次报名结束时间
     *
     * @return actvbmedate - 活动场次报名结束时间
     */
    public Date getActvbmedate() {
        return actvbmedate;
    }

    /**
     * 设置活动场次报名结束时间
     *
     * @param actvbmedate 活动场次报名结束时间
     */
    public void setActvbmedate(Date actvbmedate) {
        this.actvbmedate = actvbmedate;
    }

    /**
     * 获取活动订票数
     *
     * @return actvitmdpcount - 活动订票数
     */
    public Integer getActvitmdpcount() {
        return actvitmdpcount;
    }

    /**
     * 设置活动订票数
     *
     * @param actvitmdpcount 活动订票数
     */
    public void setActvitmdpcount(Integer actvitmdpcount) {
        this.actvitmdpcount = actvitmdpcount;
    }

    /**
     * 获取活动报名数
     *
     * @return actvitmbmcount - 活动报名数
     */
    public Integer getActvitmbmcount() {
        return actvitmbmcount;
    }

    /**
     * 设置活动报名数
     *
     * @param actvitmbmcount 活动报名数
     */
    public void setActvitmbmcount(Integer actvitmbmcount) {
        this.actvitmbmcount = actvitmbmcount;
    }

	public Integer getActvitmstate() {
		return actvitmstate;
	}

	public void setActvitmstate(Integer actvitmstate) {
		this.actvitmstate = actvitmstate;
	}

	public Integer getActvitmonemax() {
		return actvitmonemax;
	}

	public void setActvitmonemax(Integer actvitmonemax) {
		this.actvitmonemax = actvitmonemax;
	}

	public Integer getActvitmupworks() {
		return actvitmupworks;
	}

	public void setActvitmupworks(Integer actvitmupworks) {
		this.actvitmupworks = actvitmupworks;
	}

	public Integer getIsmoney() {
		return ismoney;
	}

	public void setIsmoney(Integer ismoney) {
		this.ismoney = ismoney;
	}
    
	
}