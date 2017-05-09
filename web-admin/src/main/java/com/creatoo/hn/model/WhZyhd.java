package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "wh_zyhd")
public class WhZyhd {
    /**
     * 主键标识
     */
    @Id
    private String zyhdid;

    /**
     * 志愿活动区域
     */
    private String zyhdarea;

    /**
     * 自愿活动分类,在whtyp中增加配置
     */
    private String zyhdtype;

    /**
     * 详情标题
     */
    private String zyhdtitle;

    /**
     * 列表短标题
     */
    private String zyhdshorttitle;

    /**
     * 服务地区
     */
    private String zyhdscope;

    /**
     * 列表图片
     */
    private String zyhdpic;

    /**
     * 详情图片
     */
    private String zyhdbigpic;

    /**
     * 发起文化馆
     */
    private String zyhdstart;

    /**
     * 活动地址
     */
    private String zyhdaddr;

    /**
     * 报名开始时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date zyhdsdate;

    /**
     * 报名结束时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date zyhdedate;

    /**
     * 修改状态时间
     */
    private Date zyhdopttime;

    /**
     * 状态:0-编辑;1-送审;2-审核;3-发布.
     */
    private Integer zyhdstate;

    /**
     * 详情内容
     */
    private String zyhdcontent;

    /**
     * 获取主键标识
     *
     * @return zyhdid - 主键标识
     */
    public String getZyhdid() {
        return zyhdid;
    }

    /**
     * 设置主键标识
     *
     * @param zyhdid 主键标识
     */
    public void setZyhdid(String zyhdid) {
        this.zyhdid = zyhdid;
    }

    /**
     * 获取志愿活动区域
     *
     * @return zyhdarea - 志愿活动区域
     */
    public String getZyhdarea() {
        return zyhdarea;
    }

    /**
     * 设置志愿活动区域
     *
     * @param zyhdarea 志愿活动区域
     */
    public void setZyhdarea(String zyhdarea) {
        this.zyhdarea = zyhdarea;
    }

    /**
     * 获取自愿活动分类,在whtyp中增加配置
     *
     * @return zyhdtype - 自愿活动分类,在whtyp中增加配置
     */
    public String getZyhdtype() {
        return zyhdtype;
    }

    /**
     * 设置自愿活动分类,在whtyp中增加配置
     *
     * @param zyhdtype 自愿活动分类,在whtyp中增加配置
     */
    public void setZyhdtype(String zyhdtype) {
        this.zyhdtype = zyhdtype;
    }

    /**
     * 获取详情标题
     *
     * @return zyhdtitle - 详情标题
     */
    public String getZyhdtitle() {
        return zyhdtitle;
    }

    /**
     * 设置详情标题
     *
     * @param zyhdtitle 详情标题
     */
    public void setZyhdtitle(String zyhdtitle) {
        this.zyhdtitle = zyhdtitle;
    }

    /**
     * 获取列表短标题
     *
     * @return zyhdshorttitle - 列表短标题
     */
    public String getZyhdshorttitle() {
        return zyhdshorttitle;
    }

    /**
     * 设置列表短标题
     *
     * @param zyhdshorttitle 列表短标题
     */
    public void setZyhdshorttitle(String zyhdshorttitle) {
        this.zyhdshorttitle = zyhdshorttitle;
    }

    /**
     * 获取服务地区
     *
     * @return zyhdscope - 服务地区
     */
    public String getZyhdscope() {
        return zyhdscope;
    }

    /**
     * 设置服务地区
     *
     * @param zyhdscope 服务地区
     */
    public void setZyhdscope(String zyhdscope) {
        this.zyhdscope = zyhdscope;
    }

    /**
     * 获取列表图片
     *
     * @return zyhdpic - 列表图片
     */
    public String getZyhdpic() {
        return zyhdpic;
    }

    /**
     * 设置列表图片
     *
     * @param zyhdpic 列表图片
     */
    public void setZyhdpic(String zyhdpic) {
        this.zyhdpic = zyhdpic;
    }

    /**
     * 获取详情图片
     *
     * @return zyhdbigpic - 详情图片
     */
    public String getZyhdbigpic() {
        return zyhdbigpic;
    }

    /**
     * 设置详情图片
     *
     * @param zyhdbigpic 详情图片
     */
    public void setZyhdbigpic(String zyhdbigpic) {
        this.zyhdbigpic = zyhdbigpic;
    }

    /**
     * 获取发起文化馆
     *
     * @return zyhdstart - 发起文化馆
     */
    public String getZyhdstart() {
        return zyhdstart;
    }

    /**
     * 设置发起文化馆
     *
     * @param zyhdstart 发起文化馆
     */
    public void setZyhdstart(String zyhdstart) {
        this.zyhdstart = zyhdstart;
    }

    /**
     * 获取活动地址
     *
     * @return zyhdaddr - 活动地址
     */
    public String getZyhdaddr() {
        return zyhdaddr;
    }

    /**
     * 设置活动地址
     *
     * @param zyhdaddr 活动地址
     */
    public void setZyhdaddr(String zyhdaddr) {
        this.zyhdaddr = zyhdaddr;
    }

    /**
     * 获取报名开始时间
     *
     * @return zyhdsdate - 报名开始时间
     */
    public Date getZyhdsdate() {
        return zyhdsdate;
    }

    /**
     * 设置报名开始时间
     *
     * @param zyhdsdate 报名开始时间
     */
    public void setZyhdsdate(Date zyhdsdate) {
        this.zyhdsdate = zyhdsdate;
    }

    /**
     * 获取报名结束时间
     *
     * @return zyhdedate - 报名结束时间
     */
    public Date getZyhdedate() {
        return zyhdedate;
    }

    /**
     * 设置报名结束时间
     *
     * @param zyhdedate 报名结束时间
     */
    public void setZyhdedate(Date zyhdedate) {
        this.zyhdedate = zyhdedate;
    }

    /**
     * 获取修改状态时间
     *
     * @return zyhdopttime - 修改状态时间
     */
    public Date getZyhdopttime() {
        return zyhdopttime;
    }

    /**
     * 设置修改状态时间
     *
     * @param zyhdopttime 修改状态时间
     */
    public void setZyhdopttime(Date zyhdopttime) {
        this.zyhdopttime = zyhdopttime;
    }

    /**
     * 获取状态:0-编辑;1-送审;2-审核;3-发布.
     *
     * @return zyhdstate - 状态:0-编辑;1-送审;2-审核;3-发布.
     */
    public Integer getZyhdstate() {
        return zyhdstate;
    }

    /**
     * 设置状态:0-编辑;1-送审;2-审核;3-发布.
     *
     * @param zyhdstate 状态:0-编辑;1-送审;2-审核;3-发布.
     */
    public void setZyhdstate(Integer zyhdstate) {
        this.zyhdstate = zyhdstate;
    }

    /**
     * 获取详情内容
     *
     * @return zyhdcontent - 详情内容
     */
    public String getZyhdcontent() {
        return zyhdcontent;
    }

    /**
     * 设置详情内容
     *
     * @param zyhdcontent 详情内容
     */
    public void setZyhdcontent(String zyhdcontent) {
        this.zyhdcontent = zyhdcontent;
    }
}