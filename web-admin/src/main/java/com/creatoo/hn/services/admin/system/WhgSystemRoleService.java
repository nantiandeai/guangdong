package com.creatoo.hn.services.admin.system;

import com.creatoo.hn.ext.emun.EnumDelState;
import com.creatoo.hn.ext.emun.EnumState;
import com.creatoo.hn.mapper.WhgSysRoleMapper;
import com.creatoo.hn.mapper.WhgSysRolePmsMapper;
import com.creatoo.hn.model.*;
import com.creatoo.hn.services.comm.CommService;
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
 * 角色|岗位服务类
 * Created by wangxl on 2017/3/18.
 */
@Service
public class WhgSystemRoleService {
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
     * 角色DAO
     */
    @Autowired
    private WhgSysRoleMapper whgSysRoleMapper;

    /**
     * 角色权限DAO
     */
    @Autowired
    private WhgSysRolePmsMapper whgSysRolePmsMapper;

    /**
     * 分页查询文化馆信息
     * @param request 请求对象
     * @param cult 条件对象
     * @return 分页数据
     * @throws Exception
     */
    public PageInfo<WhgSysRole> t_srchList4p(HttpServletRequest request, WhgSysRole cult)throws Exception{
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);

        //分页信息
        int page = Integer.parseInt((String)paramMap.get("page"));
        int rows = Integer.parseInt((String)paramMap.get("rows"));

        //搜索条件
        Example example = new Example(WhgSysRole.class);
        Example.Criteria c = example.createCriteria();

        //名称条件
        if(cult != null && cult.getName() != null){
            c.andLike("name", "%"+cult.getName()+"%");
            cult.setName(null);
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
        List<WhgSysRole> list = this.whgSysRoleMapper.selectByExample(example);
        return new PageInfo<WhgSysRole>(list);
    }

    /**
     * 查询文化馆列表
     * @param request 请求对象
     * @param cult 条件对象
     * @return 文化馆列表
     * @throws Exception
     */
    public List<WhgSysRole> t_srchList(HttpServletRequest request, WhgSysRole cult)throws Exception{
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);

        //搜索条件
        Example example = new Example(WhgSysRole.class);
        Example.Criteria c = example.createCriteria();

        //名称条件
        if(cult != null && cult.getName() != null){
            c.andLike("name", "%"+cult.getName()+"%");
            cult.setName(null);
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
        return this.whgSysRoleMapper.selectByExample(example);
    }

    /**
     * 查询单个文化馆信息
     * @param id 文化馆主键
     * @return 文化馆对象
     * @throws Exception
     */
    public WhgSysRole t_srchOne(String id)throws Exception{
        WhgSysRole record = new WhgSysRole();
        record.setId(id);
        return this.whgSysRoleMapper.selectOne(record);
    }

    /**
     * 添加文化馆
     * @param cult
     * @return
     * @throws Exception
     */
    public WhgSysRole t_add(WhgSysRole cult, String[] pms, WhgSysUser user)throws Exception{
        //名称不能重复
        Example example = new Example(WhgSysRole.class);
        Example.Criteria c = example.createCriteria();
        c.andEqualTo("name", cult.getName());
        int count = this.whgSysRoleMapper.selectCountByExample(example);
        if(count > 0){
            throw new Exception("岗位名称重复");
        }

        //设置初始值
        Date now = new Date();
        String roleId = commService.getKey("whg_sys_role");
        cult.setId(roleId);
        cult.setState(EnumState.STATE_YES.getValue());
        cult.setCrtdate(now);
        cult.setCrtuser(user.getId());
        cult.setDelstate(EnumDelState.STATE_DEL_NO.getValue());
        cult.setStatemdfdate(now);
        cult.setStatemdfuser(user.getId());
        int rows = this.whgSysRoleMapper.insertSelective(cult);
        if(rows != 1){
            throw new Exception("添加岗位失败");
        }

        //添加角色权限
        if(pms != null){
            for(String p : pms){
                WhgSysRolePms rpms = new WhgSysRolePms();
                rpms.setId(this.commService.getKey("whg_sys_role_pms"));
                rpms.setState(EnumState.STATE_YES.getValue());
                rpms.setCrtdate(now);
                rpms.setCrtuser(user.getId());
                rpms.setDelstate(EnumDelState.STATE_DEL_NO.getValue());
                rpms.setStatemdfdate(now);
                rpms.setStatemdfuser(user.getId());
                rpms.setRoleid(roleId);
                rpms.setPmsstr(p);
                this.whgSysRolePmsMapper.insert(rpms);
            }
        }

        return cult;
    }

