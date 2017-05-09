package com.creatoo.hn.actions.admin.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhRole;
import com.creatoo.hn.model.WhRolepms;
import com.creatoo.hn.services.admin.system.RoleService;
import com.creatoo.hn.services.comm.CommService;

@RestController
@RequestMapping("/admin")
public class RoleAction{
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public RoleService roleService;
	
	@Autowired
	public CommService commService;
	
	/**
	 * 提供视图
	 * @return
	 */
	@RequestMapping("/role")
	public ModelAndView toRolePage(){
		ModelAndView mav=new ModelAndView();
		mav.setViewName("admin/system/role");
		return mav;
	}	
	/**
	 * 提供数据
	 * @return
	 */
	@RequestMapping("/loadRoleList")
	public Object loadRoleList(int rows,int page){
		Object obj = null;
		try {
			obj = roleService.getList(page,rows);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return obj;
	}
	
	/**
	 * 查询可用的角色
	 * @return
	 */
	@RequestMapping("/srchRoles")
	public Object srchRoles(){
		Object obj = null;
		try {
			obj = roleService.srchAllRole();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return obj;
	}
	
	/**
	 * 修改状态
	 * @param id 角色标识 
	 * @param state 状态
	 * @return
	 */
	@RequestMapping("/updRoleState")
	public Object updRoleState(String id, String state){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		String errfield = "";
		
		//
		try {
			roleService.updateRoleState(id, state);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
			errfield = this.roleService.getErrfield();
		}
		
		//返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		rtnMap.put("errfield", errfield);
		return rtnMap;
	}
	
	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	@RequestMapping("/delRole")
	public Object delRole(String id){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		String errfield = "";
		
		//
		try {
			roleService.delRole(id);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
			errfield = this.roleService.getErrfield();
		}
		
		//返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		rtnMap.put("errfield", errfield);
		return rtnMap;
	}
	
	/**
	 * 增加角色
	 * @param name
	 * @param state
	 * @return
	 */
	@RequestMapping("/addRole")
	public Object addRole(WhRole whrole, HttpServletRequest request){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		String errfield = "";
		
		//
		try {
			//权限
			String[] pms = request.getParameterValues("pms");
			this.roleService.addRole(whrole, pms);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
			errfield = this.roleService.getErrfield();
		}
		
		//返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		rtnMap.put("errfield", errfield);
		return rtnMap;
	}
	
	/**
	 * 更新角色
	 * @param id
	 * @param name
	 * @param state
	 * @return
	 */
	@RequestMapping("/updateRole")
	public Object updateRole(WhRole whRole, HttpServletRequest request){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		String errfield = "";
		
		try{
			//权限
			String[] pms = request.getParameterValues("pms");
			this.roleService.updateRole(whRole, pms);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
			errfield = this.roleService.getErrfield();
		}
		
		//返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		rtnMap.put("errfield", errfield);
		return rtnMap;
	}
	
	/**
	 * 查询角色权限
	 * @return
	 */
	@RequestMapping("/srchRolePMS")
	public Object index2(String id){
		List<WhRolepms> pmsList = new ArrayList<WhRolepms>();
		try {
			pmsList = this.roleService.getRolePMS(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pmsList;
	}
	
}
