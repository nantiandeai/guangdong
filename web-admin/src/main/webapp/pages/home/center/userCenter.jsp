<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); request.setAttribute("basePath", basePath); %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="author" content="">
<title>广东省文化馆-用户中心台</title>
<link href="${basePath }/static/assets/css/public/reset.css" rel="stylesheet">
<link href="${basePath }/static/assets/css/userCenter/userCenter.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/public/jquery-1.11.0.min.js"></script>
<script src="${basePath }/static/assets/js/public/jquery-migrate-1.0.0.js"></script>

<!--[if lt IE 9] >
<script src="${basePath }/static/assets/js/plugins/ie/IE9.js"></script> 
<! [endif]]-->
<script>
var basePath = '${basePath}';
</script>
</head>
<body>

<!-- 公共头部开始 -->
<%@include file="/pages/comm/agdtop.jsp"%>
<!-- 公共头部结束 -->

<!-- 公共绑定开始 -->
<%@include file="/pages/comm/comm_center.jsp"%>
<!-- 公共绑定结束 -->

<div class="main clearfix">
 	<div class="leftPanel">
    	<ul>
            <!--用户中心导航开始-->
			<%@include file="/pages/comm/ucnav.jsp"%>
			<!--用户中心导航结束-->
            
        </ul>
    </div>
    <div class="rightPanel">
    	<div class="qkJoin clearfix">
        	<!--<div class="qkBtn tuan already">
            	<a href="javascript:void(0)">已完成</a>
            </div>-->
            <!--如果已经升级，就加个already样式，改变文字内容为“已完成”-->
            <div class="qkBtn tuan">
            	<a href="javascript:void(0)">升级团队账号</a>
            </div>
            <div class="qkBtn qi">
            	<a href="javascript:void(0)">升级企业账号</a>
            </div>
        </div>
        <div class="msg none">没有找到您的消息，快去<a href="#" target="_blank">参加活动</a>吧</div>
        <!-- <div class="activity">
            <ul class="centered" >
                 <li class="item">
                    <h2><a href="">培训课程： 2016年度环卫工人未成年子女免费艺术培训</a></h2>
                    <p>讲师 :<span>谢东林</span></p>
                    <P>电话 :<span>13233334444</span></P>
                    <P>地址  :<span>广东省文化馆</span></P>
                    <P class="tickets">课程周期 :<span>2016-11-28 至 2016-12-16 </span></P>
                </li>
                <li class="item">
                    <h2><a href="">场馆预定： 菊园新区社区文化活动中心</a></h2>
                    <p>类型 :<span>体育馆</span></p>
                    <P>电话  :<span>0733-258652210</span></P>
                    <P>地址 :<span>广州市八一路3302号</span></P>
                    <P class="timeIn">预定时间段 :<span>2016-12-25 15:30-17:30</span></P>
                </li>
            </ul>
            
            <div class="arrow prev"></div>
            <div class="arrow next"></div>
        </div> -->
        <div class="scrollbar">
					<div class="handle"></div>
		</div>
    </div>
</div>

<!--底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--底部结束-->

<!-- core public JavaScript --> 
<script src="${basePath }/static/assets/js/public/sly.js"></script>
<script src="${basePath }/static/assets/js/plugins/tipso/tipso.js"></script>
<script src="${basePath }/static/assets/js/userCenter/public.js"></script>
<script src="${basePath }/static/assets/js/public/comm.js"></script>
<script src="${basePath }/static/assets/js/userCenter/userCenter.js"></script>
</body>
</html>