package com.creatoo.hn.services.admin.system;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.ext.emun.EnumDelState;
import com.creatoo.hn.ext.emun.EnumState;
import com.creatoo.hn.mapper.WhgSysCultMapper;
import com.creatoo.hn.model.WhgSysCult;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * 场馆服务类
 * Created by wangxl on 2017/3/18.
 */
@Service
public class WhgSystemCultService {
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
    private WhgSysCultMapper whgSysCultMapper;

    /**
     * 分页查询文化馆信息
     * @param request 请求对象
     * @param cult 条件对象
     * @return 分页数据
     * @throws Exception
     */
    public PageInfo<WhgSysCult> t_srchList4p(HttpServletRequest request, WhgSysCult cult)throws Exception{
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);

        //分页信息
        int page = Integer.parseInt((String)paramMap.get("page"));
        int rows = Integer.parseInt((String)paramMap.get("rows"));

        //搜索条件
        Example example = new Example(WhgSysCult.class);
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
            example.setOrderByClause("idx");
        }

        //分页查询
        PageHelper.startPage(page, rows);
        List<WhgSysCult> list = this.whgSysCultMapper.selectByExample(example);
        return new PageInfo<WhgSysCult>(list);
    }

    /**
     * 查询文化馆列表
     * @param request 请求对象
     * @param cult 条件对象
     * @return 文化馆列表
     * @throws Exception
     */
    public List<WhgSysCult> t_srchList(HttpServletRequest request, WhgSysCult cult)throws Exception{
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);

        //搜索条件
        Example example = new Example(WhgSysCult.class);
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
        return this.whgSysCultMapper.selectByExample(example);
    }

    /**
     * 查询单个文化馆信息
     * @param id 文化馆主键
     * @return 文化馆对象
     * @throws Exception
     */
    public WhgSysCult t_srchOne(String id)throws Exception{
        WhgSysCult record = new WhgSysCult();
        record.setId(id);
        return this.whgSysCultMapper.selectOne(record);
    }

    /**
     * 添加文化馆
     * @param cult
     * @return
     * @throws Exception
     */
    public WhgSysCult t_add(WhgSysCult cult, WhgSysUser user)throws Exception{
        //名称不能重复
        Example example = new Example(WhgSysCult.class);
        Example.Criteria c = example.createCriteria();
        c.andEqualTo("name", cult.getName());
        int count = this.whgSysCultMapper.selectCountByExample(example);
        if(count > 0){
            throw new Exception("文化馆名称重复");
        }

        //获得idx
        Example ex1 = new Example(WhgSysCult.class);
        ex1.createCriteria().andIsNotNull("id");
        int idx = this.whgSysCultMapper.selectCountByExample(ex1);
        idx++;

        //设置初始值
        Date now = new Date();
        cult.setId(commService.getKey("whg_sys_cult"));
        cult.setState(EnumState.STATE_YES.getValue());
        cult.setCrtdate(now);
        cult.setCrtuser(user.getId());
        cult.setDelstate(EnumDelState.STATE_DEL_NO.getValue());
        cult.setStatemdfdate(now);
        cult.setStatemdfuser(user.getId());
        cult.setIdx(idx);
        int rows = this.whgSysCultMapper.insertSelective(cult);
        if(rows != 1){
            throw new Exception("添加文化馆资料失败");
        }

        return cult;
    }

    /**
     * 编辑文化馆
     * @param cult
     * @return
     * @throws Exception
     */
    public WhgSysCult t_edit(WhgSysCult cult, WhgSysUser user)throws Exception{
        //名称不能重复
        Example example = new Example(WhgSysCult.class);
        Example.Criteria c = example.createCriteria();
        c.andEqualTo("name", cult.getName());
        c.andNotEqualTo("id", cult.getId());
        int count = this.whgSysCultMapper.selectCountByExample(example);
        if(count > 0){
            throw new Exception("文化馆名称重复");
        }

        //设置初始值
        Date now = new Date();
        cult.setStatemdfdate(now);
        cult.setStatemdfuser(user.getId());
        int rows = this.whgSysCultMapper.updateByPrimaryKeySelective(cult);
        if(rows != 1){
            throw new Exception("编辑文化馆资料失败");
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
            Example example = new Example(WhgSysCult.class);
            Example.Criteria c = example.createCriteria();
            c.andIn("id", Arrays.asList(idArr));
            this.whgSysCultMapper.deleteByExample(example);
        }
    }

    /**
     * 排序
     * @param id 主键
     * @param type up|top|idx
     * @param val 当type=idx时，表示直接设置排序值
     * @param user
     * @throws Exception
     */
    public void t_sort(String id, String type, String val, WhgSysUser user)throws Exception{
        //初始所有记录的idx值
        WhgSysCult whgSysCult = this.whgSysCultMapper.selectByPrimaryKey(id);
        Integer idx = whgSysCult.getIdx();
        if(idx == null){
            Example example = new Example(WhgSysCult.class);
            example.setOrderByClause("crtdate desc");
            List<WhgSysCult> allList = this.whgSysCultMapper.selectByExample(example);
            if(allList != null && allList.size() > 0){
                for(int i=0; i<allList.size(); i++){
                    WhgSysCult _cult = allList.get(i);
                    _cult.setIdx(i+1);
                    this.whgSysCultMapper.updateByPrimaryKeySelective(_cult);
                }
            }
        }

        //up
        if("up".equals(type)){
            //当前记录
            WhgSysCult curCult = this.whgSysCultMapper.selectByPrimaryKey(id);
            int curIdx = curCult.getIdx();

            //前一条记录
            Example example2 = new Example(WhgSysCult.class);
            example2.createCriteria().andLessThan("idx", curCult.getIdx());
            example2.setOrderByClause("idx desc");
            PageHelper.startPage(1,1);
            List<WhgSysCult> lessList = this.whgSysCultMapper.selectByExample(example2);
            PageInfo<WhgSysCult> pi = new PageInfo<WhgSysCult>(lessList);
            if(lessList != null && lessList.size() == 1){
                WhgSysCult preCult = lessList.get(0);
                int preIdx = preCult.getIdx();

                curCult.setIdx(preIdx);
                this.whgSysCultMapper.updateByPrimaryKeySelective(curCult);

                preCult.setIdx(curIdx);
                this.whgSysCultMapper.updateByPrimaryKeySelective(preCult);
            }
        }

        //top
        else if("top".equals(type)){
            //当前记录
            WhgSysCult curCult = this.whgSysCultMapper.selectByPrimaryKey(id);
            int curIdx = curCult.getIdx();

            Example example = new Example(WhgSysCult.class);
            example.createCriteria().andLessThan("idx", curIdx);
            example.setOrderByClause("idx");
            List<WhgSysCult> allList = this.whgSysCultMapper.selectByExample(example);
            if(allList != null && allList.size() > 0){
                List<WhgSysCult> newList = new ArrayList<WhgSysCult>();
                for(WhgSysCult c : allList){
                    if(c != null && !curCult.getId().equals(c.getId())){
                        newList.add(c);
                    }
                }

                curCult.setIdx(1);
                this.whgSysCultMapper.updateByPrimaryKey(curCult);

                for(int i=0; i<newList.size(); i++){
                    WhgSysCult _cult = newList.get(i);
                    _cult.setIdx(i+2);
                    this.whgSysCultMapper.updateByPrimaryKeySelective(_cult);
                }
            }
        }

        //idx 直接设置idx值
        else if("idx".equals(type)){
            //当前记录
            WhgSysCult curCult = this.whgSysCultMapper.selectByPrimaryKey(id);
            curCult.setIdx(Integer.parseInt(val));
            this.whgSysCultMapper.updateByPrimaryKey(curCult);
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
            Example example = new Example(WhgSysCult.class);
            Example.Criteria c = example.createCriteria();
            c.andIn("id", Arrays.asList(idArr));
            if(fromState != null){
                c.andEqualTo("state", fromState);
            }
            WhgSysCult record = new WhgSysCult();
            record.setState(Integer.parseInt(toState));
            Date now = new Date();
            record.setStatemdfdate(now);
            record.setStatemdfuser(user.getId());
            this.whgSysCultMapper.updateByExampleSelective(record, example);
        }
    }
}
