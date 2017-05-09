package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "whg_ywi_key")
public class WhgYwiKey {
    /**
     * 关键字ID
     */
    @Id
    private String id;

    /**
     * 关键字名称
     */
    private String name;

    /**
     * 关键字类型（1、培训 2、活动 3、场馆）
     */
    private String type;

    /**
     * 创建人
     */
    private String crtuser;

    /**
     * 创建时间
     */
    private Date crtdate;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 状态变更时间
     */
    private Date statemdfdate;

    /**
     * 状态变更用户ID
     */
    private String statemdfuser;

    /**
     * 删除状态
     */
    private Integer delstate;

    /**
     * 排序
     */
    private Integer idx;

    /**
     * 获取关键字ID
     *
     * @return id - 关键字ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置关键字ID
     *
     * @param id 关键字ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取关键字名称
     *
     * @return name - 关键字名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置关键字名称
     *
     * @param name 关键字名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取关键字类型（1、培训 2、活动 3、场馆）
     *
     * @return type - 关键字类型（1、培训 2、活动 3、场馆）
     */
    public String getType() {
        return type;
    }

    /**
     * 设置关键字类型（1、培训 2、活动 3、场馆）
     *
     * @param type 关键字类型（1、培训 2、活动 3、场馆）
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取创建人
     *
     * @return crtuser - 创建人
     */
    public String getCrtuser() {
        return crtuser;
    }

    /**
     * 设置创建人
     *
     * @param crtuser 创建人
     */
    public void setCrtuser(String crtuser) {
        this.crtuser = crtuser;
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
     * 获取状态
     *
     * @return state - 状态
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置状态
     *
     * @param state 状态
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取状态变更时间
     *
     * @return statemdfdate - 状态变更时间
     */
    public Date getStatemdfdate() {
        return statemdfdate;
    }

    /**
     * 设置状态变更时间
     *
     * @param statemdfdate 状态变更时间
     */
    public void setStatemdfdate(Date statemdfdate) {
        this.statemdfdate = statemdfdate;
    }

    /**
     * 获取状态变更用户ID
     *
     * @return statemdfuser - 状态变更用户ID
     */
    public String getStatemdfuser() {
        return statemdfuser;
    }

    /**
     * 设置状态变更用户ID
     *
     * @param statemdfuser 状态变更用户ID
     */
    public void setStatemdfuser(String statemdfuser) {
        this.statemdfuser = statemdfuser;
    }

    /**
     * 获取删除状态
     *
     * @return delstate - 删除状态
     */
    public Integer getDelstate() {
        return delstate;
    }

    /**
     * 设置删除状态
     *
     * @param delstate 删除状态
     */
    public void setDelstate(Integer delstate) {
        this.delstate = delstate;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }
}