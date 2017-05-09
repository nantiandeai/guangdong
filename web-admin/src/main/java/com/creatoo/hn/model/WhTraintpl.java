package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "wh_traintpl")
public class WhTraintpl {
    /**
     * 培训模板标识
     */
    @Id
    private String tratplid;

    /**
     * 培训标识
     */
    private String traid;

    /**
     * 培训类型
     */
    private String tratyp;

    /**
     * 艺术类型
     */
    private String traarttyp;
    
    /**
     * 短标题
     */
    private String trashorttitle;

    /**
     * 课程标题
     */
    private String tratitle;

    /**
     * 培训列表图片
     */
    private String trapic;

    /**
     * 详细页图片
     */
    private String trabigpic;

    /**
     * 培训开始日期
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date trasdate;

    /**
     * 培训结束日期
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date traedate;

    /**
     * 详细地址
     */
    private String traaddress;

    /**
     * 适合年龄:幼儿成人老龄
     */
    private String traagelevel;

    /**
     * 课程级别:初中高
     */
    private String tralevel;

    /**
     * 必须登录点评.0-否,1-是
     */
    private Integer traislogincomment;

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
    private String traarea;

    /**
     * 授课老师名称
     */
    private String trateacher;

    /**
     * 授课老师标识
     */
    private String trateacherid;

    /**
     * 是否需要报名
     */
    private Integer traisenrol;

    /**
     * 报名开始时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date traenrolstime;

    /**
     * 报名结束时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date traenroletime;

    /**
     * 报名人数限制
     */
    private Integer traenrollimit;

    /**
     * 报名是否需要审核
     */
    private Integer traisenrolqr;

    /**
     * 是否需要面试通知
     */
    private Integer traisnotic;

    /**
     * 必须实名报名
     */
    private Integer traisrealname;

    /**
     * 是否需要完善资料
     */
    private Integer traisfulldata;

    /**
     * 是否需要上传附件
     */
    private Integer traisattach;

    /**
     * 限制一人只能参加同类的一个培训,一个人只能同时参加一个培训类型的课程
     */
    private Integer traisonlyone;

    /**
     * 允许个人报名.0-是;1-否
     */
    private Integer tracanperson;

    /**
     * 个人附件路径
     */
    private String trapersonfile;

    /**
     * 允许团队报名.0-是;1-否
     */
    private Integer tracanteam;

    /**
     * 团队附件路径
     */
    private String trateamfile;

    /**
     * 课程简介，限制30个中文之内
     */
    private String traintroduce;

    /**
     * 老师介绍
     */
    private String trateacherdesc;

    /**
     * 报名介绍
     */
    private String traenroldesc;

    /**
     * 培训大纲
     */
    private String tracatalog;

    /**
     * 培训课程详情介绍
     */
    private String tradetail;
    
    /**
     * 培训联系电话
     */
    private String traphone;
    
    /**
     * 联系人
     */
    private String tracontact;

    /**
     * 是否需要收费
     */
    private Integer ismoney;
    /**
     * 获取培训模板标识
     *
     * @return tratplid - 培训模板标识
     */
    public String getTratplid() {
        return tratplid;
    }

    /**
     * 设置培训模板标识
     *
     * @param tratplid 培训模板标识
     */
    public void setTratplid(String tratplid) {
        this.tratplid = tratplid;
    }

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
     * @return traarttyp - 艺术类型
     */
    public String getTraarttyp() {
        return traarttyp;
    }

    /**
     * 设置艺术类型
     *
     * @param traarttyp 艺术类型
     */
    public void setTraarttyp(String traarttyp) {
        this.traarttyp = traarttyp;
    }

    /**
     * 获取课程标题
     *
     * @return tratitle - 课程标题
     */
    public String getTratitle() {
        return tratitle;
    }

    /**
     * 设置课程标题
     *
     * @param tratitle 课程标题
     */
    public void setTratitle(String tratitle) {
        this.tratitle = tratitle;
    }

