package com.creatoo.hn.services.home.userCenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.ActivityMapper;
import com.creatoo.hn.mapper.WhActivitybmMapper;
import com.creatoo.hn.model.WhActivitybm;
import com.creatoo.hn.utils.UploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class MyEventrollservice {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public ActivityMapper activityMapper; 
	
	@Autowired
	private WhActivitybmMapper bmMapper;

	/**
	 * 查询我的报名
	 * @param param 条件和分页参数
	 * @return 分页对象
	 * @throws Exception
	 */
	public Map<String, Object> eventbmList(Map<String, Object> param)throws Exception{
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.activityMapper.selectMyeventenroll(param);
		
		// 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);
        System.out.println("============pageInfo.getTotal()"+pageInfo.getTotal());
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	};
	
	/**
	 * 删除我的报名
	 * @param id 报名ID
	 * @throws Exception
	 */
	public void removeMyenroll(String id, HttpServletRequest request)throws Exception{
		WhActivitybm actbm = this.bmMapper.selectByPrimaryKey(id);
		String dbfile = actbm.getActbmfilepath();
		
		Example example = new Example(WhActivitybm.class);
		example.createCriteria().andEqualTo("actbmid", id);
		this.bmMapper.deleteByExample(example);
		
		//处理报名文件删除
		if (dbfile!=null && !"".equals(dbfile)){
			UploadUtil.delUploadFile(UploadUtil.getUploadPath(request), dbfile);
		}
	}
}
