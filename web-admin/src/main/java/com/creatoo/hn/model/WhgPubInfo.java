package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "whg_pub_info")
public class WhgPubInfo {
    /**
     * ID
     */
    @Id
    private String id;

    /**
     * 对应的实体ID
     */
    private String entityid;

    /**
     * 资讯或公告ID
     */
    private String clnfid;

    /**
     * 资讯公告类型（培训 活动 非遗）
     */
    private String clnftype;



    /**
     * 获取ID
     *
     * @return id - ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取对应的实体ID
     *
     * @return entityid - 对应的实体ID
     */
    public String getEntityid() {
        return entityid;
    }

    /**
     * 设置对应的实体ID
     *
     * @param entityid 对应的实体ID
     */
    public void setEntityid(String entityid) {
        this.entityid = entityid;
    }

    /**
     * 获取资讯或公告ID
     *
     * @return clnfid - 资讯或公告ID
     */
    public String getClnfid() {
        return clnfid;
    }

    /**
     * 设置资讯或公告ID
     *
     * @param clnfid 资讯或公告ID
     */
    public void setClnfid(String clnfid) {
        this.clnfid = clnfid;
    }

    /**
     * 获取资讯公告类型（培训 活动 非遗）
     *
     * @return clnftype - 资讯公告类型（培训 活动 非遗）
     */
    public String getClnftype() {
        return clnftype;
    }

    /**
     * 设置资讯公告类型（培训 活动 非遗）
     *
     * @param clnftype 资讯公告类型（培训 活动 非遗）
     */
    public void setClnftype(String clnftype) {
        this.clnftype = clnftype;
    }
}