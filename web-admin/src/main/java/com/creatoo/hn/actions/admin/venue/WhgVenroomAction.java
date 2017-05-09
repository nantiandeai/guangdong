package com.creatoo.hn.actions.admin.venue;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.model.WhgVenRoom;
import com.creatoo.hn.services.admin.venue.WhgVenroomService;
import com.creatoo.hn.services.admin.venue.WhgVenueService;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 场馆管理-活动室控件器
 * Created by rbg on 2017/3/23.
 */

@Controller
@RequestMapping("/admin/venue/room")
public class WhgVenroomAction {
    Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private WhgVenroomService whgVenroomService;

    @Autowired
    private WhgVenueService venueService;

    /**
     * 页面转入处理
     * @param type add[id,targetShow]|edit|del
     * @param mmp
     * @param request
     * @return
     */
    @RequestMapping("/view/{type}")
    @WhgOPT(optType = EnumOptType.ROOM, optDesc = {"进入活动室列表"})
    public String view(@PathVariable("type")String type, ModelMap mmp, WebRequest request) throws Exception{
        if ("add".equalsIgnoreCase(type)){
            String id = request.getParameter("id");
            String targetShow = request.getParameter("targetShow");
            if (id != null){
                mmp.addAttribute("id", id);
                mmp.addAttribute("targetShow", targetShow);
                try {
                    WhgVenRoom room = whgVenroomService.srchOne(id);
                    mmp.addAttribute("whgVenRoom", room);
                    if (room!=null && room.getVenid()!=null){
                        mmp.addAttribute("whgVen", this.venueService.srchOne(room.getVenid()));
                    }
                } catch (Exception e) {
                    log.error("加载指定ID的活动室信息失败", e);
                    throw e;
                }
            }
            return "admin/venue/room/view_add";
        }

        mmp.addAttribute("type", type);
        return "admin/venue/room/view_list";
    }

    /**
     * 分页查询活动室信息
     * @param page
     * @param rows
     * @param request
     * @return
     */
    @RequestMapping("/srchList4p")
    @ResponseBody
    public Object srchList4p(int page, int rows, WebRequest request){
        ResponseBean resb = new ResponseBean();
        try {
            Map<String, String[]> pmap = request.getParameterMap();
            Map record = new HashMap();
            for(Map.Entry<String, String[]> ent : pmap.entrySet()){
                if (ent.getValue().length == 1 && ent.getValue()[0]!=null && !ent.getValue()[0].isEmpty()){
                    record.put(ent.getKey(), ent.getValue()[0]);
                }
                if (ent.getValue().length > 1){
                    record.put(ent.getKey(), Arrays.asList( ent.getValue() ) );
                }
            }

            String pageType = request.getParameter("__pageType");
            //编辑列表，查 1可编辑 5已撤消
            if ("edit".equalsIgnoreCase(pageType)){
                record.put("states", Arrays.asList(1,5) );
            }
            //审核列表，查 9待审核
            if ("check".equalsIgnoreCase(pageType)){
                record.put("states", Arrays.asList(9) );
            }
            //发布列表，查 2待发布 6已发布 4已下架
            if ("publish".equalsIgnoreCase(pageType)){
                record.put("states", Arrays.asList(2,6,4) );
            }

            //删除列表，查已删除 否则查未删除的
            if ("del".equalsIgnoreCase(pageType)){
                record.put("delstate", 1);
            }else{
                record.put("delstate", 0);
            }

            PageInfo pageInfo = this.whgVenroomService.srchList4p(page, rows, record);
            resb.setRows( (List)pageInfo.getList() );
            resb.setTotal(pageInfo.getTotal());
        } catch (Exception e) {
            log.debug("活动室查询失败", e);
            resb.setRows(new ArrayList());
            resb.setSuccess(ResponseBean.FAIL);
        }

        return resb;
    }

