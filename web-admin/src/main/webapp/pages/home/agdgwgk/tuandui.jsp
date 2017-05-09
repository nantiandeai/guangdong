<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<link href="${basePath }/static/assets/css/museum/museum.css" rel="stylesheet">
<title>广东省文化馆-馆务公开-馆办团队</title>
<script type="text/javascript">
/** page onload event */
$(function(){
	//加载分页工具栏
	genPagging('whgPagging', '${page}' || 1, 10, '${total}', function(page, rows){
		window.location.href = '${basePath}/agdgwgk/tuandui?page='+page+'&rows='+rows;
	});
});
</script>
</head>

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
                <li><a href="${basePath }/agdgwgk/fagui">政策法规</a></li>
                <li><a href="${basePath }/agdgwgk/zhinan">业务指南</a></li>
                <li class="active"><a href="${basePath }/agdgwgk/tuandui">馆办团队</a></li>
                <li class="last"><a href="${basePath }/agdgwgk/fankui">意见反馈</a></li>
            </ul>
        </div>
    </div>
</div>
<!-- 二级栏目-END -->

<!--主体开始-->
<div class="main-info-bg main-info-no-padding main-info-bgColorW">
    <div class="main-info-container">
        <div class="team-list">
            <ul>
           	<c:choose>
			<c:when test="${rows != null && fn:length(rows) > 0}">
				<c:forEach items="${rows }" var="row" varStatus="s">
				    <li class="clearfix">
	                    <div class="img">
	                        <a href="${basePath }/agdgwgk/tuanduiinfo?id=${row.troupeid}">
	                        	<img src="${imgServerAddr }/${row.troupepic}" width="320" height="200">
	                        </a>
	                    </div>
	                    <div class="detail">
							<a href="${basePath }/agdgwgk/tuanduiinfo?id=${row.troupeid}">
	                        <h2>${row.troupename }&nbsp;</h2>
	                        <p>${row.troupedesc }&nbsp;</p>
	                        </a>
	                    </div>
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
            
            <!-- 分页 -->
			<div class="green-black" id="whgPagging"></div>
			<!-- 分页-END -->
			
        </div>
	</div>
</div>
<!--主体结束-->

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
<body>
</body>
</html>