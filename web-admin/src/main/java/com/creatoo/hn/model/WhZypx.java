package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "wh_zypx")
public class WhZypx {
    /**
     * 主键标识
     */
    @Id
    private String zypxid;

    /**
     * 分类,在whtyp中增加配置
     */
    private String zypxtype;

    /**
     * 关键字,在whtyp和whkey中配置
     */
    private String zypxkey;

    /**
     * 详情标题
     */
    private String zypxtitle;

    /**
     * 列表短标题
     */
    private String zypxshorttitle;

    /**
     * 来源
     */
    private String zypxfrom;

    /**
     * 列表图片
     */
    private String zypxpic;

    /**
     * 视频地址
     */
    private String zypxvideo;

    /**
     * 视频时长
     */
    private Integer zypxvideolen;

    /**
     * 修改状态时间
     */
    private Date zypxopttime;

    /**
     * 状态:0-编辑;1-送审;2-审核;3-发布.
     */
    private Integer zypxstate;

    /**
     * 详情内容
     */
    private String zypxcontent;

    /**
     * 获取主键标识
     *
     * @return zypxid - 主键标识
     */
    public String getZypxid() {
        return zypxid;
    }

    /**
     * 设置主键标识
     *
     * @param zypxid 主键标识
     */
    public void setZypxid(String zypxid) {
        this.zypxid = zypxid;
    }

    /**
     * 获取分类,在whtyp中增加配置
     *
     * @return zypxtype - 分类,在whtyp中增加配置
     */
    public String getZypxtype() {
        return zypxtype;
    }

    /**
     * 设置分类,在whtyp中增加配置
     *
     * @param zypxtype 分类,在whtyp中增加配置
     */
    public void setZypxtype(String zypxtype) {
        this.zypxtype = zypxtype;
    }

    /**
     * 获取关键字,在whtyp和whkey中配置
     *
     * @return zypxkey - 关键字,在whtyp和whkey中配置
     */
    public String getZypxkey() {
        return zypxkey;
    }

    /**
     * 设置关键字,在whtyp和whkey中配置
     *
     * @param zypxkey 关键字,在whtyp和whkey中配置
     */
    public void setZypxkey(String zypxkey) {
        this.zypxkey = zypxkey;
    }

    /**
     * 获取详情标题
     *
     * @return zypxtitle - 详情标题
     */
    public String getZypxtitle() {
        return zypxtitle;
    }

    /**
     * 设置详情标题
     *
     * @param zypxtitle 详情标题
     */
    public void setZypxtitle(String zypxtitle) {
        this.zypxtitle = zypxtitle;
    }

    /**
     * 获取列表短标题
     *
     * @return zypxshorttitle - 列表短标题
     */
    public String getZypxshorttitle() {
        return zypxshorttitle;
    }

    /**
     * 设置列表短标题
     *
     * @param zypxshorttitle 列表短标题
     */
    public void setZypxshorttitle(String zypxshorttitle) {
        this.zypxshorttitle = zypxshorttitle;
    }

    /**
     * 获取来源
     *
     * @return zypxfrom - 来源
     */
    public String getZypxfrom() {
        return zypxfrom;
    }

    /**
     * 设置来源
     *
     * @param zypxfrom 来源
     */
    public void setZypxfrom(String zypxfrom) {
        this.zypxfrom = zypxfrom;
    }

    /**
     * 获取列表图片
     *
     * @return zypxpic - 列表图片
     */
    public String getZypxpic() {
        return zypxpic;
    }

    /**
     * 设置列表图片
     *
     * @param zypxpic 列表图片
     */
    public void setZypxpic(String zypxpic) {
        this.zypxpic = zypxpic;
    }

    /**
     * 获取视频地址
     *
     * @return zypxvideo - 视频地址
     */
    public String getZypxvideo() {
        return zypxvideo;
    }

    /**
     * 设置视频地址
     *
     * @param zypxvideo 视频地址
     */
    public void setZypxvideo(String zypxvideo) {
        this.zypxvideo = zypxvideo;
    }

    /**
     * 获取视频时长
     *
     * @return zypxvideolen - 视频时长
     */
    public Integer getZypxvideolen() {
        return zypxvideolen;
    }

    /**
     * 设置视频时长
     *
     * @param zypxvideolen 视频时长
     */
    public void setZypxvideolen(Integer zypxvideolen) {
        this.zypxvideolen = zypxvideolen;
    }

    /**
     * 获取修改状态时间
     *
     * @return zypxopttime - 修改状态时间
     */
    public Date getZypxopttime() {
        return zypxopttime;
    }

    /**
     * 设置修改状态时间
     *
     * @param zypxopttime 修改状态时间
     */
    public void setZypxopttime(Date zypxopttime) {
        this.zypxopttime = zypxopttime;
    }

    /**
     * 获取状态:0-编辑;1-送审;2-审核;3-发布.
     *
     * @return zypxstate - 状态:0-编辑;1-送审;2-审核;3-发布.
     */
    public Integer getZypxstate() {
        return zypxstate;
    }

    /**
     * 设置状态:0-编辑;1-送审;2-审核;3-发布.
     *
     * @param zypxstate 状态:0-编辑;1-送审;2-审核;3-发布.
     */
    public void setZypxstate(Integer zypxstate) {
        this.zypxstate = zypxstate;
    }

    /**
     * 获取详情内容
     *
     * @return zypxcontent - 详情内容
     */
    public String getZypxcontent() {
        return zypxcontent;
    }

    /**
     * 设置详情内容
     *
     * @param zypxcontent 详情内容
     */
    public void setZypxcontent(String zypxcontent) {
        this.zypxcontent = zypxcontent;
    }
}