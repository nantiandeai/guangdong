package com.creatoo.hn.model;

import javax.persistence.*;

@Table(name = "wh_cfg_adv")
public class WhCfgAdv {
    /**
     * 页面广告配置
     */
    @Id
    private String cfgadvid;

    /**
     * 页面类型：首页/培训首页
     */
    private String cfgadvpagetype;

    /**
     * 广告图
     */
    private String cfgadvpic;

    /**
     * 排序字段
     */
    private Integer cfgadvidx;

    /**
     * 状态：0-无效，1-有效
     */
    private Integer cfgadvstate;
    /**
     * 	广告连接地址
     */
    private String cfgadvlink;
    
    /**
     * 	广告连接地址
     */
    public String getCfgadvlink() {
		return cfgadvlink;
	}
    /**
     * 	广告连接地址
     */
	public void setCfgadvlink(String cfgadvlink) {
		this.cfgadvlink = cfgadvlink;
	}

	/**
     * 获取页面广告配置
     *
     * @return cfgadvid - 页面广告配置
     */
    public String getCfgadvid() {
        return cfgadvid;
    }

    /**
     * 设置页面广告配置
     *
     * @param cfgadvid 页面广告配置
     */
    public void setCfgadvid(String cfgadvid) {
        this.cfgadvid = cfgadvid;
    }

    /**
     * 获取页面类型：首页/培训首页
     *
     * @return cfgadvpagetype - 页面类型：首页/培训首页
     */
    public String getCfgadvpagetype() {
        return cfgadvpagetype;
    }

    /**
     * 设置页面类型：首页/培训首页
     *
     * @param cfgadvpagetype 页面类型：首页/培训首页
     */
    public void setCfgadvpagetype(String cfgadvpagetype) {
        this.cfgadvpagetype = cfgadvpagetype;
    }

    /**
     * 获取广告图
     *
     * @return cfgadvpic - 广告图
     */
    public String getCfgadvpic() {
        return cfgadvpic;
    }

    /**
     * 设置广告图
     *
     * @param cfgadvpic 广告图
     */
    public void setCfgadvpic(String cfgadvpic) {
        this.cfgadvpic = cfgadvpic;
    }

    /**
     * 获取排序字段
     *
     * @return cfgadvidx - 排序字段
     */
    public Integer getCfgadvidx() {
        return cfgadvidx;
    }

    /**
     * 设置排序字段
     *
     * @param cfgadvidx 排序字段
     */
    public void setCfgadvidx(Integer cfgadvidx) {
        this.cfgadvidx = cfgadvidx;
    }

    /**
     * 获取状态：0-无效，1-有效
     *
     * @return cfgadvstate - 状态：0-无效，1-有效
     */
    public Integer getCfgadvstate() {
        return cfgadvstate;
    }

    /**
     * 设置状态：0-无效，1-有效
     *
     * @param cfgadvstate 状态：0-无效，1-有效
     */
    public void setCfgadvstate(Integer cfgadvstate) {
        this.cfgadvstate = cfgadvstate;
    }
}