package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "wh_user")
public class WhUser {
    @Id
    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 最后登录时间
     */
    private Date lastdate;

    /**
     * 性别 0：女  1：男
     */
    private String sex;

    /**
     * 职业
     */
    private String job;

    /**
     * 出生日期
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    /**
     * qq账号
     */
    private String qq;

    /**
     * 微信账号
     */
    private String wx;

    /**
     * 注册密码
     */
    private String password;

    /**
     * 民族
     */
    private String nation;

    /**
     * 籍贯
     */
    private String origo;

    /**
     * 工作单位
     */
    private String company;

    /**
     * 通讯地址
     */
    private String address;

    /**
     * 个人简历
     */
    private String resume;

    /**
     * 从事文艺活动简介
     */
    @Column(name = "actBrief")
    private String actbrief;

    /**
     * 是否实名 0:否 1：是
     */
    private Integer isrealname;

    /**
     * 是否完善资料 0：否 1：是
     */
    private Integer isperfect;

    /**
     * 身份证号码
     */
    private String idcard;

    /**
     * 身份证正面图
     */
    private String idcardface;

    /**
     * 身份证背面
     */
    private String idcardback;

    /**
     * 实名审核被打回的消息
     */
    private String checkmsg;

    /**
     * QQ用户唯一标识
     */
    private String openid;

    /**
     * 内部员工：0-否 1-是
     */
    private Integer isinner;

    /**
     * 内部员工描述
     */
    private String innerdesc;

    /**
     * 所属文化馆标识
     */
    private String venueid;

    /**
     * 用户的微博标识
     */
    private String wid;
    
    /**
     * 用户的微信标识
     */
    private String wxopenid;

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
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
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
     * 获取电话号码
     *
     * @return phone - 电话号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话号码
     *
     * @param phone 电话号码
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取电子邮件
     *
     * @return email - 电子邮件
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置电子邮件
     *
     * @param email 电子邮件
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
     * 获取性别 0：女  1：男
     *
     * @return sex - 性别 0：女  1：男
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别 0：女  1：男
     *
     * @param sex 性别 0：女  1：男
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取职业
     *
     * @return job - 职业
     */
    public String getJob() {
        return job;
    }

    /**
     * 设置职业
     *
     * @param job 职业
     */
    public void setJob(String job) {
        this.job = job;
    }

    /**
     * 获取出生日期
     *
     * @return birthday - 出生日期
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 设置出生日期
     *
     * @param birthday 出生日期
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取qq账号
     *
     * @return qq - qq账号
     */
    public String getQq() {
        return qq;
    }

    /**
     * 设置qq账号
     *
     * @param qq qq账号
     */
    public void setQq(String qq) {
        this.qq = qq;
    }

    /**
     * 获取微信账号
     *
     * @return wx - 微信账号
     */
    public String getWx() {
        return wx;
    }

    /**
     * 设置微信账号
     *
     * @param wx 微信账号
     */
    public void setWx(String wx) {
        this.wx = wx;
    }

    /**
     * 获取注册密码
     *
     * @return password - 注册密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置注册密码
     *
     * @param password 注册密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取民族
     *
     * @return nation - 民族
     */
    public String getNation() {
        return nation;
    }

    /**
     * 设置民族
     *
     * @param nation 民族
     */
    public void setNation(String nation) {
        this.nation = nation;
    }

    /**
     * 获取籍贯
     *
     * @return origo - 籍贯
     */
    public String getOrigo() {
        return origo;
    }

    /**
     * 设置籍贯
     *
     * @param origo 籍贯
     */
    public void setOrigo(String origo) {
        this.origo = origo;
    }

    /**
     * 获取工作单位
     *
     * @return company - 工作单位
     */
    public String getCompany() {
        return company;
    }

    /**
     * 设置工作单位
     *
     * @param company 工作单位
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * 获取通讯地址
     *
     * @return address - 通讯地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置通讯地址
     *
     * @param address 通讯地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取个人简历
     *
     * @return resume - 个人简历
     */
    public String getResume() {
        return resume;
    }

    /**
     * 设置个人简历
     *
     * @param resume 个人简历
     */
    public void setResume(String resume) {
        this.resume = resume;
    }

    /**
     * 获取从事文艺活动简介
     *
     * @return actBrief - 从事文艺活动简介
     */
    public String getActbrief() {
        return actbrief;
    }

