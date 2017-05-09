package com.creatoo.hn.model;

import javax.persistence.*;

@Table(name = "wh_personnel")
public class WhPersonnel {
    @Id
    private String id;

    /**
     * 特长
     */
    private String talanted;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取特长
     *
     * @return talanted - 特长
     */
    public String getTalanted() {
        return talanted;
    }

    /**
     * 设置特长
     *
     * @param talanted 特长
     */
    public void setTalanted(String talanted) {
        this.talanted = talanted;
    }
}