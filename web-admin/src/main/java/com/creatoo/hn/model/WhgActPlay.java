package com.creatoo.hn.model;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "whg_act_play")
public class WhgActPlay {
    /**
     * 主键ID
     */
    @Id
    private String id;

    /**
     * 活动ID
     */
    private String actid;

    /**
     * 场次开始时间
     */
    private String playstrtime;

    /**
     * 场次结束时间
     */
    private String playendtime;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取活动ID
     *
     * @return actid - 活动ID
     */
    public String getActid() {
        return actid;
    }

    /**
     * 设置活动ID
     *
     * @param actid 活动ID
     */
    public void setActid(String actid) {
        this.actid = actid;
    }

    /**
     * 获取场次开始时间
     *
     * @return playstrtime - 场次开始时间
     */
    public String getPlaystrtime() {
        return playstrtime;
    }

    /**
     * 设置场次开始时间
     *
     * @param playstrtime 场次开始时间
     */
    public void setPlaystrtime(String playstrtime) {
        this.playstrtime = playstrtime;
    }

    /**
     * 获取场次结束时间
     *
     * @return playendtime - 场次结束时间
     */
    public String getPlayendtime() {
        return playendtime;
    }

    /**
     * 设置场次结束时间
     *
     * @param playendtime 场次结束时间
     */
    public void setPlayendtime(String playendtime) {
        this.playendtime = playendtime;
    }
}