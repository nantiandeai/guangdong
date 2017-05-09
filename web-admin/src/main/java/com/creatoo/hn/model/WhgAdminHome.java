package com.creatoo.hn.model;

/**
 * Created by Administrator on 2017/3/24.
 */
public class WhgAdminHome {

    /**
     * 会员总数（非数据库字段）
     */
    private String memberTotal;
    /**
     * 培训总数（非数据库字段）
     */
    private String traTotal;
    /**
     * 活动总数（非数据库字段）
     */
    private String actTotal;
    /**
     * 场馆总数（非数据库字段）
     */
    private String venTotal;
    /**
     * 后台首页柱形图公用字段（非数据库字段）
     */
    private String name;
    /**
     * 后台首页柱形图公用字段（非数据库字段）
     */
    private String cnt;


    public String getMemberTotal() {
        return memberTotal;
    }

    public void setMemberTotal(String memberTotal) {
        this.memberTotal = memberTotal;
    }

    public String getTraTotal() {
        return traTotal;
    }

    public void setTraTotal(String traTotal) {
        this.traTotal = traTotal;
    }

    public String getActTotal() {
        return actTotal;
    }

    public void setActTotal(String actTotal) {
        this.actTotal = actTotal;
    }

    public String getVenTotal() {
        return venTotal;
    }

    public void setVenTotal(String venTotal) {
        this.venTotal = venTotal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }
}
