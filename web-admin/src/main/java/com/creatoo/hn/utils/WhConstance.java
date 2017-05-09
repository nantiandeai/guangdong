package com.creatoo.hn.utils;

import java.util.List;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.creatoo.hn.mapper.WhConfigMapper;
import com.creatoo.hn.model.WhConfig;

import tk.mybatis.mapper.entity.Example;

/**
 * 静态常量信息
 * @author dzl
 *
 */
public class WhConstance {
	/**
	 * session:存放管理员用户登录信息
	 */
	public static final String SESS_USER_KEY = "sessionUser"; 
	public static final String SESS_USER_ID_KEY = "sessionUserId";//用户id
	public static final String SUPER_USER_PASSWORD = "6185c28b757544b0c907ef2986285dfd";//hncreatoo2017 getSysProperty("SUPER_USER_PASSWORD", "e10adc3949ba59abbe56e057f20f883e");//超级管理员密码
	
	/**
	 * session:存放三方登录信息
	 */
	public static final String SESS_QQ_ACCESS = "access_token"; 
	public static final String SESS_QQ_EXPIREIN = "token_expirein"; 
	public static final String SESS_QQ_OPENID = "openid"; 
	public static final String SESS_QQ_NICKNAME = "userNickName";
	public static final String SESS_QQ_UID = "uid";
	public static final String SESS_QQ_SEX = "sex";
	
	
	/**-------------------------上传文件的路径配置-------------------------------------*/
	public static final String SYS_UPLOAD_PATH = getSysProperty("SYS_UPLOAD_PATH", "C:\\temp\\upload\\");//上传文件的路径配置
	
	
	
	
	/**-------------------------邮件发送参数-------------------------------------*/
	/** SMTP服务器 */
	public static final String EMAIL_SMTPSERVER = getSysProperty("EMAIL_SMTPSERVER", "smtp.qq.com");//SMTP服务器
	
	/** SMTP服务器端口 */
	public static final String EMAIL_SMTPSERVER_PORT = getSysProperty("EMAIL_SMTPSERVER_PORT", "465");
	
	/**  邮件服务器账号 */
	public static final String EMAIL_USERNAME = getSysProperty("EMAIL_USERNAME", "36762196@qq.com");
	
	/** 邮件服务器密码 */
	public static final String EMAIL_USERPWD = getSysProperty("EMAIL_USERPWD", "wjehphzceqdzbjde");
		
	/** 发送邮件的邮件地址 */
	public static final String EMAIL_SENDER = getSysProperty("EMAIL_SENDER", "36762196@qq.com");//邮箱发送人用户名
	
	/** 发送邮件的邮件地址的昵称 */
	public static final String EMAIL_SENDERNAME = getSysProperty("EMAIL_SENDERNAME", "创图");//邮箱发送人姓名
	
	/** 注册验证码邮件主题 */
	public static final String EMAIL_REG_SUBJECT = getSysProperty("EMAIL_REG_SUBJECT", "湖南创图网络信息发展有限公司");//邮件标题
	
	/** 用户注册验证码的模板 */
	public static final String EMAIL_REG_CONTENT_TPL = getSysProperty("EMAIL_REG_CONTENT_TPL", "您正在使用的账户验证码为：{code},5分钟内有效。");
	
	/** 用户邮箱通知的模板 */
	public static final String EMAIL_CONTENT_TPL = getSysProperty("EMAIL_CONTENT_TPL", "moban");
	
	/**-------------------------第三方SDK 开发者信息-------------------------------------*/
	public static final String WECHAT_APPID = getSysProperty("WECHAT_APPID", "wxd8c81cbf81ab3361");//上传文件的路径配置
	
	
	
	/**--------------------------短信参数-------------------------------------*/
	/**
	 * 阿里云通讯
	 */
	public static final String smsUrl = getSysProperty("smsUrl", "http://gw.api.tbsandbox.com/router/rest");//正式环境下http请求地址
	
	public static final String smsAppKey = getSysProperty("smsAppKey", "23548829");
	
	public static final String smsSecret =  getSysProperty("smsSecret", "054d2871e11657df7716d7c4b8cbcd13");
	
	public static final String smsSignName =  getSysProperty("smsSignName", "数字文化馆");//签名
	
	public static final String smsTemplateCode = getSysProperty("smsTemplateCode", "您正在使用的账户验证码为：{\"code\":validCode,\"product\":\"数字文化馆 \"}");//模板
	//	public static final String smsOn = "";//是否开启短信发送服务
	/**
	 * 吉信通
	 */
	public static final String gxtUrl = getSysProperty("gxtUrl", "http://service.winic.org:8009/sys_port/gateway/index.asp?");//正式环境下http请求地址
	
	public static final String gxtId = getSysProperty("gxtId", "creatooszwhg");//吉信通用户名
	
	public static final String gxtPwd = getSysProperty("gxtPwd", "creatooszwhg369");//吉信通登录密码
	
	public static final String gxtContent = getSysProperty("gxtContent","您正在使用的验证码为：{code},5分钟内有效");//短信发送验证码模板
	
	public static final String gxtMsg = getSysProperty("gxtMsg","moban");//短信通知模板
	
	public static final String smsClazz = getSysProperty("SMS_CLAZZ", "com.creatoo.hn.sms.GxtSmsServiceImpl");


	
	
	/**--------------------------获取系统配置参数-------------------------------------*/
	/**
	 * 获取系统配置参数
	 * @param key 系统配置参数KEY
	 * @param defaultVal 默认值
	 * @return
	 */
	public static String getSysProperty(String key, String defaultVal){
		String val = defaultVal;
		try {
			String _val = getSysProperty(key);
			if(_val != null){
				val = _val;
			}
		} catch (Exception e) {
		}
		return val;
	}
	
	/**
	 * 获取系统参数配置
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getSysProperty(String key)throws Exception{
		String val = null;
		
		DataSourceTransactionManager transactionManager = null;
		TransactionStatus status = null;
		try {
			transactionManager = (DataSourceTransactionManager) SpringContextUtil.getApplicationContext().getBean("transactionManager");
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
			status = transactionManager.getTransaction(def); // 获得事务状态
			
			//查询数据库中的值
			WhConfigMapper configMapper = (WhConfigMapper)SpringContextUtil.getApplicationContext().getBean("whConfigMapper");
			Example example = new Example(WhConfig.class);
			example.createCriteria().andEqualTo("syskey", key).andEqualTo("systate", "1");
			List<WhConfig> configList = configMapper.selectByExample(example);
			if(configList != null && configList.size() > 0){
				val = configList.get(0).getSysval();
			}
	        
			//逻辑代码，可以写上你的逻辑处理代码
			transactionManager.commit(status);
		} catch (Exception e) {
			//回滚事务
			transactionManager.rollback(status);
		}
	
		return val;
	}
}
