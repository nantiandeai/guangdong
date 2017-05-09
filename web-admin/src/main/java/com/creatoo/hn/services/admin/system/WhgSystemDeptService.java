package com.creatoo.hn.services.admin.system;

import com.creatoo.hn.ext.emun.EnumDelState;
import com.creatoo.hn.ext.emun.EnumState;
import com.creatoo.hn.mapper.WhgSysDeptMapper;
import com.creatoo.hn.model.WhgSysDept;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 部门服务类
 * Created by wangxl on 2017/3/18.
 */
@Service
public class WhgSystemDeptService {
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
    private WhgSysDeptMapper whgSysDeptMapper;

    /**
     * 分页查询文化馆信息
     * @param request 请求对象
     * @param dept 条件对象
     * @return 分页数据
     * @throws Exception
     */
    public PageInfo<WhgSysDept> t_srchList4p(HttpServletRequest request, WhgSysDept dept)throws Exception{
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);

        //分页信息
        int page = Integer.parseInt((String)paramMap.get("page"));
        int rows = Integer.parseInt((String)paramMap.get("rows"));

        //搜索条件
        Example example = new Example(WhgSysDept.class);
        Example.Criteria c = example.createCriteria();

        //名称条件
        if(dept != null && dept.getName() != null){
            c.andLike("name", "%"+dept.getName()+"%");
            dept.setName(null);
        }

        //其它条件
        c.andEqualTo(dept);

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
        List<WhgSysDept> list = this.whgSysDeptMapper.selectByExample(example);
        return new PageInfo<WhgSysDept>(list);
    }

    /**
     * 查询文化馆列表
     * @param request 请求对象
     * @param dept 条件对象
     * @return 文化馆列表
     * @throws Exception
     */
    public List<WhgSysDept> t_srchList(HttpServletRequest request, WhgSysDept dept)throws Exception{
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);

        //搜索条件
        Example example = new Example(WhgSysDept.class);
        Example.Criteria c = example.createCriteria();

        //名称条件
        if(dept != null && dept.getName() != null){
            c.andLike("name", "%"+dept.getName()+"%");
            dept.setName(null);
        }

        //其它条件
        c.andEqualTo(dept);

        //排序
        if(paramMap.containsKey("sort") && paramMap.get("sort")!= null){
            StringBuffer sb = new StringBuffer((String)paramMap.get("sort"));
            if(paramMap.containsKey("order") && paramMap.get("order") !=  null){
                sb.append(" ").append(paramMap.get("order"));
            }
            example.setOrderByClause(sb.toString());
        }else{
            example.setOrderByClause("crtdate");
        }

        //分页查询
        return this.whgSysDeptMapper.selectByExample(example);
    }

    /**
     * 查询单个文化馆信息
     * @param id 文化馆主键
     * @return 文化馆对象
     * @throws Exception
     */
    public WhgSysDept t_srchOne(String id)throws Exception{
        WhgSysDept record = new WhgSysDept();
        record.setId(id);
        return this.whgSysDeptMapper.selectOne(record);
    }

    /**
     * 添加文化馆
     * @param dept
     * @return
     * @throws Exception
     */
    public WhgSysDept t_add(WhgSysDept dept, WhgSysUser user)throws Exception{
        //名称不能重复
        Example example = new Example(WhgSysDept.class);
        Example.Criteria c = example.createCriteria();
        c.andEqualTo("name", dept.getName());
        int count = this.whgSysDeptMapper.selectCountByExample(example);
        if(count > 0){
            throw new Exception("部门名称重复");
        }

        //设置初始值
        Date now = new Date();
        dept.setId(commService.getKey("whg_sys_dept"));
        dept.setState(EnumState.STATE_YES.getValue());
        dept.setCrtdate(now);
        dept.setCrtuser(user.getId());
        dept.setDelstate(EnumDelState.STATE_DEL_NO.getValue());
        dept.setStatemdfdate(now);
        dept.setStatemdfuser(user.getId());

        //设置CODE
        String prefixCode = "";
        Example example2 = new Example(WhgSysDept.class);
        if(dept.getPid() != null && !dept.getPid().isEmpty()){
            WhgSysDept parent = this.whgSysDeptMapper.selectByPrimaryKey(dept.getPid());
            prefixCode = parent.getCode()+"_";
            example2.createCriteria().andEqualTo("pid", dept.getPid());
        }else{
            prefixCode = "dpt";
            example2.or().andEqualTo("pid", "");
            example2.or().andIsNull("pid");
        }
        int counts = this.whgSysDeptMapper.selectCountByExample(example2);
        dept.setCode(prefixCode+""+(counts+1));

        int rows = this.whgSysDeptMapper.insertSelective(dept);
        if(rows != 1){
            throw new Exception("添加部门资料失败");
        }

        return dept;
    }

    /**
     * 编辑文化馆
     * @param dept
     * @return
     * @throws Exception
     */
    public WhgSysDept t_edit(WhgSysDept dept, WhgSysUser user)throws Exception{
        //名称不能重复
        Example example = new Example(WhgSysDept.class);
        Example.Criteria c = example.createCriteria();
        c.andEqualTo("name", dept.getName());
        c.andNotEqualTo("id", dept.getId());
        int count = this.whgSysDeptMapper.selectCountByExample(example);
        if(count > 0){
            throw new Exception("部门名称重复");
        }

        //设置初始值
        Date now = new Date();
        dept.setStatemdfdate(now);
        dept.setStatemdfuser(user.getId());
        int rows = this.whgSysDeptMapper.updateByPrimaryKeySelective(dept);
        if(rows != 1){
            throw new Exception("编辑部门资料失败");
        }

        return dept;
    }

    /**
     * 删除文化馆
     * @param ids 文化馆ID
     * @throws Exception
     */
    public void t_del(String ids, WhgSysUser user)throws Exception{
        if(ids != null){
            String[] idArr = ids.split(",");
            Example example = new Example(WhgSysDept.class);
            Example.Criteria c = example.createCriteria();
            c.andIn("id", Arrays.asList(idArr));
            this.whgSysDeptMapper.deleteByExample(example);
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
            Example example = new Example(WhgSysDept.class);
            Example.Criteria c = example.createCriteria();
            c.andIn("id", Arrays.asList(idArr));
            if(fromState != null){
                c.andEqualTo("state", fromState);
            }
            WhgSysDept record = new WhgSysDept();
            record.setState(Integer.parseInt(toState));
            record.setStatemdfdate(new Date());
            record.setStatemdfuser(user.getId());
            this.whgSysDeptMapper.updateByExampleSelective(record, example);
        }
    }
}
