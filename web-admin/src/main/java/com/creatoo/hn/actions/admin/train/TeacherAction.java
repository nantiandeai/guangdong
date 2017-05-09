package com.creatoo.hn.actions.admin.train;

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

import com.creatoo.hn.model.WhTrain;
import com.creatoo.hn.model.WhUserTeacher;
import com.creatoo.hn.services.admin.train.TeacherService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.UploadUtil;

@RestController
@RequestMapping("/admin/tea")
public class TeacherAction {
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private TeacherService service;

	@Autowired
	private CommService commService;
	/**
	 * 返回老师管理视图
	 * @return
	 */
	@RequestMapping("/teacher")
	public ModelAndView index(){
		return new ModelAndView("/admin/train/teacher");
	}
	
	/**
	 * 分页查询培训老师
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("/findTeacher")
	public Object sreachTeacher(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.service.paggingTeacher(paramMap);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
		return rtnMap;
	}
	
	/**
	 * 添加或者修改
	 * @param whUserTeacher
	 * @param req
	 * @param teacherpic
	 * @return
	 */
	@RequestMapping("/save")
	public Object saveTraintpl(WhUserTeacher whUserTeacher, HttpServletRequest req,  @RequestParam("teacherpic_up")MultipartFile teacherpic){
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		//添加修改课时
		try {
			//当前日期
			Date now = new Date();
			//图片或者文件处理
			String uploadPath = UploadUtil.getUploadPath(req);
			//图片处理
			//trapic
			if(teacherpic != null && !teacherpic.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, whUserTeacher.getTeacherpic());
				String imgPath_trapic = UploadUtil.getUploadFilePath(teacherpic.getOriginalFilename(), commService.getKey("train.picture"), "train", "picture", now);
				teacherpic.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_trapic) );
				whUserTeacher.setTeacherpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_trapic));
			}
					
			//保存
			String teacherid = whUserTeacher.getTeacherid();
			if(teacherid != null && !"".equals(teacherid.trim())){
				//修改
				this.service.updTeacher(whUserTeacher);
			}else{
				//添加
				whUserTeacher.setTeacherstate(1);
				whUserTeacher.setTeacheropttime(new Date());
				whUserTeacher.setTeacherid(commService.getKey("wh_traintpl"));
				this.service.addTeacher(whUserTeacher);
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
	 *  删除培训数据
	 * @param req
	 * @param
	 * @return
	 */
	@RequestMapping("/delteacher")
	public Object saveTraintpl(HttpServletRequest req,String teacherid){
		String uploadPath = UploadUtil.getUploadPath(req);
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		try {
			this.service.deleteTea(uploadPath,teacherid);
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
	@RequestMapping("/check")
	public Object sendCheck(String teacherid, int fromstate, int tostate){
		Map<String, Object> res = new HashMap<String, Object>();
		//添加修改课时
		try {
			//修改
			Object msg = this.service.checkTeacher(teacherid, fromstate, tostate);
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
	@RequestMapping("/checkAll")
	public Object CheckAll(String teacherids, int fromstate, int tostate){
		Map<String, Object> res = new HashMap<String, Object>();
		//添加修改课时
		try {
			//修改
			Object msg = this.service.checkAllTeacher(teacherids, fromstate, tostate);
			res.put("success", "success");
			res.put("msg", msg);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			res.put("error", "error"); 
		}
		return res;
	}
}
