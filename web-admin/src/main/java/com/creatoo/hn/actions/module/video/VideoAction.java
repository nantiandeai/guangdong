package com.creatoo.hn.actions.module.video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.emun.EnumOptType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.AliyunOssUtil;
import com.creatoo.hn.utils.ReqParamsUtil;

/**
 * 用户资讯展示控制器
 * @author wangxl
 * @version 2016.11.08
 */
@RestController
@RequestMapping("/admin/video")
public class VideoAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public VideoService service;
	
	@Autowired
	public CommService commservice;
	
	/**
	 * 数字资源管理页面
	 * @return 数字资源管理页面
	 */
	@RequestMapping("/index")
	@WhgOPT(optType = EnumOptType.VIDEO, optDesc = "访问视频管理页面")
	public ModelAndView index(){
		return new ModelAndView( "admin/video/list" );
	}
	
	/**
	 * 上传文件页面
	 * @return
	 */
	@RequestMapping("/upload")
	@WhgOPT(optType = EnumOptType.VIDEO, optDesc = "访问上传页面")
	public ModelAndView uploadPage(String dir){
		ModelAndView view = new ModelAndView( "admin/video/upload" );
		view.addObject("dir", dir);
		return view;
	}
	
	/**
	 * 分页查询数字资源
	 * @param req
	 * @param resp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/srchPagging")
	public Object srchPagging(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();
        try {
        	rtnList = (List<Map<String, Object>>)this.service.srchPagging(paramMap).get("rows");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return rtnList;
	}
	
	/**
	 * 批量删除视频
	 * @param ids
	 * @return
	 */
	@RequestMapping("/del")
	@WhgOPT(optType = EnumOptType.VIDEO, optDesc = "删除")
	public Object updState(String ids){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		
		//添加修改课时
		try {
			//保存
			this.service.delete(ids);
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
	 * 创建文件夹
	 * @return
	 */
	@RequestMapping("/createDir")
	@WhgOPT(optType = EnumOptType.VIDEO, optDesc = "创建目录")
	public Object createDir(String pdir, String dir){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		
		//添加修改课时
		try {
			AliyunOssUtil.createDir(pdir, dir);
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
