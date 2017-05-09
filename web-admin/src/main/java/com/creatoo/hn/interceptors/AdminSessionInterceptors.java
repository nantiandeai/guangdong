package com.creatoo.hn.interceptors;

import com.creatoo.hn.actions.admin.system.AdminAction;
import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.emun.EnumDelState;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.ext.emun.EnumState;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.services.admin.system.WhgSystemUserService;
import com.creatoo.hn.utils.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 管理员控制台请求拦截
 */
public class AdminSessionInterceptors extends HandlerInterceptorAdapter{
	/**
	 * 管理员服务类
	 */
	@Autowired
	private WhgSystemUserService whgSystemUserService;

	/**
	 * 验证会话
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)	throws Exception {
		Object sessionObject = request.getSession().getAttribute("user");

		//管理员是否有会话
		if (sessionObject == null){
			String forwardName = "/pages/admin/login.jsp";
			request.getRequestDispatcher(forwardName).forward(request, response);
			return false;
		}else{
			WhgSysUser sysUser = (WhgSysUser)sessionObject;
			if(!"administrator".equals(sysUser.getAccount())){//有会话，不是超级管理员时，判断管理员是否已经停用
				sysUser = this.whgSystemUserService.t_srchOne(sysUser.getId());
				if (sysUser == null || EnumState.STATE_NO.getValue() == sysUser.getState().intValue()
						|| EnumDelState.STATE_DEL_YES.getValue() == sysUser.getDelstate().intValue()) {
					String forwardName = "/pages/admin/login.jsp";
					request.getRequestDispatcher(forwardName).forward(request, response);
					return false;
				}
			}
		}
		return true;
	}

}
