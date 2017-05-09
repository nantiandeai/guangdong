package com.creatoo.hn.actions.home.gypx;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.creatoo.hn.model.WhTyp;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.home.gypx.GypxService;

/**
 * Created by qxk on 2016/10/20.
 */

@Controller
@RequestMapping("/gypx")
public class GypxAction {
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private GypxService gypxService;
	@Autowired
	private CommService commService;

    /** 进入公益培训首页
     * @return
     */
    @RequestMapping("/index")
    public String toGypxIndex(ModelMap mmp){
    	mmp.addAttribute("gypx_adv", this.gypxService.findGypxAdv());
    	try {
			List<?> zxlist = (List<?>) this.gypxService.selectGypxZx();
			mmp.addAttribute("gypz_zxlist", zxlist);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
        return "home/gypx/gypxpageIndex";
    }
    
    /** 进入公益培训列表页
     * @return
     */
    @RequestMapping("/list")
    public String toGypxList(WebRequest request, ModelMap mmp){
    	//培训分类
    	List<WhTyp> pxtypes = null;
    	//艺术分类
    	List<WhTyp> ystypes = null;
    	//区域分类
    	List<WhTyp> qrtypes = null;
    	try {
    		pxtypes = this.commService.findWhtyp("2");
    		ystypes = this.commService.findWhtyp("0");
    		qrtypes = this.commService.findWhtyp("8");
    		
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
    	//参数带入的培训分类处理
    	String pxtype = request.getParameter("pxtype");
    	String ystype = request.getParameter("ystype");
    	mmp.addAttribute("pxtype", pxtype);
    	mmp.addAttribute("ystype", ystype);
    	
    	//培训分类和传入的当前培训类型
    	mmp.addAttribute("pxtypes", pxtypes);
    	//艺术分类
    	mmp.addAttribute("ystypes", ystypes);
    	//区域
    	mmp.addAttribute("qrtypes", qrtypes);
    	
    	mmp.addAttribute("gypx_adv", this.gypxService.findGypxAdv());
    	
    	return "home/gypx/gypxpagelist";
    }
    
    /** 进入公益培训详情页
     * @return
     */
    @RequestMapping("/detail/{id}")
    public String toGypxDetail(WebRequest request, ModelMap mmp, @PathVariable String id){
    	try {
    		//String id = request.getParameter("id");
			Map<String,Object> gypx = this.gypxService.findGypxInfo(id);
			mmp.addAttribute("px", gypx);
			
			//视频
			List<?> volsit = (List<?>) this.gypxService.selectEntSrc("2016101400000056", id);
			List<?> mplsit = (List<?>) this.gypxService.selectEntSrc("2016101400000057", id);
			mmp.addAttribute("vos", volsit);
			mmp.addAttribute("mps", mplsit);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
    	mmp.addAttribute("gypx_adv", this.gypxService.findGypxAdv());
    	return "home/gypx/gypxpageDetail";
    }
    
    /** 提取上首页的培训列表
     * @param request
     * @return
     */
    @RequestMapping("/searchHpList")
    @ResponseBody
    public Object searchHpList(WebRequest request){
    	try {
			return this.gypxService.searchHpList(request);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
    }
    
    /** 提取培训分类信息
     * @return
     */
    @RequestMapping("/loadTypes")
    @ResponseBody
    public Object searchGypxType(){
    	try {
			return this.commService.findWhtyp("2");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
    }
    
    /** 提取艺术分类信息
     * @return
     */
    @RequestMapping("/loadYsTypes")
    @ResponseBody
    public Object searchYsType(){
    	try {
    		return this.commService.findWhtyp("0");
    	} catch (Exception e) {
    		log.error(e.getMessage(), e);
    		return null;
    	}
    }
    
    /** 加载公益培训列表数据
     * @param request
     * @return
     */
    @RequestMapping("/loadGypxList")
    @ResponseBody
    public Object loadGypxList(WebRequest request){
    	try {
			return this.gypxService.searchGypxList(request);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
    }
    
    
    /** 提取与当月有关的相关课程日期
     * @return
     */
    @RequestMapping("/monthDateList")
    @ResponseBody
    public Object getTraitmtimeMonthDateList(){
    	try {
			return this.gypxService.selectTraitmtimeMonth();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
    }
    
    /** 按指定课程日期查询相关的培训信息集
     * @param day
     * @return
     */
    @RequestMapping("/searchGypx4Day")
    @ResponseBody
    public Object searchGypx4Day(String day){
    	try {
			return this.gypxService.selectGypx4Day(day);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
    }
    
    @RequestMapping("/searchGypxTimes")
    @ResponseBody
    public Object searchGypxTimes(String traitmid){
    	try {
			return this.gypxService.selectGypxTimes(traitmid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
    }
    
}
