package com.creatoo.hn.mapper;

import com.creatoo.hn.model.WhZyhd;

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

public interface WhZyhdMapper extends Mapper<WhZyhd> {

	/**
	 * 分页查询
	 * @param param
	 * @return
	 */
	List<Map> findzyhd(Map<String, Object> param);
	
	List<Map> showzyhd(Map<String,Object> param) ;
	
	Map<String,Object> showzyhdDetail(Map<String,Object> param) ;
	
	List<Map> showzyfcProject(Map<String,Object> param) ;
	
	Map<String,Object> showzyfcProjectDetail(Map<String,Object> param) ;
	
	List<Map> showzyfcPerson(Map<String,Object> param) ;
	
	Map<String,Object> showzyfcPersonDetail(Map<String,Object> param) ;
	
	List<Map> showzyfczuzhi(Map<String,Object> param) ;
	
	Map<String,Object> showzyfczuzhiDetail(Map<String,Object> param) ;
}