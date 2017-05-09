package com.creatoo.hn.actions.admin.train;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.WhUserTeacher;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.model.WhgTra;
import com.creatoo.hn.services.admin.train.WhgTrainTeacherService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.UploadUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 培训师资管理action
 * @author wenjingqiang
 * @version 1-201703
 * Created by Administrator on 2017/4/20.
 */
@RestController
@RequestMapping("/admin/train/tea")
public class WhgTrainTeacherAction {
    /**
     * 培训师资service
     */
    @Autowired
    private WhgTrainTeacherService whgTrainTeacherService;

    /**
     * 日志
     */
    Logger log = org.apache.log4j.Logger.getLogger(this.getClass());

    /**
     * 进入培训师资管理视图
     * @return
     */
    @RequestMapping("/view/{type}")
    @WhgOPT(optType = EnumOptType.TRATEA, optDesc = {"进入培训师资列表"})
    public ModelAndView view(HttpServletRequest request, ModelMap mmp, @PathVariable("type") String type){
        ModelAndView view = new ModelAndView();
        try {
            mmp.addAttribute("type", type);
            if ("add".equalsIgnoreCase(type)){
                String teacherid = request.getParameter("id");
                String targetShow = request.getParameter("targetShow");
                if(teacherid != null){
                    mmp.addAttribute("id", teacherid);
                    mmp.addAttribute("targetShow", targetShow);
                    mmp.addAttribute("tea",this.whgTrainTeacherService.srchOne(teacherid));
                }
                view.setViewName("admin/train/teacher/view_add");
            }else{
                view.setViewName("admin/train/teacher/view_list");
            }
        } catch (Exception e) {
            log.error("加载指定ID的培训师资信息失败", e);
        }
        return view;
    }

    /**
     *  分页加载培训师资列表数据
     * @param request
     * @return
     */
    @RequestMapping("/srchList4p")
    public ResponseBean srchList4p(HttpServletRequest request){
        //获取请求参数
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);
        ResponseBean resb = new ResponseBean();
        try {
            PageInfo pageInfo = this.whgTrainTeacherService.t_srchList4p(paramMap);
            resb.setRows( (List)pageInfo.getList() );
            resb.setTotal(pageInfo.getTotal());
        } catch (Exception e) {
            log.debug("培训师资查询失败", e);
            resb.setTotal(0);
            resb.setRows(new ArrayList());
            resb.setSuccess(ResponseBean.FAIL);
        }
        return resb;
    }

    /**
     * 添加培训师资
     * @return
     */
    @RequestMapping("/add")
    @WhgOPT(optType = EnumOptType.TRATEA, optDesc = {"添加培训师资"})
    public ResponseBean add(WhUserTeacher tea, HttpServletRequest request, HttpSession session) {
        ResponseBean res = new ResponseBean();
        WhgSysUser user = (WhgSysUser) session.getAttribute("user");
        try {
            this.whgTrainTeacherService.t_add(tea,user,request);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("培训师资保存失败");
            log.error(res.getErrormsg(), e);
        }
        return res;
    }

    /**
     * 修改培训师资
     * @return
     */
    @RequestMapping("/edit")
    @WhgOPT(optType = EnumOptType.TRATEA, optDesc = {"编辑培训师资"})
    public ResponseBean edit(WhUserTeacher tea){
        ResponseBean res = new ResponseBean();
        if (tea.getTeacherid() == null){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("培训师资主键信息丢失");
            return res;
        }
        try {
            this.whgTrainTeacherService.t_edit(tea);
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("培训师资信息保存失败");
            log.error(res.getErrormsg(), e);
        }
        return res;
    }

    /**
     *  删除培训师资数据
     * @param req
     * @param
     * @return
     */
    @RequestMapping("/del")
    @WhgOPT(optType = EnumOptType.TRATEA, optDesc = {"删除培训师资"})
    public Object saveTraintpl(HttpServletRequest req,String teacherid){
        ResponseBean res = new ResponseBean();
        try {
            this.whgTrainTeacherService.t_del(teacherid);

        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("培训师资信息删除失败");
            log.error(res.getErrormsg(), e);
        }
        return res;
    }

    /**
     * 修改状态
     * @param ids
     * @param formstates
     * @param tostate
     * @return
     */
    @RequestMapping("/updstate")
    @WhgOPT(optType = EnumOptType.TRATEA, optDesc = {"审核","打回","发布","取消发布"}, valid = {"tostate=2","tostate=1","tostate=3","tostate=2"})
    public ResponseBean updstate(String ids, String formstates, int tostate){
        ResponseBean res = new ResponseBean();
        try {
            res = this.whgTrainTeacherService.t_updstate(ids, formstates, tostate);
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("培训师资状态更改失败");
            log.error(res.getErrormsg()+" formstate: "+formstates+" tostate:"+tostate+" ids: "+ids, e);
        }
        return res;
    }
}
