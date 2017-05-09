package com.creatoo.hn.actions.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhTag;
import com.creatoo.hn.services.admin.TagService;
import com.creatoo.hn.services.comm.CommService;
/**
 * @author wenjingqiang
 * @version 20160930
 */
@RestController
@RequestMapping("/admin")
public class TagAction {
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public TagService tagService;
	
	@Autowired
	public CommService commService;
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/selAllTag")
	public ModelAndView selAllTag(HttpServletRequest request){
		return new ModelAndView("admin/tag");
	}
	

	/**
	 * 得到所有标签
	 * @return
	 */
	@RequestMapping("/getAllTags")
	public Object getAllTags(int page, int rows,String type){
		Object tags = this.tagService.selAllTag(page, rows,type);
		return tags;
	}
	/*
	 * 增加标签
	 */
	@RequestMapping("/addTag")
	public Object addTag(String name,String type,String idx)
	{
		WhTag whTag = new WhTag();
		String errmsg = "";
		try {
			whTag.setId(this.commService.getKey("WhTag"));
			whTag.setName(name);
			whTag.setType(type);
			whTag.setIdx(idx);
		} catch (Exception e) {
			errmsg = e.getMessage();
		}
		this.tagService.insertTag(whTag);
		Map<String,Object> res = new HashMap<String, Object>();
		res.put("success", true);
		res.put("msg", errmsg);
		return res;	
	}
	/*
	 * 更新标签
	 */
	@RequestMapping("/updateTag")
	public Object updateTag(String id,String name,String type,String idx)
	{
		String success = "0";
		String errmsg = "";
		try {
			WhTag whTag=new WhTag();
			whTag.setId(id);
			whTag.setName(name);
			whTag.setType(type);
			whTag.setIdx(idx);
			this.tagService.updateTag(whTag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String,Object> res = new HashMap<String, Object>();
		res.put("success", true);
		return res;	
		
	}
	
	/*
	 * 功能跳转页面
	 */
//	@RequestMapping("/tagedit")
//	public ModelAndView tagedit(WebRequest request){
//		ModelAndView mav = new ModelAndView( "admin/tagedit" );
//		String id = request.getParameter("id");
//		WhTag whTag = (WhTag) this.tagService.selTagByid(id);
//		if (id!=null){
//			mav.addObject("id",id);
//			mav.addObject("whtag",whTag);
//			mav.addObject("title","修改标签信息");			
//		}else{
//			mav.addObject("title","添加标签信息");
//		}
//		return mav;
//	}
	/*
	 * 删除标签
	 */
	@RequestMapping("/delTag")
	public Object delTag(String id)throws Exception{
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		//
		try {
			tagService.deleteTag(id);
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
	 * 标签树形查询
	 */
	@RequestMapping("/tagtree")
	public Object tree(String type){
		try {
			return this.tagService.inquire(type);
		} catch (Exception e) {
			log.debug(e.getMessage());
			return null;
		}
	}
}
