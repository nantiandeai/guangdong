package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "whg_tra_enrol_course")
public class WhgTraEnrolCourse {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 报名表ID
     */
    private String enrolid;

    /**
     * 培训ID
     */
    private String traid;

    /**
     * 课程ID
     */
    private String courseid;

    /**
     * 课程开始时间
     */
    private Date coursestime;

    /**
     * 课程结束时间
     */
    private Date courseetime;

    /**
     * 签到状态。1-已签到, 0未签到
     */
    private Integer sign;

    private Date signtime;

    private String userid;

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
     * 获取报名表ID
     *
     * @return enrolid - 报名表ID
     */
    public String getEnrolid() {
        return enrolid;
    }

    /**
     * 设置报名表ID
     *
     * @param enrolid 报名表ID
     */
    public void setEnrolid(String enrolid) {
        this.enrolid = enrolid;
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
     * 获取课程ID
     *
     * @return courseid - 课程ID
     */
    public String getCourseid() {
        return courseid;
    }

    /**
     * 设置课程ID
     *
     * @param courseid 课程ID
     */
    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    /**
     * 获取课程开始时间
     *
     * @return coursestime - 课程开始时间
     */
    public Date getCoursestime() {
        return coursestime;
    }

    /**
     * 设置课程开始时间
     *
     * @param coursestime 课程开始时间
     */
    public void setCoursestime(Date coursestime) {
        this.coursestime = coursestime;
    }

    /**
     * 获取课程结束时间
     *
     * @return courseetime - 课程结束时间
     */
    public Date getCourseetime() {
        return courseetime;
    }

    /**
     * 设置课程结束时间
     *
     * @param courseetime 课程结束时间
     */
    public void setCourseetime(Date courseetime) {
        this.courseetime = courseetime;
    }

    /**
     * 获取签到状态。1-已签到, 0未签到
     *
     * @return sign - 签到状态。1-已签到, 0未签到
     */
    public Integer getSign() {
        return sign;
    }

    /**
     * 设置签到状态。1-已签到, 0未签到
     *
     * @param sign 签到状态。1-已签到, 0未签到
     */
    public void setSign(Integer sign) {
        this.sign = sign;
    }

    /**
     * @return signtime
     */
    public Date getSigntime() {
        return signtime;
    }

    /**
     * @param signtime
     */
    public void setSigntime(Date signtime) {
        this.signtime = signtime;
    }

    /**
     * @return userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }
}