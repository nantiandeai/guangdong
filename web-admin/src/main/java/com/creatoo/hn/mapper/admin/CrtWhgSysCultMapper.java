package com.creatoo.hn.mapper.admin;

import java.util.List;
import java.util.Map;

/**
 * 文化馆联盟查询
 * Created by wangxl on 2017/5/5.
 */
public interface CrtWhgSysCultMapper {
    /**
     * 查询文化馆
     * @param cult 条件
     * @return
     */
    List<Map> srchListCult(Map cult);
}
