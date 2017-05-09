package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "wh_collection")
public class WhCollection {
    /**
     * 收藏id
     */
    @Id
    private String cmid;

    /**
     * 收藏时间
     */
    private Date cmdate;

    /**
     * 用户id
     */
    private String cmuid;

    /**
     * 收藏关联类型1.活动 2.培训
     */
    private String cmreftyp;

    /**
     * 收藏关联类型id
     */
    private String cmrefid;

    /**
     * 操作类型:0收藏,1浏览,2推荐,3置顶
     */
    private String cmopttyp;
    
    private String cmtitle;
    
    private String cmurl;

    /**
     * 获取收藏id
     *
     * @return cmid - 收藏id
     */
    public String getCmid() {
        return cmid;
    }

    /**
     * 设置收藏id
     *
     * @param cmid 收藏id
     */
    public void setCmid(String cmid) {
        this.cmid = cmid;
    }

    /**
     * 获取收藏时间
     *
     * @return cmdate - 收藏时间
     */
    public Date getCmdate() {
        return cmdate;
    }

    /**
     * 设置收藏时间
     *
     * @param cmdate 收藏时间
     */
    public void setCmdate(Date cmdate) {
        this.cmdate = cmdate;
    }

    /**
     * 获取用户id
     *
     * @return cmuid - 用户id
     */
    public String getCmuid() {
        return cmuid;
    }

    /**
     * 设置用户id
     *
     * @param cmuid 用户id
     */
    public void setCmuid(String cmuid) {
        this.cmuid = cmuid;
    }

    /**
     * 获取收藏关联类型1.活动 2.培训
     *
     * @return cmreftyp - 收藏关联类型1.活动 2.培训
     */
    public String getCmreftyp() {
        return cmreftyp;
    }

    /**
     * 设置收藏关联类型1.活动 2.培训
     *
     * @param cmreftyp 收藏关联类型1.活动 2.培训
     */
    public void setCmreftyp(String cmreftyp) {
        this.cmreftyp = cmreftyp;
    }

    /**
     * 获取收藏关联类型id
     *
     * @return cmrefid - 收藏关联类型id
     */
    public String getCmrefid() {
        return cmrefid;
    }

    /**
     * 设置收藏关联类型id
     *
     * @param cmrefid 收藏关联类型id
     */
    public void setCmrefid(String cmrefid) {
        this.cmrefid = cmrefid;
    }

    /**
     * 获取操作类型:0收藏,1浏览,2推荐,3置顶
     *
     * @return cmopttyp - 操作类型:0收藏,1浏览,2推荐,3置顶
     */
    public String getCmopttyp() {
        return cmopttyp;
    }

    /**
     * 设置操作类型:0收藏,1浏览,2推荐,3置顶
     *
     * @param cmopttyp 操作类型:0收藏,1浏览,2推荐,3置顶
     */
    public void setCmopttyp(String cmopttyp) {
        this.cmopttyp = cmopttyp;
    }

	public String getCmtitle() {
		return cmtitle;
	}

	public void setCmtitle(String cmtitle) {
		this.cmtitle = cmtitle;
	}

	public String getCmurl() {
		return cmurl;
	}

	public void setCmurl(String cmurl) {
		this.cmurl = cmurl;
	}
    
}