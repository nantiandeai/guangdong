package com.creatoo.hn.actions.admin.arts;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhMagepage;
import com.creatoo.hn.model.WhMagezine;
import com.creatoo.hn.services.admin.arts.MagezineService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.UploadUtil;

@RestController
@RequestMapping("/admin/magezine")
public class MagazineAction {
	
	/**
	 * 日志
	 */
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private MagezineService service;
	@Autowired
	private CommService commService;
	/**
	 * 返回电子杂志视图
	 * @return
	 */
	@RequestMapping("/magepage")
	public ModelAndView index(){
		return new ModelAndView("/admin/arts/magezine");
	}
	
	/**
	 * 查找所有的电子杂志
	 * @return
	 */
	@RequestMapping("/selmage")
	public Object selectmaga(HttpServletRequest req,HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.service.findMage(paramMap);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
		return rtnMap;
	}
	
	/**
	 * 保存电子杂志
	 */
	@RequestMapping("/save")
	public Object saveMage(WhMagezine whMagezine,HttpServletRequest req,@RequestParam(value="magepic_up")MultipartFile magepic){
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		String mageid = whMagezine.getMageid();
		try {
			//当前日期
			Date now = new Date();
			//图片或者文件处理
			String uploadPath = UploadUtil.getUploadPath(req);
			//图片处理
			//trapic
			if(magepic != null && !magepic.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, whMagezine.getMagepic());
				String imgPath_magepic = UploadUtil.getUploadFilePath(magepic.getOriginalFilename(), commService.getKey("art.picture"), "art", "picture", now);
				magepic.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_magepic) );
				whMagezine.setMagepic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_magepic));
			}
			
			if (mageid != null && !"".equals(mageid.trim()) ) {
				this.service.updateMage(whMagezine);
			}else {
				whMagezine.setMageid(this.commService.getKey("wh_magezine"));
				whMagezine.setMagestate(0);
				this.service.insertMage(whMagezine);
			}
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		res.put("success", success);
		res.put("errmsg", errmsg);
		return res;
	}
	
	/**
	 * 删除电子杂志
	 * @param mageid
	 * @return
	 */
	@RequestMapping("/delmage")
	public Object deleteMage(String mageid,HttpServletRequest req){
		String uploadPath = UploadUtil.getUploadPath(req);
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		try {
			this.service.delMage(mageid,uploadPath);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		res.put("success", success);
		res.put("errmsg", errmsg);
		return res;
	}
	
	/**
	 * 审核和发布
	 * @param traid
	 * @param trastate
	 * @return
	 */
	@RequestMapping("/check")
	public Object publish(String mageid,int fromstate, int tostate){
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		//添加修改课时
		try {
			//修改
			this.service.checkOrBack(mageid,fromstate,tostate);
			
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		res.put("success", success);
		res.put("errmsg", errmsg);
		return res;
	}
	/**
	 * 批量审核和发布
	 * @param traid
	 * @param trastate
	 * @return
	 */
	@RequestMapping("/allcheck")
	public Object allcheck(String mageid,int fromstate, int tostate){
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		//添加修改课时
		try {
			//修改
			this.service.allCheckOrBack(mageid,fromstate,tostate);
			
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		res.put("success", success);
		res.put("errmsg", errmsg);
		return res;
	}
	//--------------------------页码管理----------------------------------------
	/**
	 * 返回页码管理视图
	 * @return
	 */
	@RequestMapping("/magezinepage")
	public ModelAndView page(String pagemageid){
		ModelAndView view = new ModelAndView("/admin/arts/magezinepage");
		try {
			view.addObject("pagemageid", pagemageid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 查找所有的页码信息
	 * @return
	 */
	@RequestMapping("/pageinfo")
	public Object selPage(HttpServletRequest req,HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.service.findPage(paramMap);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
		return rtnMap;
	}
	
	@RequestMapping("/savePage")
	public Object savePage(WhMagepage whMagepage,HttpServletRequest req,@RequestParam(value="pagepic_up")MultipartFile pagepic){
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		String pageid = whMagepage.getPageid();
		try {
			//当前日期
			Date now = new Date();
			//图片或者文件处理
			String uploadPath = UploadUtil.getUploadPath(req);
			//图片处理
			//trapic
			if(pagepic != null && !pagepic.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, whMagepage.getPagepic());
				String imgPath_pagepic = UploadUtil.getUploadFilePath(pagepic.getOriginalFilename(), commService.getKey("art.picture"), "art", "picture", now);
				pagepic.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_pagepic) );
				whMagepage.setPagepic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_pagepic));
			}
			
			if (pageid != null && !"".equals(pageid.trim()) ) {
				this.service.updatePage(whMagepage);
			}else {
				System.out.println(whMagepage.getPagemageid()+"-------------------");
				whMagepage.setPageid(this.commService.getKey("wh_magezine"));
				whMagepage.setPagestate(1);
				this.service.insertPage(whMagepage);
			}
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		res.put("success", success);
		res.put("errmsg", errmsg);
		return res;
	} 
	
	/**
	 * 删除页码
	 * @return
	 */
	@RequestMapping("/delPage")
	public Object delPage(String pageid,HttpServletRequest req){
		String uploadPath = UploadUtil.getUploadPath(req);
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		try {
			this.service.delPage(pageid,uploadPath);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		res.put("success", success);
		res.put("errmsg", errmsg);
		return res;
	}
	
	/**
	 * 启用和停用
	 * @param traid
	 * @param trastate
	 * @return
	 */
	@RequestMapping("/checkPage")
	public Object onOrOff(String pageid,int fromstate, int tostate){
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		//添加修改课时
		try {
			//修改
			this.service.onOrOff(pageid,fromstate,tostate);
			
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		res.put("success", success);
		res.put("errmsg", errmsg);
		return res;
	}
}
