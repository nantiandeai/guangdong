package com.creatoo.hn.services.admin.activity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.creatoo.hn.mapper.WhgActSeatMapper;
import com.creatoo.hn.model.WhgActSeat;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.services.comm.CommService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@SuppressWarnings("all")
@Service
/**
 * 活动座位管理 服务层
 * @author heyi
 *
 */
public class WhgActivitySeatService {
	
	/**
     * 日志
     */
    Logger log = Logger.getLogger(this.getClass().getName());
    
    /**
     * 活动DAO
     */
    @Autowired
    private WhgActSeatMapper whgActSeatMapper;

    /**
     * 公共服务
     */
    @Autowired
    private CommService commService;

    /**
     * 添加活动座位
     * @param actId
     * @param seatList
     * @param user
     * @return
     * @throws Exception
     */
    public void t_add(List<Map<String, String>> seatList, WhgSysUser user,String actId)throws Exception{
        //设置初始值
        Date now = new Date();
        int num = 1;
        for (int i = 0; i < seatList.size(); i++) {
        	Map<String,String> map = seatList.get(i);
        	int seatrow = Integer.parseInt(map.get("seatrow"));
        	int seatcolumn = Integer.parseInt(map.get("seatcolumn"));
        	int seatstatus = Integer.parseInt(map.get("seatstatus"));
        	String seatcode = map.get("seatcode");
        	String numreal = map.get("numreal");
        	WhgActSeat actSeat = new WhgActSeat();
        	actSeat.setId(commService.getKey("whg_sys_act"));
        	actSeat.setActivityid(actId);
        	actSeat.setSeatcode(seatcode);
        	actSeat.setSeatcolumn(seatcolumn);
        	actSeat.setSeatrow(seatrow);
        	actSeat.setSeatcreatetime(now);
        	actSeat.setSeatupdatetime(now);
        	actSeat.setSeatstatus(seatstatus);
        	actSeat.setSeatcreateuser(user.getId());
        	actSeat.setSeatupdateuser(user.getId());
        	actSeat.setSeatticketstatus(1);
        	actSeat.setSeatnum(numreal);
//        	actSeat.setSeatid(seatid);
        	this.whgActSeatMapper.insert(actSeat);
		}
    }
    
    /**
     * 组装需要添加的活动座位信息
     */
    public List<Map<String, String>> setSeatList(String seatJson ){
    	JSONArray seatArry = JSON.parseArray(seatJson);
    	List<Map<String, String>> seatList = new ArrayList<Map<String, String>>();
        for (int i = 0; i < seatArry.size(); i++) {
        	JSONArray  jsonArray  = seatArry.getJSONArray(i);
        	for (int j = 0; j < jsonArray.size(); j++) {
        		Map<String, String> map = new HashMap<String, String>();
        		JSONObject jsonObject = jsonArray.getJSONObject(j);
        		int numrow = jsonObject.getIntValue("numrow");
        		int numcol = jsonObject.getIntValue("numcol");
        		int open = jsonObject.getIntValue("open");
        		int type = jsonObject.getIntValue("type");
        		String numreal = jsonObject.getString("numreal");
        		//type=1 && open = 1 正常座位
				//type=0 不存在的座位
				//type=1 && open = 0 不可选座位
				if(1 == type){
					if(1 == open){
						map.put("seatstatus", String.valueOf(1));
					}else {
						map.put("seatstatus", String.valueOf(2));
					}
				}else{
					map.put("seatstatus", String.valueOf(3));
				}
        		map.put("seatcode", numrow+"_"+numcol);//座位编号
        		map.put("seatrow", String.valueOf(numrow));
        		map.put("seatcolumn", String.valueOf(numcol));
        		map.put("numreal",numreal);
        		seatList.add(map);
			}
		}
        return seatList;
    }

    public void updateActivitySeatInfo(List<Map<String, String>> seats,String activiyId,WhgSysUser whgSysUser) throws Exception{
		Date now = new Date();
    	for(Map<String, String> item : seats){
    		WhgActSeat whgActSeat = new WhgActSeat();
			int seatrow = Integer.parseInt(item.get("seatrow"));
			int seatcolumn = Integer.parseInt(item.get("seatcolumn"));
			int seatstatus = Integer.parseInt(item.get("seatstatus"));
			String seatcode = item.get("seatcode");
			WhgActSeat actSeat = new WhgActSeat();
			actSeat.setActivityid(activiyId);
			actSeat.setSeatcode(seatcode);
			actSeat = whgActSeatMapper.selectOne(actSeat);
			if(null == actSeat){
				continue;
			}
			actSeat.setSeatcreatetime(now);
			actSeat.setSeatupdatetime(now);
			actSeat.setSeatstatus(seatstatus);
			actSeat.setSeatcreateuser(whgSysUser.getId());
			actSeat.setSeatupdateuser(whgSysUser.getId());
			actSeat.setSeatticketstatus(1);
			whgActSeatMapper.updateByPrimaryKey(actSeat);
		}
	}

    /**
	 * 获取活动座位信息
	 * added by caiyong
	 * 2017/4/6
	 * */
	public JSONObject getActivitySeatInfo(String activityId){
		JSONObject jsonObject = new JSONObject();
		WhgActSeat whgActSeat = new WhgActSeat();
		whgActSeat.setActivityid(activityId);
		List<WhgActSeat> list = whgActSeatMapper.select(whgActSeat);
		Map<Integer,List<WhgActSeat>> rowsMap = new LinkedHashMap<Integer,List<WhgActSeat>>();
		for(WhgActSeat item:list){
			if(rowsMap.containsKey(item.getSeatrow())){
				rowsMap.get(item.getSeatrow()).add(item);
			}else{
				List<WhgActSeat> subList = new ArrayList<WhgActSeat>();
				subList.add(item);
				rowsMap.put(item.getSeatrow(),subList);
			}
		}
		JSONArray jsonArray = new JSONArray();
		Iterator<Integer> iter = rowsMap.keySet().iterator();
		Integer maxCol = 0;
		while(iter.hasNext()){
			List<WhgActSeat> oneRow = rowsMap.get(iter.next());
			JSONArray oneRowArray = getOneRow(oneRow);
			jsonArray.add(oneRowArray);
			if(maxCol < oneRowArray.size()){
				maxCol = oneRowArray.size();
			}
		}
		jsonObject.put("rowNum",jsonArray.size());
		jsonObject.put("colNum",maxCol);
		jsonObject.put("mySeatMap",jsonArray);
		return jsonObject;
	}

	/**
	 * 组装一行座位成JSON数组
	 * added by caiyong
	 * 2017/4/6
	 * */
	private JSONArray getOneRow(List<WhgActSeat> oneRow){
		JSONArray jsonArray = new JSONArray();
		for(WhgActSeat item : oneRow){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("numrow",item.getSeatrow());
			jsonObject.put("numcol",item.getSeatcolumn());
			jsonObject.put("numreal",item.getSeatnum());
			Integer status = item.getSeatstatus();
			if(1 == status){
				jsonObject.put("type",1);
				jsonObject.put("open",1);
			}else if(2 == status){
				jsonObject.put("type",1);
				jsonObject.put("open",0);
			}else{
				jsonObject.put("type",0);
				jsonObject.put("open",0);
			}
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	
	/**
	 * 根据活动Id删除该活动所对应的座位信息
	 * @param actId
	 */
	public void delActSeat4ActId(String actId){
		WhgActSeat whgActSeat = new WhgActSeat();
		whgActSeat.setActivityid(actId);
		whgActSeatMapper.delete(whgActSeat);
	}
}
