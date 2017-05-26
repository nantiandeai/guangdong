package com.creatoo.hn.services.admin.train;

import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.mapper.WhgTraEnrolMapper;
import com.creatoo.hn.mapper.WhgTraMapper;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.model.WhgTra;
import com.creatoo.hn.model.WhgTraEnrol;
import com.creatoo.hn.services.comm.SMSService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 培训报名管理service
 * @author wenjingqiang
 * @version 1-201703
 * Created by Administrator on 2017/4/1.
 */
@Service
public class WhgTrainEnrolService {
    /**
     * 培训报名mapper
     */
    @Autowired
    private WhgTraEnrolMapper whgTraEnrolMapper;

    @Autowired
    private WhgTraMapper whgTraMapper;

    /**
     * 发送短信service
     */
    @Autowired
    private SMSService smsService;

    /**
     *分页查询培训报名数据
     * @param request
     * @return
     */
    public PageInfo t_srchList4p(HttpServletRequest request)throws Exception{
        int page = Integer.parseInt((String)request.getParameter("page"));
        int rows = Integer.parseInt((String)request.getParameter("rows"));
        String traid = request.getParameter("traid");
        int type = Integer.parseInt((String)request.getParameter("type"));
        int tab = Integer.parseInt((String)request.getParameter("tab"));
        Example example = new Example(WhgTraEnrol.class);
        Example.Criteria c = example.createCriteria();
        c.andEqualTo("traid",traid);
        if(type == 0 && tab == 0){
            c.andIn("state", Arrays.asList(1,2,3));
        }
        if(tab == 1){
            c.andIn("state", Arrays.asList(4,5,6));
        }
        if(request.getParameter("state") != null){
            int state = Integer.parseInt((String)request.getParameter("state"));
            c.andEqualTo("state", state);
        }
        if(request.getParameter("contactphone") != null){
            String contactphone = (String)request.getParameter("contactphone");
            c.andLike("contactphone", "%"+contactphone+"%");
        }
        example.setOrderByClause("crttime desc");
        PageHelper.startPage(page,rows);
        List<WhgTraEnrol> list = this.whgTraEnrolMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    /**
     * 导出带条件查询
     * @param request
     * @return
     */
    public List<WhgTraEnrol> serch(HttpServletRequest request){

        Example example = new Example(WhgTraEnrol.class);
        Example.Criteria c = example.createCriteria();
        String traid = request.getParameter("traid");
        int type = Integer.parseInt(request.getParameter("type"));
        int tab = Integer.parseInt(request.getParameter("tab"));
        if(type == 0 && tab == 0){
            c.andIn("state", Arrays.asList(1,2,3));
        }
        if(tab == 1){
            c.andIn("state", Arrays.asList(4,5,6));
        }
        c.andEqualTo("traid",traid);
        if(request.getParameter("state") != null&&!"".equals(request.getParameter("state"))){
            int state = Integer.parseInt(request.getParameter("state"));
            c.andEqualTo("state", state);
        }
        if(request.getParameter("contactphone") != null){
            String contactphone = request.getParameter("contactphone");
            c.andLike("contactphone", "%"+contactphone+"%");
        }
        example.setOrderByClause("crttime desc");
        List<WhgTraEnrol> list = this.whgTraEnrolMapper.selectByExample(example);
        return list;
    }

    /**
     * 修改状态
     * @param ids
     * @param fromstate
     * @param tostate
     * @param sysUser
     * @return
     */
    public ResponseBean t_updstate(String statedesc, String ids, String fromstate, int tostate, WhgSysUser sysUser, String viewtime, String viewaddress)throws Exception {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ResponseBean rb = new ResponseBean();
        String[] enrollid = ids.split("\\s*,\\s*");

        if (ids == null){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("培训报名信息主键丢失");
            return rb;
        }
        //修改状态
        Example example = new Example(WhgTraEnrol.class);
        example.createCriteria()
                .andIn("state", Arrays.asList( fromstate.split("\\s*,\\s*") ))
                .andIn("id", Arrays.asList( ids.split("\\s*,\\s*") ) );
        List<WhgTraEnrol> list = this.whgTraEnrolMapper.selectByExample(example);
        String title = "";
        int basicNumber = 0;
        Date starttime = new Date();
        Date endtime = new Date();
        if(list.size() != 0){
            String traid = list.get(0).getTraid();
            Example example1 = new Example(WhgTra.class);
            example1.createCriteria().andEqualTo("id",traid);
            List<WhgTra> tra = this.whgTraMapper.selectByExample(example1);
            if(tra.size() != 0){
                title = tra.get(0).getTitle();
                starttime = tra.get(0).getStarttime();
                endtime = tra.get(0).getEndtime();
                basicNumber = tra.get(0).getBasicenrollnumber();

                Example enrol = new Example(WhgTraEnrol.class);
                enrol.createCriteria().andEqualTo("state",6).andEqualTo("traid",tra.get(0).getId());
                int count = whgTraEnrolMapper.selectCountByExample(enrol);

                if((tostate==6) &&(basicNumber - count) < enrollid.length){
                    rb.setSuccess(ResponseBean.FAIL);
                    rb.setErrormsg(title+"的基础报名人数是"+basicNumber+"，选择的人数已超过基础报名人数");
                    return rb;
                }
            }
        }else{
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("找不到相应的报名记录");
            return rb;
        }


        for(int j=0; j<list.size(); j++){
            String phone = list.get(j).getContactphone();
            String name = list.get(j).getRealname();
            Map<String,String> _map = new HashMap<>();
            _map.put("name",name);
            _map.put("title",title);
            _map.put("starttime",sdf.format(starttime));
            _map.put("endtime",sdf.format(endtime));
            _map.put("date",viewtime);
            _map.put("address",viewaddress);
            String tempCode = "";
            if(tostate == 4){
                tempCode = "TRA_CHECK_PASS";
            }
            if(tostate == 3){
                tempCode = "TRA_CHECK_FAIL";
            }
            if(tostate == 5){
                tempCode = "TRA_VIEW_FAIL";
            }
            if(tostate == 6){
                tempCode = "TRA_VIEW_PASS";
            }
            if(tostate == 2){
                tempCode = "TRA_CANCEL";
            }
            this.smsService.t_sendSMS(phone,tempCode,_map);
        }


        for(int i = 0; i<list.size(); i++){
            list.get(i).setState(tostate);
            list.get(i).setStatemdfdate(new Date());
            list.get(i).setStatedesc(statedesc);
            list.get(i).setStatemdfuser(sysUser.getId());
            this.whgTraEnrolMapper.updateByPrimaryKeySelective(list.get(i));
        }
        return rb;
    }

    /**
     * 查询报名人数
     * @param id
     * @param i(1、总报名数 2、有效的报名数 3、未处理的报名数 4、面试总人数 5、成功录取人数)
     * @return
     */
    public int t_selCount(String id, int i) {
        Example example = new Example(WhgTraEnrol.class);
        Example.Criteria c = example.createCriteria();
        c.andEqualTo("traid",id);
        if(i == 1){
            c.andIn("state",Arrays.asList(1,2,3,4,5,6));
        }
        if(i == 2){
            c.andIn("state",Arrays.asList(1,4,6));
        }
        if(i == 3){
            c.andEqualTo("state",1);
        }
        if(i == 4){
            c.andIn("state",Arrays.asList(4,5,6));
        }
        if(i == 5){
            c.andEqualTo("state",6);
        }
        return this.whgTraEnrolMapper.selectCountByExample(example);
    }

    /**
     * 随机报名
     * @param ids
     * @param fromstate
     * @param tostate
     * @return
     */
    public ResponseBean ramEnroll(String ids, String fromstate, int tostate, WhgSysUser sysUser)throws Exception{
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ResponseBean res = new ResponseBean();
        //拿到所选的未处理的报名记录
        Example example = new Example(WhgTraEnrol.class);
        example.createCriteria().andIn("id",Arrays.asList(ids.split("\\s*,\\s*"))).andEqualTo("state",1);
        List<WhgTraEnrol> list = whgTraEnrolMapper.selectByExample(example);

        List<WhgTraEnrol> updateList = new ArrayList<WhgTraEnrol>();
        int basicenrollnumber = 0;
        int trabasicnumber = 0;
        WhgTra tra = new WhgTra();
        if(list.size() > 0){
            //拿到这个培训已经通过报名的记录数量
            Example example1 = new Example(WhgTraEnrol.class);
            example1.createCriteria().andEqualTo("traid",list.get(0).getTraid()).andEqualTo("state",6);
            int count = whgTraEnrolMapper.selectCountByExample(example1);

            String traid = list.get(0).getTraid();
            tra = this.whgTraMapper.selectByPrimaryKey(traid);
            trabasicnumber = tra.getBasicenrollnumber();
            //如果通过的数量不等于0 那么需要一键通过的名单数量为基础人数减去通过的人数
            if(count != 0){
                basicenrollnumber = trabasicnumber - count;
            }else{
                basicenrollnumber = trabasicnumber;
            }
        }
        if(basicenrollnumber <= list.size()){
            //取随机报名集合
            updateList = getRandomEnrolList(basicenrollnumber,list);
        }else{
            updateList = list;
        }
        //更新报名表状态
        for (int i = 0; i<updateList.size(); i++){
            updateList.get(i).setState(tostate);
            updateList.get(i).setStatemdfdate(new Date());
            updateList.get(i).setStatemdfuser(sysUser.getId());
            this.whgTraEnrolMapper.updateByPrimaryKeySelective(updateList.get(i));
            //发送短信
            Map<String,String> _map = new HashMap<>();
            _map.put("name",updateList.get(i).getRealname());
            _map.put("title",tra.getTitle());
            _map.put("starttime",sdf.format(tra.getStarttime()));
            _map.put("endtime",sdf.format(tra.getEndtime()));
            String tempCode = "TRA_VIEW_PASS";
            this.smsService.t_sendSMS(updateList.get(i).getContactphone(),tempCode,_map);
        }
        return res;
    }

    /**
     * 随机报名集合
     * @param maxNumber
     * @param list
     * @return
     */
    private List<WhgTraEnrol> getRandomEnrolList(int maxNumber,List<WhgTraEnrol> list){
        if(maxNumber == 0 || list.size() == 0) {
            return Collections.emptyList();
        }
        List<WhgTraEnrol> randomList = new ArrayList<WhgTraEnrol>();
        int index = 0;
        while (randomList.size() < maxNumber){
            index = (int) (Math.random()*(list.size()-1));
            randomList.add(list.get(index));
            list.remove(index);
        }
        return randomList;
    }
}
