package com.creatoo.hn.services.home.agdpxyz;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.creatoo.hn.mapper.*;
import com.creatoo.hn.model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.WhConstance;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import sun.util.calendar.LocalGregorianCalendar;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 培训驿站
 * @author wangxl
 * @version 2016.11.16
 */
@Service
public class PxyzService {
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
	public WhgTraMapper whTrainMapper;
	
	@Autowired
	private WhDrscMapper whDrscMapper;
	
	@Autowired
	private WhCfgListMapper cfgListMapper;
	
	@Autowired
	private WhCfgAdvMapper whCfgAdvMapper;

	@Autowired
	private WhgTraCourseMapper whgTraCourseMapper;

	@Autowired
	private WhgTraEnrolMapper whgTraEnrolMapper;

	@Autowired
	private WhUserTeacherMapper whUserTeacherMapper;

	@Autowired
	private WhgYwiLbtMapper whgYwiLbtMapper;
	
	/**
	 * 在线报名
	 * @param param
	 * @return
	 */
	public Map<String, Object> paggingzxbm(Map<String, Object> param)throws Exception {
		//分页信息
		int page = 1;
		int rows = 9;
		if(param != null && param.get("page") != null && param.get("rows") != null){
			page = Integer.parseInt((String)param.get("page"));
			rows = Integer.parseInt((String)param.get("rows"));
		}
		//区域
		String quyu = (String) param.get("traarea");
		//艺术类型
		String leixing = (String) param.get("typid");
		//分馆
		String cult = (String) param.get("cult");
		//培训类型
		String tratype = (String) param.get("tratype");
		//搜索标题
		String title = (String) param.get("tratitle");
		//排序
		int paixu = 0;
		if (param.get("time") != null && ((String)param.get("time")).matches("^\\d+$")){
			paixu = Integer.parseInt((String)param.get("time"));
		}
		
		//String order = WhConstance.getSysProperty("SYS_ORDER");
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		
		//分页查询培训报名
		Example example = new Example(WhgTra.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("state", 6);//已发布
		//c.andEqualTo("cultid","TOP");
		if (!"".equals(cult) && cult != null ) {
			if("TOP".equals(cult)){
				//c.andEqualTo("cultid","");
				c.andEqualTo("cultid",(String) param.get("cult"));
			}
		}else{
			c.andEqualTo("cultid","TOP");
		}

		if (quyu != null && !quyu.isEmpty()) {
			c.andEqualTo("area",quyu);
		}
		if (leixing != null && !leixing.isEmpty()) {
			c.andEqualTo("arttype",leixing);
		}
		if (tratype != null && !tratype.isEmpty()) {
			c.andEqualTo("etype",tratype);
		}
		if (paixu == 2) {
			c.andGreaterThanOrEqualTo("endtime", new Date()).andLessThanOrEqualTo("starttime",new Date());
		}
		if (paixu == 1) {
			c.andGreaterThanOrEqualTo("starttime", new Date());
		}
		if (paixu == 0) {
			c.andIsNotNull("id");
		}
		if (title != null && !title.isEmpty()) {
			//c.andEqualTo("tratitle", title);
			c.andLike("title", "%"+title+"%");
		}

		example.setOrderByClause("statemdfdate desc");

		List<WhgTra> list = this.whTrainMapper.selectByExample(example);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getAddress().length() > 20) {
				String address = list.get(i).getAddress().substring(0, 20);
				list.get(i).setAddress(address);
			}
			
		}

		for(int j =0; j<list.size(); j++){
			if(!"".equals(list.get(j).getAge()) && list.get(j).getAge() != null){
				String[] age = list.get(j).getAge().split(",");
				list.get(j).setAge(age[0]+"-"+age[1]);
			}

		}
		// 取分页信息
        PageInfo<WhgTra> pageInfo = new PageInfo<WhgTra>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
        rtnMap.put("page", pageInfo.getPageNum());
        rtnMap.put("pageSize", pageInfo.getPageSize());

