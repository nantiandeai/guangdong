package com.creatoo.hn.services.admin.train;

import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.mapper.WhUserTeacherMapper;
import com.creatoo.hn.model.WhUserTeacher;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.model.WhgTra;
import com.creatoo.hn.services.comm.CommService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 培训师资管理Service
 * @author wenjingqiang
 * @version 1-201703
 * Created by Administrator on 2017/4/20.
 */
@Service
public class WhgTrainTeacherService {
    /**
     * 培训师资mapper
     */
    @Autowired
    private WhUserTeacherMapper teacherMapper;
    /**
     * CommService
     */
    @Autowired
    private CommService commService;

    /**
     * 查询培训师资
     * @param param
     * @return
     */
    public PageInfo t_srchList4p(Map<String, Object> param) throws Exception {
        //分页信息
        int page = Integer.parseInt((String)param.get("page"));
        int rows = Integer.parseInt((String)param.get("rows"));

        //带条件的分页查询
        PageHelper.startPage(page, rows);
        List<Map> list = this.teacherMapper.selTeacher(param);
        // 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);

        return pageInfo;
    }

    /**
     * 添加培训师资
     * @param tea
     * @param user
     * @param request
     */
    public void t_add(WhUserTeacher tea, WhgSysUser user, HttpServletRequest request) throws Exception {
        Date now = new Date();
        tea.setTeacheropttime(now);
        tea.setTeacherid(this.commService.getKey("wh_user_teacher"));
        tea.setTeacherstate(1);
        this.teacherMapper.insertSelective(tea);
    }

    /**
     * 编辑培训师资
     * @param tea
     */
    public void t_edit(WhUserTeacher tea) throws Exception {
        this.teacherMapper.updateByPrimaryKeySelective(tea);
    }

    /**
     * 删除培训师资
     * @param teacherid
     */
    public void t_del(String teacherid) throws Exception {
        WhUserTeacher tea = teacherMapper.selectByPrimaryKey(teacherid);
        if (tea == null){
            return;
        }
        this.teacherMapper.deleteByPrimaryKey(teacherid);
    }

    /**
     * 修改状态
     * @param ids
     * @param formstates
     * @param tostate
     * @return
     */
    public ResponseBean t_updstate(String ids, String formstates, int tostate) {
        ResponseBean rb = new ResponseBean();
        if (ids == null){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("培训师资主键丢失");
            return rb;
        }
        Example example = new Example(WhUserTeacher.class);
        example.createCriteria()
                .andIn("teacherstate", Arrays.asList( formstates.split("\\s*,\\s*") ))
                .andIn("teacherid", Arrays.asList( ids.split("\\s*,\\s*") ) );
        WhUserTeacher tea = new WhUserTeacher();
        tea.setTeacherstate(tostate);
        tea.setTeacheropttime(new Date());
        this.teacherMapper.updateByExampleSelective(tea, example);
        return rb;
    }

    /**
     * 根据ID查找
     * @param teacherid
     * @return
     */
    public Object srchOne(String teacherid) {
        return teacherMapper.selectByPrimaryKey(teacherid);
    }
}
