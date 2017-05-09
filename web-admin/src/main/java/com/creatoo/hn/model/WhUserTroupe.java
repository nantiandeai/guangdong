package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "wh_user_troupe")
public class WhUserTroupe {
    /**
     * 艺术团标识
     */
    @Id
    private String troupeid;

    /**
     * 艺术团所属注册用户标识
     */
    private String troupeuid;

    /**
     * 艺术团图片,列表图片
     */
    private String troupepic;

    /**
     * 艺术团名称
     */
    private String troupename;

    /**
     * 成员人数
     */
    private Integer troupernum;

    /**
     * 艺术类型:音乐书法等
     */
    private String troupearttyp;

    /**
     * 艺术团类型
     */
    private String troupetype;

    /**
     * 代表作品说明
     */
    private String troupemain;

    /**
     * 艺术团简介
     */
    private String troupedesc;

    /**
     * 成立时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date trouperegtime;

    /**
     * 上首页标识
     */
    private Integer troupeghp;

    /**
     * 上首页排序
     */
    private Integer troupeidx;

    /**
     * 状态:0-初始,1-送审,2-已审,3-已发岸上
     */
    private Integer troupestate;

    /**
     * 详情页图片
     */
    private String troupebigpic;

    /**
     * 关键字
     */
    private String troupekey;

    /**
     * 标签
     */
    private String troupetag;

    /**
     * 修改状态的操作时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date troupeopttime;

    /**
     * 团队地址
     */
    private String troupeaddr;

    /**
     * 团队联系人
     */
    private String troupecontact;

    /**
     * 详细介绍
     */
    private String troupecontent;
    /**
     * 创建时间
     */
    private Date crtdate;
    /**
     * 获取艺术团标识
     *
     * @return troupeid - 艺术团标识
     */
    public String getTroupeid() {
        return troupeid;
    }

    /**
     * 设置艺术团标识
     *
     * @param troupeid 艺术团标识
     */
    public void setTroupeid(String troupeid) {
        this.troupeid = troupeid;
    }

    /**
     * 获取艺术团所属注册用户标识
     *
     * @return troupeuid - 艺术团所属注册用户标识
     */
    public String getTroupeuid() {
        return troupeuid;
    }

    /**
     * 设置艺术团所属注册用户标识
     *
     * @param troupeuid 艺术团所属注册用户标识
     */
    public void setTroupeuid(String troupeuid) {
        this.troupeuid = troupeuid;
    }

    /**
     * 获取艺术团图片,列表图片
     *
     * @return troupepic - 艺术团图片,列表图片
     */
    public String getTroupepic() {
        return troupepic;
    }

    /**
     * 设置艺术团图片,列表图片
     *
     * @param troupepic 艺术团图片,列表图片
     */
    public void setTroupepic(String troupepic) {
        this.troupepic = troupepic;
    }

    /**
     * 获取艺术团名称
     *
     * @return troupename - 艺术团名称
     */
    public String getTroupename() {
        return troupename;
    }

    /**
     * 设置艺术团名称
     *
     * @param troupename 艺术团名称
     */
    public void setTroupename(String troupename) {
        this.troupename = troupename;
    }

    /**
     * 获取成员人数
     *
     * @return troupernum - 成员人数
     */
    public Integer getTroupernum() {
        return troupernum;
    }

    /**
     * 设置成员人数
     *
     * @param troupernum 成员人数
     */
    public void setTroupernum(Integer troupernum) {
        this.troupernum = troupernum;
    }

    /**
     * 获取艺术类型:音乐书法等
     *
     * @return troupearttyp - 艺术类型:音乐书法等
     */
    public String getTroupearttyp() {
        return troupearttyp;
    }

    /**
     * 设置艺术类型:音乐书法等
     *
     * @param troupearttyp 艺术类型:音乐书法等
     */
    public void setTroupearttyp(String troupearttyp) {
        this.troupearttyp = troupearttyp;
    }

    /**
     * 获取艺术团类型
     *
     * @return troupetype - 艺术团类型
     */
    public String getTroupetype() {
        return troupetype;
    }

    /**
     * 设置艺术团类型
     *
     * @param troupetype 艺术团类型
     */
    public void setTroupetype(String troupetype) {
        this.troupetype = troupetype;
    }

    /**
     * 获取代表作品说明
     *
     * @return troupemain - 代表作品说明
     */
    public String getTroupemain() {
        return troupemain;
    }

    /**
     * 设置代表作品说明
     *
     * @param troupemain 代表作品说明
     */
    public void setTroupemain(String troupemain) {
        this.troupemain = troupemain;
    }

