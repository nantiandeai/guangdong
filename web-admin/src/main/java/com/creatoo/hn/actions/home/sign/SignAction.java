package com.creatoo.hn.actions.home.sign;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.creatoo.hn.model.WhActivitybm;
import com.creatoo.hn.model.WhTraenr;
import com.creatoo.hn.model.WhTrain;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.services.home.sign.SignService;
import com.creatoo.hn.utils.UploadUtil;
import com.creatoo.hn.utils.WhConstance;

/** 
 * 培训报名业务处理
 * @author qxk
 *
 */
@Controller
@RequestMapping("/sign")
public class SignAction {
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private SignService signService;
	
	
	/**
	 * 进入培训报名步骤1界面
	 * @return
	 */
	@RequestMapping("/step1/{id}")
	public String toSignStep1(HttpSession session, ModelMap mmp, @PathVariable String id){
		//获取报名的培训
		Object train = this.signService.getTrain4Id(id);
		mmp.addAttribute("train", train);
		mmp.addAttribute("mark", "gypx");
		return "home/sign/sign1st";
	}
	
	/** 进入活动报步骤1界面
	 * @param itmid
	 * @param mmp
	 * @return
	 */
	@RequestMapping("/evt/step1/{itmid}")
	public String toEventSignStep1(@PathVariable String itmid, ModelMap mmp, WebRequest request){
		String actbmcount = request.getParameter("actbmcount");
		//获取报名的活动信息
		Object event = this.signService.getEventInfo4itmid(itmid);
		mmp.addAttribute("event", event);
		mmp.addAttribute("mark", "event");
		mmp.addAttribute("actbmcount", actbmcount);
		return "home/sign/sign1st";
	}
	
	
	/**
	 * 进入培训报名步骤2界面
	 * @return
	 */
	@RequestMapping("/step2/{enrid}")
	public String toSignStep2(HttpSession session, ModelMap mmp, HttpServletRequest request, @PathVariable String enrid){
		String pageVal = "home/sign/sign2nd";
		mmp.addAttribute("mark", "gypx");
		//会话用户
		WhUser userInfo = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
		//取当前会话用户对应的报名id
		WhTraenr traenr = this.signService.getTraenr4KeyandUid(enrid, userInfo.getId());
		if (traenr == null){
			mmp.addAttribute("error", "没有找到给定的用户报名信息");
			return pageVal;
		}
		
		this.signService.clearTemp(enrid, "gypx", request);
		
		mmp.addAttribute("traenr", traenr);
		//相关的培训
		WhTrain train = this.signService.getTrain4Id(traenr.getEnrtraid());
		if (train == null){
			mmp.addAttribute("error", "没有找到报名相关的培训信息");
			return pageVal;
		}
		mmp.addAttribute("train", train);
		//上传模板的文件个数
		String temp = train.getTrapersonfile();
		if (traenr.getEnrtype()!=null && traenr.getEnrtype().compareTo(new Integer(1))==0){
			temp = train.getTrateamfile();
		}
		int fcount = 0;
		if (temp != null && !"".equals(temp)){
			String filePath = UploadUtil.getUploadFileDelPath(UploadUtil.getUploadPath(request), temp);
			fcount = this.signService.getTempFileCount(filePath);
		}
		mmp.addAttribute("fileCount", fcount);
		
		return pageVal;
	}
	
	/** 进入活动报名step2界面
	 * @param bmid
	 * @param mmp
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/evt/step2/{bmid}")
	public String toEventSignStep2(@PathVariable String bmid, ModelMap mmp, HttpServletRequest request, HttpSession session){
		String pageVal = "home/sign/sign2nd";
		mmp.addAttribute("mark", "event");
		//会话用户
		WhUser userInfo = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
		WhActivitybm actbm = this.signService.getEventBmInfo(bmid, userInfo.getId());
		if (actbm == null){
			mmp.addAttribute("error", "没有找到给定的用户报名信息");
			return pageVal;
		}
		
		this.signService.clearTemp(bmid, "event", request);
		
		mmp.addAttribute("actbm", actbm);
		//相关活动信息
		@SuppressWarnings("unchecked")
		Map<String,Object> event = (Map<String, Object>) this.signService.getEventInfo4itmid(actbm.getActvitmid());
		if (event == null){
			mmp.addAttribute("error", "没有找报名相关的活动和场次信息");
			return pageVal;
		}
		mmp.addAttribute("event", event);
		//上传模板文件个数
		String temp = (String) event.get("actvpersonfile");
		if (actbm.getActbmtype()!=null && actbm.getActbmtype().compareTo(new Integer(1))==0){
			temp = (String) event.get("actvteamfile");
		}
		int fcount = 0;
		if (temp != null && !"".equals(temp)){
			String filePath = UploadUtil.getUploadFileDelPath(UploadUtil.getUploadPath(request), temp);
			fcount = this.signService.getTempFileCount(filePath);
		}
		mmp.addAttribute("fileCount", fcount);
		
		return pageVal;
	}
	
	/**
	 * 进入培训报名步骤3界面
	 * @return
	 */
	@RequestMapping("/step3/{enrid}")
	public String toSignStep3(HttpSession session, ModelMap mmp, HttpServletRequest request, @PathVariable String enrid){
		String pageVal = "home/sign/sign3rd";
		mmp.addAttribute("mark", "gypx");
		
		//会话用户
		WhUser userInfo = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
		//取当前会话用户对应的报名id
		WhTraenr traenr = this.signService.getTraenr4KeyandUid(enrid, userInfo.getId());
		if (traenr == null){
			mmp.addAttribute("error", "没有找到给定的用户报名信息");
			return pageVal;
		}
		//this.signService.clearTemp(enrid, request);
		mmp.addAttribute("traenr", traenr);
		
		//是否要收费
		mmp.addAttribute("ismoney", this.signService.findTraenIsmoney(traenr.getEnrtraid()));
		
		Object train = this.signService.getTrain4Id(traenr.getEnrtraid());
		mmp.addAttribute("train", train);
		
		return pageVal;
	}
	
