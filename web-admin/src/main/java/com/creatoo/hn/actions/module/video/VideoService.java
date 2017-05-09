package com.creatoo.hn.actions.module.video;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.AliyunOssUtil;

/**
 * 数字资源服务类
 * @author wangxl
 * @version 20161108
 */
@Service
public class VideoService {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public CommService commService;
	
	/**
	 * 删除数字资源
	 * @param keys
	 * @throws Exception
	 */
	public void delete(String keys)throws Exception{
		OSSClient ossClient = null;
		try {
			// 创建OSSClient实例
			String endpoint = AliyunOssUtil.getEndpoint();// endpoint以杭州为例，其它region请按实际情况填写
			String accessKeyId = AliyunOssUtil.getAccessKeyId();// accessKey请登录https://ak-console.aliyun.com/#/查看
			String accessKeySecret = AliyunOssUtil.getAccessKeySecret();
			String bucketName = AliyunOssUtil.getBucketName();
			ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			
			List<String> _keys = new ArrayList<String>();
			if(keys != null){
				String[] keyArr = keys.split(",");
				for(String key : keyArr){
					if(!key.isEmpty()){
						if(key.endsWith("/")){
							_keys.addAll(AliyunOssUtil.listKeys(key));
						}else{
							_keys.add(key);
						}
					}
				}
			}
			
			ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(_keys));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(ossClient != null){
				ossClient.shutdown();
			}
		}
	}
	
	/**
	 * 分页查询数字资源
	 * @param param 参数条件
	 * @return 分页内容
	 * @throws Exception
	 */
	public Map<String, Object> srchPagging(Map<String, Object> param)throws Exception{
		List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
		if(param.containsKey("srchFile") && "1".equals(param.get("srchFile"))){//查询指定目录下的资源
			String dir = (String)param.get("dir");
			if(dir == null || dir.isEmpty()){
				dir = "root";
			}
			resultMap = AliyunOssUtil.listAllFile4Dir(dir);
		}else if(param.containsKey("srchDir") && "1".equals(param.get("srchDir"))){//查询所有目录
			resultMap = AliyunOssUtil.listAllDir();
		}else if(param.containsKey("dir")){//查询指定目录下的资源-后台视频管理列表查询页用到
			String dir = (String)param.get("dir");
			String prefix = (String)param.get("keyname");
			resultMap = AliyunOssUtil.listAllObject(dir, prefix);
		}else{//查询所有资源-不含目录
			resultMap = AliyunOssUtil.listAllObject4Data();
		}
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", resultMap.size());
        rtnMap.put("rows", resultMap);
		return rtnMap;
	};
}
