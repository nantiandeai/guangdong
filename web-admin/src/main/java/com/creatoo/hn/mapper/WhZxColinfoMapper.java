package com.creatoo.hn.mapper;

import com.creatoo.hn.model.WhZxColinfo;

import java.util.List;
import java.util.Map;
import tk.mybatis.mapper.common.Mapper;

public interface WhZxColinfoMapper extends Mapper<WhZxColinfo> {
	
	public List<WhZxColinfo> getZxColInfo(Map<String, Object> params) ;
	
	public List<Map> showZxDetail(Map<String, Object> params) ;

	/**
	 * 查询置顶
	 * @return
     */
	WhZxColinfo findIsTop(String clnftype);
	
}