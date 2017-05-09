package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "wh_quest")
public class WhQuest {
    /**
     * 问卷调查标识
     */
    private String queid;

    /**
     * 问卷调查标题
     */
    private String quetitle;

    /**
     * 问卷调查副标题
     */
    private String quesubtitle;

    /**
     * 开始时间
     */
    private Date questime;

    /**
     * 结束时间
     */
    private Date queetime;

    /**
     * 状态0-编辑;1-送审;2-审核;3-发布;
     */
    private Integer questate;

    /**
     * 修改状态的操作时间
     */
    private Date queopttime;

    /**
     * 获取问卷调查标识
     *
     * @return queid - 问卷调查标识
     */
    public String getQueid() {
        return queid;
    }

    /**
     * 设置问卷调查标识
     *
     * @param queid 问卷调查标识
     */
    public void setQueid(String queid) {
        this.queid = queid;
    }

    /**
     * 获取问卷调查标题
     *
     * @return quetitle - 问卷调查标题
     */
    public String getQuetitle() {
        return quetitle;
    }

    /**
     * 设置问卷调查标题
     *
     * @param quetitle 问卷调查标题
     */
    public void setQuetitle(String quetitle) {
        this.quetitle = quetitle;
    }

    /**
     * 获取问卷调查副标题
     *
     * @return quesubtitle - 问卷调查副标题
     */
    public String getQuesubtitle() {
        return quesubtitle;
    }

    /**
     * 设置问卷调查副标题
     *
     * @param quesubtitle 问卷调查副标题
     */
    public void setQuesubtitle(String quesubtitle) {
        this.quesubtitle = quesubtitle;
    }

    /**
     * 获取开始时间
     *
     * @return questime - 开始时间
     */
    public Date getQuestime() {
        return questime;
    }

    /**
     * 设置开始时间
     *
     * @param questime 开始时间
     */
    public void setQuestime(Date questime) {
        this.questime = questime;
    }

    /**
     * 获取结束时间
     *
     * @return queetime - 结束时间
     */
    public Date getQueetime() {
        return queetime;
    }

    /**
     * 设置结束时间
     *
     * @param queetime 结束时间
     */
    public void setQueetime(Date queetime) {
        this.queetime = queetime;
    }

    /**
     * 获取状态0-编辑;1-送审;2-审核;3-发布;
     *
     * @return questate - 状态0-编辑;1-送审;2-审核;3-发布;
     */
    public Integer getQuestate() {
        return questate;
    }

    /**
     * 设置状态0-编辑;1-送审;2-审核;3-发布;
     *
     * @param questate 状态0-编辑;1-送审;2-审核;3-发布;
     */
    public void setQuestate(Integer questate) {
        this.questate = questate;
    }

    /**
     * 获取修改状态的操作时间
     *
     * @return queopttime - 修改状态的操作时间
     */
    public Date getQueopttime() {
        return queopttime;
    }

    /**
     * 设置修改状态的操作时间
     *
     * @param queopttime 修改状态的操作时间
     */
    public void setQueopttime(Date queopttime) {
        this.queopttime = queopttime;
    }
}