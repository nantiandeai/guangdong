package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "whg_exh_art")
public class WhgExhArt {
    /**
     * 主键
     */
    @Id
    private String artid;

    /**
     * 标题
     */
    private String arttitle;

    /**
     * 数字展览ID
     */
    private String artexhid;

    /**
     * 标签
     */
    private String arttags;

    /**
     * 关键字
     */
    private String artkeys;

    /**
     * 作者
     */
    private String artauthor;

    /**
     * 创建时间
     */
    private Date artcrttime;

    /**
     * 作品类型. 1-图片, 2-视频, 3-音频
     */
    private Integer arttype;

    /**
     * 作品图片地址
     */
    private String artpic;

    /**
     * 作品视频地址
     */
    private String artpvod;

    /**
     * 作品音频地址
     */
    private String artpaud;

    /**
     * 排序标识
     */
    private Integer artidx;

    /**
     * 状态,1-启用, 0-停用
     */
    private Integer artstate;

    /**
     * 改变审核状态的操作时间
     */
    private Date artopttime;

    /**
     * 所属文化馆标识
     */
    private String exhcultid;

    /**
     * 所属部门标识
     */
    private String exhdeptid;

    /**
     * 作者介绍
     */
    private String artartistdesc;

    /**
     * 作品介绍
     */
    private String artcontent;

    /**
     * 获取主键
     *
     * @return artid - 主键
     */
    public String getArtid() {
        return artid;
    }

    /**
     * 设置主键
     *
     * @param artid 主键
     */
    public void setArtid(String artid) {
        this.artid = artid;
    }

    /**
     * 获取标题
     *
     * @return arttitle - 标题
     */
    public String getArttitle() {
        return arttitle;
    }

    /**
     * 设置标题
     *
     * @param arttitle 标题
     */
    public void setArttitle(String arttitle) {
        this.arttitle = arttitle;
    }

    /**
     * 获取数字展览ID
     *
     * @return artexhid - 数字展览ID
     */
    public String getArtexhid() {
        return artexhid;
    }

    /**
     * 设置数字展览ID
     *
     * @param artexhid 数字展览ID
     */
    public void setArtexhid(String artexhid) {
        this.artexhid = artexhid;
    }

    /**
     * 获取标签
     *
     * @return arttags - 标签
     */
    public String getArttags() {
        return arttags;
    }

    /**
     * 设置标签
     *
     * @param arttags 标签
     */
    public void setArttags(String arttags) {
        this.arttags = arttags;
    }

    /**
     * 获取关键字
     *
     * @return artkeys - 关键字
     */
    public String getArtkeys() {
        return artkeys;
    }

    /**
     * 设置关键字
     *
     * @param artkeys 关键字
     */
    public void setArtkeys(String artkeys) {
        this.artkeys = artkeys;
    }

    /**
     * 获取作者
     *
     * @return artauthor - 作者
     */
    public String getArtauthor() {
        return artauthor;
    }

    /**
     * 设置作者
     *
     * @param artauthor 作者
     */
    public void setArtauthor(String artauthor) {
        this.artauthor = artauthor;
    }

    /**
     * 获取创建时间
     *
     * @return artcrttime - 创建时间
     */
    public Date getArtcrttime() {
        return artcrttime;
    }

    /**
     * 设置创建时间
     *
     * @param artcrttime 创建时间
     */
    public void setArtcrttime(Date artcrttime) {
        this.artcrttime = artcrttime;
    }

    /**
     * 获取作品类型. 1-图片, 2-视频, 3-音频
     *
     * @return arttype - 作品类型. 1-图片, 2-视频, 3-音频
     */
    public Integer getArttype() {
        return arttype;
    }

    /**
     * 设置作品类型. 1-图片, 2-视频, 3-音频
     *
     * @param arttype 作品类型. 1-图片, 2-视频, 3-音频
     */
    public void setArttype(Integer arttype) {
        this.arttype = arttype;
    }

    /**
     * 获取作品图片地址
     *
     * @return artpic - 作品图片地址
     */
    public String getArtpic() {
        return artpic;
    }

    /**
     * 设置作品图片地址
     *
     * @param artpic 作品图片地址
     */
    public void setArtpic(String artpic) {
        this.artpic = artpic;
    }

    /**
     * 获取作品视频地址
     *
     * @return artpvod - 作品视频地址
     */
    public String getArtpvod() {
        return artpvod;
    }

    /**
     * 设置作品视频地址
     *
     * @param artpvod 作品视频地址
     */
    public void setArtpvod(String artpvod) {
        this.artpvod = artpvod;
    }

    /**
     * 获取作品音频地址
     *
     * @return artpaud - 作品音频地址
     */
    public String getArtpaud() {
        return artpaud;
    }

    /**
     * 设置作品音频地址
     *
     * @param artpaud 作品音频地址
     */
    public void setArtpaud(String artpaud) {
        this.artpaud = artpaud;
    }

    /**
     * 获取排序标识
     *
     * @return artidx - 排序标识
     */
    public Integer getArtidx() {
        return artidx;
    }

    /**
     * 设置排序标识
     *
     * @param artidx 排序标识
     */
    public void setArtidx(Integer artidx) {
        this.artidx = artidx;
    }

    /**
     * 获取状态,1-启用, 0-停用
     *
     * @return artstate - 状态,1-启用, 0-停用
     */
    public Integer getArtstate() {
        return artstate;
    }

    /**
     * 设置状态,1-启用, 0-停用
     *
     * @param artstate 状态,1-启用, 0-停用
     */
    public void setArtstate(Integer artstate) {
        this.artstate = artstate;
    }

    /**
     * 获取改变审核状态的操作时间
     *
     * @return artopttime - 改变审核状态的操作时间
     */
    public Date getArtopttime() {
        return artopttime;
    }

    /**
     * 设置改变审核状态的操作时间
     *
     * @param artopttime 改变审核状态的操作时间
     */
    public void setArtopttime(Date artopttime) {
        this.artopttime = artopttime;
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

    /**
     * 获取作者介绍
     *
     * @return artartistdesc - 作者介绍
     */
    public String getArtartistdesc() {
        return artartistdesc;
    }

    /**
     * 设置作者介绍
     *
     * @param artartistdesc 作者介绍
     */
    public void setArtartistdesc(String artartistdesc) {
        this.artartistdesc = artartistdesc;
    }

    /**
     * 获取作品介绍
     *
     * @return artcontent - 作品介绍
     */
    public String getArtcontent() {
        return artcontent;
    }

    /**
     * 设置作品介绍
     *
     * @param artcontent 作品介绍
     */
    public void setArtcontent(String artcontent) {
        this.artcontent = artcontent;
    }
}