package com.creatoo.hn.actions.admin.yunwei;


import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.WhgYwiType;
import com.creatoo.hn.services.admin.yunwei.WhgYunweiTypeService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 系统运营的分类action
 * @author wenjingqiang
 * @version 1-201703
 */
@RestController
@RequestMapping("/admin/yunwei/type")
public class WhgYunweiTypeAction {
    /**
     * log
     */
    Logger log = Logger.getLogger(this.getClass());

    /**
     * services
     */
    @Autowired
    private WhgYunweiTypeService whgYunweiTypeService;

    /**
     * CommService
     */
    @Autowired
    private CommService commService;



    /**
     * 进入分类管理视图
     * type: 页面类型的参数（列表、添加、编辑）
     * classify : 分类的类型（1、艺术类型 2、场馆分类 3、活动室分类 4、活动分类 5、培训分类）
     * @return
     */
    @RequestMapping("/view/{type}/{classify}")
    @WhgOPT(optType = EnumOptType.TYPE
    , optDesc = {"访问艺术分类配置"
            ,"访问场馆分类配置"
            ,"访问活动室分类配置"
            ,"访问活动分类配置"
            ,"访问培训分类配置"
            ,"访问区域配置"
            ,"访问活动室设备配置"
            ,"访问名录项目类型配置"
            ,"访问名录项目批次配置"
            ,"访问名录项目级别配置"
            ,"访问老师专长配置"
            ,"访问志愿培训类型配置"
            ,"访问志愿活动类型配置"
            ,"访问资源分类配置"}
    , valid = {"classify=1&&type=list", "classify=2&&type=list", "classify=3&&type=list", "classify=4&&type=list", "classify=5&&type=list"
            , "classify=6&&type=list", "classify=7&&type=list", "classify=8&&type=list", "classify=9&&type=list", "classify=10&&type=list"
            , "classify=11&&type=list", "classify=12&&type=list", "classify=13&&type=list", "classify=14&&type=list"})
    public ModelAndView view(HttpServletRequest request, @PathVariable("type") String type, @PathVariable("classify") String classify){
        ModelAndView view = new ModelAndView("admin/yunwei/type/view_"+type);
        view.addObject("classify", classify);
        return view;
    }



    /**
     *  分页加载分类管理列表数据
     * @param request
     * @return
     */
    @RequestMapping("/srchList4p")
    public ResponseBean srchList4p(HttpServletRequest request){
        ResponseBean res = new ResponseBean();
        try {
            PageInfo<WhgYwiType> pageInfo = this.whgYunweiTypeService.t_srchList4p(request);
            res.setRows(pageInfo.getList());
            res.setTotal(pageInfo.getTotal());
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setRows(new ArrayList(0));
            res.setTotal(0);
            log.error(e.getMessage());
        }
        return res;
    }

    /**
     * 列表查询
     * @return
     */
    @RequestMapping("/srchList")
    public ResponseBean srchList(){
        return new ResponseBean();
    }

    /**
     * 添加分类
     * @return
     */
    @RequestMapping("/add")
    @WhgOPT(optType = EnumOptType.TYPE, optDesc = {"添加"})
    public ResponseBean add(HttpServletRequest request, HttpSession session){
        ResponseBean res = new ResponseBean();
        try {
            this.whgYunweiTypeService.t_add(request,session);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage());
        }

        return res;
    }

    /**
     * 修改分类
     * @return
     */
    @RequestMapping("/edit")
    @WhgOPT(optType = EnumOptType.TYPE, optDesc = {"编辑"})
    public ResponseBean edit(HttpServletRequest request){
        ResponseBean res = new ResponseBean();
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);
        try {
            this.whgYunweiTypeService.t_edit(paramMap);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage());
        }
        return res;
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping("/del")
    @WhgOPT(optType = EnumOptType.TYPE, optDesc = {"删除"})
    public ResponseBean del(HttpServletRequest request){
        ResponseBean res = new ResponseBean();
        try {
            this.whgYunweiTypeService.t_del(request);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage());
        }
        return res;
    }

    /**
     * 修改状态
     * @return
     */
    @RequestMapping("/updstate")
    public ResponseBean updstate(HttpServletRequest request){
        return new ResponseBean();
    }
}
