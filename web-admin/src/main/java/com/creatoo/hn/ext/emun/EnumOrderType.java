package com.creatoo.hn.ext.emun;

/**
 * 订单类型
 * Created by wangxl on 2017/4/12.
 */
public enum EnumOrderType {
    ORDER_ACT(1, "活动订单"),ORDER_TRA(2, "培训订单"), ORDER_VEN(3, "活动室订单");


    EnumOrderType(int value, String name){
        this.value = value;
        this.name = name;
    }

    private int value;
    private String name;
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
