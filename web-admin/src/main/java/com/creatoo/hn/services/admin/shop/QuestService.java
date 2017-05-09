package com.creatoo.hn.services.admin.shop;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhQuestMapper;
import com.creatoo.hn.model.WhQuest;
import com.creatoo.hn.services.comm.CommService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 数字资源服务类
 * @author wangxl
 * @version 201611015
 */
@Service
public class QuestService {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public CommService commService;
	
	@Autowired
	public WhQuestMapper questMapper;
	
	/**
	 * 批量修改状态
	 * @param ids 资源标识，多个用逗号分隔
	 * @param fromState 修改之前的状态
	 * @param state 修改之后的状态
	 */
	public void updState(String ids, int fromState, int toState)throws Exception{
		if(ids != null){
			String[] idArr = ids.split(",");
			Date now = new Date();
			for(String id : idArr){
				if(id != null){
					Example example = new Example(WhQuest.class);
					example.createCriteria().andEqualTo("queid", id).andEqualTo("questate", fromState);
					
					WhQuest record = new WhQuest();
					record.setQuestate(toState);
					record.setQueopttime(now);
					this.questMapper.updateByExampleSelective(record, example);
				}
			}
		}
	}
	
	/**
	 * 添加调查问卷
	 * @param quest
	 * @throws Exception
	 */
	public void add(WhQuest quest)throws Exception{
		this.questMapper.insert(quest);
	}
	
	/**编辑调查问卷
	 * @param drsc
	 * @throws Exception
	 */
	public void edit(WhQuest quest)throws Exception{
		Example example = new Example(WhQuest.class);
		example.createCriteria().andEqualTo("queid", quest.getQueid()).andIn("questate", new ArrayList<Integer>(){
			private static final long serialVersionUID = 1L;
			{
			add(0);
			add(1);
			}
		});
		this.questMapper.updateByExampleSelective(quest, example);
	}
	
	/**
	 * 删除数字资源
	 * @param drsc
	 * @throws Exception
	 */
	public void delete(String id, String uploadPath)throws Exception{
		//删除数字资源
		Example example = new Example(WhQuest.class);
		example.createCriteria().andEqualTo("queid", id).andIn("questate", new ArrayList<Integer>(){
			private static final long serialVersionUID = 1L;
			{
			add(0);
			add(1);
			}
		});
		this.questMapper.deleteByExample(example);
	}
	
	/**
	 * 分页查询问卷调查
	 * @param param 参数条件
	 * @return 分页内容
	 * @throws Exception
	 */
	public Map<String, Object> srchPagging(Map<String, Object> param)throws Exception{
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		Example example = new Example(WhQuest.class);
		Criteria criteria = example.createCriteria();
		
		//排序
		if(param.containsKey("sort") && param.get("sort")!= null){
			StringBuffer sb = new StringBuffer((String)param.get("sort"));
			if(param.containsKey("order") && param.get("order") !=  null){
				sb.append(" ").append(param.get("order"));
			}
			example.setOrderByClause(sb.toString());
		}else{
			example.setOrderByClause("queid desc");
		}
		
		//列表标题
		if(param.containsKey("quetitle") && param.get("quetitle") != null){
			String quetitle = (String)param.get("quetitle");
			if(!"".equals(quetitle.trim())){
				criteria.andLike("drsctitle", "%"+quetitle.trim()+"%");
			}
		}
		
		//状态
		if(param.containsKey("questate") && param.get("questate") != null){
			String questate = (String)param.get("questate");
			if(!"".equals(questate)){
				criteria.andEqualTo("questate", questate);
			}
		}
		
		List<WhQuest> list = this.questMapper.selectByExample(example);
		
		// 取分页信息
        PageInfo<WhQuest> pageInfo = new PageInfo<WhQuest>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	};
}
