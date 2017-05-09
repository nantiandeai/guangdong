package com.creatoo.hn.services.admin.yunwei;

import com.creatoo.hn.mapper.WhgYwiNoteMapper;
import com.creatoo.hn.model.WhgYwiNote;
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
 * 系统操作日志服务类
 * Created by wangxl on 2017/4/25.
 */
@Service
public class WhgYunweiNoteService {
    /**
     * 日志
     */
    Logger log = Logger.getLogger(this.getClass().getName());

    /**
     * 操作日志DAO
     */
    @Autowired
    private WhgYwiNoteMapper whgYwiNoteMapper;

    public PageInfo<WhgYwiNote> t_srchList4p(HttpServletRequest request, WhgYwiNote note) throws Exception {
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);

        //分页信息
        int page = Integer.parseInt((String) paramMap.get("page"));
        int rows = Integer.parseInt((String) paramMap.get("rows"));

        //搜索条件
        Example example = new Example(WhgYwiNote.class);
        Example.Criteria c = example.createCriteria();

        //名称条件
        if (note != null && note.getAdminaccount() != null) {
            c.andLike("adminaccount", "%" + note.getAdminaccount() + "%");
            note.setAdminaccount(null);
        }
        if (note != null && note.getOptargs() != null) {
            c.andLike("optargs", "%" + note.getOptargs() + "%");
            note.setOptargs(null);
        }
        if (note != null && note.getOptdesc() != null) {
            c.andLike("optdesc", "%" + note.getOptdesc() + "%");
            note.setOptdesc(null);
        }

        //其它条件
        c.andEqualTo(note);

        //排序
        example.setOrderByClause("opttime desc");

        //分页查询
        PageHelper.startPage(page, rows);
        List<WhgYwiNote> list = this.whgYwiNoteMapper.selectByExample(example);
        return new PageInfo<WhgYwiNote>(list);
    }

    /**
     * 查询操作日志详情
     * @param id 操作日志ID
     * @return
     * @throws Exception
     */
    public WhgYwiNote t_srchOne(String id)throws Exception{
        return this.whgYwiNoteMapper.selectByPrimaryKey(id);
    }
}
