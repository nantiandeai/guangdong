package com.creatoo.hn.mapper.home;

import java.util.Map;

import com.creatoo.hn.model.WhgUsrWeixin;

import tk.mybatis.mapper.common.Mapper;

/**
 * Created by rbg on 2017/4/5.
 */
public interface CrtWeiXinMapper extends Mapper<WhgUsrWeixin>{

    /**
     * 根据UserId查询用户对象
     * @param params
     * @return
     */
	public Map<String,Object> findUserInfo4UserId(Map params);

}
