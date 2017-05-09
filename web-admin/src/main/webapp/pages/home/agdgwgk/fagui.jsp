<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<link href="${basePath }/static/assets/css/museum/guide.css" rel="stylesheet">
<title>广东省文化馆-馆务公开-政策法规</title>
<script type="text/javascript">
/** page onload event */
$(function(){
	//加载分页工具栏
	genPagging('whgPagging', '${page}' || 1, 8, '${total}', function(page, rows){
		window.location.href = '${basePath}/agdgwgk/fagui?page='+page+'&rows='+rows;
	});
});
</script>
</head>
<body>

<!-- 公共头部开始 -->
<%@include file="/pages/comm/agdtopsmall.jsp"%>
<!-- 公共头部结束-END -->

<!-- 二级栏目 -->
<div id="header-fix">
    <div class="header-nav-bg">
        <div class="header-nav">
            <div class="logo-small">
                <a href="${basePath }/home"><img src="${basePath }/static/assets/img/public/logoSmall.png"></a>
            </div>
            <ul>
                <li><a href="${basePath }/agdgwgk/index">省馆介绍</a></li>
                <li><a href="${basePath }/agdgwgk/jigou">组织机构</a></li>
                <li class="active"><a href="${basePath }/agdgwgk/fagui">政策法规</a></li>
                <li><a href="${basePath }/agdgwgk/zhinan">业务指南</a></li>
                <li><a href="${basePath }/agdgwgk/tuandui">馆办团队</a></li>
                <li class="last"><a href="${basePath }/agdgwgk/fankui" >意见反馈</a></li>
            </ul>
        </div>
    </div>
</div>
<!-- 二级栏目-END -->

<!--资讯广告开始-->
<div class="public-information-ad" style="background:url(${basePath }/static/assets/img/museum/adBanner2.jpg) no-repeat 50% 50%">
	<div class="posi-fix">
    	<div class="titleBg" style="background:url(${basePath }/static/assets/img/museum/title-bg-2.png) no-repeat 50% 50%;"></div>
    </div>
</div>
<!--资讯广告结束-END-->

<!-- 政策法规 -->
<div class="public-information-container">
	<ul>
		<c:choose>
			<c:when test="${rows != null && fn:length(rows) > 0}">
				<c:forEach items="${rows }" var="row" varStatus="s">
				    <li>
			        	<i></i>
			        	<h2><a href="${basePath }/agdgwgk/faguiinfo?id=${row.clnfid}">${row.clnftltle }</a></h2>
			            <p>${row.clnfintroduce}</p>
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
    
    <!-- 分页栏 -->
    <div class="green-black" id="whgPagging"></div>
	<!-- 分页栏-END -->
	
</div>
<hr class="strongHr">
<!-- 政策法规-END -->

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
</body>
</html>