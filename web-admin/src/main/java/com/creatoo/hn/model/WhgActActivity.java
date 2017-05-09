package com.creatoo.hn.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Table(name = "whg_act_activity")
public class WhgActActivity {
    /**
     * 主键ID
     */
    @Id
    private String id;

    /**
     * 创建时间
     */
    private Date crtdate;

    /**
     * 创建人
     */
    private String crtuser;

    /**
     * 1：可编辑, 9：待审核, 2：待发布，6：已发布, 4：已下架
     */
    private Integer state;

    /**
     * 状态变更时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date statemdfdate;

    /**
     * 状态变更用户ID
     */
    private String statemdfuser;

    /**
     * 删除状态 0：未删除 1： 删除
     */
    private Integer delstate;

    /**
     * 艺术分类
     */
    private String arttype;

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
     * 权限字段
     */
    private String epms;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 活动封面
     */
    private String imgurl;

    /**
     *  场馆Id
     */
    private String venueid;

    /**
     * 主办方
     */
    private String host;

    /**
     * 承办单位
     */
    private String organizer;

    /**
     * 协办单位
     */
    private String coorganizer;

    /**
     * 演出单位
     */
    private String performed;

    /**
     * 主讲人
     */
    private String speaker;

    /**
     * 活动开始时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date starttime;

    /**
     * 活动结束时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endtime;

    /**
     * 活动地址
     */
    private String address;

    /**
     * 活动电话
     */
    private String telphone;

    /**
     * 是否消耗活动积分 1：是 2：否
     */
    private Integer integral;
    
    /**
     * 积分数
     */
    private Integer integralnum;
    
    public Integer getIntegralnum() {
		return integralnum;
	}

	public void setIntegralnum(Integer integralnum) {
		this.integralnum = integralnum;
	}

	/**
     * 在线售票 1：不可预定 2：自由入座 3：在线选票
     */
    private Integer sellticket;

    /**
     * 附件
     */
    private String filepath;

    /**
     * 活动坐标经度
     */
    private Double actlon;

    /**
     * 活动坐标维度
     */
    private Double actlat;

    /**
     * 文化馆ID
     */
    private String cultid;

    /**
     * 部门ID
     */
    private String deptid;

    /**
     * 报名开始时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date enterstrtime;

    /**
     * 报名结束时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date enterendtime;

    /**
     * 活动室ID
     */
    private String roomid;

    /**
     * 是否推荐 0：否 1：是
     */
    private Integer isrecommend;

    /**
     * 座位总数
     */
    private Integer seatcount;

    /**
     * 每场次售票张数
     */
    private Integer ticketnum;

    /**
     * 是否收费：0不收，1收
     */
    private Integer hasfees;

    /**
     * 每场每人限制预定座位数
     */
    private Integer seats;

    /**
     * 区域ID
     */
    private String areaid;

    /**
     * 活动简介
     */
    private String actsummary;

    /**
     * 活动描述
     */
    private String remark;
    
    /**
     * 是否需要实名认证 1：是 0：否
     */
    private int isrealname;

    public int getIsrealname() {
		return isrealname;
	}

	public void setIsrealname(int isrealname) {
		this.isrealname = isrealname;
	}

	/**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
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
     * 获取1：可编辑, 9：待审核, 2：待发布，6：已发布, 4：已下架
     *
     * @return state - 1：可编辑, 9：待审核, 2：待发布，6：已发布, 4：已下架
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置1：可编辑, 9：待审核, 2：待发布，6：已发布, 4：已下架
     *
     * @param state 1：可编辑, 9：待审核, 2：待发布，6：已发布, 4：已下架
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
     * 获取删除状态 0：未删除 1： 删除
     *
     * @return delstate - 删除状态 0：未删除 1： 删除
     */
    public Integer getDelstate() {
        return delstate;
    }

    /**
     * 设置删除状态 0：未删除 1： 删除
     *
     * @param delstate 删除状态 0：未删除 1： 删除
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
     * 获取权限字段
     *
     * @return epms - 权限字段
     */
    public String getEpms() {
        return epms;
    }

    /**
     * 设置权限字段
     *
     * @param epms 权限字段
     */
    public void setEpms(String epms) {
        this.epms = epms;
    }

