package com.creatoo.hn.services.wap;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhUserMapper;
import com.creatoo.hn.mapper.WhVenuebkedMapper;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.model.WhVenuebked;
import com.creatoo.hn.services.comm.CommService;
import com.mysql.fabric.xmlrpc.base.Data;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class WapVenueService {
	@Autowired
	private CommService commService;
	@Autowired
	private WhUserMapper whUserMapper;
	@Autowired
	private WhVenuebkedMapper whVenuebkedMapper;

	/**
	 * 活动预约
	 * @param req
	 * @throws Exception 
	 */
	public void saveVenue(HttpServletRequest req) throws Exception {
		String vebid = this.commService.getKey("wh_venuebked");
		String vebvenid = req.getParameter("itemId");
		String openid = req.getParameter("openId");
		String vebstime = req.getParameter("begin");
		String vebetime = req.getParameter("end");
		Example example = new Example(WhUser.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("wxopenid", openid);
		List<WhUser> user = this.whUserMapper.selectByExample(example);
		String vebuid = user.get(0).getId();
		String day = req.getParameter("date"); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date now = new java.util.Date();
		WhVenuebked whVenuebked = new WhVenuebked();
		whVenuebked.setVebid(vebid);
		whVenuebked.setVebvenid(vebvenid);
		whVenuebked.setVebuid(vebuid);
		whVenuebked.setVebday(sdf.parse(day));
		whVenuebked.setVebstime(vebstime);
		whVenuebked.setVebetime(vebetime);
		whVenuebked.setVebstate(0);
		whVenuebked.setVebordertime(now);
		this.whVenuebkedMapper.insertSelective(whVenuebked);
	}

}
