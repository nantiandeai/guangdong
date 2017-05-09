package com.creatoo.hn.actions.home.xuanchuan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhEntsource;
import com.creatoo.hn.model.WhFeedback;
import com.creatoo.hn.model.WhZxColinfo;
import com.creatoo.hn.model.WhZxUpload;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.home.xuanchuan.XuanchuanService;

/**
 * 宣传栏
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/xuanchuan")
public class XuanchuanAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public CommService commservice;
	
	@Autowired
	private XuanchuanService service;
	
	/**
	 * 宣传栏文化动态子类型
	 */
	@RequestMapping("/whdt")
	public ModelAndView jpwhz(){
		ModelAndView view = new ModelAndView( "home/xuanchuan/whdt" );
		try {
			String colid = "2016102700000006";
			
			//查询文化动态子栏目
			view.addObject("childList", this.service.selectwhhm(colid));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return view;
	}

	/**
	 * 馆务
	 */
	@RequestMapping("/gwgk")
	public ModelAndView gwgks(){
		ModelAndView view = new ModelAndView( "home/xuanchuan/gwgk" );
		try {
			String colid = "2016102700000009";
			view.addObject("childList", this.service.selectwhhm(colid));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return view;
	}
	
	/**
	 * 动态查询
	 */
	@RequestMapping("/gowhdt")
	public Object getAllwhdt(int page, int rows,String colid){
		Object whdt = null;
		try {
			//查询文化动态子栏目数据
			 whdt = this.service.selAllWhdt(page, rows,colid);
			 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return whdt;
	}

	
	
	/**
	 * 宣传栏办事指南
	 */
	@RequestMapping("/bszn")
	public ModelAndView bszn(){
		ModelAndView view = new ModelAndView( "home/xuanchuan/bszn" );
		try {
			String colid = "2016102700000008";
			
			view.addObject("childList",this.service.selectbszn(colid));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return view;
	}
	
	
	/**
	 * 宣传栏专题专栏
	 */
	@RequestMapping("/ztzl")
	public ModelAndView ztzl(){
		ModelAndView view = new ModelAndView( "home/xuanchuan/ztzl" );
		
		try {
			String colid = "2016110100000001";
			String cfgentclazz="2016110700000001";
			String cfgpagetype="2016111400000002";
			//查询文化动态子栏目
			view.addObject("childList", this.service.selectwhhm(colid));
			//专题专栏显示列表图和信息display
			view.addObject("cfg", this.service.selAllplays(cfgentclazz,cfgpagetype));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return view;
	}
	/**
	 * 宣传栏联系我们
	 */
	@RequestMapping("/lxwm")
	public ModelAndView lxwm(){
		ModelAndView view = new ModelAndView( "home/xuanchuan/lxwm" );
		try {
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return view;
	}
	/**
	 * 查询宣传栏目详细信息
	 * 
	 */
	@RequestMapping("/xqinfo")
	public ModelAndView xqzw(String clnfid){
		ModelAndView view = new ModelAndView( "home/xuanchuan/xqinfo" );
		try {
			//
			WhZxColinfo colinfo = (WhZxColinfo)this.service.selectinfo(clnfid);
			view.addObject("childList", colinfo);
			//上传
			List<WhZxUpload> whup =  this.service.selecup(clnfid);
			view.addObject("loadlist", whup);
			//资源图片
			String enttype="2016101400000055";
			List<WhEntsource> whe= this.service.selecent(clnfid,enttype);
			view.addObject("loadwhe", whe);
			//资源视频
			String type="2016101400000056";
			List<WhEntsource> ent= this.service.selecsource(clnfid,type);
			view.addObject("loadent", ent);
			//资源音频
			String clazz="2016101400000057";
			List<WhEntsource> whent= this.service.selecwhent(clnfid,clazz);
			view.addObject("loadclazz", whent);
			
			Map<String, String> map = this.service.selectColumn(colinfo.getClnftype());
			view.addObject("column", map.get("column"));
			view.addObject("columnIdx", map.get("columnIdx"));
			view.addObject("columnName", map.get("columnName"));
			
			//
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 保存意见反馈
	 * 
	 */
	@RequestMapping("/addfeed")
	public Object add(WhFeedback whf, HttpServletRequest request){
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			//
			String yanzhen = request.getParameter("feedyanzhen");
			String rand = (String) request.getSession().getAttribute("rand");
			
			if (yanzhen.equals(rand)) {
				whf.setFeedid(this.commservice.getKey("WhFeedback"));
				this.service.addinfo(whf);
				res.put("success", true);
			}else {
				throw new Exception("验证码不正确");
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;
	}
}
