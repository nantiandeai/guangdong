package com.creatoo.hn.services.home.agdwhhd;

import com.creatoo.hn.ext.emun.EnumBizState;
import com.creatoo.hn.ext.emun.EnumDelState;
import com.creatoo.hn.ext.emun.EnumState;
import com.creatoo.hn.mapper.WhgActActivityMapper;
import com.creatoo.hn.mapper.WhgComResourceMapper;
import com.creatoo.hn.mapper.WhgYwiWhppMapper;
import com.creatoo.hn.model.WhgActActivity;
import com.creatoo.hn.model.WhgComResource;
import com.creatoo.hn.model.WhgSysCult;
import com.creatoo.hn.model.WhgYwiWhpp;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文化品牌服务类
 * Created by wangxl on 2017/4/8.
 */
@Service
public class WhppService {
    /**
     * 日志控制器
     */
    Logger log = Logger.getLogger(this.getClass());

    /**
     * 文化品牌DAO
     */
    @Autowired
    private WhgYwiWhppMapper whgYwiWhppMapper;

    /**
     * 文化活动DAO
     */
    @Autowired
    private WhgActActivityMapper whgActActivityMapper;

    /**
     * 图片视频音频DAO
     */
    private WhgComResourceMapper whgComResourceMapper;

    /**
     * 查找图片视频音频资源
     * //@param type 1图片/2视频/3音频
     * @param refid 文化品牌ID
     * @return 资源列表
     * @throws Exception
     */
    public Map<String,List<WhgComResource>> findCommResource(String refid)throws Exception{
        Map<String,List<WhgComResource>> map = new HashMap<String,List<WhgComResource>>();
        List<WhgComResource> list1 = new ArrayList<WhgComResource>();
        List<WhgComResource> list2 = new ArrayList<WhgComResource>();
        List<WhgComResource> list3 = new ArrayList<WhgComResource>();

        Example example = new Example(WhgComResource.class);
        example.createCriteria().andEqualTo("reftype", "13")
                .andEqualTo("refid", refid);
        List<WhgComResource> list = this.whgComResourceMapper.selectByExample(example);
        if(list != null){
            for(WhgComResource r : list){
                if("1".equals(r.getEnttype())){
                    list1.add(r);
                }else if("2".equals(r.getEnttype())){
                    list2.add(r);
                }else if("2".equals(r.getEnttype())){
                    list3.add(r);
                }
            }
        }
        map.put("img", list1);
        map.put("video", list2);
        map.put("audio", list3);
        return map;
    }

    /**
     * 分页查询文化品牌列表
     *
     * @param request 请求对象
     * @param whpp    查询条件
     * @return 文化品牌分页列表
     * @throws Exception
     */
    public PageInfo<WhgYwiWhpp> t_search4p(HttpServletRequest request, WhgYwiWhpp whpp) throws Exception {
        //分页信息
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);
        int page = ReqParamsUtil.getPage(request, 1);
        int rows = ReqParamsUtil.getRows(request, 10);

        //搜索条件
        Example example = new Example(WhgYwiWhpp.class);
        Example.Criteria c = example.createCriteria();
        c.andEqualTo("state", EnumState.STATE_YES.getValue());
        c.andEqualTo("delstate", EnumDelState.STATE_DEL_NO.getValue());

        //名称条件
        if (whpp != null && whpp.getName() != null) {
            c.andLike("name", "%" + whpp.getName() + "%");
            whpp.setName(null);
        }

        //其它条件
        c.andEqualTo(whpp);

        //排序
        if (paramMap.containsKey("sort") && paramMap.get("sort") != null) {
            StringBuffer sb = new StringBuffer((String) paramMap.get("sort"));
            if (paramMap.containsKey("order") && paramMap.get("order") != null) {
                sb.append(" ").append(paramMap.get("order"));
            }
            example.setOrderByClause(sb.toString());
        } else {
            example.setOrderByClause("statemdfdate desc");
        }

        //分页查询
        PageHelper.startPage(page, rows);
        List<WhgYwiWhpp> list = this.whgYwiWhppMapper.selectByExample(example);
        PageInfo<WhgYwiWhpp> pageInfo = new PageInfo<WhgYwiWhpp>(list);
        return pageInfo;
    }

    /**
     * 查询文化品牌详情
     *
     * @param id 文化品牌ID
     * @return 返回文化品牌详情
     * @throws Exception
     */
    public WhgYwiWhpp getWhpp(String id) throws Exception {
        WhgYwiWhpp whpp = new WhgYwiWhpp();
        whpp.setId(id);
        return this.whgYwiWhppMapper.selectOne(whpp);
    }

    /**
     * 查询文化品牌下的活动列表
     * @param brandid 品牌ID
     * @return 返回活动列表
     * @throws Exception
     */
    public List<WhgActActivity> findActActivity(String brandid) throws Exception {
        Example example = new Example(WhgActActivity.class);
        Example.Criteria c = example.createCriteria();
        c.andEqualTo("state", EnumBizState.STATE_PUB.getValue());
        c.andEqualTo("delstate", EnumDelState.STATE_DEL_NO.getValue());
        c.andLike("ebrand", brandid);
        example.setOrderByClause("statemdfdate desc");
        return this.whgActActivityMapper.selectByExample(example);
    }
}
