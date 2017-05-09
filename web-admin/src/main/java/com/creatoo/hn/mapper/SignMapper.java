package com.creatoo.hn.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SignMapper {

	/** 培训一人一项限制验证查询
	 * @param uid
	 * @param enrtype
	 * @param typid
	 * @return
	 */
	public int selectOneInOneType(@Param("uid") String uid, @Param("enrtype") Integer enrtype, @Param("typid")String typid);
	
	/** 按场次ID提取活动的相关信息供报名用
	 * @param itmid
	 * @return
	 */
	public List<?> selectEventInfoToSign(@Param("itmid") String itmid);
	
	/** 活动重复报名记数验证
	 * @param bmtype
	 * @param uid
	 * @param itmid
	 * @return
	 */
	public int selectEventBmSum4User(@Param("bmtype") Integer bmtype, @Param("uid") String uid, @Param("itmid") String itmid);
	
	/** 活动报名人数统计
	 * @param itmid
	 * @param shstate
	 * @return
	 */
	public Object selectEventBmCount(@Param("itmid")String itmid, @Param("shstate")String shstate);
	
	/** 培训是否收费查询
	 * @param traid
	 * @return
	 */
	public Map<String,Object> findTraenIsmoney(@Param("traid") String traid);
	
	/** 活动条目是否收费查询
	 * @param actvitmid
	 * @return
	 */
	public Map<String,Object> findActItmIsmoney(@Param("actvitmid") String actvitmid);
 }
