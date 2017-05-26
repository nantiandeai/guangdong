package com.creatoo.hn.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "whg_tra")
public class WhgTra {
    /**
     * 培训ID
     */
    @Id
    private String id;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date crtdate;

    /**
     * 创建人
     */
    private String crtuser;

    /**
     * 状态(1-可编辑, 9-待审核, 2-待发布，6-已发布, 4-已下架, 5-已撤消)
     */
    private Integer state;

    /**
     * 状态变更时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date statemdfdate;

    /**
     * 状态变更用户ID
     */
    private String statemdfuser;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 删除状态(0-未删除；1-已删除)
     */
    private Integer delstate;

    /**
     * 艺术分类
     */
    private String arttype;

    /**
     * 老师ID
     */
    private String teacherid;

    /**
     * 老师名字
     */
    private String teachername;

    /**
     * 分类
     */
    private String etype;

    /**
     * 标签
     */
    private String etag;

    /**
     * 关键字
     */
    private String ekey;

    /**
     * 文化品牌
     */
    private String ebrand;

    /**
     * 培训名称
     */
    private String title;

    /**
     * 区域
     */
    private String area;

    /**
     * 培训所在场馆
     */
    private String venue;

    /**
     * 培训地址
     */
    private String address;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 是否实名(0:否 1:是)
     */
    private Integer isrealname;

    /**
     * 培训开始时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date starttime;

    /**
     * 培训结束时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endtime;

    /**
     * 培训图片
     */
    private String trainimg;

    /**
     * 报名人数上限
     */
    private Integer maxnumber;

    /**
     * 报名开始时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date enrollstarttime;

    /**
     * 报名结束时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date enrollendtime;

    /**
     * 是否学期制
     */
    private Integer isterm;

    /**
     * 是否基础培训(0:需要面试，1:需人工录取 2、即报即得)
     */
    private Integer isbasicclass;

    /**
     * 基础报名人数
     */
    private Integer basicenrollnumber;

    /**
     * 是否多场次(0:单场 1:多场 )
     */
    private Integer ismultisite;

    /**
     * 是否显示最大报名人数
     */
    private Integer isshowmaxnumber;

    /**
     * 固定场开始时段
     */
    @DateTimeFormat(pattern="HH:mm")
    private Date fixedstarttime;

    /**
     * 固定场结束时段
     */
    @DateTimeFormat(pattern="HH:mm")
    private Date fixedendtime;

    /**
     * 审核不通过/上架不通过原因
     */
    private String trainbackreason;

    /**
     * 文化馆ID
     */
    private String cultid;

    /**
     * 所属部门ID
     */
    private String deptid;

    /**
     * 固定班周几
     */
    private String fixedweek;

    /**
     * 课程时间
     */
    private String coursetime;

    /**
     * 所在活动室
     */
    private String venroom;

    /**
     * 是否推荐（0、不推荐 1、推荐）
     */
    private Integer recommend;

    /**
     * 是否上首页
     */
    private Integer upindex;

    /**
     * 培训课程描述
     */
    private String coursedesc;

    /**
     * 适合年龄
     */
    private String age;

    /**
     * 是否收费
     */
    private Integer ismoney;

    /**
     * 培训大纲
     */
    private String outline;

    /**
     * 培训老师介绍
     */
    private String teacherdesc;

    /**
     * 获取培训ID
     *
     * @return id - 培训ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置培训ID
     *
     * @param id 培训ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取创建时间
     *
     * @return crtdate - 创建时间
     */
    public Date getCrtdate() {
        return crtdate;
    }

    /**
     * 设置创建时间
     *
     * @param crtdate 创建时间
     */
    public void setCrtdate(Date crtdate) {
        this.crtdate = crtdate;
    }

    /**
     * 获取创建人
     *
     * @return crtuser - 创建人
     */
    public String getCrtuser() {
        return crtuser;
    }

    /**
     * 设置创建人
     *
     * @param crtuser 创建人
     */
    public void setCrtuser(String crtuser) {
        this.crtuser = crtuser;
    }

