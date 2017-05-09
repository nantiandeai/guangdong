package com.creatoo.hn.services.admin.train;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.GypxMapper;
import com.creatoo.hn.mapper.WhBrandActMapper;
import com.creatoo.hn.mapper.WhTraenrMapper;
import com.creatoo.hn.mapper.WhTrainMapper;
import com.creatoo.hn.mapper.WhTraitmtimeMapper;
import com.creatoo.hn.mapper.WhUserMapper;
import com.creatoo.hn.model.WhTraenr;
import com.creatoo.hn.model.WhTrain;
import com.creatoo.hn.model.WhTraitmtime;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.EmailUtil;
import com.creatoo.hn.utils.SmsUtil;
import com.creatoo.hn.utils.WhConstance;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 培训报名服务类
 * @author wangxl
 * @version 20161009
 */
@Service
public class EnrollService {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public CommService commService;
	
	@Autowired
	public WhTraenrMapper whTraenrMapper;
	
	@Autowired
	public WhTraitmtimeMapper whTraitmtimeMapper;
	
	@Autowired
	public WhTrainMapper whTrainMapper;
	
	@Autowired
	public WhUserMapper whUserMapper;
	
	@Autowired
	private GypxMapper gypxMapper;
	
	private WhBrandActMapper whBrandActMapper;
	
	/**
	 * 根据条件查询培训报名列表
	 * @param page 当前页
	 * @param rows 每页数
	 * @return
	 * @throws Exception
	 */
	public Object sreach(Map<String, Object> param)throws Exception{
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		PageHelper.startPage(page, rows);
		//多表查询带条件的分页查询
		
		List<HashMap> list = this.gypxMapper.selectEnroll(param);//this.whTraenrMapper.selectEnroll(param);
		// 取分页信息
        PageInfo<HashMap> pageInfo = new PageInfo<HashMap>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}
	
	/**
	 * 查询培训回收站的内容
	 * @param page 当前页
	 * @param rows 每页数
	 * @return 已删除的培训记录
	 * @throws Exception
	 */
//	public Object sreachTrainHis(int page, int rows)throws Exception{
//		
//		PageHelper.startPage(page, rows);
//		
//		//多表查询带条件的分页查询
//		List<HashMap> list = this.whTraenrMapper.selectEnroll("a");
//		
//		// 取分页信息
//        PageInfo<HashMap> pageInfo = new PageInfo<HashMap>(list);
//       
//        Map<String, Object> rtnMap = new HashMap<String, Object>();
//        rtnMap.put("total", pageInfo.getTotal());
//        rtnMap.put("rows", pageInfo.getList());
//		return rtnMap;
//	}
	
	/**
	 * 根据培训批次查询课程
	 * @param traitmid 培训批次标识
	 * @return 培训课程列表
	 * @throws Exception
	 */
	public List<WhTraitmtime> sreachKecheng(String traitmid, String start, String end)throws Exception{
		Example example = new Example(WhTraitmtime.class);
		example.createCriteria()
		.andEqualTo("traitmid", traitmid)
		.andGreaterThanOrEqualTo("tradate", start)
		.andLessThan("tradate", end);
		return this.whTraitmtimeMapper.selectByExample(example);
	}
	
	/**
	 * 添加或者编辑课时信息
	 * @param timeid
	 * @param traitmid
	 * @param stitle
	 * @param tradate
	 * @param stime
	 * @param etime
	 * @throws Exception
	 */
	public void kcadd(WhTraitmtime time)throws Exception{
		time.setTimeid(this.commService.getKey("WhTraitmtime"));
		this.whTraitmtimeMapper.insert(time);
	}
	
	/**
	 * @param time
	 * @throws Exception
	 */
	public void kcupd(WhTraitmtime time)throws Exception{
		Example example = new Example(WhTraitmtime.class);
		example.createCriteria().andEqualTo("timeid", time.getTimeid());
		this.whTraitmtimeMapper.updateByExampleSelective(time, example);
	}
	
