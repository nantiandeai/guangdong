package com.creatoo.hn.actions.admin.train;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhTrain;
import com.creatoo.hn.model.WhTraintpl;
import com.creatoo.hn.services.admin.train.TrainManageService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.UploadUtil;

@RestController
@RequestMapping("/admin/trainManage")
public class TrainManageAction {
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private TrainManageService service;
	@Autowired
	private CommService commservice;
	/**
	 * 返回新的培训管理视图
	 * @param request
	 * @return
	 */
	@RequestMapping("/showTrain")
	public ModelAndView showTrain(HttpServletRequest request){
		return new ModelAndView("admin/train/trainmanage");
	}
	/**
	 * 返回新的培训审核视图
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkTra")
	public ModelAndView checkTrain(HttpServletRequest request){
		return new ModelAndView("admin/train/check");
	}
	/**
	 * 返回新的培训发布视图
	 * @param request
	 * @return
	 */
	@RequestMapping("/publish")
	public ModelAndView publishTrain(HttpServletRequest request){
		return new ModelAndView("admin/train/publish");
	}
	/**
	 * 分页查询培训审核和发布
	 * @param req 请求
	 * @param resp 响应
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectTrain")
	public Object sreachAllTrain(HttpServletRequest req, HttpServletResponse resp){
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
	 * 保存培训管理数据
	 * @param whTrain
	 * @param req
	 * @param resp
	 * @param trapic
	 * @param trabigpic
	 * @param trapersonfile
	 * @param trateamfile
	 * @return
	 */
	@RequestMapping("/saveTrain")
	public Object saveTraintpl(String tratplid,WhTrain whTrain,String trasdate, HttpServletRequest req, HttpServletResponse resp, @RequestParam("trapic_up")MultipartFile trapic, @RequestParam("trabigpic_up")MultipartFile trabigpic, 
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
			if (whTrain.getTrapic() != null && !"".equals(whTrain.getTrapic()) && !tratplid.isEmpty()) {
				String picUrl = UploadUtil.getUploadFileDelPath(uploadPath, whTrain.getTrapic());
				String newUrl = UploadUtil.getUploadFilePath(whTrain.getTrapic(), commservice.getKey("train.picture"), "train", "picture", now);
				FileUtil.copyFile(new File(picUrl),UploadUtil.createUploadFile(uploadPath, newUrl));
				whTrain.setTrapic(UploadUtil.getUploadFileUrl(uploadPath,newUrl));
			}
			if (whTrain.getTrabigpic() != null && !"".equals(whTrain.getTrabigpic()) && !tratplid.isEmpty()) {
				String picUrl = UploadUtil.getUploadFileDelPath(uploadPath, whTrain.getTrabigpic());
				String newUrl = UploadUtil.getUploadFilePath(whTrain.getTrabigpic(), commservice.getKey("train.picture"), "train", "picture", now);
				FileUtil.copyFile(new File(picUrl),UploadUtil.createUploadFile(uploadPath, newUrl));
				whTrain.setTrabigpic(UploadUtil.getUploadFileUrl(uploadPath,newUrl));
			}
			if (whTrain.getTrapersonfile() != null && !whTrain.getTrapersonfile().isEmpty() && !tratplid.isEmpty()) {
				String picUrl = UploadUtil.getUploadFileDelPath(uploadPath, whTrain.getTrapersonfile());
				String newUrl = UploadUtil.getUploadFilePath(whTrain.getTrapersonfile(), commservice.getKey("train.archive"), "train", "archive", now);
				FileUtil.copyFile(new File(picUrl),UploadUtil.createUploadFile(uploadPath, newUrl));
				whTrain.setTrapersonfile(UploadUtil.getUploadFileUrl(uploadPath,newUrl));
			}
			if (whTrain.getTrateamfile() != null && !whTrain.getTrateamfile().isEmpty() && !tratplid.isEmpty()) {
				String picUrl = UploadUtil.getUploadFileDelPath(uploadPath, whTrain.getTrateamfile());
				String newUrl = UploadUtil.getUploadFilePath(whTrain.getTrateamfile(), commservice.getKey("train.archive"), "train", "archive", now);
				FileUtil.copyFile(new File(picUrl),UploadUtil.createUploadFile(uploadPath, newUrl));
				whTrain.setTrateamfile(UploadUtil.getUploadFileUrl(uploadPath,newUrl));
			}
			
			if(trapic != null && !trapic.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, whTrain.getTrapic());
				String imgPath_trapic = UploadUtil.getUploadFilePath(trapic.getOriginalFilename(), commservice.getKey("train.picture"), "train", "picture", now);
				trapic.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_trapic) );
				whTrain.setTrapic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_trapic));
			}
			//trapic1
			if(trabigpic != null && !trabigpic.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, whTrain.getTrabigpic());
						
				String imgPath_trapic1 = UploadUtil.getUploadFilePath(trabigpic.getOriginalFilename(), commservice.getKey("train.picture"), "train", "picture", now);
				trabigpic.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_trapic1) );
				whTrain.setTrabigpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_trapic1));
			}
			//个人附件
			if(trapersonfile != null && !trapersonfile.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, whTrain.getTrapersonfile());
								
				String imgPath_userfile = UploadUtil.getUploadFilePath(trapersonfile.getOriginalFilename(), commservice.getKey("train.archive"), "train", "archive", now);
				trapersonfile.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_userfile) );
				whTrain.setTrapersonfile(UploadUtil.getUploadFileUrl(uploadPath, imgPath_userfile));
			}
			//groupfile
			if(trateamfile != null && !trateamfile.isEmpty()){
				
				UploadUtil.delUploadFile(uploadPath, whTrain.getTrateamfile());
								
				String imgPath_groupfile = UploadUtil.getUploadFilePath(trateamfile.getOriginalFilename(), commservice.getKey("train.archive"), "train", "archive", now);
				trateamfile.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_groupfile) );
				whTrain.setTrateamfile(UploadUtil.getUploadFileUrl(uploadPath, imgPath_groupfile));
			}
					
			//保存
			String traid = whTrain.getTraid();
			if(traid != null && !"".equals(traid.trim())){
				if (whTrain.getTraisenrol() == 0 ) {
					whTrain.setTraisenrolqr(0);
				}else {
					whTrain.setTraisenrolqr(1);
				}
				//修改
				this.service.updWhtra(whTrain);
			}else{
				if (whTrain.getTraisenrol() == 0 ) {
					whTrain.setTraisenrolqr(0);
				}else {
					whTrain.setTraisenrolqr(1);
				}
				//添加
				whTrain.setTrastate(0);
				whTrain.setTraisonlyone(0);
				whTrain.setTraisenrolqr(1);
				whTrain.setTraid(commservice.getKey("wh_traintpl"));
				if (whTrain.getTracanperson() == null) {
					whTrain.setTracanperson(0);
				}
				if (whTrain.getTracanteam() == null) {
					whTrain.setTracanteam(0);
				}
				this.service.addTrain(whTrain);
			}
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
			log.error(e.getMessage(), e);
		}
		res.put("success", success);
		res.put("errmsg", errmsg);
		return res;
	}
	/**
	 *  删除培训数据
	 * @param req
	 * @param traid
	 * @return
	 */
	@RequestMapping("/deltrain")
	public Object saveTraintpl(HttpServletRequest req,String traid){
		String uploadPath = UploadUtil.getUploadPath(req);
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
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
	 * 批量审核或者取消操作操作
	 */
	@RequestMapping("/checkTrain")
	public Object sendCheck(String traid, int fromstate, int tostate,String iss){
		Map<String, Object> res = new HashMap<String, Object>();
		//添加修改课时
		try {
			//修改
			Object msg = this.service.checkWhtra(traid, fromstate, tostate,iss);
			res.put("success", "success");
			res.put("msg", msg);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			res.put("error", "error"); 
		}
		return res;
	}
	/**
	 * 单个操作审核，状态加1
	 */
	@RequestMapping("/passTrain")
	public Object publish(String traid,Integer trastate){
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		//添加修改课时
		try {
			//修改
			this.service.pass(traid,trastate);
			
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		res.put("success", success);
		res.put("errmsg", errmsg);
		return res;
	}
	/**
	 * 单个操作打回打回
	 */
	@RequestMapping("/goBack")
	public Object goBack(String traid,Integer trastate){
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		//添加修改课时
		try {
		//	whTrain.setTrastate(trastate+1);
			//修改
			this.service.back(traid,trastate);
			
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		res.put("success", success);
		res.put("errmsg", errmsg);
		return res;
	}
	
	/**
	 * 设置上首页及排序值
	 * @return
	 */
	@RequestMapping("/goPage")
	public Object goPage(WhTrain whTrain){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		try {
			 this.service.goHomePage(whTrain);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	}
	/**
	 * 拿到所有标题
	 * @return
	 */
	@RequestMapping("/getAllTitle")
	public Object getAllTitle(){
		return this.service.getTitle();
	}
	/**
	 * 拿到所有培训模板标题
	 * @return
	 */
	@RequestMapping("/getTratplTitle")
	public Object getTratplTitle(){
		return this.service.getTraTplTitle();
	}
	/**
	 * 根据ID拿到模板
	 * @return
	 */
	@RequestMapping("/getTratpl")
	public Object getTratpl(String tratplid){
		return this.service.getTratpl(tratplid);
	}
	
	/**
	 * 页面配置不带分页的条件查询
	 */
	@RequestMapping("/getTra")
	public Object seletTrain(String tratyp,String trastate){
		Object tra=this.service.seletAll(tratyp,trastate);
		return tra;
		
	}
	/**
	 * 判断是否能够取消发布
	 * @return
	 */
	@RequestMapping("/canNoPublish")
	public int isPublish(String traid){
		return	this.service.isOrNo(traid);
	}
	/**
	 * 判断是否能够送审
	 * @return
	 */
	@RequestMapping("/isCheck")
	public Object isCheck(String traid){
		return this.service.isCanCheck(traid);
	}
	/**
	 * 判断是否能够送审
	 * @return
	 */
	@RequestMapping("/isCheckAll")
	public Object isCheckAll(String traids){
		return this.service.isCanCheckAll(traids);
	}
	
	/**
	 * 设为模板
	 */
	@RequestMapping("/setTratpl")
	public Object setTraintpl(WhTraintpl whTraintpl, HttpServletRequest req){
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
			
			if (whTraintpl.getTrapic() != null) {
				String picUrl = UploadUtil.getUploadFileDelPath(uploadPath, whTraintpl.getTrapic());
				String newUrl = UploadUtil.getUploadFilePath(whTraintpl.getTrapic(), commservice.getKey("train.picture"), "train", "picture", now);
				FileUtil.copyFile(new File(picUrl),UploadUtil.createUploadFile(uploadPath, newUrl));
				whTraintpl.setTrapic(UploadUtil.getUploadFileUrl(uploadPath,newUrl));
			}
			if (whTraintpl.getTrabigpic() != null) {
				String picUrl = UploadUtil.getUploadFileDelPath(uploadPath, whTraintpl.getTrabigpic());
				String newUrl = UploadUtil.getUploadFilePath(whTraintpl.getTrabigpic(), commservice.getKey("train.picture"), "train", "picture", now);
				FileUtil.copyFile(new File(picUrl),UploadUtil.createUploadFile(uploadPath, newUrl));
				whTraintpl.setTrabigpic(UploadUtil.getUploadFileUrl(uploadPath,newUrl));
			}
			if (whTraintpl.getTrapersonfile() != null && !whTraintpl.getTrapersonfile().isEmpty()) {
				String picUrl = UploadUtil.getUploadFileDelPath(uploadPath, whTraintpl.getTrapersonfile());
				String newUrl = UploadUtil.getUploadFilePath(whTraintpl.getTrapersonfile(), commservice.getKey("train.picture"), "train", "picture", now);
				FileUtil.copyFile(new File(picUrl),UploadUtil.createUploadFile(uploadPath, newUrl));
				whTraintpl.setTrapersonfile(UploadUtil.getUploadFileUrl(uploadPath,newUrl));
			}
			if (whTraintpl.getTrateamfile() != null && !whTraintpl.getTrateamfile().isEmpty()) {
				String picUrl = UploadUtil.getUploadFileDelPath(uploadPath, whTraintpl.getTrateamfile());
				String newUrl = UploadUtil.getUploadFilePath(whTraintpl.getTrateamfile(), commservice.getKey("train.picture"), "train", "picture", now);
				FileUtil.copyFile(new File(picUrl),UploadUtil.createUploadFile(uploadPath, newUrl));
				whTraintpl.setTrateamfile(UploadUtil.getUploadFileUrl(uploadPath,newUrl));
			}
					
			
			//添加
			whTraintpl.setTraisonlyone(0);
			whTraintpl.setTratplid(commservice.getKey("wh_traintpl"));
			this.service.setTraintpl(whTraintpl);
			
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		res.put("success", success);
		res.put("errmsg", errmsg);
		return res;
//		whTraintpl.setTratplid(this.commservice.getKey("wh_traintpl"));
//		return this.service.setTraintpl(whTraintpl);
	}
	/**
	 * 查找老师
	 */
	@RequestMapping("/findTeacher")
	public Object findTeacher(){
		
		return this.service.findTeacher();
	}
}
