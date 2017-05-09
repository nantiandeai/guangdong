package com.creatoo.hn.actions.admin.arts;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.emun.EnumOptType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.model.WhUserTroupe;
import com.creatoo.hn.model.WhUserTroupeuser;
import com.creatoo.hn.services.admin.arts.TroupeService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.UploadUtil;

/**
 * 用户艺术团
 * 
 * @author lijun
 *
 */
@RestController
@RequestMapping("/admin/arts")
public class TroupeAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	public CommService service;
	@Autowired
	private TroupeService troupeService;

	
	/**
     * 进入type(list|add|edit|view)视图
     * @param request 请求对象
     * @param type 视图类型(list|add|edit|view)
     * @return
     */
    @RequestMapping("/view/{type}")
	@WhgOPT(optType = EnumOptType.TEAM, optDesc = {"访问列表页","访问添加页","访问编辑页面"},valid= {"type=list","type=add","type=edit"})
	public ModelAndView listview(HttpServletRequest request, @PathVariable("type")String type){
        ModelAndView view = new ModelAndView("admin/arts/team/view_"+type);
        try {
            if("edit".equals(type) || "view".equals(type)){
                String id = request.getParameter("id");
                view.addObject("team", troupeService.t_srchOne(id));
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }

        return view;
    }
	
	@RequestMapping("/whtroupe")
	public ModelAndView index() {
		return new ModelAndView("admin/arts/team/view_list");
	}

	/**
	 * 查询艺术团队信息
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/seletroupe")
	public Object inquire(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.troupeService.troselect(paramMap);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
        return rtnMap;
	}

	/**
	 * 添加艺术团队信息
	 */
	@RequestMapping("/addtroupe")
	@WhgOPT(optType = EnumOptType.TEAM, optDesc = {"添加"})
	public Object addTrou(HttpServletRequest req,  WhUserTroupe whu) {
		ResponseBean res = new ResponseBean();
		try {
			whu.setTroupeid(this.service.getKey("WhUserTroupe"));
			whu.setTroupeopttime(new Date());
			whu.setTroupestate(0);
			whu.setCrtdate(new Date());
			this.troupeService.addgroup(whu);
		} catch (Exception e) {
			res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
		}
		return res;
	}
	/**
	 * 删除艺术团信息
	 */
	@RequestMapping("/deltroupe")
	@WhgOPT(optType = EnumOptType.TEAM, optDesc = {"删除"})
	public Object delTrou(HttpServletRequest req, String troupeid){
		ResponseBean res = new ResponseBean();
		
		try {
			/*String uploadPath = UploadUtil.getUploadPath(req);
			if(troupepic != null && !troupepic.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, troupepic);
			}
			if(troupebigpic != null && !troupebigpic.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, troupebigpic);
			}*/
		   troupeService.delgroup(troupeid);
		} catch (Exception e) {
			res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
		}
		return res;
		
	}
	/**
	 * 更新艺术团信息
	 */
	@RequestMapping("/uptroupe")
	@WhgOPT(optType = EnumOptType.TEAM, optDesc = {"编辑"})
	public Object upTrou(HttpServletRequest req, WhUserTroupe whu){
		ResponseBean res = new ResponseBean();
		try {
			whu.setTroupeopttime(new Date());
			this.troupeService.upgroup(whu);
		} catch (Exception e) {
			res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
		}
		return res;
	}
	/**
	 * 审核状态
	 */
	@RequestMapping("/checktroupe")
	@WhgOPT(optType = EnumOptType.TEAM, optDesc = {"审核","取消审核","发布","取消发布"},valid = {"troupestate=2","troupestate=0","troupestate=3","troupestate=2"})
	public String troupeCheck(WhUserTroupe whu){
		Date a = new Date();
		try {
			whu.setTroupeopttime(a);
			this.troupeService.checkTrou(whu);
			return "success";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return "error";
		}
	}
	//------------------------艺术团团员管理 --------------------------------------
	/**
	 * 转到团员管理页面
	 */
	@RequestMapping("/troupeUser")
	@WhgOPT(optType = EnumOptType.TEAMEM, optDesc = {"访问馆办团队成员管理列表页"})
	public ModelAndView troupeUser() {
		return new ModelAndView("admin/arts/troupeUser");
	}
	/**
	 * 拿到艺术团团员信息
	 */
	@RequestMapping("/findTroupeUser")
	public Object findTroupeUser(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
				Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
				
				//分页查询
		        Map<String, Object> rtnMap = new HashMap<String, Object>();
		        try {
					rtnMap = (Map<String, Object>) this.troupeService.findTroupeUser(paramMap);
				} catch (Exception e) {
			        rtnMap.put("total", 0);
			        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
				}
				
				return rtnMap;
	}
	/**
	 * 添加团员信息
	 * @param user
	 * @return
	 */
	@RequestMapping("/saveUser")
	@WhgOPT(optType = EnumOptType.TEAMEM, optDesc = {"添加","修改"},valid = {"tuid=null","tuid=notnull"})
	public Object addUser(WhUserTroupeuser user,HttpServletRequest req,@RequestParam("tupic_up")MultipartFile tupic){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		try {
			//当前日期
			Date now = new Date();
			//图片或者文件处理
			String uploadPath = UploadUtil.getUploadPath(req);
			//图片处理
			//trapic
			if(tupic != null && !tupic.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, user.getTupic());
				String imgPath_trapic = UploadUtil.getUploadFilePath(tupic.getOriginalFilename(), service.getKey("art.picture"), "art", "picture", now);
				tupic.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_trapic) );
				user.setTupic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_trapic));
			}
			
			String tuid = user.getTuid();
			if(tuid != null && !"".equals(tuid.trim())){
				//修改
				
				this.troupeService.updTroupeuser(user);
			}else{
				//添加
				user.setTuid(this.service.getKey("wh_user_troupeuser"));
				this.troupeService.saveUser(user);
			}
			
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
		
	}
	/**
	 * 删除团员信息
	 * @return
	 */
	@RequestMapping("/delUser")
	@WhgOPT(optType = EnumOptType.TEAMEM, optDesc = {"删除"})
	public Object delUser(HttpServletRequest req,String tuid){
		String uploadPath = UploadUtil.getUploadPath(req);
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		//
		try {
			this.troupeService.delTroupeUser(uploadPath,tuid);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		//返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	}
	/**
	 * 拿到艺术团名称
	 */
	@RequestMapping("/findTroupe")
	public Object findTroupe(){
		return this.troupeService.findTroupe();
	}
	/**
	 * 设置上首页及排序值
	 * @return
	 */
	@RequestMapping("/goTroupe")
	public Object goPage(WhUserTroupe whu){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		try {
			 this.troupeService.goHomePage(whu);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	}
	/**
	 * 艺术团批量审核
	 */
	@RequestMapping("/gocheckTrou")
	public Object goPagecheck(String troupeid, int fromstate, int tostate){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		try {
			 this.troupeService.goPage(troupeid,fromstate,tostate);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	}
}
