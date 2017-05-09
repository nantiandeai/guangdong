package com.creatoo.hn.actions.admin.user;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.model.WhgUsrBacklist;
import com.creatoo.hn.model.WhgYwiType;
import com.creatoo.hn.services.admin.user.WhgBlackListService;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * 黑名单action
 * @author wenjingqiang
 * @version 1-201703
 * Created by Administrator on 2017/4/26.
 */
@RestController
@RequestMapping("/admin/user/black")
public class WhgBlackListAction {

    /**
     * 黑名单service
     */
    @Autowired
    private WhgBlackListService whgBlackListService;

    /**
     * log
     */
    Logger log = Logger.getLogger(this.getClass());

    @RequestMapping("/view/{type}")
    @WhgOPT(optType = EnumOptType.MEMBER, optDesc = {"进入黑名单列表"})
    public ModelAndView view(HttpServletRequest request, ModelMap mmp, @PathVariable("type") String type){
        ModelAndView view = new ModelAndView();
        view.setViewName("admin/user/blacklist/view_"+type);
        return view;
    }

    /**
     *  分页加载黑名单列表数据
     * @param request
     * @return
     */
    @RequestMapping("/srchList4p")
    public ResponseBean srchList4p(HttpServletRequest request){
        ResponseBean res = new ResponseBean();
        try {
            PageInfo<WhgUsrBacklist> pageInfo = this.whgBlackListService.t_srchList4p(request);
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
     * 取消黑名单
     * @return
     */
    @RequestMapping("/cancel")
    @WhgOPT(optType = EnumOptType.MEMBER, optDesc = {"取消黑名单"})
    public ResponseBean cancel(String id, String userid, HttpSession session) {
        ResponseBean res = new ResponseBean();
        try {
            this.whgBlackListService.t_cancel(id,userid);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("取消黑名单失败");
            log.error(res.getErrormsg(), e);
        }
        return res;
    }
}
