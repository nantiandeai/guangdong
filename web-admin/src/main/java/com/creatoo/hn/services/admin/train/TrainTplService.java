package com.creatoo.hn.services.admin.train;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.creatoo.hn.mapper.ActivityMapper;
import com.creatoo.hn.mapper.WhTraMapper;
import com.creatoo.hn.mapper.WhTraitmMapper;
import com.creatoo.hn.model.WhAct;
import com.creatoo.hn.model.WhTra;
import com.creatoo.hn.model.WhTraitm;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.UploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * 培训模板服务类
 * @author wangxl
 * @version 20161010
 */
@Service
public class TrainTplService {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public WhTraMapper whTraMapper;
	
	@Autowired
	public WhTraitmMapper whTraitmMapper;
	
	@Autowired
	public ActivityMapper activityMapper;
	
	@Autowired
	public CommService commservice;
	
	/**
	 * 删除培训模板
	 * @param traid 培训模板标识
	 * @throws Exception
	 */
	public void deleteWhtra(String uploadPath,String traid)throws Exception{
		Example example = new Example(WhTraitm.class);
		example.createCriteria().andEqualTo("traid", traid);
		List<WhTraitm> list = whTraitmMapper.selectByExample(example);
		if(list != null && list.size() > 0){
			throw new Exception("请先删除培训模板下的所有培训!");
		}
		WhTra whTra = this.whTraMapper.selectByPrimaryKey(traid);
		String trapic = whTra.getTrapic();
		String trapic1 = whTra.getTrapic1();
		String trapic2 = whTra.getTrapic2();
		String userfile = whTra.getUserfile();
		String groupfile = whTra.getGroupfile();
		this.whTraMapper.deleteByPrimaryKey(traid);
		UploadUtil.delUploadFile(uploadPath, trapic);
		UploadUtil.delUploadFile(uploadPath, trapic1);
		UploadUtil.delUploadFile(uploadPath, trapic2);
		UploadUtil.delUploadFile(uploadPath, userfile);
		UploadUtil.delUploadFile(uploadPath, groupfile);
	}
	
	/**
	 * 新增培训模板
	 * @param whtra 培训模板信息
	 * @throws Exception
	 */
	public void addWhtra(WhTra whtra)throws Exception{
		this.whTraMapper.insertSelective(whtra);
	}

	/**
	 * 更新培训模板信息
	 * @param whtra 培训模板信息
	 * @throws Exception
	 */
	public void updWhtra(WhTra WhTra)throws Exception{
		//更新时，如果有培训批次属于待审以上的状态，不能修改
		Example example_itm = new Example(WhTraitm.class);
		example_itm.createCriteria().andEqualTo("traid", WhTra.getTraid()).andNotEqualTo("state", 0);
		List<WhTraitm> list = whTraitmMapper.selectByExample(example_itm);
		if(list != null && list.size() > 0){
			throw new Exception("此模板正在被使用,不能修改!");
		}
		//更新记录
		Example example = new Example(WhTra.class);
		example.createCriteria().andEqualTo("traid", WhTra.getTraid());
		this.whTraMapper.updateByExampleSelective(WhTra, example);
	}
	
	/**
	 * 根据培训模板标识查询详情
	 * @param traid 培训模板标识
	 * @return 培训模板详细
	 * @throws Exception
	 */
	public WhTra searchWhtrd(String traid)throws Exception{
		return this.whTraMapper.selectByPrimaryKey(traid);
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
		List<Map> list = this.activityMapper.selectTraintpl(param);
		
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
	
	/**
	 * 找到所有的培训模板标题
	 * @return
	 */
	public Object selAllTitle() {
		return this.whTraMapper.selectAll();
	}
}
