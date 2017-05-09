package com.creatoo.hn.actions.admin.shop;

import com.creatoo.hn.model.WhZxUpload;
import com.creatoo.hn.services.admin.shop.UploadService;
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

@RestController
@RequestMapping("/admin/shop")
public class UploadAction {

	//日志
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	public CommService commService;
	@Autowired
	private UploadService uploadService;

	@RequestMapping("/uploads")
	public ModelAndView index() {
		return new ModelAndView("/admin/shop/upload");
	}

	/**
	 * 进入type(list|add|edit|view)视图
	 * @param request 请求对象
	 * @param type    视图类型(list|add|edit|view)
	 * @return 视图
	 */
	@RequestMapping("/uploads/view/{type}")
	public ModelAndView listview(HttpServletRequest request, @PathVariable("type") String type) {
		ModelAndView view = new ModelAndView("admin/shop/upload/view_" + type);
		String refid = request.getParameter("refid");
		String upid = request.getParameter("upid");

		view.addObject("refid", refid);
		view.addObject("upid", upid);
		view.addObject("upload", uploadService.select(upid));

		return view;
	}
	/**
	 * 查询
	 *
	 */
	@RequestMapping("/seletup")
	public Object selecuplo(HttpServletRequest req){
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);

		//分页查询
        Map<String, Object> rtnMap = new HashMap<>();
        try {
			rtnMap = this.uploadService.inquire(paramMap);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
        return rtnMap;
	}
    /**
     * 添加
     */
	@RequestMapping("/adduploda")
	public Object add(WhZxUpload whup,HttpServletRequest req){
		String success = "0";
		String errmasg = "";
		try {
			//当前日期
			Date now = new Date();
			//保存图片
//			String uploadPath = UploadUtil.getUploadPath(req);
//			//列表图
//			if(uplink_up != null && !uplink_up.isEmpty()){
//				String imgPath_uplink = UploadUtil.getUploadFilePath(uplink_up.getOriginalFilename(), commService.getKey("art.picture"), "shop", "picture", now);
//				uplink_up.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_uplink) );
//				whup.setUplink(UploadUtil.getUploadFileUrl(uploadPath, imgPath_uplink));
//			}
			whup.setUpid(this.commService.getKey("WhZxUpload"));
			whup.setUptime(now);
			this.uploadService.save(whup);
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
	 * 修改
	 */
	@RequestMapping("/douploda")
	public Object doloda(WhZxUpload whup){
		String success = "0";
		String errmasg = "";
		try {
			//当前日期
			Date now = new Date();
			//保存图片
//			String uploadPath = UploadUtil.getUploadPath(req);
//			//列表图
//			if(uplink_up != null && !uplink_up.isEmpty()){
//				UploadUtil.delUploadFile(uploadPath, whup.getUplink());
//
//				String imgPath_uplink = UploadUtil.getUploadFilePath(uplink_up.getOriginalFilename(), commService.getKey("art.picture"), "shop", "picture", now);
//				uplink_up.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_uplink) );
//				whup.setUplink(UploadUtil.getUploadFileUrl(uploadPath, imgPath_uplink));
//			}
			whup.setUptime(now);
			this.uploadService.updata(whup);
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
	@RequestMapping("/deluploda")
	public Object delup(String upid,HttpServletRequest req,String uplink){
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
        //删除图片
		try {
			String uploadPath = UploadUtil.getUploadPath(req);
			if(uplink!= null && !uplink.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, uplink);
			}
			this.uploadService.delete(upid);
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
	@RequestMapping("/upstate")
	public String upCheck(WhZxUpload whup){
		try {
			this.uploadService.checkup(whup);
			return "success";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return "error";
		}
	}
}
