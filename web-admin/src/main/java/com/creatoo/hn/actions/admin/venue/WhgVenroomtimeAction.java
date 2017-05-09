package com.creatoo.hn.actions.admin.venue;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.model.WhgVenRoomTime;
import com.creatoo.hn.services.admin.venue.WhgVenroomtimeService;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rbg on 2017/3/30.
 */

@Controller
@RequestMapping("/admin/venue/roomtime")
public class WhgVenroomtimeAction {
    Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private WhgVenroomtimeService whgVenroomtimeService;

    @RequestMapping("/view")
    @WhgOPT(optType = EnumOptType.ROOM, optDesc = {"进入活动室时段列表"})
    public String view(ModelMap mmp, String roomid, WebRequest request){
        mmp.addAttribute("roomid", roomid);
        mmp.addAttribute("roomtitle", request.getParameter("roomtitle"));
        return "admin/venue/roomtime/view_list";
    }

    @RequestMapping("/srchList4p")
    @ResponseBody
    public Object srchList4p(int page, int rows, WhgVenRoomTime roomTime, WebRequest request,
                            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDay,
                             @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDay){
        ResponseBean resb = new ResponseBean();
        try {
            String sort = request.getParameter("sort");
            String order = request.getParameter("order");

            PageInfo pageInfo = this.whgVenroomtimeService.srchList4p(page, rows, roomTime, sort, order, startDay, endDay);
            resb.setRows( (List)pageInfo.getList() );
            resb.setTotal(pageInfo.getTotal());
        } catch (Exception e) {
            log.debug("活动室时段查询失败", e);
            resb.setRows(new ArrayList());
            resb.setSuccess(ResponseBean.FAIL);
        }

        return resb;
    }

    @RequestMapping("/add")
    @ResponseBody
    @WhgOPT(optType = EnumOptType.ROOM, optDesc = {"添加活动室时段"})
    public Object add(HttpSession session, String roomid,
                      @DateTimeFormat(pattern="yyyy-MM-dd") Date startDay,
                      @DateTimeFormat(pattern="yyyy-MM-dd") Date endDay ){
        ResponseBean rb = new ResponseBean();
        try {
            rb = this.whgVenroomtimeService.t_add(roomid, startDay, endDay);
        }catch (Exception e){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("添加预定时段失败");
            log.error(rb.getErrormsg(), e);
        }
        return rb;
    }

    @RequestMapping("/edit")
    @ResponseBody
    @WhgOPT(optType = EnumOptType.ROOM, optDesc = {"编辑活动室时段"})
    public Object edit(HttpSession session, WhgVenRoomTime roomTime,
                       @DateTimeFormat(pattern="HH:mm") Date timeStart,
                       @DateTimeFormat(pattern="HH:mm") Date timeEnd ){
        ResponseBean rb = new ResponseBean();
        if (roomTime.getId() == null){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("预定时段主键信息丢失");
            return rb;
        }
        if (timeStart==null || timeEnd==null){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("预定时段开始或结束时间丢失");
            return rb;
        }

        try {
            WhgSysUser sysUser = (WhgSysUser) session.getAttribute("user");

            roomTime.setTimestart(timeStart);
            roomTime.setTimeend(timeEnd);

            rb = this.whgVenroomtimeService.t_edit(roomTime, sysUser);
        }catch (Exception e){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("预定时段信息保存失败");
            log.error(rb.getErrormsg(), e);
        }
        return rb;
    }

    @RequestMapping("/updstate")
    @ResponseBody
    @WhgOPT(optType = EnumOptType.ROOM, optDesc = {"启用","禁用"}, valid = {"tostate=1","tostate=0"})
    public Object updstate(HttpSession session, String ids, String formstates, int tostate){
        ResponseBean rb = new ResponseBean();
        try {
            WhgSysUser sysUser = (WhgSysUser) session.getAttribute("user");
            rb = this.whgVenroomtimeService.t_updstate(ids, formstates, tostate, sysUser);
        }catch (Exception e){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("预定时段状态更改失败");
            log.error(rb.getErrormsg()+" formstate: "+formstates+" tostate:"+tostate+" ids: "+ids, e);
        }
        return rb;
    }

    @RequestMapping("/ajaxAddStartDay")
    @ResponseBody
    public Object ajaxAddStartDay(String roomid){
        return this.whgVenroomtimeService.selectAddStartDay(roomid);

    }
}
