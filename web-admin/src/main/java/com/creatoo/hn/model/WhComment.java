package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "wh_comment")
public class WhComment {
    /**
     * 点评id
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String rmid;

    /**
     * 用户id
     */
    private String rmuid;

    /**
     * 评论时间
     */
    private Date rmdate;

    /**
     * 评论信息
     */
    private String rmcontent;

    /**
     * 评论关联类型'1:(活动)2:(培训)3:(培训师资)4:培训点播5:志愿培训6:场馆评论7:场馆-活动室'
     */
    private String rmreftyp;

    /**
     * 评论关联类型id
     */
    private String rmrefid;

    /**
     * 0评论,1回复
     */
    private Integer rmtyp;
    /**
     * 评论状态
     */
    private Integer rmstate;
    
    
    /**
     * 点评对象的标题
     */
    private String rmtitle;
    
    /**
     * 点评对象的连接
     */
    private String rmurl;
    
    private Integer rmpltype;
    
    private String rmvenueid;
    
    public String getRmtitle() {
		return rmtitle;
	}

	public void setRmtitle(String rmtitle) {
		this.rmtitle = rmtitle;
	}

	public String getRmurl() {
		return rmurl;
	}

	public void setRmurl(String rmurl) {
		this.rmurl = rmurl;
	}

	/**
     * 获取点评id
     *
     * @return rmid - 点评id
     */
    public String getRmid() {
        return rmid;
    }

    /**
     * 设置点评id
     *
     * @param rmid 点评id
     */
    public void setRmid(String rmid) {
        this.rmid = rmid;
    }

    /**
     * 获取用户id
     *
     * @return rmuid - 用户id
     */
    public String getRmuid() {
        return rmuid;
    }

    /**
     * 设置用户id
     *
     * @param rmuid 用户id
     */
    public void setRmuid(String rmuid) {
        this.rmuid = rmuid;
    }

    /**
     * 获取评论时间
     *
     * @return rmdate - 评论时间
     */
    public Date getRmdate() {
        return rmdate;
    }

    /**
     * 设置评论时间
     *
     * @param rmdate 评论时间
     */
    public void setRmdate(Date rmdate) {
        this.rmdate = rmdate;
    }

    /**
     * 获取评论信息
     *
     * @return rmcontent - 评论信息
     */
    public String getRmcontent() {
        return rmcontent;
    }

    /**
     * 设置评论信息
     *
     * @param rmcontent 评论信息
     */
    public void setRmcontent(String rmcontent) {
        this.rmcontent = rmcontent;
    }

    /**
     * 获取评论关联类型
     *
     * @return rmreftyp - 评论关联类型
     */
    public String getRmreftyp() {
        return rmreftyp;
    }

    /**
     * 设置评论关联类型
     *
     * @param rmreftyp 评论关联类型
     */
    public void setRmreftyp(String rmreftyp) {
        this.rmreftyp = rmreftyp;
    }

    /**
     * 获取评论关联类型id
     *
     * @return rmrefid - 评论关联类型id
     */
    public String getRmrefid() {
        return rmrefid;
    }

    /**
     * 设置评论关联类型id
     *
     * @param rmrefid 评论关联类型id
     */
    public void setRmrefid(String rmrefid) {
        this.rmrefid = rmrefid;
    }

    /**
     * 获取0评论,1回复
     *
     * @return rmtyp - 0评论,1回复
     */
    public Integer getRmtyp() {
        return rmtyp;
    }

    /**
     * 设置0评论,1回复
     *
     * @param rmtyp 0评论,1回复
     */
    public void setRmtyp(Integer rmtyp) {
        this.rmtyp = rmtyp;
    }

	public Integer getRmstate() {
		return rmstate;
	}

	public void setRmstate(Integer rmstate) {
		this.rmstate = rmstate;
	}

	public Integer getRmpltype() {
		return rmpltype;
	}

	public void setRmpltype(Integer rmpltype) {
		this.rmpltype = rmpltype;
	}

	public String getRmvenueid() {
		return rmvenueid;
	}

	public void setRmvenueid(String rmvenueid) {
		this.rmvenueid = rmvenueid;
	}
    
	
}