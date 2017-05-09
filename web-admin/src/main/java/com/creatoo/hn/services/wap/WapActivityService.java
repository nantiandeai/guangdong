package com.creatoo.hn.services.wap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.ActivityMapper;
import com.creatoo.hn.mapper.WhActivitybmMapper;
import com.creatoo.hn.mapper.WhActivityitmMapper;
import com.creatoo.hn.mapper.WhBrandMapper;
import com.creatoo.hn.mapper.WhCollectionMapper;
import com.creatoo.hn.mapper.WhCommentMapper;
import com.creatoo.hn.mapper.WhUserMapper;
import com.creatoo.hn.model.WhActivitybm;
import com.creatoo.hn.model.WhActivityitm;
import com.creatoo.hn.model.WhBrand;
import com.creatoo.hn.model.WhCollection;
import com.creatoo.hn.model.WhComment;
import com.creatoo.hn.model.WhUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
@Service
public class WapActivityService {
	@Autowired
	private ActivityMapper activityMapper;
	@Autowired
	private WhUserMapper whUserMapper;
	@Autowired
	private WhCollectionMapper whCollectionMapper;
	
	@Autowired
	public WhActivitybmMapper abmMapper;
	
	@Autowired
	public WhActivityitmMapper itmMapper;
	
	@Autowired
	public WhCommentMapper commMapper;
	
	@Autowired
	public WhBrandMapper brandMapper ;
	/**
	 * 保存活动预约信息
	 * @param whabm
	 * @return
	 * @throws Exception
	 */
	public Object saveActivityOrder(WhActivitybm whabm)throws Exception {
		return this.abmMapper.insert(whabm);
	}
	
	/**
	 * 根据活动id获得相应的信息
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public List<WhActivityitm> selectActitm(String itemId)throws Exception {
		Example example = new Example(WhActivityitm.class);
		example.createCriteria().andEqualTo("actvrefid", itemId);
		return this.itmMapper.selectByExample(example);
		
	}
	
	/**
	 * 根据活动id获得相应的信息
	 * @param rmuid
	 * @return
	 * @throws Exception
	 */
	public List<WhComment> selectComm(String uid)throws Exception {
		Example example = new Example(WhComment.class);
		example.createCriteria().andEqualTo("rmuid", uid);
		return this.commMapper.selectByExample(example);
		
	}
	/**
	 * 活动
	 * @return
	 */
	public List<Map> selectact(Map<String,Object> param) {
		// 分页信息
		int page = 1;
		int rows = 10;
		if (param != null && param.get("index") != null && param.get("size") != null) {
			page = Integer.parseInt((String) param.get("index"));
			rows = Integer.parseInt((String) param.get("size"));
		}
		// 带条件的分页查询
		PageHelper.startPage(page, rows);
		return this.activityMapper.selectfavact(param);
	}
	/**
	 * 品牌活动
	 * @return
	 */
	public List<Map> selectpp(Map<String,Object> param) {
		// 分页信息
		int page = 1;
		int rows = 10;
		if (param != null && param.get("index") != null && param.get("size") != null) {
			page = Integer.parseInt((String) param.get("index"));
			rows = Integer.parseInt((String) param.get("size"));
		}
		// 带条件的分页查询
		PageHelper.startPage(page, rows);
		return this.activityMapper.selectfavppact();
		
	}
	public WhUser slelctuser(String openId) {
		if("".equals(openId) || openId == null) {
			return null ;
		}
		//Example example= new Example(WhUser.class);
		//Criteria c =  example.createCriteria();
		//c.andEqualTo("wxopenid", openId);
		WhUser user = new WhUser() ;
		user.setWxopenid(openId); ;
		return this.whUserMapper.selectOne(user) ;//.selectByExample(example);
	}
	
	/**
	 * 获取品牌信息
	 * @param param
	 * @return
	 */
	public List<Map> slelctBrand(Map<String,Object> param) {
		// 分页信息
		int page = 1;
		int rows = 10;
		if (param != null && param.get("index") != null && param.get("size") != null) {
			page = Integer.parseInt((String) param.get("index"));
			rows = Integer.parseInt((String) param.get("size"));
		}
		// 带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.brandMapper.selBrandPage(param) ;
		// 取分页信息
		PageInfo<Map> pageInfo = new PageInfo<Map>(list);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> listMap = new HashMap<String, Object>();
		Map<String, Object> pager = new HashMap<String, Object>();
//		pager.put("index", pageInfo.getPageNum()) ;
//		pager.put("total", pageInfo.getTotal());
//		pager.put("count", pageInfo.getPageSize());
//		pager.put("size", pageInfo.getSize());
//		listMap.put("list", pageInfo.getList());
		//rtnMap.put("brandList", pageInfo.getList()) ;
		//rtnMap.put("pager", pager) ;
		return pageInfo.getList();
		
	}
	
	/**
	 * 点赞数量
	 * @param id
	 * @param itemId
	 * @return
	 */
	public List<WhCollection> selctwhc(String itemId) {
		Example example = new Example(WhCollection.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("cmrefid", itemId);
		c.andEqualTo("cmopttyp","2");
		return this.whCollectionMapper.selectByExample(example);
	}
	/**
	 * 收藏
	 * @param itemId
	 * @param id
	 * @return
	 */
	public WhCollection selectcc(String itemId, String id) {
		Example example = new Example(WhCollection.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("cmrefid", itemId);
		c.andEqualTo("cmuid",id);
		c.andEqualTo("cmopttyp","0");
		WhCollection collection = new WhCollection() ;
		collection.setCmrefid(itemId) ;
		collection.setCmuid(id) ;
		collection.setCmopttyp("0") ;
		return this.whCollectionMapper.selectOne(collection) ;//.selectByExample(example);
	}

}
