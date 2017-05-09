package com.creatoo.hn.actions.home.agdwhlm;

import com.creatoo.hn.ext.emun.EnumTypeClazz;
import com.creatoo.hn.model.WhgSysCult;
import com.creatoo.hn.services.home.agdwhlm.WhlmService;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.services.comm.CommService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 文化联盟
 * @author wangxl
 * @version 2016.11.16
 */
@RestController
@RequestMapping("/agdwhlm")
public class WhlmAction {
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
	 * 文化联盟服务类
	 */
	@Autowired
	public WhlmService whlmService;
	
	/**
	 * 首页
	 * @return 首页
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, WhgSysCult cult){
		ModelAndView view = new ModelAndView( "home/agdwhlm/index" );
		try {
			//查询区域
			view.addObject("areaList", this.commservice.findYwiType(EnumTypeClazz.TYPE_AREA.getValue()));

			//查询文化联盟列表
			PageInfo<Map> pageInfo = this.whlmService.t_search4p(request, cult);
			view.addObject("cultList", pageInfo.getList());//分馆列表
			view.addObject("page", pageInfo.getPageNum());//当前页
			view.addObject("pageSize", pageInfo.getPageSize());//每页数
			view.addObject("total", pageInfo.getTotal()); //总页数
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}

	/**
	 * 分馆详情页
	 * @return 分馆详情页
	 */
	@RequestMapping("/info/{id}")
	public ModelAndView info(HttpServletRequest request, @PathVariable("id")String id){
		ModelAndView view = new ModelAndView( "home/agdwhlm/info" );
		try {
			//分馆详情
			view.addObject("cult", this.whlmService.findCult(id));

			//通知公告
			view.addObject("noticeList", this.whlmService.findNotice4Cult(id));

			//活动
			view.addObject("actList", this.whlmService.findActivity4Cult(id));

			//培训
			view.addObject("traList", this.whlmService.findTrain4Cult(id));

			//场馆
			view.addObject("venList", this.whlmService.findVenue4Cult(id));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
}
