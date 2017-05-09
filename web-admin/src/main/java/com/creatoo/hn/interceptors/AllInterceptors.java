package com.creatoo.hn.interceptors;

import com.alibaba.fastjson.JSON;
import com.creatoo.hn.actions.admin.system.AdminAction;
import com.creatoo.hn.ext.annotation.WhgOPT;
import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.model.WhgYwiNote;
import com.creatoo.hn.services.comm.CommPropertiesService;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.SpringContextUtil;
import com.creatoo.hn.utils.WhConstance;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by wangxl on 2017/4/7.
 */
public class AllInterceptors extends HandlerInterceptorAdapter {
    /**
     * 日志
     */
    Logger log = Logger.getLogger(this.getClass());

    /**
     * 配置文件服务
     */
    @Autowired
    private CommPropertiesService commPropertiesService;

    /**
     * 公共服务
     */
    @Autowired
    private CommService commService;

    /**
     * 获取所有请求映射标志
     */
    private static boolean initRequestMapping = false;

    /**
     * 所有请求映射
     */
    private List<HashMap<String, Object>> urlList = new ArrayList<HashMap<String, Object>>();

    /**
     * 获取所有请求映射方法
     */
    private void initRequestMappingMethod(HttpServletRequest request){
        if(!AllInterceptors.initRequestMapping){
            try{
                AdminAction adminAction = (AdminAction) SpringContextUtil.getApplicationContext().getBean("adminAction");
                urlList = adminAction.initRequestMappingMethod();
            }catch (Exception e){
                e.printStackTrace();
            }
            AllInterceptors.initRequestMapping = true;
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
        //对于所有请求，在上下文中设置静态图片访问地址
        request.getServletContext().setAttribute("imgServerAddr", commPropertiesService.getUploadLocalServerAddr());
        //记录操作日志
        writeOptNode(request, response, handler);
        return super.preHandle(request, response, handler);
    }

    /**
     * 记录操作日志
     * @param request 请求对象
     * @param response 响应对象
     * @param handler
     */
    private void writeOptNode(HttpServletRequest request, HttpServletResponse response, Object handler){
        try{
            //记录操作日志
            String curtURI = request.getRequestURI();
            int idx = curtURI.indexOf("/admin");
            if(idx > -1 && urlList != null){
                initRequestMappingMethod(request);
                curtURI = curtURI.substring(idx);
                for(HashMap<String, Object> map : urlList){
                    String thisURI = (String)map.get("url");
                    //System.out.println(thisURI);

                    //找到映射的类
                    if(thisURI.startsWith("/admin") && eq(thisURI, curtURI, request)){
                        //操作注解
                        Method clazz = (Method)map.get("class");
                        WhgOPT whgOPT = (WhgOPT)clazz.getAnnotation(WhgOPT.class);
                        if(whgOPT != null){
                            String[] opts = whgOPT.optDesc();
                            EnumOptType type = whgOPT.optType();
                            String[] valid = whgOPT.valid();

                            //获操作对象和操作说明
                            int optType = type.getValue();
                            String optDesc = "";
                            if(opts.length == 1){
                                optDesc = opts[0];
                            }else if(opts.length > 1){
                                for(int i=0; i<opts.length; i++){
                                    if(valid.length >= i){
                                        optDesc = valid(valid[i], opts[i], request);
                                        if(!"".equals(optDesc)){
                                            break;
                                        }
                                    }
                                }
                            }
                            if("".equals(optDesc)){//未根据注解找到操作说明
                                continue;
                            }

                            //从会话中获取操作用户
                            String adminid = ""; //管理员ID
                            String adminaccount = ""; //管理员账号
                            Object sessionObject = request.getSession().getAttribute("user");
                            if(sessionObject != null){
                                WhgSysUser sysUser = (WhgSysUser)sessionObject;
                                adminid = sysUser.getId();
                                adminaccount = sysUser.getAccount();
                            }else{
                                continue;
                            }

                            //请求参数
                            Map<String, String> allProp = new HashMap<String, String>();
                            Enumeration<String> enums = request.getParameterNames();
                            int count = 2;
                            while(enums.hasMoreElements()){
                                String pkey = enums.nextElement();
                                String pval = request.getParameter(pkey);
                                if(pval.length()> 100){
                                    pval = pval.substring(0,100)+"...";
                                }
                                //控制总长度要小于1000
                                int curtLen = pkey.length()+pval.length()+6;
                                if((count + curtLen) < 1020 ){
                                    count = count + curtLen;
                                    allProp.put(pkey, pval);
                                }
                            }
                            String optargs = JSON.toJSON(allProp).toString();

                            //System.out.println( "操作对象："+type.getName()+"    操作说明:"+optDesc +"        请求参数："+JSON.toJSON(allProp).toString());

                            //保存操作日志
                            try {
                                WhgYwiNote note = new WhgYwiNote();
                                note.setAdminid(adminid);
                                note.setAdminaccount(adminaccount);
                                note.setOpttype(optType);
                                note.setOptdesc(optDesc);
                                note.setOpttime(new Date());
                                note.setOptargs(optargs);
                                this.commService.saveOptNote(note);
                            }catch(Exception e){
                                log.error(e.getMessage(), e);
                            }
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 根据表达式获取操作说明
     * @param el 表达式
     * @param optDescArg 操作说明
     * @param request 请求对象
     * @return 操作说明,如果不满足el时，返回空字符串, 否则返回optDescArg
     */
    private String valid(String el, String optDescArg, HttpServletRequest request){
        String optDesc = "";

        if(el != null && !el.isEmpty()){
            if("true".equals(el.trim())){
                optDesc = optDescArg;
            }else{
                String[] arr = el.split("&&");//key1=val1,val2,val3&&key2=val4,val5

                boolean is = true;
                for(String _elc : arr){
                    String[] parr = _elc.split("=");
                    String pkey = parr[0];//属性名
                    String pval = parr[1];//属性值

                    //属性值真实值
                    String propVal =  request.getParameter(pkey);
                    if(propVal == null){
                        Object object = request.getAttribute("whg_"+pkey);
                        if(object != null){
                            propVal = (String) object;
                        }
                    }

                    //是否满足条件
                    boolean isc = false;
                    String[] pvals = pval.split(",");
                    for(String pvalsc : pvals){
                        if("null".equalsIgnoreCase(pvalsc.trim()) && (propVal == null || propVal.isEmpty())){
                            isc = true;
                        }else if("notnull".equalsIgnoreCase(pvalsc.trim()) && propVal != null){
                            isc = true;
                        }else if(pvalsc != null && propVal != null && pvalsc.trim().equals(propVal.trim()) ){
                            isc = true;
                        }
                    }

                    //是否全部满足条件
                    is = is && isc;
                }

                //是否满足EL
                if(is){
                    optDesc = optDescArg;
                }
            }
        }else{
            optDesc = optDescArg;
        }

        return optDesc;
    }

    /**
     * 请求连接是否匹配
     * @param uri1 配置好的映射URI
     * @param uri2 真实的URI
     * @return true-匹配， false-不匹配
     */
    private boolean eq(String uri1, String uri2, HttpServletRequest request){
        boolean is = false;

        if(uri1 != null && uri1.equals(uri2)){
            is = true;
        }else if(uri1 != null && uri2 != null){
            String[] uri1s = uri1.split("/");
            String[] uri2s = uri2.split("/");

            if(uri1s.length == uri2s.length){
                boolean isok = true;
                for(int i=0; i<uri1s.length; i++){
                    String _u1s = uri1s[i];
                    String _u2s = uri2s[i];

                    if(!_u1s.equals(_u2s) && !_u1s.startsWith("{") ){
                        isok = false;
                        break;
                    }

                    if(_u1s.startsWith("{") && _u1s.endsWith("}")){
                        String pkey = _u1s.substring(1,_u1s.length()-1);
                        request.setAttribute("whg_"+pkey,_u2s);
                    }
                }
                is = isok;
            }
        }

        return is;
    }
}
