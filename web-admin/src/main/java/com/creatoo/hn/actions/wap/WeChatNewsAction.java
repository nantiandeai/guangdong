package com.creatoo.hn.actions.wap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhTyp;
import com.creatoo.hn.services.home.agdgwgk.GwgkService;
import com.creatoo.hn.services.wap.WechatNewsService;
import com.creatoo.hn.utils.ReqParamsUtil;

@RestController
@RequestMapping("/wap/wechat/news")
public class WeChatNewsAction {
	
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * 馆务公开服务类
	 */
	@Autowired
	public GwgkService gwgkService;
	
	@Autowired
	public WechatNewsService wechatNewService ;
	
	
	
	@RequestMapping("/list")
	public Object getNews(HttpServletResponse response,HttpServletRequest req) {
		
		//资讯栏目
		String realtype = "2016111900000014";
		String postType = null ;
		if(req.getParameter("type") != null){
			postType = req.getParameter("type");
			if("1".equals(postType)) {
				realtype = "2016111900000014" ;
			}
			if("2".equals(postType)) {
				realtype = "2016111900000015" ;
			}
			if("3".equals(postType)) {
				realtype = "2016111900000016" ;
			}
			if("4".equals(postType)) {
				realtype = "2016111900000017" ;
			}
		}
		
		//获取请求参数
		Map<String, Object> param = ReqParamsUtil.parseRequest(req);
		param.put("type", realtype) ;
		//分页查询
        Map<String, Object> rtnMap = null;
		try {
			rtnMap = this.wechatNewService.paggingColinfo(param);
			rtnMap.put("code", 0);
			rtnMap.put("msg", null);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			this._resolveException2Map(e.getMessage(), rtnMap);
		}
		return rtnMap ;
	}
	
	@RequestMapping("/detail")
	public Object showDetail(HttpServletResponse res,HttpServletRequest req){
		Map<String, Object> rtnMap = new HashMap<String,Object>();
		Map<String, Object> map = new HashMap<String,Object>();
		if(!"".equals(req.getParameter("itemId")) && req.getParameter("itemId") != null) {
			Map<String, Object> param = ReqParamsUtil.parseRequest(req);
			List<Map> list = this.wechatNewService.showZxDetail(param) ;
			if(list.size() > 0) {
				map.put("itemId", list.get(0).get("itemId")) ;
				map.put("author", list.get(0).get("author")) ;
				map.put("intro", list.get(0).get("intro")) ;
				map.put("source", list.get(0).get("source")) ;
				map.put("time", list.get(0).get("time")) ;
				map.put("tag", list.get(0).get("tag")) ;
				map.put("title", list.get(0).get("title")) ;
				map.put("content", list.get(0).get("content")) ;
			}
			rtnMap.put("data", map) ;
			rtnMap.put("code", 0);
			rtnMap.put("msg", null);
		} else {
			this._resolveException2Map(null, rtnMap);
		}
		
		return rtnMap ;
	}

	
	/**
	 * 解析异常为返回的 code
	 */
	private void _resolveException2Map(String message, Map<String, Object> resMap) {
		try {
			resMap.put("code", -1);
			resMap.put("msg", "");
			Pattern p = Pattern.compile("\\<\\[(code|msg):([^\\]\\>]*)\\]\\>");
			if(message != null){
				Matcher m = p.matcher(message);
				while (m.find()) {
					String key = m.group(1);
					String value = m.group(2);
					resMap.put(key.trim(), value.trim());
				}
			}else {
				resMap.put("msg", " 参数不能为空");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
