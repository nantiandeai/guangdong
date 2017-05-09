package com.creatoo.hn.mapper.home;

import java.util.List;
import java.util.Map;

/**
 * Created by rbg on 2017/4/5.
 */
public interface CrtCgfwMapper {

    /**
     * 查询场馆列表
     * @param params
     * @return
     */
    List selectVenList(Map params);

    /**
     * 查询用户场馆预订列表
     * @param params
     * @return
     */
    List selectUserVenOrderList(Map params);
}
