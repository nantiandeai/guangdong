package com.creatoo.hn.services.admin.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhRoleMapper;
import com.creatoo.hn.mapper.WhRolepmsMapper;
import com.creatoo.hn.model.WhRole;
import com.creatoo.hn.model.WhRolepms;
import com.creatoo.hn.services.BaseService;
import com.creatoo.hn.services.comm.CommService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class RoleService extends BaseService {

	@Autowired
	public WhRoleMapper roleMapper;
	
	@Autowired
	public WhRolepmsMapper rolepmsMapper;
	
	@Autowired
	private CommService commService;
	
	/**
	 * 查找所有角色
	 * @return
	 */
	public Object getList(int page,int rows)throws Exception{
		Example example=new Example(WhRole.class);
		PageHelper.startPage(page, rows);
		List<WhRole> rlist = this.roleMapper.selectByExample(example);
		// 取分页信息
        PageInfo<WhRole> pageInfo = new PageInfo<WhRole>(rlist);
        
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap; 
	}
	
	/**
	 * 查询所有可用的角色
	 * @return
	 * @throws Exception
	 */
	public Object srchAllRole()throws Exception{
		Example example = new Example(WhRole.class);
		example.createCriteria().andEqualTo("state", "1");
		
		return this.roleMapper.selectByExample(example);
	}
	
	/**
	 * 根据角色标识查询权限
	 * @param id 角色标识
	 * @return
	 * @throws Exception
	 */
	public List<WhRolepms> getRolePMS(String id)throws Exception{
		Example example_pms = new Example(WhRolepms.class);
		example_pms.createCriteria().andEqualTo("rpmroleid", id);
		return this.rolepmsMapper.selectByExample(example_pms);
	}
	
	
	/**
	 * 修改角色状态
	 * @param id 角色标识
	 * @param state 状态
	 * @throws Exception
	 */
	public void updateRoleState(String id, String state)throws Exception{
		Example example = new Example(WhRole.class);
		example.createCriteria().andEqualTo("id", id);
		
		WhRole role = new WhRole();
		role.setState(state);
		this.roleMapper.updateByExampleSelective(role, example);
	}	
	
	/**
	 * 删除角色
	 * @return
	 */
	public int delRole(String id)throws Exception{
		//先删除角色权限 
		Example example_pms = new Example(WhRolepms.class);
		example_pms.createCriteria().andEqualTo("rpmroleid", id);
		this.rolepmsMapper.deleteByExample(example_pms);
		
		//再删除角色
		return this.roleMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 增加角色
	 * @return
	 */
	public Object addRole(WhRole whrole, String[] pms)throws Exception{
		//角色名不能重复
		Example example = new Example(WhRole.class);
		example.createCriteria().andEqualTo("name", whrole.getName());
		int cnt = this.roleMapper.selectCountByExample(example);
		if(cnt > 0){
			this.setErrfield("name");
			throw new Exception("角色名称不能重复!");
		}
		
		//添加角色
		whrole.setId(this.commService.getKey("whrole"));
		this.roleMapper.insert(whrole);
		
		//添加角色权限
		if(pms != null){
			for(String p : pms){
				WhRolepms rpms = new WhRolepms();
				rpms.setRpmid(this.commService.getKey("whrolepms"));
				rpms.setRpmroleid(whrole.getId());
				rpms.setRpmstr(p);
				this.rolepmsMapper.insert(rpms);
			}
		}
		
		return whrole;
	}
	
	/**
	 * 修改角色
	 * @return
	 */
	public Object updateRole(WhRole whrole, String[] pms)throws Exception{
		//角色名不能重复
		Example example = new Example(WhRole.class);
		example.createCriteria().andEqualTo("name", whrole.getName()).andNotEqualTo("id", whrole.getId());
		int cnt = this.roleMapper.selectCountByExample(example);
		if(cnt > 0){
			this.setErrfield("name");
			throw new Exception("角色名称不能重复!");
		}
		
		//更新角色
		this.roleMapper.updateByPrimaryKey(whrole);
		
		//更新权限(先删除角色的所有权限,再添加权限)
		Example example_pms = new Example(WhRolepms.class);
		example_pms.createCriteria().andEqualTo("rpmroleid", whrole.getId());
		this.rolepmsMapper.deleteByExample(example_pms);
		if(pms != null){
			for(String p : pms){
				WhRolepms rpms = new WhRolepms();
				rpms.setRpmid(this.commService.getKey("whrolepms"));
				rpms.setRpmroleid(whrole.getId());
				rpms.setRpmstr(p);
				this.rolepmsMapper.insert(rpms);
			}
		}
		return whrole;
	}
}
