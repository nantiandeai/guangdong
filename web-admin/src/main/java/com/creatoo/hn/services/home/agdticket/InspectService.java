package com.creatoo.hn.services.home.agdticket;

import com.creatoo.hn.ext.emun.EnumState;
import com.creatoo.hn.mapper.*;
import com.creatoo.hn.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rbg on 2017/4/12.
 */

@SuppressWarnings("ALL")
@Service
public class InspectService {

    @Autowired
    private WhgSysUserMapper whgSysUserMapper;
    @Autowired
    private WhgSysDeptMapper whgSysDeptMapper;
    @Autowired
    private WhgSysRoleMapper whgSysRoleMapper;

    public Map<String, Object> loginCheckSysUser(String username, String password) throws Exception{
        Map<String, Object> rest = new HashMap();
        rest.put("status", "RESULT_SUCCESS_CODE_00000");

        WhgSysUser record = new WhgSysUser();
        record.setAccount(username);
        record.setPassword(password);
        record.setState(EnumState.STATE_YES.getValue());
        WhgSysUser user = this.whgSysUserMapper.selectOne(record);

        if (user == null){
            rest.put("status", "RESULT_USER_ERROR_CODE_10003");
            return rest;
        }

        WhgSysDept dept = this.whgSysDeptMapper.selectByPrimaryKey(user.getDeptid());
        //WhgSysRole role = this.whgSysRoleMapper.selectByPrimaryKey(user.getEpms());


        Map<String, Object> data = new HashMap();
        data.put("userAccount", user.getAccount());
        data.put("userId", user.getId());

        if (dept != null){
            data.put("deptName", dept.getName());
        }
        data.put("roleName", null);

        rest.put("data", data);
        return rest;
    }


    @Autowired
    private WhgActOrderMapper whgActOrderMapper;
    @Autowired
    private WhgActTicketMapper whgActTicketMapper;
    @Autowired
    private WhgActTimeMapper whgActTimeMapper;
    @Autowired
    private WhgActActivityMapper whgActActivityMapper;

    public Map<String, Object> orderActivityCode(String orderValidateCode) throws Exception{
        Map<String, Object> rest = new HashMap();
        rest.put("status", "RESULT_SUCCESS_CODE_00000");

        //参数无效
        if (orderValidateCode==null || orderValidateCode.isEmpty()){
            rest.put("status", "RESULT_ACTIVITY_ERROR_CODE_10001");
            return rest;
        }
        //取 orderValidateCode 对应 活动订单号 的数据
        WhgActOrder actOrder = new WhgActOrder();
        actOrder.setOrdernumber(orderValidateCode);
        actOrder = this.whgActOrderMapper.selectOne(actOrder);
        //无对应的取票验证码订单
        if (actOrder == null){
            rest.put("status", "RESULT_ACTIVITY_ERROR_CODE_10002");
            return rest;
        }
        //订单无效
        if (actOrder.getOrderisvalid()==null || actOrder.getOrderisvalid().compareTo(new Integer(1))!=0){
            rest.put("status", "RESULT_ACTIVITY_ERROR_CODE_10003");
            return rest;
        }

        //活动和场次
        WhgActActivity act = this.whgActActivityMapper.selectByPrimaryKey(actOrder.getActivityid());
        WhgActTime time = this.whgActTimeMapper.selectByPrimaryKey(actOrder.getEventid());

        //组装订单信息返回
        Map<String, Object> data = new HashMap();
        data.put("orderValidateCode", orderValidateCode);
        data.put("orderNumber", actOrder.getOrdernumber());
        data.put("orderPhotoNo", actOrder.getOrderphoneno());
        data.put("orderPayStatus", actOrder.getOrderisvalid());
        if (act != null){
            data.put("activityName", act.getName());
            data.put("activityAddress", act.getAddress());
        }
        if (time !=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            data.put("activityTime", sdf.format(time.getPlaydate())+" "+time.getPlaystime());
        }

        data.put("orderVotes", 0);
        //订单对应的票
        WhgActTicket ticket = new WhgActTicket();
        ticket.setOrderid( actOrder.getId() );
        List<WhgActTicket> list= this.whgActTicketMapper.select(ticket);
        if (list!=null && list.size()>0){
            StringBuffer activitySeats = new StringBuffer("");
            StringBuffer activitySeatsIds = new StringBuffer("");
            StringBuffer seatStatus = new StringBuffer("");
            String point = "";
            for(WhgActTicket tk : list){
                String seatcode = tk.getSeatcode()==null? "" : tk.getSeatcode();
                String seatid = tk.getSeatid()==null? "": tk.getSeatid();
                String ticketstatus = tk.getTicketstatus()==null? "" : String.valueOf( tk.getTicketstatus() );

                activitySeats.append(point + seatcode);
                activitySeatsIds.append(point + seatid);
                seatStatus.append(point + ticketstatus);

                point = ",";
            }

            data.put("activitySeats", activitySeats.toString());
            data.put("activitySeatsIds", activitySeatsIds.toString());
            data.put("seatStatus", seatStatus.toString());
            data.put("orderVotes", list.size());
        }

        rest.put("data", data);
        return rest;
    }

