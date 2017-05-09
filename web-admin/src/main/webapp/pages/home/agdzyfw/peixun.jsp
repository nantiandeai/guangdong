<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆-志愿服务-志愿培训</title>

<link href="${basePath }/static/assets/css/volunteer/fengcaizhanshi.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/volunteer/fengcaizhanshi.js"></script>
<script type="text/javascript">
function send(_page, _rows) {
	var page = _page || 1;
	var rows = _rows || 9;
	var typid = $('.categoryChange > .row:eq(0) > .adrList > span.active > a').attr("typid");
	var time = $('.categoryChange > .row:eq(1) > .adrList > span.active > a').attr("time");
	//alert(typid);
	window.location.href = '${basePath}/agdzyfw/peixun?typid='+typid+'&time='+time+'&page='+page+'&rows='+rows;
}

$(function(){
	//加载分页工具栏
	var _page = parseInt( "${page}"||1 );
	var _pageSize = parseInt( "${pageSize}"||9 );
	var _total = parseInt( "${total}" );
	genPagging('whgPagging', _page, _pageSize, _total, function(page, rows){
		send(page, rows);
	});
	//初始时加载样式
	//$('.categoryChange >.row:eq(0) >.adrList > span:eq(0)').addClass('active').siblings('span').removeClass('active');
	//$(".categoryChange >.row:eq(1) >.adrList > span:eq(0)").addClass('active').siblings('span').removeClass('active');
	//
	//艺术类型
	$('.categoryChange >.row:eq(0) >.adrList > span > a').on('click', function(e){
		e.preventDefault();
		$(this).parent('span').addClass('active').siblings('span').removeClass('active');
		send();
	});
	//进行状态
	$('.categoryChange >.row:eq(1) >.adrList > span > a').on('click', function(e){
		e.preventDefault();
		$(this).parent('span').addClass('active').siblings('span').removeClass('active');
		send();
	});
	
	$("#btn_sub").on('click', function(e){
		e.preventDefault();
		var _val = $("#title").val();
		var page =  1;
		var rows =  9;
		var typid = $('.categoryChange > .row:eq(0) > .adrList > span.active > a').attr("typid");
		var time = $('.categoryChange > .row:eq(1) > .adrList > span.active > a').attr("time");
		
		window.location.href = '${basePath}/agdzyfw/peixun?typid='+typid+'&time='+time+'&page='+page+'&rows='+rows+'&zypxtitle='+_val;
	});
	//搜索的回车事件
	$("body").keydown(function() {
		var isFocus = $("#title").is(":focus");
		var _val = $("#title").val();
		if (event.keyCode == "13" && isFocus) {
			var page =  1;
			var rows =  9;
			var typid = $('.categoryChange > .row:eq(0) > .adrList > span.active > a').attr("typid");
			var time = $('.categoryChange > .row:eq(1) > .adrList > span.active > a').attr("time");
			
			window.location.href = '${basePath}/agdzyfw/peixun?typid='+typid+'&time='+time+'&page='+page+'&rows='+rows+'&zypxtitle='+_val;
			//加载默认数据...
			
		}
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
                <li><a href="${basePath }/agdzyfw/news">志愿资讯</a></li>
                <li><a href="${basePath }/agdzyfw/huodong">志愿活动</a></li>
                <li class="active"><a href="${basePath }/agdzyfw/peixun">志愿培训</a></li>
                <li><a href="${basePath }/agdzyfw/xiangmu">风采展示</a></li>
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
            <div class="title">类型</div>
            <div class="adrList">
                <span class="item ${(empty params.id or params.name eq '')? 'active':'' }"><a href="javascript:void(0)" typid="">全部</a></span>
	                <c:if test="${not empty pxtype }">
				        <c:forEach items="${pxtype }" var="row">
				        	<span class="item ${(not empty params.id and params.id eq row.id)? 'active':'' }"><a href="javascript:void(0)" typid="${row.id}">${row.name}</a></span>
				        </c:forEach>
			        </c:if>
            </div>
        </div>
        <div class="row clearfix">
            <div class="title">排序</div>
            <div class="adrList adrList1">
                <span class="item ${(empty params.time or params.time eq 0)? 'active':'' }"><a href="javascript:void(0)" time = "0">智能排序</a></span>
                <span class="item ${(not empty params.time and params.time eq 1)? 'active':'' }"><a href="javascript:void(0)" time = "1">最新发布</a></span>
            </div>
            <div class="searchCont">
                <input placeholder="搜点什么..." id="title" value="${params.zypxtitle }">
                <button type="submit" id="btn_sub"></button>
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
		                    <a href="${basePath }/agdzyfw/peixuninfo?zypxid=${row.zypxid}">
		                        <div class="img" >
		                        	<img src="${imgServerAddr }/${not empty row.zypxpic ? row.zypxpic : 'static/assets/img/img_demo/activi-img1.jpg' }" width="380" height="240"/>
		                            <div class="mask"></div>
		                        </div>
		                    </a>
		                    <div class="detail">
		                        <h2>${row.zypxshorttitle}</h2>
		                        <p>更新日期：<span><fmt:formatDate value="${row.zypxopttime}" pattern="yyyy-MM-dd "/></span></p>
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