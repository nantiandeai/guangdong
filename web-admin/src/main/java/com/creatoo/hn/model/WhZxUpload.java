package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "wh_zx_upload")
public class WhZxUpload {
    /**
     * id
     */
    @Id
    private String upid;

    /**
     * 资源名称
     */
    private String upname;

    /**
     * 资源url
     */
    private String uplink;

    /**
     * 上传时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date uptime;

    /**
     * 状态 0停用1启用
     */
    private Integer upstate;

    /**
     * 关联栏目内容id
     */
    private String uptype;

    /**
     * 获取id
     *
     * @return upid - id
     */
    public String getUpid() {
        return upid;
    }

    /**
     * 设置id
     *
     * @param upid id
     */
    public void setUpid(String upid) {
        this.upid = upid;
    }

    /**
     * 获取资源名称
     *
     * @return upname - 资源名称
     */
    public String getUpname() {
        return upname;
    }

    /**
     * 设置资源名称
     *
     * @param upname 资源名称
     */
    public void setUpname(String upname) {
        this.upname = upname;
    }

    /**
     * 获取资源url
     *
     * @return uplink - 资源url
     */
    public String getUplink() {
        return uplink;
    }

    /**
     * 设置资源url
     *
     * @param uplink 资源url
     */
    public void setUplink(String uplink) {
        this.uplink = uplink;
    }

    /**
     * 获取上传时间
     *
     * @return uptime - 上传时间
     */
    public Date getUptime() {
        return uptime;
    }

    /**
     * 设置上传时间
     *
     * @param uptime 上传时间
     */
    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }

    /**
     * 获取状态 0停用1启用
     *
     * @return upstate - 状态 0停用1启用
     */
    public Integer getUpstate() {
        return upstate;
    }

    /**
     * 设置状态 0停用1启用
     *
     * @param upstate 状态 0停用1启用
     */
    public void setUpstate(Integer upstate) {
        this.upstate = upstate;
    }

    /**
     * 获取关联栏目内容id
     *
     * @return uptype - 关联栏目内容id
     */
    public String getUptype() {
        return uptype;
    }

    /**
     * 设置关联栏目内容id
     *
     * @param uptype 关联栏目内容id
     */
    public void setUptype(String uptype) {
        this.uptype = uptype;
    }
}