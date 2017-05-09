<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆-培训驿站-在线报名</title>
<link href="${basePath }/static/assets/css/train/registration.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/train/registration.js"></script>
<script type="text/javascript">

function send(_page, _rows) {
	var page = _page || 1;
	var rows = _rows || 9;
	var cult = $('.categoryChange > .masterRow > .clearfix > li.active > a').attr("cult");
	var areaid = $('.categoryChange > .row:eq(0) > .adrList > span.active > a').attr("areaid");
	var typid = $('.categoryChange > .row:eq(1) > .adrList > span.active > a').attr("typid");
	var tratype = $('.categoryChange > .row:eq(2) > .adrList > span.active > a').attr("tratype");
	var time = $('.categoryChange > .row:eq(3) > .adrList > span.active > a').attr("time");
	//alert(cult);
	window.location.href = '${basePath}/agdpxyz/trainlist?traarea='+areaid+'&typid='+typid+'&time='+time+'&page='+page+'&rows='+rows+'&tratype='+tratype+'&cult='+cult;
}

function showArea(cult){
	if(cult == "" || cult == "TOP"){

		$(".categoryChange > .row:eq(0)").css("display","none");
	}
	if( cult == "1"){

		$(".categoryChange > .row:eq(0)").css("display","");
	}
}

