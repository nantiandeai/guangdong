package com.creatoo.hn.actions.wap;

import java.util.ArrayList;
import java.util.Date;
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

import com.creatoo.hn.model.WhActivity;
import com.creatoo.hn.model.WhArtExhibition;
import com.creatoo.hn.model.WhBrand;
import com.creatoo.hn.model.WhCollection;
import com.creatoo.hn.model.WhComment;
import com.creatoo.hn.model.WhTrain;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.model.WhUserTroupe;
import com.creatoo.hn.model.WhVenue;
import com.creatoo.hn.model.WhZxColinfo;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.wap.WapExhibitionService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/wap/wechat/misc")
public class WapMiscAction {
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private WapExhibitionService wapExhibitionService;
	@Autowired
	public CommService service;

	/**
	 * 广告
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping("/ad")
	public Object getUser(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取请求参数
		Map<String, Object> params = ReqParamsUtil.parseRequest(req);
		try {
			map.put("data", this.wapExhibitionService.selectadv(params));
			map.put("code", 0);
			map.put("msg", null);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			this._resolveException2Map(e.getMessage(), map);
		}
		return map;
	}
	
	/**
	 * 广告
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping("/adbanner")
	public Object getshowPxyz(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> listMap = new HashMap<String, Object>();
		// 获取请求参数
		Map<String, Object> params = ReqParamsUtil.parseRequest(req);
		try {
			
			String typeId = req.getParameter("type") ;
			String msg = "" ;
			if(typeId != null && !"".equals(typeId)){
				
				if(typeId.equals("3")){
					msg = "品牌活动Banner" ;
					params.put("pageType", "2016102900000006") ;
					params.put("entType", "2016103100000001") ;
					//params.put("type", "11") ;
				} else {
					msg = "培训驿站Banner" ;
					params.put("pageType", "2016102800000002") ;
					params.put("entType", "2016101400000051") ;
					//params.put("type", "2") ;
				}
			} else {
				msg = "培训驿站Banner" ;
				params.put("pageType", "2016102800000002") ;
				params.put("entType", "2016101400000051") ;
				//params.put("type", "2") ;
			}
			map = this.wapExhibitionService.selectAdBanner(params);
			map.put("code", 0);
			map.put("msg", msg);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			this._resolveException2Map(e.getMessage(), map);
		}
		return map;
	}

	/**
	 * 区域字典
	 */
	@RequestMapping("/district")
	public Object getdistrict(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("data", this.wapExhibitionService.selectdistrictlist());
			map.put("code", 0);
			map.put("msg", null);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			this._resolveException2Map(e.getMessage(), map);
		}
		return map;

	}

	/**
	 * 点赞
	 */
	@RequestMapping("/voteup")
	public Object getvoteup(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取请求参数
		Map<String, Object> params = ReqParamsUtil.parseRequest(req);
		// 微信标识
		String openId = req.getParameter("openId");
		//时间
		Date date = new Date();
		// 关联id
		String itemId = req.getParameter("itemId");
		// 类型
		String itemType = req.getParameter("itemType");
		// 点赞
		WhCollection whc = new WhCollection();
		try {
			WhUser whu = this.wapExhibitionService.selectuser(openId);

			if (itemType.equals(1)) {
				// 活动
				whc.setCmreftyp("2016101400000051");
				WhActivity wha = this.wapExhibitionService.selecthd(itemId);
				whc.setCmtitle(wha.getActvshorttitle());
				whc.setCmurl("http://120.77.10.118:8082/dgszwhg/event/detail?actvid=" + itemId);
			} else if (itemType.equals(2)) {
				// 培训
				whc.setCmreftyp("2016101400000052");
				WhTrain wht = this.wapExhibitionService.selectpx(itemId);
				whc.setCmtitle(wht.getTrashorttitle());
				whc.setCmurl("http://120.77.10.118:8082/dgszwhg/gypx/detail/" + itemId);
			} else if (itemType.equals(3)) {
				// 场馆
				whc.setCmreftyp("2016101400000053");
				WhVenue whv = this.wapExhibitionService.selecvenue(itemId);
				whc.setCmtitle(whv.getVenname());
				whc.setCmurl("http://120.77.10.118:8082/dgszwhg/venue/order/" + itemId);
			} else if (itemType.equals(4)) {
				// 艺术作品
				whc.setCmreftyp("2016101400000054");
				WhArtExhibition whex = this.wapExhibitionService.selectys(itemId);
				whc.setCmtitle(whex.getExhtitle());
				whc.setCmurl("http://120.77.10.118:8082/dgszwhg/art/jpwhzdesc?id=" + itemId);
			} else if (itemType.equals(5)) {
				// 艺术团队
				whc.setCmreftyp("2016102400000001");
				WhUserTroupe whuser = this.wapExhibitionService.selecttd(itemId);
				whc.setCmtitle(whuser.getTroupename());
				whc.setCmurl("http://120.77.10.118:8082/dgszwhg/art/ystddesc?id=" + itemId);
			} else if (itemType.equals(6)) {
				// 馆务资讯
				whc.setCmreftyp("2016102800000001");
				WhZxColinfo whinfo = this.wapExhibitionService.selectgw(itemId);
				whc.setCmtitle(whinfo.getClnftltle());
				whc.setCmurl("http://120.77.10.118:8082/dgszwhg/xuanchuan/gwgkinfo?clnfid=" + itemId);
			} else if (itemType.equals(7)) {
				// 品牌活动
				whc.setCmreftyp("2016103100000001");
				WhBrand whb = this.wapExhibitionService.selectbrand(itemId);
				whc.setCmtitle(whb.getBratitle());
				whc.setCmurl("http://120.77.10.118:8082/dgszwhg/event/pplist?braid=" + itemId);
			}
			whc.setCmuid(whu.getId());
			whc.setCmid(this.service.getKey("WhCollection"));
			whc.setCmdate(date);
			whc.setCmrefid(itemId);
			whc.setCmopttyp("2");
			this.wapExhibitionService.addwhc(whc);
			map.put("code", 0);
			map.put("msg", null);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			this._resolveException2Map(e.getMessage(), map);
		}
		return map;

	}

	/**
	 * 评论
	 */
	@RequestMapping("/comment")
	public Object getcomment(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取请求参数
		Map<String, Object> params = ReqParamsUtil.parseRequest(req);
		// 微信标识
		String openId = req.getParameter("openId");
		// 评论内容
		String content = req.getParameter("content");
		//时间
		Date date = new Date();
		// 关联id
		String itemId = req.getParameter("itemId");
		// 类型
		String itemType = req.getParameter("itemType");
		// 点赞
		WhComment whc = new WhComment();
		try {
			WhUser whu = this.wapExhibitionService.selectuser(openId);

			if (itemType.equals(1)) {
				// 活动
				whc.setRmreftyp("2016101400000051");
				WhActivity wha = this.wapExhibitionService.selecthd(itemId);
				whc.setRmtitle(wha.getActvshorttitle());
				whc.setRmurl("http://120.77.10.118:8082/dgszwhg/event/detail?actvid=" + itemId);
			} else if (itemType.equals(2)) {
				// 培训
				whc.setRmreftyp("2016101400000052");
				WhTrain wht = this.wapExhibitionService.selectpx(itemId);
				whc.setRmtitle(wht.getTrashorttitle());
				whc.setRmurl("http://120.77.10.118:8082/dgszwhg/gypx/detail/" + itemId);
			} else if (itemType.equals(3)) {
				// 场馆
				whc.setRmreftyp("2016101400000053");
				WhVenue whv = this.wapExhibitionService.selecvenue(itemId);
				whc.setRmtitle(whv.getVenname());
				whc.setRmurl("http://120.77.10.118:8082/dgszwhg/venue/order/" + itemId);
			} else if (itemType.equals(4)) {
				// 艺术作品
				whc.setRmreftyp("2016101400000054");
				WhArtExhibition whex = this.wapExhibitionService.selectys(itemId);
				whc.setRmtitle(whex.getExhtitle());
				whc.setRmurl("http://120.77.10.118:8082/dgszwhg/art/jpwhzdesc?id=" + itemId);
			} else if (itemType.equals(5)) {
				// 艺术团队
				whc.setRmreftyp("2016102400000001");
				WhUserTroupe whuser = this.wapExhibitionService.selecttd(itemId);
				whc.setRmtitle(whuser.getTroupename());
				whc.setRmurl("http://120.77.10.118:8082/dgszwhg/art/ystddesc?id=" + itemId);
			} else if (itemType.equals(6)) {
				// 馆务资讯
				whc.setRmreftyp("2016102800000001");
				WhZxColinfo whinfo = this.wapExhibitionService.selectgw(itemId);
				whc.setRmtitle(whinfo.getClnftltle());
				whc.setRmurl("http://120.77.10.118:8082/dgszwhg/xuanchuan/gwgkinfo?clnfid=" + itemId);
			} else if (itemType.equals(7)) {
				// 品牌活动
				whc.setRmreftyp("2016103100000001");
				WhBrand whb = this.wapExhibitionService.selectbrand(itemId);
				whc.setRmtitle(whb.getBratitle());
				whc.setRmurl("http://120.77.10.118:8082/dgszwhg/event/pplist?braid=" + itemId);
			}
			whc.setRmuid(whu.getId());
			whc.setRmid(this.service.getKey("WhComment"));
			whc.setRmdate(date);
			whc.setRmrefid(itemId);
			whc.setRmcontent(content);
			this.wapExhibitionService.addwhcom(whc);
			map.put("code", 0);
			map.put("msg", null);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			this._resolveException2Map(e.getMessage(), map);
		}
		return map;

	}

	/**
	 * 搜索接口
	 */
	@RequestMapping("/search")
	public Object getsearch(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();

		// 获取请求参数
		Map<String, Object> param = ReqParamsUtil.parseRequest(req);
		List<Map> list = new ArrayList<>();
		int page = 1;
		int rows = 10;
		try {
			if (param.containsKey("keyword")) {
				param.put("keyword", "%" + param.get("keyword") + "%");
			}
			if (param.containsKey("index") && param.get("index") != null) {
				page = Integer.valueOf(param.get("index").toString());
			}
			if (param.containsKey("size") && param.get("size") != null) {
				rows = Integer.valueOf(param.get("size").toString());
			}
			PageHelper.startPage(page, rows);
			list = this.wapExhibitionService.selctsearch(param);
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
