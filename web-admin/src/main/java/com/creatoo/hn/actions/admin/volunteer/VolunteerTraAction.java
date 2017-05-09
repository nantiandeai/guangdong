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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.model.WhZypx;
import com.creatoo.hn.model.WhgSysCult;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.services.admin.volunteer.VolunteerTraService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.home.zhiyuan.VolunteerService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.UploadUtil;
@RestController
@RequestMapping("/admin/volun")
public class VolunteerTraAction {
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private CommService commservice;
	
	@Autowired
	private VolunteerTraService service;
	
	/**
	 * 返回视图
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(){
		return new ModelAndView("admin/volunteer/train/view_list");
	}
	
	 /**
     * 进入type(list|add|edit|view)视图
     * @param request 请求对象
     * @param type 视图类型(list|add|edit|view)
     * @return
     */
    @RequestMapping("/view/{type}")
	@WhgOPT(optType = EnumOptType.VOLTRA, optDesc = {"访问列表页","访问添加页","访问编辑页面"},valid= {"type=list","type=add","type=edit"})
    public ModelAndView listview(HttpServletRequest request, @PathVariable("type")String type){
        ModelAndView view = new ModelAndView("admin/volunteer/train/view_"+type);
        try {
            if("edit".equals(type) || "view".equals(type)){
                String id = request.getParameter("id");
                view.addObject("train", service.t_srchOne(id));
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }

        return view;
    }
	
	/**
	 * 分页查询志愿培训
	 */
	@RequestMapping("/findVolun")
	public Object findVolun(HttpServletRequest req, HttpServletResponse resp){

		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.service.findVolun(paramMap);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
		return rtnMap;
	}

	/**
	 * 保存志愿培训
	 * @param whZypx
	 * @param req
	 * @return
	 */
	@RequestMapping("/save")
	@WhgOPT(optType = EnumOptType.VOLTRA, optDesc = {"添加"})
	public Object saveVolunTra(WhZypx whZypx,HttpServletRequest req){
		ResponseBean res = new ResponseBean();
		try {
			//添加
			whZypx.setZypxstate(1);
			whZypx.setZypxid(commservice.getKey("wh_zypx"));
			whZypx.setZypxopttime(new Date());
			this.service.addZypx(whZypx);
		} catch (Exception e) {
			res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
		}
		return res;

	}
	
	/**
     * 编辑
     * @param request 请求对象
     * @param whZypx 编辑的资料
     * @return 执行操作返回结果的JSON信息
     */
    @RequestMapping(value = "/edit")
	@WhgOPT(optType = EnumOptType.VOLTRA, optDesc = {"编辑"})
    public ResponseBean edit(HttpServletRequest request, WhZypx whZypx){
        ResponseBean res = new ResponseBean();
        try {
            service.upZypx(whZypx);
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }
	
	/**
	 * 删除志愿培训
	 * @param zypxid
	 * @param req
	 * @return
	 */
	@RequestMapping("/delpx")
	@WhgOPT(optType = EnumOptType.VOLTRA, optDesc = {"删除"})
	public Object delZypx(String zypxid,HttpServletRequest req){
		ResponseBean res = new ResponseBean();
		try {
			this.service.deleteZypx(zypxid);
		} catch (Exception e) {
			res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
		//返回
		return res;
	}
	
	/**
	 * 审核或者取消操作
	 */
	@RequestMapping("/check")
	@WhgOPT(optType = EnumOptType.VOLTRA, optDesc = {"审核","打回","发布","取消发布"},valid = {"tostate=2","tostate=1","tostate=3","tostate=2"})
	public Object sendCheck(String zypxid, int fromstate, int tostate){
		Map<String, Object> res = new HashMap<String, Object>();
		//添加修改课时
		try {
			//修改
			Object msg = this.service.checkTeacher(zypxid, fromstate, tostate);
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
	public Object CheckAll(String zypxids, int fromstate, int tostate){
		Map<String, Object> res = new HashMap<String, Object>();
		//添加修改课时
		try {
			//修改
			Object msg = this.service.checkAllTeacher(zypxids, fromstate, tostate);
			res.put("success", "success");
			res.put("msg", msg);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			res.put("error", "error"); 
		}
		return res;
	}

}
