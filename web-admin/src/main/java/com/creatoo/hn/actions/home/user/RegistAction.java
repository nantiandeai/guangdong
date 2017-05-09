package com.creatoo.hn.actions.home.user;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.creatoo.hn.services.comm.SMSService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhCode;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.home.user.RegistService;
import com.creatoo.hn.utils.EmailUtil;
import com.creatoo.hn.utils.RegistRandomUtil;
import com.creatoo.hn.utils.SmsUtil;
import com.creatoo.hn.utils.WhConstance;

/**
 * 用户注册控制类
 * 
 * @author dzl
 *
 */
@Controller
public class RegistAction{
	/**
	 * 日志控制器
	 * 
	 * @return
	 */
	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private RegistService regService;
	@Autowired
	public CommService commService;

	/**
	 * 短信服务
	 */
	@Autowired
	private SMSService smsService;

	/**
	 * 注册第一步界面
	 * 
	 * @return
	 */
	@RequestMapping("/toregist")
	public String toRegistPage() {
		return "home/user/regist";
	}
	
	/**
	 * 注册第二步界面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/toregist_2")
	@ResponseBody
	public ModelAndView toRegist_2(WebRequest request) {
		ModelAndView model = new ModelAndView("home/user/regist_2");
		
	try {
			String id = request.getParameter("id");
			model.addObject("id", id);
			//判断用户id是否为空
			if(id == null || "".equals(id.trim())){
				throw new Exception();
			}
		} catch (Exception e) {
			//log.error(e.getMessage(), e);
			model.setViewName("redirect:/toregist");
		}
		
		return model;
	}

	/**
	 * 注册成功界面
	 * 
	 * @return
	 */
	@RequestMapping("/toregist_3")
	public String toRegist_3() {
		return "home/user/regist_3";
	}

    /**-------------------验证手机及其验证码-------------------------*/
	
	/**
	 * 验证手机是否已注册
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/isPhone")
	@ResponseBody
	public Object isPhone(WebRequest request,WhCode whcode) {
		String success = "0";
		String errMsg = "";
		Map<String, String>  map = new HashMap<String, String>();
		try {
			String phone = request.getParameter("phone");
			int IsPhone = this.regService.getPhone(phone);
			if (IsPhone != 0 ) {
				success = "2";
			} else {
				success = "1";
			}
		} catch (Exception e) {
			success = "3";
			errMsg = e.getMessage();
		}
		map.put("success",success);
		map.put("errMsg", errMsg);
		return map;
	}
	
	/**
	 * 输入的手机验证码是否正确
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/phoneCode")
	@ResponseBody
	public Object phoneCode(WebRequest request,HttpSession session) {
		String success = "0";
		String errMsg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//当前时间
			Date now = new Date();
			//验证码的最新时间
			Date codetime = null;
			
			//获取页面参数
			String msgcontent = request.getParameter("msgcontent");
			String msgphone = request.getParameter("msgphone");
			
			//获取验证码会话id
			String cid = (String) session.getAttribute("temp_pid");
			
			//判断页面参数是否为空
			if(msgcontent != null && !"".equals(msgcontent) && msgphone != null && !"".equals(msgphone) && cid != null && !"".equals(cid)){
				//根据邮箱验证码查找最新发送的验证码记录
				List<WhCode> codeList = this.regService.getPhoneList(msgcontent, msgphone, cid);
				//判断记录是否为空
				if(codeList != null && !(codeList.size() == 0)){
					codetime = codeList.get(0).getMsgtime();
					if(((now.getTime() - codetime.getTime())/1000) > 300){
						success = "2";	//验证码失效
					}
				}else{
					success = "1";	//验证码输入错误
				}
			}else{
				throw new Exception("未输入验证码");
			}
		} catch (Exception e) {
			errMsg = e.getMessage();
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;

	}
	
    /**-------------------验证邮箱及其验证码-------------------------*/
	
	/**
	 * 验证邮箱是否已注册
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/isEmail")
	@ResponseBody
	public Object isEmail(WebRequest request,WhCode whcode) {
		String success = "0";
		String errMsg = "";
		Map<String, String>  map = new HashMap<String, String>();
		
		try {
			String email = request.getParameter("email");
			int IsEmail = this.regService.getEmail(email);
			if (IsEmail != 0) {
				success = "2";
			} else {
				success = "1";
			}
		} catch (Exception e) {
			errMsg = e.getMessage();
		}
		map.put("success",success);
		map.put("errMsg", errMsg);
		return map;
	}
	
	/**
	 * 输入的邮箱验证码是否正确
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/emailCode")
	@ResponseBody
	public Object emailCode(WebRequest request,HttpSession session) {
		String success = "0";
		String errMsg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//当前时间
			Date now = new Date();
			//验证码的最新时间
			Date codetime = null;
			
			//获取页面参数
			String emailcode = request.getParameter("emailcode");
			String email = request.getParameter("email");
			//获取验证码会话id
			String cid = (String) session.getAttribute("temp_eid");
			
			//判断页面参数是否为空
			if(emailcode != null && !"".equals(emailcode) && email != null && !"".equals(email) && cid != null && !"".equals(cid)){
				//根据邮箱验证码查找最新发送的验证码记录
				List<WhCode> codeList = this.regService.getEmailList(emailcode, email, cid);
				//判断记录是否为空
				if(codeList != null && !(codeList.size() == 0)){
					codetime = codeList.get(0).getMsgtime();
					if(((now.getTime() - codetime.getTime())/1000) > 300){
						success = "2";	//验证码失效
					}
				}else{
					success = "1";	//验证码输入错误
				}
			}else{
				throw new Exception("未输入邮箱验证码");
			}
		} catch (Exception e) {
			errMsg = e.getMessage();
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;

	}
    /**--------------------邮箱/短信发送验证码-------------------------*/
	
