package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "wh_wechat")
public class WhWechat {
    @Id
    private String wechatid;

    private String openid;

    private String unionid;

    private String nickname;

    private String sex;

    private String province;

    private String city;

    private String country;

    private String headimgurl;

    private String accesstoken;

    private String refreshcode;

    private Integer tokenexpiry;

    private Date refreshtime;

    private Date createdtime;

    private Date updatedtime;

    /**
     * @return wechatid
     */
    public String getWechatid() {
        return wechatid;
    }

    /**
     * @param wechatid
     */
    public void setWechatid(String wechatid) {
        this.wechatid = wechatid;
    }

    /**
     * @return openid
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * @param openid
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /**
     * @return unionid
     */
    public String getUnionid() {
        return unionid;
    }

    /**
     * @param unionid
     */
    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    /**
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return province
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return headimgurl
     */
    public String getHeadimgurl() {
        return headimgurl;
    }

    /**
     * @param headimgurl
     */
    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    /**
     * @return accesstoken
     */
    public String getAccesstoken() {
        return accesstoken;
    }

    /**
     * @param accesstoken
     */
    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    /**
     * @return refreshcode
     */
    public String getRefreshcode() {
        return refreshcode;
    }

    /**
     * @param refreshcode
     */
    public void setRefreshcode(String refreshcode) {
        this.refreshcode = refreshcode;
    }

    /**
     * @return tokenexpiry
     */
    public Integer getTokenexpiry() {
        return tokenexpiry;
    }

    /**
     * @param tokenexpiry
     */
    public void setTokenexpiry(Integer tokenexpiry) {
        this.tokenexpiry = tokenexpiry;
    }

    /**
     * @return refreshtime
     */
    public Date getRefreshtime() {
        return refreshtime;
    }

    /**
     * @param refreshtime
     */
    public void setRefreshtime(Date refreshtime) {
        this.refreshtime = refreshtime;
    }

    /**
     * @return createdtime
     */
    public Date getCreatedtime() {
        return createdtime;
    }

    /**
     * @param createdtime
     */
    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }

    /**
     * @return updatedtime
     */
    public Date getUpdatedtime() {
        return updatedtime;
    }

    /**
     * @param updatedtime
     */
    public void setUpdatedtime(Date updatedtime) {
        this.updatedtime = updatedtime;
    }
}