package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "whg_usr_backlist")
public class WhgUsrBacklist {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 会员ID
     */
    private String userid;

    /**
     * 会员手机号
     */
    private String userphone;

    /**
     * 黑名单类型。1-多次取消
     */
    private Integer type;

    /**
     * 加入黑名单时间
     */
    private Date jointime;

    /**
     * 状态. 1-有效黑名单， 0-无效黑名单
     */
    private Integer state;

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
     * 获取会员ID
     *
     * @return userid - 会员ID
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置会员ID
     *
     * @param userid 会员ID
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * 获取会员手机号
     *
     * @return userphone - 会员手机号
     */
    public String getUserphone() {
        return userphone;
    }

    /**
     * 设置会员手机号
     *
     * @param userphone 会员手机号
     */
    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    /**
     * 获取黑名单类型。1-多次取消
     *
     * @return type - 黑名单类型。1-多次取消
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置黑名单类型。1-多次取消
     *
     * @param type 黑名单类型。1-多次取消
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取加入黑名单时间
     *
     * @return jointime - 加入黑名单时间
     */
    public Date getJointime() {
        return jointime;
    }

    /**
     * 设置加入黑名单时间
     *
     * @param jointime 加入黑名单时间
     */
    public void setJointime(Date jointime) {
        this.jointime = jointime;
    }

    /**
     * 获取状态. 1-有效黑名单， 0-无效黑名单
     *
     * @return state - 状态. 1-有效黑名单， 0-无效黑名单
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置状态. 1-有效黑名单， 0-无效黑名单
     *
     * @param state 状态. 1-有效黑名单， 0-无效黑名单
     */
    public void setState(Integer state) {
        this.state = state;
    }
}