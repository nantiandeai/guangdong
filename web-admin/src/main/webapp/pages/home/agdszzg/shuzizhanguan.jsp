<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <%@include file="/pages/comm/agdhead.jsp"%>
    <title>广东省数字文化馆-数字展馆</title>
    <link href="${basePath}/static/assets/css/resource/shuzizhanguan.css" rel="stylesheet">
    <script src="${basePath}/static/assets/js/plugins/roll/jquery.sly.min.js" type="text/javascript"></script>
    <script src="${basePath}/static/assets/js/science/science.js"></script>
    <script src="${basePath}/static/assets/js/resource/zoompic.js"></script>
    <script src="${basePath}/static/assets/js/resource/shuzizhanguan.js"></script>

</head>
<body>
<!--公共主头部开始-->
<%@include file="/pages/comm/agdtopsmall.jsp"%>

<div id="header-fix">
    <div class="header-nav-bg">
        <div class="header-nav">
            <div class="logo-small"> <a href="${basePath}/home"><img src="${basePath}/static/assets/img/public/logoSmall.png"></a> </div>
            <ul>
                <li class="active last"><a href="${basePath }/agdszzg/index">数字展馆</a></li>
                <%--<li class="last"><a href="${basePath }/agdszzg/list">赛事活动</a></li>--%>
            </ul>
        </div>
    </div>
</div>
<!--公共主头部结束-->
<!--主体开始-->
<div class="banner-content">
    <div class="title">
    </div>

    <div id="focus_Box">
        <span class="prev">&nbsp;</span>
        <span class="next">&nbsp;</span>
        <ul>
            <c:choose>
                <c:when test="${exh != null && fn:length(exh) > 0}">
                    <c:forEach items="${exh}" var="row" varStatus="s" begin="0" end="5">
                        <li>
                            <a href="${basePath}/agdszzg/info?exhid=${row.exhid}"><img width="445" height="308" alt="这个时代 你所追求的是什么？" src="${imgServerAddr}/${row.exhpic}"/></a>
                        </li>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <li>
                        <a href="../index.html"><img width="445" height="308" alt="这个时代 你所追求的是什么？" src="http://www.17sucai.com/preview/11/2013-05-02/ZoomPic/images/1.jpg" /></a>
                    </li>
                    <li>
                        <a href="../index.html"><img width="445" height="308" alt="我们所追求的不是拥有一切，而是拥有值得的一切" src="http://www.17sucai.com/preview/11/2013-05-02/ZoomPic/images/2.jpg" /></a>
                    </li>
                    <li>
                        <a href="../index.html"><img width="445" height="308" alt="一段旅程，两个城市，潮流正在被重塑" src="http://www.17sucai.com/preview/11/2013-05-02/ZoomPic/images/3.jpg" /></a>
                    </li>
                    <li>
                        <a href="../index.html"><img width="445" height="308" alt="你的眼光、激情、创意和内涵，也在重塑着城市的潮流" src="http://www.17sucai.com/preview/11/2013-05-02/ZoomPic/images/4.jpg" /></a>
                    </li>
                    <li>
                        <a href="../index.html"><img width="445" height="308" alt="在这里，抛开重重限制，释放真实自我" src="http://www.17sucai.com/preview/11/2013-05-02/ZoomPic/images/5.jpg" /></a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>

<div class="sshd-main">
    <div class="title">
        <div class="more">
            <a href="${basePath }/agdszzg/list">MORE</a>
        </div>
    </div>
    <div class="info">
        <ul class="l">
            <c:if test="${not empty exh}">
            <c:forEach items="${exh}" var="row" varStatus="s" begin="0" end="3">
                <c:if test="${s.count%2==1}">
                <li>
                    <div class="ss-main">
                        <a href="${basePath}/agdszzg/info?exhid=${row.exhid}"></a>
                        <div class="img">
                            <img src="${imgServerAddr}/${row.exhpic}">
                        </div>
                        <h2>${row.exhtitle}</h2>
                        <p class="t">时间：<fmt:formatDate value="${row.exhstime}" pattern="yyyy-MM-dd"/> </p>
                        <c:choose>
                            <c:when test="${fn:length(row.exhdesc) > 100}">
                                <p class="i">${fn:substring(row.exhdesc, 1, 100)}......</p>
                            </c:when>
                            <c:otherwise>
                                <p class="i">${row.exhdesc}</p>
                            </c:otherwise>
                        </c:choose>
                        <em></em>
                    </div>
                </li>
                </c:if>
            </c:forEach>
            </c:if>
        </ul>
        <ul class="r">
            <c:forEach items="${exh}" var="row" varStatus="s" begin="0" end="3">
                <c:if test="${s.count%2==0}">
                <li>
                    <div class="ss-main">
                        <a href="${basePath}/agdszzg/info?exhid=${row.exhid}"></a>
                        <div class="img">
                            <img src="${imgServerAddr}/${row.exhpic}">
                        </div>
                        <h2>${row.exhtitle}</h2>
                        <p class="t">时间：<fmt:formatDate value="${row.exhstime}" pattern="yyyy-MM-dd"/> </p>
                        <c:choose>
                            <c:when test="${fn:length(row.exhdesc) > 100}">
                                <p class="i">${fn:substring(row.exhdesc, 1, 100)}......</p>
                            </c:when>
                            <c:otherwise>
                                <p class="i">${row.exhdesc}</p>
                            </c:otherwise>
                        </c:choose>
                        <em></em>
                    </div>
                </li>
                </c:if>
            </c:forEach>
        </ul>
    </div>
</div>

<!--主体结束-->

<!--底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--底部结束-->
<a class="to-top"></a>
</body>
<script>
    console.log(${ech});
</script>
</html>