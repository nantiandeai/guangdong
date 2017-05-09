package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "whg_ven_room")
public class WhgVenRoom {
    /**
     * pk
     */
    @Id
    private String id;

    /**
     * 关联场馆id
     */
    private String venid;

    /**
     * 创建时间
     */
    private Date crtdate;

    /**
     * 创建人
     */
    private String crtuser;

    /**
     * 类型
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
     * 状态：1可编辑，9待审核，2待发布，6已发布，4已下架，5已撤消
     */
    private Integer state;

    /**
     * 删除状态：0未删除，1已删除
     */
    private Integer delstate;

    private Date statemdfdate;

    private String statemdfuser;

    /**
     * 是否推荐：0否1是
     */
    private Integer recommend;

    /**
     * 活动室名称
     */
    private String title;

    /**
     * 简介
     */
    private String summary;

    /**
     * 图片
     */
    private String imgurl;

    /**
     * 设施
     */
    private String facility;

    /**
     * 位置
     */
    private String location;

    /**
     * 面积大小
     */
    private Float sizearea;

    /**
     * 可容人数
     */
    private Integer sizepeople;

    /**
     * 坐位行数
     */
    private Integer seatrows;

    /**
     * 坐位列数
     */
    private Integer seatcols;

    /**
     * 开放参数：周日到周六 1,2,3,4,5,6,7
     */
    private String openweek;

    /**
     * 是否收费：0不收，1收
     */
    private Integer hasfees;

    /**
     * 描述
     */
    private String description;

    /**
     * 坐位模板信息json:[{numrow:行下标, numcol:列下标,type:1坐位0非坐位,numreal:编号}]
     */
    private String seatjson;

    /**
     * 开放时段模型json
     */
    private String weektimejson;

    /**
     * 获取pk
     *
     * @return id - pk
     */
    public String getId() {
        return id;
    }

    /**
     * 设置pk
     *
     * @param id pk
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取关联场馆id
     *
     * @return venid - 关联场馆id
     */
    public String getVenid() {
        return venid;
    }

