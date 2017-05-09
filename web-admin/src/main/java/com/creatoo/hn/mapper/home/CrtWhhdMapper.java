package com.creatoo.hn.mapper.home;

import java.util.List;
import java.util.Map;

import com.creatoo.hn.model.WhgActOrder;

import tk.mybatis.mapper.common.Mapper;

/**
 * Created by rbg on 2017/4/5.
 */
public interface CrtWhhdMapper extends Mapper<WhgActOrder>{

    public List<Map> findSeat4EventId(Map params);
}
