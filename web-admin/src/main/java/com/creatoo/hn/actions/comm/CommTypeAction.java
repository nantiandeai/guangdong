package com.creatoo.hn.actions.comm;

import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.services.comm.CommService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 分类
 * Created by wangxl on 2017/3/17.
 */
@RestController
@RequestMapping("/comm")
public class CommTypeAction {
    /**
     * 日志
     */
    Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private CommService commService;

    /**
     * 分类
     * @param request
     * @return
     */
    @RequestMapping("/type/srchList")
    public ResponseBean srchListType(HttpServletRequest request){
        ResponseBean res = new ResponseBean();
        try {
            res.setData(commService.findYwiType());
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }

    /**
     * 分类
     * @param request
     * @return
     */
    @RequestMapping("/tag/srchList")
    public ResponseBean srchListTag(HttpServletRequest request){
        ResponseBean res = new ResponseBean();
        try {
            res.setData(commService.findYwiTag());
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }

    /**
     * 关键字
     * @param request
     * @return
     */
    @RequestMapping("/key/srchList")
    public ResponseBean srchListKey(HttpServletRequest request){
        ResponseBean res = new ResponseBean();
        try {
            res.setData(commService.findYwiKey());
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }

    /**
     * 查询表的公共状态信息
     * @param request
     * @return
     */
    @RequestMapping("/state/srchList/{type}")
    public ResponseBean srchListState(HttpServletRequest request, @PathVariable("type")String type){
        ResponseBean res = new ResponseBean();
        try {
            res.setData(commService.findEnumState(type));
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }

    /**
     * 查询文化品牌信息
     * @param request
     * @return
     */
    @RequestMapping("/brand/srchList")
    public ResponseBean srchListBrand(HttpServletRequest request){
        ResponseBean res = new ResponseBean();
        try {
            res.setData(commService.findBrand());
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }

    /**
     * 查询文化馆信息
     * @param request
     * @return
     */
    @RequestMapping("/cult/srchList")
    public ResponseBean srchListCult(HttpServletRequest request){
        ResponseBean res = new ResponseBean();
        try {
            res.setData(commService.findCult());
        }catch (Exception e){
            res.setSuccess(ResponseBean.FAIL);
            res.setErrormsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return res;
    }
}
