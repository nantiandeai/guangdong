package com.creatoo.hn.services.admin.feiyi;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.creatoo.hn.ext.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.creatoo.hn.actions.admin.feiyi.FeiyiAction;
import com.creatoo.hn.mapper.ActivityMapper;
import com.creatoo.hn.mapper.WhDecprojectMapper;
import com.creatoo.hn.mapper.WhSuccessorMapper;
import com.creatoo.hn.mapper.WhSuorproMapper;
import com.creatoo.hn.model.WhActivity;
import com.creatoo.hn.model.WhActivitybm;
import com.creatoo.hn.model.WhActivityitm;
import com.creatoo.hn.model.WhCfgList;
import com.creatoo.hn.model.WhDecproject;
import com.creatoo.hn.model.WhSuccessor;
import com.creatoo.hn.model.WhSuorpro;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.UploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class FeiyiService {
	
	/**
	 * 名录项目mapper
	 */
	@Autowired
	private WhDecprojectMapper decprojectMapper;
	
	/**
	 * 传承人mapper
	 */
	@Autowired
	private WhSuccessorMapper successorMapper;
	
	/**
	 * 自写xml语句
	 */
	@Autowired
	private ActivityMapper acMapper;
	
	/**
	 * 公共 工具
	 * 
	 */
	@Autowired
	private CommService commService;
	
	/**
	 * 名录 传承人关联
	 */
	@Autowired
	private WhSuorproMapper suorproMapper;
	/**
	 * 名录活动数据加载
	 * @return
	 */
	public Object loadMinglu(String suorid,int rows,int page,WebRequest request)throws Exception{
		Map<String,Object> params = ReqParamsUtil.parseRequest(request);

		List<?> list=new ArrayList<Object>();
		if(suorid != null && !"".equals(suorid)){
			Example example = new Example(WhSuorpro.class);
			example.or().andEqualTo("spsuorid", suorid);
			List<WhSuorpro> listsp = this.suorproMapper.selectByExample(example);
			List<String> listproid =new  ArrayList<String>();
			for(int i = 0; i < listsp.size(); i++) {
				listproid.add(listsp.get(i).getSpmlproid());
			}
			if(listproid != null && !"".equals(listproid) && listproid.size() != 0){
				params.put("list", listproid);
				PageHelper.startPage(page, rows);
				list = this.acMapper.selectminglu(params);
			}
		}else{
			PageHelper.startPage(page, rows);
			list= this.acMapper.selectminglu(params);
		}
		PageInfo pinfo = new PageInfo(list);
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("total", pinfo.getTotal());
		res.put("rows", pinfo.getList());
		return res;
	}
	
	
	/**
	 * 传承人下拉框名录数据加载
	 * @param suorid
	 * @param rows
	 * @param page
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Object loadMingluleavl(String suorid, int rows, int page, WebRequest request) throws Exception {
		Map<String, Object> params = ReqParamsUtil.parseRequest(request);

		Example example2 = new Example(WhSuorpro.class);
		example2.or().andEqualTo("spsuorid", suorid);
		List<WhSuorpro> listsp = this.suorproMapper.selectByExample(example2);
		List<String> listmlid = new ArrayList<String>();
		for (int i = 0; i < listsp.size(); i++) {
			listmlid.add(listsp.get(i).getSpmlproid());
		}
		Example example = new Example(WhDecproject.class);
		Criteria c = example.createCriteria();
		if (listmlid != null && listmlid.size() != 0) {
			c.andNotIn("mlproid", listmlid);
		}
		if (params.get("mlproshortitel") != null && !"".equals(params.get("mlproshortitel"))) {
			//c.andEqualTo("mlproshortitel", params.get("mlproshortitel"));
			c.andLike("mlproshortitel", "%" + params.get("mlproshortitel") + "%");
		}
		if (params.get("mlprolevel") != null && !"".equals(params.get("mlprolevel"))) {
			c.andEqualTo("mlprolevel", params.get("mlprolevel"));
		}
		if (params.get("mlprostate") != null) {
			c.andEqualTo("mlprostate", params.get("mlprostate"));
		}
		if (params.get("order") != null && !"".equals(params.get("order")) && params.get("sort") != null && !"".equals(params.get("sort"))) {
			example.setOrderByClause(params.get("order") + " " + params.get("sort"));
		}
		PageHelper.startPage(page, rows);
		List<?> list = this.decprojectMapper.selectByExample(example);
		PageInfo pinfo = new PageInfo(list);
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("total", pinfo.getTotal());
		res.put("rows", pinfo.getList());
		return res;
	}
	
	/**
	 * 传承人数据加载
	 * @return
	 */
	public Object loadSuccessor(int rows,int page,WebRequest request)throws Exception{
		Map<String,Object> params = ReqParamsUtil.parseRequest(request);
//		Example example = new Example(WhSuccessor.class);
//		Criteria c = example.createCriteria();
//		if (params.get("suorname") != null && !"".equals(params.get("suorname"))) {
//			c.andLike("suorname", "%" + params.get("suorname") + "%");
//		}
//		c.andEqualTo(params);
		PageHelper.startPage(page, rows);
		List<Map> list = this.acMapper.selectSuccessor(params);
		
		PageInfo pinfo = new PageInfo(list);
		Map<String, Object> res = new HashMap<>();
		res.put("total", pinfo.getTotal());
		res.put("rows", pinfo.getList());
		return res;
	}

	/**
	 * 查询所有名录
	 * @return
	 * @throws Exception
     */
	public List<WhDecproject> selMingLu() throws Exception {
		return this.acMapper.selMingLu();
	}
	
	/**
	 * 增加或修改 名录项目
	 * @param minglu
	 * @param req
	 * @param file_image
	 * @param file_imagesm
	 * @throws Exception
	 */
	public void addOrEditminglu(WhDecproject minglu,HttpServletRequest req,MultipartFile file_image,MultipartFile file_imagesm) throws Exception {
		// 保存记录
		String id = null;
		if (minglu.getMlproid() != null && !"".equals(minglu.getMlproid())) {
			id = minglu.getMlproid();
			minglu.setMlprotime(new Date());
			this.decprojectMapper.updateByPrimaryKeySelective(minglu);
		} else {
			id=this.commService.getKey("whactivity");
			minglu.setRecommend(0);
			minglu.setMlproid(id);
			minglu.setMlprostate(1);
			minglu.setMlprotime(new Date());
			this.decprojectMapper.insert(minglu);
		}
		// 处理图片
		WhDecproject ml = this.decprojectMapper.selectByPrimaryKey(id);
		this.actInfoFile(ml,req,file_image,file_imagesm);
		// 再次保存
		this.decprojectMapper.updateByPrimaryKeySelective(ml);
	}

	/**
	 * 查询名录单条记录
	 * @param id id
	 * @return 对象
	 * @throws Exception
	 */
	public WhDecproject t_srchOne(String id)throws Exception{
//		WhDecproject record = new WhDecproject();
//		record.setMlproid(id);
		WhDecproject whDecproject = this.decprojectMapper.selectByPrimaryKey(id);
		return whDecproject;
	}

	/**
	 * 名录是否推荐
	 * @param ids
	 * @param formrecoms
	 * @param torecom
	 * @return
	 */
	public ResponseBean t_updrecommend(String ids, String formrecoms, int torecom) throws Exception {
		ResponseBean res = new ResponseBean();
		if(ids == null){
			res.setSuccess(ResponseBean.FAIL);
			res.setErrormsg("名录主键丢失");
			return res;
		}
		Example example = new Example(WhDecproject.class);
		Example.Criteria c = example.createCriteria();
		c.andEqualTo("recommend",1);
//		int result = this.decprojectMapper.selectCountByExample(example);

		Example example1 = new Example(WhDecproject.class);
		Example.Criteria c1 = example1.createCriteria();
		c1.andIn("mlproid", Arrays.asList( ids.split("\\s*,\\s*") ));
		c1.andIn("recommend", Arrays.asList( formrecoms.split("\\s*,\\s*") ));
		WhDecproject dec = new WhDecproject();
		dec.setRecommend(torecom);
		this.decprojectMapper.updateByExampleSelective(dec,example1);

		return res;
	}

	/**
	 * 查询传承人单条记录
	 * @param id id
	 * @return 对象
	 * @throws Exception
	 */
	public WhSuccessor srchOne(String id)throws Exception{
		WhSuccessor record = new WhSuccessor();
		record.setSuorid(id);
		return this.successorMapper.selectOne(record);
	}

	/**
	 * 删除名录信息操作
	 * 
	 * @return
	 */
	public void removeMinglu(String mlproid,HttpServletRequest req) throws Exception{
		// 获取数据库中存放的图片路径,资料路径
		WhDecproject minglu = this.decprojectMapper.selectByPrimaryKey(mlproid);
		String uploadPath = UploadUtil.getUploadPath(req);
		String probigpic=minglu.getMlprobigpic();
		String prosmpic=minglu.getMlprosmpic();
		// 删除记录
		this.decprojectMapper.deleteByPrimaryKey(mlproid);
		
		UploadUtil.delUploadFile(uploadPath, probigpic);
		UploadUtil.delUploadFile(uploadPath, prosmpic);
	}
	
	/**
	 * 删除 名录 传承人关联
	 * @return
	 */
	public String removeSuorpro(String spmlproid) throws Exception{
		Example example = new Example(WhSuorpro.class);
		example.or().andEqualTo("spmlproid", spmlproid);
		List<WhSuorpro> list = this.suorproMapper.selectByExample(example);
		if(list != null && list.size() > 0){
			String spid = list.get(0).getSpid();
			this.suorproMapper.deleteByPrimaryKey(spid);
			return "删除成功!";
		}else{
			return "删除项不存在!";
		}
	}
	
	
	/**
	 * 删除传承人操作
	 * 
	 * @return
	 */
	public void removeSuccessor(String suorid,HttpServletRequest req) throws Exception{
		// 获取数据库中存放的图片路径,资料路径
		WhSuccessor suor = this.successorMapper.selectByPrimaryKey(suorid);
		String uploadPath = UploadUtil.getUploadPath(req);
		String prosmpic=suor.getSuorpic();
		// 删除记录
		this.successorMapper.deleteByPrimaryKey(suorid);
		
		UploadUtil.delUploadFile(uploadPath, prosmpic);
	}
	
	/**
	 * 增加 传承人  名录项目关联
	 * 
	 * @return
	 */
	public void addsuorpro(WhSuorpro sp) throws Exception{
		String id=this.commService.getKey("WhSuorpro");
		sp.setSpid(id);
		this.suorproMapper.insert(sp);
	}
	
	
	public void addOrEditsuccessor(WhSuccessor successor,HttpServletRequest req,MultipartFile file_image) throws Exception {
		Map<String, Object> paramMap = ReqParamsUtil.parseRequest(req);
		String mlproid = (String) paramMap.get("mlproid");
		// 保存记录
		String id = null;

		if (successor.getSuorid() != null && !"".equals(successor.getSuorid())) {
			id = successor.getSuorid();
			successor.setSuoroptime(new Date());
			this.successorMapper.updateByPrimaryKeySelective(successor);
		} else {
			id=this.commService.getKey("whsuccessor");
			successor.setRecommend(0);
			successor.setSuorid(id);
			successor.setProid(mlproid);
			successor.setSuorstate(1);
			successor.setSuoroptime(new Date());
			this.successorMapper.insert(successor);
		}
		// 处理图片
		WhSuccessor suor = this.successorMapper.selectByPrimaryKey(id);
		this.actInfoFile2(suor,req,file_image);
		// 再次保存
		this.successorMapper.updateByPrimaryKeySelective(suor);
	}
	
	/**
	 * 写入图片 名录
	 * @param minglu
	 * @param req
	 * @param mfile
	 * @param mfile1
	 * @throws Exception
	 */
	private void actInfoFile(WhDecproject minglu,HttpServletRequest req,MultipartFile mfile,MultipartFile mfile1)
			throws Exception {
		// 当前日期
		Date now = new Date();
		//图片或者文件获取根目录
		String uploadPath = UploadUtil.getUploadPath(req);
		
		//图片处理
		if(mfile != null && !mfile.isEmpty()){
			//删除图片
			UploadUtil.delUploadFile(uploadPath, minglu.getMlprosmpic());
			//获取保存到db的路径
			String imgPath = UploadUtil.getUploadFilePath(mfile.getOriginalFilename(), commService.getKey("feiyi.picture"), "feiyi", "picture", now);
			//上传到本地
			mfile.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath) );
			//将\转/保存
			minglu.setMlprosmpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath));
		}
		if(mfile1 != null && !mfile1.isEmpty()){
			//删除图片
			UploadUtil.delUploadFile(uploadPath, minglu.getMlprobigpic());
			//获取保存到db的路径
			String imgPath = UploadUtil.getUploadFilePath(mfile1.getOriginalFilename(), commService.getKey("feiyi.picture"), "feiyi", "picture", now);
			System.out.println("===============imgPath2"+imgPath);
			//上传到本地
			mfile1.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath) );
			//将\转/保存
			minglu.setMlprobigpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath));
		}
	}
	
	/**
	 * 写入图片
	 * @param req
	 * @param mfile
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void actInfoFile2(WhSuccessor successor,HttpServletRequest req,MultipartFile mfile)throws Exception {
		// 当前日期
		Date now = new Date();
		//图片或者文件获取根目录
		String uploadPath = UploadUtil.getUploadPath(req);
		
		//图片处理
		if(mfile != null && !mfile.isEmpty()){
			//删除图片
			UploadUtil.delUploadFile(uploadPath, successor.getSuorpic());
			//获取保存到db的路径
			String imgPath = UploadUtil.getUploadFilePath(mfile.getOriginalFilename(), commService.getKey("feiyi.picture"), "feiyi", "picture", now);
			//上传到本地
			mfile.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath) );
			//将\转/保存
			successor.setSuorpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath));
		}
	}
	
	/**
	 * 名录项目修改状态
	 * @param decpject
	 * @param fromstate
	 * @param tostate
	 * @return
	 * @throws Exception
	 */
	public Object editState(WhDecproject decpject,int fromstate,int tostate,WebRequest request)throws Exception{
		Example example2 = new Example(WhSuorpro.class);
		Criteria c = example2.createCriteria();
		String spmlproid = request.getParameter("mlproid");
		String spsuorid = request.getParameter("suorid");
		c.andEqualTo("spsuorid", spsuorid);
		c.andEqualTo("spmlproid", spmlproid);
		List<WhSuorpro> listsp = this.suorproMapper.selectByExample(example2);
		
		Example example = new Example(WhDecproject.class);
		Criteria cs = example.createCriteria();
		if(listsp != null && listsp.size() >0){
			String spid = listsp.get(0).getSpid();
			cs.andEqualTo("mlproid", spid);
		}else{
			cs.andEqualTo("mlproid",spmlproid);
		}
		cs.andEqualTo("mlprostate", fromstate);
		decpject.setMlprostate(tostate);
		this.decprojectMapper.updateByExampleSelective(decpject, example);
		return "操作成功!";
	}

	/**
	 * 名录改变状态
	 * @param ids
	 * @param formstates
	 * @param tostate
	 * @return
	 */
	public ResponseBean t_updstate(String ids, String formstates, int tostate) throws Exception {
		ResponseBean rb = new ResponseBean();
		if (ids == null){
			rb.setSuccess(ResponseBean.FAIL);
			rb.setErrormsg("主键丢失");
			return rb;
		}
		Example example = new Example(WhDecproject.class);
		example.createCriteria()
				.andIn("mlprostate", Arrays.asList( formstates.split("\\s*,\\s*") ))
				.andIn("mlproid", Arrays.asList( ids.split("\\s*,\\s*") ) );
		WhDecproject tra = new WhDecproject();
		tra.setMlprostate(tostate);
		tra.setMlprotime(new Date());
		this.decprojectMapper.updateByExampleSelective(tra, example);
		return rb;
	}

	/**
	 * 名录批量修改状态
	 * @param fromstate
	 * @param tostate
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Object pleditState(WhDecproject decproject,int fromstate,int tostate,String params)throws Exception{
		//放名录id
		List<String> list = new ArrayList<String>();
		
		Example example =new Example(WhDecproject.class);
		Criteria c=example.createCriteria();
		if(params != null && !"".equals(params)){
			String p[]=params.split(",");
			for(int i=0;i<p.length;i++){
				list.add(p[i]);
			}
			if(list != null && list.size() > 0){
				c.andIn("mlproid", list);
				c.andEqualTo("mlprostate", fromstate);
				decproject.setMlprostate(tostate);
				this.decprojectMapper.updateByExampleSelective(decproject, example);
				return "操作成功！";
			}	
		}
		return "没有符合批量操作的！";
	}
	
	/**
	 * 传承人 修改状态
	 * @param suor
	 * @param fromstate
	 * @param tostate
	 * @return
	 * @throws Exception
	 */
	public Object editStatesuor(WhSuccessor suor,int fromstate,int tostate)throws Exception{
		Example example = new Example(WhSuccessor.class);
		Criteria cs = example.createCriteria();
		cs.andEqualTo("suorstate", fromstate);
		cs.andEqualTo("suorid", suor.getSuorid());
		suor.setSuorstate(tostate);
		this.successorMapper.updateByExampleSelective(suor, example);
		return "操作成功!";
	}

	/**
	 * 传承人改变状态
	 * @param ids
	 * @param formstates
	 * @param tostate
	 * @return
	 */
	public ResponseBean updstate(String ids, String formstates, int tostate) throws Exception {
		ResponseBean rb = new ResponseBean();
		if (ids == null){
			rb.setSuccess(ResponseBean.FAIL);
			rb.setErrormsg("主键丢失");
			return rb;
		}
		Example example = new Example(WhSuccessor.class);
		example.createCriteria()
				.andIn("suorstate", Arrays.asList(formstates.split("\\s*,\\s*")))
				.andIn("suorid", Arrays.asList(ids.split("\\s*,\\s*")));
		WhSuccessor suc = new WhSuccessor();
		suc.setSuorstate(tostate);
		suc.setSuoroptime(new Date());
		this.successorMapper.updateByExampleSelective(suc, example);
		return rb;
	}
	
	/**
	 * 传承人 批量修改状态
	 * @param fromstate
	 * @param tostate
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Object pleditStatesuor(WhSuccessor suor,int fromstate,int tostate,String params)throws Exception{
		//放名录id
		List<String> list = new ArrayList<String>();
		
		Example example =new Example(WhSuccessor.class);
		Criteria c=example.createCriteria();
		if(params != null && !"".equals(params)){
			String p[]=params.split(",");
			for(int i=0;i<p.length;i++){
				list.add(p[i]);
			}
			if(list != null && list.size() > 0){
				c.andIn("suorid", list);
				c.andEqualTo("suorstate", fromstate);
				suor.setSuorstate(tostate);
				this.successorMapper.updateByExampleSelective(suor, example);
				return "操作成功！";
			}	
		}
		return "没有符合批量操作的！";
	}


	/**
	 * 传承人是否推荐
	 * @param ids
	 * @param formrecoms
	 * @param torecom
	 * @return
	 */
	public ResponseBean updrecommend(String ids, String formrecoms, int torecom) throws Exception {
		ResponseBean res = new ResponseBean();
		if(ids == null){
			res.setSuccess(ResponseBean.FAIL);
			res.setErrormsg("传承人主键丢失");
			return res;
		}
		Example example = new Example(WhSuccessor.class);
		Example.Criteria c = example.createCriteria();
		c.andEqualTo("recommend",1);
		int result = this.successorMapper.selectCountByExample(example);
		if(result < 10){
			Example example1 = new Example(WhSuccessor.class);
			Example.Criteria c1 = example1.createCriteria();
			c1.andIn("suorid", Arrays.asList( ids.split("\\s*,\\s*") ));
			c1.andIn("recommend", Arrays.asList( formrecoms.split("\\s*,\\s*") ));
			WhSuccessor suc = new WhSuccessor();
			suc.setRecommend(torecom);
			this.successorMapper.updateByExampleSelective(suc,example1);
		}
		return res;
	}
}
