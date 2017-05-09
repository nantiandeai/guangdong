package com.creatoo.hn.services.admin.venue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhMgrMapper;
import com.creatoo.hn.mapper.WhSubvenueMapper;
import com.creatoo.hn.model.WhMgr;
import com.creatoo.hn.model.WhSubvenue;
import com.creatoo.hn.utils.UploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class SubService {

	@Autowired
	private WhSubvenueMapper whSubvenueMapper;
	@SuppressWarnings("unused")
	@Autowired
	private WhMgrMapper whMgrMapper;
	/**
	 * 找到子馆信息
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> findVenue(HttpSession session,Map<String, Object> param) {
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		WhMgr whMgr = (WhMgr) session.getAttribute("user");
		String subpid = "";
		if (whMgr.getVenueid() != null) {
			subpid = whMgr.getVenueid();
		}else{
			subpid = "0";
		}
		
		param.put("subpid", subpid);
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.whSubvenueMapper.selSub(param);
		
		// 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap; 
	}
	/**
	 * 更新子馆信息
	 * @param whSubvenue
	 */
	public void updateSub(WhSubvenue whSubvenue)throws Exception {
		
		this.whSubvenueMapper.updateByPrimaryKeySelective(whSubvenue);
		
	}
	/**
	 * 添加子馆信息
	 * @param whSubvenue
	 */
	public void addSub(HttpSession session,WhSubvenue whSubvenue)throws Exception {
		WhMgr whMgr = (WhMgr) session.getAttribute("user");
		if (whMgr.getVenueid() == null) {
			whSubvenue.setSubpid("0");
		}else{
			whSubvenue.setSubpid(whMgr.getVenueid());
		}
		this.whSubvenueMapper.insertSelective(whSubvenue);
		
	}
	
	/**
	 * 删除志愿培训
	 * @param uploadPath
	 * @param zypxid
	 */
	public void deleteZypx(String uploadPath, String subid)throws Exception {
		WhSubvenue whSubvenue = this.whSubvenueMapper.selectByPrimaryKey(subid);
		String subpic = whSubvenue.getSubpic();
		String subbigpic = whSubvenue.getSubbigpic();
		this.whSubvenueMapper.deleteByPrimaryKey(subid);
		UploadUtil.delUploadFile(uploadPath, subpic);
		UploadUtil.delUploadFile(uploadPath, subbigpic);
	}
	/**
	 * 审核或者发布等操作
	 * @param traid
	 * @param fromstate
	 * @param tostate
	 * @param iss
	 * @return
	 */
	public Object checkTeacher(String subid, int fromstate, int tostate) {
		
		WhSubvenue whSubvenue = new WhSubvenue();
		whSubvenue.setSubstate(tostate);
		whSubvenue.setSubopttime(new Date());
		Example example = new Example(WhSubvenue.class);
		example.createCriteria().andEqualTo("subid", subid).andEqualTo("substate", fromstate);
		this.whSubvenueMapper.updateByExampleSelective(whSubvenue, example);
		return "操作成功";
	}

	/**
	 * 批量审核或者发布等操作
	 * @param traid
	 * @param fromstate
	 * @param tostate
	 * @return
	 */
	public Object checkAllTeacher(String subids, int fromstate, int tostate) {
		List<String> list = new ArrayList<String>();
		String[] pxid = subids.split(",");
		for (int i = 0; i < pxid.length; i++) {
			list.add(pxid[i]);
		}
		WhSubvenue whSubvenue = new WhSubvenue();
		whSubvenue.setSubstate(tostate);
		whSubvenue.setSubopttime(new Date());
		Example example = new Example(WhSubvenue.class);
		example.createCriteria().andIn("subid", list).andEqualTo("substate", fromstate);
		this.whSubvenueMapper.updateByExampleSelective(whSubvenue, example);
		return "操作成功";
	}


}