    /**
     * 获取培训列表图片
     *
     * @return trapic - 培训列表图片
     */
    public String getTrapic() {
        return trapic;
    }

    /**
     * 设置培训列表图片
     *
     * @param trapic 培训列表图片
     */
    public void setTrapic(String trapic) {
        this.trapic = trapic;
    }

    /**
     * 获取详细页图片
     *
     * @return trabigpic - 详细页图片
     */
    public String getTrabigpic() {
        return trabigpic;
    }

    /**
     * 设置详细页图片
     *
     * @param trabigpic 详细页图片
     */
    public void setTrabigpic(String trabigpic) {
        this.trabigpic = trabigpic;
    }

    /**
     * 获取培训开始日期
     *
     * @return trasdate - 培训开始日期
     */
    public Date getTrasdate() {
        return trasdate;
    }

    /**
     * 设置培训开始日期
     *
     * @param trasdate 培训开始日期
     */
    public void setTrasdate(Date trasdate) {
        this.trasdate = trasdate;
    }

    /**
     * 获取培训结束日期
     *
     * @return traedate - 培训结束日期
     */
    public Date getTraedate() {
        return traedate;
    }

    /**
     * 设置培训结束日期
     *
     * @param traedate 培训结束日期
     */
    public void setTraedate(Date traedate) {
        this.traedate = traedate;
    }

    /**
     * 获取详细地址
     *
     * @return traaddress - 详细地址
     */
    public String getTraaddress() {
        return traaddress;
    }

    /**
     * 设置详细地址
     *
     * @param traaddress 详细地址
     */
    public void setTraaddress(String traaddress) {
        this.traaddress = traaddress;
    }

    /**
     * 获取适合年龄:幼儿成人老龄
     *
     * @return traagelevel - 适合年龄:幼儿成人老龄
     */
    public String getTraagelevel() {
        return traagelevel;
    }

