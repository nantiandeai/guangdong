package com.creatoo.hn.services.admin.system;

import com.creatoo.hn.ext.emun.EnumDelState;
import com.creatoo.hn.ext.emun.EnumState;
import com.creatoo.hn.mapper.WhgSysUserCultMapper;
import com.creatoo.hn.mapper.WhgSysUserMapper;
import com.creatoo.hn.mapper.WhgSysUserRoleMapper;
import com.creatoo.hn.model.*;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.MD5Util;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 管理员服务类
 * Created by wangxl on 2017/3/18.
 */
@Service
public class WhgSystemUserService {
    /**
     * 日志
     */
    Logger log = Logger.getLogger(this.getClass().getName());

    /**
     * 公共服务
     */
    @Autowired
    private CommService commService;

    /**
     * 文化馆DAO
     */
    @Autowired
    private WhgSysUserMapper whgSysUserMapper;

    @Autowired
    private WhgSysUserRoleMapper whgSysUserRoleMapper;

    @Autowired
    private WhgSysUserCultMapper whgSysUserCultMapper;

    /**
     * 分页查询文化馆信息
     * @param request 请求对象
     * @param cult 条件对象
     * @return 分页数据
     * @throws Exception
     */
    public PageInfo<WhgSysUser> t_srchList4p(HttpServletRequest request, WhgSysUser cult)throws Exception{
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);

        //分页信息
        int page = Integer.parseInt((String)paramMap.get("page"));
        int rows = Integer.parseInt((String)paramMap.get("rows"));

        //搜索条件
        Example example = new Example(WhgSysUser.class);
        Example.Criteria c = example.createCriteria();

        //名称条件
        if(cult != null && cult.getAccount() != null){
            c.andLike("account", "%"+cult.getAccount()+"%");
            cult.setAccount(null);
        }

        //其它条件
        c.andEqualTo(cult);

        //排序
        if(paramMap.containsKey("sort") && paramMap.get("sort")!= null){
            StringBuffer sb = new StringBuffer((String)paramMap.get("sort"));
            if(paramMap.containsKey("order") && paramMap.get("order") !=  null){
                sb.append(" ").append(paramMap.get("order"));
            }
            example.setOrderByClause(sb.toString());
        }else{
            example.setOrderByClause("crtdate desc");
        }

