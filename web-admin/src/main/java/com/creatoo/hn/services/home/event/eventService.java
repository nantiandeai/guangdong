package com.creatoo.hn.services.home.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import com.creatoo.hn.mapper.ActivityMapper;
import com.creatoo.hn.mapper.WhActivityMapper;
import com.creatoo.hn.mapper.WhActivitybmMapper;
import com.creatoo.hn.mapper.WhActivityitmMapper;
import com.creatoo.hn.mapper.WhBrandActMapper;
import com.creatoo.hn.mapper.WhBrandMapper;
import com.creatoo.hn.mapper.WhCfgAdvMapper;
import com.creatoo.hn.mapper.WhCfgListMapper;
import com.creatoo.hn.mapper.WhEntsourceMapper;
import com.creatoo.hn.mapper.WhTagMapper;
import com.creatoo.hn.model.WhActivity;
import com.creatoo.hn.model.WhActivitybm;
import com.creatoo.hn.model.WhActivityitm;
import com.creatoo.hn.model.WhBrand;
import com.creatoo.hn.model.WhBrandAct;
import com.creatoo.hn.model.WhCfgAdv;
import com.creatoo.hn.model.WhCfgList;
import com.creatoo.hn.model.WhEntsource;
import com.creatoo.hn.model.WhTag;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 前台 活动业务层 
 * @author yanjianbo
 *
 */
@SuppressWarnings("all")
@Service
public class eventService {
	
	@Autowired
	private WhCfgListMapper whCfgListMapper;
	
	@Autowired
	private WhActivityMapper whActivityMapper;
	
	@Autowired
	private WhCfgAdvMapper whCfgAdvMapper;
	@Autowired
	private WhTagMapper whTagMapper;
	@Autowired
	private WhActivityitmMapper activityitmMapper;
	@Autowired
	private WhBrandActMapper whBrandActMapper;
	@Autowired
	private WhBrandMapper whBrandMapper;
	@Autowired
	private WhEntsourceMapper whEntsourceMapper;
	/**
	 * ActivityMapper 提取xml语句
	 */
	@Autowired
	private ActivityMapper activityMapper;
	@Autowired
	private WhActivitybmMapper bmMapper;
	/**
	 * 活动首页 加载 活动分类数据
	 * @param cfgenttype
	 * @return
	 */
	public List<WhCfgList> eventLoad(String cfgentclazz)throws Exception{
		Example example = new Example(WhCfgList.class);
		Criteria c = example.createCriteria();
		
		if(cfgentclazz!=null || "".equals(cfgentclazz)){
			c.andEqualTo("cfgentclazz",cfgentclazz);
		}
		c.andEqualTo("cfgstate",1);
		//实体类型
		c.andEqualTo("cfgenttype","2016101400000052");
		//实体类型分页
		c.andEqualTo("cfgpagetype","2016102900000006");
		//按 cfgshowidx 排序 取前三
		
		example.setOrderByClause("cfgshowidx" + " " + "desc");
		return this.whCfgListMapper.selectByExample(example);
	}
	/**
	 * 活动广告图
	 * @return
	 * @throws Exception
	 */
	public List<WhCfgAdv> advLoad()throws Exception{
		Example example = new Example(WhCfgAdv.class);
		example.createCriteria().andEqualTo("cfgadvpagetype", "2016102900000006").andEqualTo("cfgadvstate", 1);
		example.setOrderByClause("cfgadvidx" + " " + "desc");
		return this.whCfgAdvMapper.selectByExample(example);
	}
	/**
	 * 活动广告图
	 * @return
	 * @throws Exception
	 */
	public List<WhCfgAdv> advListLoad()throws Exception{
		Example example = new Example(WhCfgAdv.class);
		example.createCriteria().andEqualTo("cfgadvpagetype", "2016110800000001").andEqualTo("cfgadvstate", 1);
		example.setOrderByClause("cfgadvidx" + " " + "desc");
		return this.whCfgAdvMapper.selectByExample(example);
	}

