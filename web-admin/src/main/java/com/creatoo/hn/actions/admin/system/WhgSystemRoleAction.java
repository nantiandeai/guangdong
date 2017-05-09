package com.creatoo.hn.actions.admin.system;

import com.alibaba.fastjson.JSON;
import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.WhgSysRole;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.services.admin.system.WhgSystemDeptService;
import com.creatoo.hn.services.admin.system.WhgSystemRoleService;
import com.creatoo.hn.services.comm.MenusService;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统管理模块角色管理控制器
 * Created by wangxl on 2017/3/16.
 */
@RestController
@RequestMapping("/admin/system/role")
public class WhgSystemRoleAction {
    /**
     * 日志控制器
     */
    Logger log = Logger.getLogger(this.getClass().getName());

    /**
     * 部门服务类
     */
    @Autowired
    private WhgSystemRoleService service;

    /**
     * 菜单服务
     */
    @Autowired
    private MenusService menusService;

    /**
     * 进入type(list|add|edit|view)视图
     * @param request 请求对象
     * @param type 视图类型(list|add|edit|view)
     * @return
     */
    @RequestMapping("/view/{type}")
    @WhgOPT(optType = EnumOptType.POST, optDesc = {"访问岗位列表页", "访问岗位添加页", "访问岗位编辑页"}, valid = {"type=list", "type=add", "type=edit"})
    public ModelAndView listview(HttpServletRequest request, @PathVariable("type")String type){
        ModelAndView view = new ModelAndView("admin/system/role/view_"+type);

        try{
            if(type != null){
                if("add".equals(type) || "edit".equals(type)){
                    Map<String, String> optMap = this.menusService.getOptsList();
                    view.addObject("optMap", JSON.toJSON(optMap));
                }
                if("edit".equals(type)){
                    String id = request.getParameter("id");
                    view.addObject("role", this.service.t_srchOne(id));
                    view.addObject("rpms", JSON.toJSON(this.service.t_srchRolePms(id)));
                }
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }

        return view;
    }

    /**
     * 查询菜单权限
     * @param request 请求对象
     * @return 菜单权限
     */
    @RequestMapping("/srchMenuTree")
    public ResponseBean srchMenuTree(HttpServletRequest request){
        ResponseBean res = new ResponseBean();
        try{
            List<Map> menuTree = this.menusService.getMeunsTreeList();
            res.setRows(menuTree);
            res.setTotal(menuTree.size());
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }



    /**
     * 分页查询
     * @return
     */
    @RequestMapping(value = "/srchList4p")
    public ResponseBean srchList4p(HttpServletRequest request, WhgSysRole role){
        ResponseBean res = new ResponseBean();
        try {
            PageInfo<WhgSysRole> pageInfo = service.t_srchList4p(request, role);
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
     * @param role 条件对象
     * @return 对象列表
     */
    @RequestMapping(value = "/srchList")
    public List<WhgSysRole> srchList(HttpServletRequest request, WhgSysRole role){
        List<WhgSysRole> resList = new ArrayList<WhgSysRole>();
        try {
            resList = service.t_srchList(request, role);
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
            WhgSysRole role = service.t_srchOne(id);
            res.setData(role);
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
     * @param role 添加的资料
     * @return 执行操作返回结果的JSON信息
     */
    @RequestMapping(value = "/add")
    @WhgOPT(optType = EnumOptType.POST, optDesc = {"添加"})
    public ResponseBean add(HttpServletRequest request, WhgSysRole role){
        ResponseBean res = new ResponseBean();
        try {
            String[] pms = request.getParameterValues("pms");
            service.t_add(role, pms, (WhgSysUser) request.getSession().getAttribute("user"));
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
     * @param role 编辑的资料
     * @return 执行操作返回结果的JSON信息
     */
    @RequestMapping(value = "/edit")
    @WhgOPT(optType = EnumOptType.POST, optDesc = {"编辑"})
    public ResponseBean edit(HttpServletRequest request, WhgSysRole role){
        ResponseBean res = new ResponseBean();
        try {
            String[] pms = request.getParameterValues("pms");
            service.t_edit(role, pms, (WhgSysUser) request.getSession().getAttribute("user"));
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
    @WhgOPT(optType = EnumOptType.POST, optDesc = {"删除"})
    public ResponseBean del(HttpServletRequest request, String ids){
        ResponseBean res = new ResponseBean();
        try {
            service.t_del(ids, (WhgSysUser) request.getSession().getAttribute("user"));
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
    @WhgOPT(optType = EnumOptType.POST, optDesc = {"启用", "停用"}, valid = {"toState=1", "toState=0"})
    public ResponseBean updstate(HttpServletRequest request, String ids, String fromState, String toState){
        ResponseBean res = new ResponseBean();
        try {
            service.t_updstate(ids, fromState, toState, (WhgSysUser) request.getSession().getAttribute("user"));
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }
}
