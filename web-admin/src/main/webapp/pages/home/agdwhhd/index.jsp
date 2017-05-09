<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<%@include file="/pages/comm/agdhead.jsp"%>
<title>广东省文化馆-文化活动</title>
<link href="${basePath }/static/assets/css/activity/activity.css" rel="stylesheet">
<script src="${basePath }/static/assets/js/activity/activity.js"></script>

<script type="text/javascript">

$(function(){
	$("#more").on('click',function(){
		var msg=$('.tab .active').html();
		if(msg == "公告"){
			$(this).attr('href','${basePath}/agdwhhd/notice');
		}else{
			$(this).attr('href','${basePath}/agdwhhd/news');
		}
	})
})

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
				<li class="active"><a href="${basePath }/agdwhhd/index">文化活动</a></li>
				<li><a href="${basePath }/agdwhhd/activitylist">活动预约</a></li>
				 <li><a href="${basePath }/agdwhhd/notice">活动公告</a></li>
				<li><a href="${basePath }/agdwhhd/news">活动资讯</a></li>
				<li  class="last"><a href="${basePath }/agdwhhd/brandlist">品牌活动</a></li>
			</ul>
		</div>
	</div>
</div>
<!--公共主头部结束-END-->

<!--广告开始-->
<c:if test="${empty listAdv}">
	<div class="public_ad" style="background:url(${basePath }/static/assets/img/activity/banner.jpg) no-repeat 50% 50%;"></div>
</c:if>
<c:if test="${not empty listAdv}">
	<c:forEach items="${listAdv}" var="item">
		<div class="public_ad" style="background:url(${basePath }/${item.cfgadvpic }) no-repeat 50% 50%;"></div>
	</c:forEach>
