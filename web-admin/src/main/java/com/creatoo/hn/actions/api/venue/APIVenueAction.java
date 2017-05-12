package com.creatoo.hn.actions.api.venue;

import com.creatoo.hn.mapper.WhUserMapper;
import com.creatoo.hn.mapper.WhgSysUserMapper;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.model.WhgVenRoomOrder;
import com.creatoo.hn.services.home.agdcgfw.CgfwService;
import com.creatoo.hn.services.home.userCenter.VenueOrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 场馆预定接口
 * Created by wangxl on 2017/4/12.
 */
@SuppressWarnings("ALL")
@CrossOrigin
@RestController
@RequestMapping("/api/ven")
public class APIVenueAction {
    /**
     * 日志控制器
     */
    Logger log = Logger.getLogger(this.getClass().getName());

    @Autowired
    private WhUserMapper whUserMapper;

    @Autowired
    private CgfwService cgfwService;

    @Autowired
    private VenueOrderService venueOrderService;

    /**
     * 场馆预订检查
     * GET url： http://IP[:prot][/APP]/api/ven/check?roomtimeid=*&userid=*
     * POST: {url: http://IP[:prot][/APP]/api/ven/check, data:{roomtimeid:'*', userid:'*'}}
     * @param roomtimeid  活动室开放时段ID
     * @param userid 系统用户ID
     * @return  {
     *     success : true | false,
     *     code : 1001 | 1002 | 1003 |1004 |9999
     *     errmsg : '错误码参考信息'
     * }
     * code => errmsg
     * 1001 ： 活动室/场馆/开放时段状态异常
     * 1002 ： 用户信息获取失败
     * 1003 ： 目标为通过审核的预订时段
     * 1004 ： 重复申请
     * 1111 ： 黑名单限制
     * 9999 ： 系统异常
     */
    @CrossOrigin
    @RequestMapping("/check")
    public Object check(String roomtimeid, String userid){
        Map<String, Object> rest = new HashMap();

        try {
            //获取 user
            WhUser user = this.whUserMapper.selectByPrimaryKey(userid);

            this.cgfwService.testUserVenroomtimeInfo(user, roomtimeid, null, null, null);

            rest.put("success", true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            rest.put("success", false);
            rest.putAll( this.cgfwService.praseTestError(e.getMessage()) );
        }

        return rest;
    }

    /**
     * 场馆预订
     * GET url: http://IP[:prot][/APP]/api/ven/order?roomtimeid=*&userid=*&userphone=138*&username=*&purpose=*
     * POST: {url: http://IP[:prot][/APP]/api/ven/order, data:{roomtimeid:'*', userid:'*', userphone:'*', username:'*', purpose:''}}
     * @param roomtimeid 活动室开放时段ID
     * @param userid 系统用户ID
     * @param userphone 预订人手机号
     * @param username 预订人姓名 len<=20
     * @param purpose  预订用途 len<=200
     * @return {
     *     success : true | false,
     *     data : {订单对象},
     *     code : 1001 | 1002 | 1003 |1004 |9999
     *     errmsg : '错误码参考信息'
     * }
     * code => errmsg
     * 1001 ： 活动室/场馆/开放时段状态异常
     * 1002 ： 用户信息获取失败
     * 1003 ： 目标为通过审核的预订时段
     * 1004 ： 重复申请
     * 9999 ： 系统异常
     * 1101 ： 手机号码格式不正确
     * 1102 ： 预订人数据格式不正确
     * 1103 ： 预订用途输入过长
     */
    @CrossOrigin
    @RequestMapping("/order")
    public Object order(String roomtimeid, String userid, String userphone, String username, String purpose){
        Map<String, Object> rest = new HashMap();
        try {
            //获取 user
            WhUser user = this.whUserMapper.selectByPrimaryKey(userid);

            WhgVenRoomOrder order = this.cgfwService.saveUserVenroomtimeInfo(roomtimeid, user, userphone, username, purpose);

            rest.put("success", true);
            rest.put("data", order);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            rest.put("success", false);
            rest.putAll( this.cgfwService.praseTestError(e.getMessage()) );
        }

        return rest;
    }

    /**
     * 场馆预订取消申请
     * GET url: http://IP[:prot][/APP]/api/ven/unorder?orderid=*&userid=uid
     * POST: {url: http://IP[:prot][/APP]/api/ven/unorder, data:{orderid:'*',userid:'uid'}}
     * @param orderid 订单记录ID; userid 用户记录表用户ID
     * @return {success : true | false}
     */
    @CrossOrigin
    @RequestMapping("/unorder")
    public Object unorder(String orderid, HttpServletRequest request){
        Map<String, Object> rest = new HashMap();
        try {
            String userid = request.getParameter("userid");
            int count = this.venueOrderService.unOrder(orderid, userid);
            rest.put("success", count>0);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            rest.put("success", false);
        }

        return rest;
    }
}
