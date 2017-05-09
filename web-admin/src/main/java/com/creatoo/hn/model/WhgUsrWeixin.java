package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "whg_usr_weixin")
public class WhgUsrWeixin {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * PC用户主键. 为whuser.id
     */
    private String userid;

    /**
     * 网页登录微信ID
     */
    private String unionid;

    /**
     * 微信登录ID
     */
    private String openid;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 国家
     */
    private String country;

    /**
     * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
     */
    private String headimgurl;

    /**
     * 添加时间
     */
    private Date crtdate;

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
     * 获取PC用户主键. 为whuser.id
     *
     * @return userid - PC用户主键. 为whuser.id
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置PC用户主键. 为whuser.id
     *
     * @param userid PC用户主键. 为whuser.id
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * 获取网页登录微信ID
     *
     * @return unionid - 网页登录微信ID
     */
    public String getUnionid() {
        return unionid;
    }

    /**
     * 设置网页登录微信ID
     *
     * @param unionid 网页登录微信ID
     */
    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    /**
     * 获取微信登录ID
     *
     * @return openid - 微信登录ID
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * 设置微信登录ID
     *
     * @param openid 微信登录ID
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /**
     * 获取用户昵称
     *
     * @return nickname - 用户昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置用户昵称
     *
     * @param nickname 用户昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取性别
     *
     * @return sex - 性别
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置性别
     *
     * @param sex 性别
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取省
     *
     * @return province - 省
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置省
     *
     * @param province 省
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取市
     *
     * @return city - 市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置市
     *
     * @param city 市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取国家
     *
     * @return country - 国家
     */
    public String getCountry() {
        return country;
    }

    /**
     * 设置国家
     *
     * @param country 国家
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 获取用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
     *
     * @return headimgurl - 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
     */
    public String getHeadimgurl() {
        return headimgurl;
    }

    /**
     * 设置用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
     *
     * @param headimgurl 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
     */
    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    /**
     * 获取添加时间
     *
     * @return crtdate - 添加时间
     */
    public Date getCrtdate() {
        return crtdate;
    }

    /**
     * 设置添加时间
     *
     * @param crtdate 添加时间
     */
    public void setCrtdate(Date crtdate) {
        this.crtdate = crtdate;
    }
}