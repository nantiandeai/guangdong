package com.creatoo.hn.actions.home.agdpxyz;

import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.model.WhgTraEnrol;
import com.creatoo.hn.services.home.agdpxyz.PxbmService;
import com.creatoo.hn.services.home.agdpxyz.PxyzService;
import com.creatoo.hn.utils.WhConstance;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 培训报名
 * Created by tangwei on 2017/4/6.
 */
@RestController
@RequestMapping("/agdpxyz")
public class PxbmAction {
    Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private PxbmService service;

    private final  String SUCCESS="0";//成功
    /**
     * 检查报名
     * @param req
     * @param trainId
     * @return
     */
    @RequestMapping("/checkTrainApply")
    @ResponseBody
    public String checkTrainApply(HttpServletRequest req,String trainId){
        WhUser userSess = (WhUser) req.getSession().getAttribute(WhConstance.SESS_USER_KEY);
        String checkResult = "";
        if(userSess == null){
            return "001";//用户绘画失效
        }
        try {
            checkResult = service.checkApplyTrain(trainId,userSess.getId());
        } catch (Exception e) {
            log.error(e.getMessage());
            return "002";
        }
        return checkResult;
    }
    /**
     * 跳转报名页
     * @param req
     * @param trainId
     * @return
     */
    @RequestMapping("/toTrainApply")
    @ResponseBody
    public ModelAndView toApply(HttpServletRequest req, String trainId, HttpSession session){
        ModelAndView view = new ModelAndView( "home/agdpxyz/trainApply" );
        try {
            if("".equals(session.getAttribute(WhConstance.SESS_USER_KEY)) || session.getAttribute(WhConstance.SESS_USER_KEY) == null){
                view.setViewName("/login");
                return view;
            }
            view.addObject("train",service.getTrainById(trainId));
            view.addObject("user",session.getAttribute(WhConstance.SESS_USER_KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    /**
     * 跳转到报名成功页
     * @param req
     * @param trainId
     * @return
     */
    @RequestMapping("/toTrainApplySuccess")
    @ResponseBody
    public ModelAndView toApplySuccess(HttpServletRequest req,String trainId){
        ModelAndView view = new ModelAndView( "home/agdpxyz/trainApplySuccess" );
        try {
            view.addObject("train",service.getTrainById(trainId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @RequestMapping("/saveTrainEnrol")
    @ResponseBody
    public String saveTrainEnrol(HttpServletRequest req, WhgTraEnrol enrol, String enrolBirthdayStr){
        WhUser userSess = (WhUser) req.getSession().getAttribute(WhConstance.SESS_USER_KEY);
        if(userSess == null){
            return "001";//用户绘画失效
        }
        String checkResult = "";
        try {
            checkResult = service.checkApplyTrain(enrol.getTraid(),userSess.getId());
            if(checkResult.equals(SUCCESS)){
                checkResult = service.validAge(enrol, enrolBirthdayStr,userSess.getId());
                if(checkResult.equals(SUCCESS)){
                    service.addTranEnrol(enrol,enrolBirthdayStr,userSess.getId());
                    return SUCCESS;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return "002";
        }
        return checkResult;
    }
}
