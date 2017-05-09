package com.creatoo.hn.model;

import javax.persistence.*;

@Table(name = "wh_user_troupeuser")
public class WhUserTroupeuser {
    /**
     * 团队成员ID
     */
    @Id
    private String tuid;

    /**
     * 团员名字
     */
    private String tuname;

    /**
     * 团员照片
     */
    private String tupic;

    /**
     * 艺术团ID
     */
    private String tutroupeid;

    /**
     * 关联用户ID
     */
    private String tuuid;

    /**
     * 状态(0：有效  1：无效)
     */
    private Integer tustate;

    /**
     * 团员介绍
     */
    private String tudesc;

    /**
     * 获取团队成员ID
     *
     * @return tuid - 团队成员ID
     */
    public String getTuid() {
        return tuid;
    }

    /**
     * 设置团队成员ID
     *
     * @param tuid 团队成员ID
     */
    public void setTuid(String tuid) {
        this.tuid = tuid;
    }

    /**
     * 获取团员名字
     *
     * @return tuname - 团员名字
     */
    public String getTuname() {
        return tuname;
    }

    /**
     * 设置团员名字
     *
     * @param tuname 团员名字
     */
    public void setTuname(String tuname) {
        this.tuname = tuname;
    }

    /**
     * 获取团员照片
     *
     * @return tupic - 团员照片
     */
    public String getTupic() {
        return tupic;
    }

    /**
     * 设置团员照片
     *
     * @param tupic 团员照片
     */
    public void setTupic(String tupic) {
        this.tupic = tupic;
    }

    /**
     * 获取艺术团ID
     *
     * @return tutroupeid - 艺术团ID
     */
    public String getTutroupeid() {
        return tutroupeid;
    }

    /**
     * 设置艺术团ID
     *
     * @param tutroupeid 艺术团ID
     */
    public void setTutroupeid(String tutroupeid) {
        this.tutroupeid = tutroupeid;
    }

    /**
     * 获取关联用户ID
     *
     * @return tuuid - 关联用户ID
     */
    public String getTuuid() {
        return tuuid;
    }

    /**
     * 设置关联用户ID
     *
     * @param tuuid 关联用户ID
     */
    public void setTuuid(String tuuid) {
        this.tuuid = tuuid;
    }

    /**
     * 获取状态(0：有效  1：无效)
     *
     * @return tustate - 状态(0：有效  1：无效)
     */
    public Integer getTustate() {
        return tustate;
    }

    /**
     * 设置状态(0：有效  1：无效)
     *
     * @param tustate 状态(0：有效  1：无效)
     */
    public void setTustate(Integer tustate) {
        this.tustate = tustate;
    }

    /**
     * 获取团员介绍
     *
     * @return tudesc - 团员介绍
     */
    public String getTudesc() {
        return tudesc;
    }

    /**
     * 设置团员介绍
     *
     * @param tudesc 团员介绍
     */
    public void setTudesc(String tudesc) {
        this.tudesc = tudesc;
    }
}