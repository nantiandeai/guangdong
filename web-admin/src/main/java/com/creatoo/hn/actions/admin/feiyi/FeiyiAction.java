package com.creatoo.hn.actions.admin.feiyi;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.WhDecproject;
import com.creatoo.hn.model.WhSuccessor;
import com.creatoo.hn.model.WhSuorpro;
import com.creatoo.hn.services.admin.feiyi.FeiyiService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 
 * @author yanjianbo
 */
@Controller
@RequestMapping("admin/feiyi")
public class FeiyiAction {
	Logger log = Logger.getLogger(this.getClass());

	private static final String VIEW_DIR = "admin/feiyi/";


	@Autowired
	private FeiyiService feiyiService;
	/**
	 * 跳转名录管理界面
	 * @return
	 */
	@RequestMapping("/minglu")
	public String toMingluPage(String suorid,ModelMap model) {
		try {
			if(suorid != null && !"".equals(suorid)){
				model.addAttribute("suorid", suorid);
			}
			return VIEW_DIR + "minglu";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 跳转名录管理界面
	 * @return
	 */
	@RequestMapping("/minglu/view/{type}")
	@WhgOPT(optType = EnumOptType.MINGLU, optDesc = {"访问列表页","访问添加页","访问编辑页面"},valid= {"type=list","type=add","type=edit"})
	public ModelAndView listview(HttpServletRequest request, @PathVariable("type") String type) {
		ModelAndView view = new ModelAndView("admin/feiyi/minglu/view_" + type);

		try {
			if("edit".equals(type) || "view".equals(type)){
				String suorid = request.getParameter("suorid");
				String mlproid = request.getParameter("mlproid");
				String targetShow = request.getParameter("targetShow");
				view.addObject("suorid", suorid);
				view.addObject("mlproid", mlproid);
				view.addObject("targetShow", targetShow);
				view.addObject("ml", feiyiService.t_srchOne(mlproid));
			}
		}catch (Exception e){
			log.error(e.getMessage(), e);
		}
		return view;
	}

	/**
	 * 跳转传承人管理界面
	 * @return
	 */
	@RequestMapping("/successor/view/{type}")
	@WhgOPT(optType = EnumOptType.SUCCOR, optDesc = {"访问列表页","访问添加页","访问编辑页面"},valid= {"type=list","type=add","type=edit"})
	public ModelAndView tolistview(HttpServletRequest request, @PathVariable("type") String type) {
		ModelAndView view = new ModelAndView("admin/feiyi/successor/view_" + type);

		String suorid = request.getParameter("suorid");
		String mlproid = request.getParameter("mlproid");
		String targetShow = request.getParameter("targetShow");
		view.addObject("suorid", suorid);
		view.addObject("mlproid", mlproid);
		view.addObject("targetShow", targetShow);
		try {
			view.addObject("sc", feiyiService.srchOne(suorid));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	/**
	 * 跳转传承人管理界面
	 * @return
	 */
	@RequestMapping("/successor")
	public String toSccuessorPage() {
		try {
			return VIEW_DIR + "successor";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	
	
	/**
	 * 跳转传承人管理界面
	 * @return
	 */
	@RequestMapping("/mingluguli")
	public String toMingluguliPage(String suorid,ModelMap model) {
		try {
			if(suorid != null && !"".equals(suorid)){
				model.addAttribute("suorid", suorid);
			}
			return VIEW_DIR + "mingluguli";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 名录活动数据加载
	 * @return
	 */
	@RequestMapping("/loadMinglu")
	@ResponseBody
	public Object loadMinglu(String suorid, int rows,int page,WebRequest request){
		try {
			return this.feiyiService.loadMinglu(suorid,rows, page, request);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 传承人下拉框名录数据加载
	 * @return
	 */
	@RequestMapping("/loadMingluleavl")
	@ResponseBody
	public Object loadMingluleavl(String suorid,int rows,int page,WebRequest request){
		try {
			return this.feiyiService.loadMingluleavl(suorid,rows, page, request);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 名录推荐
	 * @param ids
	 * @param formrecoms
	 * @param torecom
	 * @return
	 */
	@RequestMapping("/updrecommend")
	@ResponseBody
	@WhgOPT(optType = EnumOptType.MINGLU, optDesc = {"推荐","取消推荐"},valid= {"torecom=1","torecom=0"})
	public ResponseBean updrecommend(String ids, String formrecoms, int torecom){
		ResponseBean res = new ResponseBean();
		try {
			res = this.feiyiService.t_updrecommend(ids,formrecoms,torecom);
		} catch (Exception e) {
			res.setSuccess(ResponseBean.FAIL);
			res.setErrormsg("推荐失败！");
			log.error(res.getErrormsg()+" formrecoms: "+formrecoms+" torecom:"+torecom+" ids: "+ids, e);
		}
		return res;
	}
	
	/**
	 * 传承人数据加载
	 * @return
	 */
	@RequestMapping("/loadSuccessor")
	@ResponseBody
	public Object loadSuccessor(int rows,int page,WebRequest request){
		try {
			return this.feiyiService.loadSuccessor(rows, page, request);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 增加或修改 名录项目
	 * @param request
	 * @param minglu
	 * @param file_image
	 * @param file_imagesm
	 * @return
	 */
	@RequestMapping("/addOrEditminglu")
	@ResponseBody
	@WhgOPT(optType = EnumOptType.MINGLU, optDesc = {"添加", "修改"}, valid = {"mlproid=null", "mlproid=notnull"})
	public Object addOrEditminglu(HttpServletRequest request, WhDecproject minglu, MultipartFile file_image,MultipartFile file_imagesm) {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			this.feiyiService.addOrEditminglu(minglu, request, file_image,file_imagesm);
			res.put("success", true);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;
	}

	  /**
     * 删除 名录
     * @param request
     * @param mlproid
     * @return
     */
    @RequestMapping("/removeMinglu")
	@ResponseBody
	@WhgOPT(optType = EnumOptType.MINGLU, optDesc = {"删除"})
	public Object removeMinglu(HttpServletRequest request, String mlproid) {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			this.feiyiService.removeMinglu(mlproid,request);
			res.put("success", true);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;
	}
	
	  /**
     * 删除 名录 传承人关联
     * @param spmlproid
     * @return
     */
    @RequestMapping("/removeSuorpro")
	@ResponseBody
	public Object removeSuorpro(String spmlproid) {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			String msg = this.feiyiService.removeSuorpro(spmlproid);
			res.put("msg", msg);
			res.put("success", true);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;
	}
    
    /**
     * 修改 或增加 传承人
     * @param request
     * @param successor
     * @param file_image
     * @return
     */
    @RequestMapping("/addOrEditsuccessor")
	@ResponseBody
	@WhgOPT(optType = EnumOptType.SUCCOR, optDesc = {"添加","修改"},valid= {"suorid=null","suorid=notnull"})
    public Object addOrEditsuccessor(HttpServletRequest request, WhSuccessor successor, MultipartFile file_image) {
		Map<String, Object> res = new HashMap<String, Object>();
		String mlproid = request.getParameter("mlproid");
		try {
			this.feiyiService.addOrEditsuccessor(successor, request, file_image);
			res.put("success", true);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;
	}
    
    /**
     * 删除传承人
     * @param request
     * @param suorid
     * @return
     */
    @RequestMapping("/removeSuccessor")
  	@ResponseBody
	@WhgOPT(optType = EnumOptType.SUCCOR, optDesc = {"删除"})
	public Object removeSuccessor(HttpServletRequest request, String suorid) {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			this.feiyiService.removeSuccessor(suorid,request);
			res.put("success", true);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;
	}
    
    /**
     * 增加 传承人  名录项目关联
     * @param sp
     * @return
     */
    @RequestMapping("/addsuorpro")
  	@ResponseBody
	public Object addsuorpro(WhSuorpro sp) {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			this.feiyiService.addsuorpro(sp);
			res.put("success", true);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;
	}
    
    /**
     * 名录项目修改状态(old)
     * @param decpject
     * @param fromstate
     * @param tostate
     * @return
     */
	@RequestMapping("/editState")
	@ResponseBody
	public Object editState(WhDecproject decpject,int fromstate,int tostate,WebRequest request) {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			Object msg=this.feiyiService.editState(decpject,fromstate,tostate,request);
			res.put("success", "0");
			res.put("msg",msg );
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			res.put("error", "error"); 
		}
		return res;
	}

	/**
	 * 名录修改状态(new)
	 * @param ids
	 * @param formstates
	 * @param tostate
	 * @return
	 */
	@RequestMapping("/updstate")
	@ResponseBody
	@WhgOPT(optType = EnumOptType.MINGLU, optDesc = {"发布","取消发布"},valid= {"tostate=6","tostate=4"})
	public ResponseBean updstate(String ids, String formstates, int tostate){
		ResponseBean res = new ResponseBean();
		try {
			res = this.feiyiService.t_updstate(ids, formstates, tostate);
		}catch (Exception e){
			res.setSuccess(ResponseBean.FAIL);
			res.setErrormsg("状态更改失败");
			log.error(res.getErrormsg()+" formstate: "+formstates+" tostate:"+tostate+" ids: "+ids, e);
		}
		return res;
	}

	/**
	 * 查询所有名录
	 * @return
     */
	@RequestMapping("/selMingLu")
	@ResponseBody
	public List<WhDecproject> selMingLu() {
		List<WhDecproject> list = new ArrayList<>();
		try {
			list = this.feiyiService.selMingLu();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 名录修改审批状态
	 * @param params
	 * @return
	 */
	@RequestMapping("/pleditState")
	@ResponseBody
	public Object pleditState(WhDecproject decproject,int fromstate,int tostate,String params) {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			Object msg=this.feiyiService.pleditState(decproject,fromstate,tostate,params);
			res.put("success", "success");
			res.put("msg", msg);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			res.put("error", "error"); 
		}
		return res;
	}
	
	 /**
     * 传承人修改状态(old)
     * @param suor
     * @param fromstate
     * @param tostate
     * @return
     */
	@RequestMapping("/editStatesuor")
	@ResponseBody
	public Object editStatesuor(WhSuccessor suor,int fromstate,int tostate) {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			Object msg=this.feiyiService.editStatesuor(suor,fromstate,tostate);
			res.put("success", "0");
			res.put("msg",msg );
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			res.put("error", "error"); 
		}
		return res;
	}

	/**
	 * 传承人修改状态(new)
	 * @param ids
	 * @param formstates
	 * @param tostate
	 * @return
	 */
	@RequestMapping("/sucupdstate")
	@ResponseBody
	@WhgOPT(optType = EnumOptType.SUCCOR, optDesc = {"发布","取消发布"},valid= {"tostate=6","tostate=4"})
	public ResponseBean sucupdstate(String ids, String formstates, int tostate){
		ResponseBean res = new ResponseBean();
		try {
			res = this.feiyiService.updstate(ids, formstates, tostate);
		}catch (Exception e){
			res.setSuccess(ResponseBean.FAIL);
			res.setErrormsg("状态更改失败");
			log.error(res.getErrormsg()+" formstate: "+formstates+" tostate:"+tostate+" ids: "+ids, e);
		}
		return res;
	}
	
	/**
	 * 传承人修改审批状态
	 * @param fromstate
	 * @return
	 */
	@RequestMapping("/pleditStatesuor")
	@ResponseBody
	public Object pleditStatesuor(WhSuccessor suor,int fromstate,int tostate,String params) {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			Object msg=this.feiyiService.pleditStatesuor(suor,fromstate,tostate,params);
			res.put("success", "success");
			res.put("msg", msg);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			res.put("error", "error"); 
		}
		return res;
	}

	/**
	 * 传承人推荐
	 * @param ids
	 * @param formrecoms
	 * @param torecom
	 * @return
	 */
	@RequestMapping("/updaterecommend")
	@ResponseBody
	@WhgOPT(optType = EnumOptType.SUCCOR, optDesc = {"推荐","取消推荐"},valid= {"torecom=1","torecom=0"})
	public ResponseBean updateRecommend(String ids, String formrecoms, int torecom){
		ResponseBean res = new ResponseBean();
		try {
			res = this.feiyiService.updrecommend(ids,formrecoms,torecom);
		} catch (Exception e) {
			res.setSuccess(ResponseBean.FAIL);
			res.setErrormsg("推荐失败！");
			log.error(res.getErrormsg()+" formrecoms: "+formrecoms+" torecom:"+torecom+" ids: "+ids, e);
		}
		return res;
	}
}
