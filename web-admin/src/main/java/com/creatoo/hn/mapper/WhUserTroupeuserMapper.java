package com.creatoo.hn.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.creatoo.hn.model.WhUserTroupeuser;
import tk.mybatis.mapper.common.Mapper;

public interface WhUserTroupeuserMapper extends Mapper<WhUserTroupeuser> {
	public List<Map> selTroupeUser(Map<String,Object> param);
	
	/**
	 * user 表查询
	 * @param param
	 * @return
	 */
	public List<Map> selectUser(Map<String,Object> param);
}	