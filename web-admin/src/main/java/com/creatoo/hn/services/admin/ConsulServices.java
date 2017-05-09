package com.creatoo.hn.services.admin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhTypMapper;
import com.creatoo.hn.model.WhTyp;
import com.creatoo.hn.services.comm.CommService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class ConsulServices {
	
	@Autowired
	private WhTypMapper commtypMapper;
	
	@Autowired
	public CommService commService;
	/*
	 * 查询顶级
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
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
					temps.put("id", temps.get("typid"));
					temps.put("text", temps.get("typname"));
					children.add(temps);
				} catch (Exception e) {
				}
			}
		}
	}

	/*
	 * 删除
	 */
	public Object delete(String typid) {
		int row = this.commtypMapper.deleteByPrimaryKey(typid);
		return row;
	}

	/*
	 * 添加
	 */
	public Object save(WhTyp wht) throws Exception {
		return this.commtypMapper.insert(wht);
	}

	/*
	 * 更新
	 */
	public Object update(WhTyp wht) {
		return this.commtypMapper.updateByPrimaryKey(wht);
	}

	/*
	 * 得到id
	 */
	public WhTyp getTypeId(String typid) {
		return this.commtypMapper.selectByPrimaryKey(typid);
	}
    /**
     * 改变状态
     * @param wht
     */
	public void dotype(WhTyp wht) {
		this.commtypMapper.updateByPrimaryKeySelective(wht);
	}

	public void goadv(WhTyp wht) {
		this.commtypMapper.updateByPrimaryKeySelective(wht);
		
	}

	public Object selectadv(String type) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<Object> list = new ArrayList<Object>();	
		// 分类表按分类条件查询
		Example example = new Example(WhTyp.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("type", type);
		criteria.andEqualTo("typadv", 1);
		example.setOrderByClause("typpid,typidx");
		List<WhTyp> listTp = this.commtypMapper.selectByExample(example);
		if (listTp != null) {
			for (WhTyp whzxtyp : listTp) {
				if (whzxtyp.getTyppid() != null && "0".equals(whzxtyp.getTyppid())) {
					Map temp = BeanUtils.describe(whzxtyp);
					this.addList(temp, listTp);
					temp.put("id", temp.get("typid"));
					temp.put("text", temp.get("typname"));
					list.add(temp);
				}
			}
		}
		return list;
	}

	public Object select(String typid) {
		return this.commtypMapper.selectByPrimaryKey(typid);
	}
	
}
