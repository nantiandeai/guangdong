package com.creatoo.hn.services.home.zhiyuan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhCfgListMapper;
import com.creatoo.hn.mapper.WhEntsourceMapper;
import com.creatoo.hn.mapper.WhZxColinfoMapper;
import com.creatoo.hn.mapper.WhZxColumnMapper;
import com.creatoo.hn.model.WhCfgList;
import com.creatoo.hn.model.WhEntsource;
import com.creatoo.hn.model.WhZxColinfo;
import com.creatoo.hn.model.WhZxColumn;
import com.creatoo.hn.services.comm.CommService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class VolunteerService {
	@Autowired
	public CommService commService;
	@Autowired
	private WhZxColumnMapper whZxColumnMapper;
	@Autowired
	private WhZxColinfoMapper WhZxColinfos;
	@Autowired
	private WhCfgListMapper whCfgListMapper;
	@Autowired
	private WhEntsourceMapper whEntsourceMapper;
	
    /**
     * 志愿者类型
     * @param colid
     * @return
     */
	public Object selectwhhm(String colid) {
		Example example = new Example(WhZxColumn.class);
		Criteria Criteria = example.createCriteria();
		Criteria.andEqualTo("colpid", colid);
		Criteria.andEqualTo("colstate", 1);
		example.setOrderByClause("colidx");
		List<WhZxColumn> col =this.whZxColumnMapper.selectByExample(example);
		return col;
	}
    
	/**
	 * 志愿者列表信息
	 * @return
	 */
	public Object selectzhiyuan() {
		Example example = new Example(WhZxColinfo.class);
		Criteria Criteria = example.createCriteria();
		Criteria.andEqualTo("clnfstata", 3);
		List<WhZxColinfo> col =this.WhZxColinfos.selectByExample(example);
		return col;
		
	}
    /**
     * 志愿者详细信息
     * @param clnfid
     * @return
     */
	public Object selectinfo(String clnfid) {
		//根据id查询
		WhZxColinfo info = this.WhZxColinfos.selectByPrimaryKey(clnfid);
		String lastId = null;
		String lasttitle = null;
		String befId = null;
		String beftitle = null;
		String type = info.getClnftype();
		
		//判断浏览量不等于空
		Integer brow = info.getClnfbrowse();
		if (brow == null || brow < 0) {
			brow=0;
		}
		info.setClnfid(clnfid);
		info.setClnfbrowse(brow+1);
		this.WhZxColinfos.updateByPrimaryKeySelective(info);
		
		Example example = new Example(WhZxColinfo.class);
		Criteria c = example.createCriteria();
		//大于当前id
		c.andEqualTo("clnftype", type).andGreaterThan("clnfid", info.getClnfid());
		
		List<WhZxColinfo> g_list = this.WhZxColinfos.selectByExampleAndRowBounds(example, new RowBounds(0, 1));
		if (g_list!=null && g_list.size()>0){
					lastId = g_list.get(0).getClnfid();
					lasttitle = g_list.get(0).getClnftltle();
		}
		example.clear();
		c = example.createCriteria();
		//小于当前id
		c.andEqualTo("clnftype", type).andLessThan("clnfid", info.getClnfid());
		example.setOrderByClause("clnfid desc");
		List<WhZxColinfo> l_list = this.WhZxColinfos.selectByExampleAndRowBounds(example, new RowBounds(0, 1));
		if (l_list!=null && l_list.size()>0){
			befId = l_list.get(0).getClnfid();
			beftitle = l_list.get(0).getClnftltle();
		}
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("info", info);
		resMap.put("lid", lastId);
		resMap.put("ltitle", lasttitle);
		resMap.put("bid", befId);
		resMap.put("btitle", beftitle);
		resMap.put("typelist", type);
		return resMap;
	}
    /**
     * 资源
     * @param clnfid
     * @return
     */
	public List<WhEntsource> selewhe(String clnfid) {
		Example example = new Example(WhEntsource.class);
		Criteria Criteria = example.createCriteria();
		Criteria.andEqualTo("refid", clnfid);
		List<WhEntsource> whe =this.whEntsourceMapper.selectByExample(example);
		return whe;
	}
   /**
    * 志愿者类型查询
    * @param cfgentclazz 
 * @param cfgtype 
    * @return
    */
	public Object selecthot(String cfgentclazz, String cfgtype) {
		Example example = new Example(WhCfgList.class);
		Criteria Criteria = example.createCriteria();
		Criteria.andEqualTo("cfgpagetype", cfgtype);
		Criteria.andEqualTo("cfgentclazz", cfgentclazz);
		Criteria.andEqualTo("cfgstate", 1);
		List<WhCfgList> cfg =this.whCfgListMapper.selectByExample(example);
		return cfg;
	}
	/**
	 * 热门动态
	 * @param clazz
	 * @param cfgpagetype 
	 * @return
	 */
	public Object selectclazz(String clazz, String cfgpagetype) {
		Example example = new Example(WhCfgList.class);
		Criteria Criteria = example.createCriteria();
		Criteria.andEqualTo("cfgpagetype", cfgpagetype);
		Criteria.andEqualTo("cfgentclazz", clazz);
		Criteria.andEqualTo("cfgstate", 1);
		List<WhCfgList> cfg =this.whCfgListMapper.selectByExample(example);
		return cfg;
	}
    /**
     * 培训动态
     * @param type
     * @param cfgpage 
     * @return
     */
	public Object select(String type, String cfgpage) {
		Example example = new Example(WhCfgList.class);
		Criteria Criteria = example.createCriteria();
		Criteria.andEqualTo("cfgpagetype", cfgpage);
		Criteria.andEqualTo("cfgentclazz", type);
		Criteria.andEqualTo("cfgstate", 1);
		List<WhCfgList> cfg =this.whCfgListMapper.selectByExample(example);
		return cfg;
	}
    /**
     * 志愿者中心
     * @param types
     * @param pagetype 
     * @return
     */
	public Object selectype(String types, String pagetype) {
		Example example = new Example(WhCfgList.class);
		Criteria Criteria = example.createCriteria();
		Criteria.andEqualTo("cfgpagetype", pagetype);
		Criteria.andEqualTo("cfgentclazz", types);
		Criteria.andEqualTo("cfgstate", 1);
		List<WhCfgList> cfg =this.whCfgListMapper.selectByExample(example);
		return cfg;
	}

}
