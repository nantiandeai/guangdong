package com.creatoo.hn.services.admin.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhZxUploadMapper;
import com.creatoo.hn.model.WhZxUpload;
import com.creatoo.hn.services.comm.CommService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
@Service
public class UploadService {
	@Autowired
	public CommService commService;
	@Autowired
	public WhZxUploadMapper whZxUploadMapper;
   /**
	* 查询
	* @param param
	* @return
	*/
	public Map<String, Object> inquire(Map<String, Object> param) {
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
				
		PageHelper.startPage(page, rows);
		Example example = new Example(WhZxUpload.class);
		Criteria criteria = example.createCriteria();
		//排序
		if(param.containsKey("sort") && param.get("sort")!= null){
			StringBuffer sbf = new StringBuffer((String)param.get("sort"));
			if(param.containsKey("order") && param.get("order") !=  null){
				sbf.append(" ").append(param.get("order"));
			}
			example.setOrderByClause(sbf.toString());
		}else {
			example.setOrderByClause("uptime desc");
		}
		//类型
		if(param.containsKey("uptype") && param.get("uptype") != null){
			String uptype = (String)param.get("uptype");
			if(!"".equals(uptype.trim())){
			 criteria.andEqualTo("uptype", uptype);
			}
		}
		List<WhZxUpload> list = this.whZxUploadMapper.selectByExample(example);
		
		PageInfo<WhZxUpload> pageInfo = new PageInfo<WhZxUpload>(list);
	    Map<String, Object> rtnMap = new HashMap<String, Object>();
	    rtnMap.put("total", pageInfo.getTotal());
	    rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}
	/**
	 * 添加
	 * @param whup
	 */
    public void save(WhZxUpload whup) {
    	this.whZxUploadMapper.insert(whup);
    }
    /**
     * 更新
     * @param whup
     */
	public void updata(WhZxUpload whup) {
		this.whZxUploadMapper.updateByPrimaryKeySelective(whup);
		
	}

	public WhZxUpload select(String  upid){
		return this.whZxUploadMapper.selectByPrimaryKey(upid);
	}
	/**
	 * 删除
	 * @param upid
	 */
	@SuppressWarnings("unused")
	public void delete(String upid) {
		int id=this.whZxUploadMapper.deleteByPrimaryKey(upid);
	}
	/**
	 * 改变状态
	 * @param whup
	 */
	public void checkup(WhZxUpload whup) {
		this.whZxUploadMapper.updateByPrimaryKeySelective(whup);
	}

}
