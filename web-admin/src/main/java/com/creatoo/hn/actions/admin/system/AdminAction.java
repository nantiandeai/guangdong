package com.creatoo.hn.actions.admin.system;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.emun.EnumDelState;
import com.creatoo.hn.ext.emun.EnumLBTClazz;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.ext.emun.EnumState;
import com.creatoo.hn.interceptors.AdminSessionInterceptors;
import com.creatoo.hn.model.WhgAdminHome;
import com.creatoo.hn.model.WhMenu;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.services.admin.system.AdminService;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.lang.model.element.Name;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author wangxl
 *
 */
@RestController
@RequestMapping("/admin")
public class AdminAction {
	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private AdminService adminService;

	/**
	 * 请求映射
	 */
	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	/**
	 * 用于获取所有请求映射
	 * @return
	 */
	public List<HashMap<String, Object>> initRequestMappingMethod() {
		List<HashMap<String, Object>> urlList = new ArrayList<HashMap<String, Object>>();
		Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
		for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			RequestMappingInfo info = m.getKey();
			HandlerMethod method = m.getValue();
			PatternsRequestCondition p = info.getPatternsCondition();
			for (String url : p.getPatterns()) {
				hashMap.put("url", url);
			}
			hashMap.put("className", method.getMethod().getDeclaringClass().getName()); // 类名
			hashMap.put("class", method.getMethod()); // class
			hashMap.put("method", method.getMethod().getName()); // 方法名
			RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
			String type = methodsCondition.toString();
			if (type != null && type.startsWith("[") && type.endsWith("]")) {
				type = type.substring(1, type.length() - 1);
				hashMap.put("type", type); // 方法名
			}
			urlList.add(hashMap);
		}
		return urlList;
	}

	/**进入后台首页
	 * @return
	 */
	@RequestMapping("")
	public ModelAndView index(){
		return new ModelAndView( "admin/main" );
	}

    /**进入后台首页内容页
     * @return
     */
    @RequestMapping("/admin_home")
	public ModelAndView adminHome() {
		ModelAndView view = new ModelAndView("admin/admin_home");

		try {

			view.addObject("inCount", adminService.t_srchList());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}

	/**处理管理员登录
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/logindo")
	public ModelAndView logindo(HttpSession session, WebRequest request){
		ModelAndView mav = new ModelAndView();

		try {
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			Object admin = this.adminService.logindo(name, password);

			if (admin != null){
				session.setAttribute("user", admin);
				mav.setViewName("redirect:/admin");
			}else{
				mav.addObject("msg", "用户名或密码不正确");
				mav.setViewName("admin/login");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			mav.addObject("msg", "用户名或密码不正确");
			mav.setViewName("admin/login");
		}

		return mav;
	}


	/**处理管理员登出
	 * @param session
	 * @return
	 */
	@RequestMapping("/loginout")
	public ModelAndView loginout(HttpSession session){
		ModelAndView mav = new ModelAndView();

		try {
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.getSession().removeAttribute("user");
			currentUser.logout();

			session.removeAttribute("user");
			mav.setViewName("admin/login");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			mav.setViewName("admin/login");
		}
		return mav;
	}

	/**进入管理菜单界面
	 * @return
	 */
	@RequestMapping("/menulist")
	public ModelAndView toMenuList(){
		return new ModelAndView("admin/system/menulist");
	}

	/**加载菜单树型数据
	 * @return
	 */
	@RequestMapping("/loadMenus")
	public Object getMenuData(String type){
		try {
			return this.adminService.getMenuList(type);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return null;
		}
	}


	/**获取父级菜单可选项
	 * @return
	 */
	@RequestMapping("/loadParentList")
	public Object getParentList(){
		try {
			return this.adminService.getMenuParent();
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return null;
		}
	}

	/**添加菜单记录
	 * @param menu
	 * @return
	 */
	@RequestMapping("/addMenuItem")
	public Object addMenuItem(WhMenu menu){
		try {
			this.adminService.addMenuItem(menu);
			return "success";
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return "error";
		}
	}

	/**编辑菜单项信息
	 * @param menu
	 * @return
	 */
	@RequestMapping("/editMenuItem")
	public Object editMenuItem(WhMenu menu){
		try {
			this.adminService.editMenuItem(menu);
			return "success";
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return "error";
		}
	}

	/**删除菜单项信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/removeMenuItem")
	public Object removeMenuItem(String id){
		try {
			this.adminService.removeMenuItem(id);
			return "success";
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return "error";
		}
	}
	/**
	 * 修改密码
	 */
    @RequestMapping("/modipasManage")
    public Object upmodifyPwd(HttpSession session, String password1, String password2, HttpServletRequest request) {
        String success = "0";
        String errmasg = "";
        try {
            //得到session值 向下类型转换
            WhgSysUser user = (WhgSysUser) session.getAttribute("user");
            String account = user.getAccount();
            String pwd = user.getPassword();
            //判断用户不是超级管理员
            if (account != "administrator") {
                if (pwd.equals(password1)) {
                	String password4 = request.getParameter("password4");
                    this.adminService.selectmagr(account, password2, password4);
                } else {
                    success = "1";
                    errmasg = "密码错误";
                }
            }
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
	 * 查询列表
	 *
	 * @return 。
	 */
	@RequestMapping(value = "/srchList")
	public List<WhgAdminHome> srchList() {
		List<WhgAdminHome> list = new ArrayList<>();
		try {
			 list = this.adminService.t_srchList();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}

	/**
	 * 首页统计（培训根据文化馆分类）
	 *
	 * @return 。
	 */
	@RequestMapping(value = "/traGroupByCult")
	public List<WhgAdminHome> traGroupByCult() {
		List<WhgAdminHome> list = new ArrayList<>();
		try {
			list = this.adminService.traGroupByCult();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}

	/**
	 * 首页统计（培训根据艺术类型分类）
	 *
	 * @return 。
	 */
	@RequestMapping(value = "/traGroupByArt")
	public List<WhgAdminHome> traGroupByArt() {
		List<WhgAdminHome> list = new ArrayList<>();
		try {
			list = this.adminService.traGroupByArt();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}

	/**
	 * 首页统计（培训根据艺术类型分类）
	 *
	 * @return 。
	 */
	@RequestMapping(value = "/actGroupByArt")
	public List<WhgAdminHome> actGroupByArt() {
		List<WhgAdminHome> list = new ArrayList<>();
		try {
			list = this.adminService.actGroupByArt();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
}
