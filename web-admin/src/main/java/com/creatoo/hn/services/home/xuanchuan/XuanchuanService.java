package com.creatoo.hn.services.home.xuanchuan;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhCfgListMapper;
import com.creatoo.hn.mapper.WhEntsourceMapper;
import com.creatoo.hn.mapper.WhFeedbackMapper;
import com.creatoo.hn.mapper.WhZxColinfoMapper;
import com.creatoo.hn.mapper.WhZxColumnMapper;
import com.creatoo.hn.mapper.WhZxUploadMapper;
import com.creatoo.hn.model.WhCfgList;
import com.creatoo.hn.model.WhEntsource;
import com.creatoo.hn.model.WhFeedback;
import com.creatoo.hn.model.WhZxColinfo;
import com.creatoo.hn.model.WhZxColumn;
import com.creatoo.hn.model.WhZxUpload;
import com.creatoo.hn.services.comm.CommService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class XuanchuanService {
	
	@Autowired
	public CommService commService;
	@Autowired
	private WhZxColumnMapper whZxColumnMapper;
	@Autowired
	private WhZxColinfoMapper WhZxColinfo;
	@Autowired
	private WhFeedbackMapper whFeedbackMapper;
	@Autowired
	private WhCfgListMapper whCfgListMapper;
	@Autowired
	private WhZxUploadMapper whZxUploadMapper;
	@Autowired
	private WhEntsourceMapper whEntsourceMapper;
	/**
	 * 查询文化动态
	 * @param colid
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public Object selectwhhm(String colid) throws Exception{
		Example example = new Example(WhZxColumn.class);
		Criteria Criteria = example.createCriteria();
		Criteria.andEqualTo("colpid", colid);
		Criteria.andEqualTo("colstate", 1);
		example.setOrderByClause("colidx");
		List<WhZxColumn> col =this.whZxColumnMapper.selectByExample(example);
		return col;
	}
    /**
     * 文化动态数据
     * @param page
     * @param rows
     * @param colid
     * @return
     */
	public Object selAllWhdt(int page, int rows, String colid)throws Exception {
		PageHelper.startPage(page, rows);
		//带条件查询
		Example example = new Example(WhZxColinfo.class);
		Criteria Criteria = example.createCriteria();
		Criteria.andEqualTo("clnftype", colid);
		Criteria.andEqualTo("clnfstata", 3);
		example.setOrderByClause("clnfopttime desc");
		List<WhZxColinfo> whzx =this.WhZxColinfo.selectByExample(example);
		
		//分页
		PageInfo<WhZxColinfo> pageInfo = new PageInfo<WhZxColinfo>(whzx);
	    Map<String, Object> rtnMap = new HashMap<String, Object>();
	    rtnMap.put("total", pageInfo.getTotal());
	    rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}
	/**
	 * 查询办事指南
	 * @param colid
	 * @return
	 */
	public Object selectbszn(String colid)throws Exception {
		Example example = new Example(WhZxColumn.class);
		example.createCriteria().andEqualTo("colpid", colid);
		example.setOrderByClause("colidx");
		List<WhZxColumn> col =this.whZxColumnMapper.selectByExample(example);
		return col;
	}
	/**
	 * id查询详细信息
	 * @param clnfid
	 * @return
	 */
	public Object selectinfo(String clnfid) {
		return this.WhZxColinfo.selectByPrimaryKey(clnfid);
	}
	
	
	/**
	 * 导航
	 * @param colid
	 * @return
	 */
	public Map<String, String> selectColumn(String colid) {
		Map<String, String> rtnMap = new HashMap<String, String>();
		String _column = "whdt";
		String _columnName = "文化动态";
		String _columnIdx = "0";
		
		Map<String, String> navMap = new HashMap<String, String>(){
			/**
			 */
			private static final long serialVersionUID = 1L;

			{
				put("文化动态", "whdt");
				put("馆务概况", "gwgk");
				put("办事指南", "bszn");
				put("专题专栏", "ztzl");
			}
		};
		
		Example example = new Example(WhZxColumn.class);
		example.createCriteria().andEqualTo("colstate",1);
		List<WhZxColumn> colLst = this.whZxColumnMapper.selectByExample(example);
		if(colLst != null){
			String pid = "";
			WhZxColumn dest = null;
			WhZxColumn curt = null;
			for(WhZxColumn column : colLst){
				if(column.getColid().equals(colid)){
					curt = column;
					if(pid != null && !"0".equals(pid)){
						pid = column.getColpid();
					}else{
						dest = column;
					}
				}
			}
			List<WhZxColumn> allChild = new ArrayList<WhZxColumn>();
			if(dest == null && !"".equals(pid)){
				for(WhZxColumn column : colLst){
					if(column.getColid().equals(pid)){
						dest = column;
					}
					if(column.getColpid().equals(pid)){
						allChild.add(column);
					}
				}
			}
			
			Collections.sort(allChild, new Comparator<WhZxColumn>(){
				@Override
				public int compare(WhZxColumn o1, WhZxColumn o2) {
					return o1.getColidx() - o2.getColidx();
				}
			});
			
			if(dest != null){
				_columnName = dest.getColtitle();
				_column = navMap.get(_columnName);
				_columnIdx =  allChild.indexOf(curt)+"";
			}
		}
		
		rtnMap.put("column", _column);
		rtnMap.put("columnName", _columnName);
		rtnMap.put("columnIdx", _columnIdx);
		return rtnMap;
	}
	/**
	 * 保存意见反馈信息
	 * @param whf
	 */
	public void addinfo(WhFeedback whf) {
		this.whFeedbackMapper.insert(whf);
		
	}
	/**
	 * 2016110700000001 专题专栏
	 * @param cfgpagetype 
	 * @param 
	 * @param colid
	 * @return
	 */
	public Object selAllplays(String cfgentclazz, String cfgpagetype) {
		Example example = new Example(WhCfgList.class);
		Criteria Criteria = example.createCriteria();
		Criteria.andEqualTo("cfgpagetype", cfgpagetype);
		Criteria.andEqualTo("cfgentclazz", cfgentclazz);
		Criteria.andEqualTo("cfgstate", 1);
		example.setOrderByClause("cfgshowidx");
		List<WhCfgList> cfg =this.whCfgListMapper.selectByExample(example);
		return cfg;
	}
	/**
	 * 查询上传信息
	 * @param clnfid
	 * @return
	 */
	public List<WhZxUpload> selecup(String clnfid) {
		Example example = new Example(WhZxUpload.class);
		Criteria Criteria = example.createCriteria();
		Criteria.andEqualTo("uptype", clnfid);
		Criteria.andEqualTo("upstate", 1);
		List<WhZxUpload> whup =this.whZxUploadMapper.selectByExample(example);
		return whup;
		
	}
	/**
	 * 资源信息
	 * @param clnfid
	 * @param enttype 
	 * @return
	 */
	public List<WhEntsource> selecent(String clnfid, String enttype) {
		Example example = new Example(WhEntsource.class);
		Criteria Criteria = example.createCriteria();
		Criteria.andEqualTo("refid", clnfid);
		Criteria.andEqualTo("enttype", enttype);
		List<WhEntsource> whe =this.whEntsourceMapper.selectByExample(example);
		return whe;
	}
	/**
	 * 视频
	 * @param clnfid
	 * @param type
	 * @return
	 */
	public List<WhEntsource> selecsource(String clnfid, String type) {
		Example example = new Example(WhEntsource.class);
		Criteria Criteria = example.createCriteria();
		Criteria.andEqualTo("refid", clnfid);
		Criteria.andEqualTo("enttype", type);
		List<WhEntsource> whe =this.whEntsourceMapper.selectByExample(example);
		return whe;
	}
	/**
	 * 音频
	 * @param clnfid
	 * @param clazz
	 * @return
	 */
	public List<WhEntsource> selecwhent(String clnfid, String clazz) {
		Example example = new Example(WhEntsource.class);
		Criteria Criteria = example.createCriteria();
		Criteria.andEqualTo("refid", clnfid);
		Criteria.andEqualTo("enttype", clazz);
		List<WhEntsource> whe =this.whEntsourceMapper.selectByExample(example);
		return whe;
	}

}
