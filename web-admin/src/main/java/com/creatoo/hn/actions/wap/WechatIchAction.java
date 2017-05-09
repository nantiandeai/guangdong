package com.creatoo.hn.actions.wap;

import java.util.ArrayList;
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

import com.creatoo.hn.services.wap.WechatNewsService;
import com.creatoo.hn.utils.ReqParamsUtil;

@RestController
@RequestMapping("/wap/wechat/ich")
public class WechatIchAction {

	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	public WechatNewsService wechatNewService;

	@RequestMapping("/list")
	public Object getNews(HttpServletResponse response, HttpServletRequest req) {

		// 非遗
		String postType = null;
		// 获取请求参数
		Map<String, Object> param = ReqParamsUtil.parseRequest(req);
		// 分页查询
		Map<String, Object> rtnMap = null;
		try {
			if (req.getParameter("type") != null) {
				postType = req.getParameter("type");
				if ("1".equals(postType)) {
					rtnMap = this.wechatNewService.showChuangcheng(param);
				}
				if ("2".equals(postType)) {
					rtnMap = this.wechatNewService.showProject(param);
				}
			}
			rtnMap.put("code", 0);
			rtnMap.put("msg", null);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			this._resolveException2Map(e.getMessage(), rtnMap);
		}
		return rtnMap;
	}

	@RequestMapping("/detail")
	public Object showDetail(HttpServletResponse res, HttpServletRequest req) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map> list = new ArrayList();
		if (!"".equals(req.getParameter("itemId")) && req.getParameter("itemId") != null) {
			Map<String, Object> param = ReqParamsUtil.parseRequest(req);
			if (req.getParameter("type") != null) {
				String postType = req.getParameter("type");
				if ("1".equals(postType)) {
					map = this.wechatNewService.showChuangchengDetail(param);
//					if(list.size() > 0) {
//						map.put("itemId", list.get(0).get("itemId")) ;
//						map.put("thumb", list.get(0).get("thumb")) ;
//						map.put("intro", list.get(0).get("intro")) ;
//						map.put("project", list.get(0).get("project")) ;
//						map.put("time", list.get(0).get("time")) ;
//						map.put("title", list.get(0).get("title")) ;
//					}
				}
				if ("2".equals(postType)) {
					map = this.wechatNewService.showProjectDetail(param);
//					if(list.size() > 0) {
//						map.put("itemId", list.get(0).get("itemId")) ;
//						map.put("imgage", list.get(0).get("imgage")) ;
//						map.put("intro", list.get(0).get("intro")) ;
//						map.put("project", list.get(0).get("project")) ;
//						map.put("thumb", list.get(0).get("thumb")) ;
//						map.put("title", list.get(0).get("title")) ;
//						map.put("prosbaddr", list.get(0).get("prosbaddr")) ;
//						map.put("source", list.get(0).get("source")) ;
//						map.put("tag", list.get(0).get("tag")) ;
//					}
				}
			}
			rtnMap.put("data", map) ;
			rtnMap.put("code", 0);
			rtnMap.put("msg", null);
		} else {
			this._resolveException2Map(null, rtnMap);
		}

		return rtnMap;
	}

	/**
	 * 解析异常为返回的 code
	 */
	private void _resolveException2Map(String message, Map<String, Object> resMap) {
		try {

			resMap.put("code", -1);
			resMap.put("msg", "");
			Pattern p = Pattern.compile("\\<\\[(code|msg):([^\\]\\>]*)\\]\\>");
			if (message != null) {
				Matcher m = p.matcher(message);
				while (m.find()) {
					String key = m.group(1);
					String value = m.group(2);
					resMap.put(key.trim(), value.trim());
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
