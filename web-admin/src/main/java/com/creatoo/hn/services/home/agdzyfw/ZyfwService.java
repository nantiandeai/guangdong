package com.creatoo.hn.services.home.agdzyfw;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;

import com.creatoo.hn.ext.emun.EnumLBTClazz;
import com.creatoo.hn.ext.emun.EnumState;
import com.creatoo.hn.mapper.*;
import com.creatoo.hn.model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.WhConstance;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 志愿服务
 * @author wangxl
 * @version 2016.11.16
 */
@Service
public class ZyfwService {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * 公用服务类
	 */
	@Autowired
	public CommService commservice;
	@Autowired
	private WhZypxMapper whZypxMapper;
	@Autowired
	private WhZyhdMapper whZyhdMapper;
	@Autowired
	private WhEntsourceMapper whEntsourceMapper;
	@Autowired
	private WhZyfcGerenMapper gerenMapper;
	@Autowired
	private WhZyfcXiangmuMapper whZyfcXiangmuMapper;
	@Autowired
	private WhZyfcZuzhiMapper whZyfcZuzhiMapper;
	@Autowired
	private WhCfgListMapper cfgListMapper;
	@Autowired
	private WhCfgAdvMapper whCfgAdvMapper;
	@Autowired
	private WhKeyMapper whKeyMapper;
	@Autowired
	private WhgYwiLbtMapper whgYwiLbtMapper;
	/**
	 * 分页查询志愿培训列表信息
	 * @param param
	 * @return 
	 * @throws Exception
	 */
	public Map<String, Object> selzypx(Map<String, Object> param)throws Exception {
		//分页信息
		int page = 1;
		int rows = 9;
		if(param != null && param.get("page") != null && param.get("rows") != null){
			page = Integer.parseInt((String)param.get("page"));
			rows = Integer.parseInt((String)param.get("rows"));
		}
		//艺术类型
		String zypxtype = (String) param.get("typid");
		//搜索标题
		String zypxtitle = (String) param.get("zypxtitle");
		//排序
		int paixu = 0;
		if (param.get("time") != null && ((String)param.get("time")).matches("^\\d+$")){
			paixu = Integer.parseInt((String)param.get("time"));
		}
//		String order = WhConstance.getSysProperty("SYS_ORDER");
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		
		//分页查询培训报名
		Example example = new Example(WhZypx.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("zypxstate", 3);//已发布
		if (zypxtype != null && !zypxtype.isEmpty()) {
			c.andEqualTo("zypxtype",zypxtype);
		}
		if (zypxtitle != null && !zypxtitle.isEmpty()) {
		
			c.andLike("zypxshorttitle", "%"+zypxtitle+"%");
		}
		if (paixu == 0) {
			c.andIsNotNull("zypxid");
		}
		if (paixu == 1) {
			example.setOrderByClause("zypxopttime desc");
		}
		/*
		if (order.equals("1")) {
			example.setOrderByClause("zypxopttime desc");
		}else{
			
		}*/
		example.setOrderByClause("zypxopttime desc");
		List<WhZypx> list = this.whZypxMapper.selectByExample(example);
		
		// 取分页信息
        PageInfo<WhZypx> pageInfo = new PageInfo<WhZypx>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
        rtnMap.put("page", pageInfo.getPageNum());
        rtnMap.put("pageSize", pageInfo.getPageSize());
        
		return rtnMap;
	
		
	}

	/**
	 * 详情页查找志愿培训
	 * @param zypxid
	 * @return
	 */
	public WhZypx selZypx(String zypxid)throws Exception {
		
		return this.whZypxMapper.selectByPrimaryKey(zypxid);
	}

	/**
	 * 志愿培训详情页查找推荐视频
	 * @param zypxid
	 * @return
	 */
	public List<WhZypx> selNewZypx(String zypxid)throws Exception {
		Example example = new Example(WhZypx.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("zypxstate", 3);
		c.andNotEqualTo("zypxid", zypxid);
		example.setOrderByClause("zypxopttime desc");
		PageHelper.startPage(1, 3);
		return this.whZypxMapper.selectByExample(example);
	}

	/**
	 * 分页查询志愿活动
	 * @param param
	 * @return
	 */
	public Map<String, Object> selzyhd(Map<String, Object> param)throws Exception {
		//分页信息
		int page = 1;
		int rows = 9;
		if(param != null && param.get("page") != null && param.get("rows") != null){
			page = Integer.parseInt((String)param.get("page"));
			rows = Integer.parseInt((String)param.get("rows"));
		}
		//艺术类型
		String zyhdtype = (String) param.get("typid");
		//quyu
		String areaid = (String) param.get("areaid");
		String order = WhConstance.getSysProperty("SYS_ORDER");
		//搜索标题
		String zypxtitle = (String) param.get("zypxtitle");
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		
		//分页查询培训报名
		Example example = new Example(WhZyhd.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("zyhdstate", 3);//已发布
		if (zyhdtype != null && !zyhdtype.isEmpty()) {
			c.andEqualTo("zyhdtype",zyhdtype);
		}
		if (areaid != null && !areaid.isEmpty()) {
			c.andEqualTo("zyhdarea",areaid);
		}
		if (zypxtitle != null && !zypxtitle.isEmpty()) {
			
			c.andLike("zyhdshorttitle", "%"+zypxtitle+"%");
		}
		if ("1".equals(order)) {
			example.setOrderByClause("zyhdsdate desc");
		}else{
			example.setOrderByClause("zyhdopttime desc");
		}
		List<WhZyhd> list = this.whZyhdMapper.selectByExample(example);
		
		// 取分页信息
        PageInfo<WhZyhd> pageInfo = new PageInfo<WhZyhd>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
        rtnMap.put("page", pageInfo.getPageNum());
        rtnMap.put("pageSize", pageInfo.getPageSize());
		return rtnMap;
	}

	/**志愿服务活动详情页推荐课程查询
	 * @param zyhdid
	 * @return
	 */
	public List<WhZyhd> selNewZyhd(String zyhdid)throws Exception {
		Example example = new Example(WhZyhd.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("zyhdstate", 3);
		c.andNotEqualTo("zyhdid", zyhdid);
		example.setOrderByClause("zyhdopttime desc");
		PageHelper.startPage(1, 3);
		return this.whZyhdMapper.selectByExample(example);
	}

	/**
	 * 志愿服务活动详情页查询
	 * @param zyhdid
	 * @return
	 */
	public WhZyhd findZyhd(String zyhdid)throws Exception {
		
		return this.whZyhdMapper.selectByPrimaryKey(zyhdid);
	}

	/**
	 * 查找图片资源
	 * @param braid
	 * @return
	 */
	public List<WhEntsource> findZY(String zyhdid) {
		Example example = new Example(WhEntsource.class);
		Criteria c = example.createCriteria();
		String enttype = "2016101400000055";
		if(zyhdid!=null && !"".equals(zyhdid)){
			c.andEqualTo("refid", zyhdid);
		}
		if(enttype!=null && !"".equals(enttype)){
			c.andEqualTo("enttype", enttype);
		}
		return this.whEntsourceMapper.selectByExample(example);
	}
	/**
	 * 查询风采展示项目列表页
	 * @return
	 */
	public Map<String, Object> selectxm(Map<String, Object> param)throws Exception {
		//分页信息
		int page = 1;
		int rows = 9;
		if(param != null && param.get("page") != null && param.get("rows") != null){
			page = Integer.parseInt((String)param.get("page"));
			rows = Integer.parseInt((String)param.get("rows"));
		}
		String order = WhConstance.getSysProperty("SYS_ORDER");
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		
		//分页查询项目展示
		Example example = new Example(WhZyfcXiangmu.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("zyfcxmstate", 3);//已发布
		
		if (order.equals("1")) {
			example.setOrderByClause("zyfcxmsstime desc");
		}else{
			example.setOrderByClause("zyfcxmopttime desc");
		}
		List<WhZyfcXiangmu> list = this.whZyfcXiangmuMapper.selectByExample(example);
		
		// 取分页信息
        PageInfo<WhZyfcXiangmu> pageInfo = new PageInfo<WhZyfcXiangmu>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
        rtnMap.put("page", pageInfo.getPageNum());
        rtnMap.put("pageSize", pageInfo.getPageSize());
        
		return rtnMap;
	}

	/**
	 * 分页查询优秀组织
	 * @param param
	 * @return
	 */
	public Map<String, Object> selyxzz(Map<String, Object> param)throws Exception {
		//分页信息
		int page = 1;
		int rows = 9;
		if(param != null && param.get("page") != null && param.get("rows") != null){
			page = Integer.parseInt((String)param.get("page"));
			rows = Integer.parseInt((String)param.get("rows"));
		}
		String order = WhConstance.getSysProperty("SYS_ORDER");
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		
		//分页查询培训报名
		Example example = new Example(WhZyfcZuzhi.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("zyfczzstate", 3);//已发布
		
		if (order.equals("1")) {
			example.setOrderByClause("zyfczzopttime desc");
		}else{
			example.setOrderByClause("zyfczzopttime desc");
		}
		
		List<WhZyfcZuzhi> list = this.whZyfcZuzhiMapper.selectByExample(example);
		
		// 取分页信息
        PageInfo<WhZyfcZuzhi> pageInfo = new PageInfo<WhZyfcZuzhi>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
        rtnMap.put("page", pageInfo.getPageNum());
        rtnMap.put("pageSize", pageInfo.getPageSize());
		return rtnMap;
	

	}

	/**
	 * 查询项目展示详细信息
	 * @param zyfcxmid
	 * @return
	 */
	public WhZyfcXiangmu selectwhzy(String zyfcxmid) {
		return this.whZyfcXiangmuMapper.selectByPrimaryKey(zyfcxmid);
	}
	/**
	 * 查询图片
	 * @param zyfcxmid
	 * @return
	 */
	public List<WhEntsource> findpic(String refid,String enttype,String reftype) {
		Example example = new Example(WhEntsource.class);
		Criteria c = example.createCriteria();
		if(refid != null && !"".equals(refid)){
			c.andEqualTo("refid", refid);
		}
		if(enttype!=null && !"".equals(enttype)){
			c.andEqualTo("enttype", enttype);
		}
		if(reftype!=null && !"".equals(reftype)){
			c.andEqualTo("reftype", reftype);
		}
		return this.whEntsourceMapper.selectByExample(example);
	}
	/**
	 * 查询视频
	 * @param zyfcxmid
	 * @return
	 */
	public List<WhEntsource> findvido(String refid,String enttype,String reftype) {
		Example example = new Example(WhEntsource.class);
		Criteria c = example.createCriteria();
		if(refid != null && !"".equals(refid)){
			c.andEqualTo("refid", refid);
		}
		if(enttype!=null && !"".equals(enttype)){
			c.andEqualTo("enttype", enttype);
		}
		if(reftype!=null && !"".equals(reftype)){
			c.andEqualTo("reftype", reftype);
		}
		return this.whEntsourceMapper.selectByExample(example);
	}
	/**
	 * 查询音频
	 * @param zyfcxmid
	 * @return
	 */
	public List<WhEntsource> findmusci(String refid,String enttype,String reftype) {
		Example example = new Example(WhEntsource.class);
		Criteria c = example.createCriteria();
		if(refid != null && !"".equals(refid)){
			c.andEqualTo("refid", refid);
		}
		if(enttype!=null && !"".equals(enttype)){
			c.andEqualTo("enttype", enttype);
		}
		if(reftype!=null && !"".equals(reftype)){
			c.andEqualTo("reftype", reftype);
		}
		return this.whEntsourceMapper.selectByExample(example);
	}
	/**
	 * 带条件查询出项目信息
	 * @return
	 */
	public List<WhZyfcXiangmu> selectlistxm(String zyfcxmid) {
		Example example = new Example(WhZyfcXiangmu.class);
		Criteria c = example.createCriteria();
		c.andNotEqualTo("zyfcxmid", zyfcxmid);
		c.andEqualTo("zyfcxmstate",3);
		example.setOrderByClause("zyfcxmopttime desc");
		PageHelper.startPage(1, 3);
		return this.whZyfcXiangmuMapper.selectByExample(example);
	}
	
	/**
	 * 优秀组织详情查询
	 * @param zyfczzid
	 * @return
	 */
	public WhZyfcZuzhi selyxzz(String zyfczzid) {
		return this.whZyfcZuzhiMapper.selectByPrimaryKey(zyfczzid);
	}

	/**
	 * 推荐组织查询
	 * @param zyfczzid
	 * @return
	 */
	public List<WhZyfcZuzhi> selNewZztj(String zyfczzid) {
		Example example = new Example(WhZyfcZuzhi.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("zyfczzstate", 3);
		c.andNotEqualTo("zyfczzid", zyfczzid);
		example.setOrderByClause("zyfczzopttime desc");
		PageHelper.startPage(1, 3);
		return this.whZyfcZuzhiMapper.selectByExample(example);
	}

	/**
	 * 首页资讯配置
	 * @return
	 */
	public List<WhCfgList> getzxpz() {
		Example example = new Example(WhCfgList.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("cfgpagetype","2016112900000004");
		c.andEqualTo("cfgenttype","2016102800000001");
		c.andEqualTo("cfgstate",1);
		PageHelper.startPage(1, 3);
		List<WhCfgList> list = this.cfgListMapper.selectByExample(example);
		
		return list;
	}
	/**
	 * 查找广告图
	 * @param cfgadvid
	 * @return
	 */
	public WhCfgAdv findAdv(String cfgadvpagetype)throws Exception {
		Example example = new Example(WhCfgAdv.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("cfgadvpagetype", cfgadvpagetype);
		c.andEqualTo("cfgadvstate", 1);
		PageHelper.startPage(1, 1);
		List<WhCfgAdv> adv =  this.whCfgAdvMapper.selectByExample(example);
		if (!adv.isEmpty()) {
			return adv.get(0);
		}else{
			return null;
		}
		//return null;
	}
	/**
	 * 先进个人详情页查询
	 * @param zyfcgrid
	 * @return
	 */
	public WhZyfcGeren selectGeren(String zyfcgrid)throws Exception {
		return this.gerenMapper.selectByPrimaryKey(zyfcgrid);
	}
	
	/**
	 * 先进个人详情--查找图片资源
	 * @param braid
	 * @return
	 */
	public List<WhEntsource> gerenZy(String zyfcgrid) {
		Example example = new Example(WhEntsource.class);
		Criteria c = example.createCriteria();
		String enttype = "2016101400000055";
		if(zyfcgrid!=null && !"".equals(zyfcgrid)){
			c.andEqualTo("refid", zyfcgrid);
		}
		if(enttype!=null && !"".equals(enttype)){
			c.andEqualTo("enttype", enttype);
		}
		return this.whEntsourceMapper.selectByExample(example);
	}
	
	/**
	 * 先进个人详情推荐
	 * @param zyfcgrid
	 * @return
	 */
	public List<WhZyfcGeren> gerenTJ(String zyfcgrid){
		Example example = new Example(WhZyfcGeren.class);
		Criteria c = example.createCriteria();
		c.andNotEqualTo("zyfcgrid", zyfcgrid);
		c.andEqualTo("zyfcgrstate", 3);
		example.setOrderByClause("zyfcgropttime"+" "+"desc");
		PageHelper.startPage(1, 3);
		List<WhZyfcGeren> listGr = this.gerenMapper.selectByExample(example);
		PageInfo pinfo = new PageInfo(listGr);
		return pinfo.getList();
	}
	
	/**
	 * 分页查询先进个人
	 * @param param
	 * @return
	 */
	public Map<String, Object> paraGr(Map<String, Object> param)throws Exception {
			//分页信息
			int page = 1;
			int rows = 9;
			if(param != null && param.get("page") != null && param.get("rows") != null){
				page = Integer.parseInt((String)param.get("page"));
				rows = Integer.parseInt((String)param.get("rows"));
			}
			String order = WhConstance.getSysProperty("SYS_ORDER");
			//带条件的分页查询
			PageHelper.startPage(page, rows);
			
			//分页查询先进个人
			Example example = new Example(WhZyfcGeren.class);
			Criteria c = example.createCriteria();
			c.andEqualTo("zyfcgrstate", 3);//已发布
			
			if ("1".equals(order)) {
				example.setOrderByClause("zyfcgrjrtime desc");
			}else{
				example.setOrderByClause("zyfcgropttime desc");
			}
			List<WhZyfcGeren> list = this.gerenMapper.selectByExample(example);
			
			// 取分页信息
	        PageInfo<WhZyfcGeren> pageInfo = new PageInfo<WhZyfcGeren>(list);
	       
	        Map<String, Object> rtnMap = new HashMap<String, Object>();
	        rtnMap.put("total", pageInfo.getTotal());
	        rtnMap.put("rows", pageInfo.getList());
	        rtnMap.put("page", pageInfo.getPageNum());
	        rtnMap.put("pageSize", pageInfo.getPageSize());
			return rtnMap;
	}

	/**
	 * 查询首页风采展示的配置信息
	 * @return
	 */
//	public List<WhCfgList> selFengcai()throws Exception {
//		Example example = new Example(WhCfgList.class);
//		Criteria c = example.createCriteria();
//		c.andEqualTo("cfgpagetype","2016112900000004");
//		c.andEqualTo("cfgenttype","2016112900000015");
//		c.andEqualTo("cfgstate",1);
//		example.setOrderByClause("cfgshowidx"+" "+"asc");
//		PageHelper.startPage(1, 5);
//		List<WhCfgList> list = this.cfgListMapper.selectByExample(example);
//		return list;
//	}

	/**
	 * 查询首页风采展示的配置信息
	 * @return
	 */
	public List<WhgYwiLbt> selFengcai()throws Exception {
		Example example = new Example(WhgYwiLbt.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("type", EnumLBTClazz.LBT_VOL_PC.getValue());
		c.andEqualTo("state", EnumState.STATE_YES.getValue());
		example.setOrderByClause("statemdfdate"+" "+"asc");
		PageHelper.startPage(1, 5);
		List<WhgYwiLbt> list = this.whgYwiLbtMapper.selectByExample(example);
		return list;
	}
	/**
	 * 将拿到的关键字ID转换成关键字
	 * @param zypxkeys
	 * @return
	 */
	public Object getKeys(String zypxkeys) {
		if (zypxkeys == null) {
			return "";
		}
		String[] key =  zypxkeys.split(",");
		List<String> keys = new ArrayList<String>();
		for (int i = 0; i < key.length; i++) {
			keys.add(key[i]);
		}
		Example example = new Example(WhKey.class);
		Criteria c = example.createCriteria();
		c.andIn("id", keys);
		return this.whKeyMapper.selectByExample(example);
		
	}
	
	
}