    /**
     * 设置适合年龄:幼儿成人老龄
     *
     * @param traagelevel 适合年龄:幼儿成人老龄
     */
    public void setTraagelevel(String traagelevel) {
        this.traagelevel = traagelevel;
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
     * 获取必须登录点评.0-否,1-是
     *
     * @return traislogincomment - 必须登录点评.0-否,1-是
     */
    public Integer getTraislogincomment() {
        return traislogincomment;
    }

    /**
     * 设置必须登录点评.0-否,1-是
     *
     * @param traislogincomment 必须登录点评.0-否,1-是
     */
    public void setTraislogincomment(Integer traislogincomment) {
        this.traislogincomment = traislogincomment;
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
     * @return traarea - 区域
     */
    public String getTraarea() {
        return traarea;
    }

    /**
     * 设置区域
     *
     * @param traarea 区域
     */
    public void setTraarea(String traarea) {
        this.traarea = traarea;
    }

    /**
     * 获取授课老师名称
     *
     * @return trateacher - 授课老师名称
     */
    public String getTrateacher() {
        return trateacher;
    }

    /**
     * 设置授课老师名称
     *
     * @param trateacher 授课老师名称
     */
    public void setTrateacher(String trateacher) {
        this.trateacher = trateacher;
    }

    /**
     * 获取授课老师标识
     *
     * @return trateacherid - 授课老师标识
     */
    public String getTrateacherid() {
        return trateacherid;
    }

    /**
     * 设置授课老师标识
     *
     * @param trateacherid 授课老师标识
     */
    public void setTrateacherid(String trateacherid) {
        this.trateacherid = trateacherid;
    }

    /**
     * 获取是否需要报名
     *
     * @return traisenrol - 是否需要报名
     */
    public Integer getTraisenrol() {
        return traisenrol;
    }

    /**
     * 设置是否需要报名
     *
     * @param traisenrol 是否需要报名
     */
    public void setTraisenrol(Integer traisenrol) {
        this.traisenrol = traisenrol;
    }

    /**
     * 获取报名开始时间
     *
     * @return traenrolstime - 报名开始时间
     */
    public Date getTraenrolstime() {
        return traenrolstime;
    }

    /**
     * 设置报名开始时间
     *
     * @param traenrolstime 报名开始时间
     */
    public void setTraenrolstime(Date traenrolstime) {
        this.traenrolstime = traenrolstime;
    }

    /**
     * 获取报名结束时间
     *
     * @return traenroletime - 报名结束时间
     */
    public Date getTraenroletime() {
        return traenroletime;
    }

    /**
     * 设置报名结束时间
     *
     * @param traenroletime 报名结束时间
     */
    public void setTraenroletime(Date traenroletime) {
        this.traenroletime = traenroletime;
    }

    /**
     * 获取报名人数限制
     *
     * @return traenrollimit - 报名人数限制
     */
    public Integer getTraenrollimit() {
		return traenrollimit;
	}

    /**
     * 设置报名人数限制
     *
     * @param traenrollimit 报名人数限制
     */
    public void setTraenrollimit(Integer traenrollimit) {
		this.traenrollimit = traenrollimit;
	}
    /**
     * 获取报名是否需要审核
     *
     * @return traisenrolqr - 报名是否需要审核
     */
    public Integer getTraisenrolqr() {
        return traisenrolqr;
    }

    /**
     * 设置报名是否需要审核
     *
     * @param traisenrolqr 报名是否需要审核
     */
    public void setTraisenrolqr(Integer traisenrolqr) {
        this.traisenrolqr = traisenrolqr;
    }

    /**
     * 获取是否需要面试通知
     *
     * @return traisnotic - 是否需要面试通知
     */
    public Integer getTraisnotic() {
        return traisnotic;
    }

    /**
     * 设置是否需要面试通知
     *
     * @param traisnotic 是否需要面试通知
     */
    public void setTraisnotic(Integer traisnotic) {
        this.traisnotic = traisnotic;
    }

    /**
     * 获取必须实名报名
     *
     * @return traisrealname - 必须实名报名
     */
    public Integer getTraisrealname() {
        return traisrealname;
    }

    /**
     * 设置必须实名报名
     *
     * @param traisrealname 必须实名报名
     */
    public void setTraisrealname(Integer traisrealname) {
        this.traisrealname = traisrealname;
    }

    /**
     * 获取是否需要完善资料
     *
     * @return traisfulldata - 是否需要完善资料
     */
    public Integer getTraisfulldata() {
        return traisfulldata;
    }

    /**
     * 设置是否需要完善资料
     *
     * @param traisfulldata 是否需要完善资料
     */
    public void setTraisfulldata(Integer traisfulldata) {
        this.traisfulldata = traisfulldata;
    }

    /**
     * 获取是否需要上传附件
     *
     * @return traisattach - 是否需要上传附件
     */
    public Integer getTraisattach() {
        return traisattach;
    }

    /**
     * 设置是否需要上传附件
     *
     * @param traisattach 是否需要上传附件
     */
    public void setTraisattach(Integer traisattach) {
        this.traisattach = traisattach;
    }

    /**
     * 获取限制一人只能参加同类的一个培训,一个人只能同时参加一个培训类型的课程
     *
     * @return traisonlyone - 限制一人只能参加同类的一个培训,一个人只能同时参加一个培训类型的课程
     */
    public Integer getTraisonlyone() {
        return traisonlyone;
    }

    /**
     * 设置限制一人只能参加同类的一个培训,一个人只能同时参加一个培训类型的课程
     *
     * @param traisonlyone 限制一人只能参加同类的一个培训,一个人只能同时参加一个培训类型的课程
     */
    public void setTraisonlyone(Integer traisonlyone) {
        this.traisonlyone = traisonlyone;
    }

    /**
     * 获取允许个人报名.0-是;1-否
     *
     * @return tracanperson - 允许个人报名.0-是;1-否
     */
    public Integer getTracanperson() {
        return tracanperson;
    }

    /**
     * 设置允许个人报名.0-是;1-否
     *
     * @param tracanperson 允许个人报名.0-是;1-否
     */
    public void setTracanperson(Integer tracanperson) {
        this.tracanperson = tracanperson;
    }

    /**
     * 获取个人附件路径
     *
     * @return trapersonfile - 个人附件路径
     */
    public String getTrapersonfile() {
        return trapersonfile;
    }

    /**
     * 设置个人附件路径
     *
     * @param trapersonfile 个人附件路径
     */
    public void setTrapersonfile(String trapersonfile) {
        this.trapersonfile = trapersonfile;
    }

    /**
     * 获取允许团队报名.0-是;1-否
     *
     * @return tracanteam - 允许团队报名.0-是;1-否
     */
    public Integer getTracanteam() {
        return tracanteam;
    }

    /**
     * 设置允许团队报名.0-是;1-否
     *
     * @param tracanteam 允许团队报名.0-是;1-否
     */
    public void setTracanteam(Integer tracanteam) {
        this.tracanteam = tracanteam;
    }

    /**
     * 获取团队附件路径
     *
     * @return trateamfile - 团队附件路径
     */
    public String getTrateamfile() {
        return trateamfile;
    }

    /**
     * 设置团队附件路径
     *
     * @param trateamfile 团队附件路径
     */
    public void setTrateamfile(String trateamfile) {
        this.trateamfile = trateamfile;
    }

    /**
     * 获取课程简介，限制30个中文之内
     *
     * @return traintroduce - 课程简介，限制30个中文之内
     */
    public String getTraintroduce() {
        return traintroduce;
    }

    /**
     * 设置课程简介，限制30个中文之内
     *
     * @param traintroduce 课程简介，限制30个中文之内
     */
    public void setTraintroduce(String traintroduce) {
        this.traintroduce = traintroduce;
    }

    /**
     * 获取老师介绍
     *
     * @return trateacherdesc - 老师介绍
     */
    public String getTrateacherdesc() {
        return trateacherdesc;
    }

    /**
     * 设置老师介绍
     *
     * @param trateacherdesc 老师介绍
     */
    public void setTrateacherdesc(String trateacherdesc) {
        this.trateacherdesc = trateacherdesc;
    }

    /**
     * 获取报名介绍
     *
     * @return traenroldesc - 报名介绍
     */
    public String getTraenroldesc() {
        return traenroldesc;
    }

    /**
     * 设置报名介绍
     *
     * @param traenroldesc 报名介绍
     */
    public void setTraenroldesc(String traenroldesc) {
        this.traenroldesc = traenroldesc;
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

    /**
     * 获取培训课程详情介绍
     *
     * @return tradetail - 培训课程详情介绍
     */
    public String getTradetail() {
        return tradetail;
    }

    /**
     * 设置培训课程详情介绍
     *
     * @param tradetail 培训课程详情介绍
     */
    public void setTradetail(String tradetail) {
        this.tradetail = tradetail;
    }
    /**
     * 获取短标题
     * @return
     */
	public String getTrashorttitle() {
		return trashorttitle;
	}
	/**
	 * 设置短标题
	 * @param trashorttitle
	 */
	public void setTrashorttitle(String trashorttitle) {
		this.trashorttitle = trashorttitle;
	}

	/**
	 * 获取培训联系电话
	 * @return
	 */
	public String getTraphone() {
		return traphone;
	}
	/**
	 * 设置培训联系电话
	 * @param traphone
	 */
	public void setTraphone(String traphone) {
		this.traphone = traphone;
	}
	/**
	 * 获取联系人
	 * @return
	 */
	public String getTracontact() {
		return tracontact;
	}
	/**
	 * 设置联系人
	 * @param tracontact
	 */
	public void setTracontact(String tracontact) {
		this.tracontact = tracontact;
	}

	/**
	 * 获取是否需要收费
	 * @return
	 */
	public Integer getIsmoney() {
		return ismoney;
	}

	public void setIsmoney(Integer ismoney) {
		this.ismoney = ismoney;
	}
	
    
}