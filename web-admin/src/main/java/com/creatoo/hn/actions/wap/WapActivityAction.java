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

import com.creatoo.hn.model.WhCollection;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.services.wap.WapActivityService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/wap/wechat/activity")
public class WapActivityAction {
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private WapActivityService wapactivityservice;
	/**
	 * 文化活动列表
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/list")
	public Object getUser(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		// 获取请求参数
		Map<String, Object> param = ReqParamsUtil.parseRequest(req);
		
		PageInfo<Map> pageInfo = null ;
		// 微信id
		String openId = req.getParameter("openId");
		List<Map> list = new ArrayList<>();
		List<Map> brandList = null ;
		// 活动类型 B_开头的是品牌 无前缀是的普通活动
		String typeId = req.getParameter("typeId");
		String type[] ;
		if(typeId == null){
			type = null;
		}else{
			type = typeId.split("_");
		}
		 
		int page = 1;
		int rows = 10;
		try {
//			// 用户id
//			WhUser whu = this.wapactivityservice.slelctuser(openId);
//			String id = whu.getId();
			//品牌活动
			if (type != null && type[0].equals("B")) {
				brandList = this.wapactivityservice.slelctBrand(param);
				list = this.wapactivityservice.selectpp(param);
				// 取分页信息
				pageInfo = new PageInfo<Map>(list);
				list = pageInfo.getList() ;
				for (int i = 0; i < list.size(); i++) {
					String itemId = (String) list.get(i).get("itemId");
					// 点赞
					List<WhCollection> whc = this.wapactivityservice.selctwhc(itemId);
					if (whc.size() > 0) {
						list.get(i).put("voteUp", whc.size());
					} else {
						list.get(i).put("voteUp", 0);
					}
					// 收藏
//					WhCollection whc1 = this.wapactivityservice.selectcc(itemId, id);
//					if (whc1 != null) {
//						list.get(i).put("favorite", true);
//					} else {
						list.get(i).put("favorite", false);
//					}
					//投票
					list.get(i).put("voted", false);
				}
			} else {
			//普通活动
			list = this.wapactivityservice.selectact(param);
			// 取分页信息
			pageInfo = new PageInfo<Map>(list);
			list = pageInfo.getList() ;
			for (int i = 0; i < list.size(); i++) {
				String itemId = (String) list.get(i).get("itemId");
				// 点赞
				List<WhCollection> whc = this.wapactivityservice.selctwhc(itemId);
				if (whc.size() > 0) {
					list.get(i).put("voteUp", whc.size());
				} else {
					list.get(i).put("voteUp", 0);
				}
//				// 收藏
//				WhCollection whc1 = this.wapactivityservice.selectcc(itemId, id);
//				if (whc1 != null) {
//					list.get(i).put("favorite", true);
//				} else {
//					list.get(i).put("favorite", false);
//				}
				//投票
				list.get(i).put("voted", false);
			}
			}
//			if (param.containsKey("index") && param.get("index") != null) {
//				page = Integer.valueOf(param.get("index").toString());
//			}
//			if (param.containsKey("size") && param.get("size") != null) {
//				rows = Integer.valueOf(param.get("size").toString());
//			}
//			//PageHelper.startPage(page, rows);
//			// 取分页信息
//			PageInfo<Map> pageInfo = new PageInfo<Map>(list);
			map2.put("index", pageInfo.getPageNum());
			map2.put("count", pageInfo.getSize());
			map2.put("size", pageInfo.getPageSize());
			map2.put("total", pageInfo.getTotal());
			map1.put("code", 0);
			map1.put("msg", "");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			this._resolveException2Map(e.getMessage(), map);
		}
		map.put("list", list);
		map.put("pager", map2);
		//map.put("brandList", brandList) ;
		//map1.put("pager", map2);
		map1.put("data", map);
		return map1;
	}

	/**
	 * 解析异常为返回的 code
	 */
	private void _resolveException2Map(String message, Map<String, Object> resMap) {
		try {
			resMap.put("code", -1);
			resMap.put("msg", "");
			Pattern p = Pattern.compile("\\<\\[(code|msg):([^\\]\\>]*)\\]\\>");
			Matcher m = p.matcher(message);
			while (m.find()) {
				String key = m.group(1);
				String value = m.group(2);
				resMap.put(key.trim(), value.trim());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
