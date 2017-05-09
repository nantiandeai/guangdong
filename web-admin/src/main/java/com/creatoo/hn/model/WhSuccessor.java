package com.creatoo.hn.model;

import java.util.Date;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "wh_successor")
public class WhSuccessor {
    /**
     * 传承人id
     */
    @Id
    private String suorid;

    /**
     * 传承项目关联id
     */
    private String proid;

    /**
     * 传承人姓名
     */
    private String suorname;

    /**
     * 传承人图片
     */
    private String suorpic;

    /**
     * 传承人区域
     */
    private String suorqy;

    /**
     * 传承人级别
     */
    private String suorlevel;

    /**
     * 传承人批次
     */
    private String suoritem;

    /**
     * 传承人类别
     */
    private String suortype;

    /**
     * 传承人 介绍
     */
    private String suorjs;

    /**
     * 传承人状态
     */
    private Integer suorstate;

    /**
     * 传承人叙史
     */
    private String suorxus;

    /**
     * 传承人成就
     */
    private String suorachv;

    /**
     * 是否推荐：0否  1 是
     */
    private Integer recommend;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date suoroptime;
    /**
     * 获取传承人id
     *
     * @return suorid - 传承人id
     */
    public String getSuorid() {
        return suorid;
    }

    /**
     * 设置传承人id
     *
     * @param suorid 传承人id
     */
    public void setSuorid(String suorid) {
        this.suorid = suorid;
    }

    /**
     * 获取传承项目关联id
     *
     * @return proid - 传承项目关联id
     */
    public String getProid() {
        return proid;
    }

    /**
     * 设置传承项目关联id
     *
     * @param proid 传承项目关联id
     */
    public void setProid(String proid) {
        this.proid = proid;
    }

    /**
     * 获取传承人姓名
     *
     * @return suorname - 传承人姓名
     */
    public String getSuorname() {
        return suorname;
    }

    /**
     * 设置传承人姓名
     *
     * @param suorname 传承人姓名
     */
    public void setSuorname(String suorname) {
        this.suorname = suorname;
    }

    /**
     * 获取传承人图片
     *
     * @return suorpic - 传承人图片
     */
    public String getSuorpic() {
        return suorpic;
    }

    /**
     * 设置传承人图片
     *
     * @param suorpic 传承人图片
     */
    public void setSuorpic(String suorpic) {
        this.suorpic = suorpic;
    }

    /**
     * 获取传承人区域
     *
     * @return suorqy - 传承人区域
     */
    public String getSuorqy() {
        return suorqy;
    }

    /**
     * 设置传承人区域
     *
     * @param suorqy 传承人区域
     */
    public void setSuorqy(String suorqy) {
        this.suorqy = suorqy;
    }

    /**
     * 获取传承人级别
     *
     * @return suorlevel - 传承人级别
     */
    public String getSuorlevel() {
        return suorlevel;
    }

    /**
     * 设置传承人级别
     *
     * @param suorlevel 传承人级别
     */
    public void setSuorlevel(String suorlevel) {
        this.suorlevel = suorlevel;
    }

    /**
     * 获取传承人批次
     *
     * @return suoritem - 传承人批次
     */
    public String getSuoritem() {
        return suoritem;
    }

    /**
     * 设置传承人批次
     *
     * @param suoritem 传承人批次
     */
    public void setSuoritem(String suoritem) {
        this.suoritem = suoritem;
    }

    /**
     * 获取传承人类别
     *
     * @return suortype - 传承人类别
     */
    public String getSuortype() {
        return suortype;
    }

    /**
     * 设置传承人类别
     *
     * @param suortype 传承人类别
     */
    public void setSuortype(String suortype) {
        this.suortype = suortype;
    }

    /**
     * 获取传承人 介绍
     *
     * @return suorjs - 传承人 介绍
     */
    public String getSuorjs() {
        return suorjs;
    }

    /**
     * 设置传承人 介绍
     *
     * @param suorjs 传承人 介绍
     */
    public void setSuorjs(String suorjs) {
        this.suorjs = suorjs;
    }

    /**
     * 获取传承人状态
     *
     * @return suorstate - 传承人状态
     */
    public Integer getSuorstate() {
        return suorstate;
    }

    /**
     * 设置传承人状态
     *
     * @param suorstate 传承人状态
     */
    public void setSuorstate(Integer suorstate) {
        this.suorstate = suorstate;
    }

    /**
     * 获取传承人叙史
     *
     * @return suorxus - 传承人叙史
     */
    public String getSuorxus() {
        return suorxus;
    }

    /**
     * 设置传承人叙史
     *
     * @param suorxus 传承人叙史
     */
    public void setSuorxus(String suorxus) {
        this.suorxus = suorxus;
    }

    /**
     * 获取传承人成就
     *
     * @return suorachv - 传承人成就
     */
    public String getSuorachv() {
        return suorachv;
    }

    /**
     * 设置传承人成就
     *
     * @param suorachv 传承人成就
     */
    public void setSuorachv(String suorachv) {
        this.suorachv = suorachv;
    }

	public Date getSuoroptime() {
		return suoroptime;
	}

	public void setSuoroptime(Date suoroptime) {
		this.suoroptime = suoroptime;
	}

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }
}