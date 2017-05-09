package com.creatoo.hn.actions.admin.yunwei;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.WhgYwiSmstmp;
import com.creatoo.hn.services.admin.yunwei.WhgYunweiSmsTmpService;
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
 * 短信模板管理action
 *
 * @author luzhihuai
 * @version 1-201703
 *          Created by Administrator on 2017/3/28.
 */
@RestController
@RequestMapping("/admin/yunwei/smstmp")
public class WhgYunweiSysTempAction {
    /**
     * log
     */
    Logger log = Logger.getLogger(this.getClass());

    /**
     * 短信模板service
     */
    @Autowired
    private WhgYunweiSmsTmpService whgYunweiSmsTmpService;

    /**
     * 进入type(list|add|edit|view)视图
     *
     * @param request 请求对象
     * @param type    视图类型(list|add|edit|view)
     * @return 视图
     */
    @RequestMapping("/view/{type}")
    @WhgOPT(optType = EnumOptType.SMS, optDesc = {"访问短信模板管理页面"}, valid = {"type=list"})
    public ModelAndView listview(HttpServletRequest request, @PathVariable("type") String type) {
        ModelAndView view = new ModelAndView("admin/yunwei/smstmp/view_" + type);
        try {
            if("edit".equals(type) || "view".equals(type)){
                String id = request.getParameter("id");
                view.addObject("id", id);
                view.addObject("whgYwiSmstmp", whgYunweiSmsTmpService.t_srchOne(id));
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
    public ResponseBean srchList4p(HttpServletRequest request,WhgYwiSmstmp whgYwiSmstmp) {
        ResponseBean res = new ResponseBean();
        try {
            PageInfo<WhgYwiSmstmp> pageInfo = whgYunweiSmsTmpService.t_srchList4p(request,whgYwiSmstmp);
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
     * @param id
     * @return
     */
    @RequestMapping(value = "/srchOne")
    public ResponseBean srchOne(String id){
        ResponseBean res = new ResponseBean();
        try {
            WhgYwiSmstmp whgYwiSmstmp = this.whgYunweiSmsTmpService.t_srchOne(id);
            res.setData(whgYwiSmstmp);
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
     * @param whgYwiSmstmp 实体
     * @return 对象
     */
    @RequestMapping(value = "/add")
    @WhgOPT(optType = EnumOptType.SMS, optDesc = {"添加"})
    public ResponseBean add(WhgYwiSmstmp whgYwiSmstmp) {
        ResponseBean res = new ResponseBean();
        try {
            this.whgYunweiSmsTmpService.t_add(whgYwiSmstmp);
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
     * @param whgYwiSmstmp whgYwiSmstmp
     * @return res
     */
    @RequestMapping(value = "/edit")
    @WhgOPT(optType = EnumOptType.SMS, optDesc = {"编辑"})
    public ResponseBean edit(HttpServletRequest request, WhgYwiSmstmp whgYwiSmstmp) {
        ResponseBean res = new ResponseBean();
        try {
            Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);
            this.whgYunweiSmsTmpService.t_edit(paramMap, whgYwiSmstmp);
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
     * @return
     */
    @RequestMapping("/del")
    @WhgOPT(optType = EnumOptType.SMS, optDesc = {"删除"})
    public ResponseBean del(HttpServletRequest request) {
        ResponseBean res = new ResponseBean();
        try {
            this.whgYunweiSmsTmpService.t_del(request);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage());
        }
        return res;
    }


}
