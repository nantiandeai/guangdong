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
import com.creatoo.hn.model.WhWechat;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.home.user.RegistService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.WhConstance;
import com.creatoo.hn.utils.social.wechat.AdvancedUtil;
import com.creatoo.hn.utils.social.wechat.CommonUtil;
import com.creatoo.hn.utils.social.wechat.SNSUserInfo;
import com.creatoo.hn.utils.social.wechat.WeixinOauth2Token;
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
public class WechatLoginAction {
	/*
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	public CommService commService;
	
	@Autowired
	public RegistService regService;
	
	/**
	 * Wechat跳转至授权页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/user/authorizeWx")
	@ResponseBody
	public Object authorizePage(HttpServletRequest request,HttpServletResponse response){
		String success = "0";
		String errMsg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String redirectUrl = String.format("%suser/afterWxLogin", CommonUtil.getWebBaseUrl(request));
			//跳转至授权界面
			String url = AdvancedUtil.getQrAuthUrl("wx8c2ea6639590f998", redirectUrl, "creatoo");
			response.sendRedirect(url);
		} catch (IOException e) {
			errMsg = e.getMessage();
		} catch (Exception e) {
			errMsg = e.getMessage();
		}
		map.put("success", success);
		map.put("errMsg", errMsg);
		return map;
	}
	
	/**
	 * Wechat授权登录后页面的跳转
	 * @return
	 */
	@RequestMapping("/user/afterWxLogin")
	public ModelAndView afterLoginRedirect(HttpSession session,HttpServletRequest request){
		String success = "0";
		String errMsg = "";
		String nickname = "";
		String sex = "";
		String failMsg = "";
		ModelAndView mav = new ModelAndView("/home/center/userInfo");
		Map<String,Object> map = new HashMap<String,Object>();
		WhUser whuser = new WhUser();
		
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);

		try {
			String code = paramMap.get("code").toString();
    		String state = paramMap.get("state").toString();
    		
    		if (!"authdeny".equals(code)) {
                // 获取网页授权access_token
                WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken("wx8c2ea6639590f998", "7c2e3b112ca21c14b42333174be4f19a", code);
                // 网页授权接口访问凭证
                String accessToken = weixinOauth2Token.getAccessToken();
                // 用户标识
                String openId = weixinOauth2Token.getOpenId();
                // 获取用户信息
                SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);

                // 设置要传递的参数
                request.getSession().setAttribute("WechatOpenId", openId);
                request.setAttribute("WechatState", state);
                
	            if (snsUserInfo != null) {
	            	//获取用户的昵称和性别 并存入会话
	            	nickname = snsUserInfo.getNickname();
	            	sex = String.valueOf(snsUserInfo.getSex());
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
	            	log.error("很抱歉，我们没能正确获取到您的信息，原因是：无法获取用户信息");
	            }
	             
	            try {
	            	String uid = this.commService.getKey("whuser");
	            	//根据wxopenid查找用户信息
					List<WhUser> userList = this.regService.getWxUser(openId);
					//判断用户是否为第一次登录	不为空：修改用户信息	为空：添加用户信息
					if(userList != null && !(userList.size() == 0)){
						//将用户信息并存入会话
						session.setAttribute(WhConstance.SESS_USER_KEY,userList.get(0));
						mav.addObject("nickname",nickname);
						mav.addObject("sex",sex);
					}else{
						//保存用户信息至user表
						whuser.setId(uid);
						whuser.setSex(sex);
						whuser.setNickname(nickname);
						whuser.setWxopenid(openId);
						whuser.setIsrealname(0);
						whuser.setIsperfect(0);
						whuser.setIsinner(0);
						this.regService.saveRegist(whuser);
						//根据openid查找用户信息并存入会话
						List<WhUser> uList = this.regService.getWxUser(openId);
						if(uList != null && !(uList.size() == 0)){
							session.setAttribute(WhConstance.SESS_USER_KEY,uList.get(0));
						}

						mav.addObject("nickname",nickname);
						mav.addObject("sex",sex);
					}
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}

			    map.put("nickname", nickname);
            }
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
