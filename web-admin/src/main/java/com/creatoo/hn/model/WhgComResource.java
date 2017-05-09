package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "whg_com_resource")
public class WhgComResource {
    /**
     * 资源ID
     */
    @Id
    private String entid;

    /**
     * 资源类型（图片/音频/视频）
     */
    private String enttype;

    /**
     * 实体类型（1、培训 2、活动 3、场馆）
     */
    private String reftype;

    /**
     * 实体id(培训/活动的ID)
     */
    private String refid;

    /**
     * 资源的地址
     */
    private String enturl;

    /**
     * 资源的名字
     */
    private String entname;

    /**
     * 视频类型相关封面图
     */
    private String deourl;

    /**
     * 视频/音频时长
     */
    private String enttimes;

    /**
     * 资源创建时间
     */
    private Date crtdate;

    /**
     * 资源创建时间
     */
    private Date redate;

    /**
     * 获取资源ID
     *
     * @return entid - 资源ID
     */
    public String getEntid() {
        return entid;
    }

    /**
     * 设置资源ID
     *
     * @param entid 资源ID
     */
    public void setEntid(String entid) {
        this.entid = entid;
    }

    /**
     * 获取资源类型（图片/音频/视频）
     *
     * @return enttype - 资源类型（图片/音频/视频）
     */
    public String getEnttype() {
        return enttype;
    }

    /**
     * 设置资源类型（图片/音频/视频）
     *
     * @param enttype 资源类型（图片/音频/视频）
     */
    public void setEnttype(String enttype) {
        this.enttype = enttype;
    }

    /**
     * 获取实体类型（1、培训 2、活动 3、场馆）
     *
     * @return reftype - 实体类型（1、培训 2、活动 3、场馆）
     */
    public String getReftype() {
        return reftype;
    }

    /**
     * 设置实体类型（1、培训 2、活动 3、场馆）
     *
     * @param reftype 实体类型（1、培训 2、活动 3、场馆）
     */
    public void setReftype(String reftype) {
        this.reftype = reftype;
    }

    /**
     * 获取实体id(培训/活动的ID)
     *
     * @return refid - 实体id(培训/活动的ID)
     */
    public String getRefid() {
        return refid;
    }

    /**
     * 设置实体id(培训/活动的ID)
     *
     * @param refid 实体id(培训/活动的ID)
     */
    public void setRefid(String refid) {
        this.refid = refid;
    }

    /**
     * 获取资源的地址
     *
     * @return enturl - 资源的地址
     */
    public String getEnturl() {
        return enturl;
    }

    /**
     * 设置资源的地址
     *
     * @param enturl 资源的地址
     */
    public void setEnturl(String enturl) {
        this.enturl = enturl;
    }

    /**
     * 获取资源的名字
     *
     * @return entname - 资源的名字
     */
    public String getEntname() {
        return entname;
    }

    /**
     * 设置资源的名字
     *
     * @param entname 资源的名字
     */
    public void setEntname(String entname) {
        this.entname = entname;
    }

    /**
     * 获取视频类型相关封面图
     *
     * @return deourl - 视频类型相关封面图
     */
    public String getDeourl() {
        return deourl;
    }

    /**
     * 设置视频类型相关封面图
     *
     * @param deourl 视频类型相关封面图
     */
    public void setDeourl(String deourl) {
        this.deourl = deourl;
    }

    /**
     * 获取视频/音频时长
     *
     * @return enttimes - 视频/音频时长
     */
    public String getEnttimes() {
        return enttimes;
    }

    /**
     * 设置视频/音频时长
     *
     * @param enttimes 视频/音频时长
     */
    public void setEnttimes(String enttimes) {
        this.enttimes = enttimes;
    }

    /**
     * 获取资源创建时间
     *
     * @return crtdate - 资源创建时间
     */
    public Date getCrtdate() {
        return crtdate;
    }

    /**
     * 设置资源创建时间
     *
     * @param crtdate 资源创建时间
     */
    public void setCrtdate(Date crtdate) {
        this.crtdate = crtdate;
    }

    /**
     * 获取资源创建时间
     *
     * @return redate - 资源创建时间
     */
    public Date getRedate() {
        return redate;
    }

    /**
     * 设置资源创建时间
     *
     * @param redate 资源创建时间
     */
    public void setRedate(Date redate) {
        this.redate = redate;
    }
}