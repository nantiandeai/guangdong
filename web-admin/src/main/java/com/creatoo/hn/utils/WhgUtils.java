package com.creatoo.hn.utils;

/**
 * 此类是提供给JSP调用的静态方法，可用于el表达式
 * Created by wangxl on 2017/4/7.
 */
public class WhgUtils {

    /**
     * 取750*500图片
     * @param imgAddr 图片地址
     * @return 新的图片地址
     */
    public static String getImg750_500(String imgAddr){
        if(imgAddr != null){
            int idx = imgAddr.lastIndexOf(".");
            if(idx > -1){
                imgAddr = imgAddr.substring(0, idx)+"_750_500"+imgAddr.substring(idx);
            }
        }
        return imgAddr;
    }

    /**
     * 取300*200图片
     * @param imgAddr 图片地址
     * @return 新的图片地址
     */
    public static String getImg300_200(String imgAddr){
        if(imgAddr != null){
            int idx = imgAddr.lastIndexOf(".");
            if(idx > -1){
                imgAddr = imgAddr.substring(0, idx)+"_300_200"+imgAddr.substring(idx);
            }
        }
        return imgAddr;
    }

    /**
     * 取740*555图片
     * @param imgAddr 图片地址
     * @return 新的图片地址
     */
    public static String getImg740_555(String imgAddr){
        if(imgAddr != null){
            int idx = imgAddr.lastIndexOf(".");
            if(idx > -1){
                imgAddr = imgAddr.substring(0, idx)+"_740_555"+imgAddr.substring(idx);
            }
        }
        return imgAddr;
    }

    /**
     * 前端活动详情下载截取
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        if (filePath != null) {
            int idx = filePath.lastIndexOf("/");
            if (idx > -1) {
                filePath = filePath.substring(idx + 1);
            }
        }
        return filePath;
    }

}
