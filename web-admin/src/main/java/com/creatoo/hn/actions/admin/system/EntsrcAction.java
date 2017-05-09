package com.creatoo.hn.actions.admin.system;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.creatoo.hn.model.WhEntsource;
import com.creatoo.hn.services.admin.system.EntsrcService;

/**
 * Created by qxk on 2016/10/19.
 */

@Controller
@RequestMapping("/admin/ent")
public class EntsrcAction {
    Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private EntsrcService entsrcService;

    @RequestMapping("/entsrc")
    public String toEntSrcPage(WebRequest request,ModelMap mmp){
    	String refid = request.getParameter("refid");
    	String reftype = request.getParameter("reftype");
    	String canEdit = request.getParameter("canEdit");
    	String entsuorpro = request.getParameter("entsuorpro");
    	mmp.addAttribute("canEdit",canEdit);
    	mmp.addAttribute("entsuorpro",entsuorpro);
		mmp.addAttribute("refid", refid);
		mmp.addAttribute("reftype", reftype);
        return "admin/system/entsource";
    }

    @RequestMapping("/loadSrcList")
    @ResponseBody
    public Object loadEntSrcList(WhEntsource ent, WebRequest request){
        try{
            return this.entsrcService.selectEntsrcList(ent,request);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return null;
        }
    }
    /**
     * 修改或增加 资源
     * @param ent
     * @param request
     * @param file_enturl
     * @param file_deourl
     * @throws Exception
     */
    @RequestMapping("/addOrEditEntInfo")
    @ResponseBody
	public Object addOrEditEntInfo(HttpServletRequest request, WhEntsource ent, MultipartFile file_enturl,MultipartFile file_deourl) {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			this.entsrcService.addOrEditActInfo(ent, request, file_enturl,file_deourl);
			res.put("success", true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;
	}
	/**
	 * 删除资源信息操作
	 * 
	 * @return
	 */
	@RequestMapping("/entRomv")
	@ResponseBody
	public Object entRomv(HttpServletRequest request, String id) {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			this.entsrcService.entRomv(id,request);
			res.put("success", true);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			res.put("success", false);
			res.put("msg", e.getMessage());
		}
		return res;
	}
}
