package com.creatoo.hn.mapper;

import java.util.List;
import java.util.Map;

import com.creatoo.hn.model.WhTrain;
import tk.mybatis.mapper.common.Mapper;

public interface WhTrainMapper extends Mapper<WhTrain> {

	/**
	 * 查询所有的培训
	 * @param param
	 * @return
	 */
	public List<Map> selectTrain(Map<String, Object> param);

	/**
	 * 判断是否能够取消发布
	 * @param traid
	 * @return
	 */
	public int isPublish(String traid);

	/**
	 * 判断是否能够送审
	 * @param traid
	 * @return
	 */
	public int isCanCheck(String traid);

	/**
	 * 查询已报名人数
	 * @param traid
	 * @return
	 */
	public int selectEnrollCount(String traid);

	public List<Map> selOldTra(Map<String, Object> param);

	public List<Map> selOldVen(Map<String, Object> param);

	public List<Map> selOldAct(Map<String, Object> param);

	/**
	 * 移动端接口查培训列表
	 * @param params
	 * @return
	 */
	public List<Map> selTrainList(Map<String, Object> params);

	/**
	 * 接口查询培训
	 * @return
	 */
	public List<Map> selTrainDetail(String itemId);

	/**
	 * 查询用户是否已经收藏
	 * @param id
	 */
	public int selCollectionCount(Map<String, Object> params);

	/**
	 * 接口查询我的培训
	 * @param params
	 * @return
	 */
	public List<Map> selMyEnroll(Map<String, Object> params);

	/**
	 * 接口查询我的场馆
	 * @param params
	 * @return
	 */
	public List<Map> selMyVenue(Map<String, Object> params);
	
}