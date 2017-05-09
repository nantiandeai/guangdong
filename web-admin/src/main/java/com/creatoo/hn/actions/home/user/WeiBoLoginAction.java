package com.creatoo.hn.actions.home.user;

import java.util.List;

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

import weibo4j.Account;
import weibo4j.Oauth;
import weibo4j.Users;
import weibo4j.http.AccessToken;
import weibo4j.model.User;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONObject;

/**
 * 新浪微博三方登录
 * @author dzl
 *
 */
@Controller
public class WeiBoLoginAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public CommService commService;
	@Autowired
	public RegistService regService;
	/**
	 * 微博跳转至授权页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/user/authorizeWb")
	@ResponseBody
	public ModelAndView authorizePage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView view = new ModelAndView("/index");
		try{
			Oauth oauth = new Oauth();
			//BareBonesBrowserLaunch.openURL(oauth.authorize("code",""));
			view.setViewName("redirect:"+oauth.authorize("code",""));
		} catch (WeiboException e) {
			if(401 == e.getStatusCode()){
				log.error("Unable to get the access token.----------------------");
			}else{
				log.error(e.getMessage(),e);
			}
		}
		return view;
	}
	
	/**
	 * 新浪微博授权后跳转页面
	 * @return
	 */
	@RequestMapping("/user/afterWbLogin")
	public ModelAndView afterLoginRedirect(HttpSession session,HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/index");
		WhUser whuser = new WhUser();
		String accessToken = null;
	    String screenName = null; 
	    String gender = null;
	    AccessToken accessTokenObj = null;
	    Oauth oauth = new Oauth();
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
        	//获取访问令牌
			String code = request.getParameter("code");  
            accessTokenObj = oauth.getAccessTokenByCode(code);
            accessToken = accessTokenObj.getAccessToken();
            
            Account account = new Account(accessToken);
           
            //获取用户id
            JSONObject uidJson = account.getUid();
            String wid = uidJson.getString("uid");
            
            Users users = new Users(accessToken);
            //根据id获取用户信息
            User weiboUser = users.showUserById(wid);
            if(weiboUser != null){
            	//用户昵称
                screenName = weiboUser.getScreenName();
                //获取性别
                gender = weiboUser.getGender();
                //判断性别：男 or 女
    	        if(gender != null && !"".equals(gender)){
    	        	if(gender.equals("m")){
    	   		 		gender = "1";//性别为男
    	   		 	}else if(gender.equals("f")){
    	   		 		gender = "0";//性别为女
    	   		 	}
    	   	 	}else{
       		 		gender = "1";	//默认为男
    	   	 	}
            } else {
            	mav.setViewName("redirect:/home/user/ThirdLoginFail");
           	 	log.error("很抱歉，我们没能正确获取到您的信息，原因是： ");
            }
			//判断用户是否为第一次登陆
			//根据用户id查询用户信息
			List<WhUser>  userList = this.regService.getWbList(wid);
			if(userList != null && !(userList.size() == 0) ){
				//用户已登陆过
				session.setAttribute(WhConstance.SESS_USER_KEY, userList.get(0));
			}else{
				//用户第一次登陆
				String uid = this.commService.getKey("whuser");
				whuser.setId(uid);
				whuser.setNickname(screenName);
				whuser.setSex(gender);
				whuser.setWid(wid);
				whuser.setIsrealname(0);
				whuser.setIsperfect(0);
				whuser.setIsinner(0);
				this.regService.saveRegist(whuser);
				//根据用户微博登陆查询用户信息并存入会话
				List<WhUser> uList = this.regService.getWbList(wid);
				if(uList != null && !(uList.size() == 0)){
					session.setAttribute(WhConstance.SESS_USER_KEY,uList.get(0));
				}
			}
			log.error(screenName.toString());
			log.error(uidJson.toString());
		} catch (WeiboException e) {
			log.error(e.getMessage(),e);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return mav;
	}

}
