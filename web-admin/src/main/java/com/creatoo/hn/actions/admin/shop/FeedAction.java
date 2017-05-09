package com.creatoo.hn.actions.admin.shop;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.emun.EnumOptType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhFeedback;
import com.creatoo.hn.model.WhUserTroupe;
import com.creatoo.hn.services.admin.shop.FeedService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.UploadUtil;

/**
 * 网上咨询和意见反馈
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/admin/shop")
public class FeedAction {
	//日志
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private FeedService feedService;
	
	@RequestMapping("/feed")
	@WhgOPT(optType = EnumOptType.OPINION, optDesc = {"访问意见反馈管理列表页"})
	public ModelAndView index() {
		return new ModelAndView("/admin/shop/feedback");
	}
	/**
	 * 查询
	 */
	@RequestMapping("/selefeed")
	public Object inquire(HttpServletRequest req, HttpServletResponse resp){
        //获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		//分页查询
		  Map<String, Object> rtnMap = new HashMap<String, Object>();
		  try {
			rtnMap = this.feedService.selefeed(paramMap);
		} catch (Exception e) {
			//log.error(e.getMessage(), e);
		    rtnMap.put("total", 0);
		    rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
		  return rtnMap;
	}
	/**
	 * 删除
	 * @param feedid
	 * @return
	 */
	@RequestMapping("/delfeed")
	@WhgOPT(optType = EnumOptType.OPINION, optDesc = {"删除"})
	public Object delete(String feedid) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		try {
			this.feedService.delete(feedid);
		} catch (Exception e) {
			errmsg = e.getMessage();
		}
		// 返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	}
	/**
	 * 查看功能
	 */
	@RequestMapping("/upfeed")
	public Object upTrou(WhFeedback whfe){
		String success = "0";
		String errmasg = "";
		try {
			this.feedService.upback(whfe);
		} catch (Exception e) {
			success = "1";
			errmasg = e.getMessage();
		}
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("success", success);
		res.put("msg", errmasg);
		return res;
	}
}
