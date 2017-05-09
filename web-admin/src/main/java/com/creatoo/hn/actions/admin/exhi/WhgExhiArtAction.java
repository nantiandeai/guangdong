package com.creatoo.hn.actions.admin.exhi;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.WhgExh;
import com.creatoo.hn.model.WhgExhArt;
import com.creatoo.hn.services.admin.exhi.WhgExhiArtService;
import com.creatoo.hn.services.admin.exhi.WhgExhiService;
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

/**
 * 数字展览作品action
 *
 * @author luzhihuai
 * @version 1-201703
 *          Created by Administrator on 2017/4/26.
 */
@RestController
@RequestMapping("/admin/exhi/exhiart")
public class WhgExhiArtAction {
    /**
     * log
     */
    Logger log = Logger.getLogger(this.getClass());

    /**
     * 数字展览作品service
     */
    @Autowired
    private WhgExhiArtService whgExhiArtService;

    /**
     * 进入type(list|add|edit|view)视图
     *
     * @param request 请求对象
     * @param type    视图类型(list|add|edit|view)
     * @return 视图
     */
    @RequestMapping("/view/{type}")
    public ModelAndView listview(HttpServletRequest request, @PathVariable("type") String type) {
        ModelAndView view = new ModelAndView("admin/exhi/exhiart/view_" + type);

        try {
            String exhid = request.getParameter("exhid");
            String artid = request.getParameter("artid");
            String targetShow = request.getParameter("targetShow");
            view.addObject("exhid", exhid);
            view.addObject("artid", artid);
            view.addObject("targetShow", targetShow);
            view.addObject("exhiart", whgExhiArtService.t_srchOne(artid));

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
    public ResponseBean srchList4p(HttpServletRequest request,WhgExhArt whgExhArt) {
        ResponseBean res = new ResponseBean();
        try {
            PageInfo<WhgExhArt> pageInfo = whgExhiArtService.t_srchList4p(request,whgExhArt);
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
     * @param whgExhArt 实体
     * @return 对象
     */
    @RequestMapping(value = "/add")
    public ResponseBean add(HttpServletRequest request, WhgExhArt whgExhArt, @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss") Date creTime) {
        ResponseBean res = new ResponseBean();
        try {
            whgExhArt.setArtcrttime(creTime);
            this.whgExhiArtService.t_add(request, whgExhArt);
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
     * @param whgExhArt whgExh
     * @return
     */
    @RequestMapping(value = "/edit")
    public ResponseBean edit(WhgExhArt whgExhArt, @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss") Date creTime) {
        ResponseBean res = new ResponseBean();
        try {
            whgExhArt.setArtcrttime(creTime);
            this.whgExhiArtService.t_edit(whgExhArt);
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
    public ResponseBean updstate(String ids, String fromState, String toState){
        ResponseBean res = new ResponseBean();
        try {
            this.whgExhiArtService.t_updstate(ids, fromState, toState);
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
            this.whgExhiArtService.t_del(request);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage());
        }
        return res;
    }

    /**
     * 排序
     * @param artid 排序的ID
     * @param type up|top|idx
     * @param val type=idx时表示直接设置排序值
     * @return
     */
    @RequestMapping(value = "/sort")
    public ResponseBean sort(String artid, String type, String val,String artexhid){
        ResponseBean res = new ResponseBean();
        try {
            this.whgExhiArtService.t_sort(artid, type, val,artexhid);
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }


}
