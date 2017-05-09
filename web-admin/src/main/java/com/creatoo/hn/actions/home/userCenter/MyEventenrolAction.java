package com.creatoo.hn.actions.home.userCenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.home.sign.SignService;
import com.creatoo.hn.services.home.userCenter.MyEventrollservice;
/**
 * 个人中心 活动报名
 * @author lenovo
 *
 */
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.WhConstance;
@RestController
@RequestMapping("/center")
public class MyEventenrolAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public CommService commservice;
	
	@Autowired
	private MyEventrollservice eventenrollService;
	
	@Autowired
	private SignService signService;
	
	@RequestMapping("/myevent/eventbmList")
	public Object eventbmList(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> param = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
        	//取消当前用户标识 
        	param.put("actbmuid", req.getSession().getAttribute(WhConstance.SESS_USER_ID_KEY));
        	
        	//取报名类型0-报名中; 1-审核中; 2-已报名.
        	if(param.containsKey("type")){
        		if("0".equals(param.get("type"))){
        			param.put("type0", "1");
        		}
        		if("1".equals(param.get("type"))){
        			param.put("type1", "1");
        		}
        		if("2".equals(param.get("type"))){
        			param.put("type2", "1");
        		}
        	}
        	
			rtnMap = this.eventenrollService.eventbmList(param);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
        return rtnMap;
	}
	
	/**
	 * 删除我的报名
	 * @param id
	 * @return
	 */
	@RequestMapping("/myevent/removeMyenroll")
	public Object removeMyenroll(String id, HttpServletRequest request){
		//返回
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        String success = "0";
        String errmsg = "";
        try {
			this.eventenrollService.removeMyenroll(id, request);
			//清除相关报名的临时上传目录
			this.signService.clearTemp(id, "event", request);
		} catch (Exception e) {
			success = "1";
	        errmsg = e.getMessage();
		}
        rtnMap.put("success", success);
        rtnMap.put("errmsg", errmsg);
        return rtnMap;
	}
}
