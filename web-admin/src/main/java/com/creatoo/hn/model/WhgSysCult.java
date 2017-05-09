package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "whg_sys_cult")
public class WhgSysCult {
    @Id
    private String id;

    private Date crtdate;

    private String crtuser;

    private Integer state;

    private Date statemdfdate;

    private String statemdfuser;

    private Integer delstate;

    private String name;

    /**
     * 所属区域
     */
    private String area;

    private String picture;

    /**
     * LOGO
     */
    private String logopicture;

    private String bgpicture;

    private String introduction;

    private String contact;

    private String contactnum;

    private String memo;

    /**
     * 文化馆地址
     */
    private String address;

    /**
     * 文化馆站点地址
     */
    private String siteurl;

    /**
     * 排序值
     */
    private Integer idx;

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

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取所属区域
     *
     * @return area - 所属区域
     */
    public String getArea() {
        return area;
    }

    /**
     * 设置所属区域
     *
     * @param area 所属区域
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * @return picture
     */
    public String getPicture() {
        return picture;
    }

    /**
     * @param picture
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * 获取LOGO
     *
     * @return logopicture - LOGO
     */
    public String getLogopicture() {
        return logopicture;
    }

    /**
     * 设置LOGO
     *
     * @param logopicture LOGO
     */
    public void setLogopicture(String logopicture) {
        this.logopicture = logopicture;
    }

    /**
     * @return bgpicture
     */
    public String getBgpicture() {
        return bgpicture;
    }

    /**
     * @param bgpicture
     */
    public void setBgpicture(String bgpicture) {
        this.bgpicture = bgpicture;
    }

    /**
     * @return introduction
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * @param introduction
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * @return contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * @param contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * @return contactnum
     */
    public String getContactnum() {
        return contactnum;
    }

    /**
     * @param contactnum
     */
    public void setContactnum(String contactnum) {
        this.contactnum = contactnum;
    }

    /**
     * @return memo
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @param memo
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * 获取文化馆地址
     *
     * @return address - 文化馆地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置文化馆地址
     *
     * @param address 文化馆地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取文化馆站点地址
     *
     * @return siteurl - 文化馆站点地址
     */
    public String getSiteurl() {
        return siteurl;
    }

    /**
     * 设置文化馆站点地址
     *
     * @param siteurl 文化馆站点地址
     */
    public void setSiteurl(String siteurl) {
        this.siteurl = siteurl;
    }

    /**
     * 获取排序值
     *
     * @return idx - 排序值
     */
    public Integer getIdx() {
        return idx;
    }

    /**
     * 设置排序值
     *
     * @param idx 排序值
     */
    public void setIdx(Integer idx) {
        this.idx = idx;
    }
}