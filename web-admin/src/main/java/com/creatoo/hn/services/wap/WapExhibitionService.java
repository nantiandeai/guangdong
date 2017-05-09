package com.creatoo.hn.services.wap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhActivityMapper;
import com.creatoo.hn.mapper.WhArtExhibitionMapper;
import com.creatoo.hn.mapper.WhArtMapper;
import com.creatoo.hn.mapper.WhBrandMapper;
import com.creatoo.hn.mapper.WhCollectionMapper;
import com.creatoo.hn.mapper.WhCommentMapper;
import com.creatoo.hn.mapper.WhTrainMapper;
import com.creatoo.hn.mapper.WhUserMapper;
import com.creatoo.hn.mapper.WhUserTroupeMapper;
import com.creatoo.hn.mapper.WhVenueMapper;
import com.creatoo.hn.mapper.WhZxColinfoMapper;
import com.creatoo.hn.model.WhActivity;
import com.creatoo.hn.model.WhArt;
import com.creatoo.hn.model.WhArtExhibition;
import com.creatoo.hn.model.WhBrand;
import com.creatoo.hn.model.WhCollection;
import com.creatoo.hn.model.WhComment;
import com.creatoo.hn.model.WhTrain;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.model.WhUserTroupe;
import com.creatoo.hn.model.WhVenue;
import com.creatoo.hn.model.WhZxColinfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class WapExhibitionService {
	@Autowired
	private WhArtExhibitionMapper whArtExhibitionMapper;
	@Autowired
	private WhArtMapper whArtMapper;
	@Autowired
	private WhUserMapper whUserMapper;
	@Autowired
	private WhCollectionMapper whCollectionMapper;
	@Autowired
	private WhCommentMapper whCommentMapper;
	@Autowired
	private WhActivityMapper whActivityMapper;
	@Autowired
	private WhTrainMapper whTrainMapper;
	@Autowired
	private WhVenueMapper whVenueMapper;
	@Autowired
	private WhUserTroupeMapper whUserTroupeMapper;
	@Autowired
	private WhZxColinfoMapper whZxColinfoMapper;
	@Autowired
	private WhBrandMapper whBrandMapper;
	/**
	 * 文化展
	 * 
	 * @param param
	 * @return
	 */
	public List<Map> exhselect(Map<String, Object> param) {
		return this.whArtExhibitionMapper.selectcategory(param);
	}

	/**
	 * 文化展详情
	 * 
	 * @param params
	 * @return
	 */
	public Object selectdetail(Map<String, Object> params) {
		return this.whArtExhibitionMapper.selectdetail(params);
	}

	public WhArt selectinfo(String itemId) {
		return this.whArtMapper.selectByPrimaryKey(itemId);
	}
	/**
	 * 精品资源
	 * @param params
	 * @return
	 */
	public List<Map> select(Map<String, Object> params) {
		return this.whArtExhibitionMapper.selectlist(params);
	}
	/**
	 * 广告
	 * @param params
	 * @return
	 */
	public List<Map> selectadv(Map<String, Object> params) {
		return this.whArtExhibitionMapper.selectadvlist(params);
	}
	/**
	 * 查询区域字典
	 * @param params
	 * @return
	 */
	public Object selectdistrictlist() {
		return this.whArtExhibitionMapper.selectdistrict();
	}
	/**
	 * 带条件查询用户表
	 * @param openId
	 * @return
	 */
	public WhUser selectuser(String openId) {
		//Example example = new Example(WhUser.class);
		//Criteria c = example.createCriteria();
		//c.andEqualTo("wxopenid",openId);
		WhUser user = new WhUser() ;
		user.setWxopenid(openId);
		return this.whUserMapper.selectOne(user);
	}
	
	/**
	 * 保存点赞信息
	 * @param whc
	 */
	public void addwhc(WhCollection whc) {
		this.whCollectionMapper.insertSelective(whc);
	}
	
	/**
	 * 评论
	 * @param whc
	 */
	public void addwhcom(WhComment whc) {
		this.whCommentMapper.insertSelective(whc);
		
	}
	
	/**
	 * 活动
	 * @param itemId
	 * @return
	 */
	public WhActivity selecthd(String itemId) {
		return this.whActivityMapper.selectByPrimaryKey(itemId);
	}
	
	/**
	 * 培训
	 * @param itemId
	 * @return
	 */
	public WhTrain selectpx(String itemId) {
		return this.whTrainMapper.selectByPrimaryKey(itemId);
	}
	
	/**
	 * 场馆
	 * @param itemId
	 * @return
	 */
	public WhVenue selecvenue(String itemId) {
		
		return this.whVenueMapper.selectByPrimaryKey(itemId);
	}
	/**
	 *艺术作品
	 * @param itemId
	 * @return
	 */
	public WhArtExhibition selectys(String itemId) {
		return this.whArtExhibitionMapper.selectByPrimaryKey(itemId);
	}
	/**
	 * 团队
	 * @param itemId
	 * @return
	 */
	public WhUserTroupe selecttd(String itemId) {
		return this.whUserTroupeMapper.selectByPrimaryKey(itemId);
	}
	/**
	 * 馆务
	 * @param itemId
	 * @return
	 */
	public WhZxColinfo selectgw(String itemId) {
		return this.whZxColinfoMapper.selectByPrimaryKey(itemId);
	}
	
	/**
	 * 品牌活动
	 * @param itemId
	 * @return
	 */
	public WhBrand selectbrand(String itemId) {
		return this.whBrandMapper.selectByPrimaryKey(itemId);
	}
	/**
	 * 搜索
	 * @param param
	 * @return
	 */

	public List<Map> selctsearch(Map<String, Object> param) {
		return this.whArtExhibitionMapper.searchContent(param);
	}
	
	/**
	 * 查找点赞
	 * @param itemId
	 * @param openId
	 * @return
	 */
	public List<WhCollection> slectcoll(String id) {
		Example example = new Example(WhCollection.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("cmuid",id );
		return this.whCollectionMapper.selectByExample(example);
	}

	public List<WhCollection> slectcollsc(String id, String itemId) {
		Example example = new Example(WhCollection.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("cmuid",id );
		c.andEqualTo("cmrefid",itemId );
		c.andEqualTo("cmopttyp","0" );
		return this.whCollectionMapper.selectByExample(example);
	}
	/**
	 * 评论数量
	 * @param id
	 * @param itemId
	 * @return
	 */
	public List<WhComment> selectpl(String id, String itemId) {
		Example example = new Example(WhComment.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("rmuid",id );
		c.andEqualTo("rmrefid",itemId );
		c.andEqualTo("rmstate", 1 );
		return this.whCommentMapper.selectByExample(example);
	}
	
	/**
	 * 广告
	 * @param params
	 * @return
	 */
	public Map<String,Object> selectAdBanner(Map<String, Object> params) {
		// 分页信息
		int page = 1;
		int rows = 10;
		if (params != null && params.get("index") != null && params.get("size") != null) {
			page = Integer.parseInt((String) params.get("index"));
			rows = Integer.parseInt((String) params.get("size"));
		}

		// 带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.whArtExhibitionMapper.selectadBanner(params);
		// 取分页信息
		PageInfo<Map> pageInfo = new PageInfo<Map>(list);
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> listMap = new HashMap<String, Object>();
		Map<String, Object> pager = new HashMap<String, Object>();
		pager.put("index", pageInfo.getPageNum()) ;
		pager.put("total", pageInfo.getTotal());
		pager.put("count", pageInfo.getPageSize());
		pager.put("size", pageInfo.getSize());
		listMap.put("list", pageInfo.getList());
		rtnMap.put("data", listMap) ;
		rtnMap.put("pager", pager) ;
		return rtnMap ;
	}

}
