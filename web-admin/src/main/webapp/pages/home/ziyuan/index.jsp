<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="author" content="">
    <title>广东省文化馆-个人文化展</title>
    <link href="${basePath }/static/assets/css/public/reset.css" rel="stylesheet">
    <link href="${basePath }/static/assets/css/libraryService/libraryList.css" rel="stylesheet">
    <!--[if lt IE 9] >
    <script src="${basePath }/static/assets/js/plugins/ie/IE9.js"></script>
    <! [endif]]-->
    <script src="${basePath }/static/assets/js/public/jquery-1.11.0.min.js"></script>
	<script src="${basePath }/static/assets/js/public/jquery-migrate-1.0.0.js"></script>
</head>
<body>

<!-- 公共头部开始 -->
<%@include file="/pages/comm/szheader.jsp"%>
<!-- 公共头部结束 -->

<!--广告开始-->
<div class="library-ad"></div>
<!--广告结束-->
<!--主体开始-->
<div class="list-row-1 clearfix">
	<div class="center-title">
    	<h2>最新动态</h2>
        <p>RECENT NEWS</p>
        <hr>
    </div>
    <div class="left-cont">
    	<div class="public-title">志愿者新闻</div>
        <div class="img">
        	<a href="#"><img src="${basePath }/static/assets/img/demoImg/7.jpg"></a>
        </div>
        <div class="public-container">
        	<ul>
            	<li><a href="#">文化志愿者大舞台第100期圆满举行</a></li>
                <li><a href="#">文化志愿者大舞台第100期圆满举行</a></li>
                <li><a href="#">文化志愿者大舞台第100期圆满举行</a></li>
                <li><a href="#">文化志愿者大舞台第100期圆满举行</a></li>
            </ul>
            <center><a href="#" class="more-center">查看更多</a></center>
        </div>
    </div>
    <div class="right-cont">
    	<div class="r-row-1">
        	<div class="public-title">热门动态<a href="#" class="more">MORE+</a></div>
             <div class="public-container">
                <ul>
                    <li><a href="#">文化志愿者大舞台第100期圆满举行</a><div class="timeCont"><span>10/31/</span>2016</div></li>
                    <li><a href="#">文化志愿者大舞台第100期圆满举行</a><div class="timeCont"><span>10/31/</span>2016</div></li>
                    <li><a href="#">文化志愿者大舞台第100期圆满举行</a><div class="timeCont"><span>10/31/</span>2016</div></li>
                    <li><a href="#">文化志愿者大舞台第100期圆满举行</a><div class="timeCont"><span>10/31/</span>2016</div></li>
                    <li><a href="#">文化志愿者大舞台第100期圆满举行</a><div class="timeCont"><span>10/31/</span>2016</div></li>
                </ul>
            </div>
        </div>
        <div class="r-row-2">
        	<div class="public-title">培训动态<a href="#" class="more">MORE+</a></div>
            <div class="public-container">
                <ul>
                    <li><a href="#">文化志愿者大舞台第100期圆满举行</a><div class="timeCont"><span>10/31/</span>2016</div></li>
                    <li><a href="#">文化志愿者大舞台第100期圆满举行</a><div class="timeCont"><span>10/31/</span>2016</div></li>
                    <li><a href="#">文化志愿者大舞台第100期圆满举行</a><div class="timeCont"><span>10/31/</span>2016</div></li>
                    <li><a href="#">文化志愿者大舞台第100期圆满举行</a><div class="timeCont"><span>10/31/</span>2016</div></li>
                    <li><a href="#">文化志愿者大舞台第100期圆满举行</a><div class="timeCont"><span>10/31/</span>2016</div></li>
                </ul>
            </div>
        </div>
    </div>
</div>
<div class="list-row-2 clearfix">
	<div class="center-title">
    	<h2>志愿者中心</h2>
        <p>VOLUNTEER CENTER</p>
        <hr>
    </div>
    <ul class="clearfix">
    	<li>
        	<a href="#" style="background:url(${basePath }/static/assets/img/demoImg/l1.jpg) no-repeat 50% 50%;">
            	<div class="txt">重要通知</div>
            	<div class="mask"></div>
                <div class="bigMask"></div>
            </a>
        </li>
        <li>
        	<a href="#" style="background:url(${basePath }/static/assets/img/demoImg/l2.jpg) no-repeat 50% 50%;">
            	<div class="txt">志愿者风采</div>
            	<div class="mask"></div>
                <div class="bigMask"></div>
            </a>	
        </li>
        <li class="last">
        	<a href="#" style="background:url(${basePath }/static/assets/img/demoImg/l3.jpg) no-repeat 50% 50%;">
            	<div class="txt">活动招募</div>
            	<div class="mask"></div>
                <div class="bigMask"></div>
            </a>
        </li>
        <li>
        	<a href="#" style="background:url(${basePath }/static/assets/img/demoImg/l4.jpg) no-repeat 50% 50%;">
            	<div class="txt">志愿组织</div>
            	<div class="mask"></div>
                <div class="bigMask"></div>
            </a>
        </li>
        <li>
        	<a href="#" style="background:url(${basePath }/static/assets/img/demoImg/l5.jpg) no-repeat 50% 50%;">
            	<div class="txt">服务项目</div>
            	<div class="mask"></div>
                <div class="bigMask"></div>
            </a>	
        </li>
        <li class="last">
        	<a href="#" style="background:url(${basePath }/static/assets/img/demoImg/l6.jpg) no-repeat 50% 50%;">
            	<div class="txt">培训计划</div>
            	<div class="mask"></div>
                <div class="bigMask"></div>
            </a>
        </li>
        <li>
        	<a href="#" style="background:url(${basePath }/static/assets/img/demoImg/l7.jpg) no-repeat 50% 50%;">
            	<div class="txt">培训动态</div>
            	<div class="mask"></div>
                <div class="bigMask"></div>
            </a>
        </li>
        <li>
        	<a href="#" style="background:url(${basePath }/static/assets/img/demoImg/l8.jpg) no-repeat 50% 50%;">
            	<div class="txt">网上课堂</div>
            	<div class="mask"></div>
                <div class="bigMask"></div>
            </a>	
        </li>
        <li class="last">
        	<a href="#" style="background:url(${basePath }/static/assets/img/libraryService/more.png) no-repeat 50% 50%;"> </a>
        </li>
        
    </ul>
</div>
<!--主体结束-->

<!--底部开始-->
<%@include file="/pages/comm/szfooter.jsp"%>
<!--底部结束--> 

<!-- core public JavaScript -->
<script src="${basePath }/static/assets/js/libraryService/libraryList.js"></script>
</body>
</html>