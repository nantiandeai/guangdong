package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "whg_ywi_sms")
public class WhgYwiSms {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 短信内容
     */
    private String smscontent;

    /**
     * 发送时间
     */
    private Date senddate;

    /**
     * 状态. 0-发送失败, 1-发送成功
     */
    private Integer sendstate;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取手机号
     *
     * @return phone - 手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号
     *
     * @param phone 手机号
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取短信内容
     *
     * @return smscontent - 短信内容
     */
    public String getSmscontent() {
        return smscontent;
    }

    /**
     * 设置短信内容
     *
     * @param smscontent 短信内容
     */
    public void setSmscontent(String smscontent) {
        this.smscontent = smscontent;
    }

    /**
     * 获取发送时间
     *
     * @return senddate - 发送时间
     */
    public Date getSenddate() {
        return senddate;
    }

    /**
     * 设置发送时间
     *
     * @param senddate 发送时间
     */
    public void setSenddate(Date senddate) {
        this.senddate = senddate;
    }

    /**
     * 获取状态. 0-发送失败, 1-发送成功
     *
     * @return sendstate - 状态. 0-发送失败, 1-发送成功
     */
    public Integer getSendstate() {
        return sendstate;
    }

    /**
     * 设置状态. 0-发送失败, 1-发送成功
     *
     * @param sendstate 状态. 0-发送失败, 1-发送成功
     */
    public void setSendstate(Integer sendstate) {
        this.sendstate = sendstate;
    }
}