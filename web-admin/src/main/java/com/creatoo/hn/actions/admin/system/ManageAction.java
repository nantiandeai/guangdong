package com.creatoo.hn.actions.admin.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhMgr;
import com.creatoo.hn.model.WhSubvenue;
import com.creatoo.hn.services.admin.system.ManageService;
import com.creatoo.hn.services.comm.CommService;

/**
 * 管理员控制类
 * @author dzl
 *
 */

@RestController
@RequestMapping("/admin")
public class ManageAction {
	/**
	 * 日志控制器
	 */
	Logger log=Logger.getLogger(this.getClass());
	
	@Autowired
	public CommService commService;
	
	@Autowired
	private ManageService manageService;
	
	@Autowired
	public AdminAction adminAction;
	
	/**
	 * 管理员列表界面
	 */
	@RequestMapping("/manage")
	public ModelAndView toManage(){
		ModelAndView mav=new ModelAndView();
		mav.setViewName("admin/system/manage");
		return mav;
	}
	
	/**
	 * 查询管理员信息
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/selectManage")
	public Object manageList(int page,int rows){
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		int total = 0;
		List list = new ArrayList();
		try {
			rtnMap = manageService.findPage(page, rows);
		} catch (Exception e) {
			rtnMap.put("total", total);
			rtnMap.put("rows", list);
			log.error(e.getMessage(), e);
		}
		return rtnMap;
	}
	
	/**
	 * 添加管理员信息
	 */
    @RequestMapping("/addManage")
    public Object addManage(WhMgr whmgr,HttpSession session){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		String errfield = "";
		
		//添加管理员
		try {
			//文化馆是否为空
			String venueid = whmgr.getVenueid();
			if(venueid != null && !"".equals(venueid) && venueid.equals("0")){
				whmgr.setVenueid("0");
			}
			String name = whmgr.getName();
			//判断名称是否已存在
			int hasMgrName = this.manageService.getMgrName(name);
			//获取用户会话
			WhMgr sessWhgr = (WhMgr) session.getAttribute("user");
			String sessMgrName = sessWhgr.getName();
			//判断页面获取的管理员名称与会话中的名称是否相同
			if(sessMgrName != null && !"".equals(sessMgrName) && !sessMgrName.equals(name)){
				if(hasMgrName != 0){
					errmsg = "该登陆名称已占用";	//已有管理员 
				}else{
					if(name.equals("administrator")){
						errmsg = "非法登录名";	//为超级管理员，提示非法登录名
					}
				}
			}
			this.manageService.addMar(whmgr);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
			errfield = this.manageService.getErrfield();
		}
		//返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		rtnMap.put("errfield", errfield);
		return rtnMap;
    }
    
	/**
	 * 
	 * 修改管理员信息
	 * @return
	 */
	@RequestMapping("/modifyManage")
	public Object modifyPage(WhMgr whmgr) {
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		String errfield = "";
		
		//编辑管理员
		try {
			//文化馆是否为空
			String venueid = whmgr.getVenueid();
			if(venueid != null && !"".equals(venueid) && venueid.equals("0")){
				whmgr.setVenueid("0");
			}
			this.manageService.modifyManage(whmgr);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
			errfield = this.manageService.getErrfield();
		}
		
		//返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		rtnMap.put("errfield", errfield);
		return rtnMap;
	}
	
	/**
	 * 删除管理员信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteManage")
	public Object removeManage(String id){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		String errfield = "";
		
		//删除管理员
		try {
			this.manageService.removeManage(id);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
			errfield = this.manageService.getErrfield();
		}
		
		//返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		rtnMap.put("errfield", errfield);
		return rtnMap;
	}
	
	/**
	 * 修改管理员状态
	 * @param id
	 * @return
	 */
	@RequestMapping("/updMgrState")
	public Object updMgrState(String id, String state){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		String errfield = "";
		
		//删除管理员
		try {
			this.manageService.updMgrState(id, state);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
			errfield = this.manageService.getErrfield();
		}
		
		//返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		rtnMap.put("errfield", errfield);
		return rtnMap;
	}
	
    
    /**
	 * 跳转内容管理界面
	 * @return 
	 */
	@RequestMapping("/managepage")
	public ModelAndView managepage(WebRequest request){
		String id=request.getParameter("id");
		ModelAndView mav=new ModelAndView("admin/system/manageedit");
		if(id!=null){
			mav.addObject("title","编辑管理员信息");
			Object manage=this.manageService.getManageId(id);
			mav.addObject("id",id);
			mav.addObject("manage",manage);
			
		}else{
			mav.addObject("title","添加管理员信息");
		}
		   return mav;
	}
	
	/**
	 * 重置管理员密码
	 */
	@RequestMapping("/resetMgrPwd")
	public Object modipass(String id, String pwd){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		String errfield = "";
		
		//删除管理员
		try {
			this.manageService.updMrgPwd(id, pwd);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
			errfield = this.manageService.getErrfield();
		}
		
		//返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		rtnMap.put("errfield", errfield);
		return rtnMap;
	}

	/**
	 * 根据管理员文化馆标识获得对应的文化馆信息
	 * @return
	 */
	@RequestMapping("/refSubvenue")
	public Object subvenue(HttpSession session){
		List<WhSubvenue> list = new ArrayList<WhSubvenue>();
		try {
			//获取管理员会话
			WhMgr  sessMgr = (WhMgr) session.getAttribute("user");
			if(sessMgr != null ){
					//获取管理员的文化馆标识
				String venueid = sessMgr.getVenueid();
				list = (List<WhSubvenue>) this.manageService.refSub(venueid);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return list;
	}
	/**
	 * 默认设置密码
	 */
	@RequestMapping("/resetpwd")
	public Object resetpwd(String id,String password){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		String errfield = "";
		
		//管理员
		try {
			this.manageService.resetpwd(id,password);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
			errfield = this.manageService.getErrfield();
		}
		
		//返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		rtnMap.put("errfield", errfield);
		return rtnMap;
	}
}
