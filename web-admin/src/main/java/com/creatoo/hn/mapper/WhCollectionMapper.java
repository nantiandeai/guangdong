package com.creatoo.hn.mapper;

import java.util.HashMap;
import java.util.List;

import com.creatoo.hn.model.WhCollection;
import tk.mybatis.mapper.common.Mapper;

public interface WhCollectionMapper extends Mapper<WhCollection> {
	/**
	 * 
	 * @return
	 */
	public List<HashMap> selectCollection(String userid);
	/**
	 * 
	 */
	public List<HashMap> selectTraitm(String userid);
	
	/**
	 * 我的活动收藏--查询
	 * @param cmuid
	 * @return
	 */
	public List<HashMap> selectMyActColle(String cmuid);
	
	/**
	 * 我的培训收藏--查询
	 * @param cmuid
	 * @return
	 */
	public List<HashMap> selectMyTraitmColle(String cmuid);
}