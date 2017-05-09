<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>广东省文化馆-品牌活动</title>
    <%@include file="/pages/comm/comm_head.jsp"%>
    <%@include file="/pages/comm/comm_video.jsp"%>
    <link href="static/assets/css/special/special.css" rel="stylesheet">
</head>
<body>

<!-- 公共头部开始 -->
<%@include file="/pages/comm/szheader.jsp"%>
<!-- 公共头部结束 -->

<!--广告开始-->
<!-- 
<div class="art-ad" style="background:url(${basePath }/static/assets/img/art/banner.jpg) no-repeat 50% 50%"></div>
 -->
<!--广告结束-->

<!--主体开始-->
<div class="special-bg" 
<c:if test="${not empty whBrand.brabigpic }">
	style="background:url(${basePath }/${whBrand.brabigpic }) no-repeat ;" 
</c:if>

<c:if test="${empty whBrand.brabigpic }">
	style="background:url(${basePath }static/assets/img/special/special-bg.jpg) no-repeat ;" 
</c:if>

>
	
    <div class="special-main">
        <div class="banner">
            <h1>${whBrand.bratitle } </h1>
            <p>${whBrand.braintroduce }</p>
        </div>
        <div class="public-crumbs">
            <span><a href="${basePath }/">首页</a></span><span>></span>
            <span><a href="${basePath }/event/index">活动预约</a></span>
            <span>></span><span>${whBrand.bratitle } </span>
        </div>
        <div class="special-head">
            <div class="special-head-left" style="background:url(${basePath }/${whBrand.brapic }) no-repeat;"></div>
            <div class="special-head-right clearfix">
                <div class="head-father">
                	<c:forEach items="${whBrandActs}" var="items">
	                	<div class="head-con">    
	                        <h1>${whBrand.bratitle }</h1>
	                        <p><i class="address"></i>${items.bracaddr}</p>
	                        <p><i class="rili"></i><fmt:formatDate value="${items.bracsdate}" pattern="yyyy年MM月dd"/></p>
	                        <p><i class="time"></i><fmt:formatDate value="${items.bracstime}" pattern="HH:mm"/></p>
	                        <p><i class="phone"></i>${items.bractelephone }</p>
	                        <a class="lookdetail" href="${basePath }/event/detail?actvid=${items.bracactid }">查看活动详情</a>
	                    </div>
                    </c:forEach>
                    <c:forEach items="${curAct}" var="item">
                    <div class="head-con on">    
                        <h1>${whBrand.bratitle }</h1>
                        <p><i class="address"></i>${item.bracaddr}</p>
                        <p><i class="rili"></i><fmt:formatDate value="${item.bracsdate}" pattern="yyyy年MM月dd"/></p>
                        <p><i class="time"></i><fmt:formatDate value="${item.bracstime}" pattern="HH:mm"/></p>
                        <p><i class="phone"></i>${item.bractelephone }</p>
                        <a class="lookdetail" href="${basePath }/event/detail?actvid=${item.bracactid}">查看活动详情</a>
                    </div>
                    </c:forEach>
                </div>
                <div class="head-con2">
                    <ul>
	                    <c:forEach items="${whBrandActs}" var="items" varStatus="status">
	                    	<li class="clearfix">
	                            <div class="time"> 
	                            	<fmt:formatDate value="${items.bracsdate}" pattern="yyyy年MM月dd"/> 
	                            </div>
	                            <div class="list"><i></i>第${status.index+1 }期</div>
	                        </li>
	                     </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
        <div class="special-content clearfix">
            <div class="con-left">
                <ul class="tab clearfix">
                    <li class="active">活动现场</li>
                    <li>视频精选<i class="video"></i></li>
                </ul>
                <div class="list1 on">
                    <div class="jianshu">
                   		${whBrand.bradetail }
                    </div>
                    <div class="works-list">
                        <p>以下是部份精彩瞬间：</p>
                        <ul class="clearfix">
	                        <c:forEach items="${whBrandZY}" var="item">
	                            <li>
	                               	<img src="${basePath }/${item.enturl}">
	                               	<span>${item.entname}</span>
	                            </li>
	                         </c:forEach>   
                        </ul>
                    </div>
                </div>
                <div class="list1">
                    <div class="video clearfix">
                        <a href="javascript:void(0)" class="js__p_vedio_start">
	                        <c:forEach items="${whBransp}" var="item">
	                            <div class="video1">
	                            	<a href="javascript:void(0)" class="js__p_vedio_start opt_videoshow" vsurl="${item.enturl}">
	                                <img src="${basePath }/${item.deourl}">
	                                <span>${item.entname}</span>
	                            </div>
	                         </c:forEach>  
                        </a>
                    </div>
                </div>
            </div>
            <div class="con-right">
                <div class="map1">
                    <a href="javascript:void(0)"><img src="${basePath }/static/assets/img/special/map.jpg"></a>
                </div>
                <div class="event">
                    <h2>往期活动</h2>
                    <ul>
                     <c:forEach items="${act}" var="item">
                        <li>
                            <a href="javascript:void(0)">
                                <img src="${basePath }/${item.actvpic}">
                                <span>${item.actvtitle }</span>
                            </a>
                        </li>
                       </c:forEach>  

                    </ul>
                </div>
                <div class="info1">
                    <h2>活动资讯</h2>
                    <c:forEach items="${actZX}" var="item">
                    	<a href="${item.cfgshowlink }">${item.cfgshowtitle }</a>
                 	</c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
<!--主体结束-->

<!--底部开始-->
<%@include file="/pages/comm/szfooter.jsp"%>
<!--底部结束--> 

<!-- core public JavaScript -->
<script src="static/assets/js/public/sly.js"></script>
<script src="static/assets/js/plugins/stickySidebar.js"></script>
<script src="static/assets/js/special/special.js"></script>
</body>
</html>