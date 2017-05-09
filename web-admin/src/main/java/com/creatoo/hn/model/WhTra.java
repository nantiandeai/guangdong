package com.creatoo.hn.model;

import javax.persistence.*;

@Table(name = "wh_tra")
public class WhTra {
    /**
     * 培训标识
     */
    @Id
    private String traid;

    /**
     * 培训类型
     */
    private String tratyp;

    /**
     * 艺术类型
     */
    private String arttyp;

    /**
     * 培训图片
     */
    private String trapic;

    /**
     * 授课老师名称
     */
    private String teacher;

    /**
     * 授课老师标识
     */
    private String teacherid;

    /**
     * 适合年龄:幼儿成人老龄
     */
    private String agelevel;

    /**
     * 课程级别:初中高
     */
    private String tralevel;

    /**
     * 必须实名报名
     */
    private String isrealname;

    /**
     * 是否需要完善资料
     */
    private String isfulldata;

    /**
     * 是否需要上传附件
     */
    private String isattach;

    /**
     * 限制一人只能参加同类的一个培训
     */
    private String isonlyone;

    /**
     * 必须登录点评
     */
    private String islogincomment;

    /**
     * 附件路径
     */
    private String userfile;

    /**
     * 标题
     */
    private String title;

    /**
     * 标签
     */
    private String tratags;

    /**
     * 关键字
     */
    private String trakeys;

    /**
     * 区域
     */
    private String area;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 首页图片
     */
    private String trapic1;

    /**
     * 详细页图片
     */
    private String trapic2;

    /**
     * 培训介绍
     */
    private String tradesc;

    /**
     * 老师介绍
     */
    private String teacherdesc;
    
    /**
     * 团队附件
     */
    private String groupfile;
   
    /**
     * 培训大纲
     */
    private String tracatalog;

    /**
     * 获取培训标识
     *
     * @return traid - 培训标识
     */
    public String getTraid() {
        return traid;
    }

    /**
     * 设置培训标识
     *
     * @param traid 培训标识
     */
    public void setTraid(String traid) {
        this.traid = traid;
    }

    /**
     * 获取培训类型
     *
     * @return tratyp - 培训类型
     */
    public String getTratyp() {
        return tratyp;
    }

    /**
     * 设置培训类型
     *
     * @param tratyp 培训类型
     */
    public void setTratyp(String tratyp) {
        this.tratyp = tratyp;
    }

    /**
     * 获取艺术类型
     *
     * @return arttyp - 艺术类型
     */
    public String getArttyp() {
        return arttyp;
    }

    /**
     * 设置艺术类型
     *
     * @param arttyp 艺术类型
     */
    public void setArttyp(String arttyp) {
        this.arttyp = arttyp;
    }

    /**
     * 获取培训图片
     *
     * @return trapic - 培训图片
     */
    public String getTrapic() {
        return trapic;
    }

    /**
     * 设置培训图片
     *
     * @param trapic 培训图片
     */
    public void setTrapic(String trapic) {
        this.trapic = trapic;
    }

    /**
     * 获取授课老师名称
     *
     * @return teacher - 授课老师名称
     */
    public String getTeacher() {
        return teacher;
    }

    /**
     * 设置授课老师名称
     *
     * @param teacher 授课老师名称
     */
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    /**
     * 获取授课老师标识
     *
     * @return teacherid - 授课老师标识
     */
    public String getTeacherid() {
        return teacherid;
    }

    /**
     * 设置授课老师标识
     *
     * @param teacherid 授课老师标识
     */
    public void setTeacherid(String teacherid) {
        this.teacherid = teacherid;
    }

    /**
     * 获取适合年龄:幼儿成人老龄
     *
     * @return agelevel - 适合年龄:幼儿成人老龄
     */
    public String getAgelevel() {
        return agelevel;
    }

    /**
     * 设置适合年龄:幼儿成人老龄
     *
     * @param agelevel 适合年龄:幼儿成人老龄
     */
    public void setAgelevel(String agelevel) {
        this.agelevel = agelevel;
    }

    /**
     * 获取课程级别:初中高
     *
     * @return tralevel - 课程级别:初中高
     */
    public String getTralevel() {
        return tralevel;
    }

    /**
     * 设置课程级别:初中高
     *
     * @param tralevel 课程级别:初中高
     */
    public void setTralevel(String tralevel) {
        this.tralevel = tralevel;
    }

    /**
     * 获取必须实名报名
     *
     * @return isrealname - 必须实名报名
     */
    public String getIsrealname() {
        return isrealname;
    }

    /**
     * 设置必须实名报名
     *
     * @param isrealname 必须实名报名
     */
    public void setIsrealname(String isrealname) {
        this.isrealname = isrealname;
    }

