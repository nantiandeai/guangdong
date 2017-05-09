package com.creatoo.hn.services.wap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhDecprojectMapper;
import com.creatoo.hn.mapper.WhSuccessorMapper;
import com.creatoo.hn.mapper.WhZxColinfoMapper;
import com.creatoo.hn.mapper.WhZyhdMapper;
import com.creatoo.hn.model.WhZxColinfo;
import com.creatoo.hn.utils.WhConstance;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class WechatNewsService {

	@Autowired
	public WhZxColinfoMapper zxcolinfoMapper;

	@Autowired
	WhSuccessorMapper whSuccessorMapper;
	@Autowired
	WhDecprojectMapper whDecprojectMapper;
	@Autowired
	WhZyhdMapper whzyhdMapper;

	/**
	 * 分页查询资讯信息
	 * 
	 * @param param
	 *            分页参数
	 * @param type
	 *            栏目类型
	 * @return 分页信息
	 * @throws Exception
	 */
	public Map<String, Object> paggingColinfo(Map<String, Object> param) throws Exception {
		// 分页信息
		int page = 1;
		int rows = 10;
		if (param != null && param.get("index") != null && param.get("size") != null) {
			page = Integer.parseInt((String) param.get("index"));
			rows = Integer.parseInt((String) param.get("size"));
		}

		// 带条件的分页查询
		PageHelper.startPage(page, rows);
		List<WhZxColinfo> list = this.zxcolinfoMapper.getZxColInfo(param);

		// 取分页信息
		PageInfo<WhZxColinfo> pageInfo = new PageInfo<WhZxColinfo>(list);

		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> listMap = new HashMap<String, Object>();
		Map<String, Object> pager = new HashMap<String, Object>();
		pager.put("index", pageInfo.getPageNum()) ;
		pager.put("total", pageInfo.getTotal());
		pager.put("count", pageInfo.getPageSize());
		pager.put("size", pageInfo.getSize());
		listMap.put("list", pageInfo.getList());
		rtnMap.put("data", listMap) ;
		rtnMap.put("pager", pager) ;
		return rtnMap;
	};

	public List<Map> showZxDetail(Map<String, Object> param) {
		List<Map> list = this.zxcolinfoMapper.showZxDetail(param);
//		Map<String, Object> rtnMap = new HashMap<String, Object>();
//		Map<String, Object> listMap = new HashMap<String, Object>();
//		listMap.put("list", list);
//		rtnMap.put("data", listMap) ;
		return list;
	}

	public Map<String, Object> showChuangcheng(Map<String, Object> param) {
		// 分页信息
		int page = 1;
		int rows = 10;
		if (param != null && param.get("index") != null && param.get("size") != null) {
			page = Integer.parseInt((String) param.get("index"));
			rows = Integer.parseInt((String) param.get("size"));
		}

		// 带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.whSuccessorMapper.showChuancheng(param);
		// 取分页信息
		PageInfo<Map> pageInfo = new PageInfo<Map>(list);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> listMap = new HashMap<String, Object>();
		Map<String, Object> pager = new HashMap<String, Object>();
		pager.put("index", pageInfo.getPageNum()) ;
		pager.put("total", pageInfo.getTotal());
		pager.put("count", pageInfo.getPageSize());
		pager.put("size", pageInfo.getSize());
		listMap.put("list", pageInfo.getList());
		rtnMap.put("data", listMap) ;
		rtnMap.put("pager", pager) ;
		return rtnMap;
	}

	public Map<String, Object> showProject(Map<String, Object> param) {
		// 分页信息
		int page = 1;
		int rows = 10;
		if (param != null && param.get("index") != null && param.get("size") != null) {
			page = Integer.parseInt((String) param.get("index"));
			rows = Integer.parseInt((String) param.get("size"));
		}

		// 带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.whDecprojectMapper.showProject(param);
		// 取分页信息
		PageInfo<Map> pageInfo = new PageInfo<Map>(list);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> pager = new HashMap<String, Object>();
		Map<String, Object> listMap = new HashMap<String, Object>();
		pager.put("index", pageInfo.getPageNum()) ;
		pager.put("total", pageInfo.getTotal());
		pager.put("count", pageInfo.getPageSize());
		pager.put("size", pageInfo.getSize());
		listMap.put("list", pageInfo.getList());
		rtnMap.put("data", listMap);
		rtnMap.put("pager", pager) ;
		return rtnMap;
	}

	public Map<String,Object> showChuangchengDetail(Map<String, Object> param) {
		//List<Map> list = 
		return this.whSuccessorMapper.showChuanchengDetail(param);
		//Map<String, Object> rtnMap = new HashMap<String, Object>();
		//<String, Object> listMap = new HashMap<String, Object>();
		//listMap.put("list", list);
		//rtnMap.put("data", listMap) ;
		//return list;
	}

	public Map<String,Object> showProjectDetail(Map<String, Object> param) {
		return this.whDecprojectMapper.showProjectDetail(param);
//		Map<String, Object> rtnMap = new HashMap<String, Object>();
//		Map<String, Object> listMap = new HashMap<String, Object>();
//		listMap.put("list", list);
//		rtnMap.put("data", listMap) ;
//		return list;
	}

	/**
	 * 查询志愿活动
	 * @param param
	 * @return
	 */
	public Map<String, Object> showzyhd(Map<String, Object> param) {

		// 分页信息
		int page = 1;
		int rows = 10;
		if (param != null && param.get("index") != null && param.get("size") != null) {
			page = Integer.parseInt((String) param.get("index"));
			rows = Integer.parseInt((String) param.get("size"));
		}

		// 带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = new ArrayList<Map>() ;
		list = this.whzyhdMapper.showzyhd(param);
		// 取分页信息
		PageInfo<Map> pageInfo = new PageInfo<Map>(list);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> pager = new HashMap<String, Object>();
		Map<String, Object> listItem = new HashMap<String, Object>();
		pager.put("index", pageInfo.getPageNum()) ;
		pager.put("total", pageInfo.getTotal());
		pager.put("size", pageInfo.getSize());
		pager.put("count", pageInfo.getPageSize());
		rtnMap.put("pager", pager);
		listItem.put("list", pageInfo.getList());
		rtnMap.put("data", listItem) ;
		return rtnMap;
	}

	public Map<String, Object> showzyhdDetail(Map<String, Object> param) {
		//List<Map> list = 
		Map<String, Object> map = this.whzyhdMapper.showzyhdDetail(param);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("data", map) ;
		return rtnMap ;
//		Map<String, Object> rtnMap = new HashMap<String, Object>();
//		Map<String, Object> listMap = new HashMap<String, Object>();
//		listMap.put("list", list);
//		rtnMap.put("data", listMap) ;
//		return rtnMap;
	}
	
	/**
	 * 查询项目
	 * @param param
	 * @return
	 */
	public Map<String, Object> showzyfcProject(Map<String, Object> param) {

		// 分页信息
		int page = 1;
		int rows = 10;
		if (param != null && param.get("index") != null && param.get("size") != null) {
			page = Integer.parseInt((String) param.get("index"));
			rows = Integer.parseInt((String) param.get("size"));
		}

		// 带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.whzyhdMapper.showzyfcProject(param);
		// 取分页信息
		PageInfo<Map> pageInfo = new PageInfo<Map>(list);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> pager = new HashMap<String, Object>();
		Map<String, Object> listItem = new HashMap<String, Object>();
		pager.put("index", pageInfo.getPageNum()) ;
		pager.put("total", pageInfo.getTotal());
		pager.put("size", pageInfo.getSize());
		pager.put("count", pageInfo.getPageSize());
		rtnMap.put("pager", pager);
		listItem.put("list", pageInfo.getList());
		rtnMap.put("data", listItem) ;
		return rtnMap;
	}
	
	/**
	 * 查詢項目詳情
	 * @param param
	 * @return
	 */
	public Map<String, Object> showzyfcProjectDetail(Map<String, Object> param) {
		//List<Map> list = 
		Map<String, Object> map = this.whzyhdMapper.showzyfcProjectDetail(param);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
//		Map<String, Object> listMap = new HashMap<String, Object>();
//		listMap.put("list", list);
		rtnMap.put("data", map) ;
		return rtnMap;
	}
	/**
	 * 個人風采分頁查詢
	 * @param param
	 * @return
	 */
	public Map<String, Object> showzyfcPerson(Map<String, Object> param) {

		// 分页信息
		int page = 1;
		int rows = 10;
		if (param != null && param.get("index") != null && param.get("size") != null) {
			page = Integer.parseInt((String) param.get("index"));
			rows = Integer.parseInt((String) param.get("size"));
		}

		// 带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.whzyhdMapper.showzyfcPerson(param);
		// 取分页信息
		PageInfo<Map> pageInfo = new PageInfo<Map>(list);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> pager = new HashMap<String, Object>();
		Map<String, Object> listItem = new HashMap<String, Object>();
		pager.put("index", pageInfo.getPageNum()) ;
		pager.put("total", pageInfo.getTotal());
		pager.put("size", pageInfo.getSize());
		pager.put("count", pageInfo.getPageSize());
		rtnMap.put("pager", pager);
		listItem.put("list", pageInfo.getList());
		rtnMap.put("data", listItem) ;
		return rtnMap;
	}
	
	/**
	 * 查詢个人风采詳情
	 * @param param
	 * @return
	 */
	public Map<String, Object> showzyfcPersonDetail(Map<String, Object> param) {
		Map<String, Object> map = this.whzyhdMapper.showzyfcPersonDetail(param);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("data", map) ;
		return rtnMap ;
//		Map<String, Object> rtnMap = new HashMap<String, Object>();
//		Map<String, Object> listMap = new HashMap<String, Object>();
//		listMap.put("list", list);
//		rtnMap.put("data", listMap) ;
//		return rtnMap;
	}
	/**
	 * 查询优秀组织
	 * @param param
	 * @return
	 */
	public Map<String, Object> showzyfczuzhi(Map<String, Object> param) {

		// 分页信息
		int page = 1;
		int rows = 10;
		if (param != null && param.get("index") != null && param.get("size") != null) {
			page = Integer.parseInt((String) param.get("index"));
			rows = Integer.parseInt((String) param.get("size"));
		}

		// 带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.whzyhdMapper.showzyfczuzhi(param);
		// 取分页信息
		PageInfo<Map> pageInfo = new PageInfo<Map>(list);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> pager = new HashMap<String, Object>();
		Map<String, Object> listItem = new HashMap<String, Object>();
		pager.put("index", pageInfo.getPageNum()) ;
		pager.put("total", pageInfo.getTotal());
		pager.put("size", pageInfo.getSize());
		pager.put("count", pageInfo.getPageSize());
		rtnMap.put("pager", pager);
		listItem.put("list", pageInfo.getList());
		rtnMap.put("data", listItem) ;
		return rtnMap;
	}
	
	/**
	 * 查詢优秀组织詳情
	 * @param param
	 * @return
	 */
	public Map<String, Object> showzyfczuzhiDetail(Map<String, Object> param) {
		//List<Map> list = 
		Map<String, Object> map = this.whzyhdMapper.showzyfczuzhiDetail(param);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
//		Map<String, Object> listMap = new HashMap<String, Object>();
//		listMap.put("list", list);
		rtnMap.put("data", map) ;
		return rtnMap;
	}
}
