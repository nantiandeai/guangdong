package com.creatoo.hn.services.home.venue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhCfgAdvMapper;
import com.creatoo.hn.mapper.WhVenueMapper;
import com.creatoo.hn.model.WhCfgAdv;
import com.creatoo.hn.model.WhVenue;
import com.creatoo.hn.services.home.art.ArtService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class VenueListService {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private WhVenueMapper whVenueMapper;
	
	@Autowired
	private WhCfgAdvMapper whCfgAdvMapper;
	
	@Autowired
	private ArtService artService;

	/**
	 * 找到可预定的场馆
	 * @return
	 */
	public List<WhVenue> findVenueyd() {
		Example example = new Example(WhVenue.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("vencanbk",1);
		return this.whVenueMapper.selectByExample(example);
	}

	/**
	 * 分页查询场馆信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> findList(Map<String, Object> param) {
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.whVenueMapper.selvenueList(param);
		
		// 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);
        //结果集
        List<Map> rowsList = pageInfo.getList();
        if(rowsList != null){
        	try {
				//
				String scopes = "";
				String _sp = "";
				for(Map m : rowsList){
					String scope = (String)m.get("venscope");
					if(scope != null && !"".equals(scope) && !"null".equalsIgnoreCase(scope)){
						scopes += _sp+scope;
						_sp = ",";
					}
				}
				
				//
				List<Map> tags = this.artService.srchTags(scopes);
				
				for(Map m : rowsList){
					String scopeTags = parseTags((String)m.get("venscope"), tags);
					m.put("venscope", scopeTags);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
        }
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", rowsList);
		return rtnMap;
	}
	
	/**查询场馆用途标签
	 * @param tagIds id1,id2,id3
	 * @param tagList [{"id":"id1", "name":"name1"},{"id":"id1", "name":"name1"}]
	 * @return name1,name2,name3
	 */
	private String parseTags(String tagIds, List<Map> tagList){
		String scopeTags = "";
		
		if(tagIds != null){
			//
			Map<String, String> tagMap = new HashMap<String, String>();
			if(tagList != null){
				for(Map m : tagList){
					tagMap.put((String)m.get("id"), (String)m.get("name"));
				}
			}
			
			//
			String[] ids = tagIds.split(",");
			String _sp = "";
			for(String id : ids){
				if(tagMap.containsKey(id)){
					scopeTags += _sp + tagMap.get(id);
					_sp = ",";
				}
			}
		}
		
		return scopeTags;
	}
	

	/**
	 * 找到广告图
	 * @return
	 */
	public List<WhCfgAdv> advLoad()throws Exception {
		Example example = new Example(WhCfgAdv.class);
		example.createCriteria().andEqualTo("cfgadvpagetype", "2016111400000009").andEqualTo("cfgadvstate", 1);
		example.setOrderByClause("cfgadvidx" + " " + "desc");
		return this.whCfgAdvMapper.selectByExample(example);
	}

}
