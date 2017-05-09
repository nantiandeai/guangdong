package com.creatoo.hn.mapper.admin;

import java.util.List;
import java.util.Map;

/**
 * Created by rbg on 2017/3/23.
 */
public interface CrtWhgVenueMapper {

    /**
     * 活动室 后台管理列表 查询
     * @param room
     * @return
     */
    List<Map> srchListVenroom(Map room);
}
