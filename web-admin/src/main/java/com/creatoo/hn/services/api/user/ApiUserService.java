package com.creatoo.hn.services.api.user;

import com.creatoo.hn.mapper.WhUserMapper;
import com.creatoo.hn.mapper.WhgUsrWeixinMapper;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.model.WhgUsrWeixin;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.comm.SMSService;
import com.creatoo.hn.utils.MD5Util;
import com.creatoo.hn.utils.RegistRandomUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户模块接口服务
 * Created by wangxl on 2017/4/13.
 */
@Service
public class ApiUserService {
    /**
     * 日志控制器
     */
    Logger log = Logger.getLogger(this.getClass().getName());

    /**
     * 公共服务，用于生成KEY等
     */
    @Autowired
    private CommService commService;

    /**
     * 短信服务
     */
    @Autowired
    private SMSService smsService;

    /**
     * 微信用户DAO
     */
    @Autowired
    private WhgUsrWeixinMapper whgUsrWeixinMapper;

    /**
     * 会员DAO
     */
    @Autowired
    private WhUserMapper whUserMapper;

    /**
     * 绑定手机
     * @param id 微信账号ID whg_usr_xeixin.id
     * @param phone 手机号
     * @return 100-绑定成功；101-手机格式不正确; 102-参数id无效； 103-手机号已经被其它账号绑定; 104-手机号已经被自己绑定
     * @throws Exception
     */
    public String bindPhone(String id, String phone)throws Exception{
        String code = "100";

        //手机格式不正确
        if(phone == null || !phone.matches("^1[3|4|5|7|8][0-9]\\d{8}$")){
            return "101";
        }

        //id有效
        WhgUsrWeixin whgUsrWeixin = whgUsrWeixinMapper.selectByPrimaryKey(id);
        if(whgUsrWeixin == null || whgUsrWeixin.getId() == null){
            return "102";
        }

        //根据phone找到会员
        Example example = new Example(WhUser.class);
        example.createCriteria().andEqualTo("phone", phone);
        List<WhUser> whuserList = this.whUserMapper.selectByExample(example);
        WhUser whUser = null;
        if(whuserList != null && whuserList.size() > 0){
            whUser = whuserList.get(0);
        }

        //存在用户
        if(whUser != null){
            //是否已经绑定
            WhgUsrWeixin userwx = new WhgUsrWeixin();
            userwx.setUserid(whUser.getId());
            userwx = this.whgUsrWeixinMapper.selectOne(userwx);
            if(userwx != null && userwx.getId() != null){
                if(userwx.getId().equals(id)){
                    return "103";//手机号已经被其它微信用户绑定
                }else{
                    return "104";//手机号已经被自己绑定
                }
            }else{//未被绑定-直接绑定到
                whgUsrWeixin.setUserid(whUser.getId());
                Example exa2 = new Example(WhgUsrWeixin.class);
                exa2.createCriteria().andEqualTo("id", id);
                this.whgUsrWeixinMapper.updateByExampleSelective(whgUsrWeixin, exa2);
            }
        }else{//不存在用户
            //插入用户记录
            WhUser whUser1 = new WhUser();
            String password = RegistRandomUtil.random();
            String passwordMD5 = MD5Util.toMd5(password);
            whUser1.setId(commService.getKey("whuser"));
            String nickname = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
            whUser1.setPhone(phone);
            whUser1.setNickname(nickname);
            whUser1.setIsrealname(0);
            whUser1.setIsperfect(0);
            whUser1.setIsinner(0);
            whUser1.setPassword( passwordMD5 );
            this.whUserMapper.insert(whUser1);

            //发送短信，告诉密码
            Map<String, String> data = new HashMap<String, String>();
            smsService.t_sendSMS(phone, "LOGIN_PASSWROD", data);

            //修改微信用户表userid
            whgUsrWeixin.setUserid(whUser.getId());
            Example exa2 = new Example(WhgUsrWeixin.class);
            exa2.createCriteria().andEqualTo("id", id);
            this.whgUsrWeixinMapper.updateByExampleSelective(whgUsrWeixin, exa2);
        }

        return code;
    }

    /**
     * 取消绑定手机
     * @param id id 微信账号ID whg_usr_xeixin.id
     * @param phone 手机号
     * @return 100-解绑成功；101-手机格式不正确; 102-参数id无效
     * @throws Exception
     */
    public String unbindPhone(String id, String phone)throws Exception{
        String code = "100";

        //手机格式不正确
        if(phone == null || !phone.matches("^1[3|4|5|7|8][0-9]\\d{8}$")){
            return "101";
        }

        //id有效
        WhgUsrWeixin whgUsrWeixin = whgUsrWeixinMapper.selectByPrimaryKey(id);
        if(whgUsrWeixin == null || whgUsrWeixin.getId() == null){
            return "102";
        }

        //解绑
        whgUsrWeixin.setUserid(null);
        Example exa2 = new Example(WhgUsrWeixin.class);
        exa2.createCriteria().andEqualTo("id", id);
        this.whgUsrWeixinMapper.updateByExample(whgUsrWeixin, exa2);

        return code;
    }

    /**
     * 微信注册
     * @param phone  手机号码
     * @param password  密码
     * @return 100-注册成功；101-手机格式不正确; 102-该号码已经存在
     */
    public String register(String phone, String password) throws Exception {
        String code = "100";
        //手机格式不正确
        if(phone == null || !phone.matches("^1[3|4|5|7|8][0-9]\\d{8}$")){
            return "101";
        }
        //根据phone找到会员
        Example example = new Example(WhUser.class);
        example.createCriteria().andEqualTo("phone", phone);
        List<WhUser> whuserList = this.whUserMapper.selectByExample(example);
        //没有用户
        if(whuserList != null && whuserList.size() > 0){
            //用户已经存在
            return "102";

        }else{
            //插入用户记录
            WhUser whUser = new WhUser();
            whUser.setId(commService.getKey("whuser"));
            String nickname = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
            whUser.setPhone(phone);
            whUser.setNickname(nickname);
            whUser.setIsrealname(0);
            whUser.setIsperfect(0);
            whUser.setIsinner(0);
            whUser.setPassword( password);
            this.whUserMapper.insert(whUser);
        }
        return code;
    }

    /**
     * 修改用户资料
     * @param whUser 用户信息
     * @throws Exception
     */
    public void saveUserInfo(WhUser whUser)throws  Exception{
        Example example = new Example(WhUser.class);
        example.createCriteria().andEqualTo("id", whUser.getId());
        this.whUserMapper.updateByExampleSelective(whUser, example);
    }
}
