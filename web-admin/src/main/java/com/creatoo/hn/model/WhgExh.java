package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "whg_exh")
public class WhgExh {
    /**
     * 主键
     */
    @Id
    private String exhid;

    /**
     * 标题
     */
    private String exhtitle;

    /**
     * 艺术分类
     */
    private String exharttyp;

    /**
     * 文化展类型
     */
    private String exhtype;

    /**
     * 封面图片
     */
    private String exhpic;

    /**
     * 简介
     */
    private String exhdesc;

    /**
     * 创建时间
     */
    private Date exhcrttime;

    /**
     * 开始时间
     */
    private Date exhstime;

    /**
     * 结束时间
     */
    private Date exhetime;

    /**
     * 地址
     */
    private String exhaddress;

    /**
     * 推荐标识, 1-推荐,0-不推荐
     */
    private Integer exhghp;

    /**
     * 推荐排序
     */
    private Integer exhidx;

    /**
     * 状态,1-启用, 0-停用
     */
    private Integer exhstate;

    /**
     * 改变审核状态的操作时间
     */
    private Date exhopttime;

    /**
     * 所属文化馆标识
     */
    private String exhcultid;

    /**
     * 所属部门标识
     */
    private String exhdeptid;

    /**
     * 主办
     */
    private String organizer;
    /**
     * 获取主键
     *
     * @return exhid - 主键
     */
    public String getExhid() {
        return exhid;
    }

    /**
     * 设置主键
     *
     * @param exhid 主键
     */
    public void setExhid(String exhid) {
        this.exhid = exhid;
    }

    /**
     * 获取标题
     *
     * @return exhtitle - 标题
     */
    public String getExhtitle() {
        return exhtitle;
    }

    /**
     * 设置标题
     *
     * @param exhtitle 标题
     */
    public void setExhtitle(String exhtitle) {
        this.exhtitle = exhtitle;
    }

    /**
     * 获取艺术分类
     *
     * @return exharttyp - 艺术分类
     */
    public String getExharttyp() {
        return exharttyp;
    }

    /**
     * 设置艺术分类
     *
     * @param exharttyp 艺术分类
     */
    public void setExharttyp(String exharttyp) {
        this.exharttyp = exharttyp;
    }

    /**
     * 获取文化展类型
     *
     * @return exhtype - 文化展类型
     */
    public String getExhtype() {
        return exhtype;
    }

    /**
     * 设置文化展类型
     *
     * @param exhtype 文化展类型
     */
    public void setExhtype(String exhtype) {
        this.exhtype = exhtype;
    }

    /**
     * 获取封面图片
     *
     * @return exhpic - 封面图片
     */
    public String getExhpic() {
        return exhpic;
    }

    /**
     * 设置封面图片
     *
     * @param exhpic 封面图片
     */
    public void setExhpic(String exhpic) {
        this.exhpic = exhpic;
    }

    /**
     * 获取简介
     *
     * @return exhdesc - 简介
     */
    public String getExhdesc() {
        return exhdesc;
    }

    /**
     * 设置简介
     *
     * @param exhdesc 简介
     */
    public void setExhdesc(String exhdesc) {
        this.exhdesc = exhdesc;
    }

    /**
     * 获取创建时间
     *
     * @return exhcrttime - 创建时间
     */
    public Date getExhcrttime() {
        return exhcrttime;
    }

    /**
     * 设置创建时间
     *
     * @param exhcrttime 创建时间
     */
    public void setExhcrttime(Date exhcrttime) {
        this.exhcrttime = exhcrttime;
    }

    /**
     * 获取开始时间
     *
     * @return exhstime - 开始时间
     */
    public Date getExhstime() {
        return exhstime;
    }

    /**
     * 设置开始时间
     *
     * @param exhstime 开始时间
     */
    public void setExhstime(Date exhstime) {
        this.exhstime = exhstime;
    }

    /**
     * 获取结束时间
     *
     * @return exhetime - 结束时间
     */
    public Date getExhetime() {
        return exhetime;
    }

    /**
     * 设置结束时间
     *
     * @param exhetime 结束时间
     */
    public void setExhetime(Date exhetime) {
        this.exhetime = exhetime;
    }

    /**
     * 获取地址
     *
     * @return exhaddress - 地址
     */
    public String getExhaddress() {
        return exhaddress;
    }

    /**
     * 设置地址
     *
     * @param exhaddress 地址
     */
    public void setExhaddress(String exhaddress) {
        this.exhaddress = exhaddress;
    }

    /**
     * 获取推荐标识, 1-推荐,0-不推荐
     *
     * @return exhghp - 推荐标识, 1-推荐,0-不推荐
     */
    public Integer getExhghp() {
        return exhghp;
    }

    /**
     * 设置推荐标识, 1-推荐,0-不推荐
     *
     * @param exhghp 推荐标识, 1-推荐,0-不推荐
     */
    public void setExhghp(Integer exhghp) {
        this.exhghp = exhghp;
    }

    /**
     * 获取推荐排序
     *
     * @return exhidx - 推荐排序
     */
    public Integer getExhidx() {
        return exhidx;
    }

    /**
     * 设置推荐排序
     *
     * @param exhidx 推荐排序
     */
    public void setExhidx(Integer exhidx) {
        this.exhidx = exhidx;
    }

    /**
     * 获取状态,1-启用, 0-停用
     *
     * @return exhstate - 状态,1-启用, 0-停用
     */
    public Integer getExhstate() {
        return exhstate;
    }

    /**
     * 设置状态,1-启用, 0-停用
     *
     * @param exhstate 状态,1-启用, 0-停用
     */
    public void setExhstate(Integer exhstate) {
        this.exhstate = exhstate;
    }

    /**
     * 获取改变审核状态的操作时间
     *
     * @return exhopttime - 改变审核状态的操作时间
     */
    public Date getExhopttime() {
        return exhopttime;
    }

    /**
     * 设置改变审核状态的操作时间
     *
     * @param exhopttime 改变审核状态的操作时间
     */
    public void setExhopttime(Date exhopttime) {
        this.exhopttime = exhopttime;
    }

    /**
     * 获取所属文化馆标识
     *
     * @return exhcultid - 所属文化馆标识
     */
    public String getExhcultid() {
        return exhcultid;
    }

    /**
     * 设置所属文化馆标识
     *
     * @param exhcultid 所属文化馆标识
     */
    public void setExhcultid(String exhcultid) {
        this.exhcultid = exhcultid;
    }

    /**
     * 获取所属部门标识
     *
     * @return exhdeptid - 所属部门标识
     */
    public String getExhdeptid() {
        return exhdeptid;
    }

    /**
     * 设置所属部门标识
     *
     * @param exhdeptid 所属部门标识
     */
    public void setExhdeptid(String exhdeptid) {
        this.exhdeptid = exhdeptid;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }
}