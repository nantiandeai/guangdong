package com.creatoo.hn.services.admin.train;

import com.creatoo.hn.mapper.WhgComResourceMapper;
import com.creatoo.hn.model.WhgComResource;
import com.creatoo.hn.model.WhgYwiSystemp;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 资源管理service
 *
 * @author luzhihuai
 * @version 1-201703
 *          Created by Administrator on 2017/3/29.
 */
@Service
public class WhgTrainResourceService {
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
     * 短信模板DAO
     */
    @Autowired
    private WhgComResourceMapper whgComResourceMapper;

    /**
     * 分页查询分类列表信息
     *
     * @param request。
     */
    public PageInfo<WhgComResource> t_srchList4p(HttpServletRequest request, WhgComResource whgComResource) throws Exception {
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);
        //分页信息
        int page = Integer.parseInt((String) paramMap.get("page"));
        int rows = Integer.parseInt((String) paramMap.get("rows"));
        //开始分页
        PageHelper.startPage(page, rows);
        String id = request.getParameter("id");
        //搜索条件
        Example example = new Example(WhgComResource.class);
        Example.Criteria c = example.createCriteria();

        c.andEqualTo("refid",id);

        //名称条件
        if (whgComResource != null && whgComResource.getEntname() != null) {
            c.andLike("entname", "%" + whgComResource.getEntname() + "%");
            whgComResource.setEntname(null);
        }
        //名称条件
        if (whgComResource != null && whgComResource.getReftype() != null) {
            if (whgComResource.getReftype() != null && !"".equals(whgComResource.getReftype())) {
//                if ("train".equals(whgComResource.getReftype()))//培训
//                    c.andEqualTo("reftype",1);
//                if ("act".equals(whgComResource.getReftype()))//活动
//                    c.andEqualTo("reftype",2);
//                if ("ven".equals(whgComResource.getReftype()))//场馆
//                    c.andEqualTo("reftype",3);
//                if ("venroom".equals(whgComResource.getReftype()))//场馆活动室
//                    c.andEqualTo("reftype",4);
//                if ("minglu".equals(whgComResource.getReftype()))//名录
//                    c.andEqualTo("reftype",5);
//                if ("succ".equals(whgComResource.getReftype()))//传承人
//                    c.andEqualTo("reftype",6);
//                if ("7".equals(whgComResource.getReftype()))//志愿活动
//                    c.andEqualTo("reftype",7);
//                if ("8".equals(whgComResource.getReftype()))//优秀组织
//                    c.andEqualTo("reftype",8);
//                if ("9".equals(whgComResource.getReftype()))//项目示范
//                    c.andEqualTo("reftype",9);
                c.andEqualTo("reftype", whgComResource.getReftype());//1、培训 2、活动 3、场馆 4、场馆活动室 5、名录、6传承人，7志愿活动，8优秀组织，9项目示范）
            }
            whgComResource.setReftype(null);
        }

        //其它条件
        c.andEqualTo(whgComResource);

        example.setOrderByClause("crtdate desc");
        //分页查询
        PageHelper.startPage(page, rows);
        List<WhgComResource> typeList = this.whgComResourceMapper.selectByExample(example);
        return new PageInfo<>(typeList);
    }

    /**
     * 查询单条记录
     * @param id id
     * @return 对象
     * @throws Exception
     */
    public WhgComResource t_srchOne(String id)throws Exception{
        WhgComResource record = new WhgComResource();
        record.setEntid(id);
        return this.whgComResourceMapper.selectOne(record);
    }

    /**
     * 添加
     *
     * @param wcr
     */
    public void t_add(HttpServletRequest request,WhgComResource wcr) throws Exception {

        WhgComResource whgComResource = new WhgComResource();
        String id = request.getParameter("id");
        if (id != null && !"".equals(id)) {
            whgComResource.setRefid(id);
        }
        String reftype = request.getParameter("reftype");
        if (reftype != null && !"".equals(reftype)) {
            whgComResource.setReftype(reftype);//1、培训 2、活动 3、场馆 4、场馆活动室 5、名录、6传承人，7志愿活动，8优秀组织，9项目示范
        }

        String penturl = request.getParameter("penturl");
        if (penturl != null && !"".equals(penturl)) {
            whgComResource.setEnturl(penturl);
        }

        if (wcr.getEnttype() != null && !"".equals(wcr.getEnttype())) {
            whgComResource.setEnttype(wcr.getEnttype());
        }
        if (wcr.getEntname() != null && !"".equals(wcr.getEntname())) {
            whgComResource.setEntname(wcr.getEntname());
        }
        if (wcr.getEnturl() != null && !"".equals(wcr.getEnturl())) {
            whgComResource.setEnturl(wcr.getEnturl());
        }
        if (wcr.getEnttimes() != null && !"".equals(wcr.getEnttimes())) {
            whgComResource.setEnttimes(wcr.getEnttimes());
        }
        if (wcr.getDeourl() != null && !"".equals(wcr.getDeourl())) {
            whgComResource.setDeourl(wcr.getDeourl());
        }

        whgComResource.setEntid(commService.getKey("whg_com_resource"));
        whgComResource.setCrtdate(new Date());

        int result = this.whgComResourceMapper.insertSelective(whgComResource);
        if (result != 1) {
            throw new Exception("添加数据失败！");
        }
    }

    /**
     * 编辑
     *
     * @param
     */
    public void t_edit(Map<String, Object> paramMap,WhgComResource wcr) throws Exception {
        String penturl = (String) paramMap.get("penturl");
        if (penturl != null && !"".equals(penturl)) {
            wcr.setEnturl(penturl);
        }
        wcr.setRedate(new Date());
        int result = this.whgComResourceMapper.updateByPrimaryKeySelective(wcr);
        if (result != 1) {
            throw new Exception("添加数据失败！");
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
        String entid = (String) paramMap.get("entid");
        int result = this.whgComResourceMapper.deleteByPrimaryKey(entid);
        if (result != 1) {
            throw new Exception("删除数据失败！");
        }
    }

}
