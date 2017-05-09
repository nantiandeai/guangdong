package com.creatoo.hn.services.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhConfigMapper;
import com.creatoo.hn.model.WhConfig;
import com.creatoo.hn.model.WhMenu;
import com.creatoo.hn.services.comm.CommService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class WhconfigService {
	@Autowired
	private WhConfigMapper whConfigMapper;
	@Autowired
	public CommService commService;
	
	/**
	 * 查询全部信息
	 * 
	 * @return
	 */
	public Object syselect(int page, int rows,String sort,String order)throws Exception{
		PageHelper.startPage(page, rows);
		Example example = new Example(WhConfig.class);
		//查询排序
		if(sort != null && !"".equals(sort)){
			example.setOrderByClause(sort+" "+order );
		}
		List<WhConfig> Configs = this.whConfigMapper.selectByExample(example);
		PageInfo<WhConfig> pageInfo = new PageInfo<WhConfig>(Configs);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("total", pageInfo.getTotal());
		rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}
	/**
	 * 主键删除基本配置信息
	 * 
	 * @param sysid
	 */
	public int delsys(String sysid)throws Exception {
		return this.whConfigMapper.deleteByPrimaryKey(sysid);
	}
	/**
	 * 添加信息
	 */
	public Object addconfig(WhConfig whc)throws Exception {
		return this.whConfigMapper.insert(whc);
	}
	/**
	 * 修改信息
	 * 
	 * @param whc
	 */
	public Object updaconfig(WhConfig whc)throws Exception {
		return this.whConfigMapper.updateByPrimaryKey(whc);
	}
}
