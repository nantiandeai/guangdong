package com.creatoo.hn.actions.admin.user;

import com.creatoo.hn.actions.admin.system.WhgSystemCultAction;
import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.model.WhgSysCult;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.services.admin.system.UserService;
import com.creatoo.hn.services.admin.system.WhgSystemCultService;
import com.creatoo.hn.services.comm.CommService;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 会员账号管理
 * Created by wangxl on 2017/4/8.
 */
@RestController
@RequestMapping("/admin/user/info")
public class WhgUserInfoAction {

    /**
     * 日志控制器
     */
    Logger log = Logger.getLogger(WhgSystemCultAction.class.getName());

    /**
     * 公共服务类
     */
    @Autowired
    public CommService commService;

    /**
     * 会员服务类
     */
    @Autowired
    private UserService userService;

    /**
     * 进入type(list|add|edit|view)视图
     * @param request 请求对象
     * @param type 视图类型(list|add|edit|view)
     * @return
     */
    @RequestMapping("/view/{type}")
    @WhgOPT(optType = EnumOptType.MEMBER, optDesc = {"访问会员列表页", "访问会员实名审核页", "访问会员编辑页"}, valid = {"type=list", "type=auth", "type=edit"})
    public ModelAndView listview(HttpServletRequest request, @PathVariable("type")String type){
        ModelAndView view = new ModelAndView("admin/user/info/view_"+type);

        try {
            if("edit".equals(type) || "auth".equals(type)){
                String id = request.getParameter("id");
                view.addObject("whuser", this.userService.t_srchOne(id));
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
    public ResponseBean srchList4p(HttpServletRequest request, WhUser user){
        ResponseBean res = new ResponseBean();
        try {
            PageInfo<WhUser> pageInfo = this.userService.t_srchList4p(request, user);
            res.setRows(pageInfo.getList());
            res.setTotal(pageInfo.getTotal());
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
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
    @WhgOPT(optType = EnumOptType.MEMBER, optDesc = {"实名审核"})
    public ResponseBean edit(HttpServletRequest request, WhUser user){
        ResponseBean res = new ResponseBean();
        try {
            userService.t_edit(user, (WhgSysUser) request.getSession().getAttribute("user"));
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
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
    @WhgOPT(optType = EnumOptType.MEMBER, optDesc = {"删除"})
    public ResponseBean del(HttpServletRequest request, String ids){
        ResponseBean res = new ResponseBean();
        try {
            userService.t_del(ids, (WhgSysUser)request.getSession().getAttribute("user"));
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
        }
        return res;
    }
}
