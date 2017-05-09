package com.creatoo.hn.actions.wap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.creatoo.hn.mapper.ActivityMapper;
import com.creatoo.hn.mapper.WhActivityitmMapper;
import com.creatoo.hn.mapper.WhCollectionMapper;
import com.creatoo.hn.mapper.WhTypMapper;
import com.creatoo.hn.mapper.WhUserMapper;
import com.creatoo.hn.mapper.WhVenuetimeMapper;
import com.creatoo.hn.model.WhActivitybm;
import com.creatoo.hn.model.WhActivityitm;
import com.creatoo.hn.model.WhCollection;
import com.creatoo.hn.model.WhComment;
import com.creatoo.hn.model.WhTyp;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.model.WhVenuetime;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.home.event.eventService;
import com.creatoo.hn.services.home.user.RegistService;
import com.creatoo.hn.services.wap.WapActivityService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 活动微信端接口控制层
 * @author lenovo
 *
 */
@Controller
@RequestMapping("/wap/wechat/")
public class WebActivityAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * 活动自写xml语句
	 */
	@Autowired
	private ActivityMapper activityMapper;
	
	/**
	 * 前台活动service
	 */
	@Autowired
	private eventService eventService;
	
	
	/**
	 * 收藏mapper
	 */
	@Autowired
	private WhCollectionMapper whCollectionMapper;
	
	@Autowired
	private WhActivityitmMapper itemMapper;
	
	@Autowired
	private WhTypMapper typeMapper;
	
	@Autowired
	private RegistService regService;
	
	@Autowired
	private WhVenuetimeMapper venitmMapper;
	
	@Autowired
	public CommService commService;
	
	@Autowired
	private WapActivityService actService;
	
	@Autowired
	private WhUserMapper whUserMapper;
	
	/**
	 * 文化活动详情
	 */
	@ResponseBody
	@RequestMapping(value="activity/detail",method=RequestMethod.GET)
	public Object getActList(WebRequest request){
		Map<String,Object> rtnMap = new HashMap<String,Object>();
		Map<String,Object> actMap = new HashMap<String,Object>();
		Map<String,Object> repliesMap = new HashMap<String,Object>();
		Map<String,Object> ticketsMap = null;
		Map<String,Object> aspectsMap = new HashMap<String,Object>();
		Map<String,Object> aspectsMap1 = new HashMap<String,Object>();
		Map<String,Object> aspectsMap2= new HashMap<String,Object>();
		Map<String,Object> commentMap = new HashMap<String,Object>();
		
		
		//获取请求参数
		Map<String,Object> params = ReqParamsUtil.parseRequest(request);
//		String openId = request.getParameter("openId");
		String itemId = request.getParameter("itemId");
		//查询活动信息
		List<Map> list = this.activityMapper.selectfavact(itemId);
		List<Map<String,Object>> aspectsList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> ticketsList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> commentList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> repliesList = new ArrayList<Map<String,Object>>();
   	 	
		String tagstr = "";
		String tagfg = "";
		String actitmid = "";
		String uid = "";
		try {
			//是否需要上传附件
			/*if (!list.get(0).get("actvisattach").equals(null)) {
				int att = (int) list.get(0).get("actvisattach");
		   		if (att == 0) {
		   			list.get(0).put("actvisattach", "否");
				}
			    if (att == 1) {
			    	list.get(0).put("actvisattach", "是");
			    }
			}*/
			
			//处理活动标签
		    if(list.size() > 0){
		        	String scopes = "";
					String _sp = "";
					for(Map m : list){
						String scope = (String)m.get("actvtags");
						if(scope != null && !"".equals(scope) && !"null".equalsIgnoreCase(scope)){
							scopes += _sp+scope;
							_sp = ",";
						}
						
						if(m.get("tag") != null && !"".equals(m.get("tag"))){
							String tags[] = ((String) m.get("tag")).split(",");
							for (int i = 0; i < tags.length; i++) {
								tagfg =",";
								tagstr = tagfg + this.eventService.tagList(tags[i]).getName();
							}
						}
						
						m.put("tag", tagstr.toString());
						//根据条件openId查询用户信息
//						Example example = new Example(WhUser.class);
//						Criteria c = example.createCriteria();
//						c.andEqualTo("wxopenid", openId);
//						List<WhUser> whuser = this.whUserMapper.selectByExample(example);
//						if(whuser != null && whuser.size() > 0 ){
//							String id = whuser.get(0).getId();
//						}
						String actvid = (String) list.get(0).get("actvid");
						Example example2 = new Example(WhCollection.class);
						Criteria c2 = example2.createCriteria();
						c2.andEqualTo("cmrefid", (String)m.get("itemId"));
						c2.andEqualTo("cmopttyp", 0);
						int coll = this.whCollectionMapper.selectCountByExample(example2);
						if (coll > 0) {
							list.get(0).put("favorite", "是");
						}else{
							list.get(0).put("favorite", "否");
						}
						
					}
					
		    }
		    list.get(0).put("voted", "否");
		    
		    //tickets
			List<WhActivityitm> actitmList = this.actService.selectActitm(itemId);
			if(actitmList != null && actitmList.size() > 0 ){
				for (WhActivityitm whActivityitm : actitmList) {
					ticketsMap = new HashMap<String,Object>();
					actitmid = actitmList.get(0).getActvitmid();
					ticketsMap.put("begin", actitmList.get(0).getActvitmsdate());
					actMap.put("begin", actitmList.get(0).getActvitmsdate()) ;
					actMap.put("end", actitmList.get(0).getActvitmedate()) ;
					ticketsMap.put("end", actitmList.get(0).getActvitmedate());
					ticketsMap.put("itemId", actitmList.get(0).getActvitmid());
					ticketsList.add(ticketsMap);
				}
			}else{
				ticketsMap = new HashMap<String,Object>();
				ticketsMap.put("begin", null);
				ticketsMap.put("end", null);
				ticketsMap.put("itemId",null);
				ticketsList.add(ticketsMap);
			}
			Integer left = (Integer) this.eventService.selectCount(actitmid);
			ticketsMap.put("left", left);
			
		    //aspects
		    aspectsMap.put("title", "活动详情介绍");
		    aspectsMap.put("content", list.get(0).get("actvdetail"));
		    aspectsMap1.put("title", "活动介绍");
		    aspectsMap1.put("content", list.get(0).get("actvintroduce"));
		    aspectsMap2.put("title", "报名介绍");
		    aspectsMap2.put("content", list.get(0).get("actvenroldesc"));
		    aspectsList.add(aspectsMap);
		    aspectsList.add(aspectsMap1);
		    aspectsList.add(aspectsMap2);
		    
		    //comments
//			List<WhUser> whuser = this.regService.getWxUser(openId);
//			if(whuser != null && whuser.size() > 0){
//				uid = whuser.get(0).getId();
//				
//			}
//			List<WhComment> commList = this.actService.selectComm(uid);
//			if(commList != null && commList.size() > 0){
//				for (WhComment whComment : commList) {
//					if(whComment.getRmtyp() == 0){
//						//评论
//						commentMap.put("userId", uid);
//						commentMap.put("itemId", whComment.getRmid());
//						commentMap.put("time", whComment.getRmdate());
//						//commentMap.put("nickName", whuser.get(0).getNickname());
//						commentMap.put("content", whComment.getRmcontent());
//						commentList.add(commentMap);
//					}else if(whComment.getRmtyp() == 1){
//						repliesMap.put("userId", uid);
//						repliesMap.put("itemId", whComment.getRmid());
//						repliesMap.put("time", whComment.getRmdate());
////						repliesMap.put("nickName", whuser.get(0).getNickname());
//						repliesMap.put("content", whComment.getRmcontent());
//						repliesList.add(repliesMap);
//						
//					}
//				}
//			}
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this._resolveException2Map(e.getMessage(), rtnMap);
		}
		
		if(list.size() > 0) {
			actMap.put("itemId", list.get(0).get("itemId")) ;
			actMap.put("title", list.get(0).get("title")) ;
			actMap.put("image", list.get(0).get("image")) ;
			actMap.put("address", list.get(0).get("address")) ;
			actMap.put("thumb", list.get(0).get("thumb")) ;
			actMap.put("phone", list.get(0).get("phone")) ;
			actMap.put("intro", list.get(0).get("intro")) ;
			actMap.put("district", list.get(0).get("district")) ;
			actMap.put("voted", list.get(0).get("voted")) ;
			actMap.put("typeId", list.get(0).get("typeId")) ;
			actMap.put("tag", list.get(0).get("tag")) ;
			actMap.put("favorite", list.get(0).get("favorite")) ;
		}
		
        //actMap.put("list", list);
		
		actMap.put("aspects", aspectsList);
		actMap.put("tickets", ticketsList);
		actMap.put("comments", commentList);
		commentMap.put("replies", repliesList);
		
		rtnMap.put("code", "0");
		rtnMap.put("msg", "");
		rtnMap.put("data", actMap);
		return rtnMap;
	}
	
	
	
	/**
	 * 文化活动预约
	 */
	@ResponseBody
	@RequestMapping(value="activity/booking/{openId}/{itemId}/{timeId}",method=RequestMethod.POST)
	public Object activityOrder(@PathVariable String openId,@PathVariable String itemId,@PathVariable String timeId,HttpServletRequest request){
		Map<String,Object> rtnMap = new HashMap<String,Object>();
		Map<String,Object> orderMap = new HashMap<String,Object>();
		WhActivitybm whabm = new WhActivitybm();
		String isPer = "";
		String uid = "";

		try {
			//验证参数
			this._paramsOrder(openId, itemId, timeId);
			//获取用户信息
			List<WhUser> whuser = this.regService.getWxUser(openId);
			if(whuser != null && whuser.size() > 0){
				Integer isperfect = whuser.get(0).getIsperfect();
				uid = whuser.get(0).getId();
				isPer = isperfect+"";
			}
			String id = this.commService.getKey("whuser");
			whabm.setActbmid(id);
			whabm.setActbmuid(uid);
			whabm.setActid(itemId);
			whabm.setActvitmid(timeId);
			whabm.setActbmisa(isPer);
			whabm.setActbmtype(2);
			whabm.setActbmisb("未");
			whabm.setActshstate("未");
			
			this.actService.saveActivityOrder(whabm);
			rtnMap.put("code","0");
			rtnMap.put("msg", "");
			rtnMap.put("data", null);
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this._resolveException2Map(e.getMessage(), rtnMap);

		}
		return rtnMap;
	}
	
	
	/**
	 * 我的活动
	 * @param requset
	 * @return
	 */
	@ResponseBody
	@RequestMapping("user/activity")
	public Object myInfo(WebRequest requset){
		Map<String,Object> rtnMap = new HashMap<String,Object>();
		Map<String,Object> infoMap = new HashMap<String,Object>();
		Map<String,Object> resMap = new HashMap<String,Object>();
		//Map<String,Object> newinfo = new HashMap<String,Object>();
		List<Map> listArray = new ArrayList<Map>();
		//获取请求参数
		Map<String, Object> params = ReqParamsUtil.parseRequest(requset);
		String openId = (String)params.get("openId");
		Integer index = (Integer)params.get("index");
		Integer size = (Integer)params.get("size");
		try {
			if(openId == null || "".equals(openId)){
				throw new Exception("<[code:1000]><[msg:用户标识为空]>");
			}
			if(size == null || "".equals(size)){
				size = 10;
			}
			if(index == null || "".equals(index)){
				index = 1;
			}
			PageHelper.startPage(index, size);
			List<Map> list = this.activityMapper.selectMywxevent(params);
			PageInfo info = new PageInfo(list);
			
			//活动标签  替换key
			String tagstr = "";
			String tagfg = "";
			Example example = new Example(WhCollection.class);
			Example example2 = new Example(WhCollection.class);
			if(list != null && list.size()>0){
				for (Map map : list) {
					Map<String,Object> newinfo = new HashMap<String,Object>();
					newinfo.put("itemId", map.get("actvid"));
					newinfo.put("title", map.get("actvshorttitle"));
					newinfo.put("intro", map.get("actvintroduce"));
					newinfo.put("thumb", map.get("actvbigpic"));
					newinfo.put("image", map.get("actvpic"));
					newinfo.put("phone", map.get("actphone"));
					newinfo.put("venue", map.get("actcg"));
					newinfo.put("address", map.get("actvaddress"));
					newinfo.put("begin", map.get("actvitmsdate"));
					newinfo.put("end", map.get("actvitmedate"));
					//处理活动标签
					if(map.get("actvtags") != null && !"".equals(map.get("actvtags"))){
						String tags[] = ((String) map.get("actvtags")).split(",");
						for (int i = 0; i < tags.length; i++) {
							tagfg =",";
							tagstr = tagfg + this.eventService.tagList(tags[i]).getName();
						}
					}
					newinfo.put("tag", tagstr.toString());
					Criteria c = example.createCriteria();
					c.andEqualTo("cmrefid", (String)map.get("actvid"));
					c.andEqualTo("cmopttyp", 0);
					int coll = this.whCollectionMapper.selectCountByExample(example);
					if (coll > 0) {
						newinfo.put("favorite", true);
					}else{
						newinfo.put("favorite", false);
					}
					Criteria c2 = example2.createCriteria();
					c2.andEqualTo("cmrefid", (String)map.get("actvid"));
					c2.andEqualTo("cmopttyp", 2);
					int coll2 = this.whCollectionMapper.selectCountByExample(example2);
					newinfo.put("voteUp", coll2);
					newinfo.put("voted", false);
					listArray.add(newinfo);
				}
			}
			
			
			infoMap.put("list", listArray);
			//当前页码的数据条数
			rtnMap.put("count", info.getSize());
			//当前请求的每页数据条数
			rtnMap.put("size", info.getPageSize());
			//总共数据条数
			rtnMap.put("total", info.getTotal());
			//当前页码
			rtnMap.put("index", info.getPageNum());
			
			infoMap.put("pager",rtnMap);

			resMap.put("code", 0);
			resMap.put("msg", "");
			resMap.put("data",infoMap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			this._resolveException2Map(e.getMessage(), resMap);
		}
		return resMap;
	}
	
	/** 
	 * 我的收藏
	 * @param requset
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/user/favorite")
	public Object myFavorite(WebRequest requset){
		Map<String,Object> resMap = new HashMap<String,Object>();
		Map<String,Object> rtnMap = new HashMap<String,Object>();
		Map<String,Object> infoMap = new HashMap<String,Object>();
		List<Map> listArray = new ArrayList<Map>();
		
		Map<String, Object> params = ReqParamsUtil.parseRequest(requset);
		String openId = (String)params.get("openId");
		Integer index = (Integer)params.get("index");
		Integer size = (Integer)params.get("size");
		//根据用户查收藏记录
		try {
			if(openId == null || "".equals(openId)){
				throw new Exception("<[code:1000]><[msg:用户标识为空]>");
			}
			if(size == null || "".equals(size)){
				size = 10;
			}
			if(index == null || "".equals(index)){
				index = 1;
			}
			Example example = new Example(WhCollection.class);
			Criteria c = example.createCriteria();
			c.andEqualTo("cmuid", openId);
			c.andEqualTo("cmopttyp", "0");
			PageHelper.startPage(index, size);
			List<WhCollection> list = this.whCollectionMapper.selectByExample(example);
			PageInfo info = new PageInfo<>(list);
			//收藏list
			List<WhCollection> listcoll = info.getList();
			//活动标签  替换key
			String tagstr = "";
			String tagfg = "";
			for (WhCollection obj : listcoll) {
				//关联类型
				String cmreftyp = obj.getCmreftyp();
				String cmrefid = obj.getCmrefid();
				if(cmreftyp != null && !"".equals(cmreftyp)){
					if("2016101400000052".equals(cmreftyp) && cmrefid != null){//1. 文化活动
						List<Map> map = this.activityMapper.selectfavact(cmrefid);
						if(map != null && map.size() > 0){
							//处理活动标签
							if(map.get(0).get("tag") != null && !"".equals(map.get(0).get("tag"))){
								String tags[] = ((String) map.get(0).get("tag")).split(",");
								for (int i = 0; i < tags.length; i++) {
									tagfg =",";
									tagstr = tagfg + this.eventService.tagList(tags[i]).getName();
								}
							}
							map.get(0).put("title", obj.getCmtitle());
							map.get(0).put("itemId", obj.getCmid());
							map.get(0).put("tag", tagstr.toString());
							map.get(0).put("favoriteType", 1);
							map.get(0).put("district", findtyname((String)map.get(0).get("district")));
							map.get(0).put("type", findtyname((String)map.get(0).get("typeId")));
							Example example2 = new Example(WhActivityitm.class);
							example2.or().andEqualTo("actvrefid", cmrefid);
							example2.setOrderByClause("actvitmsdate"+" "+"asc");
							List<WhActivityitm> listitm = this.itemMapper.selectByExample(example2);
							if(listitm != null && listitm.size() > 0){
								map.get(0).put("begin", listitm.get(0).getActvitmedate());
								map.get(0).put("end", listitm.get(0).getActvitmsdate());
							}else{
								map.get(0).put("begin", null);
								map.get(0).put("end", null);
							}
							map.get(0).remove("itemId");
							listArray.add(map.get(0));
						}
					}else if("2016101400000051".equals(cmreftyp)){//2.	公益培训
						List<Map> map = this.activityMapper.selectfavtra(cmrefid);
						if(map != null && map.size() > 0){
							//处理活动标签
							if(map.get(0).get("tag") != null && !"".equals(map.get(0).get("tag"))){
								String tags[] = ((String) map.get(0).get("tag")).split(",");
								for (int i = 0; i < tags.length; i++) {
									tagfg =",";
									tagstr = tagfg + this.eventService.tagList(tags[i]).getName();
								}
							}
							map.get(0).put("title", obj.getCmtitle());
							map.get(0).put("itemId", obj.getCmid());
							map.get(0).put("tag", tagstr.toString());
							map.get(0).put("favoriteType", 2);
							map.get(0).put("district", findtyname((String)map.get(0).get("district")));
							map.get(0).put("type", findtyname((String)map.get(0).get("typeId")));
							listArray.add(map.get(0));
						}
					}else if("2016101400000053".equals(cmreftyp)){//3. 文化场馆。
						List<Map> map = this.activityMapper.selectfaven(cmrefid);
						if(map != null && map.size() > 0){
							//处理活动标签
							if(map.get(0).get("tag") != null && !"".equals(map.get(0).get("tag"))){
								String tags[] = ((String) map.get(0).get("tag")).split(",");
								for (int i = 0; i < tags.length; i++) {
									tagfg =",";
									tagstr = tagfg + this.eventService.tagList(tags[i]).getName();
								}
							}
							map.get(0).put("tag", tagstr.toString());
							
							map.get(0).put("title", obj.getCmtitle());
							map.get(0).put("itemId", obj.getCmid());
							map.get(0).put("image",(String)map.get(0).get("thumb"));
							map.get(0).put("favoriteType", 3);
							map.get(0).put("district", findtyname((String)map.get(0).get("district")));
							map.get(0).put("type", findtyname((String)map.get(0).get("typeId")));
							Example example2 = new Example(WhVenuetime.class);
							example2.or().andEqualTo("venid", cmrefid);
							example2.setOrderByClause("vtstime"+" "+"asc");
							List<WhVenuetime> listitm = this.venitmMapper.selectByExample(example2);
							if(listitm != null && listitm.size() > 0){
								map.get(0).put("begin", listitm.get(0).getVtstime());
								map.get(0).put("end", listitm.get(0).getVtetime());
							}else{
								map.get(0).put("begin", null);
								map.get(0).put("end", null);
							}
							listArray.add(map.get(0));
						}
					}else if("2016101400000054".equals(cmreftyp)){//4. 文化展。
						List<Map> map = this.activityMapper.selectfaart(cmrefid);
						if(map != null && map.size() > 0){
							map.get(0).put("tag", null);
							map.get(0).put("phone", null);
							map.get(0).put("address", null);
							
							map.get(0).put("title", obj.getCmtitle());
							map.get(0).put("itemId", obj.getCmid());
							
							map.get(0).put("image",(String)map.get(0).get("thumb"));
							map.get(0).put("favoriteType", 4);
							map.get(0).put("type", findtyname((String)map.get(0).get("typeId")));
							
							listArray.add(map.get(0));
						}
					}else if("5".equals(cmreftyp)){//5. 精品资源
						
					}
				}
			}
			infoMap.put("list", listArray);
			//当前页码的数据条数
			rtnMap.put("count", info.getSize());
			//当前请求的每页数据条数
			rtnMap.put("size", info.getPageSize());
			//总共数据条数
			rtnMap.put("total", info.getTotal());
			//当前页码
			rtnMap.put("index", info.getPageNum());
			
			infoMap.put("pager",rtnMap);
			
			resMap.put("data",infoMap);
			resMap.put("code", 0);
			resMap.put("msg", "");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			this._resolveException2Map(e.getMessage(), resMap);
		}
		return resMap;
	}
	
	@ResponseBody
	@RequestMapping("/user/favorite/delete")
	public Object delMyFavorite(WebRequest requset){
		Map<String, Object> infoMap = new HashMap<String, Object>();
		try {
			Map<String, Object> params = ReqParamsUtil.parseRequest(requset);
			String openId = (String) params.get("openId");
			/*if(openId == null || "".equals(openId)){
				throw new Exception("<[code:1000]><[msg:用户标识为空]>");
			}*/
			String itemId = (String) params.get("itemId");
			int count = this.whCollectionMapper.deleteByPrimaryKey(itemId);
			infoMap.put("code", 0);
			infoMap.put("data", null);
			if(count > 0){
				infoMap.put("msg", ""); 
			}else{
				infoMap.put("msg", "收藏id不存在!");
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			this._resolveException2Map(e.getMessage(), infoMap);
		}
		return infoMap;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/activity/index")
	public Object activityindex(WebRequest requset){
		Map<String,Object> resMap = new HashMap<String,Object>();
		Map<String,Object> infoMap = new HashMap<String,Object>();
		
		Map<String, Object> params = ReqParamsUtil.parseRequest(requset);
		//String openId = (String)params.get("openId");
		try {
			//查询活动list
			PageHelper.startPage(1, 5);
			List<Map> list = this.activityMapper.selectfavact(params);
			PageInfo info = new PageInfo<>(list);
			List<Map> listmap = info.getList();
			//活动标签  替换key
			String tagstr = "";
			String tagfg = "";
			Map<String,Object> listppandact = new HashMap<String,Object>();
			
			List<Map> savelist = new ArrayList<Map>();
			Example example = new Example(WhCollection.class);
			if(listmap != null && listmap.size() > 0){
				for (Map map : listmap) {
				//select actvtype typeId,actvintroduce intro,actvbigpic thumb,actvpic image,actphone phone,actcg venue,actvtags tag,actvarea district,actvaddress address
//					infoMap.put("typeId",map.get("typeId"));
//					infoMap.put("type", findtyname((String)map.get("typeId")));
//					infoMap.put("band", false);
					//处理活动标签
					if(map.get("tag") != null && !"".equals(map.get("tag"))){
						String tags[] = ((String) map.get("tag")).split(",");
						for (int i = 0; i < tags.length; i++) {
							tagfg =",";
							tagstr = tagfg + this.eventService.tagList(tags[i]).getName();
						}
					}
					map.put("tag", tagstr.toString());
					
					Example example2 = new Example(WhActivityitm.class);
					example2.or().andEqualTo("actvrefid", map.get("itemId"));
					example2.setOrderByClause("actvitmsdate"+" "+"asc");
					List<WhActivityitm> listitm = this.itemMapper.selectByExample(example2);
					if(listitm != null && listitm.size() > 0){
						map.put("begin", listitm.get(0).getActvitmedate());
						map.put("end", listitm.get(0).getActvitmsdate());
					}else{
						map.put("begin", null);
						map.put("end", null);
					}
					Criteria c = example.createCriteria();
					c.andEqualTo("cmrefid", (String)map.get("itemId"));
					c.andEqualTo("cmopttyp", 0);
					int coll = this.whCollectionMapper.selectCountByExample(example);
					if (coll > 0) {
						map.put("favorite", true);
					}else{
						map.put("favorite", false);
					}
					Criteria c2 = example.createCriteria();
					c2.andEqualTo("cmrefid", (String)map.get("itemId"));
					c2.andEqualTo("cmopttyp", 2);
					int coll2 = this.whCollectionMapper.selectCountByExample(example);
					map.put("voteUp", coll2);
					map.put("voted", false);
					
					map.remove("typeid");
					
					savelist.add(map);
				}
				listppandact.put("activities", savelist);
			}
			List<Map> savepplist = new ArrayList<Map>();
			//查询活动list
			List<Map> listpp = this.activityMapper.selectfavppact();
			PageInfo info2 = new PageInfo<>(listpp);
			List<Map> listppmap = info2.getList();
			Map<String,Object> ppMap = new HashMap<String,Object>();
			if(listppmap != null && listppmap.size() > 0){
//				infoMap.put("typeId","2016103100000001");
//				infoMap.put("type","品牌活动");
//				infoMap.put("band", true);
				for (Map map : listppmap) {
					map.put("image", map.get("thumb"));
					map.put("phone", null);
					map.put("venue", null);
					map.put("address", null);
					map.put("tag", null);
					map.put("voted", false);
					map.put("favorite", false);
					map.put("voteUp", 0);
					savepplist.add(map);
				}
				listppandact.put("brandList", savepplist);
			}
			//list 加 活动mp
//			listppandact.add(infoMap);
			//list 加 品牌活动mp
			//listppandact.add(ppMap);
			resMap.put("data", listppandact);
			resMap.put("msg", "");
			resMap.put("code", 0);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			this._resolveException2Map(e.getMessage(), resMap);
		}
		return resMap;
	}
	   /**
     * 解析异常为返回的 code
     */
    private void _resolveException2Map(String message, Map<String, Object> resMap){
        // message => "<[code:-1]><[msg:名字不正确]>"
        try {
            resMap.put("code", -1);
            resMap.put("msg", "");
            Pattern p = Pattern.compile("\\<\\[(code|msg):([^\\]\\>]*)\\]\\>");
            Matcher m = p.matcher(message);
            while (m.find()){
                String key = m.group(1);
                String value = m.group(2);
                resMap.put(key.trim(), value.trim());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
    
    public	String findtyname(String typid){
    	//Example example = new Example(WhTyp.class); 
    	WhTyp whtyp= this.typeMapper.selectByPrimaryKey(typid);
    	if(whtyp != null){
    		return whtyp.getTypname();
    	}else{
    		return "";
    	}
    }
    
	 /**
     * 验证用户绑定的参数
     */
	private void _paramsOrder(String openId, String itemId, String timeId) throws Exception{
        if (openId==null || openId.isEmpty()){
            throw new Exception("<[code:1000]><[msg:用户标识为空]>");
        }
        if (itemId==null || itemId.isEmpty()){
            throw new Exception("<[code:1000]><[msg:活动标识为空]>");
        }
        if (timeId==null || timeId.isEmpty()){
            throw new Exception("<[code:1000]><[msg:场次标识为空]>");
        }
       
    }
	
	
}
