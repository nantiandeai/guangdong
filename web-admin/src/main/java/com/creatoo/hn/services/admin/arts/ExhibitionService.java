package com.creatoo.hn.services.admin.arts;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhArtExhibitionMapper;
import com.creatoo.hn.model.WhArtExhibition;
import com.creatoo.hn.model.WhTrain;
import com.creatoo.hn.services.comm.CommService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 艺术展
 * @author Administrator
 *
 */
@Service
public class ExhibitionService {
	@Autowired
	public CommService commService;
	@Autowired
	private WhArtExhibitionMapper whArtExhibitionMapper;
    /**
     * 查询艺术展信息
     * @param page
     * @param rows
     * @return
     */
	public Map<String, Object> exhselect(Map<String, Object> param)throws Exception {
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		
		PageHelper.startPage(page, rows);
		Example example=new Example(WhArtExhibition.class);
		Criteria criteria=example.createCriteria();
		//排序
		if(param.containsKey("sort") && param.get("sort")!= null){
			StringBuffer sbf = new StringBuffer((String)param.get("sort"));
			if(param.containsKey("order") && param.get("order") !=  null){
				sbf.append(" ").append(param.get("order"));
			}
			example.setOrderByClause(sbf.toString());
		}
		//标题
		if(param.containsKey("exhtitle") && param.get("exhtitle") != null){
			String troupename = (String)param.get("exhtitle");
			if(!"".equals(troupename.trim())){
				criteria.andLike("exhtitle", "%"+troupename.trim()+"%");
			}
		}
		//艺术类型
		if(param.containsKey("exharttyp") && param.get("exharttyp") != null){
			String exharttyp = (String)param.get("exharttyp");
			if(!"".equals(exharttyp.trim())){
			 criteria.andEqualTo("exharttyp", exharttyp);
			}
		}
		//状态
		if(param.containsKey("exhstate") && param.get("exhstate") != null){
			String exhstate = (String)param.get("exhstate");
			if(!"".equals(exhstate.trim())){
			 criteria.andEqualTo("exhstate", exhstate);
			}
		}	
		List<WhArtExhibition> exhs = this.whArtExhibitionMapper.selectByExample(example);
		PageInfo<WhArtExhibition> pageInfo = new PageInfo<WhArtExhibition>(exhs);
	    Map<String, Object> rtnMap = new HashMap<String, Object>();
	    rtnMap.put("total", pageInfo.getTotal());
	    rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}
    /**
     * 添加艺术展信息
     * @param wha
     * @return
     */
	public Object addexchi(WhArtExhibition wha)throws Exception {
		return this.whArtExhibitionMapper.insert(wha);
	}
    /**
     * 更新艺术展信息
     * @param wha
     */
	public Object upexchi(WhArtExhibition wha)throws Exception {
		Example example = new Example(WhArtExhibition.class);
		example.createCriteria().andEqualTo("exhid", wha.getExhid());
		
		return whArtExhibitionMapper.updateByExampleSelective(wha, example);
	}
    /**
     * 根据主键删除艺术展信息
     * @param exhid
     */
	public int delgexh(String exhid)throws Exception {
		return this.whArtExhibitionMapper.deleteByPrimaryKey(exhid);
	}
    /**
     * 改变艺术展状态
     * @param wha
     */
	public void checkexh(WhArtExhibition wha)throws Exception {
		Date date=new Date();
		wha.setExhtime(date);
		this.whArtExhibitionMapper.updateByPrimaryKeySelective(wha);
	}
	/**
	 * 上首页排序
	 * @param wha
	 */
	public void goHomePage(WhArtExhibition wha)throws Exception {
		this.whArtExhibitionMapper.updateByPrimaryKeySelective(wha);
		
	}
	/**
	 * 批量审核
	 * @param exhid
	 * @param fromstate
	 * @param tostate
	 */
	public void checkexhi(String exhid, int fromstate, int tostate)throws Exception {
		List<String> list = new ArrayList<String>();
		String[] exh = exhid.split(",");
		for (int i = 0; i < exh.length; i++) {
			list.add(exh[i]);
		}
		WhArtExhibition wha = new WhArtExhibition();
		wha.setExhstate(tostate);
		wha.setExhtime(new Date());
		Example example = new Example(WhArtExhibition.class);
		example.createCriteria().andIn("exhid", list).andEqualTo("exhstate", fromstate);
		this.whArtExhibitionMapper.updateByExampleSelective(wha, example);
		
	}

}
