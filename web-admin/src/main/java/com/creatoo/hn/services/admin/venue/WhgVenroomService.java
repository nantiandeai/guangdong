package com.creatoo.hn.services.admin.venue;

import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.mapper.WhgVenMapper;
import com.creatoo.hn.mapper.WhgVenRoomMapper;
import com.creatoo.hn.mapper.admin.CrtWhgVenueMapper;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.model.WhgVen;
import com.creatoo.hn.model.WhgVenRoom;
import com.creatoo.hn.services.comm.CommService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by rbg on 2017/3/23.
 */

@SuppressWarnings("ALL")
@Service
public class WhgVenroomService {

    @Autowired
    private WhgVenRoomMapper whgVenRoomMapper;

    @Autowired
    private WhgVenMapper whgVenMapper;

    @Autowired
    private CrtWhgVenueMapper crtWhgVenueMapper;

    @Autowired
    private CommService commService;

    /**
     * 分页查询活动室
     * @param page
     * @param rows
     * @param request
     * @return
     * @throws Exception
     */
    public PageInfo srchList4p(int page, int rows, Map record) throws Exception{
        PageHelper.startPage(page, rows);
        List list = this.crtWhgVenueMapper.srchListVenroom(record);
        return new PageInfo<Object>(list);
    }

    public List srchList(WhgVenRoom room, List states, String sort, String order) throws Exception{
        Example example = new Example(WhgVenRoom.class);
        Example.Criteria c = example.createCriteria();

        //标题处理
        if (room.getTitle()!=null){
            c.andLike("title", "%"+room.getTitle()+"%");
            room.setTitle(null); //去除title等于条件
        }

        //指定状态集时
        if (states!=null && states.size()>0){
            c.andIn("state", states);
        }

        //常规等于处理
        c.andEqualTo(room);

        //排序
        if (sort!=null && !sort.isEmpty()){
            if (order!=null && "asc".equalsIgnoreCase(order)){
                example.orderBy(sort).asc();
            }else{
                example.orderBy(sort).desc();
            }
        }else{
            example.orderBy("statemdfdate").desc();
        }

        return this.whgVenRoomMapper.selectByExample(example);
    }

    /**
     * 查询指定ID的活动室
     * @param id
     * @return
     * @throws Exception
     */
    public WhgVenRoom srchOne(String id) throws Exception{
        return this.whgVenRoomMapper.selectByPrimaryKey(id);
    }


    /**
     * 删除活动室
     * @param id
     * @param user
     * @throws Exception
     */
    public void t_del(String id, WhgSysUser user) throws Exception{
        WhgVenRoom whgdata = this.whgVenRoomMapper.selectByPrimaryKey(id);
        if (whgdata == null){
            return;
        }

        if (whgdata.getDelstate()!=null && whgdata.getDelstate().compareTo(new Integer(1))==0 ){
            this.whgVenRoomMapper.deleteByPrimaryKey(id);
        }else {
            whgdata = new WhgVenRoom();
            whgdata.setId(id);
            whgdata.setDelstate(1);
            this.whgVenRoomMapper.updateByPrimaryKeySelective(whgdata);
        }
    }

    /**
     * 还原删除
     * @param id
     * @param user
     * @throws Exception
     */
    public void t_undel(String id, WhgSysUser user) throws Exception{
        WhgVenRoom whgdata = this.whgVenRoomMapper.selectByPrimaryKey(id);
        if (whgdata == null){
            return;
        }

        whgdata = new WhgVenRoom();
        whgdata.setId(id);
        whgdata.setDelstate(0);
        this.whgVenRoomMapper.updateByPrimaryKeySelective(whgdata);
    }

    /**
     * 更新活动室状态
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
            rb.setErrormsg("活动室标记丢失");
            return rb;
        }
        Example example = new Example(WhgVen.class);
        example.createCriteria()
                .andIn("state", Arrays.asList( formstates.split("\\s*,\\s*") ))
                .andIn("id", Arrays.asList( ids.split("\\s*,\\s*") ) );
        WhgVenRoom whgdata = new WhgVenRoom();
        whgdata.setState(tostate);
        if (optTime==null) optTime = new Date();
        whgdata.setStatemdfdate(optTime);
        whgdata.setStatemdfuser(user.getId());
        this.whgVenRoomMapper.updateByExampleSelective(whgdata, example);

        return rb;
    }

    /**
     * 添加活动室
     * @param room
     * @param user
     * @throws Exception
     */
    public ResponseBean t_add(WhgVenRoom room, WhgSysUser user) throws Exception{
        ResponseBean rb = new ResponseBean();
        if (room == null || room.getVenid()==null){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("活动室信息未指定");
            return rb;
        }
        WhgVen ven = new WhgVen();
        ven.setId(room.getVenid());
        ven.setDelstate(0);
        List expList = this.whgVenMapper.select(ven);
        if (expList==null || expList.size()==0){
            rb.setSuccess(ResponseBean.FAIL);
            rb.setErrormsg("活动室指定的场馆不可用");
            return rb;
        }

        room.setId(commService.getKey("whgvenroom"));
        room.setState(1);
        room.setDelstate(0);
        room.setStatemdfdate(new Date());
        room.setStatemdfuser(user.getId());
        room.setCrtdate(new Date());
        room.setCrtuser(user.getId());
        room.setRecommend(0);
        this.whgVenRoomMapper.insert(room);

        return rb;
    }

    /**
     * 编辑活动室
     * @param room
     * @param user
     * @throws Exception
     */
    public void t_edit(WhgVenRoom room, WhgSysUser user) throws Exception{
        this.whgVenRoomMapper.updateByPrimaryKeySelective(room);
    }
}
