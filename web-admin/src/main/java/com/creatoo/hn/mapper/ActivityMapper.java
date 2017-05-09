package com.creatoo.hn.mapper;

import java.util.List;
import java.util.Map;

import com.creatoo.hn.model.WhDecproject;
import com.creatoo.hn.model.WhSuccessor;
import org.apache.ibatis.annotations.Param;

/**
 * 复杂SQL查询映射类
 * @author rbg
 *
 */
@SuppressWarnings("all")
public interface ActivityMapper {
	
	/** 广东省馆品牌活动查询
	 * @param param
	 * @return
	 */
	public List<Object> selectBrandList(Map<String,Object> param);

	/**
	 * 查询名录名称
	 * @return
     */
	public List<WhDecproject> selMingLu();

	/**
	 * 查询活动
	 * @param param
	 * @return
	 */
	public List<Map> selectActList(Map<String,Object> param);
	
	/**
	 * 查询培训模板
	 * @param param
	 * @return
	 */
	public List<Map> selectTraintpl(Map<String,Object> param);
	
	/**
	 * 查询培训审核与发布列表
	 * @param param
	 * @return
	 */
	public List<Map> selectTrainitm(Map<String,Object> param);
	/**
	 * 查询活动表与活动批次表
	 * @param param
	 * @return
	 */
	public List<Map> selectActitm(Map<String,Object> param);
	/**
	 * 查询活动标题
	 * @return
	 */
	public List<Map> selectTitle();
	/**
	 * 查询活动批次标题
	 * @return
	 */
	public List<Map> selectitmTitle();
	/**
	 * 通过标题查询ID
	 * @return
	 */
	public String selTraid(String tratitle);
	
	/**
	 * Activity 表查询
	 * @param param
	 * @return
	 */
	public List<Map> selectActivity(Map<String,Object> param);
	
	/**
	 * Activitytpl 表查询
	 * @param param
	 * @return
	 */
	public List<Map> selectActivitytpl(Map<String,Object> param);
	
	/**
	 * 前台  多表查询 页面 eventlist.jsp
	 * @param param
	 * @return
	 */
	public List<Map> selecteventList(Map<String,Object> param);
	
	/**
	 * 前台 往期回顾  同类 过时 查询 
	 * @param param
	 * @return
	 */
	public List<Map> selectwqAct(Map<String,Object> param);
	/**
	 * 前台 往期回顾  专题 过时 查询 
	 * @param param
	 * @return
	 */
	public List<Map> selectwqActtype(Map<String,Object> param);
	
	/**
	 * 后台 活动报名  关联查询4个表 
	 * @param param
	 * @return
	 */
	public List<Map> selectActivitybm(Map<String,Object> param);
	
	/***
	 * 个人中心 我的报名查询
	 * @param param
	 * @return
	 */
	public List<Map> selectMyeventenroll(Map<String,Object> param);
	
	/**
	 * 活动品牌加载
	 * @param param
	 * @return
	 */
	public List<Map> loadPP(Map<String,Object> param);
	
	/**
	 * 活动 查找 首页 地址
	 * @return
	 */
	public List<Map> selectactadress();
	
	/**
	 * 活动 查找品牌 首页 类型
	 * @return
	 */
	public List<Map> selectpptype();
	
	
	/**
	 * dg 文化活动 list
	 * @param param
	 * @return
	 */
	public List<Map> selectlistAct(Map<String,Object> param);
	
	/**
	 * dg 品牌活动详情
	 * @param param
	 * @return
	 */
	public List<Map> selectppDetail(Map<String,Object> param);
	

	/**
	 * dg 品牌活动详情 往期回顾
	 * @param param
	 * @return
	 */
	public List<Map> selectppwqact(Map<String,Object> param);
	
	/**
	 * 活动审核 是否有场次时间
	 * @param param
	 * @return
	 */
	public List<Map> selectcountActitm(Map<String,Object> param);
	
	
	
	/***************名录表/传承人*****************/
	 /**
	  * 后台维护名录查询
	  * @param param
	  * @return
	  */
	public List<Map> selectminglu(Map<String,Object> param);
	
	/**
	 * 后台维护传承人
	 * @param param
	 * @return
	 */
	public List<Map> selectSuccessor(Map<String,Object> param);
	
	 /**
	  * 前台名录列表查询
	  * @param param
	  * @return
	  */
	public List<Map> dataloadminglu(Map<String,Object> param);
	
	 /**
	  * 前台传承人列表查询
	  * @param param
	  * @return
	  */
	public List<Map> dataloadsuccessor(Map<String,Object> param);
	
	/**
	  * 前台传承人列表查询
	  * @param param
	  * @return
	  */
	public List<Map> suorDetail(@Param("suorid")String suorid);
	
	/**
	  * 查找传承人的相关项目
	  * @param param
	  * @return
	  */
	public List<Map> successortopro(Map<String,Object> param);

	/**
	 * 查找传承人
	 * @param suorid
     * @return
     */
	public List<WhSuccessor> successor(@Param("suorid")String suorid);

	
	/**
	  * 前台传承人列表查询
	  * @param param
	  * @return
	  */
	public List<Map> tjsuor(@Param("suorid")String suorid);
	
	/**----------微信端接口---------*/
	public List<Map> selectMywxevent(Map<String,Object> param);
	
	public List<Map> selectfavact(@Param("actvid")String actvid);
	
	public List<Map> selectfavtra(@Param("traid")String traid);
	
	public List<Map> selectfaven(@Param("venid")String venid);
	
	public List<Map> selectfaart(@Param("exhid")String exhid);
	
	public List<Map> selectfavppact();
	
	public List<Map> selectfavact(Map<String,Object> param);
}
