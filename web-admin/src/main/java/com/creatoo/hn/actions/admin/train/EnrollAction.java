package com.creatoo.hn.actions.admin.train;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhTraitmtime;
import com.creatoo.hn.services.admin.train.EnrollService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.EmailUtil;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.SmsUtil;
import com.creatoo.hn.utils.WhConstance;

/**
 * 培训-报名控制器
 * @author wangxl
 * @version 2016.10.08
 */
@RestController
@RequestMapping("/admin/train")
public class EnrollAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public EnrollService service;
	
	@Autowired
	private CommService commService;
	
	/**
	 * 进入培训报名管理界面
	 * @return 资讯内容管理界面
	 */
	@RequestMapping("/enroll")
	public ModelAndView index(){
		return new ModelAndView( "admin/train/enroll" );
	}
	
	/**
	 * 根据条件查询培训报名列表
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/sreachenroll")
	public Object sreach(HttpServletRequest req, HttpServletResponse resp)throws Exception{
		//获取请求参数
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		//分页查询
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		try {
			rtnMap = (Map<String, Object>) this.service.sreach(paramMap);
			} catch (Exception e) {
				rtnMap.put("total", 0);
			    rtnMap.put("rows", new ArrayList<Map<String, Object>>(0));
		}
		return rtnMap;
	}
	
	/**
	 * 删除已报名人员
	 * @param enrid
	 * @return
	 */
	@RequestMapping("/delEnroll")
	public Object delEnroll(String enrid){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		try {
			this.service.delTraEnroll(enrid);
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
	 * 审核报名信息
	 * @return
	 */
	@RequestMapping("/checkEnroll")
	public Object checkEnroll(String enrid,String enruid,String enrstatemsg,String state,String name,String enrtraid){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		try {
			
			//审核
			this.service.checkEnroll(enrid,enrstatemsg,state,enruid,name,enrtraid);
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
	 * 批量审核不通过报名信息
	 * @return
	 */
	@RequestMapping("/checkAll")
	public Object checkAll(String enrids,String fromstate,String tostate,String enrstatemsg){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		
		try {
			//有电话号码就获取电话号码，如果没有就获取邮箱，然后发送短信
			this.service.selPhone(enrids,fromstate);
			
			this.service.checkAll(enrids,fromstate,tostate,enrstatemsg);
			
		} catch (Exception e) {
			log.error(e.getMessage());
			success = "1";
			errmsg = e.getMessage();
		}
		//返回
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
		
	}
	//------------------------------课程表---------------------------------------
	/**
	 * 进入课程编辑界面
	 * @param traitmid 培训批次标识
	 * @return 课程编辑界面
	 */
	@RequestMapping("/kecheng")
	public ModelAndView kecheng(String traid, String type,String tra_s_date,String tra_e_date){
		ModelAndView view = new ModelAndView( "admin/train/kecheng"+type );
		try {
			view.addObject("traid", traid);
			view.addObject("tra_s_date", tra_s_date);
			view.addObject("tra_e_date", tra_e_date);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return view;
	}
	
	/**
	 * ajax查询课程表
	 * @param traitmid 培训标识
	 * @return 课程表
	 */
	@RequestMapping("/kclist")
	public Object kechengData(String traitmid, String start, String end){
		List<Map<String, String>> renMap = new ArrayList<Map<String, String>>();
		try {
			java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
			java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyyMMdd");
			
			start = sdf2.format(sdf1.parse(start));
			end = sdf2.format(sdf1.parse(end));
			List<WhTraitmtime> list = service.sreachKecheng(traitmid, start, end);
			if(list != null){
				for(WhTraitmtime kc : list){
					Map<String, String> tmap = new HashMap<String, String>();
					tmap.put("title", kc.getDtitle());
					tmap.put("start", sdf1.format(sdf2.parse(kc.getTradate()))+" "+kc.getStime()+":00");
					tmap.put("end", sdf1.format(sdf2.parse(kc.getTradate()))+" "+kc.getEtime()+":00");
					tmap.put("timeid", kc.getTimeid());
					renMap.add(tmap);
				}
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return renMap;
	}
	
	@RequestMapping("/kcadd")
	public Object kcadd(WhTraitmtime time){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		
		//添加修改课时
		try {
			java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
			java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyyMMdd");
			java.text.SimpleDateFormat sdf3 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
			java.text.SimpleDateFormat sdf4 = new java.text.SimpleDateFormat("HH:mm");
			
			String timeid = time.getTimeid();
			time.setTradate(sdf2.format(sdf1.parse(time.getTradate())));
			time.setStime(sdf4.format(sdf3.parse(time.getStime())));
			time.setEtime(sdf4.format(sdf3.parse(time.getEtime())));
			if(timeid != null && !"".equals(timeid.trim())){
				//修改
				this.service.kcupd(time);
			}else{
				//添加
				this.service.kcadd(time);
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
	 * 
	 * @param timeid
	 * @return
	 */
	@RequestMapping("/kcdel")
	public Object kcdel(String timeid){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		//删除课时
		try {
			this.service.kcdel(timeid);
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
	 * 查询已报名人数审核通过的数量
	 */
	@RequestMapping("/enrollcount")
	public int findCount(String enrtraid){
		int result = 0;
		try {
			result = this.service.findCount(enrtraid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 查询能否发送面试通知
	 * @param enrtraid
	 * @param enruid
	 * @return
	 */
	@RequestMapping("/isSendTZ")
	public Object isSendTZ(String enrtraid,String enruid){
		Object msg = "";
		try {
			msg = this.service.isCanSend(enrtraid,enruid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	/**
	 * 发送短信面试通知
	 * @param enrtraid
	 * @param enruid
	 * @return
	 */
	@RequestMapping("/sendFaceTZ")
	public Object sendFaceTZ(String phone,String name, String date, String msg){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		try {
			String moban = WhConstance.getSysProperty("SP_NOTICE");
			moban = moban.replace("{name}", name);
			moban = moban.replace("{date}", date);
			moban = moban.replace("{address}", msg);
			SmsUtil.sendNotice(phone, moban);
		} catch (Exception e) {
			success = "1";
			errmsg = "短信发送失败！";
		}
		rtnMap.put("success", success);
		rtnMap.put("msg", errmsg);
		return rtnMap;
	}
	
	/**
	 * 发送邮件面试通知
	 * @param enrtraid
	 * @param enruid
	 * @return
	 */
	@RequestMapping("/sendYJTZ")
	public Object sendYJTZ(String name,String email, String date, String msg){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		try {
			String moban = WhConstance.getSysProperty("SP_NOTICE");
			moban = moban.replace("{name}", name);
			moban = moban.replace("{date}", date);
			moban = moban.replace("{address}", msg);
			EmailUtil.sendNoticeEmail(email, moban);
		} catch (Exception e) {
			success = "1";
			errmsg = "邮件发送失败！";
		}
		rtnMap.put("success", success);
		rtnMap.put("msg", errmsg);
		return rtnMap;
	}
	
}