    /**
     * 查活动室列表
     * @return
     */
    @RequestMapping("/srchList")
    @ResponseBody
    public Object srchList(WhgVenRoom room,
                           @RequestParam(required = false)String states,
                           @RequestParam(required = false)String sort,
                           @RequestParam(required = false)String order
    ){
        try {
            List stateslist = null;
            if (states!=null && !states.isEmpty()){
                stateslist = Arrays.asList(states.split("\\s*,\\s*"));
            }
            return this.whgVenroomService.srchList(room, stateslist, sort, order);
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
            return new ArrayList();
        }
    }

    @RequestMapping("/add")
    @ResponseBody
    @WhgOPT(optType = EnumOptType.ROOM, optDesc = {"添加活动室"})
    public Object add(HttpSession session, WhgVenRoom room){
        ResponseBean rb = new ResponseBean();
        try {
            WhgSysUser sysUser = (WhgSysUser) session.getAttribute("user");
            rb = this.whgVenroomService.t_add(room, sysUser);
        } catch (Exception e) {
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("活动室信息添加失败");
            log.error(rb.getErrormsg(), e);
        }
        return rb;
    }

    @RequestMapping("/edit")
    @ResponseBody
    @WhgOPT(optType = EnumOptType.ROOM, optDesc = {"编辑活动室"})
    public Object edit(HttpSession session, WhgVenRoom room){
        ResponseBean rb = new ResponseBean();
        try {
            WhgSysUser sysUser = (WhgSysUser) session.getAttribute("user");

            if (room.getEkey()==null) room.setEkey("");
            if (room.getEtag()==null) room.setEtag("");
            if (room.getFacility()==null) room.setFacility("");
            if (room.getOpenweek()==null) room.setOpenweek("");

            this.whgVenroomService.t_edit(room, sysUser);
        } catch (Exception e) {
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("活动室信息保存失败");
            log.error(rb.getErrormsg(), e);
        }
        return rb;
    }

    @RequestMapping("/del")
    @ResponseBody
    @WhgOPT(optType = EnumOptType.ROOM, optDesc = {"删除活动室"})
    public Object del(HttpSession session, String id){
        ResponseBean rb = new ResponseBean();
        try {
            WhgSysUser sysUser = (WhgSysUser) session.getAttribute("user");
            this.whgVenroomService.t_del(id, sysUser);
        }catch (Exception e){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("活动室信息删除失败");
            log.error(rb.getErrormsg(), e);
        }
        return rb;
    }

    /**
     * 还原删除
     * @param session
     * @param id
     * @return
     */
    @RequestMapping("/undel")
    @ResponseBody
    @WhgOPT(optType = EnumOptType.ROOM, optDesc = {"还原活动室"})
    public Object undel(HttpSession session, String id){
        ResponseBean rb = new ResponseBean();
        try {
            WhgSysUser sysUser = (WhgSysUser) session.getAttribute("user");
            this.whgVenroomService.t_undel(id, sysUser);
        }catch (Exception e){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("活动室信息还原失败");
            log.error(rb.getErrormsg(), e);
        }
        return rb;
    }

    @RequestMapping("/updstate")
    @ResponseBody
    @WhgOPT(optType = EnumOptType.ROOM, optDesc = {"送审","审核","打回","发布","取消发布"}, valid = {"tostate=9","tostate=2","tostate=1","tostate=6","tostate=4"})
    public Object updstate(HttpSession session, String ids, String formstates, int tostate,
                           @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date optTime){
        ResponseBean rb = new ResponseBean();
        try {
            WhgSysUser sysUser = (WhgSysUser) session.getAttribute("user");
            rb = this.whgVenroomService.t_updstate(ids, formstates, tostate, sysUser, optTime);
        }catch (Exception e){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("活动室状态更改失败");
            log.error(rb.getErrormsg()+" formstate: "+formstates+" tostate:"+tostate+" ids: "+ids, e);
        }
        return rb;
    }
}
