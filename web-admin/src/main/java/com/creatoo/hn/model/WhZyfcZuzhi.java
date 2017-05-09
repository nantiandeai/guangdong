package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "wh_zyfc_zuzhi")
public class WhZyfcZuzhi {
    /**
     * 主键标识
     */
    @Id
    private String zyfczzid;

    /**
     * 详情标题
     */
    private String zyfczztitle;

    /**
     * 列表短标题
     */
    private String zyfczzshorttitle;

    /**
     * 列表图片
     */
    private String zyfczzpic;

    /**
     * 详情图片
     */
    private String zyfczzbigpic;

    /**
     * 志愿者数量
     */
    private Integer zyfczzpnum;

    /**
     * 活动数量
     */
    private Integer zyfczzanum;

    /**
     * 参与活动
     */
    private String zyfczzjoinact;

    /**
     * 服务地区
     */
    private String zyfczzscope;

    /**
     * 服务时间
     */
    private Integer zyfczzfwtime;

    /**
     * 修改状态时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date zyfczzopttime;

    /**
     * 状态:0-编辑;1-送审;2-审核;3-发布.
     */
    private Integer zyfczzstate;

    /**
     * 组织介绍
     */
    private String zyfczzcontent;

    /**
     * 获取主键标识
     *
     * @return zyfczzid - 主键标识
     */
    public String getZyfczzid() {
        return zyfczzid;
    }

    /**
     * 设置主键标识
     *
     * @param zyfczzid 主键标识
     */
    public void setZyfczzid(String zyfczzid) {
        this.zyfczzid = zyfczzid;
    }

    /**
     * 获取详情标题
     *
     * @return zyfczztitle - 详情标题
     */
    public String getZyfczztitle() {
        return zyfczztitle;
    }

    /**
     * 设置详情标题
     *
     * @param zyfczztitle 详情标题
     */
    public void setZyfczztitle(String zyfczztitle) {
        this.zyfczztitle = zyfczztitle;
    }

    /**
     * 获取列表短标题
     *
     * @return zyfczzshorttitle - 列表短标题
     */
    public String getZyfczzshorttitle() {
        return zyfczzshorttitle;
    }

    /**
     * 设置列表短标题
     *
     * @param zyfczzshorttitle 列表短标题
     */
    public void setZyfczzshorttitle(String zyfczzshorttitle) {
        this.zyfczzshorttitle = zyfczzshorttitle;
    }

    /**
     * 获取列表图片
     *
     * @return zyfczzpic - 列表图片
     */
    public String getZyfczzpic() {
        return zyfczzpic;
    }

    /**
     * 设置列表图片
     *
     * @param zyfczzpic 列表图片
     */
    public void setZyfczzpic(String zyfczzpic) {
        this.zyfczzpic = zyfczzpic;
    }

    /**
     * 获取详情图片
     *
     * @return zyfczzbigpic - 详情图片
     */
    public String getZyfczzbigpic() {
        return zyfczzbigpic;
    }

    /**
     * 设置详情图片
     *
     * @param zyfczzbigpic 详情图片
     */
    public void setZyfczzbigpic(String zyfczzbigpic) {
        this.zyfczzbigpic = zyfczzbigpic;
    }

    /**
     * 获取志愿者数量
     *
     * @return zyfczzpnum - 志愿者数量
     */
    public Integer getZyfczzpnum() {
        return zyfczzpnum;
    }

    /**
     * 设置志愿者数量
     *
     * @param zyfczzpnum 志愿者数量
     */
    public void setZyfczzpnum(Integer zyfczzpnum) {
        this.zyfczzpnum = zyfczzpnum;
    }

    /**
     * 获取活动数量
     *
     * @return zyfczzanum - 活动数量
     */
    public Integer getZyfczzanum() {
        return zyfczzanum;
    }

    /**
     * 设置活动数量
     *
     * @param zyfczzanum 活动数量
     */
    public void setZyfczzanum(Integer zyfczzanum) {
        this.zyfczzanum = zyfczzanum;
    }

    /**
     * 获取参与活动
     *
     * @return zyfczzjoinact - 参与活动
     */
    public String getZyfczzjoinact() {
        return zyfczzjoinact;
    }

    /**
     * 设置参与活动
     *
     * @param zyfczzjoinact 参与活动
     */
    public void setZyfczzjoinact(String zyfczzjoinact) {
        this.zyfczzjoinact = zyfczzjoinact;
    }

    /**
     * 获取服务地区
     *
     * @return zyfczzscope - 服务地区
     */
    public String getZyfczzscope() {
        return zyfczzscope;
    }

    /**
     * 设置服务地区
     *
     * @param zyfczzscope 服务地区
     */
    public void setZyfczzscope(String zyfczzscope) {
        this.zyfczzscope = zyfczzscope;
    }

    /**
     * 获取服务时间
     *
     * @return zyfczzfwtime - 服务时间
     */
    public Integer getZyfczzfwtime() {
        return zyfczzfwtime;
    }

    /**
     * 设置服务时间
     *
     * @param zyfczzfwtime 服务时间
     */
    public void setZyfczzfwtime(Integer zyfczzfwtime) {
        this.zyfczzfwtime = zyfczzfwtime;
    }

    /**
     * 获取修改状态时间
     *
     * @return zyfczzopttime - 修改状态时间
     */
    public Date getZyfczzopttime() {
        return zyfczzopttime;
    }

    /**
     * 设置修改状态时间
     *
     * @param zyfczzopttime 修改状态时间
     */
    public void setZyfczzopttime(Date zyfczzopttime) {
        this.zyfczzopttime = zyfczzopttime;
    }

    /**
     * 获取状态:0-编辑;1-送审;2-审核;3-发布.
     *
     * @return zyfczzstate - 状态:0-编辑;1-送审;2-审核;3-发布.
     */
    public Integer getZyfczzstate() {
        return zyfczzstate;
    }

    /**
     * 设置状态:0-编辑;1-送审;2-审核;3-发布.
     *
     * @param zyfczzstate 状态:0-编辑;1-送审;2-审核;3-发布.
     */
    public void setZyfczzstate(Integer zyfczzstate) {
        this.zyfczzstate = zyfczzstate;
    }

    /**
     * 获取组织介绍
     *
     * @return zyfczzcontent - 组织介绍
     */
    public String getZyfczzcontent() {
        return zyfczzcontent;
    }

    /**
     * 设置组织介绍
     *
     * @param zyfczzcontent 组织介绍
     */
    public void setZyfczzcontent(String zyfczzcontent) {
        this.zyfczzcontent = zyfczzcontent;
    }
}