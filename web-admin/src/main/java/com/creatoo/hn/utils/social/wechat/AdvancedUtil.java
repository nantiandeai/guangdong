package com.creatoo.hn.utils.social.wechat;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class AdvancedUtil {
	
	/**
	 * 日志控制器
	 */ 
	static Logger log = Logger.getLogger(AdvancedUtil.class);
	
	public static String getOpenId(String appId, String redirectUrl, String state) {
		// 拼接请求地址
        String requestUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECTURL&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
        requestUrl = requestUrl.replace("APPID", CommonUtil.urlEncodeUTF8(appId));
        requestUrl = requestUrl.replace("REDIRECTURL", CommonUtil.urlEncodeUTF8(redirectUrl));
        requestUrl = requestUrl.replace("STATE", CommonUtil.urlEncodeUTF8(state));
        // 获取网页授权凭证
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                return jsonObject.toJSONString();
            } catch (Exception e) {
                int errorCode = Integer.parseInt(jsonObject.getString("errcode"));
                String errorMsg = jsonObject.getString("errmsg");
                log.error(String.format("获取网页授权凭证失败 errcode:%d errmsg:%s", errorCode, errorMsg));
            }
        }
        return "";
	}
	
	/**
     * 获取网页授权凭证
     * 
     * @param appId 公众账号的唯一标识
     * @param appSecret 公众账号的密钥
     * @param code
     * @return WeixinAouth2Token
     */
    public static WeixinOauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
        WeixinOauth2Token wat = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);
        // 获取网页授权凭证
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                wat = new WeixinOauth2Token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(Integer.parseInt(jsonObject.getString("expires_in")));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                wat = null;
                int errorCode = Integer.parseInt(jsonObject.getString("errcode"));
                String errorMsg = jsonObject.getString("errmsg");
                log.error(String.format("获取网页授权凭证失败 errcode:%d errmsg:%s", errorCode, errorMsg));
            }
        }
        return wat;
    }
    
    /**
     * 通过网页授权获取用户信息
     * 
     * @param accessToken 网页授权接口调用凭证
     * @param openId 用户标识
     * @return SNSUserInfo
     */
	@SuppressWarnings( { "deprecation", "unchecked" })
    public static SNSUserInfo getSNSUserInfo(String accessToken, String openId) {
        SNSUserInfo snsUserInfo = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 通过网页授权获取用户信息
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                snsUserInfo = new SNSUserInfo();
                // 用户的标识
                snsUserInfo.setOpenId(jsonObject.getString("openid"));
                // 昵称
                snsUserInfo.setNickname(jsonObject.getString("nickname"));
                // 性别（1是男性，2是女性，0是未知）
                snsUserInfo.setSex(Integer.parseInt(jsonObject.getString("sex")));
                // 用户所在国家
                snsUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                snsUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                snsUserInfo.setCity(jsonObject.getString("city"));
                // 用户头像
                snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
                JSON.parseArray(jsonObject.getString("privilege"), String.class);
                // 用户特权信息
                snsUserInfo.setPrivilegeList(JSON.parseArray(jsonObject.getString("privilege"), String.class));
            } catch (Exception e) {
                snsUserInfo = null;
                int errorCode = Integer.parseInt(jsonObject.getString("errcode"));
                String errorMsg = jsonObject.getString("errmsg");
                log.error(String.format("获取用户信息失败 errcode:%d errmsg:%s", errorCode, errorMsg));
            }
        }
        return snsUserInfo;
    }
	
	public static String getQrAuthCode(String appId, String redirectUrl, String state) {
		// 拼接请求地址
        String requestUrl = "https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECTURL&response_type=code&scope=snsapi_login&state=STATE#wechat_redirect";
        requestUrl = requestUrl.replace("APPID", CommonUtil.urlEncodeUTF8(appId));
        requestUrl = requestUrl.replace("REDIRECTURL", CommonUtil.urlEncodeUTF8(redirectUrl));
        requestUrl = requestUrl.replace("STATE", CommonUtil.urlEncodeUTF8(state));
        // 获取网页授权凭证
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                return jsonObject.toJSONString();
            } catch (Exception e) {
                int errorCode = Integer.parseInt(jsonObject.getString("errcode"));
                String errorMsg = jsonObject.getString("errmsg");
                log.error(String.format("获取网页授权凭证失败 errcode:%d errmsg:%s", errorCode, errorMsg));
            }
        }
        return "";
	}
	
	public static String getQrAuthUrl(String appId, String redirectUrl, String state) {
		// 拼接请求地址
        String requestUrl = "https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECTURL&response_type=code&scope=snsapi_login&state=STATE#wechat_redirect";
        requestUrl = requestUrl.replace("APPID", CommonUtil.urlEncodeUTF8(appId));
        requestUrl = requestUrl.replace("REDIRECTURL", CommonUtil.urlEncodeUTF8(redirectUrl));
        requestUrl = requestUrl.replace("STATE", CommonUtil.urlEncodeUTF8(state));
        return requestUrl;
	}
}
