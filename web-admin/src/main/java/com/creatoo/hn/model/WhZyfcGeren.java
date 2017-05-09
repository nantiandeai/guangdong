package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "wh_zyfc_geren")
public class WhZyfcGeren {
    /**
     * 主键标识
     */
    @Id
    private String zyfcgrid;

    /**
     * 详情标题
     */
    private String zyfcgrtitle;

    /**
     * 列表短标题
     */
    private String zyfcgrshorttitle;

    /**
     * 列表图片
     */
    private String zyfcgrpic;

    /**
     * 详情图片
     */
    private String zyfcgrbigpic;

    /**
     * 文艺专长
     */
    private String zyfcgrzc;

    /**
     * 工作单位
     */
    private String zyfcgrworkaddr;

    /**
     * 参与活动
     */
    private String zyfcgrjoinact;

    /**
     * 加入时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date zyfcgrjrtime;

    /**
     * 服务地区
     */
    private String zyfcgrscope;

    /**
     * 服务时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date zyfcgrfwtime;

    /**
     * 修改状态时间
     */
    private Date zyfcgropttime;

    /**
     * 状态:0-编辑;1-送审;2-审核;3-发布.
     */
    private Integer zyfcgrstate;

    /**
     * 事迹材料
     */
    private String zyfcgrcontent;
    
    /**
     * 服务时长
     */
    private Integer zyfwhen;
    
    /**
     * 服务时长
     */
    
    public Integer getZyfwhen() {
		return zyfwhen;
	}
    
    /**
     * 服务时长
     */
	public void setZyfwhen(Integer zyfwhen) {
		this.zyfwhen = zyfwhen;
	}

	/**
     * 获取主键标识
     *
     * @return zyfcgrid - 主键标识
     */
    public String getZyfcgrid() {
        return zyfcgrid;
    }

    /**
     * 设置主键标识
     *
     * @param zyfcgrid 主键标识
     */
    public void setZyfcgrid(String zyfcgrid) {
        this.zyfcgrid = zyfcgrid;
    }

    /**
     * 获取详情标题
     *
     * @return zyfcgrtitle - 详情标题
     */
    public String getZyfcgrtitle() {
        return zyfcgrtitle;
    }

    /**
     * 设置详情标题
     *
     * @param zyfcgrtitle 详情标题
     */
    public void setZyfcgrtitle(String zyfcgrtitle) {
        this.zyfcgrtitle = zyfcgrtitle;
    }

    /**
     * 获取列表短标题
     *
     * @return zyfcgrshorttitle - 列表短标题
     */
    public String getZyfcgrshorttitle() {
        return zyfcgrshorttitle;
    }

    /**
     * 设置列表短标题
     *
     * @param zyfcgrshorttitle 列表短标题
     */
    public void setZyfcgrshorttitle(String zyfcgrshorttitle) {
        this.zyfcgrshorttitle = zyfcgrshorttitle;
    }

    /**
     * 获取列表图片
     *
     * @return zyfcgrpic - 列表图片
     */
    public String getZyfcgrpic() {
        return zyfcgrpic;
    }

    /**
     * 设置列表图片
     *
     * @param zyfcgrpic 列表图片
     */
    public void setZyfcgrpic(String zyfcgrpic) {
        this.zyfcgrpic = zyfcgrpic;
    }

    /**
     * 获取详情图片
     *
     * @return zyfcgrbigpic - 详情图片
     */
    public String getZyfcgrbigpic() {
        return zyfcgrbigpic;
    }

    /**
     * 设置详情图片
     *
     * @param zyfcgrbigpic 详情图片
     */
    public void setZyfcgrbigpic(String zyfcgrbigpic) {
        this.zyfcgrbigpic = zyfcgrbigpic;
    }

    /**
     * 获取文艺专长
     *
     * @return zyfcgrzc - 文艺专长
     */
    public String getZyfcgrzc() {
        return zyfcgrzc;
    }

