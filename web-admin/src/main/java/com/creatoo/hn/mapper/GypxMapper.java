package com.creatoo.hn.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface GypxMapper {
	
	
	/** 查询公益培训页广告图
	 * @return
	 */
	public List<?> selectGypxAdv();

	/**查询公益培训模块首页的资讯动态信息配置
	 * @return
	 */
	public List<?> selectGypxZx();

	/** 查询公益培训首页列表
	 * @param params
	 * @return
	 */
	public List<?> selectHpList();
	
	/** 查询公益培训列表页数据
	 * @param params
	 * @return
	 */
	public List<?> selectGypxList(Map<String,Object> params);
	
	/**
	 * 提取指定ID批次的公益培训
	 * @param id
	 * @return
	 */
	public Map<String,Object> findGypxInfo(String id);
	
	/** 查询培训课程时间段相关的日期
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<?> selectTradate4Month(@Param("begin") String begin, @Param("end")String end);
	
	/** 按指定课程日期查询相关的培训信息集
	 * @param day
	 * @return
	 */
	public List<?> selectGypx4Day(@Param("day")String day);
	
	/** 查询指定培训ID相关的课程记录
	 * @param traitmid
	 * @return
	 */
	public List<?> selectPXTimes(@Param("traitmid")String traitmid);
	
	/**
	 * 查询培训报名
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<HashMap> selectEnroll(Map<String,Object> params);

	/**
	 * 查询已通过的报名数量
	 * @param enrtraid
	 * @return
	 */
	public int selEnrollCount(String enrtraid);
}
