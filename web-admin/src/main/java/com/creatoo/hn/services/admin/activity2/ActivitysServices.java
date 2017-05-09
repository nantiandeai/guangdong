package com.creatoo.hn.services.admin.activity2;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.creatoo.hn.actions.home.userCenter.UserCenterAction;
import com.creatoo.hn.mapper.ActivityMapper;
import com.creatoo.hn.mapper.WhActivityMapper;
import com.creatoo.hn.mapper.WhActivitybmMapper;
import com.creatoo.hn.mapper.WhActivityitmMapper;
import com.creatoo.hn.mapper.WhActivitytplMapper;
import com.creatoo.hn.mapper.WhCfgListMapper;
import com.creatoo.hn.mapper.WhUserMapper;
import com.creatoo.hn.model.WhActivity;
import com.creatoo.hn.model.WhActivitybm;
import com.creatoo.hn.model.WhActivityitm;
import com.creatoo.hn.model.WhActivitytpl;
import com.creatoo.hn.model.WhCfgList;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.EmailUtil;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.SmsUtil;
import com.creatoo.hn.utils.UploadUtil;
import com.creatoo.hn.utils.WhConstance;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@SuppressWarnings("all")
@Service
/**
 * 活动管理 服务层
 * @author chengxj
 *
 */
public class ActivitysServices {
	/**
	 * 活动表 Activity mapper
	 */
	@Autowired
	private WhActivityMapper activityMapper;
	/***
	 * 工具类
	 */
	@Autowired
	private CommService commService;
	/**
	 * 活动模板表 Activitytpl
	 */
	@Autowired
	private WhActivitytplMapper activitytplMapper;
	/***
	 * xml 语句
	 */
	@Autowired
	private ActivityMapper aMapper;
	/**
	 * 活动场次
	 */
	@Autowired
	private WhActivityitmMapper activityitmMapper;
	
	/**
	 * 活动报名
	 */
	@Autowired
	private WhActivitybmMapper whActivitybmMapper;
	
	@Autowired
	private WhCfgListMapper wListMapper;
	
	@Autowired
	private WhUserMapper userMapper;
	/**
	 * 新增消息提示
	 */
	@Autowired
	private UserCenterAction useaction;
	
	/**
	 * 活动表加载 数据
	 * @param page
	 * @param rows
	 * @param request
	 * @return
	 */
	public Object loadActivity(int page,int rows,WebRequest request) throws Exception{
		//Example	example=(Example) loadList(WhActivity.class, page, rows,request);
		Map<String, Object> param = ReqParamsUtil.parseRequest(request);
		PageHelper.startPage(page, rows);
		List<Map> list = this.aMapper.selectActivity(param);
		PageInfo pinfo = new PageInfo(list);

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("total", pinfo.getTotal());
		res.put("rows", pinfo.getList());

		return res;
	}
	
	/**
	 * 活动模板表加载 数据
	 * @param page
	 * @param rows
	 * @param request
	 * @return
	 */
	public Object loadActivitytpl(int page,int rows,WebRequest request) throws Exception{
		//Example	example=(Example) loadList(WhActivitytpl.class, page, rows,request);
		Map<String, Object> param = ReqParamsUtil.parseRequest(request);
		
		PageHelper.startPage(page, rows);
		List<Map> list = this.aMapper.selectActivitytpl(param);
		PageInfo pinfo = new PageInfo(list);

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("total", pinfo.getTotal());
		res.put("rows", pinfo.getList());
		return res;
	}
	
	/**
	 * 获取 模板 标题
	 * 
	 * @return
	 */
	public Object loadActtplTiltle(WebRequest request)throws Exception{
		String actvtplid=request.getParameter("actvtplid");
		Example example = new Example(WhActivitytpl.class);
		if(actvtplid!= null && !"".equals(actvtplid)){
			example.or().andEqualTo("actvtplid",actvtplid);
		}
		return this.activitytplMapper.selectByExample(example);
	}
	
	/**
	 * 表 数据加载
	 * @param request
	 * @return
	 */
	/*public Object loadList(Class clas,int page,int rows,WebRequest request){
		
		Map<String, Object> param = ReqParamsUtil.parseRequest(request);

		String actvstate=(String) param.get("actvstate");
		//发布 查 state：2,3 
		String actvstate2 = (String) param.get("actvstate2");
		String actvtitle=(String) param.get("actvtitle");
		String actvarttyp=(String) param.get("actvarttyp");
		String actvtype=(String) param.get("actvtype");
		String actvsdate=(String) param.get("actvsdate");
		String actvedate=(String) param.get("actvedate");
		
		Example example = new Example(clas);
		Criteria c=example.createCriteria();
		if(actvstate!=null || "".equals(actvstate)){
			c.andEqualTo("actvstate", actvstate);
		}
		if(actvstate2!=null || "".equals(actvstate2)){
			c.andBetween("actvstate",actvstate2,3);
		}
		if(actvtitle!=null || "".equals(actvtitle)){
			c.andLike("actvshorttitle",  "%"+actvtitle+"%");
			example.or().andLike("actvtitle",  "%"+actvtitle+"%");
		}
		if(actvarttyp!=null || "".equals(actvarttyp)){
			c.andEqualTo("actvarttyp", actvarttyp);
		}
		if(actvtype!=null || "".equals(actvtype)){
			c.andEqualTo("actvtype", actvtype);
		}
		if(actvsdate!=null || "".equals(actvsdate)){
			c.andGreaterThanOrEqualTo("actvsdate", actvsdate);
		}
		if(actvedate!=null || "".equals(actvedate)){
			c.andLessThanOrEqualTo("actvedate", actvedate);
		}

		return example;
	}*/
	
