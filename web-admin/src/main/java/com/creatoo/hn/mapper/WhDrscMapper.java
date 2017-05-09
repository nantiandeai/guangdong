package com.creatoo.hn.mapper;

import com.creatoo.hn.model.WhDrsc;
import com.creatoo.hn.model.WhTrain;

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

public interface WhDrscMapper extends Mapper<WhDrsc> {

	/**
	 * 查询在线点播分页信息
	 * @param param
	 * @return
	 */
	List<Map> selvodList(Map<String, Object> param);

	/**
	 * 查询详情信息
	 * @param drscid
	 * @return
	 */
	WhDrsc selVodInfo(String drscid);

	/**
	 * 在线点播查询推荐课程
	 * @return
	 */
	List<Map<String, Object>> selNewVod(String drscid);
	
	/**
	 * 查询在线报名详情信息
	 * @param traid
	 * @return
	 */
	public WhTrain selectTrain(String traid);

	/**
	 * 查询在线报名详情推荐课程
	 * @return
	 */
	List<Map<String, Object>> selNewTrain(String traid);

	/**
	 * 首页查询师资信息
	 * @return
	 */
	List<Map<String, Object>> selTeacher();
}