	/**
	 * 邮箱验证：发送验证码（并保存邮箱地址及邮箱验证码至验证码表）
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/sendEmail")
	@ResponseBody
	public Object sendEmail(HttpSession session,WebRequest request,WhCode whcode){
		String success = "0";
		String errMsg = "";
		HashMap<String, String> map = new HashMap<String, String>();

		try {
			//用户邮箱地址
			String emailaddr = request.getParameter("email");
			
			//0.验证不能连续发送
			boolean canSend = false;
			Date now = new Date();//now
			Date emailtime = null;//preDate
			//获取发送邮箱记录
			List<WhCode> emailCodeList = this.regService.getEmailTime(emailaddr, now);
			//判断邮箱记录列表是否为空
			if(emailCodeList != null && emailCodeList.size() > 0){
				emailtime = emailCodeList.get(0).getMsgtime();
			}else{
				canSend = true;
			}
			if(emailtime != null){
				if(emailCodeList.size() >= 10){
					success = "3";	//一天最多发10次
					errMsg = "一天最多发送10次验证码";
				}else if(((now.getTime() - emailtime.getTime())/1000) < 120){
					success = "2";	//少于120秒不可发送验证码
					errMsg = "120秒后重新发送验证码";
				}else{
					canSend = true;
				}
			}
			//判断是否能发送验证码
			if(canSend){
				//生成随机验证码
				RegistRandomUtil randomUtil=new RegistRandomUtil();
				String emailcode=randomUtil.random();
			    //发送信息至邮箱
				EmailUtil.sendValidCodeEmail(emailaddr, emailcode);
				//将数据保存至code表
				String cid = this.commService.getKey("whcode");
				whcode.setId(cid);
				whcode.setEmailaddr(emailaddr);
				whcode.setEmailcode(emailcode);
				whcode.setMsgtime(new Date());
				whcode.setSessid(session.getId());
				//将验证码id存入会话
				session.setAttribute("temp_eid", cid);
				//将邮箱信息保存至数据库（验证码表）
				this.regService.saveEmail(whcode);
			}
		} catch (Exception e) {
			success = "1";
			errMsg = e.getMessage();
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;
	}
	
	/**
	 * 短信验证:发送验证码（并保存手机号码及短信验证码至验证码表）
	 */
	@RequestMapping("/user/sendPhone")
	@ResponseBody
	public Object sendPhone(WhCode whcode,WebRequest request, HttpSession session) {
		String success = "0";
		String errMsg = "";

		HashMap<String, String> map = new HashMap<String, String>();

		try {
			//获取页面参数
			String msgphone = request.getParameter("msgphone");
			//0.验证不能连续发送
			boolean canSend = false;
			Date now = new Date();
			Date phonetime = null;

			//获取手机发送记录
			if (msgphone != null && !"".equals(msgphone)) {
				List<WhCode> phoneCodeList = this.regService.getPhoneTime(msgphone, now);
				//判断手机发送记录列表是否为空
				if (phoneCodeList != null && phoneCodeList.size() > 0) {
					phonetime = phoneCodeList.get(0).getMsgtime();
				} else {
					canSend = true;
				}
				if (phonetime != null) {
					if (phoneCodeList.size() >= 10) {
						success = "3";
						errMsg = "验证码一天最多发10次";
					} else if (((now.getTime() - phonetime.getTime()) / 1000) < 120) {
						success = "2";    //少于120秒不可发送验证码
						errMsg = "120秒后重新发送验证码";
					} else {
						canSend = true;
					}
				}
			}
			//判断是否能够发送验证码
			if (canSend) {
				//1.生成随机验证码
				RegistRandomUtil randomUtil = new RegistRandomUtil();
				String msgcontent = randomUtil.random();

				//2.将短信发送至手机
				Map<String, String> smsData = new HashMap<String, String>();
				smsData.put("validCode", msgcontent);
				smsService.t_sendSMS(msgphone, "LOGIN_VALIDCODE", smsData);

				//将数据保存至code表
				String cid = this.commService.getKey("whcode");
				whcode.setId(cid);
				whcode.setSessid(session.getId());
				whcode.setMsgcontent(msgcontent);
				whcode.setMsgtime(new Date());
				whcode.setMsgphone(msgphone);
				//将验证码id存入会话
				session.setAttribute("temp_pid", cid);
				//将邮箱信息保存至数据库（验证码表）
				this.regService.savePhone(whcode);
			}
		} catch (Exception e) {
			success = "1";
			errMsg = e.getMessage();
		}

		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;
	}
	
	
    /**-------------------保存用户信息-------------------------*/
  
