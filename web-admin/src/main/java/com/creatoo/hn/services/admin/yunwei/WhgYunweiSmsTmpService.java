package com.creatoo.hn.services.admin.yunwei;

import com.creatoo.hn.mapper.WhgYwiSmstmpMapper;
import com.creatoo.hn.model.WhgYwiSmstmp;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 短信模板管理service
 *
 * @author luzhihuai
 * @version 1-201703
 *          Created by Administrator on 2017/3/28.
 */
@Service
public class WhgYunweiSmsTmpService {
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
    private WhgYwiSmstmpMapper whgYwiSmstmpMapper;

    /**
     * 分页查询分类列表信息
     *
     * @param request。
     */
    public PageInfo<WhgYwiSmstmp> t_srchList4p(HttpServletRequest request, WhgYwiSmstmp whgYwiSmstmp) throws Exception {
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);
        //分页信息
        int page = Integer.parseInt((String) paramMap.get("page"));
        int rows = Integer.parseInt((String) paramMap.get("rows"));
        //开始分页
        PageHelper.startPage(page, rows);

        //搜索条件
        Example example = new Example(WhgYwiSmstmp.class);
        Example.Criteria c = example.createCriteria();

        //名称条件
        if (whgYwiSmstmp != null && whgYwiSmstmp.getCode() != null) {
            c.andLike("code", "%" + whgYwiSmstmp.getCode() + "%");
            whgYwiSmstmp.setCode(null);
        }

        //其它条件
//        c.andEqualTo(whgYwiSystemp);


        //分页查询
        PageHelper.startPage(page, rows);
        List<WhgYwiSmstmp> typeList = this.whgYwiSmstmpMapper.selectByExample(example);
        return new PageInfo<>(typeList);
    }

    /**
     * 查询单条记录
     * @param id id
     * @return 对象
     * @throws Exception
     */
    public WhgYwiSmstmp t_srchOne(String id)throws Exception{
        WhgYwiSmstmp record = new WhgYwiSmstmp();
        record.setId(id);
        return this.whgYwiSmstmpMapper.selectOne(record);
    }

    /**
     * 添加
     *
     * @param
     */
    public void t_add(WhgYwiSmstmp wyl) throws Exception {
        WhgYwiSmstmp whgYwiSmstmp = new WhgYwiSmstmp();

        if (wyl.getCode() != null && !"".equals(wyl.getCode())) {
            whgYwiSmstmp.setCode(wyl.getCode());
        }
        if (wyl.getContent() != null && !"".equals(wyl.getContent())) {
            whgYwiSmstmp.setContent(wyl.getContent());
        }

        whgYwiSmstmp.setId(commService.getKey("whg_ywi_systemp"));
//        whgYwiSystemp.setCrtdate(new Date());

        int result = this.whgYwiSmstmpMapper.insertSelective(whgYwiSmstmp);
        if (result != 1) {
            throw new Exception("添加数据失败！");
        }

    }

    /**
     * 编辑
     *
     * @param
     */
    public void t_edit(Map<String, Object> paramMap,WhgYwiSmstmp wyl) throws Exception {
        String id = (String) paramMap.get("id");
        WhgYwiSmstmp whgYwiSmstmp = this.whgYwiSmstmpMapper.selectByPrimaryKey(id);

        if (whgYwiSmstmp != null) {
            if (wyl.getCode() != null && !"".equals(wyl.getCode())) {
                whgYwiSmstmp.setCode(wyl.getCode());
            }
            if (wyl.getContent() != null && !"".equals(wyl.getContent())) {
                whgYwiSmstmp.setContent(wyl.getContent());
            }
        }
        int result = this.whgYwiSmstmpMapper.updateByPrimaryKeySelective(whgYwiSmstmp);
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
        String id = (String) paramMap.get("id");
        int result = this.whgYwiSmstmpMapper.deleteByPrimaryKey(id);
        if (result != 1) {
            throw new Exception("删除数据失败！");
        }
    }


}
