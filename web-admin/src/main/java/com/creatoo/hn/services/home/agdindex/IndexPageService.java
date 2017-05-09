package com.creatoo.hn.services.home.agdindex;

import com.creatoo.hn.ext.emun.EnumBizState;
import com.creatoo.hn.ext.emun.EnumDelState;
import com.creatoo.hn.ext.emun.EnumLBTClazz;
import com.creatoo.hn.ext.emun.EnumState;
import com.creatoo.hn.mapper.*;
import com.creatoo.hn.model.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * PC首页服务
 */
@Service
public class IndexPageService {
	/**
	 * 日志
	 */
	Logger log = Logger.getLogger(this.getClass());

	/**
	 * 轮播图DAO
	 */
	@Autowired
	public WhgYwiLbtMapper whgYwiLbtMapper;

	/**
	 * 资讯DAO
	 */
	@Autowired
	public WhZxColinfoMapper whZxColinfoMapper;

	/**
	 * 文化活动DAO
	 */
	@Autowired
	public WhgActActivityMapper whgActActivityMapper;

	/**
	 * 文化培训DAO
	 */
	@Autowired
	public WhgTraMapper whgTraMapper;

	/**
	 * 培训课时DAO
	 */
	@Autowired
	public WhgTraCourseMapper whgTraCourseMapper;

	/**
	 *  分馆DAO
	 */
	@Autowired
	public WhgSysCultMapper whgSysCultMapper;


	@Autowired
	private IndexPageMapper pageMapper;

	@Autowired
	private WhZxColinfoMapper zxcolinfoMapper;

	@Autowired
	private WhCfgListMapper cfgListMapper;

	/**
	 * 取轮播图
	 * @param type 轮播图类型
	 * @return 轮播图列表
	 * @throws Exception
	 */
	public List<WhgYwiLbt> findLBT(String type, int size)throws Exception{
		Example example = new Example(WhgYwiLbt.class);
		Example.Criteria c = example.createCriteria();
		c.andEqualTo("type", type);
		c.andEqualTo("state", EnumState.STATE_YES.getValue());
		example.setOrderByClause("crtdate");//按创建时间排序
		PageHelper.startPage(1, size);
		List<WhgYwiLbt> list = this.whgYwiLbtMapper.selectByExample(example);
		PageInfo<WhgYwiLbt> pageInfo = new PageInfo<WhgYwiLbt>(list);
		return pageInfo.getList();
	}

	/**
	 * 取轮播图创建时间的倒叙
	 * @param type 轮播图类型
	 * @return 轮播图列表
	 * @throws Exception
	 */
	public List<WhgYwiLbt> findLBTDesc(String type, int size)throws Exception{
		Example example = new Example(WhgYwiLbt.class);
		Example.Criteria c = example.createCriteria();
		c.andEqualTo("type", type);
		c.andEqualTo("state", EnumState.STATE_YES.getValue());
		example.setOrderByClause("crtdate desc");//按创建时间排序
		PageHelper.startPage(1, size);
		List<WhgYwiLbt> list = this.whgYwiLbtMapper.selectByExample(example);
		PageInfo<WhgYwiLbt> pageInfo = new PageInfo<WhgYwiLbt>(list);
		return pageInfo.getList();
	}

