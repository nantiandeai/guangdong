package com.creatoo.hn.services.home.userCenter;

import com.creatoo.hn.mapper.WhUserMapper;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserRealService {
	
	@Autowired
	private WhUserMapper userMapper;
	
	@Autowired
	public CommService commservice;

	/** 保存用户实名图片
	 * @param user
	 * @param file
	 * @param filemake
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public WhUser saveUserIdCardPic(WhUser user, MultipartFile file, String filemake, HttpServletRequest request) throws Exception{
		if (file.isEmpty()) return user;
		//图片类型验证
		String mimetype = file.getContentType().toLowerCase();
		if (mimetype == null || !mimetype.startsWith("image")){
			throw new Exception("2");
		}
		//取最新会话用户数据
		user = this.userMapper.selectByPrimaryKey(user.getId());
		//若已审核实名，阻止修改
		if (user.getIsrealname()!=null && user.getIsrealname().compareTo(new Integer(1))==0){
			throw new Exception("1");
		}
		
		String rootPath = UploadUtil.getUploadPath(request);
		//删旧的相关图片
		String oldpath = user.getIdcardface();
		if (filemake!=null && filemake.equalsIgnoreCase("idcardback")){
			oldpath = user.getIdcardback();
		}
		UploadUtil.delUploadFile(rootPath, oldpath);
		
		//处理图片
		Date now = new Date();
		String picPath = UploadUtil.getUploadFilePath(file.getOriginalFilename(), commservice.getKey("whuserreal.picture"), "whuserreal", "picture", now);
		file.transferTo( UploadUtil.createUploadFile(rootPath, picPath) );
		String newPath = UploadUtil.getUploadFileUrl(rootPath, picPath);
		
		//写入新的图片关连
		if (filemake!=null && filemake.equalsIgnoreCase("idcardback")){
			user.setIdcardback(newPath);
		}else{
			user.setIdcardface(newPath);
		}
		_setterState(user);
		this.userMapper.updateByPrimaryKeySelective(user);
		
		//返回新图片访问地址
		return user;
	}
	
	/** 保存用户实名信息
	 * @param user
	 * @return
	 */
	public WhUser saveInfo(WhUser user) throws Exception{
		WhUser _user = this.userMapper.selectByPrimaryKey(user.getId());
		//若已审核实名，阻止修改
		if (_user.getIsrealname()!=null && _user.getIsrealname().compareTo(new Integer(1))==0){
			throw new Exception("1");
		}
		//若已存在审核或待审相同的身份证号，阻止修改
		if (this._getIdcardNum(user.getIdcard(), user.getId()) > 0){
			throw new Exception("3");
		}
		_user.setName(user.getName());
		_user.setIdcard(user.getIdcard());
		_setterState(_user);
		this.userMapper.updateByPrimaryKeySelective(_user);
		return _user;
	}
	
	//设置用户实名状态
	private void _setterState(WhUser user){
		//已实名的不做处理
		if (user.getIsrealname()!=null && user.getIsrealname().compareTo(new Integer(1))==0){
			return;
		}
		//信息完备后设为待审状态
		if (user.getName()!=null && user.getIdcard()!=null && user.getIdcardface()!=null && user.getIdcardback()!=null
				&& _getIdcardNum(user.getIdcard(), user.getId())==0){
			user.setIsrealname(3);
		}else{
			user.setIsrealname(0);
		}
	}

	/**
	 * 检查用户身份证已审和待审状态的记录数，不包函自己ID的计数
	 * @param idcard
	 * @return
     */
	private int _getIdcardNum(String idcard, String uid){
		List<Integer> realState = new ArrayList<Integer>();
		realState.add(1);
		realState.add(3);
		Example example = new Example(WhUser.class);
		example.createCriteria().andEqualTo("idcard", idcard)
			.andIn("isrealname", realState)
			.andNotEqualTo("id", uid);
		return this.userMapper.selectCountByExample(example);
	}
}
