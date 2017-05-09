package com.creatoo.hn.services.admin.arts;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhDrscMapper;
import com.creatoo.hn.model.WhDrsc;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.UploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.servlet.http.HttpServletRequest;

/**
 * 数字资源服务类
 * @author wangxl
 * @version 20161108
 */
@Service
public class DrscService {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public CommService commService;
	
	@Autowired
	public WhDrscMapper drscMapper;
	
	/**
	 * 批量修改资源状态
	 * @param ids 资源标识，多个用逗号分隔
	 * @param fromState 修改之前的状态
	 * @param toState 修改之后的状态
	 */
	public void updState(String ids, int fromState, int toState)throws Exception{
		if(ids != null){
			String[] idArr = ids.split(",");
			Date now = new Date();
			for(String id : idArr){
				if(id != null){
					Example example = new Example(WhDrsc.class);
					example.createCriteria().andEqualTo("drscid", id).andEqualTo("drscstate", fromState);
					
					WhDrsc record = new WhDrsc();
					record.setDrscstate(toState);
					record.setDrscopttime(now);
					this.drscMapper.updateByExampleSelective(record, example);
				}
			}
		}
	}
	
	/**
	 * 添加数字资源
	 * @param drsc
	 * @throws Exception
	 */
	public void t_add(WhDrsc drsc)throws Exception{
		drsc.setDrscid(commService.getKey("wh_drsc"));
		drsc.setDrsccrttime(new Date());
		drsc.setDrscopttime(new Date());
		drsc.setDrscstate(0);
		this.drscMapper.insert(drsc);
	}
	
	/**编辑数字资源
	 * @param drsc
	 * @throws Exception
	 */
	public void t_edit(WhDrsc drsc)throws Exception{
		/*Example example = new Example(WhDrsc.class);
		example.createCriteria().andEqualTo("drscid", drsc.getDrscid()).andIn("drscstate", new ArrayList<Integer>(){
			private static final long serialVersionUID = 1L;
			{
			add(0);
			add(1);
			}
		});
		this.drscMapper.updateByExampleSelective(drsc, example);*/
		this.drscMapper.updateByPrimaryKeySelective(drsc);
	}
	
	/**
	 * 删除数字资源
	 * @throws Exception
	 */
	public void delete(String id, String uploadPath)throws Exception{
		//获取图片和资源
		WhDrsc record = new WhDrsc();
		record.setDrscid(id);
		record = this.drscMapper.selectOne(record);
		String derscpic = record.getDrscpic();
		String drscpath = record.getDrscpath();
		
		//删除数字资源
		Example example = new Example(WhDrsc.class);
		example.createCriteria().andEqualTo("drscid", id).andIn("drscstate", new ArrayList<Integer>(){
			private static final long serialVersionUID = 1L;
			{
			add(0);
			add(1);
			}
		});
		int cnt = this.drscMapper.deleteByExample(example);
		if(cnt > 0){
			//删除图片
			if(record.getDrscpath() != null){
				UploadUtil.delUploadFile(uploadPath, drscpath);
			}
			//删除资源 
			if(derscpic != null){
				UploadUtil.delUploadFile(uploadPath, derscpic);
			}
		}
	}
	
	/**
	 * 分页查询数字资源
	 * @param param 参数条件
	 * @return 分页内容
	 * @throws Exception
	 */
	public Map<String, Object> srchPagging(Map<String, Object> param)throws Exception{
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		Example example = new Example(WhDrsc.class);
		Criteria criteria = example.createCriteria();
		
		//排序
		if(param.containsKey("sort") && param.get("sort")!= null){
			StringBuffer sb = new StringBuffer((String)param.get("sort"));
			if(param.containsKey("order") && param.get("order") !=  null){
				sb.append(" ").append(param.get("order"));
			}
			example.setOrderByClause(sb.toString());
		}else{
			example.setOrderByClause("drscid desc");
		}
		
		//列表标题
		if(param.containsKey("drsctitle") && param.get("drsctitle") != null){
			String drsctitle = (String)param.get("drsctitle");
			if(!"".equals(drsctitle.trim())){
				criteria.andLike("drsctitle", "%"+drsctitle.trim()+"%");
			}
		}
		
		//艺术作品类型
		if(param.containsKey("drscarttyp") && param.get("drscarttyp") != null){
			String drscarttyp = (String)param.get("drscarttyp");
			if(!"".equals(drscarttyp.trim())){
				criteria.andEqualTo("drscarttyp", drscarttyp);
			}
		}
		
		//来源
		if(param.containsKey("drscfrom") && param.get("drscfrom") != null){
			String drscfrom = (String)param.get("drscfrom");
			if(!"".equals(drscfrom.trim())){
				criteria.andLike("drscfrom", "%"+drscfrom.trim()+"%");
			}
		}
		
		//数字资源类型
		if(param.containsKey("drsctyp") && param.get("drsctyp") != null){
			String drsctyp = (String)param.get("drsctyp");
			if(!"".equals(drsctyp.trim())){
				criteria.andEqualTo("drsctyp", drsctyp.trim());
			}
		}
		
		//状态
		if(param.containsKey("drscstate") && param.get("drscstate") != null){
			String drscstate = (String)param.get("drscstate");
			if(!"".equals(drscstate)){
				criteria.andEqualTo("drscstate", drscstate);
			}
		}
		
		List<WhDrsc> list = this.drscMapper.selectByExample(example);
		
		// 取分页信息
        PageInfo<WhDrsc> pageInfo = new PageInfo<WhDrsc>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	};

	/**
	 * 根据主键查询在线点播
	 * @param id
	 * @return
     */
	public Object srchOne(String id) {
		return drscMapper.selectByPrimaryKey(id);
	}
}
