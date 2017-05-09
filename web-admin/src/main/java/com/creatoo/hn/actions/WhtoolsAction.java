package com.creatoo.hn.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creatoo.hn.utils.UploadUtil;
import com.creatoo.hn.utils.WhConstance;

@Controller
@RequestMapping("/whtools")
public class WhtoolsAction {
	Logger log = Logger.getLogger(this.getClass());
	
	/** 下载指定的存于数据库记录中的文件
	 * @param filePath
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/downFile")
	public Object downloadFile(String filePath, HttpServletRequest request, HttpServletResponse response){
		OutputStream sos = null;
		InputStream fis = null;
		try {
			String rootPath = UploadUtil.getUploadPath(request);
			String targetFilePath = UploadUtil.getUploadFileDelPath(rootPath, filePath);
			File targetFile = new File(targetFilePath);
			
			String fileName = targetFile.getName();
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(fileName,"UTF-8") + "\"");
			//response.addHeader("Content-Length", "" + FileUtils.get);
			response.setContentType("application/octet-stream;charset=UTF-8");

			sos = response.getOutputStream();
			
			fis = new FileInputStream(targetFile);
			byte[] filebyte = new byte[2048];
			int length;
			while ((length = fis.read(filebyte))> 0) {
				sos.write(filebyte, 0, length);
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				if (sos!=null) sos.close();
				if (fis!=null) sos.close();
			} catch (IOException e) {
				//...
			}
		}
		
		return null;
	}
	
	/** ajax取当前系统时间
	 * @return
	 */
	@RequestMapping("/now")
	@ResponseBody
	public Object getNowDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format( new Date(System.currentTimeMillis()) );
	}
	
	/** 取前端当前会话对象，前端需要ajax检测会话时可用
	 * @param session
	 * @return
	 */
	@RequestMapping("/homeUser")
	@ResponseBody
	public Object getUserSession4Home(HttpSession session){
		Map<String,Object> res = new HashMap<String,Object>();
		Object user = session.getAttribute(WhConstance.SESS_USER_KEY);
		res.put("user", user);
		return res;
	}
}
