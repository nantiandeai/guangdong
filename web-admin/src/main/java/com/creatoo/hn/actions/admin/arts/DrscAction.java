package com.creatoo.hn.actions.admin.arts;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.model.WhgSysUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhDrsc;
import com.creatoo.hn.services.admin.arts.DrscService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.UploadUtil;

/**
 * 用户资讯展示控制器
 * @author wangxl
 * @version 2016.11.08
 */
@RestController
@RequestMapping("/admin/drsc")
public class DrscAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public DrscService service;
	
	@Autowired
	public CommService commservice;
	
	/**
	 * 数字资源管理页面
	 * @return 数字资源管理页面
	 */
	@RequestMapping("/index/{type}")
	public ModelAndView index(HttpServletRequest request, ModelMap mmp, @PathVariable("type") String type){
		ModelAndView view = new ModelAndView();
		try {
			mmp.addAttribute("type", type);
			if ("add".equalsIgnoreCase(type)){
				String id = request.getParameter("id");
				String targetShow = request.getParameter("targetShow");
				if(id != null){
					mmp.addAttribute("id", id);
					mmp.addAttribute("targetShow", targetShow);
					mmp.addAttribute("drsc",this.service.srchOne(id));
				}
				view.setViewName("admin/train/drsc/view_add");
			}else{
				view.setViewName("admin/train/drsc/view_list");
			}
		} catch (Exception e) {
			log.error("加载指定ID的培训师资信息失败", e);
		}
		return view;
		//return new ModelAndView( "admin/arts/drsc" );
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
	public Object add(WhDrsc drsc){
		ResponseBean res = new ResponseBean();
		try {
			//当前日期
			Date now = new Date();
			//图片或者文件处理
			/*String uploadPath = UploadUtil.getUploadPath(req);
			//数字资源
			if(drscfile_up != null && !drscfile_up.isEmpty()){
				String drscfile_up_path = UploadUtil.getUploadFilePath(drscfile_up.getOriginalFilename(), commservice.getKey("drsc.video"), "drsc", "video", now);
				drscfile_up.transferTo( UploadUtil.createUploadFile(uploadPath, drscfile_up_path) );
				drsc.setDrscpath(UploadUtil.getUploadFileUrl(uploadPath, drscfile_up_path));
			}*/
			this.service.t_add(drsc);
		} catch (Exception e) {
			res.setSuccess(ResponseBean.FAIL);
			res.setErrormsg("在线点播保存失败");
			log.error(res.getErrormsg(), e);
		}
		return res;
	}
	
	/**
	 * 编辑
	 * @return
	 */
	@RequestMapping("/edit")
	public Object edit(WhDrsc drsc, HttpServletRequest req){
		ResponseBean res = new ResponseBean();
		if (drsc.getDrscid() == null){
			res.setSuccess(ResponseBean.FAIL);
			res.setErrormsg("在线点播主键信息丢失");
			return res;
		}
		try {
			//当前日期
			Date now = new Date();
			//图片或者文件处理
			/*String uploadPath = UploadUtil.getUploadPath(req);
			//数字资源
			if(drscfile_up != null && !drscfile_up.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, drsc.getDrscpath());//编辑修改了资源先删除之前的资源

				String drscfile_up_path = UploadUtil.getUploadFilePath(drscfile_up.getOriginalFilename(), commservice.getKey("drsc.video"), "drsc", "video", now);
				drscfile_up.transferTo( UploadUtil.createUploadFile(uploadPath, drscfile_up_path) );
				drsc.setDrscpath(UploadUtil.getUploadFileUrl(uploadPath, drscfile_up_path));
			}*/
			this.service.t_edit(drsc);
		}catch (Exception e){
			res.setSuccess(ResponseBean.FAIL);
			res.setErrormsg("在线点播信息保存失败");
			log.error(res.getErrormsg(), e);
		}
		return res;
	}
	
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("/del")
	public Object del(String id, HttpServletRequest req){
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
	 * @param ids 资源标识
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
