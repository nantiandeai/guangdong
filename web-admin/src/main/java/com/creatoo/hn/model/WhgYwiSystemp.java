package com.creatoo.hn.model;

import javax.persistence.*;

@Table(name = "whg_ywi_systemp")
public class WhgYwiSystemp {
    /**
     * ID
     */
    @Id
    private String id;

    /**
     * 短信编码
     */
    private String code;

    /**
     * 短信模板内容
     */
    private String content;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取短信编码
     *
     * @return code - 短信编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置短信编码
     *
     * @param code 短信编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取短信模板内容
     *
     * @return content - 短信模板内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置短信模板内容
     *
     * @param content 短信模板内容
     */
    public void setContent(String content) {
        this.content = content;
    }
}