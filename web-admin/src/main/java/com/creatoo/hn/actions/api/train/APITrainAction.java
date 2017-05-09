package com.creatoo.hn.actions.api.train;

import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.model.WhgTraEnrol;
import com.creatoo.hn.services.home.agdpxyz.PxbmService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 提供培训报名接口
 * Created by wangxl on 2017/4/12.
 */
@RestController
@RequestMapping("/api/tra")
public class APITrainAction {
    /**
     * 日志控制器
     */
    Logger log = Logger.getLogger(this.getClass().getName());

    /**
     * 报名服务
     */
    @Autowired
    private PxbmService service;

    /**
     * 检查能否报名
     * 访问路径 /api/tra/check/{traId}/{userId}
     * @param traId 培训ID
     * @param userId 用户ID
     * @return JSON: {
     * "success" : "1"                        //1表示可以报名，其它失败
     * "errormsg" : "100|101|102|103|104"     //100-培训已失效; 101-培训报名已结束; 102-培训报名额已满; 103-已经报名不能重报; 104-未实名认证
     * }
     */
    @CrossOrigin
    @RequestMapping("/check/{traId}/{userId}")
    public ResponseBean check(@PathVariable("traId")String traId, @PathVariable("userId")String userId){
        ResponseBean res = new ResponseBean();
        try{
            String validCode = this.service.checkApplyTrain(traId, userId);
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
     * 报名(POST提交数据)
     * 访问路径 /api/tra/enrol
     * @param traid        -培训ID(whg_tra.id)
     * @param realname     -真实姓名
     * @param birthday     -出生年月日（yyyy-MM-dd）
     * @param sex          -性别(2-女； 1-男; 3-保密)
     * @param certtype     -证件类型(1-身份证,2-户口本,其它)
     * @param cardno       -证件号码
     * @param contactphone -联系手机号码
     * @param userId        -报名的用户Id
     * @return JSON : {
     * "success" : "1"                        //1表示报名成功，其它失败
     * "errormsg" : "100|101|102|103|104|105|106|107"
     *   //100-培训已失效; 101-培训报名已结束; 102-培训报名额已满; 103-已经报名不能重报; 104-未实名认证; 105-报名失败 106-年龄段不符合培训要求 107-已报两次普及班
     * }
     */
    @CrossOrigin
    @RequestMapping(value = "/enrol", method = RequestMethod.POST)
    public ResponseBean enroll(WhgTraEnrol enrol, String userId){
        ResponseBean res = new ResponseBean();
        try{
            String validCode = this.service.checkApplyTrain(enrol.getTraid(), userId);
            if(!"0".equals(validCode)){
                res.setSuccess(ResponseBean.FAIL);
                res.setErrormsg(validCode);
            }else{
                try{
                    String birthday = new java.text.SimpleDateFormat("yyyy-MM-dd").format(enrol.getBirthday());
                    validCode = service.validAge(enrol, birthday, userId);
                    if(!"0".equals(validCode)) {
                        res.setSuccess(ResponseBean.FAIL);
                        res.setErrormsg(validCode);
                    }else{
                        service.addTranEnrol(enrol, "", userId);
                    }
                }catch (Exception e){
                    res.setSuccess(ResponseBean.FAIL);
                    res.setErrormsg(e.getMessage());
                }
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
        }
        return res;
    }

    /**
     *  培训订单号查询签名列表
     *  访问路径 /api/tra/signList/{orderId}
     * @param orderId 用户的培训订单号whg_tra_enrol.orderid
     * @return JSON : {
     * "success" : "1"                        //1表示请求成功，其它失败
     * "errormsg" : ""                        //请求失败时的异常信息
     *  "data": [
     *      {
     *          "traid":"培训ID",
     *          "enrolid":"报名ID",
     *          "courseid":"培训课程ID",
     *          "starttime":"课程开始时间yyyy-MM-dd HH:mm:ss",
     *          "endtime":"课程结束时间",
     *          "sign":"签到状态 0-未签到, 1-已签到"}
     *          "signtime":"签到时间yyyy-MM-dd HH:mm:ss",
     *          "userid":"签到会员ID"}
     *      ,...]                     //data=签名列表
     * }
     */
    @CrossOrigin
    @RequestMapping(value = "/signList/{orderId}")
    public ResponseBean signInfo(String orderId){
        ResponseBean res = new ResponseBean();
        try{
            List<Map<String, String>> eclist = this.service.queryEnrolCourseList(orderId);
            res.setData(eclist);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
        }
        return res;
    }

    /**
     * 培训课程签到
     * 访问路径 /api/tra/sign
     * @param trainId 培训ID whg_tra.id
     * @param enrolId 报名表ID whg_tra_enrol.id
     * @param courseId 培训报名中课程ID whg_tra_enrol_course.id
     * @param userId 用户ID wh_user.id
     * @return JSON ： {
     *  "success" : "1"             //1-签到成功, 其它签到失败
     *  "errormsg" : "100|101|102|103|109"             //100-签到成功; 101-已经签到; 102-已签到课程已取消; 109-签到异常
     * }
     */
    @CrossOrigin
    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    public ResponseBean signUp(String trainId, String enrolId, String courseId, String userId){
        ResponseBean res = new ResponseBean();
        try{
            String code = this.service.signup(trainId, enrolId, courseId, userId);
            if(!"100".equals(code)){
                res.setSuccess(ResponseBean.FAIL);
            }
            res.setErrormsg(code);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg("109");
        }
        return res;
    }


    /**
     * 所有日期类型使用指定格式转换
     * @param webDataBinder
     * @throws Exception
     */
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Date.class , new CustomDateEditor(simpleDateFormat , true));
    }
}
