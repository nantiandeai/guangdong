package com.creatoo.hn.services.admin.yunwei;

import com.creatoo.hn.mapper.WhgYwiTagMapper;
import com.creatoo.hn.model.WhgYwiTag;
import com.creatoo.hn.model.WhgYwiType;
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
 * 系统运营的标签管理action
 * @author wenjingqiang
 * @version 1-201703
 * Created by Administrator on 2017/3/17.
 */
@Service
public class WhgYunweiTagService {
    /**
     * mapper
     */
    @Autowired
    private WhgYwiTagMapper whgYwiTagMapper;

    /**
     * CommService
     */
    @Autowired
    private CommService commService;

    /**
     * 分页查询标签列表信息
     * @param request
     */
    public PageInfo<WhgYwiTag> t_srchList4p(HttpServletRequest request) throws Exception {
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
        List<WhgYwiTag> typeList = this.whgYwiTagMapper.selectByExample(example);
        return new PageInfo<>(typeList);
    }

    /**
     * 添加标签
     * @param
     */
    public void t_add(HttpServletRequest request, HttpSession session)throws Exception {
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);
        WhgYwiTag ywiTag = new WhgYwiTag();
        String type = (String)paramMap.get("type");
        if(type != null && !"".equals(type)){
            ywiTag.setType(type);
        }
        String name = (String)paramMap.get("name");
        if(name != null && !"".equals(name)){
            ywiTag.setName(name);
        }
        if(paramMap.get("idx") != null && !"".equals(paramMap.get("idx"))){
            int idx = Integer.parseInt((String)paramMap.get("idx"));
            ywiTag.setIdx(idx);

        }
        ywiTag.setId(commService.getKey("whg_ywi_tag"));
        ywiTag.setState(1);
        ywiTag.setCrtdate(new Date());
        ywiTag.setCrtuser(session.getId());
        int result = this.whgYwiTagMapper.insertSelective(ywiTag);
        if(result != 1){
            throw new Exception("添加数据失败！");
        }

    }

    /**
     * 编辑标签
     * @param paramMap
     */
    public void t_edit(Map<String, Object> paramMap)throws Exception {
        String id = (String)paramMap.get("id");
        WhgYwiTag ywTag = this.whgYwiTagMapper.selectByPrimaryKey(id);
        String name = (String)paramMap.get("name");
        if(name != null && !"".equals(name)){
            ywTag.setName(name);
        }
        if(paramMap.get("idx") != null && !"".equals(paramMap.get("idx"))){
            int idx = Integer.parseInt((String)paramMap.get("idx"));
            ywTag.setIdx(idx);

        }
        int result = this.whgYwiTagMapper.updateByPrimaryKey(ywTag);
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
        int result = this.whgYwiTagMapper.deleteByPrimaryKey(id);
        if(result != 1){
            throw new Exception("删除数据失败！");
        }
    }
}