    /**
     * 设置文艺专长
     *
     * @param zyfcgrzc 文艺专长
     */
    public void setZyfcgrzc(String zyfcgrzc) {
        this.zyfcgrzc = zyfcgrzc;
    }

    /**
     * 获取工作单位
     *
     * @return zyfcgrworkaddr - 工作单位
     */
    public String getZyfcgrworkaddr() {
        return zyfcgrworkaddr;
    }

    /**
     * 设置工作单位
     *
     * @param zyfcgrworkaddr 工作单位
     */
    public void setZyfcgrworkaddr(String zyfcgrworkaddr) {
        this.zyfcgrworkaddr = zyfcgrworkaddr;
    }

    /**
     * 获取参与活动
     *
     * @return zyfcgrjoinact - 参与活动
     */
    public String getZyfcgrjoinact() {
        return zyfcgrjoinact;
    }

    /**
     * 设置参与活动
     *
     * @param zyfcgrjoinact 参与活动
     */
    public void setZyfcgrjoinact(String zyfcgrjoinact) {
        this.zyfcgrjoinact = zyfcgrjoinact;
    }

    /**
     * 获取加入时间
     *
     * @return zyfcgrjrtime - 加入时间
     */
    public Date getZyfcgrjrtime() {
        return zyfcgrjrtime;
    }

    /**
     * 设置加入时间
     *
     * @param zyfcgrjrtime 加入时间
     */
    public void setZyfcgrjrtime(Date zyfcgrjrtime) {
        this.zyfcgrjrtime = zyfcgrjrtime;
    }

    /**
     * 获取服务地区
     *
     * @return zyfcgrscope - 服务地区
     */
    public String getZyfcgrscope() {
        return zyfcgrscope;
    }

    /**
     * 设置服务地区
     *
     * @param zyfcgrscope 服务地区
     */
    public void setZyfcgrscope(String zyfcgrscope) {
        this.zyfcgrscope = zyfcgrscope;
    }

    /**
     * 获取服务时间
     *
     * @return zyfcgrfwtime - 服务时间
     */
    public Date getZyfcgrfwtime() {
        return zyfcgrfwtime;
    }

    /**
     * 设置服务时间
     *
     * @param zyfcgrfwtime 服务时间
     */
    public void setZyfcgrfwtime(Date zyfcgrfwtime) {
        this.zyfcgrfwtime = zyfcgrfwtime;
    }

    /**
     * 获取修改状态时间
     *
     * @return zyfcgropttime - 修改状态时间
     */
    public Date getZyfcgropttime() {
        return zyfcgropttime;
    }

    /**
     * 设置修改状态时间
     *
     * @param zyfcgropttime 修改状态时间
     */
    public void setZyfcgropttime(Date zyfcgropttime) {
        this.zyfcgropttime = zyfcgropttime;
    }

    /**
     * 获取状态:0-编辑;1-送审;2-审核;3-发布.
     *
     * @return zyfcgrstate - 状态:0-编辑;1-送审;2-审核;3-发布.
     */
    public Integer getZyfcgrstate() {
        return zyfcgrstate;
    }

    /**
     * 设置状态:0-编辑;1-送审;2-审核;3-发布.
     *
     * @param zyfcgrstate 状态:0-编辑;1-送审;2-审核;3-发布.
     */
    public void setZyfcgrstate(Integer zyfcgrstate) {
        this.zyfcgrstate = zyfcgrstate;
    }

    /**
     * 获取事迹材料
     *
     * @return zyfcgrcontent - 事迹材料
     */
    public String getZyfcgrcontent() {
        return zyfcgrcontent;
    }

    /**
     * 设置事迹材料
     *
     * @param zyfcgrcontent 事迹材料
     */
    public void setZyfcgrcontent(String zyfcgrcontent) {
        this.zyfcgrcontent = zyfcgrcontent;
    }
}