    /**
     * 获取状态(1-可编辑, 9-待审核, 2-待发布，6-已发布, 4-已下架, 5-已撤消)
     *
     * @return state - 状态(1-可编辑, 9-待审核, 2-待发布，6-已发布, 4-已下架, 5-已撤消)
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置状态(1-可编辑, 9-待审核, 2-待发布，6-已发布, 4-已下架, 5-已撤消)
     *
     * @param state 状态(1-可编辑, 9-待审核, 2-待发布，6-已发布, 4-已下架, 5-已撤消)
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取状态变更时间
     *
     * @return statemdfdate - 状态变更时间
     */
    public Date getStatemdfdate() {
        return statemdfdate;
    }

    /**
     * 设置状态变更时间
     *
     * @param statemdfdate 状态变更时间
     */
    public void setStatemdfdate(Date statemdfdate) {
        this.statemdfdate = statemdfdate;
    }

    /**
     * 获取状态变更用户ID
     *
     * @return statemdfuser - 状态变更用户ID
     */
    public String getStatemdfuser() {
        return statemdfuser;
    }

    /**
     * 设置状态变更用户ID
     *
     * @param statemdfuser 状态变更用户ID
     */
    public void setStatemdfuser(String statemdfuser) {
        this.statemdfuser = statemdfuser;
    }

    /**
     * 获取删除状态(0-未删除；1-已删除)
     *
     * @return delstate - 删除状态(0-未删除；1-已删除)
     */
    public Integer getDelstate() {
        return delstate;
    }

    /**
     * 设置删除状态(0-未删除；1-已删除)
     *
     * @param delstate 删除状态(0-未删除；1-已删除)
     */
    public void setDelstate(Integer delstate) {
        this.delstate = delstate;
    }

    /**
     * 获取艺术分类
     *
     * @return arttype - 艺术分类
     */
    public String getArttype() {
        return arttype;
    }

    /**
     * 设置艺术分类
     *
     * @param arttype 艺术分类
     */
    public void setArttype(String arttype) {
        this.arttype = arttype;
    }

    /**
     * 获取分类
     *
     * @return etype - 分类
     */
    public String getEtype() {
        return etype;
    }

    /**
     * 设置分类
     *
     * @param etype 分类
     */
    public void setEtype(String etype) {
        this.etype = etype;
    }

    /**
     * 获取标签
     *
     * @return etag - 标签
     */
    public String getEtag() {
        return etag;
    }

    /**
     * 设置标签
     *
     * @param etag 标签
     */
    public void setEtag(String etag) {
        this.etag = etag;
    }

    /**
     * 获取关键字
     *
     * @return ekey - 关键字
     */
    public String getEkey() {
        return ekey;
    }

    /**
     * 设置关键字
     *
     * @param ekey 关键字
     */
    public void setEkey(String ekey) {
        this.ekey = ekey;
    }

    /**
     * 获取文化品牌
     *
     * @return ebrand - 文化品牌
     */
    public String getEbrand() {
        return ebrand;
    }

    /**
     * 设置文化品牌
     *
     * @param ebrand 文化品牌
     */
    public void setEbrand(String ebrand) {
        this.ebrand = ebrand;
    }

