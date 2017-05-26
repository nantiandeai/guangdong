package com.creatoo.hn.services.admin.train;

import com.creatoo.hn.mapper.WhgTraMapper;
import com.creatoo.hn.model.WhgTra;
import com.creatoo.hn.utils.WeekDayUtil;
import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.mapper.WhgTraCourseMapper;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.model.WhgTraCourse;
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
 * Created by Administrator on 2017/3/21.
 */
@Service
public class WhgTrainService {
    /**
     * 培训mapper
     */
    @Autowired
    private WhgTraMapper whgTraMapper;

    /**
     * 课程mapper
     */
    @Autowired
    private WhgTraCourseMapper whgTraCourseMapper;

    /**
     * CommService
     */
    @Autowired
    private CommService commService;

    /**
     * 分页查询培训
     * @param request
     * @return
     * @throws Exception
     */
    public PageInfo t_srchList4p(HttpServletRequest request, String sort, String order) throws Exception {
        int page = Integer.parseInt((String)request.getParameter("page"));
        int rows = Integer.parseInt((String)request.getParameter("rows"));
        Example example = new Example(WhgTra.class);
        Example.Criteria c = example.createCriteria();
        String title = request.getParameter("title");
        if(title != null && !"".equals(title)){
            c.andLike("title","%"+title+"%");
        }
        String pageType = request.getParameter("type");
        if ("edit".equalsIgnoreCase(pageType)){
            c.andIn("state", Arrays.asList(1,5));
        }
        //审核列表，查 9待审核
        if ("check".equalsIgnoreCase(pageType)){
            c.andEqualTo("state", 9);
        }
        //发布列表，查 2待发布 6已发布 4已下架
        if ("publish".equalsIgnoreCase(pageType)){
            c.andIn("state", Arrays.asList(2,6,4));
        }
        //删除列表，查已删除 否则查未删除的
        if ("del".equalsIgnoreCase(pageType)){
            c.andEqualTo("delstate", 1);
        }else{
            c.andEqualTo("delstate", 0);
        }
        if(request.getParameter("state") != null){
            int state = Integer.parseInt((String)request.getParameter("state"));
            c.andEqualTo("state", state);
        }
        if(request.getParameter("recommend") != null){
            int recommend = Integer.parseInt((String)request.getParameter("recommend"));
            c.andEqualTo("recommend", recommend);
        }
        //排序
        if (sort!=null && !sort.isEmpty()){
            if (order!=null && "asc".equalsIgnoreCase(order)){
                example.orderBy(sort).asc();
            }else{
                example.orderBy(sort).desc();
            }
        }else{
            example.setOrderByClause(" crtdate desc");
        }

        PageHelper.startPage(page, rows);
        List list= this.whgTraMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    /**
     * 根据主键查询培训
     * @param id
     * @return
     */
    public WhgTra srchOne(String id) throws Exception{
        return whgTraMapper.selectByPrimaryKey(id);
    }

    /**
     *添加培训
     * @param tra
     * @param user
     */
    public ResponseBean t_add(WhgTra tra, WhgSysUser user, HttpServletRequest request) throws Exception {
        ResponseBean res = new ResponseBean();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(!"".equals(request.getParameter("age1")) && !"".equals(request.getParameter("age2"))){
            String _age1 = request.getParameter("age1");
            String _age2 = request.getParameter("age2");
            tra.setAge(_age1+","+_age2);
        }
        Date now = new Date();
        tra.setId(commService.getKey("whgtra"));  //id
        tra.setCrtdate(now);  //创建时间
        tra.setUpindex(0);   //上首页
        tra.setCrtuser(user.getId());   //创建人
        //tra.setCultid(user.getCultid());
        tra.setDeptid(user.getDeptid());
        tra.setDelstate(0);   //删除状态
        tra.setState(1);  //编辑状态
        tra.setStatemdfdate(now);  //状态时间
        tra.setStatemdfuser(user.getId());   //修改状态的人
        tra.setRecommend(0);  //是否推荐，默认不推荐
        if(!"".equals(request.getParameter("sin_starttime")) && !"".equals(request.getParameter("sin_endtime"))){
            tra.setStarttime(sdf.parse((String)request.getParameter("sin_starttime")));
            tra.setEndtime(sdf.parse((String)request.getParameter("sin_endtime")));
        }
        this.whgTraMapper.insertSelective(tra);
        //

        WhgTraCourse traCourse = new WhgTraCourse();
        int i;

        if(tra.getIsmultisite() == 1){
            if(!"".equals(request.getParameterValues("_starttime")) && !"".equals(request.getParameterValues("_endtime"))){
                for(int j = 0; j<request.getParameterValues("_starttime").length; j++){
                    String _starttime = (String) request.getParameterValues("_starttime")[j];
                    String _endtime = (String) request.getParameterValues("_endtime")[j];
                    traCourse.setEndtime(sdf.parse(_endtime));
                    traCourse.setStarttime(sdf.parse(_starttime));
                    saveCourse(tra, user, traCourse);
                }
            }
        }else if(tra.getIsmultisite() == 2){
            if(!"".equals(request.getParameterValues("fixedweek"))){
                for( i=0; i<request.getParameterValues("fixedweek").length; i++){
                    List<Integer> daysOfOneWeek = new ArrayList<Integer>();
                    daysOfOneWeek.add(Integer.parseInt(request.getParameterValues("fixedweek")[i]));
                    List<String> daysNeedBookList = WeekDayUtil.getDates(sdf.format(tra.getStarttime()), sdf.format(tra.getEndtime()), daysOfOneWeek);
                    if(daysNeedBookList.size() > 0){
                        for (String s : daysNeedBookList) {
                            if(!"".equals(request.getParameter("fixedstarttime")) && !"".equals(request.getParameter("fixedendtime"))){
                                String a = s+" "+request.getParameter("fixedstarttime")+":00";
                                String b = s+" "+request.getParameter("fixedendtime")+":00";
                                traCourse.setStarttime(sdf.parse(a));
                                traCourse.setEndtime(sdf.parse(b));
                                saveCourse(tra, user, traCourse);
                            }
                        }
                    }else{
                        res.setSuccess(ResponseBean.FAIL);
                        res.setErrormsg("您选择的周几在时段内没有,请重新选择.");
                        this.whgTraMapper.deleteByPrimaryKey(tra.getId());
                        return res;
                    }

                }

            }
            //saveCourse(tra, user, traCourse);
        }else{
            traCourse.setStarttime(sdf.parse(request.getParameter("sin_starttime")));
            traCourse.setEndtime(sdf.parse(request.getParameter("sin_endtime")));
            saveCourse(tra, user, traCourse);
        }
        return res;
    }



    /**
     * 保存课程表
     * @param tra
     * @param user
     * @throws Exception
     */
    private void saveCourse(WhgTra tra, WhgSysUser user, WhgTraCourse traCourse) throws Exception{
        Date now = new Date();

        traCourse.setId(commService.getKey("whg_tra_course"));
        traCourse.setTraid(tra.getId());
        traCourse.setDelstate(0);
        traCourse.setCrtuser(user.getId());
        traCourse.setCrtdate(now);
        traCourse.setState(1);
        traCourse.setStatemdfdate(now);
        traCourse.setStatemdfuser(user.getId());
        this.whgTraCourseMapper.insertSelective(traCourse);
    }

    /**
     *编辑培训
     * @param tra
     * @param sysUser
     */
    public void t_edit(WhgTra tra, WhgSysUser sysUser, HttpServletRequest request) throws Exception {
        if(!"".equals(request.getParameter("age1")) && !"".equals(request.getParameter("age2"))){
            String _age1 = request.getParameter("age1");
            String _age2 = request.getParameter("age2");
            tra.setAge(_age1+","+_age2);
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(tra.getIsmultisite() == 0 && request.getParameter("sin_starttime") != null && request.getParameter("sin_endtime") != null){
            tra.setStarttime(sdf.parse((String)request.getParameter("sin_starttime")));
            tra.setEndtime(sdf.parse((String)request.getParameter("sin_endtime")));
        }
        this.whgTraMapper.updateByPrimaryKeySelective(tra);
        String traid = tra.getId();
        Example example = new Example(WhgTraCourse.class);
        example.createCriteria().andEqualTo("traid",traid);
        this.whgTraCourseMapper.deleteByExample(example);
        WhgTraCourse traCourse = new WhgTraCourse();
        if(tra.getIsmultisite() == 0 && request.getParameter("sin_starttime") != null && request.getParameter("sin_endtime") != null){

            traCourse.setStarttime(sdf.parse(request.getParameter("sin_starttime")));
            traCourse.setEndtime(sdf.parse(request.getParameter("sin_endtime")));
            saveCourse(tra, sysUser, traCourse);
        }
        if(tra.getIsmultisite() == 1){
            if(!"".equals(request.getParameterValues("_starttime")) && !"".equals(request.getParameterValues("_endtime"))){
                for(int j = 0; j<request.getParameterValues("_starttime").length; j++){
                    String _starttime = (String) request.getParameterValues("_starttime")[j];
                    String _endtime = (String) request.getParameterValues("_endtime")[j];
                    if(!"".equals(_starttime) && !"".equals(_endtime)){
                        traCourse.setEndtime(sdf.parse(_endtime));
                        traCourse.setStarttime(sdf.parse(_starttime));
                        saveCourse(tra, sysUser, traCourse);
                    }
                }
            }
        }
        if(tra.getIsmultisite() == 2 && !"".equals(request.getParameter("starttime")) && !"".equals(request.getParameter("endtime"))){
            String starttime = (String)request.getParameter("starttime");
            String endtime = (String)request.getParameter("endtime");
            if(!"".equals(request.getParameterValues("fixedweek"))){
                for(int i=0; i<request.getParameterValues("fixedweek").length; i++){
                    List<Integer> daysOfOneWeek = new ArrayList<Integer>();
                    daysOfOneWeek.add(Integer.parseInt(request.getParameterValues("fixedweek")[i]));
                    List<String> daysNeedBookList = WeekDayUtil.getDates(starttime, endtime, daysOfOneWeek);
                    for (String s : daysNeedBookList) {
                        if(!"".equals(request.getParameter("fixedstarttime")) && !"".equals(request.getParameter("fixedendtime"))){
                            String a = s+" "+request.getParameter("fixedstarttime")+":00";;
                            String b = s+" "+request.getParameter("fixedendtime")+":00";;
                            traCourse.setStarttime(sdf.parse(a));
                            traCourse.setEndtime(sdf.parse(b));
                            saveCourse(tra, sysUser, traCourse);
                        }
                    }
                }

            }
        }

    }

    /**
     * 删除培训
     * @param id
     * @param user
     */
    public void t_del(String id, WhgSysUser user) throws Exception {
        WhgTra tra = whgTraMapper.selectByPrimaryKey(id);
        if (tra == null){
            return;
        }
        if (tra.getDelstate()!=null && tra.getDelstate().compareTo(new Integer(1))==0 ){
            this.whgTraMapper.deleteByPrimaryKey(id);
        }else {
            tra = new WhgTra();
            tra.setId(id);
            tra.setDelstate(1);
            this.whgTraMapper.updateByPrimaryKeySelective(tra);
        }
    }

    /**
     * 改变状态
     * @param ids
     * @param formstates
     * @param tostate
     * @param user
     * @return
     */
    public ResponseBean t_updstate(String statemdfdate, String ids, String formstates, int tostate, WhgSysUser user) throws Exception {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ResponseBean rb = new ResponseBean();
        if (ids == null){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("培训主键丢失");
            return rb;
        }
        Example example = new Example(WhgTra.class);
        example.createCriteria()
                .andIn("state", Arrays.asList( formstates.split("\\s*,\\s*") ))
                .andIn("id", Arrays.asList( ids.split("\\s*,\\s*") ) );
        WhgTra tra = new WhgTra();
        tra.setState(tostate);
        if(!"".equals(statemdfdate) && statemdfdate != null){
            tra.setStatemdfdate(sdf.parse(statemdfdate));
        }else{
            tra.setStatemdfdate(new Date());
        }

        tra.setStatemdfuser(user.getId());
        this.whgTraMapper.updateByExampleSelective(tra, example);
        return rb;
    }

    /**
     * 是否推荐
     * @param ids
     * @param formrecoms
     * @param torecom
     * @return
     */
    public ResponseBean t_updrecommend(String ids, String formrecoms, int torecom) throws Exception {
        ResponseBean res = new ResponseBean();
        if(ids == null){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("培训主键丢失");
            return res;
        }
        Example example = new Example(WhgTra.class);
        Example.Criteria c = example.createCriteria();
        c.andEqualTo("state",6);
        c.andEqualTo("recommend",1);
        int result = this.whgTraMapper.selectCountByExample(example);
        Example example1 = new Example(WhgTra.class);
        Example.Criteria c1 = example1.createCriteria();
        c1.andIn("id", Arrays.asList( ids.split("\\s*,\\s*") ));
        c1.andIn("recommend", Arrays.asList( formrecoms.split("\\s*,\\s*") ));
        WhgTra tra = new WhgTra();
        tra.setRecommend(torecom);
        this.whgTraMapper.updateByExampleSelective(tra,example1);
        return res;
    }

    /**
     * 还原
     * @param id
     * @param sysUser
     */
    public void t_undel(String id, WhgSysUser sysUser) {
        WhgTra tra = this.whgTraMapper.selectByPrimaryKey(id);
        if (tra == null){
            return;
        }

        tra = new WhgTra();
        tra.setId(id);
        tra.setDelstate(0);
        this.whgTraMapper.updateByPrimaryKeySelective(tra);
    }

    /**
     * 上首页
     * @param ids
     * @param formupindex
     * @param toupindex
     * @return
     */
    public ResponseBean t_upindex(String ids, String formupindex, int toupindex) {
        ResponseBean res = new ResponseBean();
        if(ids == null){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("培训主键丢失");
            return res;
        }
        Example example = new Example(WhgTra.class);
        Example.Criteria c = example.createCriteria();
        c.andIn("id", Arrays.asList( ids.split("\\s*,\\s*") ));
        c.andIn("upindex", Arrays.asList( formupindex.split("\\s*,\\s*") ));
        WhgTra tra = new WhgTra();
        tra.setUpindex(toupindex);
        this.whgTraMapper.updateByExampleSelective(tra,example);
        return res;
    }
}
