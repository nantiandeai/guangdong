/**
 * 
 */
package com.creatoo.hn.actions.home.comm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;

/**
 * 公共访问控制
 * @author wangxl
 * @version 20161213
 */
@RestController
public class DefaultCommAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public CommService commservice;
	
	/**
	 * 跳转到全局搜索页面
	 * @return 全局搜索页面
	 */
	@RequestMapping("/search")
	public ModelAndView search(String srchkey){
		ModelAndView view = new ModelAndView( "search" );
		view.addObject("srchkey", srchkey);
		return view;
	}
	
	/**
	 * 全局搜索关键字
	 * @param req 请求对象
	 * @param res 响应对象
	 * @return 分页搜索数据
	 */
	@RequestMapping("/globalsrchcontent")
	public Object globalsrchcontent(HttpServletRequest req, HttpServletResponse res){
		//获取请求参数
		Map<String, Object> param = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
        	if(param.containsKey("srchkey")){
        		param.put("srchkey", "%"+param.get("srchkey")+"%");
        	}
			rtnMap = this.commservice.globalsrchcontent(param);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
        return rtnMap;
	}
	
	/**
	 * 返回搜索关键字列表
	 * @return 搜索关键字列表
	 */
	/*@RequestMapping("/searchkeylist")
	public Object searchkeylist(){
        List<String> keyList = new ArrayList<String>();
        try {
			String searchKeys = WhConstance.getSysProperty(WhConstance.GLOBAL_SEARCH_KEYS, "场馆,订票,培训,免费");
			String[] keys = searchKeys.split(",");
			for(String k : keys){
				keyList.add(k);
			}
		} catch (Exception e) {
	        log.error(e.getMessage(), e);
		}
        return keyList;
	}*/
}
