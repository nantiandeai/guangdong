package com.creatoo.hn.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface VenuesMapper {

	/**
	 * 查询指定天的场馆时段集
	 * @param venid
	 * @param day
	 * @return
	 */
	public List<Object> selectVenueTimes4Day(@Param("venid")String venid, @Param("day")Date day);

	/**
	 * 查询指定天的场馆已审核过的预定信息
	 * @param venid
	 * @param day
     * @return
     */
	public List<Object> selectVenueBked4Day(@Param("venid")String venid, @Param("day")Date day);

	/**
	 * 查询指定天的场馆指定用户的预定未审核信息
	 * @param venid
	 * @param day
	 * @param uid
     * @return
     */
	public List<Object> selectVenueBked4DayUserBide(@Param("venid")String venid, @Param("day")Date day, @Param("uid")String uid);

	/**
	 * 后台内定查询指定场馆的日期信息
	 * @param venid
	 * @return
     */
	public List<Object> selectVenueDateList(@Param("venid")String venid);

	/**
	 * 后台内定查询指定日期的时段信息
	 * @param vendid
	 * @return
     */
	public List<Object> selectVenueTimeList(@Param("vendid")String vendid);

	/**
	 * 后台内定查询指定日期的时段已订信息，用于参考控制
	 * @param vendid
	 * @return
     */
	public List<Object> selectVenueTimeBkedList(@Param("vendid")String vendid);




	/** 查指定ID的场馆
	 * @param id
	 * @return
	 */
	public Object findVenue4Id(@Param("id") String id); 
	
	/** 查询场馆时段
	 * @param venid
	 * @return
	 */
	public List<Object> selectVenueTime(@Param("venid") String venid);
	
	/** 查询场馆指定日期段的预订信息
	 * @param venid
	 * @param bday
	 * @param eday
	 * @return
	 */
	public List<Object> selectVenueBked(@Param("venid")String venid, 
			@Param("bday")Date bday,@Param("eday")Date eday);
	
	
	/** 查询场馆 某用户指定日期时段时未审核通过的预定
	 * @param venid
	 * @param uid
	 * @param bday
	 * @param eday
	 * @return
	 */
	public List<Object> selectVenueBkedNotcheck4User(@Param("venid")String venid, @Param("uid")String uid,
			@Param("bday")Date bday,@Param("eday")Date eday);
	
	/** 查询场馆资源图片集
	 * @param venid
	 * @return
	 */
	public List<Object> selectVenueEntSrc(@Param("venid")String venid);
	
	
	/** 查询场馆列表，调用时注意限制返回记录数
	 * @return
	 */
	public List<Object> selectVenueList(@Param("notid")String notid);

}
