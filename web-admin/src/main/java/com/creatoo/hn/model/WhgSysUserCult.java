package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "whg_sys_user_cult")
public class WhgSysUserCult {
    @Id
    private String id;

    /**
     * 管理员ID
     */
    private String sysuserid;

    /**
     * 分馆ID
     */
    private String syscultid;

    private Date crtdate;

    private String crtuser;

    private Integer state;

    private Date statemdfdate;

    private String statemdfuser;

    private Integer delstate;

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
     * 获取分馆ID
     *
     * @return syscultid - 分馆ID
     */
    public String getSyscultid() {
        return syscultid;
    }

    /**
     * 设置分馆ID
     *
     * @param syscultid 分馆ID
     */
    public void setSyscultid(String syscultid) {
        this.syscultid = syscultid;
    }

    /**
     * @return crtdate
     */
    public Date getCrtdate() {
        return crtdate;
    }

    /**
     * @param crtdate
     */
    public void setCrtdate(Date crtdate) {
        this.crtdate = crtdate;
    }

    /**
     * @return crtuser
     */
    public String getCrtuser() {
        return crtuser;
    }

    /**
     * @param crtuser
     */
    public void setCrtuser(String crtuser) {
        this.crtuser = crtuser;
    }

    /**
     * @return state
     */
    public Integer getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * @return statemdfdate
     */
    public Date getStatemdfdate() {
        return statemdfdate;
    }

    /**
     * @param statemdfdate
     */
    public void setStatemdfdate(Date statemdfdate) {
        this.statemdfdate = statemdfdate;
    }

    /**
     * @return statemdfuser
     */
    public String getStatemdfuser() {
        return statemdfuser;
    }

    /**
     * @param statemdfuser
     */
    public void setStatemdfuser(String statemdfuser) {
        this.statemdfuser = statemdfuser;
    }

    /**
     * @return delstate
     */
    public Integer getDelstate() {
        return delstate;
    }

    /**
     * @param delstate
     */
    public void setDelstate(Integer delstate) {
        this.delstate = delstate;
    }
}