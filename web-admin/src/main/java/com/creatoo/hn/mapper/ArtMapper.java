package com.creatoo.hn.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 前端艺术广场的复杂查询
 * @author wangxl
 *
 */
@SuppressWarnings("all")
public interface ArtMapper {
	
	/**
	 * 查询个人作品展
	 * @param param
	 * @return
	 */
	public List<Map> searchArtGrzpz(Map<String,Object> param);
	
	
	/**
	 * 查询精品文化展
	 * @param param
	 * @return
	 */
	public List<Map> searchArtJpwhz(Map<String,Object> param);
	
	/**
	 * 查询艺术团队
	 * @param param
	 * @return
	 */
	public List<Map> searchArtYstd(Map<String,Object> param);
	
	/**
	 * 根据ID数组(idArray)查询关键字
	 * @param param 条件param.idArray中ID数组
	 * @return
	 */
	public List<Map> searchArtKeys(Map<String,Object> param);
	
	/**
	 * 根据ID数组(idArray)查询标签
	 * @param param 条件param.idArray中ID数组
	 * @return
	 */
	public List<Map> searchArtTags(Map<String,Object> param);
	
	
	/**
	 * 查询我的报名
	 * @param param
	 * @return
	 */
	public List<Map> searchMyEnroll(Map<String,Object> param);
	
	/** 查询指定培训指定天数集合的数据
	 * @param traid
	 * @param days
	 * @return
	 */
	public List<Object> searchSkdjs(@Param("traid") String traid, @Param("days")List<String> days);
}
