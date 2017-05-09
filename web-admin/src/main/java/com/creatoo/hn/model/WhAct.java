package com.creatoo.hn.model;

import java.util.Date;

import javax.persistence.*;

@Table(name = "wh_act")
public class WhAct {
	/**
	 * 活动id
	 */
	@Id
	private String actid;

	/**
	 * 活动名称
	 */
	private String name;

	/**
	 * 区域
	 */
	private String area;

	/**
	 * 活动类型
	 */
	private String acttype;

	/**
	 * 活动分类
	 */
	private String actcalss;

	/**
	 * 活动详细地址
	 */
	private String address;

	/**
	 * 活动介绍
	 */
	private String actdesc;

	/**
	 * 活动图片详情页主图
	 */
	private String image;

	/**
	 * 列表展示小图
	 */
	private String imagesm;

	/**
	 * 活动主办方
	 */
	private String acthost;

	/**
	 * 适合年龄段
	 */
	private String agelevel;

	/**
	 * 是否需上传资料
	 */
	private Integer isenroldata;

	/**
	 * 个人报名模版
	 */
	private String tempone;

	/**
	 * 团队报名模版
	 */
	private String tempteam;

	/**
	 * 关键字
	 */
	private String trakeys;

	/**
	 * 必须登录点评
	 */
	private Integer islogincomment;

	/**
	 * 活动详情
	 */
	private String content;

	/**
	 * 活动创建时间
	 */
	private Date creatime;

	/**
	 * 获取活动创建时间
	 *
	 * @return 活动创建时间
	 */
	public Date getCreatime() {
		return creatime;
	}

	/**
	 * 设置活动创建时间
	 *
	 * @param actid
	 *            活动创建时间
	 */
	public void setCreatime(Date creatime) {
		this.creatime = creatime;
	}

	/**
	 * 获取活动id
	 *
	 * @return actid - 活动id
	 */
	public String getActid() {
		return actid;
	}

	/**
	 * 设置活动id
	 *
	 * @param actid
	 *            活动id
	 */
	public void setActid(String actid) {
		this.actid = actid;
	}

	/**
	 * 获取活动名称
	 *
	 * @return name - 活动名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置活动名称
	 *
	 * @param name
	 *            活动名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取区域
	 *
	 * @return area - 区域
	 */
	public String getArea() {
		return area;
	}

	/**
	 * 设置区域
	 *
	 * @param area
	 *            区域
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * 获取活动类型
	 *
	 * @return acttype - 活动类型
	 */
	public String getActtype() {
		return acttype;
	}

	/**
	 * 设置活动类型
	 *
	 * @param acttype
	 *            活动类型
	 */
	public void setActtype(String acttype) {
		this.acttype = acttype;
	}

	/**
	 * 获取活动分类
	 *
	 * @return actcalss - 活动分类
	 */
	public String getActcalss() {
		return actcalss;
	}

	/**
	 * 设置活动分类
	 *
	 * @param actcalss
	 *            活动分类
	 */
	public void setActcalss(String actcalss) {
		this.actcalss = actcalss;
	}

	/**
	 * 获取活动详细地址
	 *
	 * @return address - 活动详细地址
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 设置活动详细地址
	 *
	 * @param address
	 *            活动详细地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取活动介绍
	 *
	 * @return actdesc - 活动介绍
	 */
	public String getActdesc() {
		return actdesc;
	}

	/**
	 * 设置活动介绍
	 *
	 * @param actdesc
	 *            活动介绍
	 */
	public void setActdesc(String actdesc) {
		this.actdesc = actdesc;
	}

	/**
	 * 获取活动图片详情页主图
	 *
	 * @return image - 活动图片详情页主图
	 */
	public String getImage() {
		return image;
	}

	/**
	 * 设置活动图片详情页主图
	 *
	 * @param image
	 *            活动图片详情页主图
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * 获取列表展示小图
	 *
	 * @return imagesm - 列表展示小图
	 */
	public String getImagesm() {
		return imagesm;
	}

	/**
	 * 设置列表展示小图
	 *
	 * @param imagesm
	 *            列表展示小图
	 */
	public void setImagesm(String imagesm) {
		this.imagesm = imagesm;
	}

	/**
	 * 获取活动主办方
	 *
	 * @return acthost - 活动主办方
	 */
	public String getActhost() {
		return acthost;
	}

	/**
	 * 设置活动主办方
	 *
	 * @param acthost
	 *            活动主办方
	 */
	public void setActhost(String acthost) {
		this.acthost = acthost;
	}

	/**
	 * 获取适合年龄段
	 *
	 * @return agelevel - 适合年龄段
	 */
	public String getAgelevel() {
		return agelevel;
	}

	/**
	 * 设置适合年龄段
	 *
	 * @param agelevel
	 *            适合年龄段
	 */
	public void setAgelevel(String agelevel) {
		this.agelevel = agelevel;
	}

	/**
	 * 获取是否需上传资料
	 *
	 * @return isenroldata - 是否需上传资料
	 */
	public Integer getIsenroldata() {
		return isenroldata;
	}

	/**
	 * 设置是否需上传资料
	 *
	 * @param isenroldata
	 *            是否需上传资料
	 */
	public void setIsenroldata(Integer isenroldata) {
		this.isenroldata = isenroldata;
	}

	/**
	 * 获取个人报名模版
	 *
	 * @return tempone - 个人报名模版
	 */
	public String getTempone() {
		return tempone;
	}

	/**
	 * 设置个人报名模版
	 *
	 * @param tempone
	 *            个人报名模版
	 */
	public void setTempone(String tempone) {
		this.tempone = tempone;
	}

	/**
	 * 获取团队报名模版
	 *
	 * @return tempteam - 团队报名模版
	 */
	public String getTempteam() {
		return tempteam;
	}

	/**
	 * 设置团队报名模版
	 *
	 * @param tempteam
	 *            团队报名模版
	 */
	public void setTempteam(String tempteam) {
		this.tempteam = tempteam;
	}

	/**
	 * 获取关键字
	 *
	 * @return trakeys - 关键字
	 */
	public String getTrakeys() {
		return trakeys;
	}

	/**
	 * 设置关键字
	 *
	 * @param trakeys
	 *            关键字
	 */
	public void setTrakeys(String trakeys) {
		this.trakeys = trakeys;
	}

	/**
	 * 获取必须登录点评
	 *
	 * @return islogincomment - 必须登录点评
	 */
	public Integer getIslogincomment() {
		return islogincomment;
	}

	/**
	 * 设置必须登录点评
	 *
	 * @param islogincomment
	 *            必须登录点评
	 */
	public void setIslogincomment(Integer islogincomment) {
		this.islogincomment = islogincomment;
	}

	/**
	 * 获取活动详情
	 *
	 * @return content - 活动详情
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置活动详情
	 *
	 * @param content
	 *            活动详情
	 */
	public void setContent(String content) {
		this.content = content;
	}
}