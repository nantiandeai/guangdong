package com.creatoo.hn.actions.home.agdfyzg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.creatoo.hn.ext.emun.EnumLBTClazz;
import com.creatoo.hn.ext.emun.EnumTypeClazz;
import com.creatoo.hn.model.*;
import com.creatoo.hn.services.home.agdindex.IndexPageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.home.agdfyzg.FyzgService;
import com.creatoo.hn.services.home.agdgwgk.GwgkService;
import com.creatoo.hn.utils.ReqParamsUtil;

/**
 * 非遗展馆
 * @author wangxl
 * @version 2016.11.16
 */
@RestController
@RequestMapping("/agdfyzg")
public class FyzgAction {
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
	 * 非遗服务类
	 */
	@Autowired
	public FyzgService feiyiService;

	/**
	 * 广告图DAO
	 */
	@Autowired
	public IndexPageService indexPageService;

	/**
	 * 首页
	 * @return 首页
	 */
	@RequestMapping("/index")
	public ModelAndView index(String type){
		ModelAndView view = new ModelAndView( "home/agdfyzg/index" );
		try {
			//gg
			List<WhZxColinfo> lsitgg = this.feiyiService.gonggaoColinfo("2016112200000004");
			view.addObject("listgg", lsitgg);
			//new
			List<WhZxColinfo> lsitnews = this.feiyiService.gonggaoColinfo("2016112200000005");
			view.addObject("listnews", lsitnews);

			//广告图片
			view.addObject("advList", indexPageService.findLBT(EnumLBTClazz.LBT_ADV_PC_FY.getValue(), 5));
			
			//类别分类
//			List<WhTyp> ttype = this.commservice.findWhtyp("18");
//			view.addObject("ttype", ttype);

			List<WhgYwiType> ttype = this.commservice.findYwiType(EnumTypeClazz.TYPE_GENRE.getValue());
			view.addObject("ttype", ttype);
			
			//首页配置
			List<WhZxColinfo> listzx = this.feiyiService.getzxpz();
			if(listzx != null && listzx.size() >0){
				view.addObject("listzx", listzx);
			}else{
				view.addObject("listzx", null);
			}
			
			List<WhgYwiLbt> listAdv = indexPageService.findLBTDesc(EnumLBTClazz.LBT_LB_PC_FY.getValue(),9);
			view.addObject("listAdv",listAdv);
		/*	//根据类型 非遗首页 取8 名录项目
			List<WhDecproject> listdp = this.feiyiService.getminglu(type);
			view.addObject("listdp", listdp);*/
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}

	/**
	 * //根据类型 非遗首页 取8 名录项目
	 * @param type
	 * @return
     */
	@RequestMapping("/getminglu")
	public List<WhDecproject> getminglu(String type){
		try {
			return this.feiyiService.getminglu(type);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 公告
	 * @return 公告
	 */
	@RequestMapping("/notice")
	public ModelAndView notice(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView view = new ModelAndView( "home/agdfyzg/notice" );
		try {
			//资讯栏目
			String realtype = "2016112200000004";
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
	 * 公告详情
	 * @return 公告详情
	 */
	@RequestMapping("/noticeinfo")
	public ModelAndView noticeinfo(String id, String type){
		ModelAndView view = new ModelAndView( "home/agdfyzg/noticeinfo" );
		try {
			String realtype = "2016112200000004";
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
	 * 新闻资讯
	 * @return 新闻资讯
	 */
	@RequestMapping("/news")
	public ModelAndView news(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView view = new ModelAndView( "home/agdfyzg/news" );
		try {
			//资讯栏目
			String realtype = "2016112200000005";
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
	 * 新闻资讯详情
	 * @return 新闻资讯详情
	 */
	@RequestMapping("/newsinfo")
	public ModelAndView newsinfo(String id, String type){
		ModelAndView view = new ModelAndView( "home/agdfyzg/newsinfo" );
		try {
			String realtype = "2016112200000005";
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
	 * 名录项目
	 * @return 名录项目
	 */
	@RequestMapping("/minglu")
	public ModelAndView minglu(WebRequest request){
		ModelAndView view = new ModelAndView( "home/agdfyzg/minglu" );
		try {
			//级别分类
	    	List<WhgYwiType> leveltype = this.commservice.findYwiType(EnumTypeClazz.TYPE_LEVEL.getValue());
	    	view.addObject("leveltype", leveltype);
	    	//类别分类
	    	List<WhgYwiType> ttype = this.commservice.findYwiType(EnumTypeClazz.TYPE_GENRE.getValue());
	    	view.addObject("ttype", ttype);
	    	//区域分类
	    	List<WhgYwiType> qrtype = this.commservice.findYwiType(EnumTypeClazz.TYPE_AREA.getValue());
	    	view.addObject("qrtype", qrtype);
	    	//批次分类
	    	List<WhgYwiType> pici = this.commservice.findYwiType(EnumTypeClazz.TYPE_BATCH.getValue());
	    	view.addObject("pici", pici);

			Map<String, Object> reqMap = ReqParamsUtil.parseRequest(request);
			view.addObject("reqMap", reqMap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 名录列表数据加载
	 * @param request
	 * @return
	 */
	@RequestMapping("/dataloadminglu")
	public Object dataloadminglu(WebRequest request){
		try {
			return this.feiyiService.dataloadminglu(request);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return null;
		}
	}
	
	
	/**
	 * 名录项目详情
	 * @return 名录项目详情
	 */
	@RequestMapping("/mingluinfo")
	public ModelAndView mingluinfo(String mlproid,ModelMap map){
		ModelAndView view = new ModelAndView( "home/agdfyzg/mingluinfo" );
		try {
			//名录 详细信息
			WhDecproject proDetail = this.feiyiService.decproDetail(mlproid);
			map.addAttribute("proDetail",proDetail);
			//名录相关传承人
			Object obj = this.feiyiService.protosuccessor(mlproid);
			map.addAttribute("suor",obj );
			//名录相关资源
			//图片
			 List<WhgComResource> tup = this.commservice.findRescource("1","5", mlproid);
			 map.addAttribute("tup",tup );
			 //视频
			 List<WhgComResource> spin = this.commservice.findRescource("2","5", mlproid);
			 map.addAttribute("spin",spin );
			 //音频
			 List<WhgComResource> ypin = this.commservice.findRescource("3","5", mlproid);
			 map.addAttribute("ypin",ypin );
			//文件
			List<WhgComResource> doc = this.commservice.findRescource("4","5", mlproid);
			map.addAttribute("doc",doc );
			 
			List<WhKey> keylist = new ArrayList<>();
			//处理活动标签
			if(proDetail.getMlprokey() != null && !"".equals(proDetail.getMlprokey())){
				String tags[] = proDetail.getMlprokey().split(",");
			
				for (int i = 0; i < tags.length; i++) {
					WhKey whkey = this.feiyiService.keyList(tags[i]);
					if(whkey != null && !"".equals(whkey)){
						keylist.add(whkey);
					}else{
						WhKey whkey2 = new WhKey();
						whkey2.setName(tags[i]);
						keylist.add(whkey2);
					}
				}
			}
			view.addObject("keylist", keylist);
			
			//名录推荐
			List<WhDecproject> tjminglu = this.feiyiService.tuijianminglu(mlproid);
			view.addObject("tjminglu", tjminglu);

			//关联资讯
			List<WhZxColinfo> info = this.commservice.findColinfo(mlproid,"2016112200000005");
			view.addObject("info", info);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 传承人
	 * @return 传承人
	 */
	@RequestMapping("/chuanchengren")
	public ModelAndView chuanchengren(){
		ModelAndView view = new ModelAndView( "home/agdfyzg/chuanchengren" );
		try {

			//级别分类
			List<WhgYwiType> leveltype = this.commservice.findYwiType(EnumTypeClazz.TYPE_LEVEL.getValue());
			view.addObject("leveltype", leveltype);
			//类别分类
			List<WhgYwiType> ttype = this.commservice.findYwiType(EnumTypeClazz.TYPE_GENRE.getValue());
			view.addObject("ttype", ttype);
			//区域分类
			List<WhgYwiType> qrtype = this.commservice.findYwiType(EnumTypeClazz.TYPE_AREA.getValue());
			view.addObject("qrtype", qrtype);
			//批次分类
			List<WhgYwiType> pici = this.commservice.findYwiType(EnumTypeClazz.TYPE_BATCH.getValue());
			view.addObject("pici", pici);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 传承人列表页 数据加载
	 * @return
	 */
	@RequestMapping("/dataloadsuccessor")
	public Object dataloadsuccessor(WebRequest request){
		try {
			return this.feiyiService.dataloadsuccessor(request);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 传承人详情
	 * @return 传承人详情
	 */
	@RequestMapping("/chuanchengreninfo")
	public ModelAndView chuanchengreninfo(String suorid,ModelMap map){
		ModelAndView view = new ModelAndView( "home/agdfyzg/chuanchengreninfo" );
		try {
			//传承人 详细信息
			List<Map> suorDetail = this.feiyiService.suorDetail(suorid);
			if(suorDetail != null && suorDetail.size() > 0 ){
				map.addAttribute("suorDetail",suorDetail.get(0));
			}
			//传承人相关名录标签
			Object obj = this.feiyiService.successortopro(suorid,3);
			map.addAttribute("minglu",obj );
			
			//传承人相关名录展示
			Object obj2 = this.feiyiService.successortopro(suorid,9);
			map.addAttribute("minglu2",obj2 );
			//传承人相关资源
			//图片
			List<WhgComResource> tup = this.commservice.findRescource("1","6", suorid);
			map.addAttribute("tup",tup );
			//视频
			List<WhgComResource> spin = this.commservice.findRescource("2","6", suorid);
			map.addAttribute("spin",spin );
			//音频
			List<WhgComResource> ypin = this.commservice.findRescource("3","6", suorid);
			map.addAttribute("ypin",ypin );
			//文档
			List<WhgComResource> doc = this.commservice.findRescource("4","6", suorid);
			map.addAttribute("doc",doc );


			//传承人推荐
			List<Map> tjsuor = this.feiyiService.tuijiansuor(suorid);
			view.addObject("tjsuor", tjsuor);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 法律文件
	 * @return 法律文件
	 */
	@RequestMapping("/falvwenjian")
	public ModelAndView falvwenjian(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView view = new ModelAndView( "home/agdfyzg/falvwenjian" );
		try {
			//资讯栏目
			String realtype = "2016112200000006";
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
	 * 法律文件详情
	 * @return 法律文件详情
	 */
	@RequestMapping("/falvwenjianinfo")
	public ModelAndView falvwenjianinfo(String id, String type){
		ModelAndView view = new ModelAndView( "home/agdfyzg/falvwenjianinfo" );
		try {
			String realtype = "2016112200000006";
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
	 * 申报指南
	 * @return 申报指南
	 */
	@RequestMapping("/shenbao")
	public ModelAndView shenbao(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView view = new ModelAndView( "home/agdfyzg/shenbao" );
		try {
			//资讯栏目
			String realtype = "2016112200000007";
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
	 * 申报指南详情
	 * @return 申报指南详情
	 */
	@RequestMapping("/shenbaoinfo")
	public ModelAndView shenbaoinfo(String id, String type){
		ModelAndView view = new ModelAndView( "home/agdfyzg/shenbaoinfo" );
		try {
			String realtype = "2016112200000007";
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

	@RequestMapping("/ttype")
	public Object getMlprotypeList(){
		try {
			return this.commservice.findWhtyp("18");
		} catch (Exception e) {
			return new ArrayList();
		}
	}
}
