package com.creatoo.hn.model;

import javax.persistence.*;

@Table(name = "wh_teacher")
public class WhTeacher {
    @Id
    private String id;

    /**
     * 教师
     */
    private String teacher;

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
     * 获取教师
     *
     * @return teacher - 教师
     */
    public String getTeacher() {
        return teacher;
    }

    /**
     * 设置教师
     *
     * @param teacher 教师
     */
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}