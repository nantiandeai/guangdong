package com.creatoo.hn.model;

import javax.persistence.*;

@Table(name = "wh_rolepms")
public class WhRolepms {
    /**
     * 主键
     */
    @Id
    private String rpmid;

    /**
     * 角色标识
     */
    private String rpmroleid;

    /**
     * 权限字符串
     */
    private String rpmstr;

    /**
     * 获取主键
     *
     * @return rpmid - 主键
     */
    public String getRpmid() {
        return rpmid;
    }

    /**
     * 设置主键
     *
     * @param rpmid 主键
     */
    public void setRpmid(String rpmid) {
        this.rpmid = rpmid;
    }

    /**
     * 获取角色标识
     *
     * @return rpmroleid - 角色标识
     */
    public String getRpmroleid() {
        return rpmroleid;
    }

    /**
     * 设置角色标识
     *
     * @param rpmroleid 角色标识
     */
    public void setRpmroleid(String rpmroleid) {
        this.rpmroleid = rpmroleid;
    }

    /**
     * 获取权限字符串
     *
     * @return rpmstr - 权限字符串
     */
    public String getRpmstr() {
        return rpmstr;
    }

    /**
     * 设置权限字符串
     *
     * @param rpmstr 权限字符串
     */
    public void setRpmstr(String rpmstr) {
        this.rpmstr = rpmstr;
    }
}