package com.creatoo.hn.services.admin.train;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.ActivityMapper;
import com.creatoo.hn.mapper.WhEntsourceMapper;
import com.creatoo.hn.mapper.WhTraitmMapper;
import com.creatoo.hn.model.WhEntsource;
import com.creatoo.hn.model.WhTraitm;
import com.creatoo.hn.services.comm.CommService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.WhitespaceStrippingElementMatcher;

	/**
	 * 培训service
	 * 
	 * @author wenjingqiang
	 *
	 */
	@Service
	public class TrainService {
//	@Autowired
//	private WhTraMapper whTraMapper;
	@Autowired
	private WhTraitmMapper WhTraitmMapper;
	
	@Autowired
	public CommService commService;
	@Autowired
	public WhEntsourceMapper whEntsourceMapper;
	@Autowired
	public ActivityMapper activityMapper;
	/**
	 * 查询所有培训信息
	 */
	public Object selAllTrain(int page,int rows){
		PageHelper.startPage(page, rows);
		List<HashMap> trains = this.WhTraitmMapper.selectTraitm();
		 //取分页信息
		 PageInfo<HashMap> pageInfo = new PageInfo<HashMap>(trains);
		 
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 rtnMap.put("total", pageInfo.getTotal());
		 rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}
	
	/**
	 * 删除培训信息
	 * @param traid 培训标识
	 * @param traitmid 培训批次标识
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public int delTrain( String traitmid)throws Exception	{
		return this.WhTraitmMapper.deleteByPrimaryKey(traitmid);
//		boolean canDel = true;//
//		List<HashMap> list = this.whTraMapper.selectSingleTrainItm(traitmid);
//		if(list != null && list.size() > 0){
//			canDel = false;
//		}
//		this.whTraMapper.delTrainitm(traitmid);
//		if(canDel){
//			this.whTraMapper.delTrain(traid);
//		}
	}
	
	/**
	 * 
	 * @param traitmid
	 * @return
	 */
	public Object selTraitmByid(String traitmid) throws Exception {
			
		return this.WhTraitmMapper.selectByPrimaryKey(traitmid);
	}
	
	/**
	 * 添加培训批次
	 * @param whTraitm
	 */
	public void addTrainitm(WhTraitm whTraitm) throws Exception {
		this.WhTraitmMapper.insertSelective(whTraitm);
		
	}
	/**
	 * 更新
	 * @param whTraitm
	 */
	public void updateTraitm(WhTraitm whTraitm) throws Exception {
		this.WhTraitmMapper.updateByPrimaryKeySelective(whTraitm);
		
	}
	/**
	 * 审核，状态加1
	 * @param traitmid
	 */
	public void pass(String traitmid,Integer state) {
		WhTraitm whTraitm = this.WhTraitmMapper.selectByPrimaryKey(traitmid);
		whTraitm.setState(state+1);
		this.WhTraitmMapper.updateByPrimaryKeySelective(whTraitm);
	}
	/**
	 * 打回
	 * @param traitmid
	 * @param state
	 */
	public void back(String traitmid, Integer state) {
		WhTraitm whTraitm = this.WhTraitmMapper.selectByPrimaryKey(traitmid);
		whTraitm.setState(state-1);
		this.WhTraitmMapper.updateByPrimaryKeySelective(whTraitm);
	}
	/**
	 * 查找所有的资源
	 * @param page
	 * @param rows
	 * @return
	 */
	public Object selAllRes(int page, int rows) {

		PageHelper.startPage(page, rows);
		List<WhEntsource> trains = this.whEntsourceMapper.selectAll();
		 //取分页信息
		 PageInfo<WhEntsource> pageInfo = new PageInfo<WhEntsource>(trains);
		 
		 Map<String, Object> rtnMap = new HashMap<String, Object>();
		 rtnMap.put("total", pageInfo.getTotal());
		 rtnMap.put("rows", pageInfo.getList());
		 return rtnMap;
	}
	/**
	 * 添加资源信息
	 * @param whEntsource
	 */
	public void addResource(WhEntsource whEntsource) {
		this.whEntsourceMapper.insertSelective(whEntsource);
	}
	/**
	 * 修改资源信息
	 * @param whEntsource
	 */
	public void updateRes(WhEntsource whEntsource) {
		this.whEntsourceMapper.updateByPrimaryKeySelective(whEntsource);
	}
	/**
	 * 删除资源信息
	 * @param rid
	 */
	public void delResource(String rid) {
		this.whEntsourceMapper.deleteByPrimaryKey(rid);
	}
	/**
	 * 通过传过来的培训标题找到培训ID
	 * @param tratitle
	 * @return
	 */
	public String seltraid(String tratitle) {
		
		return this.activityMapper.selTraid(tratitle);
	}
	/**
	 * 上首页及设置上首页排序值
	 * @param traitmid
	 * @return
	 */
	public void goHomePage(WhTraitm whTraitm)throws Exception {
		 this.WhTraitmMapper.updateByPrimaryKeySelective(whTraitm);
	}
	
}
