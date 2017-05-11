package com.creatoo.hn.services.home.agdgwgk;

import java.util.*;

import com.creatoo.hn.mapper.*;
import com.creatoo.hn.model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.WhConstance;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 馆务公开
 * @author wangxl
 * @version 2016.11.16
 */
@Service
public class GwgkService {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * 公用服务类
	 */
	@Autowired
	public CommService commservice;
	
	/**
	 * wh_zx_colinfo表
	 */
	@Autowired
	public WhZxColinfoMapper zxcolinfoMapper;
	
	/**
	 * wh_user_troupe表
	 */
	@Autowired
	public WhUserTroupeMapper troupeMapper;
	
	/**
	 * wh_user_troupe_user表
	 */
	@Autowired
	public WhUserTroupeuserMapper troupeUserMapper;
	
	/**
	 * wh_entsource表
	 */
	@Autowired
	public WhEntsourceMapper entSourceMapper;
	
	/**
	 * wh_user_teacher表
	 */
	@Autowired
	public WhUserTeacherMapper teacherMapper;
	
	/**
	 * wh_train表
	 */
	@Autowired
	public WhTrainMapper trainMapper;

	@Autowired
	public WhgTraMapper whgTraMapper;

	@Autowired
	private WhgYwiTypeMapper whgYwiTypeMapper;
	
	/**
	 * wh_Feedback表
	 */
	@Autowired
	public WhFeedbackMapper whFeedbackMapper;
	
	@Autowired
	public WhZxUploadMapper whZxUploadMapper;
	
	@Autowired
	public WhEntsourceMapper whEntsourceMapper;
	