    /**
     * 设置关联场馆id
     *
     * @param venid 关联场馆id
     */
    public void setVenid(String venid) {
        this.venid = venid;
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
     * 获取类型
     *
     * @return etype - 类型
     */
    public String getEtype() {
        return etype;
    }

    /**
     * 设置类型
     *
     * @param etype 类型
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
     * 获取状态：1可编辑，9待审核，2待发布，6已发布，4已下架，5已撤消
     *
     * @return state - 状态：1可编辑，9待审核，2待发布，6已发布，4已下架，5已撤消
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置状态：1可编辑，9待审核，2待发布，6已发布，4已下架，5已撤消
     *
     * @param state 状态：1可编辑，9待审核，2待发布，6已发布，4已下架，5已撤消
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取删除状态：0未删除，1已删除
     *
     * @return delstate - 删除状态：0未删除，1已删除
     */
    public Integer getDelstate() {
        return delstate;
    }

    /**
     * 设置删除状态：0未删除，1已删除
     *
     * @param delstate 删除状态：0未删除，1已删除
     */
    public void setDelstate(Integer delstate) {
        this.delstate = delstate;
    }

    /**
     * @return statemdfdate
     */
    public Date getStatemdfdate() {
        return statemdfdate;
    }

    /**
     * @param statemdfdate
     */
    public void setStatemdfdate(Date statemdfdate) {
        this.statemdfdate = statemdfdate;
    }

    /**
     * @return statemdfuser
     */
    public String getStatemdfuser() {
        return statemdfuser;
    }

    /**
     * @param statemdfuser
     */
    public void setStatemdfuser(String statemdfuser) {
        this.statemdfuser = statemdfuser;
    }

    /**
     * 获取是否推荐：0否1是
     *
     * @return recommend - 是否推荐：0否1是
     */
    public Integer getRecommend() {
        return recommend;
    }

    /**
     * 设置是否推荐：0否1是
     *
     * @param recommend 是否推荐：0否1是
     */
    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    /**
     * 获取活动室名称
     *
     * @return title - 活动室名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置活动室名称
     *
     * @param title 活动室名称
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取简介
     *
     * @return summary - 简介
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 设置简介
     *
     * @param summary 简介
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * 获取图片
     *
     * @return imgurl - 图片
     */
    public String getImgurl() {
        return imgurl;
    }

    /**
     * 设置图片
     *
     * @param imgurl 图片
     */
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    /**
     * 获取设施
     *
     * @return facility - 设施
     */
    public String getFacility() {
        return facility;
    }

    /**
     * 设置设施
     *
     * @param facility 设施
     */
    public void setFacility(String facility) {
        this.facility = facility;
    }

    /**
     * 获取位置
     *
     * @return location - 位置
     */
    public String getLocation() {
        return location;
    }

    /**
     * 设置位置
     *
     * @param location 位置
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * 获取面积大小
     *
     * @return sizearea - 面积大小
     */
    public Float getSizearea() {
        return sizearea;
    }

    /**
     * 设置面积大小
     *
     * @param sizearea 面积大小
     */
    public void setSizearea(Float sizearea) {
        this.sizearea = sizearea;
    }

    /**
     * 获取可容人数
     *
     * @return sizepeople - 可容人数
     */
    public Integer getSizepeople() {
        return sizepeople;
    }

    /**
     * 设置可容人数
     *
     * @param sizepeople 可容人数
     */
    public void setSizepeople(Integer sizepeople) {
        this.sizepeople = sizepeople;
    }

    /**
     * 获取坐位行数
     *
     * @return seatrows - 坐位行数
     */
    public Integer getSeatrows() {
        return seatrows;
    }

    /**
     * 设置坐位行数
     *
     * @param seatrows 坐位行数
     */
    public void setSeatrows(Integer seatrows) {
        this.seatrows = seatrows;
    }

    /**
     * 获取坐位列数
     *
     * @return seatcols - 坐位列数
     */
    public Integer getSeatcols() {
        return seatcols;
    }

    /**
     * 设置坐位列数
     *
     * @param seatcols 坐位列数
     */
    public void setSeatcols(Integer seatcols) {
        this.seatcols = seatcols;
    }

    /**
     * 获取开放参数：周日到周六 1,2,3,4,5,6,7
     *
     * @return openweek - 开放参数：周日到周六 1,2,3,4,5,6,7
     */
    public String getOpenweek() {
        return openweek;
    }

    /**
     * 设置开放参数：周日到周六 1,2,3,4,5,6,7
     *
     * @param openweek 开放参数：周日到周六 1,2,3,4,5,6,7
     */
    public void setOpenweek(String openweek) {
        this.openweek = openweek;
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
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取坐位模板信息json:[{numrow:行下标, numcol:列下标,type:1坐位0非坐位,numreal:编号}]
     *
     * @return seatjson - 坐位模板信息json:[{numrow:行下标, numcol:列下标,type:1坐位0非坐位,numreal:编号}]
     */
    public String getSeatjson() {
        return seatjson;
    }

    /**
     * 设置坐位模板信息json:[{numrow:行下标, numcol:列下标,type:1坐位0非坐位,numreal:编号}]
     *
     * @param seatjson 坐位模板信息json:[{numrow:行下标, numcol:列下标,type:1坐位0非坐位,numreal:编号}]
     */
    public void setSeatjson(String seatjson) {
        this.seatjson = seatjson;
    }

    /**
     * 获取开放时段模型json
     *
     * @return weektimejson - 开放时段模型json
     */
    public String getWeektimejson() {
        return weektimejson;
    }

    /**
     * 设置开放时段模型json
     *
     * @param weektimejson 开放时段模型json
     */
    public void setWeektimejson(String weektimejson) {
        this.weektimejson = weektimejson;
    }
}