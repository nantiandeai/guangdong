package com.creatoo.hn.actions.home.agdszzg;

import com.creatoo.hn.ext.emun.EnumTypeClazz;
import com.creatoo.hn.model.*;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.home.agdszzg.SzzgService;
import com.creatoo.hn.services.home.art.ArtService;
import com.creatoo.hn.utils.ReqParamsUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数字展览
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/agdszzg")
public class ExhibitionszAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());

	/**
	 * 公用服务类
	 */
	@Autowired
	private CommService commservice;
	
	@Autowired
	public ArtService artService;
	@Autowired
	public SzzgService szzgService;
	
	/**
	 * 查询数字展览全分类信息
	 */
	@RequestMapping("/index")
	public ModelAndView news(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView view = new ModelAndView( "home/agdszzg/shuzizhanguan" );
//		Map<String, Object> param = ReqParamsUtil.parseRequest(req);
		try {
			//查询艺术分类
			List<WhgYwiType> typList = commservice.findYwiType(EnumTypeClazz.TYPE_ART.getValue());
			view.addObject("artList", typList);

			//查询展览
			List<WhgExh> list = szzgService.serchList();
			view.addObject("exh", list);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}

	/**
	 * 数字展览列表
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest req){
		ModelAndView view = new ModelAndView( "home/agdszzg/shuzizhanguanliebiao" );
		Map<String, Object> param = ReqParamsUtil.parseRequest(req);
		try {
			//查询展览
			Map<String, Object> rtnMap  = szzgService.paggingSzzg(param);
//			view.addObject("exh", list);
			try {
				view.addObject("total", rtnMap.get("total"));
				view.addObject("exh", rtnMap.get("rows"));
				view.addObject("page", rtnMap.get("page"));
			} catch (Exception e) {
				view.addObject("total", 0);
				view.addObject("rows", null);
				view.addObject("page", 1);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}

	/**
	 * 数字展览详情
	 */
	@RequestMapping("/info")
	public ModelAndView info(HttpServletRequest req){
		ModelAndView view = new ModelAndView( "home/agdszzg/shuzizhanguaninfo" );
		try {
			String exhid = req.getParameter("exhid");
			//查询展览详情
			WhgExh whgExh = szzgService.t_srchOne(exhid);
			view.addObject("whgExh", whgExh);
			//查询展览作品详情
			List<WhgExhArt> whgExhArt = szzgService.srchOne_art(exhid);
			view.addObject("whgExhArt", whgExhArt);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	/**
	 * AJAX数字展览
	 * @return AJAX数字展馆
	 */
	@RequestMapping("/srchszzg")
	public Object srchjpwhz(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> param = ReqParamsUtil.parseRequest(req);

		//分页查询
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		try {
			rtnMap = this.szzgService.paggingSzzg(param);
		} catch (Exception e) {
			rtnMap.put("total", 0);
			rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
		return rtnMap;
	}


	/**
	 * AJAX数字展览作品
	 * @return AJAX数字展馆
	 */
	@RequestMapping("/srchszzg/artlist")
	public Object srchjpwhz_art(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> param = ReqParamsUtil.parseRequest(req);

		//分页查询
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		try {
			rtnMap = this.szzgService.paggingSzzgArt(param);
		} catch (Exception e) {
			rtnMap.put("total", 0);
			rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
		return rtnMap;
	}
	
	/**
	 * 进入数字展览作品列表页面
	 */
	@RequestMapping("/zglist")
	public ModelAndView zglist(HttpServletRequest req, HttpServletResponse resp, String id){
		ModelAndView view = new ModelAndView( "home/agdszzg/zglist" );
		try {
			//查询艺术分类
			List<WhgYwiType> typList = commservice.findYwiType(EnumTypeClazz.TYPE_ART.getValue());
			view.addObject("artList", typList);
			
			//查询精品文化展标题
			//WhArtExhibition exhibition = this.artService.srchExhibition(id);
			WhgExh exhibition = this.szzgService.t_srchOne(id);
			view.addObject("exh", exhibition);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	/**
	 * 查询数字馆全信息详情
	 */
	@RequestMapping("/zginfo")
	public ModelAndView newsinfo(String id){
		ModelAndView view = new ModelAndView( "home/agdszzg/zginfo" );
		try {
			//查询个人艺术作品详情
			WhgExhArt whart = this.szzgService.t_srchOne_art(id);
			view.addObject("art", whart);
			
			//前一个艺术作品
			view.addObject("preArt", this.szzgService.getPreArt(id, whart.getArtexhid()));
			
			//后一个艺术作品
			view.addObject("nextArt", this.szzgService.getNextArt(id, whart.getArtexhid()));
			
			//查询文化展信息
			WhgExh exhibition = this.szzgService.t_srchOne(whart.getArtexhid());
			view.addObject("exh", exhibition);
			
			//查询个人艺术作品图片列表
			Map<String, List<WhgComResource>> map = this.szzgService.findCommResource(whart.getArtid());
			List<WhgComResource> picList = map.get("pic");
			List<WhgComResource> videoList = map.get("vod");
			List<WhgComResource> audioList = map.get("aud");
			if(picList != null && picList.size() > 0){
				view.addObject("picList", picList);
			}
			
			//查询个人艺术作品视频列表
			if(videoList != null && videoList.size() > 0){
				view.addObject("videoList", videoList);
			}
			
			//查询个人艺术作品音频列表
			if(audioList != null && audioList.size() > 0){
				view.addObject("audioList", audioList);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}	
}
