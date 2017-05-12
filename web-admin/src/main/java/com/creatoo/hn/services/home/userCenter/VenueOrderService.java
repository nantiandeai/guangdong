package com.creatoo.hn.services.home.userCenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.creatoo.hn.ext.emun.EnumOrderType;
import com.creatoo.hn.mapper.*;
import com.creatoo.hn.mapper.home.CrtCgfwMapper;
import com.creatoo.hn.model.*;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.comm.SMSService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import tk.mybatis.mapper.entity.Example;

@SuppressWarnings("ALL")
@Service
public class VenueOrderService {
    Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private CrtCgfwMapper crtCgfwMapper;
    @Autowired
    private WhgVenRoomOrderMapper whgVenRoomOrderMapper;
    @Autowired
    private WhgVenRoomMapper whgVenRoomMapper;

    @Autowired
    private SMSService smsService;

    @Autowired
    private WhgUsrUnorderMapper whgUsrUnorderMapper;
    @Autowired
    private CommService commService;

    @Autowired
    private WhgUsrBacklistMapper whgUsrBacklistMapper;

    @Autowired
    private WhUserMapper whUserMapper;

	/**
	 * 查询场馆预定信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> findOrder(Map<String, Object> param)throws Exception {
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));

        SimpleDateFormat nowdaysdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat nowtimesdf = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date();
        String _nowday = nowdaysdf.format(now);
        String _nowtime = nowtimesdf.format(now);
        param.put("nowday", nowdaysdf.parse(_nowday));
        param.put("nowtime", nowtimesdf.parse(_nowtime));

		//带条件的分页查询
		PageHelper.startPage(page, rows);
		//List<Map> list = this.whVenuebkedMapper.findVenueOrder(param);
		List<Map> list = this.crtCgfwMapper.selectUserVenOrderList(param);

		// 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}

	/**
	 * 取消预定
	 * @param id
	 * @return
	 */
	public int unOrder(String id, String userid) throws Exception {
		WhgVenRoomOrder order = new WhgVenRoomOrder();
        order.setState(1);

        Example example = new Example(WhgVenRoomOrder.class);
        example.createCriteria().andEqualTo("id", id).andEqualTo("state", 0);
        int count = this.whgVenRoomOrderMapper.updateByExampleSelective(order, example);

        try {
            order = this.whgVenRoomOrderMapper.selectByPrimaryKey(id);
            WhgVenRoom room = this.whgVenRoomMapper.selectByPrimaryKey(order.getRoomid());

            Map<String, String> data = new HashMap<>();
            data.put("userName", order.getOrdercontact());
            data.put("title", room.getTitle());
            data.put("orderNum", order.getOrderid());
            this.smsService.t_sendSMS(order.getOrdercontactphone(), "VEN_ORDER_UNADD", data);
        } catch (Exception e) {
            log.error("roomOrderUnAdd sendSMS error", e);
        }

        //与黑名单相关的操作
        if (userid != null){
            try {
                //当前用户场馆订单取消记数
                WhgUsrUnorder uuo = new WhgUsrUnorder();
                uuo.setUserid(userid);
                uuo.setOrdertype(EnumOrderType.ORDER_VEN.getValue());

                int currUnCount = this.whgUsrUnorderMapper.selectCount(uuo);
                if (currUnCount>0){
                    //超过一次了这就是第二次以上，记黑名单，清除记录
                    WhgUsrBacklist ubl = new WhgUsrBacklist();
                    ubl.setUserid(uuo.getUserid());
                    ubl.setState(1);
                    int ublcount = this.whgUsrBacklistMapper.selectCount(ubl);
                    if (ublcount == 0){
                        WhUser user = this.whUserMapper.selectByPrimaryKey(uuo.getUserid());
                        ubl.setId(this.commService.getKey("whgusrbacklist"));
                        ubl.setUserphone(user.getPhone());
                        ubl.setType(1);
                        ubl.setJointime(new Date());
                        this.whgUsrBacklistMapper.insert(ubl);
                    }

                    this.whgUsrUnorderMapper.delete(uuo);
                }else{
                    //记入取消记录
                    uuo.setOrderid(order.getId());
                    uuo.setUntime(new Date());
                    uuo.setId(this.commService.getKey("whgusrunorder"));
                    this.whgUsrUnorderMapper.insert(uuo);
                }
            } catch (Exception e) {
                log.error("取消场馆订单处理黑名单失败", e);
            }
        }

        return count;
	}

    /**
     * 删除预定
     * @param id
     * @return
     */
    public int delOrder(String id) throws Exception{
        WhgVenRoomOrder order = this.whgVenRoomOrderMapper.selectByPrimaryKey(id);
        if (order.getState()==null || order.getState().compareTo(new Integer(1))!=0){
            return 0;
        }
        return this.whgVenRoomOrderMapper.deleteByPrimaryKey(order.getId());
    }

}
