package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "wh_art_journal")
public class WhArtJournal {
    /**
     * 杂志标识
     */
    @Id
    private String journalid;

    /**
     * 杂志图片
     */
    private String journalpic;

    /**
     * 杂志名称
     */
    private String journalname;

    /**
     * 杂志类型
     */
    private String journaltype;

    /**
     * 注册时间
     */
    private Date journalregtime;

    /**
     * 上首页标识
     */
    private Integer journalghp;

    /**
     * 上首页排序
     */
    private Integer journalidx;

    /**
     * 状态:0-初始,1-送审,2-已审,3-已发岸上
     */
    private Integer journalstate;

    /**
     * 杂志简介
     */
    private String journaldesc;

    /**
     * 获取杂志标识
     *
     * @return journalid - 杂志标识
     */
    public String getJournalid() {
        return journalid;
    }

    /**
     * 设置杂志标识
     *
     * @param journalid 杂志标识
     */
    public void setJournalid(String journalid) {
        this.journalid = journalid;
    }

    /**
     * 获取杂志图片
     *
     * @return journalpic - 杂志图片
     */
    public String getJournalpic() {
        return journalpic;
    }

    /**
     * 设置杂志图片
     *
     * @param journalpic 杂志图片
     */
    public void setJournalpic(String journalpic) {
        this.journalpic = journalpic;
    }

    /**
     * 获取杂志名称
     *
     * @return journalname - 杂志名称
     */
    public String getJournalname() {
        return journalname;
    }

    /**
     * 设置杂志名称
     *
     * @param journalname 杂志名称
     */
    public void setJournalname(String journalname) {
        this.journalname = journalname;
    }

    /**
     * 获取杂志类型
     *
     * @return journaltype - 杂志类型
     */
    public String getJournaltype() {
        return journaltype;
    }

    /**
     * 设置杂志类型
     *
     * @param journaltype 杂志类型
     */
    public void setJournaltype(String journaltype) {
        this.journaltype = journaltype;
    }

    /**
     * 获取注册时间
     *
     * @return journalregtime - 注册时间
     */
    public Date getJournalregtime() {
        return journalregtime;
    }

    /**
     * 设置注册时间
     *
     * @param journalregtime 注册时间
     */
    public void setJournalregtime(Date journalregtime) {
        this.journalregtime = journalregtime;
    }

    /**
     * 获取上首页标识
     *
     * @return journalghp - 上首页标识
     */
    public Integer getJournalghp() {
        return journalghp;
    }

    /**
     * 设置上首页标识
     *
     * @param journalghp 上首页标识
     */
    public void setJournalghp(Integer journalghp) {
        this.journalghp = journalghp;
    }

    /**
     * 获取上首页排序
     *
     * @return journalidx - 上首页排序
     */
    public Integer getJournalidx() {
        return journalidx;
    }

    /**
     * 设置上首页排序
     *
     * @param journalidx 上首页排序
     */
    public void setJournalidx(Integer journalidx) {
        this.journalidx = journalidx;
    }

    /**
     * 获取状态:0-初始,1-送审,2-已审,3-已发岸上
     *
     * @return journalstate - 状态:0-初始,1-送审,2-已审,3-已发岸上
     */
    public Integer getJournalstate() {
        return journalstate;
    }

    /**
     * 设置状态:0-初始,1-送审,2-已审,3-已发岸上
     *
     * @param journalstate 状态:0-初始,1-送审,2-已审,3-已发岸上
     */
    public void setJournalstate(Integer journalstate) {
        this.journalstate = journalstate;
    }

    /**
     * 获取杂志简介
     *
     * @return journaldesc - 杂志简介
     */
    public String getJournaldesc() {
        return journaldesc;
    }

    /**
     * 设置杂志简介
     *
     * @param journaldesc 杂志简介
     */
    public void setJournaldesc(String journaldesc) {
        this.journaldesc = journaldesc;
    }
}