package com.creatoo.hn.model;

import java.util.Date;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "wh_entsource")
public class WhEntsource {
    /**
     * 资源ID
     */
    @Id
    private String entid;

    /**
     * 资源类型（图片/音频/视频）
     */
    private String enttype;

    /**
     * 实体类型（活动/培训）
     */
    private String reftype;

    /**
     * 实体id(培训/活动的ID)
     */
    private String refid;

    /**
     * 资源的地址
     */
    private String enturl;

    /**
     * 资源的名字
     */
    private String entname;

    /**
     * 视频/音频 时长
     */
    private String enttimes;
    
    /**
     * 封面图Url
     */
    private String deourl;
    
    /**
     * 所属名录/传承人 标识
     */
    private String entsuorpro;
    
    /**
     * 作者
     */
    private String entauthor;
    
    /**
     * 关键字
     */
    private String entkey;
    
    /**
     * 资源修改时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date entoptimt;
    
    /**
     * 地点
     */
    private String entaddress;
    
    /**
     * 资源内容
     */
    private String entcontent;
    
    /**
     * 获取资源ID
     *
     * @return entid - 资源ID
     */
    public String getEntid() {
        return entid;
    }
    /**
     * 设置资源ID
     *
     * @param entid 资源ID
     */
    public void setEntid(String entid) {
        this.entid = entid;
    }

    /**
     * 获取资源类型（图片/音频/视频）
     *
     * @return enttype - 资源类型（图片/音频/视频）
     */
    public String getEnttype() {
        return enttype;
    }

    /**
     * 设置资源类型（图片/音频/视频）
     *
     * @param enttype 资源类型（图片/音频/视频）
     */
    public void setEnttype(String enttype) {
        this.enttype = enttype;
    }

    /**
     * 获取实体类型（活动/培训）
     *
     * @return reftype - 实体类型（活动/培训）
     */
    public String getReftype() {
        return reftype;
    }

    /**
     * 设置实体类型（活动/培训）
     *
     * @param reftype 实体类型（活动/培训）
     */
    public void setReftype(String reftype) {
        this.reftype = reftype;
    }

    /**
     * 获取实体id(培训/活动的ID)
     *
     * @return refid - 实体id(培训/活动的ID)
     */
    public String getRefid() {
        return refid;
    }

    /**
     * 设置实体id(培训/活动的ID)
     *
     * @param refid 实体id(培训/活动的ID)
     */
    public void setRefid(String refid) {
        this.refid = refid;
    }

    /**
     * 获取资源的地址
     *
     * @return enturl - 资源的地址
     */
    public String getEnturl() {
        return enturl;
    }

    /**
     * 设置资源的地址
     *
     * @param enturl 资源的地址
     */
    public void setEnturl(String enturl) {
        this.enturl = enturl;
    }

    /**
     * 获取资源的名字
     *
     * @return entname - 资源的名字
     */
    public String getEntname() {
        return entname;
    }

    /**
     * 设置资源的名字
     *
     * @param entname 资源的名字
     */
    public void setEntname(String entname) {
        this.entname = entname;
    }

    /**
     * 获取视频类型相关封面图
     *
     * @return entvideopic - 视频类型相关封面图
     */
	public String getDeourl() {
		return deourl;
	}
	  /**
     * 设置视频类型相关封面图
     *
     * @param entvideopic 视频类型相关封面图
     */
	public void setDeourl(String deourl) {
		this.deourl = deourl;
	}
	public String getEnttimes() {
		return enttimes;
	}
	public void setEnttimes(String enttimes) {
		this.enttimes = enttimes;
	}
	public String getEntsuorpro() {
		return entsuorpro;
	}
	public void setEntsuorpro(String entsuorpro) {
		this.entsuorpro = entsuorpro;
	}
	public String getEntauthor() {
		return entauthor;
	}
	public void setEntauthor(String entauthor) {
		this.entauthor = entauthor;
	}
	public String getEntkey() {
		return entkey;
	}
	public void setEntkey(String entkey) {
		this.entkey = entkey;
	}
	public Date getEntoptimt() {
		return entoptimt;
	}
	public void setEntoptimt(Date entoptimt) {
		this.entoptimt = entoptimt;
	}
	public String getEntaddress() {
		return entaddress;
	}
	public void setEntaddress(String entaddress) {
		this.entaddress = entaddress;
	}
	public String getEntcontent() {
		return entcontent;
	}
	public void setEntcontent(String entcontent) {
		this.entcontent = entcontent;
	}
	
    
}