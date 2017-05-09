package com.creatoo.hn.model;

import javax.persistence.*;

@Table(name = "wh_user_alerts")
public class WhUserAlerts {
    /**
     * PK
     */
    @Id
    private String aleid;

    /**
     * 关联用户ID
     */
    private String refuid;

    /**
     * 关联类型：1活动、2课程、3场馆、4收藏、5点评、6安全设置
     */
    private String reftype;
    
    /**
     * 分类汇总
     */
    private String refcount;
    
    /**
     * 获取PK
     *
     * @return aleid - PK
     */
    public String getAleid() {
        return aleid;
    }

    /**
     * 设置PK
     *
     * @param aleid PK
     */
    public void setAleid(String aleid) {
        this.aleid = aleid;
    }
    
    /**
     * 获取汇总
     *
     * @return refcount - 汇总
     */
    public String getRefcount() {
        return refcount;
    }

    /**
     * 设置汇总
     *
     * @param refcount 汇总
     */
    public void setRefcount(String refcount) {
        this.refcount = refcount;
    }

    
    /**
     * 获取关联用户ID
     *
     * @return refuid - 关联用户ID
     */
    public String getRefuid() {
        return refuid;
    }

    /**
     * 设置关联用户ID
     *
     * @param refuid 关联用户ID
     */
    public void setRefuid(String refuid) {
        this.refuid = refuid;
    }

    /**
     * 获取关联类型：1活动、2课程、3场馆、4收藏、5点评、6安全设置
     *
     * @return reftype - 关联类型：1活动、2课程、3场馆、4收藏、5点评、6安全设置
     */
    public String getReftype() {
        return reftype;
    }

    /**
     * 设置关联类型：1活动、2课程、3场馆、4收藏、5点评、6安全设置
     *
     * @param reftype 关联类型：1活动、2课程、3场馆、4收藏、5点评、6安全设置
     */
    public void setReftype(String reftype) {
        this.reftype = reftype;
    }
}