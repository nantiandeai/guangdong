package com.creatoo.hn.actions.admin.activity2;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhActivityitm;
import com.creatoo.hn.model.WhBrand;
import com.creatoo.hn.model.WhBrandAct;
import com.creatoo.hn.services.admin.activity2.BrandService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.UploadUtil;

@RestController
@RequestMapping("/admin/brand")
public class BrandActAction {
	/**
	 * 日志
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private BrandService brandService;
	@Autowired
	private CommService commservice;
	
	/**
	 * 返回视图
	 * @param request
	 * @return
	 */
	@RequestMapping("/showBrand")
	public ModelAndView showBrand(HttpServletRequest request){
		return new ModelAndView("admin/activity/brandact");
	}
	/**
	 * 返回活动管理视图
	 * @param request
	 * @return
	 */
	@RequestMapping("/showBrandAct")
	public ModelAndView showBrandAct(HttpServletRequest request,String braid){
		ModelAndView view = new ModelAndView("admin/activity/brandactmanage");
		try {
			view.addObject("braid", braid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return view;
	}
	/**
	 * 查询专题活动
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findBrand")
	public Object searchBrand(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		
		//分页查询
	    Map<String, Object> rtnMap = new HashMap<String, Object>();
	    try {
			rtnMap = (Map<String, Object>) this.brandService.searchBrand(paramMap);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
		return rtnMap;
	}
	/**
	 * 保存专题活动
	 * @param whBrand
	 * @param req
	 * @param brapic
	 * @param brabigpic
	 * @return
	 */
	@RequestMapping("/save")
	public Object saveBrand(WhBrand whBrand,HttpServletRequest req,@RequestParam("brapic_up")MultipartFile brapic, @RequestParam("brabigpic_up")MultipartFile brabigpic){
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		//添加修改课时
		try {
			//当前日期
			Date now = new Date();
			//图片或者文件处理
			String uploadPath = UploadUtil.getUploadPath(req);
			//图片处理
			//trapic
			if(brapic != null && !brapic.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, whBrand.getBrapic());
				String imgPath_trapic = UploadUtil.getUploadFilePath(brapic.getOriginalFilename(), commservice.getKey("brand.picture"), "brand", "picture", now);
				brapic.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_trapic) );
				whBrand.setBrapic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_trapic));
			}
			//trapic1
			if(brabigpic != null && !brabigpic.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, whBrand.getBrabigpic());
						
				String imgPath_trapic1 = UploadUtil.getUploadFilePath(brabigpic.getOriginalFilename(), commservice.getKey("brand.picture"), "brand", "picture", now);
				brabigpic.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_trapic1) );
				whBrand.setBrabigpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_trapic1));
			}
			//保存
			String braid = whBrand.getBraid();
			if(braid != null && !"".equals(braid.trim())){
				//修改
				whBrand.setBravoptime(new Date());
				this.brandService.updBrand(whBrand);
			}else{
				//添加
				whBrand.setBrastate(1);
				whBrand.setBravoptime(new Date());
				whBrand.setBraid(commservice.getKey("wh_traintpl"));
				this.brandService.addTrain(whBrand);
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
	 * 删除专题活动
	 * @param req
	 * @param braid
	 * @return
	 */
	@RequestMapping("/delBrand")
	public Object delBrand(HttpServletRequest req,String braid){
		String uploadPath = UploadUtil.getUploadPath(req);
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		try {
			this.brandService.delBrand(uploadPath,braid);
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
	 * 审核和发布
	 * @param traid
	 * @param trastate
	 * @return
	 */
	@RequestMapping("/check")
	public Object publish(String braid,Integer brastate){
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		//添加修改课时
		try {
			//修改
			this.brandService.pass(braid,brastate);
			
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		res.put("success", success);
		res.put("errmsg", errmsg);
		return res;
	}
	
	/**
	 * 单个操作打回
	 */
	@RequestMapping("/back")
	public Object goBack(String braid,Integer brastate){
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		Object result = null;
		//添加修改课时
		try {
		//	whTrain.setTrastate(trastate+1);
			//修改
			result = this.brandService.back(braid,brastate);
			
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		res.put("success", success);
		res.put("errmsg", errmsg);
		res.put("msg", result);
		return res;
	}
	/**
	 * 批量审核或者取消操作操作
	 */
	@RequestMapping("/checkBrand")
	public Object sendCheck(String braid, int fromstate, int tostate){
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		Object msg = null;
		//添加修改课时
		try {
			//修改
			msg = this.brandService.checkBrand(braid, fromstate, tostate);
			
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		res.put("success", success);
		res.put("errmsg", errmsg);
		res.put("msg", msg);
		return res;
	}
	/**
	 * 获取活动标题
	 * @return
	 */
	@RequestMapping("/getActTitle")
	public Object findActTitle(){
		return this.brandService.findActTitle();
	}
	
	/**
	 * 找到所有的活动管理 
	 */
	@RequestMapping("/findActBrand")
	public Object searchActBrand(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		
		//分页查询
	    Map<String, Object> rtnMap = new HashMap<String, Object>();
	    try {
			rtnMap = (Map<String, Object>) this.brandService.searchActBrand(paramMap);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
		return rtnMap;
	}
	/**
	 * 保存品牌活动的活动
	 * @return
	 */
	@RequestMapping("/savebrand")
	public Object saveBrand(WhBrandAct whBrandAct){
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		//添加修改课时
		try {
			String bracid = whBrandAct.getBracid();
			if(bracid != null && !"".equals(bracid.trim())){
//				//修改
				this.brandService.updBrandAct(whBrandAct);
			}else{
				//添加
				whBrandAct.setBracstate(1);
				whBrandAct.setBracid(commservice.getKey("wh_brand_act"));
				this.brandService.addBrand(whBrandAct);
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
	 * 删除
	 */
	@RequestMapping("/delActBrand")
	public Object delActBrand(String bracid){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		try {
			this.brandService.delActBrand(bracid);
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
	 * 通过活动ID 找到活动
	 */
	@RequestMapping("/getAct")
	public Object findAct(String actvid){
		return this.brandService.serachAct(actvid);
	}
	
	/**
	 * 判断是否能够取消发布
	 * @return
	 */
	@RequestMapping("/canNoPublish")
	public Object isPublish(String braid){
		return this.brandService.isOrNo(braid);
	}
	
	/**
	 * 
	 */
	@RequestMapping("/getActTime")
	public Object findActTime(String actvrefid){
		WhActivityitm act = null;
		try {
			act = this.brandService.findActTime(actvrefid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return act;
	}
}
