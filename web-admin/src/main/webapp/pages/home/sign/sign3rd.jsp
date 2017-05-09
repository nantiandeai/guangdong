<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆</title>
<link href="${basePath }/static/assets/css/sign/sign.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/plugins/tipso/tipso.js"></script> 
<script src="${basePath }/static/assets/js/sign/sign.js"></script>

<script type="text/javascript">
$(function(){
	//处理错误时跳到首页去
	var error = "${error}";
	if (error){
		rongDialog({
			type : false,
			title : error,
			time : 3*1000,
			url : "${basePath}"
		});
		return ;
	}
})
</script>
</head>
<body>
<!-- 公共头部开始 -->
<%@include file="/pages/comm/agdtop.jsp"%>
<!-- 公共头部结束 -->

<c:choose>
	<c:when test="${mark eq 'event' }">
		<h1 class="sign-title">《${event.actvtitle }》活动报名</h1>
	</c:when>
	
	<c:otherwise>
		<h1 class="sign-title">《${train.tratitle }》培训报名</h1>
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${train.traisattach eq 1 or event.actvisattach eq 1}">
	<ul class="crumbs crumbs-3rd clearfix">
	  <li class="step-1">1. 选择报名方式<em class="arrow"></em></li>
	  <li class="step-2">2. 上传报名资料<em class="arrow"></em></li>
	  <li class="step-3 last">3. 报名完成</li>
	</ul>
	</c:when>
	
	<c:otherwise>
	<ul class="crumbs2 crumbs2-2nd clearfix">
	  <li class="step-1">1. 选择报名方式<em class="arrow"></em></li>
	  <li class="step-2 last">2. 报名完成</li>
	</ul>
	</c:otherwise>
</c:choose>
<div class="main">
  <div class="main-cont">
	<div class="reg-msg">
        <div class="msg success">
            <h2><span></span>报名申请成功</h2>
            <p>恭喜您报名申请成功
            <c:if test="${not empty ismoney and ismoney eq 1}">
            	<br/>请在 5 小时内确认预定，超时将被取消预订
				<br/>联系人:${not empty train.tracontact?train.tracontact : '-'}
				<br/>电话:${not empty train.traphone ? train.traphone : event.actvphone}
            </c:if>
            <c:if test="${train.traisenrolqr eq 1 or event.actvisenrolqr eq 1}">
            	<br/>请耐心等待管理员审核 
           	</c:if>
            </p>
            <p>
            <c:choose>
            	<c:when test="${not empty event.actvid }">
            		<a href="${basePath }/agdwhhd/activityinfo?actvid=${event.actvid }">返回活动详情页</a>
            	</c:when>
            	<c:when test="${not empty train.traid }">
            		<a href="${basePath }/agdpxyz/traininfo?traid=${train.traid }">返回培训详情页</a>
            	</c:when>
            </c:choose>
            </p>
        </div>
        <div class="msg err"></div>
    </div>
  </div>
</div>

<!--底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--底部结束-->

</body>
</html>
