package com.creatoo.hn.actions.wap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.WechatUtil;

/**
 * 微信接口
 * @author Ray
 * @version 2016.09.27
 */
@RestController
@RequestMapping("/wap/wechat")
public class WechatAction {
	
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * 微信验证接口
	 * @return 验证结果
	 */
	@RequestMapping("/validate")
	public Object validate(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		
        try {
    		String signature = paramMap.get("signature").toString();
    		String timestamp = paramMap.get("timestamp").toString();
    		String nonce = paramMap.get("nonce").toString();
    		String echostr = paramMap.get("echostr").toString();
    		
			if (WechatUtil.checkSignature(signature, timestamp, nonce)) {
				return echostr;
			}
			else {
				rtnMap.put("exception", "Invalid signature.");
			}
		} catch (Exception e) {
			rtnMap.put("exception", e.getMessage());
		}
		
		return rtnMap;
	}
}
