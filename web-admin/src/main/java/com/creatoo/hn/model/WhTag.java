package com.creatoo.hn.model;

import javax.persistence.*;

@Table(name = "wh_tag")
public class WhTag {
    /**
     * 标签ID
     */
    @Id
    @SequenceGenerator(name="",sequenceName="SELECT LAST_INSERT_ID()")
    private String id;

    /**
     * 标签名字
     */
    private String name;

    /**
     * 标签类型
     */
    private String type;

    /**
     * 标签排序
     */
    private String idx;

    /**
     * 获取标签ID
     *
     * @return id - 标签ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置标签ID
     *
     * @param id 标签ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取标签名字
     *
     * @return name - 标签名字
     */
    public String getName() {
        return name;
    }

    /**
     * 设置标签名字
     *
     * @param name 标签名字
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取标签类型
     *
     * @return type - 标签类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置标签类型
     *
     * @param type 标签类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取标签排序
     *
     * @return idx - 标签排序
     */
    public String getIdx() {
        return idx;
    }

    /**
     * 设置标签排序
     *
     * @param idx 标签排序
     */
    public void setIdx(String idx) {
        this.idx = idx;
    }
}