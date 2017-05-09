package com.creatoo.hn.services.admin.activity;

import com.creatoo.hn.mapper.WhgActTimeMapper;
import com.creatoo.hn.model.WhgActTime;
import com.creatoo.hn.services.comm.CommService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Service
/**
 * 活动场次管理 服务层
 * @author heyi
 *
 */
public class WhgActivityTimeService {
	
	/**
     * 日志
     */
    Logger log = Logger.getLogger(this.getClass().getName());
    
    /**
     * 活动DAO
     */
    @Autowired
    private WhgActTimeMapper whgActTimeMapper;

    /**
     * 公共服务
     */
    @Autowired
    private CommService commService;

   
   /**
    * 添加场次
    * @param act
    * @return
    * @throws Exception
    */
    public void t_add(List<Map<String, String>> timePlayList,String actId,Date time,int seats){
		SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	for (int i = 0; i < timePlayList.size(); i++) {
        	Map<String,String> map = timePlayList.get(i);
        	String playstrtime = String.valueOf(map.get("playstrtime"));
        	String playendtime = String.valueOf(map.get("playendtime"));
        	WhgActTime actTime = new WhgActTime();
        	try {
				Date playStartTime = sdfDateTime.parse(sdf.format(time) + " " + playstrtime);
				Date playEndTime = sdfDateTime.parse(sdf.format(time) + " " + playendtime);
        		actTime.setId(commService.getKey("whg_sys_act"));
        		actTime.setActid(actId);
        		actTime.setPlaydate(time);
        		actTime.setPlayetime(playendtime);
        		actTime.setPlaystime(playstrtime);
        		actTime.setSeats(seats);
        		actTime.setState(1);
				actTime.setPlaystarttime(playStartTime);
				actTime.setPlayendtime(playEndTime);
	        	this.whgActTimeMapper.insert(actTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }
    
    public void updateOne(WhgActTime param){
    	try{
    		whgActTimeMapper.updateByPrimaryKey(param);
		}catch (Exception e){
    		e.printStackTrace();
		}
	}
    
    /**
     * 根据活动Id获取场次对象
     * @param actId
     * @return
     */
    public WhgActTime findWhgActTime4ActId(String actId){
    	WhgActTime whgActTime = new WhgActTime();
    	whgActTime.setActid(actId);
    	return whgActTimeMapper.select(whgActTime).get(0);
    }
    
    /**
     * 保存单条场次信息
     * @param whgActTime
     */
    public void addOne(WhgActTime whgActTime){
    	whgActTimeMapper.insert(whgActTime);
    }
    
    public WhgActTime findWhgActTime4Id(String id){
    	return whgActTimeMapper.selectByPrimaryKey(id);
    }
   
	
}
