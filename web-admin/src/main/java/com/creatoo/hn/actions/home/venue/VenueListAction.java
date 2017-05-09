package com.creatoo.hn.actions.home.venue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhCfgAdv;
import com.creatoo.hn.model.WhTyp;
import com.creatoo.hn.model.WhVenue;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.home.venue.VenueListService;
import com.creatoo.hn.utils.ReqParamsUtil;

@RestController
@RequestMapping("/venue")
public class VenueListAction {
	
	@Autowired
	private VenueListService service;
	
	@Autowired
	private CommService commService;
	/**
	 * 返回场馆预定列表视图
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView view = new ModelAndView("/home/venue/venuelist");
		
		
		try {
			//场馆区域
	    	List<WhTyp> venArea = this.commService.findWhtyp("8");
	    	if (venArea != null && venArea.size() >0) {
	    		view.addObject("venArea", venArea);
			}
			
			//场馆类型
	    	List<WhTyp> ventype = this.commService.findWhtyp("15");
	    	if (ventype != null && ventype.size() > 0) {
	    		view.addObject("ventype", ventype);
			}
	    	
	    	//查找可预订的场馆
	    	List<WhVenue> venyd =  this.service.findVenueyd();
	    	if (venyd != null && venyd.size() >0) {
	    		view.addObject("venyd", venyd);
			}
	    	
	    	//广告图
	    	List<WhCfgAdv> venlist=this.service.advLoad();
	    	if (venlist != null && venlist.size() > 0) {
	    		view.addObject("venlist", venlist.get(0));
			}
	    	
	    	//查找场馆用途标签
	    	
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	
	/**
	 * 场馆预定列表页数据分页
	 */
	@RequestMapping("/venlist")
	public Object Srchlist(HttpServletRequest req,HttpServletResponse res){
		//获取请求参数
		Map<String, Object> param = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.service.findList(param);
			
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
        return rtnMap;
		
	}

}
