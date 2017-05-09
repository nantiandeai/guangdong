package com.creatoo.hn.actions.home.agdpxyz;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.creatoo.hn.ext.emun.EnumTypeClazz;
import com.creatoo.hn.model.WhgYwiType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhTyp;
import com.creatoo.hn.model.WhUserTeacher;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.home.agdgwgk.GwgkService;
import com.creatoo.hn.utils.ReqParamsUtil;

/**
 * 培训驿站-培训师资
 * @author wangxl
 * @version 2016.11.16
 */
@RestController
@RequestMapping("/agdpxyz")
public class PxszAction {
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
	 * 馆务公开服务类
	 */
	@Autowired
	public GwgkService gwgkService;
	
	/**
	 * 培训老师列表
	 * @return 培训老师列表
	 */
	@RequestMapping("/teacher")
	@SuppressWarnings("unchecked")
	public ModelAndView teachlist(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView view = new ModelAndView( "home/agdpxyz/teacher" );
		try {
			//获取请求参数
			Map<String, Object> param = ReqParamsUtil.parseRequest(req);
			
			//分页查询培训老师
	        Map<String, Object> rtnMap = this.gwgkService.paggingTeacher(param);
	        try {
	        	List<WhUserTeacher> list = (List<WhUserTeacher>)rtnMap.get("rows");
	        	if(list != null){
	        		for(WhUserTeacher teacher : list){
	        			String teacherintroduce = teacher.getTeacherintroduce();
	        			if(teacherintroduce != null && teacherintroduce.length() > 40){
	        				teacherintroduce = teacherintroduce.substring(0, 40)+"...";
	        			}else if(teacherintroduce == null){
	        				teacherintroduce = "";
	        			}
	        			teacher.setTeacherintroduce(teacherintroduce);
	        		}
	        	}
	        	
				view.addObject("total", rtnMap.get("total"));
				view.addObject("rows", list);
				view.addObject("page", rtnMap.get("page"));
			} catch (Exception e) {
				view.addObject("total", 0);
				view.addObject("rows", null);
				view.addObject("page", 1);
			}
	        
	        //艺术分类
			List<WhgYwiType> typList = this.commservice.findYwiType(EnumTypeClazz.TYPE_ART.getValue());
			view.addObject("artList", typList);
	        
	        //区域
			List<WhgYwiType> areaList = this.commservice.findYwiType(EnumTypeClazz.TYPE_AREA.getValue());
			view.addObject("areaList", areaList);
			
			//请求参数
			if(req.getParameter("areaid") != null){
				view.addObject("areaid", req.getParameter("areaid"));
			}
			if(req.getParameter("artid") != null){
				view.addObject("artid", req.getParameter("artid"));
			}
			if(req.getParameter("srchkey") != null){
				view.addObject("srchkey", req.getParameter("srchkey"));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 培训老师详细
	 * @return 培训老师详细
	 */
	@RequestMapping("/teacherinfo")
	public ModelAndView teachdesc(String id){
		ModelAndView view = new ModelAndView( "home/agdpxyz/teacherinfo" );
		try {
			//老师详情
			view.addObject("teacher", this.gwgkService.queryOneTeacher(id));
			
			//推荐课程
			view.addObject("trainList", this.gwgkService.queryTeacherTrain(id));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
}
