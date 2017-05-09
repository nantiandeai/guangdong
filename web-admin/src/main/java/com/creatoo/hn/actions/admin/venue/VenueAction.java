package com.creatoo.hn.actions.admin.venue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.creatoo.hn.model.WhMgr;
import com.creatoo.hn.model.WhVenuebked;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhVenue;
import com.creatoo.hn.model.WhVenuedate;
import com.creatoo.hn.model.WhVenuetime;
import com.creatoo.hn.services.admin.venue.VenueService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.UploadUtil;

@RestController
@RequestMapping("/admin/ven")
public class VenueAction {
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private VenueService service;
	@Autowired
	private CommService commService;

	/*
	 * 返回视图
	 */
	@RequestMapping("/index")
	public ModelAndView index(){
		
		return new ModelAndView("/admin/venue/venue");
	}
	
	/**
	 * 查找场馆信息
	 * @return
	 */
	@RequestMapping("/findvenue")
	public Object findVenue(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.service.findVenue(paramMap);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
		return rtnMap;
	}
	
	/**
	 * 、保存场馆信息
	 * @param whVenue
	 * @return
	 */
	@RequestMapping("/save")
	public Object saveVenue(WhVenue whVenue,HttpServletRequest req, HttpServletResponse resp, @RequestParam("venpic_up")MultipartFile venpic){
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
			if(venpic != null && !venpic.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, whVenue.getVenpic());
				String imgPath_venpic = UploadUtil.getUploadFilePath(venpic.getOriginalFilename(), commService.getKey("whVenue.picture"), "venue", "picture", now);
				venpic.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_venpic) );
				whVenue.setVenpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_venpic));
			}
			
			//保存
			String venid = whVenue.getVenid();
			if(venid != null && !"".equals(venid.trim())){
				//修改
				this.service.upVenue(whVenue);
			}else{
				//添加
				whVenue.setVenstate(1);
				whVenue.setVenid(this.commService.getKey("wh_venue"));
				this.service.addVenue(whVenue);
			}
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		res.put("success", success);
		res.put("errmsg", errmsg);
		return res;
	}
	
	/**
	 * 删除场馆
	 * @param mageid
	 * @return
	 */
	@RequestMapping("delven")
	public Object deleteVenue(String venid,HttpServletRequest req){
		String uploadPath = UploadUtil.getUploadPath(req);
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		try {
			this.service.delVen(uploadPath,venid);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		res.put("success", success);
		res.put("errmsg", errmsg);
		return res;
	}
	
	/**
	 * 审核和发布
	 * @param traid
	 * @param trastate
	 * @return
	 */
	@RequestMapping("/check")
	public Object publish(int vencanbk,String venid,int fromstate, int tostate,String _is){
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		//添加修改课时
		try {
			//修改
			Object msg = this.service.checkOrBack(vencanbk,venid,fromstate,tostate,_is);
			res.put("success", success);
			res.put("msg", msg);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		res.put("success", success);
		res.put("errmsg", errmsg);
		return res;
	}
	/**
	 * 批量审核和发布
	 * @param traid
	 * @param trastate
	 * @return
	 */
	@RequestMapping("/allcheck")
	public Object allcheck(String venid,int fromstate, int tostate,String _is){
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		
		try {
			
		Object msg = this.service.allCheckOrBack(venid,fromstate,tostate,_is);
		res.put("success", success);
		res.put("msg", msg);	
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		res.put("success", success);
		res.put("errmsg", errmsg);
		return res;
	}
//--------------------------日期管理------------------------------------
	/**
	 * 转到时段管理界面
	 * @param vtid
	 * @return
	 */
	@RequestMapping("/datepage")
	public ModelAndView datepage(String venid){
		
		ModelAndView view = new ModelAndView("/admin/venue/venuedate");
		view.addObject("venid", venid);
		return view;
	}
	/**
	 * 查找场馆信息
	 * @return
	 */
	@RequestMapping("/finddate")
	public Object findDate(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.service.findDate(paramMap);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
		return rtnMap;
	}
	/**
	 * 保存日期
	 * @param whVenuetime
	 * @return
	 */
	@RequestMapping("/savedate")
	public Object saveDate(String venid,WhVenuedate whVenuedate){
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		Object msg = "";
		//添加修改课时
		try {
			//保存
			String vendid = whVenuedate.getVendid();
			if(vendid != null && !"".equals(vendid.trim())){
				//修改
				msg = this.service.upVenDate(venid,whVenuedate);
			}else{
				//添加
				whVenuedate.setVendstate(0);
				whVenuedate.setVendpid(venid);
				whVenuedate.setVendid(this.commService.getKey("wh_venuetime"));
				msg = this.service.addVenDate(venid,whVenuedate);
			}
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		res.put("success", success);
		res.put("msg", msg);
		res.put("errmsg", errmsg);
		return res;
	}
	
	/**
	 * 判断是否能够修改时段信息（如果在预定表中有记录则不能修改）
	 */
	@RequestMapping("/isEditDate")
	public Object isEditDate(String vendid){
		int result = 0;
		try {
			result = this.service.isCanEditDate(vendid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);;
		}
		return result;
		
	}
	/**
	 * 判断是否能够启用时段信息（如果在预定表中有记录则不能修改）
	 */
	@RequestMapping("/isUseDate")
	public Object isUseDate(String vendid){
		int result = 0;
		try {
			result = this.service.isUseDate(vendid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);;
		}
		return result;
		
	}
	
	/**
	 * 删除日期
	 */
	@RequestMapping("delvenDate")
	public Object delVenDate(String vendid,HttpServletRequest req){
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		try {
			this.service.delVenDate(vendid);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		res.put("success", success);
		res.put("errmsg", errmsg);
		return res;
	}
	/**
	 * 启用和停用日期
	 * @param traid
	 * @param trastate
	 * @return
	 */
	@RequestMapping("/checkDate")
	public Object checkDate(String vendid,int fromstate, int tostate){
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		//添加修改课时
		try {
			//修改
			this.service.checkDate(vendid,fromstate,tostate);
			
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		res.put("success", success);
		res.put("errmsg", errmsg);
		return res;
	}
	
//--------------------------时段管理------------------------------------
	/**
	 * 转到时段管理界面
	 * @param vtid
	 * @return
	 */
	@RequestMapping("/timepage")
	public ModelAndView timePage(String venid){
		
		ModelAndView view = new ModelAndView("/admin/venue/ventime");
		view.addObject("venid", venid);
		return view;
	}
	
	/**
	 * 找到时段信息
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("/findtime")
	public Object findTime(HttpServletRequest req,HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.service.findTime(paramMap);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
		return rtnMap;
	}
	
	/**
	 * 保存时段
	 * @param whVenuetime
	 * @return
	 */
	@RequestMapping("/saveTime")
	public Object saveTime(String venid,WhVenuetime whVenuetime){
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		Object msg = "";
		//添加修改课时
		try {
			//保存
			String vtid = whVenuetime.getVtid();
			if(vtid != null && !"".equals(vtid.trim())){
				//修改
				msg = this.service.upVenTime(venid,whVenuetime);
			}else{
				//添加
				whVenuetime.setVtstate(1);
				whVenuetime.setVtid(this.commService.getKey("wh_venuetime"));
				msg = this.service.addVenTime(venid,whVenuetime);
			}
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		res.put("success", success);
		res.put("msg", msg);
		res.put("errmsg", errmsg);
		return res;
		
	}
	/**
	 * 删除时段
	 */
	@RequestMapping("/delvenTime")
	public Object delTime(String vtid){
		return this.service.delTime(vtid);
		
	}
	
	/**
	 * 启用和停用
	 * @param traid
	 * @param trastate
	 * @return
	 */
	@RequestMapping("/checkTime")
	public Object onOrOff(String vtid,int fromstate, int tostate){
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		//添加修改课时
		try {
			//修改
			this.service.onOrOff(vtid,fromstate,tostate);
			
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		res.put("success", success);
		res.put("errmsg", errmsg);
		return res;
	}
	
	/**
	 * 返回场馆预定视图
	 * @return
	 */
	@RequestMapping("/des")
	public ModelAndView toDestine(){
		return new ModelAndView("/admin/venue/destine");
	}
	
	/**
	 * 找到场馆预定
	 * @return
	 */
	@RequestMapping("/finddestine")
	public Object findDestine(HttpServletRequest req,HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.service.finddes(paramMap);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
		return rtnMap;
	}
	
	/**
	 * 删除预定记录
	 * @param vebid
	 * @return
	 */
	@RequestMapping("/deldes")
	public Object delDestine(String vebid){
		return this.service.delDestine(vebid);
	}
	
	/**
	 * 审核预定
	 * @return
	 */
	@RequestMapping("/checkdes")
	public Object checkdes(String vebid,String vebcheckmsg,int vebstate,String name,String venname,String vebuid){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		try {
			this.service.checkDestine(vebid,vebcheckmsg,vebstate,name,venname,vebuid);
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
	 * 批量审核预定不通过
	 * @return
	 */
	@RequestMapping("/allcheckdes")
	public Object allcheckdes(String vebids,int fromstate,int tostate,String vebcheckmsg){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		try {
			//有电话号码就获取电话号码，如果没有就获取邮箱，然后发送短信
			this.service.selPhone(vebids);
			
			this.service.checkAllDestine(vebids,fromstate,tostate,vebcheckmsg);
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
	 * 找到场馆名称
	 * @return
	 */
	@RequestMapping("/findName")
	public Object findVenName(){
		return this.service.findName();
	}
	
	/**
	 * 判断是否能够修改时段信息（如果在预定表中有记录则不能修改）
	 */
	@RequestMapping("/isEdit")
	public Object isEdit(String vtid){
		int result = 0;
		try {
			result = this.service.isCanEdit(vtid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);;
		}
		return result;
		
	}
	/**
	 * 判断预定信息是否能够通过审核
	 */
	@RequestMapping("/isCanCheck")
	public int isCheck(String vebid,String vebvenid,String vebday,String vebstime,String vebetime ){
		int result = 0;
		try {
		result = this.service.isCanCheck(vebid,vebvenid,vebday,vebstime,vebetime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 关联到培训
	 */
	@RequestMapping("/findTra")
	public Object findTrain(){
		Object title = "";
		try {
		title = this.service.findTrain();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return title;
	}
	/**
	 * 关联到活动
	 */
	@RequestMapping("/findAct")
	public Object findAct(){
		Object title = "";
		try {
			title = this.service.findAct();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return title;
	}
	
	/**
	 * 通过id找到用户
	 */
	@RequestMapping("/findUser")
	public Object findUser(String id){
		return this.service.findUser(id);
	}

	/**
	 * 查找指定场馆的预定日期列表
	 */
	@RequestMapping("/loadVenDateList")
	public Object loadVenDateList(String venid){
		try {
			return this.service.selectVenueDateList(venid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ArrayList<Object>();
		}
	}
	/**
	 * 查找指定场馆的预定时段列表
	 */
	@RequestMapping("/loadVenTimeList")
	public Object loadVenTimeList(String vendid){
		try {
			return this.service.selectVenueTimeList(vendid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ArrayList<Object>();
		}
	}
	/**
	 * 查找指定场馆的预定时段列表
	 */
	@RequestMapping("/loadVenTimeBkedList")
	public Object loadVenTimeBkedList(String vendid){
		try {
			return this.service.selectVenueTimeBkedList(vendid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ArrayList<Object>();
		}
	}

	/**
	 * 处理内部预定保存
	 */
	@RequestMapping("/addVenSystimeBked")
	public Object addVenSystimeBked(WhVenuebked bked, HttpSession session){
		Map<String, Object> resMap = new HashMap<String, Object>();
		try{
			//WhMgr admin = (WhMgr) session.getAttribute("user");

			bked.setVebid( this.commService.getKey("WhVenuebked") );
			bked.setVebstate(1);
			bked.setVebordertime(new Date());
			bked.setVebuid("admin");

			this.service.saveVenuebked4Admin(bked);
			resMap.put("success", true);
		} catch (Exception e){
			log.error(e.getMessage(), e);
			resMap.put("success", false);
		}
		return resMap;
	}
	/**
	 * 处理内部预定删除
	 */
	@RequestMapping("/delVenSystimeBked")
	public Object delVenSystimeBked(WhVenuebked bked, HttpSession session){
		Map<String, Object> resMap = new HashMap<String, Object>();
		try{
			this.service.removeVenuebked4Admin(bked);
			resMap.put("success", true);
		} catch (Exception e){
			log.error(e.getMessage(), e);
			resMap.put("success", false);
		}
		return resMap;
	}

}
