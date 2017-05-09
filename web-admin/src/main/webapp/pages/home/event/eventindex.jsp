<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" >
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="author" content="">
<title>广东省文化馆-活动预约首页</title>
<!-- core public JavaScript -->
<%@include file="/pages/comm/comm_head.jsp"%>
<link href="${basePath }/static/assets/css/event/eventindex.css" rel="stylesheet">
</head>
<body>

<!-- 公共头部开始 -->
<%@include file="/pages/comm/szheader.jsp"%>
<!-- 公共头部结束 -->

<!--广告开始-->
<div class="art-ad" style="background:url(${basePath}/static/assets/img/event/event-banner.jpg) no-repeat 50% 50%">
	<c:if test="${not empty advlist}">
		<img src="${basePath }/${advlist.cfgadvpic}" height="250" width="100%"/>
	</c:if>
</div>
<!--广告结束-->

<!--主体开始-->
<div class="event-main clearfix">
    <div>
    	<c:if test="${not empty ppact}">
        <div class="event">
            <div>
                <span>品牌活动</span>
                <a href="${basePath}/event/srchlist">更多</a>
            </div>
            <ul class="eventlist clearfix">
				<c:if test="${not empty ppact}">
	            	<c:forEach items="${ppact}" var="item">
		            	<li>
		                    <div class="img">
		                        <div class="img-mask" style="background-image:url(${basePath }/static/assets/img/event/appt-1.jpg)">
		                        	<c:if test="${item.cfgshowpic != null}">
				                    	<a href="${basePath}/event/pplist?braid=${item.cfgshowid}">
		                        			<img src="${basePath }/${item.cfgshowpic}" width="386"/>
				                        </a>
		                        	</c:if>
		                        </div>
		                    </div>
		                    <div class="detail">
		                        <p class="author">${item.cfgshowtitle}</p>
		                        <p class="pageCount">简介:${item.cfgshowintroduce}</p>
		                    </div>
		                </li>
	            	</c:forEach>
                </c:if>
            </ul>
        </div>
        </c:if>

		<c:if test="${not empty ssact}">
        <div class="event">
            <div>
                <span>赛事活动</span>
                <a href="${basePath }/event/list?actvtype=2016101400000010">更多</a>
            </div>
            <ul class="eventlist clearfix">
				<c:if test="${not empty ssact}">
	            	<c:forEach items="${ssact}" var="item"> 
		                <li>
		                    <div class="img">
		                        <div class="img-mask" style="background-image:url(${basePath }/static/assets/img/event/appt-1.jpg)">
		                        	<c:if test="${item.cfgshowpic != null}">
		                        		<a href="${basePath}/event/detail?actvid=${item.cfgshowid}">
		                        			<img src="${basePath }/${item.cfgshowpic}" width="386"/>
		                        		</a>
		                        	</c:if>
		                        </div>
		                    </div>
		                    <div class="detail">
		                        <p class="author">${item.cfgshowtitle}</p>
		                        <p class="pageCount">简介:${item.cfgshowintroduce}</p>
		                    </div>
		                </li>
	            	</c:forEach>
                </c:if>	            	          
            </ul>
        </div>
        </c:if>

		<c:if test="${not empty zlact}">
        <div class="event">
            <div>
                <span>展览</span>
                <a href="${basePath }/event/list?actvtype=2016101400000011">更多</a>
            </div>
            <ul class="eventlist clearfix">
            	<c:if test="${not empty zlact}">
	            	<c:forEach items="${zlact}" var="item"> 
		                <li>
		                    <div class="img">
		                        <div class="img-mask" style="background-image:url(${basePath }/static/assets/img/event/appt-1.jpg)">
		                        	<c:if test="${item.cfgshowpic != null}">
		                        		<a href="${basePath}/event/detail?actvid=${item.cfgshowid}">
		                        			<img src="${basePath }/${item.cfgshowpic}" width="386"/>
		                        		</a>
		                        	</c:if>
		                        </div>
		                    </div>
		                    <div class="detail">
		                        <p class="author">${item.cfgshowtitle}</p>
		                        <p class="pageCount">简介:${item.cfgshowintroduce}</p>
		                    </div>
		                </li>
                	</c:forEach>
                </c:if>	  
            </ul>
        </div>
		</c:if>
		
		
		<c:if test="${not empty wenhuaact}">
        <div class="event">
            <div>
                <span>文化惠民</span>
                <a href="${basePath }/event/list?actvtype=2016101400000012">更多</a>
            </div>
            <ul class="eventlist clearfix">
            	<c:if test="${not empty wenhuaact}">
	            	<c:forEach items="${wenhuaact}" var="item"> 
		                <li>
		                    <div class="img">
		                        <div class="img-mask" style="background-image:url(${basePath }/static/assets/img/event/appt-1.jpg)">
		                        	<c:if test="${item.cfgshowpic != null}">
		                        		<a href="${basePath}/event/detail?actvid=${item.cfgshowid}">
		                        			<img src="${basePath }/${item.cfgshowpic}" width="386"/>
		                        		</a>
		                        	</c:if>
		                        </div>
		                    </div>
		                    <div class="detail">
		                        <p class="author">${item.cfgshowtitle}</p>
		                        <p class="pageCount">简介:${item.cfgshowintroduce}</p>
		                    </div>
		                </li>
                	</c:forEach>
                </c:if>	
            </ul>
        </div>
        </c:if>
        
    </div>
</div>
<!--主体结束-->

<!--底部开始-->
<%@include file="/pages/comm/szfooter.jsp"%>
<!--底部结束-->

</body>
</html>
