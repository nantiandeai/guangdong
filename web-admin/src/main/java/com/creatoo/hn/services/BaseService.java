package com.creatoo.hn.services;

/**
 * 可做为所有service的基类
 * @author wangxl
 *
 */
public class BaseService {
	/** 错误码 */
	private String errorCode;
	
	/** 错误消息 */
	private String errorMsg;
	
	/** 错误字段 */
	private String errfield;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrfield() {
		return errfield;
	}

	public void setErrfield(String errfield) {
		this.errfield = errfield;
	}
}
