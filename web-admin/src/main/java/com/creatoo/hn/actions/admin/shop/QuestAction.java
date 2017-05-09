package com.creatoo.hn.actions.admin.shop;

import java.util.ArrayList;
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

import com.creatoo.hn.model.WhQuest;
import com.creatoo.hn.services.admin.shop.QuestService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.UploadUtil;

/**
 * 艺术广场页面导航
 * @author wangxl
 * @version 2016.10.19
 */
@RestController
@RequestMapping("/admin/quest")
public class QuestAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public CommService commservice;
	
	@Autowired
	public QuestService service;
	
	/**
	 * 调查问卷管理页面
	 * @return 调查问卷管理页面
	 */
	@RequestMapping("/index")
	public ModelAndView index(){
		return new ModelAndView( "admin/quest/index" );
	}
	
	/**
	 * 分页查询数字资源
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("/srchPagging")
	public Object srchPagging(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.service.srchPagging(paramMap);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
		
		return rtnMap;
	}
	
	/**
	 * 添加
	 * @return
	 */
	@RequestMapping("/add")
	public Object add(WhQuest drsc, HttpServletRequest req, HttpServletResponse resp, @RequestParam(value="drscpic_up", required=false)MultipartFile drscpic_up, @RequestParam(value="drscpath_up", required=false)MultipartFile drscfile_up){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		
		try {
			
			//保存
			drsc.setQueid(commservice.getKey("WhQuest"));
			this.service.add(drsc);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		
		//返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	}
	
	/**
	 * 编辑
	 * @return
	 */
	@RequestMapping("/edit")
	public Object edit(WhQuest drsc, HttpServletRequest req, HttpServletResponse resp, @RequestParam(value="drscpic_up", required=false)MultipartFile drscpic_up, @RequestParam(value="drscpath_up", required=false)MultipartFile drscfile_up){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		
		//编辑
		try {			
			//保存
			this.service.edit(drsc);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		
		//返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	}
	
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("/del")
	public Object edit(String id, HttpServletRequest req){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		
		//添加修改课时
		try {
			//图片或者文件处理
			String uploadPath = UploadUtil.getUploadPath(req);
			
			//删除
			this.service.delete(id, uploadPath);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		
		//返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	}
	
	/**
	 * 修改资源状态
	 * @param id 资源标识
	 * @param fromState 修改之前的状态
	 * @param toState 修改之后的状态
	 * @return
	 */
	@RequestMapping("/updState")
	public Object updState(String ids, int fromState, int toState){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		
		//添加修改课时
		try {
			//保存
			this.service.updState(ids, fromState, toState);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		
		//返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	} 
}
