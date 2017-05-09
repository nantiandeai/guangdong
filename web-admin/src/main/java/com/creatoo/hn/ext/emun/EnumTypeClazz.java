package com.creatoo.hn.ext.emun;

/**
 * 系统分类类别常量
 * Created by wangxl on 2017/3/17.
 */
public enum EnumTypeClazz {
    TYPE_ART("1", "艺术分类"), TYPE_VENUE("2", "场馆分类"), TYPE_ROOM("3", "活动室分类"), TYPE_ACTIVITY("4", "活动分类"),
    TYPE_TRAIN("5", "培训分类"), TYPE_AREA("6", "区域") , TYPE_ROOM_SHEBEI("7", "活动室设备分类"),
    TYPE_GENRE("8", "类别"),TYPE_BATCH("9", "批次"),TYPE_LEVEL("10", "级别"),TYPE_TEA_SPE("11", "老师专长"),
    TYPE_VOL_TRAIN("12", "志愿培训类型"),TYPE_VOL_ACT("13", "志愿活动类型"),TYPE_ZYFL("14", "资源分类"),TYPE_CUL("15","文化展类型");

    private String value;
    private String name;


    EnumTypeClazz(String value, String name){
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
