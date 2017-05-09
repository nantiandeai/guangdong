package com.creatoo.hn.actions.admin.train;

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

import com.creatoo.hn.model.WhTra;
import com.creatoo.hn.services.admin.train.TrainTplService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.UploadUtil;

/**
 * 培训模板管理控制器
 * @author wangxl
 * @version 20161010
 */
@RestController
@RequestMapping("/admin")
public class TrainTplAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public TrainTplService service;
	
	@Autowired
	public CommService commservice;
	
	/**
	 * 进入培训模板管理界面
	 * @return 进入培训模板管理界面
	 */
	@RequestMapping("/traintpl/gopage")
	public ModelAndView index(String traid){
		ModelAndView view = new ModelAndView("admin/train/traintpl");
		try {
			if (traid != null && !"".equals(traid.trim())) {
				// 编辑
				view.addObject("title", "编辑培训模板");
				view.addObject("whtra", service.searchWhtrd(traid));
			} else {
				// 添加
				view.addObject("title", "添加培训模板");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return view;
	}
	
	
	/**
	 * 分页查询培训模板信息
	 * @return
	 */
	@RequestMapping("/traintpl/sreachTraintpl")
	public Object sreachTrain(HttpServletRequest req, HttpServletResponse resp)throws Exception{
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.service.paggingTratpl(paramMap);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
		
		return rtnMap;
	}
	
	/**
	 * 根据traid加载表单数据
	 * @return 进入培训模板管理界面
	 */
	@RequestMapping("/traintpl/gettratpl")
	public Object getTraintpl(String traid){
		return null;
	}
	
	/**
	 * 保存培训模板数据
	 * @return 进入培训模板管理界面
	 */
	@RequestMapping("/traintpl/savetratpl")
	public Object saveTraintpl(WhTra whtra, HttpServletRequest req, HttpServletResponse resp, @RequestParam("trapic_up")MultipartFile trapic, @RequestParam("trapic_home")MultipartFile trapic1,@RequestParam("trapic_de")MultipartFile trapic2, 
			@RequestParam(value="userfile_up", required=false)MultipartFile userfile,@RequestParam(value="groupfile_up", required=false)MultipartFile groupfile){
		
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
			if(trapic != null && !trapic.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, whtra.getTrapic());
				String imgPath_trapic = UploadUtil.getUploadFilePath(trapic.getOriginalFilename(), commservice.getKey("train.picture"), "train", "picture", now);
				trapic.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_trapic) );
				whtra.setTrapic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_trapic));
			}
			//trapic1
			if(trapic1 != null && !trapic1.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, whtra.getTrapic1());
						
				String imgPath_trapic1 = UploadUtil.getUploadFilePath(trapic1.getOriginalFilename(), commservice.getKey("train.picture"), "train", "picture", now);
				trapic1.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_trapic1) );
				whtra.setTrapic1(UploadUtil.getUploadFileUrl(uploadPath, imgPath_trapic1));
			}
			//trapic2
			if(trapic2 != null && !trapic2.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, whtra.getTrapic2());
							
				String imgPath_trapic2 = UploadUtil.getUploadFilePath(trapic2.getOriginalFilename(), commservice.getKey("train.picture"), "train", "picture", now);
				trapic2.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_trapic2) );
				whtra.setTrapic2(UploadUtil.getUploadFileUrl(uploadPath, imgPath_trapic2));
			}
			//userfile
			if(userfile != null && !userfile.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, whtra.getUserfile());
								
				String imgPath_userfile = UploadUtil.getUploadFilePath(userfile.getOriginalFilename(), commservice.getKey("train.archive"), "train", "archive", now);
				userfile.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_userfile) );
				whtra.setUserfile(UploadUtil.getUploadFileUrl(uploadPath, imgPath_userfile));
			}
			//groupfile
			if(groupfile != null && !groupfile.isEmpty()){
				
				UploadUtil.delUploadFile(uploadPath, whtra.getGroupfile());
								
				String imgPath_groupfile = UploadUtil.getUploadFilePath(groupfile.getOriginalFilename(), commservice.getKey("train.archive"), "train", "archive", now);
				groupfile.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_groupfile) );
				whtra.setGroupfile(UploadUtil.getUploadFileUrl(uploadPath, imgPath_groupfile));
			}
					
			//保存
			String traid = whtra.getTraid();
			if(traid != null && !"".equals(traid.trim())){
				//修改
				this.service.updWhtra(whtra);
			}else{
				//添加
				whtra.setTraid(commservice.getKey("whart"));
				this.service.addWhtra(whtra);
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
	 * 删除培训模板数据
	 * @return 进入培训模板管理界面
	 */
	@RequestMapping("/traintpl/deltratpl")
	public Object saveTraintpl(HttpServletRequest req,String traid){
		String uploadPath = UploadUtil.getUploadPath(req);
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		
		//如果有培训批次记录，不能删除
		try {
			this.service.deleteWhtra(uploadPath,traid);
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
	 * 进入培训审核列表
	 * @return 进入培训审核列表
	 */
	@RequestMapping("/train/checklist")
	public ModelAndView checklist(){
		ModelAndView view = new ModelAndView("admin/train/checklist");
		return view;
	}
	
	/**
	 * 进入培训发布列表
	 * @return 进入培训审核列表
	 */
	@RequestMapping("/train/publishlist")
	public ModelAndView publishlist(){
		ModelAndView view = new ModelAndView("admin/train/publishlist");
		return view;
	}
	
	/**
	 * 分页查询培训审核和发布
	 * @param req 请求
	 * @param resp 响应
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/train/sreachAllTrain")
	public Object sreachAllTrain(HttpServletRequest req, HttpServletResponse resp)throws Exception{
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.service.paggingTrainitm(paramMap);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
		return rtnMap;
	}
	
	/**
	 * 得到所有的培训模板标题
	 * @return
	 */
	@RequestMapping("/train/getAllTitle")
	public Object getAllTitle(){
		return this.service.selAllTitle();
	}
}
