package com.creatoo.hn.actions.home.agdzyfw;

import java.io.BufferedReader;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.creatoo.hn.ext.emun.EnumLBTClazz;
import com.creatoo.hn.ext.emun.EnumTypeClazz;
import com.creatoo.hn.model.*;
import com.creatoo.hn.services.home.agdindex.IndexPageService;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.home.agdgwgk.GwgkService;
import com.creatoo.hn.services.home.agdzyfw.ZyfwService;
import com.creatoo.hn.utils.ReqParamsUtil;

/**
 * 志愿服务
 * @author wangxl
 * @version 2016.11.16
 */
@RestController
@RequestMapping("/agdzyfw")
public class ZyfwAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * 公用服务类
	 */
	@Autowired
	public CommService commservice;
	
	/**
	 * 馆务公开服务类
	 */
	@Autowired
	public GwgkService gwgkService;
	
	@Autowired
	private ZyfwService service;
	/**
	 * 广告图DAO
	 */
	@Autowired
	public IndexPageService indexPageService;
	/**
	 * 首页-志愿服务
	 * @return 首页-志愿服务
	 */
	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView view = new ModelAndView( "home/agdzyfw/index" );
		BufferedReader in = null;
		String content = null;
		try {
			//首页志愿公告
			String typ = "2016112200000008";
			view.addObject("zygg", this.gwgkService.queryAllColinfo(typ));
			//首页志愿资讯
			String realtype = "2016112200000009";
			view.addObject("zyzx", this.gwgkService.queryAllColinfo(realtype));
			//首页查公告页面配置图
			List<WhCfgList> ympz=this.service.getzxpz();
			if (ympz != null && !ympz.isEmpty()) {
				view.addObject("ympz",ympz);
			}
			
			//广告图
			String cfgadvpagetype = "2016122100000011";
			WhCfgAdv adv = this.service.findAdv(cfgadvpagetype);
			if (adv != null && !adv.equals("")) {
				view.addObject("adv",adv);
			}

			//首页动态图片
			view.addObject("advList", indexPageService.findLBT(EnumLBTClazz.LBT_ADV_PC_VOL.getValue(), 5));
			
			//首页取风采展示配置的图片
			List<WhgYwiLbt> fengcai = this.service.selFengcai();
			if (fengcai != null && !fengcai.isEmpty()) {
				view.addObject("fengcai", fengcai);
			}
			
			//获取志愿服务的数据
			Document doc = Jsoup.connect("http://www.gdzyz.cn/index?districtId=402800e245df56eb0145dfd7d6bb0043").get();
			Elements title = doc.getElementsByClass("MarT61");
			Elements title1 = doc.getElementsByClass("MarT62");
			Elements title2 = doc.getElementsByClass("MarT64");

//	        Element title = doc.getElementById("countDis");
//	        Element title1 = doc.getElementById("time");
//	        Element title2 = doc.getElementById("countVo");

			if (title.val() != null) {
				view.addObject("team", title2.text());
			}
	        if (title1.val() != null) {
	        	view.addObject("time", title1.text());
			}
	        if (title2.val() != null) {
	        	view.addObject("person", title.text());
	        }
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 志愿公告
	 * @return 志愿公告
	 */
	@RequestMapping("/notice")
	public ModelAndView notice(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView view = new ModelAndView( "home/agdzyfw/notice" );
		try {
			//资讯栏目
			String realtype = "2016112200000008";
			if(req.getParameter("type") != null){
				realtype = req.getParameter("type");
			}
			
			//获取请求参数
			Map<String, Object> param = ReqParamsUtil.parseRequest(req);
			
			//分页查询
	        Map<String, Object> rtnMap = this.gwgkService.paggingColinfo(param, realtype);
	        try {
				view.addObject("total", rtnMap.get("total"));
				view.addObject("rows", rtnMap.get("rows"));
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
	 * 志愿公告详情
	 * @return 志愿公告详情
	 */
	@RequestMapping("/noticeinfo")
	public ModelAndView noticeinfo(String id, String type){
		ModelAndView view = new ModelAndView( "home/agdzyfw/noticeinfo" );
		try {
			String realtype = "2016112200000008";
			if(type != null){
				realtype = type;
			}
			
			//当前资讯
			view.addObject("wh_zx_colinfo", this.gwgkService.queryOneColinfo(id, realtype));
			
			//next
			view.addObject("wh_zx_colinfo_next", this.gwgkService.queryNextColinfo(id, realtype));
			
			//相关推荐
			view.addObject("wh_zx_colinfo_ref", this.gwgkService.queryREFColinfo(id, realtype));
			
			//资源图片
			String enttype="2016101400000055";
			String reftype = "2016102800000001";
			List<WhEntsource> whe= this.gwgkService.selecent(id,enttype,reftype);
			view.addObject("loadwhe", whe);
			
			//资源视频
			String types="2016101400000056";
			List<WhEntsource> ent= this.gwgkService.selecsource(id,types,reftype);
			view.addObject("loadent", ent);
			
			//资源音频
			String clazz="2016101400000057";
			List<WhEntsource> whent= this.gwgkService.selecwhent(id,clazz,reftype);
			view.addObject("loadclazz", whent);
			
			//上传
			List<WhZxUpload> whup =  this.gwgkService.selecup(id);
			view.addObject("loadlist", whup);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 志愿资讯
	 * @return 志愿资讯
	 */
	@RequestMapping("/news")
	public ModelAndView news(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView view = new ModelAndView( "home/agdzyfw/news" );
		try {
			//资讯栏目
			String realtype = "2016112200000009";
			if(req.getParameter("type") != null){
				realtype = req.getParameter("type");
			}
			
			//获取请求参数
			Map<String, Object> param = ReqParamsUtil.parseRequest(req);
			
			//分页查询
	        Map<String, Object> rtnMap = this.gwgkService.paggingColinfo(param, realtype);
	        try {
				view.addObject("total", rtnMap.get("total"));
				view.addObject("rows", rtnMap.get("rows"));
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
	 *  志愿资讯详情
	 * @return  志愿资讯详情
	 */
	@RequestMapping("/newsinfo")
	public ModelAndView newsinfo(String id, String type){
		ModelAndView view = new ModelAndView( "home/agdzyfw/newsinfo" );
		try {
			String realtype = "2016112200000009";
			if(type != null){
				realtype = type;
			}
			
			//当前资讯
			view.addObject("wh_zx_colinfo", this.gwgkService.queryOneColinfo(id, realtype));
			
			//next
			view.addObject("wh_zx_colinfo_next", this.gwgkService.queryNextColinfo(id, realtype));
			
			//相关推荐
			view.addObject("wh_zx_colinfo_ref", this.gwgkService.queryREFColinfo(id, realtype));

			//资源图片
//			String enttype="2016101400000055";
//			String reftype = "2016102800000001";
//			List<WhEntsource> whe= this.gwgkService.selecent(id,enttype,reftype);
//			view.addObject("loadwhe", whe);
//
//			//资源视频
//			String types="2016101400000056";
//			List<WhEntsource> ent= this.gwgkService.selecsource(id,types,reftype);
//			view.addObject("loadent", ent);
//
//			//资源音频
//			String clazz="2016101400000057";
//			List<WhEntsource> whent= this.gwgkService.selecwhent(id,clazz,reftype);
//			view.addObject("loadclazz", whent);

			//图片
			List<WhgComResource> pic = this.commservice.findRescource("1","11", id);
			view.addObject("loadwhe",pic );
			//视频
			List<WhgComResource> vido = this.commservice.findRescource("2","11", id);
			view.addObject("loadent",vido );
			//音频
			List<WhgComResource> musci = this.commservice.findRescource("3","11", id);
			view.addObject("loadclazz",musci );
			
			//上传
			List<WhZxUpload> whup =  this.gwgkService.selecup(id);
			view.addObject("loadlist", whup);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 志愿活动
	 * @return 志愿活动
	 */
	@RequestMapping("/huodong")
	public ModelAndView huodong(HttpServletRequest req){
		ModelAndView view = new ModelAndView( "home/agdzyfw/huodong" );
		try {
			//获取请求参数
			Map<String, Object> param = ReqParamsUtil.parseRequest(req);
			
			//查询志愿培训
	        Map<String, Object> rtnMap = this.service.selzyhd(param);
			
			//艺术分类
//	    	List<WhTyp> hdtype = this.commservice.findWhtyp("20");
	    	List<WhgYwiType> hdtype = this.commservice.findYwiType(EnumTypeClazz.TYPE_VOL_ACT.getValue());
	    	//区域
//			List<WhTyp> qytype = this.commservice.findWhtyp("8");
			List<WhgYwiType> qytype = this.commservice.findYwiType(EnumTypeClazz.TYPE_AREA.getValue());
	    	try {
	        	view.addObject("params", param);
	        	view.addObject("qytype", qytype);
	        	view.addObject("hdtype", hdtype);
				view.addObject("total", rtnMap.get("total"));
				view.addObject("rows", rtnMap.get("rows"));
				view.addObject("page", rtnMap.get("page"));
				view.addObject("pageSize", rtnMap.get("pageSize"));
			} catch (Exception e) {
				view.addObject("qytype", null);
				view.addObject("hdtype", null);
				view.addObject("total", 0);
				view.addObject("rows", null);
				view.addObject("page", 1);
				view.addObject("pageSize", 0);
			}
	    	
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 *  志愿活动详情
	 * @return  志愿活动详情
	 */
	@RequestMapping("/huodonginfo")
	public ModelAndView huodonginfo(String zyhdid){
		ModelAndView view = new ModelAndView( "home/agdzyfw/huodonginfo" );
		try {
			//
			//详情查询
			WhZyhd whZyhd = this.service.findZyhd(zyhdid);
			if (whZyhd != null) {
				view.addObject("zyhd", whZyhd);
			}
			zyhdid = whZyhd.getZyhdid();
			//志愿培训详情推荐
			List<WhZyhd> kecheng = this.service.selNewZyhd(zyhdid);
			if (kecheng != null && !kecheng.isEmpty()) {
				view.addObject("kecheng", kecheng);
			}

			//查询资源图片 音频 视频
			//图片
			List<WhgComResource> pic = this.commservice.findRescource("1","7", zyhdid);
			view.addObject("pic",pic );
			//视频
			List<WhgComResource> vido = this.commservice.findRescource("2","7", zyhdid);
			view.addObject("vido",vido );
			//音频
			List<WhgComResource> musci = this.commservice.findRescource("3","7", zyhdid);
			view.addObject("musci",musci );
			//文档
			List<WhgComResource> doc = this.commservice.findRescource("4","7", zyhdid);
			view.addObject("doc",doc );

//			String enttype = "2016101400000055";
//			String reftype = "2016112800000002";
//			String refid = zyhdid;
//			List<WhEntsource> pic = this.service.findpic(refid,enttype,reftype);
//			if (pic != null && pic.size() >0) {
//				view.addObject("pic", pic);
//			}
//			enttype = "2016101400000056";
//			List<WhEntsource> vido = this.service.findvido(refid,enttype,reftype);
//			if (vido != null && vido.size() >0) {
//				view.addObject("vido", vido);
//			}
//			enttype = "2016101400000057";
//			List<WhEntsource> musci = this.service.findmusci(refid,enttype,reftype);
//			if (musci != null && musci.size() >0) {
//				view.addObject("musci", musci);
//			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 志愿培训
	 * @return 志愿培训
	 */
	@RequestMapping("/peixun")
	public ModelAndView peixun(HttpServletRequest req){
		ModelAndView view = new ModelAndView( "home/agdzyfw/peixun" );
		try {
			//获取请求参数
			Map<String, Object> param = ReqParamsUtil.parseRequest(req);
			
			//查询志愿培训
	        Map<String, Object> rtnMap = this.service.selzypx(param);
	        //艺术分类
//	    	List<WhTyp> pxtype = this.commservice.findWhtyp("19");
	    	List<WhgYwiType> pxtype = this.commservice.findYwiType(EnumTypeClazz.TYPE_VOL_TRAIN.getValue());

	    	
	        try {
	        	view.addObject("params", param);
	        	
	        	view.addObject("pxtype", pxtype);

				view.addObject("total", rtnMap.get("total"));
				view.addObject("rows", rtnMap.get("rows"));
				view.addObject("page", rtnMap.get("page"));
				view.addObject("pageSize", rtnMap.get("pageSize"));
			} catch (Exception e) {
				view.addObject("pxtype", null);
				view.addObject("total", 0);
				view.addObject("rows", null);
				view.addObject("page", 1);
				view.addObject("pageSize", 0);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	/**
	 * 志愿培训详情
	 * @return 志愿培训详情
	 */
	@RequestMapping("/peixuninfo")
	public ModelAndView peixuninfo(String zypxid){
		ModelAndView view = new ModelAndView( "home/agdzyfw/peixuninfo" );
		try {
			//详情查询
			WhZypx whZypx = this.service.selZypx(zypxid);
			if (whZypx != null) {
				view.addObject("zypx", whZypx);
			}
			zypxid = whZypx.getZypxid();
			//当前时间
			Date now = new Date();
			view.addObject("now", now);
			//志愿培训详情推荐视频
			List<WhZypx> shipin = this.service.selNewZypx(zypxid);
			if (shipin != null && !shipin.isEmpty()) {
				view.addObject("shipin", shipin);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 项目示范
	 * @return 项目示范
	 */
	@RequestMapping("/xiangmu")
	public ModelAndView xiangmu(HttpServletRequest req){
		ModelAndView view = new ModelAndView( "home/agdzyfw/xiangmu" );
		try {
			//获取请求参数
			Map<String, Object> param = ReqParamsUtil.parseRequest(req);
			
			//查询项目展示
	        Map<String, Object> rtnMap = this.service.selectxm(param);
	    	try {
	        	view.addObject("params", param);
				view.addObject("total", rtnMap.get("total"));
				view.addObject("rows", rtnMap.get("rows"));
				view.addObject("page", rtnMap.get("page"));
				view.addObject("pageSize", rtnMap.get("pageSize"));
			} catch (Exception e) {
				view.addObject("total", 0);
				view.addObject("rows", null);
				view.addObject("page", 1);
				view.addObject("pageSize", 0);
			}
	    	
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 项目示范详情
	 * @return 项目示范详情
	 */
	@RequestMapping("/xiangmuinfo")
	public ModelAndView xiangmuinfo(String zyfcxmid){
		ModelAndView view = new ModelAndView( "home/agdzyfw/xiangmuinfo" );
		try {
			//详情查询
			WhZyfcXiangmu whzy = this.service.selectwhzy(zyfcxmid);
			if (whzy != null) {
				view.addObject("whzy", whzy);
			}
			//查询资源图片 音频 视频
//			String enttype = "2016101400000055";
//			String reftype = "2016112900000015";
//			String refid = zyfcxmid;
//			List<WhEntsource> pic = this.service.findpic(refid,enttype,reftype);
//			if (pic != null && pic.size() >0) {
//				view.addObject("pic", pic);
//			}
//			enttype = "2016101400000056";
//			List<WhEntsource> vido = this.service.findvido(refid,enttype,reftype);
//			if (vido != null && vido.size() >0) {
//				view.addObject("vido", vido);
//			}
//			enttype = "2016101400000057";
//			List<WhEntsource> musci = this.service.findmusci(refid,enttype,reftype);
//			if (musci != null && musci.size() >0) {
//				view.addObject("musci", musci);
//			}
			//查询资源图片 音频 视频
			//图片
			List<WhgComResource> pic = this.commservice.findRescource("1","9", zyfcxmid);
			view.addObject("pic",pic );
			//视频
			List<WhgComResource> vido = this.commservice.findRescource("2","9", zyfcxmid);
			view.addObject("vido",vido );
			//音频
			List<WhgComResource> musci = this.commservice.findRescource("3","9", zyfcxmid);
			view.addObject("musci",musci );
			//文档
			List<WhgComResource> doc = this.commservice.findRescource("4","9", zyfcxmid);
			view.addObject("doc",doc );

			//带条件查询项目信息
			List<WhZyfcXiangmu> whxm = this.service.selectlistxm(zyfcxmid);
			view.addObject("whxm", whxm);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	
	/**
	 * 优秀组织
	 * @return 优秀组织
	 */
	@RequestMapping("/youxiuzuzhi")
	public ModelAndView youxiuzuzhi(HttpServletRequest req){
		ModelAndView view = new ModelAndView( "home/agdzyfw/youxiuzuzhi" );
		try {
			
			//获取请求参数
			Map<String, Object> param = ReqParamsUtil.parseRequest(req);
			
			//优秀组织查询
	        Map<String, Object> rtnMap = this.service.selyxzz(param);
	        try {
				view.addObject("total", rtnMap.get("total"));
				view.addObject("rows", rtnMap.get("rows"));
				view.addObject("page", rtnMap.get("page"));
				view.addObject("pageSize", rtnMap.get("pageSize"));
			} catch (Exception e) {
				view.addObject("total", 0);
				view.addObject("rows", null);
				view.addObject("page", 1);
				view.addObject("pageSize", 0);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 优秀组织详情
	 * @return 优秀组织详情
	 */
	@RequestMapping("/youxiuzuzhiinfo")
	public ModelAndView youxiuzuzhiinfo(String zyfczzid){
		ModelAndView view = new ModelAndView( "home/agdzyfw/youxiuzuzhiinfo" );
		try {
			//详情查询
			WhZyfcZuzhi whZyfcZuzhi = this.service.selyxzz(zyfczzid);
			if (whZyfcZuzhi != null) {
				view.addObject("zuzhi", whZyfcZuzhi);
			}
			zyfczzid = whZyfcZuzhi.getZyfczzid();
			//优秀组织推荐
			List<WhZyfcZuzhi> zztj = this.service.selNewZztj(zyfczzid);
			if (zztj != null && !zztj.isEmpty()) {
				view.addObject("zztj", zztj);
			}
			//查询资源图片 音频 视频
//			String enttype = "2016101400000055";
//			String reftype = "2017010400000001";
//			String refid = zyfczzid;
//			List<WhEntsource> pic = this.service.findpic(refid,enttype,reftype);
//			if (pic != null && pic.size() >0) {
//				view.addObject("pic", pic);
//			}
//			enttype = "2016101400000056";
//			List<WhEntsource> vido = this.service.findvido(refid,enttype,reftype);
//			if (vido != null && vido.size() >0) {
//				view.addObject("video", vido);
//			}
//			enttype = "2016101400000057";
//			List<WhEntsource> musci = this.service.findmusci(refid,enttype,reftype);
//			if (musci != null && musci.size() >0) {
//				view.addObject("audio", musci);
//			}
			//查询资源图片 音频 视频
			//图片
			List<WhgComResource> pic = this.commservice.findRescource("1","8", zyfczzid);
			view.addObject("pic",pic );
			//视频
			List<WhgComResource> vido = this.commservice.findRescource("2","8", zyfczzid);
			view.addObject("video",vido );
			//音频
			List<WhgComResource> musci = this.commservice.findRescource("3","8", zyfczzid);
			view.addObject("audio",musci );

			//文档
			List<WhgComResource> doc = this.commservice.findRescource("4","8", zyfczzid);
			view.addObject("doc",doc );
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 风采展示
	 * @return 风采展示
	 */
	@RequestMapping("/fengcai")
	public ModelAndView fengcai(){
		ModelAndView view = new ModelAndView( "home/agdzyfw/fengcai" );
		try {
			//do...
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 他山之石
	 * @return 他山之石
	 */
	@RequestMapping("/tashan")
	public ModelAndView tashan(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView view = new ModelAndView( "home/agdzyfw/tashan" );
		try {
			//资讯栏目
			String realtype = "2016112200000010";
			if(req.getParameter("type") != null){
				realtype = req.getParameter("type");
			}
			
			//获取请求参数
			Map<String, Object> param = ReqParamsUtil.parseRequest(req);
			
			//分页查询
	        Map<String, Object> rtnMap = this.gwgkService.paggingColinfo(param, realtype);
	        try {
				view.addObject("total", rtnMap.get("total"));
				view.addObject("rows", rtnMap.get("rows"));
				view.addObject("page", rtnMap.get("page"));
				view.addObject("pageSize", rtnMap.get("pageSize"));
			} catch (Exception e) {
				view.addObject("total", 0);
				view.addObject("rows", null);
				view.addObject("page", 1);
				view.addObject("pageSize", 0);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 他山之石详情
	 * @return 他山之石详情
	 */
	@RequestMapping("/tashaninfo")
	public ModelAndView tashaninfo(String id, String type){
		ModelAndView view = new ModelAndView("home/agdzyfw/tashaninfo" );
		try {
			String realtype = "2016112200000010";
			if(type != null){
				realtype = type;
			}
			
			//当前资讯
			view.addObject("wh_zx_colinfo", this.gwgkService.queryOneColinfo(id, realtype));
			
			//next
			view.addObject("wh_zx_colinfo_next", this.gwgkService.queryNextColinfo(id, realtype));
			
			//相关推荐
			view.addObject("wh_zx_colinfo_ref", this.gwgkService.queryREFColinfo(id, realtype));

//			//资源图片
//			String enttype="2016101400000055";
//			String reftype = "2016102800000001";
//			List<WhEntsource> whe= this.gwgkService.selecent(id,enttype,reftype);
//			view.addObject("loadwhe", whe);
//
//			//资源视频
//			String types="2016101400000056";
//			List<WhEntsource> ent= this.gwgkService.selecsource(id,types,reftype);
//			view.addObject("loadent", ent);
//
//			//资源音频
//			String clazz="2016101400000057";
//			List<WhEntsource> whent= this.gwgkService.selecwhent(id,clazz,reftype);
//			view.addObject("loadclazz", whent);

			//图片
			List<WhgComResource> pic = this.commservice.findRescource("1","11", id);
			view.addObject("loadwhe",pic );
			//视频
			List<WhgComResource> vido = this.commservice.findRescource("2","11", id);
			view.addObject("loadent",vido );
			//音频
			List<WhgComResource> musci = this.commservice.findRescource("3","11", id);
			view.addObject("loadclazz",musci );
			
			//上传
			List<WhZxUpload> whup =  this.gwgkService.selecup(id);
			view.addObject("loadlist", whup);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 理论探索
	 * @return 理论探索
	 */
	@RequestMapping("/lilun")
	public ModelAndView lilun(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView view = new ModelAndView( "home/agdzyfw/lilun" );
		try {
			//资讯栏目
			String realtype = "2016112200000011";
			if(req.getParameter("type") != null){
				realtype = req.getParameter("type");
			}
			
			//获取请求参数
			Map<String, Object> param = ReqParamsUtil.parseRequest(req);
			
			//分页查询
	        Map<String, Object> rtnMap = this.gwgkService.paggingColinfo(param, realtype);
	        try {
				view.addObject("total", rtnMap.get("total"));
				view.addObject("rows", rtnMap.get("rows"));
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
	 * 理论探索详情
	 * @return 理论探索详情
	 */
	@RequestMapping("/liluninfo")
	public ModelAndView liluninfo(String id, String type){
		ModelAndView view = new ModelAndView( "home/agdzyfw/liluninfo" );
		try {
			String realtype = "2016112200000011";
			if(type != null){
				realtype = type;
			}
			
			//当前资讯
			view.addObject("wh_zx_colinfo", this.gwgkService.queryOneColinfo(id, realtype));
			
			//next
			view.addObject("wh_zx_colinfo_next", this.gwgkService.queryNextColinfo(id, realtype));
			
			//相关推荐
			view.addObject("wh_zx_colinfo_ref", this.gwgkService.queryREFColinfo(id, realtype));

			//资源图片
			String enttype="2016101400000055";
			String reftype = "2016102800000001";
			List<WhEntsource> whe= this.gwgkService.selecent(id,enttype,reftype);
			view.addObject("loadwhe", whe);
			
			//资源视频
			String types="2016101400000056";
			List<WhEntsource> ent= this.gwgkService.selecsource(id,types,reftype);
			view.addObject("loadent", ent);
			
			//资源音频
			String clazz="2016101400000057";
			List<WhEntsource> whent= this.gwgkService.selecwhent(id,clazz,reftype);
			view.addObject("loadclazz", whent);
			
			//上传
			List<WhZxUpload> whup =  this.gwgkService.selecup(id);
			view.addObject("loadlist", whup);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 政策法规
	 * @return 政策法规
	 */
	@RequestMapping("/zhengce")
	public ModelAndView zhengce(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView view = new ModelAndView( "home/agdzyfw/zhengce" );
		try {
			//资讯栏目
			String realtype = "2016112200000012";
			if(req.getParameter("type") != null){
				realtype = req.getParameter("type");
			}
			
			//获取请求参数
			Map<String, Object> param = ReqParamsUtil.parseRequest(req);
			
			//分页查询
	        Map<String, Object> rtnMap = this.gwgkService.paggingColinfo(param, realtype);
	        try {
				view.addObject("total", rtnMap.get("total"));
				view.addObject("rows", rtnMap.get("rows"));
				view.addObject("page", rtnMap.get("page"));
				view.addObject("pageSize", rtnMap.get("pageSize"));
			} catch (Exception e) {
				view.addObject("total", 0);
				view.addObject("rows", null);
				view.addObject("page", 1);
				view.addObject("pageSize", 0);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 政策法规详情
	 * @return 政策法规详情
	 */
	@RequestMapping("/zhengceinfo")
	public ModelAndView zhengceinfo(String id, String type){
		ModelAndView view = new ModelAndView( "home/agdzyfw/zhengceinfo" );
		try {
			String realtype = "2016112200000012";
			if(type != null){
				realtype = type;
			}
			
			//当前资讯
			view.addObject("wh_zx_colinfo", this.gwgkService.queryOneColinfo(id, realtype));
			
			//next
			view.addObject("wh_zx_colinfo_next", this.gwgkService.queryNextColinfo(id, realtype));
			
			//相关推荐
			view.addObject("wh_zx_colinfo_ref", this.gwgkService.queryREFColinfo(id, realtype));

//			//资源图片
//			String enttype="2016101400000055";
//			String reftype = "2016102800000001";
//			List<WhEntsource> whe= this.gwgkService.selecent(id,enttype,reftype);
//			view.addObject("loadwhe", whe);
//
//			//资源视频
//			String types="2016101400000056";
//			List<WhEntsource> ent= this.gwgkService.selecsource(id,types,reftype);
//			view.addObject("loadent", ent);
//
//			//资源音频
//			String clazz="2016101400000057";
//			List<WhEntsource> whent= this.gwgkService.selecwhent(id,clazz,reftype);
//			view.addObject("loadclazz", whent);

			//图片
			List<WhgComResource> pic = this.commservice.findRescource("1","11", id);
			view.addObject("loadwhe",pic );
			//视频
			List<WhgComResource> vido = this.commservice.findRescource("2","11", id);
			view.addObject("loadent",vido );
			//音频
			List<WhgComResource> musci = this.commservice.findRescource("3","11", id);
			view.addObject("loadclazz",musci );
			
			//上传
			List<WhZxUpload> whup =  this.gwgkService.selecup(id);
			view.addObject("loadlist", whup);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 先进个人
	 * @return 先进个人
	 */
	@RequestMapping("/geren")
	public ModelAndView geren(HttpServletRequest req){
		ModelAndView mav = new ModelAndView("home/agdzyfw/geren");
		//获取请求参数
		Map<String, Object> param = ReqParamsUtil.parseRequest(req);
		//查询先进个人
        try {
			Map<String, Object> rtnMap = this.service.paraGr(param);
			try {
				mav.addObject("params", param);
				mav.addObject("total", rtnMap.get("total"));
				mav.addObject("rows", rtnMap.get("rows"));
				mav.addObject("page", rtnMap.get("page"));
				mav.addObject("pageSize", rtnMap.get("pageSize"));
			} catch (Exception e) {
				mav.addObject("total", 0);
				mav.addObject("rows", null);
				mav.addObject("page", 1);
				mav.addObject("pageSize", 0);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return mav;
	}
	
	/**
	 * 先进个人详情
	 * @return 先进个人详情
	 */
	@RequestMapping("/gereninfo")
	public ModelAndView gereninfo(String zyfcgrid){
		ModelAndView mav = new ModelAndView("home/agdzyfw/gereninfo");
		String zyfcxmid = zyfcgrid;
		try {
			//查询先进个人详情
			WhZyfcGeren whGr = this.service.selectGeren(zyfcgrid);
			if(whGr != null){
				mav.addObject("geren",whGr);
			}
			//查询资源图片 音频 视频
//			String enttype = "2016101400000055";
//			String reftype = "2016112900000002";
//			String refid = zyfcxmid;
//			List<WhEntsource> pic = this.service.findpic(refid,enttype,reftype);
//			if (pic != null && pic.size() >0) {
//				mav.addObject("pic", pic);
//			}
//			enttype = "2016101400000056";
//			List<WhEntsource> vido = this.service.findvido(refid,enttype,reftype);
//			if (vido != null && vido.size() >0) {
//				mav.addObject("vido", vido);
//			}
//			enttype = "2016101400000057";
//			List<WhEntsource> musci = this.service.findmusci(refid,enttype,reftype);
//			if (musci != null && musci.size() >0) {
//				mav.addObject("musci", musci);
//			}
			List<WhgComResource> pic = this.commservice.findRescource("1","10", zyfcxmid);
			mav.addObject("pic",pic );
			//视频
			List<WhgComResource> vido = this.commservice.findRescource("2","10", zyfcxmid);
			mav.addObject("vido",vido );
			//音频
			List<WhgComResource> musci = this.commservice.findRescource("3","10", zyfcxmid);
			mav.addObject("musci",musci );
			//文档
			List<WhgComResource> doc = this.commservice.findRescource("4","10", zyfcxmid);
			mav.addObject("doc",doc );
			//先进个人推荐
			List<WhZyfcGeren> whGrList = this.service.gerenTJ(zyfcgrid);
			mav.addObject("whGrList", whGrList);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return mav;
	}
	

	/**
	 * 将关键字ID转换为关键字
	 * @return
	 */
	@RequestMapping("/getKey")
	public Object getKeys(String zypxkeys){
		
		return this.service.getKeys(zypxkeys);
	}
	
}
