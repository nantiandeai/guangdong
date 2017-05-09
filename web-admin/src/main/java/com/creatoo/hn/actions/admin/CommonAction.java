package com.creatoo.hn.actions.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.creatoo.hn.model.WhKey;
import com.creatoo.hn.model.WhTag;
import com.creatoo.hn.model.WhTyp;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.CommUtil;

/**
 * 这是一个公共的访问控制类，对于一些有共性的服务都可以放在这个类里面，如请求艺术类型/请求培训类型等
 * @author wangxl
 * @version 20161011
 */
@RestController
@RequestMapping("/comm")
public class CommonAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public CommService service;
	
	/**
	 * 查询艺术分类/培训分类/活动分类列表
	 * @return 艺术分类/培训分类/活动分类列表
	 */
	@RequestMapping("/whtyp")
	public Object findwhtyp(String type){
		List<Object> rtnlist = new ArrayList<Object>();
		try {
			List<WhTyp> list = this.service.findWhtyp(type);
			rtnlist = CommUtil.parseTree(list);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return rtnlist;
	}
	
	/**
	 * 查询关键字
	 * @return 关键字
	 */
	@RequestMapping("/whkey")
	public Object findwhkey(String type){
		List<WhKey> list = new ArrayList<WhKey>();
		try {
			list = this.service.findWhKey(type);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	
	/**
	 * 查询关键字
	 * @return 关键字
	 */
	@RequestMapping("/whtag")
	public Object findwhtag(String type){
		List<WhTag> list = new ArrayList<WhTag>();
		try {
			list = this.service.findWhTag(type);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
}