		return rtnMap;
	}
	
	/**
	 * 在线点播信息查询
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> findvod(Map<String, Object> param)throws Exception {
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		String order = WhConstance.getSysProperty("SYS_ORDER");
		param.put("order", order);
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		
		List<Map> list = this.whDrscMapper.selvodList(param);
//		String intro = "";
//		for (int i = 0; i < list.size(); i++) {
//			intro = (String) list.get(i).get("drscintro");
//			String _intro = intro.substring(0, 40);
//			list.get(i).put("drscintro", _intro);
//		}
		
		// 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}

	/**
	 * 查询数字资源的详情信息
	 * @param drscid
	 * @return
	 */
	public WhDrsc findVodInfo(String drscid)throws Exception {
		//drscid = "2016112100000001";
		return this.whDrscMapper.selVodInfo(drscid);
	}

	/**
	 * 查询数字资源的推荐课程
	 * @return
	 * @throws Exception
	 */
	public List<WhDrsc> selNewVod(String drscid)throws Exception {
		Example example = new Example(WhDrsc.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("drscstate", 3);
		c.andNotEqualTo("drscid", drscid);
		example.setOrderByClause("drscopttime desc");
		PageHelper.startPage(1, 3);
		return this.whDrscMapper.selectByExample(example);
		//return this.whDrscMapper.selNewVod(drscid);
	}

	/**
	 * 查询在线报名详情信息
	 * @param traid
	 * @return
	 */
	public WhgTra selTrain(String traid)throws Exception {
		
		return this.whTrainMapper.selectByPrimaryKey(traid);
	}

	/**
	 * 在线报名详情推荐课程
	 * @return
	 */
	public List<WhgTra> selNewTrain(String traid)throws Exception {
		Example example = new Example(WhgTra.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("state", 6);
		c.andEqualTo("recommend",1);
		c.andNotEqualTo("id", traid);
		example.setOrderByClause("statemdfdate desc");
		PageHelper.startPage(1, 3);
		return this.whTrainMapper.selectByExample(example);
	}

	/**
	 * 查询首页在线点播
	 * @return
	 */
	public List<WhDrsc> findVodList()throws Exception {
		Example example = new Example(WhDrsc.class);
		Criteria c = example.createCriteria();

		c.andEqualTo("drscstate",3);
		example.setOrderByClause("drscopttime desc");
		List<WhDrsc> list = this.whDrscMapper.selectByExample(example);
		return list;
	}

	/**
	 * 首页查询热门培训
	 * @return
	 */
	public List<WhgTra> selTrainList()throws Exception {
		Example example = new Example(WhgTra.class);
		Criteria c = example.createCriteria();

		c.andEqualTo("state", 6);//已发布
		example.setOrderByClause("starttime asc");
		List<WhgTra> list = this.whTrainMapper.selectByExample(example);
		return list;
	}

	/**
	 * 首页查询培训师资
	 * @return
	 */
	public List<WhUserTeacher> selTeacher()throws Exception {
		Example example = new Example(WhUserTeacher.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("teacherstate",3);

		example.setOrderByClause("teacheropttime desc");
		List<WhUserTeacher> list = this.whUserTeacherMapper.selectByExample(example);
		
		return list;
	}

	/**
	 * 获取首页新闻图片配置
	 * @return
	 */
	public List<WhCfgList> getzxpz()throws Exception {
		Example example = new Example(WhCfgList.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("cfgpagetype","2016102800000002");
		c.andEqualTo("cfgenttype","2016102800000001");
		c.andEqualTo("cfgstate",1);
		PageHelper.startPage(1, 3);
		List<WhCfgList> list = this.cfgListMapper.selectByExample(example);
		
		return list;
	}

	/**
	 * 查找广告图
	 * @return
	 */
	public List<WhgYwiLbt> findAdv()throws Exception {
		Example example = new Example(WhgYwiLbt.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("delstate",0);
		c.andEqualTo("type",5);
		c.andEqualTo("state",1);
		example.setOrderByClause("statemdfdate desc");
		return this.whgYwiLbtMapper.selectByExample(example);
	}

	/**
	 * 根据老师ID找到培训图片
	 * @param teacherid
	 * @return
	 */
	public List<WhgTra> selTrainPic(String teacherid) {
		Example example = new Example(WhgTra.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("teacherid", teacherid);
		c.andEqualTo("state", 6);
		return this.whTrainMapper.selectByExample(example);
	}

	/**
	 * 查询已报名人数
	 * @param traid
	 * @return
	 */
	public int selCount(String traid) {
		Example example = new Example(WhgTraEnrol.class);
		example.createCriteria().andEqualTo("traid",traid).andIn("state", Arrays.asList(1,4,6));
		return this.whgTraEnrolMapper.selectCountByExample(example);
	}


	/**
	 * 查询课程表
	 * @param param
	 * @return
     */
	public Map<String, Object> selectCourse(Map<String, Object> param) throws Exception{
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		String traid = (String)param.get("traid");
		Example example = new Example(WhgTraCourse.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("traid",traid);
		c.andEqualTo("state",1);
		example.setOrderByClause("starttime asc");
		PageHelper.startPage(page,rows);
		List<WhgTraCourse> list = this.whgTraCourseMapper.selectByExample(example);
		// 取分页信息
		PageInfo<WhgTraCourse> pageInfo = new PageInfo<WhgTraCourse>(list);

		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("total", pageInfo.getTotal());
		rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}

	/**
	 * 查找培训相关的老师
	 * @param traid
	 * @return
     */
	public List<String> findTeacher(String traid) throws Exception {
		WhgTra tra = this.whTrainMapper.selectByPrimaryKey(traid);
		String teacherid = tra.getTeacherid();
		List list = new ArrayList();
		List name = new ArrayList();
		if(teacherid == null){
			return new ArrayList();
		}
		String[] tid = teacherid.split(",");
		for (int i = 0; i < tid.length; i++) {
			list.add(tid[i]);
		}
		//list.add(teacherid.split(","));
		for(int i = 0; i<list.size(); i++){
			String id = (String)list.get(i);
			Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(id);
			if(mer.find() == false){
				name.add(list.get(i));
			}else{
				Example example = new Example(WhUserTeacher.class);
				example.createCriteria().andEqualTo("teacherid",Arrays.asList( list.get(i) )).andEqualTo("teacherstate",3);
				List<WhUserTeacher> teacher = this.whUserTeacherMapper.selectByExample(example);

				for(int j = 0; j<teacher.size(); j++){
					name.add(teacher.get(j).getTeachername());
				}
			}
		}

		return name;
	}

	/**
	 * 根据老师ID查询关联培训信息
	 * @param id
	 * @return
     */
	public List<WhgTra> selTrainByTeacher(String id) throws Exception {
		Example example = new Example(WhgTra.class);
		example.createCriteria().andLike("teacherid","%"+id+"%").andEqualTo("state",6);
		return whTrainMapper.selectByExample(example);
	}
}
