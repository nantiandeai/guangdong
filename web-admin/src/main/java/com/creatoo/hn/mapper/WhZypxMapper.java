package com.creatoo.hn.mapper;

import com.creatoo.hn.model.WhZypx;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.common.Mapper;

public interface WhZypxMapper extends Mapper<WhZypx> {

	/**
	 * 后台分页查询志愿培训信息
	 * @param param
	 * @return
	 */
	List<Map> selectZypx(Map<String, Object> param);
	
	/**
	 * 后台分页查询--先进个人信息
	 * @param param
	 * @return
	 */
	List<Map> selectPersonal(Map<String, Object> param);
}