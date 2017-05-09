package com.creatoo.hn.ext.emun;

/**
 * Created by Administrator on 2017/3/16.
 */
public enum EnumDelState {
    STATE_DEL_NO(0, "未删除"),STATE_DEL_YES(1, "已删除");

    private int value;
    private String name;

    EnumDelState(int value, String name){
        this.value = value;
        this.name = name;
    }

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
