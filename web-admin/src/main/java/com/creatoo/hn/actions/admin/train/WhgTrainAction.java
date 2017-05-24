package com.creatoo.hn.actions.admin.train;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;

import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.model.WhgTra;
import com.creatoo.hn.services.admin.train.WhgTrainCourseService;
import com.creatoo.hn.services.admin.train.WhgTrainService;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 培训驿站action
 * @author wenjingqiang
 * @version 1-201703
 * Created by Administrator on 2017/3/21.
 */
@RestController
@RequestMapping("/admin/train")
public class WhgTrainAction {

    /**
     * 培训service
     */
    @Autowired
    private WhgTrainService whgTrainService;

    /**
     * 课程service
     */
    @Autowired
    private WhgTrainCourseService whgTrainCourseService;

    org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
    /**
     * 进入培训管理视图
     * @return
     */
    @RequestMapping("/view/{type}")
    @WhgOPT(optType = EnumOptType.TRA, optDesc = {"进入培训列表页"})
    public ModelAndView view(HttpServletRequest request, ModelMap mmp, @PathVariable("type") String type){
        ModelAndView view = new ModelAndView();
        String[] age = new String[2];

        try {
            mmp.addAttribute("type", type);
            if ("add".equalsIgnoreCase(type)){
                String id = request.getParameter("id");
                String targetShow = request.getParameter("targetShow");
                if(id != null){
                    mmp.addAttribute("id", id);
                    mmp.addAttribute("targetShow", targetShow);
                    String age1 = this.whgTrainService.srchOne(id).getAge();
                    if(!"".equals(age1) && age1 != null){
                        age = age1.split(",");
                        mmp.addAttribute("age1",age[0]);
                        mmp.addAttribute("age2",age[1]);
                    }
                    mmp.addAttribute("whgTra",this.whgTrainService.srchOne(id));
                    mmp.addAttribute("state",this.whgTrainService.srchOne(id).getState());
                    mmp.addAttribute("course",this.whgTrainCourseService.srchList(id));
                    view.setViewName("admin/train/base/view_edit");
                }else{
                    view.setViewName("admin/train/base/view_add");
                }

            }else{
                view.setViewName("admin/train/base/view_list");
            }
        } catch (Exception e) {
            log.error("加载指定ID的培训信息失败", e);


        }
        return view;
    }



    /**
     *  分页加载培训列表数据
     * @param request
     * @return
     */
    @RequestMapping("/srchList4p")
    public ResponseBean srchList4p(HttpServletRequest request, String sort, String order){
        ResponseBean resb = new ResponseBean();
        try {
            PageInfo pageInfo = this.whgTrainService.t_srchList4p(request,sort,order);
            resb.setRows( (List)pageInfo.getList() );
            resb.setTotal(pageInfo.getTotal());
        } catch (Exception e) {
            log.debug("培训查询失败", e);
            resb.setTotal(0);
            resb.setRows(new ArrayList());
            resb.setSuccess(ResponseBean.FAIL);
        }
        return resb;
    }

    /**
     * 列表查询
     * @return
     */
    @RequestMapping("/srchList")
    public ResponseBean srchList(){return new ResponseBean();}

    /**
     * 添加培训
     * @return
     */
    @RequestMapping("/add")
    @WhgOPT(optType = EnumOptType.TRA, optDesc = {"添加培训"})
    public ResponseBean add(WhgTra tra, HttpServletRequest request, HttpSession session) {
        ResponseBean res = new ResponseBean();
        WhgSysUser user = (WhgSysUser) session.getAttribute("user");
        try {
            res = this.whgTrainService.t_add(tra,user,request);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("培训保存失败");
            log.error(res.getErrormsg(), e);
        }
        return res;
    }

