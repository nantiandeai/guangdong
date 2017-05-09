package com.creatoo.hn.services.admin.user;

import com.creatoo.hn.mapper.WhgActOrderMapper;
import com.creatoo.hn.mapper.WhgUsrBacklistMapper;
import com.creatoo.hn.model.WhgActOrder;
import com.creatoo.hn.model.WhgUsrBacklist;
import com.creatoo.hn.model.WhgYwiType;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * 黑名单services
 * @author wenjingqiang
 * @version 1-201703
 * Created by Administrator on 2017/4/26.
 */
@Service
public class WhgBlackListService {
    @Autowired
    private WhgUsrBacklistMapper whgUsrBacklistMapper;

    @Autowired
    private WhgActOrderMapper whgActOrderMapper;

    public PageInfo<WhgUsrBacklist> t_srchList4p(HttpServletRequest request) {
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);
        int page = Integer.parseInt((String) paramMap.get("page"));
        int rows = Integer.parseInt((String) paramMap.get("rows"));


        String state = request.getParameter("state");
        Example example = new Example(WhgUsrBacklist.class);
        Example.Criteria c = example.createCriteria();

        if (!"".equals(state) && state != null) {
            c.andEqualTo("state", state);
        }
        String userphone = (String)paramMap.get("userphone");
        if(userphone != null && !"".equals(userphone)){
            c.andLike("userphone","%"+userphone+"%");
        }

        example.setOrderByClause("jointime desc");
        //开始分页
        PageHelper.startPage(page, rows);
        List<WhgUsrBacklist> typeList = this.whgUsrBacklistMapper.selectByExample(example);
        return new PageInfo<>(typeList);
    }

    /**
     * 取消黑名单
     * @param id
     */
    public void t_cancel(String id, String userid)throws Exception {
        WhgUsrBacklist black = whgUsrBacklistMapper.selectByPrimaryKey(id);
        black.setState(0);
        whgUsrBacklistMapper.updateByPrimaryKeySelective(black);
        Example example = new Example(WhgActOrder.class);
        example.createCriteria().andEqualTo("userid",userid).andEqualTo("ticketstatus",3);
        List<WhgActOrder> order = whgActOrderMapper.selectByExample(example);
        if(order.size() >0){
            for(int i=0; i<order.size(); i++){
                order.get(i).setOrderisvalid(3);
                whgActOrderMapper.updateByPrimaryKey(order.get(i));
            }
        }

    }
}
