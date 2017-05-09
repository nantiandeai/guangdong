package com.creatoo.hn.mapper;

import java.util.List;
import java.util.Map;

/**
 * 公共的复杂，需要自定义的SQL查询定义在这里
 * @author wangxl
 */
public interface CommonMapper {
	/**
	 * 系统全局搜索
	 * @param srchKey 搜索关键字
	 * @return 全局搜索内容
	 */
	public List<Map<String, Object>> searchGlobalContent(Map<String, Object> param);
}
