package com.creatoo.hn.services.admin.volunteer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhZyfcXiangmuMapper;
import com.creatoo.hn.model.WhCfgList;
import com.creatoo.hn.model.WhZyfcXiangmu;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class ProjectService {
	@Autowired
	private WhZyfcXiangmuMapper whZyfcXiangmuMapper;
	/**
	 * 查询展示项目信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> select(Map<String, Object> param)throws Exception {
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
				
		PageHelper.startPage(page, rows);
		Example example = new Example(WhZyfcXiangmu.class);
		Criteria criteria = example.createCriteria();
		//状态
		if(param.containsKey("zyfcxmstate") && param.get("zyfcxmstate") != null){
			String zyfcxmstate = (String)param.get("zyfcxmstate");
			if(!"".equals(zyfcxmstate.trim())){
			 criteria.andEqualTo("zyfcxmstate", zyfcxmstate);
			}
		}	
		example.setOrderByClause("zyfcxmid desc");//("zyfcxmopttime desc");
		
		List<WhZyfcXiangmu> list = this.whZyfcXiangmuMapper.selectByExample(example);
		
		PageInfo<WhZyfcXiangmu> pageInfo = new PageInfo<WhZyfcXiangmu>(list);
	    Map<String, Object> rtnMap = new HashMap<String, Object>();
	    rtnMap.put("total", pageInfo.getTotal());
	    rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}
	/**
	 * 添加展示项目
	 * @param whxm
	 */
	public void addpro(WhZyfcXiangmu whxm)throws Exception {
		this.whZyfcXiangmuMapper.insertSelective(whxm);
		
	}
	/**
	 * 更新展示项目
	 * @param whxm
	 */
	public void upProj(WhZyfcXiangmu whxm)throws Exception {
		this.whZyfcXiangmuMapper.updateByPrimaryKeySelective(whxm);
		
	}

	/**
	 * 根据ID查询示范项目
	 * @param id 示范项目ID
	 * @return 示范项目详情
	 * @throws Exception
	 */
	public WhZyfcXiangmu getXiangmu(String id)throws Exception{
		WhZyfcXiangmu xm = new WhZyfcXiangmu();
		xm.setZyfcxmid(id);
		return this.whZyfcXiangmuMapper.selectOne(xm);
	}

	/**
	 * 根据主键删除展示项目
	 * @param zyfcxmid
	 */
	public void delpro(String zyfcxmid)throws Exception {
		this.whZyfcXiangmuMapper.deleteByPrimaryKey(zyfcxmid);
		
	}
	/**
	 * 改变审核状态
	 * @param zyfcxmid
	 * @param fromstate
	 * @param tostate
	 * @return
	 */
	public Object checkexce(String zyfcxmid, int fromstate, int tostate)throws Exception {
		WhZyfcXiangmu whxm = new WhZyfcXiangmu();
		whxm.setZyfcxmstate(tostate);
		whxm.setZyfcxmopttime(new Date());
		Example example = new Example(WhZyfcXiangmu.class);
		example.createCriteria().andEqualTo("zyfcxmid", zyfcxmid).andEqualTo("zyfcxmstate", fromstate);
		return this.whZyfcXiangmuMapper.updateByExampleSelective(whxm, example);
	}
	/**
	 * 批量改变审核状态
	 * @param zyfcxmids
	 * @param fromstate
	 * @param tostate
	 * @return
	 */
	public Object checkAllzyzz(String zyfcxmids, int fromstate, int tostate)throws Exception {
		List<String> list = new ArrayList<String>();
		String[] hdid = zyfcxmids.split(",");
		for (int i = 0; i < hdid.length; i++) {
			list.add(hdid[i]);
		}
		WhZyfcXiangmu whxm = new WhZyfcXiangmu();
		whxm.setZyfcxmstate(tostate);
		whxm.setZyfcxmopttime(new Date());
		Example example = new Example(WhZyfcXiangmu.class);
		example.createCriteria().andIn("zyfcxmid", list).andEqualTo("zyfcxmstate", fromstate);
		return this.whZyfcXiangmuMapper.updateByExampleSelective(whxm, example);
	}

}
