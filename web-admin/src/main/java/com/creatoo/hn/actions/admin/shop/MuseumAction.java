package com.creatoo.hn.actions.admin.shop;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.emun.EnumOptType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhTyp;
import com.creatoo.hn.model.WhZxColumn;
import com.creatoo.hn.services.admin.shop.MuseumService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.UploadUtil;

@RestController
@RequestMapping("/admin/shop")
public class MuseumAction {
	//日志
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	public CommService commService;
	@Autowired
	private MuseumService museumService;
	
	@RequestMapping("/muse")
	@WhgOPT(optType = EnumOptType.COLUMN, optDesc = {"查看列表页"})
	public ModelAndView index() {
		return new ModelAndView("/admin/shop/museum");
	}
	
	
	/**
	 * 查询树形
	 * 
	 * @return
	 */
	@RequestMapping("/selmus")
	public Object inquire() {
		try {
			return this.museumService.inquire();
		} catch (Exception e) {
			log.debug(e.getMessage());
			return null;
		}
	}

	/**
	 * 添加
	 * @param whzx
	 * @param req
	 * @param resp
	 * @param muspic_up
     * @return
     */
	@RequestMapping("/addmus")
	@WhgOPT(optType = EnumOptType.COLUMN, optDesc = {"添加"})
	public Object add(WhZxColumn whzx,HttpServletRequest req, HttpServletResponse resp,@RequestParam("muspic_up")MultipartFile muspic_up) {
		String success = "0";
		String errmasg = "";
		try {
			//当前日期
			Date now = new Date();
			//保存图片
			String uploadPath = UploadUtil.getUploadPath(req);
			if(muspic_up != null && !muspic_up.isEmpty()){
				String imgPath_muspic = UploadUtil.getUploadFilePath(muspic_up.getOriginalFilename(), commService.getKey("art.picture"), "shop", "picture", now);
				muspic_up.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_muspic) );
				whzx.setColpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_muspic));
			}
			whzx.setColid(this.commService.getKey("WhZxColumn"));
			this.museumService.save(whzx);
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
	@RequestMapping("/upmus")
	@WhgOPT(optType = EnumOptType.COLUMN, optDesc = {"修改"})
	public Object revise(WhZxColumn whzx,HttpServletRequest req, HttpServletResponse resp,@RequestParam("muspic_up")MultipartFile muspic_up) {
		String success = "0";
		String errmasg = "";
		try {
			//当前日期
			Date now = new Date();
			String uploadPath = UploadUtil.getUploadPath(req);
			if (muspic_up !=null && !muspic_up.isEmpty()) {
			UploadUtil.delUploadFile(uploadPath, whzx.getColpic());
			//保存图片
			String imgPath_muspic = UploadUtil.getUploadFilePath(muspic_up.getOriginalFilename(), commService.getKey("art.picture"), "shop", "picture", now);
			muspic_up.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_muspic) );
			whzx.setColpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_muspic));
			}
			this.museumService.update(whzx);
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
	 * 删除deltyp
	 */
	@RequestMapping("/delmus")
	@WhgOPT(optType = EnumOptType.COLUMN, optDesc = {"删除"})
	public Object delete(String colid,HttpServletRequest req,String colpic) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		 String success = "0";
		String errmsg = "";
        //删除图片
		try {
			String uploadPath = UploadUtil.getUploadPath(req);
			if(colpic!= null && !colpic.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, colpic);
			}
			this.museumService.delete(colid);
		} catch (Exception e) {
			errmsg = e.getMessage();
		}

		// 返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	}
	
	/**
	 * 带状态查询树形
	 * 
	 * @return
	 */
	@RequestMapping("/selecol")
	public Object inquires() {
		try {
			return this.museumService.select();
		} catch (Exception e) {
			log.debug(e.getMessage());
			return null;
		}
	}
}
