package com.creatoo.hn.actions.home.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhActivity;
import com.creatoo.hn.model.WhActivityitm;
import com.creatoo.hn.model.WhBrand;
import com.creatoo.hn.model.WhBrandAct;
import com.creatoo.hn.model.WhCfgAdv;
import com.creatoo.hn.model.WhCfgList;
import com.creatoo.hn.model.WhEntsource;
import com.creatoo.hn.model.WhTag;
import com.creatoo.hn.model.WhTyp;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.home.event.eventService;
import com.creatoo.hn.utils.ReqParamsUtil;

/**
 * 活动预约访问控制
 * @author wangxl
 * @version 2016.10.19
 */
@RestController
@RequestMapping("/event")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class EventAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public CommService commService;
	
	@Autowired
	private eventService eventService;
	
	/**
	 * 活动预约首页
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView eventIndex(){
		ModelAndView view = null;
		try {
			view = new ModelAndView( "home/event/eventindex" );
			
			//常规活动
		/*	List<WhCfgList> cgact = (List<WhCfgList>) this.eventService.eventLoad("2016101400000009");
			if(cgact != null){
				view.addObject("cgact", cgact);
			}*/
			
			//赛事活动
			List<WhCfgList> ssact=this.eventService.eventLoad("2016101400000010");
			if(ssact != null){
				view.addObject("ssact",ssact);
			}
			
			//展览活动
			List<WhCfgList> zlact =  this.eventService.eventLoad("2016101400000011");
			if(zlact != null){
				view.addObject("zlact", zlact);
			}
			
			//文化慧明活动
			List<WhCfgList> wenhuaact = this.eventService.eventLoad("2016101400000012");
			if(wenhuaact!=null){
				view.addObject("wenhuaact",wenhuaact);
			}
			
			//品牌活动
			List<WhCfgList> ppact = this.eventService.ppLoad();
			if(ppact!=null){
				view.addObject("ppact",ppact);
			}
			
			//活动广告图
			List<WhCfgAdv> advlist = this.eventService.advLoad();
			if(advlist != null && advlist.size() > 0){
				view.addObject("advlist",advlist.get(0)); 
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return view;
	}
	
	/**
	 * 进入活动列表页
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView eventList(String actvtype){
		//新建视图对象
		ModelAndView view = new ModelAndView( "home/event/eventlist" );
		try {
			//活动分类
	    	List<WhTyp> acttype = this.commService.findWhtyp("1");
	    	view.addObject("acttype", acttype);
	    	//艺术分类
	    	List<WhTyp> ystype = this.commService.findWhtyp("0");
	    	view.addObject("ystype", ystype);
	    	//区域分类
	    	List<WhTyp> qrtype = this.commService.findWhtyp("8");
	    	view.addObject("qrtype", qrtype);
	    	//活动广告图
	    	List<WhCfgAdv> advlist=this.eventService.advLoad();
	    	if(advlist!=null && advlist.size()>0){
	    		view.addObject("advlist",advlist.get(0)); 
			}
	    	//活动类型
			view.addObject("actvtype", actvtype);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 进入品牌活动详情页
	 * @return
	 */
	@RequestMapping("/pplist")
	public ModelAndView eventPPList(String braid){
		ModelAndView view = new ModelAndView( "home/event/eventpplist" );
		try {
			//braid="2016110200000019";
			//当前时间
			Date now = new Date();
			
			//专题活动
			WhBrand whBrand = this.eventService.findDetail(braid);
			if (whBrand != null ) {
				view.addObject("whBrand", whBrand);
			}
			
			
			//子活动信息
			List<Map> whBrandActs = this.eventService.findBrandAct(braid);
			if (whBrandActs !=null && whBrandActs.size() > 0) {
				view.addObject("whBrandActs", whBrandActs);
			}
			
			
			//当前活动
			String curActid = null;
			for (int i = 0; i < whBrandActs.size(); i++) {
				Date hdDateTime = (Date) whBrandActs.get(i).get("bracedate");
				if(hdDateTime.before(now)){
					curActid = (String) whBrandActs.get(i).get("bracactid");
				}
			}
			if(curActid == null && whBrandActs.size() > 0){//first
				curActid = (String) whBrandActs.get(0).get("bracactid");
			}
			List<WhBrandAct> curAct = (List<WhBrandAct>) this.eventService.findCurAct(curActid);
			if (curAct != null && curAct.size() > 0) {
				view.addObject("curAct", curAct);
			}
			
			 
			//图片资源信息
			List<WhEntsource> whBrandZY = this.eventService.findZY(braid); 
			if (whBrandZY != null && whBrandZY.size() >0) {
				view.addObject("whBrandZY", whBrandZY);
			}
			
			
			//视频资源信息
			List<WhEntsource> whBransp = this.eventService.findspZY(braid);
			if (whBransp != null && whBransp.size() > 0) {
				view.addObject("whBransp", whBransp);
			}
			
			
			//往期活动
			List<Map> act = new ArrayList<Map>();
			for (int i = 0; i < whBrandActs.size(); i++) {
				Date hdDateTime = (Date) whBrandActs.get(i).get("bracedate");
				if(hdDateTime.before(now)){
					act.add(whBrandActs.get(i));
				}
			}
			if (act.size() > 0 && act != null) {
				view.addObject("act", act);
			}
			
			
			//活动资讯
			List<Map> actZX = this.eventService.selActZX();
			if (actZX != null && actZX.size() >0) {
				view.addObject("actZX", actZX);
			}
			
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 品牌活动列表页
	 * @return
	 */
	@RequestMapping("/srchlist")
	public Object eventSrchlist(){
		ModelAndView view = new ModelAndView( "home/event/eventsrchlist" );
		List<WhBrand> whBrand;
		try {
			whBrand = this.eventService.findBrandList();
			view.addObject("whBrand", whBrand);
			//广告图
			List<WhCfgAdv> archlistAdv = this.eventService.advListLoad();
			if(archlistAdv != null && archlistAdv.size() > 0){
				view.addObject("archlistAdv", archlistAdv.get(0));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 品牌活动列表页数据分页
	 */
	@RequestMapping("/pagelist")
	public Object Srchlist(HttpServletRequest req,HttpServletResponse res){
		//获取请求参数
		Map<String, Object> param = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.eventService.findList(param);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
        return rtnMap;
		
	}
	
	/**
	 * 进入活动详情页
	 * @return
	 */
	@RequestMapping("/detail")
	public ModelAndView eventDetail(String actvid){
		ModelAndView view = new ModelAndView( "home/event/eventdetail" );
		try {
			//活动信息
			WhActivity actdetail = this.eventService.eventDetail(actvid);
			view.addObject("actdetail", actdetail);
			
			//活动标签
			List<WhTag> taglist = new ArrayList<WhTag>();
			//处理活动标签
			if(actdetail.getActvtags() != null && !"".equals(actdetail.getActvtags())){
				String tags[] = actdetail.getActvtags().split(",");
				for (int i = 0; i < tags.length; i++) {
					taglist.add(this.eventService.tagList(tags[i]));
				}
			}
			view.addObject("taglist", taglist);
			
			//活动广告图
			List<WhCfgAdv> advlist = this.eventService.advLoad();
			if(advlist != null && advlist.size() > 0){
				view.addObject("advlist", advlist.get(0)); 
			}
			
			//活动场次 时间
			List<WhActivityitm> actvitm = this.eventService.findActivityitm(actvid);
			view.addObject("actvitm", actvitm);
			
			//获取 往期回顾
			List<Map> wqAct = (List<Map>) this.eventService.wqList(actdetail.getActvtype(),actvid);
			view.addObject("wqAct", wqAct);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 活动列表页 加载相关数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/eventList")
	public Object eventList(int page,int rows,WebRequest request){
		try {
			return this.eventService.eventList(page,rows,request);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return null;
		}
	}
	
}
