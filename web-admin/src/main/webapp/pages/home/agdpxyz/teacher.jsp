<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆-培训驿站-培训师资</title>
<link href="${basePath }/static/assets/css/train/teach.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/train/teach.js"></script>
<script type="text/javascript">
function loadData(page, rows){
	var page = page || 1;//分页加载第一页 
	var rows = rows || 10;//每页10条记录
	
	//areaid
	var areaid = "";
	$('#areaDiv > span > a').each(function(idx){
		if($(this).parent('span').hasClass('active')){
			areaid = $(this).attr('areaid');
		}
	});
	
	//artid
	var artid = "";
	$('#artDiv > span > a').each(function(idx){
		if($(this).parent('span').hasClass('active')){
			artid = $(this).attr('artid');
		}
	});
	
	//srchkey
	var srchkey = $(".searchCont input").val();
	
	//url
	var url = "${basePath }/agdpxyz/teacher?page="+page+"&rows="+rows;
	if(areaid != ""){
		url += '&areaid='+areaid;
	}
	if(artid != ""){
		url += '&artid='+artid;
	}
	if(srchkey != ""){
		url += '&srchkey='+encodeURIComponent(srchkey);
	}
	window.location.href = url;
}


/** page onload event */
$(function(){
	//加载分页工具栏
	genPagging('whgPagging', '${page}' || 1, 10, '${total}', loadData);
	
	//区域
	$('#areaDiv > span > a').on('click', function(e){
		e.preventDefault();
		$(this).parent('span').addClass('active').siblings('span').removeClass('active');
		loadData();
	});
	
	//分类
	$('#artDiv > span > a').on('click', function(e){
		e.preventDefault();
		$(this).parent('span').addClass('active').siblings('span').removeClass('active');
		loadData();
	});
	
	//搜索
	$('.searchCont > button').unbind('click.sz').bind('click.sz', function(e){
		e.preventDefault();
		loadData();
	});
	
	//回车事件
	$("body").keydown(function() {
		var isFocus = $(".searchCont input").is(":focus");
        if (event.keyCode == "13" && isFocus) {
        	//加载默认数据...
        	loadData();
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
                <li class="active"><a href="${basePath }/agdpxyz/teacher">培训师资</a></li>
                <li><a href="${basePath }/agdpxyz/trainlist">在线报名</a></li>
                <li class="last"><a href="${basePath }/agdpxyz/vod">在线点播</a></li>
                <%-- <li class="last"><a href="${basePath }/agdpxyz/resources">培训资源库</a></li> --%>
            </ul>
        </div>
    </div>
</div>
<!--公共主头部开始-END-->

<!--主体开始-->
<div class="main-info-bg main-info-no-padding main-info-bgColorW">
  <div class="main-info-container">
    <div class="categoryChange">
        <div class="row clearfix">
            <div class="title">区域</div>
            <div class="adrList" id="areaDiv">
                
                <c:choose>
                	<c:when test="${areaid == null}">
		                <span class="item active"><a href="javascript:void(0)" areaid="">全部</a></span>
                	</c:when>
                	<c:otherwise>
		                <span class="item"><a href="javascript:void(0)" areaid="">全部</a></span>
                	</c:otherwise>
                </c:choose>
                
                <c:if test="${areaList != null && fn:length(areaList) > 0 }">
                	<c:forEach items="${areaList }" var="row" varStatus="s">
                		<c:if test="${areaid != null && areaid == row.id }">
	                		<span class="item active"><a href="javascript:void(0)"  areaid="${row.id }" >${row.name }</a></span>
                		</c:if>
                		<c:if test="${!(areaid != null && areaid == row.id ) }">
	                		<span class="item"><a href="javascript:void(0)"  areaid="${row.id }" >${row.name }</a></span>
                		</c:if>
                	</c:forEach>
                </c:if>
            </div>
        </div>
        <div class="row clearfix">
            <div class="title">类型</div>
            <div class="adrList adrList1" id="artDiv">
                
                <c:choose>
                	<c:when test="${artid == null}">
		                <span class="item active"><a href="javascript:void(0)" artid="">全部</a></span>
                	</c:when>
                	<c:otherwise>
		                <span class="item"><a href="javascript:void(0)" artid="">全部</a></span>
                	</c:otherwise>
                </c:choose>
                
                <c:if test="${artList != null && fn:length(artList) > 0 }">
                	<c:forEach items="${artList }" var="row" varStatus="s">
                		<c:if test="${artid != null && artid == row.id }">
	                		<span class="item active"><a href="javascript:void(0)" artid="${row.id }" >${row.name }</a></span>
                		</c:if>
                		<c:if test="${!(artid != null && artid == row.id) }">
	                		<span class="item"><a href="javascript:void(0)" artid="${row.id }" >${row.name }</a></span>
                		</c:if>
                	</c:forEach>
                </c:if>
            </div>
            <div class="searchCont">
                <input placeholder="搜点什么..." value="${srchkey }">
                <button type="button" id="artSearch"></button>
            </div>
        </div>
    </div>
    <div class="teacherList">
    	<ul>
    		<c:choose>
			   <c:when test="${rows != null && fn:length(rows) > 0 }">  
					<c:forEach items="${rows }" var="row" varStatus="s">
			        	<li>
			            	<div class="img">
			                	<a href="${basePath }/agdpxyz/teacherinfo?id=${row.teacherid}"><img src="${imgServerAddr}${row.teacherpic}" width="136" height="136"></a>
			                </div>
			                <div class="info">
			                	<a href="${basePath }/agdpxyz/teacherinfo?id=${row.teacherid}">
				                	<h2>${row.teachername }</h2>
									<c:if test="${not empty row.teachertype }">
										<p class="type">专长 :<span>${row.teachertype }</span></p>
									</c:if>
									<c:if test="${not empty row.teacherintroduce }">
				                    	<p>简介 :<span>${row.teacherintroduce }</span></p>
									</c:if>
			                    </a>
			                    <div class="arrow"></div>
			                </div>
			            </li>
	    			</c:forEach>      
			   </c:when>
			   <c:otherwise> 
			   		<div class="public-no-message "></div>
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
</body>
</html>