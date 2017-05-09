package com.creatoo.hn.model;

import javax.persistence.*;

@Table(name = "wh_questitem")
public class WhQuestitem {
    /**
     * 题目标识
     */
    @Id
    private String qitmid;

    /**
     * 问卷调查标识
     */
    private String qitmqid;

    /**
     * 题目类型0-单选;1-多选;2-问答;
     */
    private Integer qitmtype;

    /**
     * 题目标题
     */
    private String qitmtitle;

    /**
     * 题目副标题
     */
    private String qitmsubtitle;

    /**
     * 题目答案
     */
    private String qitmresult;

    /**
     * 获取题目标识
     *
     * @return qitmid - 题目标识
     */
    public String getQitmid() {
        return qitmid;
    }

    /**
     * 设置题目标识
     *
     * @param qitmid 题目标识
     */
    public void setQitmid(String qitmid) {
        this.qitmid = qitmid;
    }

    /**
     * 获取问卷调查标识
     *
     * @return qitmqid - 问卷调查标识
     */
    public String getQitmqid() {
        return qitmqid;
    }

    /**
     * 设置问卷调查标识
     *
     * @param qitmqid 问卷调查标识
     */
    public void setQitmqid(String qitmqid) {
        this.qitmqid = qitmqid;
    }

    /**
     * 获取题目类型0-单选;1-多选;2-问答;
     *
     * @return qitmtype - 题目类型0-单选;1-多选;2-问答;
     */
    public Integer getQitmtype() {
        return qitmtype;
    }

    /**
     * 设置题目类型0-单选;1-多选;2-问答;
     *
     * @param qitmtype 题目类型0-单选;1-多选;2-问答;
     */
    public void setQitmtype(Integer qitmtype) {
        this.qitmtype = qitmtype;
    }

    /**
     * 获取题目标题
     *
     * @return qitmtitle - 题目标题
     */
    public String getQitmtitle() {
        return qitmtitle;
    }

    /**
     * 设置题目标题
     *
     * @param qitmtitle 题目标题
     */
    public void setQitmtitle(String qitmtitle) {
        this.qitmtitle = qitmtitle;
    }

    /**
     * 获取题目副标题
     *
     * @return qitmsubtitle - 题目副标题
     */
    public String getQitmsubtitle() {
        return qitmsubtitle;
    }

    /**
     * 设置题目副标题
     *
     * @param qitmsubtitle 题目副标题
     */
    public void setQitmsubtitle(String qitmsubtitle) {
        this.qitmsubtitle = qitmsubtitle;
    }

    /**
     * 获取题目答案
     *
     * @return qitmresult - 题目答案
     */
    public String getQitmresult() {
        return qitmresult;
    }

    /**
     * 设置题目答案
     *
     * @param qitmresult 题目答案
     */
    public void setQitmresult(String qitmresult) {
        this.qitmresult = qitmresult;
    }
}