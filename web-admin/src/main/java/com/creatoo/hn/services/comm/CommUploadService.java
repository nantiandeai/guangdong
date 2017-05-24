package com.creatoo.hn.services.comm;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GetObjectRequest;
import com.creatoo.hn.ext.bean.UploadFileBean;
import com.creatoo.hn.ext.emun.EnumUploadType;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * 文件上传服务
 * Created by wangxl on 2017/3/25.
 */
@Service
public class CommUploadService {
    /**
     * 日志
     */
    Logger log = Logger.getLogger(this.getClass());

    /**
     * 配置文件服务
     */
    @Autowired
    private CommPropertiesService commPropertiesService;

    /**
     * 上传文件
     * @param multiFile Web上传对象
     * @param uploadFileType 上传文件类型
     * @param needCut 是否需要人工裁剪图片
     * @param cutWidth 宽
     * @param cutHeight 高
     * @return 上传文件信息
     * @throws Exception
     */
    public UploadFileBean uploadFile(MultipartFile multiFile, String uploadFileType, boolean needCut, String cutWidth, String cutHeight, String waterFilePath)throws Exception{
        UploadFileBean uploadFileBean = new UploadFileBean();

        try{
            if(EnumUploadType.TYPE_IMG.getValue().equals(uploadFileType)){
            	//验证图片尺寸
                BufferedImage bimg = ImageIO.read(multiFile.getInputStream());
                uploadFileBean.setWidth(bimg.getWidth());
                uploadFileBean.setHeight(bimg.getHeight());

                if( multiFile.getSize() > (1048576*2) ){
                    uploadFileBean.setSuccess(UploadFileBean.FAIL);
                    uploadFileBean.setErrormsg("图片大小必须在2MB以内");
                    return uploadFileBean;
                }
            	//裁剪时的宽高
                int _cutWidth = Integer.parseInt(cutWidth);
                int _cutHeight = Integer.parseInt(cutHeight);
                if(needCut && (bimg.getWidth() < _cutWidth || bimg.getHeight() < _cutHeight)){
                    uploadFileBean.setSuccess(UploadFileBean.FAIL);
                    uploadFileBean.setErrormsg("图片尺寸必须大于等于"+_cutWidth+"*"+_cutHeight);
                    return uploadFileBean;
                }else if(!needCut){
                    if( _cutWidth != 750 && _cutHeight != 500 ){
                        if( bimg.getWidth() != _cutWidth || bimg.getHeight() != _cutHeight ){
                            uploadFileBean.setSuccess(UploadFileBean.FAIL);
                            uploadFileBean.setErrormsg("图片尺寸必须等于"+_cutWidth+"*"+_cutHeight);
                            return uploadFileBean;
                        }
                    }else if (_cutWidth != 750 && bimg.getWidth() != _cutWidth){
                        uploadFileBean.setSuccess(UploadFileBean.FAIL);
                        uploadFileBean.setErrormsg("图片宽度必须等于"+_cutWidth+"px");
                        return uploadFileBean;
                    }else if (_cutHeight != 500 && bimg.getHeight() != _cutHeight){
                        uploadFileBean.setSuccess(UploadFileBean.FAIL);
                        uploadFileBean.setErrormsg("图片高度必须等于"+_cutHeight+"px");
                        return uploadFileBean;
                    }
                }
            }

            // 获取文件后缀
            String oldFileName = multiFile.getOriginalFilename();
            String suffix = "";
            int idx = oldFileName.lastIndexOf(".");
            if(idx > -1){
                suffix = oldFileName.substring(idx);
            }

            //生成新的文件名
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String newFileName;
            String newFileName_bak = null;
//            String file = suffix.toLowerCase();
            if (".doc".equals(suffix)||".zip".equals(suffix)||".docx".equals(suffix)||".xls".equals(suffix)||".pdf".equals(suffix)||".xlsx".equals(suffix)){
                newFileName = oldFileName;
            }else{
                newFileName = uuid+suffix;
                newFileName_bak = uuid+"_bak"+suffix;
            }

            // img/2017/201701/a.jpg  video/2017/201701/  audio/2017/201701/  file/2017/201701/
            Date now = new Date();
            String yyyyMMdd = new java.text.SimpleDateFormat("yyyyMMdd").format(now);
            String yyyyMM = new java.text.SimpleDateFormat("yyyyMM").format(now);
            String yyyy = new java.text.SimpleDateFormat("yyyy").format(now);

            //使用本地上传还是其它方式上传
            String useType = commPropertiesService.getUploadType();
            if("local".equals(useType)){
                //保存到本地
                String rootPath = commPropertiesService.getUploadLocalAddr();
                File dir = new File(rootPath, uploadFileType+File.separator+yyyy+File.separator+yyyyMM+File.separator+yyyyMMdd);
                if(!dir.exists()){
                    dir.mkdirs();
                }
                File uploadFile = new File(dir, newFileName);
                multiFile.transferTo(uploadFile);

                //上传图片的处理
                if(EnumUploadType.TYPE_IMG.getValue().equals(uploadFileType)){
                    //如果不是人工裁剪图片,此时默认生成[3:2(750*500 300*200)] [4:3(740*555)],人工裁剪时需要根据裁剪后的图片生成,全系统图片最小尺寸为750*500
                    if(!needCut){
                        if (newFileName_bak!=null){
                            File uploadFile_bak = new File(dir, newFileName_bak);
                            FileUtils.copyFile(uploadFile, uploadFile_bak);
                        }

                        //水印图片
                        File waterPic = new File(waterFilePath);
                        BufferedImage waterbi = ImageIO.read(waterPic);

                        //原图处理 .outputQuality(0.8f)
                        Thumbnails.of(uploadFile).scale(1f).outputQuality(0.9f).toFile(uploadFile);

                        //750_500
                        String newFileName_750_500 = getFileName(newFileName, "750", "500");
                        File newFile_750_500 = new File(dir, newFileName_750_500);
                        Thumbnails.of(uploadFile).size(750,500).keepAspectRatio(false).outputQuality(0.8f).toFile(newFile_750_500);
                        Thumbnails.of(newFile_750_500).size(750, 500).watermark(Positions.BOTTOM_RIGHT,waterbi,1f).toFile(newFile_750_500);

                        //740_555
                        String newFileName_740_555 = getFileName(newFileName, "740", "555");
                        File newFile_740_555 = new File(dir, newFileName_740_555);
                        Thumbnails.of(uploadFile).size(740,550).keepAspectRatio(false).outputQuality(0.8f).toFile(newFile_740_555);
                        Thumbnails.of(newFile_740_555).size(740,550).watermark(Positions.BOTTOM_RIGHT,waterbi,1f).toFile(newFile_740_555);

                        //300_200
                        String newFileName_300_200 = getFileName(newFileName, "300", "200");
                        File newFile_300_200 = new File(dir, newFileName_300_200);//.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("watermark.png")), 0.5f)
                        Thumbnails.of(uploadFile).size(300,200).keepAspectRatio(false).outputQuality(0.8f).toFile(newFile_300_200);
                        Thumbnails.of(newFile_300_200).size(300,200).watermark(Positions.BOTTOM_RIGHT,waterbi,1f).toFile(newFile_300_200);

                        //原图水印
                        Thumbnails.of(uploadFile).size(uploadFileBean.getWidth(),uploadFileBean.getHeight()).watermark(Positions.BOTTOM_RIGHT,waterbi,1f).toFile(uploadFile);
                    }
                }

                //最终上传文件的地址
                uploadFileBean.setUrl("/"+uploadFileType+"/"+yyyy+"/"+yyyyMM+"/"+yyyyMMdd+"/"+newFileName);
            }else{ //上传到oss
                //oss配置
                String endpoint = commPropertiesService.getUploadOssEndpoint();//AliyunOssUtil.getEndpoint();// endpoint以杭州为例，其它region请按实际情况填写
                String accessKeyId = commPropertiesService.getUploadOssAccessKeyId();//AliyunOssUtil.getAccessKeyId();// accessKey请登录https://ak-console.aliyun.com/#/查看
                String accessKeySecret = commPropertiesService.getUploadOssAccessKeySecret();//AliyunOssUtil.getAccessKeySecret();
                String bucketName = commPropertiesService.getUploadOssBucketName();//AliyunOssUtil.getBucketName();

                // 创建OSSClient实例
                OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

                // 上传文件流
                String key = newFileName;

                //上传图片的处理
                if(EnumUploadType.TYPE_IMG.getValue().equals(uploadFileType)){
                    //保存原图到oss
                    ossClient.putObject(bucketName, key, multiFile.getInputStream());

                    //如果不是人工裁剪图片,此时默认生成[3:2(750*500 300*200)] [4:3(740*555)],人工裁剪时需要根据裁剪后的图片生成,全系统图片最小尺寸为750*500
                    if(!needCut){
                        //原图处理 .outputQuality(0.9f)
                        String style = "image/resize,m_fixed,w_"+uploadFileBean.getWidth()+",h_"+uploadFileBean.getHeight();
                        GetObjectRequest request = new GetObjectRequest(bucketName, key);
                        request.setProcess(style);

                        //750_500
                        style = "image/resize,m_fixed,w_750,h_500";
                        Date expiration = new Date(new Date().getTime() + 315360000000l );
                        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, key, HttpMethod.GET);
                        req.setExpiration(expiration);
                        req.setProcess(style);
                        URL signedUrl = ossClient.generatePresignedUrl(req);
                        System.out.println(signedUrl);


                        //740_555

                        //300_200
                    }
                }else{
                    //保存到oss
                    ossClient.putObject(bucketName, key, multiFile.getInputStream());
                }

                //最终图片地址
                Date expiration = new Date(new Date().getTime() + 315360000000l);
                URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
                uploadFileBean.setUrl(url.toString());
            }
        }catch (Exception e){
            throw e;
        }

