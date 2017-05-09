package com.creatoo.hn.actions.admin.yunwei;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.*;
import com.creatoo.hn.services.admin.yunwei.WhgYunweiCommentService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 运维品论管理
 * Created by tangwei on 2017/4/8.
 */
@RestController
@RequestMapping("/admin/yunwei/comment")
public class WhgYunweiCommentAction {

    Logger log = Logger.getLogger(this.getClass());

    private final  String ACT = "1";

    private final  String TRA = "2";

    private final  String VEN = "3";

    @Autowired
    private WhgYunweiCommentService whgYunweiCommentService;

    /**
     * 跳转到评论管理
     * @param request
     * @param type
     * @return
     */
    @RequestMapping("/view/list/{type}")
    @WhgOPT(optType = EnumOptType.COMMENT,
            optDesc = {"访问活动评论管理页", "访问培训评论管理页", "访问培训师资评论管理页", "访问培训点播评论管理页", "访问志愿培训评论管理页","访问场馆评论管理页", "访问活动室评论管理页"},
            valid = {"type=1", "type=2", "type=3", "type=4", "type=5","type=6", "type=7"})
    public ModelAndView listview(HttpServletRequest request, @PathVariable("type") String type) {
        ModelAndView view = new ModelAndView("admin/yunwei/comment/view_list");
        view.addObject("type",type);
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
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);
        try {
            PageInfo<Map> pageInfo = whgYunweiCommentService.t_srchList4p(paramMap);
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
     *  查看评论
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/viewComment")
    public ModelAndView viewComment(HttpServletRequest request,String id) {
        ModelAndView view = createBaseView(id,"admin/yunwei/comment/view_comment");
        return view;
    }

    /**
     *  查看回复
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/viewReply")
    public ModelAndView viewReply(HttpServletRequest request,String id) {
        Map<String, Object> param = ReqParamsUtil.parseRequest(request);
        ModelAndView view =  createBaseView(id,"admin/yunwei/comment/view_reply");
        List<Map> replyList = whgYunweiCommentService.getComentReplyInfoById(id);
        view.addObject("replyList",replyList);
        return view;
    }

    /**
     *  回复
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/toReply")
    public ModelAndView doReply(HttpServletRequest request,String id) {
        ModelAndView view =  createBaseView(id,"admin/yunwei/comment/reply");
        return view;
    }

    /**
     * 添加回复
     * @param session
     * @param comment
     * @return
     */
    @RequestMapping("/addReply")
    @ResponseBody
    @WhgOPT(optType = EnumOptType.COMMENT, optDesc = { "回复"})
    public Object addReply(HttpSession session, WhComment comment){
        ResponseBean rb = new ResponseBean();
        try {
            WhgSysUser sysUser = (WhgSysUser) session.getAttribute("user");
            whgYunweiCommentService.addReply(comment,sysUser);
        } catch (Exception e) {
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("评论回复失败");
            log.error(rb.getErrormsg(), e);
        }
        return rb;
    }

    /**
     * 删除评论
     * @param id
     * @return
     */
    @RequestMapping("/del")
    @WhgOPT(optType = EnumOptType.COMMENT, optDesc = { "删除"})
    public ResponseBean del(String id) {
        ResponseBean res = new ResponseBean();
        try {
            whgYunweiCommentService.delComentById(id);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("评论信息删除失败");
            log.error(res.getErrormsg(), e);
        }
        return res;
    }

    /**
     *  审核评论
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/toAudit")
    public ModelAndView viewAudit(HttpServletRequest request,String id) {
        ModelAndView view = createBaseView(id,"admin/yunwei/comment/view_audit");
        return view;
    }
    /**
     * 审核评论通过/不通过
     * @param comment
     * @return
     */
    @RequestMapping("/audit")
    @ResponseBody
    @WhgOPT(optType = EnumOptType.COMMENT, optDesc = { "审核"})
    public ResponseBean audit(WhComment comment){
        ResponseBean res = new ResponseBean();
        try {
            whgYunweiCommentService.auditComment(comment);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("评论信息审核失败");
            log.error(res.getErrormsg(), e);
        }
        return res;
    }

    private ModelAndView createBaseView(String id,String viewName){
        ModelAndView view = new ModelAndView();
        view.setViewName(viewName);
        Map<String,Object> result = whgYunweiCommentService.getComentInfoById(id);
        view.addObject("commentInfo",result.get("comment"));
        view.addObject("comentUser",result.get("user"));
        return view;
    }
}
