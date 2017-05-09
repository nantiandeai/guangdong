package com.creatoo.hn.actions.wap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.creatoo.hn.mapper.WhCollectionMapper;
import com.creatoo.hn.mapper.WhTrainMapper;
import com.creatoo.hn.mapper.WhUserMapper;
import com.creatoo.hn.mapper.WhVenueMapper;
import com.creatoo.hn.mapper.WhVenuetimeMapper;
import com.creatoo.hn.model.WhCollection;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.model.WhVenuetime;
import com.creatoo.hn.services.home.art.ArtService;
import com.creatoo.hn.services.wap.WapVenueService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@RestController
@RequestMapping("/wap/wechat/venue")
public class WapVenueAction {
	
	private static final String WAP_SESSION_KEY = "sessionid";
	
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private WhVenueMapper whVenueMapper;
	@Autowired
	private WhCollectionMapper whCollectionMapper;
	@Autowired
	private WhUserMapper whUserMapper;
	@Autowired
	private WhTrainMapper whTrainMapper;
	@Autowired
	private WhVenuetimeMapper whVenuetimeMapper;
	@Autowired
	private WapVenueService wapVenueService;
	@Autowired
	private ArtService artService;
	/**
	 * 场馆列表
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public Object getTrainList(WebRequest req){
		
		//
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		List<Map> list = new ArrayList<>();
		Map<String,Object> map2 = new HashMap<String,Object>();
		//获取请求参数
		Map<String, Object> params = ReqParamsUtil.parseRequest(req);
		
		int page = 1;
		int rows = 12;
		if (params.containsKey("index") && params.get("index")!=null){
			page = Integer.valueOf(params.get("index").toString());
		}
		if (params.containsKey("size") && params.get("size")!=null){
			rows = Integer.valueOf(params.get("size").toString());
		}
		PageHelper.startPage(page, rows);
		Example example = new Example(WhCollection.class);
		Criteria c = example.createCriteria();
		 try {
			 list = this.whVenueMapper.selVenueList(params);
			 
			 // 取分页信息
			 PageInfo<Map> pageInfo = new PageInfo<Map>(list);
			 //标签
			 List<Map> rowsList = pageInfo.getList();
		        if(rowsList != null){
		        	try {
						//
						String scopes = "";
						String _sp = "";
						for(Map m : rowsList){
							String scope = (String)m.get("ventag");
							if(scope != null && !"".equals(scope) && !"null".equalsIgnoreCase(scope)){
								scopes += _sp+scope;
								_sp = ",";
							}
						}
						
						//
						List<Map> tags = this.artService.srchTags(scopes);
						
						for(Map m : rowsList){
							String scopeTags = parseTags((String)m.get("ventag"), tags);
							m.put("tag", scopeTags);
						}
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
		        }
		        //
			 for (int i = 0; i < list.size(); i++) {
				 //分页信息
				list.get(i).put("voted", "否");
				//用户信息
				String openid = req.getParameter("openId");
				Example example1 = new Example(WhUser.class);
				Criteria c1 = example1.createCriteria();
		 		c1.andEqualTo("wxopenid", openid);
		 		List<WhUser> user = this.whUserMapper.selectByExample(example1);
		 		String id = user.get(0).getId();
		 		String venid = (String) list.get(i).get("venid");
				c.andEqualTo("cmrefid",venid );
				c.andEqualTo("cmopttyp", 0);
				Map<String,Object> params1 = new HashMap<String,Object>();
		 		params.put("venid", venid) ;
		 		params.put("id", id) ;
				int coll = this.whVenueMapper.selCollectionCount(params1);
				if (coll > 0) {
					list.get(i).put("favorite", "是");
				}else{
					list.get(i).put("favorite", "否");
				}
				
			}
			map2.put("index", pageInfo.getPageNum());
			map2.put("count", pageInfo.getSize());
			map2.put("size", pageInfo.getPageSize());
			map2.put("total", pageInfo.getTotal());
			map1.put("code",0);
	        map1.put("msg",null);
			} catch (Exception e) {
		        
		        this._resolveException2Map(e.getMessage(), map);
			}
		
		map.put("list", list);
		map.put("pager", map2);
		map1.put("data", map);
		return map1;
		
	}
	/**
	 * 场馆详情
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/detail",method=RequestMethod.GET)
	public Object getTrainDetail(HttpServletRequest req){
		
		//
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		List<Map> titleList = new ArrayList<Map>();
		List<Map> tickList = new ArrayList<Map>();
		Map<String,Object> timeMap = new HashMap<String,Object>();
		Map<String,Object> titleMap = new HashMap<String,Object>();
		Map<String,Object> titleMap1 = new HashMap<String,Object>();
		Map<String,Object> titleMap2 = new HashMap<String,Object>();
		Map<String,Object> map2 = new HashMap<String,Object>();
		//获取请求参数
		String openid = req.getParameter("openId");
		 try {
	 		 //查询场馆
	 		 String itemId = req.getParameter("itemId");
	    	 List<Map> list = this.whVenueMapper.selWapVenueDetail(itemId);
	    	 //标签
	    	 if(list != null && list.size() > 0){
		        if(list.size() > 0){
		        	try {
						//
						String scopes = "";
						String _sp = "";
						for(Map m : list){
							String scope = (String)m.get("ventag");
							if(scope != null && !"".equals(scope) && !"null".equalsIgnoreCase(scope)){
								scopes += _sp+scope;
								_sp = ",";
							}
						}
						
						//
						List<Map> tags = this.artService.srchTags(scopes);
						
						for(Map m : list){
							String scopeTags = parseTags((String)m.get("ventag"), tags);
							m.put("tag", scopeTags);
						}
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
		        }
		        //
	    	 //list.get(0).put("voted", "否");
	    	 
//	 		 Example example = new Example(WhUser.class);
//	 		 Criteria c = example.createCriteria();
//	 		 c.andEqualTo("wxopenid", openid);
//	 		 List<WhUser> user = this.whUserMapper.selectByExample(example);
//	 		 String id = user.get(0).getId();
//	 		 String venid = (String) list.get(0).get("itemId");
//	 		 Map<String,Object> params = new HashMap<String,Object>();
//	 		 params.put("venid", venid) ;
//	 		 params.put("id", id) ;
//	 		 int result = this.whVenueMapper.selCollectionCount(params);
//	 		 if (result > 0) {
//	 			list.get(0).put("favorite", "是");
//	 		 }else{
//				list.get(0).put("favorite", "否");
//			 }
	 		 //时段
	 		 Example example2 = new Example(WhVenuetime.class);
	 		 Criteria criteria = example2.createCriteria();
	 		 criteria.andEqualTo("venid", list.get(0).get("itemId"));
	 		 List<WhVenuetime> time = this.whVenuetimeMapper.selectByExample(example2);
	 		 for (int i = 0; i < time.size(); i++) {
	 			timeMap.put("itemId", time.get(i).getVtid());
				timeMap.put("begin", time.get(i).getVtstime());
				timeMap.put("end", time.get(i).getVtetime());
				timeMap.put("available", list.get(0).get("vencanbk"));
				map.put("timeTable", timeMap);
			 }
	 		 
	 		 map1.put("code",0);
	         map1.put("msg",null);
	         titleMap.put("title", "场馆描述");
	 		 titleMap.put("content", list.get(0).get("venintroduce1"));
	 		 titleMap1.put("title", "设施描述");
	 		 titleMap1.put("content", list.get(0).get("venintroduce2"));
	 		 titleMap2.put("title", "申请条件");
	 		 titleMap2.put("content", list.get(0).get("vencondition"));
	  		 titleList.add(0, titleMap);
	  		 titleList.add(1, titleMap1);
	  		 titleList.add(2, titleMap2);
	  		 //map.put("list", list);
	  		 if(list.size() > 0) {
	  			map.put("image", list.get(0).get("image")) ;
	  			map.put("address", list.get(0).get("address")) ;
	  			map.put("thumb", list.get(0).get("thumb")) ;
	  			map.put("venintroduce2", list.get(0).get("venintroduce2")) ;
	  			map.put("venintroduce1", list.get(0).get("venintroduce1")) ;
	  			map.put("vencondition", list.get(0).get("vencondition")) ;
	  			map.put("voted", list.get(0).get("voted")) ;
	  			map.put("voteUp", list.get(0).get("voteUp")) ;
	  			map.put("title", list.get(0).get("title")) ;
	  			map.put("type", list.get(0).get("type")) ;
	  			map.put("vencanbk", list.get(0).get("vencanbk")) ;
	  			map.put("itemId", list.get(0).get("itemId")) ;
	  			map.put("phone", list.get(0).get("phone")) ;
	  			map.put("intro", list.get(0).get("intro")) ;
	  			map.put("district", list.get(0).get("district")) ;
	  			map.put("typeId", list.get(0).get("typeId")) ;
	  			map.put("tag", list.get(0).get("tag")) ;
	  			map.put("favorite", list.get(0).get("favorite")) ;
	  		 }
	    	 } else {
	    		 map1.put("code", -1) ;
	    		 map1.put("msg", "参数值无效") ;
	    	 }
    	} catch (Exception e) {
	        this._resolveException2Map(e.getMessage(), map1);
	        e.printStackTrace();
		}
		map.put("aspects", titleList) ;
		map1.put("data", map);
		return map1;
		
	}
	/**
	 * 场馆预约
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/booking",method=RequestMethod.GET)
	public Object booking(HttpServletRequest req,HttpServletResponse resp){
		Map<String, Object> map = new HashMap<String, Object>();
        try {
            this._sessoinCookieHandle(req,resp);
            this.wapVenueService.saveVenue(req);
            map.put("code",0);
            map.put("data",null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            this._resolveException2Map(e.getMessage(), map);
        }
        return map;
	}
	
	/**
     * 公共处理session 和 cookie
     * @param request
     */
    private void _sessoinCookieHandle(HttpServletRequest request, HttpServletResponse response){
        String openId = request.getParameter("openId");
        if (openId == null || openId.isEmpty()) return;
        try {
            HttpSession session = request.getSession();
            session.setAttribute(WAP_SESSION_KEY, openId);
            Cookie cookie = new Cookie(WAP_SESSION_KEY, openId);
            cookie.setMaxAge(60*60*24*365);
            response.addCookie(cookie);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
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
	
    /**查询标签
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
}