	/**
	 * 活动首页 加载 活动品牌数据
	 * @return
	 * @throws Exception
	 */
	public List<WhCfgList> ppLoad()throws Exception{
		Example example = new Example(WhCfgList.class);
		Criteria c = example.createCriteria();
		//有效
		c.andEqualTo("cfgstate",1);
		//实体类型
		c.andEqualTo("cfgenttype","2016103100000001");
		//实体类型分页
		c.andEqualTo("cfgpagetype","2016102900000006");
		//按 cfgshowidx 排序 取前三
		example.setOrderByClause("cfgshowidx" + " " + "desc");
		return this.whCfgListMapper.selectByExample(example);
	}
	
	/***
	 * 活动列表页 加载相关数据
	 * @param request
	 * @return
	 */
	public Object eventList(int page, int rows,WebRequest request)throws Exception{
		Map<String, Object> params = ReqParamsUtil.parseRequest(request);
		String sdate=(String) params.get("sdate");
		String actvsdate2=(String) params.get("actvsdate2");
		//时间 搜索
		if(sdate!=null && !"".equals(sdate)){
			Date date=null;
			Date newdate=null;
			if(sdate.equals("1")){
				GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
				date=cal.getTime();
				params.put("date", date);
				cal.add((GregorianCalendar.MONTH), 1);
				newdate=cal.getTime();
				params.put("newdate", newdate);
			}else if(sdate.equals("11")){
				GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
				 date=cal.getTime();
				params.put("date", date);
				cal.add((GregorianCalendar.WEEK_OF_MONTH), 1);
				 newdate=cal.getTime();
				params.put("newdate", newdate);
			}else if(sdate.equals("3")){
				GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
				 date=cal.getTime();
				params.put("date", date);
				cal.add((GregorianCalendar.MONTH), 3);
				 newdate=cal.getTime();
				params.put("newdate", newdate);
			}else if(sdate.equals("12")){
				GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
				 date=cal.getTime();
				params.put("date", date);
				cal.add((GregorianCalendar.YEAR),1);
				 newdate=cal.getTime();
				params.put("newdate", newdate);
			}
			
		}
		//状态搜索
		if(actvsdate2!=null && !"".equals(actvsdate2)){
			Date date2=null;
			Date newdate2=null;
			//即将结束 系统时间 离开始时间 还有30天
			if(actvsdate2.equals("1")){
				GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
				date2=cal.getTime();
				params.put("date2", date2);
				cal.add((GregorianCalendar.MONTH), 1);
				newdate2=cal.getTime();
				params.put("newdate2", newdate2);
			}else if(actvsdate2.equals("2")){
				//已结束
				GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
				date2=cal.getTime();
				params.put("date3", date2);
			}else if(actvsdate2.equals("0")){
				//正在进行
				GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
				date2=cal.getTime();
				params.put("date4", date2);
			}
		}
		PageHelper.startPage(page, rows);
		List<Map> actlist = this.activityMapper.selecteventList(params);
		PageInfo pinfo = new PageInfo(actlist);

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("total", pinfo.getTotal());
		res.put("actlist", pinfo.getList());
		return res;
	}
	/*public Object eventList(int page, int rows,WebRequest request)throws Exception{
		String actvarea=request.getParameter("actvarea");
		String actvarttyp=request.getParameter("actvarttyp");
		String actvsdate=request.getParameter("actvsdate");
		String actvtype=request.getParameter("actvtype");
		String actvid=request.getParameter("actvid");
		Example example = new Example(WhActivity.class);
		Criteria c = example.createCriteria();
		
		if(actvarea!=null && !"".equals(actvarea)){
			c.andEqualTo("actvarea",actvarea);
		}
		if(actvarttyp!=null && !"".equals(actvarttyp)){
			c.andEqualTo("actvarttyp",actvarttyp);
		}
		if(actvarea!=null && !"".equals(actvarea)){
			c.andEqualTo("actvsdate",actvsdate);
		}
		if(actvtype!=null && !"".equals(actvtype)){
			c.andEqualTo("actvtype",actvtype);
		}
		if(actvid!=null && !"".equals(actvid)){
			c.andEqualTo("actvid",actvid);
		}
		
		PageHelper.startPage(page, rows);
		List<WhActivity> actlist = this.whActivityMapper.selectByExample(example);
		PageInfo pinfo = new PageInfo(actlist);

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("total", pinfo.getTotal());
		res.put("actlist", pinfo.getList());
		return res;
	}*/
	/**
	 * 根据 活动ID 查找详情
	 * @param actvid
	 * @return
	 * @throws Exception
	 */
	public WhActivity eventDetail(String actvid)throws Exception{
		return this.whActivityMapper.selectByPrimaryKey(actvid);
	}
	
