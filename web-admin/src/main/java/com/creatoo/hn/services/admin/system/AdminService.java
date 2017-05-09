package com.creatoo.hn.services.admin.system;

import com.creatoo.hn.model.WhgAdminHome;
import com.creatoo.hn.mapper.WhMenuMapper;
import com.creatoo.hn.mapper.WhMgrMapper;
import com.creatoo.hn.mapper.WhgAdminHomeMapper;
import com.creatoo.hn.mapper.WhgSysUserMapper;
import com.creatoo.hn.model.WhMenu;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.comm.MenusService;
import com.creatoo.hn.utils.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.ArrayList;
import java.util.List;

@Service
@SuppressWarnings("all")
public class AdminService {
	
	@Autowired
	private WhMgrMapper mgrMapper;

	@Autowired
	private WhgSysUserMapper whgSysUserMapper;
	@Autowired
	private WhMenuMapper menuMapper;
	@Autowired
	private CommService commService;

    @Autowired
    private MenusService menusService;

	@Autowired
	private WhgAdminHomeMapper whgAdminHomeMapper;
	
	/**登录验证
	 * @param name
	 * @param pwd
	 * @return
	 */
	public Object logindo(String name, String pwd)throws Exception{
		WhgSysUser mgr = null;
		try {
			
			Subject currentUser = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(name, pwd);
			currentUser.login(token);
			mgr = (WhgSysUser)currentUser.getSession().getAttribute("user");
			
//			WhMgr record = new WhMgr();
//			record.setName(name);
//			record.setPassword(pwd);
//			String account = mgr.getAccount();
//			WhgSysUser whgSysUser = new WhgSysUser();
//			mgr = this.whgSysUserMapper.selectOne(mgr);
//			mgr.getAccount()

		} catch (Exception e) {
			throw e;
		}
		return mgr;
	}
	
	/**提取全部的树型菜单内容
	 * @return
	 * @throws Exception
	 */
	public Object getMenuList(String type) throws Exception{
		/*//List<WhMenu> list = this.menuMapper.selectAll();
		Example example = new Example(WhMenu.class);
		example.setOrderByClause("parent,idx");
		List<WhMenu> mlist = this.menuMapper.selectByExample(example);
		
		//权限处理
		List<WhMenu> list = new ArrayList<WhMenu>();
		if(!"off".equals(type)){
			if(mlist != null){
				Subject currentUser = SecurityUtils.getSubject();
				for(WhMenu menu : mlist){
					String permission = menu.getId()+":view";
					if(menu.getType() != 1 || currentUser.isPermitted(permission)){
						list.add(menu);
					}
				}
			}
		}else{
			list = mlist;
		}*/
		
		/*List<Object> res = new ArrayList<Object>();
		for (int i=0; i<list.size(); i++) {
			WhMenu whMenu = list.get(i);
			if (whMenu.getId() == null || whMenu.getId().isEmpty()){
				continue;
			}
			if (whMenu.getParent() == null || "".equals(whMenu.getParent().trim())){
				Map item = BeanUtils.describe(whMenu);
				this.compMenuTree(item, list, i);
				res.add(item);
				
				list.remove(i);
				--i;
			}
		}
		return res;*/

        return this.menusService.getMeunsTree4Auth(SecurityUtils.getSubject());
	}
	
//	/**帮助组装树型菜单
//	 * @param item
//	 * @param list
//	 */
//	private void compMenuTree(Map<String,Object> item, List<WhMenu> list, int refidx)throws Exception{
//		if (item.get("children")==null){
//			item.put("children", new ArrayList<Object>());
//		}
//		for(int i=0; i<list.size(); i++){
//			WhMenu m = list.get(i);
//			if (m.getId()==null || m.getId().isEmpty()){
//				continue;
//			}
//			if (m.getParent()==null || m.getParent().equals(m.getId())){
//				continue;
//			}
//
//			if (item.get("id").equals(m.getParent())){
//				List children = (List) item.get("children");
//				try {
//					Map _item = BeanUtils.describe(m);
//					this.compMenuTree(_item, list, refidx);
//					children.add(_item);
//					/*list.remove(i);
//					if (refidx >= i){
//						--refidx;
//					}
//					--i;*/
//				} catch (Exception e) {
//					//...
//				}
//			}
//		}
//	}
	
	/**按Id取得菜单项
	 * @param id
	 * @return
	 */
	public Object getMenu4Id(Object id)throws Exception {
		return this.menuMapper.selectByPrimaryKey(id);
	}
	
	/**获取添加编辑菜单时供选择的上级菜单项
	 * @return
	 * @throws Exception
	 */
	public Object getMenuParent() throws Exception{
		Example example = new Example(WhMenu.class);
		example.or().andEqualTo("type", 0);
		example.setOrderByClause("parent,idx");
		return this.menuMapper.selectByExample(example);
	}
	
	/**添加菜单信息
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	public Object addMenuItem(WhMenu menu) throws Exception{
		menu.setId( this.commService.getKey("WhMenu") );
		
		Example example = new Example(WhMenu.class);
		example.or().andEqualTo("parent", menu.getParent());
		int count = this.menuMapper.selectCountByExample(example);
		
		menu.setIdx(count+1);
		
		int recount = this.menuMapper.insert(menu);
		return recount;
	}
	
	/**编辑菜单信息
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	public Object editMenuItem(WhMenu menu) throws Exception{
		int recount = this.menuMapper.updateByPrimaryKeySelective(menu);
		return recount;
	}
	
	/**删除菜单信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Object removeMenuItem(String id) throws Exception{
		int recount = this.menuMapper.deleteByPrimaryKey(id);
		return recount;
	}
	/**
	 * 修改密码
	 * @param name 管理员账号
	 * @param password2 修改后的密码
	 * @param password4 修改后的密码明文
	 * @return 
	 */
	public void selectmagr(String account, String password2, String password4) throws Exception {
		WhgSysUser whgSysUser = new WhgSysUser();
//		WhMgr whm = new WhMgr();
		whgSysUser.setPassword(password2);
		whgSysUser.setEpms(MD5Util.encode4Base64(password4));
		Example example = new Example(WhgSysUser.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("account", account);
		this.whgSysUserMapper.updateByExampleSelective(whgSysUser, example);
	}

	/**
	 * 首页统计数据查询
	 *
	 * @return
	 * @throws Exception
	 */
	public List<WhgAdminHome> t_srchList() throws Exception {
		return this.whgAdminHomeMapper.homeCount();
	}

	/**
	 * 首页统计（培训根据文化馆分类）
	 *
	 * @return
	 * @throws Exception
	 */
	public List<WhgAdminHome> traGroupByCult() throws Exception {
		return whgAdminHomeMapper.traGroupByCult();
	}

	/**
	 * 首页统计（培训根据艺术类型分类）
	 *
	 * @return
	 * @throws Exception
	 */
	public List<WhgAdminHome> traGroupByArt() throws Exception {
		return whgAdminHomeMapper.traGroupByArt();
	}

	/**
	 * 首页统计（活动根据艺术类型分类）
	 *
	 * @return
	 * @throws Exception
	 */
	public List<WhgAdminHome> actGroupByArt() throws Exception {
		return whgAdminHomeMapper.actGroupByArt();
	}

}