    /**
     * 获取是否需要完善资料
     *
     * @return isfulldata - 是否需要完善资料
     */
    public String getIsfulldata() {
        return isfulldata;
    }

    /**
     * 设置是否需要完善资料
     *
     * @param isfulldata 是否需要完善资料
     */
    public void setIsfulldata(String isfulldata) {
        this.isfulldata = isfulldata;
    }

    /**
     * 获取是否需要上传附件
     *
     * @return isattach - 是否需要上传附件
     */
    public String getIsattach() {
        return isattach;
    }

    /**
     * 设置是否需要上传附件
     *
     * @param isattach 是否需要上传附件
     */
    public void setIsattach(String isattach) {
        this.isattach = isattach;
    }

    /**
     * 获取限制一人只能参加同类的一个培训
     *
     * @return isonlyone - 限制一人只能参加同类的一个培训
     */
    public String getIsonlyone() {
        return isonlyone;
    }

    /**
     * 设置限制一人只能参加同类的一个培训
     *
     * @param isonlyone 限制一人只能参加同类的一个培训
     */
    public void setIsonlyone(String isonlyone) {
        this.isonlyone = isonlyone;
    }

    /**
     * 获取必须登录点评
     *
     * @return islogincomment - 必须登录点评
     */
    public String getIslogincomment() {
        return islogincomment;
    }

    /**
     * 设置必须登录点评
     *
     * @param islogincomment 必须登录点评
     */
    public void setIslogincomment(String islogincomment) {
        this.islogincomment = islogincomment;
    }

    /**
     * 获取附件路径
     *
     * @return userfile - 附件路径
     */
    public String getUserfile() {
        return userfile;
    }

    /**
     * 设置附件路径
     *
     * @param userfile 附件路径
     */
    public void setUserfile(String userfile) {
        this.userfile = userfile;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取标签
     *
     * @return tratags - 标签
     */
    public String getTratags() {
        return tratags;
    }

    /**
     * 设置标签
     *
     * @param tratags 标签
     */
    public void setTratags(String tratags) {
        this.tratags = tratags;
    }

    /**
     * 获取关键字
     *
     * @return trakeys - 关键字
     */
    public String getTrakeys() {
        return trakeys;
    }

    /**
     * 设置关键字
     *
     * @param trakeys 关键字
     */
    public void setTrakeys(String trakeys) {
        this.trakeys = trakeys;
    }

    /**
     * 获取区域
     *
     * @return area - 区域
     */
    public String getArea() {
        return area;
    }

    /**
     * 设置区域
     *
     * @param area 区域
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * 获取详细地址
     *
     * @return address - 详细地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置详细地址
     *
     * @param address 详细地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取首页图片
     *
     * @return trapic1 - 首页图片
     */
    public String getTrapic1() {
        return trapic1;
    }

    /**
     * 设置首页图片
     *
     * @param trapic1 首页图片
     */
    public void setTrapic1(String trapic1) {
        this.trapic1 = trapic1;
    }

    /**
     * 获取详细页图片
     *
     * @return trapic2 - 详细页图片
     */
    public String getTrapic2() {
        return trapic2;
    }

    /**
     * 设置详细页图片
     *
     * @param trapic2 详细页图片
     */
    public void setTrapic2(String trapic2) {
        this.trapic2 = trapic2;
    }

    /**
     * 获取团队附件
     *
     * @return groupfile - 团队附件
     */
    public String getGroupfile() {
        return groupfile;
    }

    /**
     * 设置团队附件
     *
     * @param groupfile 团队附件
     */
    public void setGroupfile(String groupfile) {
        this.groupfile = groupfile;
    }

    /**
     * 获取培训介绍
     *
     * @return tradesc - 培训介绍
     */
    public String getTradesc() {
        return tradesc;
    }

    /**
     * 设置培训介绍
     *
     * @param tradesc 培训介绍
     */
    public void setTradesc(String tradesc) {
        this.tradesc = tradesc;
    }

    /**
     * 获取老师介绍
     *
     * @return teacherdesc - 老师介绍
     */
    public String getTeacherdesc() {
        return teacherdesc;
    }

    /**
     * 设置老师介绍
     *
     * @param teacherdesc 老师介绍
     */
    public void setTeacherdesc(String teacherdesc) {
        this.teacherdesc = teacherdesc;
    }
    /**
     * 获取培训大纲
     *
     * @return tracatalog - 培训大纲
     */
    public String getTracatalog() {
        return tracatalog;
    }
    /**
     * 设置培训大纲
     *
     * @param tracatalog 培训大纲
     */
    public void setTracatalog(String tracatalog) {
        this.tracatalog = tracatalog;
    }
}