	/**
	 * 根据主键id 查tag 标签 
	 * @param tagid
	 * @return
	 * @throws Exception
	 */
	public WhTag tagList(String tagid)throws Exception{
		return this.whTagMapper.selectByPrimaryKey(tagid);
	}
	
	/**
	 * 根据actvrefid 找 相关场次
	 * @param actvrefid
	 * @return
	 * @throws Exception
	 */
	public List<WhActivityitm> findActivityitm(String actvrefid)throws Exception{
		Example example = new Example(WhActivityitm.class);
		if(actvrefid!=null && !"".equals(actvrefid)){
			example.or().andEqualTo("actvrefid", actvrefid);
		}
		return this.activityitmMapper.selectByExample(example);
	}
	
	/**
	 * 查找品牌活动
	 * @param braid
	 * @return
	 */
	public WhBrand findDetail(String braid)throws Exception {
		
		return this.whBrandMapper.selectByPrimaryKey(braid);
	}
	/**
	 * 查找活动信息
	 * @param braid
	 * @return
	 */
	public List<Map> findBrandAct(String braid)throws Exception {
		return this.whBrandActMapper.selBrandinfo(braid);
	}
	/**
	 * 查找图片资源
	 * @param braid
	 * @return
	 */
	public List<WhEntsource> findZY(String braid) throws Exception{
		Example example = new Example(WhEntsource.class);
		Criteria c = example.createCriteria();
		String enttype = "2016101400000055";
		if(braid!=null && !"".equals(braid)){
			c.andEqualTo("refid", braid);
		}
		if(enttype!=null && !"".equals(enttype)){
			c.andEqualTo("enttype", enttype);
		}
		return this.whEntsourceMapper.selectByExample(example);
	}
	
	/**
	 * 活动详情页 往期回顾
	 * @param request
	 * @return
	 */
	public Object wqList(String actvtype,String actvid)throws Exception{
		List<Map> list_rtn = new ArrayList<Map>();
		
		//查询最多8条往期品牌活动
		PageHelper.startPage(1, 8);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("actvid", actvid);
		List<Map> list = this.activityMapper.selectwqActtype(params);
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);
        long cnt = pageInfo.getTotal();
        
        if(cnt > 0){
        	list_rtn = pageInfo.getList();
        }
        
