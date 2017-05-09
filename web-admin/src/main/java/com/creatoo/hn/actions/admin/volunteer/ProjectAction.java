package com.creatoo.hn.actions.admin.volunteer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.emun.EnumOptType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhZyfcXiangmu;
import com.creatoo.hn.model.WhZyfcZuzhi;
import com.creatoo.hn.services.admin.volunteer.ExcellentService;
import com.creatoo.hn.services.admin.volunteer.ProjectService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.UploadUtil;
/**
 * 志愿者风采项目
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/admin/volun")
public class ProjectAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private ProjectService service;
	@Autowired
	public CommService commService;
	/**
	 * 返回视图
	 * @return
	 */
	@RequestMapping("/projec")
	@WhgOPT(optType = EnumOptType.PROJECT, optDesc = {"访问列表页"})
	public ModelAndView index(){
		return new ModelAndView("admin/volunteer/xmsf/view_list");
	}

	/**
	 * 添加项目示范表单
	 * @return
	 */
	@RequestMapping("/projec/view/add")
	@WhgOPT(optType = EnumOptType.PROJECT, optDesc = {"访问添加页"})
	public ModelAndView addView(){
		return new ModelAndView("admin/volunteer/xmsf/view_add");
	}

	/**
	 * 编辑项目示范表单
	 * @return
	 */
	@RequestMapping("/projec/view/edit")
	@WhgOPT(optType = EnumOptType.PROJECT, optDesc = {"访问编辑页"})
	public ModelAndView editView(String id){
		ModelAndView view = new ModelAndView("admin/volunteer/xmsf/view_edit");
		try{
			view.addObject("xminfo", this.service.getXiangmu(id));
		}catch (Exception e){
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 分页查询优风采项目
	 */
	@RequestMapping("/findpro")
	public Object selectVolun(HttpServletRequest req, HttpServletResponse resp){

		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.service.select(paramMap);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
		return rtnMap;
	}
	/**
	 * 添加展示项目
	 */
	@RequestMapping("/addpro")
	@WhgOPT(optType = EnumOptType.PROJECT, optDesc = {"添加"})
	public Object addProj(HttpServletRequest req, HttpServletResponse resp, WhZyfcXiangmu whxm) {
		String success = "0";
		String errmasg = "";
		//图片或者文件处理
		try {
			//当前日期
			Date now = new Date();
			whxm.setZyfcxmid(this.commService.getKey("WhZyfcXiangmu"));
			whxm.setZyfcxmopttime(now);
			this.service.addpro(whxm);
		} catch (Exception e) {
			success = "1";
			errmasg = e.getMessage();
		}
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("success", success);
		res.put("msg", errmasg);
		return res;
	} 
	/**
	 * 修改展示项目
	 */
	@RequestMapping("/upproj")
	@WhgOPT(optType = EnumOptType.PROJECT, optDesc = {"修改"})
	public Object upProj(HttpServletRequest req, HttpServletResponse resp, WhZyfcXiangmu whxm){
		String success = "0";
		String errmasg = "";
		//图片或者文件处理
		try {
			//当前日期
			Date now = new Date();
			this.service.upProj(whxm);
		} catch (Exception e) {
			success = "1";
			errmasg = e.getMessage();
		}
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("success", success);
		res.put("msg", errmasg);
		return res;
	}
	/**
	 * 根据主键 删除 
	 */
	@RequestMapping("/delproj")
	@WhgOPT(optType = EnumOptType.PROJECT, optDesc = {"删除"})
	public Object delTrou(HttpServletRequest req, String zyfcxmid, String zyfcxmpic,String zyfcxmbigpic){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		
		try {
			String uploadPath = UploadUtil.getUploadPath(req);
			if(zyfcxmpic != null && !zyfcxmpic.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, zyfcxmpic);
			}
			if(zyfcxmbigpic != null && !zyfcxmbigpic.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, zyfcxmbigpic);
			}
		   this.service.delpro(zyfcxmid);
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
	 * 审核或者取消操作
	 */
	@RequestMapping("/checkproj")
	@WhgOPT(optType = EnumOptType.PROJECT, optDesc = {"审核","打回","发布","取消发布"},valid = {"tostate=2","tostate=1","tostate=3","tostate=2"})
	public Object sendCheck(String zyfcxmid, int fromstate, int tostate){
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			//修改
			Object msg = this.service.checkexce(zyfcxmid, fromstate, tostate);
			res.put("success", "success");
			res.put("msg", msg);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			res.put("error", "error"); 
		}
		return res;
	}
	/**
	 * 批量审核或者取消操作操作
	 */
	@RequestMapping("/Allproj")
	public Object CheckAll(String zyfcxmids, int fromstate, int tostate){
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			//修改
			Object msg = this.service.checkAllzyzz(zyfcxmids, fromstate, tostate);
			res.put("success", "success");
			res.put("msg", msg);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			res.put("error", "error"); 
		}
		return res;
	}
}
