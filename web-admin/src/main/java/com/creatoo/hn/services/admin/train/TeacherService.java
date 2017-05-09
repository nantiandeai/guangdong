package com.creatoo.hn.services.admin.train;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhCfgListMapper;
import com.creatoo.hn.mapper.WhTeacherMapper;
import com.creatoo.hn.mapper.WhUserTeacherMapper;
import com.creatoo.hn.model.WhCfgList;
import com.creatoo.hn.model.WhTrain;
import com.creatoo.hn.model.WhTraitmtime;
import com.creatoo.hn.model.WhUserTeacher;
import com.creatoo.hn.utils.UploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class TeacherService {
	
	@Autowired
	private WhUserTeacherMapper teacherMapper;
	
	@Autowired
	private WhCfgListMapper whCfgListMapper;

	public Map<String, Object> paggingTeacher(Map<String, Object> param)throws Exception {
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.teacherMapper.selTeacher(param);
		
		// 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}

	/**
	 * 更新培训老师
	 * @param whUserTeacher
	 */
	public void updTeacher(WhUserTeacher whUserTeacher)throws Exception {
		this.teacherMapper.updateByPrimaryKeySelective(whUserTeacher);
		
	}

	/**
	 * 添加培训老师
	 * @param whUserTeacher
	 */
	public void addTeacher(WhUserTeacher whUserTeacher)throws Exception {
		this.teacherMapper.insertSelective(whUserTeacher);
		
	}

	/**
	 * 删除培训老师
	 * @param uploadPath
	 * @param teacherid
	 */
	public void deleteTea(String uploadPath, String teacherid)throws Exception {
		WhUserTeacher whUserTeacher = this.teacherMapper.selectByPrimaryKey(teacherid);
		String teacherpic = whUserTeacher.getTeacherpic();
		this.teacherMapper.deleteByPrimaryKey(teacherid);
		UploadUtil.delUploadFile(uploadPath, teacherpic);
		
	}

	/**
	 * 审核或者发布等操作
	 * @param traid
	 * @param fromstate
	 * @param tostate
	 * @param iss
	 * @return
	 */
	public Object checkTeacher(String teacherid, int fromstate, int tostate) {
		/*if(tostate == 2 && fromstate == 3){
			Example example2 =new Example(WhCfgList.class);
			Criteria c2 = example2.createCriteria();
			c2.andEqualTo("cfgpagetype","");
			c2.andEqualTo("cfgenttype","");
			c2.andEqualTo("cfgshowid",teacherid);
			int listcount2 = this.whCfgListMapper.selectCountByExample(example2); 
			if(listcount2>0){
				return "批量活动中含有页面元素配置,不允许取消发布！";
			}
		}*/
		
		WhUserTeacher whUserTeacher = new WhUserTeacher();
		whUserTeacher.setTeacherstate(tostate);
		whUserTeacher.setTeacheropttime(new Date());
		Example example = new Example(WhUserTeacher.class);
		example.createCriteria().andEqualTo("teacherid", teacherid).andEqualTo("teacherstate", fromstate);
		this.teacherMapper.updateByExampleSelective(whUserTeacher, example);
		return "操作成功";
	}

	/**
	 * 批量审核或者发布等操作
	 * @param traid
	 * @param fromstate
	 * @param tostate
	 * @return
	 */
	public Object checkAllTeacher(String teacherids, int fromstate, int tostate) {
		List<String> list = new ArrayList<String>();
		String[] tea = teacherids.split(",");
		for (int i = 0; i < tea.length; i++) {
			list.add(tea[i]);
		}
		/*if(tostate == 2 && fromstate == 3){
			Example example2 =new Example(WhCfgList.class);
			Criteria c2 = example2.createCriteria();
			c2.andEqualTo("cfgpagetype","2016102800000002");
			c2.andEqualTo("cfgenttype","2016101400000051");
			c2.andIn("cfgshowid",list);
			int listcount2=this.whcfgListMapper.selectCountByExample(example2); 
			if(listcount2>0){
				return "批量活动中含有页面元素配置,不允许取消发布！";
			}
		}*/
		WhUserTeacher whUserTeacher = new WhUserTeacher();
		whUserTeacher.setTeacherstate(tostate);
		whUserTeacher.setTeacheropttime(new Date());
		Example example = new Example(WhUserTeacher.class);
		example.createCriteria().andIn("teacherid", list).andEqualTo("teacherstate", fromstate);
		this.teacherMapper.updateByExampleSelective(whUserTeacher, example);
		return "操作成功";
	}

}
