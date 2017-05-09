package com.creatoo.hn.actions.home.comm;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.UploadUtil;
import com.creatoo.hn.utils.img.FileUploadUtil;
import com.creatoo.hn.utils.img.ImgCut;

@RestController
@RequestMapping("/imgcut")
public class ImageCutAction {

	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	public CommService service;
	
	@RequestMapping(value = "/uploadCutImage")
    public Object uploadHeadImage(
            HttpServletRequest request,HttpServletResponse response,
            @RequestParam(value = "x") String x,
            @RequestParam(value = "y") String y,
            @RequestParam(value = "h") String h,
            @RequestParam(value = "w") String w,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "imgFile") MultipartFile imageFile
    ) throws Exception{
        //System.out.println("==========Start=============");
        Map<String,Object> map = new HashMap<String,Object>();
        //当前日期
		Date now = new Date();
       
		
        //String realPath = request.getSession().getServletContext().getRealPath("/");
        //String resourcePath = "resources/uploadImages/";
        if(imageFile!=null){
            if(FileUploadUtil.allowUpload(imageFile.getContentType())){
                String fileName = FileUploadUtil.rename(imageFile.getOriginalFilename());
                String extname = fileName.substring(fileName.lastIndexOf("."));
                String newFileName = service.getKey(type+".picture") ;
        		String imgname = newFileName+extname;
                //保存图片
              	String realPath = UploadUtil.getUploadPath(request);
                String resourcePath = UploadUtil.getUploadFilePath(fileName, newFileName, type, "picture", now);
                imageFile.transferTo( UploadUtil.createUploadFile(realPath, resourcePath) );
        		//whc.setClnfpic();
                String cutPath = UploadUtil.getUploadFileUrl(realPath, resourcePath) ;
                
                int end = imgname.lastIndexOf(".");
                //String saveName = imgname.substring(0,end);
//                File dir = new File(realPath + resourcePath);
//                if(!dir.exists()){
//                    dir.mkdirs();
//                }
                //File file = new File(dir,saveName+"_src.jpg");
                //imageFile.transferTo(file);
                String srcImagePath = realPath + resourcePath;
                int imageX = Integer.parseInt(x);
                int imageY = Integer.parseInt(y);
                int imageH = Integer.parseInt(h);
                int imageW = Integer.parseInt(w);
                //这里开始截取操作
                //System.out.println("==========imageCutStart=============");
                ImgCut.imgCut(srcImagePath,imageX,imageY,imageW,imageH);
                //System.out.println("==========imageCutEnd=============");
                //request.getSession().setAttribute("imgSrc",resourcePath + saveName+"_src.jpg");//成功之后显示用
                //request.getSession().setAttribute("imgCut",resourcePath + saveName+"_cut.jpg");//成功之后显示用
                map.put("imgpath", cutPath) ;
                //response.getWriter().write(resourcePath);
            }
        }
        return map ;
    }
}
