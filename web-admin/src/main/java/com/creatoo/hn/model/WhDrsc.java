package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "wh_drsc")
public class WhDrsc {
    /**
     * 数院资源标识
     */
    @Id
    private String drscid;

    /**
     * 数字资源名称
     */
    private String drsctitle;

    /**
     * 数字资源来源
     */
    private String drscfrom;

    /**
     * 数字资源封面图片
     */
    private String drscpic;

    /**
     * 数字资源创建时间
     */
    private Date drsccrttime;

    /**
     * 数字资源路径
     */
    private String drscpath;

    /**
     * 艺术分类
     */
    private String drscarttyp;

    /**
     * 数字资源的分类
     */
    private String drsctyp;

    /**
     * 状态.0-初始;1-送审;2-已审;3-发布.
     */
    private Integer drscstate;

    /**
     * 修改状态时间
     */
    private Date drscopttime;

    /**
     * 资源时长
     */
    private String drsctime;
    
    /**
     * 关键字
     */
    private String drsckey;
    
    /**
     * 简介
     */
    private String drscintro;
    /**
     * 获取数院资源标识
     *
     * @return drscid - 数院资源标识
     */
    public String getDrscid() {
        return drscid;
    }

    /**
     * 设置数院资源标识
     *
     * @param drscid 数院资源标识
     */
    public void setDrscid(String drscid) {
        this.drscid = drscid;
    }

    /**
     * 获取数字资源名称
     *
     * @return drsctitle - 数字资源名称
     */
    public String getDrsctitle() {
        return drsctitle;
    }

    /**
     * 设置数字资源名称
     *
     * @param drsctitle 数字资源名称
     */
    public void setDrsctitle(String drsctitle) {
        this.drsctitle = drsctitle;
    }

    /**
     * 获取数字资源来源
     *
     * @return drscfrom - 数字资源来源
     */
    public String getDrscfrom() {
        return drscfrom;
    }

    /**
     * 设置数字资源来源
     *
     * @param drscfrom 数字资源来源
     */
    public void setDrscfrom(String drscfrom) {
        this.drscfrom = drscfrom;
    }

    /**
     * 获取数字资源封面图片
     *
     * @return drscpic - 数字资源封面图片
     */
    public String getDrscpic() {
        return drscpic;
    }

    /**
     * 设置数字资源封面图片
     *
     * @param drscpic 数字资源封面图片
     */
    public void setDrscpic(String drscpic) {
        this.drscpic = drscpic;
    }

    /**
     * 获取数字资源创建时间
     *
     * @return drsccrttime - 数字资源创建时间
     */
    public Date getDrsccrttime() {
        return drsccrttime;
    }

    /**
     * 设置数字资源创建时间
     *
     * @param drsccrttime 数字资源创建时间
     */
    public void setDrsccrttime(Date drsccrttime) {
        this.drsccrttime = drsccrttime;
    }

    /**
     * 获取数字资源路径
     *
     * @return drscpath - 数字资源路径
     */
    public String getDrscpath() {
        return drscpath;
    }

    /**
     * 设置数字资源路径
     *
     * @param drscpath 数字资源路径
     */
    public void setDrscpath(String drscpath) {
        this.drscpath = drscpath;
    }

    /**
     * 获取艺术分类
     *
     * @return drscarttyp - 艺术分类
     */
    public String getDrscarttyp() {
        return drscarttyp;
    }

    /**
     * 设置艺术分类
     *
     * @param drscarttyp 艺术分类
     */
    public void setDrscarttyp(String drscarttyp) {
        this.drscarttyp = drscarttyp;
    }

    /**
     * 获取数字资源的分类
     *
     * @return drsctyp - 数字资源的分类
     */
    public String getDrsctyp() {
        return drsctyp;
    }

    /**
     * 设置数字资源的分类
     *
     * @param drsctyp 数字资源的分类
     */
    public void setDrsctyp(String drsctyp) {
        this.drsctyp = drsctyp;
    }

    /**
     * 获取状态.0-初始;1-送审;2-已审;3-发布.
     *
     * @return drscstate - 状态.0-初始;1-送审;2-已审;3-发布.
     */
    public Integer getDrscstate() {
        return drscstate;
    }

    /**
     * 设置状态.0-初始;1-送审;2-已审;3-发布.
     *
     * @param drscstate 状态.0-初始;1-送审;2-已审;3-发布.
     */
    public void setDrscstate(Integer drscstate) {
        this.drscstate = drscstate;
    }

    /**
     * 获取修改状态时间
     *
     * @return drscopttime - 修改状态时间
     */
    public Date getDrscopttime() {
        return drscopttime;
    }

    /**
     * 设置修改状态时间
     *
     * @param drscopttime 修改状态时间
     */
    public void setDrscopttime(Date drscopttime) {
        this.drscopttime = drscopttime;
    }

    /**
     * 获取时长
     * @return
     */
	public String getDrsctime() {
		return drsctime;
	}
	/**
	 * 设置时长
	 * @param drsctime
	 */
	public void setDrsctime(String drsctime) {
		this.drsctime = drsctime;
	}

	/**
	 * 获取关键字
	 * @return
	 */
	public String getDrsckey() {
		return drsckey;
	}
	/**
	 * 设置关键字
	 * @param drsckey
	 */
	public void setDrsckey(String drsckey) {
		this.drsckey = drsckey;
	}

	/**
	 * 获取简介
	 * @return
	 */
	public String getDrscintro() {
		return drscintro;
	}

	/**
	 * 设置简介
	 * @param drscintro
	 */
	public void setDrscintro(String drscintro) {
		this.drscintro = drscintro;
	}
    
    
}