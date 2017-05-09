package com.creatoo.hn.ext.emun;

/**
 * 标签类别常量
 * Created by wangxl on 2017/4/5.
 */
public enum EnumTagClazz {
    TAG_VENUE("1", "场馆标签"), TAG_ROOM("2", "活动室标签"), TAG_ACTIVITY("3", "活动标签"), TAG_TRAIN("4", "培训标签"), TAG_ZX("5", "资讯标签");

    private String value;
    private String name;


    EnumTagClazz(String value, String name){
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
