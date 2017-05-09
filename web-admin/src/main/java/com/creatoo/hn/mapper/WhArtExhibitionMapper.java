package com.creatoo.hn.mapper;

import java.util.List;
import java.util.Map;

import com.creatoo.hn.model.WhArtExhibition;
import tk.mybatis.mapper.common.Mapper;

public interface WhArtExhibitionMapper extends Mapper<WhArtExhibition> {
	
	/**
	 * 查询文化展
	 * @param param
	 * @return
	 */
	public List<Map> selectcategory(Map<String,Object> param);
	
	/**
	 * 查询文化展详情
	 * @param param
	 * @return
	 */
	public List<Map> selectdetail(Map<String, Object> params);
	
	/**
	 * 查询精品资源
	 * @param param
	 * @return
	 */
	public List<Map> selectlist(Map<String, Object> params);
	/**
	 * 查询广告
	 * @param param
	 * @return
	 */
	public List<Map> selectadvlist(Map<String, Object> params);
	
	/**
	 * 查询字典区域
	 * @param param
	 * @return
	 */
	public List<Map> selectdistrict();
	
	/**
	 * 搜索
	 */
	public List<Map> searchContent(Map<String, Object> params);
	
	/**
	 * 查询热门培训驿站
	 * @param param
	 * @return
	 */
	public List<Map> selectadBanner(Map<String, Object> params);
	
}