</c:if>
<!--广告结束-->
<!--主体开始-->
<div class="main-info-bg bg-color">
    <div class="main-info-container main-info-title">
        <center><h2><span class="line-l"></span><span class="tt">最新动态</span><span class="line-r"></span></h2></center>
        <div class="news-cont clearfix">
            <div class="img">
              	<ul class="basic">
	                <c:forEach items="${zxpz}" var="item">
		            	<li>
			                <div class="mask">
			                    <a href="${basePath}/agdwhhd/newsinfo?id=${item.cfgshowid}">${item.cfgshowtitle }</a>
			                </div>
		                		<a href="${basePath}/agdwhhd/newsinfo?id=${item.cfgshowid}"><img src="${basePath }/${item.cfgshowpic}"  onerror="showDefaultIMG(this, '${basePath }/static/assets/img/img_demo/1.jpg')"></a>
		                </li>		
	                </c:forEach>
	                <c:if test="${empty zxpz || fn:length(zxpz)<1}">
		                <li>
	                        <div class="mask">
	                            <a href="javascript:void(0)">2016年度中国歌剧舞剧的院舞</a>
	                        </div>
	                        <a href="javascript:void(0)"><img src="${basePath}/static/assets/img/img_demo/activityindex1.jpg"  onerror="showDefaultIMG(this, '${basePath }/static/assets/img/img_demo/1.jpg')"></a>
	                	</li>
	                </c:if>
                </ul>
                <div class="btn-top btn-top-prev"></div>
                <div class="btn-top btn-top-next"></div>
            </div>
            <div class="newsGroups">
                <div class="tab">
                    <ul>
                       <!--  <li class="active">公告</li> -->
                        <li class="active">资讯</li>
                    </ul>
                </div>
               <%--  <div class="detail on">
                    <ul>
                    <c:if test="${not empty listgg}">
	            		<c:forEach items="${listgg}" var="item"> 
                       	 <li><i></i><a href="${basePath }/agdwhhd/noticeinfo?clnfid='+${item.clnfid}+'">${item.clnftltle}</a><span>
                       	 <fmt:formatDate value='${item.clnfcrttime}' pattern='yyyy-MM-dd'/></span></li>
	            		</c:forEach>
	            	</c:if>
                    </ul>
                </div> --%>
                <div class="detail on">
                    <ul>
                      <c:if test="${not empty listzx}">
	            		<c:forEach items="${listzx}" var="item"> 
	            			<li><i></i><a href="${basePath }/agdwhhd/newsinfo?id=${item.clnfid}">${item.clnftltle}</a><span><fmt:formatDate value="${item.clnfcrttime}" pattern="yyyy-MM-dd"/></span></li>
	            		</c:forEach>
	            	  </c:if>
                    </ul>
                </div>
                <div class="more">
                    <a class="public-more" id="more" href="javascript:void(0)">MORE</a>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="main-info-bg" style="background: url(${basePath }/static/assets/img/activity/activity-bg.jpg) no-repeat #FFFFFF">
    <div class="main-info-container main-info-title">
        <center><h2><span class="line-l"></span><span class="tt">品牌活动</span><span class="line-r"></span></h2></center>
        <div class="event-con clearfix">
            <div class="event-left">
                <h2>品牌活动</h2>
                <ul>
                    <%--<li>1.市属文化场馆免费开放，市民群众畅享公益文化服</li>
                    <li>2.各镇区文化服务积极开展城乡文体活动</li>
                    <li>3.开展国庆节前文化市场安全生产督查</li>
                    <li>4.全市各镇区文化部门积极响应，因地制宜，就地取材，着重挖掘本土历史文化资源。   如石岐区文化部门。</li>--%>
                    <li>2016年，为进一步丰富群众精神文化生活，引导和带动基层群众文化活动蓬勃开展，广东省文化馆积极创新载体、搭建平台，开展了一系列极具广东地方特色、贴近基层百姓生活、群众喜闻乐见的覆盖各年龄阶层、各类社会群体的群众文化活动。</li>
                </ul>
            </div>
            <div class="event-right">
                <div class="event-right-list">
                    <ul class="clearfix">
                    	<c:forEach items="${ppact}" var="item">
                        <li>
                           <div class="img">
                                <a href="${basePath }/agdwhhd/brandinfo?braid=${item.cfgshowid}">
                                	<img src="${basePath }/${item.cfgshowpic}"  onerror="showDefaultIMG(this, '${basePath }/static/assets/img/img_demo/1.jpg')"> 
                                </a>
                            </div>
                            <div class="detail">
                   				${item.cfgshowtitle}
                            </div>
                        </li>
                        </c:forEach>
                        <c:if test="${empty ppact}">
                        	<li class="none"></li>
                        </c:if>
                    </ul>
                </div>
                <div class="arrow">
                    <div class="arrow-left">
                        <i></i>
                    </div>
                    <div class="arrow-right">
                        <i></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="main-info-bg bg-color">
    <div class="main-info-container main-info-title">
        <center><h2><span class="line-l"></span><span class="tt">活动预约</span><span class="line-r"></span></h2></center>
        <div class="cul-cont clearfix">
            <div class="label">
                <ul class="clearfix">
	                <c:forEach items="${acttype }" var="item">
	                    <li><a href="${basePath }/agdwhhd/activitylist?actvtype=${item.typid}" >${item.typname}</a></li>
	                </c:forEach>
	                	<li><a href="${basePath }/agdwhhd/activitylist" >全部</a></li>
                </ul>
            </div>
            
            <div class="list-detail">
                <ul class="clearfix">
                <c:forEach items="${act}" var="item">
                    <li>
                        <div class="img">
                            <a href="${basePath }/agdwhhd/activityinfo?actvid=${item.cfgshowid}"><img src="${basePath }/${item.cfgshowpic}"  onerror="showDefaultIMG(this, '${basePath }/static/assets/img/img_demo/1.jpg')">
                        </div>
                        <div class="detail">
                            <div class="title"><span>${item.cfgshowtitle}</span></div>
                            <div class="time">时间：<span><fmt:formatDate value='${item.cfgshowtime}' pattern='yyyy-MM-dd'/></span></div>
                            <div class="address">地址：<span>${item.actvaddress}</span></div>
                        </div>
                    </li>
                </c:forEach>
                </ul>
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