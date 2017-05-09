<%@ page import="com.creatoo.hn.utils.WhConstance" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    Object user = request.getSession().getAttribute(WhConstance.SESS_USER_KEY);
    if (user!=null){
        request.setAttribute("isSessUser", true);
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <%@include file="/pages/comm/agdhead.jsp"%>
    <title>场馆服务-${room.title }</title>
    <link href="${basePath }/static/assets/css/field/fieldOrder.css" rel="stylesheet">
    <link href="${basePath }/static/assets/js/plugins/oiplayer-master/css/oiplayer.css" rel="stylesheet">

    <script src="${basePath }/static/assets/js/plugins/oiplayer-master/plugins/flowplayer-3.2.6.min.js"></script>
    <script src="${basePath }/static/assets/js/plugins/oiplayer-master/js/jquery.oiplayer.js"></script>

    <script src="${basePath }/static/assets/js/field/fieldOrder.js"></script>

</head>
<body class="oiplayer-example">

<!-- 公共头部开始 -->
<%@include file="/pages/comm/agdtopsmall.jsp"%>
<!-- 公共头部结束-END -->

<div class="main-info-bg bg-color">
    <ul class="crumbs crumbs-4 clearfix">
        <li class="step-1">1. 填写基本信息<em class="arrow"></em></li>
        <li class="step-2">2. 预订人信息<em class="arrow"></em></li>
        <li class="step-3">3. 确认订单<em class="arrow"></em></li>
        <li class="step-4 last">4. 提交审核<em class="arrow"></em></li>
    </ul>
    <div class="container-wrapper">
        <div class="order-msg">
            <span class="msg-title">提交审核</span>
            <a href="${basePath}/agdcgfw/venroominfo?roomid=${room.id}" class="return">查看场地</a>
        </div>
        <div class="order-content clearfix">
            <div class="complete-order">
                <img src="${basePath }/static/assets/img/activity/complete-order.png" width="61" height="61">
            </div>
            <c:if test="${room.hasfees==1}">
                <div class="compltet-order-msg">
                    <p>恭喜您，${room.title} 预定订单提交成功</p>
                    <span>请耐心等待审核并及时与场地管理员联系，联系电话：${ven.phone} </span>
                </div>
            </c:if>

            <c:if test="${room.hasfees==0}">
                <div class="compltet-order-msg">
                    <p>恭喜您，${room.title} 预定订单提交成功</p>
                    <span>请耐心等待审核 ！</span>
                </div>
            </c:if>
        </div>
        <a href="${basePath}/center/myVenue" class="submit">查看场地预定</a>
    </div>
</div>


<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->

</body>
</html>
