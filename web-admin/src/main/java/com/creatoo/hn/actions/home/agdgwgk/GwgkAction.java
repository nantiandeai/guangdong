package com.creatoo.hn.actions.home.agdgwgk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.creatoo.hn.model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.home.agdgwgk.GwgkService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.WhConstance;

/**
 * 馆务公开
 * @author wangxl
 * @version 2016.11.16
 */
@RestController
@RequestMapping("/agdgwgk")
public class GwgkAction {
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
	
	/**
	 * 首页-省馆介绍
	 * @return 首页-省馆介绍
	 */
	@RequestMapping("/index")
	@SuppressWarnings("unchecked")
	public ModelAndView index(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView view = new ModelAndView( "home/agdgwgk/index" );
		try {
			//获取请求参数
			Map<String, Object> param = ReqParamsUtil.parseRequest(req);
			
			//查询省馆介绍
			Map<String, Object> rtnMap = this.gwgkService.paggingColinfo(param, "2016111900000006");
			List<WhZxColinfo> list = (List<WhZxColinfo>)rtnMap.get("rows");
			if(list != null && list.size() > 0){
				WhZxColinfo wh_zx_colinfo = list.get(0);
				view.addObject("wh_zx_colinfo", wh_zx_colinfo);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 组织机构
	 * @return 组织机构
	 */
	@RequestMapping("/jigou")
	public ModelAndView jigou(){
		ModelAndView view = new ModelAndView( "home/agdgwgk/jigou" );
		try {
			view.addObject("wh_zx_colinfo_p_list", this.gwgkService.queryAllColinfo("2016111900000010"));//负责人
			view.addObject("wh_zx_colinfo_org_list", this.gwgkService.queryAllColinfo("2016111900000011"));//部门
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 政策法规
	 * @return 政策法规
	 */
	@RequestMapping("/fagui")
	public ModelAndView fagui(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView view = new ModelAndView( "home/agdgwgk/fagui" );
		try {
			//资讯栏目
			String realtype = "2016111900000007";
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
	 * 政策法规详情
	 * @return 政策法规详情
	 */
	@RequestMapping("/faguiinfo")
	public ModelAndView faguiinfo(String id, String type){
		ModelAndView view = new ModelAndView( "home/agdgwgk/faguiinfo" );
		try {
			String realtype = "2016111900000007";
			if(type != null){
				realtype = type;
			}
			//当前资讯
			view.addObject("wh_zx_colinfo", this.gwgkService.queryOneColinfo(id, realtype));
			
			//next
			view.addObject("wh_zx_colinfo_next", this.gwgkService.queryNextColinfo(id, realtype));
			
			//相关推荐
			view.addObject("wh_zx_colinfo_ref", this.gwgkService.queryREFColinfo(id, realtype));
			
			//上传
			List<WhZxUpload> whup =  this.gwgkService.selecup(id);
			view.addObject("loadlist", whup);
			//资源图片
//			String enttype="2016101400000055";
//			String reftype = "2016102800000001";
//			List<WhEntsource> whe= this.gwgkService.selecent(id,enttype,reftype);
//			view.addObject("loadwhe", whe);
//			//资源视频
//			String types="2016101400000056";
//			List<WhEntsource> ent= this.gwgkService.selecsource(id,types,reftype);
//			view.addObject("loadent", ent);
//			//资源音频
//			String clazz="2016101400000057";
//			List<WhEntsource> whent= this.gwgkService.selecwhent(id,clazz,reftype);
//			view.addObject("loadclazz", whent);
			//查询资源图片 音频 视频
			//图片
			List<WhgComResource> pic = this.commservice.findRescource("1","11", id);
			view.addObject("loadwhe",pic );
			//视频
			List<WhgComResource> vido = this.commservice.findRescource("2","11", id);
			view.addObject("loadent",vido );
			//音频
			List<WhgComResource> musci = this.commservice.findRescource("3","11", id);
			view.addObject("loadclazz",musci );
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 业务指南
	 * @return 业务指南jigou.jsp
	 */
	@RequestMapping("/zhinan")
	public ModelAndView zhinan(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView view = new ModelAndView( "home/agdgwgk/zhinan" );
		try {
			//获取请求参数
			Map<String, Object> param = ReqParamsUtil.parseRequest(req);
			
			//分页查询
	        Map<String, Object> rtnMap = this.gwgkService.paggingColinfo(param, "2016111900000008");
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
	 * 业务指南详细
	 * @return 政策法规
	 */
	@RequestMapping("/zhinaninfo")
	public ModelAndView zhinaninfo(String id, String type){
		ModelAndView view = new ModelAndView( "home/agdgwgk/zhinaninfo" );
		try {
			String realtype = "2016111900000008";
			if(type != null){
				realtype = type;
			}
			
			//当前资讯
			view.addObject("wh_zx_colinfo", this.gwgkService.queryOneColinfo(id, realtype));
			
			//next
			view.addObject("wh_zx_colinfo_next", this.gwgkService.queryNextColinfo(id, realtype));
			
			//相关推荐
			view.addObject("wh_zx_colinfo_ref", this.gwgkService.queryREFColinfo(id, realtype));
			//上传
			List<WhZxUpload> whup =  this.gwgkService.selecup(id);
			view.addObject("loadlist", whup);
			//资源图片
//			String enttype="2016101400000055";
//			String reftype = "2016102800000001";
//			List<WhEntsource> whe= this.gwgkService.selecent(id,enttype,reftype);
//			view.addObject("loadwhe", whe);
//			//资源视频
//			String types="2016101400000056";
//			List<WhEntsource> ent= this.gwgkService.selecsource(id,types,reftype);
//			view.addObject("loadent", ent);
//			//资源音频
//			String clazz="2016101400000057";
//			List<WhEntsource> whent= this.gwgkService.selecwhent(id,clazz,reftype);
//			view.addObject("loadclazz", whent);

			//查询资源图片 音频 视频
			//图片
			List<WhgComResource> pic = this.commservice.findRescource("1","11", id);
			view.addObject("loadwhe",pic );
			//视频
			List<WhgComResource> vido = this.commservice.findRescource("2","11", id);
			view.addObject("loadent",vido );
			//音频
			List<WhgComResource> musci = this.commservice.findRescource("3","11", id);
			view.addObject("loadclazz",musci );
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 馆办团队
	 * @return 馆办团队
	 */
	@RequestMapping("/tuandui")
	public ModelAndView tuandui(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView view = new ModelAndView( "home/agdgwgk/tuandui" );
		try {
			//获取请求参数
			Map<String, Object> param = ReqParamsUtil.parseRequest(req);
			
			//分页查询艺术团队
	        Map<String, Object> rtnMap = this.gwgkService.paggingTroupe(param);
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
	 * 团队详情
	 * @param id 团队标识
	 * @return
	 */
	@RequestMapping("/tuanduiinfo")
	public ModelAndView tuanduiinfo(String id){
		ModelAndView view = new ModelAndView( "home/agdgwgk/tuanduiinfo" );
		try {
			//团队详情
			view.addObject("troupe", this.gwgkService.queryOneTroupe(id));
			
			//团队成员
			view.addObject("troupe_user_list", this.gwgkService.queryAllTroupeUsers(id));

			//图片
			List<WhgComResource> pic = this.commservice.findRescource("1","12", id);
			view.addObject("troupe_pic_list",pic );
			//视频
			List<WhgComResource> vido = this.commservice.findRescource("2","12", id);
			view.addObject("troupe_video_list",vido );

			//团队图片
//			view.addObject("troupe_pic_list", this.gwgkService.queryAllTroupeSources(id, "2016101400000055"));
			
			//团队视频
//			view.addObject("troupe_video_list", this.gwgkService.queryAllTroupeSources(id, "2016101400000056"));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 意见反馈
	 * @return 意见反馈
	 */
	@RequestMapping("/fankui")
	public ModelAndView fankui(){
		ModelAndView view = new ModelAndView( "home/agdgwgk/fankui" );
		try {
			//do...
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 保存意见反馈
	 * 
	 */
	@RequestMapping("/addfeed")
	public Object add(WhFeedback whf, HttpServletRequest request){
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			//得到页面的值转小写
			String yanzhen = request.getParameter("feedyanzhen").toLowerCase();
			//得到验证的值
			String rand = (String) request.getSession().getAttribute("rand");
			//判断页面值和验证匹配
			if (yanzhen.equals(rand)) {
				whf.setFeedid(this.commservice.getKey("WhFeedback"));
				this.gwgkService.addinfo(whf);
				res.put("success", true);
			}else {
				throw new Exception("验证码不正确");
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;
	}
	/**
	 * 得到用户登录信息
	 */
	@RequestMapping("/upfeed")
	public Object upReverse(HttpSession session){
		  Object obj = null;
		try {
			//得到当前登录的信息
			WhUser sessUser = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			
			String id = sessUser.getId();
			
			WhUser whu=	this.gwgkService.uprevrse(id);
			obj = whu;
			
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	
		return obj; 
	}
}
