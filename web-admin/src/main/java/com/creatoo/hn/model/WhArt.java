package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "wh_art")
public class WhArt {
    /**
     * 艺术标识
     */
    @Id
    private String artid;
    /**
     * 长标题
     */
    private String arttitle;

    /**
     * 短标题
     */
    private String artshorttitle;

    /**
     * 艺术类型：精品文化/个人作品/团队作品
     */
    private String arttyp;

    /**
     * 艺术类型标识：艺术家/团队标识
     */
    private String arttypid;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date artstime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date artetime;

    /**
     * 标签
     */
    private String arttags;

    /**
     * 关键字
     */
    private String artkeys;

    /**
     * 地址
     */
    private String artaddr;

    /**
     * 作者
     */
    private String artauthor;

    /**
     * 小编
     */
    private String arteditor;

    /**
     * 创建者
     */
    private String artcreator;

    /**
     * 创建时间
     */
    private Date artcrttime;

    /**
     * 备注
     */
    private String artnote;

    /**
     * 来源
     */
    private String artfrom;

    /**
     * 封面图
     */
    private String artpic;

    /**
     * 首页图
     */
    private String artpic1;

    /**
     * 详情图
     */
    private String artpic2;

    /**
     * 流程状态：0初始，1送审，2已审，3已发
     */
    private Integer artstate;

    /**
     * 流程标识
     */
    private String artflowid;

    /**
     * 上首页：0-否,1-上首页
     */
    private Integer artghp;

    /**
     * 上首页排序
     */
    private Integer artidx;

    /**
     * 详细内容
     */
    private String artcontent;
    
    /**
     * 艺术家介绍
     */
    private String artartistdesc;
    
   
    public String getArtartistdesc() {
		return artartistdesc;
	}

	public void setArtartistdesc(String artartistdesc) {
		this.artartistdesc = artartistdesc;
	}

	/**
     * 获取艺术标识
     *
     * @return artid - 艺术标识
     */
    public String getArtid() {
        return artid;
    }

    /**
     * 设置艺术标识
     *
     * @param artid 艺术标识
     */
    public void setArtid(String artid) {
        this.artid = artid;
    }

    /**
     * 获取长标题
     *
     * @return arttitle - 长标题
     */
    public String getArttitle() {
        return arttitle;
    }

    /**
     * 设置长标题
     *
     * @param arttitle 长标题
     */
    public void setArttitle(String arttitle) {
        this.arttitle = arttitle;
    }

    /**
     * 获取短标题
     *
     * @return artshorttitle - 短标题
     */
    public String getArtshorttitle() {
        return artshorttitle;
    }

    /**
     * 设置短标题
     *
     * @param artshorttitle 短标题
     */
    public void setArtshorttitle(String artshorttitle) {
        this.artshorttitle = artshorttitle;
    }

    /**
     * 获取艺术类型：精品文化/个人作品/团队作品
     *
     * @return arttyp - 艺术类型：精品文化/个人作品/团队作品
     */
    public String getArttyp() {
        return arttyp;
    }

    /**
     * 设置艺术类型：精品文化/个人作品/团队作品
     *
     * @param arttyp 艺术类型：精品文化/个人作品/团队作品
     */
    public void setArttyp(String arttyp) {
        this.arttyp = arttyp;
    }

    /**
     * 获取艺术类型标识：艺术家/团队标识
     *
     * @return arttypid - 艺术类型标识：艺术家/团队标识
     */
    public String getArttypid() {
        return arttypid;
    }

    /**
     * 设置艺术类型标识：艺术家/团队标识
     *
     * @param arttypid 艺术类型标识：艺术家/团队标识
     */
    public void setArttypid(String arttypid) {
        this.arttypid = arttypid;
    }

    /**
     * 获取开始时间
     *
     * @return artstime - 开始时间
     */
    public Date getArtstime() {
        return artstime;
    }

    /**
     * 设置开始时间
     *
     * @param artstime 开始时间
     */
    public void setArtstime(Date artstime) {
        this.artstime = artstime;
    }

    /**
     * 获取结束时间
     *
     * @return artetime - 结束时间
     */
    public Date getArtetime() {
        return artetime;
    }

