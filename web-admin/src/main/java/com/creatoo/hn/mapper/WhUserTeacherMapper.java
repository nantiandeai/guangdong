package com.creatoo.hn.mapper;

import java.util.List;
import java.util.Map;

import com.creatoo.hn.model.WhUserTeacher;

import tk.mybatis.mapper.common.Mapper;

public interface WhUserTeacherMapper extends Mapper<WhUserTeacher> {

	/**
	 * 分页查询培训老师
	 * @param param
	 * @return
	 */
	List<Map> selTeacher(Map<String, Object> param);
}