	/**
	 * 修改审批状态
	 * @param whActitm
	 * @return
	 */
	public Object editActitmState(WhActivity activity,int fromstate,int tostate,String params,String isss)throws Exception{
		List<String> list = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		Example example =new Example(WhActivity.class);
		Criteria c=example.createCriteria();
		if(params != null && !"".equals(params)){
			String p[]=params.split(",");
			for(int i=0;i<p.length;i++){
				list.add(p[i]);
				list2.add(p[i]);
			}
			//如果 是送审 验证 是否有时间
			if(isss != null && !"".equals(isss)){
				boolean issucc = true;
				Map<String, Object> map =new HashMap<String, Object>();
				map.put("list",list2);
				Example example2 = new Example(WhActivity.class);
				example2.or().andIn("actvid",list );
				List<WhActivity> listact = this.activityMapper.selectByExample(example2);
				for (WhActivity whActivity : listact) {
					if(whActivity.getActvisenrol() == 0 && whActivity.getActvisyp() == 0){
						list2.remove(whActivity.getActvid());
					}else{
						issucc = false;
					}
				}
				if(issucc = false){
					List<Map> ListMap=this.aMapper.selectcountActitm(map);
					
					if(ListMap.size() < list.size()){
						return "含有未设置时间的活动,不允许送审！";
					}
				}
			}
			if(tostate == 2 && fromstate == 3){
				Example example2 =new Example(WhCfgList.class);
				Criteria c2 = example2.createCriteria();
				c2.andEqualTo("cfgpagetype","2016102900000006");
				c2.andEqualTo("cfgenttype","2016101400000052");
				c2.andIn("cfgshowid",list);
				int listcount2=this.wListMapper.selectCountByExample(example2); 
				//有页面元素 不能取消发布
				if(listcount2>0){
					return "批量活动中含有页面元素配置,不允许取消发布！";
				}
				//有报名/订票 不能取消发布
				Example example3 =new Example(WhActivitybm.class);
				Criteria c3 = example3.createCriteria();
				c3.andIn("actid", list);
				c3.andNotEqualTo("actshstate", "3");
				c3.andEqualTo("actbmstate", 1);
				//获取活动报名
				List<WhActivitybm>	listbm=this.whActivitybmMapper.selectByExample(example3);
				//List<String> list2=new ArrayList<String>();
				if(listbm.size() > 0){
					return "活动含有处于进行中 有报名或订票,不允许取消发布！";
					}
				/*if(listbm != null && listbm.size() > 0){
					for(int i=0;i<listbm.size();i++){
						list2.add(listbm.get(i).getActvitmid());
						}
						Example example4 =new Example(WhActivitybm.class);
						example4.or().andIn("actvitmid", list2);
						List<WhActivityitm> listitm = this.activityitmMapper.selectByExample(example4);
						for(int i=0;i<listitm.size();i++){
							if(listitm.get(i).getActvitmdpcount() != listitm.get(i).getActvitmbmcount()){
								return "活动含有处于进行中 有报名或订票,不允许取消发布！";
							}
						}
					}*/
			}
			c.andIn("actvid", list);
			activity.setActvstate(tostate);
			c.andEqualTo("actvstate", fromstate);
			
			this.activityMapper.updateByExampleSelective(activity, example);
			return "操作成功！";
		}else{
			if(isss != null && !"".equals(isss)){
				Example example2 =new Example(WhActivityitm.class);
				example2.or().andEqualTo("actvrefid", activity.getActvid());
				int	listcount=this.activityitmMapper.selectCountByExample(example2);
					if(activity.getActvisenrol() == 0 && activity.getActvisyp() == 0){
						
					}else{
						if(listcount <1){
							return "活动未设置时间,不允许送审！";
						}
					}
			}
			if(tostate ==2 && fromstate == 3){
				Example example2 =new Example(WhCfgList.class);
				Criteria c2 = example2.createCriteria();
				c2.andEqualTo("cfgpagetype","2016102900000006");
				c2.andEqualTo("cfgenttype","2016101400000052");
				c2.andEqualTo("cfgshowid", activity.getActvid());
				int listcount2=this.wListMapper.selectCountByExample(example2); 
				if(listcount2 == 1){
					return "活动含有页面元素配置,不允许取消发布！";
				}
				
				//有报名/订票 不能取消发布	
				Example example3 = new Example(WhActivitybm.class);
				Criteria c3 = example3.createCriteria();
				c3.andNotEqualTo("actshstate", "3");
				c3.andEqualTo("actbmstate", 1);
				//c.andEqualTo("actid", listbm.get(i).getActid());
				c3.andEqualTo("actid", activity.getActvid());
				List<WhActivitybm> actbm=this.whActivitybmMapper.selectByExample(example3);
				if(actbm != null){
					if(actbm.size() > 0){
						return "活动处于进行中 有报名或订票,不允许取消发布！";
						}
					}
				}
			activity.setActvstate(tostate);
			this.activityMapper.updateByPrimaryKeySelective(activity);
			return "操作成功！";
		}
	}
	
