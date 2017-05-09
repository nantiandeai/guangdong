package com.creatoo.hn.actions.admin.train;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.services.admin.train.WhgTrainEnrolService;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * * 培训报名管理action
 * @author wenjingqiang
 * @version 1-201703
 * Created by Administrator on 2017/4/1.
 */
@RestController
@RequestMapping("/admin/train/enrol")
public class WhgTrainEnrolAction {

    /**
     * 报名service
     */
    @Autowired
    private WhgTrainEnrolService whgTrainEnrolService;

    /**
     * 日志
     */
    Logger log = Logger.getLogger(this.getClass());

    @RequestMapping("/view/{type}")
    @WhgOPT(optType = EnumOptType.TRA, optDesc = {"进入培训报名列表页"})
    public ModelAndView view(HttpServletRequest request, ModelMap mmp, @PathVariable("type") String type){
        ModelAndView view = new ModelAndView();
        String id = request.getParameter("id");
        //总报名数
        int count = this.whgTrainEnrolService.t_selCount(id,1);
        //有效的报名数
        int goodCount = this.whgTrainEnrolService.t_selCount(id,2);
        //未处理的报名数
        int unCheckCount = this.whgTrainEnrolService.t_selCount(id,3);
        //面试总人数
        int viewCount = this.whgTrainEnrolService.t_selCount(id,4);
        //成功录取人数
        int passCount = this.whgTrainEnrolService.t_selCount(id,5);
        String isbasicclass = request.getParameter("isbasicclass");
        view.addObject("isbasicclass",isbasicclass);
        view.addObject("id",id);
        view.addObject("count",count);
        view.addObject("goodCount",goodCount);
        view.addObject("unCheckCount",unCheckCount);
        view.addObject("viewCount",viewCount);
        view.addObject("passCount",passCount);
        view.setViewName("admin/train/enrol/view_"+type);
        return view;
    }

    /**
     *  分页加载培训列表数据
     * @param request
     * @return
     */
    @RequestMapping("/srchList4p")
    public ResponseBean srchList4p(HttpServletRequest request){
        ResponseBean resb = new ResponseBean();
        try {
            PageInfo pageInfo = this.whgTrainEnrolService.t_srchList4p(request);
            resb.setRows( (List)pageInfo.getList() );
            resb.setTotal(pageInfo.getTotal());
        } catch (Exception e) {
            log.debug("培训报名信息查询失败", e);
            resb.setTotal(0);
            resb.setRows(new ArrayList());
            resb.setSuccess(ResponseBean.FAIL);
        }
        return resb;
    }

    /**
     * 改变状态
     * @param ids
     * @param fromstate
     * @param tostate
     * @param session
     * @return
     */
    @RequestMapping("/updstate")
    @WhgOPT(optType = EnumOptType.TRA, optDesc = {"审核面试","审核失败","报名成功","面试失败"}, valid = {"tostate=4","tostate=3","tostate=6","tostate=5"})
    public ResponseBean updstate(String statedesc,String ids, String fromstate, int tostate, HttpSession session, String viewtime, String viewaddress){
        ResponseBean res = new ResponseBean();
        try {
            WhgSysUser sysUser = (WhgSysUser) session.getAttribute("user");
            res = this.whgTrainEnrolService.t_updstate(statedesc, ids, fromstate, tostate, sysUser, viewtime, viewaddress);
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("培训报名状态更改失败");
            log.error(res.getErrormsg()+" formstate: "+fromstate+" tostate:"+tostate+" ids: "+ids, e);
        }
        return res;
    }

    /**
     * 随机录取
     * @param ids
     * @param fromstate
     * @param tostate
     * @return
     */
    @RequestMapping("/ramEnroll")
    @WhgOPT(optType = EnumOptType.TRA, optDesc = {"随机录取"})
    public ResponseBean ramEnroll(String ids, String fromstate, int tostate, HttpSession session){
        ResponseBean res = new ResponseBean();
        if (ids == null){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("培训报名信息主键丢失");
            return res;
        }
        try {
            WhgSysUser sysUser = (WhgSysUser) session.getAttribute("user");
            res = this.whgTrainEnrolService.ramEnroll(ids,fromstate,tostate,sysUser);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("随机录取操作失败");
            log.error(res.getErrormsg()+" formstate: "+fromstate+" tostate:"+tostate+" ids: "+ids, e);
        }
        return res;
    }
}
