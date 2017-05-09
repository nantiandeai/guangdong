package com.creatoo.hn.services.admin.train;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.ActivityMapper;
import com.creatoo.hn.mapper.WhTraintplMapper;
import com.creatoo.hn.mapper.WhTraitmMapper;
import com.creatoo.hn.model.WhTraintpl;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.UploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class TrainMouldService {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	public ActivityMapper activityMapper;
	
	@Autowired
	public CommService commservice;
	
	@Autowired
	public WhTraintplMapper whTraintplMapper;
	/**
	 * 删除培训模板
	 * @param traid 培训模板标识
	 * @throws Exception
	 */
	public void deleteWhtra(String uploadPath,String tratplid)throws Exception{
		WhTraintpl whTra = this.whTraintplMapper.selectByPrimaryKey(tratplid);
		String trapic = whTra.getTrapic();
		String trabigpic = whTra.getTrabigpic();
		String trapersonfile = whTra.getTrapersonfile();
		String trateamfile = whTra.getTrateamfile();
		this.whTraintplMapper.deleteByPrimaryKey(tratplid);
		UploadUtil.delUploadFile(uploadPath, trapic);
		UploadUtil.delUploadFile(uploadPath, trabigpic);
		UploadUtil.delUploadFile(uploadPath, trapersonfile);
		UploadUtil.delUploadFile(uploadPath, trateamfile);
	}
	
	/**
	 * 新增培训模板
	 * @param whtra 培训模板信息
	 * @throws Exception
	 */
	public void addWhtra(WhTraintpl whtra)throws Exception{
		this.whTraintplMapper.insertSelective(whtra);
	}

	/**
	 * 更新培训模板信息
	 * @param whtra 培训模板信息
	 * @throws Exception
	 */
	public void updWhtra(WhTraintpl whtra)throws Exception{
		this.whTraintplMapper.updateByPrimaryKeySelective(whtra);
	}
	
	/**
	 * 分页查询培训模板信息
	 * @param param 条件参数
	 * @return 分页培训模板列表
	 * @throws Exception
	 */
	public Map<String, Object> paggingTratpl(Map<String, Object> param)throws Exception{
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.whTraintplMapper.selectTraintpl(param);
		
		// 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	};
	
	/**
	 * 分页查询培训模板信息
	 * @param param 条件参数
	 * @return 分页培训模板列表
	 * @throws Exception
	 */
	public Map<String, Object> paggingTrainitm(Map<String, Object> param)throws Exception{
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.activityMapper.selectTrainitm(param);
		
		// 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}
	
}
