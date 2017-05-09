package com.creatoo.hn.model;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "whg_act_ticket")
public class WhgActTicket {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 订单ID
     */
    private String orderid;

    /**
     * 出票时间
     */
    private Date printtime;

    /**
     * 验票状态。1：未取票，2：已取票，3：已取消
     */
    private Integer ticketstatus;

    /**
     * 座位ID
     */
    private String seatid;

    /**
     * 对应票务信息(如果是自由入座，显示票编号；如果是在线选择，显示座位编号)
     */
    private String seatcode;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
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
     * 获取出票时间
     *
     * @return printtime - 出票时间
     */
    public Date getPrinttime() {
        return printtime;
    }

    /**
     * 设置出票时间
     *
     * @param printtime 出票时间
     */
    public void setPrinttime(Date printtime) {
        this.printtime = printtime;
    }

    /**
     * 获取验票状态。1：未取票，2：已取票，3：已取消
     *
     * @return ticketstatus - 验票状态。1：未取票，2：已取票，3：已取消
     */
    public Integer getTicketstatus() {
        return ticketstatus;
    }

    /**
     * 设置验票状态。1：未取票，2：已取票，3：已取消
     *
     * @param ticketstatus 验票状态。1：未取票，2：已取票，3：已取消
     */
    public void setTicketstatus(Integer ticketstatus) {
        this.ticketstatus = ticketstatus;
    }

    /**
     * 获取座位ID
     *
     * @return seatid - 座位ID
     */
    public String getSeatid() {
        return seatid;
    }

    /**
     * 设置座位ID
     *
     * @param seatid 座位ID
     */
    public void setSeatid(String seatid) {
        this.seatid = seatid;
    }

    /**
     * 获取对应票务信息(如果是自由入座，显示票编号；如果是在线选择，显示座位编号)
     *
     * @return seatcode - 对应票务信息(如果是自由入座，显示票编号；如果是在线选择，显示座位编号)
     */
    public String getSeatcode() {
        return seatcode;
    }

    /**
     * 设置对应票务信息(如果是自由入座，显示票编号；如果是在线选择，显示座位编号)
     *
     * @param seatcode 对应票务信息(如果是自由入座，显示票编号；如果是在线选择，显示座位编号)
     */
    public void setSeatcode(String seatcode) {
        this.seatcode = seatcode;
    }
}