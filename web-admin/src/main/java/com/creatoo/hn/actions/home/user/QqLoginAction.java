package com.creatoo.hn.actions.home.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.home.user.RegistService;
import com.creatoo.hn.utils.WhConstance;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;

/**
 * QQ三方登陆控制层
 * @author dzl
 *
 */
@Controller
public class QqLoginAction {
	/*
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public CommService commService;
	
	@Autowired
	public RegistService regService;
	
	/**
	 * QQ跳转至授权页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/user/authorizePage")
	@ResponseBody
	public Object authorizePage(HttpServletRequest request,HttpServletResponse response){
		String success = "0";
		String errMsg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		try {	
				//跳转至授权界面
				response.sendRedirect(new Oauth().getAuthorizeURL(request));
			} catch (IOException e) {
				errMsg = e.getMessage();
			} catch (QQConnectException e) {
				errMsg = e.getMessage();
			}
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;
	}
	
	/**
	 * QQ授权登录后页面的跳转
	 * @return
	 */
	@RequestMapping("/user/afterloginPage")
	public ModelAndView afterLoginRedirect(HttpSession session,HttpServletRequest request){
		String success = "0";
		String errMsg = "";
		String nickname = "";
		String sex = "";
		String failMsg = "";
		ModelAndView mav = new ModelAndView("/index");
		Map<String,Object> map = new HashMap<String,Object>();
		WhUser whuser = new WhUser();

		try {
			
			//获取前序地址
			String retUrl = request.getHeader("Referer");   
			if(retUrl != null){
				//判断前序地址是否为找回密码路径
				if(retUrl.indexOf("/findPwd3") > -1){
				}else{
					mav.addObject("preurl", retUrl);
				}
			}
			//资源访问凭证
		    AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
		    String accessToken   = null,
		           openID        = null;
		    long tokenExpireIn = 0L;
		    
		    if (accessTokenObj.getAccessToken().equals("")) {
				//我们的网站被CSRF攻击了或者用户取消了授权
				//做一些数据统计工作
		    	log.error("没有获取到响应参数");	
		    } else {
			    accessToken = accessTokenObj.getAccessToken();
			    //获取访问令牌accessToken的单位：秒
			    tokenExpireIn = accessTokenObj.getExpireIn();
			    //将获取的信息存入会话
			    session.setAttribute(WhConstance.SESS_QQ_ACCESS, accessToken);
			    session.setAttribute(WhConstance.SESS_QQ_EXPIREIN, String.valueOf(tokenExpireIn));
		
				//利用获取到的accessToken 去获取当前用的openid(通过openid获得用户信息)
				OpenID openIDObj =  new OpenID(accessToken);
				openID = openIDObj.getUserOpenID();
				
				log.error("欢迎你，代号为 " + openID + " 的用户!");
				//将用户的唯一标识openid存入会话
				session.setAttribute(WhConstance.SESS_QQ_OPENID, openID);
				
				 //根据已获得的openid、accesstoken来获取用户信息
				 UserInfo userInfo = new UserInfo(accessToken, openID);
	             UserInfoBean userInfoBean = userInfo.getUserInfo();
	              failMsg = userInfoBean.getMsg();
	             if (userInfoBean.getRet() == 0) {
	            	 //获取用户的昵称和性别 并存入会话
	            	 nickname = userInfoBean.getNickname();
	            	 sex = userInfoBean.getGender();
	            	 if(sex != null && !"".equals(sex)){
	            		 if(sex.equals("男")){
	            			 sex = "1";
	            		 }else if(sex.equals("女")){
	            			 sex = "0";
	            		 }
	            	 }else{
	            		 sex = "1";	//默认为男 
	            	 }
	             } else {
	            	 mav.setViewName("redirect:/home/user/ThirdLoginFail");
	            	 log.error("很抱歉，我们没能正确获取到您的信息，原因是： " + userInfoBean.getMsg());
	             }
	             
	             try {
	            	 String uid = this.commService.getKey("whuser");
	            	 //根据openid查找用户信息
					 List<WhUser> userList = this.regService.getUserList(openID);
					 //判断用户是否为第一次登录	不为空：修改用户信息	为空：添加用户信息
					 if(userList != null && !(userList.size() == 0)){
						 //修改用户信息
						/* whuser.setId(uid);
						 whuser.setNickname(nickname);
						 whuser.setOpenid(openID);
						 whuser.setSex(sex);
						 this.regService.modifyUser(whuser);*/
						 //将用户信息并存入会话
						session.setAttribute(WhConstance.SESS_USER_KEY,userList.get(0));
					 }else{
						 //保存用户信息至user表
						 whuser.setId(uid);
						 whuser.setSex(sex);
						 whuser.setNickname(nickname);
						 whuser.setOpenid(openID);
						 whuser.setIsrealname(0);
						 whuser.setIsperfect(0);
						 whuser.setIsinner(0);
						 this.regService.saveRegist(whuser);
						 //根据openid查找用户信息并存入会话
						 List<WhUser> uList = this.regService.getUserList(openID);
						 if(uList != null && !(uList.size() == 0)){
							session.setAttribute(WhConstance.SESS_USER_KEY,uList.get(0));
						 }
					 }
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
				
			 }
		    map.put("nickname", nickname);
		} catch (QQConnectException e) {
			success = "1";
			log.error(e.getMessage());
			errMsg =e.getMessage();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		map.put("success", success);
		map.put("errMsg", errMsg);
		mav.addObject("map",map);
		mav.addObject("failMsg",failMsg);
		return mav;
	}
	
}