    /**
     * 设置从事文艺活动简介
     *
     * @param actbrief 从事文艺活动简介
     */
    public void setActbrief(String actbrief) {
        this.actbrief = actbrief;
    }

    /**
     * 获取是否实名 0:否 1：是
     *
     * @return isrealname - 是否实名 0:否 1：是
     */
    public Integer getIsrealname() {
        return isrealname;
    }

    /**
     * 设置是否实名 0:否 1：是
     *
     * @param isrealname 是否实名 0:否 1：是
     */
    public void setIsrealname(Integer isrealname) {
        this.isrealname = isrealname;
    }

    /**
     * 获取是否完善资料 0：否 1：是
     *
     * @return isperfect - 是否完善资料 0：否 1：是
     */
    public Integer getIsperfect() {
        return isperfect;
    }

    /**
     * 设置是否完善资料 0：否 1：是
     *
     * @param isperfect 是否完善资料 0：否 1：是
     */
    public void setIsperfect(Integer isperfect) {
        this.isperfect = isperfect;
    }

    /**
     * 获取身份证号码
     *
     * @return idcard - 身份证号码
     */
    public String getIdcard() {
        return idcard;
    }

    /**
     * 设置身份证号码
     *
     * @param idcard 身份证号码
     */
    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    /**
     * 获取身份证正面图
     *
     * @return idcardface - 身份证正面图
     */
    public String getIdcardface() {
        return idcardface;
    }

    /**
     * 设置身份证正面图
     *
     * @param idcardface 身份证正面图
     */
    public void setIdcardface(String idcardface) {
        this.idcardface = idcardface;
    }

    /**
     * 获取身份证背面
     *
     * @return idcardback - 身份证背面
     */
    public String getIdcardback() {
        return idcardback;
    }

    /**
     * 设置身份证背面
     *
     * @param idcardback 身份证背面
     */
    public void setIdcardback(String idcardback) {
        this.idcardback = idcardback;
    }

    /**
     * 获取实名审核被打回的消息
     *
     * @return checkmsg - 实名审核被打回的消息
     */
    public String getCheckmsg() {
        return checkmsg;
    }

    /**
     * 设置实名审核被打回的消息
     *
     * @param checkmsg 实名审核被打回的消息
     */
    public void setCheckmsg(String checkmsg) {
        this.checkmsg = checkmsg;
    }

    /**
     * 获取QQ用户唯一标识
     *
     * @return openid - QQ用户唯一标识
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * 设置QQ用户唯一标识
     *
     * @param openid QQ用户唯一标识
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /**
     * 获取内部员工：0-否 1-是
     *
     * @return isinner - 内部员工：0-否 1-是
     */
    public Integer getIsinner() {
        return isinner;
    }

    /**
     * 设置内部员工：0-否 1-是
     *
     * @param isinner 内部员工：0-否 1-是
     */
    public void setIsinner(Integer isinner) {
        this.isinner = isinner;
    }

    /**
     * 获取内部员工描述
     *
     * @return innerdesc - 内部员工描述
     */
    public String getInnerdesc() {
        return innerdesc;
    }

    /**
     * 设置内部员工描述
     *
     * @param innerdesc 内部员工描述
     */
    public void setInnerdesc(String innerdesc) {
        this.innerdesc = innerdesc;
    }

    /**
     * 获取所属文化馆标识
     *
     * @return venueid - 所属文化馆标识
     */
    public String getVenueid() {
        return venueid;
    }

    /**
     * 设置所属文化馆标识
     *
     * @param venueid 所属文化馆标识
     */
    public void setVenueid(String venueid) {
        this.venueid = venueid;
    }

    /**
     * 获取用户的微博标识
     *
     * @return wid - 用户的微博标识
     */
    public String getWid() {
        return wid;
    }

    /**
     * 设置用户的微博标识
     *
     * @param wid 用户的微博标识
     */
    public void setWid(String wid) {
        this.wid = wid;
    }
    
    /**
     * 获取用户的微信标识
     *
     * @return wxopenid - 用户的微信标识
     */
    public String getWxopenid() {
        return wxopenid;
    }

    /**
     * 设置用户的微信标识
     *
     * @param wxopenid 用户的微信标识
     */
    public void setWxopenid(String wxopenid) {
        this.wxopenid = wxopenid;
    }
}