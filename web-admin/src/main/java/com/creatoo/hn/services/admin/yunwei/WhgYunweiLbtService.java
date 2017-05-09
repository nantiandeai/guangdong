package com.creatoo.hn.services.admin.yunwei;

import com.creatoo.hn.ext.emun.EnumState;
import com.creatoo.hn.mapper.WhgYwiLbtMapper;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.model.WhgYwiLbt;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统管理的轮播图配置service
 *
 * @author luzhihuai
 * @version 1-201703
 *          Created by Administrator on 2017/3/20.
 */
@Service
public class WhgYunweiLbtService {
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
    private WhgYwiLbtMapper whgYwiLbtMapper;

    /**
     * 分页查询分类列表信息
     *
     * @param request。
     */
    public PageInfo<WhgYwiLbt> t_srchList4p(HttpServletRequest request) throws Exception {
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);
        //分页信息
        int page = Integer.parseInt((String) paramMap.get("page"));
        int rows = Integer.parseInt((String) paramMap.get("rows"));
        //开始分页
        PageHelper.startPage(page, rows);

        //搜索条件
        Example example = new Example(WhgYwiLbt.class);
        Example.Criteria c = example.createCriteria();

        String name = (String) paramMap.get("name");
        if (name != null && !"".equals(name)) {
            c.andLike("name", "%" + name + "%");
        }
        c.andEqualTo("state", 1);
        c.andEqualTo("delstate", 0);
        example.setOrderByClause("crtdate");
        List<WhgYwiLbt> typeList = this.whgYwiLbtMapper.selectByExample(example);
        return new PageInfo<>(typeList);
    }

    /**
     * 列表查询
     *
     * @return
     * @throws Exception
     */
    public List<WhgYwiLbt> t_srchList(WhgYwiLbt whgYwiLbt) throws Exception {
        //搜索条件
        Example example = new Example(WhgYwiLbt.class);
        Example.Criteria c = example.createCriteria();
        c.andEqualTo(whgYwiLbt);
        example.setOrderByClause("crtdate desc");
        return this.whgYwiLbtMapper.selectByExample(example);
    }

    /**
     * 查询单条记录
     * @param id id
     * @return 对象
     * @throws Exception
     */
    public WhgYwiLbt t_srchOne(String id)throws Exception{
        WhgYwiLbt record = new WhgYwiLbt();
        record.setId(id);
        return this.whgYwiLbtMapper.selectOne(record);
    }

    /**
     * 添加
     *
     * @param
     */
    public void t_add(HttpServletRequest request, WhgYwiLbt wyl) throws Exception {
        WhgSysUser user = (WhgSysUser) request.getSession().getAttribute("user");
        WhgYwiLbt whgYwiLbt = new WhgYwiLbt();
        if (wyl.getName() != null && !"".equals(wyl.getName())) {
            whgYwiLbt.setName(wyl.getName());
        }
        if (wyl.getPicture() != null && !"".equals(wyl.getPicture())) {
            whgYwiLbt.setPicture(wyl.getPicture());
        }
        if (wyl.getUrl() != null && !"".equals(wyl.getUrl())) {
            whgYwiLbt.setUrl(wyl.getUrl());
        }
        if (wyl.getType() != null && !"".equals(wyl.getType())) {
            whgYwiLbt.setType(wyl.getType());
        }

        whgYwiLbt.setId(commService.getKey("whg_ywi_ltb"));
        whgYwiLbt.setState(EnumState.STATE_YES.getValue());
        whgYwiLbt.setCrtdate(new Date());
        whgYwiLbt.setCrtuser(user.getId());
        whgYwiLbt.setStatemdfuser(user.getId());

        int a = this.whgYwiLbtMapper.count(wyl);
        if (a<10){
            int result = this.whgYwiLbtMapper.insertSelective(whgYwiLbt);
            if (result != 1) {
                throw new Exception("添加数据失败！");
            }
        }else {
            throw new Exception("添加图片不能大于十张！");
        }

    }

    /**
     * 编辑
     *
     * @param
     */
    public void t_edit(Map<String, Object> paramMap, HttpServletRequest request, WhgYwiLbt wyl) throws Exception {
        WhgSysUser user = (WhgSysUser) request.getSession().getAttribute("user");
        String id = (String) paramMap.get("id");
        WhgYwiLbt whgYwiLbt = this.whgYwiLbtMapper.selectByPrimaryKey(id);

//        Example example = new Example(WhgYwiLbt.class);
//        Example.Criteria c = example.createCriteria();
//        c.andNotEqualTo("id", wyl.getId());

//        whgYwiLbt=this.whgYwiLbtMapper.selectCountByExample(example);

        if (whgYwiLbt != null) {
            whgYwiLbt.setName(wyl.getName());
            whgYwiLbt.setPicture(wyl.getPicture());
            whgYwiLbt.setUrl(wyl.getUrl());

            whgYwiLbt.setState(EnumState.STATE_YES.getValue());
            whgYwiLbt.setStatemdfuser(user.getId());
        }
        int result = this.whgYwiLbtMapper.updateByPrimaryKeySelective(whgYwiLbt);
        if (result != 1) {
            throw new Exception("添加数据失败！");
        }
    }

    /**
     * 更新状态
     *
     * @param paramMap
     * @throws Exception
     */
    public void t_updstate(Map<String, Object> paramMap, int state) throws Exception {
        String id = (String) paramMap.get("id");
        WhgYwiLbt whgYwiLbt = this.whgYwiLbtMapper.selectByPrimaryKey(id);
        whgYwiLbt.setState(state);
        int result = this.whgYwiLbtMapper.updateByPrimaryKey(whgYwiLbt);
        if (result != 1) {
            throw new Exception("编辑数据失败！");
        }
    }

    /**
     * 删除
     *
     * @param request
     * @throws Exception
     */
    public void t_del(HttpServletRequest request) throws Exception {
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);
        String id = (String) paramMap.get("id");
        int result = this.whgYwiLbtMapper.deleteByPrimaryKey(id);
        if (result != 1) {
            throw new Exception("删除数据失败！");
        }
    }


}
