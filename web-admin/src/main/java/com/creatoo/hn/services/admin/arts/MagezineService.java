package com.creatoo.hn.services.admin.arts;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhCfgAdvMapper;
import com.creatoo.hn.mapper.WhMagepageMapper;
import com.creatoo.hn.mapper.WhMagezineMapper;
import com.creatoo.hn.model.WhCfgAdv;
import com.creatoo.hn.model.WhMagepage;
import com.creatoo.hn.model.WhMagezine;
import com.creatoo.hn.model.WhTrain;
import com.creatoo.hn.utils.UploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class MagezineService {
	@Autowired
	private WhMagezineMapper whMagezineMapper;
	@Autowired
	private WhMagepageMapper whMagepageMapper;
	
	@Autowired
	private WhCfgAdvMapper whCfgAdvMapper;
	/**
	 * 查找所有的电子杂志
	 * @return
	 */
	public Map<String, Object> findMage(Map<String, Object> param) {
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.whMagezineMapper.selmagezine(param);
		
		// 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}

	/**
	 * 更新电子杂志信息
	 * @param whMagezine
	 */
	public void updateMage(WhMagezine whMagezine)throws Exception {
		this.whMagezineMapper.updateByPrimaryKeySelective(whMagezine);
		
	}
	
	/**
	 * 添加电子杂志
	 * @param whMagezine
	 */
	public void insertMage(WhMagezine whMagezine)throws Exception {
		this.whMagezineMapper.insertSelective(whMagezine);
	}

	/**
	 * 删除电子杂志
	 * @param mageid
	 * @throws Exception
	 */
	public void delMage(String mageid,String uploadPath)throws Exception {
		WhMagezine whMagezine = this.whMagezineMapper.selectByPrimaryKey(mageid);
		String magepic = whMagezine.getMagepic();
		this.whMagezineMapper.deleteByPrimaryKey(mageid);
		UploadUtil.delUploadFile(uploadPath, magepic);
		
	}

	/**
	 * 审核或者打回
	 * @param mageid
	 * @param magestate
	 * @param tostate 
	 */
	public Object checkOrBack(String mageid, Integer fromstate, int tostate) {
		WhMagezine whMagezine = new WhMagezine();
		Example example = new Example(WhMagepage.class);
		example.createCriteria().andEqualTo("pagemageid", mageid);
		int count = this.whMagepageMapper.selectCountByExample(example);
		if (count != 0 && count > 0 ) {
			return "没有配置页码，不能审核！";
		}
		whMagezine.setMagestate(tostate);
		whMagezine.setMageopttime(new Date());
		Example example1 = new Example(WhMagezine.class);
		example1.createCriteria().andEqualTo("mageid", mageid);
		example1.createCriteria().andEqualTo("magestate", fromstate);
		this.whMagezineMapper.updateByExampleSelective(whMagezine, example1);
		return "操作成功";
		
	}

	/**
	 * 批量审核或者发布操作
	 * @param mageid
	 * @param fromstate
	 * @param tostate
	 */
	public void allCheckOrBack(String mageid, int fromstate, int tostate) {
		List<String> list = new ArrayList<String>();
		String[] mage = mageid.split(",");
		for (int i = 0; i < mage.length; i++) {
			list.add(mage[i]);
		}
		WhMagezine whMagezine = new WhMagezine();
		whMagezine.setMagestate(tostate);
		whMagezine.setMageopttime(new Date());
		Example example = new Example(WhMagezine.class);
		example.createCriteria().andIn("mageid", list).andEqualTo("magestate", fromstate);
		this.whMagezineMapper.updateByExampleSelective(whMagezine, example);
		
	}

	//-----------------------------页码管理-------------------------------------
	public Map<String, Object> findPage(Map<String, Object> param) {
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.whMagezineMapper.selMagezinePage(param);
		
		// 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}

	/**
	 * 更新页码信息
	 * @param whMagepage
	 * @throws Exception
	 */
	public void updatePage(WhMagepage whMagepage)throws Exception {
		
		this.whMagepageMapper.updateByPrimaryKeySelective(whMagepage);
	}

	/**
	 * 添加页码信息
	 * @param whMagepage
	 * @throws Exception
	 */
	public void insertPage(WhMagepage whMagepage)throws Exception {
		
		this.whMagepageMapper.insertSelective(whMagepage);
	}

	/**
	 * 删除页码
	 * @param pageid
	 * @param uploadPath
	 */
	public void delPage(String pageid, String uploadPath)throws Exception {
		WhMagepage whMagepage = this.whMagepageMapper.selectByPrimaryKey(pageid);
		String pagepic = whMagepage.getPagepic();
		this.whMagepageMapper.deleteByPrimaryKey(pageid);
		UploadUtil.delUploadFile(uploadPath, pagepic);
	}

	/**
	 * 启用或者停用
	 * @param pageid
	 * @param fromstate
	 * @param tostate
	 */
	public void onOrOff(String pageid, int fromstate, int tostate)throws Exception {
		WhMagepage whMagepage = new WhMagepage();
		whMagepage.setPagestate(tostate);
		Example example = new Example(WhMagepage.class);
		if (pageid != null) {
			example.createCriteria().andEqualTo("pageid", pageid);
		}
		example.createCriteria().andEqualTo("pagestate", fromstate);
		
		this.whMagepageMapper.updateByExampleSelective(whMagepage, example);
	}

	/**
	 * 查询发布的电子杂志
	 * @return
	 */
	public List<WhMagezine> selMagezine()throws Exception {
		return this.whMagezineMapper.selDetail();
	}
	
	/**
	 * 查询当前发布的电子杂志
	 * @return
	 */
	public List<WhMagepage> selMagezineidx(String mageid)throws Exception {
		return this.whMagezineMapper.selDetailIdx(mageid);
	}

	/**
	 * 查找页码信息
	 * @param pagemageids
	 * @return 
	 */
	public Object findPageinfo(String pagemageid)throws Exception {
		
		return this.whMagezineMapper.selMagePage(pagemageid);
	}

	/**
	 * 查找电子杂志广告图
	 * @return
	 */
	public List<WhCfgAdv> advListLoad() {
		Example example = new Example(WhCfgAdv.class);
		example.createCriteria().andEqualTo("cfgadvpagetype", "2016111000000001").andEqualTo("cfgadvstate", 1);
		example.setOrderByClause("cfgadvidx" + " " + "desc");
		return this.whCfgAdvMapper.selectByExample(example);
	}
}
