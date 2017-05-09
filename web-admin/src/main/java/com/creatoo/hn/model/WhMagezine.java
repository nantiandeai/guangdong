package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "wh_magezine")
public class WhMagezine {
    /**
     * 电子杂志ID
     */
    @Id
    private String mageid;

    /**
     * 电子杂志标题
     */
    private String magetitle;

    /**
     * 电子杂志刊号
     */
    private String magenum;

    /**
     * 电子杂志图片
     */
    private String magepic;

    /**
     * 电子杂志简介
     */
    private String magedesc;

    /**
     * 艺术类型
     */
    private String magearttyp;

    /**
     * 电子杂志状态（0初始 1审核 2 发布）
     */
    private Integer magestate;

    /**
     * 修改状态的操作时间
     */
    private Date mageopttime;

    /**
     * 获取电子杂志ID
     *
     * @return mageid - 电子杂志ID
     */
    public String getMageid() {
        return mageid;
    }

    /**
     * 设置电子杂志ID
     *
     * @param mageid 电子杂志ID
     */
    public void setMageid(String mageid) {
        this.mageid = mageid;
    }

    /**
     * 获取电子杂志标题
     *
     * @return magetitle - 电子杂志标题
     */
    public String getMagetitle() {
        return magetitle;
    }

    /**
     * 设置电子杂志标题
     *
     * @param magetitle 电子杂志标题
     */
    public void setMagetitle(String magetitle) {
        this.magetitle = magetitle;
    }

    /**
     * 获取电子杂志刊号
     *
     * @return magenum - 电子杂志刊号
     */
    public String getMagenum() {
        return magenum;
    }

    /**
     * 设置电子杂志刊号
     *
     * @param magenum 电子杂志刊号
     */
    public void setMagenum(String magenum) {
        this.magenum = magenum;
    }

    /**
     * 获取电子杂志图片
     *
     * @return magepic - 电子杂志图片
     */
    public String getMagepic() {
        return magepic;
    }

    /**
     * 设置电子杂志图片
     *
     * @param magepic 电子杂志图片
     */
    public void setMagepic(String magepic) {
        this.magepic = magepic;
    }

    /**
     * 获取电子杂志简介
     *
     * @return magedesc - 电子杂志简介
     */
    public String getMagedesc() {
        return magedesc;
    }

    /**
     * 设置电子杂志简介
     *
     * @param magedesc 电子杂志简介
     */
    public void setMagedesc(String magedesc) {
        this.magedesc = magedesc;
    }

    /**
     * 获取艺术类型
     *
     * @return magearttyp - 艺术类型
     */
    public String getMagearttyp() {
        return magearttyp;
    }

    /**
     * 设置艺术类型
     *
     * @param magearttyp 艺术类型
     */
    public void setMagearttyp(String magearttyp) {
        this.magearttyp = magearttyp;
    }

    /**
     * 获取电子杂志状态（0初始 1审核 2 发布）
     *
     * @return magestate - 电子杂志状态（0初始 1审核 2 发布）
     */
    public Integer getMagestate() {
        return magestate;
    }

    /**
     * 设置电子杂志状态（0初始 1审核 2 发布）
     *
     * @param magestate 电子杂志状态（0初始 1审核 2 发布）
     */
    public void setMagestate(Integer magestate) {
        this.magestate = magestate;
    }

    /**
     * 获取修改状态的操作时间
     *
     * @return mageopttime - 修改状态的操作时间
     */
    public Date getMageopttime() {
        return mageopttime;
    }

    /**
     * 设置修改状态的操作时间
     *
     * @param mageopttime 修改状态的操作时间
     */
    public void setMageopttime(Date mageopttime) {
        this.mageopttime = mageopttime;
    }
}