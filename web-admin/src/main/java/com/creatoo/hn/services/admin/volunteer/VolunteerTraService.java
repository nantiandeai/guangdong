package com.creatoo.hn.services.admin.volunteer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhZypxMapper;
import com.creatoo.hn.model.WhTrain;
import com.creatoo.hn.model.WhUserTeacher;
import com.creatoo.hn.model.WhZypx;
import com.creatoo.hn.model.WhgSysCult;
import com.creatoo.hn.utils.UploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class VolunteerTraService {
	@Autowired
	private WhZypxMapper whZypxMapper;

	/**
	 * 更新志愿培训
	 * @param whZypx
	 */
	public void upZypx(WhZypx whZypx)throws Exception {
		this.whZypxMapper.updateByPrimaryKeySelective(whZypx);
		
	}

	/**
	 * 添加志愿培训
	 * @param whZypx
	 */
	public void addZypx(WhZypx whZypx)throws Exception {
		this.whZypxMapper.insertSelective(whZypx);
		
	}

	
	
	/**
	 * 删除志愿培训
	 * @param uploadPath
	 * @param zypxid
	 */
	public void deleteZypx( String zypxid)throws Exception {
//		WhZypx whZypx = this.whZypxMapper.selectByPrimaryKey(zypxid);
//		String trapic = whZypx.getZypxpic();
		this.whZypxMapper.deleteByPrimaryKey(zypxid);
//		UploadUtil.delUploadFile(uploadPath, trapic);
	}

	/**
	 * 分页查询志愿培训信息
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> findVolun(Map<String, Object> param)throws Exception {
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.whZypxMapper.selectZypx(param);
		
		// 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}
	
	/**
	 * 审核或者发布等操作
	 * @param traid
	 * @param fromstate
	 * @param tostate
	 * @param iss
	 * @return
	 */
	public Object checkTeacher(String zypxid, int fromstate, int tostate) {
		WhZypx whZypx = new WhZypx();
		whZypx.setZypxstate(tostate);
		whZypx.setZypxopttime(new Date());
		Example example = new Example(WhZypx.class);
		example.createCriteria().andEqualTo("zypxid", zypxid).andEqualTo("zypxstate", fromstate);
		this.whZypxMapper.updateByExampleSelective(whZypx, example);
		return "操作成功";
	}

	/**
	 * 批量审核或者发布等操作
	 * @param traid
	 * @param fromstate
	 * @param tostate
	 * @return
	 */
	public Object checkAllTeacher(String zypxids, int fromstate, int tostate) {
		List<String> list = new ArrayList<String>();
		String[] pxid = zypxids.split(",");
		for (int i = 0; i < pxid.length; i++) {
			list.add(pxid[i]);
		}
		WhZypx whZypx = new WhZypx();
		whZypx.setZypxstate(tostate);
		whZypx.setZypxopttime(new Date());
		Example example = new Example(WhZypx.class);
		example.createCriteria().andIn("zypxid", list).andEqualTo("zypxstate", fromstate);
		this.whZypxMapper.updateByExampleSelective(whZypx, example);
		return "操作成功";
	}
	
	 /**
     * 查询单个志愿培训信息
     * @param id 主键
     * @return 志愿培训对象
     * @throws Exception
     */
    public WhZypx t_srchOne(String id)throws Exception{
    	WhZypx record = new WhZypx();
        record.setZypxid(id);
        return this.whZypxMapper.selectOne(record);
    }

}
