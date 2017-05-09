package com.creatoo.hn.actions.admin.arts;

import java.io.File;
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

import com.creatoo.hn.model.WhArt;
import com.creatoo.hn.model.WhUserTroupe;
import com.creatoo.hn.model.WhZxinfo;
import com.creatoo.hn.services.admin.arts.QunwenService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.UploadUtil;

/**
 * 用户资讯展示控制器
 * @author wangxl
 * @version 2016.09.27
 */
@RestController
@RequestMapping("/admin/arts")
public class ZxzsAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public QunwenService service;
	
	@Autowired
	public CommService commservice;
	
	/**
	 * 资讯内容管理界面
	 * @return 资讯内容管理界面
	 */
	@RequestMapping("/zxpage")
	public ModelAndView index(){
		return new ModelAndView( "admin/arts/zxinfo" );
	}
	
	/**---------------------------艺术作品---------------------------------------------------*/
	
	/**
	 * 精品文化展界面
	 * @return 精品文化展界面
	 */
	@RequestMapping("/whzp")
	public ModelAndView jpwhz(){
		return new ModelAndView( "admin/arts/whzp" );
	}
	
	/**
	 * 分页查询培训审核和发布
	 * @param req 请求
	 * @param resp 响应
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sreachAllWhzp")
	public Object sreachAllTrain(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.service.paggingAllJpwhz(paramMap);
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
	@RequestMapping("/saveWhzp")
	public Object saveWhzp(WhArt whart, HttpServletRequest req, HttpServletResponse resp, @RequestParam(value="artpic_up", required=false)MultipartFile artpic_up, @RequestParam(value="artpic2_up", required=false)MultipartFile artpic2_up,String artstate){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		
		//添加修改课时
		try {
			//当前日期
			Date now = new Date();
		    int result = whart.getArtstime().compareTo(whart.getArtetime());
			    if (result>0) {
				    whart.setArtstime(now);
				}else {
					whart.setArtstime(whart.getArtstime());
				 }
			
			//图片或者文件处理
			String uploadPath = UploadUtil.getUploadPath(req);
			
			//图片处理
			//artpic_up
			if(artpic_up != null && !artpic_up.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, whart.getArtpic());
				
				String imgPath_artpic = UploadUtil.getUploadFilePath(artpic_up.getOriginalFilename(), commservice.getKey("train.picture"), "train", "picture", now);
				artpic_up.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_artpic) );
				whart.setArtpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_artpic));
			}
			
			//artpic1_up
//			if(artpic1_up != null && !artpic1_up.isEmpty()){
//				UploadUtil.delUploadFile(uploadPath, whart.getArtpic1());
//				
//				String imgPath_artpic1 = UploadUtil.getUploadFilePath(artpic1_up.getOriginalFilename(), commservice.getKey("train.picture"), "train", "picture", now);
//				artpic1_up.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_artpic1) );
//				whart.setArtpic1(UploadUtil.getUploadFileUrl(uploadPath, imgPath_artpic1));
//			}
//			
			//artpic2_up
			if(artpic2_up != null && !artpic2_up.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, whart.getArtpic2());
						
				String imgPath_artpic2 = UploadUtil.getUploadFilePath(artpic2_up.getOriginalFilename(), commservice.getKey("train.picture"), "train", "picture", now);
				artpic2_up.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_artpic2) );
				whart.setArtpic2(UploadUtil.getUploadFileUrl(uploadPath, imgPath_artpic2));
			}
			
			//保存
			String artid = whart.getArtid();
			if(artid != null && !"".equals(artid.trim())){
				
				
				//修改
				this.service.updWhart(whart);
			}else{
				//添加
				whart.setArtid(commservice.getKey("whart"));
				this.service.addWhart(whart);
			}
			
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
	 * 删除艺术作品
	 * @param artid 艺术作品标识
	 * @return 
	 */
	@RequestMapping("/delWhzp")
	public Object delWhzp(String id){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		
		//删除艺术作品
		try {
			service.delWhart(id);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		
		//返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	}
	/**--------------------------END-艺术作品---------------------------------------------------*/
	
	/**
	 * 个人文化作品展界面
	 * @return 精品文化展界面
	 */
	@RequestMapping("/grwhz")
	public ModelAndView grwhz(){
		return new ModelAndView( "admin/arts/grwhz" );
	}
	
	/**
	 * 资讯内容管理界面
	 * @return 资讯内容管理界面
	 */
	@RequestMapping("/zxinfopage")
	public ModelAndView zxinfopage(String id){
		ModelAndView view = new ModelAndView( "admin/zxinfoedit" );
		
		try {
			//设置页面标题
			String pageTitle = "添加资讯内容";
			String method = "add";
			if(!"add".equals(id)){
				pageTitle = "编辑资讯内容";
				method = "edit";
				
				//编辑详情
				view.addObject("zxinfo", service.findZxinfoById(id));
			}
			view.addObject("zxtitle", pageTitle);
			view.addObject("method", method);
			
			//查询标签
			view.addObject("zxtags", service.findZxinfoTags());
			
			//查询关键字
			view.addObject("zxkeys", service.findZxinfoKeys());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return view;
	}
	
	/**
	 * 搜索资讯内容
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findzx")
	public Object findAllZxinfo(int page, int rows)throws Exception{

		return service.findAllZx(page, rows);
	}
	
	/**
	 * 添加资讯信息
	 * @return 资讯对象和成功标识
	 */
	@RequestMapping("/addzx")
	public Object addInfo(WhZxinfo zxinfo, HttpServletRequest req, HttpServletResponse resp, @RequestParam("zximg")MultipartFile file)throws Exception{
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		
		//
		try {
			//图片处理
			String imgName = "zx/a.jpg";
			String path = req.getSession().getServletContext().getRealPath("/upload");
			File imgFile = new File(path, imgName);
			if(!imgFile.exists()){
				imgFile.mkdirs();
			}
			file.transferTo(imgFile);
			
			//资讯信息
			service.addZxinfo(zxinfo);
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
	 * 编辑资讯信息
	 * @return 资讯对象和成功标识
	 */
	@RequestMapping("/editzx")
	public Object editInfo(WhZxinfo zxinfo, HttpServletRequest req, HttpServletResponse resp, @RequestParam("zximg")MultipartFile file)throws Exception{
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		
		//
		try {
			//图片处理
			String imgName = "zx/a.jpg";
			String path = req.getSession().getServletContext().getRealPath("/upload");
			File imgFile = new File(path, imgName);
			if(!imgFile.exists()){
				imgFile.mkdirs();
			}
			file.transferTo(imgFile);
			
			//添加资讯信息
			service.addZxinfo(zxinfo);
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
	 * 删除资讯信息
	 * @return 资讯对象和成功标识
	 */
	@RequestMapping("/delzx")
	public Object delInfo(String id)throws Exception{
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		
		//
		try {
			service.delZxinfo(id);
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
	 * 
	 * @param type
	 * @return
	 */
	@RequestMapping("/findType")
	public Object findType(String type){
		return this.service.findType(type);
	}
	/**
	 * 审核状态
	 */
	@RequestMapping("/checkType")
	public String troupeCheck(WhArt wha){
		try {
			this.service.checkinfo(wha);
			return "success";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return "error";
		}
	}
	/**
	 * 设置上首页及排序值
	 * @return
	 */
	@RequestMapping("/gowha")
	public Object goPage(WhArt wha){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		try {
			 this.service.goHomePage(wha);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	}
	/**
	 * 批量审核
	 */
	@RequestMapping("/goAllwha")
	public Object goPage(String artid, int fromstate, int tostate){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		try {
			 this.service.goAllPage(artid,fromstate,tostate);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	}
	
}
