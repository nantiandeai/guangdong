package com.creatoo.hn.actions.admin.advertisement;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import com.creatoo.hn.model.WhActivityitm;
import com.creatoo.hn.model.WhCfgList;
import com.creatoo.hn.services.admin.adve.CfgService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.UploadUtil;

/**
 * 页面元素配置
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/admin/adve")
public class CfgAction {
	//日志
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public CommService commService;
	
	@Autowired
	private CfgService cfgService;
	
	@RequestMapping("/cgf")
	public ModelAndView index() {
		return new ModelAndView("/admin/adve/whcfg");
	}
	
	/**
	 * 查询
	 * 
	 */
	@RequestMapping("/seletcgf")
	public Object selecgf(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.cfgService.inquire(paramMap);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
        return rtnMap;
	}
	
	/**
	 * 添加
	 */
	@RequestMapping("/addcgflist")
	public Object add(WhCfgList whc,HttpServletRequest req, HttpServletResponse resp,@RequestParam("cfgshowpic_up")MultipartFile cfgshowpic_up){
		String success = "0";
		String errmasg = "";
		try {
			//当前日期
			Date now = new Date();
			String uploadPath = UploadUtil.getUploadPath(req);
			//复制图片
			if (whc.getCfgshowpic() != null && !whc.getCfgshowpic().isEmpty() && cfgshowpic_up.isEmpty()) {
				String picUrl = UploadUtil.getUploadFileDelPath(uploadPath, whc.getCfgshowpic());
				String newUrl = UploadUtil.getUploadFilePath(whc.getCfgshowpic(), commService.getKey("art.picture"), "adve", "picture", now);
				FileUtil.copyFile(new File(picUrl),UploadUtil.createUploadFile(uploadPath, newUrl));
				whc.setCfgshowpic(UploadUtil.getUploadFileUrl(uploadPath,newUrl));
			}
			//保存图片
			if(cfgshowpic_up != null && !cfgshowpic_up.isEmpty()){
				String imgPath_cfgshowpic = UploadUtil.getUploadFilePath(cfgshowpic_up.getOriginalFilename(), commService.getKey("art.picture"), "adve", "picture", now);
				cfgshowpic_up.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_cfgshowpic) );
				whc.setCfgshowpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_cfgshowpic));
			}
			
			whc.setCfgid(this.commService.getKey("WhCfgList"));
			this.cfgService.save(whc);
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
	 * 编辑
	 * 
	 */
	@RequestMapping("/upcgf")
	public Object tocgf(WhCfgList whc,HttpServletRequest req, HttpServletResponse resp,@RequestParam("cfgshowpic_up")MultipartFile cfgshowpic_up){
		String success = "0";
		String errmasg = "";
		try {
			//当前日期
			Date now = new Date();
			String uploadPath = UploadUtil.getUploadPath(req);
			if(cfgshowpic_up != null && !cfgshowpic_up.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, whc.getCfgshowpic());
				//保存图片
				String imgPath_cfgshowpic = UploadUtil.getUploadFilePath(cfgshowpic_up.getOriginalFilename(), commService.getKey("art.picture"), "adve", "picture", now);
				cfgshowpic_up.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_cfgshowpic) );
				whc.setCfgshowpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_cfgshowpic));
			}
			this.cfgService.upwhcgf(whc);
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
	 * 删除
	 */
	@RequestMapping("/delcgf")
	public Object delete(String cfgid,HttpServletRequest req,String cfgshowpic) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
        //删除图片
		try {
			String uploadPath = UploadUtil.getUploadPath(req);
			if(cfgshowpic!= null && !cfgshowpic.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, cfgshowpic);
			}
			this.cfgService.delete(cfgid);
		} catch (Exception e) {
			errmsg = e.getMessage();
		}

		// 返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	}
	
	/**
	 * 改变状态
	 */
	@RequestMapping("/docfg")
	public String cfgCheck(WhCfgList whc){
		try {
			this.cfgService.checkcfg(whc);
			return "success";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return "error";
		}
	}
	
	/**
	 * 查询活动/培训/品牌/资讯信息
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("/srchEntList")
	public Object srchEntList(HttpServletRequest req, HttpServletResponse resp){
		Object rtnList = null;
        try {
        	//获取请求参数
        	String type = req.getParameter("type");//0-培训;1-活动;2-品牌;3-资讯
        	String clazz = req.getParameter("clazz");
        	String sort = req.getParameter("sort");
        	String order = req.getParameter("order");
        	
        	rtnList = this.cfgService.srchEntList(type, clazz, sort, order);
		} catch (Exception e) {
	        log.error(e.getMessage(),  e);
		}
        return rtnList;
	}
	/**
	 * 查询活动的第一批次的时间
	 */
	@RequestMapping("/selecthd")
	public Object srchEntList(String actvid){
		Object obj = null;
        try {
        	//获取请求参数
        	List<WhActivityitm> wha = this.cfgService.selecthd(actvid);
        	if (wha.size() > 0) {
				obj = wha.get(0);
			}
		} catch (Exception e) {
	        log.error(e.getMessage(),  e);
		}
		return obj;
		
       
	}
	
	
}
