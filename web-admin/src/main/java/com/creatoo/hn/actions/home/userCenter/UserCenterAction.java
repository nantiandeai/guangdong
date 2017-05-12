package com.creatoo.hn.actions.home.userCenter;

import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.model.*;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.comm.SMSService;
import com.creatoo.hn.services.home.agdindex.IndexPageService;
import com.creatoo.hn.services.home.agdwhhd.WhhdService;
import com.creatoo.hn.services.home.userCenter.UserCenterService;
import com.creatoo.hn.utils.CommUtil;
import com.creatoo.hn.utils.WhConstance;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户中心控制类
 * 
 * @author dzl
 *
 */
@Controller
@SuppressWarnings("all")
public class UserCenterAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private UserCenterService userCenterService; 
	
	@Autowired
	private CommService commService;
	
	@Autowired
	private IndexPageService indexService;

    @Autowired
    private WhhdService WhhdService;

    @Autowired
    private SMSService smsService;

	/**
	 * 用户名登录验证
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/loginpage")
	@ResponseBody
	public Object loginpage(HttpSession session, WebRequest request, HttpServletResponse response) {
		String id = "";
		String success = "";
		Map<String, Object> map = new HashMap<String, Object>();
		//ModelAndView mav = new ModelAndView();

		try {
			//获取页面数据
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			//获取用户登录方式（邮箱/手机）
			List<WhUser> userlist = (List<WhUser>) this.userCenterService.login(userName, password);
			if (userlist != null && !(userlist.size() == 0)) {
				id = userlist.get(0).getId();
				
				// 设置session有效期为30分钟
				/*String sessionId = session.getId();
				Cookie cookie = new Cookie("JSESSIONID", sessionId);
				cookie.setMaxAge(60 * 30);
				cookie.setPath(request.getContextPath());
				response.addCookie(cookie);*/
				
				session.setAttribute(WhConstance.SESS_USER_ID_KEY, id);
				session.setAttribute(WhConstance.SESS_USER_KEY, userlist.get(0));
				//重定向
				//mav.setViewName("redirect:/login");
				success = "success";
			}
		} catch (Exception e) {
			log.debug(e.getMessage(),e);
			e.printStackTrace();
		}
		map.put("id", id);
		map.put("success", success);
		return map;
	}

	/**
	 * session登录问题处理
	 *  
	 * @param session
	 * @return
	 */
	@RequestMapping("/sessionLogin")
	public ModelAndView isLogin(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		try {
			//获取用户会话id
			WhUser userSess = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			String uid = userSess.getId();
			//若会话为空，重定向回登录页面
			if(uid == null || "".equals(uid.trim())){
				mav.setViewName("redirect:/login");
			}
		} catch (Exception e) {
			log.debug(e.getMessage(),e);
		}
		return mav;
	}

	/**
	 * session退出问题处理（首页头部）
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/sessionExit")
	public ModelAndView IsExit(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		try {
			 //获取用户会话
			WhUser userSess = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			 //判断用户会话是否存在
			 if(userSess != null){
				 //从sesssion中移除用户登陆状态
				 session.removeAttribute(WhConstance.SESS_USER_KEY);
			 }
		} catch (Exception e) {
			log.debug(e.getMessage(),e);
		}
        //重定向回首页
        mav.setViewName("redirect:/home");
		return mav;
	}
	
	/**-----------------------------找回密码开始--------------------------------*/
	
	/**
	 * 找回密码--界面
	 * @return
	 */
	@RequestMapping("/user/tofindPwd")
	public ModelAndView toFindPwd(){
		ModelAndView model = new ModelAndView("/home/user/findPwd");
		return model;
	}
	
	/**
	 * 查找手机邮箱信息（用户表）（第一步）
	 * @param request
	 * @param whuser
	 * @return
	 */
	@RequestMapping("/user/selectFindPwd")
	@ResponseBody
	public Object selectFindPwd(WebRequest request,WhUser whuser){
		String success = "0";
		String errMsg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		String id = "";
		try {
			//获取页面参数
			String phone = request.getParameter("phone");
			//判断页面参数phone 是否为空
			if(phone != null && !"".equals(phone)){
				//根据手机号码获得用户信息
				List<WhUser> phoneList = this.userCenterService.getPhoneList(phone);
				if(phoneList != null){
					id = phoneList.get(0).getId();
				}
			}
			//获取页面参数
			String email = request.getParameter("email");
			//判断页面参数phone 是否为空
			if(email != null && !"".equals(email)){
				//用过邮箱获得用户信息
				List<WhUser> emailList = this.userCenterService.getEmailList(email);
				if(emailList != null){
					id = emailList.get(0).getId();
				}
			}
			map.put("id", id);
		} catch (Exception e) {
			success = "1";
			errMsg = e.getMessage();
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;
	}
	
	/**
	 * 找回密码第二步--界面(验证信息)
	 * @return
	 */
	@RequestMapping("/user/findPwd2")
	public ModelAndView findPwd2(String email,String phone){
		ModelAndView model = new ModelAndView("home/user/findPwd2");
		try {
			//判断邮箱地址是否为空
			if(email != null && !"".equals(email)){
				model.addObject("email", email);
			}
			//判断手机号码是否为空
			if(phone != null && !"".equals(phone)){
				model.addObject("phone", phone);
			}
		} catch (Exception e){
			log.debug(e.getMessage());
		}
		return model;
	}
	
	/**
	 * 保存修改密码（手机、邮箱）信息至验证码表（第二步）
	 * @return
	 */
	@RequestMapping("/user/saveFindPwd2")
	@ResponseBody
	public Object saveFindPwd2(WhCode whcode, WebRequest request, HttpSession session){
		String success = "0";
		String errMsg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		String uid = "";
		try {
			//获取页面参数
			String email = request.getParameter("email");
			String emailCode = request.getParameter("emailCode");
			String phone = request.getParameter("phone");
			String msgcontent = request.getParameter("msgcontent");
			//保存数据至验证码表
			String id = this.commService.getKey("whcode");
			whcode.setId(id);
			//判断邮箱验证码是否为空，并保存信息
			if(emailCode != null &&  !"".equals(emailCode)){
				whcode.setEmailcode(emailCode);
			}
			//判断手机验证码是否为空，并保存信息
			if(msgcontent != null &&  !"".equals(msgcontent)){
				whcode.setMsgcontent(msgcontent);
			}
			//判 断邮箱是否为空，并保存信息
			if(email !=null && !"".equals(email)){
				whcode.setEmailaddr(email);
				//通过邮箱获得用户信息
				List<WhUser> emailList = this.userCenterService.getEmailList(email);
				if(emailList != null){
					 uid = emailList.get(0).getId();
				}
			}
			//判断手机号码是否为空，并保存信息
			if(phone != null && !"".equals(phone)){
				whcode.setMsgphone(phone);
				//根据手机号码获得用户信息
				List<WhUser> phoneList = this.userCenterService.getPhoneList(phone);
				if(phoneList != null){
					 uid = phoneList.get(0).getId();
				}
			}
			this.userCenterService.saveFindPwd2(whcode);
			//map.put("uid",uid);
			session.setAttribute("tmp_uid", uid);
		} catch (Exception e) {
			success = "1";
			errMsg = e.getMessage();
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;
	}
	
	/**
	 * 找回密码(第三步)
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/findPwd")
	@ResponseBody
	public Object findPwd(WebRequest request, HttpSession session){
		String success = "0";
		String errMsg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		
		try {
			String uid = (String)session.getAttribute("tmp_uid");
			//获取页面参数
			String password = request.getParameter("pwdMd5");
			//根据id获得用户信息
			WhUser whuser = (WhUser) this.userCenterService.getList(uid);
			//修改用户密码
			whuser.setPassword(password);
			this.userCenterService.modifyPwd(whuser);
		} catch (Exception e) {
			success = "1";
			errMsg = e.getMessage();
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;
	}
	
	/**
	 * 找回密码成功--界面
	 * @return
	 */
	@RequestMapping("/user/findPwd3")
	public ModelAndView findPwd3(HttpSession session, WebRequest request){
		ModelAndView model = new ModelAndView("/home/user/findPwd3");
		try {
			String uid = (String)session.getAttribute("tmp_uid");
			//判断用户id是否为空
			if(uid == null || "".equals(uid.trim())){
				model.setViewName("redirect:/login");
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return model;
	}
	
	/**-----------------------------找回密码结束--------------------------------*/


	/**
	 * 登录页面
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public ModelAndView loginPage(HttpSession session, WebRequest request) {
		ModelAndView view = new ModelAndView("/login");
		try {
			//获取前序地址
			String retUrl = request.getHeader("Referer");   
			if(retUrl != null){
				//判断前序地址是否为找回密码路径
				if(retUrl.indexOf("/findPwd3") > -1){
				}else{
					view.addObject("preurl", retUrl);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return view;
	}
	
	/**-----------------------------个人中心开始--------------------------------*/
	
	/**
	 * 个人中心添加新消息
	 * @return
	 */
	@RequestMapping("/ajax/addNewAlert")
	@ResponseBody
	public Object addNewAlert(String refuid,String reftype){
		String success = "0";
		String errMsg="";
		Map<String,Object> map = new HashMap<String,Object>();
		WhUserAlerts userAlert = new WhUserAlerts();
		try {
			if(refuid != null && !"".equals(refuid)){
				userAlert.setRefuid(refuid);
			}
			if(reftype != null && !"".equals(reftype)){
				userAlert.setReftype(reftype);
			}
			String aleid = this.commService.getKey("whuseralerts");
			userAlert.setAleid(aleid);
			this.userCenterService.addNewAlert(userAlert);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;
	}
	/**
	 * 个人中心消息提醒
	 * @param session
	 * @return
	 */
	@RequestMapping("/center/alerts")
	@ResponseBody
	public Object alert(HttpSession session,WebRequest request){
		String success = "0";
		String errMsg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//获取用户会话id
			WhUser whuser = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			String uid = whuser.getId();
			
			//判断用户id是否为空
			if(uid != null && !"".equals(uid)){
				List<WhUserAlerts> alertList = (List<WhUserAlerts>) this.userCenterService.selectMsgAlert(uid);
				map.put("alertList", alertList);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;
	}
	
	/**
	 * 去除消息提示
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/center/delAlert")
	@ResponseBody
	public Object delAlert(HttpSession session,WebRequest request){
		String success = "0";
		String errMsg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//获取用户会话id
			WhUser whuser = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			String refuid = whuser.getId();
			//获取页面参数
			String reftype = request.getParameter("reftype");

			if(refuid != null && !"".equals(refuid)){
				 this.userCenterService.delMsgAlert(refuid,reftype);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;
	}
	
	/**
	 * 个人中心头部消息提醒
	 * @param session
	 * @return
	 */
	@RequestMapping("/center/alertHeader")
	@ResponseBody
	public Object alertHeader(HttpSession session){
		String success = "0";
		String errMsg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//获取用户会话id
			WhUser whuser = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);

			String uid = whuser.getId();
			if(uid != null && !"".equals(uid)){
				int msgCount = this.userCenterService.selectMsgHeader(uid);
				map.put("msgCount", msgCount);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;
	}
	/**
	 * 个人用户中心界面
	 * 
	 * @return
	 */
	@RequestMapping("/center/userCenter")
	public String userCenter(HttpSession session, String id) {
		try {
			isLogin(session);
		} catch (Exception e) {
			log.debug(e.getMessage(),e);
			e.printStackTrace();
		}
		return "home/center/userCenter";
	}
	
	/**
	 * 个人中心--公共绑定部分
 	 * @param session
	 * @return
	 */
	@RequestMapping("/center/commBind")
	@ResponseBody
	public Object commBind(HttpSession session){
		String success = "0";
		String errMsg = "";
		String s1 = "";
		String s2 = "";
		String s3 = "";
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//获取用户会话
			WhUser userSess = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			//判断用户会话是否为空
			if(userSess != null){
				//获取用户手机号码
				String	phone = userSess.getPhone();
				//判断手机号是否为空
				if(phone != null && !"".equals(phone)){
					s1 = "1";	//点亮手机绑定
				}
				//获取用户邮箱账号
				String	email = userSess.getEmail();
				//判断邮箱是否为空
				if(email != null && !"".equals(email)){
					s2 = "2";	//点亮邮箱绑定
				}
				//获取用户实名信息
				Integer	isRealName = userSess.getIsrealname();
				//判断实名信息是否为空
				if(isRealName != null && !"".equals(isRealName)){
					if(isRealName == 1)
					s3 = "3";	//点亮实名绑定
				}
			}
		} catch (Exception e) {
			success = "1";
			errMsg = e.getMessage();
		}
		map.put("s1", s1);
		map.put("s2", s2);
		map.put("s3", s3);
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;
	}
	
	/**--------------------基本信息开始---------------------*/

	/**
	 * 基本信息--界面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/center/userInfo")
	public ModelAndView userInfo(HttpSession session, WebRequest request) {
		ModelAndView model = new ModelAndView("home/center/userInfo");

		try {
			//是否有登录会话
			isLogin(session);
			//获取前序路径
			String retUrl = request.getHeader("Referer");   
			if(retUrl != null){   
				model.addObject("preurl", retUrl);  
			}
			//获取存在session中的用户id
			WhUser userSess = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			String id = userSess.getId();
			WhUser user = (WhUser) this.userCenterService.getList(id);
			//获取出生日期
			Date birthday = user.getBirthday();
			//判断日期是否为空
			model.addObject("birthday",birthday);
			model.addObject("id", user.getId());
			model.addObject("nickname", user.getNickname());
			model.addObject("sex", user.getSex());
			model.addObject("nation", user.getNation());
			model.addObject("origo", user.getOrigo());
			model.addObject("qq", user.getQq());
			model.addObject("wx", user.getWx());
			model.addObject("job", user.getJob());
			model.addObject("company", user.getCompany());
			model.addObject("phone", user.getPhone());
			model.addObject("email",user.getEmail());
			model.addObject("address", user.getAddress());
			model.addObject("resume", user.getResume());
			model.addObject("actbrief", user.getActbrief());
		} catch (Exception e) {
			log.debug(e.getMessage(),e);
			e.printStackTrace();
		}
		return model;
	}
	
	/**
	 * 判断用户是否完善资料
	 * @param session
	 * @return
	 */
	@RequestMapping("/center/IsPerfect")
	@ResponseBody
	public Object IsPerfect(HttpSession session){
		String success = "0";
		String errMsg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//获取用户会话
			WhUser userSess = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			//获取会话中的用户是否完善资料信息
			Integer isperfect = userSess.getIsperfect();
			if(isperfect != null && !"".equals(isperfect)){
				if(isperfect == 1){
					success = "1";	//已完善资料
				}
			}else{
				success = "2";	//完善资料为空：默认为未完善资料
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			errMsg = e.getMessage();
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;
	}

	/**
	 * 判断昵称是否已存在
	 * @return
	 */
	@RequestMapping("/center/hasNickName")
	@ResponseBody
	public Object hasNickName(WebRequest request,HttpSession session){
		String success = "0";
		String errMsg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		
		try {
			//获取昵称
			String nickname = request.getParameter("nickname");
			if(nickname != null && !"".equals(nickname)){
				//判断昵称是否已存在
				int hasNickName = this.userCenterService.getNickName(nickname);
				//获取用户会话
				WhUser userSess = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
				String sessNickName = userSess.getNickname();
				//判断页面获取的昵称与会话中的昵称是否相同
				if(sessNickName != null && !"".equals(sessNickName) && !sessNickName.equals(nickname)){
					if(hasNickName != 0){
						success = "1";
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;

	}

	/**
	 * 修改用户信息
	 * @return
	 */
	@RequestMapping("/center/modifyUser")
	@ResponseBody
	public Object modifyUser(HttpSession session,WebRequest request, WhUser whuser) {
		String success = "0";
		String errMsg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			whuser.setIsperfect(1);
			//修改用户信息
			this.userCenterService.modifyUser(whuser);
			//reset 用户会话
			WhUser userSess = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			userSess.setIsperfect(1);
			session.setAttribute(WhConstance.SESS_USER_KEY, userSess);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;
	}
	
	/**--------------------基本信息结束---------------------*/

	/**
	 * 我的订单--界面
	 * 
	 * @return
	 */
	@RequestMapping("/center/order")
	public String order(HttpSession session) {
		try {
			isLogin(session);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return "home/center/order";
	}

	/**
	 * 我的活动--界面
	 * 
	 * @return
	 */
	@RequestMapping("/center/activity")
	public String activity(HttpSession session) {
		try {
			isLogin(session);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return "home/center/activity";
	}

	@ResponseBody
	@RequestMapping(value = "/center/getMyActList")
	public Object getMyActList(HttpServletRequest request){
		ResponseBean res = new ResponseBean();
		String type = request.getParameter("type");
		if(null == type || !CommUtil.isInteger(type)){
			type = "1";
		}
		String page = request.getParameter("page");
		if(null == page || !CommUtil.isInteger(page)){
			page = "1";
		}
		String rows = request.getParameter("rows");
		if(null == rows || !CommUtil.isInteger(rows)){
			rows = "5";
		}
		HttpSession session = request.getSession();
		WhUser userSess = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
		PageInfo pageInfo = this.userCenterService.getOrderForCenter(Integer.valueOf(page),Integer.valueOf(rows),Integer.valueOf(type),userSess.getId());
		res.setRows(pageInfo.getList());
		res.setTotal(pageInfo.getTotal());
		return  res;
	}
	
	/**
	 * 取消订单
	 * @param request
	 * @param orderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value= "/center/cancelActOrder")
	public ResponseBean cancelActOrder(HttpServletRequest request,String orderId){
		ResponseBean res = new ResponseBean();
		WhgActOrder actOrder = userCenterService.findOrderDetail(orderId);
		actOrder.setTicketstatus(3);
		actOrder.setOrderisvalid(2);
		userCenterService.upActOrder(actOrder);

        //发送取消短信
        try {
            WhgActActivity act = WhhdService.getActDetail(actOrder.getActivityid());
            Map<String, String> smsData = new HashMap<String, String>();
            smsData.put("userName", actOrder.getOrdername());
            smsData.put("activityName", act.getName());
            smsData.put("ordernumber", actOrder.getOrdernumber());
            smsService.t_sendSMS(actOrder.getOrderphoneno(), "ACT_UNORDER", smsData);
        } catch (Exception e) {
            log.error("取消活动订单短信发送失败", e);
        }

        return res;
	}

	/**
	 * 我的课程--界面
	 * 
	 * @return
	 */
	@RequestMapping("/center/curriculum")
	public String curricuclum(HttpSession session) {
		try {
			isLogin(session);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return "home/center/curriculum";
	}
	
	/**
	 * 我的课程--已报名界面
	 * 
	 * @return
	 */
	@RequestMapping("/center/curriculumReady")
	public String curricuclumReady(HttpSession session) {
		try {
			isLogin(session);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return "home/center/curriculumReady";
	}
	
	/**
	 * 我的课程--审核界面
	 * 
	 * @return
	 */
	@RequestMapping("/center/curriculumExamine")
	public String curricuclumExamine(HttpSession session) {
		try {
			isLogin(session);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return "home/center/curriculumExamine";
	}
	
	/**
	 * 我的收藏--界面
	 * 
	 * @return
	 */
	@RequestMapping("/center/favorite")
	public String favorite(HttpSession session) {
		try {
			isLogin(session);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return "home/center/favorite";
	}

	/**
	 * 我的点评--界面
	 * 
	 * @return
	 */
	@RequestMapping("/center/comment")
	public String comment(HttpSession session) {
		try {
			isLogin(session);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return "home/center/comment";
	}

	/**
	 * 我的消息--界面
	 * 
	 * @return
	 */
	@RequestMapping("/center/message")
	public String message(HttpSession session) {
		try {
			isLogin(session);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return "home/center/message";
	}
	
	/**--------------------安全设置开始---------------------*/
	
	/**
	 * 安全设置--界面
	 * 
	 * @return
	 */
	@RequestMapping("/center/safely")
	public ModelAndView safely(HttpSession session,WebRequest request) {
		ModelAndView mav = new ModelAndView("home/center/safely");
		try {
			isLogin(session);
			//获取会话
			WhUser userSess = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			String phone = userSess.getPhone();
			String email = userSess.getEmail();
			//判断手机与邮箱均为空
			if((phone == null || "".equals(phone)) && (email == null || "".equals(email))){
				//获取前序地址
				String retUrl = request.getHeader("Referer");   
				mav.addObject("preurl", retUrl);
			}
			mav.addObject("phone",phone);
			mav.addObject("email",email);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return mav;
	}
	
	/**
	 * 判断用户是否绑定手机/邮箱
	 * @param session
	 * @return
	 */
	@RequestMapping("/center/isBind")
	@ResponseBody
	public Object isBind(HttpSession session){
		String success = "0";
		String s1 = "";
		String s2 = "";
		String s3 = "";
		String s4 = "";
		String errMsg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//获取用户会话
			WhUser userSess = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			//判断会话是否为空
			if(userSess != null){
				userSess = (WhUser)this.userCenterService.getList(userSess.getId());


				//获取用户的手机号码
				String phone = userSess.getPhone();
				if(phone != null && !"".equals(phone)){
					s1 = "1";	//设置手机为已绑定
				}
				//获取用户的邮箱
				String email = userSess.getEmail();
				if(email != null && !"".equals(email)){
					s2 = "2";	//设置邮箱为已绑定	
				}
				//获取用户登录密码
				String password = userSess.getPassword();
				if(password != null && !"".equals(password)){
					s3 = "3";	//已设置登录密码
				}
				
				//获取用户是否已实名
				Integer isRealName = userSess.getIsrealname();
				if(isRealName != null && !"".equals(isRealName)){
					if(isRealName == 1){
						s4 = "4";	//设置为已实名
					}else{
						s4 = isRealName+"";
					}
				}else{
					s4 = isRealName+"";
				}
			}
		} catch (Exception e) {
			errMsg = e.getMessage();
			e.printStackTrace();
		}
		map.put("success", success);
		map.put("s1", s1);
		map.put("s2", s2);
		map.put("s3", s3);
		map.put("s4", s4);
		map.put("errMsg", errMsg);
		return map;
	}
	
	/**
	 * 是否有密码会话
	 * @return
	 */
	@RequestMapping("/center/hasSessPwd")
	@ResponseBody
	public Object hasSessPwd(HttpSession session){
		String success = "0";
		String errMsg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//获取用户会话
			WhUser userSess = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			//判断用户会话是否为空
			if(userSess != null){
				String sessPwd = userSess.getPassword();
				//判断密码是否为空
				if(sessPwd != null && !"".equals(sessPwd)){
					success = "1";	//已设置密码
				}else{
					success = "2";
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			errMsg = e.getMessage();
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;
	}
	
	/**
	 * 已绑定的邮箱--界面
	 * 
	 * @return
	 */
	@RequestMapping("/center/safely-email")
	public ModelAndView safely_email(HttpSession session) {
		ModelAndView mav = new ModelAndView("home/center/safelyEmail");
		try {
			isLogin(session);
			//获取用户会话
			WhUser userSess = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			//判断用户会话是否为空
			if(userSess != null){
				String email = userSess.getEmail();
				if(email != null && !"".equals(email)){
					mav.addObject("email",email.replaceAll("(.{2}).+(.{2}@.+)", "$1****$2"));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return mav;
	}
	/**
	 * 是否有邮箱会话
	 * @return
	 */
	@RequestMapping("/center/hasSessEmail")
	@ResponseBody
	public Object hasSessEmail(HttpSession session){
		String success = "0";
		String errMsg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//获取用户会话
			WhUser userSess = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			//判断用户会话是否为空
			if(userSess != null){
				String email = userSess.getEmail();
				//判断邮箱是否为空
				if(email != null && !"".equals(email)){
					success = "1";	//已绑定邮箱
				}else{
					success = "2";
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			errMsg = e.getMessage();
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;
	}
	
	/**
	 * 判断原始邮箱
	 * @param request
	 * @return
	 */
	@RequestMapping("/center/isPreEmail")
	@ResponseBody
	public Object isPreEmail(WebRequest request,HttpSession session) {
		String success = "0";
		String errMsg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			WhUser user = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			String email = user.getEmail(); 
			String preEmailAddr = request.getParameter("preEmailAddr");
			if(preEmailAddr != null && email.equals(preEmailAddr)){
				success = "1";	//与原始邮箱匹配
			}else{
				success = "2";	//与原始邮箱不匹配
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;
	}
	
	/**
	 * 绑定邮箱
	 * @param session
	 * @param email
	 * @return
	 */
	@RequestMapping("/center/modifyEmail")
	@ResponseBody
	public Object modifyEmail(HttpSession session,String email){
		String success = "0";
		String errMsg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			WhUser userSess = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			userSess.setEmail(email);
			this.userCenterService.modifyUser(userSess);
		} catch (Exception e) {
			success = "1";
			errMsg = e.getMessage();
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;
	}
	/**
	 * 已绑定的手机--界面
	 * 
	 * @return
	 */
	@RequestMapping("/center/safely-phone")
	public ModelAndView safely_phone(HttpSession session) {
		ModelAndView mav = new ModelAndView("home/center/safelyPhone");
		try {
			isLogin(session);
			//获取用户会话
			WhUser userSess = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			//判断用户会话是否为空
			if(userSess != null){
				String phone = userSess.getPhone();
				if(phone != null && !"".equals(phone)){
					mav.addObject("phone",phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return mav;
	}
    
	/**
	 * 是否有手机会话
	 * @return
	 */
	@RequestMapping("/center/hasSessPhone")
	@ResponseBody
	public Object hasSessPhone(HttpSession session){
		String success = "0";
		String errMsg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//获取用户会话
			WhUser userSess = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			//判断用户会话是否为空
			if(userSess != null){
				String phone = userSess.getPhone();
				//判断邮箱是否为空
				if(phone != null && !"".equals(phone)){
					success = "1";	//已绑定手机
				}else{
					success = "2";	//未绑定
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			errMsg = e.getMessage();
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;
	}
	
	/**
	 * 判断原始手机
	 * @param request
	 * @return
	 */
	@RequestMapping("/center/isPrePhone")
	@ResponseBody
	public Object isPrePhone(WebRequest request,HttpSession session) {
		String success = "0";
		String errMsg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			WhUser user = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			String phone = user.getPhone(); 
			String preMsgPhone = request.getParameter("preMsgPhone");
			if(preMsgPhone != null && phone.equals(preMsgPhone)){
				success = "1";	//与原始手机匹配
			}else{
				success = "2";	//与原始手机不匹配
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;
	}
	
	/**
	 * 绑定手机号码
	 * @param phone
	 * @return
	 */
	@RequestMapping("/center/modifyPhone")
	@ResponseBody
	public Object modifyPhone(HttpSession session,String phone){
		String success = "0";
		String errMsg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//获取用户会话
			WhUser whuser=(WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			whuser.setPhone(phone);
		    this.userCenterService.modifyPhone(whuser);
		} catch (Exception e) {
			errMsg = e.getMessage();
			e.printStackTrace();
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;
	}
	
	/**
	 * 已实名验证--界面
	 * 
	 * @return
	 */
	@RequestMapping("/center/safely-userReal")
	public String safely_userReal(HttpSession session) {
		try {
			isLogin(session);
			WhUser userSess = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			//重新写会话
			session.setAttribute(WhConstance.SESS_USER_KEY, this.userCenterService.getList(userSess.getId()));
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return "home/center/safelyUserReal";
	}

	/**
	 * 设置登录密码--界面
	 * 
	 * @return
	 */
	@RequestMapping("/center/safely-pwd")
	public String safely_pwd(HttpSession session) {
		try {
			isLogin(session);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return "home/center/safelyPwd";
	}
	
	/**
	 * 判断原始密码
	 * @param request
	 * @return
	 */
	@RequestMapping("/center/isPwd")
	@ResponseBody
	public String isPwd(WebRequest request,HttpSession session) {
		WhUser user=(WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
		String pwd=user.getPassword();
		String prePwd = request.getParameter("password");
		if(prePwd != null && pwd.equals(prePwd)){
			return "success";
		}else{
			return "error";
		}

	}
	
	/**
	 * 修改用户密码
	 * @return
	 */
	@RequestMapping("/center/modifyPwd")
	@ResponseBody
	public Object modifyPwd(HttpSession session,WebRequest request,WhUser whuser){
		String success = "0";
		String errMsg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//获取页面数据
			String newPwdMd5 = request.getParameter("newPwdMd5");
			//获取用户会话
			whuser = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			whuser.setPassword(newPwdMd5);
			this.userCenterService.modifyPwd(whuser);
		} catch (Exception e) {
			errMsg = e.getMessage();
			e.printStackTrace();
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;
	}
	
	/**--------------------安全设置结束---------------------*/

	/**
	 * 历史订单--界面
	 * 
	 * @return
	 */
	@RequestMapping("/center/order-history")
	public String safely_history(HttpSession session) {
		try {
			isLogin(session);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return "home/center/safelyHistory";
	}

	/**
	 * 审核订单 --界面
	 * 
	 * @return
	 */
	@RequestMapping("/center/order-examine")
	public String safely_examine(HttpSession session) {
		try {
			isLogin(session);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return "home/center/safelyExamine";
	}

	/**
	 * 我的活动--已报名界面
	 * 
	 * @return
	 */
	@RequestMapping("/center/activity-history")
	public String activity_history(HttpSession session) {
		try {
			isLogin(session);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return "home/center/activityHistory";
	}
	
	/**
	 * 我的活动--审核界面
	 * 
	 * @return
	 */
	@RequestMapping("/center/activity-examine")
	public String activity_examine(HttpSession session) {
		try {
			isLogin(session);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return "home/center/activityExamine";
	}
	
	/**-----------------------------个人中心结束--------------------------------*/

}