    /**
     * 查询角色权限
     * @param id 角色ID
     * @return 角色权限列表
     * @throws Exception
     */
    public List<String> t_srchRolePms(String id)throws Exception{
        Example example_pms = new Example(WhgSysRolePms.class);
        example_pms.createCriteria().andEqualTo("roleid", id);
        List<WhgSysRolePms> rpmsList = this.whgSysRolePmsMapper.selectByExample(example_pms);
        List<String> pmsList = new ArrayList<String>();
        if(rpmsList != null){
            for(WhgSysRolePms wsrp : rpmsList){
                pmsList.add(wsrp.getPmsstr());
            }
        }
        return pmsList;
    }

    /**
     * 编辑文化馆
     * @param cult
     * @return
     * @throws Exception
     */
    public WhgSysRole t_edit(WhgSysRole cult, String[] pms, WhgSysUser user)throws Exception{
        //名称不能重复
        Example example = new Example(WhgSysRole.class);
        Example.Criteria c = example.createCriteria();
        c.andEqualTo("name", cult.getName());
        c.andNotEqualTo("id", cult.getId());
        int count = this.whgSysRoleMapper.selectCountByExample(example);
        if(count > 0){
            throw new Exception("岗位名称重复");
        }

        //设置初始值
        Date now = new Date();
        cult.setStatemdfdate(now);
        cult.setStatemdfuser(user.getId());
        int rows = this.whgSysRoleMapper.updateByPrimaryKeySelective(cult);
        if(rows != 1){
            throw new Exception("编辑岗位失败");
        }

        //添加角色权限
        Example examplex = new Example(WhgSysRolePms.class);
        Example.Criteria c2 = examplex.createCriteria();
        c2.andEqualTo("roleid", cult.getId());
        this.whgSysRolePmsMapper.deleteByExample(examplex);
        if(pms != null){
            for(String p : pms){
                WhgSysRolePms rpms = new WhgSysRolePms();
                rpms.setId(this.commService.getKey("whg_sys_role_pms"));
                rpms.setState(EnumState.STATE_YES.getValue());
                rpms.setCrtdate(now);
                rpms.setCrtuser(user.getId());
                rpms.setDelstate(EnumDelState.STATE_DEL_NO.getValue());
                rpms.setStatemdfdate(now);
                rpms.setStatemdfuser(user.getId());
                rpms.setRoleid(cult.getId());
                rpms.setPmsstr(p);
                this.whgSysRolePmsMapper.insert(rpms);
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

            Example examplex = new Example(WhgSysRolePms.class);
            Example.Criteria c2 = examplex.createCriteria();
            c2.andIn("roleid", Arrays.asList(idArr));
            this.whgSysRolePmsMapper.deleteByExample(examplex);

            Example example = new Example(WhgSysRole.class);
            Example.Criteria c = example.createCriteria();
            c.andIn("id", Arrays.asList(idArr));
            this.whgSysRoleMapper.deleteByExample(example);

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
            Example example = new Example(WhgSysRole.class);
            Example.Criteria c = example.createCriteria();
            c.andIn("id", Arrays.asList(idArr));
            if(fromState != null){
                c.andEqualTo("state", fromState);
            }
            WhgSysRole record = new WhgSysRole();
            record.setState(Integer.parseInt(toState));
            record.setStatemdfdate(new Date());
            record.setStatemdfuser(user.getId());
            this.whgSysRoleMapper.updateByExampleSelective(record, example);
        }
    }
}