    /**
     * 修改培训
     * @return
     */
    @RequestMapping("/edit")
    @WhgOPT(optType = EnumOptType.TRA, optDesc = {"修改培训"})
    public ResponseBean edit(WhgTra tra, HttpSession session, HttpServletRequest request){
        ResponseBean res = new ResponseBean();
        if (tra.getId() == null){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("培训主键信息丢失");
            return res;
        }
        try {
            WhgSysUser sysUser = (WhgSysUser) session.getAttribute("user");
            if("".equals(request.getParameter("age1")) && "".equals(request.getParameter("age2"))){
                tra.setAge("");
            }
            if(tra.getEbrand() == null ){
                tra.setEbrand("");
            }
            if(tra.getTeacherid() == null ){
                tra.setTeacherid("");
            }
            if(tra.getEkey() == null ){
                tra.setEkey("");
            }
            if(tra.getEtag() == null ){
                tra.setEtag("");
            }
            if(tra.getVenue() == null ){
                tra.setVenue("");
            }
            if(tra.getVenroom() == null ){
                tra.setVenroom("");
            }
            this.whgTrainService.t_edit(tra, sysUser, request);
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("培训信息保存失败");
            log.error(res.getErrormsg(), e);
        }
        return res;
    }

    /**
     * 删除培训
     * @return
     */
    @RequestMapping("/del")
    @WhgOPT(optType = EnumOptType.TRA, optDesc = {"删除培训"})
    public ResponseBean del(String id, HttpSession session) {
        ResponseBean res = new ResponseBean();
        try {
            WhgSysUser user = (WhgSysUser) session.getAttribute("user");
            this.whgTrainService.t_del(id, user);

        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("培训信息删除失败");
            log.error(res.getErrormsg(), e);
        }
        return res;
    }

    /**
     * 还原删除
     * @param id
     * @param session
     * @return
     */
    @RequestMapping("/undel")
    @WhgOPT(optType = EnumOptType.TRA, optDesc = {"还原培训"})
    public Object undel(String id, HttpSession session){
        ResponseBean rb = new ResponseBean();
        try {
            WhgSysUser sysUser = (WhgSysUser) session.getAttribute("user");
            this.whgTrainService.t_undel(id, sysUser);
        }catch (Exception e){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("培训信息还原失败");
            log.error(rb.getErrormsg(), e);
        }
        return rb;
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
    @WhgOPT(optType = EnumOptType.TRA, optDesc = {"送审","审核","打回","发布","取消发布"}, valid = {"tostate=9","tostate=2","tostate=1","tostate=6","tostate=4"})
    public ResponseBean updstate(String statemdfdate, String ids, String formstates, int tostate, HttpSession session){
        ResponseBean res = new ResponseBean();
        try {
            WhgSysUser sysUser = (WhgSysUser) session.getAttribute("user");
            res = this.whgTrainService.t_updstate(statemdfdate, ids, formstates, tostate, sysUser);
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("培训状态更改失败");
            log.error(res.getErrormsg()+" formstate: "+formstates+" tostate:"+tostate+" ids: "+ids, e);
        }
        return res;
    }

    /**
     * 是否推荐
     * @param ids
     * @param formrecoms
     * @param torecom
     * @return
     */
    @RequestMapping("/updrecommend")
    @WhgOPT(optType = EnumOptType.TRA, optDesc = {"推荐","取消推荐"}, valid = {"recommend=1","recommend=0"})
    public ResponseBean updrecommend(String ids, String formrecoms, int torecom){
        ResponseBean res = new ResponseBean();
        try {
            res = this.whgTrainService.t_updrecommend(ids,formrecoms,torecom);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("推荐失败！");
            log.error(res.getErrormsg()+" formrecoms: "+formrecoms+" torecom:"+torecom+" ids: "+ids, e);
        }
        return res;
    }
    /**
     * 是否上首页
     * @param ids
     * @param formupindex
     * @param toupindex
     * @return
     */
    @RequestMapping("/upindex")
    @WhgOPT(optType = EnumOptType.TRA, optDesc = {"上首页","取消上首页"}, valid = {"formupindex=1","formupindex=0"})
    public ResponseBean upindex(String ids, String formupindex, int toupindex){
        ResponseBean res = new ResponseBean();
        try {
            res = this.whgTrainService.t_upindex(ids,formupindex,toupindex);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("上首页失败！");
            log.error(res.getErrormsg()+" formupindex: "+formupindex+" toupindex:"+toupindex+" ids: "+ids, e);
        }
        return res;
    }

}
