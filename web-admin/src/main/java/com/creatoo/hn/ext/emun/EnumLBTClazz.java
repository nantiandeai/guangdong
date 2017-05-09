package com.creatoo.hn.ext.emun;

/**
 * 轮播图类型
 * Created by wangxl on 2017/4/6.
 */
public enum EnumLBTClazz {
    LBT_PC("1", "PC首页轮播图"), LBT_APP("2", "APP首页轮播图"), LBT_PC_ADV("3", "PC首页广告")
    , LBT_APP_ADV("4", "APP首页广告"), LBT_TRA_PC("5", "PC培训首页轮播图配置"), LBT_VOL_PC("6", "PC首页志愿服务配置"),
    LBT_ADV_PC_FY("7", "PC非遗首页广告图"),LBT_ADV_PC_VOL("8", "PC志愿服务广告图"),LBT_LB_PC_FY("9", "PC非遗首页轮播图");

    private String value;
    private String name;

    EnumLBTClazz(String value, String name){
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
