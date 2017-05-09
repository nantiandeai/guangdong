package com.creatoo.hn.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface IndexPageMapper {

	/** 查询首页广告图，可传入场馆ID
	 * @param venueid
	 * @return
	 */
	public List<Object> selectBannerList(@Param("venueid")String venueid);
	
	/** 查找首页活动页面配置项
	 * @return
	 */
	public List<Object> selectWhhdList(@Param("venueid")String venueid);
	
	/** 查找首页非遗配置项
	 * @param venueid
	 * @return
	 */
	public List<Object> selectFeiyiList(@Param("venueid")String venueid);
	
	/** 查找首页培训数据，需要处理记录数限制
	 * @param venueid
	 * @return
	 */
	public List<Object> selectPxyzList(@Param("venueid")String venueid);
	
	/**
	 * 首页 文化联盟
	 * @return
	 */
	public List<Object> selectVenue(); 
}
