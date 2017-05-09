package com.creatoo.hn.model;

import javax.persistence.*;

@Table(name = "wh_suorpro")
public class WhSuorpro {
    /**
     * 名录 传承人 关联id
     */
    @Id
    private String spid;

    /**
     * 传承人关联id
     */
    private String spsuorid;

    /**
     * 名录项目关联id
     */
    private String spmlproid;

    /**
     * 获取名录 传承人 关联id
     *
     * @return spid - 名录 传承人 关联id
     */
    public String getSpid() {
        return spid;
    }

    /**
     * 设置名录 传承人 关联id
     *
     * @param spid 名录 传承人 关联id
     */
    public void setSpid(String spid) {
        this.spid = spid;
    }

    /**
     * 获取传承人关联id
     *
     * @return spsuorid - 传承人关联id
     */
    public String getSpsuorid() {
        return spsuorid;
    }

    /**
     * 设置传承人关联id
     *
     * @param spsuorid 传承人关联id
     */
    public void setSpsuorid(String spsuorid) {
        this.spsuorid = spsuorid;
    }

    /**
     * 获取名录项目关联id
     *
     * @return spmlproid - 名录项目关联id
     */
    public String getSpmlproid() {
        return spmlproid;
    }

    /**
     * 设置名录项目关联id
     *
     * @param spmlproid 名录项目关联id
     */
    public void setSpmlproid(String spmlproid) {
        this.spmlproid = spmlproid;
    }
}