$(function(){
	var _page = parseInt( "${page}"||1 );
	var _pageSize = parseInt( "${pageSize}"||9 );
	var _total = parseInt( "${total}" );
	
	//加载分页工具栏
	genPagging('whgPagging', _page, _pageSize, _total, function(page, rows){
		//window.location.href = '${basePath}/agdpxyz/trainlist?page='+page+'&rows='+rows;
		send(page, rows);
	});

	var cult = $('.categoryChange > .masterRow > .clearfix > li.active > a').attr("cult");
	showArea(cult);
	//初始时加载样式
	//$('.categoryChange > .masterRow > .clearfix > li:eq(0) > a').addClass('active').siblings('span').removeClass('active');
	$(".main > categoryChange >row:eq(0) >adrList > span:eq(0)").addClass('active').siblings('span').removeClass('active');
	$(".main > categoryChange >row:eq(1) >adrList > span:eq(0)").addClass('active').siblings('span').removeClass('active');
	$(".main > categoryChange >row:eq(2) >adrList > span:eq(0)").addClass('active').siblings('span').removeClass('active');
	$(".main > categoryChange >row:eq(3) >adrList > span:eq(0)").addClass('active').siblings('span').removeClass('active');


	//发起
	$('.categoryChange > .masterRow > .clearfix > li > a').on('click', function(e){

		e.preventDefault();
		$(this).parent('li').addClass('active').siblings('li').removeClass('active');
		var cult = $('.categoryChange > .masterRow > .clearfix > li.active > a').attr("cult");
		showArea(cult);
		send();
	});
	//区域
	$('.categoryChange > .row:eq(0) > .adrList > span > a').on('click', function(e){
		e.preventDefault();
		$(this).parent('span').addClass('active').siblings('span').removeClass('active');
		send();
		
	});
	//艺术类型
	$('.categoryChange > .row:eq(1) > .adrList > span > a').on('click', function(e){
		e.preventDefault();
		$(this).parent('span').addClass('active').siblings('span').removeClass('active');
		send();
	});
	//培训类型
	$('.categoryChange > .row:eq(2) > .adrList > span > a').on('click', function(e){
		e.preventDefault();
		$(this).parent('span').addClass('active').siblings('span').removeClass('active');
		send();
	});
	//进行状态
	$('.categoryChange > .row:eq(3) > .adrList > span > a').on('click', function(e){
		e.preventDefault();
		$(this).parent('span').addClass('active').siblings('span').removeClass('active');
		send();
	});
	
	$("#btn_sub").on('click', function(e){
		e.preventDefault();
		var _val = encodeURI($("#title").val());
		var page =  1;
		var rows =  9;
		var cult = $('.categoryChange > .masterRow > .clearfix > li.active > a').attr("cult");
		var areaid = $('.categoryChange > .row:eq(0) > .adrList > span.active > a').attr("areaid");
		var typid = $('.categoryChange > .row:eq(1) > .adrList > span.active > a').attr("typid");
		var tratype = $('.categoryChange > .row:eq(2) > .adrList > span.active > a').attr("tratype");
		var time = $('.categoryChange > .row:eq(3) > .adrList > span.active > a').attr("time");
		window.location.href = '${basePath}/agdpxyz/trainlist?traarea='+areaid+'&typid='+typid+'&time='+time+'&page='+page+'&rows='+rows+'&tratitle='+_val+'&tratype='+tratype+'&cult='+cult;
	});
	//搜索的回车事件
	$("body").keydown(function() {
		var isFocus = $("#title").is(":focus");
		var _val = encodeURI($("#title").val());
		if (event.keyCode == "13" && isFocus) {
			var page =  1;
			var rows =  9;
			var cult = $('.categoryChange > .masterRow > .clearfix > li.active > a').attr("cult");
			var areaid = $('.categoryChange > .row:eq(0) > .adrList > span.active > a').attr("areaid");
			var typid = $('.categoryChange > .row:eq(1) > .adrList > span.active > a').attr("typid");
			var tratype = $('.categoryChange > .row:eq(2) > .adrList > span.active > a').attr("tratype");
			var time = $('.categoryChange > .row:eq(3) > .adrList > span.active > a').attr("time");
			window.location.href = '${basePath}/agdpxyz/trainlist?traarea='+areaid+'&typid='+typid+'&time='+time+'&page='+page+'&rows='+rows+'&tratitle='+_val+'&tratype='+tratype+'&cult='+cult;
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
        	<div class="logo-small">
            	<a href="${basePath }/home"><img src="${basePath }/static/assets/img/public/logoSmall.png"></a>
            </div>
            <ul>
            	<%--<li><a href="${basePath }/agdpxyz/index">培训驿站</a></li>--%>
            	<li><a href="${basePath }/agdpxyz/notice">培训公告</a></li>
                <li><a href="${basePath }/agdpxyz/news">培训资讯</a></li>
                <li><a href="${basePath }/agdpxyz/teacher">培训师资</a></li>
                <li class="active"><a href="${basePath }/agdpxyz/trainlist">在线报名</a></li>
                <li class="last"><a href="${basePath }/agdpxyz/vod">在线点播</a></li>
               <%--  <li class="last"><a href="${basePath }/agdpxyz/resources">培训资源库</a></li> --%>
            </ul>
        </div>
    </div>
</div>
<!--公共主头部开始-END-->

<!--主体开始-->
<div class="main-info-bg main-info-no-padding main-info-bgColorW">
  <div class="main-info-container">
    <div class="categoryChange">

		<div class="masterRow">
			<ul class="clearfix">
				<li class="${(empty params.cult or params.cult eq 'TOP')? 'active':'' } closeAdr"><a href="javascript:void(0)" cult="TOP">省馆</a></li>
				<li class="showAdr ${(not empty params.cult and params.cult eq '1')? 'active':'' }"><a href="javascript:void(0)" cult="1">全省</a></li>
			</ul>
		</div>

      <%--<div class="row clearfix">
        <div class="title">发起</div>
        <div class="adrList">
			<span class="item ${(empty params.cult or params.cult eq 'TOP')? 'active':'' }"><a href="javascript:void(0)" cult="TOP">省馆</a></span>
			<span class="item ${(not empty params.cult and params.cult eq '1')? 'active':'' }"><a href="javascript:void(0)" cult="1">全省</a></span>
		</div>
      </div>--%>
      
      <div class="row clearfix adrCont ">
        <div class="title">区域</div>
        <div class="adrList"> 
	        <span class="item ${(empty params.traarea or params.traarea eq '')? 'active':'' }"><a href="javascript:void(0)" areaid="">全部</a></span> 
	       <c:if test="${not empty qrtypes }">
	        <c:forEach items="${qrtypes }" var="row">
	        	<span class="item ${(not empty params.traarea and params.traarea eq row.id)? 'active':'' }"><a href="javascript:void(0)" areaid="${row.id}">${row.name}</a></span>
	        </c:forEach>
	       </c:if>
        </div>
      </div>
      
      <div class="row clearfix">
        <div class="title">艺术类型</div>
        <div class="adrList"> 
	        <span class="item ${(empty params.typid or params.typid eq '')? 'active':'' }"><a href="javascript:void(0)" typid="">全部</a></span>
	        <c:if test="${not empty ystypes }">
	        <c:forEach items="${ystypes }" var="rows">
	        	<span class="item ${(not empty params.typid and params.typid eq rows.id)? 'active':'' }"><a href="javascript:void(0)" typid="${rows.id}">${rows.name}</a></span>
	        </c:forEach>
	       </c:if>
        </div>
      </div>

		<div class="row clearfix">
			<div class="title">培训类型</div>
			<div class="adrList">
				<span class="item ${(empty params.tratype or params.tratype eq '')? 'active':'' }"><a href="javascript:void(0)" tratype="">全部</a></span>
				<c:if test="${not empty tratype }">
					<c:forEach items="${tratype }" var="rows">
						<span class="item ${(not empty params.tratype and params.tratype eq rows.id)? 'active':'' }"><a href="javascript:void(0)" tratype="${rows.id}">${rows.name}</a></span>
					</c:forEach>
				</c:if>
			</div>
		</div>
      
      <div class="row clearfix">
        <div class="title">排序</div>
        <div class="adrList adrList1"> 
     
	        <span class="item ${(empty params.time or params.time eq 0)? 'active':'' }"><a href="javascript:void(0) " time="0">智能排序</a></span> 
	        <span class="item ${(not empty params.time and params.time eq 1)? 'active':'' }"><a href="javascript:void(0) " time="1">即将开始</a></span> 
	        <span class="item ${(not empty params.time and params.time eq 2)? 'active':'' }"><a href="javascript:void(0) " time="2" >即将结束</a></span> 
	   <!-- <span class="item"><a href="javascript:void(0)">最新发布</a></span> 
	        <span class="item"><a href="javascript:void(0)">人气最高</a></span> -->
	    
        </div>
        
        <div class="searchCont">
          <input placeholder="搜点什么..." id="title">
          <button type="submit" id="btn_sub"></button>
        </div>
        
      </div>
    </div>
    
    <div class="registration container">
        <div class="con">
            <ul class="clearfix">
	            <c:choose>
				   <c:when test="${not empty rows }">  
					   <c:forEach items="${rows }" var="row" varStatus="s">
			                <li>
			                    <a href="${basePath }/agdpxyz/traininfo?traid=${row.id}">
			                        <div class="img">
			                        	<img src="${imgServerAddr}${whg:getImg750_500(row.trainimg)}" width="380" height="240"/>
			                            <div class="mask"></div>
			                        </div>
			                    </a>
			                    <div class="detail">
			                        <h2>${row.title}</h2>
			                        <!-- <p class="page">余数 :<span class="num"><span>168</span>/200</span></p> -->
			                        <p class="adr">地址 :<span>${row.address }</span></p>
			                        <p class="time">时间 :<span><fmt:formatDate value="${row.starttime }" pattern="yyyy-MM-dd "/></span>至<span><fmt:formatDate value="${row.endtime }" pattern="yyyy-MM-dd "/></span></p>
									<c:if test="${not empty row.age}">
										<p class="time">年龄段 :<span>${row.age}</span>岁</p>
									</c:if>
									<c:if test="${empty row.age}">
										<p class="time">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
									</c:if>
								</div>
			                </li>
		               </c:forEach>      
				   </c:when>
				   <c:otherwise> 
				   		<div class="public-no-message "></div>
				   </c:otherwise>
				</c:choose>
            </ul>
            <!-- 分页栏 -->
			    <div class="green-black" id="whgPagging">
			    </div>
			<!-- 分页栏-END -->
        </div>
    </div>
  </div>
</div>
<!--主体结束-->



<!--公共主底部开始-->
<%@include file="/pages/comm/agdfooter.jsp"%>
<!--公共主底部结束-END-->
</body>
</html>