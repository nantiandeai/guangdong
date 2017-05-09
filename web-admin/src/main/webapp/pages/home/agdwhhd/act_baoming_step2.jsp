<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆-活动报名</title>
<link href="${basePath }/static/assets/css/activity/activityDetaiil.css" rel="stylesheet">
<link href="${basePath }/static/assets/js/plugins/oiplayer-master/css/oiplayer.css" rel="stylesheet">
<link href="${basePath }/static/assets/css/activity/baoming.css" rel="stylesheet">

<script src="${basePath }/static/assets/js/plugins/sidebar/stickySidebar.js"></script>
<script src="${basePath }/static/assets/js/activity/activityDetail.js"></script>
<script src="${basePath }/static/assets/js/plugins/oiplayer-master/plugins/flowplayer-3.2.6.min.js"></script>
<script src="${basePath }/static/assets/js/plugins/oiplayer-master/js/jquery.oiplayer.js"></script>
<script src="${basePath }/static/assets/js/activity/seatOrder.js"></script>
<script src="${basePath }/static/assets/js/plugins/laydate.dev.min.js"></script>
<script type="text/javascript">
	function actDetail(){
		var actId = $("#actId").val();
		
	}

</script>
</head>
<body class="oiplayer-example">
<!-- 公共头部开始 -->
<%@include file="/pages/comm/agdtopsmall.jsp"%>
<!-- 公共头部结束-END -->

<!--公共主头部开始-->
<div id="header-fix">
	<div class="header-nav-bg">
		<div class="header-nav">
			<div class="logo-small">
				<a href="${basePath }/index"><img src="${basePath }/static/assets/img/public/logoSmall.png"></a>
			</div>
			<ul>
				<li><a href="${basePath }/agdwhhd/index">文化活动</a></li>
				<li class="active"><a href="${basePath }/agdwhhd/activitylist">活动预约</a></li>
				<%-- <li><a href="${basePath }/agdwhhd/notice">活动公告</a></li> --%>
				<li><a href="${basePath }/agdwhhd/news">活动资讯</a></li>
				<li class="last"><a href="${basePath }/agdwhhd/brandlist">品牌活动</a></li>
			</ul>
		</div>
	</div>
</div>
<!--公共主头部结束-END-->

<!--主体开始-->
<div class="main-info-bg bg-color">
   <ul class="crumbs crumbs-4 clearfix">
        <li class="step-1">1. 填写基本信息<em class="arrow"></em></li>
        <li class="step-2">2. 选择座位<em class="arrow"></em></li>
        <li class="step-3">3. 填写取票信息<em class="arrow"></em></li>
        <li class="step-4">4. 确认订单信息<em class="arrow"></em></li>
        <li class="step-5 last">5. 完成报名<em class="arrow"></em></li>
    </ul>
    <div class="container-wrapper">
        <div class="order-msg">
            <span class="msg-title">确认订单信息</span>
            <a href="#" class="return">返回上一级修改信息</a>
        </div>
        <div class="order-content clearfix">
            <div class="order-img">
                <img src="${imgServerAddr}${order.imgurl}" width="249" height="170">
            </div>
            <div class="order-detail">
                <h1>${order.name }</h1>
                <p><i class="iconfont icon-shijian"></i>时间：<span><fmt:formatDate value="${order.playdate }" pattern="yyyy-MM-dd"/>  ${order.playstime } </span></p>
                <p><i class="iconfont icon-dibiao"></i>地址：<span>${order.address }</span></p>
                <p><i class="iconfont icon-yqf-menpiao"></i>订票数：<span>${order.ordervotes}</span>张</p>
                <p><i class="iconfont icon-dianhua"></i>联系方式：<span>${order.orderphoneno }</span></p>
            </div>
        </div>
        <a href="#" class="submit">确认订单</a>
    </div>
</div>
<!--主体结束-->

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
</body>
</html>