package com.creatoo.hn.services.admin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhTagMapper;
import com.creatoo.hn.mapper.WhTypMapper;
import com.creatoo.hn.model.WhTag;
import com.creatoo.hn.model.WhTyp;
import com.creatoo.hn.services.comm.CommService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;


/**
 * @author wenjingqiang
 * @version 20160930
 */
@Service
public class TagService {
	@Autowired
	private WhTagMapper tagMapper;

	@Autowired
	public CommService commService;
	
	private WhTypMapper commtypMapper;
	
	
	/*
	 * 查询所有标签信息
	 */
	public Object selAllTag(int page, int rows,String type) {
		PageHelper.startPage(page, rows);
		Example example=new Example(WhTag.class);
		example.createCriteria().andEqualTo("type", type);
		example.setOrderByClause("type,idx");
		List<WhTag> tags = this.tagMapper.selectByExample(example);
		// 取分页信息
        PageInfo<WhTag> pageInfo = new PageInfo<WhTag>(tags);
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
		
	}
	/*
	 * 添加一个标签
	 */
	public Object insertTag(WhTag whTag) {
		return this.tagMapper.insert(whTag);
	}

	/*
	 * 更新标签
	 */
	public Object updateTag (WhTag whTag)throws Exception {
		return this.tagMapper.updateByPrimaryKey(whTag);
	}
	/*
	 * 删除标签
	 */
	public int deleteTag(String id){		
		int rows = this.tagMapper.deleteByPrimaryKey(id);
		return rows;
		
	}
	
	public Object selTagByid(String id) {
		
		return this.tagMapper.selectByPrimaryKey(id);
	}
	/**
	 * 树形型查询
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public Object inquire(String type) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<Object> list = new ArrayList<Object>();	
		// 分类表按分类条件查询
		Example example = new Example(WhTyp.class);
		example.createCriteria().andEqualTo("type", type);
		example.setOrderByClause("typpid,typidx");
		List<WhTyp> listTp = this.commtypMapper.selectByExample(example);
		if (listTp != null) {
			for (WhTyp whzxtyp : listTp) {
				if (whzxtyp.getTyppid() != null && "0".equals(whzxtyp.getTyppid())) {
					Map temp = BeanUtils.describe(whzxtyp);
					this.addList(temp, listTp);
					//temp.put("status", temp.get("typstatus"));
					temp.put("id", temp.get("typid"));
					temp.put("text", temp.get("typname"));
					list.add(temp);
				}
			}
		}
		return list;
	}

	/**
	 * 查询子级查单
	 * 
	 * @param temp
	 * @param list
	 */

	private void addList(Map<String, Object> temp, List<WhTyp> listTp) {
		if (temp.get("children") == null) {
			temp.put("children", new ArrayList<Object>());
		}
		for (WhTyp typ : listTp) {
			if (temp.get("typid").equals(typ.getTyppid())) {
				List children = (List) temp.get("children");
				try {
					Map temps = BeanUtils.describe(typ);
					this.addList(temps, listTp);
					//temps.put("status", temps.get("typstatus"));
					temps.put("id", temps.get("typid"));
					temps.put("text", temps.get("typname"));
					children.add(temps);
				} catch (Exception e) {
				}
			}
		}
	}

}
