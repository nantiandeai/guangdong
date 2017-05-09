package com.creatoo.hn.actions.api.user;

import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.model.WhCode;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.services.api.user.ApiUserService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.comm.SMSService;
import com.creatoo.hn.services.home.user.RegistService;
import com.creatoo.hn.utils.RegistRandomUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 用户个人中心修改
 * Created by wangxl on 2017/4/12.
 */
@RestController
@RequestMapping("/api/user")
public class APIUserAction {
    /**
     * 日志控制器
     */
    Logger log = Logger.getLogger(this.getClass().getName());

    /**
     * 用户接口服务
     */
    @Autowired
    private ApiUserService apiUserService;

    /**
     * 短信服务
     */
    @Autowired
    private SMSService smsService;

    /**
     * 公共服务
     */
    @Autowired
    private CommService commService;

    /**
     * 注册服务
     */
    @Autowired
    private RegistService regService;

    /**
     * 绑定微信用户绑定手机
     * 访问地址 /api/user/bind/{id}/{phone}
     * @param id 微信用户ID whg_usr_weixin.id
     * @param phone 手机号码
     * @return JSON : {
     *     "success" : "1"                             //1-成功； 其它失败
     *     "errormsg" : "100|101|102|103|104"          //100-绑定成功；101-手机格式不正确; 102-参数id无效； 103-手机号已经被其它账号绑定; 104-手机号已经被自己绑定
     * }
     */
    @CrossOrigin
    @RequestMapping("/bind/{id}/{phone}")
    public ResponseBean bind(@PathVariable("id") String id, @PathVariable("phone") String phone){
        ResponseBean res = new ResponseBean();
        try{
            String code = this.apiUserService.bindPhone(id, phone);
            if(!"100".equals(code)){
                res.setSuccess(ResponseBean.FAIL);
            }
            res.setErrormsg(code);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
        }
        return res;
    }

    /**
     * 取消绑定
     * 访问地址 /api/user/unbind/{id}/{phone}
     * @param id 微信用户ID whg_usr_weixin.id
     * @param phone 手机号码
     * @return JSON : {
     *     "success" : "1"                             //1-成功； 其它失败
     *     "errormsg" : "100|101|102|103|104"          //100-解绑成功；101-手机格式不正确; 102-参数id无效
     * }
     */
    @CrossOrigin
    @RequestMapping("/unbind/{id}/{phone}")
    public ResponseBean unbind(@PathVariable("id") String id, @PathVariable("phone") String phone){
        ResponseBean res = new ResponseBean();
        try{
            String validCode = this.apiUserService.unbindPhone(id, phone);
            if(!"0".equals(validCode)){
                res.setSuccess(ResponseBean.FAIL);
                res.setErrormsg(validCode);
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
        }
        return res;
    }

    /**
     * 微信注册
     * 访问地址 /api/user/register/{phone}/{password}
     * @param phone 手机号码
     * @param password  密码
     * @return
     */
    @CrossOrigin
    @RequestMapping("/register/{phone}/{password}")
    public ResponseBean register(@PathVariable("phone") String phone, @PathVariable("password") String password){
        ResponseBean res = new ResponseBean();
        try {
            String validCode = this.apiUserService.register(phone, password);
            if(!"100".equals(validCode)){
                res.setSuccess(ResponseBean.FAIL);
                res.setErrormsg(validCode);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
        }
        return res;
    }



    /**
     * 发送验证码
     * 访问地址 /api/user/sendSMS/{phone}/{code}
     * @param phone 手机号码
     * @return JSON : {
     *     "success" : "1"                             //1-成功； 其它失败
     *     "errormsg" : "100|101|102"          //100-发送成功；101-手机格式不正确; 102-发生异常
     * }
     */
    @CrossOrigin
    @RequestMapping("/sendSMS/{phone}")
    public ResponseBean sendValidCode(@PathVariable("phone") String phone){
        ResponseBean res = new ResponseBean();
        try {
            //手机格式不正确
            if(phone == null || !phone.matches("^1[3|4|5|7|8][0-9]\\d{8}$")){
                res.setSuccess(ResponseBean.FAIL);
                res.setErrormsg("101");
            }else{
                Map<String, String> smsData = new HashMap<String, String>();
                String code = RegistRandomUtil.random();
                smsData.put("validCode", code);
                smsService.t_sendSMS(phone, "LOGIN_VALIDCODE", smsData);

                //将数据保存至code表
                this.regService.delPhoneCode(phone);
                String cid = this.commService.getKey("whcode");
                WhCode whcode = new WhCode();
                whcode.setId(cid);
                whcode.setSessid("ssid");
                whcode.setMsgcontent(code);
                whcode.setMsgtime(new Date());
                whcode.setMsgphone(phone);
                this.regService.savePhone(whcode);
                res.setErrormsg("100");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
        }
        return res;
    }

    /**
     * 验证手机验证码
     * @param phone 手机号
     * @param code 验证码
     * @return JSON : {
     *     "success" : "1"                             //1-成功； 其它失败
     *     "errormsg" : "错误消息"
     * }
     */
    @CrossOrigin
    @RequestMapping("/validSMSCode/{phone}/{code}")
    public ResponseBean validSMSCode(@PathVariable("phone") String phone, @PathVariable("code")String code){
        ResponseBean res = new ResponseBean();
        try {
            boolean isok = this.regService.validPhoneCode4API(phone, code);
            if(isok){
                this.regService.delPhoneCode(phone);
            }else{
                res.setSuccess(ResponseBean.FAIL);
                res.setErrormsg("验证码错误");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
        }
        return res;
    }

    /**
     * 保存用户资料(POST提交，表单数据为需要修改的whusr表的字段)
     * 访问地址 /api/user/info/save
     * @param whUser 需要修改的用户信息，参考whusr表结构, POST表单数据如：{
     *       "id": "userid,whuser.id的值",
     *       "name": "修改后的用户姓名",
     *       "nickname": "修改后的用户昵称"， ....更多whusr表的字段参数
     * }
     * @return JSON : {
     *     "success" : "1"                  //1-成功； 其它失败
     *     "errormsg" : "异常消息"          //失败时的异常消息
     * }
     */
    @CrossOrigin
    @RequestMapping("/info/save")
    public ResponseBean saveUserInfo(WhUser whUser){
        ResponseBean res = new ResponseBean();
        try {
            this.apiUserService.saveUserInfo(whUser);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("102");
        }
        return res;
    }

}
