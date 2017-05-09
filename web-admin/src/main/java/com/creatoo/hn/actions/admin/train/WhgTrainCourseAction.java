package com.creatoo.hn.actions.admin.train;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.mapper.WhgTraMapper;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.model.WhgTraCourse;
import com.creatoo.hn.services.admin.train.WhgTrainCourseService;
import com.creatoo.hn.services.admin.train.WhgTrainService;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 培训课程管理action
 * @author wenjingqiang
 * @version 1-201703
 * Created by Administrator on 2017/3/28.
 */
@RestController
@RequestMapping("/admin/train/course")
public class WhgTrainCourseAction {

    /**
     * 课程管理seivice
     */
    @Autowired
    private WhgTrainCourseService whgTrainCourseService;

    @Autowired
    private WhgTrainService whgTrainService;

    /**
     * 日志
     */
    Logger log = Logger.getLogger(this.getClass());

    @RequestMapping("/view/{type}")
    @WhgOPT(optType = EnumOptType.TRA, optDesc = {"进入培训课程列表页"})
    public ModelAndView view(HttpServletRequest request, ModelMap mmp, @PathVariable("type") String type){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ModelAndView view = new ModelAndView();
        String id = request.getParameter("id");
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        view.addObject("id",id);
        view.addObject("starttime",starttime);
        view.addObject("endtime",endtime);
        view.setViewName("admin/train/course/view_"+type);
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
            PageInfo pageInfo = this.whgTrainCourseService.t_srchList4p(request);
            resb.setRows( (List)pageInfo.getList() );
            resb.setTotal(pageInfo.getTotal());
        } catch (Exception e) {
            log.debug("课程查询失败", e);
            resb.setTotal(0);
            resb.setRows(new ArrayList());
            resb.setSuccess(ResponseBean.FAIL);
        }
        return resb;
    }

    /**
     * 编辑课程
     * @return
     */
    @RequestMapping("/edit")
    @WhgOPT(optType = EnumOptType.TRA, optDesc = {"编辑课程"})
    public ResponseBean edit(String traid, WhgTraCourse course, HttpSession session, HttpServletRequest request){
        ResponseBean res = new ResponseBean();
        try {
            if (course.getId() == null){
                res.setSuccess(ResponseBean.FAIL);
                res.setErrormsg("课程主键信息丢失");
                return res;
            }
            int count = this.whgTrainCourseService.selCountEnroll(traid);
            if(count > 0){
                res.setSuccess(ResponseBean.FAIL);
                res.setErrormsg("已经存在报名记录，不可编辑课程！");
                return res;
            }
            WhgSysUser sysUser = (WhgSysUser) session.getAttribute("user");

            this.whgTrainCourseService.t_edit(course, sysUser);
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("课程信息保存失败");
            log.error(res.getErrormsg(), e);
        }
        return res;
    }

    /**
     * 删除课程
     * @return
     */
    @RequestMapping("/del")
    @WhgOPT(optType = EnumOptType.TRA, optDesc = {"删除课程"})
    public ResponseBean del(String id, HttpSession session) {
        ResponseBean res = new ResponseBean();
        try {
            WhgSysUser user = (WhgSysUser) session.getAttribute("user");
            this.whgTrainCourseService.t_del(id, user);

        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("课程信息删除失败");
            log.error(res.getErrormsg(), e);
        }
        return res;
    }
    /**
     * 修改状态
     * @param ids
     * @param formstates
     * @param tostate
     * @param session
     * @return
     */
    @RequestMapping("/updstate")
    @WhgOPT(optType = EnumOptType.TRA, optDesc = {"启用","停用"}, valid = {"tostate=1","tostate=0"})
    public ResponseBean updstate(String ids, String formstates, int tostate, HttpSession session){
        ResponseBean res = new ResponseBean();
        try {
            WhgSysUser sysUser = (WhgSysUser) session.getAttribute("user");
            res = this.whgTrainCourseService.t_updstate(ids, formstates, tostate, sysUser);
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("课程状态更改失败");
            log.error(res.getErrormsg()+" formstate: "+formstates+" tostate:"+tostate+" ids: "+ids, e);
        }
        return res;
    }
}
