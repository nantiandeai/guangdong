package com.creatoo.hn.actions.admin.advertisement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhFetchFrom;
import com.creatoo.hn.services.admin.adve.OperateService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;

/**
 * 数据采集
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/admin/adve")
public class OperateAction {
	// 日志
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	public CommService commService;
	@Autowired
	private OperateService operatService;
	
	@RequestMapping("/operate")
	public ModelAndView index() {
		return new ModelAndView("/admin/adve/operate");
	}
	/**
	 * 查询
	 */
	@RequestMapping("/seleoper")
	public Object inquire(HttpServletRequest req, HttpServletResponse resp) {
		// 获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);

		// 分页查询
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		try {
			rtnMap = this.operatService.seleadv(paramMap);
		} catch (Exception e) {
			rtnMap.put("total", 0);
			rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
		return rtnMap;
	}
	
	/**
	 * 添加
	 */
	@RequestMapping("/addoper")
	public Object add(WhFetchFrom whf) {
		String success = "0";
		String errmasg = "";
		try {
			whf.setFromid(this.commService.getKey("WhFetchFrom"));
			this.operatService.save(whf);
		} catch (Exception e) {
			success = "1";
			errmasg = e.getMessage();
		}
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("success", success);
		res.put("msg", errmasg);
		return res;
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/upoper")
	public Object updata(WhFetchFrom whf) {
		String success = "0";
		String errmasg = "";
		try {
			this.operatService.upoper(whf);
		} catch (Exception e) {
			success = "1";
			errmasg = e.getMessage();
		}
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("success", success);
		res.put("msg", errmasg);
		return res;
	}
	
	/**
	 * 根据主键删除
	 */
	@RequestMapping("/deloper")
	public Object deloper(String fromid) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		try {
			this.operatService.deloper(fromid);
		} catch (Exception e) {
			errmsg = e.getMessage();
		}
		// 返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	}

	/**
	 * 改变状态
	 */
	@RequestMapping("/uptype")
	public Map<String, Object> operCheck(WhFetchFrom whf) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		try {
			this.operatService.uptype(whf);
		} catch (Exception e) {
			errmsg = e.getMessage();
		}
		// 返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	}
}
