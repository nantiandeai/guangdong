package com.creatoo.hn.actions.admin.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.creatoo.hn.model.WhUserArtist;
import com.creatoo.hn.services.admin.user.ActilstServices;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.UploadUtil;


/**
 * 艺术家管理
 * Created by qxk on 2016/10/17.
 */

@Controller
@RequestMapping("/admin/user")
public class ArtistAction {
    Logger log = Logger.getLogger(this.getClass());

    private static final String VIEW_DIR = "admin/user/";

    @Autowired
    private ActilstServices actilstServices;

    @RequestMapping("/toartistlist")
    public String toArtistListPage(){
        return VIEW_DIR+"artistlist";
    }
/**
 * 带条件查询
 * @param request
 * @param artist
 * @return 
 */
    @RequestMapping("/loadArtistList")
    @ResponseBody
    public Object loadArtistList(HttpServletRequest req, HttpServletResponse resp){
    	//获取请求参数
    	 Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
        //分页查询
    	Map<String, Object> rtnMap = new HashMap<String, Object>();
    	 try {
    	      rtnMap = this.actilstServices.loadActilsList(paramMap);
    	 } catch (Exception e) {
    		  rtnMap.put("total", 0);
    		  rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
    	   }
    	return rtnMap;
    }
/**
 * 添加和编辑
 * @param artist
 * @param request
 * @param file_artistpic
 * @return
 */
    @RequestMapping("/addOrEditArtist")
    @ResponseBody
    public Object addOrEditArtist(WhUserArtist artist, HttpServletRequest request, MultipartFile file_artistpic){
        Map<String, Object> res = new HashMap<String, Object>();
        try{
            String uploadPath = UploadUtil.getUploadPath(request);// request.getSession().getServletContext().getRealPath("/upload");
            this.actilstServices.addOrEditActilst(artist,uploadPath,file_artistpic);
            res.put("success", true);
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
            res.put("success", false);
            res.put("msg", e.getMessage());
        }
        return res;
    }

    @RequestMapping("/removeArtist")
    @ResponseBody
    public Object removeArtist(String artistid, HttpServletRequest request){
        Map<String, Object> res = new HashMap<String, Object>();
        try{
            String uploadPath = UploadUtil.getUploadPath(request);//request.getSession().getServletContext().getRealPath("/upload");
            this.actilstServices.removeActilst(artistid, uploadPath);
            res.put("success", true);
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
            res.put("success", false);
            res.put("msg", e.getMessage());
        }
        return res;
    }
    /**
	 * 审核状态改变
	 */
	@RequestMapping("/updateState")
	@ResponseBody
	public String actitmCheck(WhUserArtist art){
		try {
			this.actilstServices.checkAct(art);
			return "success";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return "error";
		}
	}
	/**
	 * 上首页 排序
	 */
	@RequestMapping("/goarts")
	@ResponseBody
	public Object goPage(WhUserArtist whus){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		try {
			 this.actilstServices.goHomePage(whus);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	}
	/**
	 * 批量生成
	 */
	@RequestMapping("/gochcek")
	@ResponseBody
	public Object gocheck(String artistid, int fromstate, int tostate){
		Map<String, Object> res = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		//添加修改课时
		try {
			//修改
			this.actilstServices.checkeart(artistid, fromstate, tostate);
			
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		res.put("success", success);
		res.put("errmsg", errmsg);
		return res;
	}
}
