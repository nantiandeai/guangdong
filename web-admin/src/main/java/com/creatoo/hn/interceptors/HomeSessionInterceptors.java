package com.creatoo.hn.interceptors;

import com.creatoo.hn.utils.WhConstance;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeSessionInterceptors extends HandlerInterceptorAdapter{



	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Object sessionObject = request.getSession().getAttribute(WhConstance.SESS_USER_KEY);
		if (sessionObject == null){
			response.sendRedirect(request.getContextPath()+"/login");
			return false;
		}
		return super.preHandle(request, response, handler);
	}

}
