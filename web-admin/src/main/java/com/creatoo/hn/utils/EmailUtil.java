package com.creatoo.hn.utils;

import org.apache.commons.mail.SimpleEmail;

/**
 * 邮箱注册辅助方法
 * @author dzl
 *
 */
public class EmailUtil {
	
	/**邮箱注册发送验证码
	 * @param userEmail
	 * @param validCode
	 * @return
	 */
	public static boolean sendValidCodeEmail(String userEmail, String validCode) {
		boolean sendOK=true;
		SimpleEmail email = new SimpleEmail();  
		
	    try {  
	    	String email_smtpserver = WhConstance.getSysProperty("EMAIL_SMTPSERVER", "smtp.qq.com");//SMTP服务器
	    	String email_smtpserver_port =WhConstance.getSysProperty("EMAIL_SMTPSERVER_PORT", "465");
	    	String email_username = WhConstance.getSysProperty("EMAIL_USERNAME", "36762196@qq.com");
	    	String email_userpwd = WhConstance.getSysProperty("EMAIL_USERPWD", "wjehphzceqdzbjde");
	    	String email_sender = WhConstance.getSysProperty("EMAIL_SENDER", "36762196@qq.com");//邮箱发送人用户名
	    	String email_sendername = WhConstance.getSysProperty("EMAIL_SENDERNAME", "创图");//邮箱发送人姓名
	    	String email_reg_subject = WhConstance.getSysProperty("EMAIL_REG_SUBJECT", "湖南创图网络信息发展有限公司");//邮件标题
	    	String email_reg_content_tpl = WhConstance.getSysProperty("EMAIL_REG_CONTENT_TPL", "您正在使用的账户验证码为：{code},5分钟内有效。");
	    	email_reg_content_tpl = email_reg_content_tpl.replace("{code}", validCode);
	    	// 邮箱服务器身份验证  
	    	email.setHostName(email_smtpserver);// 设置使用发电子邮件的邮件服务器
	    	email.setAuthentication(email_username, email_userpwd);  
	    	email.setSSLOnConnect(Boolean.TRUE);
	    	email.setSslSmtpPort(email_smtpserver_port); // 设定SSL端口
	        // 收件人邮箱  
	        email.addTo(userEmail,userEmail);  
	        // 发件人邮箱  
	        email.setFrom(email_sender, email_sendername);  
	        // 邮件主题  
	        email.setSubject(email_reg_subject);  
	        // 邮件内容  
	        String emailContent = email_reg_content_tpl;
	        emailContent = emailContent.replaceAll("\\{code\\}", validCode);
	        email.setMsg(emailContent);  
	        // 发送邮件  
	        email.send();  
	    } catch (Exception ex) {  
	        ex.printStackTrace();  
	        sendOK = false;
	    } 
	    return sendOK;
	}
	/**邮箱通知
	 * @param userEmail
	 * @param moban
	 * @return
	 */
	public static boolean sendNoticeEmail(String userEmail, String moban) {
		boolean sendOK=true;
		SimpleEmail email = new SimpleEmail();  
	    try {  
	    	String email_smtpserver = WhConstance.getSysProperty("EMAIL_SMTPSERVER", "smtp.qq.com");//SMTP服务器
	    	String email_smtpserver_port =WhConstance.getSysProperty("EMAIL_SMTPSERVER_PORT", "465");
	    	String email_username = WhConstance.getSysProperty("EMAIL_USERNAME", "36762196@qq.com");
	    	String email_userpwd = WhConstance.getSysProperty("EMAIL_USERPWD", "wjehphzceqdzbjde");
	    	String email_sender = WhConstance.getSysProperty("EMAIL_SENDER", "36762196@qq.com");//邮箱发送人用户名
	    	String email_sendername = WhConstance.getSysProperty("EMAIL_SENDERNAME", "创图");//邮箱发送人姓名
	    	String email_reg_subject = WhConstance.getSysProperty("EMAIL_REG_SUBJECT", "湖南创图网络信息发展有限公司");//邮件标题
	    	String email_content_tpl = WhConstance.getSysProperty("EMAIL_CONTENT_TPL", moban);
	    	// 邮箱服务器身份验证  
	    	email.setHostName(email_smtpserver);// 设置使用发电子邮件的邮件服务器
	    	email.setAuthentication(email_username, email_userpwd);  
	    	email.setSSLOnConnect(Boolean.TRUE);
	    	email.setSslSmtpPort(email_smtpserver_port); // 设定SSL端口
	        // 收件人邮箱  
	        email.addTo(userEmail,userEmail);  
	        // 发件人邮箱  
	        email.setFrom(email_sender, email_sendername);  
	        // 邮件主题  
	        email.setSubject(email_reg_subject);  
	        // 邮件内容  
	        String emailContent = email_content_tpl;
	        emailContent = emailContent.replaceAll("\\{moban\\}", moban);
	        email.setMsg(emailContent);  
	        // 发送邮件  
	        email.send();  
	    } catch (Exception ex) {  
	        ex.printStackTrace();  
	        sendOK = false;
	    } 
	    return sendOK;
	}
	
}
