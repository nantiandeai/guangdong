package com.creatoo.hn.services.home.userCenter;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhCodeMapper;
import com.creatoo.hn.mapper.WhUserAlertsMapper;
import com.creatoo.hn.mapper.WhUserMapper;
import com.creatoo.hn.mapper.WhgActActivityMapper;
import com.creatoo.hn.mapper.WhgActOrderMapper;
import com.creatoo.hn.mapper.WhgActTicketMapper;
import com.creatoo.hn.model.WhCode;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.model.WhUserAlerts;
import com.creatoo.hn.model.WhgActActivity;
import com.creatoo.hn.model.WhgActOrder;
import com.creatoo.hn.model.WhgActTicket;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 个人用户中心业务类
 * 
 * @author dzl
 *
 */
@Service
public class UserCenterService {
	@Autowired
	private WhUserMapper userMapper;
	
	@Autowired
	private WhCodeMapper codeMapper;
	
	@Autowired
	private WhUserAlertsMapper alertMapper;
	
	@Autowired
	private WhgActTicketMapper whgActTicketMapper;
	
	@Autowired
	private WhgActActivityMapper whgActActivityMapper;
	
	@Autowired
	private WhgActOrderMapper whgActOrderMapper;

	/**
	 * 判断用户名和密码
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public Object login(String userName, String password)throws Exception {
		Example example = new Example(WhUser.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("phone", userName);
		c.andEqualTo("password", password);
		Criteria c2 = example.createCriteria();
		c2.andEqualTo("password", password);
		c2.andEqualTo("email", userName);
		example.or(c2);
		List<WhUser> userlist = this.userMapper.selectByExample(example);
		if(userlist != null && userlist.size() == 1 ){
			WhUser record = new WhUser();
			record.setId(userlist.get(0).getId());
			record.setLastdate(new Date());
			this.userMapper.updateByPrimaryKeySelective(record);
		}
		return userlist;
	}
	
	/**
	 * 修改用户密码
	 * @param whuser
	 * @return
	 */
	public Object modifyPwd(WhUser whuser)throws Exception{
		return this.userMapper.updateByPrimaryKeySelective(whuser);
	}
    
	/**
	 * 保存修改密码信息（手机/邮箱 及其相应的验证码）
	 * @param whcode 验证码表
	 * @return
	 * @throws Exception
	 */
	public Object saveFindPwd2(WhCode whcode)throws Exception{
		return this.codeMapper.insert(whcode);
	}
	
	/**
	 * 根据手机号码查询
	 */
	public List<WhUser> getPhoneList(String phone)throws Exception{
		Example example = new Example(WhUser.class);
		example.createCriteria().andEqualTo("phone",phone);
		return this.userMapper.selectByExample(example);
	}
	
	/**
	 * 根据邮箱地址查询
	 */
	public List<WhUser> getEmailList(String email)throws Exception{
		Example example = new Example(WhUser.class);
		example.createCriteria().andEqualTo("email", email);
		return this.userMapper.selectByExample(example);
	}
	
	/**
	 * 根据昵称查询记录
	 */
	public int getNickName(String nickname)throws Exception{
		Example example = new Example(WhUser.class);
		example.createCriteria().andEqualTo("nickname", nickname);
		return this.userMapper.selectCountByExample(example);
	}
	
	/**
	 * 绑定手机号码
	 * @param whuser
	 * @return
	 */
	public Object modifyPhone(WhUser whuser)throws Exception{
	 return this.userMapper.updateByPrimaryKeySelective(whuser);
		
	}
	
