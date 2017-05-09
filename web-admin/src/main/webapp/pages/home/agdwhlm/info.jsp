<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/pages/comm/agdhead.jsp" %>
    <title>广东省文化馆-文化馆联盟-</title>
    <link href="${basePath }/static/assets/css/fenguan/fenguan.css" rel="stylesheet">
    <link href="${basePath }/static/assets/js/plugins/oiplayer-master/css/oiplayer.css" rel="stylesheet">
</head>
<body class="oiplayer-example">

<!--公共主头部开始-->
<div id="header">
    <div class="main clearfix">
        <div class="gatalogMain">
            <div class="return">
                <a href="${basePath }/agdwhlm/index" class="btn">
                    <i class="kx-arrow kx-arrow-right">
                        <em></em>
                        <span></span>
                    </i>
                    返回文化馆主站
                </a>
            </div>
        </div>
    </div>
</div>
<!--公共主头部结束-->

<!--主体开始-->
<div class="special-bg" style="background:url(${imgServerAddr}/${cult.bgpicture}) no-repeat 50% 0;">
    <div class="header">
        <div class="logo-login clearfix">
            <div class="logo">
                <a href="#">
                    <img src="${imgServerAddr}/${cult.logopicture}" width="283" height="62">
                    <span>${cult.name}</span></a>
            </div>
        </div>
    </div>
    <div class="contents">
        <!--第一层开始-->
        <div class="row clearfix">
            <div class="left-wrapper page-wrapper">
                <div class="title">本馆简介</div>
                <div class="list">
                    <p>${cult.introduction}</p>
                </div>
            </div>
            <div class="right-wrapper page-wrapper">
                <div class="title">通知公告</div>
                <div class="list">
                    <ul>
                        <c:if test="${noticeList != null && fn:length(noticeList) > 0}">
                            <c:forEach items="${noticeList}" var="row" varStatus="s">
                                <li class="clearfix">
                                    <i></i>
                                    <p><a href="${basePath}/agdzxdt/noticeinfo?id=${row.clnfid}&type=${row.clnftype}">${row.clnftltle}</a></p>
                                    <span><fmt:formatDate value="${row.clnfcrttime}" pattern="yyyy-MM-dd"/></span>
                                </li>
                            </c:forEach>
                        </c:if>
                    </ul>
                </div>
                <a href="${basePath}/agdzxdt/index" class="lookmore">更多</a>
            </div>
        </div>
        <!--第一层结束-->

        <!--第二层开始-->
        <div class="row">
            <div class="page-wrapper">
                <div class="title">活动预告</div>
                <div class="wrapper-list clearfix">
                    <ul>
                        <c:if test="${actList != null && fn:length(actList) > 0}"><c:forEach items="${actList}" var="row" varStatus="s">
                        <li>
                            <div class="img">
                                <a href="${basePath }/agdwhhd/activityinfo?actvid=${row.id}">
                                    <img src="${imgServerAddr}/${row.imgurl}" width="275" height="183">
                                </a>
                            </div>
                            <div class="list-title">
                                <span><a href="${basePath }/agdwhhd/activityinfo?actvid=${row.id}">${row.name}</a></span>
                            </div>
                        </li>
                        </c:forEach></c:if>
                    </ul>
                </div>
                <a href="${basePath}//agdwhhd/activitylist?cultid=${row.id}" class="lookmore">更多</a>
            </div>
        </div>
        <!--第二层结束-->

        <!--第三层开始-->
        <div class="row">
            <div class="page-wrapper">
                <div class="title">培训驿站</div>
                <div class="wrapper-list clearfix">
                    <ul>
                        <c:if test="${traList != null && fn:length(traList) > 0}"><c:forEach items="${traList}" var="row" varStatus="s">
                            <li>
                                <div class="img">
                                    <a href="${basePath }/agdpxyz/traininfo?traid=${row.id}">
                                        <img src="${imgServerAddr}/${row.trainimg}" width="275" height="183">
                                    </a>
                                </div>
                                <div class="list-title">
                                    <span><a href="${basePath }/agdpxyz/traininfo?traid=${row.id}">${row.title}</a></span>
                                </div>
                            </li>
                        </c:forEach></c:if>
                    </ul>
                </div>
                <a href="${basePath }/agdpxyz/trainlist?cultid=${row.id}" class="lookmore">更多</a>
            </div>
        </div>
        <!--第三层结束-->

        <!--第四层开始-->
        <div class="row">
            <div class="page-wrapper">
                <div class="title">场馆预定</div>
                <div class="wrapper-list clearfix">
                    <ul>
                        <c:if test="${venList != null && fn:length(venList) > 0}"><c:forEach items="${venList}" var="row" varStatus="s">
                            <li>
                                <div class="img">
                                    <a href="${basePath }/agdcgfw/venueinfo?venid=${row.id}">
                                        <img src="${imgServerAddr }${row.imgurl}" width="275" height="183">
                                    </a>
                                </div>
                                <div class="list-title">
                                    <span><a href="${basePath }/agdcgfw/venueinfo?venid=${row.id}">${row.title}</a></span>
                                </div>
                            </li>
                        </c:forEach></c:if>
                    </ul>
                </div>
                <a href="${basePath }/agdcgfw/index?cultid=${row.id}" class="lookmore">更多</a>
            </div>
        </div>
        <!--第四层结束-->
    </div>
</div>
<!--主体结束-->

<!--底部开始-->
<div class="fg-footer">
    <%--<img src="${basePath }/static/assets/img/fenguan/fg-footer-bg.png" width="100%" height="450">--%>
    <p>Copyright 2005-2017 GDWH.GOV.CN All right reserved. 广东省文化馆 版权所有 粤ICP备16019011号-1</p>
</div>
<!--底部结束-->
<a class="to-top"></a>
</body>
</html>