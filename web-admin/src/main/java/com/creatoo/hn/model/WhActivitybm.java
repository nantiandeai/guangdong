package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "wh_activitybm")
public class WhActivitybm {
    /**
     * 报名标识
     */
    @Id
    private String actbmid;

    /**
     * 用户标识
     */
    private String actbmuid;

    /**
     * 活动标识
     */
    private String actid;

    /**
     * 报名活动时间段id
     */
    private String actvitmid;

    /**
     * 个人团队标识：0个人，1团队
     */
    private Integer actbmtype;

    /**
     * 是否需要善资料:0-未完善资料；1-已完善资料；2-不需要
     */
    private String actbmisa;

    /**
     * 是否已上传附件:0-未上传附件；1-已上传附件；2-不需要
     */
    private String actbmisb;

    /**
     * 附件地址
     */
    private String actbmfilepath;

    /**
     * 报名/预票 数量
     */
    private Integer actbmcount;

    /**
     * 报名时间
     */
    private Date actbmtime;

    /**
     * 审核状态:0-未审核;1-已审核;2-不需要;3-审核不通过
     */
    private String actshstate;

    /**
     * 审核失败原因
     */
    private String actbmstatemsg;

    /**
     * 报名是否完成：0未完成，1已完成
     */
    private Integer actbmstate;

    /**
     * 获取报名标识
     *
     * @return actbmid - 报名标识
     */
    public String getActbmid() {
        return actbmid;
    }

    /**
     * 设置报名标识
     *
     * @param actbmid 报名标识
     */
    public void setActbmid(String actbmid) {
        this.actbmid = actbmid;
    }

    /**
     * 获取用户标识
     *
     * @return actbmuid - 用户标识
     */
    public String getActbmuid() {
        return actbmuid;
    }

    /**
     * 设置用户标识
     *
     * @param actbmuid 用户标识
     */
    public void setActbmuid(String actbmuid) {
        this.actbmuid = actbmuid;
    }

    /**
     * 获取活动标识
     *
     * @return actid - 活动标识
     */
    public String getActid() {
        return actid;
    }

    /**
     * 设置活动标识
     *
     * @param actid 活动标识
     */
    public void setActid(String actid) {
        this.actid = actid;
    }

    /**
     * 获取报名活动时间段id
     *
     * @return actvitmid - 报名活动时间段id
     */
    public String getActvitmid() {
        return actvitmid;
    }

    /**
     * 设置报名活动时间段id
     *
     * @param actvitmid 报名活动时间段id
     */
    public void setActvitmid(String actvitmid) {
        this.actvitmid = actvitmid;
    }

    /**
     * 获取个人团队标识：0个人，1团队
     *
     * @return actbmtype - 个人团队标识：0个人，1团队
     */
    public Integer getActbmtype() {
        return actbmtype;
    }

    /**
     * 设置个人团队标识：0个人，1团队
     *
     * @param actbmtype 个人团队标识：0个人，1团队
     */
    public void setActbmtype(Integer actbmtype) {
        this.actbmtype = actbmtype;
    }

    /**
     * 获取是否需要善资料:0-未完善资料；1-已完善资料；2-不需要
     *
     * @return actbmisa - 是否需要善资料:0-未完善资料；1-已完善资料；2-不需要
     */
    public String getActbmisa() {
        return actbmisa;
    }

    /**
     * 设置是否需要善资料:0-未完善资料；1-已完善资料；2-不需要
     *
     * @param actbmisa 是否需要善资料:0-未完善资料；1-已完善资料；2-不需要
     */
    public void setActbmisa(String actbmisa) {
        this.actbmisa = actbmisa;
    }

    /**
     * 获取是否已上传附件:0-未上传附件；1-已上传附件；2-不需要
     *
     * @return actbmisb - 是否已上传附件:0-未上传附件；1-已上传附件；2-不需要
     */
    public String getActbmisb() {
        return actbmisb;
    }

    /**
     * 设置是否已上传附件:0-未上传附件；1-已上传附件；2-不需要
     *
     * @param actbmisb 是否已上传附件:0-未上传附件；1-已上传附件；2-不需要
     */
    public void setActbmisb(String actbmisb) {
        this.actbmisb = actbmisb;
    }

    /**
     * 获取附件地址
     *
     * @return actbmfilepath - 附件地址
     */
    public String getActbmfilepath() {
        return actbmfilepath;
    }

    /**
     * 设置附件地址
     *
     * @param actbmfilepath 附件地址
     */
    public void setActbmfilepath(String actbmfilepath) {
        this.actbmfilepath = actbmfilepath;
    }

    /**
     * 获取报名/预票 数量
     *
     * @return actbmcount - 报名/预票 数量
     */
    public Integer getActbmcount() {
        return actbmcount;
    }

    /**
     * 设置报名/预票 数量
     *
     * @param actbmcount 报名/预票 数量
     */
    public void setActbmcount(Integer actbmcount) {
        this.actbmcount = actbmcount;
    }

    /**
     * 获取报名时间
     *
     * @return actbmtime - 报名时间
     */
    public Date getActbmtime() {
        return actbmtime;
    }

    /**
     * 设置报名时间
     *
     * @param actbmtime 报名时间
     */
    public void setActbmtime(Date actbmtime) {
        this.actbmtime = actbmtime;
    }

    /**
     * 获取审核状态:0-未审核;1-已审核;2-不需要;3-审核不通过
     *
     * @return actshstate - 审核状态:0-未审核;1-已审核;2-不需要;3-审核不通过
     */
    public String getActshstate() {
        return actshstate;
    }

    /**
     * 设置审核状态:0-未审核;1-已审核;2-不需要;3-审核不通过
     *
     * @param actshstate 审核状态:0-未审核;1-已审核;2-不需要;3-审核不通过
     */
    public void setActshstate(String actshstate) {
        this.actshstate = actshstate;
    }

    /**
     * 获取审核失败原因
     *
     * @return actbmstatemsg - 审核失败原因
     */
    public String getActbmstatemsg() {
        return actbmstatemsg;
    }

    /**
     * 设置审核失败原因
     *
     * @param actbmstatemsg 审核失败原因
     */
    public void setActbmstatemsg(String actbmstatemsg) {
        this.actbmstatemsg = actbmstatemsg;
    }

    /**
     * 获取报名是否完成：0未完成，1已完成
     *
     * @return actbmstate - 报名是否完成：0未完成，1已完成
     */
    public Integer getActbmstate() {
        return actbmstate;
    }

    /**
     * 设置报名是否完成：0未完成，1已完成
     *
     * @param actbmstate 报名是否完成：0未完成，1已完成
     */
    public void setActbmstate(Integer actbmstate) {
        this.actbmstate = actbmstate;
    }
}