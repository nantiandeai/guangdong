<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>广东省文化馆-个人文化展</title>
    <%@include file="/pages/comm/comm_head.jsp"%>
    
    <link href="${basePath }/static/assets/css/publicity/publicityindex.css" rel="stylesheet">
	<script src="${basePath }/pages/home/xuanchuan/lxwm.js"></script>
</head>
<body>

<!-- 公共头部开始 -->
<%@include file="/pages/comm/szheader.jsp"%>
<!-- 公共头部结束 -->

<!--广告开始-->
<div class="page-ad" style="background:url(${basePath }/static/assets/img/publicity/pub-banner.jpg) no-repeat 50% 50%"></div>
<!--广告结束-->

<!--主体开始-->
<div class="page-main clearfix">
	<div class="page-content clearfix">
       	<!-- 左边 -->
        <div class="page-left">
            <div class="title title1"><span>宣传栏</span></div>
            <ul>
            	<li url="${basePath }/xuanchuan/whdt"><div class="title title2"><span><i></i>文化动态</span></div></li>
                <li url="${basePath }/xuanchuan/gwgk"><div class="title title3"><span><i></i>馆务概况</span></div></li>
                <li url="${basePath }/xuanchuan/bszn"><div class="title title4"><span><i></i>办事指南</span></div></li>
                <li url="${basePath }/xuanchuan/ztzl"><div class="title title5"><span><i></i>专题专栏</span></div></li>
                <li url="${basePath }/xuanchuan/lxwm" class="active"><div class="title title6"><span><i></i>联系我们</span></div></li>

            </ul>
        </div>
        <!-- 左边 END -->
            
        <!-- 主体 -->
        <div class="page-right">
            <div class="page1 page5">
                 <!-- 子栏目 -->
                    <div class="page1-title">
                        <ul>
                            <li class="active">联系方式</li>
                            <li>网上咨询</li>
                            <li>问卷调查</li>
                            <li>意见反馈</li>
                        </ul>
                    </div>
                 <!-- 子栏目 end -->
                    <div class="page1-con">
                        <!--联系方式-->
                        <div class="page-con page5-con1 on">
                            <div class="contact clearfix">
                                <div class="left">
                                    <ul>
                                        <li class="clearfix"><span></span></li>
                                        <li class="clearfix">
                                            <div class="logo"><i
                                                    style="background-image: url(${basePath }/static/assets/img/publicity/mail.png)"></i>
                                            </div>
                                            <div class="detail">
                                                <span>电子邮箱：</span>
                                                <p>DONGGUAN002@HOT.COM</p>
                                            </div>
                                        </li>
                                        <li class="clearfix">
                                            <div class="logo"><i
                                                    style="background-image: url(${basePath }/static/assets/img/publicity/phone.png)"></i>
                                            </div>
                                            <div class="detail">
                                                <span>电话总机：</span>
                                                <p>0769-22847170
                                                </p>
                                            </div>
                                        </li>
                                        <li class="clearfix">
                                            <div class="logo"><i
                                                    style="background-image: url(${basePath }/static/assets/img/publicity/print.png)"></i>
                                            </div>
                                            <div class="detail">
                                                <span>传真号码：</span>
                                                <p>0769-22847170</p>
                                            </div>
                                        </li>
                                        <li class="clearfix">
                                            <div class="logo"><i
                                                    style="background-image: url(${basePath }/static/assets/img/publicity/golbe.png)"></i>
                                            </div>
                                            <div class="detail">
                                                <span>邮政编码：</span>
                                                <p>214005</p>
                                            </div>
                                        </li>
                                        <li class="clearfix">
                                            <div class="logo"><i
                                                    style="background-image: url(${basePath }/static/assets/img/publicity/location.png)"></i>
                                            </div>
                                            <div class="detail">
                                                <span>文化馆地址：</span>
                                                <p>广东省广东省市万江区鸿福西路6号</p>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                                <a href="javascript:void(0)">
                                    <div class="right"
                                         style="background-image: url(${basePath }/static/assets/img/publicity/map.jpg)"></div>
                                </a>
                            </div>
                            <div class="contact-2 clearfix">
                                <div class="logo"><i style="background-image: url(${basePath }/static/assets/img/publicity/bus.png)"></i></div>
                                <div class="detail">
                                    <span>交通指引：</span>
                                    <p>路线1：<span>公车可乘坐3路、14路、X13路到达“霸螺围”</span></p>
                                    <p>路线2：<span>乘坐2路、14路、19路、35路、601路、K1路、K2路K7路、X7路到达“曲海桥”</span></p>
                                </div>

                            </div>
                        </div>
                        
                        <!--网上咨询-->
                        <div class="page-con page5-con4">
                            <form method="post" id="ff_">
                            <div class="form-table">
                                <ul>
                                    <li class="clearfix">
                                        <div class="wenzi">咨询信息：</div>
                                        <div class="input1">
                                            <textarea type="text" name="feeddesc" placeholder="留下您的咨询信息 我们提供更好的服务..." maxlength="120"></textarea>
                                        </div>
                                    </li>
                                    <li class="clearfix">
                                        <div class="wenzi">姓名：</div>
                                        <div class="input1">
                                            <input type="text" name="feedname" placeholder="请输入联系人姓名" maxlength="10">
                                        </div>
                                    </li>
                                    <li class="clearfix">
                                        <div class="wenzi">单位：</div>
                                        <div class="input1">
                                            <input type="text" name="feedcom" placeholder="请输入贵公司联系地址" maxlength="20">
                                        </div>
                                    </li>
                                    <li class="clearfix">
                                        <div class="wenzi">手机号码：</div>
                                        <div class="input1">
                                            <input type="text" name="feedphone" placeholder="请输入联系人手机号码" maxlength="11"/>
                                        </div>
                                    </li>
                                    <li class="clearfix">
                                        <div class="wenzi">电子邮箱：</div>
                                        <div class="input1">
                                            <input type="text" name="feedmail" placeholder="请输入正确的邮箱地址">
                                        </div>
                                    </li>
                                    <li class="clearfix">
                                        <div class="wenzi">验证码：</div>
                                        <div class="input1">
                                            <input class="vfcode" name="feedyanzhen" type="text"placeholder="请输入验证码">
                                             <span><img src="${basePath }/authImage?t=1" alt="验证码" width="113" height="36" id="yanzhen1"></span>
                                        </div>
                                    </li>
                                    <li class="clearfix">
                                        <div class="wenzi"></div>
                                        <div class="input1">
                                            <input class="btn" type="button" value="提交" id="btn_" onclick="addzx();" >
                                        </div>
                                    </li>

                                </ul>
                            </div>
                          </form>
                        </div>
                        <!--问卷调查-->
                        <div class="page-con page5-con3">
                            <div class="wenjuan" style="background: url(${basePath }/static/assets/img/publicity/wenjuan.png) 50% 50% no-repeat"></div>
                        </div>
                        <!--意见反馈-->
                        <div class="page-con page5-con4">
                           <div class="img" style="background-image: url(${basePath }/static/assets/img/publicity/freeback.jpg)"></div>
                           <form method="post" id="ff">
                            <div class="form-table">
                                <ul>
                                    <li class="clearfix">
                                        <div class="wenzi">意见反馈：</div>
                                        <div class="input1">
                                            <textarea type="text" name="feeddesc" placeholder="留下您的意见 我们提供更好的服务..." maxlength="120"></textarea>
                                        </div>
                                    </li>
                                    <li class="clearfix">
                                        <div class="wenzi">姓名：</div>
                                        <div class="input1">
                                            <input type="text" name="feedname" placeholder="请输入联系人姓名" maxlength="10">
                                        </div>
                                    </li>
                                    <li class="clearfix">
                                        <div class="wenzi">单位：</div>
                                        <div class="input1">
                                            <input type="text" name="feedcom" placeholder="请输入贵公司联系地址" maxlength="20">
                                        </div>
                                    </li>
                                    <li class="clearfix">
                                        <div class="wenzi">手机号码：</div>
                                        <div class="input1">
                                            <input type="text" name="feedphone" placeholder="请输入联系人手机号码" maxlength="11"/>
                                        </div>
                                    </li>
                                    <li class="clearfix">
                                        <div class="wenzi">电子邮箱：</div>
                                        <div class="input1">
                                            <input type="text" name="feedmail" placeholder="请输入正确的邮箱地址">
                                        </div>
                                    </li>
                                    <li class="clearfix">
                                        <div class="wenzi">验证码：</div>
                                        <div class="input1">
                                            <input class="vfcode" type="text" name="feedyanzhen" placeholder="请输入验证码">
                                            <span><img src="${basePath }/authImage?t=1" alt="验证码" width="113" height="36" id="yanzhen"></span>
                                        </div>
                                    </li>
                                    <li class="clearfix">
                                        <div class="wenzi"></div>
                                        <div class="input1">
                                            <input class="btn" type="button" value="提交" id="btn_" onclick="addyj();" >
                                        </div>
                                    </li>

                                </ul>
                            </div>
                          </form>
                        </div>
                    </div>

                </div>
    	</div>
    	<!-- 主体 -END-->
	</div>
</div>
<!--主体结束-->

<!--底部开始-->
<%@include file="/pages/comm/szfooter.jsp"%>
<!--底部结束--> 

</body>
</html>