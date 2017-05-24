package com.creatoo.hn.actions.admin.system;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.WhgSysCult;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.services.admin.system.WhgSystemCultService;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统管理模块子馆管理控制器
 * Created by wangxl on 2017/3/16.
 */
@RestController
@RequestMapping("/admin/system/cult")
public class WhgSystemCultAction {
    /**
     * 日志控制器
     */
    Logger log = Logger.getLogger(WhgSystemCultAction.class.getName());

    /**
     * 文化馆服务类
     */
    @Autowired
    private WhgSystemCultService service;

    /**
     * 进入type(list|add|edit|view)视图
     * @param request 请求对象
     * @param type 视图类型(list|add|edit|view)
     * @return
     */
    @RequestMapping("/view/{type}")
    @WhgOPT(optType = EnumOptType.CULT, optDesc = {"访问分馆列表页", "访问分馆添加页", "访问分馆编辑页"}, valid = {"type=list", "type=add", "type=edit"})
    public ModelAndView listview(HttpServletRequest request, @PathVariable("type")String type){
        ModelAndView view = new ModelAndView("admin/system/cult/view_"+type);

        try {
            if("edit".equals(type) || "view".equals(type)){
                String id = request.getParameter("id");
                view.addObject("cult", service.t_srchOne(id));
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }

        return view;
    }

    /**
     * 分页查询
     * @return
     */
    @RequestMapping(value = "/srchList4p")
    public ResponseBean srchList4p(HttpServletRequest request, WhgSysCult cult){
        ResponseBean res = new ResponseBean();
        try {
            PageInfo<WhgSysCult> pageInfo = service.t_srchList4p(request, cult);
            res.setRows(pageInfo.getList());
            res.setTotal(pageInfo.getTotal());
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }

    /**
     * 查询列表
     * @param request 请求对象
     * @param cult 条件对象
     * @return 对象列表
     */
    @RequestMapping(value = "/srchList")
    public List<WhgSysCult> srchList(HttpServletRequest request, WhgSysCult cult){
        List<WhgSysCult> resList = new ArrayList<WhgSysCult>();
        try {
            resList = service.t_srchList(request, cult);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return resList;
    }

    /**
     * 查询详情
     * @param request 请求对象
     * @param id 标识
     * @return 详情资料
     */
    @RequestMapping(value = "/srchOne")
    public ResponseBean srchOne(HttpServletRequest request, String id){
        ResponseBean res = new ResponseBean();
        try {
            WhgSysCult cult = service.t_srchOne(id);
            res.setData(cult);
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }

    /**
     * 添加
     * @param request 请求对象
     * @param cult 添加的资料
     * @return 执行操作返回结果的JSON信息
     */
    @RequestMapping(value = "/add")
    @WhgOPT(optType = EnumOptType.CULT, optDesc = {"添加"})
    public ResponseBean add(HttpServletRequest request, WhgSysCult cult){
        ResponseBean res = new ResponseBean();
        try {
            service.t_add(cult, (WhgSysUser) request.getSession().getAttribute("user"));
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }

    /**
     * 编辑
     * @param request 请求对象
     * @param cult 编辑的资料
     * @return 执行操作返回结果的JSON信息
     */
    @RequestMapping(value = "/edit")
    @WhgOPT(optType = EnumOptType.CULT, optDesc = {"编辑"})
    public ResponseBean edit(HttpServletRequest request, WhgSysCult cult){
        ResponseBean res = new ResponseBean();
        try {
            service.t_edit(cult, (WhgSysUser) request.getSession().getAttribute("user"));
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }

    /**
     * 删除
     * @param request 请求对象
     * @param ids 用逗号分隔的多个ID
     * @return 执行操作返回结果的JSON信息
     */
    @RequestMapping(value = "/del")
    @WhgOPT(optType = EnumOptType.CULT, optDesc = {"删除"})
    public ResponseBean del(HttpServletRequest request, String ids){
        ResponseBean res = new ResponseBean();
        try {
            service.t_del(ids, (WhgSysUser)request.getSession().getAttribute("user"));
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }

    /**
     * 修改状态
     * @param request 请求对象
     * @param ids 用逗号分隔的多个ID
     * @param fromState 修改之前的状态
     * @param toState 修改后的状态
     * @return 执行操作返回结果的JSON信息
     */
    @RequestMapping(value = "/updstate")
    @WhgOPT(optType = EnumOptType.CULT, optDesc = {"启用", "停用"}, valid = {"toState=1", "toState=0"})
    public ResponseBean updstate(HttpServletRequest request, String ids, String fromState, String toState){
        ResponseBean res = new ResponseBean();
        try {
            service.t_updstate(ids, fromState, toState, (WhgSysUser)request.getSession().getAttribute("user"));
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }

    /**
     * 排序
     * @param request 请求
     * @param id 排序的ID
     * @param type up|top|idx
     * @param val type=idx时表示直接设置排序值
     * @return
     */
    @RequestMapping(value = "/sort")
    @WhgOPT(optType = EnumOptType.CULT, optDesc = {"上移","置顶"}, valid = {"type=up", "type=top"})
    public ResponseBean sort(HttpServletRequest request, String id, String type, String val){
        ResponseBean res = new ResponseBean();
        try {
            service.t_sort(id, type, val, (WhgSysUser)request.getSession().getAttribute("user"));
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
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
    @WhgOPT(optType = EnumOptType.CULT, optDesc = {"上首页","取消上首页"}, valid = {"formupindex=1","formupindex=0"})
    public ResponseBean upindex(String ids, String formupindex, int toupindex){
        ResponseBean res = new ResponseBean();
        try {
            res = this.service.t_upindex(ids,formupindex,toupindex);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("上首页失败！");
            log.error(res.getErrormsg()+" formupindex: "+formupindex+" toupindex:"+toupindex+" ids: "+ids, e);
        }
        return res;
    }
}
