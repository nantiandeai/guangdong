package com.creatoo.hn.services.admin.venue;

import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.mapper.WhgVenMapper;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.model.WhgVen;
import com.creatoo.hn.services.comm.CommService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 场馆管理-场馆业务处理
 * Created by qxkun on 2017/3/17.
 */

@SuppressWarnings("ALL")
@Service
public class WhgVenueService {

    @Autowired
    private WhgVenMapper venMapper;

    @Autowired
    private CommService commService;


    /**
     * 组合列表查询条件
     * @param ven
     * @param states
     * @param sort
     * @param order
     * @return
     */
    private Example getSrchListExample(WhgVen ven, List states, String sort, String order){
        Example example = new Example(WhgVen.class);
        Example.Criteria c = example.createCriteria();

        //标题处理
        if (ven.getTitle()!=null){
            c.andLike("title", "%"+ven.getTitle()+"%");
            ven.setTitle(null); //去除title等于条件
        }

        //指定状态集时
        if (states!=null && states.size()>0){
            c.andIn("state", states);
        }

        //常规等于处理
        c.andEqualTo(ven);

        //排序
        if (sort!=null && !sort.isEmpty()){
            if (order!=null && "asc".equalsIgnoreCase(order)){
                example.orderBy(sort).asc();
            }else{
                example.orderBy(sort).desc();
            }
        }else{
            example.orderBy("crtdate").desc();
        }

        return example;
    }

    /**
     * 场馆列表查询
     * @param ven
     * @param states
     * @param sort
     * @param order
     * @return
     * @throws Exception
     */
    public List srchList(WhgVen ven, List states, String sort, String order) throws Exception{
        Example example = getSrchListExample(ven, states, sort, order);
        return this.venMapper.selectByExample(example);
    }


    /**
     * 场馆列表页分页查询
     * @param page
     * @param rows
     * @param ven
     * @param request
     * @return
     * @throws Exception
     */
    public PageInfo srchList4p(int page, int rows, WhgVen ven, List states, String sort, String order) throws Exception{

        Example example = getSrchListExample(ven, states, sort, order);

        //分页查
        PageHelper.startPage(page, rows);
        List list= this.venMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    /**
     * 查指定ID 的场馆记录
     * @param id
     * @return
     * @throws Exception
     */
    public WhgVen srchOne(String id) throws Exception{
        return venMapper.selectByPrimaryKey(id);
    }

    /**
     * 添加场馆记录
     * @param ven
     * @param user
     * @throws Exception
     */
    public void t_add(WhgVen ven, WhgSysUser user) throws Exception{
        Date now = new Date();

        ven.setId(commService.getKey("whgven"));    //pk
        ven.setCrtdate(now);     //创建时间
        ven.setCrtuser(user.getId());   //创建人
        //ven.setCultid(user.getCultid());    //文化馆
        ven.setDeptid(user.getDeptid());    //部门
        ven.setState(1);    //编辑状态
        ven.setStatemdfdate(now);   //状态时间
        ven.setStatemdfuser(user.getId());  //状态人
        ven.setDelstate(0); //标记未删除状态
        ven.setRecommend(0);

        this.venMapper.insert(ven);
    }

    /**
     * 编辑场馆记录
     * @param ven
     * @param user
     * @throws Exception
     */
    public void t_edit(WhgVen ven, WhgSysUser user) throws Exception{
        this.venMapper.updateByPrimaryKeySelective(ven);
    }

    /**
     * 删除场馆
     * @param id
     * @param user
     * @throws Exception
     */
    public void t_del(String id, WhgSysUser user) throws Exception{
        WhgVen ven = this.venMapper.selectByPrimaryKey(id);
        if (ven == null){
            return;
        }

        if (ven.getDelstate()!=null && ven.getDelstate().compareTo(new Integer(1))==0 ){
            this.venMapper.deleteByPrimaryKey(id);
        }else {
            ven = new WhgVen();
            ven.setId(id);
            ven.setDelstate(1);
            this.venMapper.updateByPrimaryKeySelective(ven);
        }
    }

    /**
     * 还原删除
     * @param id
     * @param user
     * @throws Exception
     */
    public void t_undel(String id, WhgSysUser user) throws Exception{
        WhgVen ven = this.venMapper.selectByPrimaryKey(id);
        if (ven == null){
            return;
        }

        ven = new WhgVen();
        ven.setId(id);
        ven.setDelstate(0);
        this.venMapper.updateByPrimaryKeySelective(ven);
    }

    /**
     * 更改场馆 state
     * @param ids
     * @param formstates
     * @param tostate
     * @param user
     * @return
     * @throws Exception
     */
    public ResponseBean t_updstate(String ids, String formstates, int tostate, WhgSysUser user, Date optTime) throws Exception{
        ResponseBean rb = new ResponseBean();
        if (ids == null){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("场馆标记丢失");
            return rb;
        }
        Example example = new Example(WhgVen.class);
        example.createCriteria()
                .andIn("state", Arrays.asList( formstates.split("\\s*,\\s*") ))
                .andIn("id", Arrays.asList( ids.split("\\s*,\\s*") ) );
        WhgVen ven = new WhgVen();
        ven.setState(tostate);
        if (optTime==null) optTime = new Date();
        ven.setStatemdfdate(optTime);
        ven.setStatemdfuser(user.getId());
        this.venMapper.updateByExampleSelective(ven, example);

        return rb;
    }
}
