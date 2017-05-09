package com.creatoo.hn.model;

import javax.persistence.*;

@Table(name = "wh_team")
public class WhTeam {
    @Id
    private String id;

    /**
     * 团队
     */
    private String team;

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
     * 获取团队
     *
     * @return team - 团队
     */
    public String getTeam() {
        return team;
    }

    /**
     * 设置团队
     *
     * @param team 团队
     */
    public void setTeam(String team) {
        this.team = team;
    }
}