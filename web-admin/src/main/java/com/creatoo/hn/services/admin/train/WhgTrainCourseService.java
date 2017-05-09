package com.creatoo.hn.services.admin.train;

import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.mapper.WhgTraCourseMapper;
import com.creatoo.hn.mapper.WhgTraEnrolMapper;
import com.creatoo.hn.mapper.WhgTraMapper;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.model.WhgTra;
import com.creatoo.hn.model.WhgTraCourse;
import com.creatoo.hn.model.WhgTraEnrol;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 培训课程管理action
 * @author wenjingqiang
 * @version 1-201703
 * Created by Administrator on 2017/3/21.
 */
@Service
public class WhgTrainCourseService {

    /**
     * 培训课程mapper
     */
    @Autowired
    private WhgTraCourseMapper whgTraCourseMapper;

    @Autowired
    private WhgTraMapper whgTraMapper;

    @Autowired
    private WhgTraEnrolMapper whgTraEnrolMapper;

    /**
     * 分页查询课程
     * @param request
     * @return
     */
    public PageInfo t_srchList4p(HttpServletRequest request) throws Exception {
        int page = Integer.parseInt((String)request.getParameter("page"));
        int rows = Integer.parseInt((String)request.getParameter("rows"));
        String id = request.getParameter("id");
        Example example = new Example(WhgTraCourse.class);
        Example.Criteria c = example.createCriteria();
        c.andEqualTo("traid",id);
        c.andEqualTo("delstate",0);
        example.setOrderByClause("starttime asc");
        PageHelper.startPage(page,rows);
        List<WhgTraCourse> list = this.whgTraCourseMapper.selectByExample(example);
        PageInfo info = new PageInfo(list);
        return info;
    }

    /**
     * 编辑课程
     * @param course
     * @param sysUser
     */
    public void t_edit(WhgTraCourse course, WhgSysUser sysUser) throws Exception {

        this.whgTraCourseMapper.updateByPrimaryKeySelective(course);
    }


    /**
     * 删除
     * @param id
     * @param user
     */
    public void t_del(String id, WhgSysUser user) throws Exception {
        WhgTraCourse course = whgTraCourseMapper.selectByPrimaryKey(id);
        if(course == null){
            return;
        }
        this.whgTraCourseMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新状态
     * @param ids
     * @param formstates
     * @param tostate
     * @param sysUser
     * @return
     */
    public ResponseBean t_updstate(String ids, String formstates, int tostate, WhgSysUser sysUser) {
        ResponseBean rb = new ResponseBean();
        if (ids == null){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("课程标识丢失");
            return rb;
        }
        Example example = new Example(WhgTraCourse.class);
        example.createCriteria()
                .andIn("state", Arrays.asList( formstates.split("\\s*,\\s*") ))
                .andIn("id", Arrays.asList( ids.split("\\s*,\\s*") ) );
        WhgTraCourse course = new WhgTraCourse();
        course.setState(tostate);
        course.setStatemdfdate(new Date());
        course.setStatemdfuser(sysUser.getId());
        this.whgTraCourseMapper.updateByExampleSelective(course, example);
        return rb;
    }

    /**
     * 根据培训ID查找课程
     * @param id
     * @return
     */
    public List<WhgTraCourse> srchList(String id) throws Exception {
        WhgTra tra = this.whgTraMapper.selectByPrimaryKey(id);

        Example example = new Example(WhgTraCourse.class);
        Example.Criteria c = example.createCriteria();
        c.andEqualTo("traid",id);
        c.andEqualTo("state",1);
        example.setOrderByClause("starttime asc");
        if(tra.getIsmultisite() != 1){
            PageHelper.startPage(1,1);
        }
        return this.whgTraCourseMapper.selectByExample(example);
    }

    /**
     * 查看是否存在报名记录
     * @param traid 培训ID
     * @return
     */
    public int selCountEnroll(String traid) {
        Example example = new Example(WhgTraEnrol.class);
        example.createCriteria().andEqualTo("traid", traid);
        return this.whgTraEnrolMapper.selectCountByExample(example);
    }
}
