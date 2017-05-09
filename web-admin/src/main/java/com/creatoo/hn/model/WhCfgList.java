package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "wh_cfg_list")
public class WhCfgList {
    /**
     * 配置标识
     */
    @Id
    private String cfgid;

    /**
     * 页面类型
     */
    private String cfgpagetype;

    /**
     * 实体类型：活动/培训/艺术作品/艺术团队等
     */
    private String cfgenttype;

    /**
     * 实体类型。培训的分类或者活动的分类
     */
    private String cfgentclazz;

    /**
     * 显示的标题
     */
    private String cfgshowtitle;

    /**
     * 显示的时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date cfgshowtime;

    /**
     * 简介
     */
    private String cfgshowintroduce;

    /**
     * 显示图片
     */
    private String cfgshowpic;

    /**
     * 连接地址
     */
    private String cfgshowlink;

    /**
     * 排序字段
     */
    private Integer cfgshowidx;

    /**
     * 状态：0无效，1有效
     */
    private Integer cfgstate;
    
    /**
     * 配置类型id
     */
    private String cfgshowid;
    
    /**
     * 配置类型id
     */
    public String getCfgshowid() {
		return cfgshowid;
	}
    /**
     * 配置类型id
     */
	public void setCfgshowid(String cfgshowid) {
		this.cfgshowid = cfgshowid;
	}

	/**
     * 获取配置标识
     *
     * @return cfgid - 配置标识 
     */
    public String getCfgid() {
        return cfgid;
    }

    /**
     * 设置配置标识
     *
     * @param cfgid 配置标识
     */
    public void setCfgid(String cfgid) {
        this.cfgid = cfgid;
    }

    /**
     * 获取页面类型
     *
     * @return cfgpagetype - 页面类型
     */
    public String getCfgpagetype() {
        return cfgpagetype;
    }

    /**
     * 设置页面类型
     *
     * @param cfgpagetype 页面类型
     */
    public void setCfgpagetype(String cfgpagetype) {
        this.cfgpagetype = cfgpagetype;
    }

    /**
     * 获取实体类型：活动/培训/艺术作品/艺术团队等
     *
     * @return cfgenttype - 实体类型：活动/培训/艺术作品/艺术团队等
     */
    public String getCfgenttype() {
        return cfgenttype;
    }

    /**
     * 设置实体类型：活动/培训/艺术作品/艺术团队等
     *
     * @param cfgenttype 实体类型：活动/培训/艺术作品/艺术团队等
     */
    public void setCfgenttype(String cfgenttype) {
        this.cfgenttype = cfgenttype;
    }

    /**
     * 获取实体类型。培训的分类或者活动的分类
     *
     * @return cfgentclazz - 实体类型。培训的分类或者活动的分类
     */
    public String getCfgentclazz() {
        return cfgentclazz;
    }

    /**
     * 设置实体类型。培训的分类或者活动的分类
     *
     * @param cfgentclazz 实体类型。培训的分类或者活动的分类
     */
    public void setCfgentclazz(String cfgentclazz) {
        this.cfgentclazz = cfgentclazz;
    }

    /**
     * 获取显示的标题
     *
     * @return cfgshowtitle - 显示的标题
     */
    public String getCfgshowtitle() {
        return cfgshowtitle;
    }

    /**
     * 设置显示的标题
     *
     * @param cfgshowtitle 显示的标题
     */
    public void setCfgshowtitle(String cfgshowtitle) {
        this.cfgshowtitle = cfgshowtitle;
    }

    /**
     * 获取显示的时间
     *
     * @return cfgshowtime - 显示的时间
     */
    public Date getCfgshowtime() {
        return cfgshowtime;
    }

    /**
     * 设置显示的时间
     *
     * @param cfgshowtime 显示的时间
     */
    public void setCfgshowtime(Date cfgshowtime) {
        this.cfgshowtime = cfgshowtime;
    }

    /**
     * 获取简介
     *
     * @return cfgshowintroduce - 简介
     */
    public String getCfgshowintroduce() {
        return cfgshowintroduce;
    }

    /**
     * 设置简介
     *
     * @param cfgshowintroduce 简介
     */
    public void setCfgshowintroduce(String cfgshowintroduce) {
        this.cfgshowintroduce = cfgshowintroduce;
    }

    /**
     * 获取显示图片
     *
     * @return cfgshowpic - 显示图片
     */
    public String getCfgshowpic() {
        return cfgshowpic;
    }

    /**
     * 设置显示图片
     *
     * @param cfgshowpic 显示图片
     */
    public void setCfgshowpic(String cfgshowpic) {
        this.cfgshowpic = cfgshowpic;
    }

    /**
     * 获取连接地址
     *
     * @return cfgshowlink - 连接地址
     */
    public String getCfgshowlink() {
        return cfgshowlink;
    }

    /**
     * 设置连接地址
     *
     * @param cfgshowlink 连接地址
     */
    public void setCfgshowlink(String cfgshowlink) {
        this.cfgshowlink = cfgshowlink;
    }

    /**
     * 获取排序字段
     *
     * @return cfgshowidx - 排序字段
     */
    public Integer getCfgshowidx() {
        return cfgshowidx;
    }

    /**
     * 设置排序字段
     *
     * @param cfgshowidx 排序字段
     */
    public void setCfgshowidx(Integer cfgshowidx) {
        this.cfgshowidx = cfgshowidx;
    }

    /**
     * 获取状态：0无效，1有效
     *
     * @return cfgstate - 状态：0无效，1有效
     */
    public Integer getCfgstate() {
        return cfgstate;
    }

    /**
     * 设置状态：0无效，1有效
     *
     * @param cfgstate 状态：0无效，1有效
     */
    public void setCfgstate(Integer cfgstate) {
        this.cfgstate = cfgstate;
    }
}