package com.creatoo.hn.services.admin.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhFeedbackMapper;
import com.creatoo.hn.model.WhFeedback;
import com.creatoo.hn.services.comm.CommService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
@Service
public class FeedService {
	
	@Autowired
	private WhFeedbackMapper whFeedbackMapper;
	@Autowired
	public CommService service;
    /**
     * 查询
     * @param param
     * @return
     */
	public Map<String, Object> selefeed(Map<String, Object> param) {
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		PageHelper.startPage(page, rows);
		Example example = new Example(WhFeedback.class);
		Criteria criteria=example.createCriteria();
		//排序
		if(param.containsKey("sort") && param.get("sort")!= null){
			StringBuffer sbf = new StringBuffer((String)param.get("sort"));
			if(param.containsKey("order") && param.get("order") !=  null){
				sbf.append(" ").append(param.get("order"));
			}
			example.setOrderByClause(sbf.toString());
		}
		//电话号码
		if(param.containsKey("feedphone") && param.get("feedphone") != null){
			String feedphone = (String)param.get("feedphone");
			if(!"".equals(feedphone.trim())){
				criteria.andLike("feedphone", "%"+feedphone.trim()+"%");
			}
		}
		//邮箱
		if(param.containsKey("feedmail") && param.get("feedmail") != null){
			String feedmail = (String)param.get("feedmail");
			if(!"".equals(feedmail.trim())){
				criteria.andLike("feedmail", "%"+feedmail.trim()+"%");
			}
		}
		
		List<WhFeedback> list = this.whFeedbackMapper.selectByExample(example);
		PageInfo<WhFeedback> pageInfo = new PageInfo<WhFeedback>(list);
	    Map<String, Object> rtnMap = new HashMap<String, Object>();
	    rtnMap.put("total", pageInfo.getTotal());
	    rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}
	/**
	 * 根据id删除
	 * @param feedid
	 */
	public void delete(String feedid) {
		int id = this.whFeedbackMapper.deleteByPrimaryKey(feedid);
		
	}
	/**
	 * 查看
	 * @param whfe
	 */
	public void upback(WhFeedback whfe) {
	   this.whFeedbackMapper.updateByPrimaryKeySelective(whfe);
	}

}
