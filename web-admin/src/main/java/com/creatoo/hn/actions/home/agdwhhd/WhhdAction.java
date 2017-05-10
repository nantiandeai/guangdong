package com.creatoo.hn.actions.home.agdwhhd;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOrderType;
import com.creatoo.hn.ext.emun.EnumTypeClazz;
import com.creatoo.hn.model.WhActivity;
import com.creatoo.hn.model.WhActivityitm;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.model.WhZxUpload;
import com.creatoo.hn.model.WhgActActivity;
import com.creatoo.hn.model.WhgActOrder;
import com.creatoo.hn.model.WhgActSeat;
import com.creatoo.hn.model.WhgActTime;
import com.creatoo.hn.model.WhgComResource;
import com.creatoo.hn.model.WhgUsrBacklist;
import com.creatoo.hn.model.WhgYwiType;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.comm.SMSService;
import com.creatoo.hn.services.home.agdgwgk.GwgkService;
import com.creatoo.hn.services.home.agdwhhd.WhhdService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.WhConstance;

/**
 * 文化活动
 * @author wangxl
 * @version 2016.11.16
 */
@RestController
@RequestMapping("/agdwhhd")
public class WhhdAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * 公用服务类
	 */
	@Autowired
	public CommService commservice;
	
	/**
	 * 文化活动service
	 */
	@Autowired
	private WhhdService WhhdService;
	
	/**
	
	/**
	 * 馆务公开服务类
	 */
	@Autowired
	public GwgkService gwgkService;
	
	/**
	 * 短信公开服务类
	 */
	@Autowired
	private SMSService smsService;
	
	
	/**
	 * 首页-文化活动
	 * @return 文化活动
	 */  
	@SuppressWarnings("rawtypes")
	@RequestMapping("/index")
	public ModelAndView index(String actvtype){
		//新建视图对象
		ModelAndView view = new ModelAndView( "home/agdwhhd/activitylist" );
		try {
			//活动分类
			List<WhgYwiType> acttype = this.commservice.findYwiType(EnumTypeClazz.TYPE_ACTIVITY.getValue());
			view.addObject("acttype", acttype);

			//艺术分类
			List<WhgYwiType> ystype = this.commservice.findYwiType(EnumTypeClazz.TYPE_ART.getValue());
			view.addObject("ystype", ystype);

			//区域分类
			List<WhgYwiType> qrtype = this.commservice.findYwiType(EnumTypeClazz.TYPE_AREA.getValue());
			view.addObject("qrtype", qrtype);

			if(actvtype != null && !"".equals(actvtype)){
				view.addObject("actvtype",actvtype);
			};
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 活动公告
	 * @return 活动公告
	 */
	@RequestMapping("/notice")
	public ModelAndView notice(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView view = new ModelAndView( "home/agdwhhd/notice" );
		try {
			//资讯栏目
			String realtype = "2016111900000012";
			if(req.getParameter("type") != null){
				realtype = req.getParameter("type");
			}
			
			//获取请求参数
			Map<String, Object> param = ReqParamsUtil.parseRequest(req);
			
			//分页查询
	        Map<String, Object> rtnMap = this.gwgkService.paggingColinfo(param, realtype);
	        try {
				view.addObject("total", rtnMap.get("total"));
				view.addObject("rows", rtnMap.get("rows"));
				view.addObject("page", rtnMap.get("page"));
			} catch (Exception e) {
				view.addObject("total", 0);
				view.addObject("rows", null);
				view.addObject("page", 1);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 活动公告详情
	 * @return 活动公告详情
	 */
	@RequestMapping("/noticeinfo")
	public ModelAndView noticeinfo(String id, String type){
		ModelAndView view = new ModelAndView( "home/agdwhhd/noticeinfo" );
		try {
			String realtype = "2016111900000012";
			if(type != null){
				realtype = type;
			}
			
			//当前资讯
			view.addObject("wh_zx_colinfo", this.gwgkService.queryOneColinfo(id, realtype));
			
			//next
			view.addObject("wh_zx_colinfo_next", this.gwgkService.queryNextColinfo(id, realtype));
			
			//相关推荐
			view.addObject("wh_zx_colinfo_ref", this.gwgkService.queryREFColinfo(id, realtype));

			//图片
			List<WhgComResource> pic = this.commservice.findRescource("1","11", id);
			view.addObject("loadwhe",pic );
			//视频
			List<WhgComResource> vido = this.commservice.findRescource("2","11", id);
			view.addObject("loadent",vido );
			//音频
			List<WhgComResource> musci = this.commservice.findRescource("3","11", id);
			view.addObject("loadclazz",musci );

			//上传
			List<WhZxUpload> whup =  this.gwgkService.selecup(id);
			view.addObject("loadlist", whup);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 活动资讯
	 * @return 活动资讯
	 */
	@RequestMapping("/news")
	public ModelAndView news(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView view = new ModelAndView( "home/agdwhhd/news" );
		try {
			//资讯栏目
			String realtype = "2016111900000018";
			if(req.getParameter("type") != null){
				realtype = req.getParameter("type");
			}
			
			//获取请求参数
			Map<String, Object> param = ReqParamsUtil.parseRequest(req);
			
			//分页查询
	        Map<String, Object> rtnMap = this.gwgkService.paggingColinfo(param, realtype);
	        try {
				view.addObject("total", rtnMap.get("total"));
				view.addObject("rows", rtnMap.get("rows"));
				view.addObject("page", rtnMap.get("page"));
			} catch (Exception e) {
				view.addObject("total", 0);
				view.addObject("rows", null);
				view.addObject("page", 1);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 活动资讯详情
	 * @return 活动资讯详情
	 */
	@RequestMapping("/newsinfo")
	public ModelAndView newsinfo(String id, String type){
		ModelAndView view = new ModelAndView( "home/agdwhhd/newsinfo" );
		try {
			String realtype = "2016111900000018";
			if(type != null){
				realtype = type;
			}
			
			//当前资讯
			view.addObject("wh_zx_colinfo", this.gwgkService.queryOneColinfo(id, realtype));
			
			//next
			view.addObject("wh_zx_colinfo_next", this.gwgkService.queryNextColinfo(id, realtype));
			
			//相关推荐
			view.addObject("wh_zx_colinfo_ref", this.gwgkService.queryREFColinfo(id, realtype));
			//上传
			List<WhZxUpload> whup =  this.gwgkService.selecup(id);
			view.addObject("loadlist", whup);
			//资源图片
//			String enttype="2016101400000055";
//			String reftype = "2016102800000001";
//			List<WhEntsource> whe= this.gwgkService.selecent(id,enttype,reftype);
//			view.addObject("loadwhe", whe);
//			//资源视频
//			String types="2016101400000056";
//			List<WhEntsource> ent= this.gwgkService.selecsource(id,types,reftype);
//			view.addObject("loadent", ent);
//			//资源音频
//			String clazz="2016101400000057";
//			List<WhEntsource> whent= this.gwgkService.selecwhent(id,clazz,reftype);
//			view.addObject("loadclazz", whent);

			//图片
			List<WhgComResource> pic = this.commservice.findRescource("1","11", id);
			view.addObject("loadwhe",pic );
			//视频
			List<WhgComResource> vido = this.commservice.findRescource("2","11", id);
			view.addObject("loadent",vido );
			//音频
			List<WhgComResource> musci = this.commservice.findRescource("3","11", id);
			view.addObject("loadclazz",musci );
			
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 品牌活动列表
	 * @return 品牌活动列表
	 */
	@RequestMapping("/brandlist")
	public ModelAndView brandlist(String actvtype){
		//新建视图对象
		ModelAndView view = new ModelAndView( "home/agdwhhd/brandlist"  );
		try {
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	

	
	/**
	 * 活动列表
	 * @return 活动列表
	 */
	@RequestMapping("/activitylist")
	public ModelAndView activitylist(String actvtype){
		//新建视图对象
		ModelAndView view = new ModelAndView( "home/agdwhhd/activitylist" );
		try {
			//活动分类
	    	List<WhgYwiType> acttype = this.commservice.findYwiType(EnumTypeClazz.TYPE_ACTIVITY.getValue());
	    	view.addObject("acttype", acttype);
	    	
	    	//艺术分类
	    	List<WhgYwiType> ystype = this.commservice.findYwiType(EnumTypeClazz.TYPE_ART.getValue());
	    	view.addObject("ystype", ystype);
	    	
	    	//区域分类
	    	List<WhgYwiType> qrtype = this.commservice.findYwiType(EnumTypeClazz.TYPE_AREA.getValue());
	    	view.addObject("qrtype", qrtype);
	    	
	    	if(actvtype != null && !"".equals(actvtype)){
	    		view.addObject("actvtype",actvtype);
	    	};
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 活动详情
	 * @return 活动详情
	 */
	@RequestMapping("/activityinfo")
	public ModelAndView activityinfo(String actvid,WebRequest request){
		ModelAndView view = new ModelAndView( "home/agdwhhd/activityinfo" );
		try {
			//活动详情
			WhgActActivity actdetail = this.WhhdService.getActDetail(actvid);
			view.addObject("actdetail",actdetail);
			view.addObject("enumtypeAct",EnumTypeClazz.TYPE_ACTIVITY.getValue());

			//活动场次 时间
			List<WhgActTime> actvitm = this.WhhdService.getActTimeList(actvid,null);
			String timeStr = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm");
			if(actvitm != null && actvitm.size() >0){
				for (int i = 0; i < actvitm.size(); i++) {
					WhgActTime whgActTime = actvitm.get(i);
					timeStr += sdf.format(whgActTime.getPlaystarttime())+";";
				}
				timeStr = timeStr.substring(0,timeStr.length() - 1);
				view.addObject("timeStr", timeStr);
			}
			//活动资源 图片
			List<WhgComResource> tsource = this.WhhdService.selectactSource(actvid,"1","2");
			view.addObject("tsource", tsource);
			
			//活动资源 音频
			List<WhgComResource> ysource = this.WhhdService.selectactSource(actvid,"3","2");
			view.addObject("ysource", ysource);
			//活动资源 视频
			List<WhgComResource> ssource = this.WhhdService.selectactSource(actvid,"2","2");
			view.addObject("ssource", ssource);

			//上传
			List whup =  this.WhhdService.serch(actvid);
			view.addObject("loadlist", whup);
			
			//活动推荐
			List<WhActivity> acttj = this.WhhdService.acttjian(request);
			view.addObject("acttj", acttj);

            //相关资讯
            List colinfolist = this.commservice.findColinfo(actvid,"2016111900000018");
            view.addObject("wh_zx_colinfo_ref", colinfolist);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 品牌活动列表页 加载相关数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/ppactivityList")
	public Object ppactivityList(WebRequest request){
		try {
			return this.WhhdService.selectBrandList(request);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * dg 文化活动list 加载数据
	 * @param page
	 * @param rows
	 * @param request
	 * @return
	 */
	@RequestMapping("/activityload")
	public Object activityList(int page,int rows,WebRequest request){
		try {
			return this.WhhdService.activityList(page,rows,request);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 根据活动id 查活动信息 品牌详情页
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectactDetail")
	public Object  selectactDetail(WebRequest request){
		try {
			return this.WhhdService.selectactDetail(request);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return new ArrayList<Object>();
		}
	}
	
	
	/**
	 * 活动报名界面
	 * @param actvId
	 * @return
	 */
	@RequestMapping("/actBaoMing")
	public ModelAndView actBaoMing(String actvid,WebRequest request){
		ModelAndView view = new ModelAndView( "home/agdwhhd/act_baoming_step1" );
		try {
			//活动详情
			WhgActActivity actdetail = this.WhhdService.getActDetail(actvid);
			view.addObject("actdetail",actdetail);

			//活动场次 时间
			List<WhgActTime> actvitm = this.WhhdService.getPlayDate4ActId(actvid);
			
			view.addObject("timeDateList", actvitm);
			view.addObject("totalSeatSize",actvitm.get(0).getSeats());
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 场次更改时，重新加载活动座位信息
	 * @param actId
	 * @param seatId
	 * @return
	 */
	@RequestMapping("/changeSeat")
	public Object changeSeat(String actId,String eventId){
		Map<String,Object> res = new HashMap<String, Object>();
		try {
			WhgActTime whgActTime = WhhdService.selectOnePlay(eventId);
			List<WhgActTime> timePlayList = WhhdService.getActTimeList(actId,whgActTime.getPlaydate());
			List<WhgActTime> newTimePlayList = new ArrayList<>();
			//将小于当前时间的场次过滤
			for(WhgActTime actTime : timePlayList ){
				Date playStartTime = actTime.getPlaystarttime();
				Date nowDate = new Date();
				if(nowDate.getTime() < playStartTime.getTime()){
					newTimePlayList.add(actTime);
				}
			}
			res.put("timePlayList", newTimePlayList);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
		return res;
	}
	
	@RequestMapping("/changePlay")
	public Object changePlay(String actId,String eventId,HttpSession session){
		Map<String,Object> res = new HashMap<String, Object>();
		WhUser user = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
		try {
			JSONObject seatJson = WhhdService.getSeat4ActId(actId,eventId,user.getId());
			WhgActTime whgActTime = WhhdService.selectOnePlay(eventId);
			res.put("mapType", seatJson.get("mapType"));
			res.put("statusMap", seatJson.get("statusMap"));
			res.put("seatSize",seatJson.get("seatSize"));
			res.put("totalSeatSize",whgActTime.getSeats());
			res.put("seatSizeUser",seatJson.get("seatSizeUser"));
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
		return res;
	}
	
	/**
	 * 保存活动报名信息
	 * @param request
	 * @return 
	 */
	@RequestMapping("/saveActBmInfo")
	public ModelAndView saveActBmInfo(String actId,WhgActOrder actOrder,WebRequest request,HttpSession session){
		ModelAndView view = new ModelAndView( "home/agdwhhd/act_baoming_step3" );
		WhgActActivity whgActActivity = WhhdService.getActDetail(actId);
		WhUser user = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
		try {
			String id = this.commservice.getKey("WhgActOrder");
			actOrder.setId(id);
			actOrder.setOrdernumber(this.commservice.getOrderId(EnumOrderType.ORDER_ACT.getValue()));
			if(user !=null){
				actOrder.setUserid(user.getId());
			}
			this.WhhdService.addActOrder(actId, actOrder);
			WhgActTime actTime = WhhdService.selectOnePlay(actOrder.getEventid());
			
			Map<String,Object> map = WhhdService.findOrderInfo4Id(id);
			String mySelectSeat = request.getParameter("seatStr");
			int seatNum = Integer.parseInt(request.getParameter("seats")); 
			String[] selectSeat = mySelectSeat.split(",");
			int totalSeat = selectSeat.length;
			if(seatNum > 0){
				totalSeat = seatNum;
			}
			int sum_ = 1;
			for(int i=0;i<totalSeat;i++){
				if(seatNum > 0){//自由入座
					WhhdService.saveSeatOrder("P"+sum_, id,"票"+sum_);
				}else{ //在线选座
					WhgActSeat whgActSeat =WhhdService.getWhgActTicket4ActId(actId, selectSeat[i]);
					WhhdService.saveSeatOrder(whgActSeat.getId(), id,whgActSeat.getSeatnum());
				}
				sum_++;
			}
			//发送短信
			Map<String, String> smsData = new HashMap<String, String>();
			smsData.put("userName", actOrder.getOrdername());
			smsData.put("activityName", whgActActivity.getName());
			smsData.put("ticketCode", actOrder.getOrdernumber());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = actTime.getPlaydate();
			String dateStr = sdf.format(date);
			smsData.put("beginTime", dateStr +" "+ actTime.getPlaystime());
			int num = 0;
			if(totalSeat > 0){
				num=totalSeat;
			}else{
				num = seatNum;
			}
			smsData.put("number", String.valueOf(num));
			smsService.t_sendSMS(actOrder.getOrderphoneno(), "ACT_DUE", smsData);
			//短信发送成功后更改订单短信状态
			WhhdService.upActOrder(actOrder);
			view.addObject("order", map);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 *	获取场次活动时间
	 * @param actvitmid
	 * @return
	 */
	@RequestMapping("/selectOneitm")
	public Object selectOneitm(String actvitmid){
		Map<String,Object> res = new HashMap<String, Object>();
		try {
			WhActivityitm actitem = this.WhhdService.selectOneitm(actvitmid);
			res.put("actitem",actitem);
			res.put("leaveCount",selectCount(actvitmid));
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
		return res;
	}
	
	/**
	 * 根据 活动场次id 查找 余票/人数
	 * @param actvitmid
	 * @return
	 */
	@RequestMapping("/getLeaveCount")
	public int selectCount(String actvitmid){
		return this.WhhdService.selectCount(actvitmid);
	}
	
	
	@RequestMapping(value= "/checkActPublish")
    public Object checkActPublish(HttpServletRequest request,String actId,HttpSession session){
    	ResponseBean res = new ResponseBean();
    	try {
			WhgActActivity whgAct = WhhdService.getActDetail(actId);
			if(whgAct.getState() != 6){
				res.setSuccess(ResponseBean.FAIL);
				res.setErrormsg("该活动已下架！");
			}
			WhUser user = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
			//查询当前活动下，该用户取消次数
			List<WhgActOrder> actOrderList = WhhdService.findOrderList4Id(actId, user.getId());
			//查询该用户取消的所有活动次数
			List<WhgActOrder> orderList = WhhdService.findOrderList4Id(null, user.getId());
			//如果用户一个活动取消两次或者一个用户统计取消活动订单超过10次则加入黑名单
			if(actOrderList.size()>=2 || orderList.size() >= 10 ){
				WhgUsrBacklist userBack = new WhgUsrBacklist();
				userBack.setUserid(user.getId());
				userBack.setState(1);
				List<WhgUsrBacklist> userBackList = WhhdService.findWhgUsrBack4UserId(userBack);
				if(userBackList.size() < 1){
					userBack.setId(this.commservice.getKey("WhgUsrBacklist"));
					userBack.setState(1);
					userBack.setJointime(new Date());
					userBack.setType(1);
					userBack.setUserid(user.getId());
					userBack.setUserphone(user.getPhone());
					WhhdService.addUserBack(userBack);
				}
				res.setSuccess(ResponseBean.FAIL);
				res.setErrormsg("您已经被系统限制执行操作，如需了解详细情况，请联系管理员！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return res;
    	
    }
	
}
