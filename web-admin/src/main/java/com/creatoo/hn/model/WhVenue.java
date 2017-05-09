package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "wh_venue")
public class WhVenue {
    /**
     * 场馆标识
     */
    @Id
    private String venid;

    /**
     * 场馆名称
     */
    private String venname;

    /**
     * 场馆区域
     */
    private String venarea;

    /**
     * 场馆类型
     */
    private String ventype;

    /**
     * 0-不能预定; 1-可预定
     */
    private Integer vencanbk;

    /**
     * 场馆联系方式
     */
    private String vencontactnum;

    /**
     * 场馆联系人
     */
    private String vencontact;

    /**
     * 场馆用途
     */
    private String venscope;

    /**
     * 场馆地址
     */
    private String venaddr;

    /**
     * 场馆地址X坐标
     */
    private Integer venaddrx;

    /**
     * 场馆地址Y坐标
     */
    private Integer venaddry;

    /**
     * 场馆地址附加字段1
     */
    private String venaddrm1;

    /**
     * 场馆地址附加字段2
     */
    private String venaddrm2;

    /**
     * 场馆申请条件
     */
    private String vencondition;

    /**
     * 场馆状态.0-初始;1-送审;2-审核;3-发布;
     */
    private Integer venstate;

    private Date venopttime;

    /**
     * 场馆描述
     */
    private String venintroduce1;

    /**
     * 设施描述
     */
    private String venintroduce2;
    
    /**
     * 场馆图片
     */
    private String venpic;
    
    /**
     * 简介
     */
    private String venintro;
    
    /**
     * 场馆关键字
     */
    private String venkeys;
    /**
     * 标签
     */
    private String ventags;
    /**
     * 是否收费
     */
    private Integer ismoney;
    
    /**
     * 获取场馆标识
     *
     * @return venid - 场馆标识
     */
    public String getVenid() {
        return venid;
    }

    /**
     * 设置场馆标识
     *
     * @param venid 场馆标识
     */
    public void setVenid(String venid) {
        this.venid = venid;
    }

    /**
     * 获取场馆名称
     *
     * @return venname - 场馆名称
     */
    public String getVenname() {
        return venname;
    }

    /**
     * 设置场馆名称
     *
     * @param venname 场馆名称
     */
    public void setVenname(String venname) {
        this.venname = venname;
    }

    /**
     * 获取场馆区域
     *
     * @return venarea - 场馆区域
     */
    public String getVenarea() {
        return venarea;
    }

    /**
     * 设置场馆区域
     *
     * @param venarea 场馆区域
     */
    public void setVenarea(String venarea) {
        this.venarea = venarea;
    }

    /**
     * 获取场馆类型
     *
     * @return ventype - 场馆类型
     */
    public String getVentype() {
        return ventype;
    }

    /**
     * 设置场馆类型
     *
     * @param ventype 场馆类型
     */
    public void setVentype(String ventype) {
        this.ventype = ventype;
    }

    /**
     * 获取0-不能预定; 1-可预定
     *
     * @return vencanbk - 0-不能预定; 1-可预定
     */
    public Integer getVencanbk() {
        return vencanbk;
    }

    /**
     * 设置0-不能预定; 1-可预定
     *
     * @param vencanbk 0-不能预定; 1-可预定
     */
    public void setVencanbk(Integer vencanbk) {
        this.vencanbk = vencanbk;
    }

    /**
     * 获取场馆联系方式
     *
     * @return vencontactnum - 场馆联系方式
     */
    public String getVencontactnum() {
        return vencontactnum;
    }

    /**
     * 设置场馆联系方式
     *
     * @param vencontactnum 场馆联系方式
     */
    public void setVencontactnum(String vencontactnum) {
        this.vencontactnum = vencontactnum;
    }

    /**
     * 获取场馆联系人
     *
     * @return vencontact - 场馆联系人
     */
    public String getVencontact() {
        return vencontact;
    }

    /**
     * 设置场馆联系人
     *
     * @param vencontact 场馆联系人
     */
    public void setVencontact(String vencontact) {
        this.vencontact = vencontact;
    }

    /**
     * 获取场馆用途
     *
     * @return venscope - 场馆用途
     */
    public String getVenscope() {
        return venscope;
    }

    /**
     * 设置场馆用途
     *
     * @param venscope 场馆用途
     */
    public void setVenscope(String venscope) {
        this.venscope = venscope;
    }

    /**
     * 获取场馆地址
     *
     * @return venaddr - 场馆地址
     */
    public String getVenaddr() {
        return venaddr;
    }

