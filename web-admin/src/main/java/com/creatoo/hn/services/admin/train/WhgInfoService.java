package com.creatoo.hn.services.admin.train;

import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.mapper.WhZxColinfoMapper;
import com.creatoo.hn.mapper.WhgPubInfoMapper;
import com.creatoo.hn.model.WhZxColinfo;
import com.creatoo.hn.model.WhgPubInfo;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.model.WhgTra;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 培训、活动、非遗公用资讯Service
 * @author wenjingqiang
 * @version 1-201703
 * Created by Administrator on 2017/5/2.
 */
@Service
public class WhgInfoService {
    @Autowired
    private WhgPubInfoMapper whgPubInfoMapper;

    @Autowired
    private CommService commService;

    /**
     * 栏目内容mapper
     */
    @Autowired
    private WhZxColinfoMapper whZxColinfoMapper;
    /**
     * 分页查询资讯公告数据
     * @param paramMap
     * @return
     */
    public PageInfo t_srchList4p(Map<String, Object> paramMap) throws Exception {
        //分页信息
        int page = Integer.parseInt((String)paramMap.get("page"));
        int rows = Integer.parseInt((String)paramMap.get("rows"));

        //带条件的分页查询
        PageHelper.startPage(page, rows);
        List<Map> list = this.whgPubInfoMapper.t_srchList4p(paramMap);

        // 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);
        return pageInfo;
    }

    /**
     *
     * @param info
     * @param user
     * @param request
     * @return
     */
    public void t_add(WhZxColinfo info, WhgSysUser user, HttpServletRequest request, String entityid) throws Exception {
        info.setClnfid(commService.getKey("whzxcolinfo"));
        //info.setClnfcrttime(new Date());
        info.setClnfopttime(new Date());
        info.setClnfstata(3);
        info.setClnvenueid(user.getCultid());
        info.setTotop(0);
        this.whZxColinfoMapper.insert(info);

        WhgPubInfo pubInfo = new WhgPubInfo();
        pubInfo.setClnftype(info.getClnftype());
        pubInfo.setId(commService.getKey("whg_pub_info"));
        pubInfo.setClnfid(info.getClnfid());
        pubInfo.setEntityid(entityid);
        this.whgPubInfoMapper.insert(pubInfo);
    }

    /**
     * 根据ID查找资讯公告信息
     * @param id
     * @return
     */
    public Map serchOne(String id) throws Exception {

        return this.whgPubInfoMapper.t_srchOne(id);
    }

    /**
     * 编辑资讯公告信息
     * @param info
     * @param user
     * @param request
     * @param entityid
     */
    public void t_edit(WhZxColinfo info, WhgSysUser user, HttpServletRequest request, String entityid)throws Exception {
        info.setTotop(0);
        this.whZxColinfoMapper.updateByPrimaryKey(info);

        Example example = new Example(WhgPubInfo.class);
        example.createCriteria().andEqualTo("clnfid",info.getClnfid()).andEqualTo("clnftype",info.getClnftype());
        List<WhgPubInfo> pubInfo = whgPubInfoMapper.selectByExample(example);
        if(pubInfo.size()>0){
            for(int i = 0; i<pubInfo.size(); i++){
                pubInfo.get(i).setClnftype(info.getClnftype());
                this.whgPubInfoMapper.updateByPrimaryKey(pubInfo.get(i));
            }
        }

    }

    /**
     * 删除资讯公告
     * @param id
     */
    public void t_del(String id) throws Exception {
        whgPubInfoMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查询可以进行选择的资讯公告
     * @param paramMap
     * @return
     */
    public PageInfo t_srchcolinfoList4p(Map<String, Object> paramMap)throws Exception {
        //分页信息
        int page = Integer.parseInt((String)paramMap.get("page"));
        int rows = Integer.parseInt((String)paramMap.get("rows"));
//        int entity = Integer.parseInt((String)paramMap.get("entity"));
//        Example example = new Example(WhZxColinfo.class);
//        Example.Criteria c = example.createCriteria();
//        if(entity == 1){
//            c.andIn("clnftype",Arrays.asList("2016111900000020","2016111900000021"));
//        }
//        if(entity == 2){
//            c.andIn("clnftype",Arrays.asList("2016111900000012","2016111900000018"));
//        }
//        if(entity == 3){
//            c.andIn("clnftype",Arrays.asList("2016112200000004","2016112200000005"));
//        }
//        c.andEqualTo("clnfstata",3);
//        example.setOrderByClause("clnfcrttime desc");
        //带条件的分页查询
        PageHelper.startPage(page, rows);
        List<Map> list = this.whgPubInfoMapper.t_srchcolinfoList4p(paramMap);

        // 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);
        return pageInfo;
    }

    /**
     * 选择添加资讯公告
     * @param request
     * @param ids
     */
    public void t_seladd(String ids,String entityid,  HttpServletRequest request) throws Exception{
        Example example = new Example(WhZxColinfo.class);
        example.createCriteria().andIn("clnfid",Arrays.asList(ids.split("\\s*,\\s*")));
        List<WhZxColinfo> list = whZxColinfoMapper.selectByExample(example);
        WhgPubInfo info = new WhgPubInfo();
        if(list.size() > 0){
            for(int i =0; i<list.size(); i++){
                info.setId(commService.getKey("whg_pub_info"));
                info.setClnfid(list.get(i).getClnfid());
                info.setEntityid(entityid);
                info.setClnftype(list.get(i).getClnftype());
                whgPubInfoMapper.insert(info);
            }
        }

    }
}