    /**
     * 获取活动名称
     *
     * @return name - 活动名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置活动名称
     *
     * @param name 活动名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取活动封面
     *
     * @return imgurl - 活动封面
     */
    public String getImgurl() {
        return imgurl;
    }

    /**
     * 设置活动封面
     *
     * @param imgurl 活动封面
     */
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    /**
     * 获取 场馆Id
     *
     * @return venueid -  场馆Id
     */
    public String getVenueid() {
        return venueid;
    }

    /**
     * 设置 场馆Id
     *
     * @param venueid  场馆Id
     */
    public void setVenueid(String venueid) {
        this.venueid = venueid;
    }

    /**
     * 获取主办方
     *
     * @return host - 主办方
     */
    public String getHost() {
        return host;
    }

    /**
     * 设置主办方
     *
     * @param host 主办方
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * 获取承办单位
     *
     * @return organizer - 承办单位
     */
    public String getOrganizer() {
        return organizer;
    }

    /**
     * 设置承办单位
     *
     * @param organizer 承办单位
     */
    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    /**
     * 获取协办单位
     *
     * @return coorganizer - 协办单位
     */
    public String getCoorganizer() {
        return coorganizer;
    }

    /**
     * 设置协办单位
     *
     * @param coorganizer 协办单位
     */
    public void setCoorganizer(String coorganizer) {
        this.coorganizer = coorganizer;
    }

    /**
     * 获取演出单位
     *
     * @return performed - 演出单位
     */
    public String getPerformed() {
        return performed;
    }

    /**
     * 设置演出单位
     *
     * @param performed 演出单位
     */
    public void setPerformed(String performed) {
        this.performed = performed;
    }

    /**
     * 获取主讲人
     *
     * @return speaker - 主讲人
     */
    public String getSpeaker() {
        return speaker;
    }

    /**
     * 设置主讲人
     *
     * @param speaker 主讲人
     */
    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    /**
     * 获取活动开始时间
     *
     * @return starttime - 活动开始时间
     */
    public Date getStarttime() {
        return starttime;
    }

    /**
     * 设置活动开始时间
     *
     * @param starttime 活动开始时间
     */
    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    /**
     * 获取活动结束时间
     *
     * @return endtime - 活动结束时间
     */
    public Date getEndtime() {
        return endtime;
    }

    /**
     * 设置活动结束时间
     *
     * @param endtime 活动结束时间
     */
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    /**
     * 获取活动地址
     *
     * @return address - 活动地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置活动地址
     *
     * @param address 活动地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取活动电话
     *
     * @return telphone - 活动电话
     */
    public String getTelphone() {
        return telphone;
    }

    /**
     * 设置活动电话
     *
     * @param telphone 活动电话
     */
    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    /**
     * 获取是否消耗活动积分 1：是 2：否
     *
     * @return integral - 是否消耗活动积分 1：是 2：否
     */
    public Integer getIntegral() {
        return integral;
    }

    /**
     * 设置是否消耗活动积分 1：是 2：否
     *
     * @param integral 是否消耗活动积分 1：是 2：否
     */
    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    /**
     * 获取在线售票 1：不可预定 2：自由入座 3：在线选票
     *
     * @return sellticket - 在线售票 1：不可预定 2：自由入座 3：在线选票
     */
    public Integer getSellticket() {
        return sellticket;
    }

    /**
     * 设置在线售票 1：不可预定 2：自由入座 3：在线选票
     *
     * @param sellticket 在线售票 1：不可预定 2：自由入座 3：在线选票
     */
    public void setSellticket(Integer sellticket) {
        this.sellticket = sellticket;
    }

    /**
     * 获取附件
     *
     * @return filepath - 附件
     */
    public String getFilepath() {
        return filepath;
    }

    /**
     * 设置附件
     *
     * @param filepath 附件
     */
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    /**
     * 获取活动坐标经度
     *
     * @return actlon - 活动坐标经度
     */
    public Double getActlon() {
        return actlon;
    }

    /**
     * 设置活动坐标经度
     *
     * @param actlon 活动坐标经度
     */
    public void setActlon(Double actlon) {
        this.actlon = actlon;
    }

    /**
     * 获取活动坐标维度
     *
     * @return actlat - 活动坐标维度
     */
    public Double getActlat() {
        return actlat;
    }

