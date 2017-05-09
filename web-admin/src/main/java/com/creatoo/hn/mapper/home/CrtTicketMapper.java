package com.creatoo.hn.mapper.home;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**取票服务专用dao
 * Created by Administrator on 2017/4/12.
 */
@SuppressWarnings("all")
public interface CrtTicketMapper {

    /**
     * 根据活动订单ID查询活动订单
     * @param orderId
     * @return
     */
    Map queryActOrder(@Param("orderId") String orderId);

    /**
     * 获取活动订单票信息
     * @param orderId
     * @return
     */
    List<Map> queryActTicket(@Param("orderId") String orderId);

    /**
     * 获取场馆信息
     * @param orderId
     * @return
     */
    Map queryVenOrder(@Param("orderId") String orderId);

    void updateActOrderPrintTimes(@Param("orderId") String orderId,@Param("times") Integer times);

    void updateVenOrderPrintTimes(@Param("orderId") String orderId,@Param("times") Integer times);
}
