<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="author" content="">
<title>广东省文化馆-精品文化展</title>
<%@include file="/pages/comm/comm_head.jsp"%>
<link href="${basePath }/static/assets/css/art/recommendList.css" rel="stylesheet">
<script src="${basePath }/pages/home/art/jpwhz.js"></script>
</head>
<body>

<!-- 公共头部开始 -->
<%@include file="/pages/comm/szheader.jsp"%>
<!-- 公共头部结束 -->

<!--广告开始-->
<div class="art-ad" style="background:url(${basePath }/static/assets/img/art/banner.jpg) no-repeat 50% 50%"></div>
<!--广告结束-->

<!--导航开始-->
<div class="art-nav">
	<div class="main">
    	<ul class="clearfix" id="artMainPageNav">
        	<li class="active"><a href="${basePath }/art/jpwhz">精品文化展</a><span>/</span></li>
            <li><a href="${basePath }/art/grzpz">个人文化展</a><span>/</span></li>
            <li><a href="${basePath }/art/ystd">艺术团队</a><span>/</span></li>
            <li><a href="${basePath }/art/dzzz">电子杂志</a><span>/</span></li>
            <li><a href="${basePath }/art/szzy">数字资源</a></li>
        </ul>
    </div>
</div>
<!--导航结束-->

<!--主体开始-->
<div class="art-main clearfix">
	<div class="left-bar">
    	<h2><span>分类</span></h2>
        <ul id="artTypeNav">
        	
        	<li><i></i><a href="#" arttyp="">全部</a></li>
        	<c:forEach items="${artList }" var="art" varStatus="s">
	              	<li<c:if test="${s.last }"> class="last"</c:if>><i></i><a href="#" arttyp="${art.typid }">${art.typname }</a></li>
            </c:forEach>
        	
        </ul>
        <div class="search-cont">
        	<h3>搜索</h3>
            <div class="search-border" id="artSearch">
            	<input placeholder="输入关键字搜索...">
            </div>
        </div>
    </div>
    <div class="art-right-main">
    	<!-- 内容区 -->
    	<ul class="recommendList" id="artContent"></ul>
        <!-- 内容区结束 -->
        
        <!-- 内容分页 -->
        <div class="green-black" id="artPagging"></div>
        <!-- 内容分页结束 -->
        
    </div>
</div>
<!--主体结束-->

<!--底部开始-->
<%@include file="/pages/comm/szfooter.jsp"%>
<!--底部结束--> 

 
</body>
</html>