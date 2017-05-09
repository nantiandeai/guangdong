package com.creatoo.hn.actions.admin.train;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.WhgComResource;
import com.creatoo.hn.services.admin.train.WhgTrainResourceService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 资源管理action
 *
 * @author luzhihuai
 * @version 1-201703
 *          Created by Administrator on 2017/3/29.
 */
@RestController
@RequestMapping("/admin/train/resource")
public class WhgTrainResourceAction {
    /**
     * log
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * 资源管理service
     */
    @Autowired
    private WhgTrainResourceService whgTrainResourceService;
    /**
     * 进入type(list|add|edit|view)视图
     *
     * @param request 请求对象
     * @param type    视图类型(list|add|edit|view)
     * @return 视图
     */
    @RequestMapping("/view/{type}")
    @WhgOPT(optType = EnumOptType.TRA, optDesc = {"进入培训资源列表页"})
    public ModelAndView listview(HttpServletRequest request, @PathVariable("type") String type) {
        ModelAndView view = new ModelAndView("admin/train/resource/view_" + type);
        String id = request.getParameter("id");
        view.addObject("id", id);
        String entid = request.getParameter("entid");
        view.addObject("entid",entid);
        String reftype = request.getParameter("reftype");
        view.addObject("reftype", reftype);
        try {
            if("edit".equals(type)){
                view.addObject("wcr", whgTrainResourceService.t_srchOne(entid));
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }

        return view;
    }

    /**
     * 分页查询
     *
     * @param request .
     * @return res
     */
    @RequestMapping(value = "/srchList4p")
    public ResponseBean srchList4p(HttpServletRequest request,WhgComResource whgComResource) {
        ResponseBean res = new ResponseBean();
        try {
            PageInfo<WhgComResource> pageInfo = whgTrainResourceService.t_srchList4p(request,whgComResource);
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
     * 查询详情
     * @param id id
     * @return res
     */
    @RequestMapping(value = "/srchOne")
    public ResponseBean srchOne(String id){
        ResponseBean res = new ResponseBean();
        try {
            WhgComResource whgComResource = this.whgTrainResourceService.t_srchOne(id);
            res.setData(whgComResource);
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
     * @param whgComResource 实体
     * @return 对象
     */
    @RequestMapping(value = "/add")
    @WhgOPT(optType = EnumOptType.TRA, optDesc = {"添加培训资源"})
    public ResponseBean add(HttpServletRequest request,WhgComResource whgComResource) {
        ResponseBean res = new ResponseBean();
        try {
            this.whgTrainResourceService.t_add(request,whgComResource);
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
     * @param request .
     * @param whgComResource whgComResource
     * @return res
     */
    @RequestMapping(value = "/edit")
    @WhgOPT(optType = EnumOptType.TRA, optDesc = {"编辑培训资源"})
    public ResponseBean edit(HttpServletRequest request, WhgComResource whgComResource) {
        ResponseBean res = new ResponseBean();
        try {
            Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);
            this.whgTrainResourceService.t_edit(paramMap, whgComResource);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }

    /**
     * 删除
     *
     * @return res
     */
    @RequestMapping("/del")
    @WhgOPT(optType = EnumOptType.TRA, optDesc = {"删除培训资源"})
    public ResponseBean del(HttpServletRequest request) {
        ResponseBean res = new ResponseBean();
        try {
            this.whgTrainResourceService.t_del(request);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage());
        }
        return res;
    }


}
