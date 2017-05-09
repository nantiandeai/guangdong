package com.creatoo.hn.services.admin.activity;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhgActOrderMapper;
import com.creatoo.hn.mapper.WhgActTicketMapper;
import com.creatoo.hn.model.WhgActOrder;
import com.creatoo.hn.model.WhgActTicket;
import com.creatoo.hn.services.comm.CommService;

@SuppressWarnings("all")
@Service
/**
 * 活动管理 服务层
 * @author heyi
 *
 */
public class WhgActivityOrderService {
	
	/**
     * 日志
     */
    Logger log = Logger.getLogger(this.getClass().getName());

    /**
     * 公共服务
     */
    @Autowired
    private CommService commService;

   
    @Autowired
    private WhgActOrderMapper whgActOrderMapper;
    
    @Autowired
    private WhgActTicketMapper whgActTicketMapper;

    /**
     * 根据订单Id获取订单对象
     * @param orderId
     * @return
     */
    public WhgActOrder findWhgActOrder4Id(String orderId){
    	WhgActOrder whgActOrder = whgActOrderMapper.selectByPrimaryKey(orderId);
    	return whgActOrder;
    }
    
    /**
     * 根据订单Id查询座位信息
     * @param orderId
     * @return
     */
    public int findWhgActTicket4OrderId(String orderId){
    	WhgActTicket whgActTicket = new WhgActTicket();
    	whgActTicket.setOrderid(orderId);
    	return whgActTicketMapper.selectCount(whgActTicket);
    }
    
    public int updateActOrder(String orderId){
    	WhgActOrder actOrder = whgActOrderMapper.selectByPrimaryKey(orderId);
    	actOrder.setOrderisvalid(2);
		actOrder.setTicketstatus(3);
		return whgActOrderMapper.updateByPrimaryKey(actOrder);
    }
    
    public List<WhgActOrder> findWhgActOrder4EventId(String eventId){
    	WhgActOrder whgActOrder = new WhgActOrder();
    	whgActOrder.setEventid(eventId);
    	return whgActOrderMapper.select(whgActOrder);
    }
    
}
