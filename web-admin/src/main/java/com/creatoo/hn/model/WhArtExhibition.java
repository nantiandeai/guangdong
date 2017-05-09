package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "wh_art_exhibition")
public class WhArtExhibition {
    /**
     * 艺术展id
     */
    @Id
    private String exhid;

    /**
     * 艺术展标题
     */
    private String exhtitle;

    /**
     * 对应艺术类型:音乐书法等
     */
    private String exharttyp;

    /**
     * 艺术展类型
     */
    private String exhtype;

    /**
     * 艺术展图片
     */
    private String exhpic;

    /**
     * 艺术展简介
     */
    private String exhdesc;

    /**
     * 艺术展开始时间
     */
    private Date exhstime;

    /**
     * 艺术展结束时间
     */
    private Date exhetime;
    /**
     * 改变状态操作时间
     */
    private Date exhtime;

    /**
     * 上首页标识
     */
    private Integer exhghp;

    /**
     * 上首页排序
     */
    private Integer exhidx;

    /**
     * 艺术展状态
     */
    private Integer exhstate;
    
    
    
    /**
     * 改变状态操作时间
     */
    public Date getExhtime() {
		return exhtime;
	}
    /**
     * 改变状态操作时间
     */
	public void setExhtime(Date exhtime) {
		this.exhtime = exhtime;
	}

	/**
     * 获取艺术展id
     *
     * @return exhid - 艺术展id
     */
    public String getExhid() {
        return exhid;
    }

    /**
     * 设置艺术展id
     *
     * @param exhid 艺术展id
     */
    public void setExhid(String exhid) {
        this.exhid = exhid;
    }

    /**
     * 获取艺术展标题
     *
     * @return exhtitle - 艺术展标题
     */
    public String getExhtitle() {
        return exhtitle;
    }

    /**
     * 设置艺术展标题
     *
     * @param exhtitle 艺术展标题
     */
    public void setExhtitle(String exhtitle) {
        this.exhtitle = exhtitle;
    }

    /**
     * 获取对应艺术类型:音乐书法等
     *
     * @return exharttyp - 对应艺术类型:音乐书法等
     */
    public String getExharttyp() {
        return exharttyp;
    }

    /**
     * 设置对应艺术类型:音乐书法等
     *
     * @param exharttyp 对应艺术类型:音乐书法等
     */
    public void setExharttyp(String exharttyp) {
        this.exharttyp = exharttyp;
    }

    /**
     * 获取艺术展类型
     *
     * @return exhtype - 艺术展类型
     */
    public String getExhtype() {
        return exhtype;
    }

    /**
     * 设置艺术展类型
     *
     * @param exhtype 艺术展类型
     */
    public void setExhtype(String exhtype) {
        this.exhtype = exhtype;
    }

    /**
     * 获取艺术展图片
     *
     * @return exhpic - 艺术展图片
     */
    public String getExhpic() {
        return exhpic;
    }

    /**
     * 设置艺术展图片
     *
     * @param exhpic 艺术展图片
     */
    public void setExhpic(String exhpic) {
        this.exhpic = exhpic;
    }

    /**
     * 获取艺术展简介
     *
     * @return exhdesc - 艺术展简介
     */
    public String getExhdesc() {
        return exhdesc;
    }

    /**
     * 设置艺术展简介
     *
     * @param exhdesc 艺术展简介
     */
    public void setExhdesc(String exhdesc) {
        this.exhdesc = exhdesc;
    }

    /**
     * 获取艺术展开始时间
     *
     * @return exhstime - 艺术展开始时间
     */
    public Date getExhstime() {
        return exhstime;
    }

    /**
     * 设置艺术展开始时间
     *
     * @param exhstime 艺术展开始时间
     */
    public void setExhstime(Date exhstime) {
        this.exhstime = exhstime;
    }

    /**
     * 获取艺术展结束时间
     *
     * @return exhetime - 艺术展结束时间
     */
    public Date getExhetime() {
        return exhetime;
    }

    /**
     * 设置艺术展结束时间
     *
     * @param exhetime 艺术展结束时间
     */
    public void setExhetime(Date exhetime) {
        this.exhetime = exhetime;
    }

    /**
     * 获取上首页标识
     *
     * @return exhghp - 上首页标识
     */
    public Integer getExhghp() {
        return exhghp;
    }

    /**
     * 设置上首页标识
     *
     * @param exhghp 上首页标识
     */
    public void setExhghp(Integer exhghp) {
        this.exhghp = exhghp;
    }

    /**
     * 获取上首页排序
     *
     * @return exhidx - 上首页排序
     */
    public Integer getExhidx() {
        return exhidx;
    }

    /**
     * 设置上首页排序
     *
     * @param exhidx 上首页排序
     */
    public void setExhidx(Integer exhidx) {
        this.exhidx = exhidx;
    }

    /**
     * 获取艺术展状态
     *
     * @return exhstate - 艺术展状态
     */
    public Integer getExhstate() {
        return exhstate;
    }

    /**
     * 设置艺术展状态
     *
     * @param exhstate 艺术展状态
     */
    public void setExhstate(Integer exhstate) {
        this.exhstate = exhstate;
    }
}