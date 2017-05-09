package com.creatoo.hn.utils;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.creatoo.hn.services.comm.CommPropertiesService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 阿里云OSS帮助方法
 * @author wangxl
 *
 */
@Component
public class AliyunOssUtil {
	/**
	 * 配置属性
	 */
	private static CommPropertiesService commPropertiesService;

	/**
	 * 强制注入静态属性
	 * @param commPropertiesService
	 */
	@Autowired(required = true)
	private void setCommPropertiesService(CommPropertiesService commPropertiesService){
		AliyunOssUtil.commPropertiesService = commPropertiesService;
	}
	
	/**
	 * 获取上传文件的域名
	 * @return
	 */
	public static String getEndpoint(){
		return AliyunOssUtil.commPropertiesService.getUploadOssEndpoint();
		//return WhConstance.getSysProperty("Aliyun.oss.domain", "http://oss-cn-shenzhen.aliyuncs.com");
	}
	
	/**
	 * 获取上传文件的AccessKeyId
	 * @return
	 */
	public static String getAccessKeyId(){
		return AliyunOssUtil.commPropertiesService.getUploadOssAccessKeyId();
		//return WhConstance.getSysProperty("Aliyun.accessKeyId", "LTAIuwerek5QXVEu");
	}
	
	/**
	 * 获取上传文件的AccessKeySecret
	 * @return
	 */
	public static String getAccessKeySecret(){
		return AliyunOssUtil.commPropertiesService.getUploadOssAccessKeySecret();
		//return WhConstance.getSysProperty("Aliyun.accessKeySecret", "tUwAa6rw6A8DFT6aoW3VCnxYoucoHK");
	}
	
	/**
	 * 获取上传文件的BucketName
	 * @return
	 */
	public static String getBucketName(){
		return AliyunOssUtil.commPropertiesService.getUploadOssBucketName();
		//return WhConstance.getSysProperty("Aliyun.oss.bucketName", "szwhg-gds");
	}
	
	/**
	 * 检查bucketName是存在
	 * @param bucketName
	 * @return
	 */
	public static boolean bucketNameExist(String bucketName){
		boolean exists = false;
		OSSClient ossClient = null;
		try {
			String endpoint = AliyunOssUtil.getEndpoint();// endpoint以杭州为例，其它region请按实际情况填写
			String accessKeyId = AliyunOssUtil.getAccessKeyId();// accessKey请登录https://ak-console.aliyun.com/#/查看
			String accessKeySecret = AliyunOssUtil.getAccessKeySecret();
			//String bucketName = WhConstance.getSysProperty("Aliyun.oss.bucketName", "szwhg-gds");
			// 创建OSSClient实例
			ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

			exists = ossClient.doesBucketExist(bucketName);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(ossClient != null){
				ossClient.shutdown();
			}
		}
		return exists;
	}
	
	/** 获取上传对象的URL
	 * @param key 上传对象的KEY
	 * @return URL 字符串
	 */
	public static String getUrl(String key){
		String urlStr = null;
		OSSClient ossClient = null;
		try {
			String endpoint = AliyunOssUtil.getEndpoint();// endpoint以杭州为例，其它region请按实际情况填写
			String accessKeyId = AliyunOssUtil.getAccessKeyId();// accessKey请登录https://ak-console.aliyun.com/#/查看
			String accessKeySecret = AliyunOssUtil.getAccessKeySecret();
			String bucketName = AliyunOssUtil.getBucketName();

			// 设置URL过期时间为1小时
			Date expiration = new Date(new Date().getTime() + 3600 * 1000);

			// 生成URL
			ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
			urlStr = url.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(ossClient != null){
				ossClient.shutdown();
			}
		}
		return urlStr;
	}

	/**
	 * 获取指定目录下的资源文件
	 * @param dir
	 * @return
	 */
	public static List<Map<String, Object>> listAllFile4Dir(String dir){
		List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list = AliyunOssUtil.listAllObject(dir, null);
		if(list != null){
			for(Map<String, Object> _map : list){
				if("dir".equals(String.valueOf(_map.get("etag"))) || "predir".equals(String.valueOf(_map.get("etag")))){
					//目录不需要
				}else{
					String addr = (String) _map.get("addr");
					addr = addr.substring(0, addr.indexOf("?"));
					_map.put("addr", addr);
					resultMap.add(_map);
				}
			}
		}
		return resultMap;
	}

