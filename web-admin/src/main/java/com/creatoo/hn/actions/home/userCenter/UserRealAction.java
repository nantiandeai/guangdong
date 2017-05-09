package com.creatoo.hn.actions.home.userCenter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.services.home.userCenter.UserRealService;
import com.creatoo.hn.utils.WhConstance;

@Controller
@RequestMapping("/center")
public class UserRealAction {
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private UserRealService realService;

	/**
	 * 处理身份证图片上传
	 * @param file
	 * @param filemake
	 * @param request
	 * @return
	 */
	@RequestMapping("/uploadIdCard")
	@ResponseBody
	public Object uploadIdCard(MultipartFile file, String filemake, HttpServletRequest request){
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			HttpSession session = request.getSession(); 
			WhUser user = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			
			user = this.realService.saveUserIdCardPic(user, file, filemake, request);
			session.setAttribute(WhConstance.SESS_USER_KEY, user);
			res.put("success", true);
			res.put("msg", user);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;
	}
	
	/**
	 * 保存用户姓名和身份证号
	 * @param name
	 * @param idcard
	 * @param session
	 * @return
	 */
	@RequestMapping("/saveInfo")
	@ResponseBody
	public Object saveInfo(String name, String idcard, HttpSession session){
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			WhUser user = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			user.setName(name);
			user.setIdcard(idcard);
			user = this.realService.saveInfo(user);
			session.setAttribute(WhConstance.SESS_USER_KEY, user);
			res.put("success", true);
			res.put("msg", user);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;
	}
}
