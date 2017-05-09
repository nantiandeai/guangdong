package com.creatoo.hn.model;

import javax.persistence.*;

@Table(name = "wh_feedback")
public class WhFeedback {
    /**
     * id
     */
    @Id
    private String feedid;

    /**
     * 姓名
     */
    private String feedname;

    /**
     * 单位
     */
    private String feedcom;

    /**
     * 电话号码
     */
    private String feedphone;

    /**
     * 邮箱
     */
    private String feedmail;

    /**
     * 意见信息
     */
    private String feeddesc;

    /**
     * 获取id
     *
     * @return feedid - id
     */
    public String getFeedid() {
        return feedid;
    }

    /**
     * 设置id
     *
     * @param feedid id
     */
    public void setFeedid(String feedid) {
        this.feedid = feedid;
    }

    /**
     * 获取姓名
     *
     * @return feedname - 姓名
     */
    public String getFeedname() {
        return feedname;
    }

    /**
     * 设置姓名
     *
     * @param feedname 姓名
     */
    public void setFeedname(String feedname) {
        this.feedname = feedname;
    }

    /**
     * 获取单位
     *
     * @return feedcom - 单位
     */
    public String getFeedcom() {
        return feedcom;
    }

    /**
     * 设置单位
     *
     * @param feedcom 单位
     */
    public void setFeedcom(String feedcom) {
        this.feedcom = feedcom;
    }

    /**
     * 获取电话号码
     *
     * @return feedphone - 电话号码
     */
    public String getFeedphone() {
        return feedphone;
    }

    /**
     * 设置电话号码
     *
     * @param feedphone 电话号码
     */
    public void setFeedphone(String feedphone) {
        this.feedphone = feedphone;
    }

    /**
     * 获取邮箱
     *
     * @return feedmail - 邮箱
     */
    public String getFeedmail() {
        return feedmail;
    }

    /**
     * 设置邮箱
     *
     * @param feedmail 邮箱
     */
    public void setFeedmail(String feedmail) {
        this.feedmail = feedmail;
    }

    /**
     * 获取意见信息
     *
     * @return feeddesc - 意见信息
     */
    public String getFeeddesc() {
        return feeddesc;
    }

    /**
     * 设置意见信息
     *
     * @param feeddesc 意见信息
     */
    public void setFeeddesc(String feeddesc) {
        this.feeddesc = feeddesc;
    }
}