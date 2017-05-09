package com.creatoo.hn.services.home.art;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.ArtMapper;
import com.creatoo.hn.mapper.WhArtExhibitionMapper;
import com.creatoo.hn.mapper.WhArtMapper;
import com.creatoo.hn.mapper.WhDrscMapper;
import com.creatoo.hn.mapper.WhEntsourceMapper;
import com.creatoo.hn.mapper.WhUserArtistMapper;
import com.creatoo.hn.mapper.WhUserTroupeMapper;
import com.creatoo.hn.mapper.WhUserTroupeuserMapper;
import com.creatoo.hn.model.WhArt;
import com.creatoo.hn.model.WhArtExhibition;
import com.creatoo.hn.model.WhDrsc;
import com.creatoo.hn.model.WhEntsource;
import com.creatoo.hn.model.WhUserArtist;
import com.creatoo.hn.model.WhUserTroupe;
import com.creatoo.hn.model.WhUserTroupeuser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 前端艺术广场服务类
 * @author wangxl
 *
 */
@Service
@SuppressWarnings("rawtypes")
public class ArtService {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public ArtMapper artMapper; 
	
	@Autowired
	public WhArtMapper whArtMapper;
	
	@Autowired
	public WhUserArtistMapper artistMapper;
	
	@Autowired
	public WhEntsourceMapper sourceMapper;
	
	@Autowired
	public WhArtExhibitionMapper exhibitionMapper;
	
	@Autowired
	public WhUserTroupeMapper troupeMapper;
	
	@Autowired
	public WhUserTroupeuserMapper troupeUserMapper;
	
	@Autowired
	public WhDrscMapper drscMapper;
	
	
	/**
	 * 查询艺术展详情
	 * @param id 艺术展标识
	 * @return
	 * @throws Exception
	 */
	public WhUserTroupe srchTroupe(String id)throws Exception{
		WhUserTroupe troupe = troupeMapper.selectByPrimaryKey(id);
		return troupe;
	}
	
	/**
	 * 根据艺术团标识查询其它成员信息
	 * @param troupeid 艺术团标识
	 * @return 艺术轩成员信息
	 * @throws Exception
	 */
	public List<WhUserTroupeuser> srchTroupeUser(String troupeid)throws Exception{
		Example example = new Example(WhUserTroupeuser.class);
		example.createCriteria().andEqualTo("tutroupeid", troupeid);//艺术团标识
		return this.troupeUserMapper.selectByExample(example);
	}
	
	/**
	 * 查询艺术展详情
	 * @param id 艺术展标识
	 * @return
	 * @throws Exception
	 */
	public WhArtExhibition srchExhibition(String id)throws Exception{
		WhArtExhibition exh = exhibitionMapper.selectByPrimaryKey(id);
		return exh;
	}
	
	
	/**
	 * 查询艺术家详情
	 * @param artistid 艺术家标识
	 * @return 艺术家详情
	 * @throws Exception
	 */
	public WhUserArtist srchArtist(String artistid)throws Exception{
		WhUserArtist artist = artistMapper.selectByPrimaryKey(artistid);
		return artist;
	}
	
	/**
	 * 查询艺术家作品列表
	 * @param artistid 艺术家标识
	 * @return 艺术家详情
	 * @throws Exception
	 */
	public List<WhArt> srchArtList(String artistid)throws Exception{
		Example example = new Example(WhArt.class);
		example.createCriteria().andEqualTo("arttyp", "2016101400000035").andEqualTo("arttypid", artistid).andEqualTo("artstate", "3");
		List<WhArt> artList =this.whArtMapper.selectByExample(example);
		return artList;
	}
	
