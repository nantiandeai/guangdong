package com.creatoo.hn.actions.admin.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.services.admin.system.UserService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.EmailUtil;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.SmsUtil;
import com.creatoo.hn.utils.WhConstance;

/**
 * 用户信息控制类
 * 
 * @author dzl
 *
 */
@RestController
@RequestMapping("/admin")
public class UserAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	public CommService commService;

	@Autowired
	private UserService userService;

	/**
	 * 用户列表界面
	 */
	@RequestMapping("/user")
	public ModelAndView toUser() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/system/user");
		return mav;
	}
	
	/**
	 * 根据id获得用户信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/lookUser")
	public Object lookUser(String id){
		return this.userService.getUserId(id);
	}
	
	/**
	 * 根据条件查询用户信息
	 * 
	 * @return
	 */
	@RequestMapping("/selectUser")
	@ResponseBody
	public Object userList(int page, int rows) {
		try {
			return this.userService.findPage(page, rows);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return null;
		}
	}
	
	/**
	 * 工具栏加载数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/loadUser")
	@ResponseBody
	public Object loadUser(int page,int rows,WebRequest request){
		
		try {
			return this.userService.loadUser(page, rows, request);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 添加用户信息
	 */
	@RequestMapping("/addUser")
	public Object addUser(WhUser whuser){
    	return this.userService.addUser(whuser);
    }

	/**
	 * 跳转内容管理界面
	 * 
	 * @return
	 */
	@RequestMapping("/userpage")
	public ModelAndView userpage(WebRequest request) {
		String id = request.getParameter("id");
		ModelAndView mav = new ModelAndView("admin/system/useredit");
		if (id != null) {
			mav.addObject("title", "编辑用户信息");
			Object user = this.userService.getUserId(id);
			mav.addObject("id", id);
			mav.addObject("user", user);

		} else {
			mav.addObject("title", "添加用户信息");
		}
		return mav;
	}

	/**
	 * 删除用户信息
	 * 
	 * @param id
	 * @return
	 */

	@RequestMapping("/deleteUser")
	public Object removeUser(String id) {
		userService.removeUser(id);
		Map<String, Object> del = new HashMap<String, Object>();
		del.put("success", true);
		return del;
	}
	
	/**
	 * 用户实名审核
	 * @param user
	 * @return
	 */
	@RequestMapping("/checkUserReal")
	public Object checkUserReal(WhUser user){
		Map<String,Object> res = new HashMap<String,Object>();
		try {
			user = this.userService.checkUserReal(user);
			//获取用户信息
			String nickname = user.getNickname();
			String phone = user.getPhone();
			String email = user.getEmail();
			Integer isrealname = user.getIsrealname();
			
			String moban = WhConstance.getSysProperty("SMS_CheckResult");
			moban = moban.replace("{name}", nickname);
			
			//优先手机发送
			if(phone != null && !"".equals(phone)){
				if(isrealname != null && !"".equals(isrealname) && isrealname == 1){
					//审核通过
					moban = moban.replace("{state}", "已");
					moban = moban.replace("{after}", "可前往查看您的实名认证信息");
					SmsUtil.sendNotice(phone, moban);
				}else if(isrealname != null && !"".equals(isrealname) && isrealname == 2){
					//审核不通过
					moban = moban.replace("{state}", "未");
					moban = moban.replace("{after}", "请查看实名资料是否完善");
					SmsUtil.sendNotice(phone, moban);
				}
			}else{
				//邮箱发送
				if(email != null && !"".equals(email)){
					if(isrealname != null && !"".equals(isrealname) && isrealname == 1){
						//审核通过
						moban = moban.replace("{state}", "已");
						moban = moban.replace("{after}", "可前往查看您的实名认证信息");
						EmailUtil.sendNoticeEmail(email, moban);
					}else if(isrealname != null && !"".equals(isrealname) && isrealname == 2){
						//审核不通过
						moban = moban.replace("{state}", "未");
						moban = moban.replace("{after}", "请查看实名资料是否完善");
						EmailUtil.sendNoticeEmail(email, moban);
					}
				}
			}
			
			res.put("success", true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			res.put("success", false);
		}
		return res;
	}
	
	/**
	 * 修改用户信息（设置内部员工）
	 */
	@RequestMapping("/setInner")
	public Object modifyUser(WhUser whuser){
		Map<String,Object> map = new HashMap<String,Object>();
		String success = "0";
		String errMsg = "";
		try {
			this.userService.modifyUser(whuser);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;
	}
	
	/**
	 * 查找历史培训
	 * @param uid
	 * @return
	 */
	@RequestMapping("/findOldTra")
	public Object selOldTra(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
		try {
			rtnMap = this.userService.selOldTra(paramMap);
		} catch (Exception e) {
			 rtnMap.put("total", 0);
		     rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
		return rtnMap;
	}
	/**
	 * 查找历史活动
	 * @param uid
	 * @return
	 */
	@RequestMapping("/selOldAct")
	public Object selOldAct(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
		try {
			rtnMap = this.userService.selOldAct(paramMap);
		} catch (Exception e) {
			 rtnMap.put("total", 0);
		     rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
		return rtnMap;
	}
	/**
	 * 查找历史场馆
	 * @param uid
	 * @return
	 */
	@RequestMapping("/selOldVen")
	public Object selOldVen(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
		try {
			rtnMap = this.userService.selOldVen(paramMap);
		} catch (Exception e) {
			 rtnMap.put("total", 0);
		     rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
		return rtnMap;
	}
}
