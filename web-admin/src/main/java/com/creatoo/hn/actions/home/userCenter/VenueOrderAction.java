package com.creatoo.hn.actions.home.userCenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.services.home.userCenter.VenueOrderService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.WhConstance;

@RestController
@RequestMapping("/center")
public class VenueOrderAction {
	/**
	 * 日志
	 */
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private VenueOrderService service;
	/**
	 * 返回我的场馆预定视图
	 * @return
	 */
	@RequestMapping("/myVenue")
	public ModelAndView showPage(){
		return new ModelAndView("/home/center/venueorder");
	}

	/**
	 * 查询我的场馆预定数据
	 * @return
	 */
	@RequestMapping("/findVenueOrder")
	public Object findMyVenue(HttpServletRequest req,HttpServletResponse res, HttpSession session){
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
            //获取请求参数
            Map<String, Object> param = ReqParamsUtil.parseRequest(req);

            WhUser user = (WhUser) session.getAttribute(WhConstance.SESS_USER_KEY);
            param.put("userid", user.getId());

			rtnMap = this.service.findOrder(param);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
        return rtnMap;
	}
	
	/**
	 * 取消预定
	 */
	@RequestMapping("/unOrder")
	public Object delVenueOrder(String orderid){
		Map<String, Object> res = new HashMap();
		try {
			int count = this.service.unOrder(orderid);
            res.put("success", count>0);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
            res.put("success", false);
		}
		return res;
	}

    /**
     * 删除预定
     */
    @RequestMapping("/delOrder")
    public Object removeVenueOrder(String orderid){
        Map<String, Object> res = new HashMap();
        try {
            int count = this.service.delOrder(orderid);
            res.put("success", count>0);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res.put("success", false);
        }
        return res;
    }
}
