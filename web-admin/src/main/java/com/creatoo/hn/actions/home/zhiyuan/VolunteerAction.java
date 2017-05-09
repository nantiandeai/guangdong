package com.creatoo.hn.actions.home.zhiyuan;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhCfgList;
import com.creatoo.hn.model.WhEntsource;
import com.creatoo.hn.model.WhKey;
import com.creatoo.hn.model.WhZxColinfo;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.home.zhiyuan.VolunteerService;

@RestController
@RequestMapping("/zhiyuan")
public class VolunteerAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public CommService commservice;
	
	@Autowired
	private VolunteerService service;
	
	/**
	 * 志愿者类型
	 */
	@RequestMapping("/zyz")
	public ModelAndView jpwhz(){
		ModelAndView view = new ModelAndView( "home/zhiyuan/volunteer" );
		try {
			
			//志愿者新闻
			String cfgentclazz="2016111200000002";
			String cfgtype="2016111400000006";
			view.addObject("hot", this.service.selecthot(cfgentclazz,cfgtype));
			//热门动态
			String cfgpagetype="2016111400000005";
			String clazz="2016111200000003";
			view.addObject("rmdt", this.service.selectclazz(clazz,cfgpagetype));
			//培训动态
			String type="2016111200000004";
			String cfgpage="2016111400000007";
			view.addObject("pxdt", this.service.select(type,cfgpage));
			//志愿者中心
			String types="2016111200000005";
			String pagetype="2016111400000008";
			view.addObject("zyzx", this.service.selectype(types,pagetype));
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return view;
	}
	/**
	 * 志愿者详细信息
	 */
	@RequestMapping("/zyzinfo")
	public ModelAndView zyzinfo(String clnfid){
		ModelAndView view = new ModelAndView( "home/zhiyuan/voluninfo");
		try {
			//热门动态
			String clazz="2016111200000003";
			String cfgpagetype="2016111400000005";
			view.addObject("rmdt", this.service.selectclazz(clazz,cfgpagetype));
			//培训动态
			String type="2016111200000004";
			String cfgpage="2016111400000007";
			view.addObject("pxdt", this.service.select(type,cfgpage));
			//志愿者信息
			Map<String, Object> resmap = (Map<String, Object>) this.service.selectinfo(clnfid);
			view.addObject("zyinfo", resmap.get("info"));
			view.addObject("lid", resmap.get("lid"));
			view.addObject("bid", resmap.get("bid"));
			view.addObject("ltitle", resmap.get("ltitle"));
			view.addObject("btitle", resmap.get("btitle"));
			//资源
			List<WhEntsource> whe= this.service.selewhe(clnfid);
			view.addObject("loadzy", whe);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return view;
	}

}