	/**
	 * 查找所有的目录
	 * @return 返回所有目录
	 */
	public static List<Map<String, Object>> listAllDir(){
		List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
		Map<String, Object> __map = new HashMap<String, Object>();
		__map.put("id", "root");
		__map.put("text", "根目录");
		resultMap.add(__map);

		OSSClient ossClient = null;
		try {
			//ossClient
			String endpoint = AliyunOssUtil.getEndpoint();// endpoint以杭州为例，其它region请按实际情况填写
			String accessKeyId = AliyunOssUtil.getAccessKeyId();// accessKey请登录https://ak-console.aliyun.com/#/查看
			String accessKeySecret = AliyunOssUtil.getAccessKeySecret();
			String bucketName = AliyunOssUtil.getBucketName();
			ClientConfiguration conf = new ClientConfiguration();
			conf.setIdleConnectionTime(1000);
			ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret, conf);// 创建OSSClient实例

			//查询所有文件
			ObjectListing objectListing = null;
			String nextMarker = null;
			int maxKeys = 200;
			do {
				objectListing = ossClient.listObjects(new ListObjectsRequest(bucketName).withMarker(nextMarker).withMaxKeys(maxKeys));
				if(objectListing != null){
					List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
					for (OSSObjectSummary s : sums) {
						String _key = s.getKey();
						////if(_key.startsWith("system")){
							System.out.println("_key:"+_key);
						//}
						if(!_key.endsWith("/")){//过滤文件
							continue;
						}
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", _key);
						map.put("text", _key);
						resultMap.add(map);
					}
				}
				nextMarker = objectListing.getNextMarker();
			} while (objectListing.isTruncated());
		} catch (Exception e) {
			Logger.getLogger(AliyunOssUtil.class.getName()).error(e.getMessage(), e);
		} finally{
			if(ossClient != null){
				ossClient.shutdown();
			}
		}
		return resultMap;
	}
	
	/**
	 * 查询所有视频文件
	 * @return 全部文件列表
	 */
	public static List<Map<String, Object>> listAllObject4Data(){
		List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
		OSSClient ossClient = null;
		try {
			//ossClient 
			String endpoint = AliyunOssUtil.getEndpoint();// endpoint以杭州为例，其它region请按实际情况填写
			String accessKeyId = AliyunOssUtil.getAccessKeyId();// accessKey请登录https://ak-console.aliyun.com/#/查看
			String accessKeySecret = AliyunOssUtil.getAccessKeySecret();
			String bucketName = AliyunOssUtil.getBucketName();
			ClientConfiguration conf = new ClientConfiguration();
			conf.setIdleConnectionTime(1000);
			ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret, conf);// 创建OSSClient实例
			
			//查询所有文件
			ObjectListing objectListing = null;
			String nextMarker = null;
			int maxKeys = 200;
			do {
				objectListing = ossClient.listObjects(new ListObjectsRequest(bucketName).withMarker(nextMarker).withMaxKeys(maxKeys));
				if(objectListing != null){
					List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
					for (OSSObjectSummary s : sums) {
						String _key = s.getKey();
						if(_key.endsWith("/")){
							continue;
						}
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("etag", s.getETag()); 
						map.put("key", _key); 
						map.put("lastmodified", s.getLastModified()); 
						map.put("size", s.getSize()); 
						URL url = ossClient.generatePresignedUrl(bucketName, s.getKey(), new Date(new Date().getTime() + 3600 * 1000));
						String urlStr = url.toString();
						urlStr = urlStr.substring(0, urlStr.indexOf("?"));
						map.put("addr", urlStr); 
						resultMap.add(map);
					}
				}
				nextMarker = objectListing.getNextMarker();
			} while (objectListing.isTruncated());
		} catch (Exception e) {
			Logger.getLogger(AliyunOssUtil.class.getName()).error(e.getMessage(), e);
		} finally{
			if(ossClient != null){
				ossClient.shutdown();
			}
		}
		return resultMap;
	}
	
	/**
	 * 列出所有Keys
	 * @param prefix
	 * @return
	 */
	public static List<String> listKeys(String prefix){
		List<String> resultMap = new ArrayList<String>();
		
		//
		OSSClient ossClient = null;
		try {
			//ossClient 
			String endpoint = AliyunOssUtil.getEndpoint();// endpoint以杭州为例，其它region请按实际情况填写
			String accessKeyId = AliyunOssUtil.getAccessKeyId();// accessKey请登录https://ak-console.aliyun.com/#/查看
			String accessKeySecret = AliyunOssUtil.getAccessKeySecret();
			String bucketName = AliyunOssUtil.getBucketName();
			ClientConfiguration conf = new ClientConfiguration();
			conf.setIdleConnectionTime(1000);
			ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret, conf);// 创建OSSClient实例
			
			ObjectListing objectListing = null;
			String nextMarker = null;
			int maxKeys = 200;
			do {
				//不带条件时列出所有的资源
				ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
				listObjectsRequest.setPrefix(prefix);
				listObjectsRequest.setMarker(nextMarker);
				listObjectsRequest.setMaxKeys(maxKeys);

				objectListing = ossClient.listObjects(listObjectsRequest);
				if(objectListing != null){
					//查询文件
					List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
					for (OSSObjectSummary s : sums) {
						String _key = s.getKey();
						resultMap.add(_key);
					}
				}
				nextMarker = objectListing.getNextMarker();
			} while (objectListing.isTruncated());
			
			
			Collections.sort(resultMap, new Comparator<String>(){
				@Override
				public int compare(String o1, String o2) {
					// TODO Auto-generated method stub
					return o2.length()-o1.length();
				}
				
			});
		} catch (Exception e) {
			Logger.getLogger(AliyunOssUtil.class.getName()).error(e.getMessage(), e);
		} finally{
			if(ossClient != null){
				ossClient.shutdown();
			}
		}
		return resultMap;
	}
	
	/**
	 * 查询指定目录下的文件(包含子目录)
	 * @param prefix
	 * @return
	 */
	public static List<Map<String, Object>> listAllObject(String dir, String prefix){
		List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
		
		//
		OSSClient ossClient = null;
		try {
			//ossClient 
			String endpoint = AliyunOssUtil.getEndpoint();// endpoint以杭州为例，其它region请按实际情况填写
			String accessKeyId = AliyunOssUtil.getAccessKeyId();// accessKey请登录https://ak-console.aliyun.com/#/查看
			String accessKeySecret = AliyunOssUtil.getAccessKeySecret();
			String bucketName = AliyunOssUtil.getBucketName();
			ClientConfiguration conf = new ClientConfiguration();
			conf.setIdleConnectionTime(1000);
			ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret, conf);// 创建OSSClient实例
			
			//
			if(dir == null || "".equals(dir) || "root".equals(dir)){
				dir = "";
				if(prefix == null || "".equals(prefix)){
					prefix = dir;
				}else if(prefix.endsWith("/")){
					prefix = prefix.substring(0, prefix.length()-1);
				}
			}else{
				if(!dir.endsWith("/")){
					dir = dir + "/";
				}
				if(prefix == null || "".equals(prefix)){
					prefix = dir;
				}else if(prefix.endsWith("/")){
					prefix = dir + prefix.substring(0, prefix.length()-1);
				}else{
					prefix = dir + prefix;
				}
			}
			
			
			//查询所有文件
			ObjectListing objectListing = null;
			String nextMarker = null;
			int maxKeys = 200;
			do {
				//不带条件时列出所有的资源
				ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
				listObjectsRequest.setDelimiter("/");
				listObjectsRequest.setPrefix(prefix);
				listObjectsRequest.setMarker(nextMarker);
				listObjectsRequest.setMaxKeys(maxKeys);

				objectListing = ossClient.listObjects(listObjectsRequest);
				if(objectListing != null){
					//查询文件
					List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
					boolean first = true;
					for (OSSObjectSummary s : sums) {
						String _key = s.getKey();
						boolean isDir = false;
						if(first){
							if(_key.endsWith("/")){
								isDir = true;
								_key = _key.substring(0, _key.length()-1);
								if(_key.indexOf("/") > -1){
									_key = _key.substring(0, _key.indexOf("/")+1);
								}else{
									//_key = ".";
								}
							}
						}
						
						Map<String, Object> map = new HashMap<String, Object>();
						if(isDir){
							map.put("etag", "predir"); 
						}else{
							map.put("etag", s.getETag()); 
						}
						map.put("key", _key); 
						if(isDir){
							map.put("lastmodified", ""); 
						}else{
							map.put("lastmodified", s.getLastModified()); 
						}
						
						long _size = s.getSize();
						String _unitSize = "";
						if(_size < 1){
							_unitSize = "";
						}else if(_size > 1024){
							BigDecimal _sizeBD = new BigDecimal(_size).divide(new BigDecimal(1024), 2, BigDecimal.ROUND_HALF_UP);  
							if(_sizeBD.longValue() > 1024){
								_sizeBD = _sizeBD.divide(new BigDecimal(1024), 2, BigDecimal.ROUND_HALF_UP); 
								if(_sizeBD.longValue() > 1024){
									_sizeBD = new BigDecimal(_size).divide(new BigDecimal(1024), 2, BigDecimal.ROUND_HALF_UP); 
									_unitSize = _sizeBD.toString()+"GB";
								}else{
									_unitSize = _sizeBD.toString()+"MB";
								}
							}else{
								_unitSize = _sizeBD.toString()+"KB";
							}
						}else{
							_unitSize = _size+"Bit";
						}
						map.put("size", _unitSize); 
						
						
						URL url = ossClient.generatePresignedUrl(bucketName, s.getKey(), new Date(new Date().getTime() + 3600 * 1000));
						map.put("addr", url.toString()); 
						
						resultMap.add(map);
						first = false;
					}
					
					//查询目录
					for (String commonPrefix : objectListing.getCommonPrefixes()) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("etag", "dir"); 
						map.put("key", commonPrefix); 
						map.put("lastmodified", ""); 
						map.put("size", ""); 
						map.put("addr", ""); 
						if("".equals(prefix)){
							resultMap.add(0,map);
						}else{
							if(resultMap.size() > 0){
								resultMap.add(1,map);
							}else{
								resultMap.add(0,map);
							}
						}
					}
				}
				nextMarker = objectListing.getNextMarker();
			} while (objectListing.isTruncated());
		} catch (Exception e) {
			Logger.getLogger(AliyunOssUtil.class.getName()).error(e.getMessage(), e);
		} finally{
			if(ossClient != null){
				ossClient.shutdown();
			}
		}
		return resultMap;
	}
	
	/**
	 * 上传文件流
	 * @param inputStream 文件流
	 * @param key 上传对象的KEY
	 */
	public static void uploadFile_file(InputStream inputStream, String key){
		OSSClient ossClient = null;
		
		try {
			String endpoint = AliyunOssUtil.getEndpoint();// endpoint以杭州为例，其它region请按实际情况填写
			String accessKeyId = AliyunOssUtil.getAccessKeyId();// accessKey请登录https://ak-console.aliyun.com/#/查看
			String accessKeySecret = AliyunOssUtil.getAccessKeySecret();
			String bucketName = AliyunOssUtil.getBucketName();
			
			// 创建OSSClient实例
			ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			
			// 上传文件流
			ossClient.putObject(bucketName, key, inputStream);
		} catch (OSSException e) {
			e.printStackTrace();
		} finally {
			if(ossClient != null){
				ossClient.shutdown();
			}
		}
	}
	
	
	/**
	 * 分片上传大的文件
	 * @param file 文件流
	 * @param key 上传对象的KEY
	 */
	public static void uploadFile_fenpian(MultipartFile file, String key, Long size){
		OSSClient ossClient = null;
		try {			
			ExecutorService executorService = Executors.newFixedThreadPool(10);
			List<PartETag> partETags = Collections.synchronizedList(new ArrayList<PartETag>());
			
			//1. 开始分片
			String endpoint = AliyunOssUtil.getEndpoint();// endpoint以杭州为例，其它region请按实际情况填写
			String accessKeyId = AliyunOssUtil.getAccessKeyId();// accessKey请登录https://ak-console.aliyun.com/#/查看
			String accessKeySecret = AliyunOssUtil.getAccessKeySecret();
			String bucketName = AliyunOssUtil.getBucketName();
			
			ClientConfiguration conf = new ClientConfiguration();
	        conf.setIdleConnectionTime(1000);
			ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret, conf);// 创建OSSClient实例
			InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, key);
			InitiateMultipartUploadResult result = ossClient.initiateMultipartUpload(request);
			String uploadId = result.getUploadId();
			
			final long partSize = 1024 * 1024L;//5 * 1024 * 1024L;   // 5MB
			long fileLength = size;//文件大小
			if(size == null){
				fileLength = file.getSize();
			}
            int partCount = (int) (fileLength / partSize);
            if (fileLength % partSize != 0) {
                partCount++;
            }
            if (partCount > 10000) {
                throw new RuntimeException("Total parts count should not exceed 10000");
            }
            
            /*
             * Upload multiparts to your bucket
             */
            for (int i = 0; i < partCount; i++) {
                long startPos = i * partSize;
                long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
                executorService.execute(new PartUpload(partETags, ossClient, key, file, startPos, curPartSize, i + 1, uploadId));
            }
            
            executorService.shutdown();
            while (!executorService.isTerminated()) {
                try {
                    executorService.awaitTermination(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            if (partETags.size() != partCount) {
                throw new IllegalStateException("Upload multiparts fail due to some parts are not finished yet");
            } else {
                System.out.println("Succeed to complete multiparts into an object named " + key + "\n");
            }		
			
			//3. 完成分片上传
			Collections.sort(partETags, new Comparator<PartETag>() {
	            @Override
	            public int compare(PartETag p1, PartETag p2) {
	                return p1.getPartNumber() - p2.getPartNumber();
	            }
	        });
			
			CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(bucketName, key, uploadId, partETags);
			ossClient.completeMultipartUpload(completeMultipartUploadRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(ossClient != null){
				ossClient.shutdown();
			}
		}
	}
	

	/**
	 * 创建文件夹
	 * @param pdir 父目录
	 * @param dir 子目录
	 */
	public static void createDir(String pdir, String dir){
		OSSClient ossClient = null;
		try {
			String endpoint = AliyunOssUtil.getEndpoint();// endpoint以杭州为例，其它region请按实际情况填写
			String accessKeyId = AliyunOssUtil.getAccessKeyId();// accessKey请登录https://ak-console.aliyun.com/#/查看
			String accessKeySecret = AliyunOssUtil.getAccessKeySecret();
			String bucketName = AliyunOssUtil.getBucketName();

			// 设置URL过期时间为1小时
			Date expiration = new Date(new Date().getTime() + 3600 * 1000);

			// 生成URL
			ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			
			if(pdir == null || "root".equals(pdir)){
				pdir = "";
			}else if(!"".equals(pdir) && !pdir.endsWith("/")){
				pdir = pdir + "/";
			}
			if(dir == null || "".equals(dir)){
				return;
			}
			if(!dir.endsWith("/")){
				dir = dir + "/";
			}
			
			
			String keySuffixWithSlash = pdir + dir;//"MyObjectKey/";
			ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(ossClient != null){
				ossClient.shutdown();
			}
		}
	} 
	
	public static void main(String[] args) {
		try {
			
//			boolean exist = AliyunOssUtil.bucketNameExist(AliyunOssUtil.getBucketName());
//			System.out.println( "exist:"+exist );
//			File f = new File("D:\\temp\\abc.jpg");
//			InputStream inputStream = new FileInputStream(f);
//			
//			System.out.println( "1 size:"+inputStream.available() );
//			System.out.println( "2 size:"+f.length() );
//			
//			String key = "abc9.jpg";
////			AliyunOssUtil.uploadFile_file(inputStream, key);
//			//AliyunOssUtil.uploadFile_fenpian(inputStream, key, (long)inputStream.available());
////			
//			String url = AliyunOssUtil.getUrl(key);
//			System.out.println( "url:"+url );
//			List<Map<String, Object>> resultMap = AliyunOssUtil.listAllObject4Data();
			//创建文件
//			AliyunOssUtil.createDir("abc/", "wxldir");
			
//			List<Map<String, Object>> resultMap = AliyunOssUtil.listAllObject("test_a1/", "");
//			System.out.println( "size:"+resultMap.size() );
//			for(Map<String, Object> map : resultMap){
//				String key = (String)map.get("key");
//				String etag = (String)map.get("etag");
//				System.out.println(etag+"->"+ key );
//			}
			
			List<String> keys = AliyunOssUtil.listKeys("abc/");
			//System.out.println( keys );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