	/**
	 * 根据id获得用户信息
	 * 
	 * @param id
	 * @return
	 */
	public Object getList(String id)throws Exception {
		return this.userMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 修改用户信息
	 * @param whuser
	 * @return
	 * @throws Exception
	 */
	public Object modifyUser(WhUser whuser)throws Exception{
		return this.userMapper.updateByPrimaryKeySelective(whuser);
	}
	
	/**
	 * 个人用户中心头部消息提示
	 * @param refuid
	 * @return
	 */
	public int selectMsgHeader(String refuid)throws Exception{
		Example example = new Example(WhUserAlerts.class);
		example.createCriteria().andEqualTo("refuid", refuid);
		return this.alertMapper.selectCountByExample(example);
	}
	
	/**
	 * 去除消息提示
	 * @param refuid
	 * @param reftype
	 * @return
	 */
	public void delMsgAlert(String refuid,String reftype)throws Exception{
		Example example = new Example(WhUserAlerts.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("refuid", refuid);
		c.andEqualTo("reftype", reftype);
		this.alertMapper.deleteByExample(example);
	}
	
	/**
	 * 个人用户中心消息提示
	 * @param refuid
	 * @return
	 */
	public Object selectMsgAlert(String refuid)throws Exception{
		Example example = new Example(WhUserAlerts.class);
		example.createCriteria().andEqualTo("refuid", refuid);
		return this.alertMapper.selectByExample(example);
	}
	
	/**
	 * 添加新信息
	 * @param userAlert
	 * @return
	 * @throws Exception
	 */
	public Object addNewAlert(WhUserAlerts userAlert)throws Exception{
		return this.alertMapper.insert(userAlert);
	}

	/**
	 *获取用户订单
	 * @param page
	 * @param rows
	 * @param type
	 * @param userId
	 * @return
	 */
	public PageInfo getOrderForCenter(int page,int rows,int type,String userId){
		PageHelper.startPage(page,rows);
		List<Map> list = null;
		if(1 == type){
			list = userMapper.getUserActOrderNotTimeOut(userId);
		}else if(2 == type){
			list = userMapper.getUserActOrderTimeOut(userId);
		}else{
			list = new ArrayList();//给个空数组
		}
		int sum = list.size();
		for(int i=0;i<sum;i++){
//			Map map= new HashMap();
			Map map = list.get(i);
			WhgActTicket whgActTicket = new WhgActTicket();
			whgActTicket.setOrderid(map.get("id").toString());
			List<WhgActTicket> ticketList = whgActTicketMapper.select(whgActTicket);
			String seatCode = "";
			for(int j= 0 ; j<ticketList.size();j++){
				WhgActTicket ticket = ticketList.get(j);
				if(ticket != null){
					
					if(0==ticketList.get(j).getTicketstatus()){
						seatCode += ticketList.get(j).getSeatcode()+"(未验票),";
					}else{
							seatCode += ticketList.get(j).getSeatcode()+"(已验票),";
					}
					
				}
			}
			if(seatCode !=""){
				seatCode = seatCode.substring(0,seatCode.length() - 1);
			}
			map.put("seatCode", seatCode);
			//验票状态
			if(ticketList.size() > 0){
				map.put("state", ticketList.get(0).getTicketstatus());
			}
//			WhgActActivity act = whgActActivityMapper.selectByPrimaryKey(map.get("activityid").toString());
//			if(act != null){
				Date dNow = new Date();   //当前时间
				Date dAffore = new Date();
				Calendar calendar = Calendar.getInstance(); //得到日历
				calendar.setTime(dNow);//把当前时间赋给日历
				calendar.add(Calendar.DAY_OF_MONTH, +2);  //设置为后两天
				dAffore = calendar.getTime();   //得到前两天的时间
				map.put("dAffore",dAffore);
				//map.put("strTime", act.getStarttime());
				map.put("strTime", map.get("playstarttime"));
//			}
		}
		return new PageInfo(list);
	}
	
	/**
	 * 根据Id查询订单详情
	 * @param orderId
	 * @return
	 */
	public WhgActOrder findOrderDetail(String orderId){
		return whgActOrderMapper.selectByPrimaryKey(orderId);
	}
	
	/**
	 * 更新订单状态
	 * @param actOrder
	 */
	public void upActOrder(WhgActOrder actOrder){
		whgActOrderMapper.updateByPrimaryKey(actOrder);
		
	}
}

