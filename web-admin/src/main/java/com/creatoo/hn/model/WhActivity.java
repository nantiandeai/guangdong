package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "wh_activity")
public class WhActivity {
    /**
     * 活动标识
     */
    @Id
    private String actvid;

    /**
     * 活动类型
     */
    private String actvtype;

    /**
     * 艺术类型
     */
    private String actvarttyp;

    /**
     * 列表标题
     */
    private String actvshorttitle;

    /**
     * 详情标题
     */
    private String actvtitle;

    /**
     * 活动图片详情页主图
     */
    private String actvpic;

    /**
     * 列表展示小图
     */
    private String actvbigpic;

    /**
     * 活动开始日期
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date actvsdate;

    /**
     * 活动结束日期
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date actvedate;

    /**
     * 详细地址
     */
    private String actvaddress;

    /**
     * 活动主办方
     */
    private String actvhost;

    /**
     * 适合年龄段
     */
    private String actvagelevel;

    /**
     * 必须登录点评
     */
    private Integer actvislogincomment;

    /**
     * 是否需要订票：0否，1是
     */
    private Integer actvisyp;

    /**
     * 标签
     */
    private String actvtags;

    /**
     * 关键字
     */
    private String actvkeys;

    /**
     * 区域
     */
    private String actvarea;

    /**
     * 活动介绍
     */
    private String actvintroduce;

    /**
     * 是否需要报名
     */
    private Integer actvisenrol;

    /**
     * 报名开始时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date actvenrolstime;

    /**
     * 报名结束时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date actvenroletime;

    /**
     * 报名人数限制
     */
    private Integer actvenrollimit;

    /**
     * 报名是否需要审核
     */
    private Integer actvisenrolqr;
    /**
     * 必须实名报名
     */
    private Integer actvisrealname;

    /**
     * 是否需要完善资料
     */
    private Integer actvisfulldata;

    /**
     * 是否需要上传附件
     */
    private Integer actvisattach;

    /**
     * 限制一人只能参加同类的一个活动
     */
    private Integer actvisonlyone;

    /**
     * 允许个人报名.0-是;1-否
     */
    private Integer actvcanperson;

    /**
     * 个人附件路径
     */
    private String actvpersonfile;

    /**
     * 允许团队报名.0-是;1-否
     */
    private Integer actvcanteam;

    /**
     * 团队附件路径
     */
    private String actvteamfile;

    /**
     * 是否上首页
     */
    private Integer actvitmghp;

    /**
     * 上首页排序
     */
    private Integer actvitmidx;

    /**
     * 状态:0：初始 1：审批 2：以审3：发布
     */
    private Integer actvstate;

    /**
     * 修改状态的操作时间
     */
    private Date actvopttime;

    /**
     * 活动详情
     */
    private String actvdetail;

    /**
     * 报名介绍
     */
    private String actvenroldesc;
    
    /**
     * 联系电话
     */
    private String actvphone;
    /**
     * 获取活动标识
     *
     * @return actvid - 活动标识
     */
    public String getActvid() {
        return actvid;
    }

    /**
     * 设置活动标识
     *
     * @param actvid 活动标识
     */
    public void setActvid(String actvid) {
        this.actvid = actvid;
    }

    /**
     * 获取活动类型
     *
     * @return actvtype - 活动类型
     */
    public String getActvtype() {
        return actvtype;
    }

    /**
     * 设置活动类型
     *
     * @param actvtype 活动类型
     */
    public void setActvtype(String actvtype) {
        this.actvtype = actvtype;
    }

    /**
     * 获取艺术类型
     *
     * @return actvarttyp - 艺术类型
     */
    public String getActvarttyp() {
        return actvarttyp;
    }

    /**
     * 设置艺术类型
     *
     * @param actvarttyp 艺术类型
     */
    public void setActvarttyp(String actvarttyp) {
        this.actvarttyp = actvarttyp;
    }

    /**
     * 获取列表标题
     *
     * @return actvshorttitle - 列表标题
     */
    public String getActvshorttitle() {
        return actvshorttitle;
    }

    /**
     * 设置列表标题
     *
     * @param actvshorttitle 列表标题
     */
    public void setActvshorttitle(String actvshorttitle) {
        this.actvshorttitle = actvshorttitle;
    }