    /**
     * 设置场馆地址
     *
     * @param venaddr 场馆地址
     */
    public void setVenaddr(String venaddr) {
        this.venaddr = venaddr;
    }

    /**
     * 获取场馆地址X坐标
     *
     * @return venaddrx - 场馆地址X坐标
     */
    public Integer getVenaddrx() {
        return venaddrx;
    }

    /**
     * 设置场馆地址X坐标
     *
     * @param venaddrx 场馆地址X坐标
     */
    public void setVenaddrx(Integer venaddrx) {
        this.venaddrx = venaddrx;
    }

    /**
     * 获取场馆地址Y坐标
     *
     * @return venaddry - 场馆地址Y坐标
     */
    public Integer getVenaddry() {
        return venaddry;
    }

    /**
     * 设置场馆地址Y坐标
     *
     * @param venaddry 场馆地址Y坐标
     */
    public void setVenaddry(Integer venaddry) {
        this.venaddry = venaddry;
    }

    /**
     * 获取场馆地址附加字段1
     *
     * @return venaddrm1 - 场馆地址附加字段1
     */
    public String getVenaddrm1() {
        return venaddrm1;
    }

    /**
     * 设置场馆地址附加字段1
     *
     * @param venaddrm1 场馆地址附加字段1
     */
    public void setVenaddrm1(String venaddrm1) {
        this.venaddrm1 = venaddrm1;
    }

    /**
     * 获取场馆地址附加字段2
     *
     * @return venaddrm2 - 场馆地址附加字段2
     */
    public String getVenaddrm2() {
        return venaddrm2;
    }

    /**
     * 设置场馆地址附加字段2
     *
     * @param venaddrm2 场馆地址附加字段2
     */
    public void setVenaddrm2(String venaddrm2) {
        this.venaddrm2 = venaddrm2;
    }

    /**
     * 获取场馆申请条件
     *
     * @return vencondition - 场馆申请条件
     */
    public String getVencondition() {
        return vencondition;
    }

    /**
     * 设置场馆申请条件
     *
     * @param vencondition 场馆申请条件
     */
    public void setVencondition(String vencondition) {
        this.vencondition = vencondition;
    }

    /**
     * 获取场馆状态.0-初始;1-送审;2-审核;3-发布;
     *
     * @return venstate - 场馆状态.0-初始;1-送审;2-审核;3-发布;
     */
    public Integer getVenstate() {
        return venstate;
    }

    /**
     * 设置场馆状态.0-初始;1-送审;2-审核;3-发布;
     *
     * @param venstate 场馆状态.0-初始;1-送审;2-审核;3-发布;
     */
    public void setVenstate(Integer venstate) {
        this.venstate = venstate;
    }

    /**
     * @return venopttime
     */
    public Date getVenopttime() {
        return venopttime;
    }

    /**
     * @param venopttime
     */
    public void setVenopttime(Date venopttime) {
        this.venopttime = venopttime;
    }

    /**
     * 获取场馆描述
     *
     * @return venintroduce1 - 场馆描述
     */
    public String getVenintroduce1() {
        return venintroduce1;
    }

    /**
     * 设置场馆描述
     *
     * @param venintroduce1 场馆描述
     */
    public void setVenintroduce1(String venintroduce1) {
        this.venintroduce1 = venintroduce1;
    }

    /**
     * 获取设施描述
     *
     * @return venintroduce2 - 设施描述
     */
    public String getVenintroduce2() {
        return venintroduce2;
    }

    /**
     * 设置设施描述
     *
     * @param venintroduce2 设施描述
     */
    public void setVenintroduce2(String venintroduce2) {
        this.venintroduce2 = venintroduce2;
    }

    /**
     * 获取场馆图片
     * @return
     */
	public String getVenpic() {
		return venpic;
	}

	/**
	 * 设置场馆图片
	 * @param venpic
	 */
	public void setVenpic(String venpic) {
		this.venpic = venpic;
	}

	public String getVenintro() {
		return venintro;
	}

	public void setVenintro(String venintro) {
		this.venintro = venintro;
	}

	public String getVenkeys() {
		return venkeys;
	}

	public void setVenkeys(String venkeys) {
		this.venkeys = venkeys;
	}

	public String getVentags() {
		return ventags;
	}

	public void setVentags(String ventags) {
		this.ventags = ventags;
	}

	public Integer getIsmoney() {
		return ismoney;
	}

	public void setIsmoney(Integer ismoney) {
		this.ismoney = ismoney;
	}

	
    
}