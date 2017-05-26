package com.creatoo.hn.actions.home.agdcgfw;


import com.creatoo.hn.ext.emun.EnumTagClazz;
import com.creatoo.hn.ext.emun.EnumTypeClazz;
import com.creatoo.hn.model.*;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.comm.SMSService;
import com.creatoo.hn.services.home.agdcgfw.CgfwService;
import com.creatoo.hn.utils.WhConstance;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;


/**
 * 场馆服务
 * @author wangxl
 * @version 2016.11.16
 */
@RestController
@RequestMapping("/agdcgfw")
public class CgfwAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * 公用服务类
	 */
	@Autowired
	private CommService commservice;

	@Autowired
	private CgfwService service;


	/**
	 * 首页-场馆列表
	 * @return 首页-场馆列表
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request){
		ModelAndView view = new ModelAndView( "home/agdcgfw/index" );
		try {	

            //场馆分类
            List<WhgYwiType> types = this.commservice.findYwiType(EnumTypeClazz.TYPE_VENUE.getValue());
            //场馆标签
            List<WhgYwiTag> tags = this.commservice.findYwiTag(EnumTagClazz.TAG_VENUE.getValue());
            //区域
            List<WhgYwiType> areas = this.commservice.findYwiType(EnumTypeClazz.TYPE_AREA.getValue());

            view.addObject("types", types);
            view.addObject("tags", tags);
            view.addObject("areas", areas);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 场馆预定列表页数据分页
	 */
	@RequestMapping("/venlist")
	public Object Srchlist(HttpServletRequest req,HttpServletResponse res){

		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {

            Map<String, Object> params = new HashMap();
            Map<String, String[]> reqMap = req.getParameterMap();
            for(Map.Entry<String, String[]> entry : reqMap.entrySet()){
                String[] values = entry.getValue();
                String key = entry.getKey();
                if (values!=null && values.length > 0){
                    params.put(key, values[0]);
                }
            }

            rtnMap = this.service.findList(params);

		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
        return rtnMap;
		
	}
	
	/**
	 * 场馆详情
	 * @return 场馆详情
	 */
	@RequestMapping("/venueinfo")
	public ModelAndView venueinfo(HttpServletRequest request){
		ModelAndView view = new ModelAndView( "home/agdcgfw/venueinfo" );
		try {	

            String venid = request.getParameter("venid");
            //场馆信息
            view.addObject("venue", this.service.findWhgVen4Id(venid));
            //推荐场馆列表
            view.addObject("venlist", this.service.selectWhgVen4Recommend(null, venid));
            //相关活动室列表
            view.addObject("roomlist", this.service.selectWhgVenroom4Ven(venid));

            //场馆类型标记
            view.addObject("enumtypevenue", EnumTypeClazz.TYPE_VENUE.getValue());

            //场馆图片
            view.addObject("venImgList", this.commservice.findRescource("1","3", venid) );
            //场馆视频
            view.addObject("venVideoList", this.commservice.findRescource("2","3", venid) );
            //场馆音频
            view.addObject("venAudioList", this.commservice.findRescource("3","3", venid) );
            //场馆音频
            view.addObject("doc", this.commservice.findRescource("4","3", venid) );

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}

    /**
     * 活动室详情页
     * @param request
     * @return
     */
    @RequestMapping("/venroominfo")
    public ModelAndView venroominfo(WebRequest request){
        ModelAndView view = new ModelAndView("home/agdcgfw/venroominfo");

        try {
            String roomid = request.getParameter("roomid");
            //活动室信息
            WhgVenRoom room = this.service.findWhgVenroom4Id(roomid);
            view.addObject("room", room);

            //所属场馆
            view.addObject("venue", this.service.findWhgVen4Id(room.getVenid()));

            //活动室分类标记
            view.addObject("enumtyperoom", EnumTypeClazz.TYPE_ROOM.getValue());

            //相关活动室列表
            view.addObject("nextroomlist", this.service.selectWhgVenroom4Room(room));

            //活动室图片
            view.addObject("roomImgList", this.commservice.findRescource("1","4", roomid) );
            //活动室视频
            view.addObject("roomVideoList", this.commservice.findRescource("2","4", roomid) );
            //活动室音频
            view.addObject("roomAudioList", this.commservice.findRescource("3","4", roomid) );

            //推荐场馆列表
            view.addObject("venlist", this.service.selectWhgVen4Recommend(null, null));

            //有效的开放时段个数
            int roomtimeCount = this.service.countRoomTimeOpen4Room(roomid);
            view.addObject("roomtimeCount", roomtimeCount);
            if (roomtimeCount > 0){
                //有效预订的最后与开始时间
                Map seday = this.service.roomTimeOpenSEday(roomid);
                view.addAllObjects(seday);
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return view;
    }

    /**
     * 活动室预定时段列表
     * @param bday
     * @param eday
     * @return
     */
    @RequestMapping("/loadRoomTimes")
    public Object loadRoomTimes(@DateTimeFormat(pattern="yyyy-MM-dd")Date bday,
                                @DateTimeFormat(pattern="yyyy-MM-dd")Date eday,
                                String roomid, HttpSession session){
        Map<String, Object> rest = new HashMap<String, Object>();
        //装入当前时间
        rest.put("nowDate", new Date());
        //预定时段
        rest.put("roomTimes", new ArrayList());
        //成功预订的订单
        rest.put("roomOrderOK", new ArrayList());
        try {
            rest.put("roomTimes", this.service.selectWhgVenroomtime(roomid, bday, eday) );
            rest.put("roomOrderOK", this.service.selectWhgVenroomorder4OK(roomid, bday, eday) );

            WhUser user = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
            if (user!= null){
                rest.put("roomOrderUser", this.service.selectWhgVenroomorder4User(roomid, bday, eday, user.getId()) );
            }

        } catch (Exception e) {
            log.error("loadRoomTimes error", e);
        }
        return rest;
    }

    /**
     * 进入活动室预订1
     * @param roomtimeid
     * @return
     */
    @RequestMapping("/roomOrder1")
    public Object roomOrder1(HttpSession session, String roomtimeid,
                             @RequestParam(required = false) String userphone,
                             @RequestParam(required = false) String purpose,
                             @RequestParam(required = false) String username){
        ModelAndView view = new ModelAndView("home/agdcgfw/venroomorder1");
        try {
            Object user = session.getAttribute(WhConstance.SESS_USER_KEY);
            if (user == null){
                return new ModelAndView("redirect:/login");
            }

            this.roomOrderDate(view, roomtimeid, userphone, username, purpose);
        } catch (Exception e) {
            log.error("roomOrder1 error", e);
        }

        return view;
    }
    @RequestMapping("/roomOrder2")
    public Object roomOrder2(HttpSession session, String roomtimeid, String userphone, String username, String purpose){
        ModelAndView view = new ModelAndView("home/agdcgfw/venroomorder2");
        try {
            Object user = session.getAttribute(WhConstance.SESS_USER_KEY);
            if (user == null){
                return new ModelAndView("redirect:/login");
            }

            this.roomOrderDate(view, roomtimeid, userphone, username, purpose);
        } catch (Exception e) {
            log.error("roomOrder2 error", e);
        }
        return view;
    }
    @RequestMapping("/roomOrder3")
    public Object roomOrder3(HttpSession session, String roomtimeid, String userphone, String username, String purpose){
        ModelAndView view = new ModelAndView("home/agdcgfw/venroomorder3");
        try {
            Object user = session.getAttribute(WhConstance.SESS_USER_KEY);
            if (user == null){
                return new ModelAndView("redirect:/login");
            }

            this.roomOrderDate(view, roomtimeid, userphone, username, purpose);
        } catch (Exception e) {
            log.error("roomOrder3 error", e);
        }
        return view;
    }

    @RequestMapping("/checkRoomOrder")
    public Object checkRoomOrder(String roomtimeid, HttpSession session){
        Map<String, Object> rest = new HashMap();

        try {
            //获取 user
            WhUser user = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);

            this.service.testUserVenroomtimeInfo(user, roomtimeid, null, null, null);

            rest.put("success", true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            rest.put("success", false);
            rest.putAll( this.service.praseTestError(e.getMessage()) );
        }

        return rest;
    }

    @RequestMapping("/saveRoomOrder")
    public Object saveRoomOrder(String roomtimeid, String userphone, String username, String purpose, HttpSession session){
        Map<String, Object> rest = new HashMap();
        try {
            WhUser user = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);

            this.service.saveUserVenroomtimeInfo(roomtimeid, user, userphone, username, purpose);
            rest.put("success", true);

        } catch (Exception e) {
            log.error("roomOrder3 error", e);
            rest.put("success", false);
            rest.putAll( this.service.praseTestError(e.getMessage()) );
        }
        return rest;
    }

    private void roomOrderDate(ModelAndView view, String roomtimeid, String userphone, String username, String purpose) throws Exception{
        WhgVenRoomTime roomTime = this.service.findWhgVenroomtime4Id(roomtimeid);
        WhgVenRoom room = this.service.findWhgVenroom4Id(roomTime.getRoomid());
        WhgVen ven = this.service.findWhgVen4Id(room.getVenid());

        view.addObject("roomtime", roomTime);
        view.addObject("room", room);
        view.addObject("ven", ven);
        view.addObject("userphone", userphone);
        view.addObject("username", username);
        view.addObject("purpose", purpose);
    }


}
