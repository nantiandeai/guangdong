package com.creatoo.hn.services.admin.activity2;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhActivityMapper;
import com.creatoo.hn.mapper.WhActivityitmMapper;
import com.creatoo.hn.mapper.WhBrandActMapper;
import com.creatoo.hn.mapper.WhBrandMapper;
import com.creatoo.hn.mapper.WhCfgListMapper;
import com.creatoo.hn.model.WhActivity;
import com.creatoo.hn.model.WhActivityitm;
import com.creatoo.hn.model.WhBrand;
import com.creatoo.hn.model.WhBrandAct;
import com.creatoo.hn.model.WhCfgList;
import com.creatoo.hn.model.WhTrain;
import com.creatoo.hn.utils.UploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class BrandService {
	@Autowired
	private WhBrandMapper whBrandMapper;
	@Autowired
	private WhBrandActMapper whBrandActMapper;
	@Autowired
	private WhActivityMapper whActivityMapper;
	@Autowired
	private WhCfgListMapper whcfgListMapper;
	@Autowired
	private WhActivityitmMapper whActivityitmMapper;
	
	public Object searchBrand(Map<String, Object> param)throws Exception  {
	//分页信息
	int page = Integer.parseInt((String)param.get("page"));
	int rows = Integer.parseInt((String)param.get("rows"));
	
	//带条件的分页查询
	PageHelper.startPage(page, rows);
	List<Map> list = this.whBrandMapper.selBrand(param);
	
	// 取分页信息
    PageInfo<Map> pageInfo = new PageInfo<Map>(list);
   
    Map<String, Object> rtnMap = new HashMap<String, Object>();
    rtnMap.put("total", pageInfo.getTotal());
    rtnMap.put("rows", pageInfo.getList());
	return rtnMap;
	}
	/**
	 * 更新专题活动
	 * @param whBrand
	 */
	public void updBrand(WhBrand whBrand) throws Exception {
		this.whBrandMapper.updateByPrimaryKeySelective(whBrand);
		
	}
	/**
	 * 添加专题
	 * @param whBrand
	 */
	public void addTrain(WhBrand whBrand)throws Exception  {
		this.whBrandMapper.insertSelective(whBrand);
	}
	/**
	 * 删除专题
	 * @param uploadPath
	 * @param braid
	 */
	public void delBrand(String uploadPath, String braid)throws Exception {
		WhBrand whBrand = this.whBrandMapper.selectByPrimaryKey(braid);
		String brapic = whBrand.getBrapic();
		String brabigpic = whBrand.getBrabigpic();
		this.whBrandMapper.deleteByPrimaryKey(braid);
		UploadUtil.delUploadFile(uploadPath, brapic);
		UploadUtil.delUploadFile(uploadPath, brabigpic);
	}
	/**
	 * 打回或者取消发布
	 * @param braid
	 * @param brastate
	 * @return 
	 */
	public String back(String braid, Integer brastate)throws Exception {
		WhBrand whBrand = this.whBrandMapper.selectByPrimaryKey(braid);
		whBrand.setBrastate(brastate-1);
		if (brastate == 3) {
			Example example2 =new Example(WhCfgList.class);
			Criteria c2 = example2.createCriteria();
			c2.andEqualTo("cfgpagetype","2016102900000006");
			c2.andEqualTo("cfgenttype","2016103100000001");
			c2.andEqualTo("cfgstate","1");
			c2.andEqualTo("cfgshowid", braid);
			int listcount2=this.whcfgListMapper.selectCountByExample(example2); 
			if(listcount2>0){
				return "批量活动中含有页面元素配置,不允许取消发布！";
			}
		}
		this.whBrandMapper.updateByPrimaryKeySelective(whBrand);
		return "操作成功！";
	}
	/**
	 * 
	 * @param braid
	 * @param brastate
	 */
	public void pass(String braid, Integer brastate)throws Exception {
		WhBrand whBrand = this.whBrandMapper.selectByPrimaryKey(braid);
		if (brastate == 1) {
			whBrand.setBrastate(2);
		}else{
			whBrand.setBrastate(brastate+1);
		}
		
		this.whBrandMapper.updateByPrimaryKeySelective(whBrand);
		
	}
	/**
	 * 审核
	 * @param braid
	 * @param fromstate
	 * @param tostate
	 * @return 
	 */
	public Object checkBrand(String braid, int fromstate, int tostate) {
		List<String> list = new ArrayList<String>();
		String[] tra = braid.split(",");
		for (int i = 0; i < tra.length; i++) {
			list.add(tra[i]);
		}
		if(tostate == 2 && fromstate == 3){
			Example example2 =new Example(WhCfgList.class);
			Criteria c2 = example2.createCriteria();
			c2.andEqualTo("cfgpagetype","2016102900000006");
			c2.andEqualTo("cfgenttype","2016103100000001");
			c2.andEqualTo("cfgstate","1");
			c2.andIn("cfgshowid",list);
			int listcount2=this.whcfgListMapper.selectCountByExample(example2); 
			if(listcount2>0){
				return "批量活动中含有页面元素配置,不允许取消发布！";
			}
		}
		WhBrand whBrand = new WhBrand();
		whBrand.setBrastate(tostate);
		Example example = new Example(WhBrand.class);
		example.createCriteria().andIn("braid", list).andEqualTo("brastate", fromstate);
		this.whBrandMapper.updateByExampleSelective(whBrand, example);
		return "操作成功！";
	}
	/**
	 * 找到活动标题
	 * @return
	 */
	public Object findActTitle() {
		return this.whBrandActMapper.selectAct();
	}
	/**
	 * 拿到活动管理表格中的内容
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> searchActBrand(Map<String, Object> param) {
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.whBrandActMapper.selBrandAct(param);
		
		// 取分页信息
	    PageInfo<Map> pageInfo = new PageInfo<Map>(list);
	   
	    Map<String, Object> rtnMap = new HashMap<String, Object>();
	    rtnMap.put("total", pageInfo.getTotal());
	    rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}
	/**
	 * 通过ID找到活动信息
	 * @param actvid
	 * @return
	 */
	public Object serachAct(String actvid) {
		return this.whActivityMapper.selectByPrimaryKey(actvid);
	}
	/**
	 * 更新品牌活动与活动关联表
	 * @param whBrandAct
	 * @throws Exception
	 */
	public void updBrandAct(WhBrandAct whBrandAct)throws Exception {
		this.whBrandActMapper.updateByPrimaryKeySelective(whBrandAct);
		
	}
	/**
	 * 添加品牌活动与活动关联表
	 * @param whBrandAct
	 * @throws Exception
	 */
	public void addBrand(WhBrandAct whBrandAct)throws Exception {
		this.whBrandActMapper.insertSelective(whBrandAct);
	}
	/**
	 * 删除
	 * @param uploadPath
	 * @param bracid
	 */
	public void delActBrand( String bracid)throws Exception {
		this.whBrandActMapper.deleteByPrimaryKey(bracid);
		
	}
	/**
	 * 判断是否能够取消发布
	 * @param braid
	 * @return
	 */
	public Object isOrNo(String braid) {
		
		return this.whBrandActMapper.isPublish(braid);
	}
	/**
	 * 找到活动场次的时间
	 * @param actvrefid
	 * @return
	 */
	public WhActivityitm findActTime(String actvrefid)throws Exception {
		Example example = new Example(WhActivityitm.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("actvrefid", actvrefid);
		example.setOrderByClause("actvitmsdate asc");
		PageHelper.startPage(1, 1);
		List<WhActivityitm> act = this.whActivityitmMapper.selectByExample(example);
		if (!act.isEmpty()) {
			return act.get(0);
		}else{
			return null;
		}
		
	}

}
