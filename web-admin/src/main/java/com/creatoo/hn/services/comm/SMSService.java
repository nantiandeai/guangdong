package com.creatoo.hn.services.comm;

import com.creatoo.hn.ext.emun.EnumState;
import com.creatoo.hn.mapper.WhgYwiSmsMapper;
import com.creatoo.hn.mapper.WhgYwiSmstmpMapper;
import com.creatoo.hn.model.WhgYwiSms;
import com.creatoo.hn.model.WhgYwiSmstmp;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import tk.mybatis.mapper.entity.Example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 提供基本的短信服务
 * Created by wangxl on 2017/4/1.
 */
@Service
public class SMSService {
    /**
     * 日志
     */
    Logger log = Logger.getLogger(this.getClass());

    /**
     * 属性配置
     */
    @Autowired
    private CommPropertiesService commPropertiesService;

    /**
     * 短信模板
     */
    @Autowired
    private WhgYwiSmstmpMapper whgYwiSmstmpMapper;

    /**
     * 短信发送记录
     */
    @Autowired
    private WhgYwiSmsMapper whgYwiSmsMapper;

    /**
     * 公共服务
     */
    @Autowired
    private CommService commService;

    /**
     * 短信发送
     * @param phoneNumber 手机号
     * @param tempCode 模板号
     * @param data 模板参数
     * @throws Exception
     */
    public void t_sendSMS(String phoneNumber, String tempCode, Map<String, String> data)throws Exception{
        //手机号不能为空
        if(phoneNumber == null || !phoneNumber.matches("\\d{11}")){
            throw new Exception("手机号不能为空");
        }

        //短信模板编码不能为空
        WhgYwiSmstmp smstmp = new WhgYwiSmstmp();
        smstmp.setCode(tempCode);
        smstmp = this.whgYwiSmstmpMapper.selectOne(smstmp);
        if(smstmp == null || smstmp.getContent() == null){
            throw new Exception("模板编号["+tempCode+"]找不到对应的短信模板内容");
        }

        //发送短信
        String smsContent = this.processSmsTemplate(smstmp.getContent(), data);
        boolean isOK = this.gxtSendMessage(phoneNumber, smsContent);
        WhgYwiSms smsrecode = new WhgYwiSms();
        smsrecode.setId(commService.getKey("whg_ywi_sms"));
        smsrecode.setPhone(phoneNumber);
        smsrecode.setSenddate(new Date());
        smsrecode.setSendstate(EnumState.STATE_YES.getValue());
        smsrecode.setSmscontent(smsContent);
        this.whgYwiSmsMapper.insert(smsrecode);
    }

    /**
     * 解析短信模板
     * @param templateContent 模板内容
     * @param data 模板参数
     * @return 短信内容
     * @throws Exception
     */
    private String processSmsTemplate(String templateContent, Map<String, String> data)throws Exception{
        String rtnContent = templateContent;
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        String template = "smsWhgTemplate";
        stringLoader.putTemplate("smsWhgTemplate", templateContent);
        Configuration cfg =  new Configuration(Configuration.VERSION_2_3_23);
        cfg.setTemplateLoader(stringLoader);
        try {
            Template templateCon =cfg.getTemplate(template);
            StringWriter writer = new StringWriter();
            templateCon.process(data, writer);
            rtnContent = writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnContent;
    }


    /**
     * 吉信通发送短信
     * @param phoneNumber 手机号
     * @param content 内容
     * @return 是否发送成功
     */
    private boolean gxtSendMessage(String phoneNumber, String content){
        boolean isOK = false;
        String result= null;
        HttpURLConnection httpconn = null;
        BufferedReader rd = null;
        try {
            //动态获取配置参数
            String gxtUrl = commPropertiesService.getSmsUrl();
            String gxtId = commPropertiesService.getSmsUsername();
            String gxtPwd = commPropertiesService.getSmsPassword();

            StringBuilder sb = new StringBuilder();
            sb.append(gxtUrl);		//正式环境下的地址
            sb.append("id=").append(URLEncoder.encode(gxtId,"gb2312"));	//吉信通用户名
            sb.append("&pwd=").append(gxtPwd);	//吉信通登录密码
            sb.append("&to=").append(phoneNumber);	//接收的手机号码
            sb.append("&content=").append(URLEncoder.encode(content,"gb2312")); //短信模板
            sb.append("&time=").append("");	//短信发送时间

            URL url = new URL(sb.toString());
            httpconn = (HttpURLConnection) url.openConnection();
            rd = new BufferedReader(new InputStreamReader(httpconn.getInputStream()));
            result = rd.readLine();

            if(result != null && result.length() > 2 && "000".equals(result.substring(0,3))){
                result = "100";
                isOK = true;
            }
        }catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally{
            if(rd != null){
                try{rd.close();}catch (Exception e){}
            }
            if(httpconn!=null){
                httpconn.disconnect();
            }
        }
        return isOK;
    }

    public static void main(String[] args)throws  Exception {
        String phoneNumber = "136773928331";
        if(phoneNumber == null || !phoneNumber.matches("\\d{11}")){
            System.out.println("error");
        }else{
            System.out.println("success");

        }
        /*Map<String, String> map = new HashMap<String, String>();
        map.put("user", "wangxinlin");
        System.out.println( SMSService.processSmsTemplate("你好，${user}", map) );*/
    }

}
