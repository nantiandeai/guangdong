<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<link href="${basePath }/static/assets/css/museum/guide.css" rel="stylesheet">
<title>广东省文化馆-资讯动态-公告</title>
<script type="text/javascript">
/** page onload event */
$(function(){
	//加载分页工具栏
	genPagging('whgPagging', '${page}' || 1, 10, '${total}', function(page, rows){
		window.location.href = '${basePath}/agdzxdt/index?page='+page+'&rows='+rows;
	});
});
</script>
</head>
<body>
<!-- 公共头部开始 -->
<%@include file="/pages/comm/agdtopsmall.jsp"%>
<!-- 公共头部结束-END -->

<!--公共主头部开始-->
<div id="header-fix">
    <div class="header-nav-bg">
    	<div class="header-nav">
        	<div class="logo-small">
            	<a href="${basePath }/home"><img src="${basePath }/static/assets/img/public/logoSmall.png"></a>
            </div>
            <ul>
                <li class="active"><a href="${basePath }/agdzxdt/index">公告</a></li>
                <li><a href="${basePath }/agdzxdt/working">工作动态</a></li>
                <li><a href="${basePath }/agdzxdt/units">基层直击</a></li>
                <li class="last"><a href="${basePath }/agdzxdt/hot">热点聚焦</a></li>
              </ul>
        </div>
    </div>
</div>
<!--公共主头部结束-->

<!--资讯广告开始-->
<div class="public-information-ad" style="background:url(${basePath }/static/assets/img/information/adBanner1.jpg) no-repeat 50% 50%">
	<div class="posi-fix">
    	<div class="titleBg" style="background:url(${basePath }/static/assets/img/information/title-bg-1.png) no-repeat 50% 50%;"></div>
    </div>
</div>
<div class="public-information-container">
	<ul>
		<c:choose>
			<c:when test="${rows != null && fn:length(rows) > 0}">
				<c:forEach items="${rows }" var="row" varStatus="s">
				    <li>
			        	<i></i>
			        	<h2><a href="${basePath }/agdzxdt/noticeinfo?id=${row.clnfid}">${row.clnftltle }</a><span class="adr">-- 源自 ：<span>${row.clnfsource }</span></span><span class="time"><fmt:formatDate value="${row.clnfcrttime}" pattern="yyyy-MM-dd" /></span></h2>
		            	<p>${row.clnfintroduce }</p>
			        </li>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<!--空页面  -->
		    	<div class="public-no-message"></div>
		    	<!--空页面-END-->
			</c:otherwise>
		</c:choose>
    </ul>
    <div class="green-black" id="whgPagging"></div>
</div>
<hr class="strongHr">
<!--资讯广告结束-->

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
</body>
</html>