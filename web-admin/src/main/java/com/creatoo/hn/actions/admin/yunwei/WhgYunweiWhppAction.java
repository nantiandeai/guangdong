package com.creatoo.hn.actions.admin.yunwei;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.model.WhgYwiWhpp;
import com.creatoo.hn.services.admin.yunwei.WhgYunweiWhppService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 文化品牌配置action
 *
 * @author luzhihuai
 * @version 1-201703
 *          Created by Administrator on 2017/3/22.
 */
@RestController
@RequestMapping("/admin/yunwei/whpp")
public class WhgYunweiWhppAction {
    /**
     * log
     */
    Logger log = Logger.getLogger(this.getClass());

    /**
     * 轮播图service
     */
    @Autowired
    private WhgYunweiWhppService whgYunweiWhppService;

    /**
     * 进入type(list|add|edit|view)视图
     *
     * @param request 请求对象
     * @param type    视图类型(list|add|edit|view)
     * @return 视图
     */
    @RequestMapping("/view/{type}")
    @WhgOPT(optType = EnumOptType.WHPP, optDesc = {"访问文化配置页面"}, valid = {"type=list"})
    public ModelAndView listview(HttpServletRequest request, @PathVariable("type") String type) {
        ModelAndView view = new ModelAndView("admin/yunwei/whpp/view_" + type);

        try {
            if("edit".equals(type) || "view".equals(type)){
                String id = request.getParameter("id");
                String targetShow = request.getParameter("targetShow");
                view.addObject("id", id);
                view.addObject("targetShow", targetShow);
                view.addObject("whpp", whgYunweiWhppService.t_srchOne(id));
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }


        return view;
    }

    /**
     * 分页查询
     *
     * @param request
     * @return res
     */
    @RequestMapping(value = "/srchList4p")
    public ResponseBean srchList4p(HttpServletRequest request,WhgYwiWhpp ywiWhpp) {
        ResponseBean res = new ResponseBean();
        try {
            PageInfo<WhgYwiWhpp> pageInfo = whgYunweiWhppService.t_srchList4p(request,ywiWhpp);
            res.setRows(pageInfo.getList());
            res.setTotal(pageInfo.getTotal());
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }


    /**
     * 查询列表
     *
     * @return 。
     */
    @RequestMapping(value = "/srchList")
    public ResponseBean srchList(WhgYwiWhpp whgYwiWhpp) {
        ResponseBean res = new ResponseBean();
        try {
            List<WhgYwiWhpp> list = this.whgYunweiWhppService.t_srchList(whgYwiWhpp);
            res.setRows(list);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }

    /**
     * 查询详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/srchOne")
    public ResponseBean srchOne(String id){
        ResponseBean res = new ResponseBean();
        try {
            WhgYwiWhpp whgYwiWhpp = this.whgYunweiWhppService.t_srchOne(id);
            res.setData(whgYwiWhpp);
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }

    /**
     * 添加
     *
     * @param request
     * @param whgYwiWhpp 实体
     * @return 对象
     */
    @RequestMapping(value = "/add")
    @WhgOPT(optType = EnumOptType.WHPP, optDesc = {"添加"})
    public ResponseBean add(HttpServletRequest request, WhgYwiWhpp whgYwiWhpp) {
        ResponseBean res = new ResponseBean();
        try {
            this.whgYunweiWhppService.t_add(request, whgYwiWhpp);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }

    /**
     * 编辑
     *
     * @param request
     * @param whgYwiWhpp whgYwiWhpp
     * @return
     */
    @RequestMapping(value = "/edit")
    @WhgOPT(optType = EnumOptType.WHPP, optDesc = {"编辑"})
    public ResponseBean edit(HttpServletRequest request, WhgYwiWhpp whgYwiWhpp) {
        ResponseBean res = new ResponseBean();
        try {
            Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);
            this.whgYunweiWhppService.t_edit(paramMap, request, whgYwiWhpp);
        } catch (Exception e) {
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
    @WhgOPT(optType = EnumOptType.WHPP, optDesc = {"启用", "停用"}, valid = {"toState=1", "toState=0"})
    public ResponseBean updstate(HttpServletRequest request, String ids, String fromState, String toState){
        ResponseBean res = new ResponseBean();
        try {
            this.whgYunweiWhppService.t_updstate(ids, fromState, toState, (WhgSysUser)request.getSession().getAttribute("user"));
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }


    /**
     * 删除
     *
     * @return
     */
    @RequestMapping("/del")
    @WhgOPT(optType = EnumOptType.WHPP, optDesc = {"删除"})
    public ResponseBean del(HttpServletRequest request) {
        ResponseBean res = new ResponseBean();
        try {
            this.whgYunweiWhppService.t_del(request);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage());
        }
        return res;
    }


}
