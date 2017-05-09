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
    <script type="text/javascript">
        /** 分页 */
        $(function(){
            //加载分页工具栏
            genPagging('whgPagging', '${page}' || 1, 10, '${total}', function(page, rows){
                window.location.href = '${basePath}/agdszzg/list?page='+page+'&rows='+rows;
            });
        });
    </script>
</head>
<body>
<!--公共主头部开始-->
<%@include file="/pages/comm/agdtopsmall.jsp"%>

<div id="header-fix">
    <div class="header-nav-bg">
        <div class="header-nav">
            <div class="logo-small"> <a href="${basePath}/home"><img src="${basePath}/static/assets/img/public/logoSmall.png"></a> </div>
            <ul>
                <li class="last"><a href="${basePath }/agdszzg/index">数字展馆</a></li>
                <%--<li class="active"><a href="${basePath }/agdszzg/list">赛事活动</a></li>--%>
            </ul>
        </div>
    </div>
</div>
<!--公共主头部结束-->
<!--主体开始-->
<div class="sshd-main" style="margin-top:140px;">
    <div class="info clearfix" style="height:auto; margin-bottom:50px;">
        <ul class="l">
            <c:if test="${not empty exh}">
                <c:forEach items="${exh}" var="row" varStatus="s">
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
            <c:forEach items="${exh}" var="row" varStatus="s" >
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
    <!-- 分页 -->
    <div class="green-black" id="whgPagging"></div>
    <!-- 分页-END -->


</div>

<!--主体结束-->

<!--底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--底部结束-->
<a class="to-top"></a>
</body>
</html>