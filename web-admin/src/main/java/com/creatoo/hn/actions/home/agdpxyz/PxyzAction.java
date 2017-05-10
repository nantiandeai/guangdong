package com.creatoo.hn.actions.home.agdpxyz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EnumType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.creatoo.hn.ext.emun.EnumTypeClazz;
import com.creatoo.hn.model.*;
import com.github.pagehelper.PageInfo;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.home.agdgwgk.GwgkService;
import com.creatoo.hn.services.home.agdpxyz.PxyzService;
import com.creatoo.hn.utils.ReqParamsUtil;

/**
 * 培训驿站
 * @author wangxl
 * @version 2016.11.16
 */
@RestController
@RequestMapping("/agdpxyz")
public class PxyzAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private PxyzService service;
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
	 * 首页
	 * @return 首页
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest req){
		/*ModelAndView view = new ModelAndView( "home/agdpxyz/index" );
		try {
			//热门培训
			List<WhgTra> whTrain = this.service.selTrainList();
			if (whTrain != null && !whTrain.isEmpty()) {
				view.addObject("whTrain", whTrain);
			}
			 //艺术分类
	    	List<WhgYwiType> ystypes = this.commservice.findYwiType(EnumTypeClazz.TYPE_ART.getValue());
	    	if (ystypes != null && !ystypes.isEmpty()) {
				view.addObject("ys", ystypes);
			}
	    	
	    	//在线点播
	    	List<WhDrsc> zxdb = this.service.findVodList();
	    	view.addObject("zxdb", zxdb);
	    	
	    	//培训师资
	    	List<WhUserTeacher> teacher = this.service.selTeacher();
	    	if (teacher != null) {
				view.addObject("teacher", teacher);
			}
	    	
	    	//培训资讯
	    	String type = "2016111900000021";
	    	view.addObject("pxzx", this.gwgkService.queryAllColinfo(type));
	    	
	    	//培训公告
	    	String typ = "2016111900000020";
	    	view.addObject("pxgg", this.gwgkService.queryAllColinfo(typ));
	    	
	    	//获取资讯配置
			List<WhCfgList> ympz=this.service.getzxpz();
			view.addObject("ympz",ympz);
			
			//广告图
			List<WhgYwiLbt> adv = this.service.findAdv();
			if (adv.size() > 0) {
				view.addObject("adv",adv.get(0));
			}
			
			//查询老师的点赞数
			
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;*/

		ModelAndView view = new ModelAndView( "home/agdpxyz/trainlist" );
		try {
			//获取请求参数
			Map<String, Object> param = ReqParamsUtil.parseRequest(req);

			//分页查询
			Map<String, Object> rtnMap = this.service.paggingzxbm(param);

			//艺术分类
			List<WhgYwiType> ystypes = this.commservice.findYwiType(EnumTypeClazz.TYPE_ART.getValue());
			//区域
			List<WhgYwiType> qrtypes = this.commservice.findYwiType(EnumTypeClazz.TYPE_AREA.getValue());
			//培训分类
			List<WhgYwiType> tratype = this.commservice.findYwiType(EnumTypeClazz.TYPE_TRAIN.getValue());

			view.addObject("params", param);
			try {
				view.addObject("tratype", tratype);
				view.addObject("ystypes", ystypes);
				view.addObject("qrtypes", qrtypes);
				view.addObject("total", rtnMap.get("total"));
				view.addObject("rows", rtnMap.get("rows"));
				view.addObject("page", rtnMap.get("page"));
				view.addObject("pageSize", rtnMap.get("pageSize"));
			} catch (Exception e) {
				view.addObject("tratype", null);
				view.addObject("ystypes", null);
				view.addObject("qrtypes", null);
				view.addObject("total", 0);
				view.addObject("rows", null);
				view.addObject("page", 1);
			}
			//do...
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 培训公告
	 * @return 培训公告
	 */
	@RequestMapping("/notice")
	public ModelAndView notice(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView view = new ModelAndView( "home/agdpxyz/notice" );
		try {
			//资讯栏目
			String realtype = "2016111900000020";
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
	 * 培训公告详情
	 * @return 培训公告详情
	 */
	@RequestMapping("/noticeinfo")
	public ModelAndView noticeinfo(String id, String type){
		ModelAndView view = new ModelAndView( "home/agdpxyz/noticeinfo" );
		try {
			String realtype = "2016111900000020";
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
//			//资源视频
//			String types="2016101400000056";
//			List<WhEntsource> ent= this.gwgkService.selecsource(id,types,reftype);
//			view.addObject("loadent", ent);
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
	 * 培训资讯
	 * @return 培训资讯
	 */
	@RequestMapping("/news")
	public ModelAndView news(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView view = new ModelAndView( "home/agdpxyz/news" );
		try {
			//资讯栏目
			String realtype = "2016111900000021";
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
	 * 培训资讯详情
	 * @return 培训资讯详情
	 */
	@RequestMapping("/newsinfo")
	public ModelAndView newsinfo(String id, String type){
		ModelAndView view = new ModelAndView( "home/agdpxyz/newsinfo" );
		try {
			String realtype = "2016111900000021";
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
//			//资源图片
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
	 * 在线报名-培训列表
	 * @return 在线报名-培训列表
	 */
	@RequestMapping("/trainlist")
	public ModelAndView trainlist(HttpServletRequest req){
		ModelAndView view = new ModelAndView( "home/agdpxyz/trainlist" );
		try {
			//获取请求参数
			Map<String, Object> param = ReqParamsUtil.parseRequest(req);
			
			//分页查询
	        Map<String, Object> rtnMap = this.service.paggingzxbm(param);
	    	
	        //艺术分类
	    	List<WhgYwiType> ystypes = this.commservice.findYwiType(EnumTypeClazz.TYPE_ART.getValue());
	    	//区域
	    	List<WhgYwiType> qrtypes = this.commservice.findYwiType(EnumTypeClazz.TYPE_AREA.getValue());
	        //培训分类
			List<WhgYwiType> tratype = this.commservice.findYwiType(EnumTypeClazz.TYPE_TRAIN.getValue());
	       
    		view.addObject("params", param);
	        try {
				view.addObject("tratype", tratype);
	        	view.addObject("ystypes", ystypes);
	        	view.addObject("qrtypes", qrtypes);
				view.addObject("total", rtnMap.get("total"));
				view.addObject("rows", rtnMap.get("rows"));
				view.addObject("page", rtnMap.get("page"));
				view.addObject("pageSize", rtnMap.get("pageSize"));
			} catch (Exception e) {
				view.addObject("tratype", null);
				view.addObject("ystypes", null);
	        	view.addObject("qrtypes", null);
				view.addObject("total", 0);
				view.addObject("rows", null);
				view.addObject("page", 1);
			}
			//do...
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 报名(培训)详情
	 * @return 报名(培训)详情
	 */
	@RequestMapping("/traininfo")
	public ModelAndView traininfo(String traid){
		ModelAndView view = new ModelAndView( "home/agdpxyz/traininfo" );
		try {
			//在线报名详情查询
			WhgTra train = this.service.selTrain(traid);
			if (train != null) {
				view.addObject("train", train);
				if(!"".equals(train.getAge()) && train.getAge() != null){
					String[] age = train.getAge().split(",");
					view.addObject("age1",age[0]);
					view.addObject("age2",age[1]);
				}
			}


			//已报名人数
    		int count = this.service.selCount(traid);
    		if (count != 0) {
				view.addObject("count", count);
			}
			//培训老师
			List teacher = this.service.findTeacher(traid);
			if (teacher.size() != 0 && teacher.get(0) != "") {
				view.addObject("teacher", teacher);
			}

			//traid = train.getTraid();
			//当前时间
			Date now = new Date();
			view.addObject("now", now);
			//在线报名详情推荐课程
			List<WhgTra> kecheng = this.service.selNewTrain(traid);
			if (kecheng != null && !kecheng.isEmpty()) {
				view.addObject("kecheng", kecheng);
			}
			//场馆类型标记
			view.addObject("enumtypetrain", EnumTypeClazz.TYPE_TRAIN.getValue());
			
			//资源图片
			String enttype="1";
			String reftype = "1";
			List<WhgComResource> pic= this.commservice.findRescource(enttype,reftype,traid);
			view.addObject("pic", pic);
			
			//资源视频
			String videoCourse="2";
			List<WhgComResource> video= this.commservice.findRescource(videoCourse,reftype,traid);
			view.addObject("video", video);
			
			//资源音频
			String clazz="3";
			List<WhgComResource> audio= this.commservice.findRescource(clazz,reftype,traid);
			view.addObject("audio", audio);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}

	/**
	 * 查询课程表
	 * @param req
	 * @return
     */
	@RequestMapping("/course")
	public Object findCourse(HttpServletRequest req){
		//获取请求参数
		Map<String, Object> param = ReqParamsUtil.parseRequest(req);
		Map<String, Object> course = new HashMap<>();
		try {
			course = this.service.selectCourse(param);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return course;
	}
	
	/**
	 * 在线点播列表
	 * @return 在线点播
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/vod")
	public ModelAndView vod(HttpServletRequest req){
		ModelAndView view = new ModelAndView( "home/agdpxyz/vod" );
		try {
			//获取请求参数
			Map<String, Object> param = ReqParamsUtil.parseRequest(req);
			
			
	        //艺术分类
	    	List<WhgYwiType> ystypes = null;
	    	ystypes = this.commservice.findYwiType(EnumTypeClazz.TYPE_ART.getValue());
	    	
	    	try {
				view.addObject("ystypes", ystypes);
			} catch (Exception e) {
				view.addObject("ystypes", null);
			}
	    	 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 分页查询在线点播的信息
	 */
	@RequestMapping("/vodlist")
	public Object Srchlist(HttpServletRequest req,HttpServletResponse res){
		//获取请求参数
		Map<String, Object> param = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
        	//分页查询
	        rtnMap = this.service.findvod(param);
			
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
        return rtnMap;
		
	}
	
	/**
	 * 在线点播详情
	 * @return 在线点播详情
	 */
	@RequestMapping("/vodinfo")
	public ModelAndView vodinfo(String drscid){
		ModelAndView view = new ModelAndView( "home/agdpxyz/vodinfo" );
		try {
			//在线点播
			WhDrsc whDrsc = this.service.findVodInfo(drscid);
			if (whDrsc != null) {
				view.addObject("whDrsc", whDrsc);
			}
			drscid = whDrsc.getDrscid();
			//推荐课程
			List<WhDrsc> kecheng = this.service.selNewVod(drscid);
			if (kecheng != null) {
				view.addObject("kecheng", kecheng);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 查询老师的培训
	 * @return
	 */
	@RequestMapping("/selTrainPic")
	public Object findTrainPic(String teacherid){
		List<WhgTra> train = this.service.selTrainPic(teacherid);
		return train;
	}
	
}
