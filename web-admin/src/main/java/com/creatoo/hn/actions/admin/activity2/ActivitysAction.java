package com.creatoo.hn.actions.admin.activity2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.creatoo.hn.model.WhActivity;
import com.creatoo.hn.model.WhActivitybm;
import com.creatoo.hn.model.WhActivityitm;
import com.creatoo.hn.model.WhActivitytpl;
import com.creatoo.hn.services.admin.activity2.ActivitysServices;

@Controller
@RequestMapping("/admin/activity")
/**
 * 活动控制层
 * @author yanjianbo
 *
 */
public class ActivitysAction {
	Logger log = Logger.getLogger(this.getClass());

	private static final String VIEW_DIR = "admin/activity2/";
	
	@Autowired
	private ActivitysServices activitysServices;
	
	/**
	 * 跳转 活动管理 页面
	 * @return
	 */
	@RequestMapping("/activitytpl")
	public String toActivitytplPage() {
		try {
			return VIEW_DIR + "activitytpl";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	/**
	 * 跳转 活动管理 页面
	 * @return
	 */
	@RequestMapping("/activity")
	public String toActivityPage() {
		try{
			return VIEW_DIR + "activity";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	/**
	 * 跳转 活动审核 页面
	 * @return
	 */
	@RequestMapping("/activitycheck")
	public String toActivitycheckPage() {
		try{
			return VIEW_DIR + "activitycheck";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	/**
	 * 跳转 活动发布 页面
	 * @return
	 */
	@RequestMapping("/activitypush")
	public String toActivitypushPage() {
		try{
			return VIEW_DIR + "activitypush";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 跳转活动场次页面
	 * @return
	 */
	@RequestMapping("/activityitm")
	public String toActivityitmPage(String actvisyp,String actvrefid,String canEdit,String actvisenrol,ModelMap map) {
		try{
			map.addAttribute("actvrefid", actvrefid);
			map.addAttribute("canEdit", canEdit);
			map.addAttribute("actvisenrol",actvisenrol);
			map.addAttribute("actvisyp",actvisyp);
			return VIEW_DIR + "activityitm";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	/**
	 * 跳转活动报名页面
	 * @return
	 */
	@RequestMapping("/activitybm")
	public String toActivitybmPage() {
		try {
			return VIEW_DIR + "activitybm";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	/**
	 * 活动 加载数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/loadActivity")
	@ResponseBody
	public Object loadActivity(int page,int rows,WebRequest request){
		
		try {
			return this.activitysServices.loadActivity(page,rows,request);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 活动模板 加载数据
	 * @param page
	 * @param rows
	 * @param request
	 * @return
	 */
	@RequestMapping("/loadActivitytpl")
	@ResponseBody
	public Object loadActivitytpl(int page,int rows,WebRequest request){
		try {
			return this.activitysServices.loadActivitytpl(page,rows,request);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取 模板 标题
	 * 
	 * @return
	 */
	@RequestMapping("/loadActtplTiltle")
	@ResponseBody
	public Object loadActtplTiltle(WebRequest request){
		try {
			return this.activitysServices.loadActtplTiltle(request);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 修改审批状态
	 * @param whActitm
	 * @return
	 */
	@RequestMapping("/editactitmstate")
	@ResponseBody
	public Object editActitmState(WhActivity activity,int fromstate,int tostate,String params,String isss) {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			Object msg=this.activitysServices.editActitmState(activity,fromstate,tostate,params,isss);
			res.put("success", "success");
			res.put("msg", msg);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			res.put("error", "error"); 
		}
		return res;
	}
	
	/**
	 * 修改或增加 活动
	 * @param request
	 * @param activity
	 * @param file_image
	 * @param file_imagesm
	 * @param one_url
	 * @param temp_url
	 * @return
	 */
    @RequestMapping("/addOrEditActivity")
    @ResponseBody
	public Object addOrEditActInfo(String actvtplid,HttpServletRequest request, WhActivity activity, MultipartFile file_image,MultipartFile file_imagesm
			,MultipartFile one_url,MultipartFile temp_url) {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			this.activitysServices.addOrEditActInfo(actvtplid,activity, request, file_image,file_imagesm,one_url,temp_url);
			res.put("success", true);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;
	}
    /**
	 * 修改或增加 活动模板
	 * @param request
	 * @param activity
	 * @param file_image
	 * @param file_imagesm
	 * @param one_url
	 * @param temp_url
	 * @return
	 */
    @RequestMapping("/addOrEditActivitytpl")
    @ResponseBody
	public Object addOrEditActtplInfo(HttpServletRequest request, WhActivitytpl activitytpl, MultipartFile file_image,MultipartFile file_imagesm
			, MultipartFile one_url, MultipartFile temp_url) {
    	System.out.println(file_image+",+,"+one_url);
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			this.activitysServices.addOrEditActtplInfo(activitytpl, request, file_image,file_imagesm,one_url,temp_url);
			res.put("success", true);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;
	}
    /**
     * 删除 活动
     * @param request
     * @param actvid
     * @return
     */
    @RequestMapping("/removeActivity")
	@ResponseBody
	public Object removeActivity(HttpServletRequest request, String actvid) {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			this.activitysServices.removeActivity(actvid,request);
			res.put("success", true);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;
	}
    /**
     * 删除 活动模板
     * @param request
     * @param actvid
     * @return
     */
    @RequestMapping("/removeActivitytpl")
	@ResponseBody
	public Object removeActivitytpl(HttpServletRequest request, String actvtplid) {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			this.activitysServices.removeActivitytpl(actvtplid,request);
			res.put("success", true);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;
	}
    
    /**
     * 页面配置不带分页,条件查询
     * lijun
     * 
     */
    @RequestMapping("/selectAct")
	@ResponseBody
	public Object actSelect(String actvtype,String actvstate){
    List<WhActivity> list = new ArrayList<WhActivity>();
	try {
		list = (List<WhActivity>) this.activitysServices.selectActiviys(actvtype,actvstate);
	} catch (Exception e) {
		log.error(e.getMessage(), e);
	}
	 return list;
    }
    
    /**
	 * 活动场次表 加载
	 * @param page
	 * @param rows
	 * @param request
	 * @return
	 */
    @RequestMapping("/activityitmList")
 	@ResponseBody
    public Object activityitmList(int page,int rows,WebRequest request){
    	try {
			return this.activitysServices.activityitmList(page, rows, request);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			return null;
		}
    }
    
    /**
     * 活动场次 增加 或 修改
     * @param actvitm
     */
    @RequestMapping("/addoreditActivityitm")
    @ResponseBody
    public String addoreditActivityitm(WhActivityitm  actvitm,String actvrefid){
    	try {
			this.activitysServices.addoreditActivityitm(actvitm,actvrefid);
			return "success";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			return "fail";
		}
    }
    /**
     * 删除活动
     * @param id
     * @return
     */
    @RequestMapping("/removeactivity")
    @ResponseBody
	public Object removeactivity(String id) {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			this.activitysServices.removeactivity(id);
			res.put("success", true);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;
	}
    /**
     * 后台 活动报名数据加载
     * @param request
     * @return
     */
    @RequestMapping("/loadActivitybm")
    @ResponseBody
    public Object loadActivitybm(int page,int rows,WebRequest request){
	   try {
		return this.activitysServices.loadActivitybm(page,rows,request);
	} catch (Exception e) {
		log.error(e.getMessage(), e);
		e.printStackTrace();
		return null;
	}
    }

    /***
     * 活动报名审核
     * @param activitybm
     * @return
     */
    @RequestMapping("/updateActivitybm")
    @ResponseBody 
    public Object updateActivitybm(WhActivitybm activitybm){
    	Map<String, Object> res = new HashMap<String, Object>();
    	try {
    	res.put("success", "0");
		String tishi = this.activitysServices.updateActivitybm(activitybm);
		res.put("success", "0");
		res.put("tishi", tishi);
		} catch (Exception e) {
		log.error(e.getMessage(),e);
		res.put("success", "1");
		res.put("msg", e.getMessage());
		}
    	return res;
    }
    
    
    /**
     * 活动批量报名
     * @param params
     * @return
     */
    @RequestMapping("/updateselectActivitybm")
    @ResponseBody 
    public Object updateselectActivitybm(String params,WhActivitybm bm){
    	Map<String, Object> res = new HashMap<String, Object>();
    	try {
    	res.put("success", "0");
		String tishi = this.activitysServices.updateselectActivitybm(params,bm);
		res.put("tishi", tishi);
		} catch (Exception e) {
		log.error(e.getMessage(),e);
		res.put("success", "1");
		res.put("msg", e.getMessage());
		}
    	return res;
    }
    
    
    
    /**
     * 活动报名删除
     * @param actbmid
     * @return
     */
    @RequestMapping("/delActbm")
    @ResponseBody 
    public Object delActbm(String actbmid,HttpServletRequest request){
    	Map<String, Object> res = new HashMap<String, Object>();
		try {
			this.activitysServices.delActbm(actbmid,request);
			res.put("success", true);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;
    }
    
    /**
     * 设置为模板
     * @param actvidtpl
     * @param req
     * @return
     * @throws Exception
     */
    @RequestMapping("/toActivitytpl")
    @ResponseBody 
    public Object toActivitytpl(WhActivitytpl actvidtpl,HttpServletRequest req) throws Exception{
	    Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		try {
			String msg=this.activitysServices.toActivitytpl(actvidtpl, req);
			success="1";
			res.put("success",success);
			res.put("msg",msg);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			res.put("errmsg","模板图片/文件找不到,设置模板失败!");
		}
		return res;
    }
}
