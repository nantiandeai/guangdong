package com.creatoo.hn.mapper;

import com.creatoo.hn.model.WhgPubInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface WhgPubInfoMapper extends Mapper<WhgPubInfo> {
    /**
     * 查询资讯公告信息
     * @param paramMap
     * @return
     */
    List<Map> t_srchList4p(Map<String, Object> paramMap);

    /**
     * 查询单个资讯公告信息
     * @param id
     * @return
     */
    Map t_srchOne(String id);


    /**
     * 查询可选择的资讯公告信息
     * @param paramMap
     * @return
     */
    List<Map> t_srchcolinfoList4p(Map<String, Object> paramMap);
}