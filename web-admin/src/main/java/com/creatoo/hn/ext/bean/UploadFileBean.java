package com.creatoo.hn.ext.bean;

/**
 * 上传文件的信息对象
 * Created by wangxl on 2017/3/25.
 */
public class UploadFileBean {
    public static final String FAIL = "0";
    private String url;//上传后的路径
    private int width;//如果是图片，图片的宽度
    private int height;//如果是图片，图片的高度
    private String success = "1"; //是否上传成功
    private String errormsg;//上传失败信息

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }
}
