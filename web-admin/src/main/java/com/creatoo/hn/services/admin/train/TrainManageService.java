package com.creatoo.hn.services.admin.train;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creatoo.hn.mapper.WhCfgListMapper;
import com.creatoo.hn.mapper.WhTrainMapper;
import com.creatoo.hn.mapper.WhTraintplMapper;
import com.creatoo.hn.mapper.WhTraitmtimeMapper;
import com.creatoo.hn.mapper.WhUserTeacherMapper;
import com.creatoo.hn.model.WhCfgList;
import com.creatoo.hn.model.WhTrain;
import com.creatoo.hn.model.WhTraintpl;
import com.creatoo.hn.model.WhTraitmtime;
import com.creatoo.hn.model.WhUserTeacher;
import com.creatoo.hn.utils.UploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
@Service
public class TrainManageService {
	@Autowired
	private WhTrainMapper whTrainMapper;
	@Autowired
	private WhTraintplMapper whTraintplMapper;
	@Autowired
	private WhTraitmtimeMapper whTraitmtimeMapper;
	@Autowired
	private WhCfgListMapper whcfgListMapper;
	@Autowired
	private WhUserTeacherMapper whUserTeacherMapper;
	
	@SuppressWarnings("rawtypes")
	public Map<String, Object> paggingTrainitm(Map<String, Object> param) {
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.whTrainMapper.selectTrain(param);
		
		// 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}

	/**
	 * 添加数据
	 * @param whTrain
	 * @throws Exception
	 */
	public void addTrain(WhTrain whTrain)throws Exception {
			this.whTrainMapper.insertSelective(whTrain);
	}

	/**
	 * 更新数据
	 * @param whTrain
	 * @throws Exception
	 */
	public void updWhtra(WhTrain whTrain)throws Exception {
		this.whTrainMapper.updateByPrimaryKeySelective(whTrain);
		
	}
	/**
	 * 审核
	 * @param traid
	 * @param whTrain
	 * @throws Exception
	 */
	public Object checkWhtra(String traids, int fromstate, int tostate,String iss)throws Exception {
		List<String> list = new ArrayList<String>();
		String[] tra = traids.split(",");
		for (int i = 0; i < tra.length; i++) {
			list.add(tra[i]);
		}
		if(tostate == 2 && fromstate == 3){
			Example example2 =new Example(WhCfgList.class);
			Criteria c2 = example2.createCriteria();
			c2.andEqualTo("cfgpagetype","2016102800000002");
			c2.andEqualTo("cfgenttype","2016101400000051");
			c2.andIn("cfgshowid",list);
			int listcount2=this.whcfgListMapper.selectCountByExample(example2); 
			if(listcount2>0){
				return "批量活动中含有页面元素配置,不允许取消发布！";
			}
		}
		if(iss != null && !"".equals(iss)){
			Example example2 =new Example(WhTraitmtime.class);
			example2.or().andIn("traitmid", list);
			int	listcount=this.whTraitmtimeMapper.selectCountByExample(example2);
			if(listcount != list.size()){
				return "没有设置课程表,不允许送审！";
			}
		}
		WhTrain whTrain = new WhTrain();
		whTrain.setTrastate(tostate);
		whTrain.setTrastateopttime(new Date());
		Example example = new Example(WhTrain.class);
		example.createCriteria().andIn("traid", list).andEqualTo("trastate", fromstate);
		this.whTrainMapper.updateByExampleSelective(whTrain, example);
		return "操作成功";
	}
	/**
	 * 删除培训数据
	 * @param uploadPath
	 * @param tratplid
	 */
	public void deleteWhtra(String uploadPath, String traid)throws Exception {
		WhTrain whTrain = this.whTrainMapper.selectByPrimaryKey(traid);
		String trapic = whTrain.getTrapic();
		String trabigpic = whTrain.getTrabigpic();
		String trapersonfile = whTrain.getTrapersonfile();
		String trateamfile = whTrain.getTrateamfile();
		this.whTrainMapper.deleteByPrimaryKey(traid);
		UploadUtil.delUploadFile(uploadPath, trapic);
		UploadUtil.delUploadFile(uploadPath, trabigpic);
		UploadUtil.delUploadFile(uploadPath, trapersonfile);
		UploadUtil.delUploadFile(uploadPath, trateamfile);
		
	}
	/**
	 * 单个审核发布
	 * @param traid
	 * @param trastate
	 */
	public void pass(String traid, Integer trastate) {
		WhTrain whTrain = this.whTrainMapper.selectByPrimaryKey(traid);
		whTrain.setTrastate(trastate+1);
		whTrain.setTrastateopttime(new Date());
		this.whTrainMapper.updateByPrimaryKeySelective(whTrain);
	}
	/**
	 * 单个取消发布 -1
	 * @param traid
	 * @param trastate
	 */
	public void back(String traid, Integer trastate) {
		WhTrain whTrain = this.whTrainMapper.selectByPrimaryKey(traid);
		whTrain.setTrastate(trastate-1);
		whTrain.setTrastateopttime(new Date());
		this.whTrainMapper.updateByPrimaryKeySelective(whTrain);
	}
	/**
	 * 上首页
	 * @param whTrain
	 */
	public void goHomePage(WhTrain whTrain) {
		this.whTrainMapper.updateByPrimaryKeySelective(whTrain);
	}
	/**
	 * 找到培训标题
	 * @return
	 */
	public Object getTitle() {
		return this.whTrainMapper.selectAll();
	}
	/**
	 * 找到培训模板标题
	 * @return
	 */
	public Object getTraTplTitle() {
		return this.whTraintplMapper.selectAll();
	}
	/**
	 * 根据模板ID拿到模板
	 * @param tratplid
	 * @return
	 */
	public Object getTratpl(String tratplid) {
		return this.whTraintplMapper.selectByPrimaryKey(tratplid);
	}
	/**
	 * 页面配置不带分页的条件,条件查询
	 */
	public Object seletAll(String tratyp,String trastate) {
		Example example = new Example(WhTrain.class);
		
		Criteria Criteria = example.createCriteria();
		Criteria.andEqualTo("tratyp", tratyp);
		Criteria.andEqualTo("trastate", trastate);
		
		example.setOrderByClause("traid desc");
		List<WhTrain> tags =this.whTrainMapper.selectByExample(example);
		return tags;
	}

	/**
	 * 是否能够取消发布
	 * @param traid
	 * @return
	 */
	@RequestMapping("/ispub")
	public int isOrNo(String traid) {
		return this.whTrainMapper.isPublish(traid);
	}

	/**
	 * 判断是否能够进行送审
	 * @param traid
	 * @return
	 */
	public Object isCanCheck(String traid) {
		
		int count = this.whTrainMapper.isCanCheck(traid);
		return count;
	}
	
	/**
	 * 
	 * @param traids
	 * @return
	 */
	public Object isCanCheckAll(String traids) {
		List<String> list = new ArrayList<String>();
		String[] tra = traids.split(",");
		for (int i = 0; i < tra.length; i++) {
			list.add(tra[i]);
		}
		Example example = new Example(WhTraitmtime.class);
		example.createCriteria().andIn("traitmid", list).andEqualTo("traitmid", list);
		return this.whTraitmtimeMapper.selectByExample(example);
	}

	/**
	 * 设置为模板
	 * @param whTraintpl
	 */
	public void setTraintpl(WhTraintpl whTraintpl)throws Exception {
		
		this.whTraintplMapper.insert(whTraintpl);
	}
	/**
	 * 查找老师
	 * @return
	 */
	public Object findTeacher() {
		Example example = new Example(WhUserTeacher.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("teacherstate", 3);
		return this.whUserTeacherMapper.selectByExample(example);
	}
	

}
