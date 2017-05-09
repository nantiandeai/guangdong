package com.creatoo.hn.services.admin.shop;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhZxColumnMapper;
import com.creatoo.hn.model.WhZxColumn;
import com.creatoo.hn.services.comm.CommService;

import tk.mybatis.mapper.entity.Example;
@Service
public class MuseumService {
	
	@Autowired
	public CommService commService;
	@Autowired
	private WhZxColumnMapper whZxColumnMapper;
    /**
     * 查询顶级
     * @param colstate 
     * @return
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object inquire() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<Object> list = new ArrayList<Object>();	
		// 分类表按分类条件查询
		Example example = new Example(WhZxColumn.class);
		example.setOrderByClause("colidx,colpid");
		
		List<WhZxColumn> listTp = this.whZxColumnMapper.selectByExample(example);
		if (listTp != null) {
			for (WhZxColumn whzx : listTp) {
				if (whzx.getColpid() != null && "0".equals(whzx.getColpid())) {
					Map temp = BeanUtils.describe(whzx);
					this.addList(temp, listTp);
					temp.put("id", temp.get("colid"));
					temp.put("text", temp.get("coltitle"));
					list.add(temp);
				}
			}
		}
		return list;
	}
	/**
	 * 查询子级目录
	 * @param temp
	 * @param listTp
	 */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void addList(Map temp, List<WhZxColumn> listTp) {
    	if (temp.get("children") == null) {
			temp.put("children", new ArrayList<Object>());
		}
		for (WhZxColumn whzx : listTp) {
			if (temp.get("colid").equals(whzx.getColpid())) {
				List children = (List) temp.get("children");
				try {
					Map temps = BeanUtils.describe(whzx);
					this.addList(temps, listTp);
					temps.put("id", temps.get("colid"));
					temps.put("text", temps.get("coltitle"));
					children.add(temps);
				} catch (Exception e) {
				}
			}
		}
		
	}
	/**
     * 添加
     * @param whzx
     */
	public void save(WhZxColumn whzx) {
         this.whZxColumnMapper.insert(whzx);
	}
    /**
     * 更新
     * @param whzx
     */
	public void update(WhZxColumn whzx) {
		this.whZxColumnMapper.updateByPrimaryKeySelective(whzx);
		
	}
    /**
     * 删除
     * @param colid
     */
	public Object delete(String colid) {
		
	    int row=  this.whZxColumnMapper.deleteByPrimaryKey(colid);
	    
	    return row;
	}
	/**
	 * 带状态查询
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object select() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<Object> list = new ArrayList<Object>();	
		// 分类表按分类条件查询
		Example example = new Example(WhZxColumn.class);
		example.createCriteria().andEqualTo("colstate",1);
		example.setOrderByClause("colidx,colpid");
		List<WhZxColumn> listTp = this.whZxColumnMapper.selectByExample(example);
		if (listTp != null) {
			for (WhZxColumn whzx : listTp) {
				if (whzx.getColpid() != null && "0".equals(whzx.getColpid())) {
					Map temp = BeanUtils.describe(whzx);
					this.addList(temp, listTp);
					temp.put("id", temp.get("colid"));
					temp.put("text", temp.get("coltitle"));
					list.add(temp);
				}
			}
		}
		return list;
	}

}
