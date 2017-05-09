<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆-志愿服务-志愿公告</title>
<link href="${basePath }/static/assets/css/train/notice.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/train/notice.js"></script>
<script type="text/javascript">
/** page onload event */
$(function(){
	//加载分页工具栏
	genPagging('whgPagging', '${page}' || 1, 10, '${total}', function(page, rows){
		window.location.href = '${basePath}/agdfyzg/notice?page='+page+'&rows='+rows;
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
            <div class="logo-small"> <a href="${basePath }/home"><img src="${basePath }/static/assets/img/public/logoSmall.png"></a> </div>
            <ul>
                <li><a href="${basePath }/agdzyfw/index">志愿服务</a></li>
                <li class="active"><a href="${basePath }/agdzyfw/notice">志愿公告</a></li>
                <li><a href="${basePath }/agdzyfw/news">志愿资讯</a></li>
                <li><a href="${basePath }/agdzyfw/huodong">志愿活动</a></li>
                <li><a href="${basePath }/agdzyfw/peixun">志愿培训</a></li>
                <li><a href="${basePath }/agdzyfw/xiangmu">项目示范</a></li>
                <li><a href="${basePath }/agdzyfw/xiangmu">风采展示</a></li>
                <li class="last add-nav">
                	<div class="nav-more">
                    	<div><a href="${basePath }/agdzyfw/tashan">他山之石</a></div>
                        <%-- <div><a href="${basePath }/agdzyfw/lilun">理论探索</a></div> --%>
                        <div><a href="${basePath }/agdzyfw/zhengce">政策法规</a></div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>
<!--公共主头部结束-END-->

<!--主体开始-->
<div class="main-info-bg main-info-no-padding main-info-bgColorW">
	<div class="main-info-container">
    	<div class="main-noticeList-container">
	    	<!-- 资讯列表 -->
    		<ul>
    			<c:if test="${rows != null && fn:length(rows) > 0}">
    				<c:forEach items="${rows }" var="row" varStatus="s">
			        	<li>
			            	<div class="timeCont">
			                	<p class="month"><fmt:formatDate value="${row.clnfcrttime}" pattern="MM-dd" /></p>
			                    <p class="year"><fmt:formatDate value="${row.clnfcrttime}" pattern="yyyy" /></p>
			                	<div class="circular"></div>
			                </div>
			                <div class="notice-list-info">
			                	<div class="arrow"></div>
			                    <div class="info">
			                    	<h2><a href="${basePath }/agdzyfw/noticeinfo?id=${row.clnfid}">${row.clnftltle }</a></h2>
			                        <p>${row.clnfintroduce }</p>
									<span class="more"><a href="${basePath }/agdzyfw/noticeinfo?id=${row.clnfid}">查看详情</a></span>
			                    </div>
			                </div>
			            </li>
	            	</c:forEach>
	            </c:if>
        	</ul>
        	<!-- 资讯列表-END -->
        
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
</body>
</html>