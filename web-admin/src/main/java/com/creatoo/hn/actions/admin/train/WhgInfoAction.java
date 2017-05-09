package com.creatoo.hn.actions.admin.train;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.WhZxColinfo;
import com.creatoo.hn.model.WhgPubInfo;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.model.WhgTra;
import com.creatoo.hn.services.admin.train.WhgInfoService;
import com.creatoo.hn.utils.ReqParamsUtil;
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
import java.util.List;
import java.util.Map;

/**
 * 培训、活动、非遗公用资讯action
 * @author wenjingqiang
 * @version 1-201703
 * Created by Administrator on 2017/5/2.
 */
@RestController
@RequestMapping("/admin/info")
public class WhgInfoAction {
    /**
     * 公用资讯serivice
     */
    @Autowired
    private WhgInfoService whgInfoService;

    /**
     * 日志
     */
    Logger log = Logger.getLogger(this.getClass());
    /**
     * 进入公共资讯列表
     * @return
     */
    @RequestMapping("/view/{type}")
    public ModelAndView view(HttpServletRequest request, @PathVariable("type") String type,String entityid, String entity){
        ModelAndView view = new ModelAndView();
        view.addObject("entity",entity);
        view.addObject("entityid",entityid);
        String id = request.getParameter("id");
        String targetShow = request.getParameter("targetShow");
        String clnfid = request.getParameter("clnfid");
        try {
            if("list".equalsIgnoreCase(type)){
                view.setViewName("admin/train/info/view_list");
            }
            if("add".equalsIgnoreCase(type)){
                view.setViewName("admin/train/info/view_add");
            }
//            if("seladd".equalsIgnoreCase(type)){
//                view.setViewName("admin/train/info/view_seladd");
//            }
            if("edit".equalsIgnoreCase(type)){
                if(id != null){
                    view.addObject("id", id);
                    view.addObject("targetShow", targetShow);
                    view.addObject("clnfid", clnfid);
                    view.addObject("info", this.whgInfoService.serchOne(id));
                    Map map =  this.whgInfoService.serchOne(id);
                    String clnftype = (String)map.get("clnftype");
                    if(clnftype.equals("2016111900000021") || clnftype.equals("2016111900000018") || clnftype.equals("2016112200000005")){
                        view.addObject("type", 1);
                    }
                    if(clnftype.equals("2016111900000020") || clnftype.equals("2016111900000012") || clnftype.equals("2016112200000004")){
                        view.addObject("type", 2);
                    }
                    view.setViewName("admin/train/info/view_edit");
                }
            }
        }catch (Exception e) {
            log.error("加载指定ID的培训信息失败", e);
        }
        return view;
    }

    /**
     * 分页查询公共资讯列表数据
     * @return
     */
    @RequestMapping("/srchList4p")
    public ResponseBean srchList4p(HttpServletRequest request){
        ResponseBean resb = new ResponseBean();
        //获取请求参数
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);
        try {
            PageInfo pageInfo = this.whgInfoService.t_srchList4p(paramMap);
            resb.setRows( (List)pageInfo.getList() );
            resb.setTotal(pageInfo.getTotal());
        } catch (Exception e) {
            log.debug("资讯公告信息查询失败", e);
            resb.setTotal(0);
            resb.setRows(new ArrayList());
            resb.setSuccess(ResponseBean.FAIL);
        }
        return resb;
    }

    /**
     * 查询可选择的资讯公告
     * @param request
     * @return
     */
    @RequestMapping("/srchcolinfoList4p")
    public ResponseBean srchcolinfoList4p(HttpServletRequest request){
        ResponseBean resb = new ResponseBean();
        //获取请求参数
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);
        try {
            PageInfo pageInfo = this.whgInfoService.t_srchcolinfoList4p(paramMap);
            resb.setRows( (List)pageInfo.getList() );
            resb.setTotal(pageInfo.getTotal());
        } catch (Exception e) {
            log.debug("资讯公告信息查询失败", e);
            resb.setTotal(0);
            resb.setRows(new ArrayList());
            resb.setSuccess(ResponseBean.FAIL);
        }
        return resb;
    }

    /**
     * 添加资讯
     * @param type 1.资讯  2.公告
     * @return
     */
    @RequestMapping("/add")
    public ResponseBean add(WhZxColinfo info, HttpServletRequest request, HttpSession session,String entityid, String entity, String type) {
        ResponseBean res = new ResponseBean();
        WhgSysUser user = (WhgSysUser) session.getAttribute("user");
        getClnftype(info, entity, type);
        try {
            this.whgInfoService.t_add(info,user,request,entityid);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("资讯公告信息保存失败");
            log.error(res.getErrormsg(), e);
        }
        return res;
    }

    /**
     * 编辑资讯公告信息
     * @param info
     * @param request
     * @param session
     * @param entityid
     * @param entity
     * @param type
     * @return
     */
    @RequestMapping("/edit")
    public ResponseBean edit(WhZxColinfo info, HttpServletRequest request, HttpSession session,String entityid, String entity, String type){
        ResponseBean res = new ResponseBean();
        WhgSysUser user = (WhgSysUser) session.getAttribute("user");
        if (info.getClnfid() == null){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("资讯公告信息主键丢失");
            return res;
        }
        getClnftype(info, entity, type);
        try {
            this.whgInfoService.t_edit(info,user,request,entityid);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("资讯公告信息修改失败");
            log.error(res.getErrormsg(), e);
        }
        return res;
    }

    public void getClnftype(WhZxColinfo info,String entity, String type){
        if(entity.equals("1")){
            if(type.equals("1")){
                info.setClnftype("2016111900000021");
            }
            if(type.equals("2")){
                info.setClnftype("2016111900000020");
            }
        }
        if(entity.equals("2")){
            if(type.equals("1")){
                info.setClnftype("2016111900000018");
            }
            if(type.equals("2")){
                info.setClnftype("2016111900000012");
            }
        }
        if(entity.equals("3")){
            if(type.equals("1")){
                info.setClnftype("2016112200000005");
            }
            if(type.equals("2")){
                info.setClnftype("2016112200000004");
            }
        }
    }

    /**
     * 删除资讯
     * @return
     */
    @RequestMapping("/del")
    public ResponseBean del(String id, HttpSession session) {
        ResponseBean res = new ResponseBean();
        if(id == null){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("资讯信息主键丢失");
            return res;
        }
        try {
            this.whgInfoService.t_del(id);

        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("资讯信息删除失败");
            log.error(res.getErrormsg(), e);
        }
        return res;
    }

    /**
     * 选择添加资讯
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping("/seladd")
    public ResponseBean seladd(String ids,String entityid, HttpServletRequest request) {
         ResponseBean res = new ResponseBean();
        if(ids == null){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("资讯公告信息主键丢失");
            return res;
        }
        try {
            this.whgInfoService.t_seladd(ids,entityid,request);
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("资讯公告信息保存失败");
            log.error(res.getErrormsg(), e);
        }
        return res;
    }

}
