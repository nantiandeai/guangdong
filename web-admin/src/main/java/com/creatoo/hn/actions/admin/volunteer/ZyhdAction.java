package com.creatoo.hn.actions.admin.volunteer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.emun.EnumOptType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhZyhd;
import com.creatoo.hn.services.admin.volunteer.ZyhdService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.UploadUtil;
@RestController
@RequestMapping("/admin/zyhd")
public class ZyhdAction {
	
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private ZyhdService service;

	@Autowired
	private CommService commservice;
	/**
	 * 返回视图
	 * @return
	 */
	@RequestMapping("/index")
	@WhgOPT(optType = EnumOptType.VOLACT, optDesc = {"访问列表页"})
	public ModelAndView index(){
		return new ModelAndView("admin/volunteer/zyhd/view_list");
	}

	/**
	 * 添加项目示范表单
	 * @return
	 */
	@RequestMapping("/view/add")
	@WhgOPT(optType = EnumOptType.VOLACT, optDesc = {"访问添加页"})
	public ModelAndView addView(){
		return new ModelAndView("admin/volunteer/zyhd/view_add");
	}

	/**
	 * 编辑项目示范表单
	 * @return
	 */
	@RequestMapping("/view/edit")
	@WhgOPT(optType = EnumOptType.VOLACT, optDesc = {"访问编辑页"})
	public ModelAndView editView(String id){
		ModelAndView view = new ModelAndView("admin/volunteer/zyhd/view_edit");
		try{
			view.addObject("zyhdinfo", this.service.getWhgzyhd(id));
		}catch (Exception e){
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 分页查询志愿活动
	 * @param req
	 * @return
	 */
	@RequestMapping("/findzyhd")
	public Object findZyhd(HttpServletRequest req){
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.service.findzyhd(paramMap);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
		return rtnMap;
	}
	
	@RequestMapping("/save")
	@WhgOPT(optType = EnumOptType.VOLACT, optDesc = {"新增","编辑"},valid = {"zyhdid=null","zyhdid=notnull"})
	public Object save(WhZyhd whZyhd,HttpServletRequest req){
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		//添加修改课时
		try {
			//当前日期
			Date now = new Date();
					
			//保存
			String zyhdid = whZyhd.getZyhdid();
			if(zyhdid != null && !"".equals(zyhdid.trim())){
				//修改
				this.service.upZyhd(whZyhd);
			}else{
				//添加
				whZyhd.setZyhdstate(1);
				whZyhd.setZyhdid(commservice.getKey("wh_zyhd"));
				whZyhd.setZyhdopttime(now);
				this.service.addZyhd(whZyhd);
			}
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		res.put("success", success);
		res.put("errmsg", errmsg);
		return res;
	}
	
	/**
	 * 删除志愿培训
	 * @param zyhdid
	 * @param req
	 * @return
	 */
	@RequestMapping("/delhd")
	@WhgOPT(optType = EnumOptType.VOLACT, optDesc = {"删除"})
	public Object delZypx(String zyhdid,HttpServletRequest req){
		String uploadPath = UploadUtil.getUploadPath(req);
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		try {
			this.service.deleteZyhd(uploadPath,zyhdid);
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
	 * 审核或者取消操作
	 */
	@RequestMapping("/check")
	@WhgOPT(optType = EnumOptType.VOLACT, optDesc = {"审核","打回","发布","取消发布"},valid = {"tostate=2","tostate=1","tostate=3","tostate=2"})
	public Object sendCheck(String zyhdid, int fromstate, int tostate){
		Map<String, Object> res = new HashMap<String, Object>();
		//添加修改课时
		try {
			//修改
			Object msg = this.service.checkZyhd(zyhdid, fromstate, tostate);
			res.put("success", "success");
			res.put("msg", msg);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			res.put("error", "error"); 
		}
		return res;
	}
	
	/**
	 * 批量审核或者取消操作操作
	 */
	@RequestMapping("/checkAll")
	public Object CheckAll(String zyhdids, int fromstate, int tostate){
		Map<String, Object> res = new HashMap<String, Object>();
		//添加修改课时
		try {
			//修改
			Object msg = this.service.checkAllZyhd(zyhdids, fromstate, tostate);
			res.put("success", "success");
			res.put("msg", msg);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			res.put("error", "error"); 
		}
		return res;
	}
	
	/**
	 * 能否进行审核
	 * @param zyhdid
	 * @return
	 */
	@RequestMapping("/canCheck")
	public Map<String, Object> canCheck(String zyhdid){
		Map<String, Object> res = new HashMap<String, Object>();
		//添加修改课时
		try {
			//修改
			Object success = this.service.canCheck(zyhdid);
			res.put("success", success);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			res.put("error", "error"); 
		}
		return res;
	}
	
	/**
	 * 能否进行批量审核
	 * @param zyhdids
	 * @return
	 */
	@RequestMapping("/canCheckAll")
	public Map<String, Object> canCheckAll(String zyhdids){
		Map<String, Object> res = new HashMap<String, Object>();
		//添加修改课时
		try {
			//修改
			Object success = this.service.canCheckAll(zyhdids);
			res.put("success", success);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			res.put("error", "error"); 
		}
		return res;
	}
}
