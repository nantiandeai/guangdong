package com.creatoo.hn.mapper;

import java.util.List;
import java.util.Map;

import com.creatoo.hn.model.WhVenue;

import tk.mybatis.mapper.common.Mapper;

public interface WhVenueMapper extends Mapper<WhVenue> {

	/**
	 * 找到场馆
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> selVenue(Map<String, Object> param);

	/**
	 * 找到数段
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> selTime(Map<String, Object> param);
	
	/**
	 * 查询预定信息
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> seldestine(Map<String, Object> param);

	/**
	 * 前台场馆预定列表页查找场馆信息
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> selvenueList(Map<String, Object> param);

	/**
	 * 
	 * @param venid
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map> selTimeList(String venid);

	/**
	 * 查询日期是否被预定
	 * @param vendid
	 * @return
	 */
	int selCountDate(String vendid);

	/**
	 * 查询日期信息
	 * @param paramMap
	 * @return
	 */
	List<Map> selDate(Map<String, Object> paramMap);
	
	/**
	 * 接口查询场馆列表
	 * @param params
	 * @return
	 */
	List<Map> selVenueList(Map<String, Object> params);

	/**
	 * 接口查询场馆详情
	 * @param itemId
	 * @return
	 */
	List<Map> selWapVenueDetail(String itemId);

	/**
	 * 收藏数量查询
	 * @param id
	 * @param venid
	 * @return
	 */
	int selCollectionCount(Map<String, Object> params);

}