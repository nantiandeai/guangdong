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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.creatoo.hn.mapper.GypxMapper;
import com.creatoo.hn.mapper.WhCollectionMapper;
import com.creatoo.hn.mapper.WhTrainMapper;
import com.creatoo.hn.mapper.WhUserMapper;
import com.creatoo.hn.model.WhCollection;
import com.creatoo.hn.model.WhMgr;
import com.creatoo.hn.model.WhTrain;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.home.art.ArtService;
import com.creatoo.hn.services.wap.WapTrainService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@RestController
@RequestMapping("/wap/wechat/train")
public class GypxList {
	private static final String WAP_SESSION_KEY = "sessionid";
	
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private WhTrainMapper whTrainMapper;
	@Autowired
	private CommService commService;
	@Autowired
	private WhCollectionMapper whCollectionMapper;
	@Autowired
	private WapTrainService wapTrainService;
	@Autowired
	private WhUserMapper whUserMapper;
	@Autowired
	private ArtService artService;
	/**
	 * 培训列表
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public Object getTrainList(HttpServletRequest req){
		
		//
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		List<Map> list = new ArrayList<>();
		Map<String,Object> map2 = new HashMap<String,Object>();
		//获取请求参数
		Map<String, Object> params = ReqParamsUtil.parseRequest(req);
		String openid = req.getParameter("openId");
		int page = 1;
		int rows = 12;
		if (params.containsKey("index") && params.get("index")!=null){
			page = Integer.valueOf(params.get("index").toString());
		}
		if (params.containsKey("size") && params.get("size")!=null){
			rows = Integer.valueOf(params.get("size").toString());
		}
		PageHelper.startPage(page, rows);
		String traid = null;
		Example example = new Example(WhCollection.class);
		Criteria c = example.createCriteria();
		list = this.whTrainMapper.selTrainList(params);
		// 取分页信息
		PageInfo<Map> pageInfo = new PageInfo<Map>(list);
		//biaoqian
		 List<Map> rowsList = pageInfo.getList();
		 if(rowsList != null){
	        	try {
					//
					String scopes = "";
					String _sp = "";
					for(Map m : rowsList){
						String scope = (String)m.get("tratags");
						if(scope != null && !"".equals(scope) && !"null".equalsIgnoreCase(scope)){
							scopes += _sp+scope;
							_sp = ",";
						}
					}
					
					//
					List<Map> tags = this.artService.srchTags(scopes);
					
					for(Map m : rowsList){
						String scopeTags = parseTags((String)m.get("tratags"), tags);
						m.put("tag", scopeTags);
					}
				} catch (Exception e) {
					this._resolveException2Map(e.getMessage(), map);
					log.error(e.getMessage(), e);
				}
	        }
		 //_end
		 try {
			for (int i = 0; i < list.size(); i++) {
				 //用户信息
				 Example example1 = new Example(WhUser.class);
		 		 Criteria c1 = example1.createCriteria();
		 		 c1.andEqualTo("wxopenid", openid);
		 		 List<WhUser> user = this.whUserMapper.selectByExample(example1);
		 		 
		 		 
		 		 String id = user.get(0).getId();
		 		 traid = (String) list.get(i).get("traid");
		 		Map<String,Object> params1 = new HashMap<String,Object>();
		 		params.put("id", id) ;
		 		params.put("traid", traid) ;
		 		 //是否收藏
		 		 int result = this.whTrainMapper.selCollectionCount(params1);
		 		 if (result > 0) {
		 			list.get(i).put("favorite", "是");
		 		 }else{
					list.get(i).put("favorite", "否");
				 }
				//分页信息
				list.get(i).put("voted", "否");
				
				
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
     * 解析异常为返回的 code
     */
    private void _resolveException2Map(String message, Map<String, Object> resMap){
        // message => "<[code:-1]><[msg:名字不正确]>"
        try {
            resMap.put("code", -1);
            resMap.put("msg", "");
            Pattern p = Pattern.compile("\\<\\[(code|msg):([^\\]\\>]*)\\]\\>");
            if(message != null){
	            Matcher m = p.matcher(message);
	            while (m.find()){
	                String key = m.group(1);
	                String value = m.group(2);
	                resMap.put(key.trim(), value.trim());
	            }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
    
    
    /**
     * 培训详情
     * @param req
     * @return
     */
     @RequestMapping(value="/detail")
	public Object getTrainDetail(HttpServletRequest req){
    	 Map<String,Object> map = new HashMap<String,Object>();
 		 Map<String,Object> map1 = new HashMap<String,Object>();
 		 List<Map> titleList = new ArrayList<Map>();
 		// List<Map> tickList = new ArrayList<Map>();
 		 Map<String,Object> tickMap = new HashMap<String,Object>();
 		 Map<String,Object> titleMap = new HashMap<String,Object>();
 		 Map<String,Object> titleMap1 = new HashMap<String,Object>();
 		 Map<String,Object> titleMap2 = new HashMap<String,Object>();
    	 //获取请求参数
 		 String openid = req.getParameter("openId");
 		 try {
	 		 //查询培训
	 		 //String itemId = "2016110100000001";
	 		 
	 		 String itemId = req.getParameter("itemId") ;
	 		 if(itemId == null || "".equals(itemId)) {
	 			 map1.put("code", -1) ;
	    		 map1.put("msg", "参数错误") ;
	    		 map1.put("data", "") ;
	    		 return map1 ;
	 		 }
	    	 List<Map> list = this.whTrainMapper.selTrainDetail(itemId);
	    	 //是否需要上传附件
	    	 if(list != null && list.size() > 0){
		    	 if (list.get(0).get("attachment") != null && !"".equals(list.get(0).get("attachment"))) {
		    		 int att = (int) list.get(0).get("attachment");
		    		 if (att == 0) {
		    			 map.put("attachment", "否");
		 			}
		 	    	 if (att == 1) {
		 	    		map.put("attachment", "是");
		 	    	 }
		    	 }
	    	 
	    	 
	    	//标签
		        if(list.size() > 0){
		        	try {
						//
						String scopes = "";
						String _sp = "";
						for(Map m : list){
							String scope = (String)m.get("tratags");
							if(scope != null && !"".equals(scope) && !"null".equalsIgnoreCase(scope)){
								scopes += _sp+scope;
								_sp = ",";
							}
						}
						//
						List<Map> tags = this.artService.srchTags(scopes);
						
						for(Map m : list){
							String scopeTags = parseTags((String)m.get("tratags"), tags);
							m.put("tag", scopeTags);
						}
					} catch (Exception e) {
						e.printStackTrace();;
					}
		        }
		        //
		        map.put("voted", "否");
	    	 
//	 		 Example example = new Example(WhUser.class);
//	 		 Criteria c = example.createCriteria();
//	 		 c.andEqualTo("wxopenid", openid);
//	 		 List<WhUser> user = this.whUserMapper.selectByExample(example);
//	 		 String id = user.get(0).getId();
//	 		 String traid = (String) list.get(0).get("itemId");
//	 		Map<String,Object> params = new HashMap<String,Object>();
//	 		params.put("id", id) ;
//	 		params.put("traid", traid) ;
//	 		 int result = this.whTrainMapper.selCollectionCount(params);
//	 		 if (result > 0) {
//	 			map.put("favorite", "是");
//	 		 }else{
//	 			map.put("favorite", "否");
//			 }
	 		 map1.put("code",0);
	         map1.put("msg",null);
	         titleMap.put("title", "课程详情介绍");
	 		 titleMap.put("content", list.get(0).get("tradetail"));
	 		 titleMap1.put("title", "课程大纲");
	 		 titleMap1.put("content", list.get(0).get("tracatalog"));
	 		 titleMap2.put("title", "课程报名要求");
	 		 titleMap2.put("content", list.get(0).get("traenroldesc"));
	  		 titleList.add(0, titleMap);
	  		 titleList.add(1, titleMap1);
	  		 titleList.add(2, titleMap2);
	  		//map.put("list", list);
	  		if(list.size() > 0) {
	  			map.put("itemId", list.get(0).get("itemId")) ;
	  			map.put("type", list.get(0).get("type")) ;
	  			map.put("title", list.get(0).get("title")) ;
	  			map.put("intro", list.get(0).get("intro")) ;
	  			map.put("typeId", list.get(0).get("typeId")) ;
	  			map.put("begin", list.get(0).get("begin")) ;
	  			map.put("end", list.get(0).get("end")) ;
	  			map.put("tradetail", list.get(0).get("tradetail")) ;
	  			map.put("tratags", list.get(0).get("tratags")) ;
	  			map.put("phone", list.get(0).get("phone")) ;
	  			map.put("thumb", list.get(0).get("thumb")) ;
	  			map.put("tracatalog", list.get(0).get("tracatalog")) ;
	  			map.put("address", list.get(0).get("address")) ;
	  			map.put("image", list.get(0).get("image")) ;
	  		}
	  		map.put("aspect", titleList);
			map.put("tickets", null);
			map1.put("data", map);
	    	 } else {
	    		 map1.put("code", -1) ;
	    		 map1.put("msg", "参数错误") ;
	    		 map1.put("data", "") ;
	    	 }
     	} catch (Exception e) {
     		e.printStackTrace();
	        this._resolveException2Map(e.getMessage(), map);
		}
		return map1;
	}
     
     /**
      * 培训预约
      * @param req
      * @returnbooking
      */
     @RequestMapping(value="/booking",method=RequestMethod.GET)
     public Object booking(HttpServletRequest request, HttpServletResponse response){
    	 Map<String, Object> map = new HashMap<String, Object>();
         try {
             this._sessoinCookieHandle(request,response);
             this.wapTrainService.saveTrain(request);
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
