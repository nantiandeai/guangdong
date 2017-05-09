<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆-志愿服务-项目示范-项目示范列表</title>
<link href="${basePath }/static/assets/css/volunteer/fengcaizhanshi.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/volunteer/fengcaizhanshi.js"></script>
</head>
<body>
<script type="text/javascript">
$(function(){
	//加载分页工具栏
	var _page = parseInt( "${page}"||1 );
	var _pageSize = parseInt( "${pageSize}"||9 );
	var _total = parseInt( "${total}" );
	genPagging('whgPagging', _page, _pageSize, _total, function(page, rows){
		window.location.href = '${basePath}/agdzyfw/xiangmu?page='+page+'&rows='+rows;
	});
})
</script>
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
		        <li><a href="${basePath }/agdzyfw/news">志愿资讯</a></li>
		        <li><a href="${basePath }/agdzyfw/huodong">志愿活动</a></li>
		        <li><a href="${basePath }/agdzyfw/peixun">志愿培训</a></li>
		        <li class="active"><a href="${basePath }/agdzyfw/xiangmu">风采展示</a></li>
		        <li><a href="${basePath }/agdzyfw/tashan">他山之石</a></li>
		        <li class="last"><a href="${basePath }/agdzyfw/zhengce">政策法规</a></li>
            </ul>
        </div>
    </div>
</div>
<!--公共主头部结束-END-->

<!--主体开始-->
<div id="content">
    <div class="categoryChange">
        <div class="row clearfix">
            <div class="title">风采展示</div>
            <div class="adrList">
                <span class="item"><a href="${basePath }/agdzyfw/geren">先进个人</a></span>
                <span class="item active"><a href="javascript:void(0)">项目示范</a></span>
                <span class="item"><a href="${basePath }/agdzyfw/youxiuzuzhi">优秀组织</a></span>
            </div>
        </div>
    </div>
    <div class="active-list container">
        <div class="con">
            <ul class="clearfix">
            	<c:choose>
				   <c:when test="${not empty rows }">  
					   <c:forEach items="${rows }" var="row" varStatus="s">
			                <li>
			                    <a href="${basePath }/agdzyfw/xiangmuinfo?zyfcxmid=${row.zyfcxmid}">
			                        <div class="img">
			                        	<img src="${imgServerAddr }/${row.zyfcxmpic}" width="380" height="240"/>
			                            <div class="mask"></div>
			                        </div>
			                    </a>
			                    <div class="detail">
			                        <h2>${row.zyfcxmshorttitle}</h2>
			                        <p>实施单位：<span>${row.zyfcxmssdw}</span></p>
			                    </div>
			                </li>
		               </c:forEach>     
				   </c:when>
				   <c:otherwise> 
				   		<div class="public-no-message "></div>
				   </c:otherwise>
				</c:choose>
            </ul>
        </div>
    </div>
</div>
<!--主体结束-->
<!-- 分页栏 -->
    <div class="green-black" id="whgPagging">
    </div>
<!-- 分页栏-END -->
<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
</body>
</html>