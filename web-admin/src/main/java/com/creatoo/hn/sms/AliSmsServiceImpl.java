package com.creatoo.hn.sms;

import org.apache.log4j.Logger;

import com.creatoo.hn.utils.WhConstance;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * 阿里云通讯短信发送
 * @author dzl
 *
 */
public class AliSmsServiceImpl implements ISmsService {
	/**
	 * 日志管理器
	 */
	Logger log = Logger.getLogger(this.getClass());

	@Override
	public void sendSms(String phone, String validCode) throws Exception {
		AlibabaAliqinFcSmsNumSendResponse rsp;
		try {
			//正式环境下http请求地址: http://gw.api.taobao.com/router/rest
			//appkey:23548829
			//appSecret:054d2871e11657df7716d7c4b8cbcd13
			String smsUrl = WhConstance.getSysProperty("smsUrl", "http://gw.api.tbsandbox.com/router/rest");
			String smsAppKey = WhConstance.getSysProperty("smsAppKey", "23548829");
			String smsSecret = WhConstance.getSysProperty("smsSecret", "054d2871e11657df7716d7c4b8cbcd13");
			String smsSignName = WhConstance.getSysProperty("smsSignName", "数字文化馆");
			String smsTemplateCode = WhConstance.getSysProperty("smsTemplateCode", "您正在注册的账户验证码为：{\"code\":validCode,\"product\":\"数字文化馆 \"}");
			
			TaobaoClient client = new DefaultTaobaoClient(smsUrl, smsAppKey, smsSecret);
			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
			
			//req.setExtend("123456");	//返回一个用户标识
			req.setSmsType("normal");	//短信类型
			req.setSmsFreeSignName(smsSignName);//短信签名 
			req.setSmsParamString(smsTemplateCode);	//短信模板
			req.setRecNum(phone);	//短信接收号码，（支持单个或多个手机号码）一次调用最多发200个号码。
			req.setSmsTemplateCode(smsTemplateCode);	//短信模板ID
			rsp = client.execute(req);
			System.out.println(rsp.getBody());
		} catch (Exception e) {
			throw e;
		}
	}


	@Override
	public void sendNotice(String phone, String moban) throws Exception {
		
	}

}
