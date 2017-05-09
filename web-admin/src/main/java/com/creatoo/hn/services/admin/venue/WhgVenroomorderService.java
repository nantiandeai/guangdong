package com.creatoo.hn.services.admin.venue;

import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.mapper.WhgVenRoomOrderMapper;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.model.WhgVenRoom;
import com.creatoo.hn.model.WhgVenRoomOrder;
import com.creatoo.hn.services.comm.SMSService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * Created by rbg on 2017/3/30.
 */

@SuppressWarnings("ALL")
@Service
public class WhgVenroomorderService {
    Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private WhgVenRoomOrderMapper whgVenRoomOrderMapper;

    @Autowired
    private WhgVenroomService whgVenroomService;

    @Autowired
    private SMSService smsService;

    public PageInfo srchList4p(int page, int rows, WhgVenRoomOrder roomOrder, String sort, String order, Date startDay, Date endDay) throws Exception{
        Example example = new Example(WhgVenRoomOrder.class);
        Example.Criteria c = example.createCriteria();

        if (roomOrder!=null && roomOrder.getOrdercontactphone()!=null){
            c.andLike("ordercontactphone", "%"+roomOrder.getOrdercontactphone()+"%");
            roomOrder.setOrdercontactphone(null);
        }
        c.andEqualTo(roomOrder);
        if (startDay != null){
            c.andGreaterThanOrEqualTo("timeday", startDay);
        }
        if (endDay != null){
            c.andLessThanOrEqualTo("timeday", endDay);
        }

        //排序
        if (sort!=null && !sort.isEmpty()){
            if (order!=null && "asc".equalsIgnoreCase(order)){
                example.orderBy(sort).asc();
            }else{
                example.orderBy(sort).desc();
            }
        }else{
            example.orderBy("timeday").desc();
        }

        PageHelper.startPage(page, rows);
        List list = this.whgVenRoomOrderMapper.selectByExample(example);

        return new PageInfo(list);
    }

    public WhgVenRoomOrder srchOne(String orderid) throws Exception{
        return this.whgVenRoomOrderMapper.selectByPrimaryKey(orderid);
    }


    public ResponseBean t_updstate(WhgVenRoomOrder roomOrder, String formstates, int tostate, WhgSysUser user) throws Exception{
        ResponseBean rb = new ResponseBean();
        if (roomOrder == null || roomOrder.getId() == null){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("预定标记丢失");
            return rb;
        }

        WhgVenRoomOrder trgOrder = this.whgVenRoomOrderMapper.selectByPrimaryKey(roomOrder.getId());

        //验证是目标是否为前置状态
        List<String> forstates = Arrays.asList( formstates.split("\\s*,\\s*") );
        String state = String.valueOf( trgOrder.getState() );
        if ( !forstates.contains(state) ){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("核审的预订状态已变更，请刷新数据后再操作");
            return rb;
        }

        if (tostate == 3){
            //通过验证时检查是否有重复通过
            WhgVenRoomOrder recode = new WhgVenRoomOrder();
            recode.setState(3);
            recode.setRoomid(trgOrder.getRoomid());
            recode.setTimeday(trgOrder.getTimeday());
            recode.setTimestart(trgOrder.getTimestart());
            recode.setTimeend(trgOrder.getTimeend());

            int countState3 = this.whgVenRoomOrderMapper.selectCount(recode);
            if (countState3 > 0){
                rb.setSuccess(ResponseBean.FAIL);
                rb.setErrormsg("已存在相同时段其它通过的审核");
                return rb;
            }
        }
        if (tostate == 2){
            //拒绝时写入摘要
            trgOrder.setOrdersummary(roomOrder.getOrdersummary());
        }

        trgOrder.setState(tostate);
        this.whgVenRoomOrderMapper.updateByPrimaryKeySelective(trgOrder);
        sendOrderSMS(trgOrder);
        return rb;
    }


    private void sendOrderSMS(WhgVenRoomOrder trgOrder) {
        try {
            String phome = trgOrder.getOrdercontactphone();
            String sempTemp = "VEN_ORDER_SUCCESS";
            if (trgOrder.getState() != null && trgOrder.getState().compareTo(new Integer(2))==0){
                sempTemp = "VEN_ORDER_FAIL";
            }

            WhgVenRoom room = this.whgVenroomService.srchOne(trgOrder.getRoomid());
            Map<String, String> data = new HashMap<>();
            data.put("userName", trgOrder.getOrdercontact());
            data.put("title", room.getTitle());
            data.put("orderNum", trgOrder.getOrderid());

            this.smsService.t_sendSMS(phome, sempTemp, data);
        } catch (Exception e) {
            log.error("roomOrderAdd sendSMS error", e);
        }
    }

}
