package com.creatoo.hn.actions.home.agdwhhd;

import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.model.*;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.home.agdwhhd.WhppService;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 文化品牌
 * Created by wangxl on 2017/4/8.
 */
@RestController
@RequestMapping("/agdwhhd")
public class WhppAction {
    /**
     * 日志控制器
     */
    Logger log = Logger.getLogger(this.getClass());

    /**
     * 文化品牌服务
     */
    @Autowired
    private WhppService whppService;
    /**
     * 公共服务层
     */
    @Autowired
    public CommService commservice;

    /**
     * 品牌活动列表页 加载相关数据
     * @param request
     * @return
     */
    @RequestMapping("/srchbrandList")
    public ResponseBean ppactivityList(HttpServletRequest request, WhgYwiWhpp whpp){
        ResponseBean res = new ResponseBean();
        try {
            PageInfo<WhgYwiWhpp> pageInfo = this.whppService.t_search4p(request, whpp);

            List<WhgYwiWhpp> whppList = pageInfo.getList();
            if(whppList != null){
                for(WhgYwiWhpp _whpp : whppList){
                    String coursedesc = _whpp.getIntroduction();
                    if(coursedesc != null) {
                        coursedesc = coursedesc.trim();
                        coursedesc = coursedesc.replaceAll("<[^>]*>", "");
                        if (coursedesc.length() > 120) {
                            coursedesc = coursedesc.substring(0, 120) + "...";
                        }
                        _whpp.setIntroduction(coursedesc);
                    }
                }
            }

            res.setRows(pageInfo.getList());//列表
            res.setTotal(pageInfo.getTotal());//总页数
            res.setPage(pageInfo.getPageNum());//当前页
            res.setPageSize(pageInfo.getPageSize());//每页数
        } catch (Exception e) {
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
        }
        return res;
    }








    /**
     * 品牌活动详情
     * @return 品牌活动详情
     */
    @RequestMapping("/brandinfo")
    public ModelAndView brandinfo(String braid, WebRequest request){
        ModelAndView view = new ModelAndView( "home/agdwhhd/brandinfo" );
        try {
            //获取品牌活动详情
            WhgYwiWhpp brand = this.whppService.getWhpp(braid);
            view.addObject("brand", brand);

            //文化品牌活动
            List<WhgActActivity> actList = this.whppService.findActActivity(braid);
            view.addObject("actList", actList);

            //关联培训
            List<WhgTra> traList = this.whppService.findTra(braid);
            view.addObject("traList", traList);

            //文化品牌活动图片
//            Map<String,List<WhgComResource>> map = this.whppService.findCommResource(braid);
//            view.addObject("imgList", map.get("img"));
//
//            //文化品牌活动视频
//            view.addObject("vedioList", map.get("video"));
//
//            //文化品牌活动音频
//            view.addObject("audioList", map.get("audio"));

            //文化品牌活动图片
            List<WhgComResource> pic = this.commservice.findRescource("1","13", braid);
            view.addObject("imgList",pic );
            //视频
            List<WhgComResource> vido = this.commservice.findRescource("2","13", braid);
            view.addObject("vedioList",vido );
            //音频
            List<WhgComResource> musci = this.commservice.findRescource("3","13", braid);
            view.addObject("audioList",musci );


        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return view;
    }
}