	/**
	 * 根据艺术作品类型和标识查询列表
	 * @param type 艺术作品类型(精品文化/个人作品/艺术团队)
	 * @param typeid 艺术作品类型标识
	 * @return 艺术作品类型
	 * @throws Exception
	 */
	public List<WhArt> srchArtList(String type, String typeid)throws Exception{
		Example example = new Example(WhArt.class);
		example.createCriteria().andEqualTo("arttyp", type).andEqualTo("arttypid", typeid);
		List<WhArt> artList =this.whArtMapper.selectByExample(example);
		return artList;
	}
	
	
	/**
	 * 分页查询精品文化作品
	 * @param param 条件参数
	 * @return 精品文化作品
	 * @throws Exception
	 */
	public Map<String, Object> srchjpwhzlist(Map<String, Object> param)throws Exception{
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		
		//带条件的分页查询
		Example example = new Example(WhArt.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("arttyp", "2016101400000036");
		criteria.andEqualTo("arttypid", param.get("id"));
		criteria.andEqualTo("artstate", "3");
		
		//title
		if(param.containsKey("title") && param.get("title") != null){
			//criteria.andLike("arttitle", "%"+param.get("title")+"%");
			criteria.andCondition("(arttitle like '%"+param.get("title")+"%' or artshorttitle like '%"+param.get("title")+"%')");
		}
		
		//arttyp
		if(param.containsKey("arttyp") && param.get("arttyp") != null){
			WhArtExhibition exh = new WhArtExhibition();
			exh.setExhid((String)param.get("id"));
			exh = this.exhibitionMapper.selectOne(exh);
			
			String arttyp = (String)param.get("arttyp");
			if(!arttyp.equals(exh.getExharttyp())){
				criteria.andIsNull("arttypid");
			}
		}
		
		PageHelper.startPage(page, rows);
		List<WhArt> list = this.whArtMapper.selectByExample(example);
		
		// 取分页信息
        PageInfo<WhArt> pageInfo = new PageInfo<WhArt>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}
	
	/**
	 * 分页查询精品文化展
	 * @param param 条件参数
	 * @return 分页对象
	 * @throws Exception
	 */
	public Map<String, Object> paggingJpwhz(Map<String, Object> param)throws Exception{
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.artMapper.searchArtJpwhz(param);
		
		// 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	};
	
	/**
	 * 分页查询个人作品展
	 * @param param 条件参数
	 * @return 分页对象
	 * @throws Exception
	 */
	public Map<String, Object> paggingGrzpz(Map<String, Object> param)throws Exception{
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.artMapper.searchArtGrzpz(param);
		
		// 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	};
	
	/**
	 * 艺术家个人作品列表-作品列表
	 * @param param 条件参数
	 * @return 分页对象-艺术家个人作品列表
	 * @throws Exception
	 */
	public Map<String, Object> paggingGrzpzList(Map<String, Object> param)throws Exception{
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		Example example = new Example(WhArt.class);
		example.createCriteria().andEqualTo("arttyp", "2016101400000035").andEqualTo("arttypid", param.get("id")).andEqualTo("artstate", "3");
		List<WhArt> list = this.whArtMapper.selectByExample(example);
		
		// 取分页信息
        PageInfo<WhArt> pageInfo = new PageInfo<WhArt>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	};
	
	/**
	 * 查询艺术作品详情
	 * @param id 艺术作品标识
	 * @return
	 * @throws Exception
	 */
	public WhArt srchWhartInfo(String id)throws Exception{
		return this.whArtMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 根据标签IDs查询标签列表
	 * @param tags ID列表
	 * @return
	 * @throws Exception
	 */
	public List<Map> srchTags(String tags)throws Exception{
		List<Map> tagLst = new ArrayList<Map>();
		if(tags != null && !"".equals(tags)){
			String[] idArray = tags.split(",");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("idArray", idArray);
			tagLst = this.artMapper.searchArtTags(param);
		}
		return tagLst;
	}
	
	/**
	 * 根据标签IDs查询关键字列表
	 * @param keys ID列表
	 * @return
	 * @throws Exception
	 */
	public List<Map> srchKeys(String keys)throws Exception{
		List<Map> keyLst = new ArrayList<Map>();
		if(keys != null && !"".equals(keys)){
			String[] idArray = keys.split(",");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("idArray", idArray);
			keyLst = this.artMapper.searchArtKeys(param);
		}
		return keyLst;
	}
	
	
	/**
	 * 查询艺术作品的图片/视频 /音频/图片类型
	 * @param artid 作品标识
	 * @param type 视频/音频/图片
	 * @return
	 * @throws Exception
	 */
	public List<WhEntsource> srchWhartSrc(String artid, String type, String reftype)throws Exception{
		Example example = new Example(WhEntsource.class);
		example.createCriteria().andEqualTo("enttype", type).andEqualTo("reftype", reftype).andEqualTo("refid", artid);
		List<WhEntsource> sourceList = this.sourceMapper.selectByExample(example);
		return sourceList;
	}
	
	/**
	 * 分页查询艺术团队
	 * @param param 条件参数
	 * @return 分页对象
	 * @throws Exception
	 */
	public Map<String, Object> paggingYstd(Map<String, Object> param)throws Exception{
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.artMapper.searchArtYstd(param);
		
		// 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}

	public Object getPreArt(String id, String arttyp, String arttypid) {
		//申明一个对象返回数据
		WhArt next = null;
		
		//查询指定的艺术作品
		WhArt whart = null;
		Example example = new Example(WhArt.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("artstate", 3);//已发布
		criteria.andEqualTo("arttyp", arttyp);//个人文化展
		criteria.andEqualTo("artid", id);//id
		criteria.andEqualTo("arttypid", arttypid);//艺术父类型
		List<WhArt> list = this.whArtMapper.selectByExample(example);
		if(list != null && list.size() > 0){
			whart = list.get(0);
		}
		
		//获取下一篇 对象不为空
		if(whart != null){
			//得到对象的时间
			Date date = whart.getArtcrttime();
			
			//查询同一时间发布的 分页为1行一条 当前时间 id 排序
			PageHelper.startPage(1, 1, "artcrttime, artid");
			Example eap = new Example(WhArt.class);
			Criteria cri = eap.createCriteria();
			cri.andEqualTo("artstate", 3);//已发布
			cri.andEqualTo("arttyp", arttyp);//个人文化展
			cri.andEqualTo("arttypid", arttypid);//艺术父类型
			cri.andEqualTo("artcrttime", date);
			cri.andGreaterThan("artid", id);
			List<WhArt> eaplist = this.whArtMapper.selectByExample(eap);
			//分页插件
			PageInfo<WhArt> pi1 = new PageInfo<WhArt>(eaplist);
			List<WhArt> rtnLst1 = pi1.getList();
			if(rtnLst1 != null && rtnLst1.size() == 1){
				next = rtnLst1.get(0);
			}else{//不同时间段的
				//查询指定时间点之后的一条记录
				PageHelper.startPage(1, 1, "artcrttime, artid");
				
				Example example2 = new Example(WhArt.class);
				Criteria criteria2 = example2.createCriteria();
				criteria2.andEqualTo("artstate", 3);//已发布
				criteria2.andEqualTo("arttyp", arttyp);//
				criteria2.andEqualTo("arttypid", arttypid);//艺术父类型
				criteria2.andGreaterThan("artcrttime", date);
				List<WhArt> list2 = this.whArtMapper.selectByExample(example2);
				PageInfo<WhArt> pageInfo = new PageInfo<WhArt>(list2);
				List<WhArt> resultList = pageInfo.getList();
				if(resultList != null && resultList.size() == 1){
					next = resultList.get(0);
				}else{
					PageHelper.startPage(1, 1, "artcrttime, artid");
					Example example3 = new Example(WhArt.class);
					Criteria criteria3 = example3.createCriteria();
					criteria3.andEqualTo("artstate", 3);//已发布
					criteria3.andEqualTo("arttyp", arttyp);//
					criteria3.andEqualTo("arttypid", arttypid);//艺术父类型
					List<WhArt> list3 = this.whArtMapper.selectByExample(example3);
					PageInfo<WhArt> pageInfo2 = new PageInfo<WhArt>(list3);
					List<WhArt> resultList2 = pageInfo2.getList();
					if(resultList2 != null && resultList2.size() == 1){
						next = resultList2.get(0);
					}
				}
			}
		}
		return next;
	}

	public Object getNextArt(String id, String arttyp, String arttypid) {
		//申明一个对象返回数据
		WhArt next = null;
		
		//查询指定的艺术作品
		WhArt whart = null;
		Example example = new Example(WhArt.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("artstate", 3);//已发布
		criteria.andEqualTo("arttyp", arttyp);//精品文化展
		criteria.andEqualTo("arttypid", arttypid);//艺术父类型
		criteria.andEqualTo("artid", id);//id
		List<WhArt> list = this.whArtMapper.selectByExample(example);
		if(list != null && list.size() > 0){
			whart = list.get(0);
		}
		
		//获取下一篇 对象不为空
		if(whart != null){
			//得到对象的时间
			Date date = whart.getArtcrttime();
			
			//查询同一时间发布的 分页为1行一条 当前时间 id 排序
			PageHelper.startPage(1, 1, "artcrttime desc, artid desc");
			Example eap = new Example(WhArt.class);
			Criteria cri = eap.createCriteria();
			cri.andEqualTo("artstate", 3);//已发布
			cri.andEqualTo("arttyp", arttyp);//个人文化展
			cri.andEqualTo("arttypid", arttypid);//艺术父类型
			cri.andEqualTo("artcrttime", date);
			cri.andLessThan("artid", id);
			List<WhArt> eaplist = this.whArtMapper.selectByExample(eap);
			//分页插件
			PageInfo<WhArt> pi1 = new PageInfo<WhArt>(eaplist);
			List<WhArt> rtnLst1 = pi1.getList();
			if(rtnLst1 != null && rtnLst1.size() == 1){
				next = rtnLst1.get(0);
			}else{//不同时间段的
				//查询指定时间点之后的一条记录
				PageHelper.startPage(1, 1, "artcrttime desc, artid desc");
				
				Example example2 = new Example(WhArt.class);
				Criteria criteria2 = example2.createCriteria();
				criteria2.andEqualTo("artstate", 3);//已发布
				criteria2.andEqualTo("arttyp", arttyp);//
				criteria2.andEqualTo("arttypid", arttypid);//艺术父类型
				criteria2.andLessThan("artcrttime", date);
				List<WhArt> list2 = this.whArtMapper.selectByExample(example2);
				PageInfo<WhArt> pageInfo = new PageInfo<WhArt>(list2);
				List<WhArt> resultList = pageInfo.getList();
				if(resultList != null && resultList.size() == 1){
					next = resultList.get(0);
				}else{
					PageHelper.startPage(1, 1, "artcrttime desc, artid desc");
					Example example3 = new Example(WhArt.class);
					Criteria criteria3 = example3.createCriteria();
					criteria3.andEqualTo("artstate", 3);//已发布
					criteria3.andEqualTo("arttyp", arttyp);//
					criteria3.andEqualTo("arttypid", arttypid);//艺术父类型
					List<WhArt> list3 = this.whArtMapper.selectByExample(example3);
					PageInfo<WhArt> pageInfo2 = new PageInfo<WhArt>(list3);
					List<WhArt> resultList2 = pageInfo2.getList();
					if(resultList2 != null && resultList2.size() == 1){
						next = resultList2.get(0);
					}
				}
			}
		}
		
		return next;
	};
	
}