    public Map<String, Object> activityCode(String userId, String orderValidateCode, String userAccount,
                               String seats, String orderPayStatus) throws Exception{

        Map<String, Object> rest = new HashMap();
        rest.put("status", "RESULT_SUCCESS_CODE_00000");

        //参数无效
        if (orderValidateCode==null || orderValidateCode.isEmpty()){
            rest.put("status", "RESULT_ACTIVITY_ERROR_CODE_10001");
            return rest;
        }
        if (seats==null || seats.isEmpty()){
            rest.put("status", "RESULT_ACTIVITY_ERROR_CODE_10001");
            return rest;
        }

        //取 orderValidateCode 对应 活动订单号 的数据
        WhgActOrder actOrder = new WhgActOrder();
        actOrder.setOrdernumber(orderValidateCode);
        actOrder = this.whgActOrderMapper.selectOne(actOrder);
        //无对应的取票验证码订单
        if (actOrder == null){
            rest.put("status", "RESULT_ACTIVITY_ERROR_CODE_10002");
            return rest;
        }
        //订单无效
        if (actOrder.getOrderisvalid()==null || actOrder.getOrderisvalid().compareTo(new Integer(1))!=0){
            rest.put("status", "RESULT_ACTIVITY_ERROR_CODE_10003");
            return rest;
        }

        //订单对应的座位
        WhgActTicket ticket = new WhgActTicket();
        ticket.setOrderid(actOrder.getId());
        ticket.setSeatid(seats);
        ticket = this.whgActTicketMapper.selectOne(ticket);
        if (ticket == null){
            rest.put("status", "RESULT_ACTIVITY_ERROR_CODE_10002");
            return rest;
        }
        //座位已退订
        /*if (ticket.getTicketstatus()==null || ticket.getTicketstatus().compareTo(new Integer(3))==0){
            rest.put("status", "RESULT_ACTIVITY_ERROR_CODE_10003");
            return rest;
        }*/
        //座位已验票
        if (ticket.getTicketstatus()!=null && ticket.getTicketstatus().compareTo(new Integer(1))==0){
            rest.put("status", "RESULT_ACTIVITY_ERROR_CODE_10004");
            return rest;
        }
        //时间开始超过2小时
        WhgActTime time = this.whgActTimeMapper.selectByPrimaryKey(actOrder.getEventid());
        if (time != null){
            SimpleDateFormat sdftime = new SimpleDateFormat("HH:mm");
            Date playstime = sdftime.parse(time.getPlaystime());
            Calendar cst = Calendar.getInstance();
            cst.setTime(playstime);

            Calendar c = Calendar.getInstance();
            c.setTime( time.getPlaydate() );
            c.set(Calendar.HOUR_OF_DAY, cst.get(Calendar.HOUR_OF_DAY));
            c.set(Calendar.MINUTE, cst.get(Calendar.MINUTE));

            c.add(Calendar.HOUR_OF_DAY, 2);

            if (c.getTime().compareTo( new Date()) < 0){
                rest.put("status", "RESULT_ACTIVITY_ERROR_CODE_10005");
                return rest;
            }
        }

        //修改验票状态
        ticket.setTicketstatus(1);
        this.whgActTicketMapper.updateByPrimaryKey(ticket);

        return rest;
    }


    @Autowired
    private WhgVenRoomOrderMapper whgVenRoomOrderMapper;
    @Autowired
    private WhgVenRoomMapper whgVenRoomMapper;
    @Autowired
    private WhgVenMapper whgVenMapper;

