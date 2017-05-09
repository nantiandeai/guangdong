package com.creatoo.hn.utils;

import com.creatoo.hn.services.comm.CommPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * 上传文件时的一些辅助方法
 * @author rbg
 *
 */
public class UploadUtil {
	/**
	 * 获得上传文件的新路径
	 * @param oldFileName 上传的文件名
	 * @param newFileName 新的文件名
	 * @param modeType 模块类型:train/art/zx/activity
	 * @param fileType 文件类型:archive/picture/video
	 * @param now 日期
	 * @return 新的路径
	 */
	public static String getUploadFilePath(String oldFileName, String newFileName, String modeType, String fileType, Date now){
		//当前日期
		String yyyyMMdd = new java.text.SimpleDateFormat("yyyyMMdd").format(now);
		String yyyyMM = new java.text.SimpleDateFormat("yyyyMM").format(now);
		String yyyy = new java.text.SimpleDateFormat("yyyy").format(now);
		
		//
		//如培训图片保存路径:/upload/train/picture/yyyy/yyyyMM/yyyyMMdd/*.jpg
		String extname = oldFileName.substring(oldFileName.lastIndexOf("."));
		String imgname = newFileName+extname;
		String imgPath = modeType+File.separator+fileType+File.separator+yyyy+File.separator+yyyyMM+File.separator+yyyyMMdd+File.separator+imgname;
		
		return imgPath;
	}
	
	/**
	 * 通过根路径和生成的文件路径获得前端可访问的Url
	 * @param rootPath 根路径
	 * @param filePath 生成的文件路径
	 * @return 前端可访问的Url
	 */
	public static String getUploadFileUrl(String rootPath, String filePath){
		return filePath = "/"+filePath.replaceAll("\\"+File.separator, "/");
		//return filePath = "upload/"+filePath.replaceAll("\\"+File.separator, "/");
	}
	
	/**
	 * 根据根路径和数据库保存的路径获是上传文件的真实路径
	 * @param rootPath 根路径
	 * @param fileUrl 数据库保存的路径
	 * @return
	 */
	public static String getUploadFileDelPath(String rootPath, String fileUrl){
		//rootPath = rootPath.replaceFirst("upload", "");
		/*int idx = rootPath.lastIndexOf("upload");
		if(idx > -1){
			rootPath = rootPath.substring(0, idx);
		}*/
		while(rootPath.endsWith(File.separator)){
			rootPath = rootPath.substring(0, rootPath.length()-1);
		}
		return fileUrl = rootPath+File.separator+fileUrl.replaceAll("/", "\\" + File.separator);
	}
	
	/**
	 * 根据上传的根路径和数据库保存的URl,删除指定文件
	 * @param rootPath 上传的根路径
	 * @param fileUrl 数据库保存的URl
	 */
	public static void delUploadFile(String rootPath, String fileUrl){
		if (fileUrl != null && !"".equals(fileUrl)) {
			//rootPath = rootPath.replaceFirst("upload", "");
			int idx = rootPath.lastIndexOf("upload");
			if(idx > -1){
				rootPath = rootPath.substring(0, idx);
			}
			while(rootPath.endsWith(File.separator)){
				rootPath = rootPath.substring(0, rootPath.length()-1);
			}
			fileUrl = fileUrl.replaceAll("/", "\\" + File.separator);

			File _file = new File(rootPath, fileUrl);
			if (_file.exists() && _file.isFile()) {
				_file.delete();
			}
		}
	}
	
	/**
	 * 创建上传文件
	 * @param rootPath 上传的根路径
	 * @param filePath 文件路径
	 * @return
	 */
	public static File createUploadFile(String rootPath, String filePath)throws Exception{
		File file = new File(rootPath, filePath);
		File pfile = file.getParentFile();
		if(!pfile.exists()){
			pfile.mkdirs();
		}
		if(!file.exists()){
			file.createNewFile();
		}
		return file;
	}
	
	/**
	 * 获取上传文件的根目录
	 * @param req 请求对象
	 * @return
	 */
	public static String getUploadPath(HttpServletRequest req){
		String uploadPath = "";
		try {
			//uploadPath = WhConstance.SYS_UPLOAD_PATH;
			WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(req.getServletContext());
			CommPropertiesService commPropertiesService = (CommPropertiesService)context.getBean("commPropertiesService");
			uploadPath = commPropertiesService.getUploadLocalAddr();
			//uploadPath = req.getSession().getServletContext().getRealPath("/upload");
		} catch (Exception e) {
			uploadPath = req.getSession().getServletContext().getRealPath("/upload");
			//uploadPath = System.getProperty("user.home");
		}
		return uploadPath;
	}
}
