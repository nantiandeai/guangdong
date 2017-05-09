package com.creatoo.hn.actions.admin.arts;

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

import com.creatoo.hn.model.WhArtExhibition;
import com.creatoo.hn.services.admin.arts.ExhibitionService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.UploadUtil;

/**
 * 艺术展览
 * @author lijun
 *
 */
@RestController
@RequestMapping("/admin/arts")
public class ExhibitionAction {
	/**
	 * 日志控制器 /admin/arts/whexhi
	 */
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	public CommService service;
	@Autowired
	private ExhibitionService exhibitionService;

	@RequestMapping("/whexhi")
	public ModelAndView index() {
		return new ModelAndView("admin/arts/exhibition");
	}
	/**
	 * 查询艺术展览信息
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */	
	@RequestMapping("/selexhib")
	public Object inquire(HttpServletRequest req, HttpServletResponse resp) {
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.exhibitionService.exhselect(paramMap);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
        return rtnMap;
	}
	/**
	 * 添加艺术展览信息
	 */
	@RequestMapping(value="/addexhib")
	public Object addexh(HttpServletRequest req, HttpServletResponse resp,String exhid,String exhtitle,String exharttyp,String exhtype, String exhdesc,
		int exhghp, int exhidx, int exhstate,String exhstime,String exhetime,@RequestParam(value="exhpic_up", required=false)MultipartFile exhpic_up, String exhpic) {
		String success = "0";
		String errmasg = "";
		//图片或者文件处理
		try {
			//转换时间格式
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			WhArtExhibition wha = new WhArtExhibition();
			//当前日期
			Date now = new Date();
			
			
			//保存图片
			if(exhpic_up != null && !exhpic_up.isEmpty()){
				String uploadPath = UploadUtil.getUploadPath(req);
				String imgPath_exhpic = UploadUtil.getUploadFilePath(exhpic_up.getOriginalFilename(), service.getKey("art.picture"), "art", "picture", now);
				exhpic_up.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_exhpic) );
				wha.setExhpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_exhpic));
			}else if(exhpic != null && !exhpic.isEmpty()){
				wha.setExhpic(exhpic);
			}
			
			wha.setExhid(this.service.getKey("WhArtExhibition"));
			wha.setExhtitle(exhtitle);
			wha.setExharttyp(exharttyp);
			wha.setExhtype(exhtype);
			wha.setExhdesc(exhdesc);
			wha.setExhghp(exhghp);
			wha.setExhstate(exhstate);
			wha.setExhidx(exhidx);
			wha.setExhstime(sdf.parse(exhstime));
			wha.setExhetime(sdf.parse(exhetime));
			this.exhibitionService.addexchi(wha);
		} catch (Exception e) {
			success = "1";
			errmasg = e.getMessage();
		}
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("success", success);
		res.put("msg", errmasg);
		return res;
	}
	/**
	 * 更新艺术展览信息
	 */
	@RequestMapping("/upexhib")
	public Object upexh(HttpServletRequest req, HttpServletResponse resp,String exhid,String exhtitle,String exharttyp,String exhtype, String exhdesc,
		int exhghp, int exhidx, int exhstate,String exhstime,String exhetime,@RequestParam(value="exhpic_up", required=false)MultipartFile exhpic_up, String exhpic) {
		String success = "0";
		String errmasg = "";
		//图片或者文件处理
		try {
			//转换时间格式
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			WhArtExhibition wha = new WhArtExhibition();
			wha.setExhpic(exhpic);
			
			//当前日期
			Date now = new Date();
			String uploadPath = UploadUtil.getUploadPath(req);
			if (exhpic_up !=null && !exhpic_up.isEmpty()) {
				//删除图片
				UploadUtil.delUploadFile(uploadPath, wha.getExhpic());
				//保存图片
				String imgPath_exhpic = UploadUtil.getUploadFilePath(exhpic_up.getOriginalFilename(), service.getKey("art.picture"), "art", "picture", now);
				exhpic_up.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_exhpic) );
				wha.setExhpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_exhpic));
			}
			//时间比较
		    int result = exhstime.compareTo(exhetime);
			if (result>0) {
				wha.setExhstime(now);
			}else {
				wha.setExhstime(sdf.parse(exhstime));
			}
			wha.setExhid(exhid);
			wha.setExhtitle(exhtitle);
			wha.setExharttyp(exharttyp);
			wha.setExhtype(exhtype);
			wha.setExhdesc(exhdesc);
			wha.setExhghp(exhghp);
			wha.setExhstate(exhstate);
			wha.setExhidx(exhidx);
			wha.setExhetime(sdf.parse(exhetime));
			this.exhibitionService.upexchi(wha);
		} catch (Exception e) {
			success = "1";
			errmasg = e.getMessage();
		}
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("success", success);
		res.put("msg", errmasg);
		return res;
	}
	/**
	 * 删除艺术展信息
	 */
	@RequestMapping("/delexhib")
	public Object delexhi(HttpServletRequest req, String exhid, String exhpic){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		//
		try {
			String uploadPath = UploadUtil.getUploadPath(req);
			if(exhpic!= null && !exhpic.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, exhpic);
			}
			exhibitionService.delgexh(exhid);
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
	 * 审核状态
	 */
	@RequestMapping("/checkexhib")
	public String exhCheck(WhArtExhibition wha){
		try {
			this.exhibitionService.checkexh(wha);
			return "success";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return "error";
		}
	}
	/**
	 * 上首页排序
	 * @param wha
	 * @return
	 */
	@RequestMapping("/goexhib")
	public Object goPage(WhArtExhibition wha){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		try {
			 this.exhibitionService.goHomePage(wha);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	}
	/**
	 * 批量审核或者取消操作操作
	 */
	@RequestMapping("/checkexi")
	public Object sendCheck(String exhid, int fromstate, int tostate){
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		//添加修改课时
		try {
			//修改
			this.exhibitionService.checkexhi(exhid, fromstate, tostate);
			
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		res.put("success", success);
		res.put("errmsg", errmsg);
		return res;
	}
}
