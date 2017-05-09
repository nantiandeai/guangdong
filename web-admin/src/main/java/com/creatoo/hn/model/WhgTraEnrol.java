package com.creatoo.hn.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "whg_tra_enrol")
public class WhgTraEnrol {
    /**
     * 培训报名ID
     */
    @Id
    private String id;

    /**
     * 订单ID
     */
    private String orderid;

    /**
     * 修改状态的时间
     */
    private Date statemdfdate;

    /**
     * 状态.1-报名申请中;2-取消报名;3-审核失败;4-等待面试;5-面试不通过; 6-报名成功.
     */
    private Integer state;

    /**
     * 修改状态的管理员ID
     */
    private String statemdfuser;

    /**
     * 修改状态操作原因
     */
    private String statedesc;

    /**
     * 培训ID
     */
    private String traid;

    /**
     * 报名的会员ID
     */
    private String userid;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 籍贯
     */
    private String nativeplace;

    /**
     * 出生年月日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 性别：2-女； 1-男; 3-保密
     */
    private Integer sex;

    /**
     * 民族
     */
    private String nation;

    /**
     * 身份证号码
     */
    private String cardno;

    /**
     * 通讯地址
     */
    private String contactaddr;

    /**
     * 手机号码
     */
    private String contactphone;

    /**
     * 工作单位
     */
    private String workunit;

    /**
     * 班级编号
     */
    private String classno;

    /**
     * 个人简历
     */
    private String resume;

    /**
     * 从事文艺活动简介
     */
    private String specialtyart;

    /**
     * 报名时间
     */
    private Date crttime;

    /**
     * 证件类型
     */
    private Integer crettype;

    /**
     * 获取培训报名ID
     *
     * @return id - 培训报名ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置培训报名ID
     *
     * @param id 培训报名ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取订单ID
     *
     * @return orderid - 订单ID
     */
    public String getOrderid() {
        return orderid;
    }

    /**
     * 设置订单ID
     *
     * @param orderid 订单ID
     */
    public void setOrderid(String orderid) {
        this.orderid = orderid;
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
     * 获取状态.1-报名申请中;2-取消报名;3-审核失败;4-等待面试;5-面试不通过; 6-报名成功.
     *
     * @return state - 状态.1-报名申请中;2-取消报名;3-审核失败;4-等待面试;5-面试不通过; 6-报名成功.
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置状态.1-报名申请中;2-取消报名;3-审核失败;4-等待面试;5-面试不通过; 6-报名成功.
     *
     * @param state 状态.1-报名申请中;2-取消报名;3-审核失败;4-等待面试;5-面试不通过; 6-报名成功.
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取修改状态的管理员ID
     *
     * @return statemdfuser - 修改状态的管理员ID
     */
    public String getStatemdfuser() {
        return statemdfuser;
    }

    /**
     * 设置修改状态的管理员ID
     *
     * @param statemdfuser 修改状态的管理员ID
     */
    public void setStatemdfuser(String statemdfuser) {
        this.statemdfuser = statemdfuser;
    }

    /**
     * 获取修改状态操作原因
     *
     * @return statedesc - 修改状态操作原因
     */
    public String getStatedesc() {
        return statedesc;
    }

    /**
     * 设置修改状态操作原因
     *
     * @param statedesc 修改状态操作原因
     */
    public void setStatedesc(String statedesc) {
        this.statedesc = statedesc;
    }

    /**
     * 获取培训ID
     *
     * @return traid - 培训ID
     */
    public String getTraid() {
        return traid;
    }

    /**
     * 设置培训ID
     *
     * @param traid 培训ID
     */
    public void setTraid(String traid) {
        this.traid = traid;
    }

    /**
     * 获取报名的会员ID
     *
     * @return userid - 报名的会员ID
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置报名的会员ID
     *
     * @param userid 报名的会员ID
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * 获取真实姓名
     *
     * @return realname - 真实姓名
     */
    public String getRealname() {
        return realname;
    }

    /**
     * 设置真实姓名
     *
     * @param realname 真实姓名
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }

    /**
     * 获取籍贯
     *
     * @return nativeplace - 籍贯
     */
    public String getNativeplace() {
        return nativeplace;
    }

    /**
     * 设置籍贯
     *
     * @param nativeplace 籍贯
     */
    public void setNativeplace(String nativeplace) {
        this.nativeplace = nativeplace;
    }

    /**
     * 获取出生年月日
     *
     * @return birthday - 出生年月日
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 设置出生年月日
     *
     * @param birthday 出生年月日
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取性别：2-女； 1-男; 3-保密
     *
     * @return sex - 性别：2-女； 1-男; 3-保密
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置性别：2-女； 1-男; 3-保密
     *
     * @param sex 性别：2-女； 1-男; 3-保密
     */
    public void setSex(Integer sex) {
        this.sex = sex;
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
     * 获取身份证号码
     *
     * @return cardno - 身份证号码
     */
    public String getCardno() {
        return cardno;
    }

    /**
     * 设置身份证号码
     *
     * @param cardno 身份证号码
     */
    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    /**
     * 获取通讯地址
     *
     * @return contactaddr - 通讯地址
     */
    public String getContactaddr() {
        return contactaddr;
    }

    /**
     * 设置通讯地址
     *
     * @param contactaddr 通讯地址
     */
    public void setContactaddr(String contactaddr) {
        this.contactaddr = contactaddr;
    }

    /**
     * 获取手机号码
     *
     * @return contactphone - 手机号码
     */
    public String getContactphone() {
        return contactphone;
    }

    /**
     * 设置手机号码
     *
     * @param contactphone 手机号码
     */
    public void setContactphone(String contactphone) {
        this.contactphone = contactphone;
    }

    /**
     * 获取工作单位
     *
     * @return workunit - 工作单位
     */
    public String getWorkunit() {
        return workunit;
    }

    /**
     * 设置工作单位
     *
     * @param workunit 工作单位
     */
    public void setWorkunit(String workunit) {
        this.workunit = workunit;
    }

    /**
     * 获取班级编号
     *
     * @return classno - 班级编号
     */
    public String getClassno() {
        return classno;
    }

    /**
     * 设置班级编号
     *
     * @param classno 班级编号
     */
    public void setClassno(String classno) {
        this.classno = classno;
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
     * @return specialtyart - 从事文艺活动简介
     */
    public String getSpecialtyart() {
        return specialtyart;
    }

    /**
     * 设置从事文艺活动简介
     *
     * @param specialtyart 从事文艺活动简介
     */
    public void setSpecialtyart(String specialtyart) {
        this.specialtyart = specialtyart;
    }

    /**
     * 获取报名时间
     *
     * @return crttime - 报名时间
     */
    public Date getCrttime() {
        return crttime;
    }

    /**
     * 设置报名时间
     *
     * @param crttime 报名时间
     */
    public void setCrttime(Date crttime) {
        this.crttime = crttime;
    }


    public Integer getCrettype() {
        return crettype;
    }

    public void setCrettype(Integer crettype) {
        this.crettype = crettype;
    }
}