	/**
	 * 保存注册信息到user：第一步
	 * @return
	 */
	@RequestMapping("/user/saveRegist")
	@ResponseBody
	public Object saveRegist(WebRequest request,WhUser whuser) {
		String success = "0";
		String errMsg = "";
		String nickname = "";
		Map<String,Object>	map = new HashMap<String,Object>();
		
		try {
			//获取页面参数
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			String password = request.getParameter("password");
			
			String id = this.commService.getKey("whuser");

			if(email != null && !"".equals(email)){
				nickname = email.replaceAll("(.{2}).+(.{2}@.+)", "$1****$2");
		        whuser.setEmail(email);
			}
			if(phone != null && !"".equals(phone)){
				nickname = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
				whuser.setPhone(phone);
			}
			if(password != null && !"".equals(password)){
		        whuser.setPassword(password);
			}
			whuser.setNickname(nickname);
			whuser.setIsrealname(0);
			whuser.setIsperfect(0);
			whuser.setIsinner(0);
			whuser.setId(id);
			map.put("id", id);
			this.regService.saveRegist(whuser);
		} catch (Exception e) {
			log.error(e.getMessage());
			success = "1";
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
	@RequestMapping("/user/hasNickName")
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
				int hasNickName = this.regService.getNickName(nickname);
				if(hasNickName != 0){
					success = "1";	//已存在
				}else{
					//刷新用户会话
					WhUser userSess = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
					userSess.setNickname(nickname);
					session.setAttribute(WhConstance.SESS_USER_KEY, userSess);
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
	 * 保存注册信息到user：第二步
	 * @return
	 */
	@RequestMapping("/user/saveRegist2")
	@ResponseBody
	public Object saveRegist2(WebRequest request,WhUser whuser) {
		Map<String,Object> map = new HashMap<String,Object>();
		String success = "0";
		String errMsg = "";
		String errfield = "";

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			//获取页面参数 
			String id = request.getParameter("id");
			String nickname = request.getParameter("nickname");
			String sex = request.getParameter("sex");
			String job = request.getParameter("job");
			String birthday = request.getParameter("birthday");
			String qq = request.getParameter("qq");
			String wx = request.getParameter("wx");
			
			//日期
			Date birthday_d = null;
			
			//验证页面参数是否符合要求
			if((nickname != null && !"".equals(nickname)) &&( nickname.length() < 2 || nickname.length() > 16)){
				errfield = "nickname";
				throw new Exception("昵称为2-16位的中文或字母数字符号组合");
			}
			if(sex != null && !"0".equals(sex) && !"1".equals(sex)){
				errfield = "sex";
				throw new Exception("性别选择有误");
			}
			if(job != null && !"".equals(job) && (job.length() < 2 || job.length() > 16)){
				errfield = "job";
				throw new Exception("工作职位格式不正确");
			}
			if(birthday != null && !"".equals(birthday)){
				try {
					birthday_d = sdf.parse(birthday);
				} catch (Exception e) {
					errfield = "birthday";
					throw new Exception("出生日期格式不正确");
				}
			}
			if(qq != null && !"".equals(qq) && !qq.matches("\\d{5,13}")){
				errfield = "qq";
				throw new Exception("QQ账号格式不正确");
			}
			if(wx != null && !"".equals(wx) && !wx.matches("[^\n\r]{5,30}")){
				errfield = "wx";
				throw new Exception("微信格式不正确");
			}
			if(birthday != null && !"".equals(birthday)){
				whuser.setBirthday(birthday_d);
			}
			//判断获取的昵称是否为空
			if(nickname != null && !"".equals(nickname)){
				whuser.setNickname(nickname);
			}else{
				//根据id获得用户信息
				if(id !=null && !"".equals(id)){
					WhUser userObj= (WhUser) this.regService.getList(id);
					if(userObj != null){
						String phone = userObj.getPhone();
						//判断手机号是否为空
						if(phone != null && !"".equals(phone)){
							nickname = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
						}
						String email = userObj.getEmail();
						//判断邮箱是否为空
						if(email != null && !"".equals(email)){
							nickname = email.replaceAll("(.{2}).+(.{2}@.+)", "$1****$2");
						}
						whuser.setNickname(nickname);
					}
				}
				
			}

			whuser.setId(id);
			whuser.setSex(sex);
			whuser.setJob(job);
			whuser.setQq(qq);
			whuser.setWx(wx);
			whuser.setIsperfect(0);
			whuser.setIsrealname(0);
			whuser.setIsinner(0);
			this.regService.saveRegist2(whuser);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			success = "1";
			errMsg = e.getMessage();
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		map.put("errfield", errfield);
		return map;
	}
}
