package com.creatoo.hn.actions.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import com.creatoo.hn.mapper.WhKeyMapper;
import com.creatoo.hn.model.WhKey;

import com.creatoo.hn.services.admin.KeyService;
import com.creatoo.hn.services.comm.CommService;


/**
 * @author wenjingqiang
 * @version 20160930
 */
@RestController
@RequestMapping("/admin")
public class KeyAction {
Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private KeyService keyservice;
	@Autowired
	private WhKeyMapper whKeyMapper;
	@Autowired
	public CommService commService;
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/selAllkey")
	public ModelAndView selAllTag(HttpServletRequest request){
		return new ModelAndView("admin/key");
	}
	
	/**
	 * 得到所有关键字
	 * @return
	 */
	@RequestMapping("/getAllKeys")
	public Object getAllKeys(int page, int rows,String type){
		Object tags = this.keyservice.selAllKey(page, rows,type);
		return tags;
	}
	/*
	 * 增加关键字
	 */
	@RequestMapping("/addKey")
	public Object addKey(String name,String type,String idx)
	{
		WhKey whKey = new WhKey();
		String errmsg = "";
		try {
			whKey.setId(this.commService.getKey("WhKey"));
			whKey.setName(name);
			whKey.setType(type);
			whKey.setIdx(idx);
		} catch (Exception e) {
			errmsg = e.getMessage();
		}
		this.keyservice.insertKey(whKey);
		Map<String,Object> res = new HashMap<String, Object>();
		res.put("success", true);
		res.put("msg", errmsg);
		return res;	
	}
	
	/*
	 * 功能跳转页面
	 */
//	@RequestMapping("/keyedit")
//	public ModelAndView tagedit(WebRequest request){
//		ModelAndView mav = new ModelAndView( "admin/keyedit" );
//		String id = request.getParameter("id");
//		WhKey whKey = (WhKey) this.keyservice.selTagByid(id);
//		if (id!=null){
//			mav.addObject("id",id);
//			mav.addObject("whKey",whKey);
//			mav.addObject("title","修改关键字信息");			
//		}else{
//			mav.addObject("title","添加关键字信息");
//		}
//		return mav;
//	}
	
	/*
	 * 删除关键字
	 */
	@RequestMapping("/delKey")
	public Object delTag(String id)throws Exception{
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		//
		try {
			keyservice.deleteKey(id);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		//返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	}
	/*
	 * 更新关键字
	 */
	@RequestMapping("/updateKey")
	public Object updateTag(String id,String name,String type,String idx)
	{
		WhKey whKey=new WhKey();
		whKey.setId(id);
		whKey.setName(name);
		whKey.setType(type);
		whKey.setIdx(idx);
		this.keyservice.updateTag(whKey);
		Map<String,Object> res = new HashMap<String, Object>();
		res.put("success", true);
		return res;	
		
	}
}