	/**
	 * 取最新发布的资讯信息
	 * @param type 资讯类型
	 * @param size 最多取几条
	 * @return 资讯列表
	 * @throws Exception
	 */
	public List<WhZxColinfo> findZX(String type, int size) throws Exception{
		List<WhZxColinfo> retList = new ArrayList<WhZxColinfo>();

		Example example = new Example(WhZxColinfo.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("clnfstata", 3);//已发布
		criteria.andEqualTo("clnftype", type);//栏目类型为政策法规
		example.setOrderByClause("clnfcrttime desc, clnfopttime desc");//发布时间排序
		PageHelper.startPage(1, size);
		List<WhZxColinfo> list = this.whZxColinfoMapper.selectByExample(example);
		PageInfo<WhZxColinfo> info = new PageInfo<WhZxColinfo>(list);
		retList = info.getList();

		return retList;
	}

	/**
	 * 取最新发布（配置）的活动
	 * @param size 取多少条
	 * @return 最新发布的活动
	 * @throws Exception
	 */
	public List<WhgActActivity> findActivity(int size)throws Exception{
		List<WhgActActivity> retList = new ArrayList<WhgActActivity>();

		Example example = new Example(WhgActActivity.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("state", EnumBizState.STATE_PUB.getValue());//已发布
		example.setOrderByClause("isrecommend desc, statemdfdate desc");//发布时间排序
		PageHelper.startPage(1, size);
		List<WhgActActivity> list = this.whgActActivityMapper.selectByExample(example);
		PageInfo<WhgActActivity> info = new PageInfo<WhgActActivity>(list);
		retList = info.getList();

		return retList;
	}

	/**
	 * 取最新发布（配置）的培训
	 * @param size 取多少条
	 * @return 新发布（配置）的培训
	 * @throws Exception
	 */
	public List<WhgTra> findTrain(int size)throws Exception{
		List<WhgTra> retList = new ArrayList<WhgTra>();

		Example example = new Example(WhgTra.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("state", EnumBizState.STATE_PUB.getValue());//已发布
		example.setOrderByClause("recommend desc, statemdfdate desc");//发布时间排序
		PageHelper.startPage(1, size);
		List<WhgTra> list = this.whgTraMapper.selectByExample(example);
		PageInfo<WhgTra> info = new PageInfo<WhgTra>(list);
		retList = info.getList();
		for(WhgTra _tra :  retList){
			String coursedesc = _tra.getCoursedesc();
			coursedesc = coursedesc.replaceAll("<[^>]*>", "");
			if(coursedesc.length() > 60){
				coursedesc = coursedesc.substring(0, 60)+"...";
			}
			_tra.setCoursedesc(coursedesc);
		}

		return retList;
	}



	/**
	 * 根据培训取培训最近开课课时
	 * @param traList 培训列表
	 * @return
	 * @throws Exception
	 */
	public Map<String, WhgTraCourse> findTrainCourse(List<WhgTra> traList)throws Exception{
		Map<String, WhgTraCourse> map = new HashMap<String, WhgTraCourse>();

		if(traList != null){
			Date now = new Date();
			for(WhgTra whgTra : traList){
				Example example = new Example(WhgTraCourse.class);
				Example.Criteria criteria = example.createCriteria();
				criteria.andEqualTo("state", EnumState.STATE_YES.getValue());//已发布
				criteria.andEqualTo("delstate", EnumDelState.STATE_DEL_NO.getValue());//未取消
				criteria.andEqualTo("traid", whgTra.getId());
				criteria.andGreaterThan("starttime", now);
				example.setOrderByClause("starttime");//时间排序
				PageHelper.startPage(1,1);
				List<WhgTraCourse> list = this.whgTraCourseMapper.selectByExample(example);
				PageInfo<WhgTraCourse> info = new PageInfo<WhgTraCourse>(list);
				List<WhgTraCourse> qryList = info.getList();
				if(qryList != null && qryList.size() > 0){
					int len = qryList.size();
					for(int i=0; i<len; i++){
						WhgTraCourse tc = qryList.get(i);
						if( (i == len-1) || tc.getStarttime().after(now) ){
							map.put(whgTra.getId(), tc);
							break;
						}
					}
				}else{
					Example exa2 = new Example(WhgTraCourse.class);
					Example.Criteria c2 = exa2.createCriteria();
					c2.andEqualTo("state", EnumState.STATE_YES.getValue());//已发布
					c2.andEqualTo("delstate", EnumDelState.STATE_DEL_NO.getValue());//未取消
					c2.andEqualTo("traid", whgTra.getId());
					c2.andLessThan("starttime", now);
					exa2.setOrderByClause("starttime desc");//时间排序
					PageHelper.startPage(1,1);
					List<WhgTraCourse> list2 = this.whgTraCourseMapper.selectByExample(exa2);
					PageInfo<WhgTraCourse> info2 = new PageInfo<WhgTraCourse>(list2);
					List<WhgTraCourse> qryList2 = info2.getList();
					if(qryList2 != null && qryList2.size() == 1){
						map.put(whgTra.getId(), qryList2.get(0));
					}else{
						WhgTraCourse tc = new WhgTraCourse();
						tc.setStarttime(now);
						map.put(whgTra.getId(), tc);
					}
				}
			}
		}

		return map;
	}

	/**
	 * 取分馆
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public List<WhgSysCult> findCult(int size)throws Exception{
		List<WhgSysCult> retList = new ArrayList<WhgSysCult>();

		Example example = new Example(WhgSysCult.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("state", EnumState.STATE_YES.getValue());//已发布
		example.setOrderByClause("idx");//发布时间排序
		PageHelper.startPage(1, size);
		List<WhgSysCult> list = this.whgSysCultMapper.selectByExample(example);
		PageInfo<WhgSysCult> info = new PageInfo<WhgSysCult>(list);
		retList = info.getList();

		return retList;
	}

	/** 查首页广告图
	 * @return
	 */
	public List<Object> selectBannerList(String venueid) throws Exception{
		List<Object> list = pageMapper.selectBannerList(venueid);
		return list;
	}

	/** 查首页活动配置
	 * @param venueid
	 * @return
	 * @throws Exception
	 */
	public List<Object> selectWhhdList(String venueid) throws Exception{
		return pageMapper.selectWhhdList(venueid);
	}

	/** 查首页培训
	 * @param venueid
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public List<Object> selectPxyzList(String venueid, int size) throws Exception{
		PageHelper.startPage(1, size);
		List<Object> list = pageMapper.selectPxyzList(venueid);
		return list;
	}

	/** 查首页非遗配置
	 * @param venueid
	 * @return
	 * @throws Exception
	 */
	public List<Object> selectFeiyiList(String venueid) throws Exception{
		PageHelper.startPage(1, 10);
		List<Object> list = pageMapper.selectFeiyiList(venueid);
		PageInfo info = new PageInfo<>(list);
		return info.getList();
	}

	/**
	 * 获取动态资讯信息
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<WhZxColinfo> selectZxinfo(String type,int size) throws Exception{
		//分页查询政策法规
		Example example = new Example(WhZxColinfo.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("clnfstata", 3);//已发布
		criteria.andEqualTo("clnftype", type);//栏目类型为政策法规
		example.setOrderByClause("clnfopttime desc, clnfid desc");//发布时间排序
		PageHelper.startPage(1, size);
		List<WhZxColinfo> list = this.zxcolinfoMapper.selectByExample(example);
		PageInfo info = new PageInfo<>(list);
		return info.getList();
	}

	/***
	 * 2016120500000001
	 * @param type
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public List<WhCfgList> selectSmallAdv(String type,String type2,int size) throws Exception{
		Example example = new Example(WhCfgList.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("cfgpagetype","2016101900000001");
		criteria.andEqualTo("cfgenttype",type);
		if(type2 != null){
			//criteria.andEqualTo("cfgentclazz",type2);
			criteria.andIn("cfgentclazz", Arrays.asList(type2.split(",")));
			example.orderBy("cfgshowidx").asc();
		}
		criteria.andEqualTo("cfgstate",1);
		return this.cfgListMapper.selectByExample(example);
	}

	/**
	 * 首页 文化联盟
	 * @return
	 */
	public List<Object> selectVenue()throws Exception{
		return this.pageMapper.selectVenue();
	}

	/**
	 * 首页 培训
	 * @return
	 * @throws Exception
	 */
	public List<WhCfgList> selectCfgList4peixun() throws Exception{
		WhCfgList record = new WhCfgList();
		record.setCfgpagetype("2016101900000001");
		record.setCfgenttype("2016101400000051");
		record.setCfgstate(1);

		Example example = new Example(WhCfgList.class);
		example.createCriteria().andEqualTo(record);
		example.orderBy("cfgshowidx");
		return this.cfgListMapper.selectByExampleAndRowBounds(example, new RowBounds(0,3));
	}

	/**
	 * 静态化首页
	 * @throws Exception
	 */
	public void staticIndex(HttpServletRequest request)throws Exception{
		System.out.println( "========================START" );
		BufferedReader rd = null;
		try {
			String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/home";
			URL uri = new URL(url);
			HttpURLConnection httpconn = (HttpURLConnection) uri.openConnection();
			rd = new BufferedReader(new InputStreamReader(httpconn.getInputStream()));

			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = rd.readLine()) != null) {
				sb.append(line + NL);
			}
			rd.close();

			String content = sb.toString();

			String path = request.getServletContext().getResource("/").getFile();
			File indexFile = new File(path, "index.html");
			if(!indexFile.exists()){
				indexFile.createNewFile();
			}
			FileUtils.writeStringToFile(indexFile, content, "UTF-8");
		}catch (Exception e){
			throw e;
		}finally {
			if(rd != null){
				rd.close();
			}
		}
		System.out.println( "========================END" );
	}
}
