package com.creatoo.hn.services.admin.adve;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhFetchFromMapper;
import com.creatoo.hn.model.WhFetchFrom;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class OperateService {

	@Autowired
	private WhFetchFromMapper whFetchFromMapper;

	/**
	 * 带分页查询
	 * 
	 * @param param
	 * @return
	 */
	public Map<String, Object> seleadv(Map<String, Object> param) {
		// 分页信息
		int page = Integer.parseInt((String) param.get("page"));
		int rows = Integer.parseInt((String) param.get("rows"));
		PageHelper.startPage(page, rows);
		Example example = new Example(WhFetchFrom.class);
		Criteria criteria = example.createCriteria();

		// 标题
		if (param.containsKey("fromname") && param.get("fromname") != null) {
			String fromname = (String) param.get("fromname");
			if (!"".equals(fromname.trim())) {
				criteria.andLike("fromname", "%" + fromname.trim() + "%");
			}
		}
		// 状态
		if (param.containsKey("fromstate") && param.get("fromstate") != null) {
			String fromstate = (String) param.get("fromstate");
			if (!"".equals(fromstate.trim())) {
				criteria.andEqualTo("fromstate", fromstate);
			}
		}

		List<WhFetchFrom> list = this.whFetchFromMapper.selectByExample(example);

		PageInfo<WhFetchFrom> pageInfo = new PageInfo<WhFetchFrom>(list);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("total", pageInfo.getTotal());
		rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}

	/**
	 * 添加
	 * 
	 * @param whf
	 */
	public void save(WhFetchFrom whf) {
		this.whFetchFromMapper.insertSelective(whf);
	}

	/**
	 * 修改
	 * 
	 * @param whf
	 */
	public void upoper(WhFetchFrom whf) {
		this.whFetchFromMapper.updateByPrimaryKeySelective(whf);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param fromid
	 */
	public void deloper(String fromid) {
		this.whFetchFromMapper.deleteByPrimaryKey(fromid);
	}

	/**
	 * 改变状态
	 * 
	 * @param whf
	 */
	public void uptype(WhFetchFrom whf) {
		this.whFetchFromMapper.updateByPrimaryKeySelective(whf);
	}

}
