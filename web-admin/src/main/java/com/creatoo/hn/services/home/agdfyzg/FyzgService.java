package com.creatoo.hn.services.home.agdfyzg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import com.creatoo.hn.mapper.ActivityMapper;
import com.creatoo.hn.mapper.WhCfgAdvMapper;
import com.creatoo.hn.mapper.WhCfgListMapper;
import com.creatoo.hn.mapper.WhDecprojectMapper;
import com.creatoo.hn.mapper.WhEntsourceMapper;
import com.creatoo.hn.mapper.WhKeyMapper;
import com.creatoo.hn.mapper.WhSuccessorMapper;
import com.creatoo.hn.mapper.WhSuorproMapper;
import com.creatoo.hn.mapper.WhTagMapper;
import com.creatoo.hn.mapper.WhZxColinfoMapper;
import com.creatoo.hn.model.WhCfgAdv;
import com.creatoo.hn.model.WhCfgList;
import com.creatoo.hn.model.WhDecproject;
import com.creatoo.hn.model.WhEntsource;
import com.creatoo.hn.model.WhKey;
import com.creatoo.hn.model.WhSuccessor;
import com.creatoo.hn.model.WhSuorpro;
import com.creatoo.hn.model.WhTag;
import com.creatoo.hn.model.WhZxColinfo;
import com.creatoo.hn.services.home.agdgwgk.GwgkService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 非遗展馆
 * @author wangxl
 * @version 2016.11.16
 */
@Service
public class FyzgService {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * 自写Xml
	 */
	@Autowired
	private ActivityMapper amapper;
	
	/**
	 * 名录项目
	 */
	@Autowired
	private WhDecprojectMapper decprojectMapper;
	
	/**
	 * 传承人
	 */
	@Autowired
	private WhSuccessorMapper successorMapper;
	
	/**
	 * 传承人 名录 关联
	 */
	@Autowired
	private WhSuorproMapper suorproMapper;
	
	/**
	 * 关键字
	 */
	@Autowired
	private WhKeyMapper whkeyMapper;
	
	/**
	 * wh_entsource表
	 */
	@Autowired
	public WhEntsourceMapper entSourceMapper;
	
	/**
	 * 资讯栏目
	 */
	@Autowired
	private WhZxColinfoMapper zxcolinfoMapper;
	
	/***
	 * 页面配置元素
	 */
	@Autowired
	private WhCfgListMapper cfgListMapper;
	
	@Autowired
	private WhCfgAdvMapper cfgAdvMapper;
	/**
	 * 名录数据加载
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Object dataloadminglu(WebRequest request) throws Exception{
		Map<String,Object> params = ReqParamsUtil.parseRequest(request);
		int page = Integer.parseInt((String)params.get("page"));
		int size = Integer.parseInt((String)params.get("rows"));
		String mlproshortitel = (String)params.get("mlproshortitel");
		if(mlproshortitel != null){
			params.put("bratitle", "%"+mlproshortitel+"%");
		}
		PageHelper.startPage(page, size);
		List<Map> list = this.amapper.dataloadminglu(params);
		PageInfo pinfo = new PageInfo(list);

        Map<String, Object> res = new HashMap<String, Object>();
        res.put("total", pinfo.getTotal());
        res.put("rows", pinfo.getList());
        res.put("page", pinfo.getPageNum());
		return res;
	}
	
	/**
	 * 传承人列表 数据加载
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Object dataloadsuccessor(WebRequest request) throws Exception{
		Map<String,Object> params = ReqParamsUtil.parseRequest(request);
		int page = Integer.parseInt((String)params.get("page"));
		int size = Integer.parseInt((String)params.get("rows"));
		String suorname = (String)params.get("suorname");
		if(suorname != null){
			params.put("suorname", "%"+suorname+"%");
		}
		PageHelper.startPage(page, size);
		List<Map> list = this.amapper.dataloadsuccessor(params);
		PageInfo pinfo = new PageInfo(list);

        Map<String, Object> res = new HashMap<String, Object>();
        res.put("total", pinfo.getTotal());
        res.put("rows", pinfo.getList());
        res.put("page", pinfo.getPageNum());
		return res;
	}
	
	/**
	 * 根据名录id 查详情
	 * @param mlproid
	 * @return
	 */
	public WhDecproject decproDetail(String mlproid)throws Exception{
		return this.decprojectMapper.selectByPrimaryKey(mlproid);
	}
	
	/**
	 * 根据名录id　查相关传承人
	 * @param mlproid
	 * @return
	 */
	public Object protosuccessor(String mlproid)throws Exception{
		Example example = new Example(WhSuccessor.class);
		Example.Criteria c = example.createCriteria();
		c.andEqualTo("proid", mlproid);
		List<WhSuccessor> listsp = this.successorMapper.selectByExample(example);

//		List<String> list = new ArrayList<>();
// 		if(listsp != null && listsp.size() >0 ){
//			for (int j = 0; j < listsp.size(); j++) {
//				list.add(listsp.get(j).getProid());
//			}
//		}
// 		Example example2 = new Example(WhSuccessor.class);
//		if(list != null && list.size()>0){
//			example2.or().andIn("suorid", list);
			PageHelper.startPage(1, 6);
//			List<WhSuccessor> listsuor = this.successorMapper.selectByExample(example2);
			PageInfo pinfo = new PageInfo(listsp);
			return pinfo.getList();
//		}else{
//			return list;
//		}
	}
	