    /**
     * 获取详情标题
     *
     * @return actvtitle - 详情标题
     */
    public String getActvtitle() {
        return actvtitle;
    }

    /**
     * 设置详情标题
     *
     * @param actvtitle 详情标题
     */
    public void setActvtitle(String actvtitle) {
        this.actvtitle = actvtitle;
    }

    /**
     * 获取活动图片详情页主图
     *
     * @return actvpic - 活动图片详情页主图
     */
    public String getActvpic() {
        return actvpic;
    }

    /**
     * 设置活动图片详情页主图
     *
     * @param actvpic 活动图片详情页主图
     */
    public void setActvpic(String actvpic) {
        this.actvpic = actvpic;
    }

    /**
     * 获取列表展示小图
     *
     * @return actvbigpic - 列表展示小图
     */
    public String getActvbigpic() {
        return actvbigpic;
    }

    /**
     * 设置列表展示小图
     *
     * @param actvbigpic 列表展示小图
     */
    public void setActvbigpic(String actvbigpic) {
        this.actvbigpic = actvbigpic;
    }

    /**
     * 获取活动开始日期
     *
     * @return actvsdate - 活动开始日期
     */
    public Date getActvsdate() {
        return actvsdate;
    }

    /**
     * 设置活动开始日期
     *
     * @param actvsdate 活动开始日期
     */
    public void setActvsdate(Date actvsdate) {
        this.actvsdate = actvsdate;
    }

    /**
     * 获取活动结束日期
     *
     * @return actvedate - 活动结束日期
     */
    public Date getActvedate() {
        return actvedate;
    }

    /**
     * 设置活动结束日期
     *
     * @param actvedate 活动结束日期
     */
    public void setActvedate(Date actvedate) {
        this.actvedate = actvedate;
    }

    /**
     * 获取详细地址
     *
     * @return actvaddress - 详细地址
     */
    public String getActvaddress() {
        return actvaddress;
    }

    /**
     * 设置详细地址
     *
     * @param actvaddress 详细地址
     */
    public void setActvaddress(String actvaddress) {
        this.actvaddress = actvaddress;
    }

    /**
     * 获取活动主办方
     *
     * @return actvhost - 活动主办方
     */
    public String getActvhost() {
        return actvhost;
    }

    /**
     * 设置活动主办方
     *
     * @param actvhost 活动主办方
     */
    public void setActvhost(String actvhost) {
        this.actvhost = actvhost;
    }

    /**
     * 获取适合年龄段
     *
     * @return actvagelevel - 适合年龄段
     */
    public String getActvagelevel() {
        return actvagelevel;
    }

    /**
     * 设置适合年龄段
     *
     * @param actvagelevel 适合年龄段
     */
    public void setActvagelevel(String actvagelevel) {
        this.actvagelevel = actvagelevel;
    }

    /**
     * 获取必须登录点评
     *
     * @return actvislogincomment - 必须登录点评
     */
    public Integer getActvislogincomment() {
        return actvislogincomment;
    }

    /**
     * 设置必须登录点评
     *
     * @param actvislogincomment 必须登录点评
     */
    public void setActvislogincomment(Integer actvislogincomment) {
        this.actvislogincomment = actvislogincomment;
    }

    /**
     * 获取是否需要订票：0否，1是
     *
     * @return actvisyp - 是否需要订票：0否，1是
     */
    public Integer getActvisyp() {
        return actvisyp;
    }

    /**
     * 设置是否需要订票：0否，1是
     *
     * @param actvisyp 是否需要订票：0否，1是
     */
    public void setActvisyp(Integer actvisyp) {
        this.actvisyp = actvisyp;
    }

    /**
     * 获取标签
     *
     * @return actvtags - 标签
     */
    public String getActvtags() {
        return actvtags;
    }

    /**
     * 设置标签
     *
     * @param actvtags 标签
     */
    public void setActvtags(String actvtags) {
        this.actvtags = actvtags;
    }

    /**
     * 获取关键字
     *
     * @return actvkeys - 关键字
     */
    public String getActvkeys() {
        return actvkeys;
    }

    /**
     * 设置关键字
     *
     * @param actvkeys 关键字
     */
    public void setActvkeys(String actvkeys) {
        this.actvkeys = actvkeys;
    }

    /**
     * 获取区域
     *
     * @return actvarea - 区域
     */
    public String getActvarea() {
        return actvarea;
    }

