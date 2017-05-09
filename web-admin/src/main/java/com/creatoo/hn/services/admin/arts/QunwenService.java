package com.creatoo.hn.services.admin.arts;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhArtExhibitionMapper;
import com.creatoo.hn.mapper.WhArtMapper;
import com.creatoo.hn.mapper.WhKeyMapper;
import com.creatoo.hn.mapper.WhTagMapper;
import com.creatoo.hn.mapper.WhTypMapper;
import com.creatoo.hn.mapper.WhUserArtistMapper;
import com.creatoo.hn.mapper.WhUserTroupeMapper;
import com.creatoo.hn.mapper.WhZxinfoMapper;
import com.creatoo.hn.model.WhArt;
import com.creatoo.hn.model.WhArtExhibition;
import com.creatoo.hn.model.WhKey;
import com.creatoo.hn.model.WhTag;
import com.creatoo.hn.model.WhUserArtist;
import com.creatoo.hn.model.WhUserTroupe;
import com.creatoo.hn.model.WhZxinfo;
import com.creatoo.hn.services.comm.CommService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 群文作品(资讯展示)服务类
 * @author wangxl
 * @version 20160928
 */
@Service
public class QunwenService {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public CommService commService;
	
	@Autowired
	public WhZxinfoMapper whZxinfoMapper;
	
	@Autowired
	public WhTypMapper whTypMapper;
	
	@Autowired
	public WhTagMapper whTagMapper;
	
	@Autowired
	public WhKeyMapper whKeyMapper;
	
	@Autowired
	public WhArtMapper whArtMapper;
	
	@Autowired
	public WhUserArtistMapper artistMapper;
	
	@Autowired
	public WhArtExhibitionMapper exhibitionMapper;
	
	@Autowired
	public WhUserTroupeMapper troupeMapper;
	
	/**
	 * 带条件查询资讯信息
	 * @param page 第几页
	 * @param rows 每页多少条 
	 * @throws Exception
	 */
	public Object findAllZx(int page, int rows)throws Exception{
		//带条件的分页查询
		Example example = new Example(WhZxinfo.class); 
		PageHelper.startPage(page, rows);
		List<WhZxinfo> list = this.whZxinfoMapper.selectByExample(example);
		
		// 取分页信息
        PageInfo<WhZxinfo> pageInfo = new PageInfo<WhZxinfo>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}
	
	/**
	 * 根据ID查询资讯详情
	 * @param id
	 * @throws Exception
	 */
	public WhZxinfo findZxinfoById(String id)throws Exception{
		WhZxinfo zxinfo = new WhZxinfo();
		zxinfo.setZxid(id);
		zxinfo = this.whZxinfoMapper.selectOne(zxinfo);
		return zxinfo;
	}
	
	/**
	 * 查询标签
	 * @throws Exception
	 */
	public List<WhTag> findZxinfoTags()throws Exception{
		return this.whTagMapper.selectAll();
	}
	
	/**
	 * 查询关键字
	 * @throws Exception
	 */
	public List<WhKey> findZxinfoKeys()throws Exception{
		return this.whKeyMapper.selectAll();
	}
	
	/**
	 * 添加资讯信息
	 * @throws Exception
	 */
	public void addZxinfo(WhZxinfo zxinfo)throws Exception{
		String id = zxinfo.getZxid();
		if(id != null && "".equals(id.trim())){//add
			id = this.commService.getKey("WhZxinfo");//获取主键
			zxinfo.setZxid(this.commService.getKey("WhZxinfo"));
			int rows = this.whZxinfoMapper.insert(zxinfo);
			if(rows != 1){
				throw new Exception("操作失败");
			}
		}else{//edit
			//old info
			WhZxinfo record = new WhZxinfo();
			record.setZxid(id);
			record = this.whZxinfoMapper.selectOne(record);
			
//			//合并新的info
//			com.creatoo.hn.utils.BeanUtils.merge(record, zxinfo);
//			
//			//更新到数据库
//			Example example = new Example(WhZxinfo.class);
//			example.createCriteria().andEqualTo("id", id);
//			int rows = this.whZxinfoMapper.updateByExample(record, example);
			int rows = this.whZxinfoMapper.updateByPrimaryKeySelective(zxinfo);
			if(rows != 1){
				throw new Exception("操作失败");
			}
		}
	}
	
