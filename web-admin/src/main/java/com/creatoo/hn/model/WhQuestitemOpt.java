package com.creatoo.hn.model;

import javax.persistence.*;

@Table(name = "wh_questitem_opt")
public class WhQuestitemOpt {
    /**
     * 选项标识
     */
    @Id
    private String qoptid;

    /**
     * 题目标识
     */
    private String qoptitmid;

    /**
     * 选项KEY
     */
    private String qoptkey;

    /**
     * 选项值
     */
    private String qoptval;

    /**
     * 获取选项标识
     *
     * @return qoptid - 选项标识
     */
    public String getQoptid() {
        return qoptid;
    }

    /**
     * 设置选项标识
     *
     * @param qoptid 选项标识
     */
    public void setQoptid(String qoptid) {
        this.qoptid = qoptid;
    }

    /**
     * 获取题目标识
     *
     * @return qoptitmid - 题目标识
     */
    public String getQoptitmid() {
        return qoptitmid;
    }

    /**
     * 设置题目标识
     *
     * @param qoptitmid 题目标识
     */
    public void setQoptitmid(String qoptitmid) {
        this.qoptitmid = qoptitmid;
    }

    /**
     * 获取选项KEY
     *
     * @return qoptkey - 选项KEY
     */
    public String getQoptkey() {
        return qoptkey;
    }

    /**
     * 设置选项KEY
     *
     * @param qoptkey 选项KEY
     */
    public void setQoptkey(String qoptkey) {
        this.qoptkey = qoptkey;
    }

    /**
     * 获取选项值
     *
     * @return qoptval - 选项值
     */
    public String getQoptval() {
        return qoptval;
    }

    /**
     * 设置选项值
     *
     * @param qoptval 选项值
     */
    public void setQoptval(String qoptval) {
        this.qoptval = qoptval;
    }
}