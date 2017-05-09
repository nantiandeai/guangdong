package com.creatoo.hn.services.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhKeyMapper;
import com.creatoo.hn.model.WhConfig;
import com.creatoo.hn.model.WhKey;
import com.creatoo.hn.services.comm.CommService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;


/**
 * @author wenjingqiang
 * @version 20160930
 */
@Service
public class KeyService {
	@Autowired
	private WhKeyMapper keyMapper;
	@Autowired
	public CommService commService;

	/*
	 * 查询所有标签信息
	 */
	public Object selAllKey(int page, int rows,String type) {
		PageHelper.startPage(page, rows);
		Example example = new Example(WhKey.class);
		example.createCriteria().andEqualTo("type", type);
		example.setOrderByClause("type,idx");
		List<WhKey> tags =this.keyMapper.selectByExample(example);
		PageInfo<WhKey> pageInfo = new PageInfo<WhKey>(tags);
	    Map<String, Object> rtnMap = new HashMap<String, Object>();
	    rtnMap.put("total", pageInfo.getTotal());
	    rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
		
	}

	/*
	 * 添加关键字
	 */
	public Object insertKey(WhKey whKey) {
//		List<WhKey> idx = new ArrayList<WhKey>();
//		
//		Example example = new Example(WhKey.class);
//		example.or().andEqualTo("idx", whKey.getIdx());
//		int count = this.keyMapper.selectCountByExample(example);
//		
//		whKey.setIdx(String.valueOf(count));
//		
//		this.keyMapper.selectByExample(example);
		return this.keyMapper.insert(whKey);
		
		

	}

	/*
	 * 通过主键查找关键字
	 */
	public WhKey selTagByid(String id) {

		return this.keyMapper.selectByPrimaryKey(id);
	}

	/*
	 * 通过主键删除关键字
	 */
	public int deleteKey(String id) {
		return this.keyMapper.deleteByPrimaryKey(id);

	}
	/*
	 * 更新关键字
	 */
	public int updateTag(WhKey whKey) {
		
		return this.keyMapper.updateByPrimaryKey(whKey);
	}

}
