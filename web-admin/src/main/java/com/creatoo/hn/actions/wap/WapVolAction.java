package com.creatoo.hn.actions.wap;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.creatoo.hn.services.wap.WechatNewsService;
import com.creatoo.hn.utils.ReqParamsUtil;

@RestController
@RequestMapping("/wap/wechat/vol")
public class WapVolAction {

	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public WechatNewsService wechatNewService;
	
	@RequestMapping("/list")
	public Object showhd(HttpServletResponse res,HttpServletRequest req){
		// 非遗
				String postType = null;
				// 获取请求参数
				String category = req.getParameter("category") ;
				Map<String, Object> param = ReqParamsUtil.parseRequest(req);
				// 分页查询
				Map<String, Object> rtnMap = null;
				try {
					if (req.getParameter("type") != null) {
						postType = req.getParameter("type");
						if ("1".equals(postType)) {
							rtnMap = this.wechatNewService.showzyhd(param);
						}
						if ("2".equals(postType)) {
							if(category != null) {
								if("1".equals(category)){
									rtnMap = this.wechatNewService.showzyfcPerson(param) ;
								}
								if("2".equals(category)){
									rtnMap = this.wechatNewService.showzyfcProject(param);
								}
								if("3".equals(category)){
									rtnMap = this.wechatNewService.showzyfczuzhi(param);
								}
							}
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
	public Object showDetail(HttpServletResponse res,HttpServletRequest req){
		Map<String, Object> rtnMap = new HashMap<String,Object>();
		String postType = null;
		// 获取请求参数
		String category = req.getParameter("category") ;
		if(!"".equals(req.getParameter("itemId")) && req.getParameter("itemId") != null) {
			Map<String, Object> param = ReqParamsUtil.parseRequest(req);
			if (req.getParameter("type") != null) {
				postType = req.getParameter("type");
				if ("1".equals(postType)) {
					rtnMap = this.wechatNewService.showzyhdDetail(param);
				}
				if ("2".equals(postType)) {
					if(category != null) {
						if("1".equals(category)){
							rtnMap = this.wechatNewService.showzyfcPersonDetail(param) ;
						}
						if("2".equals(category)){
							rtnMap = this.wechatNewService.showzyfcProjectDetail(param);
						}
						if("3".equals(category)){
							rtnMap = this.wechatNewService.showzyfczuzhiDetail(param);
						}
					}
				}
			}
			rtnMap.put("code", 0);
			rtnMap.put("msg", "");
		} else {
			this._resolveException2Map(new Exception().getMessage(), rtnMap);
		}
		
		return rtnMap ;
	}
	
	/**
	 * 抓取志愿者服务人数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/showCount")
	public Object showCount() throws IOException{
		Document doc = Jsoup.connect("http://www.gdzyz.cn/").get();
        Element regCount = doc.getElementById("countVo");
        Element timeCount = doc.getElementById("time");
        Element teamCount = doc.getElementById("countDis");
        Map<String,Object> map = new HashMap<String,Object>() ;
        Map<String,Object> resultMap = new HashMap<String,Object>() ;
        resultMap.put("regCount", regCount.val()) ;
        resultMap.put("timeCount", timeCount.val()) ;
        resultMap.put("teamCount", teamCount.val()) ;
        map.put("code", 0) ;
        map.put("msg", "regCount：注册志愿者 (人)，timeCount：志愿服务时长 (万小时)，teamCount：志愿服务组织及团体 (个)") ;
        map.put("data", resultMap) ;
        return map ;
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
