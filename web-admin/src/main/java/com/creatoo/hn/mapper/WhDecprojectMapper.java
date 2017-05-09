package com.creatoo.hn.mapper;

import com.creatoo.hn.model.WhDecproject;

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

public interface WhDecprojectMapper extends Mapper<WhDecproject> {
	
	List<Map> showProject(Map<String,Object> params) ;
	
	Map<String,Object> showProjectDetail(Map<String,Object> params) ;
	
}