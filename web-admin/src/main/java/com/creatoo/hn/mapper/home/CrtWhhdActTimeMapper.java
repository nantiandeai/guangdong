package com.creatoo.hn.mapper.home;

import java.util.List;
import java.util.Map;

import com.creatoo.hn.model.WhgActTime;

import tk.mybatis.mapper.common.Mapper;

/**
 * Created by rbg on 2017/4/5.
 */
public interface CrtWhhdActTimeMapper extends Mapper<WhgActTime>{

    public List<WhgActTime> findPlayDate4actId(Map params);
    
    
}
