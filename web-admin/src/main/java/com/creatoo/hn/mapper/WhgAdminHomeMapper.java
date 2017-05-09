package com.creatoo.hn.mapper;

import com.creatoo.hn.model.WhgAdminHome;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface WhgAdminHomeMapper extends Mapper<WhgAdminHome> {

    List<WhgAdminHome> homeCount();

    /**
     * 培训根据文化馆分类
     * @return
     */
    List<WhgAdminHome> traGroupByCult();

    /**
     * 培训根据艺术类型分类
     * @return
     */
    List<WhgAdminHome> traGroupByArt();
    /**
     * 活动根据艺术类型分类
     * @return
     */
    List<WhgAdminHome> actGroupByArt();
}