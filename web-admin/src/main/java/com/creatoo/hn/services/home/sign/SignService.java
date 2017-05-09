package com.creatoo.hn.services.home.sign;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.creatoo.hn.mapper.SignMapper;
import com.creatoo.hn.mapper.WhActivityMapper;
import com.creatoo.hn.mapper.WhActivitybmMapper;
import com.creatoo.hn.mapper.WhActivityitmMapper;
import com.creatoo.hn.mapper.WhTraenrMapper;
import com.creatoo.hn.mapper.WhTrainMapper;
import com.creatoo.hn.model.WhActivity;
import com.creatoo.hn.model.WhActivitybm;
import com.creatoo.hn.model.WhActivityitm;
import com.creatoo.hn.model.WhTraenr;
import com.creatoo.hn.model.WhTrain;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.UploadUtil;
import com.creatoo.hn.utils.WhConstance;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class SignService {
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private WhTrainMapper trainMapper;
	@Autowired
	private WhTraenrMapper traenrMapper;
	
	@Autowired
    private CommService commservice;
	
	@Autowired
	private SignMapper signMapper;
	
	@Autowired
	private WhActivityMapper actMapper; 
	@Autowired
	private WhActivityitmMapper actitmMapper;
	@Autowired
	private WhActivitybmMapper actbmMapper;
	
	
	/**
	 * 取对应ID的培训信息
	 * @param traid
	 * @return
	 */
	public WhTrain getTrain4Id(String traid){
		if (traid==null) return null;
		return this.trainMapper.selectByPrimaryKey(traid);
	}
	
	/** 取对应的场次id的活动信息
	 * @param itmid
	 * @return
	 */
	public Object getEventInfo4itmid(String itmid){
		if (itmid == null) return null;
		List<?> list = this.signMapper.selectEventInfoToSign(itmid);
		if (list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 处理报名第一步，保存报名记录
	 * @param traenr
	 */
	public String addStep1(WhTraenr traenr, WhUser user) throws Exception{
		//报名限制验证
		traenr.setEnrstepstate(0);
		checkTrain(traenr, user);
		
		try {
			traenr.setEnrid(this.commservice.getKey("whtraenr"));
			traenr.setEnruid(user.getId());
			
			this.traenrMapper.insert(traenr);
			return traenr.getEnrid();
		} catch (Exception e) {
			throw new Exception("保存报名信息失败", e);
		}
	}
	
	/**
	 * 报名的限制验证,初值处理
	 * "1"="指定的培训已不存在"
	 * "2"="指定的培训已取消了发布"
	 * "3"="指定的培训不需要报名"
	 * "4"="报名时间未开始"
	 * "5"="报名时间已结束"
	 * "6"="当前培训不允许个人报名"
	 * "7"="当前培训不允许团队报名"
	 * "8"="当前培训报名人数已达上限"
	 * "9"="当前培训不允许已报过同类型培训的重复报名"
	 * "10"="当前培训需要实名认证才可报名"
	 * "11"="当前培训需要完善资料才可报名"
	 * "12"="已存在指定培训的报名记录"
	 * "14,15,16" = 年龄段验证
	 * 
	 * @param traenr
	 * @param user
	 * @throws Exception
	 */
	private void checkTrain(WhTraenr traenr, WhUser user) throws Exception{
		//验证train 是否存在
		WhTrain train =this.getTrain4Id(traenr.getEnrtraid());
		if (train == null){
			throw new Exception("1");
		}
		//验证是培训记录是否有效
		if (train.getTrastate()!=null && train.getTrastate().compareTo(new Integer(3))!=0){
			throw new Exception("2");
		}
		//验证是否需要报名
		if (train.getTraisenrol()!=null && train.getTraisenrol().compareTo(new Integer(1))!=0){
			throw new Exception("3");
		}
		//验证报名时间是否有效
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(System.currentTimeMillis());
		Calendar traenrolstime = Calendar.getInstance();
		traenrolstime.setTime(train.getTraenrolstime());
		Calendar raenroletime = Calendar.getInstance();
		raenroletime.setTime(train.getTraenroletime());
		if (now.compareTo(traenrolstime)<0){
			throw new Exception("4");
		}
		if (now.compareTo(raenroletime)>0){
			throw new Exception("5");
		}
		traenr.setEnrtime(now.getTime());//设置报名时间
		
		//验证个人或团队报名
		if ( (train.getTracanperson()==null || train.getTracanperson().compareTo(new Integer(0))==0)
				&& traenr.getEnrtype().compareTo(new Integer(0))==0){
			throw new Exception("6");
		}
		if ( (train.getTracanteam()==null || train.getTracanteam().compareTo(new Integer(0))==0)
				&& traenr.getEnrtype().compareTo(new Integer(1))==0){
			throw new Exception("7");
		}
		//验证报名人数
		if (train.getTraenrollimit()!=null && train.getTraenrollimit().compareTo(new Integer(0))>0){
			int bmcount = this.getBaomingCount(traenr.getEnrtraid(), train.getTraisenrolqr());
			if (train.getTraenrollimit().compareTo(new Integer(bmcount))<=0){
				throw new Exception("8");
			}
		}
		
		//验证是否一人只能参加一个
		boolean typonly = this.getUserBaomingTypeOnly(train.getTratyp(), user.getId(), traenr.getEnrtype());
		if (!typonly){
			throw new Exception("9");
		}
		
		//验证实名或完善资料
		if (train.getTraisrealname()!=null && train.getTraisrealname().compareTo(new Integer(1))==0){
			if (user.getIsrealname()==null || user.getIsrealname().compareTo(new Integer(1))!=0){
				throw new Exception("10");
			}
		}
		traenr.setEnrisa("2"); //初设不需要完善
		if (train.getTraisfulldata()!=null && train.getTraisfulldata().compareTo(new Integer(1))==0){
			traenr.setEnrisa("0");	//设为需要但没有完善
			if (user.getIsperfect()==null || user.getIsperfect().compareTo(new Integer(1))!=0){
				throw new Exception("11");
			}
			traenr.setEnrisa("1");	//设为需要已完善
		}
		
		//验证同一人不能以同种方式多次提交同一培训报名
		int onlyCount = this.getUserBaomingCount4Only(user.getId(), train.getTraid(), traenr.getEnrtype());
		if (onlyCount >0 ){
			throw new Exception("12");
		}
		
		//年龄段验证
		this.checkUserAge(user, train.getTraagelevel(), train.getTraisrealname());
		
		//处理是否要上传附件值
		if (train.getTraisattach()==null || train.getTraisattach().compareTo(new Integer(0))==0){
			traenr.setEnrisb("2");
			traenr.setEnrstepstate(1);
		}else{
			traenr.setEnrisb("0");
		}
		//处理是否要审核值
		if (train.getTraisenrolqr()==null || train.getTraisenrolqr().compareTo(new Integer(0))==0){
			traenr.setEnrstate("2");
		}else{
			traenr.setEnrstate("0");
		}
	}
	
	
	/**
	 * 培训报名验证一人一项类型限制下指定用户的相关个人或团队报名情况
	 * @param tratyp
	 * @param uid
	 * @param enrtype
	 * @return
	 */
	public boolean getUserBaomingTypeOnly(String tratyp, String uid, Integer enrtype){
		int onlytypecount = this.signMapper.selectOneInOneType(uid, enrtype, tratyp);
		if (onlytypecount >0 ) 
			return false;
		else 
			return true;
	}
	
	/** 查找指定Id用户在指定id的培训以指定的团队或个人类型的报名数
	 * @param uid
	 * @param traid
	 * @param enrtype
	 * @return
	 */
	public int getUserBaomingCount4Only(String uid, String traid, Integer enrtype){
		Example example = new Example(WhTraenr.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("enrtraid", traid);
		c.andEqualTo("enruid", uid);
		c.andEqualTo("enrtype", enrtype);
		return this.traenrMapper.selectCountByExample(example);
	}
	
	/** 查找相关培训的有效报名人数
	 * @param traid
	 * @param isCheck
	 * @return
	 */
	public int getBaomingCount(String traid, Integer isCheck){
		Example example = new Example(WhTraenr.class);
		Criteria c = example.createCriteria();
		//相关的培训
		c.andEqualTo("enrtraid", traid);
		//审核状态不通过的不算记数
		c.andNotEqualTo("enrstate", "3");
		
		//如果要审查审过的记录数
		/*if (isCheck !=null && isCheck.compareTo(new Integer(1)) == 0){
			c.andEqualTo("enrstate", "1");
		}*/
		
		return this.traenrMapper.selectCountByExample(example);
	}
	
	/** 用户ID与报名ID找培训报名记录
	 * @param enrid
	 * @param uid
	 * @return
	 */
	public WhTraenr getTraenr4KeyandUid(String enrid, String uid){
		Example example = new Example(WhTraenr.class);
		example.createCriteria().andEqualTo("enrid", enrid).andEqualTo("enruid",uid);
		List<WhTraenr> list = this.traenrMapper.selectByExample(example);
		if (list==null || list.size()==0){
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 将上传的报名文件暂存到临时目录中
	 * @param enrid
	 * @param file
	 * @throws Exception 
	 */
	public void addEnrFile2Temp(String enrid, String mark, MultipartFile file, HttpServletRequest req) throws Exception{
		if (file.isEmpty()) return;
		//临时目录
		String uploadPath = UploadUtil.getUploadPath(req);
		String tempDIR = uploadPath+File.separator+"TEMP_ENR"+File.separator+mark+File.separator+enrid;
		File tempFile = new File(tempDIR, file.getOriginalFilename());
		if (!tempFile.exists()){
			tempFile.mkdirs();
		}
		
		file.transferTo(tempFile);
	}
	
	/** 清除报名文件上传和打包的临时目录
	 * @param enrid
	 * @param req
	 */
	public void clearTemp(String enrid, String mark, HttpServletRequest req){
		try {
			//临时目录
			String uploadPath = UploadUtil.getUploadPath(req);
			String tempDIR = uploadPath+File.separator+"TEMP_ENR"+File.separator+mark+File.separator+enrid;
			File tempFile = new File(tempDIR);
			FileUtils.deleteDirectory(tempFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 处理报名模板文件上传与打包
	 * @param enrid
	 * @param req
	 * @throws Exception
	 */
	public void optEnrFiles(String enrid, String mark, HttpServletRequest req) throws Exception{
		//临时目录
		String uploadPath = UploadUtil.getUploadPath(req);
		String tempDIR = uploadPath+File.separator+"TEMP_ENR"+File.separator+mark+File.separator+enrid;
		File tempFile = new File(tempDIR);
		
		if (!tempFile.exists() || !tempFile.isDirectory()){
			return ;
		}
		
		//要打包的文件列表
		File[] enrfiles = tempFile.listFiles();
		if (enrfiles.length == 0) return ;
		
		WhTraenr enr = null;
		WhActivitybm actbm = null;
		String tablename = "";
		String oldPath = "";
		if (mark!=null && mark.equalsIgnoreCase("event")){
			actbm = this.actbmMapper.selectByPrimaryKey(enrid);
			tablename = "whactivitybm";
			oldPath = actbm.getActbmfilepath();
		}else{
			enr = this.traenrMapper.selectByPrimaryKey(enrid);
			tablename = "whtraenr";
			oldPath = enr.getEnrfilepath();
		}
		UploadUtil.delUploadFile(uploadPath, oldPath);
		
		Date now = new Date();
		//只一个文件，直接复制到数据目录去
		String dbfilepath = "";
		if (enrfiles.length == 1){
			File flie = enrfiles[0];
			dbfilepath = UploadUtil.getUploadFilePath(flie.getName(), commservice.getKey(tablename+".archive"), tablename, "archive", now);
			
			File dbfile = UploadUtil.createUploadFile(uploadPath, dbfilepath);
			if (dbfile.exists()){
				dbfile.delete();
			}
			dbfile.createNewFile();
			
			FileUtils.copyFile(flie, UploadUtil.createUploadFile(uploadPath, dbfilepath));
		}else{
			dbfilepath = UploadUtil.getUploadFilePath("XXX.zip", commservice.getKey(tablename+".archive"), tablename, "archive", now);
			
			zipFiel(enrfiles, UploadUtil.createUploadFile(uploadPath, dbfilepath));
		}
		if (enr != null){
			enr.setEnrfilepath(UploadUtil.getUploadFileUrl(uploadPath, dbfilepath) );
			enr.setEnrisb("1");
			enr.setEnrstepstate(1);
			this.traenrMapper.updateByPrimaryKeySelective(enr);
		}
		if (actbm != null){
			actbm.setActbmfilepath(UploadUtil.getUploadFileUrl(uploadPath, dbfilepath) );
			actbm.setActbmisb("1");
			actbm.setActbmstate(1);
			this.actbmMapper.updateByPrimaryKeySelective(actbm);
		}
		
		FileUtils.deleteDirectory(tempFile);
	}
	
	//处理文件压缩
	private void zipFiel(File[] files, File dbfile) throws Exception{
		if (dbfile.exists()){
			dbfile.delete();
		}
		dbfile.createNewFile();
		
		ZipOutputStream zipStream = new ZipOutputStream(new FileOutputStream(dbfile));
		
		for (File file : files){
			BufferedInputStream bufferStream = null;
			FileInputStream zipSource = null;
			try {
				
				zipSource = new FileInputStream(file);
				ZipEntry zipEntry = new ZipEntry(file.getName());
				zipStream.putNextEntry(zipEntry);//定位到该压缩条目位置，开始写入文件到压缩包中
				
				byte[] bufferArea = new byte[1024 * 10];//读写缓冲区
				bufferStream = new BufferedInputStream(zipSource, 1024 * 10);//输入缓冲流
				int read = 0;
				while((read = bufferStream.read(bufferArea, 0, 1024 * 10)) != -1)
				{
					zipStream.write(bufferArea, 0, read);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			} finally {
				if (bufferStream!=null) bufferStream.close();
				if (zipSource!=null) zipSource.close();
			}
		}
		
		zipStream.close();
	}
	
	/** 取相关模板文件对应的文件数，供给报名上传文件个数限制
	 * @param filePath
	 * @return
	 */
	public int getTempFileCount(String filePath){
		ZipFile zf = null;
		try {
			File file = new File(filePath);
			if (!file.exists()) return 1;
			String fname = file.getName();
			String ext = fname.substring(fname.lastIndexOf(".")).toLowerCase();
			
			List<String> exts = new ArrayList<String>();
			exts.add(".zip");
			
			if (!exts.contains(ext)){
				return 1;
			}
			zf = new ZipFile(file);
			@SuppressWarnings("unchecked")
			Enumeration<ZipEntry> enu = (Enumeration<ZipEntry>) zf.entries();
			int fnum = 0;
			while(enu.hasMoreElements() ){
				ZipEntry ze = enu.nextElement();
				if (!ze.isDirectory()){
					fnum ++;
				}
			}
			//System.out.println("zip file num test : sumNum:"+zf.size()+" fileNum:"+fnum);
			return fnum;//zf.size();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return 1;
		} finally {
			try {
				if (zf!=null) zf.close();
			} catch (IOException e) {
				//...
			}
		}
	}
	
	
	/** 添加活动报名信息
	 * @param actbm
	 * @param userInfo
	 * @return
	 */
	public String addEventBmStep1(WhActivitybm actbm, WhUser user) throws Exception{
		//按传入参数取相关的报名对像数据
		String actitmid = actbm.getActvitmid(); 
		//相关活动时间段
		WhActivityitm actitm = this.actitmMapper.selectByPrimaryKey(actitmid);
		//相关活动数据
		WhActivity act = this.actMapper.selectByPrimaryKey(actitm.getActrefvid());
		
		actbm.setActbmstate(0);
		//检查报名条件
		checkEventBm(actbm, user, act, actitm);
		
		try {
			actbm.setActbmid(this.commservice.getKey("whactivitybm"));
			actbm.setActbmuid(user.getId());
			actbm.setActid(actitm.getActrefvid());
			
			this.actbmMapper.insert(actbm);
			return actbm.getActbmid();
		} catch (Exception e) {
			throw new Exception("提交活动报名失败", e);
		}
	}
	
	/**  报名的限制验证,初值处理
	 * "1"="指定的已不存在"
	 * "2"="指定的已取消了发布"
	 * "3"="指定的不需要报名"
	 * "4"="报名时间未开始"
	 * "5"="报名时间已结束"
	 * "6"="不允许个人报名"
	 * "7"="不允许团队报名"
	 * "8"="报名人数已达上限"
	 * "9"="不允许已报过同类型的重复报名"
	 * "10"="需要实名认证才可报名"
	 * "11"="需要完善资料才可报名"
	 * "12"="已存在报名记录"
	 * "13"="单次报名人数超过限定"
	 * "14,15,16" = 年龄段验证
	 * @param actbm
	 * @param user
	 * @param act
	 * @param actitm
	 * @throws Exception
	 */
	private void checkEventBm(WhActivitybm actbm, WhUser user, WhActivity act, WhActivityitm actitm) throws Exception{
		if (act==null || actitm ==null || (actitm.getActvitmstate()==null || actitm.getActvitmstate().compareTo(new Integer(1))!=0)){
			throw new Exception("1");
		}
		if (act.getActvstate()==null || act.getActvstate().compareTo(new Integer(3))!=0){
			throw new Exception("2");
		}
		if (act.getActvisenrol()==null || act.getActvisenrol().compareTo(new Integer(1))!=0){
			throw new Exception("3");
		}
		Date now = new Date(System.currentTimeMillis());
		if (actitm.getActvbmsdate()!=null && actitm.getActvbmsdate().after(now)){
			throw new Exception("4");
		}
		if (actitm.getActvbmedate()!=null && actitm.getActvbmedate().before(now)){
			throw new Exception("5");
		}
		actbm.setActbmtime(now); //设置报名时间
		
		if (actbm.getActbmtype()!=null && actbm.getActbmtype().compareTo(new Integer(0))==0){
			if (act.getActvcanperson()==null || act.getActvcanperson().compareTo(new Integer(1))!=0){
				throw new Exception("6");
			}
		}
		if (actbm.getActbmtype()!=null && actbm.getActbmtype().compareTo(new Integer(1))==0){
			if (act.getActvcanteam()==null || act.getActvcanteam().compareTo(new Integer(1))!=0){
				throw new Exception("7");
			}
		}
		
		if (actitm.getActvitmdpcount()!=null && actitm.getActvitmdpcount().compareTo(new Integer(0))>0){
			int count = eventBmCount(actitm.getActvitmid(), act.getActvisenrolqr());
			if (actitm.getActvitmdpcount().compareTo(new Integer(count))<=0){
				throw new Exception("8");
			}
		}
		if (act.getActvisrealname()!=null && act.getActvisrealname().compareTo(new Integer(1))==0){
			if (user.getIsrealname()==null || user.getIsrealname().compareTo(new Integer(1))!=0){
				throw new Exception("10");
			}
		}
		actbm.setActbmisa("2"); //初设不需完善
		if (act.getActvisfulldata()!=null && act.getActvisfulldata().compareTo(new Integer(1))==0){
			if (user.getIsperfect()==null || user.getIsperfect().compareTo(new Integer(1))!=0){
				throw new Exception("11");
			}
			actbm.setActbmisa("1");//设为已完善
		}
		
		int bmcount = this.signMapper.selectEventBmSum4User(actbm.getActbmtype(), user.getId(), actbm.getActvitmid());
		if (bmcount>0){
			throw new Exception("12");
		}
		
		//单次报名人数
		if (actbm.getActbmcount()==null) actbm.setActbmcount(1);
		Integer actvitmonemax = actitm.getActvitmonemax()==null? 1 : actitm.getActvitmonemax();
		if (actbm.getActbmcount().compareTo(actvitmonemax)>0){
			throw new Exception("13");
		}
		
		//年龄段验证
		this.checkUserAge(user, act.getActvagelevel(), act.getActvisrealname());
		
		//处理是否要上传附件值
		if (act.getActvisattach()==null || act.getActvisattach().compareTo(new Integer(0))==0){
			actbm.setActbmisb("2");
			actbm.setActbmstate(1);
		}else{
			actbm.setActbmisb("0");
		}
		//处理是否要审核值
		if (act.getActvisenrolqr()==null || act.getActvisenrolqr().compareTo(new Integer(0))==0){
			actbm.setActshstate("2");
		}else{
			actbm.setActshstate("0");
		}
	}
	
	//活动报名有效数量
	private int eventBmCount(String itmid, Integer isCheck){
		/*Example example = new Example(WhActivitybm.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("actvitmid", itmid);
		if (isCheck!=null && isCheck.compareTo(new Integer(1))==0){
			c.andEqualTo("actshstate", 1);
		}
		return this.actbmMapper.selectCountByExample(example);*/
		
		String shstate = null;
		/*if (isCheck!=null && isCheck.compareTo(new Integer(1))==0){
			shstate = "1";
		}*/
		Object sum = this.signMapper.selectEventBmCount(itmid, shstate);
		if (sum == null) return 0;
		return Integer.parseInt( sum.toString());
	}
	
	/** 取用户ID与报名ID相关的活动报名
	 * @param bmid
	 * @param uid
	 * @return
	 */
	public WhActivitybm getEventBmInfo(String bmid, String uid){
		Example example = new Example(WhActivitybm.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("actbmid", bmid);
		c.andEqualTo("actbmuid", uid);
		List<WhActivitybm> list = this.actbmMapper.selectByExample(example);
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	
	/** 查询是否指定的培训需要收费
	 * @param traid
	 * @return
	 */
	public Object findTraenIsmoney(String traid){
		Map<String, Object> rest = this.signMapper.findTraenIsmoney(traid);
		if (rest!=null && rest.containsKey("ismoney")){
			return rest.get("ismoney");
		}
		return 0;
	}
	
	/** 查询是否指定的活动场次需要收费
	 * @param actvitmid
	 * @return
	 */
	public Object findActItmIsmoney(String actvitmid){
		Map<String, Object> rest = this.signMapper.findActItmIsmoney(actvitmid);
		if (rest!=null && rest.containsKey("ismoney")){
			return rest.get("ismoney");
		}
		return 0;
	}
	
	
	/** 验证实名限制时报名者年龄段
	 * @param user
	 * @param age
	 * @param isreal
	 * @throws Exception
	 */
	private void checkUserAge(WhUser user, String agetag, Integer isreal) throws Exception{
		//如果不需要实名，不做验证
		if(isreal==null || isreal.compareTo(new Integer(1))!=0 ){
			return;
		}
		//如果没有指名年龄段不处理
		if(agetag==null || agetag.isEmpty()){
			return;
		}
		//如果用户没有实名不处理，实名验证处理是否实名
		if (user.getIsrealname()==null || user.getIsrealname().compareTo(new Integer(1))!=0){
			return;
		}
		//身份证号信息
		String idcard = user.getIdcard();
		if (idcard == null || idcard.isEmpty()){
			return;
		}
		int ageNum = this.getAge(idcard);
		
		//按年龄段取对应配置
		if (agetag.equals("2016101400000014")){//少儿
			int[] cfgages = this.cfgageInfo("AGE_GROUP_YOUNG");
			if (cfgages!=null && !(cfgages[0]<= ageNum && ageNum <=cfgages[1]) ){
				throw new Exception("14");
			}
		}
		if (agetag.equals("2016101400000015")){//成人
			int[] cfgages = this.cfgageInfo("AGE_GROUP_ADULT");
			if (cfgages!=null && !(cfgages[0]<= ageNum && ageNum <=cfgages[1]) ){
				throw new Exception("15");
			}
		}
		if (agetag.equals("2016101400000016")){//老年
			int[] cfgages = this.cfgageInfo("AGE_GROUP_OLD");
			if (cfgages!=null && !(cfgages[0]<= ageNum && ageNum <=cfgages[1]) ){
				throw new Exception("16");
			}
		}
	}
	//取身份证所示年龄
	private int getAge(String idcard) throws Exception{
		//身份证不能提到出生年月日的不处理
		String pt = "^\\d{6}(\\d{4})(\\d{2})(\\d{2}).*";
		if(!idcard.matches(pt)){
			return -1;
		}
		//取身份证号计算年龄
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int nowYear = c.get(Calendar.YEAR);
		int nowMM = c.get(Calendar.MONTH);
		String _srstr = idcard.replaceAll(pt, "$1-$2-$3");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date userbth = sdf.parse(_srstr);
		c.setTime(userbth);
		int ubthYear = c.get(Calendar.YEAR);
		int ubthMM = c.get(Calendar.MONTH);
		int ageNum = (nowMM > ubthMM)? (nowYear-ubthYear)-1 : (nowYear-ubthYear);
		return ageNum;
	}
	//取年龄段参数配置
	private int[] cfgageInfo(String cfgkey) throws Exception{
		String cfg_age = WhConstance.getSysProperty(cfgkey, "");
		String[] cfgages = cfg_age.split("\\D+", 2);
		if (cfgages.length ==0){
			return null;
		}
		
		int[] cfgs = new int[2];
		if (cfgages.length == 1 ){
			if (cfgkey.equals("AGE_GROUP_YOUNG")){
				cfgs[0] = 0;
				cfgs[1] = Integer.parseInt(cfgages[0]);
			}
			else if (cfgkey.equals("AGE_GROUP_OLD")){
				cfgs[0] = Integer.parseInt(cfgages[0]);
				cfgs[1] = 150;
			}
			else{
				cfgs[0] = Integer.parseInt(cfgages[0]);
				cfgs[1] = Integer.parseInt(cfgages[0]);
			}
		}
		
		if (cfgages.length ==2){
			cfgs[0] = Integer.parseInt(cfgages[0]);
			cfgs[1] = Integer.parseInt(cfgages[1]);
		}
		
		return cfgs;
	}
}
