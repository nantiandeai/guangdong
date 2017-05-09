package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "wh_user_teacher")
public class WhUserTeacher {
    /**
     * 培训老师标识
     */
    @Id
    private String teacherid;

    /**
     * 培训老师所属注册用户标识
     */
    private String teacheruid;

    /**
     * 培训老师图片
     */
    private String teacherpic;

    /**
     * 培训老师名称
     */
    private String teachername;

    /**
     * 培训老师专长类型
     */
    private String teachertype;

    /**
     * 培训老师所属区域
     */
    private String teacherarea;

    /**
     * 培训老师艺术类型
     */
    private String teacherarttyp;

    /**
     * 老师课程说明
     */
    private String teachercourse;

    /**
     * 培训老师简介
     */
    private String teacherintroduce;

    /**
     * 专长介绍
     */
    private String teacherexpdesc;

    /**
     * 开课介绍
     */
    private String teacherstartdesc;
    

    /**
     * 注册日期
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date teacherregtime;

    /**
     * 修改状态的时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date teacheropttime;

    /**
     * 状态:0-初始,1-送审,2-已审,3-已发岸上
     */
    private Integer teacherstate;

    /**
     * 获取培训老师标识
     *
     * @return teacherid - 培训老师标识
     */
    public String getTeacherid() {
        return teacherid;
    }

    /**
     * 设置培训老师标识
     *
     * @param teacherid 培训老师标识
     */
    public void setTeacherid(String teacherid) {
        this.teacherid = teacherid;
    }

    /**
     * 获取培训老师所属注册用户标识
     *
     * @return teacheruid - 培训老师所属注册用户标识
     */
    public String getTeacheruid() {
        return teacheruid;
    }

    /**
     * 设置培训老师所属注册用户标识
     *
     * @param teacheruid 培训老师所属注册用户标识
     */
    public void setTeacheruid(String teacheruid) {
        this.teacheruid = teacheruid;
    }

    /**
     * 获取培训老师图片
     *
     * @return teacherpic - 培训老师图片
     */
    public String getTeacherpic() {
        return teacherpic;
    }

    /**
     * 设置培训老师图片
     *
     * @param teacherpic 培训老师图片
     */
    public void setTeacherpic(String teacherpic) {
        this.teacherpic = teacherpic;
    }

    /**
     * 获取培训老师名称
     *
     * @return teachername - 培训老师名称
     */
    public String getTeachername() {
        return teachername;
    }

    /**
     * 设置培训老师名称
     *
     * @param teachername 培训老师名称
     */
    public void setTeachername(String teachername) {
        this.teachername = teachername;
    }

    /**
     * 获取培训老师专长类型
     *
     * @return teachertype - 培训老师专长类型
     */
    public String getTeachertype() {
        return teachertype;
    }

    /**
     * 设置培训老师专长类型
     *
     * @param teachertype 培训老师专长类型
     */
    public void setTeachertype(String teachertype) {
        this.teachertype = teachertype;
    }

    /**
     * 获取培训老师所属区域
     *
     * @return teacherarea - 培训老师所属区域
     */
    public String getTeacherarea() {
        return teacherarea;
    }

    /**
     * 设置培训老师所属区域
     *
     * @param teacherarea 培训老师所属区域
     */
    public void setTeacherarea(String teacherarea) {
        this.teacherarea = teacherarea;
    }

    /**
     * 获取培训老师艺术类型
     *
     * @return teacherarttyp - 培训老师艺术类型
     */
    public String getTeacherarttyp() {
        return teacherarttyp;
    }

    /**
     * 设置培训老师艺术类型
     *
     * @param teacherarttyp 培训老师艺术类型
     */
    public void setTeacherarttyp(String teacherarttyp) {
        this.teacherarttyp = teacherarttyp;
    }

    /**
     * 获取老师课程说明
     *
     * @return teachercourse - 老师课程说明
     */
    public String getTeachercourse() {
        return teachercourse;
    }

    /**
     * 设置老师课程说明
     *
     * @param teachercourse 老师课程说明
     */
    public void setTeachercourse(String teachercourse) {
        this.teachercourse = teachercourse;
    }

    /**
     * 获取培训老师简介
     *
     * @return teacherintroduce - 培训老师简介
     */
    public String getTeacherintroduce() {
        return teacherintroduce;
    }

    /**
     * 设置培训老师简介
     *
     * @param teacherintroduce 培训老师简介
     */
    public void setTeacherintroduce(String teacherintroduce) {
        this.teacherintroduce = teacherintroduce;
    }

    /**
     * 获取专长介绍
     *
     * @return teacherexpdesc - 专长介绍
     */
    public String getTeacherexpdesc() {
        return teacherexpdesc;
    }

    /**
     * 设置专长介绍
     *
     * @param teacherexpdesc 专长介绍
     */
    public void setTeacherexpdesc(String teacherexpdesc) {
        this.teacherexpdesc = teacherexpdesc;
    }

    /**
     * 获取开课介绍
     *
     * @return teacherstartdesc - 开课介绍
     */
    public String getTeacherstartdesc() {
        return teacherstartdesc;
    }

    /**
     * 设置开课介绍
     *
     * @param teacherstartdesc 开课介绍
     */
    public void setTeacherstartdesc(String teacherstartdesc) {
        this.teacherstartdesc = teacherstartdesc;
    }

    /**
     * 获取注册日期
     *
     * @return teacherregtime - 注册日期
     */
    public Date getTeacherregtime() {
        return teacherregtime;
    }

    /**
     * 设置注册日期
     *
     * @param teacherregtime 注册日期
     */
    public void setTeacherregtime(Date teacherregtime) {
        this.teacherregtime = teacherregtime;
    }

    /**
     * 获取修改状态的时间
     *
     * @return teacheropttime - 修改状态的时间
     */
    public Date getTeacheropttime() {
        return teacheropttime;
    }

    /**
     * 设置修改状态的时间
     *
     * @param teacheropttime 修改状态的时间
     */
    public void setTeacheropttime(Date teacheropttime) {
        this.teacheropttime = teacheropttime;
    }

    /**
     * 获取状态:0-初始,1-送审,2-已审,3-已发岸上
     *
     * @return teacherstate - 状态:0-初始,1-送审,2-已审,3-已发岸上
     */
    public Integer getTeacherstate() {
        return teacherstate;
    }

    /**
     * 设置状态:0-初始,1-送审,2-已审,3-已发岸上
     *
     * @param teacherstate 状态:0-初始,1-送审,2-已审,3-已发岸上
     */
    public void setTeacherstate(Integer teacherstate) {
        this.teacherstate = teacherstate;
    }

    
    
}