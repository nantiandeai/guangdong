package com.creatoo.hn.mapper;

import java.util.List;
import java.util.Map;

import com.creatoo.hn.model.WhVenuebked;

import tk.mybatis.mapper.common.Mapper;

public interface WhVenuebkedMapper extends Mapper<WhVenuebked> {
	List<Map> findVenueOrder(Map<String, Object> param);
}