    /**
     * 设置区域
     *
     * @param actvarea 区域
     */
    public void setActvarea(String actvarea) {
        this.actvarea = actvarea;
    }

    /**
     * 获取活动介绍
     *
     * @return actvintroduce - 活动介绍
     */
    public String getActvintroduce() {
        return actvintroduce;
    }

    /**
     * 设置活动介绍
     *
     * @param actvintroduce 活动介绍
     */
    public void setActvintroduce(String actvintroduce) {
        this.actvintroduce = actvintroduce;
    }

    /**
     * 获取是否需要报名
     *
     * @return actvisenrol - 是否需要报名
     */
    public Integer getActvisenrol() {
        return actvisenrol;
    }

    /**
     * 设置是否需要报名
     *
     * @param actvisenrol 是否需要报名
     */
    public void setActvisenrol(Integer actvisenrol) {
        this.actvisenrol = actvisenrol;
    }

    /**
     * 获取报名开始时间
     *
     * @return actvenrolstime - 报名开始时间
     */
    public Date getActvenrolstime() {
        return actvenrolstime;
    }

    /**
     * 设置报名开始时间
     *
     * @param actvenrolstime 报名开始时间
     */
    public void setActvenrolstime(Date actvenrolstime) {
        this.actvenrolstime = actvenrolstime;
    }

    /**
     * 获取报名结束时间
     *
     * @return actvenroletime - 报名结束时间
     */
    public Date getActvenroletime() {
        return actvenroletime;
    }

    /**
     * 设置报名结束时间
     *
     * @param actvenroletime 报名结束时间
     */
    public void setActvenroletime(Date actvenroletime) {
        this.actvenroletime = actvenroletime;
    }

    /**
     * 获取报名人数限制
     *
     * @return actvenrollimit - 报名人数限制
     */
    public Integer getActvenrollimit() {
        return actvenrollimit;
    }

    /**
     * 设置报名人数限制
     *
     * @param actvenrollimit 报名人数限制
     */
    public void setActvenrollimit(Integer actvenrollimit) {
        this.actvenrollimit = actvenrollimit;
    }

    /**
     * 获取报名是否需要审核
     *
     * @return actvisenrolqr - 报名是否需要审核
     */
    public Integer getActvisenrolqr() {
        return actvisenrolqr;
    }

    /**
     * 设置报名是否需要审核
     *
     * @param actvisenrolqr 报名是否需要审核
     */
    public void setActvisenrolqr(Integer actvisenrolqr) {
        this.actvisenrolqr = actvisenrolqr;
    }

    /**
     * 获取必须实名报名
     *
     * @return actvisrealname - 必须实名报名
     */
    public Integer getActvisrealname() {
        return actvisrealname;
    }

    /**
     * 设置必须实名报名
     *
     * @param actvisrealname 必须实名报名
     */
    public void setActvisrealname(Integer actvisrealname) {
        this.actvisrealname = actvisrealname;
    }

    /**
     * 获取是否需要完善资料
     *
     * @return actvisfulldata - 是否需要完善资料
     */
    public Integer getActvisfulldata() {
        return actvisfulldata;
    }

    /**
     * 设置是否需要完善资料
     *
     * @param actvisfulldata 是否需要完善资料
     */
    public void setActvisfulldata(Integer actvisfulldata) {
        this.actvisfulldata = actvisfulldata;
    }

    /**
     * 获取是否需要上传附件
     *
     * @return actvisattach - 是否需要上传附件
     */
    public Integer getActvisattach() {
        return actvisattach;
    }

    /**
     * 设置是否需要上传附件
     *
     * @param actvisattach 是否需要上传附件
     */
    public void setActvisattach(Integer actvisattach) {
        this.actvisattach = actvisattach;
    }

    /**
     * 获取限制一人只能参加同类的一个培训,一个人只能同时参加一个培训类型的课程
     *
     * @return actvisonlyone - 限制一人只能参加同类的一个培训,一个人只能同时参加一个培训类型的课程
     */
    public Integer getActvisonlyone() {
        return actvisonlyone;
    }

    /**
     * 设置限制一人只能参加同类的一个培训,一个人只能同时参加一个培训类型的课程
     *
     * @param actvisonlyone 限制一人只能参加同类的一个培训,一个人只能同时参加一个培训类型的课程
     */
    public void setActvisonlyone(Integer actvisonlyone) {
        this.actvisonlyone = actvisonlyone;
    }

