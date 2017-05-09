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

import com.creatoo.hn.model.WhTraintpl;
import com.creatoo.hn.services.admin.train.TrainMouldService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.UploadUtil;
@RestController
@RequestMapping("/admin/trainMould")
public class TrainMouldAction {
	
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public TrainMouldService service;
	
	@Autowired
	public CommService commservice;
	/**
	 * 返回新培训模板视图
	 * @param request
	 * @return
	 */
	@RequestMapping("/showTrainTpl")
	public ModelAndView showTrain(HttpServletRequest request){
		return new ModelAndView("admin/train/trainmould");
	}
	/**
	 * 分页查询培训模板信息
	 * @return
	 */
	@RequestMapping("/findTraintpl")
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
	 * 保存培训模板数据
	 * @return 进入培训模板管理界面
	 */
	@RequestMapping("/save")
	public Object saveTraintpl(WhTraintpl whtra, HttpServletRequest req, HttpServletResponse resp, @RequestParam("trapic_up")MultipartFile trapic, @RequestParam("trabigpic_up")MultipartFile trabigpic, 
			@RequestParam(value="trapersonfile_up", required=false)MultipartFile trapersonfile,@RequestParam(value="trateamfile_up", required=false)MultipartFile trateamfile){
		
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
			if(trabigpic != null && !trabigpic.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, whtra.getTrabigpic());
						
				String imgPath_trapic1 = UploadUtil.getUploadFilePath(trabigpic.getOriginalFilename(), commservice.getKey("train.picture"), "train", "picture", now);
				trabigpic.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_trapic1) );
				whtra.setTrabigpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_trapic1));
			}
			//个人附件
			if(trapersonfile != null && !trapersonfile.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, whtra.getTrapersonfile());
								
				String imgPath_userfile = UploadUtil.getUploadFilePath(trapersonfile.getOriginalFilename(), commservice.getKey("train.archive"), "train", "archive", now);
				trapersonfile.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_userfile) );
				whtra.setTrapersonfile(UploadUtil.getUploadFileUrl(uploadPath, imgPath_userfile));
			}
			//groupfile
			if(trateamfile != null && !trateamfile.isEmpty()){
				
				UploadUtil.delUploadFile(uploadPath, whtra.getTrateamfile());
								
				String imgPath_groupfile = UploadUtil.getUploadFilePath(trateamfile.getOriginalFilename(), commservice.getKey("train.archive"), "train", "archive", now);
				trateamfile.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_groupfile) );
				whtra.setTrateamfile(UploadUtil.getUploadFileUrl(uploadPath, imgPath_groupfile));
			}
					
			//保存
			String traplid = whtra.getTratplid();
			if(traplid != null && !"".equals(traplid.trim())){
				//修改
				this.service.updWhtra(whtra);
			}else{
				//添加
				whtra.setTraisonlyone(0);
				whtra.setTraisenrolqr(1);
				whtra.setTratplid(commservice.getKey("wh_traintpl"));
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
	 *  删除培训模板数据
	 * @param req
	 * @param traid
	 * @return
	 */
	@RequestMapping("/deltraintpl")
	public Object saveTraintpl(HttpServletRequest req,String tratplid){
		String uploadPath = UploadUtil.getUploadPath(req);
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		
		//如果有培训批次记录，不能删除
		try {
			this.service.deleteWhtra(uploadPath,tratplid);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		//返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	}

}
