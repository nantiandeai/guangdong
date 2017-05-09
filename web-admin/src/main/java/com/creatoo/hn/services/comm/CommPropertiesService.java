package com.creatoo.hn.services.comm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 配置文件服务
 * Created by wangxl on 2017/3/25.
 */
public class CommPropertiesService {
    //上传文件参数
    private String uploadType;
    private String uploadLocalAddr;
    private String uploadLocalServerAddr;
    private String uploadOssEndpoint;
    private String uploadOssAccessKeyId;
    private String uploadOssAccessKeySecret;
    private String uploadOssBucketName;

    //短信参数
    private String smsUrl;
    private String smsUsername;
    private String smsPassword;

    //redis参数
    private String redisHost;
    private String redisPort;
    private String redisPass;
    private String redisMaxIdle;
    private String redisMaxActive;
    private String redisMaxWait;
    private String redisTestOnBorrow;

    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    public String getUploadLocalAddr() {
        return uploadLocalAddr;
    }

    public void setUploadLocalAddr(String uploadLocalAddr) {
        this.uploadLocalAddr = uploadLocalAddr;
    }

    public String getUploadLocalServerAddr() {
        return uploadLocalServerAddr;
    }

    public void setUploadLocalServerAddr(String uploadLocalServerAddr) {
        this.uploadLocalServerAddr = uploadLocalServerAddr;
    }

    public String getUploadOssEndpoint() {
        return uploadOssEndpoint;
    }

    public void setUploadOssEndpoint(String uploadOssEndpoint) {
        this.uploadOssEndpoint = uploadOssEndpoint;
    }

    public String getUploadOssAccessKeyId() {
        return uploadOssAccessKeyId;
    }

    public void setUploadOssAccessKeyId(String uploadOssAccessKeyId) {
        this.uploadOssAccessKeyId = uploadOssAccessKeyId;
    }

    public String getUploadOssAccessKeySecret() {
        return uploadOssAccessKeySecret;
    }

    public void setUploadOssAccessKeySecret(String uploadOssAccessKeySecret) {
        this.uploadOssAccessKeySecret = uploadOssAccessKeySecret;
    }

    public String getUploadOssBucketName() {
        return uploadOssBucketName;
    }

    public void setUploadOssBucketName(String uploadOssBucketName) {
        this.uploadOssBucketName = uploadOssBucketName;
    }

    public String getSmsUrl() {
        return smsUrl;
    }

    public void setSmsUrl(String smsUrl) {
        this.smsUrl = smsUrl;
    }

    public String getSmsUsername() {
        return smsUsername;
    }

    public void setSmsUsername(String smsUsername) {
        this.smsUsername = smsUsername;
    }

    public String getSmsPassword() {
        return smsPassword;
    }

    public void setSmsPassword(String smsPassword) {
        this.smsPassword = smsPassword;
    }

    public String getRedisHost() {
        return redisHost;
    }

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    public String getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(String redisPort) {
        this.redisPort = redisPort;
    }

    public String getRedisPass() {
        return redisPass;
    }

    public void setRedisPass(String redisPass) {
        this.redisPass = redisPass;
    }

    public String getRedisMaxIdle() {
        return redisMaxIdle;
    }

    public void setRedisMaxIdle(String redisMaxIdle) {
        this.redisMaxIdle = redisMaxIdle;
    }

    public String getRedisMaxActive() {
        return redisMaxActive;
    }

    public void setRedisMaxActive(String redisMaxActive) {
        this.redisMaxActive = redisMaxActive;
    }

    public String getRedisMaxWait() {
        return redisMaxWait;
    }

    public void setRedisMaxWait(String redisMaxWait) {
        this.redisMaxWait = redisMaxWait;
    }

    public String getRedisTestOnBorrow() {
        return redisTestOnBorrow;
    }

    public void setRedisTestOnBorrow(String redisTestOnBorrow) {
        this.redisTestOnBorrow = redisTestOnBorrow;
    }
}
