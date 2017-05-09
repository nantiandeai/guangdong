package com.creatoo.hn.actions.home.art;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhArt;
import com.creatoo.hn.model.WhArtExhibition;
import com.creatoo.hn.model.WhCfgAdv;
import com.creatoo.hn.model.WhEntsource;
import com.creatoo.hn.model.WhMagepage;
import com.creatoo.hn.model.WhMagezine;
import com.creatoo.hn.model.WhTyp;
import com.creatoo.hn.model.WhUserArtist;
import com.creatoo.hn.model.WhUserTroupe;
import com.creatoo.hn.model.WhUserTroupeuser;
import com.creatoo.hn.services.admin.arts.DrscService;
import com.creatoo.hn.services.admin.arts.MagezineService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.home.art.ArtService;
import com.creatoo.hn.utils.ReqParamsUtil;

/**
 * 艺术广场页面导航
 * @author wangxl
 * @version 2016.10.19
 */
@RestController
@RequestMapping("/art")
public class ArtAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public CommService commservice;
	
	@Autowired
	public ArtService artService;
	
	@Autowired
	public DrscService drscService;
	
	@Autowired
	public MagezineService magezineService;
	
	/**
	 * -----------------------------精品文化展---------------------------------------
	 */
	/**
	 * 精品文化展
	 * @return 精品文化展
	 */
	@RequestMapping("/jpwhz")
	public ModelAndView jpwhz(){
		ModelAndView view = new ModelAndView( "home/art/jpwhz" );
		try {
			//查询艺术分类
			List<WhTyp> typList = commservice.findWhtyp("0");
			view.addObject("artList", typList);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return view;
	}
	
	
	/**
	 * 精品文化展-查询
	 * @return 精品文化展
	 */
	@RequestMapping("/srchjpwhz")
	public Object srchjpwhz(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> param = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.artService.paggingJpwhz(param);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
        return rtnMap;
	}
	
	
	/**
	 * 精品文化展-作品列表
	 * @return 精品文化展-作品列表
	 */
	@RequestMapping("/jpwhzlist")
	public ModelAndView jpwhzlist(String id){
		ModelAndView view = new ModelAndView( "home/art/jpwhzlist" );
		try {
			//查询艺术分类
			List<WhTyp> typList = commservice.findWhtyp("0");
			view.addObject("artList", typList);
			
			//查询精品文化展标题
			WhArtExhibition exhibition = this.artService.srchExhibition(id);
			view.addObject("exh", exhibition);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return view;
	}
	
	/**
	 * 精品文化展作品-查询
	 * @return 精品文作品化展
	 */
	@RequestMapping("/srchjpwhzlist")
	public Object srchjpwhzlist(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> param = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.artService.srchjpwhzlist(param);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
        return rtnMap;
	}
	
	/**
	 * 进入精品文化作品详情界面
	 * @param id 艺术作品标识
	 * @return
	 */
	@RequestMapping("/jpwhzdesc")
	public ModelAndView jpwhzdesc(String id){
		ModelAndView view = new ModelAndView( "home/art/jpwhzdesc" );
		try {
			//查询个人艺术作品详情
			WhArt whart = this.artService.srchWhartInfo(id);
			view.addObject("art", whart);
			
			//标签
			view.addObject("tags", this.artService.srchTags(whart.getArttags()));
			
			//关键字
			view.addObject("keys", this.artService.srchKeys(whart.getArtkeys()));
			
			//查询文化展信息
			WhArtExhibition exhibition = this.artService.srchExhibition(whart.getArttypid());
			view.addObject("exh", exhibition);
			
			//查询个人艺术作品图片列表
			List<WhEntsource> picList = this.artService.srchWhartSrc(whart.getArtid(), "2016101400000055", "2016101400000054");
			if(picList != null && picList.size() > 0){
				view.addObject("picList", picList);
			}
			
			//查询个人艺术作品视频列表
			List<WhEntsource> videoList = this.artService.srchWhartSrc(whart.getArtid(), "2016101400000056", "2016101400000054");
			if(videoList != null && videoList.size() > 0){
				view.addObject("videoList", videoList);
			}
			
			//查询个人艺术作品音频列表
			List<WhEntsource> audioList = this.artService.srchWhartSrc(whart.getArtid(), "2016101400000057", "2016101400000054");
			if(audioList != null && audioList.size() > 0){
				view.addObject("audioList", audioList);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return view;
	}
	
	
	
	
	/**
	 * -----------------------------个人作品展---------------------------------------
	 */
	
	/**
	 * 个人作品展
	 * @return 个人作品展
	 */
	@RequestMapping("/grzpz")
	public ModelAndView grzpz(){
		ModelAndView view = new ModelAndView( "home/art/grzpz" );
		try {
			//查询艺术分类
			List<WhTyp> typList = commservice.findWhtyp("0");
			view.addObject("artList", typList);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return view;
	}
	


	
	/**
	 * 个人作品化展-查询
	 * @return 个人作品化展
	 */
	@RequestMapping("/srchgrzpz")
	public Object srchgrzpz(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> param = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.artService.paggingGrzpz(param);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
        return rtnMap;
	}
	
	
	
	/**
	 * 个人作品化展-个人主页
	 * @return 个人作品化展
	 */
	@RequestMapping("/grzpzlist")
	public ModelAndView grzpzlist(String id){
		ModelAndView view = new ModelAndView( "home/art/grzpzlist" );
		try {
			//查询艺术家信息
			view.addObject("artist", this.artService.srchArtist(id));
			
			//查询艺术家作品
			view.addObject("artistList", this.artService.srchArtList(id));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return view;
	}
	
	/**
	 * 艺术家个人作品列表-查询
	 * @return 艺术家个人作品列表
	 */
	@RequestMapping("/srchgrzpzlist")
	public Object srchgrzpzlist(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> param = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.artService.paggingGrzpzList(param);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
        return rtnMap;
	}
	
	/**
	 * 进入个人作品详情界面
	 * @param id 艺术作品标识
	 * @return
	 */
	@RequestMapping("/grzpzdesc")
	public ModelAndView grzpz(String id){
		ModelAndView view = new ModelAndView( "home/art/grzpzdesc" );
		try {
			//查询个人艺术作品详情
			WhArt whart = this.artService.srchWhartInfo(id);
			view.addObject("art", whart);
			
			//标签
			view.addObject("tags", this.artService.srchTags(whart.getArttags()));
			
			//关键字
			view.addObject("keys", this.artService.srchKeys(whart.getArtkeys()));
			
			//查询文化展信息
			WhUserArtist artist = this.artService.srchArtist(whart.getArttypid());
			view.addObject("artist", artist);
			
			//查询个人艺术作品图片列表
			List<WhEntsource> picList = this.artService.srchWhartSrc(whart.getArtid(), "2016101400000055", "2016101400000054");
			if(picList != null && picList.size() > 0){
				view.addObject("picList", picList);
			}
			
			//查询个人艺术作品视频列表
			List<WhEntsource> videoList = this.artService.srchWhartSrc(whart.getArtid(), "2016101400000056", "2016101400000054");
			if(videoList != null && videoList.size() > 0){
				view.addObject("videoList", videoList);
			}
			
			//查询个人艺术作品音频列表
			List<WhEntsource> audioList = this.artService.srchWhartSrc(whart.getArtid(), "2016101400000057", "2016101400000054");
			if(audioList != null && audioList.size() > 0){
				view.addObject("audioList", audioList);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return view;
	}
	
	
	
	/** -------------------------------艺术团队-------------------------------------------------------*/
	/**
	 * 艺术团队
	 * @return 艺术团队
	 */
	@RequestMapping("/ystd")
	public ModelAndView ystd(){
		ModelAndView view = new ModelAndView( "home/art/ystd" );
		try {
			//查询艺术分类
			List<WhTyp> typList = commservice.findWhtyp("0");
			view.addObject("artList", typList);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return view;
	}
	
	/**
	 * 艺术团队-查询
	 * @return 艺术团队
	 */
	@RequestMapping("/srchystd")
	public Object srchystd(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> param = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.artService.paggingYstd(param);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
        return rtnMap;
	}
	
	/**
	 * 艺术团队详情界面
	 * @param id 艺术团队标识
	 * @return 艺术团队详情界面
	 */
	@RequestMapping("/ystddesc")
	public ModelAndView ystddesc(String id){
		ModelAndView view = new ModelAndView( "home/art/ystddesc" );
		try {
			//查询艺术团信息
			WhUserTroupe troupe = this.artService.srchTroupe(id);
			view.addObject("troupe", troupe);

			//标签
			view.addObject("tags", this.artService.srchTags(troupe.getTroupetag()));
			
			//关键字
			view.addObject("keys", this.artService.srchKeys(troupe.getTroupekey()));
			
			//查询个人艺术作品图片列表
			List<WhEntsource> picList = this.artService.srchWhartSrc(troupe.getTroupeid(), "2016101400000055", "2016102400000001");
			if(picList != null && picList.size() > 0){
				view.addObject("picList", picList);
			}
			
			//查询个人艺术作品视频列表
			List<WhEntsource> videoList = this.artService.srchWhartSrc(troupe.getTroupeid(), "2016101400000056", "2016102400000001");
			if(videoList != null && videoList.size() > 0){
				view.addObject("videoList", videoList);
			}
			
			//查询个人艺术作品音频列表
			List<WhEntsource> audioList = this.artService.srchWhartSrc(troupe.getTroupeid(), "2016101400000057", "2016102400000001");
			if(audioList != null && audioList.size() > 0){
				view.addObject("audioList", audioList);
			}
			
			//查询成员列表
			List<WhUserTroupeuser> users = this.artService.srchTroupeUser(troupe.getTroupeid());
			if(users != null && users.size() > 0){
				view.addObject("troupUserList", this.artService.srchTroupeUser(troupe.getTroupeid()));
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return view;
	}
	
	
	/** -----------------------------------------数字资源------------------------------ */
	/**
	 * 数字资源
	 * @return 数字资源
	 */
	@RequestMapping("/szzy")
	public ModelAndView szzy(){
		ModelAndView view = new ModelAndView( "home/art/szzy" );
		try {
			//查询艺术分类
			List<WhTyp> typList = commservice.findWhtyp("0");
			view.addObject("artList", typList);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return view;
	}
	
	/**
	 * 数字资源-查询
	 * @return 数字资源
	 */
	@RequestMapping("/srchszzy")
	public Object srchszzy(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> param = ReqParamsUtil.parseRequest(req);
		
		//分页查询
		param.put("drscstate", "3");
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.drscService.srchPagging(param);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
        return rtnMap;
	}
	
	
	/** -----------------------------------------电子杂志------------------------------ */
	/**
	 * 电子杂志
	 * @return 数字资源
	 */
	@RequestMapping("/dzzz")
	public ModelAndView dzzz(){
		ModelAndView view = new ModelAndView( "home/art/dzzz" );
		try {
			//查询艺术分类
			List<WhTyp> typList = commservice.findWhtyp("0");
			view.addObject("artList", typList);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return view;
	}
	
	/**
	 * 数字资源-查询
	 * @return 数字资源
	 */
	@RequestMapping("/srchdzzz")
	public Object srchdzzz(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> param = ReqParamsUtil.parseRequest(req);
		
		//分页查询
		param.put("magestate", "3");
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.magezineService.findMage(param);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
        return rtnMap;
	}
	
	/**
	 * 查询电子杂志页码
	 */
	@RequestMapping("/dzzzdetail")
	public ModelAndView findMage(String pagemageid){
		ModelAndView view = new ModelAndView("home/art/dzzzdetail");
		try {
			//查找电子杂志
			List<WhMagezine> mage = this.magezineService.selMagezine();
			if (mage != null && mage.size() > 0) {
				view.addObject("mage", mage);
			} 
			
			//查找点击的电子杂志
			List<WhMagepage> mageidx = (List<WhMagepage>) this.magezineService.findPageinfo(pagemageid);
			if (mageidx != null ) {
				view.addObject("mageidx", mageidx);
			} 
			//查找广告
			List<WhCfgAdv> listAdv = this.magezineService.advListLoad();
			if(listAdv != null && listAdv.size() > 0){
				view.addObject("listAdv", listAdv.get(0));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 查询电子杂志页码
	 * @param pagemageids
	 * @return
	 */
	@RequestMapping("/showPage")
	public Object findPage(String pagemageid){
		Object page = null;
		try {
			page = this.magezineService.findPageinfo(pagemageid);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return page;
	}
}
