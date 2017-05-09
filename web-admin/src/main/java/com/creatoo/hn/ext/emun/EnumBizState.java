package com.creatoo.hn.ext.emun;

/**
 *
 * Created by Administrator on 2017/3/16.
 */
public enum EnumBizState {
    STATE_CAN_EDIT(1, "可编辑"),STATE_CAN_CHECK(9, "待审核"),STATE_CAN_PUB(2, "待发布"),
    STATE_NO_PUB(4, "已下架"),STATE_PUB(6, "已发布")/*,STATE_DEL(5, "已撤销")*/;

    private int value;
    private String name;

    EnumBizState(int value, String name){
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
