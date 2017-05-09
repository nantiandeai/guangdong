package com.creatoo.hn.utils.img;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

public class ImgUtils {

	public static String cutImg(HttpServletRequest request,MultipartFile imgFile) throws IllegalStateException, IOException {
		String x = request.getParameter("x") ;
		String y = request.getParameter("y") ;
		String w = request.getParameter("w") ;
		String h = request.getParameter("h") ;
		//String imageFile = (String)request.getParameter("imgFile") ;
		//Object imgFiles = imageFile ;
		//MultipartFile imgFile = (MultipartFile) imgFiles ;
		System.out.println("==========Start=============");
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String resourcePath = "resources/uploadImages/";
		if (imgFile != null) {
			if (FileUploadUtil.allowUpload(imgFile.getContentType())) {
				String fileName = FileUploadUtil.rename(imgFile.getOriginalFilename());
				int end = fileName.lastIndexOf(".");
				String saveName = fileName.substring(0, end);
				File dir = new File(realPath + resourcePath);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				File file = new File(dir, saveName + "_src.jpg");
				imgFile.transferTo(file);
				String srcImagePath = realPath + resourcePath + saveName;
				int imageX = Integer.parseInt(x);
				int imageY = Integer.parseInt(y);
				int imageH = Integer.parseInt(w);
				int imageW = Integer.parseInt(h);
				// 这里开始截取操作
				System.out.println("==========imageCutStart=============");
				ImgCut.imgCut(srcImagePath, imageX, imageY, imageW, imageH);
				System.out.println("==========imageCutEnd=============");

				return resourcePath + saveName+"_cut.jpg";
			}

		}
		return null;
	}
}
