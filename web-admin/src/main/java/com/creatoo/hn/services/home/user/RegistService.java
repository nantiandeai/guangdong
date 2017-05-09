package com.creatoo.hn.services.home.user;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhCodeMapper;
import com.creatoo.hn.mapper.WhUserMapper;
import com.creatoo.hn.model.WhCode;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.services.comm.CommService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 用户注册业务类
 * @author dzl
 *
 */
@Service
public class RegistService {
	@Autowired
	private WhCodeMapper codeMapper;

	@Autowired
	private WhUserMapper userMapper;

	@Autowired
	public CommService commService;
	
	/**
	 * 根据手机号码查询
	 */
	public List<WhCode> getPhoneTime(String msgphone,Date now)throws Exception{
		//设置now为：yyyy-MM-dd 00:00:00
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(now);
		cal.set(java.util.Calendar.HOUR_OF_DAY,0);
		cal.set(java.util.Calendar.MINUTE,0);
		cal.set(java.util.Calendar.SECOND,0);
		
		Example example = new Example(WhCode.class);
		example.createCriteria().andEqualTo("msgphone",msgphone).andGreaterThanOrEqualTo("msgtime", cal.getTime());
		example.setOrderByClause("msgtime desc");
		return this.codeMapper.selectByExample(example);
	}
	
	/**
	 * 根据邮箱地址查询
	 */
	public List<WhCode> getEmailTime(String emailaddr, Date now)throws Exception{
		//设置now为：yyyy-MM-dd 00:00:00
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(now);
		cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
		cal.set(java.util.Calendar.MINUTE, 0);
		cal.set(java.util.Calendar.SECOND, 0);
		
		Example example = new Example(WhCode.class);
		example.createCriteria().andEqualTo("emailaddr", emailaddr).andGreaterThanOrEqualTo("msgtime", cal.getTime());
		example.setOrderByClause("msgtime desc");
		return this.codeMapper.selectByExample(example);
	}
	
	/**
	 * 根据微信三方登录的wxopenid查询
	 */
	public List<WhUser> getWxUser(String wxopenid)throws Exception{
		Example example = new Example(WhUser.class);
		example.createCriteria().andEqualTo("wxopenid",wxopenid);
		return this.userMapper.selectByExample(example);
	}
	
	/**
	 * 判断手机是否已注册
	 * @return 0-可注册, 非0-不可注册
	 */
	public int getPhone(String phone)throws Exception{
		Example example = new Example(WhUser.class);
		example.createCriteria().andEqualTo("phone", phone);
		return this.userMapper.selectCountByExample(example);
	}
	
	/**
	 * 判断邮箱是否已注册
	 * @param email
	 * @return 0-可注册, 非0-不可注册
	 */
	public int getEmail(String email)throws Exception{
		Example example = new Example(WhUser.class);
		example.createCriteria().andEqualTo("email", email);
		return this.userMapper.selectCountByExample(example);
	}
	
	/**
	 * 判断手机验证码是否正确
	 * @param msgcontent
	 * @param msgphone
	 * @param cid
	 * @return
	 */
	public List<WhCode> getPhoneList(String msgcontent,String msgphone,String cid)throws Exception{
		Example example = new Example(WhCode.class);
		Criteria c =  example.createCriteria();
		c.andEqualTo("msgphone",msgphone);
		c.andEqualTo("msgcontent",msgcontent);
		c.andEqualTo("id",cid);
		example.setOrderByClause("msgtime desc");
		return this.codeMapper.selectByExample(example);
	}
	
	/**
	 * 判断邮箱验证码是否正确
	 * @param email
	 * @param emailcode
	 * @param cid
	 * @return
	 */
	public List<WhCode> getEmailList(String emailcode,String email,String cid)throws Exception{
		Example example = new Example(WhCode.class);
		Criteria c =  example.createCriteria();
		c.andEqualTo("emailaddr",email);
		c.andEqualTo("emailcode",emailcode);
		c.andEqualTo("id",cid);
		example.setOrderByClause("msgtime desc");
		return this.codeMapper.selectByExample(example);
	}
	/**-------------------保存手机/邮箱/验证码信息（验证码表）------------------*/
	
	/**
	 * 保存手机号码及验证码至数据库（验证码表）
	 */
	public Object savePhone(WhCode whcode)throws Exception {
		return this.codeMapper.insert(whcode);
	}

	/**
	 * 删除手机发送的验证码
	 * @param phone
	 * @throws Exception
	 */
	public void delPhoneCode(String phone)throws Exception{
		Example example = new Example(WhCode.class);
		example.createCriteria().andEqualTo("msgphone", phone);
		this.codeMapper.deleteByExample(example);
	}

	public boolean validPhoneCode4API(String phone, String code)throws Exception{
		Example example = new Example(WhCode.class);
		example.createCriteria().andEqualTo("msgphone", phone).andEqualTo("msgcontent", code);
		int row = this.codeMapper.selectCountByExample(example);
		return row == 1 ;
	}

	/**
	 * 保存邮箱地址及验证码到数据库（验证码表）
	 */
	public Object saveEmail(WhCode whcode)throws Exception {
		return this.codeMapper.insert(whcode);
	}
	
	/**----------------------保存用户信息（用户表）----------------------*/

	/**
	 * 将注册信息保存到user中:第一步
	 * 
	 * @return
	 */
	
	public Object saveRegist(WhUser whuser)throws Exception{
		Example example = new Example(WhUser.class);
		if(whuser.getWxopenid() == null && "".equals(whuser.getWxopenid())) {
			if (whuser.getEmail() != null && !whuser.getEmail().isEmpty()) {
				example.createCriteria().andEqualTo("email", whuser.getEmail());
			} else if (whuser.getPhone() != null && !whuser.getPhone().isEmpty()) {
				example.createCriteria().andEqualTo("phone", whuser.getPhone());
			} else {
				throw new Exception("not account info");
			}
			int count = userMapper.selectCountByExample(example);
			if (count > 0){
				throw new Exception("exits account info");
			}
		}
		return this.userMapper.insert(whuser);
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
	 * 根据QQ三方登录的openid查询
	 */
	public List<WhUser> getUserList(String openid)throws Exception{
		Example example = new Example(WhUser.class);
		example.createCriteria().andEqualTo("openid",openid);
		return this.userMapper.selectByExample(example);
	}
	
	/**
	 * 根据微博三方登录的wid查询
	 */
	public List<WhUser> getWbList(String wid)throws Exception{
		Example example = new Example(WhUser.class);
		example.createCriteria().andEqualTo("wid",wid);
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
	 * 将注册信息保存到user中:第二步
	 * 
	 * @return
	 */
	public Object saveRegist2(WhUser whuser)throws Exception {
		return this.userMapper.updateByPrimaryKeySelective(whuser);
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
}