	/**
	 * 根据主键id 查key 关键字 
	 * @param tagid
	 * @return
	 * @throws Exception
	 */
	public WhKey keyList(String tagid)throws Exception{
		return this.whkeyMapper.selectByPrimaryKey(tagid);
	}
	
	/**
	 * 根据主键id 获取相关资源
	 * @param type
	 * @param mlproid
	 * @return
	 * @throws Exception
	 */
	public List<WhEntsource> getmingluEnt(String type,String mlproid)throws Exception{
		Example example = new Example(WhEntsource.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("entsuorpro", mlproid);
		c.andEqualTo("enttype", type);
		PageHelper.startPage(1, 6);
		List<WhEntsource> list = this.entSourceMapper.selectByExample(example);
		PageInfo pinfo = new PageInfo(list);
		return pinfo.getList();
	}
	
	/**
	 * 名录推荐
	 * @param mlproid
	 * @return
	 */
	public List<WhDecproject> tuijianminglu(String mlproid){
		Example example = new Example(WhDecproject.class);
		Criteria c = example.createCriteria();
		c.andNotEqualTo("mlproid", mlproid);
		c.andEqualTo("recommend", 1);
		PageHelper.startPage(1, 4);
		example.setOrderByClause("mlprotime"+" "+"desc");
		return this.decprojectMapper.selectByExample(example);
	}
	
	/**
	 * 根据传承人id 查详情
	 * @param
	 * @return
	 */
	public List<Map> suorDetail(String suorid)throws Exception{
		return this.amapper.suorDetail(suorid);
	}
	
	/**
	 * 根据传承人id　查相关传承人
	 * @param
	 * @return
	 */
	public Object successortopro(String suorid,int size)throws Exception{
//		Example example = new Example(WhSuccessor.class);
//		example.or().andEqualTo("proid", suorid);
		List<WhSuccessor> listsp = this.amapper.successor(suorid);
		List<String> list = new ArrayList<>();
 		if(listsp != null && listsp.size() >0 ){
			for (int j = 0; j < listsp.size(); j++) {
				list.add(listsp.get(j).getProid());
			}
		}
 		
		if(list != null && list.size()>0){
			Map<String, Object> map = new HashMap<>();
			map.put("list", list);
 			PageHelper.startPage(1, size);
			List<Map> listml = this.amapper.successortopro(map);
			PageInfo pinfo = new PageInfo(listml);
			return pinfo.getList();
		}else{
			return list;
		}
	}
	
	/**
	 * 传承人推荐
	 * @param suorid
	 * @return
	 */
	public List<Map> tuijiansuor(String suorid){
		PageHelper.startPage(1, 6);
		List<Map> list = this.amapper.tjsuor(suorid);
		PageInfo pinfo = new PageInfo(list);
		return pinfo.getList();
	}
	
	/**
	 * 查询公告/news
	 * @param
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<WhZxColinfo> gonggaoColinfo(String type)throws Exception{
		//WhZxColinfo colinfo = null;
		
		//分页查询政策法规
		Example example = new Example(WhZxColinfo.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("clnfstata", 3);//已发布
		criteria.andEqualTo("clnftype", type);
		example.setOrderByClause("clnfcrttime desc");
		//criteria.andEqualTo("clnfid", id);//id
		PageHelper.startPage(1, 6);
		List<WhZxColinfo> list = this.zxcolinfoMapper.selectByExample(example);
		
		PageInfo pinfo = new PageInfo(list);
		return pinfo.getList();
	}
	
	/**
	 * 非遗首页 配置
	 * @return
	 * @throws Exception
	 */
	public List<WhZxColinfo> getzxpz()throws Exception{
		Example example = new Example(WhCfgList.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("cfgpagetype","2016112900000013");
		c.andEqualTo("cfgenttype","2016102800000001");
		c.andEqualTo("cfgentclazz","2016112200000005");
		c.andEqualTo("cfgstate",1);
		PageHelper.startPage(1, 3);
		List<WhCfgList> list = this.cfgListMapper.selectByExample(example);
		@SuppressWarnings("rawtypes")
		PageInfo pinfo = new PageInfo(list);
		return pinfo.getList();
	}
	
	/**
	 * 非遗首页 取8 名录项目
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<WhDecproject> getminglu(String type)throws Exception{
		Example example = new Example(WhDecproject.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("mlprotype",type);
//		c.andEqualTo("mlprolevel","2017040500000004");
		c.andEqualTo("mlprostate",6);
		example.setOrderByClause("mlprotime"+" "+"desc");
		PageHelper.startPage(1, 30);
		List<WhDecproject> list = this.decprojectMapper.selectByExample(example);
		@SuppressWarnings("rawtypes")
		PageInfo pinfo = new PageInfo(list);
		return pinfo.getList();
	}
	
	/**
	 * 非遗首页大广告
	 * @param
	 * @return
	 * @throws Exception
	 */
	public List<WhCfgAdv> getAdv()throws Exception{
		Example example = new Example(WhCfgAdv.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("cfgadvpagetype","2016122100000003");
		c.andEqualTo("cfgadvstate",1);
		example.setOrderByClause("cfgadvidx"+" "+"desc");
		PageHelper.startPage(1, 1);
		List<WhCfgAdv> list = this.cfgAdvMapper.selectByExample(example);
		@SuppressWarnings("rawtypes")
		PageInfo pinfo = new PageInfo(list);
		return pinfo.getList();
		
	}
}
