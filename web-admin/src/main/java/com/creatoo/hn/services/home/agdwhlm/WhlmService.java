package com.creatoo.hn.services.home.agdwhlm;

import com.creatoo.hn.ext.emun.EnumBizState;
import com.creatoo.hn.ext.emun.EnumDelState;
import com.creatoo.hn.ext.emun.EnumState;
import com.creatoo.hn.mapper.*;
import com.creatoo.hn.mapper.admin.CrtWhgSysCultMapper;
import com.creatoo.hn.model.*;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.services.comm.CommService;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 文化联盟
 * @author wangxl
 * @version 2016.11.16
 */
@Service
public class WhlmService {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * 公用服务类
	 */
	@Autowired
	public CommService commservice;

	/**
	 * 文化馆DAO
	 */
	@Autowired
	public WhgSysCultMapper whgSysCultMapper;

	/**
	 * 场馆DAO
	 */
	@Autowired
	public WhgVenMapper whgVenMapper;

	/**
	 * 培训DAO
	 */
	@Autowired
	public WhgTraMapper whgTraMapper;

	/**
	 * 活动DAO
	 */
	@Autowired
	public WhgActActivityMapper whgActActivityMapper;

	/**
	 * 资讯DAO
	 */
	@Autowired
	public WhZxColinfoMapper whZxColinfoMapper;

	/**
	 * 文化联盟DAO
	 */
	@Autowired
	public CrtWhgSysCultMapper crtWhgSysCultMapper;

	/**
	 * 分页查询文化馆列表
	 * @param request 请求对象
	 * @param cult 查询条件
	 * @return 指定页的文化馆列表
	 * @throws Exception
	 */
	public PageInfo<Map> t_search4p(HttpServletRequest request, WhgSysCult cult)throws  Exception{
		//分页信息
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);
		int page = ReqParamsUtil.getPage(request, 1);
		int rows = ReqParamsUtil.getRows(request, 10);

/*		//搜索条件
		Example example = new Example(WhgSysCult.class);
		Example.Criteria c = example.createCriteria();
		c.andEqualTo("state", EnumState.STATE_YES.getValue());
		c.andEqualTo("delstate", EnumDelState.STATE_DEL_NO.getValue());

		//名称条件
		if(cult != null && cult.getName() != null){
			c.andLike("name", "%"+cult.getName()+"%");
			cult.setName(null);
		}

		//其它条件
		c.andEqualTo(cult);


		//排序
		if(paramMap.containsKey("sort") && paramMap.get("sort")!= null){
			StringBuffer sb = new StringBuffer((String)paramMap.get("sort"));
			if(paramMap.containsKey("order") && paramMap.get("order") !=  null){
				sb.append(" ").append(paramMap.get("order"));
			}
			example.setOrderByClause(sb.toString());
		}else{
			example.setOrderByClause("idx, crtdate desc");
		}*/

		//分页查询
		PageHelper.startPage(page, rows);
		//List<WhgSysCult> list = this.whgSysCultMapper.selectByExample(example);
		List<Map> list = this.crtWhgSysCultMapper.srchListCult(paramMap);
		PageInfo<Map> pageInfo = new PageInfo<Map>(list);
		return pageInfo;
	}

	/**
	 * 查询文化馆详情
	 * @param id 文化馆ID
	 * @return yywx 文化馆详细
	 * @throws Exception
	 */
	public WhgSysCult findCult(String id)throws Exception{
		WhgSysCult cult = new WhgSysCult();
		cult.setId(id);
		return this.whgSysCultMapper.selectOne(cult);
	}

	/**
	 * 取文化馆的通知公告信息
	 * @param id 文化馆ID
	 * @return 分馆最新发布的通知公告列表
	 * @throws Exception
	 */
	public List<WhZxColinfo> findNotice4Cult(String id)throws  Exception{
		Example example = new Example(WhZxColinfo.class);
		Example.Criteria c = example.createCriteria();
		c.andEqualTo("clnfstata", 3); //发布状态
		//c.andEqualTo("clnvenueid", id); //所属分馆
		example.setOrderByClause("clnfopttime desc");//发布时间排序
		PageHelper.startPage(1, 4);
		List<WhZxColinfo> list = this.whZxColinfoMapper.selectByExample(example);
		PageInfo<WhZxColinfo> pageInfo = new PageInfo<WhZxColinfo>(list);
		return pageInfo.getList();
	}

	/**
	 * 查询分馆活动列表：按发布时间取最新发布的4条记录
	 * @param id 分馆Id
	 * @return 分馆最新发布的活动列表
	 * @throws Exception
	 */
	public List<WhgActActivity> findActivity4Cult(String id)throws Exception{
		Example example = new Example(WhgActActivity.class);
		Example.Criteria c = example.createCriteria();
		c.andEqualTo("state", EnumBizState.STATE_PUB.getValue());
		c.andEqualTo("cultid", id);
		example.setOrderByClause("statemdfdate desc");
		PageHelper.startPage(1, 4);
		List<WhgActActivity> list = this.whgActActivityMapper.selectByExample(example);
		PageInfo<WhgActActivity> pageInfo = new PageInfo<WhgActActivity>(list);
		return pageInfo.getList();
	}

	/**
	 * 查询分馆培训列表：按发布时间取最新发布的4条记录
	 * @param id 分馆Id
	 * @return 分馆最新发布的培训列表
	 * @throws Exception
	 */
	public List<WhgTra> findTrain4Cult(String id)throws Exception{
		Example example = new Example(WhgTra.class);
		Example.Criteria c = example.createCriteria();
		c.andEqualTo("state", EnumBizState.STATE_PUB.getValue());
		c.andEqualTo("cultid", id);
		example.setOrderByClause("statemdfdate desc");
		PageHelper.startPage(1, 4);
		List<WhgTra> list = this.whgTraMapper.selectByExample(example);
		PageInfo<WhgTra> pageInfo = new PageInfo<WhgTra>(list);
		return pageInfo.getList();
	}

	/**
	 * 查询分馆场馆列表：按发布时间取最新发布的4条记录
	 * @param id 分馆Id
	 * @return 分馆最新发布的场馆列表
	 * @throws Exception
	 */
	public List<WhgVen> findVenue4Cult(String id)throws Exception{
		Example example = new Example(WhgVen.class);
		Example.Criteria c = example.createCriteria();
		c.andEqualTo("state", EnumBizState.STATE_PUB.getValue());
		c.andEqualTo("cultid", id);
		example.setOrderByClause("statemdfdate desc");
		PageHelper.startPage(1, 4);
		List<WhgVen> list = this.whgVenMapper.selectByExample(example);
		PageInfo<WhgVen> pageInfo = new PageInfo<WhgVen>(list);
		return pageInfo.getList();
	}
	
}
