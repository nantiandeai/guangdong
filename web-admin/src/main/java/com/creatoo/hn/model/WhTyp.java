package com.creatoo.hn.model;

import javax.persistence.*;

@Table(name = "wh_typ")
public class WhTyp {
    /**
     * 分类总表id
     */
    @Id
    private String typid;

    /**
     * 分类名称
     */
    private String typname;

    /**
     * 分类排序
     */
    private Integer typidx;

    /**
     * 分类父类型
     */
    private String typpid;

    /**
     * 分类状态
     */
    private String typstate;

    /**
     * 0.艺术分类1.活动分类2.培训分类3.年龄4.课程级别5.资讯分类6.标签 7.关键字
     */
    private String type;

    /**
     * 分类说明
     */
    private String typmemo;

    /**
     * 报名限制.0-不限制,1-1人只能报一次
     */
    private Integer typbmcfg;
    
    /**
     * 广告 页面配置图片大小提示信息
     */
    private String typpic;
    
    
    /**
     * 广告 页面配置图片大小提示信息
     */
	public String getTyppic() {
		return typpic;
	}
	
	/**
     * 广告 页面配置图片大小提示信息
     */
	public void setTyppic(String typpic) {
		this.typpic = typpic;
	}

	/**
     * 获取分类总表id
     *
     * @return typid - 分类总表id
     */
    public String getTypid() {
        return typid;
    }

    /**
     * 设置分类总表id
     *
     * @param typid 分类总表id
     */
    public void setTypid(String typid) {
        this.typid = typid;
    }

    /**
     * 获取分类名称
     *
     * @return typname - 分类名称
     */
    public String getTypname() {
        return typname;
    }

    /**
     * 设置分类名称
     *
     * @param typname 分类名称
     */
    public void setTypname(String typname) {
        this.typname = typname;
    }

    /**
     * 获取分类排序
     *
     * @return typidx - 分类排序
     */
    public Integer getTypidx() {
        return typidx;
    }

    /**
     * 设置分类排序
     *
     * @param typidx 分类排序
     */
    public void setTypidx(Integer typidx) {
        this.typidx = typidx;
    }

    /**
     * 获取分类父类型
     *
     * @return typpid - 分类父类型
     */
    public String getTyppid() {
        return typpid;
    }

    /**
     * 设置分类父类型
     *
     * @param typpid 分类父类型
     */
    public void setTyppid(String typpid) {
        this.typpid = typpid;
    }

    /**
     * 获取分类状态
     *
     * @return typstate - 分类状态
     */
    public String getTypstate() {
        return typstate;
    }

    /**
     * 设置分类状态
     *
     * @param typstate 分类状态
     */
    public void setTypstate(String typstate) {
        this.typstate = typstate;
    }

    /**
     * 获取0.艺术分类1.活动分类2.培训分类3.年龄4.课程级别5.资讯分类6.标签 7.关键字
     *
     * @return type - 0.艺术分类1.活动分类2.培训分类3.年龄4.课程级别5.资讯分类6.标签 7.关键字
     */
    public String getType() {
        return type;
    }

    /**
     * 设置0.艺术分类1.活动分类2.培训分类3.年龄4.课程级别5.资讯分类6.标签 7.关键字
     *
     * @param type 0.艺术分类1.活动分类2.培训分类3.年龄4.课程级别5.资讯分类6.标签 7.关键字
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取分类说明
     *
     * @return typmemo - 分类说明
     */
    public String getTypmemo() {
        return typmemo;
    }

    /**
     * 设置分类说明
     *
     * @param typmemo 分类说明
     */
    public void setTypmemo(String typmemo) {
        this.typmemo = typmemo;
    }

    /**
     * 获取报名限制.0-不限制,1-1人只能报一次
     *
     * @return typbmcfg - 报名限制.0-不限制,1-1人只能报一次
     */
    public Integer getTypbmcfg() {
        return typbmcfg;
    }

    /**
     * 设置报名限制.0-不限制,1-1人只能报一次
     *
     * @param typbmcfg 报名限制.0-不限制,1-1人只能报一次
     */
    public void setTypbmcfg(Integer typbmcfg) {
        this.typbmcfg = typbmcfg;
    }
}