package com.creatoo.hn.actions.admin.system;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.creatoo.hn.services.comm.CommService;

/**
 * 总分馆管理
 * @author wangxl
 * @version 2016.11.15
 */
@RestController
@RequestMapping("/admin/org")
public class SubvenueActtion {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public CommService commservice;
}
