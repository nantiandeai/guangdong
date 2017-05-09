package com.creatoo.hn.actions.home.userCenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.creatoo.hn.ext.emun.EnumBMState;
import com.creatoo.hn.ext.emun.EnumState;
import com.creatoo.hn.model.WhgTraEnrol;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.home.sign.SignService;
import com.creatoo.hn.services.home.userCenter.MyEnrollService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.WhConstance;

import weibo4j.model.User;

/**
 * 我的报名
 * @author wangxl
 *
 */
@RestController
@RequestMapping("/center")
public class MyEnrollAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public CommService commservice;
	
	@Autowired
	public MyEnrollService enrollService;
	
	@Autowired
	private SignService signService;

	/**
	 * 我的课程列表/历史列表
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("/myenroll/srchmyenroll")
	public Object srchjpwhz(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> param = ReqParamsUtil.parseRequest(req);
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
        	//取消当前用户标识 
        	WhUser user = (WhUser) req.getSession().getAttribute(WhConstance.SESS_USER_KEY);
        	param.put("userid", user.getId());
        	//取报名类型1当前报名; 0历史报名;
			param.put("type", param.get("type"));
			rtnMap = this.enrollService.paggingEnroll(param);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<WhgTraEnrol>(0));
		}
        return rtnMap;
	}
	
	/**
	 * 删除我的报名
	 * @param id
	 * @return
	 */
	@RequestMapping("/myenroll/delmyenroll")
	public Object delmyenroll(String id, String state, HttpServletRequest request){
		WhgTraEnrol enrol = new WhgTraEnrol();
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		if(!("2").equals(state)){
			enrol.setState(EnumBMState.BM_QXBM.getValue());
			enrol.setId(id);
			try {
				this.enrollService.delMyEnroll(enrol);
				//清除相关报名的临时上传目录
			} catch (Exception e) {
				log.error(e.getMessage());
				success = "1";
				errmsg = e.getMessage();
			}
		}else{
			try {
				this.enrollService.delEnroll(id);
				//清除相关报名的临时上传目录
			} catch (Exception e) {
				log.error(e.getMessage());
				success = "1";
				errmsg = e.getMessage();
			}
		}
        rtnMap.put("success", success);
        rtnMap.put("errmsg", errmsg);
        return rtnMap;
	}
	
	/** 取两天内上课数据
	 * @param traid
	 * @return
	 */
	@RequestMapping("/ajaxSkdjs")
	public Object getTimeSkdjs(String traid){
		try {
			return this.enrollService.searchSkdjs(traid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ArrayList<Object>();
		}
	}
}
