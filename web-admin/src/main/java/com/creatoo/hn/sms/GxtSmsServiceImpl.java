package com.creatoo.hn.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.creatoo.hn.utils.WhConstance;

/**
 * 吉信通短信发送
 * @author dzl
 *
 */
public class GxtSmsServiceImpl implements ISmsService {
	/**
	 * 短信验证码发送
	 */
	@Override
	public void sendSms(String phone, String validCode) throws Exception {
		//Integer x_ac=10;//发送信息
		HttpURLConnection httpconn = null;
		String result= null;
		
		try {
			//动态获取配置参数
			String gxtUrl = WhConstance.getSysProperty("gxtUrl", "http://service.winic.org:8009/sys_port/gateway/index.asp?");
			String gxtId = WhConstance.getSysProperty("gxtId", "creatooszwhg");
			String gxtPwd = WhConstance.getSysProperty("gxtPwd", "creatooszwhg369");
			String gxtContent = WhConstance.getSysProperty("gxtContent","您正在使用的验证码为：{code},5分钟内有效。");
			gxtContent = gxtContent.replace("{code}", validCode);
			
			StringBuilder sb = new StringBuilder();
			sb.append(gxtUrl);		//正式环境下的地址
			sb.append("id=").append(URLEncoder.encode(gxtId,"gb2312"));	//吉信通用户名
			sb.append("&pwd=").append(gxtPwd);	//吉信通登录密码
			sb.append("&to=").append(phone);	//接收的手机号码
			sb.append("&content=").append(URLEncoder.encode(gxtContent,"gb2312")); //短信模板
			sb.append("&time=").append("");	//短信发送时间
			
			URL url = new URL(sb.toString());
			httpconn = (HttpURLConnection) url.openConnection();
			BufferedReader rd = new BufferedReader(new InputStreamReader(httpconn.getInputStream()));
			result = rd.readLine();
			rd.close();
		}catch (Exception e) {
			throw e;
		} finally{
			if(httpconn!=null){
				httpconn.disconnect();
				
				httpconn=null;
			}
		}
		//return result;
	}

	
	/**
	 * 短信通知
	 */
	@Override
	public void sendNotice(String phone,String moban) throws Exception {
		HttpURLConnection httpconn = null;
		String result= null;
		
		try {
			//动态获取配置参数
			String gxtUrl = WhConstance.getSysProperty("gxtUrl", "http://service.winic.org:8009/sys_port/gateway/index.asp?");
			String gxtId = WhConstance.getSysProperty("gxtId", "creatooszwhg");
			String gxtPwd = WhConstance.getSysProperty("gxtPwd", "creatooszwhg369");
			String gxtMsg = WhConstance.getSysProperty("gxtMsg",moban);
			
			StringBuilder sb = new StringBuilder();
			sb.append(gxtUrl);		//正式环境下的地址
			sb.append("id=").append(URLEncoder.encode(gxtId,"gb2312"));	//吉信通用户名
			sb.append("&pwd=").append(gxtPwd);	//吉信通登录密码
			sb.append("&to=").append(phone);	//接收的手机号码
			sb.append("&content=").append(URLEncoder.encode(gxtMsg,"gb2312")); //短信模板
			sb.append("&time=").append("");	//短信发送时间
			
			URL url = new URL(sb.toString());
			httpconn = (HttpURLConnection) url.openConnection();
			BufferedReader rd = new BufferedReader(new InputStreamReader(httpconn.getInputStream()));
			result = rd.readLine();
			rd.close();
		}catch (Exception e) {
			throw e;
		} finally{
			if(httpconn!=null){
				httpconn.disconnect();
				httpconn=null;
			}
		}
		//return result;
		
	}
	
	
	

}
