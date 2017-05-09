package com.creatoo.hn.services.wap;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhTraenrMapper;
import com.creatoo.hn.mapper.WhTrainMapper;
import com.creatoo.hn.mapper.WhUserMapper;
import com.creatoo.hn.model.WhTraenr;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.services.comm.CommService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class WapTrainService {
	@Autowired
	private WhTraenrMapper whTraenrMapper;
	@Autowired
	private WhUserMapper whUserMapper;
	@Autowired
	private CommService commService;
	
	/**
	 * 培训报名
	 * @param request
	 * @throws Exception
	 */
	public void saveTrain(HttpServletRequest request) throws Exception {
		String openId = request.getParameter("openId");
		Example example = new Example(WhUser.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("wxopenid", openId);
		List<WhUser> user = this.whUserMapper.selectByExample(example);
		String enruid = user.get(0).getId();
		String enrid = this.commService.getKey("wh_traenr");
        String enrtraid = request.getParameter("itemId");
        WhTraenr whTraenr = new WhTraenr();
        whTraenr.setEnrid(enrid);
        whTraenr.setEnrtraid(enrtraid);
        whTraenr.setEnrid(enrid);
        whTraenr.setEnrstepstate(0);
        whTraenr.setEnrstate("0");
		this.whTraenrMapper.insertSelective(whTraenr);
	}

}
