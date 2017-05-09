package com.creatoo.hn.services.admin.yunwei;

import com.creatoo.hn.mapper.WhgYwiKeyMapper;
import com.creatoo.hn.model.WhgYwiKey;
import com.creatoo.hn.model.WhgYwiTag;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/17.
 */
@Service
public class WhgYunweiKeyService {
    /**
     * 关键字mapper
     */
    @Autowired
    private WhgYwiKeyMapper whgYwiKeyMapper;

    /**
     * CommService
     */
    @Autowired
    private CommService commService;

    /**
     * 分页查询分类列表信息
     * @param request
     */
    public PageInfo<WhgYwiKey> t_srchList4p(HttpServletRequest request) throws Exception {
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);
        int page = Integer.parseInt((String) paramMap.get("page"));
        int rows = Integer.parseInt((String) paramMap.get("rows"));
        //开始分页
        PageHelper.startPage(page, rows);

        String type = request.getParameter("type");
        Example example = new Example(WhgYwiTag.class);
        Example.Criteria c = example.createCriteria();

        if (!"".equals(type) && type != null) {
            c.andEqualTo("type", type);
        }

        String name = (String)paramMap.get("name");
        if(name != null && !"".equals(name)){
            c.andLike("name","%"+name+"%");
        }
        c.andEqualTo("state",1);
        c.andEqualTo("delstate",0);
        example.setOrderByClause("idx asc");
        List<WhgYwiKey> typeList = this.whgYwiKeyMapper.selectByExample(example);
        return new PageInfo<>(typeList);
    }

    /**
     * 添加分类
     * @param
     */
    public void t_add(HttpServletRequest request, HttpSession session)throws Exception {
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);
        WhgYwiKey ywKey = new WhgYwiKey();
        String type = (String)paramMap.get("type");
        if(type != null && !"".equals(type)){
            ywKey.setType(type);
        }
        if(paramMap.get("idx") != null && !"".equals(paramMap.get("idx"))){
            int idx = Integer.parseInt((String)paramMap.get("idx"));
            ywKey.setIdx(idx);

        }
        String name = (String)paramMap.get("name");
        if(name != null && !"".equals(name)){
            ywKey.setName(name);
        }
        ywKey.setId(commService.getKey("whg_ywi_key"));
        ywKey.setState(1);
        ywKey.setCrtdate(new Date());
        ywKey.setCrtuser(session.getId());
        int result = this.whgYwiKeyMapper.insertSelective(ywKey);
        if(result != 1){
            throw new Exception("添加数据失败！");
        }

    }

    /**
     * 编辑分类
     * @param paramMap
     */
    public void t_edit(Map<String, Object> paramMap)throws Exception {
        String id = (String)paramMap.get("id");
        WhgYwiKey ywKey = this.whgYwiKeyMapper.selectByPrimaryKey(id);
        String name = (String)paramMap.get("name");
        if(name != null && !"".equals(name)){
            ywKey.setName(name);
        }
        if(paramMap.get("idx") != null && !"".equals(paramMap.get("idx"))){
            int idx = Integer.parseInt((String)paramMap.get("idx"));
            ywKey.setIdx(idx);

        }
        int result = this.whgYwiKeyMapper.updateByPrimaryKey(ywKey);
        if(result != 1){
            throw new Exception("编辑数据失败！");
        }
    }

    /**
     * 删除分类信息
     * @param request
     * @throws Exception
     */
    public void t_del(HttpServletRequest request) throws Exception {
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);
        String id = (String)paramMap.get("id");
        int result = this.whgYwiKeyMapper.deleteByPrimaryKey(id);
        if(result != 1){
            throw new Exception("删除数据失败！");
        }
    }

    /**
     * 设置排序值
     * @param paramMap
     * @throws Exception
     */
    public void t_sort(Map<String, Object> paramMap) throws Exception {
        String id = (String)paramMap.get("id");
        WhgYwiKey ywKey = this.whgYwiKeyMapper.selectByPrimaryKey(id);
        int idx = Integer.parseInt((String)paramMap.get("idx"));
        if(!"".equals(idx)){
            ywKey.setIdx(idx);
        }
        int result = this.whgYwiKeyMapper.updateByPrimaryKey(ywKey);
        if(result != 1){
            throw new Exception("设置排序值失败！");
        }
    }
}
