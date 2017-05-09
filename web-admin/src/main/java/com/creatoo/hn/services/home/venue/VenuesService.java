package com.creatoo.hn.services.home.venue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.VenuesMapper;
import com.creatoo.hn.mapper.WhVenuebkedMapper;
import com.creatoo.hn.model.WhVenuebked;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.WhConstance;
import com.github.pagehelper.PageHelper;

@Service
public class VenuesService {
	
	@Autowired
	private VenuesMapper venueMapper;
	
	@Autowired
    private CommService commservice;
	
	@Autowired
	private WhVenuebkedMapper venuebkedMapper;
	
	
	/** 查询指定ID的场馆
	 * @param id
	 * @return
	 */
	public Object findVenue(String id){
		return this.venueMapper.findVenue4Id(id);
	}
	
	
	/** 查询场馆开放预订时段列表
	 * @param venid
	 * @return
	 */
	public List<Object> selectVenueTime(String venid){
		return this.venueMapper.selectVenueTime(venid);
	}

	/** 查询场馆指定日期时段预定信息
	 * @param venid
	 * @param bday
	 * @return
	 * @throws Exception
	 */
	public List<Object> selectVenueBked(String venid, String bday, int lastNum) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date _bday = sdf.parse(bday);
		Date _eday = lastDate4Day(_bday, lastNum);
		
		return this.venueMapper.selectVenueBked(venid, _bday, _eday);
	}
	
	/** 查询指定时段内某用户对某场馆未审核通过的预定
	 * @param venid
	 * @param uid
	 * @param bday
	 * @param lastNum
	 * @return
	 * @throws Exception
	 */
	public List<Object> selectVenueBkedNotcheck4User(String venid, String uid, String bday, int lastNum) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date _bday = sdf.parse(bday);
		Date _eday = lastDate4Day(_bday, lastNum);
		
		return this.venueMapper.selectVenueBkedNotcheck4User(venid, uid, _bday, _eday);
	}
	
	//处理指定天数的时间对象
	private Date lastDate4Day(Date bday, int lastNum){
		Calendar c = Calendar.getInstance();
		c.setTime(bday);
		c.add(Calendar.DAY_OF_YEAR, lastNum);
		return c.getTime();
	}
	
	/** 保存传入的场馆预定信息
	 * @param uid
	 * @param bkedlist
	 * @throws Exception
	 */
	public void saveVenueBkedList(String uid, List<Map<String,Object>> bkedlist) throws Exception{
		if (uid == null) throw new Exception("0"); //会话ID没有
		if (bkedlist == null) throw new Exception("error");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//同时段可定数量限制 VENUE_DATE_NUM
		int maxNum = 0;
		try {
			String date_num = WhConstance.getSysProperty("VENUE_DATE_NUM", "0");
			maxNum = Integer.valueOf(date_num);
		} catch (Exception e) {
			maxNum = 0;
		}
		
		List<WhVenuebked> vbklist = new ArrayList<WhVenuebked>();
		for(Map<String,Object> ent : bkedlist){
			WhVenuebked vbk = new WhVenuebked();
			vbk.setVebuid(uid);
			vbk.setVebvenid( (String)ent.get("vebvenid"));
			vbk.setVebday( sdf.parse( (String)ent.get("vebday") ) );
			vbk.setVebstime( (String)ent.get("vebstime") );
			vbk.setVebetime( (String)ent.get("vebetime") );
			vbk.setVebtid( (String)ent.get("vebtid") );
			
			//调用验证
			checkBked(vbk, maxNum);
			vbklist.add(vbk);
		}
		
		Date ordertime = new Date(System.currentTimeMillis());
		for(WhVenuebked vbk : vbklist){
			vbk.setVebid( this.commservice.getKey("WhVenuebked") );
			vbk.setVebstate(0);
			vbk.setVebordertime(ordertime);
			this.venuebkedMapper.insert(vbk);
		}
	}
	
	//验证预定信息
	private void checkBked(WhVenuebked vbk, int maxNum) throws Exception{
		WhVenuebked test = new WhVenuebked();
		int count = 0;
		test.setVebvenid(vbk.getVebvenid());
		test.setVebday(vbk.getVebday());
		test.setVebstime(vbk.getVebstime());
		test.setVebetime(vbk.getVebetime());
		
		//相关时段时否已有审核的预订
		test.setVebstate(1);
		count = this.venuebkedMapper.selectCount(test);
		if (count > 0){
			throw new Exception("1"); //场馆时段已有有效预定
		}
		test.setVebstate(null);
		
		//是否同场馆同时段同用户已预订过
		test.setVebuid(vbk.getVebuid());
		count = this.venuebkedMapper.selectCount(test);
		if (count > 0){
			throw new Exception("2"); //重复预订同场馆同时段
		}
		//是否异场馆同时段同用户已预订过
		/*test.setVebvenid(null);
		if (maxNum > 0){
			count = this.venuebkedMapper.selectCount(test);
			if (count >= maxNum){
				throw new Exception("3"); //同用户同时间有其它场馆预定,数量超系统限定
			}
		}*/
	}
	
	/** 查询场馆相关的图片资源
	 * @param venid
	 * @return
	 * @throws Exception
	 */
	public List<Object> selectVenueEntSrc(String venid){
		return this.venueMapper.selectVenueEntSrc(venid);
	}

	/**
	 * 查询推荐场馆集
	 * @param size
	 * @param notid
     * @return
     */
	public List<Object> selectVenueList(int size, String notid){
		PageHelper.startPage(1, size);
		return this.venueMapper.selectVenueList(notid);
	}


	/**
	 * 查询指定天的场馆时段集
	 * @param venid
	 * @param day
     * @return
     */
	public List<Object> selectVenueTimes4Day(String venid, Date day){
		return this.venueMapper.selectVenueTimes4Day(venid, day);
	}

	/**
	 * 查询指定天的场馆有效预定
	 * @param venid
	 * @param day
     * @return
     */
	public List<Object> selectVenueBked4Day(String venid, Date day){
		return this.venueMapper.selectVenueBked4Day(venid, day);
	}

	/**
	 * 查询指定天的场馆指定用户的预定未审核信息
	 * @param venid
	 * @param day
	 * @param uid
     * @return
     */
	public List<Object> selectVenueBked4DayUserBide(String venid, Date day, String uid){
		return this.venueMapper.selectVenueBked4DayUserBide(venid, day, uid);
	}
}
