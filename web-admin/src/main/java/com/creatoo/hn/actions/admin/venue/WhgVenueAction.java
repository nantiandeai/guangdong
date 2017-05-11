package com.creatoo.hn.actions.admin.venue;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.model.WhgVen;
import com.creatoo.hn.services.admin.venue.WhgVenueService;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 场馆管理-场馆控制器
 * Created by qxkun on 2017/3/17.
 */

@Controller
@RequestMapping("/admin/venue")
public class WhgVenueAction {
    Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private WhgVenueService venueService;

    /**
     * 页面跳入处理
     * @param type add[id,targetShow]|edit|check|publish|del
     * @return
     */
    @RequestMapping("/view/{type}")
    @WhgOPT(optType = EnumOptType.VEN, optDesc = {"进入场馆列表"})
    public String view(@PathVariable("type")String type, ModelMap mmp, WebRequest request) throws Exception{
        if ("add".equalsIgnoreCase(type)){
            String id = request.getParameter("id");
            String targetShow = request.getParameter("targetShow");
            if (id != null){
                mmp.addAttribute("id", id);
                mmp.addAttribute("targetShow", targetShow);
                try {
                    mmp.addAttribute("whgVen", this.venueService.srchOne(id));
                } catch (Exception e) {
                    log.error("加载指定ID的场馆信息失败", e);
                    throw e;
                }
            }
            return "admin/venue/ven/view_add";
        }

        mmp.addAttribute("type", type);
        return "admin/venue/ven/view_list";
    }

    @RequestMapping("/srchList4p")
    @ResponseBody
    public Object srchList4p(int page, int rows, WhgVen ven, WebRequest request){
        ResponseBean resb = new ResponseBean();
        try {
            String pageType = request.getParameter("__pageType");
            String sort = request.getParameter("sort");
            String order = request.getParameter("order");

            List states = null;
            //编辑列表，查 1可编辑 5已撤消
            if ("edit".equalsIgnoreCase(pageType)){
                states = Arrays.asList(1,5);
            }
            //审核列表，查 9待审核
            if ("check".equalsIgnoreCase(pageType)){
                states = Arrays.asList(9);
            }
            //发布列表，查 2待发布 6已发布 4已下架
            if ("publish".equalsIgnoreCase(pageType)){
                states = Arrays.asList(2,6,4);
            }

            //删除列表，查已删除 否则查未删除的
            if ("del".equalsIgnoreCase(pageType)){
                ven.setDelstate(1);
            }else{
                ven.setDelstate(0);
            }

            PageInfo pageInfo = this.venueService.srchList4p(page, rows, ven, states, sort, order);
            resb.setRows( (List)pageInfo.getList() );
            resb.setTotal(pageInfo.getTotal());
        } catch (Exception e) {
            log.debug("场馆查询失败", e);
            resb.setRows(new ArrayList());
            resb.setSuccess(ResponseBean.FAIL);
        }

        return resb;
    }

    /**
     * 查场馆列表
     * @return
     */
    @RequestMapping("/srchList")
    @ResponseBody
    public Object srchList(WhgVen ven,
                           @RequestParam(required = false)String states,
                           @RequestParam(required = false)String sort,
                           @RequestParam(required = false)String order
                           ){
        try {
            List stateslist = null;
            if (states!=null && !states.isEmpty()){
                stateslist = Arrays.asList(states.split("\\s*,\\s*"));
            }
            return this.venueService.srchList(ven, stateslist, sort, order);
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
            return new ArrayList();
        }
    }

