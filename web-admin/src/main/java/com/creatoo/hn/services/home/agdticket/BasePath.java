package com.creatoo.hn.services.home.agdticket;

/**
 * Created by Administrator on 2017/4/12.
 */
@SuppressWarnings("all")
public class BasePath {
    private  String basePath;
    private  String baseUrl;
    private String audioBaseUrl;

    public BasePath(){  }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getAudioBaseUrl() {
        return audioBaseUrl;
    }

    public void setAudioBaseUrl(String audioBaseUrl) {
        this.audioBaseUrl = audioBaseUrl;
    }
}
