package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "wh_user_artist")
public class WhUserArtist {
    /**
     * 艺术家标识
     */
    @Id
    private String artistid;

    /**
     * 艺术家所属注册用户标识
     */
    private String artistuid;

    /**
     * 艺术家图片
     */
    private String artistpic;

    private String artistarttyp;

    /**
     * 艺术家名称
     */
    private String artistname;

    /**
     * 艺术家类型
     */
    private String artisttype;

    /**
     * 艺术家简介
     */
    private String artistdesc;

    /**
     * 注册时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date artistregtime;

    /**
     * 上首页标识
     */
    private Integer artistghp;

    /**
     * 上首页排序
     */
    private Integer artistidx;

    /**
     * 状态:0-初始,1-送审,2-已审,3-已发岸上
     */
    private Integer artiststate;
    /**
     * 改变状态的操作时间
     */
    private Date artstime;
    
    /**
     * 改变状态的操作时间
     */
    public Date getArtstime() {
		return artstime;
	}
    /**
     * 改变状态的操作时间
     */
	public void setArtstime(Date artstime) {
		this.artstime = artstime;
	}

	/**
     * 获取艺术家标识
     *
     * @return artistid - 艺术家标识
     */
    public String getArtistid() {
        return artistid;
    }

    /**
     * 设置艺术家标识
     *
     * @param artistid 艺术家标识
     */
    public void setArtistid(String artistid) {
        this.artistid = artistid;
    }

    /**
     * 获取艺术家所属注册用户标识
     *
     * @return artistuid - 艺术家所属注册用户标识
     */
    public String getArtistuid() {
        return artistuid;
    }

    /**
     * 设置艺术家所属注册用户标识
     *
     * @param artistuid 艺术家所属注册用户标识
     */
    public void setArtistuid(String artistuid) {
        this.artistuid = artistuid;
    }

    /**
     * 获取艺术家图片
     *
     * @return artistpic - 艺术家图片
     */
    public String getArtistpic() {
        return artistpic;
    }

    /**
     * 设置艺术家图片
     *
     * @param artistpic 艺术家图片
     */
    public void setArtistpic(String artistpic) {
        this.artistpic = artistpic;
    }

    /**
     * @return artistarttyp
     */
    public String getArtistarttyp() {
        return artistarttyp;
    }

    /**
     * @param artistarttyp
     */
    public void setArtistarttyp(String artistarttyp) {
        this.artistarttyp = artistarttyp;
    }

    /**
     * 获取艺术家名称
     *
     * @return artistname - 艺术家名称
     */
    public String getArtistname() {
        return artistname;
    }

    /**
     * 设置艺术家名称
     *
     * @param artistname 艺术家名称
     */
    public void setArtistname(String artistname) {
        this.artistname = artistname;
    }

    /**
     * 获取艺术家类型
     *
     * @return artisttype - 艺术家类型
     */
    public String getArtisttype() {
        return artisttype;
    }

    /**
     * 设置艺术家类型
     *
     * @param artisttype 艺术家类型
     */
    public void setArtisttype(String artisttype) {
        this.artisttype = artisttype;
    }

    /**
     * 获取艺术家简介
     *
     * @return artistdesc - 艺术家简介
     */
    public String getArtistdesc() {
        return artistdesc;
    }

    /**
     * 设置艺术家简介
     *
     * @param artistdesc 艺术家简介
     */
    public void setArtistdesc(String artistdesc) {
        this.artistdesc = artistdesc;
    }

    /**
     * 获取注册时间
     *
     * @return artistregtime - 注册时间
     */
    public Date getArtistregtime() {
        return artistregtime;
    }

    /**
     * 设置注册时间
     *
     * @param artistregtime 注册时间
     */
    public void setArtistregtime(Date artistregtime) {
        this.artistregtime = artistregtime;
    }

    /**
     * 获取上首页标识
     *
     * @return artistghp - 上首页标识
     */
    public Integer getArtistghp() {
        return artistghp;
    }

    /**
     * 设置上首页标识
     *
     * @param artistghp 上首页标识
     */
    public void setArtistghp(Integer artistghp) {
        this.artistghp = artistghp;
    }

    /**
     * 获取上首页排序
     *
     * @return artistidx - 上首页排序
     */
    public Integer getArtistidx() {
        return artistidx;
    }

    /**
     * 设置上首页排序
     *
     * @param artistidx 上首页排序
     */
    public void setArtistidx(Integer artistidx) {
        this.artistidx = artistidx;
    }

    /**
     * 获取状态:0-初始,1-送审,2-已审,3-已发岸上
     *
     * @return artiststate - 状态:0-初始,1-送审,2-已审,3-已发岸上
     */
    public Integer getArtiststate() {
        return artiststate;
    }

    /**
     * 设置状态:0-初始,1-送审,2-已审,3-已发岸上
     *
     * @param artiststate 状态:0-初始,1-送审,2-已审,3-已发岸上
     */
    public void setArtiststate(Integer artiststate) {
        this.artiststate = artiststate;
    }
}