package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "wh_subvenue")
public class WhSubvenue {
    /**
     * 子馆标识
     */
    @Id
    private String subid;

    /**
     * 子馆名称
     */
    private String subname;

    /**
     * 子馆列表图片
     */
    private String subpic;

    /**
     * 子馆详情图片
     */
    private String subbigpic;

    /**
     * 子馆简介
     */
    private String subintroduce;

    /**
     * 父馆标识
     */
    private String subpid;

    /**
     * 登记时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date subregtime;

    /**
     * 子馆联系人
     */
    private String subcontact;

    /**
     * 子馆联系手机号
     */
    private String subcontactnum;

    /**
     * 子馆联系邮箱
     */
    private String subcontactemail;

    /**
     * 状态0-编辑;1-送审;2-审核;3-发布;
     */
    private Integer substate;

    /**
     * 修改状态时间
     */
    private Date subopttime;

    /**
     * 获取子馆标识
     *
     * @return subid - 子馆标识
     */
    public String getSubid() {
        return subid;
    }

    /**
     * 设置子馆标识
     *
     * @param subid 子馆标识
     */
    public void setSubid(String subid) {
        this.subid = subid;
    }

    /**
     * 获取子馆名称
     *
     * @return subname - 子馆名称
     */
    public String getSubname() {
        return subname;
    }

    /**
     * 设置子馆名称
     *
     * @param subname 子馆名称
     */
    public void setSubname(String subname) {
        this.subname = subname;
    }

    /**
     * 获取子馆列表图片
     *
     * @return subpic - 子馆列表图片
     */
    public String getSubpic() {
        return subpic;
    }

    /**
     * 设置子馆列表图片
     *
     * @param subpic 子馆列表图片
     */
    public void setSubpic(String subpic) {
        this.subpic = subpic;
    }

    /**
     * 获取子馆详情图片
     *
     * @return subbigpic - 子馆详情图片
     */
    public String getSubbigpic() {
        return subbigpic;
    }

    /**
     * 设置子馆详情图片
     *
     * @param subbigpic 子馆详情图片
     */
    public void setSubbigpic(String subbigpic) {
        this.subbigpic = subbigpic;
    }

    /**
     * 获取子馆简介
     *
     * @return subintroduce - 子馆简介
     */
    public String getSubintroduce() {
        return subintroduce;
    }

    /**
     * 设置子馆简介
     *
     * @param subintroduce 子馆简介
     */
    public void setSubintroduce(String subintroduce) {
        this.subintroduce = subintroduce;
    }

    /**
     * 获取父馆标识
     *
     * @return subpid - 父馆标识
     */
    public String getSubpid() {
        return subpid;
    }

    /**
     * 设置父馆标识
     *
     * @param subpid 父馆标识
     */
    public void setSubpid(String subpid) {
        this.subpid = subpid;
    }

    /**
     * 获取登记时间
     *
     * @return subregtime - 登记时间
     */
    public Date getSubregtime() {
        return subregtime;
    }

    /**
     * 设置登记时间
     *
     * @param subregtime 登记时间
     */
    public void setSubregtime(Date subregtime) {
        this.subregtime = subregtime;
    }

    /**
     * 获取子馆联系人
     *
     * @return subcontact - 子馆联系人
     */
    public String getSubcontact() {
        return subcontact;
    }

    /**
     * 设置子馆联系人
     *
     * @param subcontact 子馆联系人
     */
    public void setSubcontact(String subcontact) {
        this.subcontact = subcontact;
    }

    /**
     * 获取子馆联系手机号
     *
     * @return subcontactnum - 子馆联系手机号
     */
    public String getSubcontactnum() {
        return subcontactnum;
    }

    /**
     * 设置子馆联系手机号
     *
     * @param subcontactnum 子馆联系手机号
     */
    public void setSubcontactnum(String subcontactnum) {
        this.subcontactnum = subcontactnum;
    }

    /**
     * 获取子馆联系邮箱
     *
     * @return subcontactemail - 子馆联系邮箱
     */
    public String getSubcontactemail() {
        return subcontactemail;
    }

    /**
     * 设置子馆联系邮箱
     *
     * @param subcontactemail 子馆联系邮箱
     */
    public void setSubcontactemail(String subcontactemail) {
        this.subcontactemail = subcontactemail;
    }

    /**
     * 获取状态0-编辑;1-送审;2-审核;3-发布;
     *
     * @return substate - 状态0-编辑;1-送审;2-审核;3-发布;
     */
    public Integer getSubstate() {
        return substate;
    }

    /**
     * 设置状态0-编辑;1-送审;2-审核;3-发布;
     *
     * @param substate 状态0-编辑;1-送审;2-审核;3-发布;
     */
    public void setSubstate(Integer substate) {
        this.substate = substate;
    }

    /**
     * 获取修改状态时间
     *
     * @return subopttime - 修改状态时间
     */
    public Date getSubopttime() {
        return subopttime;
    }

    /**
     * 设置修改状态时间
     *
     * @param subopttime 修改状态时间
     */
    public void setSubopttime(Date subopttime) {
        this.subopttime = subopttime;
    }
}