	@Autowired
	private WhKeyMapper whKeyMapper;
	@Autowired
	private WhUserMapper whUserMapper;
	/**
	 * 自写Xml
	 */
	@Autowired
	private ActivityMapper amapper;
	
	
	/**
	 * 分页查询培训老师
	 * @param param 请求参数
	 * @return 培训老师列表
	 * @throws Exception
	 */
	public Map<String, Object> paggingTeacher(Map<String, Object> param)throws Exception{
		//分页信息
		int page = 1;
		int rows = 10;
		if(param != null && param.get("page") != null && param.get("rows") != null){
			page = Integer.parseInt((String)param.get("page"));
			rows = Integer.parseInt((String)param.get("rows"));
		}
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		
		//分页查询政策法规
		Example example = new Example(WhUserTeacher.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("teacherstate", 3);//已发布
		
		//艺术类型
		if(param != null && param.get("artid") != null && !"".equals((String)param.get("artid"))){
			String artid = (String)param.get("artid");
			//criteria.andEqualTo("teacherarttyp", artid);
			criteria.andLike("teacherarttyp", "%"+artid+"%");
		}
		
		//区域
		if(param != null && param.get("areaid") != null && !"".equals((String)param.get("areaid"))){
			String areaid = (String)param.get("areaid");
			criteria.andEqualTo("teacherarea", areaid);
		}
		
		//关键字
		if(param != null && param.get("srchkey") != null && !"".equals((String)param.get("srchkey"))){
			String srchkey = (String)param.get("srchkey");
			criteria.andLike("teachername", "%"+srchkey+"%");
		}
		
		example.setOrderByClause("teacheropttime desc, teacherid desc");//发布时间排序
		List<WhUserTeacher> list = this.teacherMapper.selectByExample(example);
		for(int i = 0; i< list.size(); i++){
			String teachertype = list.get(i).getTeachertype();
			Example example1 = new Example(WhgYwiType.class);
			example1.createCriteria().andIn("id", Arrays.asList(teachertype.split(",")));
			List<WhgYwiType> whgYwiType = this.whgYwiTypeMapper.selectByExample(example1);
			String a = "";
			String b = "";
			if(whgYwiType.size() >0){
				for(int j = 0; j<Arrays.asList(teachertype.split(",")).size(); j++){
					a += b+whgYwiType.get(j).getName();
					b = ",";
				}
				list.get(i).setTeachertype(a);
			}

		}
        PageInfo<WhUserTeacher> pageInfo = new PageInfo<WhUserTeacher>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
        rtnMap.put("page", page);
		return rtnMap;
	}
	
	/**
	 * 培训老师详情
	 * @param id  老师标识
	 * @return
	 * @throws Exception
	 */
	public WhUserTeacher queryOneTeacher(String id)throws Exception{
		WhUserTeacher teacher = null;
		
		//
		Example example = new Example(WhUserTeacher.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("teacherstate", 3);//已发布
		criteria.andEqualTo("teacherid", id);//id
		List<WhUserTeacher> list = this.teacherMapper.selectByExample(example);
		for(int i = 0; i< list.size(); i++){
			String teachertype = list.get(i).getTeachertype();
			Example example1 = new Example(WhgYwiType.class);
			example1.createCriteria().andIn("id", Arrays.asList(teachertype.split(",")));
			List<WhgYwiType> whgYwiType = this.whgYwiTypeMapper.selectByExample(example1);
			String a = "";
			String b = "";
			if(whgYwiType.size() >0){
				for(int j = 0; j<Arrays.asList(teachertype.split(",")).size(); j++){
					a += b+whgYwiType.get(j).getName();
					b = ",";
				}
				list.get(i).setTeachertype(a);
			}

		}
		if(list != null && list.size() > 0){
			teacher = list.get(0);
		}
				
		return teacher;
	}
	
	/**
	 * 老师课程列表
	 * @param id 老师标识
	 * @return 老师课程列表 
	 * @throws Exception
	 */
	public List<WhgTra> queryTeacherTrain(String id)throws Exception{
		Example example = new Example(WhgTra.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("state", 6);
		c.andEqualTo("recommend",1);
		c.andNotEqualTo("id", id);
		example.setOrderByClause("statemdfdate desc");
		PageHelper.startPage(1, 3);
		return this.whgTraMapper.selectByExample(example);
	}
	
	
	/**
	 * 分页查询艺术团队
	 * @param param 请求参数
	 * @return 艺术团队列表
	 * @throws Exception
	 */
	public Map<String, Object> paggingTroupe(Map<String, Object> param)throws Exception{
		//分页信息
		int page = 1;
		int rows = 10;
		if(param != null && param.get("page") != null && param.get("rows") != null){
			page = Integer.parseInt((String)param.get("page"));
			rows = Integer.parseInt((String)param.get("rows"));
		}
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		
		//分页查询政策法规
		Example example = new Example(WhUserTroupe.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("troupestate", 3);//已发布
		example.setOrderByClause("troupeopttime desc, troupeid desc");//发布时间排序
		List<WhUserTroupe> list = this.troupeMapper.selectByExample(example);
		
		// 取分页信息
        PageInfo<WhUserTroupe> pageInfo = new PageInfo<WhUserTroupe>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
        rtnMap.put("page", page);
		return rtnMap;
	}
	
	/**
	 * 获团队详情
	 * @param id 团队ID
	 * @return 团队详情
	 * @throws Exception
	 */
	public WhUserTroupe queryOneTroupe(String id)throws Exception{
		WhUserTroupe troupe = null;
		
		//分页查询政策法规
		Example example = new Example(WhUserTroupe.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("troupestate", 3);//已发布
		criteria.andEqualTo("troupeid", id);//id
		List<WhUserTroupe> list = this.troupeMapper.selectByExample(example);
		if(list != null && list.size() > 0){
			troupe = list.get(0);
		}
				
		return troupe;
	}
	
	/**
	 * 团队成员列表
	 * @param id 团队标识
	 * @return 团队成员列表
	 * @throws Exception
	 */
	public List<WhUserTroupeuser> queryAllTroupeUsers(String id)throws Exception{
		List<WhUserTroupeuser> users = null;
		
		Example example = new Example(WhUserTroupeuser.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("tustate", 1);//有效
		criteria.andEqualTo("tutroupeid", id);//有效
		example.setOrderByClause("tuid");
		users = this.troupeUserMapper.selectByExample(example);
		
		return users;
	}
	
	/**
	 * 团队资源(图片/视频)列表
	 * @param id 团队标识
	 * @param type 图片/视频/音频
	 * @return 团队资源(图片/视频)列表
	 * @throws Exception
	 */
	public List<WhEntsource> queryAllTroupeSources(String id, String type)throws Exception{
		List<WhEntsource> sources = null;
		
		Example example = new Example(WhEntsource.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("enttype", type);//图片/视频/音频
		criteria.andEqualTo("reftype", "2016102400000001");
		criteria.andEqualTo("refid", id);
		example.setOrderByClause("entid");
		sources = this.entSourceMapper.selectByExample(example);
		
		return sources;
	}
	
	
	/**
	 * 分页查询资讯信息
	 * @param param 分页参数
	 * @param type 栏目类型
	 * @return 分页信息
	 * @throws Exception
	 */
	public Map<String, Object> paggingColinfo(Map<String, Object> param, String type)throws Exception{
		//分页信息
		int page = 1;
		int rows = 10;
		if(param != null && param.get("page") != null && param.get("rows") != null){
			page = Integer.parseInt((String)param.get("page"));
			rows = Integer.parseInt((String)param.get("rows"));
		}
		String sort= WhConstance.getSysProperty("SYS_ORDER");
		
		//带条件的分页查询
		PageHelper.startPage(page, rows, "totop desc,clnfcrttime desc, clnfid desc");
		
		//分页查询政策法规
		Example example = new Example(WhZxColinfo.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("clnfstata", 3);//已发布
		criteria.andEqualTo("clnftype", type);//栏目类型为政策法规
		if ("0".equals(sort)) {
			example.setOrderByClause("totop desc,clnfcrttime desc, clnfid desc");//发布时间排序
		}else {
			example.setOrderByClause("totop desc,clnfcrttime desc");//发布时间排序
		}
		
//		example.setOrderByClause("clnfcrttime desc, clnfid desc");//发布时间排序
		
		List<WhZxColinfo> list = this.zxcolinfoMapper.selectByExample(example);
		
		// 取分页信息
        PageInfo<WhZxColinfo> pageInfo = new PageInfo<>(list);
       
        Map<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
        rtnMap.put("pageSize", pageInfo.getPageSize());
        rtnMap.put("page", page);
		return rtnMap;
	};
	
	/**
	 * 按栏目类型查询资讯内容
	 * @param type 栏目类型
	 * @return 资讯列表
	 * @throws Exception
	 */
	public List<WhZxColinfo> queryAllColinfo(String type)throws Exception{
		//分页查询政策法规
		Example example = new Example(WhZxColinfo.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("clnfstata", 3);//已发布
		criteria.andEqualTo("clnftype", type);//栏目类型为政策法规
		example.setOrderByClause("clnfidx asc,clnfcrttime desc");//发布时间排序
		
		return this.zxcolinfoMapper.selectByExample(example);
	};
	
	/**
	 * 根据类型查询资讯内容
	 * @param id 标识
	 * @param type 类型
	 * @return 资讯内容
	 * @throws Exception
	 */
	public WhZxColinfo queryOneColinfo(String id, String type)throws Exception{
		WhZxColinfo colinfo = null;
		
		//分页查询政策法规
		Example example = new Example(WhZxColinfo.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("clnfstata", 3);//已发布
		criteria.andEqualTo("clnftype", type);//栏目类型为政策法规
		criteria.andEqualTo("clnfid", id);//id
		List<WhZxColinfo> list = this.zxcolinfoMapper.selectByExample(example);
		if(list != null && list.size() > 0){
			colinfo = list.get(0);
		}
		
		return colinfo;
	}
	
	/**
	 * 下一篇资讯内容
	 * @param id 标识
	 * @param type 类型
	 * @return 资讯内容
	 * @throws Exception
	 */
	public WhZxColinfo queryNextColinfo(String id, String type)throws Exception{
		//申明一个对象返回数据
		WhZxColinfo nextColinfo = null;
		
		//查询政策法规
		WhZxColinfo colinfo = null;
		Example example = new Example(WhZxColinfo.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("clnfstata", 3);//已发布
		criteria.andEqualTo("clnftype", type);//栏目类型为政策法规
		criteria.andEqualTo("clnfid", id);//id
		List<WhZxColinfo> list = this.zxcolinfoMapper.selectByExample(example);
		//判读集合不为空
		if(list != null && list.size() > 0){
			//取第一个数据
			colinfo = list.get(0);
		}
		
		//获取下一篇 对象不为空
		if(colinfo != null){
			//得到对象的时间
			Date date = colinfo.getClnfcrttime();//colinfo.getClnfopttime();
			
			//查询同一时间发布的 分页为1行一条 当前时间 id 排序
			PageHelper.startPage(1, 1, "clnfcrttime desc, clnfid desc");
			Example eap = new Example(WhZxColinfo.class);
			Criteria cri = eap.createCriteria();
			cri.andEqualTo("clnfstata", 3);//已发布
			cri.andEqualTo("clnftype", type);//栏目类型为政策法规
			cri.andEqualTo("clnfcrttime", date);
			cri.andLessThan("clnfid", id);
			List<WhZxColinfo> eaplist = this.zxcolinfoMapper.selectByExample(eap);
			//分页插件
			PageInfo<WhZxColinfo> pi1 = new PageInfo<WhZxColinfo>(eaplist);
			List<WhZxColinfo> rtnLst1 = pi1.getList();
			if(rtnLst1 != null && rtnLst1.size() == 1){
				nextColinfo = rtnLst1.get(0);
			}else{//不同时间段的
				//查询指定时间点之后的一条记录
				PageHelper.startPage(1, 1, "clnfcrttime desc, clnfid desc");
				
				Example example2 = new Example(WhZxColinfo.class);
				Criteria criteria2 = example2.createCriteria();
				criteria2.andEqualTo("clnfstata", 3);//已发布
				criteria2.andEqualTo("clnftype", type);//栏目类型为政策法规
				criteria2.andLessThan("clnfcrttime", date);
				List<WhZxColinfo> list2 = this.zxcolinfoMapper.selectByExample(example2);
				PageInfo<WhZxColinfo> pageInfo = new PageInfo<WhZxColinfo>(list2);
				List<WhZxColinfo> resultList = pageInfo.getList();
				if(resultList != null && resultList.size() == 1){
					nextColinfo = resultList.get(0);
				}else{
					PageHelper.startPage(1, 1, "clnfcrttime desc, clnfid desc");
					Example example3 = new Example(WhZxColinfo.class);
					Criteria criteria3 = example3.createCriteria();
					criteria3.andEqualTo("clnfstata", 3);//已发布
					criteria3.andEqualTo("clnftype", type);//栏目类型为政策法规
					List<WhZxColinfo> list3 = this.zxcolinfoMapper.selectByExample(example3);
					PageInfo<WhZxColinfo> pageInfo2 = new PageInfo<WhZxColinfo>(list3);
					List<WhZxColinfo> resultList2 = pageInfo2.getList();
					if(resultList2 != null && resultList2.size() == 1){
						nextColinfo = resultList2.get(0);
					}
				}
			}
		}
		
		return nextColinfo;
	}
	
	/**
	 * 获取相关资讯
	 * @param id 标识
	 * @param type 类型
	 * @return 资讯列表
	 * @throws Exception
	 */
	public List<WhZxColinfo> queryREFColinfo(String id, String type)throws Exception{
		List<WhZxColinfo> colinfos = null;
			
		//带条件的分页查询
		PageHelper.startPage(1, 4);
		
		Example example2 = new Example(WhZxColinfo.class);
		Criteria criteria2 = example2.createCriteria();
		criteria2.andEqualTo("clnfstata", 3);//已发布
		criteria2.andEqualTo("clnftype", type);//栏目类型
		criteria2.andNotEqualTo("clnfid", id);
		example2.setOrderByClause("clnfcrttime desc, clnfid desc");//发布时间排序
		List<WhZxColinfo> list2 = this.zxcolinfoMapper.selectByExample(example2);
		
		// 取分页信息
        PageInfo<WhZxColinfo> pageInfo = new PageInfo<WhZxColinfo>(list2);
        colinfos = pageInfo.getList();
		
		return colinfos;
	}
	/**
	 * 保存意见反馈信息
	 * @param whf
	 */
	public void addinfo(WhFeedback whf)throws Exception {
		this.whFeedbackMapper.insert(whf);
		
	}
	/**
	 * 上传
	 * @param id
	 * @return
	 */
	public List<WhZxUpload> selecup(String id)throws Exception {
		Example example = new Example(WhZxUpload.class);
		Criteria Criteria = example.createCriteria();
		Criteria.andEqualTo("uptype", id);
		Criteria.andEqualTo("upstate", 1);
		List<WhZxUpload> whup =this.whZxUploadMapper.selectByExample(example);
		return whup;
		
	}
	/**
	 * 图片
	 * @param id
	 * @param enttype
	 * @return
	 */
	public List<WhEntsource> selecent(String id, String enttype,String reftype)throws Exception {
		Example example = new Example(WhEntsource.class);
		Criteria Criteria = example.createCriteria();
		Criteria.andEqualTo("refid", id);
		Criteria.andEqualTo("enttype", enttype);
		Criteria.andEqualTo("reftype", reftype);
		List<WhEntsource> whe =this.whEntsourceMapper.selectByExample(example);
		return whe;
	}
	/**
	 * 视频
	 * @param id
	 * @param types
	 * @return
	 */
	public List<WhEntsource> selecsource(String id, String types,String reftype)throws Exception {
		Example example = new Example(WhEntsource.class);
		Criteria Criteria = example.createCriteria();
		Criteria.andEqualTo("refid", id);
		Criteria.andEqualTo("enttype", types);
		Criteria.andEqualTo("reftype", reftype);
		List<WhEntsource> whe =this.whEntsourceMapper.selectByExample(example);
		return whe;
	}
	/**
	 * 音乐
	 * @param id
	 * @param clazz
	 * @return
	 */
	public List<WhEntsource> selecwhent(String id, String clazz,String reftype)throws Exception {
		Example example = new Example(WhEntsource.class);
		Criteria Criteria = example.createCriteria();
		Criteria.andEqualTo("refid", id);
		Criteria.andEqualTo("enttype", clazz);
		Criteria.andEqualTo("reftype", reftype);
		List<WhEntsource> whe =this.whEntsourceMapper.selectByExample(example);
		return whe;
	}
	/**
	 * 查询用户表
	 * @param id
	 * @return 
	 */
	public WhUser uprevrse(String id)throws Exception {
		return this.whUserMapper.selectByPrimaryKey(id);
		
	}
}
