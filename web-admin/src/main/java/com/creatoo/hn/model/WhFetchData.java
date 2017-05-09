package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "wh_fetch_data")
public class WhFetchData {
    /**
     * 来源标识
     */
    @Id
    private String fdfromid;

    /**
     * 数据在第三方系统中的主键.
     */
    @Id
    private String fdfromdataid;

    /**
     * 采集时间.
     */
    private Date fdfetchtime;

    /**
     * 采集人
     */
    private String fdfetchuid;

    /**
     * 获取来源标识
     *
     * @return fdfromid - 来源标识
     */
    public String getFdfromid() {
        return fdfromid;
    }

    /**
     * 设置来源标识
     *
     * @param fdfromid 来源标识
     */
    public void setFdfromid(String fdfromid) {
        this.fdfromid = fdfromid;
    }

    /**
     * 获取数据在第三方系统中的主键.
     *
     * @return fdfromdataid - 数据在第三方系统中的主键.
     */
    public String getFdfromdataid() {
        return fdfromdataid;
    }

    /**
     * 设置数据在第三方系统中的主键.
     *
     * @param fdfromdataid 数据在第三方系统中的主键.
     */
    public void setFdfromdataid(String fdfromdataid) {
        this.fdfromdataid = fdfromdataid;
    }

    /**
     * 获取采集时间.
     *
     * @return fdfetchtime - 采集时间.
     */
    public Date getFdfetchtime() {
        return fdfetchtime;
    }

    /**
     * 设置采集时间.
     *
     * @param fdfetchtime 采集时间.
     */
    public void setFdfetchtime(Date fdfetchtime) {
        this.fdfetchtime = fdfetchtime;
    }

    /**
     * 获取采集人
     *
     * @return fdfetchuid - 采集人
     */
    public String getFdfetchuid() {
        return fdfetchuid;
    }

    /**
     * 设置采集人
     *
     * @param fdfetchuid 采集人
     */
    public void setFdfetchuid(String fdfetchuid) {
        this.fdfetchuid = fdfetchuid;
    }
}