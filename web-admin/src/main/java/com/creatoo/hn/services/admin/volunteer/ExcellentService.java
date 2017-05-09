package com.creatoo.hn.services.admin.volunteer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.creatoo.hn.services.comm.CommService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhZyfcZuzhiMapper;
import com.creatoo.hn.model.WhCfgList;
import com.creatoo.hn.model.WhZyfcZuzhi;
import com.creatoo.hn.model.WhZyhd;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.servlet.http.HttpServletRequest;

@Service
public class ExcellentService {
	@Autowired
	private WhZyfcZuzhiMapper whZyfcZuzhiMapper;
	@Autowired
	public CommService commService;
	/**
	 * 查询志愿者风采优秀组织者
	 * @param
	 * @return
	 */
	public Map<String, Object> select(Map<String, Object> param) {
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
				
		PageHelper.startPage(page, rows);
		Example example = new Example(WhZyfcZuzhi.class);
		Criteria criteria = example.createCriteria();
		//状态
		if(param.containsKey("zyfczzstate") && param.get("zyfczzstate") != null){
			String zyfczzstate = (String)param.get("zyfczzstate");
			if(!"".equals(zyfczzstate.trim())){
			 criteria.andEqualTo("zyfczzstate", zyfczzstate);
			}
		}
		example.setOrderByClause("zyfczzopttime desc");
		List<WhZyfcZuzhi> list = this.whZyfcZuzhiMapper.selectByExample(example);
		
		PageInfo<WhZyfcZuzhi> pageInfo = new PageInfo<WhZyfcZuzhi>(list);
	    Map<String, Object> rtnMap = new HashMap<String, Object>();
	    rtnMap.put("total", pageInfo.getTotal());
	    rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}
	/**
	 * 添加志愿者风采组织者
	 * @param whzz
	 */
	public void addexce(WhZyfcZuzhi whzz) {
		this.whZyfcZuzhiMapper.insert(whzz);
	}

	/**
	 * 添加或修改组织
	 * @param whzz
	 * @throws Exception
     */
	public void addOrUpdate(WhZyfcZuzhi whzz) throws Exception {
		if (whzz.getZyfczzid() != null &&!"".equals(whzz.getZyfczzid())) {
			this.whZyfcZuzhiMapper.updateByPrimaryKeySelective(whzz);
		} else {
			whzz.setZyfczzid(this.commService.getKey("WhZyfcZuzhi"));
			whzz.setZyfczzstate(0);//新增默认状态0:为可编辑
			whzz.setZyfczzopttime(new Date());
			this.whZyfcZuzhiMapper.insertSelective(whzz);
		}
	}

	/**
	 * 查询单条记录
	 * @param zyfczzid
     * @return
     */
	public WhZyfcZuzhi seach(String zyfczzid) {
		return this.whZyfcZuzhiMapper.selectByPrimaryKey(zyfczzid);
	}
	/**
	 * 修改志愿组织者
	 * @param whzz
	 */
	public void updataexce(WhZyfcZuzhi whzz) {
		this.whZyfcZuzhiMapper.updateByPrimaryKeySelective(whzz);
		
	}
	/**
	 * 根据主键删除
	 * @param zyfczzid
	 */
	public void delexc(String zyfczzid) {
		this.whZyfcZuzhiMapper.deleteByPrimaryKey(zyfczzid);
		
	}
	/**
	 * 审核状态改变
	 * @param zyfczzid
	 * @param fromstate
	 * @param tostate
	 * @return
	 */
	public Object checkexce(String zyfczzid, int fromstate, int tostate) {
		WhZyfcZuzhi whzz = new WhZyfcZuzhi();
		whzz.setZyfczzstate(tostate);
		whzz.setZyfczzopttime(new Date());
		Example example = new Example(WhZyfcZuzhi.class);
		example.createCriteria().andEqualTo("zyfczzid", zyfczzid).andEqualTo("zyfczzstate", fromstate);
		return this.whZyfcZuzhiMapper.updateByExampleSelective(whzz, example);
	}
	/**
	 * 批量审核状态改变
	 * @param
	 * @param fromstate
	 * @param tostate
	 * @return
	 */
	public Object checkAllzyzz(String zyfczzids, int fromstate, int tostate) {
		List<String> list = new ArrayList<String>();
		String[] hdid = zyfczzids.split(",");
		for (int i = 0; i < hdid.length; i++) {
			list.add(hdid[i]);
		}
		WhZyfcZuzhi whzz = new WhZyfcZuzhi();
		whzz.setZyfczzstate(tostate);
		whzz.setZyfczzopttime(new Date());
		Example example = new Example(WhZyfcZuzhi.class);
		example.createCriteria().andIn("zyfczzid", list).andEqualTo("zyfczzstate", fromstate);
		return this.whZyfcZuzhiMapper.updateByExampleSelective(whzz, example);
	}

}