    /**
     * 设置活动坐标维度
     *
     * @param actlat 活动坐标维度
     */
    public void setActlat(Double actlat) {
        this.actlat = actlat;
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
     * 获取部门ID
     *
     * @return deptid - 部门ID
     */
    public String getDeptid() {
        return deptid;
    }

    /**
     * 设置部门ID
     *
     * @param deptid 部门ID
     */
    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    /**
     * 获取报名开始时间
     *
     * @return enterstrtime - 报名开始时间
     */
    public Date getEnterstrtime() {
        return enterstrtime;
    }

    /**
     * 设置报名开始时间
     *
     * @param enterstrtime 报名开始时间
     */
    public void setEnterstrtime(Date enterstrtime) {
        this.enterstrtime = enterstrtime;
    }

    /**
     * 获取报名结束时间
     *
     * @return enterendtime - 报名结束时间
     */
    public Date getEnterendtime() {
        return enterendtime;
    }

    /**
     * 设置报名结束时间
     *
     * @param enterendtime 报名结束时间
     */
    public void setEnterendtime(Date enterendtime) {
        this.enterendtime = enterendtime;
    }

    /**
     * 获取活动室ID
     *
     * @return roomid - 活动室ID
     */
    public String getRoomid() {
        return roomid;
    }

    /**
     * 设置活动室ID
     *
     * @param roomid 活动室ID
     */
    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    /**
     * 获取是否推荐 0：否 1：是
     *
     * @return isrecommend - 是否推荐 0：否 1：是
     */
    public Integer getIsrecommend() {
        return isrecommend;
    }

    /**
     * 设置是否推荐 0：否 1：是
     *
     * @param isrecommend 是否推荐 0：否 1：是
     */
    public void setIsrecommend(Integer isrecommend) {
        this.isrecommend = isrecommend;
    }

    /**
     * 获取座位总数
     *
     * @return seatcount - 座位总数
     */
    public Integer getSeatcount() {
        return seatcount;
    }

    /**
     * 设置座位总数
     *
     * @param seatcount 座位总数
     */
    public void setSeatcount(Integer seatcount) {
        this.seatcount = seatcount;
    }

    /**
     * 获取每场次售票张数
     *
     * @return ticketnum - 每场次售票张数
     */
    public Integer getTicketnum() {
        return ticketnum;
    }

    /**
     * 设置每场次售票张数
     *
     * @param ticketnum 每场次售票张数
     */
    public void setTicketnum(Integer ticketnum) {
        this.ticketnum = ticketnum;
    }

    /**
     * 获取是否收费：0不收，1收
     *
     * @return hasfees - 是否收费：0不收，1收
     */
    public Integer getHasfees() {
        return hasfees;
    }

    /**
     * 设置是否收费：0不收，1收
     *
     * @param hasfees 是否收费：0不收，1收
     */
    public void setHasfees(Integer hasfees) {
        this.hasfees = hasfees;
    }

    /**
     * 获取每场每人限制预定座位数
     *
     * @return seats - 每场每人限制预定座位数
     */
    public Integer getSeats() {
        return seats;
    }

    /**
     * 设置每场每人限制预定座位数
     *
     * @param seats 每场每人限制预定座位数
     */
    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    /**
     * 获取区域ID
     *
     * @return areaid - 区域ID
     */
    public String getAreaid() {
        return areaid;
    }

    /**
     * 设置区域ID
     *
     * @param areaid 区域ID
     */
    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    /**
     * 获取活动简介
     *
     * @return actsummary - 活动简介
     */
    public String getActsummary() {
        return actsummary;
    }

    /**
     * 设置活动简介
     *
     * @param actsummary 活动简介
     */
    public void setActsummary(String actsummary) {
        this.actsummary = actsummary;
    }

    /**
     * 获取活动描述
     *
     * @return remark - 活动描述
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置活动描述
     *
     * @param remark 活动描述
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public  static  WhgActActivity getInstanceByRequest(HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        WhgActActivity whgActActivity = new WhgActActivity();
        whgActActivity.setId(request.getParameter("id"));
        whgActActivity.setName(request.getParameter("name"));
        whgActActivity.setActsummary(request.getParameter("actsummary"));
        whgActActivity.setImgurl(request.getParameter("imgurl"));
//        whgActActivity.setFilepath(request.getParameter("filepath"));
        whgActActivity.setAreaid(request.getParameter("areaid"));
        whgActActivity.setVenueid(request.getParameter("venueid"));
        whgActActivity.setRoomid(request.getParameter("roomid"));
//        whgActActivity.setEbrand(request.getParameter("ebrand"));
        whgActActivity.setHost(request.getParameter("host"));
        whgActActivity.setOrganizer(request.getParameter("organizer"));
        whgActActivity.setCoorganizer(request.getParameter("coorganizer"));
        whgActActivity.setPerformed(request.getParameter("performed"));
        whgActActivity.setSpeaker(request.getParameter("speaker"));
        whgActActivity.setCultid(request.getParameter("cultid"));
        //处理多值
        String[] etype = request.getParameterValues("etype");
        if(null != etype){
            whgActActivity.setEtype(String.join(",",etype));
        }
        String[] etag = request.getParameterValues("etag");
        if(null != etag){
            whgActActivity.setEtag(String.join(",",etag));
        }
        String[] arttype = request.getParameterValues("arttype");
        if(null != arttype){
            whgActActivity.setArttype(String.join(",",arttype));
        }
        String[] filepath = request.getParameterValues("filepath");
        if(null != filepath){
            whgActActivity.setFilepath(String.join(",",filepath));
        }
        String[] ekey = request.getParameterValues("ekey");
        if(null != ekey){
            whgActActivity.setEkey(String.join(",",ekey));
        }
        String[] ebrand = request.getParameterValues("ebrand");
        if(null != ebrand){
            whgActActivity.setEbrand(String.join(",",ebrand));
        }
        String enterstrtime = request.getParameter("enterstrtime");
        if(null != enterstrtime){
            try {
                whgActActivity.setEnterstrtime(sdfTime.parse(enterstrtime));
            }catch (Exception e){
                System.out.println(e.toString());
            }
        }
        String enterendtime = request.getParameter("enterendtime");
        if(null != enterendtime){
            try {
                whgActActivity.setEnterendtime(sdfTime.parse(enterendtime));
            }catch (Exception e){
                System.out.println(e.toString());
            }
        }
        String starttime = request.getParameter("starttime");
        if(null != starttime){
            try {
                whgActActivity.setStarttime(sdf.parse(starttime));
            }catch (Exception e){
                System.out.println(e.toString());
            }
        }
        String endtime = request.getParameter("endtime");
        if(null != endtime){
            try {
                whgActActivity.setEndtime(sdf.parse(endtime));
            }catch (Exception e){
                System.out.println(e.toString());
            }
        }
        whgActActivity.setAddress(request.getParameter("address"));
        try{
            whgActActivity.setActlon(Double.valueOf(request.getParameter("actlon")));
        }catch (Exception e){
            System.out.println(e.toString());
        }
        try{
            whgActActivity.setActlat(Double.valueOf(request.getParameter("actlat")));
        }catch (Exception e){
            System.out.println(e.toString());
        }
        whgActActivity.setTelphone(request.getParameter("telphone"));
        try {
            whgActActivity.setIntegral(Integer.valueOf(request.getParameter("integral")));
        }catch (Exception e){
            System.out.println(e.toString());
        }
        try {
            whgActActivity.setIntegralnum(Integer.valueOf(request.getParameter("integralnum")));
        }catch (Exception e){
            System.out.println(e.toString());
        }
        try {
            whgActActivity.setHasfees(Integer.valueOf(request.getParameter("hasfees")));
        }catch (Exception e){
            System.out.println(e.toString());
        }
        try {
            whgActActivity.setSellticket(Integer.valueOf(request.getParameter("sellticket")));
            if(2 == whgActActivity.getSellticket()){
                whgActActivity.setTicketnum(Integer.valueOf(request.getParameter("ticketnum")));
                whgActActivity.setSeats(Integer.valueOf(request.getParameter("seats")));
            }
            if(3 == whgActActivity.getSellticket()){
                whgActActivity.setSeats(Integer.valueOf(request.getParameter("seats")));
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        whgActActivity.setRemark(request.getParameter("remark"));
        //whgActActivity.setState(1);
        WhgSysUser whgSysUser = (WhgSysUser)request.getSession().getAttribute("user");
        if(null != whgSysUser){
            whgActActivity.setStatemdfuser(whgSysUser.getId());
            whgActActivity.setCrtdate(new Date());
        }
        whgActActivity.setDelstate(0);
        return whgActActivity;
    }
}