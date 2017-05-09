package com.creatoo.hn.services.admin.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhMgrMapper;
import com.creatoo.hn.mapper.WhSubvenueMapper;
import com.creatoo.hn.model.WhMgr;
import com.creatoo.hn.model.WhSubvenue;
import com.creatoo.hn.services.BaseService;
import com.creatoo.hn.services.comm.CommService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
/**
 * 管理员业务类
 * @author dzl
 *
 */
@Service
public class ManageService extends BaseService {

	
    @Autowired
    private WhMgrMapper whMgrMapper;

    @Autowired
	public CommService commService;
    
    @Autowired
    public WhSubvenueMapper	subMapper; 
    
    /**
     * 显示管理员列表
     * @return
     */
    public List<WhMgr> getList(){
    	
    	return this.whMgrMapper.selectAll() ;  	
    }
    
    /**
     * 添加管理员信息
     * @throws Exception 
     */
	public void addMar(WhMgr whmgr) throws Exception {
		//登录名不能为administrator
		if("administrator".equals(whmgr.getName())){
			this.setErrfield("name");
			this.setErrorMsg("登录名错误");
			throw new Exception(this.getErrorMsg());
		}
		
		//不能重名
		Example example = new Example(WhMgr.class);
		example.createCriteria().andEqualTo("name", whmgr.getName());
		int cnt = this.whMgrMapper.selectCountByExample(example);
		if(cnt > 0){
			this.setErrfield("name");
			this.setErrorMsg("登录名重复");
			throw new Exception(this.getErrorMsg());
		}
		
		//添加
		whmgr.setId(this.commService.getKey("whmgr"));
		this.whMgrMapper.insert(whmgr);
	}
   
	/**
	 * 根据名称查询记录
	 */
	public int getMgrName(String mgrNname)throws Exception{
		Example example = new Example(WhMgr.class);
		example.createCriteria().andEqualTo("name", mgrNname);
		return this.whMgrMapper.selectCountByExample(example);
	}
	
	/**
	 * 修改管理员信息
	 */
	public void modifyManage(WhMgr whmgr)throws Exception {
		//登录名不能为administrator
		if("administrator".equals(whmgr.getName())){
			this.setErrfield("name");
			this.setErrorMsg("登录名错误");
			throw new Exception(this.getErrorMsg());
		}
		
		//不能重名
		Example example = new Example(WhMgr.class);
		example.createCriteria().andEqualTo("name", whmgr.getName()).andNotEqualTo("id", whmgr.getId());
		int cnt = this.whMgrMapper.selectCountByExample(example);
		if(cnt > 0){
			this.setErrfield("name");
			this.setErrorMsg("登录名重复");
			throw new Exception(this.getErrorMsg());
		}
		
		//修改
		Example example_upd = new Example(WhMgr.class);
		example_upd.createCriteria().andEqualTo("id", whmgr.getId());
		whmgr.setPassword(null);
		this.whMgrMapper.updateByExampleSelective(whmgr, example_upd);
	}
	
   /**
    * 根据id获得管理员信息
    * @param id
    * @return
    */
   public Object getManageId(Object id){
		return this.whMgrMapper.selectByPrimaryKey(id);
	}
  
   /**
    * 删除管理员信息
    */
    public int removeManage(String id)throws Exception{
    	return this.whMgrMapper.deleteByPrimaryKey(id);
    }
    
    /**
     * 修改管理员状态
     * @param id
     * @param state
     * @throws Exception
     */
    public void updMgrState(String id, String state)throws Exception{
    	WhMgr whmgr = new WhMgr();
    	whmgr.setStatus(Integer.parseInt(state));
    	
    	Example example_upd = new Example(WhMgr.class);
		example_upd.createCriteria().andEqualTo("id", id);
		
    	this.whMgrMapper.updateByExampleSelective(whmgr, example_upd);
    }
    
    /**
     * 重置密码
     * @param id
     * @param pwd
     * @throws Excepiton
     */
    public void updMrgPwd(String id, String pwd)throws Exception{
    	WhMgr whmgr = new WhMgr();
    	whmgr.setPassword(pwd);
    	
    	Example example_upd = new Example(WhMgr.class);
		example_upd.createCriteria().andEqualTo("id", id);
		
    	this.whMgrMapper.updateByExampleSelective(whmgr, example_upd);
    }
    
   
   /**
    * 分页管理
    */
   public Map<String, Object> findPage(int page, int rows)throws Exception{
		// 带条件的分页查询
		PageHelper.startPage(page, rows);
		List<WhMgr> list = this.whMgrMapper.selectAll();

		// 取分页信息
		PageInfo<WhMgr> pageInfo = new PageInfo<WhMgr>(list);

		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("total", pageInfo.getTotal());
		rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}
   
    /**
     * 修改密码
     * @param whm
     */
    public void modiPassw(WhMgr whm,String password2,String id) {
    	whm.setId(id);
    	whm.setPassword(password2);
	 this.whMgrMapper.updateByPrimaryKeySelective(whm);
	}
    /**
     * 验证原密码
     * @param id
     * @param password
     * @return
     */
	public Object logindo(String id, String password) {
		WhMgr whm = new WhMgr();
		whm.setId(id);
		whm.setPassword(password);
		try {
			return this.whMgrMapper.selectOne(whm);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 *根据id获得管理员信息
	 * 
	 * @param id
	 * @return
	 */
	public Object getMgr(String id)throws Exception {
		return this.whMgrMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 根据管理员的文化馆标识获得关联的分馆名称
	 * @param venueid
	 * @return
	 */
	public List<WhSubvenue> refSub(String venueid)throws Exception{
		//根据条件查询文化馆信息
		Example exmaple = new Example(WhSubvenue.class);
		if(venueid != null && !"".equals(venueid)){
			exmaple.or(exmaple.createCriteria().andEqualTo("subid", venueid).andEqualTo("substate", 3));
			exmaple.or(exmaple.createCriteria().andEqualTo("subpid", venueid).andEqualTo("substate", 3));
		}else{
			exmaple.or(exmaple.createCriteria().andIsNull("subpid").andEqualTo("substate", 3));
			exmaple.or(exmaple.createCriteria().andEqualTo("subpid", "0").andEqualTo("substate", 3));
			exmaple.or(exmaple.createCriteria().andEqualTo("subpid", "").andEqualTo("substate", 3));
		}
		 List<WhSubvenue>  subList =  this.subMapper.selectByExample(exmaple);
		 //判断文化馆标识是否为空
		 if(venueid == null  || venueid.equals("0") || venueid.equals("")){
				WhSubvenue whsub = new WhSubvenue();
				whsub.setSubid("0");
				whsub.setSubname("总馆"); 
				//判断获得的文化馆信息是否为空
				if(subList == null){
					subList = new ArrayList<WhSubvenue>();
				}
				subList.add(whsub);
		 }
		return subList;
	}

	public void resetpwd(String id, String password) {
		WhMgr whmgr = new WhMgr();
    	whmgr.setPassword(password);
    	Example example_upd = new Example(WhMgr.class);
		example_upd.createCriteria().andEqualTo("id", id);
    	this.whMgrMapper.updateByExampleSelective(whmgr, example_upd);
	}
	
}
