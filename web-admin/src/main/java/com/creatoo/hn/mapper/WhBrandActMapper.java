package com.creatoo.hn.mapper;

import com.creatoo.hn.model.WhBrandAct;

import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;

public interface WhBrandActMapper extends Mapper<WhBrandAct> {
	/**
	 * 查找品牌活动
	 * @param param
	 * @return
	 */
	List<Map> selBrandAct(Map<String, Object> param);
	/**
	 * 查找品牌活动中所有的活动
	 * @param braid
	 * @return
	 */
	List<Map> selBrandinfo(String braid);
	/**
	 * 查找活动资讯
	 * @param braid
	 * @return
	 */
	List<Map> selZX();
	
	List<Map> selectAct();
	
	List<Map> isPublish(String braid);
}