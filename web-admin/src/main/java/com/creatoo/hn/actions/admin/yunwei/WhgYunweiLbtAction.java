package com.creatoo.hn.actions.admin.yunwei;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.WhgYwiLbt;
import com.creatoo.hn.services.admin.yunwei.WhgYunweiLbtService;
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
 * 系统管理的轮播图配置action
 *
 * @author luzhihuai
 * @version 1-201703
 *          Created by Administrator on 2017/3/20.
 */
@RestController
@RequestMapping("/admin/yunwei/lbt")
public class WhgYunweiLbtAction {
    /**
     * log
     */
    Logger log = Logger.getLogger(this.getClass());

    /**
     * 轮播图service
     */
    @Autowired
    private WhgYunweiLbtService whgYunweiLbtService;

    /**
     * 进入type(list|add|edit|view)视图
     *
     * @param request 请求对象
     * @param type    视图类型(list|add|edit|view)
     * @return 视图
     */
    @RequestMapping("/view/{type}/{clazz}")
    @WhgOPT(optType = EnumOptType.LBT
    ,optDesc = {"访问PC首页轮播图配置", "访问APP首页轮播图配置", "访问PC首页广告图配置", "访问APP首页广告图图配置", "访问PC培训首页轮播图配置", "访问PC志愿服务首页风采展示", "访问PC非遗首页广告图配置", "访问PC志愿服务首页广告图配置", "访问PC非遗首页轮播图配置"}
    ,valid = {"clazz=1&&type=list", "clazz=2&&type=list", "clazz=3&&type=list", "clazz=4&&type=list", "clazz=5&&type=list", "clazz=6&&type=list", "clazz=7&&type=list", "clazz=8&&type=list", "clazz=9&&type=list"})
    public ModelAndView listview(HttpServletRequest request, @PathVariable("type") String type, @PathVariable("clazz") String clazz) {
        ModelAndView view = new ModelAndView("admin/yunwei/lbt/view_" + type);

        String id = request.getParameter("id");
        view.addObject("id", id);
        try {
            view.addObject("lbt",whgYunweiLbtService.t_srchOne(id));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        view.addObject("clazz", clazz);

        return view;
    }

    /**
     * 分页查询
     *
     * @param request
     * @return res
     */
    @RequestMapping(value = "/srchList4p")
    public ResponseBean srchList4p(HttpServletRequest request) {
        ResponseBean res = new ResponseBean();
        try {
            PageInfo<WhgYwiLbt> pageInfo = whgYunweiLbtService.t_srchList4p(request);
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
    public ResponseBean srchList(WhgYwiLbt whgYwiLbt) {
        ResponseBean res = new ResponseBean();
        try {
            List<WhgYwiLbt> list = this.whgYunweiLbtService.t_srchList(whgYwiLbt);
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
            WhgYwiLbt whgYwiLbt = this.whgYunweiLbtService.t_srchOne(id);
            res.setData(whgYwiLbt);
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
     * @param whgYwiLbt 实体
     * @return 对象
     */
    @RequestMapping(value = "/add")
    public ResponseBean add(HttpServletRequest request, WhgYwiLbt whgYwiLbt) {
        ResponseBean res = new ResponseBean();
        try {
            this.whgYunweiLbtService.t_add(request, whgYwiLbt);
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
     * @param whgYwiLbt
     * @return
     */
    @RequestMapping(value = "/edit")
    public ResponseBean edit(HttpServletRequest request, WhgYwiLbt whgYwiLbt) {
        ResponseBean res = new ResponseBean();
        try {
            Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);
            whgYunweiLbtService.t_edit(paramMap, request, whgYwiLbt);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }

    /**
     * 修改状态
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/updstate")
    public ResponseBean updstate(HttpServletRequest request, int state) {
        ResponseBean res = new ResponseBean();
        try {
            Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);
            this.whgYunweiLbtService.t_updstate(paramMap, state);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }

    /**
     * 删除标签
     *
     * @return
     */
    @RequestMapping("/del")
    public ResponseBean del(HttpServletRequest request) {
        ResponseBean res = new ResponseBean();
        try {
            this.whgYunweiLbtService.t_del(request);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage());
        }
        return res;
    }


}