        if(cnt < 8){
        	cnt = 8-cnt;
        	PageHelper.startPage(1, (int)cnt);
        	Map<String, Object> map = new HashMap<String, Object>();
        	map.put("actvitmedate", new Date());
			map.put("actvtype", actvtype);
			map.put("actvid", actvid);
			List<Map> list2 = this.activityMapper.selectwqAct(map);
			PageInfo<Map> pageInfo2 = new PageInfo<Map>(list2);
			
			if(pageInfo2.getTotal() > 0){
				for(Map _m : pageInfo2.getList()){
					list_rtn.add(_m);
				}
			}
        }
        System.out.println("==========list_rtn===="+list_rtn.size());
        return list_rtn;
	}	
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("actvid", actvid);
//		List<Map> list=this.activityMapper.selectwqActtype(params);
//		if (list.size() <= 8) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			// 少于8条 加 同类 已过的活动
//			map.put("actvitmedate", new Date());
//			map.put("actvtype", actvtype);
//			List<Map> list2 = this.activityMapper.selectwqAct(map);
//			for (int i = list.size() - 1; i < 8; i++) {
//				list.add(list2.get(i));
//			}
//		} else {
//			// 大于 8条 romve
//			for (int i = 8; i < list.size(); i++) {
//				list.remove(i);
//			}
//		}
//		return list;

        
		
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("actvid", actvid);
//		List<Map> list=this.activityMapper.selectwqActtype(params);
//		if (list.size() <= 8) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			// 少于8条 加 同类 已过的活动
//			map.put("actvitmedate", new Date());
//			map.put("actvtype", actvtype);
//			List<Map> list2 = this.activityMapper.selectwqAct(map);
//			for (int i = list.size() - 1; i < 8; i++) {
//				list.add(list2.get(i));
//			}
//		} else {
//			// 大于 8条 romve
//			for (int i = 8; i < list.size(); i++) {
//				list.remove(i);
//			}
//		}
//		return list;
//	}
	/**
	 * 查找视频资源
	 * @param braid
	 * @return
	 */
	public List<WhEntsource> findspZY(String braid)throws Exception {
		Example example = new Example(WhEntsource.class);
		Criteria c = example.createCriteria();
		String enttype = "2016101400000056";
		if(braid!=null && !"".equals(braid)){
			c.andEqualTo("refid", braid);
		}
		if(enttype!=null && !"".equals(enttype)){
			c.andEqualTo("enttype", enttype);
		}
		return this.whEntsourceMapper.selectByExample(example);
	}
	/**
	 * 找到当前活动
	 * @param curActid
	 * @return 
	 */
	public List<WhBrandAct> findCurAct(String curActid)throws Exception {
		Example example = new Example(WhBrandAct.class);
		Criteria c = example.createCriteria();
		if(curActid!=null && !"".equals(curActid)){
			c.andEqualTo("bracactid", curActid);
		}
		return this.whBrandActMapper.selectByExample(example);
	}
	/**
	 * 查找活动资讯
	 * @return
	 */
	public List<Map> selActZX()throws Exception {
		
		return this.whBrandActMapper.selZX();
	}
	/**
	 * 找到所有的品牌活动
	 * @return
	 */
	public List<WhBrand> findBrandList()throws Exception {
		return this.whBrandMapper.selectAll();
	}
	/**
	 * 分页
	 */
	public Map<String, Object> findList(Map<String, Object> param)throws Exception {
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.whBrandMapper.selBrandPage(param);
		
		// 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
		//return (Map<String, Object>) this.whBrandMapper.selectAll();
	}
	
	/**
	 * 根据 活动场次id 查找 余票/人数
	 * @param actvitmid
	 * @return
	 */
	public Object selectCount(String actvitmid){
		WhActivityitm wActivityitm = this.activityitmMapper.selectByPrimaryKey(actvitmid);
		//报名 最大人数
		int maxpeople = wActivityitm.getActvitmdpcount();
		
		//已报名人数 
		Example example = new Example(WhActivitybm.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("actshstate", "1");
		c.andEqualTo("actbmstate", 1);
		c.andEqualTo("actvitmid",actvitmid);
		List<WhActivitybm> listbm = this.bmMapper.selectByExample(example);
		//已报名 总数
		int sum=0;
		for(int i = 0; i < listbm.size(); i++){
			//获取 每个 用户的报名人数
			sum+=listbm.get(i).getActbmcount();
		}
		//获取 余票数
		int leavecount=maxpeople-sum;
		return leavecount;
	}
	
}