	/**
     * 修改或增加 活动
     * @param ent
     * @param req
     * @param file_enturl
     * @param file_deourl
     * @throws Exception
     */
	public void addOrEditActInfo(String actvtplid,WhActivity activity,HttpServletRequest req,MultipartFile file_image,MultipartFile file_imagesm,MultipartFile one_url,MultipartFile temp_url) throws Exception {
			// 保存记录
			String id = null;
		
			if (activity.getActvid()!= null && !"".equals(activity.getActvid())) {
				id = activity.getActvid();
				activity.setActvopttime(new Date());
				this.activityMapper.updateByPrimaryKeySelective(activity);
			} else{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				id=this.commService.getKey("whactivity");
				activity.setActvid(id);
				activity.setActvstate(0);
				if(activity.getActvsdate() == null || "".equals(activity.getActvsdate()) || "1970/01/01".equals(sdf.format(activity.getActvsdate()))){
					activity.setActvsdate(new Date());
				}
				activity.setActvopttime(new Date());
				this.activityMapper.insert(activity);
			}
			
			// 处理图片
			WhActivity act = this.activityMapper.selectByPrimaryKey(id);
			this.actInfoFile(actvtplid,act,req,file_image,file_imagesm,one_url,temp_url);
			// 再次保存
			this.activityMapper.updateByPrimaryKeySelective(act);
	}
	
	/***
	 * 活动模板 增加 修改
	 * @param activitytpl
	 * @param req
	 * @param file_image
	 * @param file_imagesm
	 * @param one_url
	 * @param temp_url
	 * @throws Exception
	 */
	public void addOrEditActtplInfo(WhActivitytpl activitytpl,HttpServletRequest req,MultipartFile file_image,MultipartFile file_imagesm,MultipartFile one_url,MultipartFile temp_url) throws Exception {
	
			// 保存记录
			String id = null;
			if (activitytpl.getActvtplid()!= null && !"".equals(activitytpl.getActvtplid())) {
				id = activitytpl.getActvtplid();
				activitytpl.setActvopttime(new Date());
				this.activitytplMapper.updateByPrimaryKeySelective(activitytpl);
			} else {
				id=this.commService.getKey("activitytpl");
				activitytpl.setActvtplid(id);
				activitytpl.setActvstate(0);
				activitytpl.setActvopttime(new Date());
				this.activitytplMapper.insert(activitytpl);
			}
			// 处理图片
			WhActivitytpl act = this.activitytplMapper.selectByPrimaryKey(id);
			this.actInfoFile2(act,req,file_image,file_imagesm,one_url,temp_url);
			// 再次保存
			this.activitytplMapper.updateByPrimaryKeySelective(act);
	}
	
	
	/**
	 * 保存上传的文件，写入文件路径到持久对像
	 * @param activity
	 * @param req
	 * @param mfile
	 * @param mfile1
	 * @param mfile2
	 * @param mfile3
	 * @throws Exception
	 */
	private void actInfoFile(String actvtplid,WhActivity activity,HttpServletRequest req,MultipartFile mfile,MultipartFile mfile1,MultipartFile mfile2,MultipartFile mfile3)
				throws Exception {
			// 当前日期
			Date now = new Date();
			//图片或者文件获取根目录
			String uploadPath = UploadUtil.getUploadPath(req); 
			if(actvtplid == null || "".equals(actvtplid) || ",".equals(actvtplid)){
				//图片处理
				if(mfile != null && !mfile.isEmpty()){
					//删除图片
					UploadUtil.delUploadFile(uploadPath, activity.getActvpic());
					//获取保存到db的路径
					String imgPath = UploadUtil.getUploadFilePath(mfile.getOriginalFilename(), commService.getKey("activity.picture"), "activity", "picture", now);
					//上传到本地
					mfile.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath) );
					//将\转/保存
					activity.setActvpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath));
				}
				if(mfile1 != null && !mfile1.isEmpty()){
					//删除图片
					UploadUtil.delUploadFile(uploadPath, activity.getActvbigpic());
					//获取保存到db的路径
					String imgPath = UploadUtil.getUploadFilePath(mfile1.getOriginalFilename(), commService.getKey("activity.picture"), "activity", "picture", now);
					//上传到本地
					mfile1.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath) );
					//将\转/保存
					activity.setActvbigpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath));
				}
				if(mfile2 != null && !mfile2.isEmpty()){
					//删除图片
					UploadUtil.delUploadFile(uploadPath, activity.getActvpersonfile());
					//获取保存到db的路径
					String imgPath = UploadUtil.getUploadFilePath(mfile2.getOriginalFilename(), commService.getKey("activity.archive"), "activity", "archive", now);
					//上传到本地
					mfile2.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath) );
					//将\转/保存
					activity.setActvpersonfile(UploadUtil.getUploadFileUrl(uploadPath, imgPath));
				}
				if(mfile3 != null && !mfile3.isEmpty()){
					//删除图片
					UploadUtil.delUploadFile(uploadPath, activity.getActvteamfile());
					//获取保存到db的路径
					String imgPath = UploadUtil.getUploadFilePath(mfile3.getOriginalFilename(), commService.getKey("activity.archive"), "activity", "archive", now);
					//上传到本地
					mfile3.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath) );
					//将\转/保存
					activity.setActvteamfile(UploadUtil.getUploadFileUrl(uploadPath, imgPath));
				}
			}else{
				//图片处理
				if(mfile != null && !mfile.isEmpty()){
					//获取保存到db的路径
					String imgPath = UploadUtil.getUploadFilePath(mfile.getOriginalFilename(), commService.getKey("activity.picture"), "activity", "picture", now);
					//上传到本地
					mfile.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath) );
					//将\转/保存
					activity.setActvpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath));
				}
				if(mfile1 != null && !mfile1.isEmpty()){
					//获取保存到db的路径
					String imgPath = UploadUtil.getUploadFilePath(mfile1.getOriginalFilename(), commService.getKey("activity.picture"), "activity", "picture", now);
					//上传到本地
					mfile1.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath) );
					//将\转/保存
					activity.setActvbigpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath));
				}
				if(mfile2 != null && !mfile2.isEmpty()){
					//获取保存到db的路径
					String imgPath = UploadUtil.getUploadFilePath(mfile2.getOriginalFilename(), commService.getKey("activity.archive"), "activity", "archive", now);
					//上传到本地
					mfile2.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath) );
					//将\转/保存
					activity.setActvpersonfile(UploadUtil.getUploadFileUrl(uploadPath, imgPath));
				}
				if(mfile3 != null && !mfile3.isEmpty()){
					//获取保存到db的路径
					String imgPath = UploadUtil.getUploadFilePath(mfile3.getOriginalFilename(), commService.getKey("activity.archive"), "activity", "archive", now);
					//上传到本地
					mfile3.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath) );
					//将\转/保存
					activity.setActvteamfile(UploadUtil.getUploadFileUrl(uploadPath, imgPath));
				}
			}
			
			
			//图片导入模板处理
			if(activity.getActvpic() != null && mfile.isEmpty() && !"".equals(activity.getActvpic()) && actvtplid !=null && !"".equals(actvtplid) && !",".equals(actvtplid)){
				//删除图片
				String picUrl = UploadUtil.getUploadFileDelPath(uploadPath, activity.getActvpic());
				//获取保存到db的路径getUploadFilePath
				String imgPath = UploadUtil.getUploadFilePath(activity.getActvpic(), commService.getKey("activity.picture"), "activity", "picture", now);
				//上传到本地
				//mfile.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath) );
				//copy 文件
				FileUtil.copyFile(new File(picUrl),UploadUtil.createUploadFile(uploadPath, imgPath));
				//将\转/保存
				activity.setActvpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath));
			}
			if(actvtplid !=null && activity.getActvbigpic() != null  && !"".equals(activity.getActvbigpic()) && mfile1.isEmpty() && !"".equals(actvtplid) && !",".equals(actvtplid)){
				//删除图片
				String picUrl = UploadUtil.getUploadFileDelPath(uploadPath, activity.getActvbigpic());
				//获取保存到db的路径
				String imgPath = UploadUtil.getUploadFilePath(activity.getActvbigpic(), commService.getKey("activity.picture"), "activity", "picture", now);
				//上传到本地
				//mfile1.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath) );
				//copy 文件
				FileUtil.copyFile(new File(picUrl),UploadUtil.createUploadFile(uploadPath, imgPath));
				//将\转/保存
				activity.setActvbigpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath));
			}
			if(actvtplid !=null && activity.getActvpersonfile() != null && !"".equals(activity.getActvpersonfile()) && mfile2.isEmpty() && !"".equals(actvtplid) && !",".equals(actvtplid)){
				//删除图片
				String picUrl = UploadUtil.getUploadFileDelPath(uploadPath, activity.getActvpersonfile());
				//获取保存到db的路径
				String imgPath = UploadUtil.getUploadFilePath(activity.getActvpersonfile(), commService.getKey("activity.archive"), "activity", "archive", now);
				//上传到本地
				//mfile2.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath) );
				//copy 文件
				FileUtil.copyFile(new File(picUrl),UploadUtil.createUploadFile(uploadPath, imgPath));
				//将\转/保存
				activity.setActvpersonfile(UploadUtil.getUploadFileUrl(uploadPath, imgPath));
			}
			if(activity.getActvteamfile() != null && !"".equals(activity.getActvteamfile()) && mfile3.isEmpty() && actvtplid !=null && !"".equals(actvtplid) && !",".equals(actvtplid)){
				//删除图片
				String picUrl = UploadUtil.getUploadFileDelPath(uploadPath, activity.getActvteamfile());
				//获取保存到db的路径
				String imgPath = UploadUtil.getUploadFilePath(activity.getActvteamfile(), commService.getKey("activity.archive"), "activity", "archive", now);
				//上传到本地
				//mfile3.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath) );
				//copy 文件
				FileUtil.copyFile(new File(picUrl),UploadUtil.createUploadFile(uploadPath, imgPath));
				//将\转/保存
				activity.setActvteamfile(UploadUtil.getUploadFileUrl(uploadPath, imgPath));
			}
			
	}
	
		
	/**
	 * 保存上传的文件，写入文件路径到持久对像
	 * @param activity
	 * @param req
	 * @param mfile
	 * @param mfile1
	 * @param mfile2
	 * @param mfile3
	 * @throws Exception
	 */
