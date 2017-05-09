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

import com.creatoo.hn.model.WhArt;
import com.creatoo.hn.model.WhCollection;
import com.creatoo.hn.model.WhComment;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.services.wap.WapExhibitionService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/wap/wechat/exhibition")
public class WapExhibitionAction {
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private WapExhibitionService wapExhibitionService;
	/**
	 * 文化展
	 * @param req
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/category")
	public Object getUser(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		List<Map> list = new ArrayList<>();
		// 获取请求参数
		Map<String, Object> params = ReqParamsUtil.parseRequest(req);
		int page = 1;
		int rows = 10;
		try {
			if (params.containsKey("index") && params.get("index") != null) {
				page = Integer.valueOf(params.get("index").toString());
			}
			if (params.containsKey("size") && params.get("size") != null) {
				rows = Integer.valueOf(params.get("size").toString());
			}
			PageHelper.startPage(page, rows);
			list = this.wapExhibitionService.exhselect(params);
			for (int i = 0; i < list.size(); i++) {
				list.get(i).put("Boolean", "否");
				list.get(i).put("voteUp", "0");
				list.get(i).put("voted", "否");
				list.get(i).put("comment", "0");
			}
			
			// 取分页信息
			PageInfo<Map> pageInfo = new PageInfo<Map>(list);
			map2.put("index", pageInfo.getPageNum());
			map2.put("count", pageInfo.getSize());
			map2.put("size", pageInfo.getPageSize());
			map2.put("total", pageInfo.getTotal());
			map1.put("code", 0);
			map1.put("msg", null);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			this._resolveException2Map(e.getMessage(), map);
		}
		map.put("list", list);
		map1.put("pager", map2);
		map1.put("data", map);
		return map1;

	}
	
	/**
	 * 文化展详情
	 */
	
	@RequestMapping("/detail")
	public Object getdetail(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取请求参数
		Map<String, Object> params = ReqParamsUtil.parseRequest(req);
		String itemId = req.getParameter("itemId");
		String openId = req.getParameter("openId");
		
		if(openId == null || "".equals(openId) || itemId == null || "".equals(itemId)) {
			map.put("code", -1) ;
			map.put("msg", "请求参数不能为空") ;
			map.put("data", "") ;
			return map ;
		}
		
		try {
			WhUser whu=this.wapExhibitionService.selectuser(openId);
			String id = whu.getId();
			List<WhCollection> whc = this.wapExhibitionService.slectcoll(id);
			List<WhCollection> whcoll = this.wapExhibitionService.slectcollsc(id,itemId);
			List<WhComment> comm = this.wapExhibitionService.selectpl(id,itemId);
			if (whc.size() > 0 ) {
				map.put("voteUp", whc.size());
			}else {
				map.put("voteUp",0);
			}
			if (whcoll.size() > 0 ) {
				map.put("favorite", true);
			}else {
				map.put("favorite",false);
			}
			if (comm.size() > 0 ) {
				map.put("comment", comm.size());
			}else {
				map.put("comment", 0);
			}
			map.put("voted", false);
			map.put("data", this.wapExhibitionService.selectdetail(params));
			WhArt wha =  this.wapExhibitionService.selectinfo(itemId);
			map.put("aspects", wha.getArtcontent());
			map.put("code", 0);
			map.put("msg", null);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			this._resolveException2Map(e.getMessage(), map);
		}
		return map;

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