	/**
	 * 进入活动报名步骤3界面
	 * @return
	 */
	@RequestMapping("/evt/step3/{enrid}")
	public String toEventSignStep3(HttpSession session, ModelMap mmp, HttpServletRequest request, @PathVariable String enrid){
		String pageVal = "home/sign/sign3rd";
		mmp.addAttribute("mark", "event");
		//会话用户
		WhUser userInfo = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
		//取当前会话用户对应的报名id
		WhActivitybm actbm = this.signService.getEventBmInfo(enrid, userInfo.getId());
		if (actbm == null){
			mmp.addAttribute("error", "没有找到给定的用户报名信息");
			return pageVal;
		}
		//this.signService.clearTemp(enrid, request);
		mmp.addAttribute("actbm", actbm);
		
		//是否要收费
		mmp.addAttribute("ismoney", this.signService.findActItmIsmoney(actbm.getActvitmid()));
		
		Object event = this.signService.getEventInfo4itmid(actbm.getActvitmid());
		mmp.addAttribute("event", event);
		
		return pageVal;
	}
	
	/**
	 * 提交培训报名步骤1
	 * @param session
	 * @param traenr
	 * @return
	 */
	@RequestMapping("/sendStep1")
	@ResponseBody
	public Object sendStep1(HttpSession session, WhTraenr traenr){
		Map<String, Object> resmap = new HashMap<String, Object>();
		try {
			//获取报名者信息
			WhUser userInfo = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			//验证参数对像
			if (traenr == null) {
				throw new Exception("数据参数丢失");
			}
			if (traenr.getEnrtraid() == null){
				throw new Exception("培训参数丢失");
			}
			if (traenr.getEnrtype() == null){
				throw new Exception("报名类型参数丢失");
			}
			if (userInfo == null){
				throw new Exception("会话信息丢失");
			}
			
			String key = this.signService.addStep1(traenr, userInfo);
			
			resmap.put("success", true);
			resmap.put("msg", key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resmap.put("success", false);
			resmap.put("msg", e.getMessage());
		}
		return resmap;
	}
	
	/** 提交活动报名step1
	 * @param actbm
	 * @param session
	 * @return
	 */
	@RequestMapping("/evt/sendStep1")
	@ResponseBody
	public Object sendEventStep1(WhActivitybm actbm, HttpSession session){
		Map<String, Object> resmap = new HashMap<String, Object>();
		try {
			//获取报名者信息
			WhUser userInfo = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			if (userInfo == null){ 
				throw new Exception("会话丢失"); 
			}
			if (actbm==null || actbm.getActvitmid()==null || actbm.getActbmtype() ==null ){
				throw new Exception("参数丢失");
			}
			
			String key = this.signService.addEventBmStep1(actbm, userInfo);
			resmap.put("success", true);
			resmap.put("msg", key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resmap.put("success", false);
			resmap.put("msg", e.getMessage());
		}
		return resmap;
	}
	
	/**
	 * 处理培训报名步骤2上传文件
	 * @param enrid
	 * @param files
	 * @return
	 */
	@RequestMapping("/upenrfiles")
	@ResponseBody
	public Object upenrfiels(String enrid, String mark, MultipartFile files, HttpServletRequest request){
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			this.signService.addEnrFile2Temp(enrid, mark, files, request);
			res.put("success", true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			res.put("success", false);
		}
		return res;
	} 
	
	/**
	 * 处理报名文件的打包等
	 * @param enrid
	 * @param request
	 * @return
	 */
	@RequestMapping("/optupfiles")
	@ResponseBody
	public Object optUpenrfiels(String enrid, String mark, HttpServletRequest request){
		try {
			this.signService.optEnrFiles(enrid, mark, request);
			return "success";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return "error";
		}
	}
	
	/**
	 * 按指定的ID和报名需要审核参数取得报名人数
	 * @param traid
	 * @param traisenrolqr
	 * @return
	 */
	@RequestMapping("/ajaxGetBaomingCount")
	@ResponseBody
	public Object getBaomingCount(String traid, Integer traisenrolqr){
		if (traid == null || "".equals(traid)){
			return 0;
		}
		return this.signService.getBaomingCount(traid, traisenrolqr);
	}
	
}