    /**
     * 获取培训名称
     *
     * @return title - 培训名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置培训名称
     *
     * @param title 培训名称
     */
    public void setTitle(String title) {
        this.title = title;
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
     * 获取培训所在场馆
     *
     * @return venue - 培训所在场馆
     */
    public String getVenue() {
        return venue;
    }

    /**
     * 设置培训所在场馆
     *
     * @param venue 培训所在场馆
     */
    public void setVenue(String venue) {
        this.venue = venue;
    }

    /**
     * 获取培训地址
     *
     * @return address - 培训地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置培训地址
     *
     * @param address 培训地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取经度
     *
     * @return longitude - 经度
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * 设置经度
     *
     * @param longitude 经度
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * 获取纬度
     *
     * @return latitude - 纬度
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * 设置纬度
     *
     * @param latitude 纬度
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * 获取是否实名(0:否 1:是)
     *
     * @return isrealname - 是否实名(0:否 1:是)
     */
    public Integer getIsrealname() {
        return isrealname;
    }

    /**
     * 设置是否实名(0:否 1:是)
     *
     * @param isrealname 是否实名(0:否 1:是)
     */
    public void setIsrealname(Integer isrealname) {
        this.isrealname = isrealname;
    }

    /**
     * 获取培训开始时间
     *
     * @return starttime - 培训开始时间
     */
    public Date getStarttime() {
        return starttime;
    }

    /**
     * 设置培训开始时间
     *
     * @param starttime 培训开始时间
     */
    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    /**
     * 获取培训结束时间
     *
     * @return endtime - 培训结束时间
     */
    public Date getEndtime() {
        return endtime;
    }

    /**
     * 设置培训结束时间
     *
     * @param endtime 培训结束时间
     */
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    /**
     * 获取培训图片
     *
     * @return trainimg - 培训图片
     */
    public String getTrainimg() {
        return trainimg;
    }

    /**
     * 设置培训图片
     *
     * @param trainimg 培训图片
     */
    public void setTrainimg(String trainimg) {
        this.trainimg = trainimg;
    }

    /**
     * 获取报名人数上限
     *
     * @return maxnumber - 报名人数上限
     */
    public Integer getMaxnumber() {
        return maxnumber;
    }

    /**
     * 设置报名人数上限
     *
     * @param maxnumber 报名人数上限
     */
    public void setMaxnumber(Integer maxnumber) {
        this.maxnumber = maxnumber;
    }

    /**
     * 获取报名开始时间
     *
     * @return enrollstarttime - 报名开始时间
     */
    public Date getEnrollstarttime() {
        return enrollstarttime;
    }

    /**
     * 设置报名开始时间
     *
     * @param enrollstarttime 报名开始时间
     */
    public void setEnrollstarttime(Date enrollstarttime) {
        this.enrollstarttime = enrollstarttime;
    }

    /**
     * 获取报名结束时间
     *
     * @return enrollendtime - 报名结束时间
     */
    public Date getEnrollendtime() {
        return enrollendtime;
    }

    /**
     * 设置报名结束时间
     *
     * @param enrollendtime 报名结束时间
     */
    public void setEnrollendtime(Date enrollendtime) {
        this.enrollendtime = enrollendtime;
    }

    /**
     * 获取是否学期制
     *
     * @return isterm - 是否学期制
     */
    public Integer getIsterm() {
        return isterm;
    }

    /**
     * 设置是否学期制
     *
     * @param isterm 是否学期制
     */
    public void setIsterm(Integer isterm) {
        this.isterm = isterm;
    }

    /**
     * 获取是否基础培训(0:需要面试，1:需人工录取 2、即报即得)
     *
     * @return isbasicclass - 是否基础培训(0:需要面试，1:需人工录取 2、即报即得)
     */
    public Integer getIsbasicclass() {
        return isbasicclass;
    }

    /**
     * 设置是否基础培训(0:需要面试，1:需人工录取 2、即报即得)
     *
     * @param isbasicclass 是否基础培训(0:需要面试，1:需人工录取 2、即报即得)
     */
    public void setIsbasicclass(Integer isbasicclass) {
        this.isbasicclass = isbasicclass;
    }

    /**
     * 获取基础报名人数
     *
     * @return basicenrollnumber - 基础报名人数
     */
    public Integer getBasicenrollnumber() {
        return basicenrollnumber;
    }

    /**
     * 设置基础报名人数
     *
     * @param basicenrollnumber 基础报名人数
     */
    public void setBasicenrollnumber(Integer basicenrollnumber) {
        this.basicenrollnumber = basicenrollnumber;
    }

    /**
     * 获取是否多场次(0:单场 1:多场 )
     *
     * @return ismultisite - 是否多场次(0:单场 1:多场 )
     */
    public Integer getIsmultisite() {
        return ismultisite;
    }

    /**
     * 设置是否多场次(0:单场 1:多场 )
     *
     * @param ismultisite 是否多场次(0:单场 1:多场 )
     */
    public void setIsmultisite(Integer ismultisite) {
        this.ismultisite = ismultisite;
    }

    /**
     * 获取是否显示最大报名人数
     *
     * @return isshowmaxnumber - 是否显示最大报名人数
     */
    public Integer getIsshowmaxnumber() {
        return isshowmaxnumber;
    }

    /**
     * 设置是否显示最大报名人数
     *
     * @param isshowmaxnumber 是否显示最大报名人数
     */
    public void setIsshowmaxnumber(Integer isshowmaxnumber) {
        this.isshowmaxnumber = isshowmaxnumber;
    }

    /**
     * 获取审核不通过/上架不通过原因
     *
     * @return trainbackreason - 审核不通过/上架不通过原因
     */
    public String getTrainbackreason() {
        return trainbackreason;
    }

    /**
     * 设置审核不通过/上架不通过原因
     *
     * @param trainbackreason 审核不通过/上架不通过原因
     */
    public void setTrainbackreason(String trainbackreason) {
        this.trainbackreason = trainbackreason;
    }

    /**
     * 获取文化馆ID
     *
     * @return cultid - 文化馆ID
     */
    public String getCultid() {
        return cultid;
    }

    /**
     * 设置文化馆ID
     *
     * @param cultid 文化馆ID
     */
    public void setCultid(String cultid) {
        this.cultid = cultid;
    }

    /**
     * 获取所属部门ID
     *
     * @return deptid - 所属部门ID
     */
    public String getDeptid() {
        return deptid;
    }

    /**
     * 设置所属部门ID
     *
     * @param deptid 所属部门ID
     */
    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    /**
     * 获取固定班周几
     *
     * @return fixedweek - 固定班周几
     */
    public String getFixedweek() {
        return fixedweek;
    }

    /**
     * 设置固定班周几
     *
     * @param fixedweek 固定班周几
     */
    public void setFixedweek(String fixedweek) {
        this.fixedweek = fixedweek;
    }

    /**
     * 获取课程时间
     *
     * @return coursetime - 课程时间
     */
    public String getCoursetime() {
        return coursetime;
    }

    /**
     * 设置课程时间
     *
     * @param coursetime 课程时间
     */
    public void setCoursetime(String coursetime) {
        this.coursetime = coursetime;
    }

    /**
     * 获取所在活动室
     *
     * @return venroom - 所在活动室
     */
    public String getVenroom() {
        return venroom;
    }

    /**
     * 设置所在活动室
     *
     * @param venroom 所在活动室
     */
    public void setVenroom(String venroom) {
        this.venroom = venroom;
    }

    /**
     * 获取是否推荐（0、不推荐 1、推荐）
     *
     * @return recommend - 是否推荐（0、不推荐 1、推荐）
     */
    public Integer getRecommend() {
        return recommend;
    }

    /**
     * 设置是否推荐（0、不推荐 1、推荐）
     *
     * @param recommend 是否推荐（0、不推荐 1、推荐）
     */
    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    /**
     * 获取培训课程描述
     *
     * @return coursedesc - 培训课程描述
     */
    public String getCoursedesc() {
        return coursedesc;
    }

    /**
     * 设置培训课程描述
     *
     * @param coursedesc 培训课程描述
     */
    public void setCoursedesc(String coursedesc) {
        this.coursedesc = coursedesc;
    }

    /**
     * 获取培训大纲
     *
     * @return outline - 培训大纲
     */
    public String getOutline() {
        return outline;
    }

    /**
     * 设置培训大纲
     *
     * @param outline 培训大纲
     */
    public void setOutline(String outline) {
        this.outline = outline;
    }

    /**
     * 获取培训老师介绍
     *
     * @return teacherdesc - 培训老师介绍
     */
    public String getTeacherdesc() {
        return teacherdesc;
    }

    /**
     * 设置培训老师介绍
     *
     * @param teacherdesc 培训老师介绍
     */
    public void setTeacherdesc(String teacherdesc) {
        this.teacherdesc = teacherdesc;
    }

    public Date getFixedstarttime() {
        return fixedstarttime;
    }

    public void setFixedstarttime(Date fixedstarttime) {
        this.fixedstarttime = fixedstarttime;
    }

    public Date getFixedendtime() {
        return fixedendtime;
    }

    public void setFixedendtime(Date fixedendtime) {
        this.fixedendtime = fixedendtime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(String teacherid) {
        this.teacherid = teacherid;
    }

    public String getTeachername() {
        return teachername;
    }

    public void setTeachername(String teachername) {
        this.teachername = teachername;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Integer getIsmoney() {
        return ismoney;
    }

    public void setIsmoney(Integer ismoney) {
        this.ismoney = ismoney;
    }

    public Integer getUpindex() {
        return upindex;
    }

    public void setUpindex(Integer upindex) {
        this.upindex = upindex;
    }
}