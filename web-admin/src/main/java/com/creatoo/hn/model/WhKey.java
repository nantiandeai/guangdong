package com.creatoo.hn.model;

import javax.persistence.*;

@Table(name = "wh_key")
public class WhKey {
    /**
     * 关键字ID
     */
    @Id
    @SequenceGenerator(name="",sequenceName="SELECT LAST_INSERT_ID()")
    private String id;

    /**
     * 关键字名字
     */
    private String name;

    /**
     * 关键字类型
     */
    private String type;

    /**
     * 关键字排序
     */
    private String idx;

    /**
     * 获取关键字ID
     *
     * @return id - 关键字ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置关键字ID
     *
     * @param id 关键字ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取关键字名字
     *
     * @return name - 关键字名字
     */
    public String getName() {
        return name;
    }

    /**
     * 设置关键字名字
     *
     * @param name 关键字名字
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取关键字类型
     *
     * @return type - 关键字类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置关键字类型
     *
     * @param type 关键字类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取关键字排序
     *
     * @return idx - 关键字排序
     */
    public String getIdx() {
        return idx;
    }

    /**
     * 设置关键字排序
     *
     * @param idx 关键字排序
     */
    public void setIdx(String idx) {
        this.idx = idx;
    }
}