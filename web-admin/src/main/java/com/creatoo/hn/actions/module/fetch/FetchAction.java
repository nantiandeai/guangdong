package com.creatoo.hn.actions.module.fetch;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.creatoo.hn.services.comm.CommService;

/**
 * 数据采集控制器
 * @author wangxl
 *
 */
@RestController
@RequestMapping("/admin/fetch")
public class FetchAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * 公共service
	 */
	@Autowired
	public CommService commService;
	
	/**
	 * 公共service
	 */
	@Autowired
	public FetchService fetchService;
}
