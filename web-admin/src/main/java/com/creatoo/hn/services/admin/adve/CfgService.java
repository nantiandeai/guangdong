package com.creatoo.hn.services.admin.adve;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhActivityMapper;
import com.creatoo.hn.mapper.WhActivityitmMapper;
import com.creatoo.hn.mapper.WhBrandMapper;
import com.creatoo.hn.mapper.WhCfgListMapper;
import com.creatoo.hn.mapper.WhTrainMapper;
import com.creatoo.hn.mapper.WhZxColinfoMapper;
import com.creatoo.hn.model.WhActivity;
import com.creatoo.hn.model.WhActivityitm;
import com.creatoo.hn.model.WhBrand;
import com.creatoo.hn.model.WhCfgList;
import com.creatoo.hn.model.WhTrain;
import com.creatoo.hn.model.WhZxColinfo;
import com.creatoo.hn.services.comm.CommService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 页面内容配置的服务类
 * @author rbg
 *
 */
@Service
public class CfgService {
	@Autowired
	public CommService commService;
	
	@Autowired
	private WhCfgListMapper whCfgListMapper;
	
	/**
	 * 培训DAO
	 */
	@Autowired
	private WhTrainMapper trainMapper;
	
	/**
	 * 活动DAO
	 */
	@Autowired
	private WhActivityMapper activityMapper;
	
	/**
	 * 品牌DAO
	 */
	@Autowired
	private WhBrandMapper brandMapper;
	
	/**
	 * 资讯DAO
	 */
	@Autowired
	private WhZxColinfoMapper colinfoMapper;
	
	@Autowired
	private WhActivityitmMapper whActivityitmMapper;
    
	/**
     * 查询
     * @param paramMap
     * @return
     */
	public Map<String, Object> inquire(Map<String, Object> param) {
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
				
		PageHelper.startPage(page, rows);
		Example example = new Example(WhCfgList.class);
		Criteria criteria = example.createCriteria();
		
		//排序
		if(param.containsKey("sort") && param.get("sort")!= null){
			StringBuffer sbf = new StringBuffer((String)param.get("sort"));
			if(param.containsKey("order") && param.get("order") !=  null){
				sbf.append(" ").append(param.get("order"));
			}
			example.setOrderByClause(sbf.toString());
		}
		//页面类型
		if(param.containsKey("cfgpagetype") && param.get("cfgpagetype") != null){
			String cfgpagetype = (String)param.get("cfgpagetype");
			if(!"".equals(cfgpagetype.trim())){
			 criteria.andEqualTo("cfgpagetype", cfgpagetype);
			}
		}
		
		//实体类型
		if(param.containsKey("cfgenttype") && param.get("cfgenttype") != null){
			String cfgenttype = (String)param.get("cfgenttype");
			if(!"".equals(cfgenttype.trim())){
			 criteria.andEqualTo("cfgenttype", cfgenttype);
			}
		}
				
		List<WhCfgList> list = this.whCfgListMapper.selectByExample(example);
		
		PageInfo<WhCfgList> pageInfo = new PageInfo<WhCfgList>(list);
	    Map<String, Object> rtnMap = new HashMap<String, Object>();
	    rtnMap.put("total", pageInfo.getTotal());
	    rtnMap.put("rows", pageInfo.getList());
		return rtnMap;

	}
    /**
     * 添加
     * @param whc
     */
	public void save(WhCfgList whc) throws Exception{
		this.whCfgListMapper.insert(whc);
		
	}
    /**
     * 更新
     * @param whc
     */
	public void upwhcgf(WhCfgList whc)throws Exception {
		this.whCfgListMapper.updateByPrimaryKeySelective(whc);
		
	}
    /**
     * 根据主键删除
     * @param cfgid
     * @return
     */
	public int delete(String cfgid)throws Exception {
		int id=this.whCfgListMapper.deleteByPrimaryKey(cfgid);
		return id;
		
	}
    /**
     * 改变状态
     * @param whc
     */
	public void checkcfg(WhCfgList whc)throws Exception {
		this.whCfgListMapper.updateByPrimaryKeySelective(whc);
		
	}
	
	/**
	 * 根据类型查询实体列表
	 * @param type 0-培训;1-活动;2-品牌;3-资讯
	 * @throws Excepiton
	 */
	public Object srchEntList(String type, String clazz, String sort, String order)throws Exception{
		Object rtnObj = null;
		
		//0-培训;1-活动;2-品牌;3-资讯
		if("0".equals(type)){//0-培训
			Example example = new Example(WhTrain.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("trastate", "3");//必须是已发布
			
			//按分类查询
			if(clazz != null && !"".equals(clazz)){
				criteria.andEqualTo("tratyp", clazz);
			}
			
			//排序
			if(sort != null && !"".equals(sort)){
				if("desc".equalsIgnoreCase(order)){
					example.setOrderByClause(sort+" desc");
				}else{
					example.setOrderByClause(sort);
				}
			}
			
			//查询
			rtnObj = this.trainMapper.selectByExample(example);
		}else if("1".equals(type)){//1-活动
			Example example = new Example(WhActivity.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("actvstate", "3");//必须是已发布
			
			//按分类查询
			if(clazz != null && !"".equals(clazz)){
				criteria.andEqualTo("actvtype", clazz);
			}
			
			//排序
			if(sort != null && !"".equals(sort)){
				if("desc".equalsIgnoreCase(order)){
					example.setOrderByClause(sort+" desc");
				}else{
					example.setOrderByClause(sort);
				}
			}
			
			//查询
			rtnObj = this.activityMapper.selectByExample(example);
		}else if("2".equals(type)){//2-品牌
			Example example = new Example(WhBrand.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("brastate", "3");//必须是已发布
			
			//排序
			if(sort != null && !"".equals(sort)){
				if("desc".equalsIgnoreCase(order)){
					example.setOrderByClause(sort+" desc");
				}else{
					example.setOrderByClause(sort);
				}
			}
			
			//查询
			rtnObj = this.brandMapper.selectByExample(example);
		}else if("3".equals(type)){//3-资讯
			Example example = new Example(WhZxColinfo.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("clnfstata", "3");//必须是已发布
			
			//按分类查询
			if(clazz != null && !"".equals(clazz)){
				criteria.andEqualTo("clnftype", clazz);
			}
			
			//排序
			if(sort != null && !"".equals(sort)){
				if("desc".equalsIgnoreCase(order)){
					example.setOrderByClause(sort+" desc");
				}else{
					example.setOrderByClause(sort);
				}
			}
			
			//查询
			rtnObj = this.colinfoMapper.selectByExample(example);
		}
		return rtnObj;
	}
	
	/**
	 * 查询活动时间
	 * @param actvid 
	 * @return 
	 */
	public List<WhActivityitm> selecthd(String actvid)throws Exception {
		Example example = new Example(WhActivityitm.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("actvrefid", actvid);
		example.setOrderByClause("actvitmsdate");
		return this.whActivityitmMapper.selectByExample(example);
		 
	}
}
