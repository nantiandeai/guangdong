package com.creatoo.hn.mapper;

import com.creatoo.hn.model.WhBrand;

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

public interface WhBrandMapper extends Mapper<WhBrand> {

	List<Map> selBrand(Map<String, Object> param);

	List<Map> selBrandPage(Map<String, Object> param);

}