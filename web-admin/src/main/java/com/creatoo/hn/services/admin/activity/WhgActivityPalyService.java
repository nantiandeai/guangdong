package com.creatoo.hn.services.admin.activity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.creatoo.hn.mapper.WhgActPalyMapper;
import com.creatoo.hn.model.WhgActActivity;
import com.creatoo.hn.model.WhgActPlay;
import com.creatoo.hn.services.comm.CommService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@SuppressWarnings("all")
@Service
/**
 * 活动时间段管理 服务层
 * @author heyi
 *
 */
public class WhgActivityPalyService {
	
	/**
     * 日志
     */
    Logger log = Logger.getLogger(this.getClass().getName());
    
    /**
     * 活动DAO
     */
    @Autowired
    private WhgActPalyMapper whgActPalyMapper;

    /**
     * 公共服务
     */
    @Autowired
    private CommService commService;

    /**
     * 根据活动ID获取改活动的场次信息
     * @param actId
     * @return
     */
    public List<WhgActPlay> srchList4actId(String actId){
    	WhgActPlay record = new WhgActPlay();
        record.setActid(actId);
    	List<WhgActPlay> seatList = whgActPalyMapper.select(record);
    	return seatList;
    }
    
   /**
    * 设置活动场次
    * @param timeJson
    * @return
    */
   public List<Map<String,String>> setTimeTemp(String timeJson){
		List<Map<String, String>> timePlayList = new ArrayList<Map<String, String>>();
		JSONArray json = JSONArray.parseArray(timeJson);
		for(int i=0;i<json.size();i++){
			Map<String, String> map = new HashMap<String, String>();
		    JSONObject job = json.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
		    map.put("playstrtime", String.valueOf(job.get("playstrtime")));
		    map.put("playendtime", String.valueOf(job.get("playendtime")));
		    timePlayList.add(map);
		}
		return timePlayList;
   }
   
   /**
    * 添加活动场次
    * @param act
    * @return
    * @throws Exception
    */
    public void t_add(List<Map<String, String>> timePlayList, WhgActActivity whgActActivity){
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		SimpleDateFormat sdfDateTime = new SimpleDateFormat("yy/MM/dd HH:mm");
		try {
			Date myDate = whgActActivity.getStarttime();
			Date myEndDate = whgActActivity.getEndtime();
			while(isAfter(myDate,myEndDate)){
				for (int i = 0; i < timePlayList.size(); i++) {
					Map<String,String> map = timePlayList.get(i);
					String playstrtime = String.valueOf(map.get("playstrtime"));
					String playendtime = String.valueOf(map.get("playendtime"));
					Date playStartTime = sdfDateTime.parse(sdf.format(myDate) + " " + playstrtime);
					Date playEndTime = sdfDateTime.parse(sdf.format(myDate) + " " + playendtime);
					WhgActPlay actPlay = new WhgActPlay();
					try {
						actPlay.setId(commService.getKey("whg_sys_act"));
						//actPlay.setActid(actId);
						actPlay.setPlayendtime(playendtime);
						actPlay.setPlaystrtime(playstrtime);

						this.whgActPalyMapper.insert(actPlay);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}catch(Exception e){
			log.error(e.toString());
		}
    }

    private boolean isAfter(Date date,Date end){
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		LocalDate myDate = LocalDate.parse(sdf.format(date));
		LocalDate endDate = LocalDate.parse(sdf.format(end));
		return myDate.isAfter(endDate);
	}

    private String getTomorrow(Date date){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE,1);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
		date=calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		return dateString;
	}
	
}
