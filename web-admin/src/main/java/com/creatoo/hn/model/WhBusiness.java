package com.creatoo.hn.model;

import javax.persistence.*;

@Table(name = "wh_business")
public class WhBusiness {
    @Id
    private String id;

    /**
     * 企业
     */
    private String company;

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
     * 获取企业
     *
     * @return company - 企业
     */
    public String getCompany() {
        return company;
    }

    /**
     * 设置企业
     *
     * @param company 企业
     */
    public void setCompany(String company) {
        this.company = company;
    }
}