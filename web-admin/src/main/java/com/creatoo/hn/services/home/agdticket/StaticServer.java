package com.creatoo.hn.services.home.agdticket;

/**
 * Created by Administrator on 2017/4/12.
 */
@SuppressWarnings("all")
public class StaticServer {
    private String staticServerUrl;

    private String sentinelPool;

    private String activeMqFailover;

    public String getActiveMqFailover() {
        return activeMqFailover;
    }

    public void setActiveMqFailover(String activeMqFailover) {
        this.activeMqFailover = activeMqFailover;
    }

    public String getSentinelPool() {
        return sentinelPool;
    }

    public void setSentinelPool(String sentinelPool) {
        this.sentinelPool = sentinelPool;
    }

    public String getStaticServerUrl() {
        return staticServerUrl;
    }

    public void setStaticServerUrl(String staticServerUrl) {
        this.staticServerUrl = staticServerUrl;
    }
}
