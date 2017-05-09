package com.creatoo.hn.actions.admin.yunwei;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.WhgYwiTag;
import com.creatoo.hn.model.WhgYwiType;
import com.creatoo.hn.services.admin.yunwei.WhgYunweiTagService;
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
import java.util.ArrayList;
import java.util.Map;

/**
 * 系统运营的标签管理action
 * @author wenjingqiang
 * @version 1-201703
 * Created by Administrator on 2017/3/17.
 */
@RestController
@RequestMapping("/admin/yunwei/tag")
public class WhgYunweiTagAction {
    /**
     * log
     */
    Logger log = Logger.getLogger(this.getClass());
    /**
     * service
     */
    @Autowired
    private WhgYunweiTagService whgYunweiTagService;

    /**
     * 进入标签管理视图
     * type: 页面类型的参数（列表、添加、编辑）
     * classify : 标签的类型（1、场馆标签 2、活动标签 3、活动室标签 4、培训标签 5、资讯标签）
     * @return
     */
    @RequestMapping("/view/{type}/{classify}")
    @WhgOPT(optType = EnumOptType.TAG
    ,optDesc = {"访问场馆标签配置页面", "访问活动室标签配置页面", "访问活动标签配置页面", "访问培训标签配置页面", "访问资讯标签配置页面"}
    ,valid = {"classify=1&&type=list", "classify=2&&type=list", "classify=3&&type=list", "classify=4&&type=list", "classify=5&&type=list"})
    public ModelAndView view(HttpServletRequest request, @PathVariable("type") String type, @PathVariable("classify") String classify){
        ModelAndView view = new ModelAndView("admin/yunwei/tag/view_"+type);
        view.addObject("classify", classify);
        return view;
    }



    /**
     *  分页加载标签管理列表数据
     * @param request
     * @return
     */
    @RequestMapping("/srchList4p")
    public ResponseBean srchList4p(HttpServletRequest request){
        ResponseBean res = new ResponseBean();
        try {
            PageInfo<WhgYwiTag> pageInfo = this.whgYunweiTagService.t_srchList4p(request);
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
     * 添加标签
     * @return
     */
    @RequestMapping("/add")
    @WhgOPT(optType = EnumOptType.TAG, optDesc = {"添加"})
    public ResponseBean add(HttpServletRequest request, HttpSession session){
        ResponseBean res = new ResponseBean();
        try {
            this.whgYunweiTagService.t_add(request,session);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage());
        }

        return res;
    }

    /**
     * 修改标签
     * @return
     */
    @RequestMapping("/edit")
    @WhgOPT(optType = EnumOptType.TAG, optDesc = {"编辑"})
    public ResponseBean edit(HttpServletRequest request){
        ResponseBean res = new ResponseBean();
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);
        try {
            this.whgYunweiTagService.t_edit(paramMap);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage());
        }
        return res;
    }

    /**
     * 删除标签
     * @return
     */
    @RequestMapping("/del")
    @WhgOPT(optType = EnumOptType.TAG, optDesc = {"删除"})
    public ResponseBean del(HttpServletRequest request){
        ResponseBean res = new ResponseBean();
        try {
            this.whgYunweiTagService.t_del(request);
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
