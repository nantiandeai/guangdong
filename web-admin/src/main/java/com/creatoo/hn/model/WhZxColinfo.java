package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "wh_zx_colinfo")
public class WhZxColinfo {
    /**
     * 栏目内容id
     */
    @Id
    private String clnfid;

    /**
     * 标题
     */
    private String clnftltle;

    /**
     * 创立时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date clnfcrttime;
    /**
     * 是否上首页
     */
    private Integer upindex;
    /**
     * 来源
     */
    private String clnfsource;

    /**
     * 作者
     */
    private String clnfauthor;

    /**
     * 列表图
     */
    private String clnfpic;

    /**
     * 详情图
     */
    private String clnfbigpic;

    /**
     * 简介
     */
    private String clnfintroduce;

    /**
     * 上首页0否1是
     */
    private Integer clnfghp;

    /**
     * 上首页排序
     */
    private Integer clnfidx;

    /**
     * 改变状态操作时间
     */
    private Date clnfopttime;

    /**
     * 状态0.1未审2审核3发布
     */
    private Integer clnfstata;

    /**
     * 栏目类型
     */
    private String clnftype;

    /**
     * 访问量
     */
    private Integer clnfbrowse;

    /**
     * 详细内容
     */
    private String clnfdetail;
    
    /**
     * 栏目关键字
     */
    private String clnfkey;

    /**
     * 所属文化馆
     */
    private String clnvenueid;
    /**
     * 置顶
     */
    private Integer totop;

    public String getClnvenueid() {
        return clnvenueid;
    }

    public void setClnvenueid(String clnvenueid) {
        this.clnvenueid = clnvenueid;
    }

    /**
     * 栏目关键字
     */
    public String getClnfkey() {
		return clnfkey;
	}
    /**
     * 栏目关键字
     */
	public void setClnfkey(String clnfkey) {
		this.clnfkey = clnfkey;
	}

	/**
     * 获取栏目内容id
     *
     * @return clnfid - 栏目内容id
     */
    public String getClnfid() {
        return clnfid;
    }

    /**
     * 设置栏目内容id
     *
     * @param clnfid 栏目内容id
     */
    public void setClnfid(String clnfid) {
        this.clnfid = clnfid;
    }

    /**
     * 获取标题
     *
     * @return clnftltle - 标题
     */
    public String getClnftltle() {
        return clnftltle;
    }

    /**
     * 设置标题
     *
     * @param clnftltle 标题
     */
    public void setClnftltle(String clnftltle) {
        this.clnftltle = clnftltle;
    }

    /**
     * 获取创立时间
     *
     * @return clnfcrttime - 创立时间
     */
    public Date getClnfcrttime() {
        return clnfcrttime;
    }

    /**
     * 设置创立时间
     *
     * @param clnfcrttime 创立时间
     */
    public void setClnfcrttime(Date clnfcrttime) {
        this.clnfcrttime = clnfcrttime;
    }

    /**
     * 获取来源
     *
     * @return clnfsource - 来源
     */
    public String getClnfsource() {
        return clnfsource;
    }

    /**
     * 设置来源
     *
     * @param clnfsource 来源
     */
    public void setClnfsource(String clnfsource) {
        this.clnfsource = clnfsource;
    }

    /**
     * 获取作者
     *
     * @return clnfauthor - 作者
     */
    public String getClnfauthor() {
        return clnfauthor;
    }

    /**
     * 设置作者
     *
     * @param clnfauthor 作者
     */
    public void setClnfauthor(String clnfauthor) {
        this.clnfauthor = clnfauthor;
    }

    /**
     * 获取列表图
     *
     * @return clnfpic - 列表图
     */
    public String getClnfpic() {
        return clnfpic;
    }

    /**
     * 设置列表图
     *
     * @param clnfpic 列表图
     */
    public void setClnfpic(String clnfpic) {
        this.clnfpic = clnfpic;
    }

    /**
     * 获取详情图
     *
     * @return clnfbigpic - 详情图
     */
    public String getClnfbigpic() {
        return clnfbigpic;
    }

    /**
     * 设置详情图
     *
     * @param clnfbigpic 详情图
     */
    public void setClnfbigpic(String clnfbigpic) {
        this.clnfbigpic = clnfbigpic;
    }

    /**
     * 获取简介
     *
     * @return clnfintroduce - 简介
     */
    public String getClnfintroduce() {
        return clnfintroduce;
    }

    /**
     * 设置简介
     *
     * @param clnfintroduce 简介
     */
    public void setClnfintroduce(String clnfintroduce) {
        this.clnfintroduce = clnfintroduce;
    }

    /**
     * 获取上首页0否1是
     *
     * @return clnfghp - 上首页0否1是
     */
    public Integer getClnfghp() {
        return clnfghp;
    }

    /**
     * 设置上首页0否1是
     *
     * @param clnfghp 上首页0否1是
     */
    public void setClnfghp(Integer clnfghp) {
        this.clnfghp = clnfghp;
    }

    /**
     * 获取上首页排序
     *
     * @return clnfidx - 上首页排序
     */
    public Integer getClnfidx() {
        return clnfidx;
    }

    /**
     * 设置上首页排序
     *
     * @param clnfidx 上首页排序
     */
    public void setClnfidx(Integer clnfidx) {
        this.clnfidx = clnfidx;
    }

    /**
     * 获取改变状态操作时间
     *
     * @return clnfopttime - 改变状态操作时间
     */
    public Date getClnfopttime() {
        return clnfopttime;
    }

    /**
     * 设置改变状态操作时间
     *
     * @param clnfopttime 改变状态操作时间
     */
    public void setClnfopttime(Date clnfopttime) {
        this.clnfopttime = clnfopttime;
    }

    /**
     * 获取状态0.1未审2审核3发布
     *
     * @return clnfstata - 状态0.1未审2审核3发布
     */
    public Integer getClnfstata() {
        return clnfstata;
    }

    /**
     * 设置状态0.1未审2审核3发布
     *
     * @param clnfstata 状态0.1未审2审核3发布
     */
    public void setClnfstata(Integer clnfstata) {
        this.clnfstata = clnfstata;
    }

    /**
     * 获取栏目类型
     *
     * @return clnftype - 栏目类型
     */
    public String getClnftype() {
        return clnftype;
    }

    /**
     * 设置栏目类型
     *
     * @param clnftype 栏目类型
     */
    public void setClnftype(String clnftype) {
        this.clnftype = clnftype;
    }

    /**
     * 获取访问量
     *
     * @return clnfbrowse - 访问量
     */
    public Integer getClnfbrowse() {
        return clnfbrowse;
    }

    /**
     * 设置访问量
     *
     * @param clnfbrowse 访问量
     */
    public void setClnfbrowse(Integer clnfbrowse) {
        this.clnfbrowse = clnfbrowse;
    }

    /**
     * 获取详细内容
     *
     * @return clnfdetail - 详细内容
     */
    public String getClnfdetail() {
        return clnfdetail;
    }

    /**
     * 设置详细内容
     *
     * @param clnfdetail 详细内容
     */
    public void setClnfdetail(String clnfdetail) {
        this.clnfdetail = clnfdetail;
    }

    public Integer getTotop() {
        return totop;
    }

    public void setTotop(Integer totop) {
        this.totop = totop;
    }

    public Integer getUpindex() {
        return upindex;
    }

    public void setUpindex(Integer upindex) {
        this.upindex = upindex;
    }
}