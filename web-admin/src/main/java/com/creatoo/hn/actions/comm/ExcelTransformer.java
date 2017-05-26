package com.creatoo.hn.actions.comm;

import com.creatoo.hn.model.WhgTraEnrol;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelTransformer {
    //公用导出方法
    public void preProcessing(HttpServletResponse response, String fileName) throws Exception {
        response.setContentType("application/x-download");
        SimpleDateFormat t = new SimpleDateFormat("yyyyMMddHHmmss");
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1") + t.format(new Date()) + ".xls");
        response.setBufferSize(2048);
    }

    public String state(WhgTraEnrol y){
        if(y.getState()==1) {
            return "已申请";
        } else if(y.getState()==2){
            return "取消报名";
        } else if(y.getState()==3){
            return "审核失败";
        }else if(y.getState()==4){
            return "等待面试";
        }else if(y.getState()==5){
            return "面试不通过";
        }else {
            return "报名成功";
        }
    }
}