private void actInfoFile2(WhActivitytpl activitytpl,HttpServletRequest req,MultipartFile mfile,MultipartFile mfile1,MultipartFile mfile2,MultipartFile mfile3)
			throws Exception {
		// 当前日期
		Date now = new Date();
		//图片或者文件获取根目录
		String uploadPath = UploadUtil.getUploadPath(req);
		
		//图片处理
		if(mfile != null && !mfile.isEmpty()){
			//删除图片
			UploadUtil.delUploadFile(uploadPath, activitytpl.getActvpic());
			//获取保存到db的路径
			String imgPath = UploadUtil.getUploadFilePath(mfile.getOriginalFilename(), commService.getKey("activity.picture"), "activity", "picture", now);
			//上传到本地
			mfile.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath) );
			//将\转/保存
			activitytpl.setActvpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath));
		}
		if(mfile1 != null && !mfile1.isEmpty()){
			//删除图片
			UploadUtil.delUploadFile(uploadPath, activitytpl.getActvbigpic());
			//获取保存到db的路径
			String imgPath = UploadUtil.getUploadFilePath(mfile1.getOriginalFilename(), commService.getKey("activity.picture"), "activity", "picture", now);
			//上传到本地
			mfile1.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath) );
			//将\转/保存
			activitytpl.setActvbigpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath));
		}
		if(mfile2 != null && !mfile2.isEmpty()){
			//删除图片
			UploadUtil.delUploadFile(uploadPath, activitytpl.getActvpersonfile());
			//获取保存到db的路径
			String imgPath = UploadUtil.getUploadFilePath(mfile2.getOriginalFilename(), commService.getKey("activity.archive"), "activity", "archive", now);
			//上传到本地
			mfile2.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath) );
			//将\转/保存
			activitytpl.setActvpersonfile(UploadUtil.getUploadFileUrl(uploadPath, imgPath));
		}
		if(mfile3 != null && !mfile3.isEmpty()){
			//删除图片
			UploadUtil.delUploadFile(uploadPath, activitytpl.getActvteamfile());
			//获取保存到db的路径
			String imgPath = UploadUtil.getUploadFilePath(mfile3.getOriginalFilename(), commService.getKey("activity.archive"), "activity", "archive", now);
			//上传到本地
			mfile3.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath) );
			//将\转/保存
			activitytpl.setActvteamfile(UploadUtil.getUploadFileUrl(uploadPath, imgPath));
		}
		
	}
	/**
	 * 删除活动信息操作
	 * 
	 * @return
	 */
	public void removeActivity(String actvid,HttpServletRequest req) throws Exception{
		// 获取数据库中存放的图片路径,资料路径
		WhActivity activity = this.activityMapper.selectByPrimaryKey(actvid);
		String uploadPath = UploadUtil.getUploadPath(req);
		String actvpic=activity.getActvpic();
		String actvbigpic=activity.getActvbigpic();
		String actvpersonfile=activity.getActvpersonfile();
		String actvteamfile=activity.getActvteamfile();
		// 删除记录
		this.activityMapper.deleteByPrimaryKey(actvid);
		
		UploadUtil.delUploadFile(uploadPath, actvpic);
		UploadUtil.delUploadFile(uploadPath, actvbigpic);
		UploadUtil.delUploadFile(uploadPath, actvpersonfile);
		UploadUtil.delUploadFile(uploadPath, actvteamfile);
	}
	
	/**
	 * 删除活动 模板信息操作
	 * 
	 * @return
	 */
	public void removeActivitytpl(String actvtplid,HttpServletRequest req) throws Exception{
		// 获取数据库中存放的图片路径,资料路径
		WhActivitytpl activitytpl = this.activitytplMapper.selectByPrimaryKey(actvtplid);
		String uploadPath = UploadUtil.getUploadPath(req);
		String actvpic=activitytpl.getActvpic();
		String actvbigpic=activitytpl.getActvbigpic();
		String actvpersonfile=activitytpl.getActvpersonfile();
		String actvteamfile=activitytpl.getActvteamfile();
		// 删除记录
		this.activitytplMapper.deleteByPrimaryKey(actvtplid);
		
		UploadUtil.delUploadFile(uploadPath, actvpic);
		UploadUtil.delUploadFile(uploadPath, actvbigpic);
		UploadUtil.delUploadFile(uploadPath, actvpersonfile);
		UploadUtil.delUploadFile(uploadPath, actvteamfile);
	}
    
	/**
	 * 页面配置不带分页,条件查询
	 * @param lijun
	 * @param actvstate
	 * @return
	 */
	public Object selectActiviys(String actvtype, String actvstate) throws Exception{
		Example example = new Example(WhActivity.class);
		
		Criteria Criteria = example.createCriteria();
		Criteria.andEqualTo("actvtype", actvtype);
		Criteria.andEqualTo("actvstate", actvstate);
		
		example.setOrderByClause("actvid desc");
		List<WhActivity> tags =this.activityMapper.selectByExample(example);
		return tags;
	}
	/**
	 * 活动场次表 加载
	 * @param page
	 * @param rows
	 * @param request
	 * @return
	 */
	 public Object activityitmList(int page,int rows,WebRequest request) throws Exception{
		Example example = new Example(WhActivityitm.class);
		Map<String, Object> param = ReqParamsUtil.parseRequest(request);
		String actvrefid= (String)param.get("actvrefid");
		String sort= (String)param.get("sort");
		String order= (String)param.get("order");
		if(actvrefid!=null && !"".equals(actvrefid)){
			example.or().andEqualTo("actvrefid", actvrefid);
		}
		if (sort != null && !"".equals(sort)&& order != null && !"".equals(order)) {
			example.setOrderByClause(sort + " " + order);
		}
		PageHelper.startPage(page, rows);
		List<WhActivityitm> list = this.activityitmMapper.selectByExample(example);
		PageInfo pinfo = new PageInfo(list);

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("total", pinfo.getTotal());
		res.put("rows", pinfo.getList());
		return res;
	 }
	 /**
	  * 活动场次 增加 或 修改
	  * @param actvitm
	  * @param actvrefid
	  * @throws Exception
	  */
	 public void addoreditActivityitm(WhActivityitm  actvitm,String actvrefid) throws Exception{
		 if(actvitm.getActvitmid()==null || "".equals(actvitm.getActvitmid())){
			 //做增加
			 actvitm.setActvitmid(this.commService.getKey("WhActivityitm"));
			 actvitm.setActvitmstate(1);
			 actvitm.setActrefvid(actvrefid);
			 actvitm.setActvitmbmcount(actvitm.getActvitmdpcount());
			 this.activityitmMapper.insert(actvitm);
		 }else{
			 
			 this.activityitmMapper.updateByPrimaryKeySelective(actvitm);
		 }
	 }
	/**
	 * 根据活动id 删除activity
	 * @param id
	 * @throws Exception
	 */
	public void removeactivity(String id) throws Exception{
		this.activityitmMapper.deleteByPrimaryKey(id);
	}
	 /**
     * 后台 活动报名数据加载
     * @param request
     * @return
     */
    public Object loadActivitybm(int page,int rows,WebRequest request)throws Exception{
    	Map<String, Object> param = ReqParamsUtil.parseRequest(request);
    	PageHelper.startPage(page,rows);
    	List<?> list=this.aMapper.selectActivitybm(param);
    	PageInfo pinfo = new PageInfo(list);
    	Map<String, Object> res = new HashMap<String, Object>();
		res.put("total", pinfo.getTotal());
		res.put("rows", pinfo.getList());
    	return res;
    }

    /***
     * 活动报名审核
     * @param activitybm
     * @return
     */
    public String updateActivitybm(WhActivitybm activitybm)throws Exception{
    	//判断状态是否为空
    	//if(activitybm.getActbmstate()!=null){
    	//通过 状态为1 减少 剩余票数
    		if(activitybm.getActbmstate() == 1){
    			//报名场次id
    			String actvitmid = activitybm.getActvitmid();
    			//活动场次对象
    			WhActivityitm wActivityitm = this.activityitmMapper.selectByPrimaryKey(actvitmid);
    			//报名 最大人数
    			int maxpeople = wActivityitm.getActvitmdpcount();
    			if(maxpeople != 0){
	    			//已报名人数 
	    			Example example = new Example(WhActivitybm.class);
	    			Criteria c = example.createCriteria();
	    			c.andEqualTo("actshstate", "1");
	    			c.andEqualTo("actbmstate", 1);
	    			c.andEqualTo("actvitmid", actvitmid);
	    			List<WhActivitybm> listbmcount = this.whActivitybmMapper.selectByExample(example);
	    			
	    			int bmcount = 0; 
	    			for(int i = 0;i < listbmcount.size();i++){
	    				int actbmcount = 0;
	    				if(listbmcount.get(i).getActbmcount() == null){
	    					actbmcount=1;
	    				}else{
	    					actbmcount = listbmcount.get(i).getActbmcount();
	    				}
	    				bmcount += actbmcount;
	    			}
	    			//获取 余票数
	    			int leavecount=maxpeople-bmcount;
	    			
	    			activitybm.setActshstate("1");
	    			if(activitybm.getActbmcount() == null){
	    				activitybm.setActbmcount(1);
	    			}
	    			//actvitmbmcount 余票>购票
	    			if(leavecount >= activitybm.getActbmcount()){
	    				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	    				this.whActivitybmMapper.updateByPrimaryKeySelective(activitybm);
	    				//消息更新提示
	    				this.useaction.addNewAlert(activitybm.getActbmuid(),"1");
	    				
	    				//给报名用户发送短信
	    				WhUser user =this.userMapper.selectByPrimaryKey(activitybm.getActbmuid());
	        			String name = user.getNickname();
	        			WhActivity activity = this.activityMapper.selectByPrimaryKey(activitybm.getActid());
	        			String act = activity.getActvtitle();
	        			//报名审核不通过 发送短信
	        			String moban = WhConstance.getSysProperty("ACT_BM_CHECK");
	        			moban = moban.replace("{name}", name);
	        			moban = moban.replace("{act}", act);
	        			moban = moban.replace("{result}", "审核通过现邀请您"+sdf.format(wActivityitm.getActvitmsdate())+"到"+activity.getActvaddress()+"参加活动");
	        			if(user.getPhone() != null && !"".equals(user.getPhone())){
	        				SmsUtil.sendNotice(user.getPhone(), moban);
	        			}else if(user.getEmail() != null && !"".equals(user.getEmail()) ){
	        				EmailUtil.sendNoticeEmail(user.getEmail(), moban);
	        			}
	    			}else{
	    				return "报名/订票 数量不足！";
	    			}
    				return "审核通过！";
    			}else{
    				activitybm.setActshstate("1");
    				if(activitybm.getActbmcount() == null){
	    				activitybm.setActbmcount(1);
	    			}
    				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    				this.whActivitybmMapper.updateByPrimaryKeySelective(activitybm);
    				//消息更新提示
    				this.useaction.addNewAlert(activitybm.getActbmuid(),"1");
    				
    				//给报名用户发送短信
    				WhUser user =this.userMapper.selectByPrimaryKey(activitybm.getActbmuid());
        			String name = user.getNickname();
        			WhActivity activity = this.activityMapper.selectByPrimaryKey(activitybm.getActid());
        			String act = activity.getActvtitle();
        			//报名审核不通过 发送短信
        			String moban = WhConstance.getSysProperty("ACT_BM_CHECK");
        			moban = moban.replace("{name}", name);
        			moban = moban.replace("{act}", act);
        			moban = moban.replace("{result}", "审核通过现邀请您"+sdf.format(wActivityitm.getActvitmsdate())+"到"+activity.getActvaddress()+"参加活动");
        			if(user.getPhone() != null && !"".equals(user.getPhone())){
        				SmsUtil.sendNotice(user.getPhone(), moban);
        			}else if(user.getEmail() != null && !"".equals(user.getEmail()) ){
        				EmailUtil.sendNoticeEmail(user.getEmail(), moban);
        			}
    				return "审核通过！";
    			}
    		}else{
    			activitybm.setActshstate("3");
    			activitybm.setActbmstate(1);
    			try {
					this.whActivitybmMapper.updateByPrimaryKeySelective(activitybm);
				} catch (Exception e) {
					e.printStackTrace();
				}
    			//消息更新提示
    			this.useaction.addNewAlert(activitybm.getActbmuid(),"1");
    			
    			WhUser user =this.userMapper.selectByPrimaryKey(activitybm.getActbmuid());
    			String name = user.getNickname();
    			WhActivity activity = this.activityMapper.selectByPrimaryKey(activitybm.getActid());
    			String act = activity.getActvtitle();
    			//报名审核不通过 发送短信
    			String moban = WhConstance.getSysProperty("ACT_BM_CHECK");
    			moban = moban.replace("{name}", name);
    			moban = moban.replace("{act}", act);
    			moban = moban.replace("{result}", "审核没通过");
    			if(user.getPhone() != null && !"".equals(user.getPhone())){
    				SmsUtil.sendNotice(user.getPhone(), moban);
    			}else{
    				EmailUtil.sendNoticeEmail(user.getEmail(), moban);
    			}
    			return "审核完成!";
    		}
    //	}
    }
    /**
     * 活动批量报名
     * @param params
     * @param actbmstate
     * @throws Exception
     */
    public String updateselectActivitybm(String params,WhActivitybm bm)throws Exception{
    	Boolean issucc=true;
		//放 报名 id
    	List<String> list1 = new ArrayList<String>();
    	//放 场次id
    	List<String> list2 = new ArrayList<String>();
    	
    	//放 活动id
    	List<String> list3 = new ArrayList<String>();
    	
    	//放 用户id
    	List<String> list4 = new ArrayList<String>();
    	
    	//活动场次 list 
    	List<WhActivityitm> listitm = null;
    	
    	List<WhActivitybm> listbm = null;
    	//获取 活动报名id
	    if(params != null && !"".equals(params)){
			String p[]=params.split(",");
			for(int i=0;i<p.length;i++){
				list1.add(p[i]);
			}
	    	
			//根据报名id 查活动报名信息
			Example example = new Example(WhActivitybm.class);
			example.or().andIn("actbmid",list1);
			listbm = this.whActivitybmMapper.selectByExample(example);
				for (int i = 0; i < listbm.size(); i++) {
					//获取 活动场次id 的集合
					list2.add(listbm.get(i).getActvitmid());
					
					//放 活动id
					list3.add(listbm.get(i).getActid());
					//放 用户id
					list4.add(listbm.get(i).getActbmuid());
				}
			
			//根据活动场次id 查活动场次信息
			Example example2 = new Example(WhActivityitm.class);
			example2.or().andIn("actvitmid",list2);
			listitm = this.activityitmMapper.selectByExample(example2);
			
			if(bm.getActbmstate() == 1){
				for (int i = 0; i < listitm.size(); i++) {
					int bmnum=0;
					//根据 自身场次 id 查找已报名人数
					Example example11 = new Example(WhActivitybm.class);
					Criteria c = example11.createCriteria();
					c.andEqualTo("actshstate", "1");
					c.andEqualTo("actbmstate", 1);
					//c.andEqualTo("actid", listbm.get(i).getActid());
					c.andEqualTo("actvitmid", listitm.get(i).getActvitmid());
					//已报名的 活动 list
					List<WhActivitybm> listbmnum = this.whActivitybmMapper.selectByExample(example11);
					
					for(int j = 0; j < listbmnum.size(); j++){
						bmnum += listbmnum.get(j).getActbmcount();
					}
					//获取 每个活动报名的最大数 获得余数
					int leavecount = listitm.get(i).getActvitmdpcount()-bmnum;
					
					for(int j = 0; j < listbm.size(); j++){
						if(listitm.get(i).getActvitmid().equals(listbm.get(j).getActvitmid())){
							if(leavecount > listbm.get(j).getActbmcount() && listitm.get(i).getActvitmdpcount() != 0){
								leavecount=leavecount-listbm.get(j).getActbmcount();
							}else if(listitm.get(i).getActvitmdpcount() == 0){
							}else{
								issucc=false;
								return "活动报名/余票 数量不足!";
								
							}
						}
					}
				}
			}
			if(issucc){
				Map<String,Object> map = new HashMap<String,Object>();
				Map<String,Object> map1 = new HashMap<String,Object>();
				Map<String,Object> map2 = new HashMap<String,Object>();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				
				//根据活动id 查活动活动信息
				Example el = new Example(WhActivity.class);
				el.or().andIn("actvid",list3);
				List<WhActivity> listact = this.activityMapper.selectByExample(el);
				for (WhActivity whActivity : listact) {
					map.put(whActivity.getActvid(), whActivity);
				}
				
				//根据用户id 查用户信息
				Example el2 = new Example(WhUser.class);
				el2.or().andIn("id",list4);
				List<WhUser> listuser = this.userMapper.selectByExample(el2);
				for (WhUser whUser : listuser) {
					map1.put(whUser.getId(), whUser);
				}
				
				for (WhActivityitm actitm : listitm) {
					map2.put(actitm.getActvitmid(), actitm.getActvitmsdate());
				}
			
				for (WhActivitybm actbm : listbm) {
					if(!actbm.getActshstate().equals("0")){
						continue;
					}
					WhActivity whact = (WhActivity)map.get(actbm.getActid());
					WhUser whuser = (WhUser)map1.get(actbm.getActbmuid());
	    			//报名审核不通过 发送短信
	    			String moban = WhConstance.getSysProperty("ACT_BM_CHECK");
	    			moban = moban.replace("{name}", whuser.getNickname());
	    			moban = moban.replace("{act}", whact.getActvshorttitle() );
	    			if(bm.getActbmstate() == 1){
	    				moban = moban.replace("{result}", "审核通过现邀请您"+sdf.format(map2.get(actbm.getActvitmid()))+"到"+whact.getActvaddress()+"参加活动");
	    			}else{
	    				moban = moban.replace("{result}", "审核没通过");
	    			}
	    			if(whuser.getPhone() != null && !"".equals(whuser.getPhone())){
	    				SmsUtil.sendNotice(whuser.getPhone(), moban);
	    			}else if(whuser.getEmail() != null && !"".equals(whuser.getEmail()) ){
	    				EmailUtil.sendNoticeEmail(whuser.getEmail(), moban);
	    			}
				}
				
				Example example3 = new Example(WhActivitybm.class);
				example3.or().andIn("actbmid", list1);
				if(bm.getActbmstate() == 0){
					bm.setActshstate("3");
				}
				if(bm.getActbmstate() == 1){
					bm.setActshstate("1");
				}
				bm.setActbmstate(1);
				this.whActivitybmMapper.updateByExampleSelective(bm, example3);
				return "审核成功!";
			}
    	}else{
    		return "未选中要审核的记录!";	
    	}
	    return null;
    }
    /**
     * 活动报名删除
     * @param actbmid
     * @return
     */
    public void delActbm(String actbmid,HttpServletRequest request)throws Exception{
    	WhActivitybm activitybm=this.whActivitybmMapper.selectByPrimaryKey(actbmid);
    	String actbmfilepath=activitybm.getActbmfilepath();
    	String rootPath=UploadUtil.getUploadPath(request);
    	UploadUtil.delUploadFile(rootPath, actbmfilepath);
    	
    	this.whActivitybmMapper.deleteByPrimaryKey(actbmid);
    }
    
    /**
     * 发布的活动 设置为模板
     * @param actvidtpl
     * @param req
     * @return
     * @throws Exception
     */
	public String toActivitytpl(WhActivitytpl actvidtpl,HttpServletRequest req) throws Exception{
		if(actvidtpl != null && !"".equals(actvidtpl)){
			//根据 活动id 查找活动
			//WhActivity act = this.activityMapper.selectByPrimaryKey(actvidtpl);
			//查看活动模板中 有无此模板
			Example example = new Example(WhActivitytpl.class);
			example.or().andEqualTo("actvid", actvidtpl.getActvid());
			List<WhActivitytpl> listtpl = this.activitytplMapper.selectByExample(example);
			if(listtpl.size() > 0){
				return "活动模板存在,无需再次添加!";
			}
			actvidtpl.setActvtplid(commService.getKey("whactivitytpl"));
			
			//当前日期
			Date now = new Date();
			//图片或者文件处理
			String uploadPath = UploadUtil.getUploadPath(req);
			//图片处理
			//trapic
			
			//图片导入模板处理
			if(actvidtpl.getActvpic() != null && !"".equals( actvidtpl.getActvpic() )){
				//删除图片
				String picUrl = UploadUtil.getUploadFileDelPath(uploadPath, actvidtpl.getActvpic());
				//获取保存到db的路径getUploadFilePath
				String imgPath = UploadUtil.getUploadFilePath(actvidtpl.getActvpic(), commService.getKey("activity.picture"), "activity", "picture", now);
				//copy 文件
				FileUtil.copyFile(new File(picUrl),UploadUtil.createUploadFile(uploadPath, imgPath));
				//将\转/保存
				actvidtpl.setActvpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath));
			}
			if(actvidtpl.getActvbigpic() != null && !"".equals( actvidtpl.getActvbigpic() )){
				//删除图片
				String picUrl = UploadUtil.getUploadFileDelPath(uploadPath, actvidtpl.getActvbigpic());
				//获取保存到db的路径
				String imgPath = UploadUtil.getUploadFilePath(actvidtpl.getActvbigpic(), commService.getKey("activity.picture"), "activity", "picture", now);
				//copy 文件
				FileUtil.copyFile(new File(picUrl),UploadUtil.createUploadFile(uploadPath, imgPath));
				//将\转/保存
				actvidtpl.setActvbigpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath));
			}
			if(actvidtpl.getActvpersonfile() != null && !"".equals( actvidtpl.getActvpersonfile() )){
				//删除图片
				String picUrl = UploadUtil.getUploadFileDelPath(uploadPath, actvidtpl.getActvpersonfile());
				//获取保存到db的路径
				String imgPath = UploadUtil.getUploadFilePath(actvidtpl.getActvpersonfile(), commService.getKey("activity.archive"), "activity", "archive", now);
				//copy 文件
				FileUtil.copyFile(new File(picUrl),UploadUtil.createUploadFile(uploadPath, imgPath));
				//将\转/保存
				actvidtpl.setActvpersonfile(UploadUtil.getUploadFileUrl(uploadPath, imgPath));
			}
			if(actvidtpl.getActvteamfile() != null && !"".equals( actvidtpl.getActvteamfile() )){
				//删除图片
				String picUrl = UploadUtil.getUploadFileDelPath(uploadPath,actvidtpl.getActvteamfile());
				//获取保存到db的路径
				String imgPath = UploadUtil.getUploadFilePath(actvidtpl.getActvteamfile(), commService.getKey("activity.archive"), "activity", "archive", now);
				//copy 文件
				FileUtil.copyFile(new File(picUrl),UploadUtil.createUploadFile(uploadPath, imgPath));
				//将\转/保存
				actvidtpl.setActvteamfile(UploadUtil.getUploadFileUrl(uploadPath, imgPath));
			}
			actvidtpl.setActvopttime(new Date());
			this.activitytplMapper.insert(actvidtpl);
			return "设置模板成功!";
		}
			return "模板图片/文件找不到,设置模板失败";
		
	}
}