    /**
     * 处理添加场馆
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    @WhgOPT(optType = EnumOptType.VEN, optDesc = {"添加场馆"})
    public Object add(WhgVen ven, HttpSession session,
                      @RequestParam(value = "datebuild_str", required = false)
                      @DateTimeFormat(pattern="yyyy-MM-dd") Date datebuild_str){
        ResponseBean rb = new ResponseBean();
        try {
            WhgSysUser sysUser = (WhgSysUser) session.getAttribute("user");
            if (datebuild_str!=null){
                ven.setDatebuild(datebuild_str);
            }
            this.venueService.t_add(ven, sysUser);
        }catch (Exception e){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("场馆保存失败");
            log.error(rb.getErrormsg(), e);
        }
        return rb;
    }

    /**
     * 处理编辑场馆
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    @WhgOPT(optType = EnumOptType.VEN, optDesc = {"编辑场馆"})
    public Object edit(WhgVen ven, HttpSession session,
                       @RequestParam(value = "datebuild_str", required = false)
                       @DateTimeFormat(pattern="yyyy-MM-dd") Date datebuild_str){
        ResponseBean rb = new ResponseBean();
        if (ven.getId() == null){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("场馆主键信息丢失");
            return rb;
        }
        try {
            WhgSysUser sysUser = (WhgSysUser) session.getAttribute("user");
            if (datebuild_str!=null){
                ven.setDatebuild(datebuild_str);
            }
            if (ven.getArttype()==null) ven.setArttype("");
            if (ven.getEtag()==null) ven.setEtag("");
            if (ven.getEkey()==null) ven.setEkey("");
            this.venueService.t_edit(ven, sysUser);
        }catch (Exception e){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("场馆信息保存失败");
            log.error(rb.getErrormsg(), e);
        }
        return rb;
    }

    @RequestMapping("/recommend")
    @ResponseBody
    @WhgOPT(optType = EnumOptType.VEN, optDesc = {"取消推荐","推荐"}, valid = {"recommend=0","recommend=1"})
    public Object recommend(WhgVen ven, HttpSession session){
        ResponseBean rb = new ResponseBean();
        if (ven.getId() == null){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("场馆主键信息丢失");
            return rb;
        }
        try {
            WhgSysUser sysUser = (WhgSysUser) session.getAttribute("user");
            this.venueService.t_edit(ven, sysUser);
        }catch (Exception e){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("场馆信息保存失败");
            log.error(rb.getErrormsg(), e);
        }
        return rb;
    }

    /**
     * 删除场馆
     * @param id
     * @param session
     * @return
     */
    @RequestMapping("/del")
    @ResponseBody
    @WhgOPT(optType = EnumOptType.VEN, optDesc = {"删除场馆"})
    public Object del(String id, HttpSession session){
        ResponseBean rb = new ResponseBean();
        try {
            WhgSysUser sysUser = (WhgSysUser) session.getAttribute("user");
            this.venueService.t_del(id, sysUser);
        }catch (Exception e){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("场馆信息删除失败");
            log.error(rb.getErrormsg(), e);
        }
        return rb;
    }

    /**
     * 还原删除
     * @param id
     * @param session
     * @return
     */
    @RequestMapping("/undel")
    @ResponseBody
    @WhgOPT(optType = EnumOptType.VEN, optDesc = {"还原场馆"})
    public Object undel(String id, HttpSession session){
        ResponseBean rb = new ResponseBean();
        try {
            WhgSysUser sysUser = (WhgSysUser) session.getAttribute("user");
            this.venueService.t_undel(id, sysUser);
        }catch (Exception e){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("场馆信息还原失败");
            log.error(rb.getErrormsg(), e);
        }
        return rb;
    }

    /**
     * 修改场馆状态
     * @param ids
     * @param formstates
     * @param tostate
     * @param session
     * @return
     */
    @RequestMapping("/updstate")
    @ResponseBody
    @WhgOPT(optType = EnumOptType.VEN, optDesc = {"送审","审核","打回","发布","取消发布"}, valid = {"tostate=9","tostate=2","tostate=1","tostate=6","tostate=4"})
    public Object updstate(String ids, String formstates, int tostate, HttpSession session,
                           @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date optTime){
        ResponseBean rb = new ResponseBean();
        try {
            WhgSysUser sysUser = (WhgSysUser) session.getAttribute("user");
            rb = this.venueService.t_updstate(ids, formstates, tostate, sysUser, optTime);
        }catch (Exception e){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("场馆状态更改失败");
            log.error(rb.getErrormsg()+" formstate: "+formstates+" tostate:"+tostate+" ids: "+ids, e);
        }
        return rb;
    }

}
