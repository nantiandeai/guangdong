package com.creatoo.hn.actions.admin.train;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.model.WhTraitm;
import com.creatoo.hn.services.admin.train.TrainService;
import com.creatoo.hn.services.comm.CommService;

/**
 * 培训发布
 * @author wenjingqiang
 *
 */
@RestController
@RequestMapping("/admin/train")
public class TrainAction {
	@Autowired
	private CommService commService;
	
	@Autowired
	private TrainService trainService;
	/**
	 * 返回培训管理视图
	 * @param request
	 * @return
	 */
	@RequestMapping("/showTrain")
	public ModelAndView showTrain(HttpServletRequest request){
		return new ModelAndView("admin/train/train");
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/showTraitm")
	public ModelAndView showTraitm(HttpServletRequest request){
		return new ModelAndView("admin/train/traitm");
	}
	/**
	 * 拿到所有培训信息
	 */
	@RequestMapping("/getAllTrain")
	public Object getAllTrain(int page,int rows){
		return this.trainService.selAllTrain(page,rows);
	}
	
	/**
	 * 添加培训批次
	 * @param traid
	 * @param traitmid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addTraitm")
	public Object addTraitm(String traitmid,String traid,String tratitle, String sdate, String
			edate,String isenrol,String enroldesc,String enrolstime,String enroletime,String isenrolqr,
			String enrollimit,Integer state,String isnotic,String trasummary){
		WhTraitm whTraitm = new WhTraitm();
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		try {
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			whTraitm.setTraitmid(this.commService.getKey("wh_traitm"));
			//先找到培训ID
			whTraitm.setTraid(traid);
			whTraitm.setTratitle(tratitle);
			whTraitm.setSdate(sdf.parse(sdate));
			whTraitm.setEdate(sdf.parse(edate));
			whTraitm.setIsenrol(isenrol);
			whTraitm.setEnroldesc(enroldesc);
			if (enrolstime != "0" && enrolstime != "" ) {
				whTraitm.setEnrolstime(sdf.parse(enrolstime));
			}
			if (enroletime != "0" && enroletime != "") {
				whTraitm.setEnroletime(sdf.parse(enroletime));
			}
			whTraitm.setIsenrolqr(isenrolqr);
			whTraitm.setEnrollimit(enrollimit);
			whTraitm.setState(0);
			whTraitm.setIsnotic(isnotic);
			whTraitm.setTrasummary(trasummary);
			this.trainService.addTrainitm(whTraitm);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	}
	/*
	 * 更新培训批次
	 */
	@RequestMapping("/updateTraitm")
	public Object updateTraitm(String traitmid,String traid,String tratitle, String sdate, String
			edate,String isenrol,String enroldesc,String enrolstime,String enroletime,String isenrolqr,
			String enrollimit,Integer state,String isnotic,String trasummary){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		WhTraitm whTraitm = new WhTraitm();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		//
		try {
			whTraitm.setTraitmid(traitmid);
			whTraitm.setTraid(traid);
			whTraitm.setTratitle(tratitle);
			whTraitm.setSdate(sdf.parse(sdate));
			whTraitm.setEdate(sdf.parse(edate));
			whTraitm.setIsenrol(isenrol);
			whTraitm.setEnroldesc(enroldesc);
			if (enrolstime != "0" && enrolstime != "" ) {
				whTraitm.setEnrolstime(sdf.parse(enrolstime));
			}else{
				whTraitm.setEnrolstime(null);
			}
			if (enroletime != "0" && enroletime != "") {
				whTraitm.setEnroletime(sdf.parse(enroletime));
			}else{
				whTraitm.setEnrolstime(null);
			}
			whTraitm.setIsenrolqr(isenrolqr);
			whTraitm.setEnrollimit(enrollimit);
			whTraitm.setState(0);
			whTraitm.setIsnotic(isnotic);
			whTraitm.setTrasummary(trasummary);
			this.trainService.updateTraitm(whTraitm);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
		
	}

	/**
	 *  删除培训信息
	 * @param traitmid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delTrain")
	public Object delTrain( String traitmid)throws Exception{
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		//
		try {
			this.trainService.delTrain( traitmid);
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
	 * 送审，状态加1
	 */
	@RequestMapping("/checkTraitm")
	public Object sendCheck(String traitmid,Integer state){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		try {
			this.trainService.pass(traitmid, state);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	}
	/**
	 * 打回
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/goBack")
	public void goBack(String traitmid,Integer state){
		String errmsg = "";
		try {
			this.trainService.back(traitmid,state);
		} catch (Exception e) {
			errmsg = e.getMessage();
		}
	}
	
	/**
	 * 设置上首页及排序值
	 * @return
	 */
	@RequestMapping("/goPage")
	public Object goPage(WhTraitm whTraitm){
		Map<String, String> rtnMap = new HashMap<String, String>();
		String success = "0";
		String errmsg = "";
		try {
			 this.trainService.goHomePage(whTraitm);
		} catch (Exception e) {
			success = "1";
			errmsg = e.getMessage();
		}
		rtnMap.put("success", success);
		rtnMap.put("errmsg", errmsg);
		return rtnMap;
	}
}
