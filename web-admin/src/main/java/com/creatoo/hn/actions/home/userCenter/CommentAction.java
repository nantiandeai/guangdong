package com.creatoo.hn.actions.home.userCenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.creatoo.hn.model.WhComment;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.home.userCenter.CommentService;
import com.creatoo.hn.utils.WhConstance;

/**
 * 个人用户中心--点评控制类
 * @author dzl
 *
 */
@Controller
public class CommentAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private CommentService commentSerice;
	@Autowired
	private CommService commService;

	/**
	 * 我的活动点评查询
	 * 
	 * @param rmuid
	 * @return
	 */
	@RequestMapping("/myActComm")
	@ResponseBody
	public Object selectMyActComm(String rmuid) {
		return this.commentSerice.selectMyActComm(rmuid);
	}

	/**
	 * 我的培训点评查询
	 * 
	 * @param rmuid
	 * @return
	 */
	@RequestMapping("/myTraitmComm")
	@ResponseBody
	public Object selectMyTraitm(String rmuid) {
		return this.commentSerice.selectMyTraitmComm(rmuid);
	}

	/**
	 * 添加我的点评
	 * 
	 * @param whcomm
	 * @return
	 */
	@RequestMapping("/addcomment")
	@ResponseBody
	public Object AddMyColle(HttpServletRequest request, WhComment whcomm) {
		Map<String, String> rtnMap = new HashMap<>();
		String success = "0";
		String errMsg = "";
		try {
			//是否登录
			WhUser suser = (WhUser)request.getSession().getAttribute(WhConstance.SESS_USER_KEY);
			if(suser == null){
				success = "2";
				errMsg = "请登录后点评";
			}else{
				//添加点评
				whcomm.setRmid(this.commService.getKey("wh_comment"));
				whcomm.setRmuid(suser.getId());
				whcomm.setRmdate(new Date());
				this.commentSerice.addMyComm(whcomm);
			}
			
		} catch (Exception e) {
			success = "1";
			errMsg = e.getMessage();
		}
		
		rtnMap.put("success", success);
		rtnMap.put("errMsg", errMsg);
		
		return rtnMap;
	}

	/**
	 * 删除我的点评
	 * 
	 * @param rmid
	 * @return
	 */
	@RequestMapping("/removeComm")
	@ResponseBody
	public Object removeComm(String rmid) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		reMap.put("success", true);
		this.commentSerice.removeComm(rmid);
		return reMap;
	}

	/**
	 * @param reftyp
	 * @param refid
	 * @return
	 */
	@RequestMapping("/srchcomment")
	@ResponseBody
	@SuppressWarnings("rawtypes")
	public Object srchComment(String reftyp, String refid) {
		List<HashMap> list = new ArrayList<HashMap>();
		try {
			list = this.commentSerice.srchComment(reftyp, refid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	
	@RequestMapping("/srchcommentHuifu")
	@ResponseBody
	public Object srchCommentHuifu(String rmids) {
		List<Object> list = new ArrayList<Object>();
		try {
			list = this.commentSerice.searchCommentHuifu(rmids);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	
	/**
	 * 点评个人中心页面加载数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/center/loadcomLoad")
	@ResponseBody
	public Object loadcomLoad(WebRequest request,HttpSession session){
		return this.commentSerice.loadcomLoad(request,session);
	}
	/**
	 * 删除个人中心评论
	 */
	@RequestMapping("/center/removeContent")
	@ResponseBody
	public Object removeContent(WebRequest request){
		String issucc="";
		try {
			issucc="success";
			this.commentSerice.removeContent(request);
		} catch (Exception e) {
			issucc="fail";
		}
		return issucc;
	}
}
