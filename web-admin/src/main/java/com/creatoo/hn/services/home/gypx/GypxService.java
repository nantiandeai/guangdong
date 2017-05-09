package com.creatoo.hn.services.home.gypx;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import com.creatoo.hn.mapper.GypxMapper;
import com.creatoo.hn.mapper.WhEntsourceMapper;
import com.creatoo.hn.model.WhEntsource;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class GypxService {
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private GypxMapper gypxMapper;
	
	@Autowired
	private WhEntsourceMapper entsrcMapper;
	
	
	/** 公益培训模块首页列表数据查询
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Object searchHpList(WebRequest request) throws Exception{
		return this.gypxMapper.selectHpList();
	}
	
	/** 公益培训列表页数据查询
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object searchGypxList(WebRequest request) throws Exception{
		Map<String, Object> params = ReqParamsUtil.parseRequest(request);
		if (params.containsKey("datePart") && params.get("datePart")!=null){
			params.put("nowdate", new Date(System.currentTimeMillis()));
		}
		
		int page = 1;
		int size = 9;
		if (params.containsKey("page") && params.get("page")!=null){
			page = Integer.valueOf(params.get("page").toString());
		}
		if (params.containsKey("size") && params.get("size")!=null){
			size = Integer.valueOf(params.get("size").toString());
		}
		
		PageHelper.startPage(page, size);
		List<?> list = this.gypxMapper.selectGypxList(params);
		PageInfo pinfo = new PageInfo(list);

        Map<String, Object> res = new HashMap<String, Object>();
        res.put("total", pinfo.getTotal());
        res.put("rows", pinfo.getList());
        res.put("page", pinfo.getPageNum());
		
		return res;
	}
	
	/** 提取指定ID的公益培训
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> findGypxInfo(String id) throws Exception{
		return this.gypxMapper.findGypxInfo(id);
	}
	
	/** 查询指定实体ID的相关类型资源
	 * @param enttype
	 * @param refid
	 * @return
	 * @throws Exception
	 */
	public Object selectEntSrc(String enttype, String refid) throws Exception{
		Example example = new Example(WhEntsource.class);
		
		Criteria c = example.createCriteria();
		if (enttype!=null){
			c.andEqualTo("enttype", enttype);
		}
		if (refid!=null){
			c.andEqualTo("refid", refid);
		}
		
		return this.entsrcMapper.selectByExample(example);
	}
	
	/** 查询课时日期与当月相关的集合
	 * @return
	 * @throws Exception
	 */
	public Object selectTraitmtimeMonth() throws Exception{
		//当前月加前后7天参数
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		Calendar cld = Calendar.getInstance();
		cld.set(Calendar.DAY_OF_MONTH, 1);
		cld.add(Calendar.WEEK_OF_YEAR, -1);
		String beginTradate = sdf.format( cld.getTime() );
		
		cld.add(Calendar.MONTH, 1);
		cld.set(Calendar.DAY_OF_MONTH, cld.getActualMaximum(Calendar.DAY_OF_MONTH));
		cld.add(Calendar.WEEK_OF_YEAR, 1);
		String endTradate = sdf.format( cld.getTime() );
		
		return this.gypxMapper.selectTradate4Month(beginTradate, endTradate);
	}
	
	
	/** 按指定课程日期查询相关的培训信息集
	 * @param day
	 * @return
	 */
	public Object selectGypx4Day(String day) throws Exception{
		return this.gypxMapper.selectGypx4Day(day);
	}
	
	/** 查找公益培训首页图
	 * @return
	 * @throws Exception
	 */
	public Object findGypxAdv(){
		try {
			List<?> list = this.gypxMapper.selectGypxAdv();
			if (list!=null && list.size()>0){
				return list.get(0);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/** 查询公益培训模块首页的资讯动态信息配置
	 * @return
	 * @throws Exception
	 */
	public Object selectGypxZx() throws Exception{
		return this.gypxMapper.selectGypxZx();
	}
	
	
	/** 公益培训课程表查看
	 * @param traitmid
	 * @return
	 * @throws Exception
	 */
	public Object selectGypxTimes(String traitmid) throws Exception{
		return this.gypxMapper.selectPXTimes(traitmid);
	}
}
