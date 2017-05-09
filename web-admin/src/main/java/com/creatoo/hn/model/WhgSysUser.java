package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "whg_sys_user")
public class WhgSysUser {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 创建时间
     */
    private Date crtdate;

    /**
     * 创建用户
     */
    private String crtuser;

    /**
     * 状态. 0-不正常, 1-正常
     */
    private Integer state;

    /**
     * 修改状态的时间
     */
    private Date statemdfdate;

    /**
     * 修改状态的用户
     */
    private String statemdfuser;

    /**
     * 删除状态. 0-未删除, 1-已删除
     */
    private Integer delstate;

    /**
     * 登录账号
     */
    private String account;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 手机号
     */
    private String contactnum;

    /**
     * 最后登录时间
     */
    private Date lastlogintime;

    /**
     * 所属文化馆
     */
    private String cultid;

    /**
     * 所属部门
     */
    private String deptid;

    /**
     * 权限标识符:文化馆ID_部门code，场馆/活动/培训资讯维护时要写入此字段
     */
    private String epms;

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
     * 获取创建时间
     *
     * @return crtdate - 创建时间
     */
    public Date getCrtdate() {
        return crtdate;
    }

    /**
     * 设置创建时间
     *
     * @param crtdate 创建时间
     */
    public void setCrtdate(Date crtdate) {
        this.crtdate = crtdate;
    }

    /**
     * 获取创建用户
     *
     * @return crtuser - 创建用户
     */
    public String getCrtuser() {
        return crtuser;
    }

    /**
     * 设置创建用户
     *
     * @param crtuser 创建用户
     */
    public void setCrtuser(String crtuser) {
        this.crtuser = crtuser;
    }

    /**
     * 获取状态. 0-不正常, 1-正常
     *
     * @return state - 状态. 0-不正常, 1-正常
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置状态. 0-不正常, 1-正常
     *
     * @param state 状态. 0-不正常, 1-正常
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取修改状态的时间
     *
     * @return statemdfdate - 修改状态的时间
     */
    public Date getStatemdfdate() {
        return statemdfdate;
    }

    /**
     * 设置修改状态的时间
     *
     * @param statemdfdate 修改状态的时间
     */
    public void setStatemdfdate(Date statemdfdate) {
        this.statemdfdate = statemdfdate;
    }

    /**
     * 获取修改状态的用户
     *
     * @return statemdfuser - 修改状态的用户
     */
    public String getStatemdfuser() {
        return statemdfuser;
    }

    /**
     * 设置修改状态的用户
     *
     * @param statemdfuser 修改状态的用户
     */
    public void setStatemdfuser(String statemdfuser) {
        this.statemdfuser = statemdfuser;
    }

    /**
     * 获取删除状态. 0-未删除, 1-已删除
     *
     * @return delstate - 删除状态. 0-未删除, 1-已删除
     */
    public Integer getDelstate() {
        return delstate;
    }

    /**
     * 设置删除状态. 0-未删除, 1-已删除
     *
     * @param delstate 删除状态. 0-未删除, 1-已删除
     */
    public void setDelstate(Integer delstate) {
        this.delstate = delstate;
    }

    /**
     * 获取登录账号
     *
     * @return account - 登录账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置登录账号
     *
     * @param account 登录账号
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取登录密码
     *
     * @return password - 登录密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置登录密码
     *
     * @param password 登录密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取手机号
     *
     * @return contactnum - 手机号
     */
    public String getContactnum() {
        return contactnum;
    }

    /**
     * 设置手机号
     *
     * @param contactnum 手机号
     */
    public void setContactnum(String contactnum) {
        this.contactnum = contactnum;
    }

    /**
     * 获取最后登录时间
     *
     * @return lastlogintime - 最后登录时间
     */
    public Date getLastlogintime() {
        return lastlogintime;
    }

    /**
     * 设置最后登录时间
     *
     * @param lastlogintime 最后登录时间
     */
    public void setLastlogintime(Date lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    /**
     * 获取所属文化馆
     *
     * @return cultid - 所属文化馆
     */
    public String getCultid() {
        return cultid;
    }

    /**
     * 设置所属文化馆
     *
     * @param cultid 所属文化馆
     */
    public void setCultid(String cultid) {
        this.cultid = cultid;
    }

    /**
     * 获取所属部门
     *
     * @return deptid - 所属部门
     */
    public String getDeptid() {
        return deptid;
    }

    /**
     * 设置所属部门
     *
     * @param deptid 所属部门
     */
    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    /**
     * 获取权限标识符:文化馆ID_部门code，场馆/活动/培训资讯维护时要写入此字段
     *
     * @return epms - 权限标识符:文化馆ID_部门code，场馆/活动/培训资讯维护时要写入此字段
     */
    public String getEpms() {
        return epms;
    }

    /**
     * 设置权限标识符:文化馆ID_部门code，场馆/活动/培训资讯维护时要写入此字段
     *
     * @param epms 权限标识符:文化馆ID_部门code，场馆/活动/培训资讯维护时要写入此字段
     */
    public void setEpms(String epms) {
        this.epms = epms;
    }
}