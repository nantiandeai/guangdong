package com.creatoo.hn.actions.admin.venue;

import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.model.WhgVenRoom;
import com.creatoo.hn.model.WhgVenRoomOrder;
import com.creatoo.hn.model.WhgVenRoomTime;
import com.creatoo.hn.services.admin.venue.WhgVenroomService;
import com.creatoo.hn.services.admin.venue.WhgVenroomorderService;
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
@RequestMapping("/admin/venue/roomorder")
public class WhgVenroomorderAction {
    Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private WhgVenroomorderService whgVenroomorderService;
    @Autowired
    private WhgVenroomService whgVenroomService;

    @RequestMapping("/view")
    @WhgOPT(optType = EnumOptType.ROOM, optDesc = {"进入活动室订单列表"})
    public String view(ModelMap mmp, String roomid, WebRequest request){
        mmp.addAttribute("roomid", roomid);
        mmp.addAttribute("roomtitle", request.getParameter("roomtitle"));
        return "admin/venue/roomorder/view_list";
    }

    @RequestMapping("/view/add")
    public String viewShow(ModelMap mmp, String id, WebRequest request){
        try {
            WhgVenRoomOrder order = this.whgVenroomorderService.srchOne(id);
            WhgVenRoom room = this.whgVenroomService.srchOne(order.getRoomid());
            mmp.addAttribute("order", order);
            mmp.addAttribute("room", room);
        } catch (Exception e) {
            log.debug("活动室订单信息查询失败", e);
        }
        return "admin/venue/roomorder/view_add";
    }

    @RequestMapping("/srchList4p")
    @ResponseBody
    public Object srchList4p(int page, int rows, WhgVenRoomOrder roomOrder, WebRequest request,
                             @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDay,
                             @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDay){
        ResponseBean resb = new ResponseBean();
        try {
            String sort = request.getParameter("sort");
            String order = request.getParameter("order");

            PageInfo pageInfo = this.whgVenroomorderService.srchList4p(page, rows, roomOrder, sort, order, startDay, endDay);
            resb.setRows( pageInfo.getList() );
            resb.setTotal( pageInfo.getTotal() );
        } catch (Exception e) {
            log.debug("活动室预订查询失败", e);
            resb.setRows(new ArrayList());
            resb.setSuccess(ResponseBean.FAIL);
        }

        return resb;
    }


    @RequestMapping("/updstate")
    @ResponseBody
    @WhgOPT(optType = EnumOptType.ROOM, optDesc = {"审核通过","审核拒绝"}, valid = {"tostate=3","tostate=2"})
    public Object updstate(HttpSession session, WhgVenRoomOrder roomOrder, String formstates, int tostate) throws Exception{
        ResponseBean rb = new ResponseBean();
        try {
            WhgSysUser sysUser = (WhgSysUser) session.getAttribute("user");
            rb = this.whgVenroomorderService.t_updstate(roomOrder, formstates, tostate, sysUser);
        }catch (Exception e){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("预定状态更改失败");
            log.error(rb.getErrormsg()+" formstate: "+formstates+" tostate:"+tostate+" ids: "+roomOrder.getId(), e);
        }
        return rb;
    }

}
