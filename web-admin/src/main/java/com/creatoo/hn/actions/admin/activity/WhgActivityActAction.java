package com.creatoo.hn.actions.admin.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.emun.EnumOptType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.model.WhgActActivity;
import com.creatoo.hn.model.WhgActOrder;
import com.creatoo.hn.model.WhgActTime;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.model.WhgVen;
import com.creatoo.hn.services.admin.activity.WhgActivityActService;
import com.creatoo.hn.services.admin.activity.WhgActivityOrderService;
import com.creatoo.hn.services.admin.activity.WhgActivityPalyService;
import com.creatoo.hn.services.admin.activity.WhgActivitySeatService;
import com.creatoo.hn.services.admin.activity.WhgActivityTimeService;
import com.creatoo.hn.services.admin.venue.WhgVenueService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.comm.SMSService;
import com.creatoo.hn.utils.CommUtil;
import com.github.pagehelper.PageInfo;


/**
 * 活动控制层
 * @author heyi
 *
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/admin/activity/act")
public class WhgActivityActAction {
    /**
     * 日志控制器
     */
    Logger log = Logger.getLogger(this.getClass().getName());

    /**
     * 活动服务类
     */
    @Autowired
    private WhgActivityActService service;

    /**
     * 活动场次服务类
     */
    @Autowired
    private WhgActivityPalyService whgActivityPalyService;

    @Autowired
    private WhgActivitySeatService whgActivitySeatService;

    /**
     * 活动时间服务类
     */
    @Autowired
    private WhgActivityTimeService whgActivityTimeService;
    
    @Autowired
    private WhgActivityOrderService whgActivityOrderService;


    @Autowired
    private SMSService smsService;
    
    @Autowired
    private WhgVenueService whgVenueService;
    
    /**
	 * 公用服务类
	 */
	@Autowired
	public CommService commservice;
    
    /**
     * 进入type(list|add|edit|view)视图
     * @param request 请求对象
     * @param type 视图类型(list|add|edit|view)
     * @return
     */
    @RequestMapping("/view/{type}")
    @WhgOPT(optType = EnumOptType.ACT, optDesc = {"进入活动列表", "进入活动添加页", "进入活动编辑页"}, valid = {"type=list", "type=add", "type=edit"})
    public ModelAndView listview(HttpServletRequest request,ModelMap mmp, @PathVariable("type")String type){
        ModelAndView view = new ModelAndView();
        try {
            mmp.addAttribute("type", type);
            if ("add".equalsIgnoreCase(type)){
                view.setViewName("admin/activity/act/view_add");
            }else if("edit".equalsIgnoreCase(type)){
                String id = request.getParameter("id");
                String onlyShow = request.getParameter("onlyshow");
                view.addObject("act", service.t_srchOne(id));
                view.addObject("actSeatList",whgActivityPalyService.srchList4actId(id));
                JSONObject seatMap = whgActivitySeatService.getActivitySeatInfo(id);
                view.addObject("whgSeat",seatMap);
                if(null != onlyShow){
                    view.setViewName("admin/activity/act/view_show");
                }else{
                    view.setViewName("admin/activity/act/view_edit");
                }
            }else if("orderList".equalsIgnoreCase(type)){
                String id = request.getParameter("id");
                view.addObject("act", service.t_srchOne(id));
                view.setViewName("admin/activity/act/view_order_list");
            } else if("screenings".equalsIgnoreCase(type)){
                String id = request.getParameter("id");
                WhgActTime whgActTime = whgActivityTimeService.findWhgActTime4ActId(id);
                view.addObject("seats",whgActTime.getSeats());
                view.addObject("act", service.t_srchOne(id));
                view.setViewName("admin/activity/act/view_screenings");
            }else{
                view.setViewName("admin/activity/act/view_list");
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        return view;
    }

    /**
     * 分页查询
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/srchList4p")
    public Object srchList4p(int page, int rows, WebRequest request){
        ResponseBean res = new ResponseBean();
        try {
            Map<String, String[]> pmap = request.getParameterMap();
            Map param = new HashMap();
            for(Map.Entry<String, String[]> ent : pmap.entrySet()){
                if (ent.getValue().length == 1 && ent.getValue()[0]!=null && !ent.getValue()[0].isEmpty()){
                    param.put(ent.getKey(), ent.getValue()[0]);
                }
                if (ent.getValue().length > 1){
                    param.put(ent.getKey(), Arrays.asList( ent.getValue() ) );
                }
            }
            String pageType = request.getParameter("__pageType");
            //编辑列表，查 1可编辑 5已撤消
            if ("editList".equalsIgnoreCase(pageType)){
                param.put("states", Arrays.asList(1,5) );
            }
            //审核列表，查 9待审核
            if ("check".equalsIgnoreCase(pageType)){
                param.put("states", Arrays.asList(9) );
            }
            //发布列表，查 2待发布 6已发布 4已下架
            if ("publish".equalsIgnoreCase(pageType)){
                param.put("states", Arrays.asList(2,6,4) );
            }
            //删除列表
            if("del".equalsIgnoreCase(pageType)){
            	param.put("delstate", 1);
            }
            PageInfo pageInfo = this.service.srchList4p(page, rows, param);
            res.setRows( (List)pageInfo.getList() );
            res.setTotal(pageInfo.getTotal());
        } catch (Exception e) {
            log.debug("活动查询失败", e);
            res.setRows(new ArrayList());
            res.setSuccess(ResponseBean.FAIL);
        }

        return res;
    }

    @RequestMapping(value = "/updateScreenings")
    @WhgOPT(optType = EnumOptType.ACT, optDesc = {"修改场次"})
    public Object updateScreenings(HttpServletRequest request){
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        ResponseBean res = new ResponseBean();
        WhgActTime whgActTime = new WhgActTime();
        Map<String,String> paramMap = CommUtil.getRequestParamByClass(request,whgActTime.getClass());
        whgActTime = this.whgActivityTimeService.findWhgActTime4Id(paramMap.get("id"));
        List<WhgActOrder> actOrderList = whgActivityOrderService.findWhgActOrder4EventId(paramMap.get("id"));
        if(actOrderList.size() < 1){
        	try{
                whgActTime.setPlaydate(sdf1.parse(paramMap.get("playdate")));
                whgActTime.setPlaystime(paramMap.get("playstime"));
                whgActTime.setPlayetime(paramMap.get("playetime"));
                whgActTime.setSeats(Integer.valueOf(paramMap.get("seats")));
                whgActTime.setState(Integer.valueOf(paramMap.get("state")));
                whgActivityTimeService.updateOne(whgActTime);
            }catch (Exception e){
                log.error(e.toString());
                res.setErrormsg("修改场次信息失败");
                res.setSuccess(ResponseBean.FAIL);
            }
        }else{
        	res.setSuccess(ResponseBean.FAIL);
    		res.setErrormsg("该场次已经产生订单，不允许修改!");
        }
        return res;
    }
    
    /**
     * 添加场次信息
     * @param request
     * @param actId
     * @return
     */
    @RequestMapping(value = "/addScreenings")
    @WhgOPT(optType = EnumOptType.ACT, optDesc = {"添加场次"})
    public Object addScreenings(HttpServletRequest request,String actId){
    	SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ResponseBean res = new ResponseBean();
        WhgActTime whgActTime = new WhgActTime();
        Map<String,String> paramMap = CommUtil.getRequestParamByClass(request,whgActTime.getClass());
        try{
        	WhgActActivity act = service.t_srchOne(actId);
        	Date playDate = sdfDateTime.parse(paramMap.get("playdate")+ " " + "00:00:00");
        	Date startDate = act.getStarttime();//活动开始时间
        	Date endDate = act.getEndtime();//活动结束时间
        	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        	Date nowDate = df.parse(df.format(new Date()));// new Date()为获取当前系统时间
        	Date strDate = df.parse(df.format(act.getEnterstrtime()));
        	if(strDate.getTime() < playDate.getTime() && playDate.getTime() > nowDate.getTime()  ){
        		whgActTime.setId(commservice.getKey("whg_sys_act"));
            	whgActTime.setActid(actId);
                whgActTime.setPlaydate(sdf.parse(paramMap.get("playdate")));
                whgActTime.setPlaystime(paramMap.get("playstime"));
                whgActTime.setPlayetime(paramMap.get("playetime"));
                whgActTime.setSeats(Integer.valueOf(paramMap.get("seats")));
                Date playStartTime = sdfDateTime.parse(paramMap.get("playdate") + " " + paramMap.get("playstime"));
    			Date playEndTime = sdfDateTime.parse(paramMap.get("playdate") + " " + paramMap.get("playetime"));
    			whgActTime.setPlaystarttime(playStartTime);
    			whgActTime.setPlayendtime(playEndTime);
    			whgActTime.setState(1);
                whgActivityTimeService.addOne(whgActTime); 
                //判断新增的场次时间是否小于活动开始时间
                if(playDate.getTime() < startDate.getTime()){
                	act.setStarttime(sdf.parse(paramMap.get("playdate")));
                }
                //判断新增的场次时间是否大于活动结束时间
                if(playDate.getTime() > endDate.getTime() ){
                	act.setEndtime(sdf.parse(paramMap.get("playdate")));
                }
                WhgSysUser whgSysUser =  (WhgSysUser) request.getSession().getAttribute("user");
                service.t_edit(act, whgSysUser);
        	}else{
        		res.setSuccess(ResponseBean.FAIL);
        		res.setErrormsg("新增日期需大于报名开始时间且小于当前时间!");
        	}
        }catch (Exception e){
            log.error(e.toString());
            res.setErrormsg("修改场次信息失败");
            res.setSuccess(ResponseBean.FAIL);
        }
        return res;
    }
    

    /**
     * 查询列表
     *  @param request 请求实体
     * @return 对象列表
     */
    @RequestMapping(value = "/getScreenings")
    public Object getScreenings(HttpServletRequest request){
        ResponseBean res = new ResponseBean();
        try {
            String pageNo = request.getParameter("page");
            String pageSize = request.getParameter("rows");
            String activityId = request.getParameter("activityId");
            if(null == pageNo || !CommUtil.isInteger(pageNo)){
                pageNo = "1";
            }
            if(null == pageSize || !CommUtil.isInteger(pageSize)){
                pageSize = "10";
            }
            if(null == activityId){
                res.setSuccess(ResponseBean.FAIL);
                res.setErrormsg("activityId can't be null");
            }else{
                WhgActTime whgActTime = new WhgActTime();
                whgActTime.setActid(activityId);
                PageInfo pageInfo = this.service.getActivityScreenings(Integer.valueOf(pageNo), Integer.valueOf(pageSize), whgActTime);
                res.setRows((List)pageInfo.getList());
                res.setTotal(pageInfo.getTotal());
            }
        }catch (Exception e){
            log.error(e.toString());
        }
        return  res;
    }

    /**
     * 获取活动订单
     * @param request
     * @return
     */
    @RequestMapping(value = "/getActOrder")
    public Object getActOrder(HttpServletRequest request){
        ResponseBean res = new ResponseBean();
        try{
            String pageNo = request.getParameter("page");
            String pageSize = request.getParameter("rows");
            if(null == pageNo || !CommUtil.isInteger(pageNo)){
                pageNo = "1";
            }
            if(null == pageSize || !CommUtil.isInteger(pageSize)){
                pageSize = "10";
            }

            String activityId = request.getParameter("activityId");
            String ordernumber = request.getParameter("ordernumber");
            String orderphoneno = request.getParameter("orderphoneno");
            String starttime = request.getParameter("starttime");
            String endtime = request.getParameter("endtime");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Map params = new HashMap<>();
            params.put("activityId", activityId);
            if (ordernumber!=null && !ordernumber.isEmpty()){
                params.put("ordernumber", ordernumber.trim());
            }
            if (orderphoneno!=null && !orderphoneno.isEmpty()){
                params.put("orderphoneno",orderphoneno.trim());
            }
            if (starttime!=null && !starttime.isEmpty()){
                params.put("starttime", sdf.parse(starttime));
            }
            if (endtime!=null && !endtime.isEmpty()){
                params.put("endtime", sdf.parse(endtime));
            }


            PageInfo pageInfo = service.getActOrderForBackManager(Integer.valueOf(pageNo),Integer.valueOf(pageSize), params);
            res.setRows((List)pageInfo.getList());
            res.setTotal(pageInfo.getTotal());
        }catch (Exception e){
            log.error(e.toString());
        }
        return res;
    }

    @RequestMapping("/view/order/info")
    public ModelAndView actOrderInfo(String orderid){
        ModelAndView mav = new ModelAndView("admin/activity/act/view_order_info");
        try {
            Map rest = service.getOrderInfo(orderid);
            mav.addAllObjects(rest);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return mav;
    }

    /**
     * 查询列表
     * @param request 请求对象
     * @param act 条件对象
     * @return 对象列表
     */
    @RequestMapping(value = "/srchList")
    public List<WhgActActivity> srchList(HttpServletRequest request, WhgActActivity act){
        List<WhgActActivity> resList = new ArrayList<WhgActActivity>();
        try {
            resList = service.t_srchList(request, act);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return resList;
    }

    /**
     * 查询详情
     * @param request 请求对象
     * @param id 标识
     * @return 详情资料
     */
    @RequestMapping(value = "/srchOne")
    public ResponseBean srchOne(HttpServletRequest request, String id){
        ResponseBean res = new ResponseBean();
        try {
            WhgActActivity act = service.t_srchOne(id);
            res.setData(act);
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }

    /**
     * 添加
     * @param request 请求对象
     * @return 执行操作返回结果的JSON信息
     */
    @RequestMapping(value = "/add")
    @WhgOPT(optType = EnumOptType.ACT, optDesc = {"添加活动"})
    public ResponseBean add(HttpServletRequest request){
        ResponseBean res = new ResponseBean();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            WhgActActivity act = WhgActActivity.getInstanceByRequest(request);
            String seatJson = request.getParameter("seatjson");
            String timeJson = request.getParameter("activityTimeList");
            //添加活动
            act = service.t_add(act, (WhgSysUser) request.getSession().getAttribute("user"));
            //添加时间段模板
            List<Map<String, String>>  timePlayList = whgActivityPalyService.setTimeTemp(timeJson);
            whgActivityPalyService.t_add(timePlayList,act);

            //如果是在线选座，需要添加活动座位信息
            int ticketnum = 0;//
            if(seatJson !=null && act.getSellticket().equals(3)){
                List<Map<String, String>>  seatList = whgActivitySeatService.setSeatList(seatJson);
                whgActivitySeatService.t_add(seatList, (WhgSysUser) request.getSession().getAttribute("user"), act.getId());
                
                //计算座位数
                ticketnum = 0;
                for(Map<String, String> _set : seatList){
                	if( "1".equals(_set.get("seatstatus") ) ){
                		ticketnum++;
                	}
                }
            }
            
            //添加活动场次表
            int dayNum = this.dayCount(act.getStarttime(), act.getEndtime());
            int num = 0;
            Date time = act.getStarttime();
            Date endDate = act.getEndtime();
            if(ticketnum == 0){
            	if(act.getTicketnum() != null){
            		ticketnum = act.getTicketnum();
            	}
            }
            while(!isAfter(time,endDate)){
                whgActivityTimeService.t_add(timePlayList, act.getId(), time, ticketnum);
                time = formatter.parse(getTomorrow(time));
            }
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }

    /**
     * 是否到结束日期
     * @param date
     * @param end
     * @return
     */
    private boolean isAfter(Date date,Date end){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate myDate = LocalDate.parse(sdf.format(date));
        LocalDate endDate = LocalDate.parse(sdf.format(end));
        return myDate.isAfter(endDate);
    }

    /**
     * 获取明天
     * @param date
     * @return
     */
    private String getTomorrow(Date date){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,1);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
        date=calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 编辑
     * @param request 请求对象
     * @return 执行操作返回结果的JSON信息
     */
    @RequestMapping(value = "/edit")
    @WhgOPT(optType = EnumOptType.ACT, optDesc = {"编辑活动"})
    public ResponseBean edit(HttpServletRequest request){
        ResponseBean res = new ResponseBean();
        try {
            WhgActActivity act = WhgActActivity.getInstanceByRequest(request);
            WhgSysUser whgSysUser =  (WhgSysUser) request.getSession().getAttribute("user");
            if(null == whgSysUser){
                res.setSuccess(ResponseBean.FAIL);
                res.setErrormsg("未登录");
            }else {
                log.info(JSON.toJSONString(act));
                service.t_edit(act, whgSysUser);
                //删除座位信息
                whgActivitySeatService.delActSeat4ActId(act.getId());
                //修改座位
                if(3 == act.getSellticket()){
                    String seatJson = request.getParameter("seatjson");
                    List<Map<String, String>> seats = whgActivitySeatService.setSeatList(seatJson);
                    whgActivitySeatService.t_add(seats,whgSysUser,act.getId());
                }
            }
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.toString());
        }
        return res;
    }

    /**
     * 删除
     * @param request 请求对象
     * @param ids 用逗号分隔的多个ID
     * @return 执行操作返回结果的JSON信息
     */
    @RequestMapping(value = "/del")
    @WhgOPT(optType = EnumOptType.ACT, optDesc = {"删除活动"})
    public ResponseBean del(HttpServletRequest request, String ids,String delStatus){
    	ResponseBean res = new ResponseBean();
	     try {
	         service.t_updDelstate(ids, delStatus, (WhgSysUser)request.getSession().getAttribute("user"));
	     }catch (Exception e){
	         res.setSuccess(ResponseBean.FAIL);
	         res.setErrormsg(e.getMessage());
	         log.error(e.getMessage(), e);
	     }
	     return res;
    }

    /**
     * 修改状态
     * @param request 请求对象
     * @param ids 用逗号分隔的多个ID
     * @param fromState 修改之前的状态
     * @param toState 修改后的状态
     * @return 执行操作返回结果的JSON信息
     */
    @RequestMapping(value = "/updstate")
    @WhgOPT(optType = EnumOptType.ACT, optDesc = {"送审","审核","打回","发布","取消发布"}, valid ={"toState=9","toState=2","toState=1","toState=6","toState=4"})
    public ResponseBean updstate(String statemdfdate, HttpServletRequest request, String ids, String fromState, String toState){
        ResponseBean res = new ResponseBean();
        try {
            service.t_updstate(statemdfdate, ids, fromState, toState, (WhgSysUser)request.getSession().getAttribute("user"));
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }
    
    /**
     * 推荐状态修改
     * @param request 请求对象
     * @param ids 用逗号分隔的多个ID
     * @param fromState 修改之前的状态
     * @param toState 修改后的状态
     * @return 执行操作返回结果的JSON信息
     */
    @RequestMapping(value = "/updCommend")
    @WhgOPT(optType = EnumOptType.ACT, optDesc = {"推荐","取消推荐"}, valid ={"toState=1","toState=0"})
    public ResponseBean updCommend(HttpServletRequest request, String ids, String fromState, String toState){
        ResponseBean res = new ResponseBean();
        try {
            service.t_updCommend(ids, fromState, toState, (WhgSysUser)request.getSession().getAttribute("user"));
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }
    
    /**
     * 还原删除状态
     * @param request 请求对象
     * @param ids 用逗号分隔的多个ID
     * @param delStatus 还原状态
     * @return 执行操作返回结果的JSON信息
     */
    @RequestMapping(value = "/reBack")
    @WhgOPT(optType = EnumOptType.ACT, optDesc = {"还原活动"})
    public ResponseBean reBack(HttpServletRequest request, String ids, String delStatus){
        ResponseBean res = new ResponseBean();
        try {
            service.reBack(ids, delStatus, (WhgSysUser)request.getSession().getAttribute("user"));
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }
    

    /**
     * 根据活动的开始时间和结束时间算出它们的时间差
     * @param
     * @return
     * @throws ParseException
     */
    public int dayCount(Date strTime,Date endTime) throws ParseException
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(strTime);
        int strNum = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTime(endTime);
        int endNum = calendar.get(Calendar.DAY_OF_YEAR);
        return endNum - strNum;
    }
    
    /**
     * 订单重新发送短信
     * @param request
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/againSendSms")
    public ResponseBean againSendSms(HttpServletRequest request,String orderId){
    	ResponseBean res = new ResponseBean();
		try {
			WhgActOrder actOrder = whgActivityOrderService.findWhgActOrder4Id(orderId);
			WhgActActivity whgActActivity = service.t_srchOne(actOrder.getActivityid());
			WhgActTime actTime = whgActivityTimeService.findWhgActTime4Id(actOrder.getEventid());
			//发送短信
			Map<String, String> smsData = new HashMap<String, String>();
			smsData.put("userName", actOrder.getOrdername());
			smsData.put("activityName", whgActActivity.getName());
			smsData.put("ticketCode", actOrder.getOrdernumber());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = actTime.getPlaydate();
			String dateStr = sdf.format(date);
			smsData.put("beginTime", dateStr +" "+ actTime.getPlaystime());
			int totalSeat = whgActivityOrderService.findWhgActTicket4OrderId(orderId);
			smsData.put("number", String.valueOf(totalSeat));
			smsService.t_sendSMS(actOrder.getOrderphoneno(), "ACT_DUE", smsData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	 return res;
    }

    /**
     * 取消订单
     * @param request
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/cancelOrder")
    @WhgOPT(optType = EnumOptType.ACT, optDesc = {"取消订单"})
    public ResponseBean cancelOrder(HttpServletRequest request,String orderId){
    	ResponseBean res = new ResponseBean();
    	try {
    		//更新订单信息
			int upCount = whgActivityOrderService.updateActOrder(orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return res;
    }
    
    @RequestMapping(value= "/changeVen")
    public Object changeVen(HttpServletRequest request,String venueId){
    	Map<String,Object> res = new HashMap<String, Object>();
    	try {
			WhgVen whgVen = whgVenueService.srchOne(venueId);
			res.put("address", whgVen.getAddress());
			res.put("longitude", whgVen.getLongitude());//坐标经度
			res.put("latitude", whgVen.getLatitude());//坐标纬度
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return res;
    }
    

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date a = format.parse("2017-01-01");
        Date b = format.parse("2017-01-03");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(a);
        int strNum = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTime(b);
        int endNum = calendar.get(Calendar.DAY_OF_YEAR);
        System.out.println(endNum-strNum);
    }
    /**
     * 是否上首页
     * @param ids
     * @param formupindex
     * @param toupindex
     * @return
     */
    @RequestMapping("/upindex")
    @WhgOPT(optType = EnumOptType.ACT, optDesc = {"上首页","取消上首页"}, valid = {"formupindex=1","formupindex=0"})
    public ResponseBean upindex(String ids, String formupindex, int toupindex){
        ResponseBean res = new ResponseBean();
        try {
            res = this.service.t_upindex(ids,formupindex,toupindex);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("上首页失败！");
            log.error(res.getErrormsg()+" formupindex: "+formupindex+" toupindex:"+toupindex+" ids: "+ids, e);
        }
        return res;
    }
    
}
