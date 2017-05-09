package com.creatoo.hn.actions.admin.yunwei;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.WhgYwiNote;
import com.creatoo.hn.services.admin.yunwei.WhgYunweiNoteService;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 操作日志管理
 * Created by wangxl on 2017/4/25.
 */
@RestController
@RequestMapping("/admin/yunwei/note")
public class WhgYunweiNoteAction {
    /**
     * 日志控制器
     */
    Logger log = Logger.getLogger(this.getClass().getName());

    /**
     * 操作日志服务
     */
    @Autowired
    private WhgYunweiNoteService whgYunweiNoteService;

    /**
     * 查看操作日志分页列表视图
     * @param request 请求对象
     * @param type 视图类型
     * @return 操作日志视图
     */
    @RequestMapping("/view/{type}")
    public ModelAndView listview(HttpServletRequest request, @PathVariable("type")String type){
        ModelAndView view = new ModelAndView("admin/yunwei/note/view_"+type);

        try {
            if("edit".equals(type) || "view".equals(type)){
                String id = request.getParameter("id");
                view.addObject("cult", whgYunweiNoteService.t_srchOne(id));
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }

        return view;
    }

    /**
     * 操作日志分页数据
     * @param request 请求对象
     * @param note 操作日志条件对象
     * @return 操作日志分页数据
     */
    @RequestMapping(value = "/srchList4p")
    public ResponseBean srchList4p(HttpServletRequest request, WhgYwiNote note){
        ResponseBean res = new ResponseBean();
        try {
            PageInfo<WhgYwiNote> pageInfo = whgYunweiNoteService.t_srchList4p(request, note);
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
     * 操作日志明细查询
     * @param request 请求对象
     * @param id 操作日志ID
     * @return 操作日志明细
     */
    @RequestMapping(value = "/srchOne")
    public ResponseBean srchOne(HttpServletRequest request, String id){
        ResponseBean res = new ResponseBean();
        try {
            WhgYwiNote note = whgYunweiNoteService.t_srchOne(id);
            res.setData(note);
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }
}