        return uploadFileBean;
    }

    /**
     * 根据宽高生成新的文件名
     * @param fileName 文件名
     * @param width 宽
     * @param height 高
     * @return img.jpg变成img_750_500.jpg
     */
    private String getFileName(String fileName, String width, String height){
        if(fileName != null){
            int idx = fileName.lastIndexOf(".");
            String preName = fileName.substring(0, idx);
            String nexName = fileName.substring(idx);
            fileName = preName+"_"+width+"_"+height+nexName;
        }
        return fileName;
    }

    /**
     * 图片裁剪
     * @param imgurl
     * @param x
     * @param y
     * @param x2
     * @param y2
     * @param w
     * @param h
     * @return
     */
    public UploadFileBean uploadImgCut(String imgurl, String x, String y, String x2, String y2, String w, String h, String waterFilePath)throws Exception {
        UploadFileBean ufb = new UploadFileBean();

        //本地裁剪
        String useType = commPropertiesService.getUploadType();
        if("local".equals(useType)){
            //图片路径转化成文件路径
            String rootPath = commPropertiesService.getUploadLocalAddr();
            String filePath = imgurl.replaceAll("/", "\\" + File.separator);

            //需要裁剪的宽高
            int _w = ((Double)Double.parseDouble(w)).intValue();
            int _h = ((Double)Double.parseDouble(h)).intValue();
            int _x = ((Double)Double.parseDouble(x)).intValue();
            int _y = ((Double)Double.parseDouble(y)).intValue();
            int _x2 = ((Double)Double.parseDouble(x2)).intValue();
            int _y2 = ((Double)Double.parseDouble(y2)).intValue();

            //新的文件名
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            int idx = imgurl.lastIndexOf("/");
            int idx2 = imgurl.lastIndexOf(".");
            String newimgurl =  imgurl.substring(0,idx+1)+uuid+imgurl.substring(idx2);
            String newFilePath = newimgurl.replaceAll("/", "\\" + File.separator);
            File newImgFile = new File(rootPath, newFilePath);
            if(!newImgFile.exists()){
                newImgFile.createNewFile();
            }

            String newimgurl_bak =  imgurl.substring(0,idx+1)+uuid+"_bak"+imgurl.substring(idx2);
            String newFilePath_bak = newimgurl_bak.replaceAll("/", "\\" + File.separator);
            File newImgFile_bak = new File(rootPath, newFilePath_bak);

            //根据原始文件裁剪
            File imgFile = new File(rootPath, filePath);
            if(imgFile.exists()){
                //水印图片
                File waterPic = new File(waterFilePath);
                BufferedImage waterbi = ImageIO.read(waterPic);

                //原图处理
                Thumbnails.of(imgFile)
                        .sourceRegion(_x, _y, _w, _h)
                        .size(_w, _h).outputQuality(1f)
                        .toFile(newImgFile);

                FileUtils.copyFile(newImgFile, newImgFile_bak);

                //750_500
                //if(_w>750 && _h>500){
                    String newFileName_750_500 = getFileName(newFilePath, "750", "500");
                    File newFile_750_500 = new File(rootPath, newFileName_750_500);
                    Thumbnails.of(newImgFile).size(750,500).keepAspectRatio(false).outputQuality(0.9f).toFile(newFile_750_500);
                    Thumbnails.of(newFile_750_500).size(750, 500).watermark(Positions.BOTTOM_RIGHT,waterbi,1f).toFile(newFile_750_500);
                //}

                //740_555
                //if(_w>740 && _h>555) {
                    String newFileName_740_555 = getFileName(newFilePath, "740", "555");
                    File newFile_740_555 = new File(rootPath, newFileName_740_555);
                    Thumbnails.of(newImgFile).size(740, 550).keepAspectRatio(false).outputQuality(0.9f).toFile(newFile_740_555);
                    Thumbnails.of(newFile_740_555).size(740, 555).watermark(Positions.BOTTOM_RIGHT,waterbi,1f).toFile(newFile_740_555);
                //}

                //300_200
                //if(_w>300 && _h>200) {
                    String newFileName_300_200 = getFileName(newFilePath, "300", "200");
                    File newFile_300_200 = new File(rootPath, newFileName_300_200);//.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("watermark.png")), 0.5f)
                    Thumbnails.of(newImgFile).size(300, 200).keepAspectRatio(false).outputQuality(0.9f).toFile(newFile_300_200);
                    Thumbnails.of(newFile_300_200).size(300, 200).watermark(Positions.BOTTOM_RIGHT,waterbi,1f).toFile(newFile_300_200);
                //}

                //原图加水印
                Thumbnails.of(newImgFile).size(_w, _h).watermark(Positions.BOTTOM_RIGHT,waterbi,1f).toFile(newImgFile);

                //最终图片地址
                ufb.setUrl(newimgurl);
                ufb.setWidth(_w);
                ufb.setWidth(_h);

                //删除原图
                imgFile.delete();
            }
        }else{//oss裁剪

        }


        return ufb;
    }

    /**
     * 根据上传文件的地址，删除上传的文件
     * @param url 上传文件的地址
     * @throws Exception
     */
    public void delUploadFile(String url)throws Exception{
        //使用本地上传还是其它方式上传
        String useType = commPropertiesService.getUploadType();
        if("local".equals(useType)){
            String rootPath = commPropertiesService.getUploadLocalAddr();
            String filePath = url.replaceAll("/", "\\" + File.separator);
            File file = new File(rootPath, filePath);
            if(file.exists()){
                file.delete();
            }
        }else{
            //oss配置
            String endpoint = commPropertiesService.getUploadOssEndpoint();//AliyunOssUtil.getEndpoint();// endpoint以杭州为例，其它region请按实际情况填写
            String accessKeyId = commPropertiesService.getUploadOssAccessKeyId();//AliyunOssUtil.getAccessKeyId();// accessKey请登录https://ak-console.aliyun.com/#/查看
            String accessKeySecret = commPropertiesService.getUploadOssAccessKeySecret();//AliyunOssUtil.getAccessKeySecret();
            String bucketName = commPropertiesService.getUploadOssBucketName();//AliyunOssUtil.getBucketName();

            // 创建OSSClient实例
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

            String key = "";
            ossClient.deleteObject(bucketName, key);
        }
    }
}