    /**
     * 设置结束时间
     *
     * @param artetime 结束时间
     */
    public void setArtetime(Date artetime) {
        this.artetime = artetime;
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
     * 获取地址
     *
     * @return artaddr - 地址
     */
    public String getArtaddr() {
        return artaddr;
    }

    /**
     * 设置地址
     *
     * @param artaddr 地址
     */
    public void setArtaddr(String artaddr) {
        this.artaddr = artaddr;
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
     * 获取小编
     *
     * @return arteditor - 小编
     */
    public String getArteditor() {
        return arteditor;
    }

    /**
     * 设置小编
     *
     * @param arteditor 小编
     */
    public void setArteditor(String arteditor) {
        this.arteditor = arteditor;
    }

    /**
     * 获取创建者
     *
     * @return artcreator - 创建者
     */
    public String getArtcreator() {
        return artcreator;
    }

    /**
     * 设置创建者
     *
     * @param artcreator 创建者
     */
    public void setArtcreator(String artcreator) {
        this.artcreator = artcreator;
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
     * 获取备注
     *
     * @return artnote - 备注
     */
    public String getArtnote() {
        return artnote;
    }

    /**
     * 设置备注
     *
     * @param artnote 备注
     */
    public void setArtnote(String artnote) {
        this.artnote = artnote;
    }

    /**
     * 获取来源
     *
     * @return artfrom - 来源
     */
    public String getArtfrom() {
        return artfrom;
    }

    /**
     * 设置来源
     *
     * @param artfrom 来源
     */
    public void setArtfrom(String artfrom) {
        this.artfrom = artfrom;
    }

    /**
     * 获取封面图
     *
     * @return artpic - 封面图
     */
    public String getArtpic() {
        return artpic;
    }

    /**
     * 设置封面图
     *
     * @param artpic 封面图
     */
    public void setArtpic(String artpic) {
        this.artpic = artpic;
    }

    /**
     * 获取首页图
     *
     * @return artpic1 - 首页图
     */
    public String getArtpic1() {
        return artpic1;
    }

    /**
     * 设置首页图
     *
     * @param artpic1 首页图
     */
    public void setArtpic1(String artpic1) {
        this.artpic1 = artpic1;
    }

    /**
     * 获取详情图
     *
     * @return artpic2 - 详情图
     */
    public String getArtpic2() {
        return artpic2;
    }

    /**
     * 设置详情图
     *
     * @param artpic2 详情图
     */
    public void setArtpic2(String artpic2) {
        this.artpic2 = artpic2;
    }

    /**
     * 获取流程状态：0初始，1送审，2已审，3已发
     *
     * @return artstate - 流程状态：0初始，1送审，2已审，3已发
     */
    public Integer getArtstate() {
        return artstate;
    }

    /**
     * 设置流程状态：0初始，1送审，2已审，3已发
     *
     * @param artstate 流程状态：0初始，1送审，2已审，3已发
     */
    public void setArtstate(Integer artstate) {
        this.artstate = artstate;
    }

    /**
     * 获取流程标识
     *
     * @return artflowid - 流程标识
     */
    public String getArtflowid() {
        return artflowid;
    }

    /**
     * 设置流程标识
     *
     * @param artflowid 流程标识
     */
    public void setArtflowid(String artflowid) {
        this.artflowid = artflowid;
    }

    /**
     * 获取上首页：0-否,1-上首页
     *
     * @return artghp - 上首页：0-否,1-上首页
     */
    public Integer getArtghp() {
        return artghp;
    }

    /**
     * 设置上首页：0-否,1-上首页
     *
     * @param artghp 上首页：0-否,1-上首页
     */
    public void setArtghp(Integer artghp) {
        this.artghp = artghp;
    }

    /**
     * 获取上首页排序
     *
     * @return artidx - 上首页排序
     */
    public Integer getArtidx() {
        return artidx;
    }

    /**
     * 设置上首页排序
     *
     * @param artidx 上首页排序
     */
    public void setArtidx(Integer artidx) {
        this.artidx = artidx;
    }

    /**
     * 获取详细内容
     *
     * @return artcontent - 详细内容
     */
    public String getArtcontent() {
        return artcontent;
    }

    /**
     * 设置详细内容
     *
     * @param artcontent 详细内容
     */
    public void setArtcontent(String artcontent) {
        this.artcontent = artcontent;
    }
}