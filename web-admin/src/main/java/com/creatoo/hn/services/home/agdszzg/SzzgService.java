package com.creatoo.hn.services.home.agdszzg;

import java.util.*;

import com.creatoo.hn.ext.emun.EnumState;
import com.creatoo.hn.ext.emun.EnumTypeClazz;
import com.creatoo.hn.mapper.*;
import com.creatoo.hn.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class SzzgService {
	
	@Autowired
	private WhArtExhibitionMapper whArtExhibitionMapper;
	@Autowired
	public ArtMapper artMapper;
	@Autowired
	public WhArtMapper whArtMapper;
	@Autowired
	private WhCfgAdvMapper whCfgAdvMapper;
	@Autowired
	private WhgExhMapper whgExhMapper;
	@Autowired
	private WhgExhArtMapper whgExhArtMapper;
	@Autowired
	private WhgComResourceMapper whgComResourceMapper;

	/**
	 * 查询图片音视频资源
	 * @param refid
	 * @return {pic:[], vod:[], aud:[]}
	 * @throws Exception
	 */
	public Map<String, List<WhgComResource>> findCommResource(String refid)throws Exception{
		Map<String, List<WhgComResource>> map = new HashMap<String, List<WhgComResource>>();
		List<WhgComResource> _lst_pic = new ArrayList<WhgComResource>();
		List<WhgComResource> _lst_vod = new ArrayList<WhgComResource>();
		List<WhgComResource> _lst_aud = new ArrayList<WhgComResource>();

		Example example = new Example(WhgComResource.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("reftype", "14");//艺术作品分类
		c.andEqualTo("refid", refid);
		example.setOrderByClause("crtdate desc");
		List<WhgComResource> list = this.whgComResourceMapper.selectByExample(example);
		if(list != null){
			for(WhgComResource wcr : list){
				if("1".equals(wcr.getEnttype())){//图片
					_lst_pic.add(wcr);
				}else if("2".equals(wcr.getEnttype())){//视频
					_lst_vod.add(wcr);
				}else if("3".equals(wcr.getEnttype())){//音频
					_lst_aud.add(wcr);
				}
			}
		}
		map.put("pic", _lst_pic);
		map.put("vod", _lst_vod);
		map.put("aud", _lst_aud);
		return map;
	}

	/**
	 * 后一个作品
	 * @param id
	 * @param arttypid
	 * @return
	 */
	public Object getNextArt(String id, String arttypid) {
		//申明一个对象返回数据
		WhgExhArt next = null;

		//查询指定的艺术作品
		WhgExhArt whart = null;
		Example example = new Example(WhgExhArt.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("artstate", EnumState.STATE_YES.getValue());//已发布
		criteria.andEqualTo("artexhid", arttypid);//艺术父类型
		criteria.andEqualTo("artid", id);//id
		List<WhgExhArt> list = this.whgExhArtMapper.selectByExample(example);
		if(list != null && list.size() > 0){
			whart = list.get(0);
		}

		//获取下一篇 对象不为空
		if(whart != null){
			//得到对象的时间
			Date date = whart.getArtcrttime();
			Integer artIdx = whart.getArtidx();

			//查询同一时间发布的 分页为1行一条 当前时间 id 排序
			Example eap = new Example(WhgExhArt.class);
			Criteria cri = eap.createCriteria();
			cri.andEqualTo("artstate", EnumState.STATE_YES.getValue());//已发布
			cri.andEqualTo("artexhid", arttypid);//艺术父类型
			cri.andGreaterThanOrEqualTo("artidx", artIdx);
			cri.andLessThanOrEqualTo("artcrttime", date);
			eap.setOrderByClause("artidx, artcrttime desc");
			PageHelper.startPage(1, 2);
			List<WhgExhArt> eaplist = this.whgExhArtMapper.selectByExample(eap);
			//分页插件
			PageInfo<WhgExhArt> pi1 = new PageInfo<WhgExhArt>(eaplist);
			List<WhgExhArt> rtnLst1 = pi1.getList();
			if(rtnLst1 != null && rtnLst1.size() > 0){
				for(WhgExhArt tart : rtnLst1){
					if(!tart.getArtid().equals(id)){
						next = tart;
					}
				}
			}
		}

		return next;
	}

	/**
	 * 前一个作品
	 * @param id
	 * @param arttypid
	 * @return
	 */
	public Object getPreArt(String id, String arttypid) {
		//申明一个对象返回数据
		WhgExhArt next = null;

		//查询指定的艺术作品
		WhgExhArt whart = null;
		Example example = new Example(WhgExhArt.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("artstate", EnumState.STATE_YES.getValue());//已发布
		criteria.andEqualTo("artid", id);//id
		criteria.andEqualTo("artexhid", arttypid);//展览ID
		List<WhgExhArt> list = this.whgExhArtMapper.selectByExample(example);
		if(list != null && list.size() > 0){
			whart = list.get(0);
		}

		//获取下一篇 对象不为空
		if(whart != null){
			//得到对象的时间
			Date date = whart.getArtcrttime();
			Integer whgIdx = whart.getArtidx();

			//查询同一时间发布的 分页为1行一条 当前时间 id 排序
			Example eap = new Example(WhgExhArt.class);
			Criteria cri = eap.createCriteria();
			cri.andEqualTo("artstate", EnumState.STATE_YES.getValue());//已发布
			cri.andEqualTo("artexhid", arttypid);//艺术父类型
			cri.andLessThanOrEqualTo("artidx", whgIdx);
			cri.andGreaterThanOrEqualTo("artcrttime", date);
			eap.setOrderByClause("artidx desc, artcrttime");
			PageHelper.startPage(1, 2);
			List<WhgExhArt> eaplist = this.whgExhArtMapper.selectByExample(eap);
			//分页插件
			PageInfo<WhgExhArt> pi1 = new PageInfo<WhgExhArt>(eaplist);
			List<WhgExhArt> rtnLst1 = pi1.getList();
			if(rtnLst1 != null && rtnLst1.size() > 0){
				for(WhgExhArt tart : rtnLst1){
					if(!tart.getArtid().equals(id)){
						next = tart;
					}
				}
			}
		}
		return next;
	}

	/**
	 * 分页查询数据展览作品信息
	 * @param param 请求参数
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> paggingSzzgArt(Map<String, Object> param)throws Exception{
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));

		//带条件的分页查询
		Example example = new Example(WhgExhArt.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("artstate", EnumState.STATE_YES.getValue());
		c.andEqualTo("artexhid", (String)param.get("id"));
		example.setOrderByClause("artidx, artcrttime desc");

		/*String arttype = (String) param.get("arttype");
		if(arttype != null && !arttype.isEmpty()){
			c.andLike("exharttyp", "%"+arttype+"%");
		}*/
		String title = (String) param.get("title");
		if(title != null && !title.isEmpty()){
			c.andLike("arttitle", "%"+title+"%");
		}

		//查询
		PageHelper.startPage(page, rows);
		List<WhgExhArt> list = this.whgExhArtMapper.selectByExample(example);

		// 取分页信息
		PageInfo<WhgExhArt> pageInfo = new PageInfo<WhgExhArt>(list);

		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("total", pageInfo.getTotal());
		rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}

	/**
	 * 数字展览首页
	 * @return
     */
	public List<WhgExh> serchList(){

		Example example = new Example(WhgExh.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("exhstate", 1);
		example.setOrderByClause("exhstime desc");
		List<WhgExh> list = whgExhMapper.selectByExample(example);
		for (WhgExh whgExh : list) {
			String exhDesc = whgExh.getExhdesc();
			exhDesc = exhDesc.replaceAll("<[^>]*>", "");
			whgExh.setExhdesc(exhDesc);
		}

		return list;
	}
	/**
	 * 分页查询数据展览信息
	 * @param param 请求参数
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> paggingSzzg(Map<String, Object> param)throws Exception{
		//分页信息
		int page = 1;
		int rows = 10;
		if(param != null && param.get("page") != null && param.get("rows") != null){
			page = Integer.parseInt((String)param.get("page"));
			rows = Integer.parseInt((String)param.get("rows"));
		}
		List<WhgExh> retList = new ArrayList<>();
		//带条件的分页查询
		Example example = new Example(WhgExh.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("exhstate", EnumState.STATE_YES.getValue());

		String arttype = (String) param.get("arttyp");
		if(arttype != null && !arttype.isEmpty()){
			c.andLike("exharttyp", "%"+arttype+"%");
		}
		String title = (String) param.get("title");
		if(title != null && !title.isEmpty()){
			c.andLike("exhtitle", "%"+title+"%");
		}
		example.setOrderByClause("exhstime desc");

		//查询
		PageHelper.startPage(page, rows);
		List<WhgExh> list = this.whgExhMapper.selectByExample(example);

		PageInfo<WhgExh> info = new PageInfo<>(list);
		retList = info.getList();
		for (WhgExh whgExh : retList) {
			String exhDesc = whgExh.getExhdesc();
			exhDesc = exhDesc.replaceAll("<[^>]*>", "");
			whgExh.setExhdesc(exhDesc);
		}

		// 取分页信息
		PageInfo<WhgExh> pageInfo = new PageInfo<>(retList);

		Map<String, Object> rtnMap = new HashMap<>();
		rtnMap.put("total", pageInfo.getTotal());
		rtnMap.put("rows", pageInfo.getList());
		rtnMap.put("pageSize", pageInfo.getPageSize());
		rtnMap.put("page", page);
		return rtnMap;
	}

	/**
	 * 查询艺术展览信息
	 * @param id 文字展览主键
	 * @return 展览信息
	 * @throws Exception
	 */
	public WhgExh t_srchOne(String id)throws Exception{
		return this.whgExhMapper.selectByPrimaryKey(id);
	}

	/**
	 * 查询艺术展览作品信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public WhgExhArt t_srchOne_art(String id)throws Exception{
		return this.whgExhArtMapper.selectByPrimaryKey(id);
	}

	/**
	 * 查询艺术展览作品信息
	 * @param exhid
	 * @return
	 * @throws Exception
	 */
	public List<WhgExhArt> srchOne_art(String exhid)throws Exception{
		Example example = new Example(WhgExhArt.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("artexhid",exhid);
		c.andEqualTo("artstate",1);//1启用状态
		return this.whgExhArtMapper.selectByExample(example);

	}
	
	public Map<String, Object> srchjpwhzlist(Map<String, Object> param,String arttypid) {
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		Example example = new Example(WhArt.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("arttyp", "2016101400000036");
		if (arttypid !=null) {
			criteria.andEqualTo("arttypid", arttypid);
		}
	
		criteria.andEqualTo("artstate", "3");
		if(param.containsKey("title") && param.get("title") != null){
			//criteria.andLike("arttitle", "%"+param.get("title")+"%");
			criteria.andCondition("(arttitle like '%"+param.get("title")+"%' or artshorttitle like '%"+param.get("title")+"%')");
		}
		List<WhArt> list = this.whArtMapper.selectByExample(example);
		
		// 取分页信息
        PageInfo<WhArt> pageInfo = new PageInfo<WhArt>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}

	/**
	 * 查询广告
	 * @param type
	 * @return
	 */
	public List<WhCfgAdv> selectadv(String type) {
		Example example = new Example(WhCfgAdv.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("cfgadvpagetype",type);
		criteria.andEqualTo("cfgadvstate",1);
		example.setOrderByClause("cfgadvidx");
		return this.whCfgAdvMapper.selectByExample(example);
	}
}
