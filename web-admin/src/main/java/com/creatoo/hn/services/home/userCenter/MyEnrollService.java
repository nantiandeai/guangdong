package com.creatoo.hn.services.home.userCenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.creatoo.hn.mapper.WhgTraEnrolMapper;
import com.creatoo.hn.model.WhgTraEnrol;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.ArtMapper;
import com.creatoo.hn.mapper.WhTraenrMapper;
import com.creatoo.hn.model.WhTraenr;
import com.creatoo.hn.utils.UploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * 前端艺术广场服务类
 * @author wangxl
 *
 */
@Service
@SuppressWarnings("rawtypes")
public class MyEnrollService {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public ArtMapper artMapper; 
	
	@Autowired
	public WhTraenrMapper traenrMapper;

	@Autowired
	WhgTraEnrolMapper whgTraEnrolMapper;
	/**
	 * 查询我的报名
	 * @param param 条件和分页参数
	 * @return 分页对象
	 * @throws Exception
	 */
	public Map<String, Object> paggingEnroll(Map<String, Object> param)throws Exception{
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));

		//带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map<String,Object>> list = whgTraEnrolMapper.getTraEnrolListByUserId(param);

		// 取分页信息
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(list);
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	};

	/**
	 * 取消我的报名
	 * @param enrol
	 * @throws Exception
	 */
	public void delMyEnroll(WhgTraEnrol enrol)throws Exception{

		whgTraEnrolMapper.updateByPrimaryKeySelective(enrol);
	}
	
	public List<Object> searchSkdjs(String traid) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.DAY_OF_YEAR, 1);
		Date nextday = c.getTime();
		
		List<String> days = new ArrayList<String>();
		days.add(sdf.format(now));
		days.add(sdf.format(nextday));
		return this.artMapper.searchSkdjs(traid, days);
	}

	/**
	 * 删除我的报名
	 * @param id
     */
	public void delEnroll(String id) {
		whgTraEnrolMapper.deleteByPrimaryKey(id);
	}
}
