package com.creatoo.hn.services.admin.volunteer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhEntsourceMapper;
import com.creatoo.hn.mapper.WhZyhdMapper;
import com.creatoo.hn.model.WhEntsource;
import com.creatoo.hn.model.WhZyhd;
import com.creatoo.hn.model.WhZypx;
import com.creatoo.hn.utils.UploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
@Service
public class ZyhdService {

	@Autowired
	private WhZyhdMapper whZyhdMapper;
	@Autowired
	private WhEntsourceMapper whEntsourceMapper;

	/**
	 * 根据ID查询文化志愿活动
	 * @param id 文化志愿活动主键
	 * @return 文化志愿活动
	 * @throws Exception
	 */
	public WhZyhd getWhgzyhd(String id)throws Exception{
		WhZyhd zyhd = new WhZyhd();
		zyhd.setZyhdid(id);
		return this.whZyhdMapper.selectOne(zyhd);
	}
	
	/**
	 * 分页查询
	 * @param param
	 * @return
	 */
	public Map<String, Object> findzyhd(Map<String, Object> param)throws Exception {

		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.whZyhdMapper.findzyhd(param);
		
		// 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	
	}

	/**
	 * 更新志愿活动信息
	 * @param whZyhd
	 * @throws Exception
	 */
	public void upZyhd(WhZyhd whZyhd)throws Exception {
		this.whZyhdMapper.updateByPrimaryKeySelective(whZyhd);
		
	}

	/**
	 * 添加志愿活动信息
	 * @param whZyhd
	 * @throws Exception
	 */
	public void addZyhd(WhZyhd whZyhd)throws Exception {
		this.whZyhdMapper.insertSelective(whZyhd);
		
	}

	/**
	 * 删除志愿活动
	 * @param uploadPath
	 * @param zyhdid
	 */
	public void deleteZyhd(String uploadPath, String zyhdid)throws Exception {

		WhZyhd whZyhd = this.whZyhdMapper.selectByPrimaryKey(zyhdid);
		String zyhdpic = whZyhd.getZyhdpic();
		String zyhdbigpic = whZyhd.getZyhdbigpic();
		this.whZyhdMapper.deleteByPrimaryKey(zyhdid);
		UploadUtil.delUploadFile(uploadPath, zyhdpic);
		UploadUtil.delUploadFile(uploadPath, zyhdbigpic);
	}

	/**
	 * 审核志愿活动
	 * @param zyhdid
	 * @param fromstate
	 * @param tostate
	 * @return
	 */
	public Object checkZyhd(String zyhdid, int fromstate, int tostate)throws Exception {
		WhZyhd whZyhd = new WhZyhd();
		whZyhd.setZyhdstate(tostate);
		whZyhd.setZyhdopttime(new Date());
		Example example = new Example(WhZyhd.class);
		example.createCriteria().andEqualTo("zyhdid", zyhdid).andEqualTo("zyhdstate", fromstate);
		this.whZyhdMapper.updateByExampleSelective(whZyhd, example);
		return "操作成功";
	}

	/**
	 * 批量审核志愿活动
	 * @param zyhdids
	 * @param fromstate
	 * @param tostate
	 * @return
	 */
	public Object checkAllZyhd(String zyhdids, int fromstate, int tostate)throws Exception {
		List<String> list = new ArrayList<String>();
		String[] hdid = zyhdids.split(",");
		for (int i = 0; i < hdid.length; i++) {
			list.add(hdid[i]);
		}
		WhZyhd whZyhd = new WhZyhd();
		whZyhd.setZyhdstate(tostate);
		whZyhd.setZyhdopttime(new Date());
		Example example = new Example(WhZyhd.class);
		example.createCriteria().andIn("zyhdid", list).andEqualTo("zyhdstate", fromstate);
		this.whZyhdMapper.updateByExampleSelective(whZyhd, example);
		return "操作成功";
	}

	/**
	 * 能否进行审核
	 * @param zyhdid
	 */
	public int canCheck(String zyhdid)throws Exception {
		Example example = new Example(WhEntsource.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("refid", zyhdid);
		return this.whEntsourceMapper.selectCountByExample(example);
		
	}

	/**
	 * 能否批量审核
	 * @param zyhdids
	 * @return
	 */
	public Object canCheckAll(String zyhdids) {
		List<String> list = new ArrayList<String>();
		String[] hdid = zyhdids.split(",");
		for (int i = 0; i < hdid.length; i++) {
			list.add(hdid[i]);
		}
		Example example = new Example(WhEntsource.class);
		Criteria c = example.createCriteria();
		c.andIn("refid", list);
		return this.whEntsourceMapper.selectCountByExample(example);
		
	}

}
