package com.creatoo.hn.mapper;

import java.util.List;
import java.util.Map;

import com.creatoo.hn.model.WhTraintpl;
import tk.mybatis.mapper.common.Mapper;

public interface WhTraintplMapper extends Mapper<WhTraintpl> {

	List<Map> selectTraintpl(Map<String, Object> param);
}