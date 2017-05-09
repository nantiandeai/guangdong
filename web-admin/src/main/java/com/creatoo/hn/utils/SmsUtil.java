package com.creatoo.hn.utils;

import org.apache.log4j.Logger;

import com.creatoo.hn.sms.ISmsService;

/**
 * 发送短信
 * @author dzl
 *
 */
public class SmsUtil {
	/**
	 * 发送验证码
	 * @param phone
	 * @param validCode
	 * @return
	 */
	public static boolean sendSms(String phone,String validCode){
		boolean sendOK = false;
		try {
			//吉信通
			String smsClazz = WhConstance.getSysProperty("SMS_CLAZZ", "com.creatoo.hn.sms.GxtSmsServiceImpl");
			//反射机制
			ISmsService smsService = (ISmsService)Class.forName(smsClazz).newInstance();
			smsService.sendSms(phone, validCode);
			sendOK = true;
		} catch (Exception e) {
			Logger.getLogger(SmsUtil.class.getName()).error(e.getMessage(), e);
		}
		return sendOK;
	}
	
	/**
	 * 发送短信通知
	 * @param phone
	 * @param moban
	 * @return
	 */
	public static boolean sendNotice(String phone,String moban){
		boolean sendOK = false;
		try {
			//吉信通
			String smsClazz = WhConstance.getSysProperty("SMS_CLAZZ", "com.creatoo.hn.sms.GxtSmsServiceImpl");
			//反射机制
			ISmsService smsService = (ISmsService)Class.forName(smsClazz).newInstance();
			//短信通知
			smsService.sendNotice(phone, moban);
			sendOK = true;
		} catch (Exception e) {
			Logger.getLogger(SmsUtil.class.getName()).error(e.getMessage(), e);
		}
		return sendOK;
	}
	
}
