package com.creatoo.hn.actions.admin.exhi;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.WhgExh;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.model.WhgYwiWhpp;
import com.creatoo.hn.services.admin.exhi.WhgExhiService;
import com.creatoo.hn.services.admin.yunwei.WhgYunweiWhppService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 数字展览action
 *
 * @author luzhihuai
 * @version 1-201703
 *          Created by Administrator on 2017/4/26.
 */
@RestController
@RequestMapping("/admin/exhi/exhi")
public class WhgExhiAction {
    /**
     * log
     */
    Logger log = Logger.getLogger(this.getClass());

    /**
     * 数字展览service
     */
    @Autowired
    private WhgExhiService whgExhiService;

    /**
     * 进入type(list|add|edit|view)视图
     *
     * @param request 请求对象
     * @param type    视图类型(list|add|edit|view)
     * @return 视图
     */
    @RequestMapping("/view/{type}")
    @WhgOPT(optType = EnumOptType.EXHI, optDesc = {"访问数字展览列表页", "访问数字展览添加页", "访问数字展览编辑页"}, valid = {"type=list", "type=add", "type=edit"})
    public ModelAndView listview(HttpServletRequest request, @PathVariable("type") String type) {
        ModelAndView view = new ModelAndView("admin/exhi/exhi/view_" + type);

        try {
            if("edit".equals(type) || "view".equals(type)){
                String exhid = request.getParameter("exhid");
                String targetShow = request.getParameter("targetShow");
                view.addObject("exhid", exhid);
                view.addObject("targetShow", targetShow);
                view.addObject("exhi", whgExhiService.t_srchOne(exhid));
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
    public ResponseBean srchList4p(HttpServletRequest request,WhgExh whgExh) {
        ResponseBean res = new ResponseBean();
        try {
            PageInfo<WhgExh> pageInfo = whgExhiService.t_srchList4p(request,whgExh);
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
     * 添加
     *
     * @param request
     * @param whgExh 实体
     * @return 对象
     */
    @RequestMapping(value = "/add")
    @WhgOPT(optType = EnumOptType.EXHI, optDesc = {"添加"})
    public ResponseBean add(HttpServletRequest request, WhgExh whgExh, @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss") Date startTime, @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss") Date endTime) {
        ResponseBean res = new ResponseBean();
        try {
            whgExh.setExhstime(startTime);
            whgExh.setExhetime(endTime);
            this.whgExhiService.t_add(request, whgExh);
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
     * @param whgExh whgExh
     * @return
     */
    @RequestMapping(value = "/edit")
    @WhgOPT(optType = EnumOptType.EXHI, optDesc = {"编辑"})
    public ResponseBean edit(WhgExh whgExh, @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss") Date startTime, @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss") Date endTime) {
        ResponseBean res = new ResponseBean();
        try {
            whgExh.setExhstime(startTime);
            whgExh.setExhetime(endTime);
            this.whgExhiService.t_edit(whgExh);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }


    /**
     * 修改状态
     * @param ids 用逗号分隔的多个ID
     * @param fromState 修改之前的状态
     * @param toState 修改后的状态
     * @return 执行操作返回结果的JSON信息
     */
    @RequestMapping(value = "/updstate")
    @WhgOPT(optType = EnumOptType.EXHI, optDesc = {"启用", "停用"}, valid = {"toState=1", "toState=0"})
    public ResponseBean updstate(String ids, String fromState, String toState){
        ResponseBean res = new ResponseBean();
        try {
            this.whgExhiService.t_updstate(ids, fromState, toState);
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
    @WhgOPT(optType = EnumOptType.EXHI, optDesc = {"删除"})
    public ResponseBean del(HttpServletRequest request) {
        ResponseBean res = new ResponseBean();
        try {
            this.whgExhiService.t_del(request);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage());
        }
        return res;
    }

    /**
     * 排序
     * @param exhid 排序的ID
     * @param type up|top|idx
     * @param val type=idx时表示直接设置排序值
     * @return
     */
    @RequestMapping(value = "/sort")
    @WhgOPT(optType = EnumOptType.EXHI, optDesc = {"上移","置顶"}, valid = {"type=up", "type=top"})
    public ResponseBean sort(String exhid, String type, String val){
        ResponseBean res = new ResponseBean();
        try {
            whgExhiService.t_sort(exhid, type, val);
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }

}