    /**
     * 获取艺术团简介
     *
     * @return troupedesc - 艺术团简介
     */
    public String getTroupedesc() {
        return troupedesc;
    }

    /**
     * 设置艺术团简介
     *
     * @param troupedesc 艺术团简介
     */
    public void setTroupedesc(String troupedesc) {
        this.troupedesc = troupedesc;
    }

    /**
     * 获取成立时间
     *
     * @return trouperegtime - 成立时间
     */
    public Date getTrouperegtime() {
        return trouperegtime;
    }

    /**
     * 设置成立时间
     *
     * @param trouperegtime 成立时间
     */
    public void setTrouperegtime(Date trouperegtime) {
        this.trouperegtime = trouperegtime;
    }

    /**
     * 获取上首页标识
     *
     * @return troupeghp - 上首页标识
     */
    public Integer getTroupeghp() {
        return troupeghp;
    }

    /**
     * 设置上首页标识
     *
     * @param troupeghp 上首页标识
     */
    public void setTroupeghp(Integer troupeghp) {
        this.troupeghp = troupeghp;
    }

    /**
     * 获取上首页排序
     *
     * @return troupeidx - 上首页排序
     */
    public Integer getTroupeidx() {
        return troupeidx;
    }

    /**
     * 设置上首页排序
     *
     * @param troupeidx 上首页排序
     */
    public void setTroupeidx(Integer troupeidx) {
        this.troupeidx = troupeidx;
    }

    /**
     * 获取状态:0-初始,1-送审,2-已审,3-已发岸上
     *
     * @return troupestate - 状态:0-初始,1-送审,2-已审,3-已发岸上
     */
    public Integer getTroupestate() {
        return troupestate;
    }

    /**
     * 设置状态:0-初始,1-送审,2-已审,3-已发岸上
     *
     * @param troupestate 状态:0-初始,1-送审,2-已审,3-已发岸上
     */
    public void setTroupestate(Integer troupestate) {
        this.troupestate = troupestate;
    }

    /**
     * 获取详情页图片
     *
     * @return troupebigpic - 详情页图片
     */
    public String getTroupebigpic() {
        return troupebigpic;
    }

    /**
     * 设置详情页图片
     *
     * @param troupebigpic 详情页图片
     */
    public void setTroupebigpic(String troupebigpic) {
        this.troupebigpic = troupebigpic;
    }

    /**
     * 获取关键字
     *
     * @return troupekey - 关键字
     */
    public String getTroupekey() {
        return troupekey;
    }

    /**
     * 设置关键字
     *
     * @param troupekey 关键字
     */
    public void setTroupekey(String troupekey) {
        this.troupekey = troupekey;
    }

    /**
     * 获取标签
     *
     * @return troupetag - 标签
     */
    public String getTroupetag() {
        return troupetag;
    }

    /**
     * 设置标签
     *
     * @param troupetag 标签
     */
    public void setTroupetag(String troupetag) {
        this.troupetag = troupetag;
    }

    /**
     * 获取修改状态的操作时间
     *
     * @return troupeopttime - 修改状态的操作时间
     */
    public Date getTroupeopttime() {
        return troupeopttime;
    }

    /**
     * 设置修改状态的操作时间
     *
     * @param troupeopttime 修改状态的操作时间
     */
    public void setTroupeopttime(Date troupeopttime) {
        this.troupeopttime = troupeopttime;
    }

    /**
     * 获取团队地址
     *
     * @return troupeaddr - 团队地址
     */
    public String getTroupeaddr() {
        return troupeaddr;
    }

    /**
     * 设置团队地址
     *
     * @param troupeaddr 团队地址
     */
    public void setTroupeaddr(String troupeaddr) {
        this.troupeaddr = troupeaddr;
    }

    /**
     * 获取团队联系人
     *
     * @return troupecontact - 团队联系人
     */
    public String getTroupecontact() {
        return troupecontact;
    }

    /**
     * 设置团队联系人
     *
     * @param troupecontact 团队联系人
     */
    public void setTroupecontact(String troupecontact) {
        this.troupecontact = troupecontact;
    }

    /**
     * 获取详细介绍
     *
     * @return troupecontent - 详细介绍
     */
    public String getTroupecontent() {
        return troupecontent;
    }

    /**
     * 设置详细介绍
     *
     * @param troupecontent 详细介绍
     */
    public void setTroupecontent(String troupecontent) {
        this.troupecontent = troupecontent;
    }

    public Date getCrtdate() {
        return crtdate;
    }

    public void setCrtdate(Date crtdate) {
        this.crtdate = crtdate;
    }
}