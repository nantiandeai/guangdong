package com.creatoo.hn.actions.admin.shop;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.WhgSysUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhUserTroupe;
import com.creatoo.hn.model.WhZxColinfo;
import com.creatoo.hn.services.admin.shop.MusInfoService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.UploadUtil;
/**
 * 栏目内容编辑
 * @author Administrator
 *
 */

@RestController
@RequestMapping("/admin/shop")
public class MusInfoAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	public CommService service;
	@Autowired
	private MusInfoService musInfoService;


    @RequestMapping("/view/{type}")
	@WhgOPT(optType = EnumOptType.NOTICE, optDesc = {"访问列表页","访问添加(编辑)页"},valid= {"type=list","type=add"})
	public Object view(@PathVariable("type")String type, WebRequest request) throws Exception{
        if ("add".equalsIgnoreCase(type)){
            ModelAndView view = new ModelAndView("admin/shop/colinfo/view_add");

            String id = request.getParameter("id");
            String targetShow = request.getParameter("targetShow");
            String clnftype = request.getParameter("clnftype");
            view.addObject("clnftype", clnftype);
            if (id != null){
                view.addObject("id", id);
                view.addObject("targetShow", targetShow);
                try {
                    view.addObject("info", this.musInfoService.srchOne(id));
                } catch (Exception e) {
                    log.error("加载指定ID的场馆信息失败", e);
                    throw e;
                }
            }
            return view;
        }

        return new ModelAndView("admin/shop/colinfo/view_list");
    }

    @RequestMapping("/add")
	@WhgOPT(optType = EnumOptType.NOTICE, optDesc = {"添加"})
	public Object add(HttpSession session, WhZxColinfo info, @DateTimeFormat(pattern="yyyy-MM-ddHH:mm:ss")Date clnfcrttime_str){
        ResponseBean rb = new ResponseBean();
        try {
            WhgSysUser sysUser = (WhgSysUser) session.getAttribute("user");
			if (clnfcrttime_str!=null){
				info.setClnfcrttime(clnfcrttime_str);
			}
			this.musInfoService.t_add(info, sysUser);
        }catch (Exception e){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("栏目信息保存失败");
            log.error(rb.getErrormsg(), e);
        }
        return rb;
    }

    @RequestMapping("/edit")
	@WhgOPT(optType = EnumOptType.NOTICE, optDesc = {"编辑"})
	public Object edit(HttpSession session, WhZxColinfo info, @DateTimeFormat(pattern="yyyy-MM-ddHH:mm:ss")Date clnfcrttime_str){
        ResponseBean rb = new ResponseBean();
        if (info.getClnfid() == null){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("栏目主键信息丢失");
            return rb;
        }
        try {
            WhgSysUser sysUser = (WhgSysUser) session.getAttribute("user");
			if (clnfcrttime_str!=null){
				info.setClnfcrttime(clnfcrttime_str);
			}
			if (info.getClnfkey()==null) info.setClnfkey("");

            this.musInfoService.t_edit(info, sysUser);
        }catch (Exception e){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("场馆信息保存失败");
            log.error(rb.getErrormsg(), e);
        }
        return rb;
    }

    //=========================================

	@RequestMapping("/colinfo")
	public ModelAndView index() {
		return new ModelAndView("admin/shop/colinfo");
	}
	/**
	 * 查询
	 */
	@RequestMapping("/seleinfo")
	public Object inquire(HttpServletRequest req, HttpServletResponse resp){
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		
		//分页查询
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
			rtnMap = this.musInfoService.selein(paramMap);
		} catch (Exception e) {
	        rtnMap.put("total", 0);
	        rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
        return rtnMap;
	}
	/**
	 * 添加栏目信息
	 */
	@RequestMapping("/addmusinfo")
	public Object addminfo(HttpServletRequest req, HttpServletResponse resp, WhZxColinfo whc, @RequestParam("colinfopic_up")MultipartFile colinfopic_up,@RequestParam("colinfobigpic_up")MultipartFile colinfobigpic_up) {
		String success = "0";
		String errmasg = "";
		//图片或者文件处理
		try {
			//当前日期
			Date now = new Date();
			//保存图片
			String uploadPath = UploadUtil.getUploadPath(req);
			//列表图
			if(colinfopic_up != null && !colinfopic_up.isEmpty()){
				String imgPath_colinfopic = UploadUtil.getUploadFilePath(colinfopic_up.getOriginalFilename(), service.getKey("art.picture"), "shop", "picture", now);
				colinfopic_up.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_colinfopic) );
				whc.setClnfpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_colinfopic));
			}
			//详情图
			if(colinfobigpic_up != null && !colinfobigpic_up.isEmpty()){
				String imgPath_colinfobigpic = UploadUtil.getUploadFilePath(colinfobigpic_up.getOriginalFilename(), service.getKey("art.picture"), "shop", "picture", now);
				colinfobigpic_up.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_colinfobigpic) );
				whc.setClnfbigpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_colinfobigpic));
			}
			whc.setClnfid(this.service.getKey("WhUserTroupe"));
			this.musInfoService.addinfo(whc);
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
	 * 编辑栏目信息
	 */
	@RequestMapping("/upminfo")
	public Object upinfo(HttpServletRequest req, HttpServletResponse resp, WhZxColinfo whc, @RequestParam("colinfopic_up")MultipartFile colinfopic_up,@RequestParam("colinfobigpic_up")MultipartFile colinfobigpic_up) {
		String success = "0";
		String errmasg = "";
		//图片或者文件处理
		try {
			//当前日期
			Date now = new Date();
			//保存图片
			String uploadPath = UploadUtil.getUploadPath(req);
			//列表图
			if(colinfopic_up != null && !colinfopic_up.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, whc.getClnfpic());
				
				String imgPath_colinfopic = UploadUtil.getUploadFilePath(colinfopic_up.getOriginalFilename(), service.getKey("art.picture"), "shop", "picture", now);
				colinfopic_up.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_colinfopic) );
				whc.setClnfpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_colinfopic));
			}
			//详情图
			if(colinfobigpic_up != null && !colinfobigpic_up.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, whc.getClnfbigpic());
				
				String imgPath_colinfobigpic = UploadUtil.getUploadFilePath(colinfobigpic_up.getOriginalFilename(), service.getKey("art.picture"), "shop", "picture", now);
				colinfobigpic_up.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_colinfobigpic) );
				whc.setClnfbigpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_colinfobigpic));
			}
			this.musInfoService.upmusinfo(whc);
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
	@RequestMapping("/delinfo")
	@WhgOPT(optType = EnumOptType.NOTICE, optDesc = {"删除"})
	public Object delete(String clnfid,HttpServletRequest req,String clnfpic,String clnfbigpic) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		 String success = "0";
		String errmsg = "";
        //删除图片
		try {
			String uploadPath = UploadUtil.getUploadPath(req);
			if(clnfpic!= null && !clnfpic.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, clnfpic);
			}
			if(clnfbigpic!= null && !clnfbigpic.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, clnfbigpic);
			}
			this.musInfoService.delete(clnfid);
		} catch (Exception e) {
			errmsg = e.getMessage();
		}

		// 返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	}
	/**
	 * 改变审核状态
	 */
	/**
	 * 审核状态
	 */
	@RequestMapping("/checkinfo")
	@WhgOPT(optType = EnumOptType.NOTICE, optDesc = {"审核","取消审核","发布","取消发布"},valid = {"clnfstata=2","clnfstata=0","clnfstata=3","clnfstata=2"})
	public String infoCheck(WhZxColinfo whz){
		Date a = new Date();
		try {
			whz.setClnfopttime(a);
			this.musInfoService.checkin(whz);
			return "success";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return "error";
		}
	}

	/**
	 * 置顶
	 * @param whz 对象
     * @return
     */
	@RequestMapping("/toTop")
	@WhgOPT(optType = EnumOptType.NOTICE, optDesc = {"置顶"})
	public String toTop(WhZxColinfo whz){
		try {
			this.musInfoService.toTop(whz);
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
	@RequestMapping("/goinfo")
	public Object goPage(WhZxColinfo whz){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		try {
			 this.musInfoService.goHomePage(whz);
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
	@RequestMapping("/checkeinfos")
	public Object sendCheck(String clnfid, int fromstate, int tostate){
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		//添加修改课时
		try {
			//修改
			this.musInfoService.checkexhi(clnfid, fromstate, tostate);
			
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		res.put("success", success);
		res.put("errmsg", errmsg);
		return res;
	}
}
