package com.creatoo.hn.actions.home.agdszzy;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.services.comm.CommService;

/**
 * 数字资源
 * @author wangxl
 * @version 2016.11.16
 */
@RestController
@RequestMapping("/agdszzy")
public class SzzyAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * 公用服务类
	 */
	@Autowired
	public CommService commservice;
	
	/**
	 * 首页
	 * @return 首页
	 */
	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView view = new ModelAndView( "home/agdszzy/index" );
		try {
			//do...
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
}
