package com.creatoo.hn.ext.emun;

/**
 * Created by wangxl on 2017/3/17.
 */
public enum EnumBMState {
    BM_SQ(1, "已申请"), BM_QXBM(2, "取消报名"),BM_SHSB(3, "审核失败"), BM_DDMS(4, "等待面试"),BM_MSBTG(5, "面试不通过"), BM_CG(6, "报名成功");
    private int value;
    private String name;
    EnumBMState(int value, String name){
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
