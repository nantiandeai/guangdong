package com.creatoo.hn.model;

import java.util.Date;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "wh_brand")
public class WhBrand {
    /**
     * 专题活动标识
     */
    @Id
    private String braid;

    /**
     * 专题活动标题
     */
    private String bratitle;

    /**
     * 专题简介
     */
    private String braintroduce;

    /**
     * 专题图片
     */
    private String brapic;

    /**
     * 专题详情背景图
     */
    private String brabigpic;

    /**
     * 状态：0-初始;1-送审;2-已审;3已发布
     */
    private Integer brastate;

    /**
     * 短标题
     */
    private String brashorttitle;

    /**
     * 专题活动的现场介绍
     */
    private String bradetail;

    /**
     * 品牌活动开始时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date brasdate;
    
    /**
     * 品牌活动结束时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date braedate;
    
    /***
     * 品牌活动修改时间
     */
    private Date bravoptime;
    /**
     * 获取专题活动标识
     *
     * @return braid - 专题活动标识
     */
    public String getBraid() {
        return braid;
    }

    /**
     * 设置专题活动标识
     *
     * @param braid 专题活动标识
     */
    public void setBraid(String braid) {
        this.braid = braid;
    }

    /**
     * 获取专题活动标题
     *
     * @return bratitle - 专题活动标题
     */
    public String getBratitle() {
        return bratitle;
    }

    /**
     * 设置专题活动标题
     *
     * @param bratitle 专题活动标题
     */
    public void setBratitle(String bratitle) {
        this.bratitle = bratitle;
    }

    /**
     * 获取专题简介
     *
     * @return braintroduce - 专题简介
     */
    public String getBraintroduce() {
        return braintroduce;
    }

    /**
     * 设置专题简介
     *
     * @param braintroduce 专题简介
     */
    public void setBraintroduce(String braintroduce) {
        this.braintroduce = braintroduce;
    }

    /**
     * 获取专题图片
     *
     * @return brapic - 专题图片
     */
    public String getBrapic() {
        return brapic;
    }

    /**
     * 设置专题图片
     *
     * @param brapic 专题图片
     */
    public void setBrapic(String brapic) {
        this.brapic = brapic;
    }

    /**
     * 获取专题详情背景图
     *
     * @return brabigpic - 专题详情背景图
     */
    public String getBrabigpic() {
        return brabigpic;
    }

    /**
     * 设置专题详情背景图
     *
     * @param brabigpic 专题详情背景图
     */
    public void setBrabigpic(String brabigpic) {
        this.brabigpic = brabigpic;
    }

    /**
     * 获取状态：0-初始;1-送审;2-已审;3已发布
     *
     * @return brastate - 状态：0-初始;1-送审;2-已审;3已发布
     */
    public Integer getBrastate() {
        return brastate;
    }

    /**
     * 设置状态：0-初始;1-送审;2-已审;3已发布
     *
     * @param brastate 状态：0-初始;1-送审;2-已审;3已发布
     */
    public void setBrastate(Integer brastate) {
        this.brastate = brastate;
    }

    /**
     * 获取短标题
     *
     * @return brashorttitle - 短标题
     */
    public String getBrashorttitle() {
        return brashorttitle;
    }

    /**
     * 设置短标题
     *
     * @param brashorttitle 短标题
     */
    public void setBrashorttitle(String brashorttitle) {
        this.brashorttitle = brashorttitle;
    }

    /**
     * 获取专题活动的现场介绍
     *
     * @return bradetail - 专题活动的现场介绍
     */
    public String getBradetail() {
        return bradetail;
    }

    /**
     * 设置专题活动的现场介绍
     *
     * @param bradetail 专题活动的现场介绍
     */
    public void setBradetail(String bradetail) {
        this.bradetail = bradetail;
    }

    /**
     * 获取品牌活动开始时间
     * @return
     */
	public Date getBrasdate() {
		return brasdate;
	}

	/**
	 * 设置品牌活动开始时间
	 * @param brasdate
	 */
	public void setBrasdate(Date brasdate) {
		this.brasdate = brasdate;
	}

	/**
	 * 获取品牌活动结束时间
	 * @return
	 */
	public Date getBraedate() {
		return braedate;
	}

	/**
	 * 设置品牌活动结束时间
	 * @param braedate
	 */
	public void setBraedate(Date braedate) {
		this.braedate = braedate;
	}

	public Date getBravoptime() {
		return bravoptime;
	}

	public void setBravoptime(Date bravoptime) {
		this.bravoptime = bravoptime;
	}
    
	
}