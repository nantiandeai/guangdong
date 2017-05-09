package com.creatoo.hn.mapper;

import com.creatoo.hn.model.WhMagepage;
import com.creatoo.hn.model.WhMagezine;

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

public interface WhMagezineMapper extends Mapper<WhMagezine> {

	/**
	 * 查找所有的电子杂志
	 * @param param
	 * @return
	 */
	List<Map> selmagezine(Map<String, Object> param);
	/**
	 * 查询电子杂志页码信息
	 * @param param
	 * @return
	 */
	List<Map> selMagezinePage(Map<String, Object> param);
	
	/**
	 * 查询电子杂志
	 * @return
	 */
	List<WhMagezine> selDetail();
	
	/**
	 * 查询电子杂志ya
	 * @return
	 */
	List<WhMagepage> selDetailIdx(String pagemageid);
	
	/**
	 * 查询页码
	 * @param pagemageid
	 */
	List<WhMagepage> selMagePage(String pagemageid);
}