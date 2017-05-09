package com.creatoo.hn.services.admin.venue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.creatoo.hn.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhActivityMapper;
import com.creatoo.hn.mapper.WhTrainMapper;
import com.creatoo.hn.mapper.WhUserMapper;
import com.creatoo.hn.mapper.WhVenueMapper;
import com.creatoo.hn.mapper.WhVenuebkedMapper;
import com.creatoo.hn.mapper.WhVenuedateMapper;
import com.creatoo.hn.mapper.WhVenuetimeMapper;
import com.creatoo.hn.model.WhActivity;
import com.creatoo.hn.model.WhTraenr;
import com.creatoo.hn.model.WhTrain;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.model.WhVenue;
import com.creatoo.hn.model.WhVenuebked;
import com.creatoo.hn.model.WhVenuedate;
import com.creatoo.hn.model.WhVenuetime;
import com.creatoo.hn.utils.EmailUtil;
import com.creatoo.hn.utils.SmsUtil;
import com.creatoo.hn.utils.UploadUtil;
import com.creatoo.hn.utils.WhConstance;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class VenueService {
	
	@Autowired
	private WhVenueMapper whVenueMapper;
	
	@Autowired
	private WhVenuetimeMapper whVenuetimeMapper;
	
	@Autowired
	private WhVenuebkedMapper whVenuebkedMapper;
	@Autowired
	private WhTrainMapper WhTrainMapper;
	@Autowired
	private WhActivityMapper whActivityMapper;
	@Autowired
	private WhUserMapper WhUserMapper;
	@Autowired
	private WhVenuedateMapper whVenuedateMapper;

	@SuppressWarnings("rawtypes")
	public Map<String, Object> findVenue(Map<String, Object> param) {
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.whVenueMapper.selVenue(param);
		
		// 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}

	/**
	 * 更新
	 * @param whVenue
	 */
	public void upVenue(WhVenue whVenue)throws Exception {
		this.whVenueMapper.updateByPrimaryKeySelective(whVenue);
		
	}

	/**
	 * 添加
	 * @param whVenue
	 */
	public void addVenue(WhVenue whVenue)throws Exception {
		this.whVenueMapper.insertSelective(whVenue);
		
	}

	/**
	 * 删除
	 * @param venid
	 */
	public void delVen(String uploadPath,String venid)throws Exception {
		WhVenue whVenue = this.whVenueMapper.selectByPrimaryKey(venid);
		String venpic = whVenue.getVenpic();
		this.whVenueMapper.deleteByPrimaryKey(venid);
		UploadUtil.delUploadFile(uploadPath, venpic);
		this.whVenueMapper.deleteByPrimaryKey(venid);
		Example example = new Example(WhVenuedate.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("vendpid", venid);
		List<WhVenuedate> date = this.whVenuedateMapper.selectByExample(example);
		this.whVenuedateMapper.deleteByExample(example);
		for (int i = 0; i < date.size(); i++) {
			String dateid = date.get(i).getVendpid();
			Example example2 = new Example(WhVenuetime.class);
			Criteria c2 = example2.createCriteria();
			c2.andEqualTo("venid", dateid);
			this.whVenuetimeMapper.deleteByExample(example2);
		}
	}

	/**
	 * 审核或者打回
	 * @param venid
	 * @param fromstate
	 * @param tostate
	 */
	public Object checkOrBack(int vencanbk,String venid, int fromstate, int tostate,String _is)throws Exception {
		Date now = new Date(); 
		WhVenue whVenue = new WhVenue();
		whVenue.setVenopttime(now);
		whVenue.setVenstate(tostate);
		if (vencanbk == 1|| vencanbk != 0) {
			if(_is != null && !"".equals(_is)){
				Example example =new Example(WhVenuedate.class);
				Criteria c = example.createCriteria();
				c.andEqualTo("vendpid", venid);
				c.andEqualTo("vendstate", 1);
				int	listcount=this.whVenuedateMapper.selectCountByExample(example);
				if(listcount == 0){
					return "没有设置日期,不允许送审！";
				}
			}
		}
		if(tostate == 2 && fromstate == 3){
			Example example =new Example(WhVenuebked.class);
			example.createCriteria().andEqualTo("vebvenid",venid);
			int listcount=this.whVenuebkedMapper.selectCountByExample(example); 
			if(listcount>0){
				return "所选场馆已被预定,不允许取消发布！";
			}
		}
		
		Example example = new Example(WhVenue.class);
		example.createCriteria().andEqualTo("venid", venid);
		example.createCriteria().andEqualTo("venstate", fromstate);
		this.whVenueMapper.updateByExampleSelective(whVenue, example);
		return "操作成功！";
	}

	/**
	 * 批量审核或者打回
	 * @param venid
	 * @param fromstate
	 * @param tostate
	 */
	@SuppressWarnings("unused")
	public Object allCheckOrBack(String venid, int fromstate, int tostate,String _is)throws Exception {
		List<String> list = new ArrayList<String>();
		Date now = new Date(); 
		String[] mage = venid.split(",");
		for (int i = 0; i < mage.length; i++) {
			list.add(mage[i]);
		}
		if(tostate == 2 && fromstate == 3){
			Example example =new Example(WhVenuebked.class);
			Criteria c2 = example.createCriteria();
			example.or().andIn("vebvenid", list);
			int listcount=this.whVenuebkedMapper.selectCountByExample(example); 
			if(listcount>0){
				return "所选场馆已被预定,不允许取消发布！";
			}
		}
		if(_is != null && !"".equals(_is)){
			Example example =new Example(WhVenuedate.class);
			
			for (int i = 0; i < list.size(); i++) {
				example.or().andEqualTo("vendpid", list.get(i));
				int	listcount=this.whVenuedateMapper.selectCountByExample(example);
				if(listcount < 1 ){
					return "没有设置时段,不允许送审！";
				}
			}
			
		}
		WhVenue whVenue = new WhVenue();
		whVenue.setVenopttime(now);
		whVenue.setVenstate(tostate);
		Example example = new Example(WhVenue.class);
		example.createCriteria().andIn("venid", list).andEqualTo("venstate", fromstate);
		this.whVenueMapper.updateByExampleSelective(whVenue, example);
		return "操作成功！";
	}
	/**
	 * 找到时段
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> findTime(Map<String, Object> param)throws Exception {
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.whVenueMapper.selTime(param);
		
		// 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}

	/**
	 * 更新时段
	 * @param whVenuetime
	 * @return 
	 */
	public Object upVenTime(String venid,WhVenuetime whVenuetime)throws Exception {
		SimpleDateFormat sdf  =   new  SimpleDateFormat( "HH:mm" );
		Date stime = sdf.parse(whVenuetime.getVtstime());
		Date etime = sdf.parse(whVenuetime.getVtetime());
		List<Map> timelist = this.whVenueMapper.selTimeList(venid);
		for (int i = 0; i < timelist.size(); i++) {
			Date _stime = sdf.parse((String) timelist.get(i).get("vtstime"));
			Date _etime = sdf.parse((String) timelist.get(i).get("vtetime"));
			if (!(stime.after(_stime) && etime.after(_etime) || stime.before(_stime) && etime.before(_etime))) {
				return "时段重复"+_stime+""+_etime;
			}
		}
		this.whVenuetimeMapper.updateByPrimaryKeySelective(whVenuetime);
		return "操作成功！";
	}

	/**
	 * 添加时段
	 * @param whVenuetime
	 */
	public Object addVenTime(String venid,WhVenuetime whVenuetime)throws Exception {
		SimpleDateFormat sdf  =   new  SimpleDateFormat( "HH:mm" );
		Date stime = sdf.parse(whVenuetime.getVtstime());
		Date etime = sdf.parse(whVenuetime.getVtetime());
		List<Map> timelist = this.whVenueMapper.selTimeList(venid);
		for (int i = 0; i < timelist.size(); i++) {
			Date _stime = sdf.parse((String) timelist.get(i).get("vtstime"));
			Date _etime = sdf.parse((String) timelist.get(i).get("vtetime"));
			if (!(stime.after(_stime) && etime.after(_etime) || stime.before(_stime) && etime.before(_etime))) {
				return "时段重复";
			}
		}
		this.whVenuetimeMapper.insertSelective(whVenuetime);
		return "操作成功！";
	}

	/**
	 * 启用和停用
	 * @param vtid
	 * @param fromstate
	 * @param tostate
	 */
	public void onOrOff(String vtid, int fromstate, int tostate)throws Exception {
		WhVenuetime whVenuetime = new WhVenuetime();
		whVenuetime.setVtstate(tostate);
		Example example = new Example(WhVenuetime.class);
		if (vtid != null) {
			example.createCriteria().andEqualTo("vtid", vtid);
		}
		example.createCriteria().andEqualTo("vtstate", fromstate);
		
		this.whVenuetimeMapper.updateByExampleSelective(whVenuetime, example);
		
	}

	/**
	 * 查询预定信息
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> finddes(Map<String, Object> param)throws Exception {
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.whVenueMapper.seldestine(param);
		
		// 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}

	/**
	 * 删除预定信息
	 * @param vebid
	 * @return
	 */
	public Object delDestine(String vebid) {
		
		return this.whVenuebkedMapper.deleteByPrimaryKey(vebid);
	}

	/**
	 * 场馆预定的审核
	 * @param vebid
	 * @param vebcheckmsg
	 * @param vebstate
	 * @throws Exception
	 */
	public void checkDestine(String vebid, String vebcheckmsg, int vebstate,String name,String venname,String vebuid)throws Exception {
		WhVenuebked whVenuebked = this.whVenuebkedMapper.selectByPrimaryKey(vebid);
		whVenuebked.setVebstate(vebstate);
		whVenuebked.setVebcheckmsg(vebcheckmsg);
		this.whVenuebkedMapper.updateByPrimaryKeySelective(whVenuebked);
		Example example = new Example(WhUser.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("id", vebuid);
		List<WhUser> user = this.WhUserMapper.selectByExample(example);
		String moban = WhConstance.getSysProperty("SMS_TRAINCHECK");
		moban = moban.replace("${name}", name);
		moban = moban.replace("${title}", venname);
		if (vebstate == 1) {
			moban = moban.replace("${result}", "通过");
		}
		if (vebstate == 2) {
			moban = moban.replace("${result}", "不通过");
		}
		if (!user.isEmpty()) {
			String phone = user.get(0).getPhone();
			String email = user.get(0).getEmail();
			if (phone != null && !phone.isEmpty()) {
				SmsUtil.sendNotice(phone, moban);
			}else{
				EmailUtil.sendNoticeEmail(email, moban);
			}
		}
		
	}
	
	/**
	 * 找到所有的场馆名称
	 * @return
	 */
	public Object findName() {
		return this.whVenueMapper.selectAll();
	}
	
	/**
	 * 是否能够修改时段
	 * @param vtid
	 * @return
	 */
	@SuppressWarnings("unused")
	public int isCanEdit(String vtid)throws Exception {
		WhVenuebked whVenuebked = new WhVenuebked();
		Example example = new Example(WhVenuebked.class);
		if (vtid != null) {
			example.createCriteria().andEqualTo("vebtid", vtid);
		}
		int result = this.whVenuebkedMapper.selectCountByExample(example);
		return result;
	}
	
	/**
	 * 判断是否能够通过审核
	 * @param vebvenid
	 * @param vebday
	 * @param vebstime
	 * @param vebetime
	 * @return
	 */
	public int isCanCheck(String vebid,String vebvenid, String vebday, String vebstime, String vebetime)throws Exception {
		Example example = new Example(WhVenuebked.class);
		Criteria c =  example.createCriteria();
		c.andEqualTo("vebstate", 1);
		c.andEqualTo("vebvenid", vebvenid);
		c.andEqualTo("vebday", vebday);
		c.andEqualTo("vebstime", vebstime);
		c.andEqualTo("vebetime", vebetime);
		
		int res = this.whVenuebkedMapper.selectCountByExample(example);
		/*
		if (res != 0 && res > 0 ) {
			WhVenuebked whVenuebked = this.whVenuebkedMapper.selectByPrimaryKey(vebid);
			whVenuebked.setVebstate(2);
			whVenuebked.setVebcheckmsg("该场馆此时段已经被预定");
			this.whVenuebkedMapper.updateByPrimaryKeySelective(whVenuebked);
		}*/
		return res; 
	}

	/**
	 * 删除
	 * @param vtid
	 * @return
	 */
	public Object delTime(String vtid) {
		
		return this.whVenuetimeMapper.deleteByPrimaryKey(vtid);
	}

	/**
	 * 关联到培训，拿到已发布的培训的标题
	 * @return
	 */
	public Object findTrain()throws Exception{
		Example example = new Example(WhTrain.class);
		example.createCriteria().andEqualTo("trastate",3);
		return this.WhTrainMapper.selectByExample(example);
	}

	/**
	 * 关联到活动
	 * @return
	 */
	public Object findAct() throws Exception {
		Example example = new Example(WhActivity.class);
		example.createCriteria().andEqualTo("actvstate",3);
		return this.whActivityMapper.selectByExample(example);
	}

	/**
	 * 找到用户
	 * @param id
	 * @return
	 */
	public Object findUser(String id) {
		
		return this.WhUserMapper.selectAll();
	}

	/**
	 * 批量审核不通过
	 * @param vebids
	 */
	public void checkAllDestine(String vebids, int fromstate, int tostate, String vebcheckmsg) {
		List<String> list = new ArrayList<String>();
		String[] vebid = vebids.split(",");
		for (int i = 0; i < vebid.length; i++) {
			list.add(vebid[i]);
		}
		Example example = new Example(WhVenuebked.class);
		Criteria c = example.createCriteria();
		c.andIn("vebid", list);
	
		List<WhVenuebked> venue = this.whVenuebkedMapper.selectByExample(example);
		
		for (int i = 0; i < venue.size(); i++) {
			if (venue.get(i).getVebstate() == fromstate) {
				venue.get(i).setVebcheckmsg(vebcheckmsg);
				venue.get(i).setVebstate(tostate);
				this.whVenuebkedMapper.updateByPrimaryKeySelective(venue.get(i));
			}
		}
	}

	/**
	 * 查找用户的电话号码或者邮箱
	 * @param vebids
	 * @return
	 * @throws Exception 
	 */
	public void selPhone(String vebids) throws Exception {
		//将预定ID保存在list里面
		List<String> enrid = new ArrayList<String>();
		List<WhUser> user = new ArrayList<WhUser>();
		List<WhVenue> venue = new ArrayList<WhVenue>();
		String[] id = vebids.split(",");
		for (int i = 0; i < id.length; i++) {
			enrid.add(id[i]);
		}
		//通过报名ID查找状态
		Example example = new Example(WhVenuebked.class);
		Criteria c = example.createCriteria();
		c.andIn("vebid", enrid);
		c.andEqualTo("vebstate", 0);
		List<WhVenuebked> venbk = this.whVenuebkedMapper.selectByExample(example);
		//放置用户ID的list
		List<String> uid = new ArrayList<String>();
		List<String> vid = new ArrayList<String>();
		
		for (int i = 0; i < venbk.size(); i++) {
			//用户ID
			uid.add(venbk.get(i).getVebuid());
			//场馆ID
			vid.add(venbk.get(i).getVebvenid());
			
		}
		//通过用户ID拿到用户信息
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		
			//用户信息
			Example userexample = new Example(WhUser.class);
			Criteria userc = userexample.createCriteria();
			userc.andIn("id", uid);
			user = this.WhUserMapper.selectByExample(userexample);
			for (WhUser item : user) {
				map.put(item.getId(), item);
			}
			
			
			//培训信息
			Example venexample = new Example(WhVenue.class);
			Criteria trac = venexample.createCriteria();
			trac.andIn("venid", vid);
			venue = this.whVenueMapper.selectByExample(venexample);
			for (WhVenue item : venue) {
				map1.put(item.getVenid(), item);
			}
			String moban = WhConstance.getSysProperty("VENUECHECK");
			for (WhVenuebked item : venbk) {
				WhUser _user = (WhUser) map.get(item.getVebuid());
				WhVenue  _venue = (WhVenue) map1.get(item.getVebvenid());
				moban = moban.replace("${name}", _user.getNickname());
				moban = moban.replace("${title}", _venue.getVenname());
				//moban = moban.replace("{address}", add);
				moban = moban.replace("${result}", "未通过");
				if (_user.getPhone() != null && !_user.getPhone().isEmpty()) {
					SmsUtil.sendNotice(_user.getPhone(), moban);
				}else{
					EmailUtil.sendNoticeEmail(_user.getEmail(), moban);
				}
			}
				
				
	}

	/**
	 * 更新场馆日期
	 * @param venid
	 * @param whVenuedate
	 * @return
	 */
	public Object upVenDate(String venid, WhVenuedate whVenuedate) {
		Date sdate = whVenuedate.getVendsdate();
		Date edate = whVenuedate.getVendedate();
		Example example = new Example(WhVenuedate.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("vendpid", venid);
		List<WhVenuedate> datelist = this.whVenuedateMapper.selectByExample(example);
		for (int i = 0; i < datelist.size(); i++) {
			Date _stime = datelist.get(i).getVendsdate();
			Date _etime = datelist.get(i).getVendedate();
			if (!(sdate.after(_stime) && edate.after(_etime) || sdate.before(_stime) && edate.before(_etime))) {
				return "日期重复";
			}
		}
		this.whVenuedateMapper.updateByPrimaryKeySelective(whVenuedate);
		return "操作成功！";
	}

	/**
	 * 添加场馆日期
	 * @param venid
	 * @param whVenuedate
	 * @return
	 */
	public Object addVenDate(String venid, WhVenuedate whVenuedate) {
		Date sdate = whVenuedate.getVendsdate();
		Date edate = whVenuedate.getVendedate();
		Example example = new Example(WhVenuedate.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("vendpid", venid);
		List<WhVenuedate> datelist = this.whVenuedateMapper.selectByExample(example);
		for (int i = 0; i < datelist.size(); i++) {
			Date _stime = datelist.get(i).getVendsdate();
			Date _etime = datelist.get(i).getVendedate();
			if (!(sdate.after(_stime) && edate.after(_etime) || sdate.before(_stime) && edate.before(_etime))) {
				return "日期重复";
			}
		}
		this.whVenuedateMapper.insertSelective(whVenuedate);
		return "操作成功！";
	}

	/**
	 * 能否修改删除停用改日期
	 * @param vendid
	 * @return
	 */
	public int isCanEditDate(String vendid)throws Exception {
		int result = this.whVenueMapper.selCountDate(vendid);
		return result;
	}

	/**
	 * 查询日期信息
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> findDate(Map<String, Object> paramMap) {
		//分页信息
		int page = Integer.parseInt((String)paramMap.get("page"));
		int rows = Integer.parseInt((String)paramMap.get("rows"));

		//带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.whVenueMapper.selDate(paramMap);

		// 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}

	/**
	 * 删除日期
	 * @param vendid
	 */
	public void delVenDate(String vendid)throws Exception {
		this.whVenuedateMapper.deleteByPrimaryKey(vendid);

	}

	/**
	 * 启用停用日期
	 * @param vendid
	 * @param fromstate
	 * @param tostate
	 */
	public void checkDate(String vendid, int fromstate, int tostate) {
		WhVenuedate whVenuedate = new WhVenuedate();
		whVenuedate.setVendstate(tostate);
		whVenuedate.setVendopttime(new Date());
		Example example = new Example(WhVenuedate.class);
		if (vendid != null) {
			example.createCriteria().andEqualTo("vendid", vendid);
		}
		example.createCriteria().andEqualTo("vendstate", fromstate);

		this.whVenuedateMapper.updateByExampleSelective(whVenuedate, example);


	}

	/**
	 * 能否启用日期
	 */
	public int isUseDate(String vendid)throws Exception {
		Example example = new Example(WhVenuetime.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("venid", vendid);
		c.andEqualTo("vtstate", 1);
		int result = this.whVenuetimeMapper.selectCountByExample(example);
		return result;
	}



	@Autowired
	private VenuesMapper venuesMapper;

	/**
	 * 取指定场馆的预定日期时段
	 * @param venid
	 * @return
	 * @throws Exception
     */
	public List<Object> selectVenueDateList(String venid) throws Exception{
		return this.venuesMapper.selectVenueDateList(venid);
	}

	/**
	 * 后台内定查询指定日期的时段信息
	 * @param vendid
	 * @return
	 * @throws Exception
     */
	public List<Object> selectVenueTimeList(String vendid) throws Exception{
		return this.venuesMapper.selectVenueTimeList(vendid);
	}

	//后台内定查询指定日期的时段已订信息，用于参考控制
	public List<Object> selectVenueTimeBkedList(String vendid) throws Exception{
		return this.venuesMapper.selectVenueTimeBkedList(vendid);
	}

	/**
	 * 内部预定保存
	 * @param bked
	 * @throws Exception
     */
	public void saveVenuebked4Admin(WhVenuebked bked) throws Exception{
		//验证有没有相同的日期时段已预定
		WhVenuebked conf = new WhVenuebked();
		conf.setVebday( bked.getVebday() );
		conf.setVebstime( bked.getVebstime() );
		conf.setVebetime( bked.getVebetime() );
		conf.setVebvenid( bked.getVebvenid() );
		conf.setVebtid( bked.getVebtid() );
		conf.setVebstate( bked.getVebstate() );

		int cont = this.whVenuebkedMapper.selectCount(conf);
		if (cont > 0){
			throw new Exception("指定的场馆时段已有有效预定存在");
		}

		this.whVenuebkedMapper.insert(bked);
	}

	/**
	 * 内部预定删除
	 * @param bked
	 * @throws Exception
     */
	public void removeVenuebked4Admin(WhVenuebked bked) throws Exception{
		this.whVenuebkedMapper.delete(bked);
	}


}