    public Map<String, Object> orderRoomCode(String orderValidateCode) throws Exception{
        Map<String, Object> rest = new HashMap();
        rest.put("status", "RESULT_SUCCESS_CODE_00000");

        //参数无效
        if (orderValidateCode==null || orderValidateCode.isEmpty()){
            rest.put("status", "RESULT_ROOM_ERROR_CODE_10001");
            return rest;
        }
        //取orderValidateCode 对应订单号的订单
        WhgVenRoomOrder order = new WhgVenRoomOrder();
        order.setOrderid(orderValidateCode);
        order = this.whgVenRoomOrderMapper.selectOne(order);
        //没有取到订单
        if (order == null){
            rest.put("status", "RESULT_ROOM_ERROR_CODE_10002");
            return rest;
        }

        //未预订成功
        Integer state = order.getState();
        if (state==null || state.compareTo(new Integer(3))!=0){
            rest.put("status", "RESULT_ROOM_ERROR_CODE_10003");
            return rest;
        }

        Map<String, Object> data = new HashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdftime = new SimpleDateFormat("HH:mm");

        WhgVenRoom room = this.whgVenRoomMapper.selectByPrimaryKey(order.getRoomid());
        if (room != null){
            data.put("roomName", room.getTitle());
            WhgVen ven = this.whgVenMapper.selectByPrimaryKey(room.getVenid());
            if (ven!=null){
                data.put("venueName", ven.getTitle());
                data.put("venueAddress", ven.getAddress());
            }
        }
        data.put("roomOderId", order.getId());
        data.put("bookStatus", order.getState());
        data.put("tuserTeamName", order.getOrdercontact());
        data.put("roomOrderNo", order.getOrderid());
        data.put("validCode", order.getOrderid());
        data.put("curDate", sdf.format( order.getTimeday() ) );
        data.put("openPeriod", sdftime.format(order.getTimestart())+"-"+sdftime.format(order.getTimeend()) );
        data.put("orderStatus", order.getTicketcheckdate() );
        data.put("orderTel", order.getOrdercontactphone() );

        rest.put("data", data);
        return rest;
    }

    public Map<String, Object> roomCode(String userId, String orderValidateCode, String userAccount,
                                        String roomOderId, String bookStatus) throws Exception{
        Map<String, Object> rest = new HashMap();
        rest.put("status", "RESULT_SUCCESS_CODE_00000");

        //参数无效
        if (orderValidateCode==null || orderValidateCode.isEmpty()){
            rest.put("status", "RESULT_ROOM_ERROR_CODE_10001");
            return rest;
        }

        //取orderValidateCode 对应订单号的订单
        WhgVenRoomOrder order = new WhgVenRoomOrder();
        order.setOrderid(orderValidateCode);
        order = this.whgVenRoomOrderMapper.selectOne(order);
        //没有取到订单
        if (order == null){
            rest.put("status", "RESULT_ROOM_ERROR_CODE_10002");
            return rest;
        }

        //未预订成功
        Integer state = order.getState();
        if (state==null || state.compareTo(new Integer(3))!=0){
            rest.put("status", "RESULT_ROOM_ERROR_CODE_10003");
            return rest;
        }

        //时间已过期了
        Date timeday = order.getTimeday();
        Date timeend = order.getTimeend();
        Calendar _day = Calendar.getInstance();
        _day.setTime(timeday);
        Calendar _end = Calendar.getInstance();
        _end.setTime(timeend);
        _day.set(Calendar.HOUR_OF_DAY, _end.get(Calendar.HOUR_OF_DAY));
        _day.set(Calendar.MINUTE, _end.get(Calendar.MINUTE));

        if (_day.getTime().compareTo(new Date()) <0){
            rest.put("status", "RESULT_ROOM_ERROR_CODE_10004");
            return rest;
        }

        //已验过票
        Integer ticketcheckstate = order.getTicketcheckstate();
        if (ticketcheckstate!=null && ticketcheckstate.compareTo(new Integer(2))==0){
            rest.put("status", "RESULT_ROOM_ERROR_CODE_10005");
            return rest;
        }

        //处理验证
        order.setTicketcheckstate(2);
        order.setTicketcheckdate(new Date());
        this.whgVenRoomOrderMapper.updateByPrimaryKey(order);

        return rest;
    }
}
