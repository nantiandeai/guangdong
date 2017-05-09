package com.creatoo.hn.actions.api.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOrderType;
import com.creatoo.hn.model.WhgActActivity;
import com.creatoo.hn.model.WhgActOrder;
import com.creatoo.hn.model.WhgActSeat;
import com.creatoo.hn.model.WhgActTime;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.comm.SMSService;
import com.creatoo.hn.services.home.agdwhhd.WhhdService;

/**
 * 活动预定接口
 * Created by wangxl on 2017/4/12.
 */
@RestController
@RequestMapping("/api/act")
public class APIActivityAction {
    /**
     * 日志控制器
     */
    Logger log = Logger.getLogger(this.getClass().getName());
    
    /**
	 * 公用服务类
	 */
	@Autowired
	public CommService commservice;
	
	/**
	 * 短信公开服务类
	 */
	@Autowired
	private SMSService smsService;
    
    @Autowired
    private WhhdService  whhdService;
    
    
    /**
     * 检查能否报名
     * 访问路径 /api/act/check/{actId}/{userId}
     * @param actId  活动Id
     * @param userId 用户Id
     * @return JSON: {
     * "success" : "1"             //1表示可以报名，其它失败
     * "errormsg" : "100|104"     //100-培训已失效;  104-未实名认证
     * }
     */
    @CrossOrigin
    @RequestMapping("/check/{actId}/{userId}")
    public ResponseBean check(@PathVariable("actId")String actId, @PathVariable("userId")String userId){
        ResponseBean res = new ResponseBean();
        try{
            String validCode = this.whhdService.checkApplyAct(actId, userId);
            if(!"0".equals(validCode)){
                res.setSuccess(ResponseBean.FAIL);
                res.setErrormsg(validCode);
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
        }
        return res;
    }
    
    /**
     * 预定活动
     * 访问路径 /api/act/reserveAct
     * @param actId  活动Id
     * @param actOrder  订单信息 可参考whg_act_order(场次、预定人姓名、预定人手机号码)，POST的数据为此表的字段小写
     * @param userId  用户Id 
     * @param seatStr  在线选座 座位编号：座位1,座位2
     * @param seats  自由选座 座位数
     * @return JSON : {
     * "success" : "1"        //1表示报名成功，其它失败
     * "errormsg" : "105"     //100-活动Id不允许为空;101-活动场次Id不允许为空;102-用户Id不允许为空;103-座位数必须大于0;105-报名失败
     * }
     */
    
    @CrossOrigin
    @RequestMapping(value = "/reserveAct", method = RequestMethod.POST)
    public ResponseBean reserveAct(String actId,WhgActOrder actOrder, String userId,String seatStr,int seats ){
        ResponseBean res = new ResponseBean();
        try{
        	if(actId ==null || actId ==""){
        		res.setSuccess(ResponseBean.FAIL);
        		res.setErrormsg("100");
        	}
        	else if(actOrder.getEventid() == null || actOrder.getEventid() ==""){
        		res.setSuccess(ResponseBean.FAIL);
        		res.setErrormsg("101");
        	}else if(userId == null || userId == ""){
        		res.setSuccess(ResponseBean.FAIL);
        		res.setErrormsg("102");
        	}else if(seatStr == null || seatStr == "" && seats <1){
        		res.setSuccess(ResponseBean.FAIL);
        		res.setErrormsg("103");
        	}else{
        		String validCode = this.whhdService.checkApplyAct(actId, userId);
	            if(!"0".equals(validCode)){
	                res.setSuccess(ResponseBean.FAIL);
	                res.setErrormsg(validCode);
	            }else{
                	//验证通过，报名活动
                	String id = this.commservice.getKey("WhgActOrder");
                	actOrder.setId(id);
                	actOrder.setOrdernumber(this.commservice.getOrderId(EnumOrderType.ORDER_ACT.getValue()));
                	actOrder.setUserid(userId);
                	whhdService.addActOrder(actId, actOrder);
                	WhgActTime actTime = whhdService.selectOnePlay(actOrder.getEventid());
//        			String mySelectSeat = request.getParameter("seatStr"); //在线选座位置数
//        			int seatNum = Integer.parseInt(request.getParameter("seats")); //自由入座位置数
        			String mySelectSeat = seatStr; //在线选座位置数
        			int seatNum = seats; //自由入座位置数
        			String[] selectSeat = mySelectSeat.split(",");
        			int totalSeat = selectSeat.length;
        			if(seatNum > 0){
        				totalSeat = seatNum;
        			}
        			int sum_ = 1;
        			for(int i=0;i<totalSeat;i++){
        				if(seatNum > 0){//自由入座
        					whhdService.saveSeatOrder("P"+sum_, id,"票"+sum_);
        				}else{ //在线选座
        					WhgActSeat whgActSeat =whhdService.getWhgActTicket4ActId(actId, selectSeat[i]);
        					whhdService.saveSeatOrder(whgActSeat.getId(), id,whgActSeat.getSeatnum());
        				}
        				sum_++;
        			}
        			//发送短信
        			Map<String, String> smsData = new HashMap<String, String>();
        			smsData.put("userName", actOrder.getOrdername());
        			WhgActActivity whgActActivity = whhdService.getActDetail(actId);
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
        			whhdService.upActOrder(actOrder);
//        			Map<String,Object> map = whhdService.findOrderInfo4Id(id);
//        			view.addObject("order", map);
            	}
        	}
        } catch (Exception e){
            log.error(e.getMessage(), e);
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
        }
        return res;
    }

}
