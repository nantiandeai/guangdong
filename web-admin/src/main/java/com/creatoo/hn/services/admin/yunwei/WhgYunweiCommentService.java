package com.creatoo.hn.services.admin.yunwei;

/**
 * Created by tangwe on 2017/4/8.
 */

import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.mapper.WhCommentMapper;
import com.creatoo.hn.mapper.WhUserMapper;
import com.creatoo.hn.model.*;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/17.
 */
@Service
public class WhgYunweiCommentService {

    @Autowired
    private WhCommentMapper whCommentMapper;

    @Autowired
    private WhUserMapper whUserMapper;

    @Autowired
    private CommService commService;

    /**
     * 分页查询分类列表信息(区分类型)
     * @param paramMap
     */
    public PageInfo<Map> t_srchList4p(Map<String, Object> paramMap) throws Exception {
        int page = Integer.parseInt((String) paramMap.get("page"));
        int rows = Integer.parseInt((String) paramMap.get("rows"));
        //开始分页
        PageHelper.startPage(page, rows);
        List<Map> listMap = whCommentMapper.selecthdCommentByType(paramMap);
        PageInfo<Map> pageInfo = new PageInfo<Map>(listMap);
        return pageInfo;
    }

    /**
     * 根据Id查询信息
     * @param id
     * @return
     */
    public Map<String,Object> getComentInfoById(String id){
        Map<String,Object> result = new HashMap<String,Object>();
        WhComment comment =  whCommentMapper.selectByPrimaryKey(id);
        WhUser user = whUserMapper.selectByPrimaryKey(comment.getRmuid());
        result.put("comment",comment);
        result.put("user",user);
        return result;
    }

    /**
     * 添加回复
     * @param record
     * @param sysUser
     * @throws Exception
     */
    public void addReply(WhComment record, WhgSysUser sysUser) throws Exception {
        WhComment comment =  whCommentMapper.selectByPrimaryKey(record.getRmid());
        if(comment != null){
            record.setRmid(commService.getKey("wh_comment"));
            record.setRmuid(sysUser.getId());
            record.setRmdate(Calendar.getInstance().getTime());
            record.setRmtitle(comment.getRmtitle());
            record.setRmurl(comment.getRmurl());
            record.setRmreftyp(comment.getRmreftyp());
            record.setRmrefid(comment.getRmid());
            record.setRmtyp(1);
            record.setRmvenueid(comment.getRmvenueid());
            record.setRmstate(1);
            int result  = whCommentMapper.insertSelective(record);
        }
    }

    /**
     * 根据评论id 查找有关的回复
     * @param id
     * @return
     */
    public List<Map> getComentReplyInfoById(String id){
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("rmrefid",id);
        return this.whCommentMapper.selecthdCommentHF(param);
    }

    /**
     * 删除评论
     * @param id
     */
    public void delComentById(String id)throws Exception{
        int result = whCommentMapper.deleteByPrimaryKey(id);
        //删除评论相关回复
        if(result > 0){
            Example example  = new Example(WhComment.class);
            example.createCriteria().andEqualTo("rmrefid",id);
            whCommentMapper.deleteByExample(example);
        }
    }

    /**
     * 审核
     * @param comment
     * @return
     * @throws Exception
     */
    public void auditComment(WhComment comment)throws Exception{
        whCommentMapper.updateByPrimaryKeySelective(comment);
    }
}



