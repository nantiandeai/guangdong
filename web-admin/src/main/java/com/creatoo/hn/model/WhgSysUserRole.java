package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "whg_sys_user_role")
public class WhgSysUserRole {
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
     * 管理员ID
     */
    private String sysuserid;

    /**
     * 岗位ID
     */
    private String sysroleid;

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
     * 获取管理员ID
     *
     * @return sysuserid - 管理员ID
     */
    public String getSysuserid() {
        return sysuserid;
    }

    /**
     * 设置管理员ID
     *
     * @param sysuserid 管理员ID
     */
    public void setSysuserid(String sysuserid) {
        this.sysuserid = sysuserid;
    }

    /**
     * 获取岗位ID
     *
     * @return sysroleid - 岗位ID
     */
    public String getSysroleid() {
        return sysroleid;
    }

    /**
     * 设置岗位ID
     *
     * @param sysroleid 岗位ID
     */
    public void setSysroleid(String sysroleid) {
        this.sysroleid = sysroleid;
    }
}