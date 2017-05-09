package com.creatoo.hn.ext.emun;

/**
 * 上传文件的类型
 * Created by wangxl on 2017/3/25.
 */
public enum EnumUploadType {
    TYPE_IMG("img", "图片"), TYPE_VIDEO("video", "视频"),TYPE_AUDIO("audio", "音频"),TYPE_FILE("file", "文件");
    private String value;
    private String name;


    EnumUploadType(String value, String name){
        this.value = value;

        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
