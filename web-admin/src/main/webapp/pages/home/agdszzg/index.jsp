<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆-数字展览</title>
<link href="${basePath }/static/assets/css/science/science.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/science/science.js"></script>
<script type="text/javascript" src="${basePath }/pages/home/agdszzg/index.js"></script>
</head>
<body>
<!-- 头部开始 -->
<%@include file="/pages/comm/agdtopsmall.jsp"%>
<!-- 头部结束-END -->

<!--公共主头部-->
<div id="header-fix">
	<div class="header-nav-bg">
		<div class="header-nav">
			<div class="logo-small">
				<a href="${basePath }/home"><img src="${basePath }/static/assets/img/public/logoSmall.png"></a>
			</div>
			<ul>
				<li class="active last"><a href="${basePath }/agdszzg/index">数字展厅</a></li>
				<!-- <li class="last"><a href="#">普通展厅</a></li> -->
			</ul>
		</div>
	</div>
</div>
<!--公共主头部结束--> 

<!--广告开始-->
<div class="public_ad" style="background:url(${basePath }/static/assets/img/science/banner.jpg) no-repeat 50% 0%;">
	<%--<c:if test="${not empty listadv}">
  		<img src="${basePath }/${listadv.cfgadvpic}" height="450" width="100%"/>
	</c:if>--%>
</div>
<!--广告结束--> 

<!--主体开始-->
<div class="main-info-bg main-info-bgColorW">
    <div class="main-info-container">
    
    	<!--分类信息   -->
	    <div class="categoryChange">
         	<div class="row clearfix">
         		<div class="title">分类</div>
				<div class="adrList adrList1">
					<span class="item active"><a arttyp="" href="javascript:void(0)">全部</a></span>
					<c:forEach items="${artList }" var="art" varStatus="s"> 
						<span class="item"><a href="#" arttyp="${art.id }">${art.name }</a></span>
					</c:forEach>
				</div>

				<div class="searchCont">
		        	<input placeholder="搜点什么..." id="select">
		            <button type="submit" id="selectBtn"></button>
		        </div>
      		</div>
		</div>
    	<!-- -END -->
    	
    	<!--空页面  -->
    	<div class="public-no-message none"></div>
    	<!--空页面-END-->
    	
   		<!--资讯内容  -->
		<div class="science-cont" id="cience">
      		<!--分页  -->
      		<div class="green-black" id="artPagging"></div>
      		<!--end  -->
   		<!-- -END -->	
    	</div>
	</div>
</div>
<!--主体结束--> 

<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
</body>
</html>