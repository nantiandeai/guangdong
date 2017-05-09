package com.creatoo.hn.actions.home.venue;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.creatoo.hn.model.WhCfgAdv;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.services.home.art.ArtService;
import com.creatoo.hn.services.home.venue.VenueListService;
import com.creatoo.hn.services.home.venue.VenuesService;
import com.creatoo.hn.utils.WhConstance;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/venue")
public class VenuesAction {
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private VenuesService venueService;
	
	@Autowired
	private VenueListService venueListService;
	
	@Autowired
	private ArtService artService;
	
	/** 进入场馆详情界面
	 * @param venid
	 * @param mmp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/order/{venid}")
	public String toVenueOrder(@PathVariable String venid, ModelMap mmp){
		//场馆信息
		Map<String,Object> venue = (Map<String,Object>) this.venueService.findVenue(venid);
		mmp.addAttribute("venue", venue);
		//场馆时段信息
		List<Object> venueTimes = this.venueService.selectVenueTime(venid);
		mmp.addAttribute("venueTimes", venueTimes);
		//可预订之后多少天
		mmp.addAttribute("lastDayNum", WhConstance.getSysProperty("VENUE_LAST_DAY", "0") );
		//图片资源
		mmp.addAttribute("srclist", this.venueService.selectVenueEntSrc(venid));
		try {
			//用途列表
			if (venue!=null){
				String venscope = (String) venue.get("venscope");
				mmp.addAttribute("venscopelist", artService.srchTags(venscope) );
			}
			//广告图
			List<WhCfgAdv> venlist=this.venueListService.advLoad();
			if (venlist != null && venlist.size() > 0) {
				mmp.addAttribute("venueAdv", venlist.get(0));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return "home/venue/venueOrder";
	}
	
	/** 查询场馆指定时段的预订信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/order/bked")
	@ResponseBody
	public Object getVenueBked(WebRequest request){
		String venid = request.getParameter("venid");
		String bday = request.getParameter("bday");
		String daynum = request.getParameter("daynum");
		try {
			int lastNum = Integer.parseInt(daynum);
			return this.venueService.selectVenueBked(venid, bday, lastNum);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	
	/** 场馆指定用户的未审核通过预订信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/order/bkednock")
	@ResponseBody
	public Object getNockUserBked(WebRequest request){
		String venid = request.getParameter("venid");
		String uid = request.getParameter("uid");
		String bday = request.getParameter("bday");
		String daynum = request.getParameter("daynum");
		try {
			int lastNum = Integer.parseInt(daynum);
			return this.venueService.selectVenueBkedNotcheck4User(venid, uid, bday, lastNum);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	
	/** 处理预定提交
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/order/addbked")
	@ResponseBody
	public Object saveUserBkedList(WebRequest request, HttpSession session){
		Map<String, Object> res = new HashMap<String, Object>();
		WhUser user = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
		if (user == null){
			res.put("success", false);
			res.put("msg", "0");
			return res;
		}
		String uid = user.getId();
		String bkedarr = request.getParameter("bkedArrayStr");
		
		try {
			ObjectMapper om = new ObjectMapper();
			
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> bkedlist = om.readValue(bkedarr, List.class);
			
			this.venueService.saveVenueBkedList(uid, bkedlist);
			
			res.put("success", true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		
		return res;
	}



	/**
	 * 加载指定天的场馆时段集 new
	 * @param request
	 * @return
	 */
	@RequestMapping("/loadVenueTimes4Day")
	@ResponseBody
	public Object loadVenueTimes4Day(WebRequest request){
		try {
			String venid = request.getParameter("venid");
			String day = request.getParameter("day");
			if (venid==null || day==null){
				throw new Exception("not params venid or day");
			}
			SimpleDateFormat sdf = new SimpleDateFormat();
			sdf.applyPattern("yyyy-MM-dd");
			Date pdate = sdf.parse(day);

			return this.venueService.selectVenueTimes4Day(venid, pdate);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Object>();
		}
	}

	/**
	 * 加载指定天的场馆已审预定信息
	 * @param request
	 * @return
     */
	@RequestMapping("/loadVenueBked4Day")
	@ResponseBody
	public Object loadVenueBked4Day(WebRequest request){
		try {
			String venid = request.getParameter("venid");
			String day = request.getParameter("day");
			if (venid==null || day==null){
				throw new Exception("not params venid or day");
			}
			SimpleDateFormat sdf = new SimpleDateFormat();
			sdf.applyPattern("yyyy-MM-dd");
			Date pdate = sdf.parse(day);

			return this.venueService.selectVenueBked4Day(venid, pdate);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Object>();
		}
	}

	/**
	 * 加载指定天的场馆当前用户的预定未通过信息
	 */
	@RequestMapping("/loadVenueBked4DayUserBide")
	@ResponseBody
	public Object loadVenueBked4DayUserBide(WebRequest request, HttpSession session){
		try {
			String venid = request.getParameter("venid");
			String day = request.getParameter("day");
			if (venid==null || day==null){
				throw new Exception("not params venid or day");
			}
			SimpleDateFormat sdf = new SimpleDateFormat();
			sdf.applyPattern("yyyy-MM-dd");
			Date pdate = sdf.parse(day);

			WhUser user = (WhUser)session.getAttribute(WhConstance.SESS_USER_KEY);
			if (user == null){
				return new ArrayList<Object>();
			}

			return this.venueService.selectVenueBked4DayUserBide(venid, pdate, user.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Object>();
		}
	}
}
