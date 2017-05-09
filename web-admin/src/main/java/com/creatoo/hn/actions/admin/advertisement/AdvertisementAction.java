package com.creatoo.hn.actions.admin.advertisement;

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
import com.creatoo.hn.model.WhCfgAdv;
import com.creatoo.hn.model.WhUserTroupe;
import com.creatoo.hn.model.WhZxColumn;
import com.creatoo.hn.services.admin.adve.AdvertisementService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.UploadUtil;

/**
 * 广告配置
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/admin/adve")
public class AdvertisementAction {
	// 日志
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	public CommService commService;
	@Autowired
	private AdvertisementService advService;

	@RequestMapping("/adverti")
	public ModelAndView index() {
		return new ModelAndView("/admin/adve/advertisement");
	}

	/**
	 * 查询
	 */
	@RequestMapping("/seleadve")
	public Object inquire(HttpServletRequest req, HttpServletResponse resp) {
		// 获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);

		// 分页查询
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		try {
			rtnMap = this.advService.seleadv(paramMap);
		} catch (Exception e) {
			rtnMap.put("total", 0);
			rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
		return rtnMap;
	}

	/**
	 * 添加
	 */
	@RequestMapping("/addadves")
	public Object add(WhCfgAdv wfc, HttpServletRequest req, HttpServletResponse resp,
			@RequestParam("cfgadvpic_up") MultipartFile cfgadvpic_up) {
		String success = "0";
		String errmasg = "";
		try {
			// 当前日期
			Date now = new Date();
			// 保存图片
			String uploadPath = UploadUtil.getUploadPath(req);
			String imgPath_cfgadvpic = UploadUtil.getUploadFilePath(cfgadvpic_up.getOriginalFilename(),
					commService.getKey("art.picture"), "adve", "picture", now);
			cfgadvpic_up.transferTo(UploadUtil.createUploadFile(uploadPath, imgPath_cfgadvpic));
			wfc.setCfgadvpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_cfgadvpic));

			wfc.setCfgadvid(this.commService.getKey("WfCfgAdv"));
			this.advService.save(wfc);
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
	@RequestMapping("/upadves")
	public Object upadv(WhCfgAdv wfc, HttpServletRequest req, HttpServletResponse resp,
			@RequestParam("cfgadvpic_up") MultipartFile cfgadvpic_up) {
		String success = "0";
		String errmasg = "";
		try {
			String uploadPath = UploadUtil.getUploadPath(req);
			// 当前日期
			Date now = new Date();
			if (cfgadvpic_up != null && !cfgadvpic_up.isEmpty()) {
				UploadUtil.delUploadFile(uploadPath, wfc.getCfgadvpic());
				// 保存图片
				String imgPath_cfgadvpic = UploadUtil.getUploadFilePath(cfgadvpic_up.getOriginalFilename(),
						commService.getKey("art.picture"), "adve", "picture", now);
				cfgadvpic_up.transferTo(UploadUtil.createUploadFile(uploadPath, imgPath_cfgadvpic));
				wfc.setCfgadvpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_cfgadvpic));
			}
			this.advService.upadv(wfc);
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
	@RequestMapping("/deladves")
	public Object delete(String cfgadvid, HttpServletRequest req, String cfgadvpic) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		// 删除图片
		try {
			String uploadPath = UploadUtil.getUploadPath(req);
			if (cfgadvpic != null && !cfgadvpic.isEmpty()) {
				UploadUtil.delUploadFile(uploadPath, cfgadvpic);
			}
			this.advService.delete(cfgadvid);
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
	@RequestMapping("/dotype")
	public String wfcCheck(WhCfgAdv wfc) {
		try {
			this.advService.checkwfc(wfc);
			return "success";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return "error";
		}
	}
	
}
