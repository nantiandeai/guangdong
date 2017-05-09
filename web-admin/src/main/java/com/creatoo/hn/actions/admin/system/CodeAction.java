package com.creatoo.hn.actions.admin.system;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhCode;
import com.creatoo.hn.services.admin.system.CodeService;
import com.creatoo.hn.services.comm.CommService;

/**
 * 验证码控制类
 * @author dzl
 *
 */
@RestController
@RequestMapping("/admin")
public class CodeAction {
	 /**
	   * 日志控制器
	   */
		Logger log=Logger.getLogger(this.getClass());
		
		@Autowired
		public CommService commService;
		
		@Autowired
		private CodeService codeService;
		
		/**
		 * 验证码列表界面
		 */
		@RequestMapping("/code")
		public ModelAndView toMsg(){
			ModelAndView mav=new ModelAndView();
			mav.setViewName("admin/system/code");
			return mav;
		}
		
		/**
		 * 查询验证码
		 * @return
		 */
		@RequestMapping("/selectCode")
		public Object msgList(int page,int rows){
			return codeService.findPage(page, rows);
		}
		
		/**
		 * 添加验证码记录
		 */
	   @RequestMapping("/addCode")
	    public Object addCode(WhCode whcode){
	    	return this.codeService.addCode(whcode);
	    }
    
	    /**
		 * 跳转内容管理界面
		 * @return 
		 */
		@RequestMapping("/codepage")
		public ModelAndView msgpage(WebRequest request){
			ModelAndView mav = new ModelAndView("admin/system/codeedit");

			try {
				String id=request.getParameter("id");
				if(id!=null){
					mav.addObject("title","编辑验证码");
					Object code=this.codeService.getCodeId(id);
					mav.addObject("id",id);
					mav.addObject("code",code);
					
				}else{
					mav.addObject("title","添加验证码记录");
				}
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
			   return mav;
		}
		/**
		 * 删除验证码记录
		 * @param id
		 * @return
		 */
		@RequestMapping("/deleteCode")
		public Object removeCode(String id){
			Map<String,Object> del = new HashMap<String, Object>();
			try {
				codeService.removeCode(id);
				del.put("success", true);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
			return del;
		}

	/**
	 * 工具栏加载 数据
	 * @return
     */
	@RequestMapping("/loadMsg")
	@ResponseBody
	public Object loadMsg(int page, int rows, WebRequest request){
		try{
			return this.codeService.loadMsg(page, rows, request);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
}
