package com.creatoo.hn.services.home.agdzxdt;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.services.comm.CommService;

/**
 * 资讯动态
 * @author wangxl
 * @version 2016.11.16
 */
@Service
public class ZxdtService {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * 公用服务类
	 */
	@Autowired
	public CommService commservice;
	
}