    /**
     * 获取允许个人报名.0-是;1-否
     *
     * @return actvcanperson - 允许个人报名.0-是;1-否
     */
    public Integer getActvcanperson() {
        return actvcanperson;
    }

    /**
     * 设置允许个人报名.0-是;1-否
     *
     * @param actvcanperson 允许个人报名.0-是;1-否
     */
    public void setActvcanperson(Integer actvcanperson) {
        this.actvcanperson = actvcanperson;
    }

    /**
     * 获取个人附件路径
     *
     * @return actvpersonfile - 个人附件路径
     */
    public String getActvpersonfile() {
        return actvpersonfile;
    }

    /**
     * 设置个人附件路径
     *
     * @param actvpersonfile 个人附件路径
     */
    public void setActvpersonfile(String actvpersonfile) {
        this.actvpersonfile = actvpersonfile;
    }

    /**
     * 获取允许团队报名.0-是;1-否
     *
     * @return actvcanteam - 允许团队报名.0-是;1-否
     */
    public Integer getActvcanteam() {
        return actvcanteam;
    }

    /**
     * 设置允许团队报名.0-是;1-否
     *
     * @param actvcanteam 允许团队报名.0-是;1-否
     */
    public void setActvcanteam(Integer actvcanteam) {
        this.actvcanteam = actvcanteam;
    }

    /**
     * 获取团队附件路径
     *
     * @return actvteamfile - 团队附件路径
     */
    public String getActvteamfile() {
        return actvteamfile;
    }

    /**
     * 设置团队附件路径
     *
     * @param actvteamfile 团队附件路径
     */
    public void setActvteamfile(String actvteamfile) {
        this.actvteamfile = actvteamfile;
    }

    /**
     * 获取是否上首页
     *
     * @return actvitmghp - 是否上首页
     */
    public Integer getActvitmghp() {
        return actvitmghp;
    }

    /**
     * 设置是否上首页
     *
     * @param actvitmghp 是否上首页
     */
    public void setActvitmghp(Integer actvitmghp) {
        this.actvitmghp = actvitmghp;
    }

    /**
     * 获取上首页排序
     *
     * @return actvitmidx - 上首页排序
     */
    public Integer getActvitmidx() {
        return actvitmidx;
    }

    /**
     * 设置上首页排序
     *
     * @param actvitmidx 上首页排序
     */
    public void setActvitmidx(Integer actvitmidx) {
        this.actvitmidx = actvitmidx;
    }

    /**
     * 获取状态:0：初始 1：审批 2：以审3：发布
     *
     * @return actvstate - 状态:0：初始 1：审批 2：以审3：发布
     */
    public Integer getActvstate() {
        return actvstate;
    }

    /**
     * 设置状态:0：初始 1：审批 2：以审3：发布
     *
     * @param actvstate 状态:0：初始 1：审批 2：以审3：发布
     */
    public void setActvstate(Integer actvstate) {
        this.actvstate = actvstate;
    }

    /**
     * 获取修改状态的操作时间
     *
     * @return actvopttime - 修改状态的操作时间
     */
    public Date getActvopttime() {
        return actvopttime;
    }

    /**
     * 设置修改状态的操作时间
     *
     * @param actvopttime 修改状态的操作时间
     */
    public void setActvopttime(Date actvopttime) {
        this.actvopttime = actvopttime;
    }

    /**
     * 获取活动详情
     *
     * @return actvdetail - 活动详情
     */
    public String getActvdetail() {
        return actvdetail;
    }

    /**
     * 设置活动详情
     *
     * @param actvdetail 活动详情
     */
    public void setActvdetail(String actvdetail) {
        this.actvdetail = actvdetail;
    }

    /**
     * 获取报名介绍
     *
     * @return actvenroldesc - 报名介绍
     */
    public String getActvenroldesc() {
        return actvenroldesc;
    }

    /**
     * 设置报名介绍
     *
     * @param actvenroldesc 报名介绍
     */
    public void setActvenroldesc(String actvenroldesc) {
        this.actvenroldesc = actvenroldesc;
    }

	public String getActvphone() {
		return actvphone;
	}

	public void setActvphone(String actvphone) {
		this.actvphone = actvphone;
	}
    
    
}