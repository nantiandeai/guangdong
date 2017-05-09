package com.creatoo.hn.actions.admin;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhConfig;
import com.creatoo.hn.services.admin.WhconfigService;
import com.creatoo.hn.services.comm.CommService;

/**
 * 基础配置
 * @author lijun
 *
 */
@RestController
@RequestMapping("/admin/conf")
public class ConfigAction {
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	public CommService commService;
	@Autowired
	private WhconfigService whconfigService;
	
	//返回视图
	@RequestMapping("/sysconfig")
	public ModelAndView index() {
		return new ModelAndView("admin/sysconfig");
	}
		
	/**
	 * 查询全部信息
	 * 
	 * @return
	 */
	@RequestMapping("/seleconfig")
	public Object inquire(int page, int rows,String sort,String order) {
			Object Configs = null;
			try {
				Configs = this.whconfigService.syselect(page, rows,sort,order);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			return Configs;
			
	}
	
	/*
	 * 删除配置信息
	 */
	@RequestMapping("/delconfig")
	public Object delConfig(String sysid){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		//
		try {
			whconfigService.delsys(sysid);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		//返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	}
	
	/**
	 * 添加配置信息
	 */
	@RequestMapping("/addconfig")
	public Object addsys(String syskey,String sysval,String systate,String systype,String sysmome)
	{
		String success = "0";
		String errmasg = "";
		try {
			WhConfig whc=new WhConfig();
			whc.setSysid(this.commService.getKey("WhConfig"));
			whc.setSyskey(syskey);
			whc.setSysval(sysval);
			whc.setSystate(systate);
			whc.setSystype(systype);
			whc.setSysmome(sysmome);
			this.whconfigService.addconfig(whc);
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
	 * 更新配置信息
	 */
	@RequestMapping("/upconfig")
	public Object upsysconfig(String sysid,String syskey,String sysval,String systate,String systype,String sysmome){
		String success = "0";
		String errmasg = "";
		try {
			WhConfig whc=new WhConfig();
			whc.setSysid(sysid);
			whc.setSyskey(syskey);
			whc.setSysval(sysval);
			whc.setSystate(systate);
			whc.setSystype(systype);
			whc.setSysmome(sysmome);
			this.whconfigService.updaconfig(whc);
		} catch (Exception e) {
			success = "1";
			errmasg = e.getMessage();
		}
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("success", success);
		res.put("msg", errmasg);
		return res;
	}
}
