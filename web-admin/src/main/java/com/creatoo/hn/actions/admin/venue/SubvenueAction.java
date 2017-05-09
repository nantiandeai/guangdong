package com.creatoo.hn.actions.admin.venue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhSubvenue;
import com.creatoo.hn.services.admin.venue.SubService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.UploadUtil;
@RestController
@RequestMapping("/admin/sub")
public class SubvenueAction {
	
	Logger log = Logger.getLogger(getClass());
	@Autowired
	private SubService service;
	
	@Autowired
	private CommService commService;
	
	
	@RequestMapping("/index")
	public ModelAndView toPage(){
		return new ModelAndView("/admin/venue/subvenue");
	}
	
	/**
	 * 找到所有的子馆
	 * @return
	 */
	@RequestMapping("/findsub")
	public Object findSubVenue(HttpSession session,HttpServletRequest req){
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.service.findVenue(session,paramMap);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
		return rtnMap;
	}
	
	/**
	 * 添加或者修改
	 * @return
	 */
	@RequestMapping("save")
	public Object saveSub(HttpSession session,HttpServletRequest req,WhSubvenue whSubvenue,@RequestParam("subpic_up")MultipartFile subpic,@RequestParam("subbigpic_up")MultipartFile subbigpic){
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		//添加修改课时
		try {
			//当前日期
			Date now = new Date();
			//图片或者文件处理
			String uploadPath = UploadUtil.getUploadPath(req);
			//图片处理
			//trapic
			if(subpic != null && !subpic.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, whSubvenue.getSubpic());
				String imgPath_venpic = UploadUtil.getUploadFilePath(subpic.getOriginalFilename(), commService.getKey("whVenue.picture"), "venue", "picture", now);
				subpic.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_venpic) );
				whSubvenue.setSubpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_venpic));
			}
			if(subbigpic != null && !subbigpic.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, whSubvenue.getSubbigpic());
				String imgPath_venpic = UploadUtil.getUploadFilePath(subbigpic.getOriginalFilename(), commService.getKey("whVenue.picture"), "venue", "picture", now);
				subbigpic.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_venpic) );
				whSubvenue.setSubbigpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_venpic));
			}
			
			//保存
			String subid = whSubvenue.getSubid();
			if(subid != null && !"".equals(subid.trim())){
				//修改
				this.service.updateSub(whSubvenue);
			}else{
				//添加
				whSubvenue.setSubstate(1);
				whSubvenue.setSubid(this.commService.getKey("wh_subvenue"));
				this.service.addSub(session,whSubvenue);
			}
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
			log.error(e.getMessage(), e);
		}
		res.put("success", success);
		res.put("errmsg", errmsg);
		return res;
	}
	
	/**
	 * 删除志愿培训
	 * @param zypxid
	 * @param req
	 * @return
	 */
	@RequestMapping("/delsub")
	public Object delZypx(String subid,HttpServletRequest req){
		String uploadPath = UploadUtil.getUploadPath(req);
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		try {
			this.service.deleteZypx(uploadPath,subid);
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
	 * 审核或者取消操作
	 */
	@RequestMapping("/check")
	public Object sendCheck(String subid, int fromstate, int tostate){
		Map<String, Object> res = new HashMap<String, Object>();
		//添加修改课时
		try {
			//修改
			Object msg = this.service.checkTeacher(subid, fromstate, tostate);
			res.put("success", "success");
			res.put("msg", msg);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			res.put("error", "error"); 
		}
		return res;
	}
	
	/**
	 * 批量审核或者取消操作操作
	 */
	@RequestMapping("/checkAll")
	public Object CheckAll(String subids, int fromstate, int tostate){
		Map<String, Object> res = new HashMap<String, Object>();
		//添加修改课时
		try {
			//修改
			Object msg = this.service.checkAllTeacher(subids, fromstate, tostate);
			res.put("success", "success");
			res.put("msg", msg);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			res.put("error", "error"); 
		}
		return res;
	}
}
