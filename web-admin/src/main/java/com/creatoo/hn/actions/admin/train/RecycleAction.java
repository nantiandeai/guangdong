package com.creatoo.hn.actions.admin.train;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.services.admin.train.EnrollService;

/**
 * 培训-回收站控制器
 * @author wangxl
 * @version 2016.10.08
 */
@RestController
@RequestMapping("/admin/train")
public class RecycleAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public EnrollService service;
	
	/**
	 * 进入回收站管理界面
	 * @return 资讯内容管理界面
	 */
	@RequestMapping("/recyclelist")
	public ModelAndView index(){
		return new ModelAndView( "admin/train/recyclelist" );
	}
	
	/**
	 * 根据条件查询培训历史列表
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping("/sreachhis")
//	public Object sreach(int page, int rows)throws Exception{
//		return service.sreachTrainHis(page, rows);
//	}
}