	/**
	 * 删除课程表中的课时信息
	 * @param timeid 课时标识
	 * @throws Exception
	 */
	public void kcdel(String timeid)throws Exception{
		this.whTraitmtimeMapper.deleteByPrimaryKey(timeid);
	}
	//-------------------------培训报名------------------------------------
	
	/**
	 * 删除报名记录
	 * @param enrid
	 */
	public void delTraEnroll(String enrid)throws Exception {
		this.whTraenrMapper.deleteByPrimaryKey(enrid);
	}

	/**
	 * 审核报名信息
	 */
	public void checkEnroll(String enrid ,String enrstatemsg, String state, String enruid ,String name,String enrtraid)throws Exception {
		WhTraenr whTraenr = this.whTraenrMapper.selectByPrimaryKey(enrid);
		Example example = new Example(WhUser.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("id", enruid);
		List<WhUser> user = this.whUserMapper.selectByExample(example);
		String phone = user.get(0).getPhone();
		String email = user.get(0).getEmail();
		String moban = WhConstance.getSysProperty("SMS_TRAINCHECK");
		moban = moban.replace("${name}", name);
		
		Example example2 = new Example(WhTrain.class);
		Criteria c1 = example2.createCriteria();
		c1.andEqualTo("traid", enrtraid);
		List<WhTrain> tra = this.whTrainMapper.selectByExample(example2);
		String title = tra.get(0).getTratitle();
		moban = moban.replace("${title}", title);
		
		if (state == "3" || "3".equals(state)) {
			//whTraenr.setEnrstepstate(0);
			whTraenr.setEnrstate(state);
			whTraenr.setEnrstatemsg(enrstatemsg);
			moban = moban.replace("${result}", "未通过");
			//
			if (phone != null && !phone.isEmpty()) {
				SmsUtil.sendNotice(phone, moban);
			}else{
				EmailUtil.sendNoticeEmail(email, moban);
			}
			
		}else{
			//whTraenr.setEnrstepstate(1);
			whTraenr.setEnrstate(state);
			whTraenr.setEnrstatemsg(enrstatemsg);
			moban = moban.replace("${result}", "已通过");
			if (phone != null && !"".equals(phone)) {
				SmsUtil.sendNotice(phone, moban);
			}else{
				EmailUtil.sendNoticeEmail(email, moban);
			}
		}
		this.whTraenrMapper.updateByPrimaryKeySelective(whTraenr);
	}

	/**
	 * 审核所有
	 * @param enrid
	 * @param fromstate
	 * @param tostate
	 */
	public void checkAll(String enrids, String fromstate, String tostate,String enrstatemsg) {
		List<String> list = new ArrayList<String>();
		String[] enrid = enrids.split(",");
		for (int i = 0; i < enrid.length; i++) {
			list.add(enrid[i]);
		}
		Example example = new Example(WhTraenr.class);
		Criteria c = example.createCriteria();
		c.andIn("enrid", list);
	
		List<WhTraenr> whTraenrs = this.whTraenrMapper.selectByExample(example);
		
		for (int i = 0; i < whTraenrs.size(); i++) {
			if (whTraenrs.get(i).getEnrstate().equals(fromstate)) {
				whTraenrs.get(i).setEnrstatemsg(enrstatemsg);;
				whTraenrs.get(i).setEnrstate(tostate);
				this.whTraenrMapper.updateByPrimaryKeySelective(whTraenrs.get(i));
			}
		}
	}

	/**
	 * 查询已报名审核通过的人数
	 * @return
	 */
	public int findCount(String enrtraid)throws Exception {
		return this.gypxMapper.selEnrollCount(enrtraid);
	}

	/**
	 * 查询能否发送面试通知
	 * @param enrtraid
	 * @param enruid
	 * @return
	 */
	public Object isCanSend(String enrtraid, String enruid)throws Exception {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		String success = "0";
		String errmsg = "";
		Object msg = "";
		if (enrtraid != null) {
			Example example = new Example(WhTrain.class);
			Criteria c = example.createCriteria();
			c.andEqualTo("traid", enrtraid);
			List<WhTrain> whTrain =  this.whTrainMapper.selectByExample(example);
			int traisnotic = whTrain.get(0).getTraisnotic();
			if (traisnotic == 0) {
				msg = "不需要发送面试通知！";
				success = "1";
				rtnMap.put("success", success);
				rtnMap.put("msg", msg);
				return rtnMap;
			}
		}
		if (enruid != null) {  
			Example example1 = new Example(WhUser.class);
			Criteria c1 = example1.createCriteria();
			c1.andEqualTo("id", enruid);
			List<WhUser> user = this.whUserMapper.selectByExample(example1);
			String phone = user.get(0).getPhone();
			if (phone.isEmpty()) {
				msg = "该用户没有填写电话号码，无法发送短信通知！";
				success = "2";
				String email = user.get(0).getEmail();
				String name = user.get(0).getNickname();
				rtnMap.put("name", name);
				rtnMap.put("email", email);
				rtnMap.put("success", success);
				rtnMap.put("msg", msg);
				return rtnMap;
			}else{
				success = "0";
				rtnMap.put("success", success);
				msg = user.get(0).getNickname();
				rtnMap.put("msg", msg);
				rtnMap.put("phone", phone);
				return rtnMap;
			}
		}
		return rtnMap;
	}

	/**
	 * 查找用户的电话号码或者邮箱批量发送通知
	 * @param enrid
	 * @return
	 * @throws Exception 
	 */
	public void selPhone(String enrids,String fromstate) throws Exception {
		//将报名ID保存在list里面
		List<String> enrid = new ArrayList<String>();
		List<WhUser> user = new ArrayList<WhUser>();
		List<WhTrain> train = new ArrayList<WhTrain>();
		String[] id = enrids.split(",");
		for (int i = 0; i < id.length; i++) {
			enrid.add(id[i]);
		}
		//通过报名ID查找状态
		Example example = new Example(WhTraenr.class);
		Criteria c = example.createCriteria();
		c.andIn("enrid", enrid);
		c.andEqualTo("enrstate", 0);
		List<WhTraenr> traenr = this.whTraenrMapper.selectByExample(example);
		//放置用户ID的list
		List<String> uid = new ArrayList<String>();
		List<String> tid = new ArrayList<String>();
		
		for (int i = 0; i < traenr.size(); i++) {
			//用户ID
			uid.add(traenr.get(i).getEnruid());
			//培训ID
			tid.add(traenr.get(i).getEnrtraid());
			
		}
			//通过用户ID拿到用户信息
			Map<String,Object> map = new HashMap<String,Object>();
			Map<String,Object> map1 = new HashMap<String,Object>();
			
				//用户信息
				Example userexample = new Example(WhUser.class);
				Criteria userc = userexample.createCriteria();
				userc.andIn("id", uid);
				user = this.whUserMapper.selectByExample(userexample);
				for (WhUser item : user) {
					map.put(item.getId(), item);
				}
				
				
				//培训信息
				Example traexample = new Example(WhTrain.class);
				Criteria trac = traexample.createCriteria();
				trac.andIn("traid", tid);
				train = this.whTrainMapper.selectByExample(traexample);
				for (WhTrain item : train) {
					map1.put(item.getTraid(), item);
				}
				String moban = WhConstance.getSysProperty("SMS_TRAINCHECK");
				for (WhTraenr item : traenr) {
					WhUser _user = (WhUser) map.get(item.getEnruid());
					WhTrain _train = (WhTrain) map1.get(item.getEnrtraid());
					moban = moban.replace("${name}", _user.getNickname());
					moban = moban.replace("${title}", _train.getTratitle());
					//moban = moban.replace("{address}", add);
					moban = moban.replace("${result}", "未通过");
					if (_user.getPhone() != null && !_user.getPhone().isEmpty()) {
						SmsUtil.sendNotice(_user.getPhone(), moban);
					}else{
						EmailUtil.sendNoticeEmail(_user.getEmail(), moban);
					}
				}
				
				
	}
}
