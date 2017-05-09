package com.creatoo.hn.ext.emun;

/**
 * 关键字类别常量
 * Created by wangxl on 2017/4/5.
 */
public enum EnumKeyClazz {
    TEY_VENUE("1", "场馆关键字"), TEY_ROOM("2", "活动室关键字"), TEY_ACTIVITY("3", "活动关键字"), TEY_TRAIN("4", "培训关键字"), TEY_ZX("5", "资讯关键字");

    private String value;
    private String name;


    EnumKeyClazz(String value, String name){
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
