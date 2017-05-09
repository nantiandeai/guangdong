package com.creatoo.hn.services.admin.exhi;

import com.creatoo.hn.ext.emun.EnumState;
import com.creatoo.hn.mapper.WhgExhArtMapper;
import com.creatoo.hn.model.WhgExh;
import com.creatoo.hn.model.WhgExhArt;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 数字展览作品service
 *
 * @author luzhihuai
 * @version 1-201703
 *          Created by Administrator on 2017/4/26.
 */
@Service
public class WhgExhiArtService {
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
     * 数字展览作品DAO
     */
    @Autowired
    private WhgExhArtMapper whgExhArtMapper;

    /**
     * 分页查询分类列表信息
     *
     * @param request。
     */
    public PageInfo<WhgExhArt> t_srchList4p(HttpServletRequest request,WhgExhArt whgExhArt) throws Exception {
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);
        //分页信息
        int page = Integer.parseInt((String) paramMap.get("page"));
        int rows = Integer.parseInt((String) paramMap.get("rows"));
        //开始分页
        PageHelper.startPage(page, rows);

        //搜索条件
        Example example = new Example(WhgExhArt.class);
        Example.Criteria c = example.createCriteria();

        //名称条件
        if(whgExhArt != null && whgExhArt.getArttitle() != null){
            c.andLike("arttitle", "%"+whgExhArt.getArttitle()+"%");
            whgExhArt.setArttitle(null);
        }
        if(whgExhArt != null && whgExhArt.getArtauthor() != null){
            c.andLike("artauthor", "%"+whgExhArt.getArtauthor()+"%");
            whgExhArt.setArtauthor(null);
        }
        //其它条件
        c.andEqualTo(whgExhArt);

        example.setOrderByClause("artidx,artcrttime desc");

