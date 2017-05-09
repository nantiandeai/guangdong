package com.creatoo.hn.actions.admin.volunteer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhZyfcZuzhi;
import com.creatoo.hn.services.admin.volunteer.ExcellentService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.UploadUtil;

/**
 * 志愿风采优秀组织者
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/admin/volun")
public class ExcellentAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private ExcellentService service;
	@Autowired
	public CommService commService;
	/**
	 * 返回视图（老的）
	 * @return
	 */
	@RequestMapping("/exces")
	public ModelAndView index(){
		return new ModelAndView("admin/volunteer/zyzz");
	}

	/**
	 * 返回视图
	 * @param request
	 * @param type
     * @return
     */
	@RequestMapping("/org/view/{type}")
	@WhgOPT(optType = EnumOptType.ORG, optDesc = {"访问列表页","访问添加页","访问编辑页面"},valid= {"type=list","type=add","type=edit"})

	public ModelAndView listview(HttpServletRequest request, @PathVariable("type") String type) {
		ModelAndView view = new ModelAndView("admin/volunteer/org/view_" + type);

		try {
			String zyfczzid = request.getParameter("zyfczzid");
			String targetShow = request.getParameter("targetShow");
			view.addObject("zyfczzid",zyfczzid);
			view.addObject("targetShow", targetShow);
			view.addObject("org", this.service.seach(zyfczzid));
		}catch (Exception e){
			log.error(e.getMessage(), e);
		}
		return view;
	}
	/**
	 * 分页查询优秀组织者
	 */
	@RequestMapping("/findexce")
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
	 * 添加组织者(old)
	 */
	@RequestMapping("/addexce")
	public Object addTrou(HttpServletRequest req, HttpServletResponse resp, WhZyfcZuzhi whzz, @RequestParam("zyfczzpic_up")MultipartFile zyfczzpic_up,@RequestParam("zyfczzbigpic_up")MultipartFile zyfczzbigpic_up) {
		String success = "0";
		String errmasg = "";
		//图片或者文件处理
		try {
			//当前日期
			Date now = new Date();
			//保存图片
			String uploadPath = UploadUtil.getUploadPath(req);
			
			if(zyfczzpic_up != null && !zyfczzpic_up.isEmpty()){
				String imgPath_zyfczzpic = UploadUtil.getUploadFilePath(zyfczzpic_up.getOriginalFilename(), commService.getKey("volunteer.picture"), "volunteer", "picture", now);
				zyfczzpic_up.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_zyfczzpic) );
				whzz.setZyfczzpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_zyfczzpic));
			}
			if(zyfczzbigpic_up != null && !zyfczzbigpic_up.isEmpty()){
				String imgPath_zyfczzbigpic = UploadUtil.getUploadFilePath(zyfczzbigpic_up.getOriginalFilename(), commService.getKey("volunteer.picture"), "volunteer", "picture", now);
				zyfczzbigpic_up.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_zyfczzbigpic) );
				whzz.setZyfczzbigpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_zyfczzbigpic));
			}
			whzz.setZyfczzid(this.commService.getKey("WhZyfcZuzhi"));
			whzz.setZyfczzopttime(now);
			this.service.addexce(whzz);
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
	 * 添加修改优秀组织
	 * @param whzz
     * @return
     */
	@RequestMapping("/addOrUpdate")
	@WhgOPT(optType = EnumOptType.ORG, optDesc = {"新增","编辑"},valid = {"zyfczzid=null","zyfczzid=notnull"})
	public ResponseBean addOrUpdate(WhZyfcZuzhi whzz){
		ResponseBean res = new ResponseBean();
		try {
			this.service.addOrUpdate(whzz);
		} catch (Exception e) {
			res.setSuccess(ResponseBean.FAIL);
			res.setErrormsg(e.getMessage());
			log.error(e.getMessage(), e);
		}
		return res;
	}
	/**
	 * 修改组织者(old)
	 */
	@RequestMapping("/upexce")
	public Object upexce(HttpServletRequest req, HttpServletResponse resp, WhZyfcZuzhi whzz, @RequestParam("zyfczzpic_up")MultipartFile zyfczzpic_up,@RequestParam("zyfczzbigpic_up")MultipartFile zyfczzbigpic_up){
		String success = "0";
		String errmasg = "";
		//图片或者文件处理
		try {
			//当前日期
			Date now = new Date();
			//保存图片
			String uploadPath = UploadUtil.getUploadPath(req);
			
			if(zyfczzpic_up != null && !zyfczzpic_up.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, whzz.getZyfczzpic());
				String imgPath_zyfczzpic = UploadUtil.getUploadFilePath(zyfczzpic_up.getOriginalFilename(), commService.getKey("volunteer.picture"), "volunteer", "picture", now);
				zyfczzpic_up.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_zyfczzpic) );
				whzz.setZyfczzpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_zyfczzpic));
			}
			if(zyfczzbigpic_up != null && !zyfczzbigpic_up.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, whzz.getZyfczzbigpic());
				String imgPath_zyfczzbigpic = UploadUtil.getUploadFilePath(zyfczzbigpic_up.getOriginalFilename(), commService.getKey("volunteer.picture"), "volunteer", "picture", now);
				zyfczzbigpic_up.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_zyfczzbigpic) );
				whzz.setZyfczzbigpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_zyfczzbigpic));
			}
			
			this.service.updataexce(whzz);
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
	 * 删除
	 */
	@RequestMapping("/delexcll")
	@WhgOPT(optType = EnumOptType.ORG, optDesc = {"删除"})

	public Object delTrou(String zyfczzid){
		Map<String, String> rtnMap = new HashMap<>();
		String success = "0";
		String errmsg = "";
		try {
//			String uploadPath = UploadUtil.getUploadPath(req);
//			if(zyfczzpic != null && !zyfczzpic.isEmpty()){
//				UploadUtil.delUploadFile(uploadPath, zyfczzpic);
//			}
//			if(zyfczzbigpic != null && !zyfczzbigpic.isEmpty()){
//				UploadUtil.delUploadFile(uploadPath, zyfczzbigpic);
//			}
		   this.service.delexc(zyfczzid);
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
	@RequestMapping("/checkexce")
	@WhgOPT(optType = EnumOptType.ORG, optDesc = {"审核","打回","发布","取消发布"},valid = {"tostate=2","tostate=1","tostate=3","tostate=2"})
	public Object sendCheck(String zyfczzid, int fromstate, int tostate){
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			//修改
			Object msg = this.service.checkexce(zyfczzid, fromstate, tostate);
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
	@RequestMapping("/Allstate")
	public Object CheckAll(String zyfczzids, int fromstate, int tostate){
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			//修改
			Object msg = this.service.checkAllzyzz(zyfczzids, fromstate, tostate);
			res.put("success", "success");
			res.put("msg", msg);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			res.put("error", "error"); 
		}
		return res;
	}
}
