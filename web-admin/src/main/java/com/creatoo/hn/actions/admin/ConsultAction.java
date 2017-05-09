package com.creatoo.hn.actions.admin;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhTyp;
import com.creatoo.hn.services.admin.ConsulServices;
import com.creatoo.hn.services.comm.CommService;

/**
 * 资讯分类
 * @author lijun
 *
 */
@RestController
@RequestMapping("/admin")
public class ConsultAction {
    
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private ConsulServices consulServices;
	@Autowired
	public CommService commService;
	@RequestMapping("/type")
	public ModelAndView index() {
		return new ModelAndView("admin/type");
	}

	/**
	 * 查询
	 * 
	 * @return
	 */
	@RequestMapping("/inquire")
	public Object inquire(String type) {
		try {
			return this.consulServices.inquire(type);
		} catch (Exception e) {
			log.debug(e.getMessage());
			return null;
		}
	}

	/**
	 * 修改 edittyp
	 */
	@RequestMapping("/typ/updtyp")
	public Object revise(WhTyp whz) {
		String success = "0";
		String errmasg = "";
		try {
			this.consulServices.update(whz);
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
	 * 增加 addtyp
	 * 
	 * @param name
	 * @param state
	 * @param pid
	 */
	@RequestMapping("/typ/addtyp")
	public Object add(WhTyp whz,String typstate) {
		String success = "0";
		String errmasg = "";
		try {
			whz.setTypid(this.commService.getKey("WhTyp"));
			whz.setTypstate("0");
			this.consulServices.save(whz);
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
	 * 删除deltyp
	 */
	@RequestMapping("/typ/deltyp")
	public Object delete(String typid) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		 String success = "0";
		String errmsg = "";

		try {
			consulServices.delete(typid);
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
	@RequestMapping("/typ/gotyp")
	public Object gotype(WhTyp wht) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		 String success = "0";
		String errmsg = "";

		try {
			consulServices.dotype(wht);
		} catch (Exception e) {
			errmsg = e.getMessage();
		}

		// 返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	}
	/**
	 * 改变广告状态
	 */
	@RequestMapping("/typ/goadv")
	public Object goadv(WhTyp wht) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		 String success = "0";
		String errmsg = "";
		
		try {
			consulServices.goadv(wht);
		} catch (Exception e) {
			errmsg = e.getMessage();
		}

		// 返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	}
	/**
	 * 查询广告
	 * 
	 * @return
	 */
	@RequestMapping("/selectadv")
	public Object selectadv(String type) {
		try {
			return this.consulServices.selectadv(type);
		} catch (Exception e) {
			log.debug(e.getMessage());
			return null;
		}
	}
	/**
	 * 带id查询
	 */
	@RequestMapping("/selecttype")
	public Object select(String typid) {
		try {
			return this.consulServices.select(typid);
		} catch (Exception e) {
			log.debug(e.getMessage());
			return null;
		}
	}

	
}
