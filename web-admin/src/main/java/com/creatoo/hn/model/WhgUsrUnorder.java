package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "whg_usr_unorder")
public class WhgUsrUnorder {
    @Id
    private String id;

    /**
     * 用户ID
     */
    private String userid;

    /**
     * 订单ID
     */
    private String orderid;

    /**
     * 订单类型，参照EnumOrderType申明
     */
    private Integer ordertype;

    /**
     * 取消时间
     */
    private Date untime;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取用户ID
     *
     * @return userid - 用户ID
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置用户ID
     *
     * @param userid 用户ID
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * 获取订单ID
     *
     * @return orderid - 订单ID
     */
    public String getOrderid() {
        return orderid;
    }

    /**
     * 设置订单ID
     *
     * @param orderid 订单ID
     */
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    /**
     * 获取订单类型，参照EnumOrderType申明
     *
     * @return ordertype - 订单类型，参照EnumOrderType申明
     */
    public Integer getOrdertype() {
        return ordertype;
    }

    /**
     * 设置订单类型，参照EnumOrderType申明
     *
     * @param ordertype 订单类型，参照EnumOrderType申明
     */
    public void setOrdertype(Integer ordertype) {
        this.ordertype = ordertype;
    }

    /**
     * 获取取消时间
     *
     * @return untime - 取消时间
     */
    public Date getUntime() {
        return untime;
    }

    /**
     * 设置取消时间
     *
     * @param untime 取消时间
     */
    public void setUntime(Date untime) {
        this.untime = untime;
    }
}