package com.creatoo.hn.actions.admin.volunteer;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.WhZyfcGeren;
import com.creatoo.hn.services.admin.volunteer.VolunPersonalService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.UploadUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 志愿者--先进个人控制类
 * @author dzl
 *
 */
@RestController
@RequestMapping("/admin/volun")
public class VolunPersonalAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private CommService commService;
	
	@Autowired
	private VolunPersonalService perService;
	
	/**
	 * 返回视图
	 * @return
	 */
	@RequestMapping("/personal")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("admin/volunteer/personal");
		return mav;
	}

	/**
	 * 返回视图
	 * @param request
	 * @param type 类型
	 * @return 视图
	 */
	@RequestMapping("/person/view/{type}")
	@WhgOPT(optType = EnumOptType.PERSON, optDesc = {"访问列表页","访问添加页","访问编辑页面"},valid= {"type=list","type=add","type=edit"})
	public ModelAndView listview(HttpServletRequest request, @PathVariable("type") String type) {
		ModelAndView view = new ModelAndView("admin/volunteer/person/view_" + type);

		try {
			String zyfcgrid = request.getParameter("zyfcgrid");
			String targetShow = request.getParameter("targetShow");
			view.addObject("zyfcgrid",zyfcgrid);
			view.addObject("targetShow", targetShow);
			view.addObject("person", this.perService.search_one(zyfcgrid));
		}catch (Exception e){
			log.error(e.getMessage(), e);
		}
		return view;
	}
	
	/**
	 * 分页查询先进个人
	 */
	@RequestMapping("/findPerson")
	public Object findPerson(HttpServletRequest req, HttpServletResponse resp){

		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.perService.findPerson(paramMap);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
		return rtnMap;
	}
	
	/**
	 * 保存先进人才（old）
	 * @param whGr
	 * @param req
	 * @param zyfcgrpic_up
	 * @param zyfcgrbigpic_up
	 * @return
	 */
	@RequestMapping("/savePerson")
	public Object addPerson(WhZyfcGeren whGr,HttpServletRequest req,@RequestParam("zyfcgrpic_up")MultipartFile zyfcgrpic_up, @RequestParam("zyfcgrbigpic_up")MultipartFile zyfcgrbigpic_up){
		Map<String, Object> map = new HashMap<>();
		String success = "0";
		String errmsg = "";
		// 一小时的毫秒数  
//		long nh = 1000 * 60 * 60;
		// 一天的毫秒数   
//		long nd = 1000 * 24 * 60 * 60;
//		long diff;
//		long hour = 0;
//		long day = 0;
		//添加修改课时
		try {
			//当前日期
			Date now = new Date();
//			 diff = now.getTime() - whGr.getZyfcgrjrtime().getTime();
//	         hour = diff % nd / nh + day * 24;// 计算差多少小时
			//图片或者文件处理
			String uploadPath = UploadUtil.getUploadPath(req);
			//图片处理
			//列表图
			if(zyfcgrpic_up != null && !zyfcgrpic_up.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, whGr.getZyfcgrpic());
				String imgPath_trapic = UploadUtil.getUploadFilePath(zyfcgrpic_up.getOriginalFilename(), commService.getKey("volunteer.picture"), "volunteer", "picture", now);
				zyfcgrpic_up.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_trapic) );
				whGr.setZyfcgrpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_trapic));
			}
			//详情图
			if(zyfcgrbigpic_up != null && !zyfcgrbigpic_up.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, whGr.getZyfcgrbigpic());
						
				String imgPath_trapic1 = UploadUtil.getUploadFilePath(zyfcgrbigpic_up.getOriginalFilename(), commService.getKey("volunteer.picture"), "volunteer", "picture", now);
				zyfcgrbigpic_up.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_trapic1) );
				whGr.setZyfcgrbigpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_trapic1));
			}
					
			//保存
			String grid = whGr.getZyfcgrid();
			if(grid != null && !"".equals(grid.trim())){
				//修改
				this.perService.upPerson(whGr);
			}else{
				//添加
				whGr.setZyfcgrstate(1);
				whGr.setZyfcgrid(commService.getKey("wh_zyfc_geren"));
				whGr.setZyfcgropttime(now);
				this.perService.addPerson(whGr);
			}
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		map.put("success", success);
		map.put("errmsg", errmsg);
		return map;
	}

	/**
	 * 新增或修改先进个人
	 * @param whzy 对象
     * @return res
     */
	@RequestMapping("/addOrUpdatePerson")
	@WhgOPT(optType = EnumOptType.PERSON, optDesc = {"新增","编辑"},valid = {"zyfcgrid=null","zyfcgrid=notnull"})
	public ResponseBean addOrUpdatePerson(WhZyfcGeren whzy){
		ResponseBean res = new ResponseBean();
		try {
			this.perService.addOrUpdatePerson(whzy);

		} catch (Exception e) {
			res.setSuccess(ResponseBean.FAIL);
			res.setErrormsg(e.getMessage());
			log.error(e.getMessage(), e);
		}
		return res;
	}

    /**
     * 删除
     * @param zyfcgrid 主键id
     * @return map
     */
	@RequestMapping("/delPerson")
	@WhgOPT(optType = EnumOptType.PERSON, optDesc = {"删除"})
	public Object delPerson(String zyfcgrid){
//		String uploadPath = UploadUtil.getUploadPath(req);
		Map<String, String> rtnMap = new HashMap<>();
		String success = "0";
		String errmsg = "";
		try {
			this.perService.delete(zyfcgrid);
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
	@RequestMapping("/checkPerson")
	@WhgOPT(optType = EnumOptType.PERSON, optDesc = {"审核","打回","发布","取消发布"},valid = {"tostate=2","tostate=1","tostate=3","tostate=2"})
	public Object sendCheck(String zyfcgrid, int fromstate, int tostate){
		Map<String, Object> map = new HashMap<>();
		try {
			//修改
			Object msg = this.perService.checkPerson(zyfcgrid, fromstate, tostate);
			map.put("success", "success");
			map.put("msg", msg);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			map.put("error", "error"); 
		}
		return map;
	}
	
	/**
	 * 批量审核或者取消操作操作
	 */
	@RequestMapping("/checkAllPer")
	public Object checkAllPer(String zyfcgrids, int fromstate, int tostate){
		Map<String, Object> map = new HashMap<>();
		//添加修改课时
		try {
			//修改
			Object msg = this.perService.checkAllPer(zyfcgrids, fromstate, tostate);
			map.put("success", "success");
			map.put("msg", msg);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			map.put("error", "error"); 
		}
		return map;
	}

	
}
