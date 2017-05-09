package com.creatoo.hn.services.comm;

import com.creatoo.hn.ext.emun.EnumDelState;
import com.creatoo.hn.ext.emun.EnumState;
import com.creatoo.hn.mapper.WhgSysRoleMapper;
import com.creatoo.hn.mapper.WhgSysRolePmsMapper;
import com.creatoo.hn.mapper.WhgSysUserMapper;
import com.creatoo.hn.mapper.WhgSysUserRoleMapper;
import com.creatoo.hn.model.WhgSysRole;
import com.creatoo.hn.model.WhgSysRolePms;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.model.WhgSysUserRole;
import com.creatoo.hn.utils.WhConstance;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 权限框架的登录和权限验证
 * @author wangxl
 *
 */
public class ShiroRealmService extends AuthorizingRealm {
	private static final String SUPER_USER_ID = "2015121200000000";//超级管理员ID
	private static final String SUPER_USER_NAME = "administrator";//超级管理员name
	//public static final String SUPER_USER_NICKNAME = "超级管理员";//超级管理员ID
	
	/**
	 * 日志控制器
	 */
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
    private WhgSysRoleMapper whgSysRoleMapper;

	@Autowired
	private WhgSysUserMapper whgSysUserMapper;

	@Autowired
	private WhgSysUserRoleMapper whgSysUserRoleMapper;

	@Autowired
	private WhgSysRolePmsMapper whgSysRolePmsMapper;

	@Autowired
	private MenusService menusService;

	/* 权限检查
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		// 为当前用户设置角色和权限
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		try {
			//用户身份：wh_mgr.id
			String username = (String) principal.getPrimaryPrincipal();
			
			//超级管理员处理
			if(SUPER_USER_NAME.equals(username)){
				//List<String> roleList = new ArrayList<String>();
				List<String> rolepmsList = new ArrayList<String>();
				
				//所有角色
				/*Example example = new Example(WhgSysRole.class);
				example.createCriteria().andEqualTo("state", EnumState.STATE_YES.getValue()).andEqualTo("delstate", EnumDelState.STATE_DEL_NO.getValue());
				List<WhgSysRole> roles = this.whgSysRoleMapper.selectByExample(example);
				if(roles != null){
					for(WhgSysRole role : roles){
						roleList.add(role.getId());
					}
				}*/
				
				//所有权限
				List<Map> menulist =  menusService.getMeunsList();//过滤了无效菜单
				if(menulist != null){
					for(Map menu : menulist){
						if(menu.containsKey("opts") && menu.containsKey("id")){
							String m_opts = (String)menu.get("opts");
							String m_id = (String)menu.get("id");
							if(m_opts != null && m_id != null && !m_opts.isEmpty() && !m_id.isEmpty()){
								String[] _ops = m_opts.split(",");
								for(String _op : _ops){
									String t_op = _op.trim();
									rolepmsList.add(m_id.trim()+":"+t_op);
								}
							}
						}
					}
				}
				
				//simpleAuthorInfo.addRoles(roleList);
				simpleAuthorInfo.addStringPermissions(rolepmsList);
			}else{//普通管理员
				//所有角色，所有权限
				//List<String> roleList = new ArrayList<String>();
				List<String> rolepmsList = new ArrayList<String>();

				// 查询用户的角色
				Subject currentUser = SecurityUtils.getSubject();
				WhgSysUser mgr = (WhgSysUser)currentUser.getSession().getAttribute("user");

				Example example = new Example(WhgSysUserRole.class);
				example.createCriteria().andEqualTo("sysuserid", mgr.getId()).andEqualTo("state", EnumState.STATE_YES.getValue())
				.andEqualTo("delstate", EnumDelState.STATE_DEL_NO);
				List<WhgSysUserRole> userroles = this.whgSysUserRoleMapper.selectByExample(example);
				if(userroles != null && userroles.size() > 0){
					for(WhgSysUserRole ur :userroles ){
						String roleid = ur.getSysroleid();
						if(roleid != null && !roleid.isEmpty()){
							//角色必须有效
							Example role_example = new Example(WhgSysRole.class);
							role_example.createCriteria().andEqualTo("id", roleid).andEqualTo("state", EnumState.STATE_YES.getValue())
									.andEqualTo("delstate", EnumDelState.STATE_DEL_NO.getValue());
							int cnt = this.whgSysRoleMapper.selectCountByExample(role_example);
							if(cnt > 0){
								//roleList.add(role);

								//查询角色权限
								Example pms_example = new Example(WhgSysRolePms.class);
								pms_example.createCriteria().andEqualTo("roleid", roleid).andEqualTo("state", EnumState.STATE_YES.getValue())
										.andEqualTo("delstate", EnumDelState.STATE_DEL_NO.getValue());
								List<WhgSysRolePms> pmsList = this.whgSysRolePmsMapper.selectByExample(pms_example);
								if(pmsList != null){
									for(WhgSysRolePms pms : pmsList){
										if(pms.getPmsstr() != null){
											rolepmsList.add(pms.getPmsstr());
										}
									}
								}
							}

							//simpleAuthorInfo.addRoles(roleList);
							simpleAuthorInfo.addStringPermissions(rolepmsList);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return simpleAuthorInfo;
	}

	/* 登录验证
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		SimpleAuthenticationInfo simpleAuthenticationInfo = null;
		
		try {
			//从token中 获取用户身份信息
			String username = (String) ((UsernamePasswordToken)token).getUsername();
			String password = String.valueOf(((UsernamePasswordToken)token).getPassword());
			
			//超级管理员
			if(SUPER_USER_NAME.equals(username)){
				if(WhConstance.SUPER_USER_PASSWORD.equals(password)){
					WhgSysUser user = new WhgSysUser();
					user.setId(SUPER_USER_ID);
					user.setAccount(SUPER_USER_NAME);
					user.setState(EnumState.STATE_YES.getValue());
					simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, password, getName());
					SecurityUtils.getSubject().getSession().setAttribute("user", user);
				}else{
					throw new UnknownAccountException("用户名密码不正确！");  
				}
			}else{
				//拿username从数据库中查询
				WhgSysUser record = new WhgSysUser();
				record.setAccount(username);
				record.setPassword(password);
				record.setState(EnumState.STATE_YES.getValue());
				WhgSysUser user = this.whgSysUserMapper.selectOne(record);
				if(user != null){
					user.setLastlogintime(new Date());
					this.whgSysUserMapper.updateByPrimaryKeySelective(user);
					simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, password, getName());
					SecurityUtils.getSubject().getSession().setAttribute("user", user);
				}else{
					throw new UnknownAccountException("用户名密码不正确！");  
				}
			}
		} catch (Exception e) {
			//log.error(e.getMessage(), e);
			throw new UnknownAccountException("用户名密码不正确！");
		}

		return simpleAuthenticationInfo;
	}
}
