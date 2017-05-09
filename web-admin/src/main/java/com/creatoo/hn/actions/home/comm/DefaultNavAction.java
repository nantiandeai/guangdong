package com.creatoo.hn.actions.home.comm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.UploadUtil;

/**
 * 前端页面的默认导航处理
 * @author wangxl
 *
 */
@RestController
public class DefaultNavAction {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public CommService commservice;
	
	/**
	 * 图片类型
	 */
	public static Map<String, String> MIMETYPE = new HashMap<String, String>(){
		private static final long serialVersionUID = 1L;
		{
			put("jpe", "image/jpeg");
			put("jpeg", "image/jpeg");
			put("jpg", "image/jpeg");
			put("gif", "image/gif");
			put("png", "image/png");
		}
	};
	

	/**
	 * 宣传栏
	 * @param nav
	 * @return
	 */
	@RequestMapping("/xuanchuan/updating")
	public ModelAndView xuanchuan_updating(){
		ModelAndView view = new ModelAndView( "home/comm/update/xuanchuan/update" );
		return view;
	}
	
	/**
	 * 活动预约
	 * @param nav
	 * @return
	 */
	@RequestMapping("/event/updating")
	public ModelAndView event_updating(){
		ModelAndView view = new ModelAndView( "home/comm/update/event/update" );
		return view;
	}
	
	/**
	 * 场馆预定
	 * @param nav
	 * @return
	 */
	@RequestMapping("/changguan/updating")
	public ModelAndView changguan_updating(){
		ModelAndView view = new ModelAndView( "home/comm/update/changguan/update" );
		return view;
	}
	
	/**
	 * 志愿服务
	 * @param nav
	 * @return
	 */
	@RequestMapping("/ziyuan/index")
	public ModelAndView ziyuan_updating(){
		//ModelAndView view = new ModelAndView( "home/comm/update/ziyuan/update" );
		ModelAndView view = new ModelAndView( "home/ziyuan/index" );
		return view;
	}
	
	/**
	 * 志愿服务
	 * @param nav
	 * @return
	 */
	@RequestMapping("/fenguan/index")
	public ModelAndView fenguan_updating(){
		ModelAndView view = new ModelAndView( "home/fenguan/index" );
		return view;
	}
	
	/**
	 * 请求资源路径/图片/文件
	 * @param path 路径
	 * @return
	 */
	@RequestMapping({"/upload/**"})
	public void resource(HttpServletRequest request, HttpServletResponse response){
		String uploadPath = UploadUtil.getUploadPath(request);
		
		BufferedInputStream bis = null;  
		BufferedOutputStream bos = null;  
		InputStream in = null;
		OutputStream os = null;
		try {
			//请求全路径
			String path = request.getRequestURL().toString();

			//基本路径
			StringBuffer sb = new StringBuffer(request.getScheme()+"://"+request.getServerName());
			if(request.getServerPort() == 80){
				sb.append(request.getContextPath());
			}else{
				sb.append(":").append(request.getServerPort()).append(request.getContextPath());
			}
			String basePath = sb.toString();
			if(!basePath.endsWith("/")){
				basePath += "/";
			}
			
			//相对路径
			String path1 = path.substring(basePath.length());
			if(path1.startsWith("upload/")){
				path1 = path1.substring(7);
			}
			
			//获得文件
			File file = new File(uploadPath, path1);
			
			//获得文件类型
			int idx = path1.lastIndexOf(".");
			if(idx > -1){
				String filetype = path1.substring(idx+1);
				filetype = MIMETYPE.get(filetype);
				response.setContentType(filetype); // 设置返回内容格式
				
			}
			if(!file.exists()){
				//static\assets\img\public\no-img.png
				String noImgFile = request.getServletContext().getRealPath("static/assets/img/public/no-img.png");
				file = new File(noImgFile);
			}
			in = new FileInputStream(file);   //用该文件创建一个输入流
			os = response.getOutputStream();  //创建输出流
			byte[] b = new byte[2048];  
			while( in.read(b)!= -1){ 
				os.write(b);     
			}
			os.flush();
			
//	        response.setContentType("text/html;charset=UTF-8");  //image/gif
//	        request.setCharacterEncoding("UTF-8");  
//	        long fileLength = file.length();  
//	        String realName = file.getName();
//	        response.setHeader("Content-disposition", "attachment; filename="+ new String(realName.getBytes("utf-8"), "ISO8859-1"));  
//	        response.setHeader("Content-Length", String.valueOf(fileLength));  
//	  
//	        bis = new BufferedInputStream(new FileInputStream(file));  
//	        bos = new BufferedOutputStream(response.getOutputStream());  
//	        byte[] buff = new byte[2048];  
//	        int bytesRead;  
//	        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
//	            bos.write(buff, 0, bytesRead);  
//	        }  
//	        bis.close();  
//	        bos.close(); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally{
			if(in != null){
				try {
					in.close();
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
			if(os != null){
				try {
					os.close();
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
			if(bis != null){
				try {
					bis.close();
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
			if(bos != null){
				try {
					bos.close();
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
	}
}
