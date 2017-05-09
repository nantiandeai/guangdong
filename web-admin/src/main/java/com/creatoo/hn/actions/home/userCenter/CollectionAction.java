package com.creatoo.hn.actions.home.userCenter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.creatoo.hn.model.WhCollection;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.home.userCenter.CollectionService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.WhConstance;

/**
 * 个人用户中心--收藏控制类
 * 
 * @author dzl
 *
 */
@Controller
public class CollectionAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private CollectionService colleService;

	@Autowired
	private CommService commService;
	
	@Autowired
	private UserCenterAction centerAction;

	/**
	 * 我的活动收藏查询
	 * 
	 * @param cmuid
	 * @return
	 */
	@RequestMapping("/center/myActColle")
	@ResponseBody
	public Object SelectMyActColle(String cmuid) {
		try {
			return this.colleService.SelectMyActColle(cmuid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 我的培训收藏查询
	 * 
	 * @param cmuid
	 * @return
	 */
	@RequestMapping("/center/myTraitmColle")
	@ResponseBody
	public Object SelectMyTraitmColle(String cmuid) {
		try {
			return this.colleService.SelectMyTraitmColle(cmuid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 添加我的收藏
	 * 
	 * @param whcolle
	 * @return
	 */
	@RequestMapping("/center/addMyColle")
	@ResponseBody
	public Object addMyColle(WhCollection whcolle) {
		try {
			whcolle.setCmid(this.commService.getKey("whcolle"));
			this.colleService.addMyColle(whcolle);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	/**
	 * 判断用户是否点亮收藏
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/comm/isLightenColle")
	@ResponseBody
	public Object isColle(HttpSession session, WebRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String success = "0";
		String errMsg = "";
		String uid = null;
		
		try {
			//取得收藏关联参数
			String reftyp = request.getParameter("reftyp"); // 收藏关联类型
			String refid = request.getParameter("refid"); // 收藏关联id
			
			//取得会话
			WhUser userSess = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			// 判断会话是否为空
			if (userSess == null) {
				success = "2";
				errMsg = "请登录后再收藏";
			} else {
				// 判断用户是否已收藏
				uid = userSess.getId(); // 获得用户id
				boolean iscolle = this.colleService.isColle(uid, reftyp, refid);
				if (iscolle) {
					success = "1"; // 已收藏
				}
			}
		} catch (Exception e) {
			success = "3";
			errMsg = e.getMessage();
		}

		map.put("errMsg", errMsg);
		map.put("success", success);
		return map;

	}

	/**
	 * 添加公共收藏
	 * 
	 * @param session
	 * @param whcolle
	 * @param request
	 * @return
	 */
	@RequestMapping("/comm/addColle")
	@ResponseBody
	public Object addColle(HttpSession session, WhCollection whcolle, WebRequest request) {
		String success = "0";
		String errMsg = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 获取存放在session的用户信息
			WhUser userSess = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			// 判断会话是否为空
			if (userSess == null) {
				success = "2";
				errMsg = "请登录后再收藏";
			} else {
				// 添加收藏
				success = "1";
				whcolle.setCmid(this.commService.getKey("whcolle"));
				whcolle.setCmuid(userSess.getId()); // 用户id
				whcolle.setCmdate(new Date()); // 用户收藏时间
				whcolle.setCmopttyp("0"); // 操作类型为收藏
				this.colleService.addMyColle(whcolle);
				this.centerAction.addNewAlert(userSess.getId(),"4");//用户中心我的收藏消息提醒
			}
		} catch (Exception e) {
			success = "3";
			errMsg = e.getMessage();
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;
	}

	/**
	 * 删除公共收藏
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/comm/removeColle")
	@ResponseBody
	public Object removeColle(HttpSession session, WebRequest request) {
		String success = "0";
		String errMsg = "";
		Map<String, Object> map = new HashMap<String, Object>();
		String uid = null; // 取得用户id

		try {
			//取得收藏关联参数
			String reftyp = request.getParameter("reftyp"); // 收藏关联类型
			String refid = request.getParameter("refid"); // 收藏关联id
			// 取得会话
			WhUser userSess = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			// 判断会话是否为空
			if (userSess == null) {
				success = "2";
				errMsg = "请登录";
			} else {
				success = "1";
				uid = userSess.getId(); 
				// 删除用户收藏记录
				this.colleService.removeCommColle(reftyp, refid, uid);
			}
		} catch (Exception e) {
			success = "3";
			errMsg = e.getMessage();
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;
	}

	/**
	 * 判断用户是否点亮点赞
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/comm/isLightenGood")
	@ResponseBody
	public Object IsGood(HttpServletRequest servletRequest,HttpSession session, WebRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String success = "0";
		String errMsg = "";
		String num = "0";
		
		try {
			//取得收藏关联参数
			String reftyp = request.getParameter("reftyp"); // 收藏关联类型
			String refid = request.getParameter("refid"); // 收藏关联id
			//获取用户id
			String uid = (String) session.getAttribute(WhConstance.SESS_USER_ID_KEY);
			//判断用户id是否为空
			if(uid == null){
				// 获取点赞ip地址
				ReqParamsUtil IP = new ReqParamsUtil();
				String dzIP = IP.gerClientIP(servletRequest);
				//判断是否有点赞记录
				boolean isgood = this.colleService.IsGood(dzIP, reftyp, refid);
				if (isgood) {
					success = "1"; // 已点赞
				}
			}else{
				//用户id不为空
				boolean isgood = this.colleService.IsGood(uid, reftyp, refid);
				if (isgood) {
					success = "1"; // 已点赞
				}
			}
			
			//设置被点赞次数
			num = this.colleService.dianZhanShu(reftyp, refid)+"";
		} catch (Exception e) {
			success = "2";
			errMsg = e.getMessage();
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		map.put("num", num);
		return map;
	}

	/**
	 * 添加点赞
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/comm/addGood")
	@ResponseBody
	public Object addGood(HttpSession session, HttpServletRequest servletRequest, WebRequest request,
			WhCollection whcolle) {
		String success = "0";
		String errMsg = "";
		// 获取点赞ip地址
		ReqParamsUtil IP = new ReqParamsUtil();
		String dzIP = IP.gerClientIP(servletRequest);

		Map<String, Object> map = new HashMap<String, Object>();
		String num = "0";
		try {
			// 取得用户id
			String uid = (String) session.getAttribute(WhConstance.SESS_USER_ID_KEY);
			// 判断用户id是否为空	 null:根据ip地址添加点赞记录	 不为null:根据用户id添加点赞记录
			if (uid == null) {
				whcolle.setCmid(this.commService.getKey("whcolle"));
				whcolle.setCmuid(dzIP);
				whcolle.setCmdate(new Date()); // 收藏时间
				whcolle.setCmopttyp("2"); // 操作类型为点赞
				this.colleService.addGood(whcolle);
			} else {
				whcolle.setCmid(this.commService.getKey("whcolle"));
				whcolle.setCmuid(uid);
				whcolle.setCmdate(new Date()); // 收藏时间
				whcolle.setCmopttyp("2"); // 操作类型为点赞
				this.colleService.addGood(whcolle);
			}
			num = this.colleService.dianZhanShu(whcolle.getCmreftyp(), whcolle.getCmrefid())+"";
		} catch (Exception e) {
			success = "1";
			errMsg = e.getMessage();
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		map.put("num", num);
		return map;
	}

	/**
	 * 删除我的收藏
	 * 
	 * @param cmid
	 * @return
	 */
	@RequestMapping("/center/removeColle")
	@ResponseBody
	public Object removeColle(String cmid) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			reMap.put("success", true);
			this.colleService.removeCollection(cmid);
		} catch (Exception e) {
			reMap.put("success", false);
		}
		return reMap;
	}

	/**
	 * 收藏页面加载数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/center/col/loadcoll")
	@ResponseBody
	public Object loadcoll(WebRequest request, HttpSession session) {
		try {
			return this.colleService.loadcoll(request, session);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
}
