package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "wh_traenr")
public class WhTraenr {
    /**
     * 报名标识
     */
    @Id
    private String enrid;

    /**
     * 用户标识
     */
    private String enruid;

    /**
     * 培训标识
     */
    private String enrtraid;

    /**
     * 个人团队标识：0个人，1团队
     */
    private Integer enrtype;

    /**
     * 是否需要善资料:0-未完善资料；1-已完善资料；2-不需要
     */
    private String enrisa;

    /**
     * 是否已上传附件:0-未上传附件；1-已上传附件；2-不需要
     */
    private String enrisb;

    /**
     * 附件地址
     */
    private String enrfilepath;

    /**
     * 报名时间
     */
    private Date enrtime;

    /**
     * 审核状态:0-未审核;1-已审核;2-不需要;3-审核不通过
     */
    private String enrstate;

    /**
     * 审核失败原因
     */
    private String enrstatemsg;

    /**
     * 报名是否完成：0未完成，1已完成
     */
    private Integer enrstepstate;

    /**
     * 获取报名标识
     *
     * @return enrid - 报名标识
     */
    public String getEnrid() {
        return enrid;
    }

    /**
     * 设置报名标识
     *
     * @param enrid 报名标识
     */
    public void setEnrid(String enrid) {
        this.enrid = enrid;
    }

    /**
     * 获取用户标识
     *
     * @return enruid - 用户标识
     */
    public String getEnruid() {
        return enruid;
    }

    /**
     * 设置用户标识
     *
     * @param enruid 用户标识
     */
    public void setEnruid(String enruid) {
        this.enruid = enruid;
    }

    /**
     * 获取培训标识
     *
     * @return enrtraid - 培训标识
     */
    public String getEnrtraid() {
        return enrtraid;
    }

    /**
     * 设置培训标识
     *
     * @param enrtraid 培训标识
     */
    public void setEnrtraid(String enrtraid) {
        this.enrtraid = enrtraid;
    }

    /**
     * 获取个人团队标识：0个人，1团队
     *
     * @return enrtype - 个人团队标识：0个人，1团队
     */
    public Integer getEnrtype() {
        return enrtype;
    }

    /**
     * 设置个人团队标识：0个人，1团队
     *
     * @param enrtype 个人团队标识：0个人，1团队
     */
    public void setEnrtype(Integer enrtype) {
        this.enrtype = enrtype;
    }

    /**
     * 获取是否需要善资料:0-未完善资料；1-已完善资料；2-不需要
     *
     * @return enrisa - 是否需要善资料:0-未完善资料；1-已完善资料；2-不需要
     */
    public String getEnrisa() {
        return enrisa;
    }

    /**
     * 设置是否需要善资料:0-未完善资料；1-已完善资料；2-不需要
     *
     * @param enrisa 是否需要善资料:0-未完善资料；1-已完善资料；2-不需要
     */
    public void setEnrisa(String enrisa) {
        this.enrisa = enrisa;
    }

    /**
     * 获取是否已上传附件:0-未上传附件；1-已上传附件；2-不需要
     *
     * @return enrisb - 是否已上传附件:0-未上传附件；1-已上传附件；2-不需要
     */
    public String getEnrisb() {
        return enrisb;
    }

    /**
     * 设置是否已上传附件:0-未上传附件；1-已上传附件；2-不需要
     *
     * @param enrisb 是否已上传附件:0-未上传附件；1-已上传附件；2-不需要
     */
    public void setEnrisb(String enrisb) {
        this.enrisb = enrisb;
    }

    /**
     * 获取附件地址
     *
     * @return enrfilepath - 附件地址
     */
    public String getEnrfilepath() {
        return enrfilepath;
    }

    /**
     * 设置附件地址
     *
     * @param enrfilepath 附件地址
     */
    public void setEnrfilepath(String enrfilepath) {
        this.enrfilepath = enrfilepath;
    }

    /**
     * 获取报名时间
     *
     * @return enrtime - 报名时间
     */
    public Date getEnrtime() {
        return enrtime;
    }

    /**
     * 设置报名时间
     *
     * @param enrtime 报名时间
     */
    public void setEnrtime(Date enrtime) {
        this.enrtime = enrtime;
    }

    /**
     * 获取审核状态:0-未审核;1-已审核;2-不需要;3-审核不通过
     *
     * @return enrstate - 审核状态:0-未审核;1-已审核;2-不需要;3-审核不通过
     */
    public String getEnrstate() {
        return enrstate;
    }

    /**
     * 设置审核状态:0-未审核;1-已审核;2-不需要;3-审核不通过
     *
     * @param enrstate 审核状态:0-未审核;1-已审核;2-不需要;3-审核不通过
     */
    public void setEnrstate(String enrstate) {
        this.enrstate = enrstate;
    }

    /**
     * 获取审核失败原因
     *
     * @return enrstatemsg - 审核失败原因
     */
    public String getEnrstatemsg() {
        return enrstatemsg;
    }

    /**
     * 设置审核失败原因
     *
     * @param enrstatemsg 审核失败原因
     */
    public void setEnrstatemsg(String enrstatemsg) {
        this.enrstatemsg = enrstatemsg;
    }

    /**
     * 获取报名是否完成：0未完成，1已完成
     *
     * @return enrstepstate - 报名是否完成：0未完成，1已完成
     */
    public Integer getEnrstepstate() {
        return enrstepstate;
    }

    /**
     * 设置报名是否完成：0未完成，1已完成
     *
     * @param enrstepstate 报名是否完成：0未完成，1已完成
     */
    public void setEnrstepstate(Integer enrstepstate) {
        this.enrstepstate = enrstepstate;
    }
}