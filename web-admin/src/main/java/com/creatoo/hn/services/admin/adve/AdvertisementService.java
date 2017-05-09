package com.creatoo.hn.services.admin.adve;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.creatoo.hn.mapper.WhCfgAdvMapper;
import com.creatoo.hn.model.WhCfgAdv;
import com.creatoo.hn.services.comm.CommService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
@Service
public class AdvertisementService {

	@Autowired
	public CommService commService;
	@Autowired
	private WhCfgAdvMapper wfCfgAdvMapper;
	
	public Map<String, Object> seleadv(Map<String, Object> param) {
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
				
		PageHelper.startPage(page, rows);
		Example example = new Example(WhCfgAdv.class);
		Criteria criteria = example.createCriteria();
		
		//排序
		if(param.containsKey("sort") && param.get("sort")!= null){
			StringBuffer sbf = new StringBuffer((String)param.get("sort"));
			if(param.containsKey("order") && param.get("order") !=  null){
				sbf.append(" ").append(param.get("order"));
			}
			example.setOrderByClause(sbf.toString());
		}
		//类型
		if(param.containsKey("cfgadvpagetype") && param.get("cfgadvpagetype") != null){
			String cfgadvpagetype = (String)param.get("cfgadvpagetype");
			if(!"".equals(cfgadvpagetype.trim())){
			 criteria.andEqualTo("cfgadvpagetype", cfgadvpagetype);
			}
		}
		List<WhCfgAdv> list = this.wfCfgAdvMapper.selectByExample(example);
		
		PageInfo<WhCfgAdv> pageInfo = new PageInfo<WhCfgAdv>(list);
	    Map<String, Object> rtnMap = new HashMap<String, Object>();
	    rtnMap.put("total", pageInfo.getTotal());
	    rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}
    /**
     * 添加
     * @param wfc
     */
	public void save(WhCfgAdv wfc) {
		this.wfCfgAdvMapper.insert(wfc);
	}
    /**
     * 根据主键删除
     * @param cfgadvid
     * @return
     */
	public int delete(String cfgadvid) {
		int id=this.wfCfgAdvMapper.deleteByPrimaryKey(cfgadvid);
		return id;
	}
    /**
     * 编辑
     * @param wfc
     */
	public void upadv(WhCfgAdv wfc) {
		this.wfCfgAdvMapper.updateByPrimaryKeySelective(wfc);
	}
     /**
      * 改变状态
      * @param wfc
      */
	public void checkwfc(WhCfgAdv wfc) {
		this.wfCfgAdvMapper.updateByPrimaryKeySelective(wfc);
	}

}