        //分页查询
        PageHelper.startPage(page, rows);
        List<WhgExhArt> typeList = this.whgExhArtMapper.selectByExample(example);
        return new PageInfo<>(typeList);
    }

    /**
     * 查询单条记录
     * @param artid artid
     * @return 对象
     * @throws Exception
     */
    public WhgExhArt t_srchOne(String artid)throws Exception{
        return this.whgExhArtMapper.selectByPrimaryKey(artid);
    }

    /**
     * 添加
     *
     * @param
     */
    public void t_add(HttpServletRequest request, WhgExhArt whgExhArt) throws Exception {
        WhgSysUser user = (WhgSysUser) request.getSession().getAttribute("user");

        Example example = new Example(WhgExhArt.class);
        example.createCriteria().andIsNotNull("artid").andEqualTo("artexhid",whgExhArt.getArtexhid());
        int exhidx = this.whgExhArtMapper.selectCountByExample(example);
        exhidx++;

        whgExhArt.setArtid(commService.getKey("whg_exh_art"));
        whgExhArt.setExhcultid(user.getCultid());
        whgExhArt.setExhdeptid(user.getDeptid());
        whgExhArt.setArtstate(EnumState.STATE_YES.getValue());
        whgExhArt.setArtidx(exhidx);
        this.whgExhArtMapper.insertSelective(whgExhArt);
    }

    /**
     * 编辑
     *
     * @param
     */
    public void t_edit(WhgExhArt whgExhArt) throws Exception {
        this.whgExhArtMapper.updateByPrimaryKeySelective(whgExhArt);
    }


    /**
     * 更新状态
     * @param ids ID，多个用逗号分隔
     * @param fromState 改状态之前的限制状态
     * @param toState 修改后的状态
     * @throws Exception
     */
    public void t_updstate(String ids, String fromState, String toState)throws Exception{
        if(ids != null && toState != null){
            String[] idArr = ids.split(",");
            Example example = new Example(WhgExhArt.class);
            Example.Criteria c = example.createCriteria();
            c.andIn("artid", Arrays.asList(idArr));
            if(fromState != null){
                c.andEqualTo("artstate", fromState);
            }
            WhgExhArt record = new WhgExhArt();
            record.setArtstate(Integer.parseInt(toState));
            Date now = new Date();
            record.setArtopttime(now);
            this.whgExhArtMapper.updateByExampleSelective(record, example);
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
        String artid = (String) paramMap.get("artid");
        int result = this.whgExhArtMapper.deleteByPrimaryKey(artid);
        if (result != 1) {
            throw new Exception("删除数据失败！");
        }
    }

    /**
     * 排序
     * @param artid 主键
     * @param type up|top|idx
     * @param val 当type=idx时，表示直接设置排序值
     * @throws Exception
     */
    public void t_sort(String artid, String type, String val,String artexhid) throws Exception {
        //初始所有记录的idx值
        WhgExhArt whgExh1 = this.whgExhArtMapper.selectByPrimaryKey(artid);
        Integer idx = whgExh1.getArtidx();
        if (idx == null) {
            Example example = new Example(WhgExhArt.class);
            example.createCriteria().andEqualTo("artexhid", artexhid);
            example.setOrderByClause("artcrttime desc");
            List<WhgExhArt> allList = this.whgExhArtMapper.selectByExample(example);
            if (allList != null && allList.size() > 0) {
                for (int i = 0; i < allList.size(); i++) {
                    WhgExhArt _cult = allList.get(i);
                    _cult.setArtidx(i + 1);
                    this.whgExhArtMapper.updateByPrimaryKeySelective(_cult);
                }
            }
        }
        //上移
        if ("up".equals(type)) {
            //当前记录
            WhgExhArt whgExh = this.whgExhArtMapper.selectByPrimaryKey(artid);
            int curIdx = whgExh.getArtidx();
            //前一条记录
            Example example2 = new Example(WhgExhArt.class);
            example2.createCriteria().andLessThan("artidx", whgExh.getArtidx());
            example2.createCriteria().andEqualTo("artexhid", artexhid);
            example2.setOrderByClause("artidx desc");
            PageHelper.startPage(1, 1);
            List<WhgExhArt> lessList = this.whgExhArtMapper.selectByExample(example2);
            PageInfo<WhgExhArt> pi = new PageInfo<>(lessList);
            if (lessList != null && lessList.size() == 1) {
                WhgExhArt exh = lessList.get(0);
                int preIdx = exh.getArtidx();

                whgExh.setArtidx(preIdx);
                this.whgExhArtMapper.updateByPrimaryKeySelective(whgExh);

                exh.setArtidx(curIdx);
                this.whgExhArtMapper.updateByPrimaryKeySelective(exh);
            }
        }
        //置顶
        else if ("top".equals(type)) {
            //当前记录
            WhgExhArt whgExh = this.whgExhArtMapper.selectByPrimaryKey(artid);
            int curIdx = whgExh.getArtidx();

            Example example = new Example(WhgExhArt.class);
            example.createCriteria().andLessThan("artidx", curIdx).andEqualTo("artexhid",artexhid);
            example.setOrderByClause("artidx");
            List<WhgExhArt> allList = this.whgExhArtMapper.selectByExample(example);
            if (allList != null && allList.size() > 0) {
                List<WhgExhArt> newList = new ArrayList<>();
                for (WhgExhArt c : allList) {
                    if (c != null && !whgExh.getArtid().equals(c.getArtid())) {
                        newList.add(c);
                    }
                }
                whgExh.setArtidx(1);
                this.whgExhArtMapper.updateByPrimaryKey(whgExh);

                for (int i = 0; i < newList.size(); i++) {
                    WhgExhArt _cult = newList.get(i);
                    _cult.setArtidx(i + 2);
                    this.whgExhArtMapper.updateByPrimaryKeySelective(_cult);
                }
            }
        }
        //idx 直接设置idx值
        else if ("artidx".equals(type)) {
            //当前记录
            WhgExhArt whgExh = this.whgExhArtMapper.selectByPrimaryKey(artid);
            whgExh.setArtidx(Integer.parseInt(val));
            this.whgExhArtMapper.updateByPrimaryKey(whgExh);
        }
    }


}
