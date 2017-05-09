package com.creatoo.hn.actions.admin.system;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.services.admin.system.WhgSystemUserService;
import com.creatoo.hn.utils.MD5Util;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 系统管理模块管理员管理控制器
 * Created by wangxl on 2017/3/16.
 */
@RestController
@RequestMapping("/admin/system/user")
public class WhgSystemUserAction {
    /**
     * 日志控制器
     */
    Logger log = Logger.getLogger(this.getClass().getName());

    /**
     * 部门服务类
     */
    @Autowired
    private WhgSystemUserService service;

    /**
     * 进入type(list|add|edit|view)视图
     * @param request 请求对象
     * @param type 视图类型(list|add|edit|view)
     * @return
     */
    @RequestMapping("/view/{type}")
    @WhgOPT(optType = EnumOptType.ADMIN, optDesc = {"访问管理员列表页", "访问管理员添加页", "访问管理员编辑页"}, valid = {"type=list", "type=add", "type=edit"})
    public ModelAndView listview(HttpServletRequest request, @PathVariable("type")String type){
        ModelAndView view = new ModelAndView("admin/system/user/view_"+type);
        try{
            if("edit".equals(type)){
                String id = request.getParameter("id");
                view.addObject("adminuser", this.service.t_srchOne(id));
                Map<String, String> map = this.service.t_srchUserRole(id);
                view.addObject("roles", map.get("_roles"));
                view.addObject("cults", map.get("_cults"));
            }
        }catch (Exception e){

        }
        return view;
    }

    /**
     * 分页查询
     * @return
     */
    @RequestMapping(value = "/srchList4p")
    public ResponseBean srchList4p(HttpServletRequest request, WhgSysUser user){
        ResponseBean res = new ResponseBean();
        try {
            PageInfo<WhgSysUser> pageInfo = service.t_srchList4p(request, user);
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
     * @param user 条件对象
     * @return 对象列表
     */
    @RequestMapping(value = "/srchList")
    public ResponseBean srchList(HttpServletRequest request, WhgSysUser user){
        ResponseBean res = new ResponseBean();
        try {
            List<WhgSysUser> list = service.t_srchList(request, user);
            res.setRows(list);
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
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
            WhgSysUser user = service.t_srchOne(id);
            res.setData(user);
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
     * @param user 添加的资料
     * @return 执行操作返回结果的JSON信息
     */
    @RequestMapping(value = "/add")
    @WhgOPT(optType = EnumOptType.ADMIN, optDesc = {"添加"})
    public ResponseBean add(HttpServletRequest request, WhgSysUser user){
        ResponseBean res = new ResponseBean();
        try {
            String[] roleids = request.getParameterValues("roleids");//岗位
            String[] cultids = request.getParameterValues("cultids");//权限分馆

            //保存密码到emps-此字段现在存密码明文base64加密后的字符串
            String password_user = request.getParameter("password2");
            user.setEpms(MD5Util.encode4Base64(password_user));

            service.t_add(user, roleids, cultids, (WhgSysUser) request.getSession().getAttribute("user"));
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
     * @param user 编辑的资料
     * @return 执行操作返回结果的JSON信息
     */
    @RequestMapping(value = "/edit")
    @WhgOPT(optType = EnumOptType.ADMIN, optDesc = {"编辑"})
    public ResponseBean edit(HttpServletRequest request, WhgSysUser user){
        ResponseBean res = new ResponseBean();
        try {
            String[] roleids = request.getParameterValues("roleids");//岗位
            String[] cultids = request.getParameterValues("cultids");//权限分馆

            //保存密码到emps-此字段现在存密码明文base64加密后的字符串
            String password_user = request.getParameter("password2");
            user.setEpms(MD5Util.encode4Base64(password_user));

            service.t_edit(user, roleids, cultids, (WhgSysUser) request.getSession().getAttribute("user"));
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
    @WhgOPT(optType = EnumOptType.ADMIN, optDesc = {"删除"})
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
    @WhgOPT(optType = EnumOptType.ADMIN, optDesc = {"启用", "停用"}, valid = {"toState=1", "toState=0"})
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
