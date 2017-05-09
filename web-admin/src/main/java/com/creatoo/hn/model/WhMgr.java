package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "wh_mgr")
public class WhMgr {
    @Id
    private String id;

    /**
     * 用户名
     */
    private String name;

    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 电话
     */
    private String phone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 最后登录时间
     */
    private Date lastdate;

    private String roleid;

    /**
     * 状态.0-无效;1-有效
     */
    private Integer status;

    /**
     * 文化馆标识
     */
    private String venueid;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取用户名
     *
     * @return name - 用户名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置用户名
     *
     * @param name 用户名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取昵称
     *
     * @return nickname - 昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置昵称
     *
     * @param nickname 昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取电话
     *
     * @return phone - 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话
     *
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取电子邮箱
     *
     * @return email - 电子邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置电子邮箱
     *
     * @param email 电子邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取最后登录时间
     *
     * @return lastdate - 最后登录时间
     */
    public Date getLastdate() {
        return lastdate;
    }

    /**
     * 设置最后登录时间
     *
     * @param lastdate 最后登录时间
     */
    public void setLastdate(Date lastdate) {
        this.lastdate = lastdate;
    }

    /**
     * @return roleid
     */
    public String getRoleid() {
        return roleid;
    }

    /**
     * @param roleid
     */
    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    /**
     * 获取状态.0-无效;1-有效
     *
     * @return status - 状态.0-无效;1-有效
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态.0-无效;1-有效
     *
     * @param status 状态.0-无效;1-有效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取文化馆标识
     *
     * @return venueid - 文化馆标识
     */
    public String getVenueid() {
        return venueid;
    }

    /**
     * 设置文化馆标识
     *
     * @param venueid 文化馆标识
     */
    public void setVenueid(String venueid) {
        this.venueid = venueid;
    }
}