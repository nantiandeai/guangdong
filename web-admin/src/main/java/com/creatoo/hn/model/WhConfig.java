package com.creatoo.hn.model;

import javax.persistence.*;

@Table(name = "wh_config")
public class WhConfig {
    /**
     * 配置id
     */
    @Id
    private String sysid;

    /**
     * 配置项
     */
    private String syskey;

    /**
     * 配置值
     */
    private String sysval;

    /**
     * 配置状态
     */
    private String systate;

    /**
     * 配置状态
     */
    private String systype;

    /**
     * 配置说明
     */
    private String sysmome;

    /**
     * 获取配置id
     *
     * @return sysid - 配置id
     */
    public String getSysid() {
        return sysid;
    }

    /**
     * 设置配置id
     *
     * @param sysid 配置id
     */
    public void setSysid(String sysid) {
        this.sysid = sysid;
    }

    /**
     * 获取配置项
     *
     * @return syskey - 配置项
     */
    public String getSyskey() {
        return syskey;
    }

    /**
     * 设置配置项
     *
     * @param syskey 配置项
     */
    public void setSyskey(String syskey) {
        this.syskey = syskey;
    }

    /**
     * 获取配置值
     *
     * @return sysval - 配置值
     */
    public String getSysval() {
        return sysval;
    }

    /**
     * 设置配置值
     *
     * @param sysval 配置值
     */
    public void setSysval(String sysval) {
        this.sysval = sysval;
    }

    /**
     * 获取配置状态
     *
     * @return systate - 配置状态
     */
    public String getSystate() {
        return systate;
    }

    /**
     * 设置配置状态
     *
     * @param systate 配置状态
     */
    public void setSystate(String systate) {
        this.systate = systate;
    }

    /**
     * 获取配置状态
     *
     * @return systype - 配置状态
     */
    public String getSystype() {
        return systype;
    }

    /**
     * 设置配置状态
     *
     * @param systype 配置状态
     */
    public void setSystype(String systype) {
        this.systype = systype;
    }

    /**
     * 获取配置说明
     *
     * @return sysmome - 配置说明
     */
    public String getSysmome() {
        return sysmome;
    }

    /**
     * 设置配置说明
     *
     * @param sysmome 配置说明
     */
    public void setSysmome(String sysmome) {
        this.sysmome = sysmome;
    }
}