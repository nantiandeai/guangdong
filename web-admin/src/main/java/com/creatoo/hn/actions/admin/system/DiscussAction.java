package com.creatoo.hn.actions.admin.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhComment;
import com.creatoo.hn.services.admin.system.DiscussService;

@RestController
@RequestMapping("/admin/dis")
public class DiscussAction {
	Logger log = Logger.getLogger(this.getClass());
	
	private static final String VIEW_DIR = "admin/system/";
	
	@Autowired
	private DiscussService discussService;
	/**
	 * 跳转页面
	 * @return
	 */
	@RequestMapping("/showcomment")
	public ModelAndView showComment(){
		ModelAndView mav=new ModelAndView(VIEW_DIR + "comment");
		return mav;
	}
	/**
	 * 查询评论信息
	 * @return
	 */
/*	@RequestMapping("/loaddisList")
	public Object loaddisList(int page,int rows,WebRequest request){
		return this.discussService.loaddisList(page,rows,request);
	}*/
	/**
	 * 删除评论信息操作
	 * 
	 * @return
	 */
	@RequestMapping("/removediscuss")
	public Object removediscuss(String id,String rmtyp) {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			this.discussService.removediscuss(id,rmtyp);
			res.put("success", true);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;
	}
	/**
	 * 评论后台 查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/selecthdComment")
	public Object selecthdComment(WebRequest request) {
		try {
			return this.discussService.selecthdComment(request);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	/**
	 * 管理员增加回复
	 * @param comment
	 * @return
	 */
	@RequestMapping("/addCommentHF")
	public Object addCommentHF(WebRequest request,HttpSession session) {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			res.put("success", true);
			this.discussService.addCommentHF(request,session);
		} catch (Exception e) {
			res.put("success", false);
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 审核评论
	 * @param comment
	 * @return
	 */
	@RequestMapping("/contentEdit")
	public Object contentEdit(WhComment comment,String rmcontent2,HttpSession session){
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			res.put("success", true);
			this.discussService.contentEdit(comment,rmcontent2,session);
		} catch (Exception e) {
			res.put("success", false);
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 根据评论id 查找有关的回复
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/loadhfList")
	public Object loadhfList(WebRequest request,HttpSession session){
			List<Map<String, Object>> list=null;
		try {
			list = (List<Map<String,Object>>) this.discussService.loadhfList(request);
			if(list == null || list.size()==0 ){
				return "nodata";
			}else{
				return list.get(0);
			}
			} catch (Exception e) {
				log.debug(e.getMessage(), e);
				e.printStackTrace();
				return "error";
			}
	}
}