	/**
	 * 删除资讯信息
	 * @throws Exception
	 */
	public void delZxinfo(String id)throws Exception{		
		Example example = new Example(WhZxinfo.class);
		example.createCriteria().andEqualTo("id", id);
		int rows = this.whZxinfoMapper.deleteByExample(example);
		if(rows != 1){
			throw new Exception("操作失败");
		}
	}
	
	
	/**-----精品文化展---------------------------------------------------------------------*/
	/**
	 * 分页查询精品文化展
	 * @param param 条件参数
	 * @return 分页培训模板列表
	 * @throws Exception
	 */
	public Map<String, Object> paggingAllJpwhz(Map<String, Object> param)throws Exception{
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		Example example = new Example(WhArt.class);
		//String aetid=(String) param.get("aetid");
		Criteria criteria = example.createCriteria();
		
		//排序
		if(param.containsKey("sort") && param.get("sort")!= null){
			StringBuffer sb = new StringBuffer((String)param.get("sort"));
			if(param.containsKey("order") && param.get("order") !=  null){
				sb.append(" ").append(param.get("order"));
			}
			example.setOrderByClause(sb.toString());
		}else{
			example.setOrderByClause("artid desc");
		}
		
		//列表标题
		if(param.containsKey("artshorttitle") && param.get("artshorttitle") != null){
			String arttitle = (String)param.get("artshorttitle");
			if(!"".equals(arttitle.trim())){
				criteria.andLike("artshorttitle", "%"+arttitle.trim()+"%");
			}
		}
		
		//艺术作品类型 arttyp
		if(param.containsKey("arttyp") && param.get("arttyp") != null){
			String arttyp = (String)param.get("arttyp");
			if(!"".equals(arttyp.trim())){
				criteria.andEqualTo("arttyp", arttyp.trim());
			}
		}
		//艺术作品类型标识 arttypid
		if(param.containsKey("arttypid") && param.get("arttypid") != null){
			String arttypid = (String)param.get("arttypid");
			if(!"".equals(arttypid.trim())){
				criteria.andEqualTo("arttypid", arttypid.trim());
			}
		}
		//艺术作品状态
		if(param.containsKey("artstate") && param.get("artstate") != null){
			String artstate = (String)param.get("artstate");
			if(!"".equals(artstate.trim())){
				criteria.andEqualTo("artstate", artstate.trim());
			}
		}
		
		//是否上首页
		if(param.containsKey("artghp") && param.get("artghp") != null){
			String artghp = (String)param.get("artghp");
			if(!"".equals(artghp.trim())){
				criteria.andEqualTo("artghp", artghp.trim());
			}
		}
		
		List<WhArt> list = this.whArtMapper.selectByExample(example);
		
		// 取分页信息
        PageInfo<WhArt> pageInfo = new PageInfo<WhArt>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	};
	
	/**
	 * 新增新艺术作品
	 * @param whtra 培训模板信息
	 * @throws Exception
	 */
	public void addWhart(WhArt whArt)throws Exception{
		this.whArtMapper.insertSelective(whArt);
	}
	
	/**
	 * 更新艺术作品
	 * @param whtra 培训模板信息
	 * @throws Exception
	 */
	public void updWhart(WhArt whArt)throws Exception{
		//更新记录
		Example example = new Example(WhArt.class);
		example.createCriteria().andEqualTo("artid", whArt.getArtid());
		this.whArtMapper.updateByExampleSelective(whArt, example);
	}
	
	/**
	 * 删除艺术作品
	 * @param id 艺术作品标识
	 * @throws Exception
	 */
	public void delWhart(String id)throws Exception{
		this.whArtMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 查询艺术作品查询详情
	 * @param traid 培训模板标识
	 * @return 培训模板详细
	 * @throws Exception
	 */
	public WhArt searchWhart(String traid)throws Exception{
		return this.whArtMapper.selectByPrimaryKey(traid);
	}
	
	/**
	 *找到相对应的类型
	 * @return
	 */
	public Object findType(String type) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		//个人作品-类型标识应该选择艺术家
		if("2016101400000035".equals(type)){
			List<WhUserArtist> lst =  artistMapper.selectAll();
			if(lst != null){
				for(WhUserArtist obj : lst){
					Map<String, String> m = new HashMap<String, String>();
					m.put("typid", obj.getArtistid());
					m.put("typname", obj.getArtistname());
					list.add(m);
				}
			}
		}
		//精品文化
		if("2016101400000036".equals(type)){
			List<WhArtExhibition> lst = exhibitionMapper.selectAll();
			if(lst != null){
				for(WhArtExhibition obj : lst){
					Map<String, String> m = new HashMap<String, String>();
					m.put("typid", obj.getExhid());
					m.put("typname", obj.getExhtitle());
					list.add(m);
				}
			}
		}
		//艺术团队-类型标识应该选择艺术团
		if("2016101400000037".equals(type)){
			List<WhUserTroupe> lst = troupeMapper.selectAll();
			if(lst != null){
				for(WhUserTroupe obj : lst){
					Map<String, String> m = new HashMap<String, String>();
					m.put("typid", obj.getTroupeid());
					m.put("typname", obj.getTroupename());
					list.add(m);
				}
			}
		}
		//电子杂志
		if("2016101400000038".equals(type)){
			//
		}
		return list;
	}
    /**
     * 审核状态
     * @param zxinfo
     */
	public void checkinfo(WhArt wha) {
		   Date now = new Date();
		   wha.setArtcrttime(now);
		this.whArtMapper.updateByPrimaryKeySelective(wha);
		
	}
    /**
     * 上首页
     * @param wha
     */
	public void goHomePage(WhArt wha)throws Exception {
		this.whArtMapper.updateByPrimaryKeySelective(wha);
	}
    /**
     * 批量审核
     * @param artid
     * @param fromstate
     * @param tostate
     */
	public void goAllPage(String artid, int fromstate, int tostate)throws Exception {
		List<String> list = new ArrayList<String>();
		String[] art = artid.split(",");
		for (int i = 0; i < art.length; i++) {
			list.add(art[i]);
		}
		WhArt wha = new WhArt();
		wha.setArtstate(tostate);
		wha.setArtcrttime(new Date());
		Example example = new Example(WhArt.class);
		example.createCriteria().andIn("artid", list).andEqualTo("artstate", fromstate);
		this.whArtMapper.updateByExampleSelective(wha, example);
		
	}
	
	/**----END-精品文化展---------------------------------------------------------------------*/
}