        //分页查询
        PageHelper.startPage(page, rows);
        List<WhgSysUser> list = this.whgSysUserMapper.selectByExample(example);
        return new PageInfo<WhgSysUser>(list);
    }

    /**
     * 查询文化馆列表
     * @param request 请求对象
     * @param cult 条件对象
     * @return 文化馆列表
     * @throws Exception
     */
    public List<WhgSysUser> t_srchList(HttpServletRequest request, WhgSysUser cult)throws Exception{
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);

        //搜索条件
        Example example = new Example(WhgSysUser.class);
        Example.Criteria c = example.createCriteria();

        //名称条件
        if(cult != null && cult.getAccount() != null){
            c.andLike("name", "%"+cult.getAccount()+"%");
            cult.setAccount(null);
        }

        //其它条件
        c.andEqualTo(cult);

        //排序
        if(paramMap.containsKey("sort") && paramMap.get("sort")!= null){
            StringBuffer sb = new StringBuffer((String)paramMap.get("sort"));
            if(paramMap.containsKey("order") && paramMap.get("order") !=  null){
                sb.append(" ").append(paramMap.get("order"));
            }
            example.setOrderByClause(sb.toString());
        }else{
            example.setOrderByClause("crtdate desc");
        }

        //分页查询
        return this.whgSysUserMapper.selectByExample(example);
    }

    /**
     * 查询单个文化馆信息
     * @param id 文化馆主键
     * @return 文化馆对象
     * @throws Exception
     */
    public WhgSysUser t_srchOne(String id)throws Exception{
        WhgSysUser record = new WhgSysUser();
        record.setId(id);
        WhgSysUser user =  this.whgSysUserMapper.selectOne(record);
        if(user != null && user.getEpms() != null) {
            user.setEpms(MD5Util.decode4Base64(user.getEpms()));
        }
        return user;
    }

    /**
     *  查询的角色和部分
     * @param roleid 角色ID
     * @return Map
     * @throws Exception
     */
    public Map<String, String> t_srchUserRole(String roleid)throws Exception{
        Example example = new Example(WhgSysUserRole.class);
        example.createCriteria().andEqualTo("sysuserid", roleid);
        List<WhgSysUserRole> roles = this.whgSysUserRoleMapper.selectByExample(example);
        String roleStr = "";
        String sp = "";
        if(roles != null){
            for (WhgSysUserRole role : roles){
                roleStr += sp+role.getSysroleid();
                sp = ",";
            }
        }

        Example examplex = new Example(WhgSysUserCult.class);
        examplex.createCriteria().andEqualTo("sysuserid", roleid);
        List<WhgSysUserCult> cults = this.whgSysUserCultMapper.selectByExample(examplex);
        String cultStr = "";
        String _sp = "";
        if(cults != null){
            for (WhgSysUserCult cult : cults){
                cultStr += _sp+cult.getSyscultid();
                _sp = ",";
            }
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("_roles", roleStr);
        map.put("_cults", cultStr);
        return map;
    }

    /**
     * 添加文化馆
     * @param cult
     * @return
     * @throws Exception
     */
    public WhgSysUser t_add(WhgSysUser cult, String[] roleids, String[] cultids, WhgSysUser user)throws Exception{
        //名称不能重复
        if("administrator".equals(cult.getAccount())){
            throw new Exception("此管理员账号不允许");
        }
        Example example = new Example(WhgSysUser.class);
        Example.Criteria c = example.createCriteria();
        c.andEqualTo("account", cult.getAccount());
        int count = this.whgSysUserMapper.selectCountByExample(example);
        if(count > 0){
            throw new Exception("管理员账号重复");
        }

        //设置初始值
        Date now = new Date();
        cult.setId(commService.getKey("whg_sys_user"));
        cult.setState(EnumState.STATE_YES.getValue());
        cult.setCrtdate(now);
        cult.setCrtuser(user.getId());
        cult.setDelstate(EnumDelState.STATE_DEL_NO.getValue());
        cult.setStatemdfdate(now);
        cult.setStatemdfuser(user.getId());
        int rows = this.whgSysUserMapper.insertSelective(cult);
        if(rows != 1){
            throw new Exception("添加管理员账号失败");
        }

        //角色
        if(roleids != null){
            for(String _role : roleids){
                WhgSysUserRole ur = new WhgSysUserRole();
                ur.setId(commService.getKey("whg_sys_user_role"));
                ur.setState(EnumState.STATE_YES.getValue());
                ur.setCrtdate(now);
                ur.setCrtuser(user.getId());
                ur.setDelstate(EnumDelState.STATE_DEL_NO.getValue());
                ur.setStatemdfdate(now);
                ur.setStatemdfuser(user.getId());
                ur.setSysuserid(cult.getId());
                ur.setSysroleid(_role);
                this.whgSysUserRoleMapper.insertSelective(ur);
            }
        }

        //文化馆
        if(cultids != null){
            for(String _cult : cultids){
                WhgSysUserCult uc = new WhgSysUserCult();
                uc.setId(commService.getKey("whg_sys_user_cult"));
                uc.setState(EnumState.STATE_YES.getValue());
                uc.setCrtdate(now);
                uc.setCrtuser(user.getId());
                uc.setDelstate(EnumDelState.STATE_DEL_NO.getValue());
                uc.setStatemdfdate(now);
                uc.setStatemdfuser(user.getId());
                uc.setSysuserid(cult.getId());
                uc.setSyscultid(_cult);
                this.whgSysUserCultMapper.insertSelective(uc);
            }
        }

        return cult;
    }

    /**
     * 编辑文化馆
     * @param cult
     * @return
     * @throws Exception
     */
    public WhgSysUser t_edit(WhgSysUser cult, String[] roleids, String[] cultids, WhgSysUser user)throws Exception{
        //名称不能重复
        if("administrator".equals(cult.getAccount())){
            throw new Exception("此管理员账号不允许");
        }
        Example example = new Example(WhgSysUser.class);
        Example.Criteria c = example.createCriteria();
        c.andEqualTo("account", cult.getAccount());
        c.andNotEqualTo("id", cult.getId());
        int count = this.whgSysUserMapper.selectCountByExample(example);
        if(count > 0){
            throw new Exception("理员账号重复");
        }

        //设置初始值
        Date now = new Date();
        cult.setStatemdfdate(now);
        cult.setStatemdfuser(user.getId());
        int rows = this.whgSysUserMapper.updateByPrimaryKeySelective(cult);
        if(rows != 1){
            throw new Exception("编辑理员账号失败");
        }

        //角色
        Example examplex = new Example(WhgSysUserRole.class);
        examplex.createCriteria().andEqualTo("sysuserid", cult.getId());
        this.whgSysUserRoleMapper.deleteByExample(examplex);
        if(roleids != null){
            for(String _role : roleids){
                WhgSysUserRole ur = new WhgSysUserRole();
                ur.setId(commService.getKey("whg_sys_user_role"));
                ur.setState(EnumState.STATE_YES.getValue());
                ur.setCrtdate(now);
                ur.setCrtuser(user.getId());
                ur.setDelstate(EnumDelState.STATE_DEL_NO.getValue());
                ur.setStatemdfdate(now);
                ur.setStatemdfuser(user.getId());
                ur.setSysuserid(cult.getId());
                ur.setSysroleid(_role);
                this.whgSysUserRoleMapper.insertSelective(ur);
            }
        }

        //文化馆
        Example exampley = new Example(WhgSysUserCult.class);
        exampley.createCriteria().andEqualTo("sysuserid", cult.getId());
        this.whgSysUserCultMapper.deleteByExample(exampley);
        if(cultids != null){
            for(String _cult : cultids){
                WhgSysUserCult uc = new WhgSysUserCult();
                uc.setId(commService.getKey("whg_sys_user_cult"));
                uc.setState(EnumState.STATE_YES.getValue());
                uc.setCrtdate(now);
                uc.setCrtuser(user.getId());
                uc.setDelstate(EnumDelState.STATE_DEL_NO.getValue());
                uc.setStatemdfdate(now);
                uc.setStatemdfuser(user.getId());
                uc.setSysuserid(cult.getId());
                uc.setSyscultid(_cult);
                this.whgSysUserCultMapper.insertSelective(uc);
            }
        }

        return cult;
    }

    /**
     * 删除文化馆
     * @param ids 文化馆ID
     * @throws Exception
     */
    public void t_del(String ids, WhgSysUser user)throws Exception{
        if(ids != null){
            String[] idArr = ids.split(",");
            Example example = new Example(WhgSysUser.class);
            Example.Criteria c = example.createCriteria();
            c.andIn("id", Arrays.asList(idArr));
            this.whgSysUserMapper.deleteByExample(example);
        }
    }

    /**
     * 更新文化馆状态
     * @param ids 文化馆ID，多个用逗号分隔
     * @param fromState 改状态之前的限制状态
     * @param toState 修改后的状态
     * @throws Exception
     */
    public void t_updstate(String ids, String fromState, String toState, WhgSysUser user)throws Exception{
        if(ids != null && toState != null){
            String[] idArr = ids.split(",");
            Example example = new Example(WhgSysUser.class);
            Example.Criteria c = example.createCriteria();
            c.andIn("id", Arrays.asList(idArr));
            if(fromState != null){
                c.andEqualTo("state", fromState);
            }
            WhgSysUser record = new WhgSysUser();
            record.setState(Integer.parseInt(toState));
            record.setStatemdfdate(new Date());
            record.setStatemdfuser(user.getId());
            this.whgSysUserMapper.updateByExampleSelective(record, example);
        }
    }
}
