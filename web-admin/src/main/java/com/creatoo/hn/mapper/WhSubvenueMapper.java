package com.creatoo.hn.mapper;

import java.util.List;
import java.util.Map;

import com.creatoo.hn.model.WhSubvenue;

import tk.mybatis.mapper.common.Mapper;

public interface WhSubvenueMapper extends Mapper<WhSubvenue> {

	/**
	 * 查询所有的子馆信息
	 * @param param
	 * @return
	 */
	List<Map> selSub(Map<String, Object> param);
}