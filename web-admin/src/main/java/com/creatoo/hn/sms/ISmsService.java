package com.creatoo.hn.sms;

/**
 * 
 * @author dzl
 *
 */
public interface ISmsService {
	/**
	 * 短信发送验证码
	 * @param phone
	 * @param validCode
	 * @throws Exception
	 */
	public void sendSms(String phone, String validCode)throws Exception;
	
	/**
	 * 短信通知
	 * @param phone
	 * @throws Exception
	 */
	public void sendNotice(String phone,String moban)throws Exception;

}
