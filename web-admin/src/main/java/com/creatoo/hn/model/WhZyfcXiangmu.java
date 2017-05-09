package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "wh_zyfc_xiangmu")
public class WhZyfcXiangmu {
    /**
     * 主键标识
     */
    @Id
    private String zyfcxmid;

    /**
     * 详情标题
     */
    private String zyfcxmtitle;

    /**
     * 列表短标题
     */
    private String zyfcxmshorttitle;

    /**
     * 列表图片
     */
    private String zyfcxmpic;

    /**
     * 详情图片
     */
    private String zyfcxmbigpic;

    /**
     * 志愿者数量
     */
    private Integer zyfcxmpnum;

    /**
     * 服务人数量
     */
    private Integer zyfcxmanum;

    /**
     * 服务地区
     */
    private String zyfcxmscope;

    /**
     * 实施时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date zyfcxmsstime;

    /**
     * 实施单位
     */
    private String zyfcxmssdw;

    /**
     * 修改状态时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date zyfcxmopttime;

    /**
     * 状态:0-编辑;1-送审;2-审核;3-发布.
     */
    private Integer zyfcxmstate;

    /**
     * 所属文化馆标识
     */
    private String zyfcxvenueid;

    /**
     * 项目实施内容
     */
    private String zyfcxmcontent;

    /**
     * 获取主键标识
     *
     * @return zyfcxmid - 主键标识
     */
    public String getZyfcxmid() {
        return zyfcxmid;
    }

    /**
     * 设置主键标识
     *
     * @param zyfcxmid 主键标识
     */
    public void setZyfcxmid(String zyfcxmid) {
        this.zyfcxmid = zyfcxmid;
    }

    /**
     * 获取详情标题
     *
     * @return zyfcxmtitle - 详情标题
     */
    public String getZyfcxmtitle() {
        return zyfcxmtitle;
    }

    /**
     * 设置详情标题
     *
     * @param zyfcxmtitle 详情标题
     */
    public void setZyfcxmtitle(String zyfcxmtitle) {
        this.zyfcxmtitle = zyfcxmtitle;
    }

    /**
     * 获取列表短标题
     *
     * @return zyfcxmshorttitle - 列表短标题
     */
    public String getZyfcxmshorttitle() {
        return zyfcxmshorttitle;
    }

    /**
     * 设置列表短标题
     *
     * @param zyfcxmshorttitle 列表短标题
     */
    public void setZyfcxmshorttitle(String zyfcxmshorttitle) {
        this.zyfcxmshorttitle = zyfcxmshorttitle;
    }

    /**
     * 获取列表图片
     *
     * @return zyfcxmpic - 列表图片
     */
    public String getZyfcxmpic() {
        return zyfcxmpic;
    }

    /**
     * 设置列表图片
     *
     * @param zyfcxmpic 列表图片
     */
    public void setZyfcxmpic(String zyfcxmpic) {
        this.zyfcxmpic = zyfcxmpic;
    }

    /**
     * 获取详情图片
     *
     * @return zyfcxmbigpic - 详情图片
     */
    public String getZyfcxmbigpic() {
        return zyfcxmbigpic;
    }

    /**
     * 设置详情图片
     *
     * @param zyfcxmbigpic 详情图片
     */
    public void setZyfcxmbigpic(String zyfcxmbigpic) {
        this.zyfcxmbigpic = zyfcxmbigpic;
    }

    /**
     * 获取志愿者数量
     *
     * @return zyfcxmpnum - 志愿者数量
     */
    public Integer getZyfcxmpnum() {
        return zyfcxmpnum;
    }

    /**
     * 设置志愿者数量
     *
     * @param zyfcxmpnum 志愿者数量
     */
    public void setZyfcxmpnum(Integer zyfcxmpnum) {
        this.zyfcxmpnum = zyfcxmpnum;
    }

    /**
     * 获取服务人数量
     *
     * @return zyfcxmanum - 服务人数量
     */
    public Integer getZyfcxmanum() {
        return zyfcxmanum;
    }

    /**
     * 设置服务人数量
     *
     * @param zyfcxmanum 服务人数量
     */
    public void setZyfcxmanum(Integer zyfcxmanum) {
        this.zyfcxmanum = zyfcxmanum;
    }

    /**
     * 获取服务地区
     *
     * @return zyfcxmscope - 服务地区
     */
    public String getZyfcxmscope() {
        return zyfcxmscope;
    }

    /**
     * 设置服务地区
     *
     * @param zyfcxmscope 服务地区
     */
    public void setZyfcxmscope(String zyfcxmscope) {
        this.zyfcxmscope = zyfcxmscope;
    }

    /**
     * 获取实施时间
     *
     * @return zyfcxmsstime - 实施时间
     */
    public Date getZyfcxmsstime() {
        return zyfcxmsstime;
    }

    /**
     * 设置实施时间
     *
     * @param zyfcxmsstime 实施时间
     */
    public void setZyfcxmsstime(Date zyfcxmsstime) {
        this.zyfcxmsstime = zyfcxmsstime;
    }

    /**
     * 获取实施单位
     *
     * @return zyfcxmssdw - 实施单位
     */
    public String getZyfcxmssdw() {
        return zyfcxmssdw;
    }

    /**
     * 设置实施单位
     *
     * @param zyfcxmssdw 实施单位
     */
    public void setZyfcxmssdw(String zyfcxmssdw) {
        this.zyfcxmssdw = zyfcxmssdw;
    }

    /**
     * 获取修改状态时间
     *
     * @return zyfcxmopttime - 修改状态时间
     */
    public Date getZyfcxmopttime() {
        return zyfcxmopttime;
    }

    /**
     * 设置修改状态时间
     *
     * @param zyfcxmopttime 修改状态时间
     */
    public void setZyfcxmopttime(Date zyfcxmopttime) {
        this.zyfcxmopttime = zyfcxmopttime;
    }

    /**
     * 获取状态:0-编辑;1-送审;2-审核;3-发布.
     *
     * @return zyfcxmstate - 状态:0-编辑;1-送审;2-审核;3-发布.
     */
    public Integer getZyfcxmstate() {
        return zyfcxmstate;
    }

    /**
     * 设置状态:0-编辑;1-送审;2-审核;3-发布.
     *
     * @param zyfcxmstate 状态:0-编辑;1-送审;2-审核;3-发布.
     */
    public void setZyfcxmstate(Integer zyfcxmstate) {
        this.zyfcxmstate = zyfcxmstate;
    }

    /**
     * 获取所属文化馆标识
     *
     * @return zyfcxvenueid - 所属文化馆标识
     */
    public String getZyfcxvenueid() {
        return zyfcxvenueid;
    }

    /**
     * 设置所属文化馆标识
     *
     * @param zyfcxvenueid 所属文化馆标识
     */
    public void setZyfcxvenueid(String zyfcxvenueid) {
        this.zyfcxvenueid = zyfcxvenueid;
    }

    /**
     * 获取项目实施内容
     *
     * @return zyfcxmcontent - 项目实施内容
     */
    public String getZyfcxmcontent() {
        return zyfcxmcontent;
    }

    /**
     * 设置项目实施内容
     *
     * @param zyfcxmcontent 项目实施内容
     */
    public void setZyfcxmcontent(String zyfcxmcontent) {
        this.zyfcxmcontent = zyfcxmcontent;
    }
}